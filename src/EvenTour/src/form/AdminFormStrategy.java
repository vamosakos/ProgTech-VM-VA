package form;

import model.tourDecorator.TourBase;
import model.tourDecorator.TourWithGuide;
import model.tourDecorator.TourWithLunch;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Date;
import model.*;
import strategy.*;
import util.*;

public class AdminFormStrategy extends JDialog implements AdminDashboardLoadStrategy {

    //region fields
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
    private Tour tour;
    private JDateChooser datechooser = new JDateChooser();
    private TableLoad tableLoad = new TableLoad();
    private TourBase tourBase;

    //endregion

    //region form load

    public AdminFormStrategy(User user) {
        adminDashboardLoad(user);
    }
    @Override
    public void adminDashboardLoad(User user) {
        setTitle("evenTour Dashboard (admin)");
        setContentPane(evenTourPanelAdmin);
        setMinimumSize(new Dimension(850, 570));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        datechooser.setDateFormatString("dd/MM/yyyy");
        jpDatePicker.add(datechooser);
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
                new LoginForm(null);
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
    private void addTour() {
        try {
            Integer.parseInt(tfDistance.getText());
            Integer.parseInt(tfPrice.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid format or missing field(s)",
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
            Integer.parseInt(tfId.getText());
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

    //endregion
}
