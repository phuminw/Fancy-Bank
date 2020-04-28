import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UserUI extends JFrame {
    public UserUI(String name, Component parent // , ArrayList<Account.AccountInfo> accntInfo,
    ) {
        setTitle("User Info");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("User: " + name));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // for (Account.AccountInfo it : accntInfo) {
        // if (it instanceof MoneyAccount.MoneyAccountInfo) {
        // panel.add(new MoneyAccountInfoPanel(it));
        // } else {
        // panel.add(new StockAccountInfoPanel(it));
        // }
        // }
        add(panel);

        pack();
        setVisible(true);
    }
}