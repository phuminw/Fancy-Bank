package fancybank.market;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import fancybank.util.Tuple;

/**
 * Stocks market for investors to buy or sell stocks
 */

public class StocksMarket {
    private HashMap<String, Tuple<Double, Double>> stocks; // Name: (price, shares)
    private static StocksMarket instance;

    private StocksMarket() throws NumberFormatException, IOException {
        stocks = new HashMap<String, Tuple<Double, Double>>();
        StocksDataReader.readCsvDebug("src/fancybank/db/stocks/stocks.csv", stocks);
    }

    public static StocksMarket getInstance() throws NumberFormatException, IOException {
        if (instance == null)
            instance = new StocksMarket();

        return instance;
    }

    /**
     * Buy stock from the market
     * 
     * @param symbol stock to buy
     * @param quantity shares to buy
     * @return success or not
     */

    public boolean buy(String symbol, double quantity) {
        // This stock does not exist
        if (!stocks.containsKey(symbol.toUpperCase()))
            return false;
        // No share available
        if (stocks.get(symbol.toUpperCase()).getSecond() == 0)
            return false;

        stocks.put(symbol.toUpperCase(), new Tuple<Double,Double>(stocks.get(symbol.toUpperCase()).getFirst(), stocks.get(symbol.toUpperCase()).getSecond()-quantity));
        return true;
    }

    /**
     * Sell stock from the market
     * 
     * @param symbol stock to sell
     * @param quantity shares to sell
     * @return success or not
     */

    public boolean sell(String symbol, double quantity) {
        // This stock does not exist
        if (!stocks.containsKey(symbol.toUpperCase()))
            return false;
        // // No share available
        // if (stocks.get(symbol.toUpperCase()).getSecond() == 0)
        //     return false;

        stocks.put(symbol.toUpperCase(), new Tuple<Double,Double>(stocks.get(symbol.toUpperCase()).getFirst(), stocks.get(symbol.toUpperCase()).getSecond()+quantity));
        return true;
    }

    public double priceLookup(String symbol) {
        symbol = symbol.toUpperCase();
        return (stocks.containsKey(symbol)) ? stocks.get(symbol).getFirst() : 0;
    }

    public Set<Entry<String, Tuple<Double, Double>>> getStocksInfo() {
        return stocks.entrySet();
    }
}