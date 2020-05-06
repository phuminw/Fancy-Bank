package fancybank.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.print.DocFlavor.STRING;
import fancybank.util.ReverseLineInputStream;
import fancybank.account.*;
import fancybank.character.*;
import fancybank.character.Character;
// import fancybank.db.*;
import fancybank.misc.Transaction;

public class Variable {

    String DBPATH;

    public static List<Character> characterList;
    public static List<Customer> customerList;
    public static List<Manager> managerList;
    
    public static List<Transaction> transactions;
    public static List<Loan> Loans;
    public static List<SavingAccount> savings;
    public static List<CheckingAccount> checkings;
    public static List<SecuritiesAccount> securites;

    public static HashMap<String, Object> USERNAME_TO_CHAR;
    //public static HashMap<Tuple<String,String>,Object> ID_TO_ACCOUNT;
    public static HashMap<String,Object> ID_TO_ACCOUNT;

    public static HashMap<String, String> DISPLAY_TRANSACTION;

    // public static HashMap<String,Object> USERNAME_TO_ACC;

    // public static void main(String[] args) {
    //     Variable var = new Variable("src/db/");
    // }

    public Variable(String path){
        this.DBPATH = path;

        //System.out.println(characterList);


        // //for testing purpose
        // Customer c = new Customer("jessy", "111", "0");
        // updateCustomer(c);

        // System.out.println(characterList);
        

    }

    public void init(){
        initCharacter();
        initAccount();
        loading();
    }

    public void initCharacter(){
        USERNAME_TO_CHAR = new HashMap<String, Object>();
        customerList = new ArrayList<Customer>();
        managerList = new ArrayList<Manager>();
        characterList = new ArrayList<Character>();
        
    }

    public void initAccount()  {
        //ID_TO_ACCOUNT = new HashMap<Tuple<String,String>,Object>();
        ID_TO_ACCOUNT = new HashMap<String,Object>();
        DISPLAY_TRANSACTION = new HashMap<String,String>();
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();
        Loans = new ArrayList<Loan>();
        

    }

    public void loading() {
        try {
            //System.out.println("hi");
            loadCharacter(DBPATH + "character/");
            loadAccount(DBPATH + "account/");
            loadTransaction(DBPATH + "transaction/");
            
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        

    }

    public void updateCustomer(Customer c) {
        customerList.add(c);
        characterList.add(c);
        USERNAME_TO_CHAR.put(c.getAccountName(), c);
        String[] lst = new String[]{c.getName(),c.getAccountName(),c.getPwd()};
        try {
            updataData(lst, "customer");
            
        }  catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }

    }

