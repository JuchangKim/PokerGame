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

public class DBInitialiser {
    
    private static Connection conn;

    public DBInitialiser(Connection conn) {
        this.conn = conn;
        createTables();
    }

    public void createTables() {
        try (Statement statement = conn.createStatement()) {
            String createUserTable = "CREATE TABLE IF NOT EXISTS User ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "username VARCHAR(50), "
                + "chips INT, "
                + "number_of_wins INT)";
            statement.executeUpdate(createUserTable);

            String createGameLogTable = "CREATE TABLE IF NOT EXISTS GameLog ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "user_id INT, "
                + "computer_player_name VARCHAR(50), "
                + "FOREIGN KEY (user_id) REFERENCES User(id))";
            statement.executeUpdate(createGameLogTable);

            String createGameStateTable = "CREATE TABLE IF NOT EXISTS GameState ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "user_id INT, "
                + "user_chips INT, "
                + "computer_chips INT, "
                + "game_info VARCHAR(255), "
                + "FOREIGN KEY (user_id) REFERENCES User(id))";
            statement.executeUpdate(createGameStateTable);

            System.out.println("Tables created successfully!");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
