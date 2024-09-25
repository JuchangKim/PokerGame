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
        try ( Statement statement = conn.createStatement()) {
            // Check and create "User" table if it doesn't exist
            if (!tableExists(statement, "User")) {
                String createUserTable = "CREATE TABLE \"User\" ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "username VARCHAR(50) NOT NULL, "
                        + "chips INT DEFAULT 0, "
                        + "number_of_wins INT DEFAULT 0)";
                statement.executeUpdate(createUserTable);
                System.out.println("\"User\" table created successfully!");
            }

            // Check and create "GameLog" table if it doesn't exist
            if (!tableExists(statement, "GameLog")) {
                String createGameLogTable = "CREATE TABLE \"GameLog\" ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "user_id INT NOT NULL, "
                        + "computer_player_name VARCHAR(50), "
                        + "FOREIGN KEY (user_id) REFERENCES \"User\"(id))";
                statement.executeUpdate(createGameLogTable);
                System.out.println("\"GameLog\" table created successfully!");
            }

            // Check and create "GameState" table if it doesn't exist
            if (!tableExists(statement, "GameState")) {
                String createGameStateTable = "CREATE TABLE \"GameState\" ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "user_id INT NOT NULL, "
                        + "user_chips INT NOT NULL, "
                        + "computer_chips INT NOT NULL, "
                        + "game_info VARCHAR(255), "
                        + "FOREIGN KEY (user_id) REFERENCES \"User\"(id))";
                statement.executeUpdate(createGameStateTable);
                System.out.println("\"GameState\" table created successfully!");
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
