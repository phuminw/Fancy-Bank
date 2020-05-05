package fancybank.character;

import fancybank.FancyBank;
import fancybank.util.Tuple;

public class Manager extends Character{

    public Manager(String name, String accountName, String pwd) {
        super(name, accountName, pwd,"M");
        // TODO Auto-generated constructor stub
    }

    public Customer checkCustomer(Customer c){
        //use c.viewBalance to display information
        return c;
    }
    
    public String toString(){
        String str = this.getName()+" , "+this.getAccountName()+" , "+this.getPwd();
        return str;
        
    }


    

}