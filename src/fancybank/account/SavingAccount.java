package fancybank.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;

import fancybank.FancyBank;
import fancybank.misc.InterestRateDBCorruptException;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

/**
 * Saving Account that pays interest
 */

public class SavingAccount extends Account {
    // private double interest; // APY e.g. 0.1 means 10%
    private Tuple<YearMonth, Integer> withdrawCount; // Associate with Month
    private int withdrawCountLimit;
    // private HashMap<YearMonth, Double> intRate; // K: year V: (K: month V:
    // interest rate)
    protected ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>> intRateHistory; // ((start, end), intRate)
    protected HashMap<YearMonth, Double> intEarned; // K: year month V: interest earned
    protected YearMonth intCheckPoint; // Most recent interest rate calculation

    public SavingAccount(double interest, int withdrawCountLimit) {
        super();
        this.withdrawCount = new Tuple<YearMonth, Integer>(YearMonth.now(), 0);
        this.withdrawCountLimit = withdrawCountLimit;
        this.intRateHistory = new ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>>();
        this.intEarned = new HashMap<YearMonth, Double>();
        this.intCheckPoint = null;

        // Record interest rate if not in debug mode. Otherwise, ignore interest param
        if (!FancyBank.DEBUG)
            intRateHistory.add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                    new Tuple<YearMonth, YearMonth>(YearMonth.now(), null), interest)); // Record current interest rate
    }

    /**
     * DEBUG constructor
     * 
     * @param interest
     * @param withdrawCountLimit
     * @param openedDate
     * @param closedDate
     */

    public SavingAccount(double balance, double interest, int withdrawCountLimit, LocalDate openedDate,
            LocalDate closedDate) {
        super(balance, openedDate, closedDate);

        if (FancyBank.DEBUG)
            this.withdrawCount = new Tuple<YearMonth, Integer>(
                    YearMonth.of(openedDate.getYear(), openedDate.getMonth()), 0);
        else
            this.withdrawCount = new Tuple<YearMonth, Integer>(YearMonth.now(), 0);

        this.withdrawCountLimit = withdrawCountLimit;
        this.intRateHistory = new ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>>();
        this.intEarned = new HashMap<YearMonth, Double>();
        this.intCheckPoint = null;

        // Record interest rate if not in debug mode. Otherwise, ignore interest param
        if (!FancyBank.DEBUG)
            intRateHistory.add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                    new Tuple<YearMonth, YearMonth>(YearMonth.now(), null), interest)); // Record current interest rate
    }

    /**
     * @return this account interest rate. zero if no recent entry of actually zero.
     */

    public Double getInterestRate() {
        return (intRateHistory.size() == 0) ? 0 : intRateHistory.get(intRateHistory.size() - 1).getSecond();
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
     * @return interest earned on the specified year and month. Zero if invalid
     *         year/month or actually zero.
     */

    public Double getIntEarned(YearMonth ym) {
        Double intEarnedYearMonth = intEarned.get(ym);
        return (intEarnedYearMonth == null) ? 0 : intEarnedYearMonth;
    }

    /**
     * Total interest earned since account opened
     * 
     * @return total interest
     */

    public Double getIntEarnedTotal() {
        Double sum = 0.0;
        for (Double v : intEarned.values()) {
            sum += v;
        }
        return sum;
    }

    /**
     * Update interest rate of this account
     * 
     * @param interest new interest rate
     * @param start    start date (ignore if not in debug mode)
     * @param end      end date (ignore if not in debug mode)
     * @return success or not
     */

    public boolean setIntRate(double interest, YearMonth start, YearMonth end) {
        // Check trying to go into history and invalidate old entry, if exists
        if (intRateHistory.size() != 0) {
            Tuple<Tuple<YearMonth, YearMonth>, Double> recentInt = intRateHistory.get(intRateHistory.size() - 1);

            // Check whether trying to overwrite history or not
            if (!recentInt.getFirst().getFirst().isBefore(start))
                return false;

            if (FancyBank.DEBUG) {
                intRateHistory.set(intRateHistory.size() - 1,
                        new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                                new Tuple<YearMonth, YearMonth>(recentInt.getFirst().getFirst(), start),
                                recentInt.getSecond()));
                intRateHistory.add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                        new Tuple<YearMonth, YearMonth>(start, null), interest));
            } else {
                intRateHistory.set(intRateHistory.size() - 1,
                        new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                                new Tuple<YearMonth, YearMonth>(recentInt.getFirst().getFirst(), YearMonth.now()),
                                recentInt.getSecond()));
                intRateHistory.add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                        new Tuple<YearMonth, YearMonth>(YearMonth.now(), null), interest));
            }

            return true;
        }

        if (FancyBank.DEBUG) {
            // First entry must start from opened date and valid
            return intRateHistory
                    .add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                            new Tuple<YearMonth, YearMonth>(
                                    YearMonth.of(getOpenedDate().getYear(), getOpenedDate().getMonth()), end),
                            interest));
        }

        return intRateHistory.add(new Tuple<Tuple<YearMonth, YearMonth>, Double>(
                new Tuple<YearMonth, YearMonth>(YearMonth.now(), null), interest));
    }

    /**
     * Attempt to add balance to saving account. Check for restrictions and take
     * action. Reject request if interest calculation is not up-to-date
     * 
     * @param amount      amount to add
     * @param currency    currency
     * @param description optional description
     * @param time        xact time (ignore if not debug)
     * @return success or fail
     * @see SavingAccount#calculateInterest(LocalDate)
     */

    public boolean addBalance(double amount, String currency, String description, LocalDateTime time) {
        if (intCheckPoint == null || intCheckPoint.isBefore(YearMonth.of(time.getYear(), time.getMonth())))
            calculateInterest(time.toLocalDate()); // Need to update interest first.
            

        if (FancyBank.DEBUG)
            return addTransaction(new Transaction(Transaction.DEPOSIT, null, amount, currency, description,
                    time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        return addTransaction(new Transaction(Transaction.DEPOSIT, amount, currency, description));
    }

    /**
     * Attempt to deduct balance from saving account. Check for restrictions and
     * take action. Reject request if interest calculation is not up-to-date
     * 
     * @param amount      amount to deduct
     * @param currency    currency
     * @param description optional description
     * @param time        transaction time (debug). Ignore if not debug
     * @return success or fail
     * @see SavingAccount#calculateInterest(LocalDate)
     */

    public boolean deductBalance(double amount, String currency, String description, LocalDateTime time) {
        if (intCheckPoint == null || intCheckPoint.isBefore(YearMonth.of(time.getYear(), time.getMonth())))
            calculateInterest(time.toLocalDate()); // Need to update interest first.

        YearMonth currentYM;

        if (FancyBank.DEBUG) {
            currentYM = YearMonth.of(time.getYear(), time.getMonth());
            if (currentYM.isBefore(withdrawCount.getFirst()))
                // Tried to go into history
                return false;
        } else
            currentYM = YearMonth.now();

        // New month, so reset withdrawal count
        if (currentYM.isAfter(withdrawCount.getFirst())) {
            withdrawCount = new Tuple<YearMonth, Integer>(currentYM, 0);
        }

        if (withdrawCount.getSecond() >= withdrawCountLimit) {
            return false;
        }

        if (FancyBank.DEBUG) {
            if (addTransaction(new Transaction(Transaction.WITHDRAW, null, amount, currency, description,
                    time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))))) {
                withdrawCount = new Tuple<YearMonth, Integer>(withdrawCount.getFirst(), withdrawCount.getSecond() + 1);
                return true;
            }

            return false;
        }

        if (addTransaction(new Transaction(Transaction.WITHDRAW, amount, currency, description))) {
            withdrawCount = new Tuple<YearMonth, Integer>(withdrawCount.getFirst(), withdrawCount.getSecond() + 1);
            return true;
        }

        return false;
    }

    /**
     * Calculate interest on this account up to specified date on monthly level
     * 
     * @param ym today (or simulated date). Ignore if not debug.
     */

    public void calculateInterest(LocalDate date) {
        Set<Currency> currencies = getCurrencies();
        for (Currency c : currencies) {
            // Interest rate calculation on both debug and non-debug mode

            if (intRateHistory.size() == 0)
                throw new InterestRateDBCorruptException("No interest rate entry");
            if (intRateHistory.get(intRateHistory.size() - 1).getFirst().getSecond() != null)
                throw new InterestRateDBCorruptException(
                        "Most recent interest rate entry was invalidated and no alive entry");

            if (!FancyBank.DEBUG)
                date = LocalDate.now(); // Ignore param and set to today

            if (getBalance(c.toString()) < 0)
                continue;

            YearMonth checkpoint = (intCheckPoint == null) ? intRateHistory.get(0).getFirst().getFirst().minusMonths(1)
                    : intCheckPoint;
            Tuple<Tuple<YearMonth, YearMonth>, Double> current = null;
            ListIterator<Tuple<Tuple<YearMonth, YearMonth>, Double>> li = intRateHistory.listIterator();

            // Find first int entry corresponding to checkpoint
            while (li.hasNext() && !(current = li.next()).getFirst().getFirst().isAfter(checkpoint)
                    && (current.getFirst().getSecond() == null || current.getFirst().getSecond().isAfter(checkpoint))) {
            }

            while (checkpoint.plusMonths(1).isBefore(YearMonth.of(date.getYear(), date.getMonth()))) {
                YearMonth stopYM = (current.getFirst().getSecond() == null)
                        ? YearMonth.of(date.getYear(), date.getMonth())
                        : (current.getFirst().getSecond().isBefore(YearMonth.of(date.getYear(), date.getMonth())))
                                ? current.getFirst().getSecond()
                                : YearMonth.of(date.getYear(), date.getMonth()); // Exclusive upper bound

                while (checkpoint.plusMonths(1).isBefore(stopYM)) {
                    checkpoint = checkpoint.plusMonths(1);
                    addTransaction(new Transaction(Transaction.INTEREST, null,
                            Math.round(getBalance(c.toString()) * current.getSecond() * 100.00) / 100.0, c.toString(),
                            String.format("Interest %s %s", checkpoint, c),
                            LocalDateTime.of(checkpoint.getYear(), checkpoint.getMonth(), 28, 23, 59, 59)
                                    .toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
                    intEarned.put(checkpoint,
                            Math.round(getBalance(c.toString()) * current.getSecond() * 100.00) / 100.0);
                }

                if (li.hasNext())
                    current = li.next();
            }
        }

        intCheckPoint = YearMonth.of(date.minusMonths(1).getYear(), date.minusMonths(1).getMonth());
    }

    @Override
    public Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> closeAccount() {
        if (FancyBank.DEBUG)
            calculateInterest(getClosedDate());
        else
            calculateInterest(LocalDate.now());

        return super.closeAccount();
    }
}