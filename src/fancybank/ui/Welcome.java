import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Welcome extends BankPanel {

    public Welcome(final BankUI bankUI) {
        super(bankUI);
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        JButton btnUserLogin = new JButton("User Login");
        JButton btnManagerLogin = new JButton("Manager Login");
        panel.add(btnUserLogin);
        panel.add(btnManagerLogin);
        btnUserLogin.addActionListener(new UserLoginListener());
        // btnManagerLogin.addActionListener(new ManagerLoginListener());
        add(panel);
    }

    private class UserLoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bankUI.switchUserLoginPanel();
        }
    }
}