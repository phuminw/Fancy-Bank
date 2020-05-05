package fancybank.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fancybank.account.*;
import fancybank.character.*;
import fancybank.character.Character;
import fancybank.db.*;


public class Variable{

    String DBPATH;
    
    public static List<Character> characterList;
    public static List<Customer> customerList;
    public static List<Manager> managerList;

    public static List<SavingAccount> savings;
    public static List<CheckingAccount> checkings;
    public static List<SecuritiesAccount> securites;

    
    
    public static HashMap<String, Object> USERNAME_TO_CHAR;



    //public static HashMap<String,Object> USERNAME_TO_ACC;   

    public Variable(String path){
        this.DBPATH = path;
        
        
    }

    public void initCharacter(){
        USERNAME_TO_CHAR = new HashMap<String,Object>();
        customerList = new ArrayList<Customer>();
        managerList = new ArrayList<Manager>();
        characterList = new ArrayList<Character>();
        loadCharacter(DBPATH + "character/");



    }

    public void initAccount(){
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();
        loadAccount(DBPATH + "account/");

    }

    public void updateCustomer(Customer c){
        customerList.add(c);
        characterList.add(c);
        USERNAME_TO_CHAR.put(c.getAccountName(), c);

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
                            characterList.add(m);
                            managerList.add(m);
                            USERNAME_TO_CHAR.put(tokens[1],m);
                            
                            //this.OnlineAccounts.add(new Tuple<String, String>(tokens[1],tokens[2]));
                            //this.AccountToType.put(tokens[1], "Manager");
                            break;
                        case "CUSTOMER":
                            Customer c = new Customer(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2]);
                            characterList.add(c);
                            customerList.add(c);
                            USERNAME_TO_CHAR.put(tokens[1],c);
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
        File[] accountCsv = new File(path).listFiles();

        for (File f : accountCsv) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String type = f.getName().substring(0, f.getName().indexOf('.')).toUpperCase();

            String line = "";

            while ((line = br.readLine()) != null) {
                // online account ID, account type, USD,balance,EUR,balance, CNY, balance.
                String[] tokens = line.replace("\n", "").strip().split(",");

                
                if (tokens.length == 8 || tokens.length == 10) {
                    switch (type) {
                        case "CHECKINGACCOUNT":
                        //     CheckingAccount c = new CheckingAccount(tokens[0].replace("-", " ").replace("_", " "),tokens[1], tokens[2],
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
