import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends BankPanel {

    public WelcomePage(final BankUI bankUI) {
        super(bankUI);
        setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        JPanel jPanel = new JPanel();
        JButton ManagerLoginButton = new JButton("Manager Login");
        JButton UserLoginButton = new JButton("User Login");
        jPanel.add(UserLoginButton);
        jPanel.add(ManagerLoginButton);
        UserLoginButton.addActionListener(new LoginUserListener());
        ManagerLoginButton.addActionListener(new LoginManagerListener());
        add(jPanel);
    }

    private class LoginUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bankUI.navigateToLoginUserPage();
        }
    }

    private class LoginManagerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bankUI.navigateToManagerPage();
        }
    }
}