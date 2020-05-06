package fancybank.ui;
import java.util.List;
import fancybank.account.*;
import javax.swing.*;
import java.awt.*;

public class UserUI extends JFrame {
    public UserUI(String name, Component parent, List<Account> saving, List<Account> checking, List<Account> securities) {
        setTitle("User Info");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("User: " + name));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        System.out.println(saving.size());
        System.out.println(checking.size());
        System.out.println(securities.size());

        if (saving.size() > 0) {
            panel.add(new NormalAccountInfoPanel(saving.get(saving.size() - 1)));
        }

        if (checking.size() > 0) {
            panel.add(new NormalAccountInfoPanel(checking.get(checking.size() - 1)));
        }

        if (securities.size() > 0) {
            panel.add(new SecurityAccountInfoPanel());
        }

        add(panel);

        pack();
        setVisible(true);
    }
}