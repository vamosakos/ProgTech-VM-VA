package HelperMethods;

import Models.Tour;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.*;
public class TableLoad {
    public PreparedStatement pst;
    public void tableLoad(JTable dashboard) {
        Tour tour = null;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();

            pst = conn.prepareStatement("SELECT * FROM tours");
            ResultSet rs = pst.executeQuery();
            dashboard.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
