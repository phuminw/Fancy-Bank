package fancybank.account;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import fancybank.FancyBank;
import fancybank.market.StocksMarket;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

/**
 * Account for investment (USD)
 */

public class SecuritiesAccount extends CheckingAccount {
    private HashMap<String, Double> portfolio; // K: Stock name, V: shares owned
    private StocksMarket market;

    /**
     * Create zero-balanced serucities account
     * 
     * @throws IOException
     * @throws NumberFormatException
     */

    public SecuritiesAccount() throws NumberFormatException, IOException {
        super();
        portfolio = new HashMap<String, Double>();
        market = StocksMarket.getInstance();
    }

    /**
     * DEBUG constructor
     * 
     * @param balance
     * @param openedDate
     * @param closedDate
     * @throws IOException
     * @throws NumberFormatException
     */

    public SecuritiesAccount(double balance, LocalDate openedDate, LocalDate closedDate) throws NumberFormatException, IOException {
        super(balance, openedDate, closedDate);
        portfolio = new HashMap<String, Double>();
        market = StocksMarket.getInstance();
    }

    /**
     * Buy asset/stocks and deduct from USD currency balance
     * 
     * @param assetName asset/stocks symbol
     * @param shares number of shares
     * @param time custom xact time (ignore if not debug)
     * @return success or not
     */

    public boolean buyAsset(String assetName, double shares, LocalDateTime time) {
        if (shares <= 0)
            return false;

        double pricePerShare;
        assetName = assetName.toUpperCase();
        shares = Math.round(shares * 100.0) / 100.0;
        if (!FancyBank.DEBUG)
            time = LocalDateTime.now();

        // Invalid stocks or not enough money (USD)
        if ((pricePerShare = market.priceLookup(assetName)) == 0 || getBalance("USD") < shares * pricePerShare)
            return false;

        if (market.buy(assetName, shares)) {
            deductBalance(shares * pricePerShare, "USD", String.format("Purchased %s %.2f shares", assetName, shares), time);

            addTransaction(new Transaction(Transaction.BUY, assetName, shares, "N/A", String.format("Purchased %s %.2f shares", assetName, shares), time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
            // Update portfolio
            if (portfolio.containsKey(assetName))
                portfolio.put(assetName, portfolio.get(assetName) + shares);
            else
                portfolio.put(assetName, shares);
            return true;
        }
        return false;
    }

    /**
     * Sell asset/stocks and deposit into USD currency balance
     * 
     * @param assetName asset/stocks symbol
     * @param shares number of shares
     * @param time custom xact time (ignore if not debug)
     * @return success or not
     */

    public boolean sellAsset(String assetName, double shares, LocalDateTime time) {
        if (shares <= 0)
            return false;

        assetName = assetName.toUpperCase();
        shares = Math.round(shares * 100.0) / 100.0;
        if (!FancyBank.DEBUG)
            time = LocalDateTime.now();

        // Not-owned stocks or not enough owned
        if (!portfolio.containsKey(assetName) || portfolio.get(assetName) < shares)
            return false;

        if (market.sell(assetName, shares)) {
            addTransaction(new Transaction(Transaction.SELL, assetName, shares, "N/A", String.format("Sold %s %.2f shares", assetName, shares), time.toEpochSecond(ZoneOffset.of(ZoneId.SHORT_IDS.get("EST")))));
            addBalance(shares * market.priceLookup(assetName), "USD", String.format("Sold %s %.2f shares", assetName, shares), time);
            if (portfolio.get(assetName) - shares == 0)
                portfolio.remove(assetName);
            else
                portfolio.put(assetName, portfolio.get(assetName) - shares);
            return true;
        }
        return false;
    }

    /**
     * Calculate net worth of assets/stocks owned
     * 
     * @return
     */

    public double getNetWorthAssets() {
        double networth = 0;
        for (String assetName: portfolio.keySet()) {
            networth += market.priceLookup(assetName.toUpperCase())*portfolio.get(assetName);
        }
        return networth;
    }

    /**
     * Return information of owned stocks
     * 
     * @return set of KV entries of each stock owned
     */

    public Set<Entry<String, Double>> getOwnedStocks() {
        return portfolio.entrySet();
    }

    @Override
    public Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> closeAccount() {
        return new Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>>(super.closeAccount().getFirst(), getOwnedStocks());
    }
}