package test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Currency;
import java.util.Set;
import java.util.Map.Entry;

import fancybank.account.CheckingAccount;
import fancybank.account.Loan;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.misc.Transaction;
import fancybank.util.Tuple;

public class AccountTester {

    private static void checkingAccTest() {
        /* Checking account test */
        System.out.println("\nChecking account\n");
        CheckingAccount c1 = new CheckingAccount(500, LocalDate.of(2019, 10, 14), LocalDate.of(2020, 03, 27));
        c1.deductBalance(50, "usd", "test deduct #1", LocalDateTime.of(2019, 11, 2, 12, 34, 56));
        c1.addBalance(900, "cny", "test dep cny #2", LocalDateTime.of(2019, 11, 3, 12, 34, 56));
        c1.addBalance(900, "THB", "test dep THB #3", LocalDateTime.of(2019, 11, 4, 12, 34, 56)); // Invalid currency
        c1.addCurrency("THB");
        c1.addBalance(900, "THB", "test dep thb #4", LocalDateTime.of(2019, 11, 5, 12, 34, 56)); // Again, should work
        c1.deductBalance(869, "THB", "test dep thb #4", LocalDateTime.of(2019, 12, 5, 12, 34, 56));
        c1.removeCurrency("cny"); // Remove non-zero currency
        c1.removeCurrency("Thb"); // Fail since need minimum 3 currencies
        c1.addBalance(334, "usd", "test add USD", LocalDateTime.of(2020, 02, 1, 11, 33, 55));

        Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> ret = c1.closeAccount();

        System.out.println("\nBalance Remaining");
        for (Entry<Currency, Double> en : ret.getFirst()) {
            System.out.printf("- %s %.2f\n", en.getKey(), en.getValue());
        }

        String c = "usd";

        System.out.printf("\nTotal xacts: %d\n", c1.getTransactions(c).size());
        for (Transaction t : c1.getTransactions(c)) {
            System.out.println(t);
        }
    }

    private static void savingAccTest() {
        /* Saving account test */
        System.out.println("\nSaving account\n");
        SavingAccount s1 = new SavingAccount(500, 0.015, 5, LocalDate.of(2019, 10, 14), LocalDate.of(2020, 03, 27));
        s1.setIntRate(0.015, YearMonth.now(), YearMonth.of(2019, 10));
        s1.setIntRate(0.012, YearMonth.of(2019, 12), YearMonth.of(2020, 3)); // Invalidate previous and replace previous end date
        // s1.deductBalance(600, "usd", "Deduct 1 to negative", LocalDateTime.of(2019, 10, 15, 10, 11, 43)); // Cause interest not to occur
        s1.addBalance(234, "cny", "Deposit cny #1", LocalDateTime.of(2020, 1, 22, 14, 33, 14));

        s1.calculateInterest(s1.getClosedDate());

        Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> ret = s1.closeAccount();

        System.out.println("\nBalance Remaining");
        for (Entry<Currency, Double> en : ret.getFirst()) {
            System.out.printf("- %s %.2f\n", en.getKey(), en.getValue());
        }

        String c = "cny";
        System.out.printf("\nTotal xacts: %d\n", s1.getTransactions(c).size());
        for (Transaction t : s1.getTransactions(c)) {
            System.out.println(t);
        }
    }

    private static void securitiesAccTest() throws NumberFormatException, IOException {
        /* Securities account test */
        System.out.println("\nSecurities account\n");
        SecuritiesAccount sec1 = new SecuritiesAccount(800, LocalDate.of(2019, 10, 14), LocalDate.of(2020, 03, 27));

        sec1.buyAsset("aapl", 2, LocalDateTime.of(2020, 1, 20, 15, 37, 8));
        sec1.buyAsset("msft", 20, LocalDateTime.of(2020, 2, 7, 9, 59, 37)); // Not enough money
        sec1.sellAsset("msft", 45, LocalDateTime.of(2020, 2, 8, 9, 59, 37)); // Sell unowned stocks
        sec1.sellAsset("aapl", 3, LocalDateTime.of(2020, 2, 12, 13, 22, 53)); // Sell more than owned
        sec1.sellAsset("aapl", 1.3, LocalDateTime.of(2020, 2, 13, 11, 22, 44)); // Sell part of owned
        sec1.sellAsset("aapl", 0.7, LocalDateTime.of(2020, 2, 13, 11, 22, 46));

        Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>> ret = sec1.closeAccount();

        System.out.println("\nBalance Remaining");
        for (Entry<Currency, Double> en: ret.getFirst()) {
            System.out.printf("- %s %.2f\n", en.getKey(), en.getValue());
        }

        String c = "usd";

        System.out.printf("\nTotal xacts: %d\n", sec1.getTransactions(c).size());
        for (Transaction t : sec1.getTransactions(c)) {
            System.out.println(t);
        }
    }

    private static void loanTester() {
        /* Loan account test */
        System.out.println("\nLoan account\n");
        Loan l1 = new Loan("USD", -800, 0.075, LocalDate.of(2019, 10, 14), LocalDate.of(2020, 03, 27));

        l1.setIntRate(0.075, YearMonth.of(1999, 9), null);

        Double ret = l1.closeLoanAccount();

        System.out.printf("\nBalance Remaining: %.2f$", ret);

        String c = "usd";

        System.out.printf("\nTotal xacts: %d\n", l1.getTransactions(c).size());
        for (Transaction t : l1.getTransactions(c)) {
            System.out.println(t);
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        checkingAccTest();
        securitiesAccTest();
        savingAccTest();
        loanTester();
    }
}