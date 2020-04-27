
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AmountTextField extends BankPanel {
    private JTextField txtAmount;
    private JComboBox cbxCurrencyType;

    public AmountTextField(BankUI bankUI) {
        super(bankUI);
        txtAmount = new JTextField(10);
        add(txtAmount);

        add(new JLabel("Currency Type"));
        cbxCurrencyType = new JComboBox<String>(new String[] { "()", "()" });
        add(cbxCurrencyType);
    }

    // public double getAmount() {
    // double amount = -1;
    // try {
    // amount = Double.parseDouble(txtAmount.getText());
    // } catch (NumberFormatException ex) {
    // new MessageWindow("Please input valid number!", dlgBank);
    // }
    // return amount;
    // }

    // public Money.Currency getCurrency() {
    // return (Money.Currency) cbxCurrencyType.getSelectedItem();
    // }
}