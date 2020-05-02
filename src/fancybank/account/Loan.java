package fancybank.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.ListIterator;

import fancybank.FancyBank;
import fancybank.misc.InterestRateDBCorruptException;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

/**
 * Loan account (USD only for now). Balance is negative. Zero balance means totally paid off.
 */

public class Loan extends SavingAccount {
    String currency;
    // private ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>> intRateHistory; // ((start, end), intRate)
    // private HashMap<YearMonth, Double> intEarned; // K: year month V: interest earned
    // private YearMonth intCheckPoint; // Most recent interest rate calculation

    /**
     * Loan account constructor. Balance should be negative.
     * 
     * @param currency loan currency
     * @param balance initial balance (should be negative)
     * @param interest loan interest
     */

    public Loan(String currency, double balance, double interest) {
        super(interest, Integer.MAX_VALUE);
        this.currency = "USD";
        // this.intRateHistory = new ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>>();
        // this.intEarned = new HashMap<YearMonth, Double>();
        // this.intCheckPoint = null;
        balance = Math.round(balance*100.00)/100.00;
        addTransaction(new Transaction(Transaction.DEPOSIT, balance, "USD", "Initial balance"));
    }

    /**
     * DEBUG constructor
     * 
     * @param currency
     * @param balance
     * @param interest
     * @param openedDate
     * @param closedDate
     */

    public Loan(String currency, double balance, double interest, LocalDate openedDate, LocalDate closedDate) {
        super(balance, interest, Integer.MAX_VALUE, openedDate, closedDate);
        this.currency = "USD";
        // this.intRateHistory = new ArrayList<Tuple<Tuple<YearMonth, YearMonth>, Double>>();
        // this.intEarned = new HashMap<YearMonth, Double>();
        // this.intCheckPoint = null;
    }

    /**
     * Attempt to add balance to loan account. Check for restrictions and take
     * action. Reject request if interest calculation is not up-to-date
     * 
     * @param amount      amount to add
     * @param currency    currency
     * @param description optional description
     * @param time        xact time (ignore if not debug)
     * @return success or fail
     * @see SavingAccount#calculateInterest(LocalDate)
     */
    @Override

    public boolean addBalance(double amount, String currency, String description, LocalDateTime time) {
        if (intCheckPoint == null || intCheckPoint.isBefore(YearMonth.of(time.getYear(), time.getMonth())))
            calculateInterest(time.toLocalDate()); // Need to update interest first.

        if (FancyBank.DEBUG)
            return addTransaction(new Transaction(Transaction.DEPOSIT, null, amount, currency, description,
                    time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        return addTransaction(new Transaction(Transaction.DEPOSIT, amount, currency, description));
    }

    /**
     * Pay off loan. Balance can become negative, but we are EARNING.
     * 
     * @param currency loan currency
     * @param amount amount to pay
     * @param time transaction time (ignore if not debug)
     * @return success or not
     * @see SavingAccount#calculateInterest(LocalDate)
     */

    public boolean payOff(String currency, double amount, LocalDateTime time) {
        if (getBalance(currency) >= 0)
            return false; // Possibly, can close this account

        amount = Math.round(amount*100.00)/100.00;
        
        return addBalance(amount, currency, "Pay off", time);
    }

    /**
     * Calculate loan interest
     * 
     * @param ym today (or simulated date). Ignore if not debug.
     */
    @Override

    public void calculateInterest(LocalDate date) {
        // Interest rate calculation on both debug and non-debug mode
        Currency c = Currency.getInstance(currency);
        if (intRateHistory.size() == 0)
            throw new InterestRateDBCorruptException("No interest rate entry");
        if (intRateHistory.get(intRateHistory.size()-1).getFirst().getSecond() != null)
            throw new InterestRateDBCorruptException("Most recent interest rate entry was invalidated and no alive entry");

        if (!FancyBank.DEBUG)
            date = LocalDate.now(); // Ignore param and set to today

        YearMonth checkpoint = (intCheckPoint == null) ? intRateHistory.get(0).getFirst().getFirst().minusMonths(1) : intCheckPoint;
        Tuple<Tuple<YearMonth, YearMonth>, Double> current = null;
        ListIterator<Tuple<Tuple<YearMonth, YearMonth>, Double>> li = intRateHistory.listIterator();

        // Find first int entry corresponding to checkpoint
        while (li.hasNext() 
        && !(current = li.next()).getFirst().getFirst().isAfter(checkpoint) 
        && (current.getFirst().getSecond() == null || current.getFirst().getSecond().isAfter(checkpoint))
        ) {}

        while (checkpoint.plusMonths(1).isBefore(YearMonth.of(date.getYear(), date.getMonth()))) {
            YearMonth stopYM = (current.getFirst().getSecond() == null) ? YearMonth.of(date.getYear(), date.getMonth()) : (current.getFirst().getSecond().isBefore(YearMonth.of(date.getYear(), date.getMonth()))) ? current.getFirst().getSecond() : YearMonth.of(date.getYear(), date.getMonth()); // Exclusive upper bound

            while (checkpoint.plusMonths(1).isBefore(stopYM)) {
                checkpoint = checkpoint.plusMonths(1);
                addTransaction(new Transaction(Transaction.INTEREST, null, Math.round(getBalance(c.toString())*current.getSecond()*100.00)/100.0, c.toString(), String.format("Interest %s %s", checkpoint, c), LocalDateTime.of(checkpoint.getYear(), checkpoint.getMonth(), 28, 23, 59, 59).toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
                intEarned.put(checkpoint, Math.round(getBalance(c.toString())*current.getSecond()*100.00)/100.0);
            }

            if (li.hasNext())
                current = li.next();
        }

        intCheckPoint = YearMonth.of(date.getYear(), date.getMonth().minus(1));
    }

    // @Override
    public Double closeLoanAccount() {
        if (FancyBank.DEBUG)
            calculateInterest(getClosedDate());
        else
            calculateInterest(LocalDate.now());

        if (FancyBank.DEBUG)
            addTransaction(new Transaction(Transaction.FEE, null, FancyBank.CLOSEFEE, "USD", String.format("CLOSED FEE %d", FancyBank.CLOSEFEE), getClosedDate().atTime(23, 59, 59).toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        else {
            addTransaction(new Transaction(Transaction.FEE, FancyBank.CLOSEFEE, "USD", String.format("CLOSED FEE %d", FancyBank.CLOSEFEE)));
            setClosedDate(LocalDate.now());
        }

        return getBalance(currency);
    }
}