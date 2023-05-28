import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnRegister;
    private JPanel loginPanel;
    public User user;
    public Encryptor encryptor = new Encryptor();

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    dispose();
                    // stratégia...
                    EvenTourFormUser evenTourFormUser = new EvenTourFormUser(user);
                    //EvenTourFormAdmin evenTourFormAdmin = new EvenTourFormAdmin(null);
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm registrationForm = new RegistrationForm(null);
            }
        });

        setVisible(true);
    }

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
                user.id = resultSet.getInt("id");
                user.full_name = resultSet.getString("full_name");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
                user.permission = resultSet.getInt("permission");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
