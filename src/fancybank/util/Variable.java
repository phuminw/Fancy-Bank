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

    public static List<SavingAccount> savings;
    public static List<CheckingAccount> checkings;
    public static List<SecuritiesAccount> securites;

    public static HashMap<String, Object> USERNAME_TO_CHAR;

    // public static HashMap<String,Object> USERNAME_TO_ACC;

    // public static void main(String[] args) {
    //     Variable var = new Variable("src/db/");
    // }

    public Variable(String path){
        this.DBPATH = path;
        initCharacter();
        initAccount();
        loading();
        //System.out.println(characterList);


        //for testing purpose
        Customer c = new Customer("jessy", "111", "0");
        updateCustomer(c);

        System.out.println(characterList);
        

    }

    public void initCharacter(){
        USERNAME_TO_CHAR = new HashMap<String, Object>();
        customerList = new ArrayList<Customer>();
        managerList = new ArrayList<Manager>();
        characterList = new ArrayList<Character>();
        
    }

    public void initAccount()  {
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();
        

    }

    public void loading() {
        try {
            loadAccount(DBPATH + "account/");
            loadCharacter(DBPATH + "character/");
            
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

    public void updateAccount(Account account)
    {


    }

    public void updateTransaction(Transaction t)
    {

    }

    public void loadCharacter(String path) throws IOException{
        File[] characterCsv = new File(path).listFiles();

        for (File f : characterCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = br.readLine();

            while (line != null) {
                // Name,accountName,pwd
                String[] tokens = line.replace("\n", "").strip().split(",");

                // Expect 3 columns, otherwise skip
                if (tokens.length == 3) {
                    switch (type) {
                    case "MANAGER":

                        Manager m = new Manager(tokens[0].replace("-", " ").replace("_", " "), tokens[1], tokens[2]);
                        //System.out.println("jia");
                        characterList.add(m);
                        managerList.add(m);
                        USERNAME_TO_CHAR.put(tokens[1], m);
                        break;
                    case "CUSTOMER":
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
                System.out.println(line);
            }

            br.close();
        }

    }

    public void updataData(String[] record,String type) throws IOException{
        System.out.println(type);
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
        else
        {
            System.err.println("Encountered undefined type");
        }
        writer.newLine();

        
        for(int i = 0;i<record.length;i++)
        {
            if(i != (record.length-1))
            {
                
                writer.write(record[i]);
                writer.write(",");
            }
            else
            {
                writer.write(record[i]);
            }
            
        }

        writer.close();
    }

    public void loadAccount(String path) throws IOException {
        File[] accountCsv = new File(path).listFiles();

        for (File f : accountCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";

            while ((line = br.readLine()) != null) {
                // username,account ID,USD,balance,EUR,balance, CNY, balance.
                String[] tokens = line.replace("\n", "").strip().split(",");

                
                if (tokens.length == 8 || tokens.length == 10) {
                    switch (type) {
                        // case "CHECKINGACCOUNT":
                        //     CheckingAccount c = new CheckingAccount(Integer.parseInt(tokens[1]),tokens[1], tokens[2],
                        //     tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                        //     checkings.add(c);
                        //     break;
                        // case "SAVINGACCOUNT":
                        //     SavingAccount s = new SavingAccount(tokens[0].replace("-", " ").replace("_", " "), tokens[1], tokens[2], tokens[3],tokens[4],tokens[5],tokens[6],tokens[7],tokens[8],tokens[9]);
                        //     savings.add(s);
                        //     break;
                        // case "SECURITIESACCOUNT":
                        //     SecuritiesAccount se = new SecuritiesAccount(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2],
                        //     tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                        //     securites.add(se);
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







}
