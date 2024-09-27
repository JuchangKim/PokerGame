/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 *
 * import java.io.*; import java.util.List;
 *
 * public class FileManager { public static void saveGameState(GameState
 * gameState, String filePath) { try (ObjectOutputStream oos = new
 * ObjectOutputStream(new FileOutputStream(filePath))) {
 * oos.writeObject(gameState); } catch (IOException e) { e.printStackTrace(); }
 * }
 *
 * public static GameState loadGameState(String filePath) { try
 * (ObjectInputStream ois = new ObjectInputStream(new
 * FileInputStream(filePath))) { return (GameState) ois.readObject(); } catch
 * (IOException | ClassNotFoundException e) { e.printStackTrace(); return null;
 * } } }
 */
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    public static void saveGame(PokerGame game, String userName) {
    try (Connection conn = DBManager.getConnection();
         Statement statement = conn.createStatement()) {

        // Ensure user exists or update existing user's chips
        String userUpsertQuery = "MERGE INTO USER AS u USING " +
                                 "(VALUES ('" + userName + "', " + game.getGameState().getPlayers().get(0).getChips() + ")) " +
                                 "AS vals(USERNAME, CHIPS) ON u.USERNAME = vals.USERNAME " +
                                 "WHEN MATCHED THEN UPDATE SET u.CHIPS = vals.CHIPS " +
                                 "WHEN NOT MATCHED THEN INSERT (USERNAME, CHIPS) VALUES (vals.USERNAME, vals.CHIPS)";
        statement.executeUpdate(userUpsertQuery);

        // Retrieve or get the generated user ID
        int userId = 0;
        ResultSet rs = statement.executeQuery("SELECT ID FROM USER WHERE USERNAME = '" + userName + "'");
        if (rs.next()) {
            userId = rs.getInt("ID");
        }

        // Insert game state for each player
        for (Player player : game.getGameState().getPlayers()) {
            if(player != game.getGameState().getPlayers().get(0)) {
            String gameInsertQuery = "INSERT INTO GAME (USER_ID, USER_CHIPS, COMPUTER_PLAYER_NAME, COMPUTER_CHIPS) VALUES (" +
                                     userId + ", '" + game.getGameState().getPlayers().get(0) + "', " +
                                     player.getName().replace(" ", "_") + "', " +
                                     player.getChips() + ", '" +
                                      "')";
            statement.executeUpdate(gameInsertQuery);
            }
        }

        System.out.println("Game and player states saved successfully.");
    } catch (SQLException e) {
        System.err.println("Error saving game and player states: " + e.getMessage());
    }
}

 // This method will load the game state from the database for a given user.
    public static PokerGame loadGame(String userName) {
        GameState gameState = null;
        PokerGame game = new PokerGame();
        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        int pot = 0, currentBet = 0;

        String query = "SELECT * FROM GAME WHERE USER_ID = (SELECT ID FROM USER WHERE USERNAME = '" + userName + "')";

        try (Connection conn = DBManager.getConnection(); 
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if (rs.next()) {
                Player userPlayer = new Player(userName, rs.getInt("USER_CHIPS"));
                Player computerPlayer = new Player(rs.getString("COMPUTER_PLAYER_NAME"), rs.getInt("COMPUTER_CHIPS"));
                players.add(userPlayer);
                players.add(computerPlayer);
                currentBet = rs.getInt("GAME_INFO");

                // You can add logic for community cards if needed
            }

            gameState = new GameState(players, communityCards, pot, currentBet);
            game.setGameState(gameState);
            System.out.println("Game loaded successfully from the database.");

        } catch (SQLException e) {
            System.err.println("Error loading game from database: " + e.getMessage());
        }
        return game;
    }
                
    // This method retrieves a list of saved users (by username) from the USER table.
    public static List<String> getSavedGameFiles() {
        List<String> savedGames = new ArrayList<>();
        String query = "SELECT USERNAME FROM USER";

        try (Connection conn = DBManager.getConnection(); 
             Statement statement = conn.createStatement(); 
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                savedGames.add(rs.getString("USERNAME"));
            }

            if (savedGames.isEmpty()) {
                System.out.println("There are no saved games.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving saved games: " + e.getMessage());
        }

        return savedGames;
    }
    
     // This method creates a new user save file in the database.
    public static boolean createNewSaveFile(String userName) {
        String query = "INSERT INTO USER (USERNAME, CHIPS, NUMBER_OF_WINS) VALUES ('" + userName + "', 1000, 0)";

        try (Connection conn = DBManager.getConnection(); 
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(query);
            System.out.println("New save file created for user: " + userName);
            return true;

        } catch (SQLException e) {
            System.err.println("Error creating new save file: " + e.getMessage());
            return false;
        }
    }
    
    /// This method inserts a log entry into the GameLog table.
    public static void appendToGameLog(String userName, String logEntry) {
        String query = "INSERT INTO GAMELOG (USER_ID, COMPUTER_PLAYER_NAME) VALUES " +
                "((SELECT ID FROM USER WHERE USERNAME = '" + userName + "'), '" + logEntry + "')";

        try (Connection conn = DBManager.getConnection(); 
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(query);
            System.out.println("Log entry added for user: " + userName);

        } catch (SQLException e) {
            System.err.println("Error appending to game log: " + e.getMessage());
        }
    }

   // Reads all log entries from the GAMELOG table in the database for a specific user.
public static List<String> readGameLog(String userName) {
    List<String> logEntries = new ArrayList<>();
    String query = "SELECT COMPUTER_PLAYER_NAME FROM GAMELOG WHERE USER_ID = (SELECT ID FROM USER WHERE USERNAME = '" + userName + "')";

    try (Connection conn = DBManager.getConnection(); 
         Statement statement = conn.createStatement();
         ResultSet rs = statement.executeQuery(query)) {

        while (rs.next()) {
            logEntries.add(rs.getString("COMPUTER_PLAYER_NAME")); // Assuming the log entries are stored in the COMPUTER_PLAYER_NAME column
        }

        if (logEntries.isEmpty()) {
            System.out.println("There are no log entries for user: " + userName);
        } else {
            System.out.println("Log entries read from the database for user: " + userName);
        }
        
    } catch (SQLException e) {
        System.err.println("Error reading log entries from the database: " + e.getMessage());
    }

    return logEntries;
}

}
