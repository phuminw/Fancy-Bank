package fancybank.character;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;



import fancybank.account.Account;
import fancybank.account.CheckingAccount;
import fancybank.account.Loan;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;
import fancybank.misc.Transaction;
import fancybank.util.Variable;
import fancybank.FancyBank;
import fancybank.util.ErrorResponse;
import fancybank.util.Variable;
import fancybank.market.StocksMarket;
import fancybank.util.Tuple;


public class Customer extends Character {
    private ArrayList<SavingAccount> savings;
    private ArrayList<CheckingAccount> checkings;
    private ArrayList<SecuritiesAccount> securities;
    private ArrayList<Loan> loans;
    private ArrayList<Transaction> transactions;

    private StdinWrapper sinwrap;

    public Customer(String name, String accountName, String pwd) {
        super(name, accountName, pwd, "C");
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securities = new ArrayList<SecuritiesAccount>();
        this.loans = new ArrayList<Loan>();
        this.transactions = new ArrayList<Transaction>();
    }

    public String viewCheckingBalance() {
        String ret = "";
        CheckingAccount c = this.checkings.get(this.checkings.size()-1);
        
        ret = ret+c.toString();
        ret = ret + "/n";
        
        //System.out.println(ret);
        return ret;
        //return this.checkings;
    }

    public String viewSavingBalance() {
        String ret = "";
        SavingAccount c = this.savings.get(this.savings.size()-1);
        
        ret = ret+c.toString();
        ret = ret + "/n";
        //System.out.println(ret);
        return ret;

    }

    public String viewSecuritiesBalance() {
        String ret = "";
        SecuritiesAccount c = this.securities.get(this.securities.size()-1);
        
        ret = ret+c.toString();
        ret = ret + "/n";
        //System.out.println(ret);
        return ret;

    }

    public List getSaving()
    {
        return this.savings;
    }

    public List getChecking()
    {
        return this.checkings;
    }

    public List getSecurities(){
        return this.securities;
    }

    public ArrayList getLoans()
    {
        return this.loans;
    }


    public void requestLoan(double money){
        Loan l = new Loan("USD", money,FancyBank.LOANINTEREST);
        this.loans.add(l);
        FancyBank.VARIABLE.updateAccount(this.getName(),l);
    }

    public void createSavingAccount(String currency, double money) {
        SavingAccount sav = new SavingAccount(FancyBank.SAVINGINTEREST, FancyBank.SAVINGWITHDRAWCOUNTLIMIT);
        this.savings.add(sav);
        FancyBank.VARIABLE.savings.add(sav);
        this.deposit(currency,money,Integer.toString(sav.getId()));
        FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
        Transaction t = new Transaction(Transaction.FEE, FancyBank.OPENFEE, "USD", String.format("OPEN FEE %d", FancyBank.OPENFEE));
        sav.addTransaction(t);
        this.transactions.add(t);
        FancyBank.VARIABLE.updateTransaction(sav.getId(),t);

    }

