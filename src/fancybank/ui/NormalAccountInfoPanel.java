package fancybank.ui;

import fancybank.account.*;
import javax.swing.*;
import java.awt.*;

public class NormalAccountInfoPanel extends JPanel {
    public NormalAccountInfoPanel(Account account) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // info.reset();

        // String strID = (String)info.getNextField();
        // String strType = (String)info.getNextField();
        // String strInterestRate = (String)info.getNextField();
        // Deposit.DepositInfo depositInfo = (Deposit.DepositInfo)info.getNextField();

        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(new GridLayout(2, 3, 10, 5));

        basicPanel.add(new JLabel("Account Type: "));
        basicPanel.add(new JLabel("Account ID: "));
        // basicPanel.add(new JLabel("Account Interest: "));

        basicPanel.add(new JLabel(account.getClass().getSimpleName() + ""));
        basicPanel.add(new JLabel(account.getId()+ ""));
        // basicPanel.add(new JLabel(strInterestRate));

        add(basicPanel);

        JPanel moneyPanel = new JPanel();
        moneyPanel.setLayout(new GridLayout(2, 2, 10, 5));
        moneyPanel.add(new JLabel("Account Balance CNY: "));
        moneyPanel.add(new JLabel(account.getBalance("CNY") + ""));

        moneyPanel.add(new JLabel("Account Balance USD: "));
        moneyPanel.add(new JLabel(account.getBalance("USD") + ""));

        moneyPanel.add(new JLabel("Account Balance EUR: "));
        moneyPanel.add(new JLabel(account.getBalance("EUR") + ""));

        add(moneyPanel);
    }
}