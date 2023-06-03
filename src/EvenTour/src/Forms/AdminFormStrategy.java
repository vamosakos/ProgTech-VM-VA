package Forms;

import Models.TourDecorator.TourBase;
import Models.TourDecorator.TourWithGuide;
import Models.TourDecorator.TourWithLunch;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import Models.*;
import Strategy.*;
import HelperMethods.*;


public class AdminFormStrategy extends JDialog implements AdminDashboardLoadStrategy {
    private JPanel evenTourPanelAdmin;
    private JTextField tfRoute;
    private JPanel jpDatePicker;
    private JTextField tfDistance;
    private JTextField tfPrice;
    private JTextField tfId;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnLogout;
    private JTable evenTourDashboardTable;
    private JCheckBox cbGuide;
    private JCheckBox cbLunch;
    public Tour tour;
    public JDateChooser datechooser = new JDateChooser();
    public PreparedStatement pst;
    public User loggedInUser;
    public TableLoad tableLoad = new TableLoad();
    TourBase tourBase;

    @Override
    public void adminDashboardLoad(User user) {
        setTitle("evenTour Dashboard (admin)");
        setContentPane(evenTourPanelAdmin);
        setMinimumSize(new Dimension(850, 570));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        datechooser.setDateFormatString("dd/MM/yyyy");
        jpDatePicker.add(datechooser);
        loggedInUser = user;
        tableLoad.tableLoad(evenTourDashboardTable);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTour();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTour();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });

        cbGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbLunch.setSelected(true);
            }
        });

        cbLunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbGuide.setSelected(false);
            }
        });

        setVisible(true);
    }

    public AdminFormStrategy(User user) {
        adminDashboardLoad(user);
    }

    private void addTour() {
        try {
            int distanceNFE = Integer.parseInt(tfDistance.getText());
            int priceNFE = Integer.parseInt(tfPrice.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String route = tfRoute.getText();
        Date date = datechooser.getDate();
        int distance = Integer.parseInt(tfDistance.getText());
        int price = Integer.parseInt(tfPrice.getText());


        if (cbGuide.isSelected())
        {
            tourBase = new TourWithGuide(new TourWithLunch(route,date,distance, price));
        }
        else if (cbLunch.isSelected()) {
            tourBase = new TourWithLunch(route,date,distance, price);
        }
        else{
            tourBase = new TourBase(route, date, distance, price) {
                @Override
                public String getRoute() {
                    return route;
                }

                @Override
                public Date getDate() {
                    return date;
                }

                @Override
                public int getDistance() {
                    return distance;
                }

                @Override
                public int getPrice() {
                    return price;
                }
            };
        }


        if (route.isEmpty() || date == null) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (distance <= 0) {
            JOptionPane.showMessageDialog(this,
                    "The distance must be greater than 0",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (price < 500) {
            JOptionPane.showMessageDialog(this,
                    "The price must be more than 500 HUF",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        tour = addTourToDatabase(tourBase);

        if (tour != null) {
            JOptionPane.showMessageDialog(this,
                    "Tour successfully added",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add new tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private Tour addTourToDatabase(TourBase tourBase) {
        Tour tour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO tours (route, date, distance, price) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tourBase.getRoute());
            preparedStatement.setObject(2, tourBase.getDate());
            preparedStatement.setInt(3, tourBase.getDistance());
            preparedStatement.setInt(4, tourBase.getPrice());

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                tour = new Tour();
                tour.setRoute(tourBase.getRoute());
                tour.setDate(tourBase.getDate());
                tour.setDistance(tourBase.getDistance());
                tour.setPrice(tourBase.getPrice());
            }

            stmt.close();
            conn.close();
            tableLoad.tableLoad(evenTourDashboardTable);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
    }


    private void deleteTour() {
        try {
            int tourIdNFE = Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid tour id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tourId = Integer.parseInt(tfId.getText());

        tour = deleteTourFromDatabase(tourId);
        if (tour != null) {
            JOptionPane.showMessageDialog(this,
                    "Tour successfully deleted",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to delete tour",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private Tour deleteTourFromDatabase(int id) {
        Tour tour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM tours where id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                tour = new Tour();
                tour.setId(id);
            }

            stmt.close();
            conn.close();
            tableLoad.tableLoad(evenTourDashboardTable);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
    }

}
