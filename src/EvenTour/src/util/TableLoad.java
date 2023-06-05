package util;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.sql.*;
public class TableLoad {
    public static void tableLoad(JTable dashboard) {
        PreparedStatement pst;
        final String DB_URL ="jdbc:mysql://localhost/eventour?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            conn.createStatement();

            pst = conn.prepareStatement("SELECT * FROM tours");
            ResultSet rs = pst.executeQuery();
            dashboard.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
