package fancybank;

import java.util.ArrayList;
import java.util.List;

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

    private List<SavingAccount> savings;
    private List<CheckingAccount> checkings;
    private List<SecuritiesAccount> securites;

    private List<Manager> managers;
    private List<Customer> customers;

    private List<Tuple> OnlineAccounts;

    private Report report;
    private int days;
    private StdinWrapper sinwrap;

    private String currentName;
    private Character currentChar;
    private static final String ownerCode = 0000;

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
                // Name,mana,strength,agility,dexterity,starting money,starting experience
                String[] tokens = line.replace("\n", "").strip().split(",");

                // Expect 3 columns, otherwise skip
                if (tokens.length == 3) {
                    switch (type) {
                        case "manager":
                            Manager m = new Manager(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2]);
                            this.managers.add(m);
                            this.OnlineAccounts.add(new Tuple(tokens[1],tokens[2]));
                            break;
                        case "customer":
                            Customer c = new Customer(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2]);
                            this.customers.add(c);
                            this.OnlineAccounts.add(new Tuple(tokens[1],tokens[2]));
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
                    Character c = new Character(currentName,id,pwd);
                    this.currentChar = c;
                    this.OnlineAccounts.add(new Tuple(id,pwd));
                    if(type.equals("Customer"))
                    {
                        Customer cust = (Customer)c;
                        this.customers.add(cust);
                    }
                    else if(type.equals("Manager"))
                    {
                        Manager man = (Manager)m;
                        this.managers.add(man);
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
                Character c = new Character(currentName,logId,pwd);
                this.currentChar = c;
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