package fancybank.character;

public abstract class Character{
    private String name;
    private String accountName;
    private String pwd;
    
    /**
     * Constructor for Character
     * 
     */
    public Character(String name, String accountName, String pwd){
        this.name = name;
        this.accountName = accountName;
        this.pwd = pwd;
    }

    public Character(){
        this.name = "";
        this.accountName = "";
        this.pwd = "";
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public String getName(){
        return this.name;
    }

    public String getAccountName(){
        return this.accountName;
    }

    public String getPwd(){
        return this.pwd;
    }
}