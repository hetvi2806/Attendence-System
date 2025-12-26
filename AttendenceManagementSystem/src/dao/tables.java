package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class tables {

    public static void main(String[] args) {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();

            // ---------- userDetails table ----------
            if (!tableExists(st, "userDetails")) {

                String sql =
                    "CREATE TABLE userDetails (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "gender VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL, " +
                    "contact VARCHAR(20) NOT NULL, " +
                    "address VARCHAR(500), " +
                    "state VARCHAR(100), " +
                    "country VARCHAR(100), " +
                    "uniqueregid VARCHAR(100) NOT NULL, " +
                    "imagename VARCHAR(100)" +
                    ")";

                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "userDetails table created");
            } else {
                System.out.println("userDetails table already exists");
            }

        
            if (!tableExists(st, "userAttendence")) {

                String sql2 =
                    "CREATE TABLE userAttendence (" +
                    "userid INT NOT NULL, " +
                    "date DATE NOT NULL, " +
                    "checkin DATETIME, " +
                    "checkout DATETIME, " +
                    "workduration VARCHAR(100)" +
                  // "FOREIGN KEY (userid) REFERENCES userDetails(id)" +
                    ")";

                st.executeUpdate(sql2);
                JOptionPane.showMessageDialog(null, "userAttendence table created");
            } else {
                System.out.println("userAttendence table already exists");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private static boolean tableExists(Statement st, String tableName) throws Exception {
        ResultSet rs = st.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
        return rs.next();
    }
}
