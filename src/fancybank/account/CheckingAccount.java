package fancybank.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import fancybank.FancyBank;
import fancybank.misc.Transaction;

/**
 * Checking account that can only deposit and withdraw money. Nothing more than that.
 */
public class CheckingAccount extends Account {
    /**
     * Create zero-balanced checking account
     */
    
    public CheckingAccount() {
        super();
    }
    
    /**
     * DEBUG constructor
     * 
     * @param balance
     * @param openedDate
     * @param closedDate
     */
    
    public CheckingAccount(double balance, LocalDate openedDate, LocalDate closedDate) {
        super(balance, openedDate, closedDate);
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
     * @param description optional description
     * @param time transaction time (debug). Ignore if not debug
     * @return success or fail
     */

    public boolean deductBalance(double amount, String currency, String description, LocalDateTime time) {
        if (getBalance("USD") < 0)
            return false; // Need to clear USD to be positive first

        if (FancyBank.DEBUG)
            return addTransaction(new Transaction(Transaction.WITHDRAW, null, amount, currency, description, time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        return addTransaction(new Transaction(Transaction.WITHDRAW, amount, currency, description));
    }
}