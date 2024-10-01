/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author billi
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public class DBInitialiser {

    private static Connection conn;

    public DBInitialiser(Connection conn) {
        this.conn = conn;
        createTables();
        alterTablesAddDateColumn();
    }

    public void createTables() {
        try (Statement statement = conn.createStatement()) {
            // Check and create "USER" table if it doesn't exist
            if (!tableExists(statement, "USER")) {
                String createUserTable = "CREATE TABLE \"USER\" ("
                        + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "USERNAME VARCHAR(50) NOT NULL, "
                        + "CHIPS INT DEFAULT 0, "
                        + "NUMBER_OF_WINS INT DEFAULT 0)";
                statement.executeUpdate(createUserTable);
                System.out.println("\"USER\" table created successfully!");
            } else {
                System.out.println("\"USER\" table already exists.");
            }
            // Check and create "GAMELOG" table if it doesn't exist
            if (!tableExists(statement, "GAMELOG")) {
                String createGameLogTable = "CREATE TABLE GAMELOG ("
                        + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "USER_ID INT, "
                        + "USERNAME_NAME VARCHAR(50), "
                        + "COMPUTER_PLAYER_NAME VARCHAR(50), "
                        + "PLAYER_CARD_RANK_PER_ROUND VARCHAR(120), "
                        + "WINNER_OF_ROUND VARCHAR(50), "
                        + "FOREIGN KEY (USER_ID) REFERENCES \"USER\"(ID))";
                statement.executeUpdate(createGameLogTable);
                System.out.println("GAMELOG table created successfully!");
            } else {
                System.out.println("GAMELOG table already exists.");
            }
            // ... (GAME table creation remains the same)
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void alterTablesAddDateColumn() {
        try (Statement statement = conn.createStatement()) {
            // Add DATE column to USER table
            if (!columnExists("USER", "CREATED_AT")) {
                String alterUserTable = "ALTER TABLE \"USER\" ADD COLUMN CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
                statement.executeUpdate(alterUserTable);
                System.out.println("Added CREATED_AT column to USER table.");
            }

            // Add DATE column to GAMELOG table
            if (!columnExists("GAMELOG", "LOG_DATE")) {
                String alterGameLogTable = "ALTER TABLE GAMELOG ADD COLUMN LOG_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
                statement.executeUpdate(alterGameLogTable);
                System.out.println("Added LOG_DATE column to GAMELOG table.");
            }

            // Add DATE column to GAME table
            if (!columnExists("GAME", "GAME_DATE")) {
                String alterGameTable = "ALTER TABLE GAME ADD COLUMN GAME_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
                statement.executeUpdate(alterGameTable);
                System.out.println("Added GAME_DATE column to GAME table.");
            }
        } catch (SQLException e) {
            System.err.println("Error altering tables to add date columns: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to check if a column exists in a table
    private boolean columnExists(String tableName, String columnName) throws SQLException {
        DatabaseMetaData metadata = conn.getMetaData();
        ResultSet resultSet = metadata.getColumns(null, null, tableName.toUpperCase(), columnName.toUpperCase());
        boolean exists = resultSet.next();
        resultSet.close();
        return exists;
    }

    // Helper method to check if a table exists
    private boolean tableExists(Statement statement, String tableName) throws SQLException {
        String query = "SELECT 1 FROM SYS.SYSTABLES WHERE TABLENAME = '" + tableName.toUpperCase() + "'";
        try (ResultSet rs = statement.executeQuery(query)) {
            return rs.next();  // Return true if table exists
        }
    }
}
