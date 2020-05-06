package fancybank.ui;

import fancybank.character.*;
import java.util.List;

import javax.swing.*;
import java.awt.event.*;

public class ManagerPanel extends BankPanel {

    AmountTextField transFeeInputer;

    AmountTextField highBalanceInputer;

    AmountTextField shareThresholdInputer;

    JTextField txtLoanInterest = new JTextField(10);

    JTextField txtStockID = new JTextField(10);

    JTextField txtStockName = new JTextField(10);

    JPanel userPanel = new JPanel();

    JPanel stockPanel = new JPanel();

    public ManagerPanel(final BankUI bankUI) {
        super(bankUI);

        transFeeInputer = new AmountTextField(bankUI);
        highBalanceInputer = new AmountTextField(bankUI);
        shareThresholdInputer = new AmountTextField(bankUI);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));

        JPanel btnPanel = new JPanel();
        JButton btnPayInterest = new JButton("Pay Interest");
        btnPayInterest.addActionListener(new PayInterestListener());
        btnPanel.add(btnPayInterest);

        JButton btnLogAll = new JButton("Log All");
        btnLogAll.addActionListener(new LogAllListener());
        btnPanel.add(btnLogAll);

        JButton btnLogUpdate = new JButton("Log Update");
        btnLogUpdate.addActionListener(new LogUpdateListener());
        btnPanel.add(btnLogUpdate);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new LogoutListener());
        btnPanel.add(btnLogout);
        ctrlPanel.add(btnPanel);

        // JPanel transFeePanel = new JPanel();
        // transFeePanel.add(new JLabel("Set Transaction Fee"));
        // transFeePanel.add(transFeeInputer);
        // JButton btnSetTransFee = new JButton("Confirm");
        // btnSetTransFee.addActionListener(new SetTransFeeListener());
        // transFeePanel.add(btnSetTransFee);
        // ctrlPanel.add(transFeePanel);

        // JPanel highBalancePanel = new JPanel();
        // highBalancePanel.add(new JLabel("Set High Balance"));
        // highBalancePanel.add(highBalanceInputer);
        // JButton btnSetHighBalance = new JButton("Confirm");
        // btnSetHighBalance.addActionListener(new SetHighBalanceListener());
        // highBalancePanel.add(btnSetHighBalance);
        // ctrlPanel.add(highBalancePanel);

        // JPanel shareThresholdPanel = new JPanel();
        // shareThresholdPanel.add(new JLabel("Set Share Account Threshold"));
        // shareThresholdPanel.add(shareThresholdInputer);
        // JButton btnSetShareThreshold = new JButton("Confirm");
        // btnSetShareThreshold.addActionListener(new SetShareThresholdListener());
        // shareThresholdPanel.add(btnSetShareThreshold);
        // ctrlPanel.add(shareThresholdPanel);

        // JPanel loanInterestPanel = new JPanel();
        // loanInterestPanel.add(new JLabel("Set Loan Interest"));
        // loanInterestPanel.add(txtLoanInterest);
        // JButton btnSetLoanInterest = new JButton("Confirm");
        // btnSetLoanInterest.addActionListener(new SetLoanInterestListener());
        // loanInterestPanel.add(btnSetLoanInterest);
        // ctrlPanel.add(loanInterestPanel);

        JScrollPane scrlCtrlPanel = new JScrollPane(ctrlPanel);
        add(scrlCtrlPanel);

        add(new JLabel("User List"));
        JScrollPane scrlUserList = new JScrollPane(userPanel);
        add(scrlUserList);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        // add(new JLabel("Create New Stock"));
        // JPanel stockCtrlPanel = new JPanel();
        // stockCtrlPanel.add(new JLabel("New Stock Name"));
        // stockCtrlPanel.add(txtStockName);
        // stockCtrlPanel.add(new JLabel("New Stock ID"));
        // stockCtrlPanel.add(txtStockID);
        // JButton btnCreateStock = new JButton("Create");
        // btnCreateStock.addActionListener(new CreateNewStockListener());
        // stockCtrlPanel.add(btnCreateStock);
        // add(stockCtrlPanel);

        // add(new JLabel("Stock List"));
        // JScrollPane scrlStockList = new JScrollPane(stockPanel);
        // add(scrlStockList);
        // stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.Y_AXIS));
    }

    public void refreshAccountList(List<Customer> userList, String stockInfo) {
        userPanel.removeAll();
        for (Customer it : userList) {
            JButton btnUser = new JButton(it.getAccountName());
            btnUser.setActionCommand(it.getAccountName());
            btnUser.addActionListener(new UserCheckListener());
            userPanel.add(btnUser);
        }


        // stockPanel.removeAll();
        // for (Stock.StockInfo it : stockInfo) {
        //     JPanel p = new JPanel();
        //     p.add(new SecurityInfoPanel());
        //     JButton btnRemove = new JButton("Remove");
        //     btnRemove.addActionListener(new RemoveStockListener());
        //     btnRemove.setActionCommand("it.id");
        //     p.add(btnRemove);
        //     stockPanel.add(p);
        // }
    }

    private class PayInterestListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bankUI.payInterest();
        }
    }

    private class LogAllListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bankUI.showLogAll();
        }
    }

    private class LogUpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // bankUI.showLogUpdate();
        }
    }

    private class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bankUI.navigateToWelcomePage();
        }
    }

    private class UserCheckListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            
            new UserUI(name, bankUI, 
            bankUI.getUserSavingAccountInfo(name),
            bankUI.getUserCheckingAccountInfo(name),
            bankUI.getUserSecurityAccountInfo(name));
        }
    }

    private class SetTransFeeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // double amount = transFeeInputer.getAmount();
            // if (amount < 0) {
            // new Message("You would like to input a number larger than 0!", bankUI);
            // return;
            // }
            // Money.Currency currency = transFeeInputer.getCurrency();
            // bankUI.setTransFee(new Money(currency, amount));
            // new Message("Set Transaction Fee Succeeded!", bankUI);
        }
    }

    private class SetHighBalanceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // double amount = highBalanceInputer.getAmount();
            // if (amount < 0) {
            // new Message("You would like to input a number larger than 0!", bankUI);
            // return;
            // }
            // Money.Currency currency = highBalanceInputer.getCurrency();
            // bankUI.setHighBalance(new Money(currency, amount));
            // new Message("Set High Balance Succeeded!", bankUI);
        }
    }

    private class SetLoanInterestListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // double interestRate = 0;
            // try {
            // interestRate = Double.parseDouble(txtLoanInterest.getText());
            // } catch (Throwable ex) {
            // System.out.println("Error " + ex.getMessage());
            // ex.printStackTrace();
            // }
            // if (interestRate < 1) {
            // new Message("You would like to input a number larger than 1!", bankUI);
            // }
            // bankUI.setLoanInterest(interestRate);
            // new Message("Set Loan Interest Succeeded!", bankUI);
        }
    }

    private class SetShareThresholdListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // double amount = shareThresholdInputer.getAmount();
            // if (amount < 0) {
            // new Message("You would like to input a number larger than 0!", bankUI);
            // return;
            // }
            // Money.Currency currency = shareThresholdInputer.getCurrency();
            // bankUI.setShareThreshold(new Money(currency, amount));
            // new Message("Set Share Account Threshold Succeeded!", bankUI);
        }
    }

    private class CreateNewStockListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // String name = txtStockName.getText();
            // int id;
            // try {
            // id = Integer.parseInt(txtStockID.getText());
            // if (id < 0) {
            // new Message("Please input an integer no less than zero.", bankUI);
            // return;
            // }
            // } catch (Throwable e) {
            // new Message("Please input valid number!", bankUI);
            // return;
            // }
            // ErrorResponse err = new ErrorResponse();
            // bankUI.tryCreateNewStock(id, name, err);
            // new Message(err.msg, bankUI);
            // bankUI.navigateToManagerPage();
        }
    }

    private class RemoveStockListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // int stockID = Integer.parseInt(e.getActionCommand());
            // ErrorResponse err = new ErrorResponse();
            // if (bankUI.tryRemoveStock(stockID, err)) {
            // bankUI.navigateToManagerPage();
            // }
            // new Message(err.msg, bankUI);
        }
    }
}