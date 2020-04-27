import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Message extends JFrame {
    public Message(Component parent, String msg) {
        JLabel message = new JLabel(msg);
        add(message);
        setTitle(msg);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}