package fancybank.character;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fancybank.account.Account;
import fancybank.account.CheckingAccount;
import fancybank.account.Loan;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;
import fancybank.misc.Transaction;
import fancybank.util.Variable;
import sun.swing.plaf.synth.Paint9Painter.PaintType;
import fancybank.FancyBank;
import fancybank.util.ErrorResponse;

public class Customer extends Character {
    private ArrayList<SavingAccount> savings;
    private ArrayList<CheckingAccount> checkings;
    private ArrayList<SecuritiesAccount> securites;
    private ArrayList<Loan> loans;
    private ArrayList<Transaction> transactions;

    private StdinWrapper sinwrap;

    public Customer(String name, String accountName, String pwd) {
        super(name, accountName, pwd, "C");
        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();
        this.loans = new ArrayList<Loan>();
        this.transactions = new ArrayList<Transaction>();
    }

    public ArrayList viewCheckingBalance() {
        return this.checkings;
    }

    public ArrayList viewSavingBalance() {
        return this.savings;

    }

    public ArrayList viewSecuritiesBalance() {
        return this.securites;

    }

    public ArrayList getSaving()
    {
        return this.savings;
    }

    public ArrayList getChecking()
    {
        return this.checkings;
    }

    public ArrayList getSecurities(){
        return this.securites;
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

    public void createSavingAccount() {
        SavingAccount sav = new SavingAccount(FancyBank.SAVINGINTEREST, FancyBank.SAVINGWITHDRAWCOUNTLIMIT);
        this.savings.add(sav);
        FancyBank.VARIABLE.savings.add(sav);
        FancyBank.VARIABLE.updateAccount(this.getName(), sav);
    }

    public void deposit(String currency, double money, String accountID) {
        Account account = (Account)Variable.ID_TO_ACCOUNT.get(accountID);
        if (account instanceof SavingAccount) {
            SavingAccount sav = (SavingAccount) account;
            sav.addBalance(money, currency, "deposit", LocalDateTime.now());
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);
        } else if (account instanceof CheckingAccount) {
            CheckingAccount sav = (CheckingAccount) account;
            sav.addBalance(money, currency, "deposit", LocalDateTime.now());
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);

        } else if (account instanceof CheckingAccount) {
            SecuritiesAccount sav = (SecuritiesAccount) account;
            sav.addBalance(money, currency, "deposit", LocalDateTime.now());
            Transaction t = new Transaction(Transaction.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);

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
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            sav.addTransaction(t);
        } else if (account instanceof CheckingAccount) {
            CheckingAccount sav = (CheckingAccount) account;
            if (sav.getBalance(currency) < money) {
                error.res = "INVALID AMOUNT";
                return false;
            }
            sav.deductBalance(money, currency, "withdraw", LocalDateTime.now());
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            sav.addTransaction(t);

        } else if (account instanceof CheckingAccount) {
            SecuritiesAccount sav = (SecuritiesAccount) account;
            if (sav.getBalance(currency) < money) {
                error.res = "INVALID AMOUNT";
                return false;
            }
            sav.deductBalance(money, currency, "withdraw", LocalDateTime.now());
            Transaction t = new Transaction(Transaction.WITHDRAW, money, currency, "WITHDRAW");
            sav.addTransaction(t);


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

        from.addTransaction(t1);
        to.addTransaction(t2);

        if (from instanceof SavingAccount) {
            SavingAccount s = (SavingAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());
        } else if (from instanceof CheckingAccount) {
            CheckingAccount s = (CheckingAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        } else if (from instanceof SecuritiesAccount) {
            SecuritiesAccount s = (SecuritiesAccount) from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        }

        if (to instanceof SavingAccount) {
            SavingAccount s = (SavingAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());
        } else if (to instanceof CheckingAccount) {
            CheckingAccount s = (CheckingAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());

        } else if (to instanceof SecuritiesAccount) {
            SecuritiesAccount s = (SecuritiesAccount) to;
            s.addBalance(money, currency, "DEPOSIT", LocalDateTime.now());

        }

        return true;

    }

    public boolean createSecuritesAccount(SavingAccount sav, String currency, double money,ErrorResponse error)
            throws NumberFormatException, IOException {
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
            this.securites.add(sec);
            FancyBank.VARIABLE.updateAccount(this.getName(), sec);
            return true;
        }
        error.res = "FAILED TO CREATE ACCOUNT";
        return false;

    }

    

    public void createCheckingAccount(){
        CheckingAccount ck = new CheckingAccount();
        FancyBank.VARIABLE.checkings.add(ck);
        this.checkings.add(ck);
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

    public ArrayList viewTransaction(){
        //display transaction
        return this.transactions;

    }



    public String toString(){
        String str = this.getName()+" , "+this.getAccountName()+" , "+this.getPwd();
        return str;
        
    }



}