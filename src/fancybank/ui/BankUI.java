// package fancybank.ui;

import javax.swing.*;

public class BankUI extends JFrame {
    private final Welcome firstPagePanel = new Welcome(this);
    private final UserPanel userPanel = new UserPanel(this);
    private final UserLoginPanel userLoginPanel = new UserLoginPanel(this);
    private final CreateAccountPanel createAccountPanel = new CreateAccountPanel(this);
    private final ManagerPanel managerPanel = new ManagerPanel(this);
    private final SecuritiesInfoPanel securitiesInfoPanel = new SecuritiesInfoPanel();
    private final SecuritiesAccountInfoPanel securitiesAccountInfoPanel = new SecuritiesAccountInfoPanel();

    // private final Bank bank; // Init the band instance

    BankUI() {
        add(firstPagePanel);
        setTitle("Fancy Bank");
        setSize(500, 300);
        setLocation(1500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void switchPage() {
        new UserUI("wo de", new BankPanel(this));

        // getContentPane().removeAll();
        // add(userPanel);
        // SwingUtilities.updateComponentTreeUI(this);
    }

    public void userLogin(final String userName) {
        // bank.userLogin(userName);
        // switchUserPanel();
    }

    public void userLogout() {
        // bank.userLogout();
        // switchWelcomePanel();
    }

    public void switchUserLoginPanel() {
        getContentPane().removeAll();
        add(securitiesAccountInfoPanel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        BankUI bankUI = new BankUI();
    }
}