package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance = null;
    private Connection con = null;

    private DBConnection() throws SQLException {
        try {
            final String JDBC_URL = "jdbc:postgresql://localhost:5432/SimpleDB";
            final String user = "postgres";
            final String password = "0000";
            con = DriverManager.getConnection(JDBC_URL,user,password);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed in DBConnection: " + e.getMessage());
        }
    }


    public Connection getConnection() {
        return con;
    }

    public static Connection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance.getConnection();
    }
}
