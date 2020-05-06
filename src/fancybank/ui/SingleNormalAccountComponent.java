package fancybank.ui;

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

    public SingleNormalAccountComponent(BankUI bankUI, String info) {
        super(bankUI);
        // info.reset();
        // String strID = (String)info.getNextField();
        // String strType = (String)info.getNextField();
        accountID = "";

        saveInputer = new AmountTextField(bankUI);
        withdrawInputer = new AmountTextField(bankUI);
        transactInputer = new AmountTextField(bankUI);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new NormalAccountInfoPanel());

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
        // JPanel transactPanel = new JPanel();
        // transactPanel.add(transactInputer);
        // JButton btnTransact = new JButton("Transact");
        // btnTransact.addActionListener(new TransactListener());
        // transactPanel.add(new JLabel("Transact Account ID"));
        // transactPanel.add(txtAccountID);
        // transactPanel.add(btnTransact);
        // add(transactPanel);
        // }

        JButton btnDestroy = new JButton("Destroy");
        btnDestroy.addActionListener(new DestroyListener());
        add(btnDestroy);
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double amount = saveInputer.getAmount();
            if (amount <= 0) {
                new Message(bankUI, "You would like to input a number larger than 0!");
                return;
            }
            String currency = saveInputer.getCurrency();
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
            // double amount = transactInputer.getAmount();
            // if (amount <= 0) {
            // new MessageWindow("You would like to input a number larger than 0!",
            // dlgBank);
            // return;
            // }
            // Money.Currency currency = transactInputer.getCurrency();
            // // int fromID = Integer.parseInt(lblAccountID.getText());
            // int fromID = accountID;
            // int toID;
            // try {
            // toID = Integer.parseInt(txtAccountID.getText());
            // } catch (NumberFormatException ex) {
            // new MessageWindow("Please input valid number!", dlgBank);
            // return;
            // }
            // dlgBank.transactMoney(new Money(currency, amount), fromID, toID);
            // dlgBank.switchUserPanel();
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