package fancybank.character;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import fancybank.account.CheckingAccount;
import fancybank.account.Loan;
import fancybank.account.SavingAccount;
import fancybank.account.SecuritiesAccount;
import fancybank.io.StdinWrapper;
import fancybank.misc.Transaction;
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

    public void viewBalance(){
        //display account info

    }

    public void createSavingAccount(){
        SavingAccount sav = new SavingAccount(FancyBank.SAVINGINTEREST, FancyBank.SAVINGWITHDRAWLIMIT);
        this.savings.add(sav);
    }

    public void deposit(String currency,double money,Account account)
    {
        if(account.instanceOf(SavingAccount))
        {
            SavingAccount sav = (SavingAccount)account;
            sav.addBalance(money, currency, "deposit",LocalDateTime);
            Transaction t = new Transaction(TRANSACTION.DEPOSIT, money, currency, "DEPOSIT");
            sav.addTransaction(t);
        }
    }


    public void createSecuritesAccount(){
        sinwrap.setMessage("In which currency?");
        String c = sinwrap.next();
        if(ifValidForSecurities(c))
        {
            sinwrap.setMessage("how much do you wish to transfer to the account?");
            Double amount = (Double)sinwrap.next();
            Boolean success = false;
            for(SavingAccount s: this.savings)
            {
                Double cur_balance = s.getBalance(c)-amount;
                if(cur_balance>=2500)
                {
                    success = true;
                    s.setBalance(cur_balance, c);
                    SecuritiesAccount se = new SecuritiesAccount();
                    se.addBalance(amount, c,"from saving",LocalDate);
                    this.securites.add(se);
                    Transaction t1 = new Transaction(Transaction.FEE, amount, c, String.format("SECURITIES ACCOUNT OPEN FEE %d", amount));
                    se.addTransaction(t1);
                    Transaction t2 = new Transaction(Transaction.WITHDRAW, amount, c, String.format("SECURITIES ACCOUNT OPEN FEE %d", amount));
                    s.addTransaction(t2);
                    break;
                }
            }
            if(success)
            {
                sinwrap.setMessage("created successfully");
            }
            else
            {
                sinwrap.setMessage("invalid amount");
            }

        }
        else
        {
            sinwrap.setMessage("No eligible for securities account");
        }

    }

    public void createCheckingAccount(){
        CheckingAccount ck = new CheckingAccount();
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

    }

}