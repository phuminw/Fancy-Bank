// package fancybank.ui;

import javax.swing.*;

public class BankUI extends JFrame {
    // private final Bank bank; // Init the band instance
    private final WelcomePage welcomePage = new WelcomePage(this);
    private final UserLoginPanel userLoginPage = new UserLoginPanel(this);
    private final UserDetailPanel userPage = new UserDetailPanel(this);
    private final CreateAccountPanel createAccountPage = new CreateAccountPanel(this);
    private final ManagerPanel managerPage = new ManagerPanel(this);
    private final SecuritiesInfoPanel securitiesInfoPanel = new SecuritiesInfoPanel();
    private final SecuritiesAccountInfoPanel securitiesAccountInfoPanel = new SecuritiesAccountInfoPanel();

    BankUI() {
        add(welcomePage);
        setTitle("Fancy Bank");
        setSize(600, 300);
        setLocation(1500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void loginUser(final String customerName) {
        // bank.userLogin(customerName);
        navigateToUserDetailPage();
    }

    public void logout() {
        // bank.userLogout();
        // switchWelcomePanel();
    }

    public void navigateToLoginUserPage() {
        getContentPane().removeAll();
        add(userLoginPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToLoginManagerPage() {
        getContentPane().removeAll();
        add(managerPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToUserDetailPage() {
        getContentPane().removeAll();
        add(userPage);
        // userPanel.update(bank.getActiveUserName(), bank.getActiveUserAccountsInfo());
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void navigateToCreateAccountPage() {
        getContentPane().removeAll();
        add(createAccountPage);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        BankUI bankUI = new BankUI();
    }
}