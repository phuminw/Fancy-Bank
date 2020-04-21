package fancybank.account;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.PriorityQueue;

import fancybank.FancyBank;
import fancybank.misc.InterestRateDBSorter;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

public class SavingAccount extends Account {
    // private double interest; // APY e.g. 0.1 means 10%
    private int withdrawCount;
    private int withdrawCountLimit;
    // private HashMap<YearMonth, Double> intRate; // K: year V: (K: month V: interest rate)
    private PriorityQueue<Tuple<Tuple<YearMonth, YearMonth>, Double>> intRateHistory; // ((start, end), intRate)
    private HashMap<YearMonth, Double> intEarned; // K: year month V: interest earned
    private YearMonth intCheckPoint; // Most recent interest rate calculation

    public SavingAccount(double interest, int withdrawCountLimit) {
        super();
        // this.interest = interest;
        this.withdrawCount = 0;
        this.withdrawCountLimit = withdrawCount;
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

    public int getWithdrawCount() {
        return withdrawCount;
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
            if (FancyBank.DEBUG)
                intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(recentInt.getFirst().getFirst(), start), recentInt.getSecond()));
            else
                intRateHistory.add(new Tuple<Tuple<YearMonth,YearMonth>,Double>(new Tuple<YearMonth,YearMonth>(recentInt.getFirst().getFirst(), YearMonth.now()), recentInt.getSecond()));
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
     * @param description optional description
     * @return success or fail
     */

    public boolean addBalance(double amount, String description) {
        return addTransaction(new Transaction(Transaction.DEPOSIT, amount, description));
    }

    /**
     * Attempt to deduct balance from saving account. Check for restrictions and take action.
     * 
     * @param amount amount to deduct
     * @param description optional description
     * @return success or fail
     */

    public boolean deductBalance(double amount, String description) {
        if (withdrawCount < withdrawCountLimit) {
            return false;
        }

        withdrawCount++;

        return addTransaction(new Transaction(Transaction.WITHDRAW, amount, description));
    }

    /**
     * Calculate interest on this account
     * 
     * @param ym year-month to emulate (ignore if not in debug mode)
     */
    @SuppressWarnings({"unchecked"})

    public void calculateInterest(YearMonth ym) {
        // TODO: Implement interest rate calculation on both debug and non-debug mode
        if (FancyBank.DEBUG) {
            Tuple<Tuple<YearMonth, YearMonth>, Double>[] intEntries = (Tuple<Tuple<YearMonth, YearMonth>, Double>[]) intRateHistory.toArray();

            for (Tuple<Tuple<YearMonth, YearMonth>, Double> intEntry: intEntries) {
                if (intEntry.getFirst().getSecond() != null) {

                }
            }
        }


    }
}