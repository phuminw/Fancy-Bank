package fancybank.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import fancybank.FancyBank;
import fancybank.misc.InterestRateDBCorruptException;
import fancybank.misc.InterestRateDBSorter;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

public class SavingAccount extends Account {
    // private double interest; // APY e.g. 0.1 means 10%
    private Tuple<YearMonth, Integer> withdrawCount; // Associate with Month
    private int withdrawCountLimit;
    // private HashMap<YearMonth, Double> intRate; // K: year V: (K: month V: interest rate)
    private PriorityQueue<Tuple<Tuple<YearMonth, YearMonth>, Double>> intRateHistory; // ((start, end), intRate)
    private HashMap<YearMonth, Double> intEarned; // K: year month V: interest earned
    private YearMonth intCheckPoint; // Most recent interest rate calculation

    public SavingAccount(double interest, int withdrawCountLimit) {
        super();
        // this.interest = interest;
        this.withdrawCount = new Tuple<YearMonth, Integer>(YearMonth.now(), 0);
        this.withdrawCountLimit = withdrawCountLimit;
        this.intRateHistory = new PriorityQueue<Tuple<Tuple<YearMonth, YearMonth>, Double>>(new InterestRateDBSorter());
        // intRate.put(YearMonth.now(), interest);
        this.intEarned = new HashMap<YearMonth, Double>();
        this.intCheckPoint = null;

        // Record interest rate if not in debug mode. Otherwise, ignore interest param
        if (!FancyBank.DEBUG)
            intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(YearMonth.now(), null), interest)); // Record current interest rate
    }

    /**
     * DEBUG constructor 
     * 
     * @param interest
     * @param withdrawCountLimit
     * @param openedDate
     * @param closedDate
     */

    public SavingAccount(double balance, double interest, int withdrawCountLimit, LocalDate openedDate, LocalDate closedDate) {
        super(balance, openedDate, closedDate);

        if (FancyBank.DEBUG)
            this.withdrawCount = new Tuple<YearMonth, Integer>(YearMonth.of(openedDate.getYear(), openedDate.getMonth()), 0);
        else
            this.withdrawCount = new Tuple<YearMonth, Integer>(YearMonth.now(), 0);
            
        this.withdrawCountLimit = withdrawCountLimit;
        this.intRateHistory = new PriorityQueue<Tuple<Tuple<YearMonth, YearMonth>, Double>>(new InterestRateDBSorter());
        // intRate.put(YearMonth.now(), interest);
        this.intEarned = new HashMap<YearMonth, Double>();
        this.intCheckPoint = null;

        // Record interest rate if not in debug mode. Otherwise, ignore interest param
        if (!FancyBank.DEBUG)
            intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(YearMonth.now(), null), interest)); // Record current interest rate
    }

    /**
     * @return this account interest rate
     */

    public Double getInterestRate() {
        return intRateHistory.peek().getSecond();
    }

    /**
     * @return the withdrawCount
     */

    public Integer getWithdrawCount() {
        return withdrawCount.getSecond();
    }

    /**
     * @return the withdrawCountLimit
     */

    public int getWithdrawCountLimit() {
        return withdrawCountLimit;
    }

    /**
     * @return interest earned on the specified year and month. Zero if invalid year/month or actually zero.
     */

    public Double getIntEarned(YearMonth ym) {
        Double intEarnedYearMonth = intEarned.get(ym);
        return (intEarnedYearMonth == null) ? 0 : intEarnedYearMonth;
    }

    /**
     * Update interest rate of this account
     * 
     * @param interest new interest rate
     * @param start start date (ignore if not in debug mode)
     * @param end end date (ignore if not in debug mode)
     * @return success or not
     */

    public boolean setIntRate(double interest, YearMonth start, YearMonth end) {
        Tuple<Tuple<YearMonth,YearMonth>,Double> recentInt;

        // Invalidate old entry, if exists
        if ((recentInt = intRateHistory.poll()) != null) {
            // Check whether trying to overwrite history or not
            if (recentInt.getFirst().getFirst().isAfter(start))
                return false;

            if (FancyBank.DEBUG)
                return intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(recentInt.getFirst().getFirst(), start), recentInt.getSecond()));
            else
                return intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(recentInt.getFirst().getFirst(), YearMonth.now()), recentInt.getSecond()));
        }
        
        if (FancyBank.DEBUG) {
            return intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth, YearMonth>(start, end), interest));
        }

        return intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(YearMonth.now(), null), interest));
    }

    /**
     * Attempt to add balance to saving account. Check for restrictions and take action.
     * 
     * @param amount amount to add
     * @param currency currency
     * @param description optional description
     * @param time xact time (ignore if not debug)
     * @return success or fail
     */

    public boolean addBalance(double amount, String currency, String description, LocalDateTime time) {
        if (FancyBank.DEBUG)
            return addTransaction(new Transaction(Transaction.DEPOSIT, null, amount, currency, description, time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        return addTransaction(new Transaction(Transaction.DEPOSIT, amount, currency, description));
    }

    /**
     * Attempt to deduct balance from saving account. Check for restrictions and take action.
     * 
     * @param amount amount to deduct
     * @param currency currency
     * @param description optional description
     * @param time transaction time (debug). Ignore if not debug
     * @return success or fail
     */

    public boolean deductBalance(double amount, String currency, String description, LocalDateTime time) {
        YearMonth currentYM;

        if (FancyBank.DEBUG) {
            currentYM = YearMonth.of(time.getYear(), time.getMonth());
            if (currentYM.isBefore(withdrawCount.getFirst()))
                // Tried to go into history
                return false;
        }
        else
            currentYM = YearMonth.now();

        // New month, so reset withdrawal count
        if (currentYM.isAfter(withdrawCount.getFirst())) {
            withdrawCount = new Tuple<YearMonth,Integer>(currentYM, 0);
        }

        if (withdrawCount.getSecond() >= withdrawCountLimit) {
            return false;
        }

        if (FancyBank.DEBUG) {
            if (addTransaction(new Transaction(Transaction.WITHDRAW, null, amount, currency, description, time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))))) {
                withdrawCount = new Tuple<YearMonth,Integer>(withdrawCount.getFirst(), withdrawCount.getSecond()+1);
                return true;
            }
    
            return false;
        }

        if (addTransaction(new Transaction(Transaction.WITHDRAW, amount, currency, description))) {
            withdrawCount = new Tuple<YearMonth,Integer>(withdrawCount.getFirst(), withdrawCount.getSecond()+1);
            return true;
        }

        return false;
    }

    /**
     * Calculate interest on this account up to specified date
     * 
     * @param ym today (or simulated date). Ignore if not debug.
     */
    @SuppressWarnings({"unchecked"})

    public void calculateInterest(LocalDate date) {
        Set<Currency> currencies = getCurrencies();
        for (Currency c: currencies) {
            // TODO: Interest rate calculation on both debug and non-debug mode
            Tuple<Tuple<YearMonth, YearMonth>, Double>[] intEntries = (Tuple<Tuple<YearMonth, YearMonth>, Double>[]) intRateHistory.toArray();

            if (intRateHistory.peek().getFirst().getSecond() != null) {
                throw new InterestRateDBCorruptException("Most recent interest rate entry was invalidated and no alive entry");
            }

            if (!FancyBank.DEBUG)
                date = LocalDate.now(); // Ignore param and set to today

            YearMonth checkpoint = (intCheckPoint == null) ? intEntries[0].getFirst().getFirst().minusMonths(1) : intCheckPoint;
            for (int i = 0 ; i < intEntries.length && checkpoint.isBefore(YearMonth.of(date.getYear(), date.getMonth())) ; i++) {
                Tuple<Tuple<YearMonth, YearMonth>, Double> current = intEntries[i]; // Current interest rate
                
                YearMonth stopYM = (current.getFirst().getSecond() == null || !current.getFirst().getSecond().isBefore(YearMonth.of(date.getYear(), date.getMonth().minus(1)))) ? YearMonth.of(date.getYear(), date.getMonth().minus(1)) : current.getFirst().getSecond();

                while (!checkpoint.isAfter(stopYM)) {
                    addTransaction(new Transaction(Transaction.INTEREST, Math.round(getBalance(c.toString())*current.getSecond()*100.00)/100.0, c.toString(), String.format("Interest %s %s", checkpoint, c)));
                    checkpoint = checkpoint.plusMonths(1);
                }
            }
            intCheckPoint = checkpoint;
        }

    }
}