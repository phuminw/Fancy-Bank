
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SingleAccountComponent extends BankPanel {
    private int accountID;
    private AmountTextField saveInputer;
    private AmountTextField withdrawInputer;
    private AmountTextField transactInputer;
    private JTextField txtAccountID = new JTextField(10);

    public SingleAccountComponent(BankUI bankUI) {
        super(bankUI);
        // info.reset();
        // String strID = (String)info.getNextField();
        // String strType = (String)info.getNextField();
        accountID = Integer.parseInt("123");

        saveInputer = new AmountTextField(bankUI);
        withdrawInputer = new AmountTextField(bankUI);
        transactInputer = new AmountTextField(bankUI);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // add(new MoneyAccountInfoPanel(info));

        JPanel savePanel = new JPanel();
        savePanel.add(saveInputer);
        JButton btnSave = new JButton("Save");
        // btnSave.addActionListener(new SaveListener());
        savePanel.add(btnSave);
        add(savePanel);

        JPanel withdrawPanel = new JPanel();
        withdrawPanel.add(withdrawInputer);
        JButton btnWithdraw = new JButton("Withdraw");
        // btnWithdraw.addActionListener(new WithdrawListener());
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
        // btnDestroy.addActionListener(new DestroyListener());
        add(btnDestroy);
    }
}