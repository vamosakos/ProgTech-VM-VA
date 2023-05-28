import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;


public class EvenTourFormA extends JDialog{
    private JPanel evenTourPanelA;
    private JTextField tfRoute;
    private JPanel jpDatePicker;
    private JTextField tfLength;
    private JTextField tfPrice;
    private JButton btnAdd;
    private JTable evenTourDashboardTable;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JTextField tfSearch;
    private JButton btnLogout;


    JDateChooser datechooser = new JDateChooser();


    public EvenTourFormA(JFrame parent) {
        super(parent);
        setTitle("evenTour Dashboard (admin)");
        setContentPane(evenTourPanelA);
        setMinimumSize(new Dimension(550, 570));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        datechooser.setDateFormatString("dd/MM/yyyy");
        jpDatePicker.add(datechooser);

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

        setVisible(true);
    }



    private void addTour() {
        String route = tfRoute.getText();
        Date date = datechooser.getDate();
        int length = Integer.parseInt(tfLength.getText());
        int price = Integer.parseInt(tfPrice.getText());
        if (route.isEmpty() || date == null || length == 0 || price == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        tour = addTourToDatabase(route, date, length, price);
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

    public Tour tour;
    private Tour addTourToDatabase(String route, Date date, int length, int price) {
        Tour tour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO tours (route, date, length, price) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, route);
            preparedStatement.setObject(2, date);
            preparedStatement.setInt(3, length);
            preparedStatement.setInt(4, price);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                tour = new Tour();
                tour.route = route;
                tour.date = date;
                tour.length = length;
                tour.price = price;
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
    }
}
