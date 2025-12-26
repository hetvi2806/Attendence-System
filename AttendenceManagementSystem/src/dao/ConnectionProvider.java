package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionProvider {

    private static final String DB_NAME = "attendence";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 1: connect without DB
            Connection con = DriverManager.getConnection(
                    DB_URL + "?useSSL=false&allowPublicKeyRetrieval=true",
                    DB_USERNAME,
                    DB_PASSWORD
            );

            // Step 2: create DB if not exists
            if (!databaseExists(con, DB_NAME)) {
                createDatabase(con, DB_NAME);
            }

            // Step 3: connect with DB
            con = DriverManager.getConnection(
                    DB_URL + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true",
                    DB_USERNAME,
                    DB_PASSWORD
            );

            return con;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean databaseExists(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        return stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'").next();
    }

    private static void createDatabase(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + dbName);
        System.out.println("Database '" + dbName + "' created successfully..");
    }
}
