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

public class SignUp extends JFrame implements ActionListener {
    private UserController controller;

    private JTextField nameField;
    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField conPassField;
    private JButton submitButton;
    private JButton backToLoginButton;

    public SignUp(UserController controller) {
        super("Mile Tracker - Sign Up");
        this.controller = controller;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 360);
        this.setLocationRelativeTo(null); // Center on screen
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header Label
        JLabel headerLabel = new JLabel("Create Account", JLabel.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(18.0f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(headerLabel, gbc);

        // Reset gridwidth for inputs
        gbc.gridwidth = 1;

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Name:"), gbc);

        nameField = new JTextField(15);
        nameField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 1;
        this.add(nameField, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(new JLabel("Username:"), gbc);

        userField = new JTextField(15);
        userField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 2;
        this.add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        this.add(new JLabel("Password:"), gbc);

        passField = new JPasswordField(15);
        passField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 3;
        this.add(passField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 4;
        this.add(new JLabel("Confirm Pass:"), gbc);

        conPassField = new JPasswordField(15);
        conPassField.setPreferredSize(new Dimension(180, 30));
        gbc.gridx = 1; gbc.gridy = 4;
        this.add(conPassField, gbc);

        // Submit Button
        submitButton = new JButton("Sign Up");
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        this.add(submitButton, gbc);

        // Back to Login Button
        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        this.add(backToLoginButton, gbc);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(conPassField.getPassword());

            // 1. Validate matching passwords
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                passField.setText("");
                conPassField.setText("");
                return;
            }

            // 2. Delegate creation to Controller
            boolean success = controller.registerUser(name, username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Account created successfully! Please log in.");
                this.dispose();
                new Login(controller);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Ensure all fields are filled.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backToLoginButton) {
            this.dispose();
            new Login(controller);
        }
    }
}