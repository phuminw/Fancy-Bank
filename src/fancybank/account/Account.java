package fancybank.account;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;

import fancybank.FancyBank;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

/**
 * Base template for all account types
 */

public abstract class Account {
    public static final Currency USD = Currency.getInstance("USD");
    public static final Currency EUR = Currency.getInstance("EUR");
    public static final Currency CNY = Currency.getInstance("CNY");

    private LocalDate openedDate;
    private LocalDate closedDate;
    private HashMap<Currency, Double> balance;
    private HashMap<Currency, ArrayList<Transaction>> xactByCurrency;
    // private ArrayList<Transaction> transactions;

    /**
     * Constructor for Account
     * 
     * @param balance initial balance
     */

    public Account() {
        openedDate = LocalDate.now();
        closedDate = null;
        balance = new HashMap<Currency, Double>();
        balance.put(USD, 0.0);
        balance.put(EUR, 0.0);
        balance.put(CNY, 0.0);
        xactByCurrency = new HashMap<Currency, ArrayList<Transaction>>();
        xactByCurrency.put(USD, new ArrayList<Transaction>());
        xactByCurrency.put(EUR, new ArrayList<Transaction>());
        xactByCurrency.put(CNY, new ArrayList<Transaction>());
        // transactions = new ArrayList<Transaction>();
        addTransaction(new Transaction(Transaction.FEE, FancyBank.OPENFEE, "USD", String.format("OPEN FEE %d", FancyBank.OPENFEE)));
    }

    /**
     * DEBUG constructor
     * 
     * @param balance initial balance
     * @param openedDate
     * @param closedDate
     */

