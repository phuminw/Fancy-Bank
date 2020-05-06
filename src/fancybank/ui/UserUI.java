package fancybank.ui;
import java.util.List;
import fancybank.account.*;
import javax.swing.*;
import java.awt.*;

public class UserUI extends JFrame {
    public UserUI(String name, Component parent, List<Account> accounts) {
        setTitle("User Info");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("User: " + name));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        

        // for (Account it : accounts) {
        //     if (it instanceof CheckingAccount || it instanceof SavingAccount) {
        //         panel.add(new NormalAccountInfoPanel(it));
        //     } else {
        //         panel.add(new SecurityAccountInfoPanel());
        //     }
        // }

        add(panel);

        pack();
        setVisible(true);
    }
}