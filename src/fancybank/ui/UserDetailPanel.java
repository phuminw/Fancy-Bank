package fancybank.ui;

import fancybank.account.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.*;

public class UserDetailPanel extends BankPanel {

    private JLabel userNameLabel = new JLabel();
    private JPanel accountsListJpanel = new JPanel();
    private JPanel stockListJpanel = new JPanel();
    private JScrollPane mainScrollJPane;
    private JScrollPane mainScrollJPane2;

    public UserDetailPanel(final BankUI bankUI) {
        super(bankUI);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(userNameLabel);
        add(new JLabel("Account List"));

        mainScrollJPane = new JScrollPane(accountsListJpanel);
        accountsListJpanel.setLayout(new BoxLayout(accountsListJpanel, BoxLayout.Y_AXIS));
        add(mainScrollJPane);

        mainScrollJPane2 = new JScrollPane(stockListJpanel);
        stockListJpanel.setLayout(new BoxLayout(stockListJpanel, BoxLayout.Y_AXIS));
        add(mainScrollJPane2);


        JButton createAccountButton = new JButton("Create New Account");
        JButton logoutButton = new JButton("Logout");
        JButton logButton = new JButton("Log");
        JButton stockListButton = new JButton("Stock List");

        createAccountButton.addActionListener(new CreateAccountListener());
        logoutButton.addActionListener(new LogoutListener());
        logButton.addActionListener(new LogListener());
        // stockListButton.addActionListener(new StockListListener());
        add(createAccountButton);
        add(logoutButton);
        add(logButton);
        add(stockListButton);
    }

    public void refreshAccountList(String name, List<Account> saving, List<Account> checking, List<Account> securities  
    // , List securities
    ) {
        userNameLabel.setText(name + "'s account");
        accountsListJpanel.removeAll();


        if (saving.size() > 0) {
            accountsListJpanel.add(new SingleNormalAccountComponent(bankUI, saving.get(saving.size() - 1)));
        }

        if (checking.size() > 0) {
            accountsListJpanel.add(new SingleNormalAccountComponent(bankUI, checking.get(checking.size() - 1)));
        }

        if (securities.size() > 0) {
            accountsListJpanel.add(new SingleSecurityAccountComponent(bankUI));
        }
            
        // System.out.println(
        // "bankUI.getStockInfo();"
        // );
        // bankUI.getStockInfo();

        // for (Account it : saving) {
        //     accountsListJpanel.add(new SingleNormalAccountComponent(bankUI, it));
        // }

        // for (Account it : checking) {
        //     accountsListJpanel.add(new SingleNormalAccountComponent(bankUI, it));
        // }

        // for (Account it : securities) {
        //     accountsListJpanel.add(new SingleSecurityAccountComponent(bankUI));
        // }

        // for (Stock.StockInfo it : stockInfo) {
        //     JPanel p = new JPanel();
        //     p.add(new SecurityInfoPanel());
        //     JButton btnRemove = new JButton("Remove");
        //     btnRemove.addActionListener(new RemoveStockListener());
        //     btnRemove.setActionCommand("it.id");
        //     p.add(btnRemove);
        //     stockListJpanel.add(p);
        // }
        
    }

    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bankUI.navigateToCreateAccountPage();
        }
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bankUI.logout();
        }
    }

    private class LogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        bankUI.showUserLog();
        }
    }

    // private class StockListListener implements ActionListener {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // new DlgStockList(bankUI.getStockInfo(), bankUI);
    // }
    // }
}