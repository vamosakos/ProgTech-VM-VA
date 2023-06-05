package form;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import strategy.*;
import model.*;
import util.*;
import log.*;


public class UserFormStrategy extends JFrame implements UserDashboardLoadStrategy {

    //region fields
    private JPanel evenTourPanelUser;
    private JTextField tfId;
    private JButton btnSignUp;
    private JButton btnUnsubscribe;
    private JButton btnLogout;
    private JTable evenTourDashboardTable;
    private User loggedInUser;
    private UserTour userTour;
    private TableLoad tableLoad = new TableLoad();

    //endregion

    //region form load

    public UserFormStrategy(User user) {
        userDashboardLoad(user);
    }
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
                logger.writeToLog("Clicked on logout button - logged out");
                dispose();
                new LoginForm(null);
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


    //endregion

    //region add / delete methods
    private void signUpTour() {
        logger.writeToLog("Clicked on sign up tour button");
        try {
            Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Failed - invalid tour id");
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
                    logger.writeToLog("Success - signed up for the tour");
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to sign up for the tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Failed - got back null value");
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
                            logger.writeToLog("Failed - already signed up for this tour");
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
        logger.writeToLog("Clicked on unsubscribe from tour button");
        try {
            Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Failed - invalid tour id");
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
                    logger.writeToLog("Success - unsubscribed from the tour");
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to unsubscribe from tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
                    logger.writeToLog("Failed - invalid tour id");
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

    //endregion
}
