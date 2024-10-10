/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game.Database;

/**
 *
 * @author billi
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    
    private static final String USER_NAME = "poker"; //your DB username
    private static final String PASSWORD = "poker"; //your DB password
    private static final String URL = "jdbc:derby:PokerGameEmbd; create=true";  //url of the DB host

    private static Connection conn = null;

    public DBManager() {
        establishConnection();
        new DBInitialiser(conn);
    }

    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        System.out.println(dbManager.getConnection());
        
        // Close connections when done
        dbManager.closeConnections();
    }

    public static Connection getConnection() {
       try {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connection (re)established successfully!");
        }
    } catch (SQLException e) {
        System.err.println("Failed to establish connection: " + e.getMessage());
        e.printStackTrace();
    }
    return conn;
    }
       

    //Establish connection
    public void establishConnection() {
        //Establish a connection to Database
        Connection connection = null;

        try {
            // Load Derby JDBC driver (optional for newer JDBC drivers)
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Establish the connection and assign it to the class field
            
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            conn.setSchema("POKER");
            System.out.println("Connection established successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();

            // Close the connection if it was partially opened
            closeConnections();
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();

            // Close the connection if it was partially opened
            closeConnections();
        }

    }
    //Code acquired from ChatGPT
    // Close the database connection and shutdown Derby
    public void closeConnections() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while closing connection: " + e.getMessage());
            e.printStackTrace();
        }

        // Now attempt to shut down the Derby database
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                System.out.println("Derby shut down successfully.");
            } else {
                e.printStackTrace(); // Handle other exceptions
            }
        }
    }
    
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ResultSet queryDB(String sql) {
        ResultSet resultSet = null;

        try ( Statement statement = conn.createStatement()) {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return resultSet;
    }

    public void updateDB(String sql) {
        try ( Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //Code acquired from ChatGPT
    public static void close() throws SQLException {
        new DBManager().closeConnections();
    }
    
}
