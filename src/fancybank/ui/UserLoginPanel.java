import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserLoginPanel extends BankPanel {
    private JTextField txtUserName;

    public UserLoginPanel(final BankUI bankUI) {
        super(bankUI);
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.add(new JLabel("User Name"));
        txtUserName = new JTextField(10);
        panel.add(txtUserName);
        JButton loginBtn = new JButton("Login");
        JButton createBtn = new JButton("Create");
        panel.add(loginBtn);
        panel.add(createBtn);
        loginBtn.addActionListener(new LoginUserListener());
        createBtn.addActionListener(new CreateUserListener());
        add(panel);
    }

    private class LoginUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userName = txtUserName.getText();
            if (userName.length() > 20) {
                new Message(bankUI, "The Username must be less than 20 characters!");
            }
            // else if (bankUI.userExists(userName)) {
            // dlgBank.userLogin(userName);
            // }
            else {
                new Message(bankUI, "No such user exists!");
            }
        }
    }

    private class CreateUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userName = txtUserName.getText();
            if (userName.length() > 20) {
                new Message(bankUI, "The Username must be less than 20 characters!");
            }
            // else if (dlgBank.userExists(userName)) {
            // new Message(bankUI,"Username has been used! Please change it");
            // }
            else {
                // bankUI.createUser(userName);
                bankUI.userLogin(userName);
            }
        }
    }

}