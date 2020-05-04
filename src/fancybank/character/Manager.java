package fancybank.character;

public class Manager extends Character{

    public Manager(String name, String accountName, String pwd) {
        super(name, accountName, pwd,"M");
        // TODO Auto-generated constructor stub
    }

    public Customer checkCustomer(Customer c){
        //use c.viewBalance to display information
        return c;
    }

    public void getReport(){
        
    }

    

}