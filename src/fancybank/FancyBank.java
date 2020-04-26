package fancybank;

import java.util.ArrayList;
import java.util.List;

import fancybank.account.CheckingAccount;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;

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
    private StdinWrapper sinwrap;

    private Character currentChar;

    public FancyBank() {

        sinwrap = new StdinWrapper("");

        //database path
        String DBPATH = findDb();
        loadCharacter(DBPATH + "character/");
        loadAccount(DBPATH + "account/");
    }

    public void initBank(){
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();

        this.managers = new ArrayList<Manager>();
        this.customers = new ArrayList<Customer>();

        this.currentChar = new Character();
    }

    public void loadCharacter(String path){

    }

    public void loadAccount(String path){

    }

    public void CreateOnlineAccount(){
        sinwrap.setMessage("Please enter your name:");
        sinwrap.setMessage("Please enter your name:");
        String userName = sinwrap.next();



    }

    public boolean logIn(){
        sinwrap.setMessage("Hi, "+this.currentChar.getName());
        sinwrap.setMessage("Please enter your Online Bank Account ID:");
        
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