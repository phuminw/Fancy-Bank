import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserLoginPanel extends BankPanel {
    private JTextField userNameTextField;

    public UserLoginPanel(final BankUI bankUI) {
        super(bankUI);
        setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.gray);
        jPanel.add(new JLabel("Customer Name"));
        userNameTextField = new JTextField(20);
        jPanel.add(userNameTextField);
        JButton loginBtn = new JButton("Login");
        JButton createBtn = new JButton("Create");
        jPanel.add(loginBtn);
        jPanel.add(createBtn);
        loginBtn.addActionListener(new LoginUserListener());
        createBtn.addActionListener(new CreateUserListener());
        add(jPanel);
    }

    private class LoginUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = userNameTextField.getText();
            bankUI.loginUser(userName);
            // if (userName.length() > 20) {
            // new Message(bankUI, "The Username must be less than 20 characters!");
            // } else if (true) { // bankUI.userExists(userName)
            // bankUI.loginUser(userName);
            // } else {
            // new Message(bankUI, "No such user exists!");
            // }
        }
    }

    private class CreateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = userNameTextField.getText();
            if (userName.length() > 20) {
                new Message(bankUI, "The Username must be less than 20 characters!");
            }
            // else if (dlgBank.userExists(userName)) {
            // new Message(bankUI,"Username has been used! Please change it");
            // }
            else {
                // bankUI.createUser(userName);
                bankUI.loginUser(userName);
            }
        }
    }

}