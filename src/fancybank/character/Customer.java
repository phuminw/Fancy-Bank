package fancybank.character;

public class Customer extends Character{
    private List<SavingAccount> savings;
    private List<CheckingAccount> checkings;
    private List<SecuritiesAccount> securites;
    private List<Loan> loans;
    private List<Transaction> transactions;

    public Customer(String name, String accountName, String pwd) {
        super(name, accountName, pwd);
        // TODO Auto-generated constructor stub
    }

    public void requestLoan(){

    }

    public void viewBalance(){

    }


}