    public void updateAccount(String username,Account account)
    {
        //ID_TO_ACCOUNT.put(new Tuple(username,Integer.toString(account.getId())), account);
        if(ID_TO_ACCOUNT.containsKey(Integer.toString(account.getId())))
        {
            account = (Account)ID_TO_ACCOUNT.get(Integer.toString(account.getId()));
            String[] record;
            if(account instanceof SavingAccount)
            {
                SavingAccount s = (SavingAccount) account;
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY")),Double.toString(s.getInterestRate()),Integer.toString(s.getWithdrawCountLimit())};
                try {
                    //updataData(record, "saving",true);
                    updataData(record, "saving");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }
            else if(account instanceof CheckingAccount)
            {
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY"))};
                try {
                    //updataData(record, "checking",true);
                    updataData(record, "checking");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }
            else if(account instanceof SecuritiesAccount)
            {
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY"))};
                try {
                    //updataData(record, "securities",true);
                    updataData(record, "securities");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }

            }
            else if(account instanceof Loan)
            {
                Loan l = (Loan) account;

                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),Double.toString(l.getInterestRate())};
                try {
                    updataData(record, "loan");
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }


        }
        else
        {
            ID_TO_ACCOUNT.put(Integer.toString(account.getId()), account);
            String[] record;
            if(account instanceof SavingAccount)
            {
                SavingAccount s = (SavingAccount) account;
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY")),Double.toString(s.getInterestRate()),Integer.toString(s.getWithdrawCountLimit())};
                try {
                    updataData(record, "saving");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }
            else if(account instanceof CheckingAccount)
            {
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY"))};
                try {
                    updataData(record, "checking");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }
            else if(account instanceof SecuritiesAccount)
            {
                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),"EUR",Double.toString(account.getBalance("EUR")),"CNY",Double.toString(account.getBalance("CNY"))};
                try {
                    updataData(record, "securities");
                    
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }

            }
            else if(account instanceof Loan)
            {
                Loan l = (Loan) account;

                record = new String[]{username,Integer.toString(account.getId()),"USD",Double.toString(account.getBalance("USD")),Double.toString(l.getInterestRate())};
                try {
                    updataData(record, "loan");
                }  catch (IOException e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }

        }
        
        


    }

    public void updateTransaction(int accountId,Transaction t)
    {
        //System.out.println("whyyyy");
        String[] record = new String[]{Integer.toString(accountId),t.getOperation(),t.getAssetName(), Double.toString(t.getAmount()),t.getCurrency(),t.getDescription()};
        try {
            updataData(record, "transaction");
            
        }  catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    
    }


    public void loadCharacter(String path) throws IOException{
        File[] characterCsv = new File(path).listFiles();
        //System.out.println(characterCsv);

        for (File f : characterCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();
            //System.out.println(type);

            String line = br.readLine();
            //System.out.println("out");
           // System.out.println(line);

            while (line != null && !(line.equals(""))){
                // Name,accountName,pwd
                String[] tokens = line.split(",");
                //System.out.println("HERE");
                //System.out.println(tokens[3]);

                // Expect 3 columns, otherwise skip
                if (tokens.length == 3) {
                    //System.out.println("HERE");
                    switch (type) {
                    case "MANAGER":
                        Manager m = new Manager(tokens[0].replace("-", " ").replace("_", " "), tokens[1], tokens[2]);
                        //System.out.println("jia");
                        characterList.add(m);
                        managerList.add(m);
                        USERNAME_TO_CHAR.put(tokens[1], m);
                        break;
                    case "CUSTOMER":
                        //System.out.println("HERE");
                        Customer c = new Customer(tokens[0].replace("-", " ").replace("_", " "), tokens[1], tokens[2]);
                        characterList.add(c);
                        customerList.add(c);
                        
                        USERNAME_TO_CHAR.put(tokens[1], c);
                        break;
                    default:
                        System.err.println("Encountered undefined type");
                    }
                } else {
                    System.out.printf("Len is %d\n", tokens.length);
                }
                line = br.readLine();
                //System.out.println(line);
            }

            br.close();
        }

    }

    public void updataData(String[] record,String type) throws IOException{
        //System.out.println(type);
        String path = DBPATH;
        
        BufferedWriter writer = null;
        if(type.equals("manager"))
        {
            writer = new BufferedWriter(new FileWriter(path+"character/customer.csv",true));
        }
        else if(type.equals("customer"))
        {
            writer = new BufferedWriter(new FileWriter(path+"character/customer.csv",true));
        }
        else if(type.equals("saving"))
        {
            writer = new BufferedWriter(new FileWriter(path+"account/savingAccount.csv",true));
        }
        else if(type.equals("checking"))
        {
            writer = new BufferedWriter(new FileWriter(path+"account/checkingAccount.csv",true));
        }
        else if(type.equals("securities"))
        {
            writer = new BufferedWriter(new FileWriter(path+"account/securitiesAccount.csv",true));
        }
        else if(type.equals("transaction"))
        {
            writer = new BufferedWriter(new FileWriter(path+"transaction/transaction.csv",true));
        }
        else if(type.equals("loan"))
        {
            writer = new BufferedWriter(new FileWriter(path+"account/loan.csv",true));
        }
        else
        {
            System.err.println("Encountered undefined type");
        }
        writer.newLine();

        
        for(int i = 0;i<record.length;i++)
        {
            if(i != (record.length-1))
            {
                if(record[i] == null)
                {
                    writer.write("");
                }
                else
                {
                    writer.write(record[i]);
                }
                
                writer.write(",");
            }
            else
            {
                if(record[i] == null)
                {
                    writer.write("");
                }
                else
                {
                    writer.write(record[i]);
                }
                
            }
            
        }

        writer.close();
    }

    // public void updataData(String[] record,String type,boolean b) throws IOException{
    //     //System.out.println(type);
    //     String path = DBPATH;
        
    //     CSVWriter writer = null;
    //     CSVReader reader = null;
    //     if(type.equals("saving"))
    //     {
    //         writer = new CSVWriter(new FileWriter(path+"account/savingAccount.csv",true));
    //         reader = new CSVReader(new FileReader(path+"account/savingAccount.csv"));
    //     }
    //     else if(type.equals("checking"))
    //     {
    //         writer = new CSVWriter(new FileWriter(path+"account/checkingAccount.csv",true));
    //         reader = new CSVReader(new FileReader(path+"account/checkingAccount.csv"));
    //     }
    //     else if(type.equals("securities"))
    //     {
    //         writer = new CSVWriter(new FileWriter(path+"account/securitiesAccount.csv",true));
    //         reader = new CSVReader(new FileReader(path+"account/securitiesAccount.csv"));
    //     }
    //     else if(type.equals("loan"))
    //     {
    //         writer = new CSVWriter(new FileWriter(path+"account/loan.csv",true));
    //         reader = new CSVReader(new FileReader(path+"account/loan.csv"));
    //     }
    //     else
    //     {
    //         System.err.println("Encountered undefined type");
    //     }

    //     if(b)
    //     {
    //         List<String[]> allElements = reader.readAll();
    //         for(int i = 0;i<allElements.size();i++)
    //         {
    //             String[] current = allElements.get(i);
    //             if(current[1].equals(record[1]))
    //             {
    //                 allElements.remove(i);
    //             }          
    //         }
    //         writer.writeAll(allElements);
    //         writer.close();

    //     }
    //     else
    //     {
    //         writer.newLine();

        
    //         for(int i = 0;i<record.length;i++)
    //         {
    //             if(i != (record.length-1))
    //             {
    //                 if(record[i] == null)
    //                 {
    //                     writer.write("");
    //                 }
    //                 else
    //                 {
    //                     writer.write(record[i]);
    //                 }
                    
    //                 writer.write(",");
    //             }
    //             else
    //             {
    //                 if(record[i] == null)
    //                 {
    //                     writer.write("");
    //                 }
    //                 else
    //                 {
    //                     writer.write(record[i]);
    //                 }
                    
    //             }
                
    //         }

    //     }
        

    //     writer.close();
    // }

    public void removeDuplicates(String path)throws IOException{
        //File[] accountCsv = new File(path).listFiles();
        
        BufferedReader br = new BufferedReader(new FileReader(path));
        //String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();
        String title = br.readLine();
        String line = br.readLine();
        line = br.readLine();
        ArrayList<String> lst = new ArrayList<String>();

        while (line != null && !(line.equals(""))){
            String[] tokens = line.replace("\n", "").strip().split(",");
            if(DISPLAY_TRANSACTION.containsKey(tokens[1]))
            {
                DISPLAY_TRANSACTION.replace(tokens[1],line);
            }
            else
            {
                DISPLAY_TRANSACTION.put(tokens[1],line);
            }
        }

        //System.out.println("DDD"+DISPLAY_TRANSACTION);
        // BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        // writer.write(title);
    }

    public void loadAccount(String path) throws IOException {
        File[] accountCsv = new File(path).listFiles();

        for (File f : accountCsv) {
            BufferedReader br = new BufferedReader (new FileReader (f));
            //BufferedReader br = new BufferedReader (new InputStreamReader (new ReverseLineInputStream(f)));
            
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";
            
            
            line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            //System.out.println(line);
           

            while (line != null && !(line.equals(""))) {
                // username,account ID,USD,balance,EUR,balance, CNY, balance.
                String[] tokens = line.replace("\n", "").strip().split(",");
                

                
                
                if(!DISPLAY_TRANSACTION.containsKey(tokens[1]))
                {
                        DISPLAY_TRANSACTION.put(tokens[1],"");
                        switch (type) {
                            case "CHECKINGACCOUNT":
                                CheckingAccount c = new CheckingAccount();
                                //set up the account
                                c.setID(Integer.parseInt(tokens[1]));
                                
                                c.setBalance(Double.parseDouble(tokens[3]), tokens[2]);
                                c.setBalance(Double.parseDouble(tokens[3]), tokens[4]);
                                c.setBalance(Double.parseDouble(tokens[7]), tokens[6]);
    
                                //给用户加进去
                                Customer customer = (Customer)USERNAME_TO_CHAR.get(tokens[0]);
                                //System.out.println(USERNAME_TO_CHAR);
                                //System.out.println(tokens[0]);
                                //System.out.println(USERNAME_TO_CHAR.get(tokens[0]));
                                customer.getChecking().add(c);
                                
                                checkings.add(c);
                                //ID_TO_ACCOUNT.put(new Tuple(tokens[0],tokens[1]), c);
                                ID_TO_ACCOUNT.put(tokens[1], c);
                                
                                break;
                            case "SAVINGACCOUNT":
                            //username,AccountID,USD,USDbalance,EUR,EURbalance,CNY,CNYbalance,interestRate,withdrawCountLimit
                                SavingAccount s = new SavingAccount(Double.parseDouble(tokens[8]),Integer.parseInt(tokens[9]));
    
                                //set up the account
                                s.setID(Integer.parseInt(tokens[1]));
                                s.setBalance(Double.parseDouble(tokens[3]), tokens[2]);
                                s.setBalance(Double.parseDouble(tokens[3]), tokens[4]);
                                s.setBalance(Double.parseDouble(tokens[7]), tokens[6]);
    
                                Customer sac_c = (Customer)USERNAME_TO_CHAR.get(tokens[0]);
                                //System.out.println(tokens[0]);
                                //System.out.println("zhe li");
    
                                sac_c.getChecking().add(s);
                                //System.out.println("加成"+sac_c.getChecking());
                                savings.add(s);
                                ID_TO_ACCOUNT.put(tokens[1], s);
                                //ID_TO_ACCOUNT.put(new Tuple(tokens[0],tokens[1]), s);
                                break;
                            case "LOAN":
                            //username,ccountId,currency,balance,interest
                                    Loan l = new Loan(tokens[2],Double.parseDouble(tokens[3]),Double.parseDouble(tokens[4]));
        
                                    //set up the account
                                    l.setID(Integer.parseInt(tokens[1]));
                                    // s.setBalance(Double.parseDouble(tokens[3]), tokens[2]);
                                    // s.setBalance(Double.parseDouble(tokens[3]), tokens[4]);
                                    // s.setBalance(Double.parseDouble(tokens[7]), tokens[6]);
        
                                    Customer loan_c = (Customer)USERNAME_TO_CHAR.get(tokens[0]);
        
                                    loan_c.getLoans().add(l);
                                    Loans.add(l);
                                    ID_TO_ACCOUNT.put(tokens[1], l);
                                    //ID_TO_ACCOUNT.put(new Tuple(tokens[0],tokens[1]), s);
                                    break;
                            case "SECURITIESACCOUNT":
                                SecuritiesAccount se = new SecuritiesAccount();
                                //set up the account
                                se.setID(Integer.parseInt(tokens[1]));
    
                                se.setBalance(Double.parseDouble(tokens[3]), tokens[2]);
                                se.setBalance(Double.parseDouble(tokens[3]), tokens[4]);
                                se.setBalance(Double.parseDouble(tokens[7]), tokens[6]);
    
                                Customer sec_c= (Customer)USERNAME_TO_CHAR.get(tokens[0]);
                                sec_c.getChecking().add(se);
                                securites.add(se);
                                ID_TO_ACCOUNT.put(tokens[1], se);
                                //ID_TO_ACCOUNT.put(new Tuple(tokens[0],tokens[1]), se);
                                break;
                            default:
                                System.err.println("Encountered undefined type");
                        }

                    }
                
                line = br.readLine();
            }

        br.close();
        }

    }

    public void loadTransaction(String path) throws IOException {
        File[] accountCsv = new File(path).listFiles();

        for (File f : accountCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";
            line = br.readLine();
            
            //System.out.println("HI");
            //System.out.println(line);

            while (line != null && !(line.equals(""))) {
                //accountID,operation,assetname,amount,currency,description
                String[] tokens = line.replace("\n", "").strip().split(",");
                //System.out.println(tokens.length);
                Account a = (Account)ID_TO_ACCOUNT.get(tokens[0]);
                //System.out.println(tokens[0]);
                Transaction t = null;
                if(tokens[2].equals(""))
                {
                    t = new Transaction(tokens[1], Double.parseDouble(tokens[3]), tokens[4], tokens[5]);
                }
                else
                {
                    
                    t = new Transaction(tokens[1], tokens[2], Double.parseDouble(tokens[3]), tokens[4], tokens[5]);
                    
                }
                a.addTransaction(t);
                this.transactions.add(t);
                line = br.readLine();
                //System.out.println(line);
            }

        br.close();
        }

    }

    public boolean checkAccountNameValid(String path,String target) throws IOException{
        File[] characterCsv = new File(path).listFiles();

        for (File f : characterCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = br.readLine();

            while (line != null) {
                // Name,accountName,pwd
                String[] tokens = line.replace("\n", "").strip().split(",");
                if(tokens[1].equals(target))
                {
                    return true;
                }
                
                line = br.readLine();
                //System.out.println(line);
            }

            br.close();
        }
        return false;

    }







}
