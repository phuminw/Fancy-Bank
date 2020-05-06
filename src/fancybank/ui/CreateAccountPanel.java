package fancybank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccountPanel extends BankPanel {
    private JComboBox cbxAccntTyp;
    private AmountTextField moneyInputer;

    public CreateAccountPanel(BankUI bankUI) {
        super(bankUI);

        setLayout(new GridBagLayout());

        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.Y_AXIS));

        createAccountPanel.add(new JLabel("Create Account"));

        JPanel typePanel = new JPanel();
        typePanel.add(new JLabel("Account Type"));
        cbxAccntTyp = new JComboBox<String>(new String[] { "Checking", "Saving", "Securities" });
        typePanel.add(cbxAccntTyp);
        createAccountPanel.add(typePanel);

        JPanel depositPanel = new JPanel();
        depositPanel.add(new JLabel("Initial Deposit"));
        moneyInputer = new AmountTextField(bankUI);
        depositPanel.add(moneyInputer);
        createAccountPanel.add(depositPanel);

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener(new ConfirmListener());
        createAccountPanel.add(btnConfirm);

        add(createAccountPanel);
    }

    private class ConfirmListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double amount = moneyInputer.getAmount();
            if (amount <= 0) {
                new Message(bankUI, "You would like to input a number larger than 0!");
                return;
            }
            String currency = moneyInputer.getCurrency();
            String type = cbxAccntTyp.getSelectedItem().toString();

            System.out.println(currency);
            System.out.println(type);
            System.out.println(amount);

            if (bankUI.tryCreateAccount(type, amount, currency)) {
                System.out.println("success");
                bankUI.navigateToUserDetailPage();
            }
        }
    }
}