import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class EvenTourFormUser extends JFrame{
    private JPanel evenTourPanelA;
    private JTextField tfRoute;
    private JPanel jpDatePicker;
    private JTextField tfUnsubscribe;
    private JTextField tfSignUpId;
    private JButton btnSignUp;
    private JTable evenTourDashboardTable;
    private JButton btnUnsubscribe;
    private JButton btnLogout;
    private JTextField tfId;

    public Tour tour;
    public User USER;
    public UserTour userTour;
    PreparedStatement pst;


    public EvenTourFormUser(User user) {
        setTitle("evenTour Dashboard");
        setContentPane(evenTourPanelA);
        setMinimumSize(new Dimension(550, 570));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        USER = user;
        tableLoad();

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpTour();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });
        btnUnsubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unsubscribeTour();
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
            int s = Integer.parseInt(tfSignUpId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int signUpId = Integer.parseInt(tfSignUpId.getText());
        int userId = USER.id;


        userTour = signUpTourToDatabase(userId, signUpId);
        if (userTour != null) {
            JOptionPane.showMessageDialog(this,
                    "Tour successfully added",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add new tour asd",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void unsubscribeTour() {

        try {
            int i = Integer.parseInt(tfUnsubscribe.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an id number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfUnsubscribe.getText());
        int userId = USER.id;

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


    private UserTour signUpTourToDatabase(int userId, int tourId) {
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
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, tourId);

                int addedRows2 = preparedStatement.executeUpdate();
                if (addedRows2 > 0) {
                    userTour = new UserTour();
                    userTour.user_id = userId;
                    userTour.tour_id = tourId;
                }

                stmt.close();
                conn.close();
                tableLoad();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return userTour;
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
                userTour.user_id = userId;
                userTour.tour_id = tourId;
            }

            stmt.close();
            conn.close();
            tableLoad();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userTour;
    }
}
