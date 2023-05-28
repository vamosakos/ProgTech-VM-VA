import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JDialog{
    private JTextField tfFullName;
    private JTextField tfEmail;
    private JPasswordField pfPasswordAgain;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JButton btnBackToLogin;
    private JPanel registerPanel;

    public User user;
    Encryptor encryptor = new Encryptor();

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(550, 570));
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
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });

        setVisible(true);
    }


    private void registerUser() {
        String full_name = tfFullName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String passwordAgain = String.valueOf(pfPasswordAgain.getPassword());
        int permission = 0;
        if (full_name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(passwordAgain)) {
            JOptionPane.showMessageDialog(this,
                    "Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (full_name.length() < 2 ) {
            JOptionPane.showMessageDialog(this,
                    "The name must be more than 1 character",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!full_name.matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(this,
                    "The name cannot contain a number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 5) {
            JOptionPane.showMessageDialog(this,
                    "The password must be more than 4 character",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(full_name, email, password, permission);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            LoginForm loginForm = new LoginForm(null);

        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private User addUserToDatabase(String full_name, String email, String password, int permission) {
        User user = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (full_name, email, password, permission) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, encryptor.encryptPassword(password));
            preparedStatement.setInt(4, 0);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.full_name = full_name;
                user.email = email;
                user.password = password;
                user.permission = permission;
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
