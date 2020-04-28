import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SecurityInfoPanel extends JPanel {
    public SecurityInfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel();
        idPanel.add(new JLabel("Stock ID: "));
        // idPanel.add(new JLabel(info.id));
        add(idPanel);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Stock Name: "));
        // namePanel.add(new JLabel(info.name));
        add(namePanel);

        JPanel pricePanel = new JPanel();
        pricePanel.add(new JLabel("Price"));
        // pricePanel.add(new JLabel(info.currency));
        // pricePanel.add(new JLabel(info.amount));
        add(pricePanel);
    }
}