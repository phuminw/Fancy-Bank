package fancybank.misc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Currency;

/**
 * An immutable transaction
 */

public class Transaction {
    public static final String FEE = "FEE";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final String INTEREST = "INTEREST";
    public static final String BUY = "BUY"; 
    public static final String SELL = "SELL";
    
    
    private String operation;
    private String assetName; // For securities trading
    private double amount;
    private Currency currency;
    private double finalBalance;
    private String description;
    private LocalDateTime transactTime;

    /**
     * Comprehensive constructor for all types of transaction
     * 
     * @param operation operation
     * @param assetName name of affecting asset
     * @param amount amount
     * @param currency
     * @param description description of this transaction
     */

    public Transaction(String operation, String assetName, double amount, String currency, String description) {
        this.operation = operation;
        this.assetName = assetName;
        this.amount = Math.round(amount*100.0)/100.0; // Round to 2 decimal places
        try {
            this.currency = Currency.getInstance(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.currency = null;
        }
        this.description = description;
        this.transactTime = LocalDateTime.now();
    }

    /**
     * Basic constructor for normal transaction
     * 
     * @param operation operation
     * @param amount amount
     * @param description description of this transaction
     */

    public Transaction(String operation, double amount, String currency, String description) {
        this(operation, "", amount, currency, description);
    }

    /**
     * DEBUG constructor to allow custom timestamp
     * 
     * @param operation operation
     * @param assetName affecting asset name
     * @param amount amount
     * @param description transaction description
     * @param timestamp timestamp
     */

    public Transaction(String operation, String assetName, double amount, String currency, String description, long timestamp) {
        this.operation = operation;
        this.assetName = assetName;
        this.amount = Math.round(amount*100.0)/100.0; // Round to 2 decimal places
        try {
            this.currency = Currency.getInstance(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.currency = null;
        }
        this.description = description;
        this.transactTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")));
    }

    /**
     * @return the operation
     */

    public String getOperation() {
        return operation;
    }

    /**
     * @return the assetName
     */

    public String getAssetName() {
        return assetName;
    }

    /**
     * @return the amount
     */

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return (currency == null) ? "N/A" : currency.toString();
    }

    /**
     * @return the description
     */

    public String getDescription() {
        return description;
    }

    /**
     * @return the transaction time
     */

    public String getTransactTime() {
        return transactTime.toString();
    }

    /**
     * Get epoch time of this transaction
     * 
     * @return epoch
     */

    public long getTimestamp() {
        return transactTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public LocalDateTime getTime() {
        return transactTime;
    }

    /**
     * @return final balance after applying this transaction
     */

    public double getFinalBalance() {
        return finalBalance;
    }

    /**
     * @param finalBalance balance after applying this transaction
     */

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    /**
     * Test whether this transtion comes after the specified transaction
     * 
     * @param t transaction to compare
     * @return test result
     */
    
    public boolean isAfter(Transaction t) {
        return transactTime.isAfter(t.getTime());
        // return transactTime.after(new Date(t.getTimestamp()));
    }

    /**
     * Test whether this transtion comes before the specified transaction
     * 
     * @param t transaction to compare
     * @return test result
     */
    
    public boolean isBefore(Transaction t) {
        return transactTime.isBefore(t.getTime());
        // return transactTime.before(new Date(t.getTimestamp()));
    }

    @Override
    public String toString() {
        // private String operation;
        // private String assetName; // For securities trading
        // private double amount;
        // private Currency currency;
        // private double finalBalance;
        // private String description;
        // private LocalDateTime transactTime;
        return String.format("%s | OPS: %s Asset: %s amount: %.2f currency: %s finalB: %.2f | %s", transactTime, operation, assetName, amount, currency, finalBalance, description);
    }
}