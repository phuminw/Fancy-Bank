package fancybank.character;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.graalvm.compiler.replacements.arraycopy.CheckcastArrayCopyCallNode;

import fancybank.account.Account;
import fancybank.account.CheckingAccount;
import fancybank.account.Loan;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;
import fancybank.misc.Transaction;
import fancybank.util.Variable;
import fancybank.FancyBank;

public class Customer extends Character{
    private List<SavingAccount> savings;
    private List<CheckingAccount> checkings;
    private List<SecuritiesAccount> securites;
    private List<Loan> loans;
    private List<Transaction> transactions;

    private StdinWrapper sinwrap;


    public Customer(String name, String accountName, String pwd) {
        super(name, accountName, pwd,"C");
        sinwrap = new StdinWrapper("");
        

        this.savings = new ArrayList<SavingAccount>();
        this.checkings = new ArrayList<CheckingAccount>();
        this.securites = new ArrayList<SecuritiesAccount>();
        this.loans = new ArrayList<Loan>();
        this.transactions = new ArrayList<Transaction>();
    }

    public void requestLoan(){
        sinwrap.setMessage("Which item do you have?");
        String item = sinwrap.next();
        sinwrap.setMessage("How much do you want?");
        Double money = (Double)sinwrap.next();
        sinwrap.setMessage("in which currency?");
        String currency = sinwrap.next();
        
        Loan l = new Loan(currency, money, Fancybank.LOANINTEREST);
        this.loans.add(l);
    }

    public void viewCheckingBalance(){
        return this.checkings;
    }

    public void viewSavingBalance(){
        return this.savings;

    }
    public void viewSecuritiesBalance(){
        return this.securites;

    }

    public void createSavingAccount(){
        SavingAccount sav = new SavingAccount(FancyBank.SAVINGINTEREST, FancyBank.SAVINGWITHDRAWLIMIT);
        this.savings.add(sav);
        FancyBank.VARIABLE.savings.add(sav);
    }

    public void deposit(String currency,double money,Account account)
    {
        if(account.instanceOf(SavingAccount))
        {
            SavingAccount sav = (SavingAccount)account;
            sav.addBalance(money, currency, "deposit",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);
        }
        else if(account.instanceOf(CheckingAccount))
        {
            CheckingAccount sav = (CheckingAccount)account;
            sav.addBalance(money, currency, "deposit",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);

        }
        else if(account.instanceOf(SecuritiesAccount))
        {
            SecuritiesAccount sav = (SecuritiesAccount)account;
            sav.addBalance(money, currency, "deposit",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);
            

        }
    }

    public boolean withdraw(String currency,double money,Account account,ErrorResponse err){
        if(account.instanceOf(SavingAccount))
        {
            SavingAccount sav = (SavingAccount)account;
            if(sav.getBalance(currency)<money)
            {
                
                return false;
            }
            sav.deductBalance(money, currency, "withdraw",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.WITHDRAW,money, currency, "WITHDRAW");
            sav.addTransaction(t);
        }
        else if(account.instanceOf(CheckingAccount))
        {
            CheckingAccount sav = (CheckingAccount)account;
            if(sav.getBalance(currency)<money)
            {
                new Message("INVALID AMOUNT");
                return false;
            }
            sav.deductBalance(money, currency, "withdraw",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.WITHDRAW,money, currency, "WITHDRAW");
            sav.addTransaction(t);

        }
        else if(account.instanceOf(SecuritiesAccount))
        {
            SecuritiesAccount sav = (SecuritiesAccount)account;
            if(sav.getBalance(currency)<money)
            {
                new Message("INVALID AMOUNT");
                return false;
            }
            sav.deductBalance(money, currency, "withdraw",LocalDateTime.now());
            Transaction t = new Transaction(TRANSACTION.WITHDRAW,money, currency, "WITHDRAW");
            sav.addTransaction(t);
            
        }

    }

    public boolean transfer(Account to,Account from, String currency, double money,ErrorResponse err){
        double total = from.getBalance(currency);
        if((total-money)<money)
        {
            new Message("INVALID AMOUNT.TRANSFER FAILED.");
            return false;
        }

        if(from.instanceOf(SavingAccount))
        {
            SavingAccount s = (SavingAccount)from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());
        }
        else if(from.instanceOf(CheckingAccount))
        {
            CheckingAccount s = (CheckingAccount)from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        }
        else if(from.instanceOf(SecuritiesAccount))
        {
            SecuritiesAccount s = (SecuritiesAccount)from;
            s.deductBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        }

        if(to.instanceOf(SavingAccount))
        {
            SavingAccount s = (SavingAccount)to;
            s.addBalance(money, currency, "WITHDRAW", LocalDateTime.now());
        }
        else if(to.instanceOf(CheckingAccount))
        {
            CheckingAccount s = (CheckingAccount)to;
            s.addBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        }
        else if(to.instanceOf(SecuritiesAccount))
        {
            SecuritiesAccount s = (SecuritiesAccount)to;
            s.addBalance(money, currency, "WITHDRAW", LocalDateTime.now());

        }

        return true;

    }


    public boolean createSecuritesAccount(SavingAccount sav,String Currency, double money,ErrorResponse err){
        double total = sav.getBalance(currency);
        double rest = total-momey;
        if(rest>=2500)
        {
            SecuritiesAccount sec = new SecuritiesAccount();
            sec.addBalance(money, currency,"TRANSFER",LocalDateTime.now());
            sav.setBalance(money, currency);
            Transaction t = new Transaction(Transaction.WITHDRAW, amount, Currency, String.format("SECURITIES ACCOUNT OPEN FEE %d", amount));
            sav.addTransaction(t);
            return true;
        }
        return false;

        // String c = sinwrap.next();
        // if(ifValidForSecurities(c))
        // {
        //     sinwrap.setMessage("how much do you wish to transfer to the account?");
        //     Double amount = (Double)sinwrap.next();
        //     Boolean success = false;
        //     for(SavingAccount s: this.savings)
        //     {
        //         Double cur_balance = s.getBalance(c)-amount;
        //         if(cur_balance>=2500)
        //         {
        //             success = true;
        //             s.setBalance(cur_balance, c);
        //             SecuritiesAccount se = new SecuritiesAccount();
        //             se.addBalance(amount, c,"from saving",LocalDate);
        //             this.securites.add(se);
        //             Transaction t1 = new Transaction(Transaction.FEE, amount, c, String.format("SECURITIES ACCOUNT OPEN FEE %d", amount));
        //             se.addTransaction(t1);
        //             Transaction t2 = new Transaction(Transaction.WITHDRAW, amount, c, String.format("SECURITIES ACCOUNT OPEN FEE %d", amount));
        //             s.addTransaction(t2);
        //             break;
        //         }
        //     }
        //     if(success)
        //     {
        //         sinwrap.setMessage("created successfully");
        //     }
        //     else
        //     {
        //         sinwrap.setMessage("invalid amount");
        //     }

        // }
        // else
        // {
        //     sinwrap.setMessage("No eligible for securities account");
        // }

    }

    

    public void createCheckingAccount(){
        CheckingAccount ck = new CheckingAccount();
        FancyBank.VARIRABLE.checkings.add(ck);
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

    public void viewTransaction(){
        //display transaction
        return this.transactions;

    }

}