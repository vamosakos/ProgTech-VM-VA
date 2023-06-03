package Forms;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import Strategy.*;
import Models.*;
import HelperMethods.*;


public class UserFormStrategy extends JFrame implements UserDashboardLoadStrategy {
    private JPanel evenTourPanelUser;
    private JTextField tfId;
    private JButton btnSignUp;
    private JButton btnUnsubscribe;
    private JButton btnLogout;
    private JTable evenTourDashboardTable;
    public User loggedInUser;
    public UserTour userTour;
    public TableLoad tableLoad = new TableLoad();

    @Override
    public void userDashboardLoad(User user) {
        setTitle("evenTour Dashboard");
        setContentPane(evenTourPanelUser);
        setMinimumSize(new Dimension(550, 570));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loggedInUser = user;
        tableLoad.tableLoad(evenTourDashboardTable);

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

        evenTourDashboardTable.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = evenTourDashboardTable.getSelectedRow();
                tfId.setText(evenTourDashboardTable.getValueAt(row, 0).toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });


        setVisible(true);


    }
    public UserFormStrategy(User user) {
        userDashboardLoad(user);
    }

    private void signUpTour() {
        try {
            int signUpId = Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfId.getText());
        int userId = loggedInUser.getId();

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
            Connection connCheck = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmtCheck = connCheck.createStatement();
            String sqlCheck = "SELECT * FROM user_tour WHERE user_id=? AND tour_id=? ";
            PreparedStatement preparedStatementForCheck = connCheck.prepareStatement(sqlCheck);
            preparedStatementForCheck.setInt(1, evenTourUserId);
            preparedStatementForCheck.setInt(2, evenTourTourId);

            ResultSet resultSetForCheck = preparedStatementForCheck.executeQuery();

            if (resultSetForCheck.next()) {
                if (!resultSetForCheck.wasNull()) {
                    JOptionPane.showMessageDialog(this,
                            "You already signed up for this tour",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
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
                        userTour.setUserId(evenTourUserId);
                        userTour.setTourId(evenTourTourId);
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

        return userTour;
    }


    private void unsubscribeTour() {
        try {
            int unsubscribeId = Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfId.getText());
        int userId = loggedInUser.getId();

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
                userTour.setUserId(userId);
                userTour.setTourId(tourId);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userTour;
    }
}
