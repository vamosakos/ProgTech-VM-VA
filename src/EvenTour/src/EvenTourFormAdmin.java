import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;


public class EvenTourFormAdmin extends JDialog{
    private JPanel evenTourPanelA;
    private JTextField tfRoute;
    private JPanel jpDatePicker;
    private JTextField tfDistance;
    private JTextField tfPrice;
    private JButton btnAdd;
    private JTable evenTourDashboardTable;
    private JButton btnDelete;
    private JButton btnLogout;
    private JTextField tfId;

    public Tour tour;
    JDateChooser datechooser = new JDateChooser();
    PreparedStatement pst;


    public EvenTourFormAdmin(JFrame parent) {
        super(parent);
        setTitle("evenTour Dashboard (admin)");
        setContentPane(evenTourPanelA);
        setMinimumSize(new Dimension(550, 570));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        datechooser.setDateFormatString("dd/MM/yyyy");
        jpDatePicker.add(datechooser);
        tableLoad();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addTour(); }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deleteTour(); }
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
    private void addTour() {
        try {
            int d = Integer.parseInt(tfDistance.getText());
            int p = Integer.parseInt(tfPrice.getText());
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

        tour = addTourToDatabase(route, date, distance, price);
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
    private void deleteTour() {

        try {
            int i = Integer.parseInt(tfId.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an id number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        int id = Integer.parseInt(tfId.getText());

        tour = deleteTourFromDatabase(id);
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


    private Tour addTourToDatabase(String route, Date date, int distance, int price) {
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
            preparedStatement.setString(1, route);
            preparedStatement.setObject(2, date);
            preparedStatement.setInt(3, distance);
            preparedStatement.setInt(4, price);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                tour = new Tour();
                tour.route = route;
                tour.date = date;
                tour.distance = distance;
                tour.price = price;
            }


            stmt.close();
            conn.close();
            tableLoad();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
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
                tour.id = id;
            }


            stmt.close();
            conn.close();
            tableLoad();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
    }
}
