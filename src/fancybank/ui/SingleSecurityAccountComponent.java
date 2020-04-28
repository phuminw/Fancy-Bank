package fancybank.ui;

import javax.swing.*;
import java.awt.event.*;

public class SingleSecurityAccountComponent extends BankPanel {
    private int accountID;

    private JTextField txtAccountID = new JTextField(5);

    private JTextField txtStockID = new JTextField(5);

    private JTextField txtStockNum = new JTextField(5);

    public SingleSecurityAccountComponent(BankUI bankUI// , Account.AccountInfo info
    ) {
        super(bankUI);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // add(new SecurityAccountInfoPanel(info));

        // info.reset();

        // String strID = (String) info.getNextField();
        // String strType = (String)info.getNextField();
        // ArrayList<StockAccount.AccountStockInfo> accntStockInfo =
        // (ArrayList<StockAccount.AccountStockInfo>)info.getNextField();
        // Money.MoneyInfo netWorth = (Money.MoneyInfo)info.getNextField();

        // accountID = Integer.parseInt(strID);

        // add(new JLabel("Account ID: " + strID));
        // add(new JLabel("Account Type: " + strType));

        // add(new JLabel("Stock List"));
        // for (StockAccount.AccountStockInfo it : accntStockInfo) {
        // JPanel stockPanel = new JPanel();
        // stockPanel.add(new StockInfoPanel(it.stockInfo));
        // stockPanel.add(new JLabel("Number"));
        // stockPanel.add(new JLabel("" + it.number));
        // add(stockPanel);
        // }
        // add(new JLabel("Net Worth" + netWorth.type + " " + netWorth.amount));

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Associated Account ID"));
        panel1.add(txtAccountID);
        add(panel1);
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Stock ID"));
        panel2.add(txtStockID);
        add(panel2);
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Number of Stock"));
        panel3.add(txtStockNum);
        add(panel3);

        JButton btnBuy = new JButton("Buy");
        btnBuy.addActionListener(new BuyStockListener());
        add(btnBuy);
        JButton btnSell = new JButton("Sell");
        btnSell.addActionListener(new SellStockListener());
        add(btnSell);

        JButton btnDestroy = new JButton("Destroy");
        add(btnDestroy);
    }

    private Integer getAssoAccntID() {
        Integer assoAccntID = null;
        try {
            assoAccntID = Integer.parseInt(txtAccountID.getText());
        } catch (Throwable e) {
            new Message(bankUI, "Please input a valid account ID!");
        }
        return assoAccntID;
    }

    private Integer getStockID() {
        Integer stockID = null;
        try {
            stockID = Integer.parseInt(txtStockID.getText());
        } catch (Throwable e) {
            new Message(bankUI, "Please input a valid stock ID!");
        }
        return stockID;
    }

    private Integer getStockNum() {
        Integer stockNum = null;
        try {
            stockNum = Integer.parseInt(txtStockNum.getText());
        } catch (Throwable e) {
            new Message(bankUI, "Stock number must larger than 0!");
        }
        return stockNum;
    }

    private class BuyStockListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer assoAccntID = getAssoAccntID();
            Integer stockID = getStockID();
            Integer stockNum = getStockNum();
            if (assoAccntID == null || stockID == null || stockNum == null) {
                return;
            }
            if (stockNum.intValue() <= 0) {
                new Message(bankUI, "Stock number must larger than 0!");
                return;
            }
            // ErrorResponse err = new ErrorResponse();
            // if (bankUI.tryBuyStock(accountID, assoAccntID.intValue(), stockID.intValue(),
            // stockNum.intValue(), err)) {
            // bankUI.navigateToUserDetailPage();
            // }
            // new Message(err.msg, bankUI);
        }
    }

    private class SellStockListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer assoAccntID = getAssoAccntID();
            Integer stockID = getStockID();
            Integer stockNum = getStockNum();
            if (assoAccntID == null || stockID == null || stockNum == null) {
                return;
            }
            if (stockNum.intValue() <= 0) {
                new Message(bankUI, "Stock number must larger than 0!");
                return;
            }
            // ErrorResponse err = new ErrorResponse();
            // if (bankUI.trySellStock(accountID, assoAccntID.intValue(),
            // stockID.intValue(), stockNum.intValue(), err)) {
            // bankUI.navigateToUserDetailPage();
            // }
            // new Message(err.msg, bankUI);
        }
    }

    private class DestoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bankUI.tryDestroyAccount(accountID);
            bankUI.navigateToUserDetailPage();
        }
    }
}