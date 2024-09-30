/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author billi
 */

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class DBInitialiser {
    
    private static Connection conn;

    public DBInitialiser(Connection conn) {
        this.conn = conn;
        createTables();
    }

    public void createTables() {
    try (Statement statement = conn.createStatement()) {
        // Check and create "User" table if it doesn't exist
        if (!tableExists(statement, "USER")) {
            String createUserTable = "CREATE TABLE \"USER\" ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "USERNAME VARCHAR(50) NOT NULL, "
                    + "CHIPS INT DEFAULT 0, "
                    + "NUMBER_OF_WINS INT DEFAULT 0)";
            statement.executeUpdate(createUserTable);
            System.out.println("\"USER\" table created successfully!");
        }

        // Check and create "GameLog" table if it doesn't exist
        if (!tableExists(statement, "GAMELOG")) {
            String createGameLogTable = "CREATE TABLE \"GAMELOG\" ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "USERNAME_NAME VARCHAR(50), "  // Updated column
                    + "COMPUTER_PLAYER_NAME VARCHAR(50), "  // Fixed typo and added column
                    + "PLAYER_CARD_RANK_PER_ROUND VARCHAR(120), "  // New column
                    + "WINNER_OF_ROUND VARCHAR(50), "  // New column
                    + "FOREIGN KEY (USER_ID) REFERENCES \"USER\"(id))";  // Keep foreign key
            statement.executeUpdate(createGameLogTable);
            System.out.println("\"GAMELOG\" table created successfully!");
        }

        // Check and create "GAME" table if it doesn't exist
        if (!tableExists(statement, "GAME")) {
            String createGameTable = "CREATE TABLE \"GAME\" ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "USER_ID INT NOT NULL, "
                    + "USER_CHIPS INT NOT NULL, "
                    + "COMPUTER_PLAYER_NAME VARCHAR(50) NOT NULL, "
                    + "COMPUTER_CHIPS INT NOT NULL, "
                    + "GAME_INFO VARCHAR(255), "
                    + "FOREIGN KEY (USER_ID) REFERENCES \"USER\"(ID))";
            statement.executeUpdate(createGameTable);
            System.out.println("\"GAME\" table created successfully!");
        }

    } catch (SQLException e) {
        System.err.println("Error creating tables: " + e.getMessage());
        e.printStackTrace();
    }
}


// Helper method to check if a table exists
    private boolean tableExists(Statement statement, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME = '" + tableName.toUpperCase() + "'";
        try ( ResultSet rs = statement.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

}
