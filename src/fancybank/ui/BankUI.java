package fancybank.ui;

import javax.swing.*;

import fancybank.FancyBank;
import fancybank.util.*;

public class BankUI extends JFrame {
    private static FancyBank fancybank; // Init the band instance
    private final WelcomePage welcomePage = new WelcomePage(this);
    private final UserLoginPanel userLoginPage = new UserLoginPanel(this);
    private final UserDetailPanel userPage = new UserDetailPanel(this);
    private final CreateAccountPanel createAccountPage = new CreateAccountPanel(this);
    private final ManagerPanel managerPage = new ManagerPanel(this);
    private final SecurityInfoPanel securitiesInfoPanel = new SecurityInfoPanel();
    private final SecurityAccountInfoPanel securitiesAccountInfoPanel = new SecurityAccountInfoPanel();

    BankUI(FancyBank fancybank) {
        BankUI.fancybank = fancybank;
        add(welcomePage);
        setTitle("Fancy Bank");
        setSize(600, 300);
        setLocation(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void loginUser(final String customerName) {
        fancybank.logIn(customerName);
        navigateToUserDetailPage();
    }

    public void logout() {
        fancybank.logOut();
        navigateToWelcomePage();
    }

    public boolean doesUserExist(String userName) {
        System.out.println(userName);
        return fancybank.checkAccountNameValid(userName);
    }

    public void managerLogin() {
        System.out.print("Manager Login");
    }

    public void createUser(String userName) {
        fancybank.createOnlineAccount(userName);
    }

    public boolean tryCreateAccount(String accountType, int initialDeposit) {
        ErrorResponse err = new ErrorResponse();
        // boolean res = fancybank.createAccount(type, initialDeposit, err);
        new Message(this, err.res);
        return false;
    }

    public boolean tryDestroyAccount(int id) {
        return false;
        // Response err = new Response();
        // boolean res = fancybank.tryDestroyAccount(id, err);
        // new Message(err.res, this);
        // return res;
    }

    public void depositMoney(String currency, double money, String accountID) {
        fancybank.deposit(currency, money, accountID);
    }

    public void withdrawMoney(String currency, double money, String accountID) {
        ErrorResponse err = new ErrorResponse();
        if (!fancybank.withdraw(currency, money, accountID, err)) {
            new Message(this, err.res);
        }
    }

    public void transactMoney(int money, int fromID, int toID) {
        // Response err = new Response();
        // if (!fancybank.transactMoney(money, fromID, toID, err)) {
        // new Message(err.res, this);
        // }
    }

    public void payInterest() {
        fancybank.chargeInterest();
    }

    public void showLogAll() {
        new Log("bank.getLogAll()", this);
    }

    // public void showLogUpdate() {
    // new DlgLog(fancybank.getLogUpdate(), this);
    // }

    // public void showUserLog() {
    // new DlgLog(fancybank.getActiveUserLog(), this);
    // }

    // public ArrayList getUserAccountInfo(String name) {
    // return fancybank.getUserAccountInfo(name);
    // }

    public void setTransFee(int newTransFee) {
        // fancybank.setTransFee(newTransFee);
    }

    public void setHighBalance(int highBalance) {
        // fancybank.setHighBalance(highBalance);
    }

    public void setLoanInterest(double interestRate) {
        // fancybank.setLoanInterest(interestRate);
    }

    public void setShareThreshold(int threshold) {
        // fancybank.setShareThreshold(threshold);
    }

    // public boolean tryCreateNewStock(int id, String name, Response err) {
    // return fancybank.tryCreateNewStock(id, name, err);
    // }

    // public boolean tryRemoveStock(int id, Response err) {
    // return fancybank.tryRemoveStock(id, err);
    // }

    // public boolean tryBuyStock(int accountID, int assoAccntID, int stockID, int
    // stockNum, Response err) {
    // return fancybank.tryBuyStock(accountID, assoAccntID, stockID, stockNum, err);
    // }

    // public boolean trySellStock(int accountID, int assoAccntID, int stockID, int
    // stockNum, Response err) {
    // return fancybank.trySellStock(accountID, assoAccntID, stockID, stockNum,
    // err);
    // }

    // public ArrayList getStockInfo() {
    // return fancybank.getStockInfo();
    // }

    public void navigateToLoginUserPage() {
        getContentPane().removeAll();
        add(userLoginPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToManagerPage() {
        getContentPane().removeAll();
        managerPage.refreshAccountList("1", "2");
        add(managerPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToUserDetailPage() {
        getContentPane().removeAll();
        add(userPage);
        // userPanel.update(fancybank.getActiveUserName(),
        // fancybank.getActiveUserAccountsInfo());
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToCreateAccountPage() {
        getContentPane().removeAll();
        add(createAccountPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToWelcomePage() {
        getContentPane().removeAll();
        add(welcomePage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        FancyBank fancybank = new FancyBank();
        BankUI bankUI = new BankUI(fancybank);
    }

}