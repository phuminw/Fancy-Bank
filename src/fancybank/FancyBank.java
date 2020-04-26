package fancybank;

import java.util.List;

import fancybank.account.CheckingAccount;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;

/**
 * Main program for the bank
 */
public class FancyBank {
    /* DEBUG flag: Enable more options and settings for debugging */
    public static final boolean DEBUG = true;

    private List<SavingAccount> savings;
    private List<CheckingAccount> checkings;
    private List<SecuritiesAccount> securites;

    private List<Manager> managers;
    private List<Customer> customers;

    private Report report;
    private int days;

    public FancyBank() {


        
    }
}