package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import util.Encryptor;
import model.*;
import log.*;

public class RegistrationForm extends JDialog{

    //region fields
    private JTextField tfFullName;
    private JTextField tfEmail;
    private JPasswordField pfPasswordAgain;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JButton btnBackToLogin;
    private JPanel registerPanel;
    private User user;
    private Encryptor encryptor = new Encryptor();

    //endregion

    //region register form load
    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(850, 570));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnBackToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.writeToLog("Clicked on back to login button - login window opened");
                dispose();
                new LoginForm(null);
            }
        });

        setVisible(true);
    }

    //endregion

    //region register and add user to database
    private void registerUser() {
        String full_name = tfFullName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String passwordAgain = String.valueOf(pfPasswordAgain.getPassword());
        int permission = 0;
        logger.writeToLog("Registration started");
        if (full_name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - empty fields");
            return;
        }

        if (!password.equals(passwordAgain)) {
            JOptionPane.showMessageDialog(this,
                    "Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - passwords are not matching");
            return;
        }

        if (full_name.length() < 2 ) {
            JOptionPane.showMessageDialog(this,
                    "The name must be more than 1 character",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - not enough character in name field");
            return;
        }

        if(!full_name.matches("^[^-\\s][a-zA-Z_\\s-]+$")) {
            JOptionPane.showMessageDialog(this,
                    "The name cannot contain number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - the name field is containing number(s)");
            return;
        }

        if (password.length() < 5) {
            JOptionPane.showMessageDialog(this,
                    "The password must be more than 4 character",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - not enough character in password field");
            return;
        }

        user = addUserToDatabase(full_name, email, password, permission);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                    logger.writeToLog("Registration success");
            dispose();
            new LoginForm(null);

        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Registration failed - got null user / database connection error");
        }
    }

    private User addUserToDatabase(String full_name, String email, String password, int permission) {
        User user = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection connCheck = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmtCheck = connCheck.createStatement();
            String sqlCheck = "SELECT * FROM users WHERE email=?";
            PreparedStatement preparedStatementForCheck = connCheck.prepareStatement(sqlCheck);
            preparedStatementForCheck.setString(1, email);

            ResultSet resultSetForCheck = preparedStatementForCheck.executeQuery();

            if (resultSetForCheck.next()) {
                if (!resultSetForCheck.wasNull()) {
                    JOptionPane.showMessageDialog(this,
                            "This user is already exist",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                            logger.writeToLog("Registration failed - already existing user");
                }
            }
            else {
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO users (full_name, email, password, permission) " +
                            "VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, full_name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, encryptor.encryptPassword(password));
                    preparedStatement.setInt(4, permission);

                    int addedRows = preparedStatement.executeUpdate();
                    if (addedRows > 0) {
                        user = new User();
                        user.setFull_name(full_name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setPermission(permission);
                    }

                    stmt.close();
                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            stmtCheck.close();
            connCheck.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    //endregion
}
