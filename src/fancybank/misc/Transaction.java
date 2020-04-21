package fancybank.misc;

import java.time.LocalDateTime;
// import java.util.Date;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * An immutable transaction
 */

public class Transaction {
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    
    private String operation;
    private String assetName; // For securities trading
    private double amount;
    private double finalBalance;
    private String description;
    private LocalDateTime transactTime;

    /**
     * Comprehensive constructor for all types of transaction
     * 
     * @param operation operation
     * @param assetName name of affecting asset
     * @param amount amount
     * @param description description of this transaction
     */

    public Transaction(String operation, String assetName, double amount, String description) {
        this.operation = operation;
        this.assetName = assetName;
        this.amount = Math.round(amount*100.0)/100/0; // Round to 2 decimal places
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

    public Transaction(String operation, double amount, String description) {
        this(operation, "", amount, description);
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

    public Transaction(String operation, String assetName, double amount, String description, long timestamp) {
        this.operation = operation;
        this.assetName = assetName;
        this.amount = Math.round(amount*100.0)/100/0; // Round to 2 decimal places
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

    @Override
    public String toString() {
        return String.format("%s | %c | %.2f | %s", transactTime, operation.charAt(0), amount, description);
    }

    /**
     * Test whether this transtion comes after the specified transaction
     * 
     * @param t transaction to compare
     * @return test result
     */
    
    public boolean isAfter(Transaction t) {
        return transactTime.isAfter(LocalDateTime.ofEpochSecond(t.getTimestamp(), 0, ZoneOffset.of(ZoneId.SHORT_IDS.get("EST"))));
        // return transactTime.after(new Date(t.getTimestamp()));
    }

    /**
     * Test whether this transtion comes before the specified transaction
     * 
     * @param t transaction to compare
     * @return test result
     */
    
    public boolean isBefore(Transaction t) {
        return transactTime.isBefore(LocalDateTime.ofEpochSecond(t.getTimestamp(), 0, ZoneOffset.of(ZoneId.SHORT_IDS.get("EST"))));
        // return transactTime.before(new Date(t.getTimestamp()));
    }
}