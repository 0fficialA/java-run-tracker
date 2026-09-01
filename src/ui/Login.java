package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import model.User;

public class Login extends JFrame implements ActionListener {
    private UserController controller;
    private JTextField userField;
    private JPasswordField passField;
    private JButton submitButton;
    private JButton toSignUp;

    public Login(UserController controller) {
        super("Mile Tracker - Login");
        this.controller = controller;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 260);
        this.setLocationRelativeTo(null); // Center on screen
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header Label
        JLabel headerLabel = new JLabel("Welcome Back", JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(18.0f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(headerLabel, gbc);

        // Reset gridwidth for inputs
        gbc.gridwidth = 1;

        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Username:"), gbc);

        userField = new JTextField(15);
        userField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 1;
        this.add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(new JLabel("Password:"), gbc);

        passField = new JPasswordField(15);
        passField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 2;
        this.add(passField, gbc);

        // Submit Button
        submitButton = new JButton("Log In");
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        this.add(submitButton, gbc);

        // Sign Up Button
        toSignUp = new JButton("Create Account");
        toSignUp.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        this.add(toSignUp, gbc);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            User user = controller.authenticateUser(userField.getText(), new String(passField.getPassword()));

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Welcome back, " + user.getName() + "!");
                this.dispose();
                new MileTracker(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passField.setText("");
            }
        } else if (e.getSource() == toSignUp) {
            this.dispose();
            new SignUp(controller);
        }
    }
}