    public Account(double balance, LocalDate openedDate, LocalDate closedDate) {
        this.balance = new HashMap<Currency, Double>();
        this.balance.put(USD, balance);
        this.balance.put(EUR, 0.0);
        this.balance.put(CNY, 0.0);
        this.openedDate = openedDate;
        this.closedDate = closedDate;
        xactByCurrency = new HashMap<Currency, ArrayList<Transaction>>();
        xactByCurrency.put(USD, new ArrayList<Transaction>());
        xactByCurrency.put(EUR, new ArrayList<Transaction>());
        xactByCurrency.put(CNY, new ArrayList<Transaction>());
        // transactions = new ArrayList<Transaction>();
        addTransaction(new Transaction(Transaction.FEE, null, FancyBank.OPENFEE, "USD", String.format("OPEN FEE %d", FancyBank.OPENFEE), openedDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
    }

    /**
     * @return current balance (zero if invalid currency or actually zero)
     */

    public double getBalance(String currency) {
        return (balance.containsKey(Currency.getInstance(currency.toUpperCase()))) ? balance.get(Currency.getInstance(currency.toUpperCase())) : 0;
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
     * @param closedDate the closedDate to set
     */

    protected void setClosedDate(LocalDate closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * Get a single transaction
     * 
     * @param i index
     * @return the transactions
     */

    public Transaction getTransaction(String currency, int i) {
        try {
            List<Transaction> xacts = xactByCurrency.get(Currency.getInstance(currency.toUpperCase()));
            return xacts.get(i);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get transactions after the specified timestamp
     * 
     * @param i timestamp (epoch)
     * @return matching transactions
     */
    
    public List<Transaction> getTransactionsAfter(String currency, long timestamp) {
        ArrayList<Transaction> transactions;

        try {
            transactions = xactByCurrency.get(Currency.getInstance(currency.toUpperCase()));
        } catch (Exception e) {
            return new ArrayList<Transaction>();
        }

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
    
    public List<Transaction> getTransactionsBefore(String currency, long timestamp) {
        ArrayList<Transaction> transactions;
        
        try {
            transactions = xactByCurrency.get(Currency.getInstance(currency.toUpperCase()));
        } catch (Exception e) {
            return new ArrayList<Transaction>();
        }

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

    public List<Transaction> getTransactions(String currency) {
        try {
            return xactByCurrency.get(Currency.getInstance(currency.toUpperCase()));
        } catch (Exception e) {
            return new ArrayList<Transaction>();
        }
    }

    /**
     * @param balance the balance to set
     */

    public boolean setBalance(double balance, String currency) {
        // Invalid currency
        if (!this.balance.containsKey(Currency.getInstance(currency.toUpperCase())))
            return false;

        this.balance.put(Currency.getInstance(currency.toUpperCase()), balance);
        return true;
    }

    /**
     * Add new currency
     * 
     * @param currency new currency
     * @return adding result
     */

    public boolean addCurrency(String currency) {
        try {
            balance.put(Currency.getInstance(currency.toUpperCase()), 0.0);
            xactByCurrency.put(Currency.getInstance(currency.toUpperCase()), new ArrayList<Transaction>());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Remove currency from account. Caller must backup to-be-removed currency if needed.
     * 
     * @param currency currency to remove
     * @return balance and all transactions
     */

    public Tuple<Double, List<Transaction>> removeCurrency(String currency) {
        if (balance.keySet().size() > 3) {
            try {
                Double t = balance.remove(Currency.getInstance(currency.toUpperCase()));
                List<Transaction> u = xactByCurrency.remove(Currency.getInstance(currency.toUpperCase()));
                return new Tuple<Double,List<Transaction>>(t, u);
            } catch (IllegalArgumentException e) {
            }
        }
        return new Tuple<Double,List<Transaction>>(-1.0, new ArrayList<>());
    }

    /**
     * All available currencies for this account
     * 
     * @return available currency
     */

    public Set<Currency> getCurrencies() {
        return balance.keySet();
    }

    /**
     * Append transaction to this account. Accept only new or more recent transaction
     * than the current most recent transaction in this account
     * 
     * @param t transaction to add
     * @return success or not
     */
    
    public boolean addTransaction(Transaction t) {
        String currency = t.getCurrency();
        ArrayList<Transaction> transactions;
        
        try {
            transactions = xactByCurrency.get(Currency.getInstance(currency.toUpperCase()));
        } catch (Exception e) {
            return false;
        }

        if (transactions == null || (transactions.size() != 0 && transactions.get(transactions.size()-1).isAfter(t)))
            return false; // Attempted to add out-of-order transaction (edit history)
        if (t.getTime().toLocalDate().isAfter(closedDate))
            return false; // xact after account was closed
        if (!t.getCurrency().equals("N/A") && !balance.containsKey(Currency.getInstance(t.getCurrency().toUpperCase())))
            return false; // Invalid currency
        
        /* Commit/Apply transaction */
        switch (t.getOperation()) {
            case Transaction.INTEREST:
            case Transaction.DEPOSIT:
                // if (t.getAmount() >= 0) {
                    balance.put(Currency.getInstance(t.getCurrency().toUpperCase()), balance.get(Currency.getInstance(t.getCurrency().toUpperCase()))+t.getAmount());
                    t.setFinalBalance(balance.get(Currency.getInstance(t.getCurrency().toUpperCase())));
                    transactions.add(t);
                    return true;
                // }
                // return false;
            case Transaction.FEE:
            case Transaction.WITHDRAW:
                // if (t.getAmount() >= 0) {
                    balance.put(Currency.getInstance(t.getCurrency().toUpperCase()), balance.get(Currency.getInstance(t.getCurrency().toUpperCase()))-t.getAmount());
                    t.setFinalBalance(balance.get(Currency.getInstance(t.getCurrency().toUpperCase())));
                    transactions.add(t);
                    return true;
                // }
                // return false;
            default: 
                t.setFinalBalance(balance.get(Currency.getInstance("USD"))); // Buy/sell asset, no affect on balance USD for this xact.
                transactions.add(t);
                return true;
        }
    }

    /**
     * Close account and return account information
     * 
     * @return Tuple(balance, stocks)
     */

    public Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> closeAccount() {
        // Return balance and stock information
        if (FancyBank.DEBUG)
            addTransaction(new Transaction(Transaction.FEE, null, FancyBank.CLOSEFEE, "USD", String.format("CLOSED FEE %d", FancyBank.CLOSEFEE), closedDate.atTime(23, 59, 59).toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
        else {
            addTransaction(new Transaction(Transaction.FEE, FancyBank.CLOSEFEE, "USD", String.format("CLOSED FEE %d", FancyBank.CLOSEFEE)));
            closedDate = LocalDate.now();
        }
        return new Tuple<Set<Entry<Currency,Double>>, Set<Entry<String,Double>>>(balance.entrySet(), null);
    }
}