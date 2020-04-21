package fancybank.account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;

import fancybank.misc.Transaction;

/**
 * Base template for all account types
 */

public abstract class Account {
    private LocalDate openedDate;
    private LocalDate closedDate;
    private double balance;
    private ArrayList<Transaction> transactions;

    /**
     * Constructor for Account
     * 
     * @param balance initial balance
     */

    public Account() {
        this.openedDate = LocalDate.now();
        this.closedDate = null;
        this.balance = 0;
        transactions = new ArrayList<Transaction>();
    }

    /**
     * DEBUG constructor
     * 
     * @param balance initial balance
     * @param openedDate
     * @param closedDate
     */

    public Account(double balance, LocalDate openedDate, LocalDate closedDate) {
        this.balance = balance;
        this.openedDate = openedDate;
        this.closedDate = closedDate;
        transactions = new ArrayList<Transaction>();
    }

    /**
     * @return current balance
     */

    public double getBalance() {
        return balance;
    }

    /**
     * @return date this account was opened
     */

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    /**
     * @return date this account was closed, if closed
     */

    public LocalDate getClosedDate() {
        return closedDate;
    }

    /**
     * Get a single transaction
     * 
     * @param i index
     * @return the transactions
     */

    public Transaction getTransaction(int i) {
        return transactions.get(i);
    }

    /**
     * Get transactions after the specified timestamp
     * 
     * @param i timestamp (epoch)
     * @return matching transactions
     */
    
    public ArrayList<Transaction> getTransactionsAfter(long timestamp) {
        ListIterator<Transaction> li = transactions.listIterator();
        ArrayList<Transaction> result = new ArrayList<Transaction>();

        Transaction t;
        while (li.hasNext()) {
            if ((t = li.next()).getTimestamp() >= timestamp) {
                result.add(t);
            }
        }

        return result;
    }

    /**
     * Get transactions after the specified timestamp
     * 
     * @param i timestamp (epoch)
     * @return matching transactions
     */
    
    public ArrayList<Transaction> getTransactionsBefore(long timestamp) {
        ListIterator<Transaction> li = transactions.listIterator();
        ArrayList<Transaction> result = new ArrayList<Transaction>();

        Transaction t;
        while (li.hasNext()) {
            if ((t = li.next()).getTimestamp() <= timestamp) {
                result.add(t);
            }
        }

        return result;
    }

    /**
     * @param balance the balance to set
     */

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Append transaction to this account. Accept only new or more recent transaction
     * than the current most recent transaction in this account
     * 
     * @param t transaction to add
     * @return success or not
     */
    
    public boolean addTransaction(Transaction t) {
        if (transactions.size() != 0 && transactions.get(transactions.size()-1).isAfter(t))
            return false; // Attempted to add out-of-order transaction (edit history)
        
        /* Commit/Apply transaction */
        switch (t.getOperation()) {
            case Transaction.DEPOSIT:
                if (t.getAmount() >= 0) {
                    balance += t.getAmount();
                    t.setFinalBalance(balance);
                    transactions.add(t);
                    return true;
                }
                return false;
            case Transaction.WITHDRAW:
                if (t.getAmount() >= 0) {
                    balance -= t.getAmount();
                    t.setFinalBalance(balance);
                    transactions.add(t);
                    return true;
                }
                return false;
            default: 
                t.setFinalBalance(balance); // Buy/sell asset, no affect on balance for this xact.
                transactions.add(t);
                return true;
        }
    }
}