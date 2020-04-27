import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserPanel extends BankPanel {

    private JLabel lblUserName = new JLabel();
    private JPanel pnlAccounts = new JPanel();
    private JScrollPane scrPnl;

    public UserPanel(final BankUI bankUI) {
        super(bankUI);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(lblUserName);
        add(new JLabel("Account List"));
        scrPnl = new JScrollPane(pnlAccounts);
        pnlAccounts.setLayout(new BoxLayout(pnlAccounts, BoxLayout.Y_AXIS));
        // add(pnlAccounts);
        add(scrPnl);
        JButton btnCreateAccount = new JButton("Create New Account");
        // btnCreateAccount.addActionListener(new CreateAccountListener());
        add(btnCreateAccount);
        JButton btnLogout = new JButton("Logout");
        // btnLogout.addActionListener(new LogoutListener());
        add(btnLogout);
        JButton btnLog = new JButton("Log");
        // btnLog.addActionListener(new LogListener());
        add(btnLog);
        JButton btnStockList = new JButton("Stock List");
        // btnStockList.addActionListener(new StockListListener());
        add(btnStockList);
    }

}