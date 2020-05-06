package fancybank.ui;

import javax.swing.*;

public class AmountTextField extends BankPanel {
    private JTextField txtAmount;
    private JComboBox cbxCurrencyType;

    public AmountTextField(BankUI bankUI) {
        super(bankUI);
        txtAmount = new JTextField(10);
        add(txtAmount);

        add(new JLabel("Currency Type"));
        cbxCurrencyType = new JComboBox<String>(new String[] { "RMB", "USD", "EUR" });
        add(cbxCurrencyType);
    }

    public double getAmount() {
        double amount = -1;
        try {
            amount = Double.parseDouble(txtAmount.getText());
        } catch (NumberFormatException ex) {
            new Message(bankUI, "Please input valid number!");
        }
        return amount;
    }

    public String getCurrency() {
        return cbxCurrencyType.getSelectedItem().toString();
    }
}
