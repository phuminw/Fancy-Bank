package fancybank.misc;

import java.time.YearMonth;
import java.util.Comparator;

import fancybank.util.Tuple;

/**
 * Compare entry of interest rate history in reverse order (most recent first)
 */

public class InterestRateDBSorter implements Comparator<Tuple<Tuple<YearMonth, YearMonth>, Double>> {
    @Override
    public int compare(Tuple<Tuple<YearMonth, YearMonth>, Double> entry1, Tuple<Tuple<YearMonth, YearMonth>, Double> entry2) {
        return entry2.getFirst().getFirst().compareTo(entry1.getFirst().getFirst());
    }
}