package fancybank.market;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import fancybank.util.Tuple;

/**
 * Collections of readers for setting up stocks data from files or API
 */

public class StocksDataReader {
    /**
     * Read from testing CSV file for debug
     * 
     * @param filename
     * @param stocks
     * @throws NumberFormatException
     * @throws IOException
     */

    public static void readCsvDebug(String filename, HashMap<String, Tuple<Double, Double>> stocks)
            throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        // Name, price, shares
        String line;

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            stocks.put(tokens[0].toUpperCase(), new Tuple<Double,Double>(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
        }

        br.close();
    }
}