    public void deposit(String currency, double money, String accountID) {
        Account account = (Account)Variable.ID_TO_ACCOUNT.get(accountID);
        if (account instanceof SavingAccount) {
            SavingAccount sav = (SavingAccount) account;
            sav.addBalance(money, currency, "deposit",LocalDateTime.now());
            //sav.addBalance(10.0, "USD", "deposit",LocalDateTime.now());
            //System.out.println(sav.getBalance("USD"));
            // System.out.println(money);
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);
            //sav.addTransaction(t);
        } else if (account instanceof CheckingAccount) {
            CheckingAccount sav = (CheckingAccount) account;
            sav.addBalance(money, currency, "deposit", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            //sav.addTransaction(t);
            //FancyBank.VARIABLE.updateTransation(t);
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);

        } else if (account instanceof SecuritiesAccount) {
            SecuritiesAccount sav = (SecuritiesAccount) account;
            sav.addBalance(money, currency, "deposit", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            //sav.addTransaction(t);
            //FancyBank.VARIABLE.updateTransation(t);
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);

        }
    }

    public boolean withdraw(String currency, double money, String accountId,ErrorResponse error) {
        Account account = (Account)Variable.ID_TO_ACCOUNT.get(accountId);     
        if (account instanceof SavingAccount) {
            SavingAccount sav = (SavingAccount) account;
            if (sav.getBalance(currency) < money) {

                return false;
            }
            sav.deductBalance(money, currency, "withdraw", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            //sav.addTransaction(t);
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);
        } else if (account instanceof CheckingAccount) {
            CheckingAccount sav = (CheckingAccount) account;
            if (sav.getBalance(currency) < money) {
                error.res = "INVALID AMOUNT";
                return false;
            }
            sav.deductBalance(money, currency, "withdraw", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            //sav.addTransaction(t);
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);

        } else if (account instanceof CheckingAccount) {
            SecuritiesAccount sav = (SecuritiesAccount) account;
            if (sav.getBalance(currency) < money) {
                error.res = "INVALID AMOUNT";
                return false;
            }
            sav.deductBalance(money, currency, "withdraw", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),sav);
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            //sav.addTransaction(t);
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            this.transactions.add(t);


        }
        return true;

    }

    public boolean transfer(String toId, String fromId, String currency, double money,ErrorResponse error) {
        Account from = (Account)Variable.ID_TO_ACCOUNT.get(toId);  
        Account to = (Account)Variable.ID_TO_ACCOUNT.get(fromId);  

        double total = from.getBalance(currency);
        if ((total - money) < money) {
            error.res = "INVALID AMOUNT.TRANSFER FAILED.";
            return false;
        }
        Transaction t1 = new Transaction(Transaction.WITHDRAW, money, currency, "TRANSFER TO");
        Transaction t2 = new Transaction(Transaction.DEPOSIT, money, currency, "TRANSFER FROM");

        FancyBank.VARIABLE.updateTransaction(from.getId(),t1);
        
        FancyBank.VARIABLE.updateTransaction(to.getId(),t2);

        if (from instanceof SavingAccount) {
            SavingAccount s = (SavingAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);
        } else if (from instanceof CheckingAccount) {
            CheckingAccount s = (CheckingAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);

        } else if (from instanceof SecuritiesAccount) {
            SecuritiesAccount s = (SecuritiesAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);

        }

        if (to instanceof SavingAccount) {
            SavingAccount s = (SavingAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);

        } else if (to instanceof CheckingAccount) {
            CheckingAccount s = (CheckingAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);

        } else if (to instanceof SecuritiesAccount) {
            SecuritiesAccount s = (SecuritiesAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());
            FancyBank.VARIABLE.updateAccount(this.getAccountName(),s);

        }

        return true;

    }

    public boolean createSecuritesAccount(String savId, String currency, double money,ErrorResponse error)
            throws NumberFormatException, IOException {
        //SavingAccount sav = (SavingAccount)FancyBank.VARIABLE.ID_TO_ACCOUNT.get(savId);
        SavingAccount sav = this.savings.get(0);
        if(this.savings.size()<=0)
        {
            error.res = "NOT ELIGIBLE FOR SECURITIES ACCOUNT.";
            return false;
        }
        double total = sav.getBalance(currency);
        double rest = total-money;
        if(rest>=2500)
        {
            SecuritiesAccount sec = new SecuritiesAccount();
            sec.addBalance(money, currency,"TRANSFER",LocalDateTime.now());
            sav.deductBalance(money, currency, "TRANSFER", LocalDateTime.now());
            Transaction t1 = new Transaction(Transaction.WITHDRAW, money, currency, String.format("SECURITIES ACCOUNT OPEN FEE %d",money));
            Transaction t2 = new Transaction(Transaction.DEPOSIT, money, currency, String.format("SECURITIES ACCOUNT OPEN FEE %d",money));
            sav.addTransaction(t1);
            sec.addTransaction(t2);
            this.securities.add(sec);
            FancyBank.VARIABLE.updateAccount(this.getAccountName(), sec);
            Transaction t = new Transaction(Transaction.FEE, FancyBank.OPENFEE, "USD", String.format("OPEN FEE %d", FancyBank.OPENFEE));
            FancyBank.VARIABLE.updateTransaction(sav.getId(),t);
            sec.addTransaction(t);
            return true;
        }
        error.res = "FAILED TO CREATE ACCOUNT";
        
        return false;

    }

    

    public void createCheckingAccount(String currency, double money){
        CheckingAccount ck = new CheckingAccount();
        FancyBank.VARIABLE.checkings.add(ck);
        this.checkings.add(ck);
        this.deposit(currency,money,Integer.toString(ck.getId()));
        FancyBank.VARIABLE.updateAccount(this.getAccountName(), ck);
        Transaction t = new Transaction(Transaction.FEE, FancyBank.OPENFEE, "USD", String.format("OPEN FEE %d", FancyBank.OPENFEE));
        ck.addTransaction(t);
        FancyBank.VARIABLE.updateTransaction(ck.getId(),t);
    }

    


   

    public Boolean ifValidForSecurities(String c){
        Boolean ret = false;
        for(SavingAccount se:this.savings)
        {
            //USD/EUR/CNY
            if(se.getBalance(c) >=5000)
            {
                ret = true;
            }
        }

        return ret;
    }

    public String viewTransaction(){
        //display transaction
        String ret = "";
        for(SavingAccount sav:this.savings)
        {
            List lst = sav.getTransactions("USD");
            lst.addAll(sav.getTransactions("CNY"));
            lst.addAll(sav.getTransactions("EUR"));
            
            for(int i = 0;i<lst.size();i++)
            {
                ret = ret+lst.get(i).toString();
                ret = ret + "/n";
            }
            System.out.println(lst);
        }

        for(CheckingAccount sav:this.checkings)
        {
            List lst = sav.getTransactions("USD");
            lst.addAll(sav.getTransactions("CNY"));
            lst.addAll(sav.getTransactions("EUR"));
            //System.out.println(sav.getTransactions("USD"));

            for(int i = 0;i<lst.size();i++)
            {
                ret = ret+lst.get(i).toString();
                ret = ret + "/n";
            }
        }

        for(SecuritiesAccount sav:this.securities)
        {
            List lst = sav.getTransactions("USD");
            lst.addAll(sav.getTransactions("CNY"));
            lst.addAll(sav.getTransactions("EUR"));
            for(int i = 0;i<lst.size();i++)
            {
                ret = ret+lst.get(0).toString();
                ret = ret + "/n";
            }
        }
        //System.out.println(ret);
        return ret;

    }

    public ArrayList getTransaction(){
        return this.transactions;
    }



    public String toString(){
        String str = this.getName()+" , "+this.getAccountName()+" , "+this.getPwd();
        return str;
    }

    public StocksMarket getStock(){
        return this.securities.get(0).getMarket();


    }

    public Set<Entry<String, Tuple<Double, Double>>> getStocksInfo(){
        return this.securities.get(this.securities.size()-1).getMarket().getStocksInfo();
    }



}