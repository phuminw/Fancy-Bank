package fancybank.ui;

import fancybank.account.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SingleNormalAccountComponent extends BankPanel {
    private String accountID;
    private AmountTextField saveInputer;
    private AmountTextField withdrawInputer;
    private AmountTextField transactInputer;
    private JTextField txtAccountID = new JTextField(10);

    public SingleNormalAccountComponent(BankUI bankUI) {
        super(bankUI);
    }

    public SingleNormalAccountComponent(BankUI bankUI, Account account) {
        super(bankUI);
        accountID = account.getId() + "";

        saveInputer = new AmountTextField(bankUI);
        withdrawInputer = new AmountTextField(bankUI);
        transactInputer = new AmountTextField(bankUI);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new NormalAccountInfoPanel(account));

        JPanel savePanel = new JPanel();
        savePanel.add(saveInputer);
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new SaveListener());
        savePanel.add(btnSave);
        add(savePanel);

        JPanel withdrawPanel = new JPanel();
        withdrawPanel.add(withdrawInputer);
        JButton btnWithdraw = new JButton("Withdraw");
        btnWithdraw.addActionListener(new WithdrawListener());
        withdrawPanel.add(btnWithdraw);
        add(withdrawPanel);

        // if (strType.equals(CheckingAccount.class.getSimpleName())) {
            JPanel transactPanel = new JPanel();
            transactPanel.add(transactInputer);
            JButton btnTransact = new JButton("Transact");
            btnTransact.addActionListener(new TransactListener());
            transactPanel.add(new JLabel("Transact Account ID"));
            transactPanel.add(txtAccountID);
            transactPanel.add(btnTransact);
            add(transactPanel);
        // }

        // JButton btnDestroy = new JButton("Destroy");
        // btnDestroy.addActionListener(new DestroyListener());
        // add(btnDestroy);
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double amount = saveInputer.getAmount();
            if (amount <= 0) {
                new Message(bankUI, "You would like to input a number larger than 0!");
                return;
            }
            String currency = saveInputer.getCurrency();
            System.out.println(currency);
            System.out.println(amount);
            System.out.println(accountID);
            
            bankUI.depositMoney(currency, amount, accountID);
            bankUI.navigateToUserDetailPage();
        }
    }

    private class WithdrawListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double amount = withdrawInputer.getAmount();
            if (amount <= 0) {
                new Message(bankUI, "You would like to input a number larger than 0!");
            return;
            }
            String currency = withdrawInputer.getCurrency();
            bankUI.withdrawMoney(currency, amount, accountID);
            bankUI.navigateToUserDetailPage();
        }
    }

    private class TransactListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double amount = transactInputer.getAmount();
            if (amount <= 0) {
                new Message(bankUI, "You would like to input a number larger than 0!");
                return;
            }
            String currency = transactInputer.getCurrency();
            String fromID = accountID;
            String toID;
            try {
                toID = txtAccountID.getText();
            } catch (NumberFormatException ex) {
                new Message(bankUI, "Please input valid number!");
                return;
            }
            
            bankUI.transactMoney(currency, amount, fromID, toID);
            bankUI.navigateToUserDetailPage();
        }
    }

    private class DestroyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // int id = Integer.parseInt(lblAccountID.getText());
            // dlgBank.tryDestroyAccount(accountID);
            // dlgBank.switchUserPanel();
        }
    }
}