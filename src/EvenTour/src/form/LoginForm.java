package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import util.Encryptor;
import strategy.*;
import model.*;
import log.*;

public class LoginForm extends JDialog {

    //region fields
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnRegister;
    private JPanel loginPanel;
    private User user;
    private Encryptor encryptor = new Encryptor();

    //endregion

    //region form load
    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(750, 574));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);
                logger.writeToLog("User verification started");
                if (user != null) {
                    dispose();
                    if (user.getPermission() == 1) {
                        new PermissionProcessor(PermissionType.ADMIN, user);
                        logger.writeToLog("Success - ADMIN logged in");
                    }
                    else {
                        new PermissionProcessor(PermissionType.USER, user);
                        logger.writeToLog("Success - USER logged in");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                            logger.writeToLog("Login failed - email or password invalid");
                }
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.writeToLog("Clicked on registration button - registration window opened");
                dispose();
                new RegistrationForm(null);
            }
        });

        setVisible(true);
    }
    //endregion

    //region user verification
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, encryptor.encryptPassword(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFull_name(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPermission(resultSet.getInt("permission"));
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    //endregion
}
