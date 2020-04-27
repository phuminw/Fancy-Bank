import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserDetailPanel extends BankPanel {

    private JLabel userNameLabel = new JLabel();
    private JPanel accountsListJpanel = new JPanel();
    private JScrollPane mainScrollJPane;

    public UserDetailPanel(final BankUI bankUI) {
        super(bankUI);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(userNameLabel);
        add(new JLabel("Account List"));

        mainScrollJPane = new JScrollPane(accountsListJpanel);
        accountsListJpanel.setLayout(new BoxLayout(accountsListJpanel, BoxLayout.Y_AXIS));
        add(mainScrollJPane);

        JButton createAccountButton = new JButton("Create New Account");
        JButton logoutButton = new JButton("Logout");
        // JButton logButton = new JButton("Log");
        JButton stockListButton = new JButton("Stock List");

        createAccountButton.addActionListener(new CreateAccountListener());
        logoutButton.addActionListener(new LogoutListener());
        // logButton.addActionListener(new LogListener());
        // stockListButton.addActionListener(new StockListListener());
        add(createAccountButton);
        add(logoutButton);
        // add(logButton);
        add(stockListButton);
        refreshAccountList("name");
    }

    public void refreshAccountList(String name) {
        userNameLabel.setText(name + "'s account");
        accountsListJpanel.removeAll();
        accountsListJpanel.add(new SingleAccountComponent(bankUI));
        accountsListJpanel.add(new SingleAccountComponent(bankUI));
        accountsListJpanel.add(new SingleAccountComponent(bankUI));
        accountsListJpanel.add(new SingleAccountComponent(bankUI));
        accountsListJpanel.add(new SingleAccountComponent(bankUI));
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

    // private class LogListener implements ActionListener {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // bankUI.showUserLog();
    // }
    // }

    // private class StockListListener implements ActionListener {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // new DlgStockList(bankUI.getStockInfo(), bankUI);
    // }
    // }
}