package fancybank.ui;

import javax.swing.*;
import java.awt.*;

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
        cbxAccntTyp = new JComboBox<String>(new String[] { "()", "()" });
        typePanel.add(cbxAccntTyp);
        createAccountPanel.add(typePanel);

        JPanel depositPanel = new JPanel();
        depositPanel.add(new JLabel("Initial Deposit"));
        moneyInputer = new AmountTextField(bankUI);
        depositPanel.add(moneyInputer);
        createAccountPanel.add(depositPanel);

        JButton btnConfirm = new JButton("Confirm");
        // btnConfirm.addActionListener(new ConfirmListener());
        createAccountPanel.add(btnConfirm);

        add(createAccountPanel);
    }
}