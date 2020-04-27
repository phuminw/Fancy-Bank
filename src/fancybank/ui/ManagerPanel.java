import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
        // btnPayInterest.addActionListener(new PayInterestListener());
        btnPanel.add(btnPayInterest);

        JButton btnLogAll = new JButton("Log All");
        // btnLogAll.addActionListener(new LogAllListener());
        btnPanel.add(btnLogAll);

        JButton btnLogUpdate = new JButton("Log Update");
        // btnLogUpdate.addActionListener(new LogUpdateListener());
        btnPanel.add(btnLogUpdate);

        JButton btnLogout = new JButton("Logout");
        // btnLogout.addActionListener(new LogoutListener());
        btnPanel.add(btnLogout);
        ctrlPanel.add(btnPanel);

        JPanel transFeePanel = new JPanel();
        transFeePanel.add(new JLabel("Set Transaction Fee"));
        transFeePanel.add(transFeeInputer);
        JButton btnSetTransFee = new JButton("Confirm");
        // btnSetTransFee.addActionListener(new SetTransFeeListener());
        transFeePanel.add(btnSetTransFee);
        ctrlPanel.add(transFeePanel);

        JPanel highBalancePanel = new JPanel();
        highBalancePanel.add(new JLabel("Set High Balance"));
        highBalancePanel.add(highBalanceInputer);
        JButton btnSetHighBalance = new JButton("Confirm");
        // btnSetHighBalance.addActionListener(new SetHighBalanceListener());
        highBalancePanel.add(btnSetHighBalance);
        ctrlPanel.add(highBalancePanel);

        JPanel shareThresholdPanel = new JPanel();
        shareThresholdPanel.add(new JLabel("Set Share Account Threshold"));
        shareThresholdPanel.add(shareThresholdInputer);
        JButton btnSetShareThreshold = new JButton("Confirm");
        // btnSetShareThreshold.addActionListener(new SetShareThresholdListener());
        shareThresholdPanel.add(btnSetShareThreshold);
        ctrlPanel.add(shareThresholdPanel);

        JPanel loanInterestPanel = new JPanel();
        loanInterestPanel.add(new JLabel("Set Loan Interest"));
        loanInterestPanel.add(txtLoanInterest);
        JButton btnSetLoanInterest = new JButton("Confirm");
        // btnSetLoanInterest.addActionListener(new SetLoanInterestListener());
        loanInterestPanel.add(btnSetLoanInterest);
        ctrlPanel.add(loanInterestPanel);

        JScrollPane scrlCtrlPanel = new JScrollPane(ctrlPanel);
        add(scrlCtrlPanel);

        add(new JLabel("User List"));
        JScrollPane scrlUserList = new JScrollPane(userPanel);
        add(scrlUserList);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        add(new JLabel("Create New Stock"));
        JPanel stockCtrlPanel = new JPanel();
        stockCtrlPanel.add(new JLabel("New Stock Name"));
        stockCtrlPanel.add(txtStockName);
        stockCtrlPanel.add(new JLabel("New Stock ID"));
        stockCtrlPanel.add(txtStockID);
        JButton btnCreateStock = new JButton("Create");
        // btnCreateStock.addActionListener(new CreateNewStockListener());
        stockCtrlPanel.add(btnCreateStock);
        add(stockCtrlPanel);

        add(new JLabel("Stock List"));
        JScrollPane scrlStockList = new JScrollPane(stockPanel);
        add(scrlStockList);
        stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.Y_AXIS));
    }
}