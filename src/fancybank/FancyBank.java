package fancybank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;



import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ErrorManager;

// import javax.lang.model.util.ElementScanner14;

import fancybank.account.CheckingAccount;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.account.Account;
import fancybank.account.AccountType;
import fancybank.io.StdinWrapper;
import fancybank.util.Tuple;
import fancybank.character.*;
import fancybank.character.Character;
import fancybank.util.Variable;
import fancybank.util.ErrorResponse;
/**
 * Main program for the bank
 */
public class FancyBank {
    /* DEBUG flag: Enable more options and settings for debugging */
    public static final boolean DEBUG = true;

    /* Account open and close fee (USD) */
    public static final int OPENFEE = 2;
    public static final int CLOSEFEE = 5;
    public static final double LOANINTEREST = 0.1;
    public static final double SAVINGINTEREST = 0.1;
    public static final double SAVINGWITHDRAWLIMIT = 500;
    public static final int SAVINGWITHDRAWCOUNTLIMIT = 5;
    // private List<SavingAccount> savings;
    // private List<CheckingAccount> checkings;
    // private List<SecuritiesAccount> securites;

    // private List<Manager> managers;
    // private List<Customer> customers;

    

    // private List<Tuple> OnlineAccounts;
    // private HashMap<String,String> AccountToType;

    //private Report report;
    private int days;
    private StdinWrapper sinwrap;

    //private String currentName;
    private Character currentChar;
    private static final String ownerCode = "0000";

    public static Variable VARIABLE;

    //database path
    private String DBPATH;
    public static void main(String[] args) {
        FancyBank f = new FancyBank();
        System.out.println(f.checkAccountNameValid("maoyizhou"));

       
    }

    public FancyBank(){
        try {
            DBPATH = findDb();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        VARIABLE = new Variable(DBPATH);
        initBank();

        

        
    }

    public void initBank(){

        
        // this.savings = new ArrayList<SavingAccount>();
        // this.checkings = new ArrayList<CheckingAccount>();
        // this.securites = new ArrayList<SecuritiesAccount>();

        // this.managers = new ArrayList<Manager>();
        // this.customers = new ArrayList<Customer>();
        this.currentChar = new Character() {
        };
    }

    public void logIn(String userName){

        Character c = (Character)VARIABLE.USERNAME_TO_CHAR.get(userName);
        if(c instanceof Customer)
        {
            this.currentChar = (Customer) c;
        }
        else if(c instanceof Manager)
        {
            this.currentChar = (Manager) c;
        }
    }

    public void logOut(){
        System.exit(0);
    }

    public void createOnlineAccount(String username){
        
        Customer c = new Customer("", username,"");
        VARIABLE.updateCustomer(c);
        this.currentChar = c;
        
    }

    public Boolean checkAccountValid(String Id, String pwd){
        if(VARIABLE.USERNAME_TO_CHAR.containsKey(Id))
        {
            Object obj = VARIABLE.USERNAME_TO_CHAR.get(Id);
            if(obj instanceof Manager)
            {
                Manager m = (Manager) obj;
                if(m.getPwd().equals(pwd))
                {
                    return true;
                }

            }
            else if(obj instanceof Customer)
            {
                Customer c = (Customer) obj;
                if(c.getPwd().equals(pwd))
                {
                    return true;
                }

            }

            return false;

        }
        else
        {
            return false;
        }
    }

    public Boolean checkAccountNameValid(String id){
        if(VARIABLE.USERNAME_TO_CHAR.containsKey(id))
        {
            return false;
        }
        return true;
    }

    // public Boolean checkAccountNameValid(String id){
    //     try {
    //         return VARIABLE.checkAccountNameValid(DBPATH+"character/", id);
    //     } catch (IOException e) {
    //         //TODO: handle exception
    //     }
    //     return false;
        
    // }

    public Tuple getReport(){
        List<Account> accounts = new ArrayList<Account>();
        accounts.addAll(VARIABLE.savings);
        accounts.addAll(VARIABLE.checkings);
        accounts.addAll(VARIABLE.securites);

        return new Tuple(VARIABLE.customerList,accounts);
    }

    public void chargeInterest(){

        for(SavingAccount sav:VARIABLE.savings)
        {
            sav.calculateInterest(LocalDate.now());
        }
    }

    public List getAccounts()
    {
        List<Account> accounts = new ArrayList<Account>();
        accounts.addAll(VARIABLE.savings);
        accounts.addAll(VARIABLE.checkings);
        accounts.addAll(VARIABLE.securites);
        return accounts;
    }

    public List getCustomers(){
        return VARIABLE.customerList;
    }

    public void deposit(String currency,double money,String accountId){
        Customer cus = (Customer)this.currentChar;
        cus.deposit(currency, money, accountId);
    }

    public boolean withdraw(String currency,double money, String accountId,ErrorResponse error){
        Customer cus = (Customer)this.currentChar;
        return cus.withdraw(currency, money, accountId, error);
    }

    public boolean transfer(String from, String to, String currency, double money, ErrorResponse error){
        Customer cus  = (Customer) this.currentChar;
        return cus.transfer(from, to, currency, money, error);
    }

    public void viewTransaction()
    {
        Customer cus  = (Customer) this.currentChar;
        cus.viewTransaction();
    }

    public void viewSecuritiesBalance(){
        Customer cus  = (Customer) this.currentChar;
        cus.viewSecuritiesBalance();
    }

    public void viewSavingBalance(){
        Customer cus  = (Customer) this.currentChar;
        cus.viewSavingBalance();
    }

    public void viewCheckingBalance(){
        Customer cus  = (Customer) this.currentChar;
        cus.viewCheckingBalance();
    }

    /**
     * Find db folder path that contains game data
     * 
     * @return Path to db folder
     * @throws IOException
     */

    public static String findDb() throws IOException {
        ArrayDeque<File> queue = new ArrayDeque<File>();
        queue.add(new File("src/")); // Search only under src folder
        while (!queue.isEmpty()) {
            File nextFile = queue.remove();
            if (nextFile.isDirectory()) {
                if (nextFile.getName().equals("db"))
                    return nextFile.getCanonicalPath() + "/";

                File[] subdirs = nextFile.listFiles();
                for (File f : subdirs)
                    queue.add(f);
            }
        }
        return null;
    }


}