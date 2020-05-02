package fancybank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.util.ElementScanner14;

import fancybank.account.CheckingAccount;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;
import fancybank.util.Tuple;
import fancybank.character.*;
import fancybank.character.Character;
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

    private List<SavingAccount> savings;
    private List<CheckingAccount> checkings;
    private List<SecuritiesAccount> securites;

    private List<Manager> managers;
    private List<Customer> customers;

    private List<Tuple> OnlineAccounts;
    private HashMap<String,String> AccountToType;

    private Report report;
    private int days;
    private StdinWrapper sinwrap;

    private String currentName;
    private Character currentChar;
    private static final String ownerCode = "0000";

    //database path
    private String DBPATH = findDb();

    public FancyBank() {
        initBank();

        sinwrap = new StdinWrapper("");
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

        this.OnlineAccounts = new ArrayList<Tuple>();
        this.AccountToType = new HashMap<String,String>();
    }

    public void run(){
        //display welcome

        //get user's nickname
        sinwrap.setMessage("What's your name?");
        String name = sinwrap.next();
        this.currentName = name;

        //create account or login
        sinwrap.setMessage("create account or login? (create or login)");
        String answer = sinwrap.next();
        if(answer.equals("create"))
        {
            this.CreateOnlineAccount();
        }
        else if(answer.equals("login"))
        {
            this.logIn();
        }

        boolean finished = false;
        //get action from user and enforce.
        while(!finished)
        {
            // if(this.currentChar.instanceOf(Manager))
            // {
            //     sinwrap.setMessage("CREATE_BANK_ACCOUNT/VIEW_BALANCE/VIEW_TRANSACTION/LOAN/STOCK/quit");
            // }
            // else
            // {
            //     sinwrap.setMessage("REPORT/quit");
            // }
            // String response = sinwrap.next();
            // switch(response)
            // {
            //     case "CREATE_BANK_ACCOUNT":
            //         sinwrap.setMessage("Which account do you wish to create? (security,saving,checking)");
                    
            //         break;
                    
            //     case "VIEW_BALANCE":
            //     case "VIEW_TRANSACTION":
            //     case "LOAN":
            //     case "STOCK":
            //     case "REPORT":
            //     case "quit":
            //         System.out.println("Bye");
            //         finished = true;
            //         break;
            //     default:
            //         System.out.println("invalid input");
            // }
        }
    }

    public void loadCharacter(String path){
        File[] characterCsv = new File(path).listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().substring(pathname.getName().length() - 4, pathname.getName().length())
                        .equals(".csv");
            }
        });

        for (File f : characterCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";

            while ((line = br.readLine()) != null) {
                // Name,accountName,pwd
                String[] tokens = line.replace("\n", "").strip().split(",");

                // Expect 3 columns, otherwise skip
                if (tokens.length == 3) {
                    switch (type) {
                        case "MANAGER":
                            Manager m = new Manager(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2]);
                            this.managers.add(m);
                            this.OnlineAccounts.add(new Tuple<String, String>(tokens[1],tokens[2]));
                            this.AccountToType.put(tokens[1], "Manager");
                            break;
                        case "CUSTOMER":
                            Customer c = new Customer(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2]);
                            this.customers.add(c);
                            this.OnlineAccounts.add(new Tuple<String, String>(tokens[1],tokens[2]));
                            this.AccountToType.put(tokens[1], "Customer");
                            break;
                        default:
                            System.err.println("Encountered undefined type");
                    }
                } else {
                    System.out.printf("Len is %d\n", tokens.length);
                }
            }

            br.close();
        }

    }

    public void loadAccount(String path){
        File[] accountCsv = new File(path).listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().substring(pathname.getName().length() - 4, pathname.getName().length())
                        .equals(".csv");
            }
        });

        for (File f : accountCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";

            while ((line = br.readLine()) != null) {
                // online account ID, account type, USD,balance,EUR,balance, CNY, balance.
                String[] tokens = line.replace("\n", "").strip().split(",");

                // Expect 3 columns, otherwise skip
                if (tokens.length == 8 || tokens.length == 10) {
                    switch (type) {
                        case "CHECKINGACCOUNT":
                            CheckingAccount c = new CheckingAccount(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2],
                            tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                            this.checkings.add(c);
                            break;
                        case "SAVINGACCOUNT":
                            SavingAccount s = new SavingAccount(tokens[0].replace("-", " ").replace("_", " "), tokens[1], tokens[2], tokens[3],tokens[4],tokens[5],tokens[6],tokens[7],tokens[8],tokens[9]);
                            this.savings.add(s);
                            break;
                        case "SECURITIESACCOUNT":
                            SecuritiesAccount se = new SecuritiesAccount(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2],
                            tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                            this.securites.add(se);
                            break;
                        default:
                            System.err.println("Encountered undefined type");
                    }
                } else {
                    System.out.printf("Len is %d\n", tokens.length);
                }
            }

        br.close();
        }

    }

    public void updataData(String path,String[] record,String type){
        
        FileWriter writer;
        
        switch(type)
        {
            case "manager":
                writer = new FileWriter(path+"manager.csv",true);
            case "customer":
                writer = new FileWriter(path+"customer.csv",true);
            case "saving":
                writer = new FileWriter(path+"savingAccount.csv",true);
            case "checking":
                writer = new FileWriter(path+"checkingAccount.csv",true);
            case "securities":
                writer = new FileWriter(path+"securitiesAccount.csv",true);
            default:
                System.err.println("Encountered undefined type");
        }
        for(int i = 0;i<record.length;i++)
        {
            if(i != (record.length-1))
            {
                writer.append(record[i]);
                writer.append(",");
            }
            else
            {
                writer.append(record[i]);
            }
            
        }

        writer.close();
    }





    public void CreateOnlineAccount(){
        Boolean finished = false;
        Boolean success = false;
        sinwrap.setMessage("Hi "+this.currentName+" :");

        while(!finished && !success)
        {
            sinwrap.setMessage("What type of account do you wish to create? (Manager or Customer)");
            String type = sinwrap.next();
            if(type.equals("Customer") || type.equals("Manager"))
            {
                sinwrap.setMessage("Please create your Online Id:");
                String id = sinwrap.next();
                if(checkAccountNameValid(id))
                {
                    finished = success = true;
                    sinwrap.setMessage("ID Valid! Please create your password: ");
                    String pwd = sinwrap.next();
                    sinwrap.setMessage("Create Account Successfully!");
                    this.OnlineAccounts.add(new Tuple<String, String>(id,pwd));
                    if(type.equals("Customer"))
                    {
                        Customer cust = new Customer(name, accountName, pwd,"C");
                        this.currentChar = cust;
                        this.customers.add(cust);
                        this.AccountToType.put(accountName,"Customer");
                        String[] newrecord = new String[]{name,accountName,pwd};
                        this.updataData(this.DBPATH+"character/", newrecord, "customer");
                    }
                    else if(type.equals("Manager"))
                    {   
                        Manager man = new Manager(name, accountName, pwd,"M");
                        this.currentChar = man;
                        this.managers.add(man);
                        this.AccountToType.put(accountName,"Manager");
                        String[] newrecord = new String[]{name,accountName,pwd};
                        this.updataData(this.DBPATH+"character/", newrecord, "manager");
                    }
                }
                else
                {
                    sinwrap.setMessage("Same ID exists.");
                }
            }
            else
            {
                sinwrap.setMessage("Invalid Input.");
            }

        }
    }

    public boolean logIn(){

        Boolean finished = false;
        Boolean success = false;

        while(!finished && !success)
        {
            sinwrap.setMessage("Hi, "+this.currentChar.getName());
            sinwrap.setMessage("Please enter your Online Bank Account ID:");
            String logId = sinwrap.next();
            sinwrap.setMessage("Please enter your Password");
            String pwd = sinwrap.next();
            if(checkAccountValid(logId, pwd))
            {
                sinwrap.setMessage("Log in Successful!");
                String type = this.AccountToType.get(logId);
                if(type.equals("Manager"))
                {
                    Manager m = new Manager(this.currentName,logId,pwd);
                    this.currentChar = m;
                }
                else if(type.equals("Customer"))
                {
                    Customer c = new Customer(this.currentName, logId, pwd);
                    this.currentChar =c;
                }
                finished = success = true;
            }
            else
            {
                sinwrap.setMessage("Log in failed! Incorrect Account name or Password!");
            }
        }
        
    }

    public Boolean checkAccountValid(String Id, String pwd){
        for(Tuple<T,U> t:OnlineAccounts)
        {
            String onlineId = t.getFirst();
            String onlinepwd = t.getSecond();
            if(Id.equals(onlineId) && pwd.equals(onlinepwd))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean checkAccountNameValid(String id){
        for(Tuple t:this.OnlineAccounts)
        {
            if(id.equals(t.getFirst()))
            {
                return false;
            }
        }
        return true;
    }

    public void getReport(){
        
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