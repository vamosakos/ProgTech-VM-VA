import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class EvenTourFormUser extends JFrame{
    private JPanel evenTourPanelUser;
    private JTextField tfSignUpId;
    private JTextField tfUnsubscribeId;
    private JButton btnSignUp;
    private JButton btnUnsubscribe;
    private JButton btnLogout;
    private JTable evenTourDashboardTable;
    public User evenTourUser;
    public UserTour userTour;
    public PreparedStatement pst;

    public EvenTourFormUser(User user) {
        setTitle("evenTour Dashboard");
        setContentPane(evenTourPanelUser);
        setMinimumSize(new Dimension(550, 570));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        evenTourUser = user;

        if (evenTourUser.permission == 1)
        {
            EvenTourFormAdmin evenTourFormAdmin = new EvenTourFormAdmin(null);
        }
        tableLoad();

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpTour();
            }
        });
        btnUnsubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unsubscribeTour();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });

        setVisible(true);
    }


    private void tableLoad() {
        Tour tour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();

            pst = conn.prepareStatement("SELECT * FROM tours");
            ResultSet rs = pst.executeQuery();
            evenTourDashboardTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void signUpTour() {
        try {
            int signUpId = Integer.parseInt(tfSignUpId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfSignUpId.getText());
        int userId = evenTourUser.id;

        userTour = signUpTourToDatabase(userId, tourId);
        if (userTour != null) {
            JOptionPane.showMessageDialog(this,
                    "Successfully signed up for the tour",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to sign up for the tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private UserTour signUpTourToDatabase(int evenTourUserId, int evenTourTourId) {
        UserTour userTour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO user_tour (user_id, tour_id) " +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, evenTourUserId);
            preparedStatement.setInt(2, evenTourTourId);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                userTour = new UserTour();
                userTour.userId = evenTourUserId;
                userTour.tourId = evenTourTourId;
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userTour;
    }


    private void unsubscribeTour() {
        try {
            int unsubscribeId = Integer.parseInt(tfUnsubscribeId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfUnsubscribeId.getText());
        int userId = evenTourUser.id;

        userTour = deleteTourFromDatabase(userId, tourId);
        if (userTour != null) {
            JOptionPane.showMessageDialog(this,
                    "Successfully unsubscribed from tour",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to unsubscribe from tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private UserTour deleteTourFromDatabase(int userId, int tourId) {
        UserTour userTour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM user_tour WHERE user_id=? AND tour_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tourId);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                userTour = new UserTour();
                userTour.userId = userId;
                userTour.tourId = tourId;
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userTour;
    }
}
