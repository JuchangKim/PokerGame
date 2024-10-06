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
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileManager {
   
    public static void saveGame(PokerGame game, String userName) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getConnection();
            statement = conn.createStatement();

            // Check if user exists
            String checkUserQuery = "SELECT ID FROM \"USER\" WHERE USERNAME = '" + userName + "'";
            rs = statement.executeQuery(checkUserQuery);
            int userId = 0;

            if (rs.next()) {
                // User exists, update their chips
                userId = rs.getInt("ID");
                String updateUserQuery = "UPDATE \"USER\" SET CHIPS = " + game.getGameState().getPlayers().get(0).getChips() + " WHERE ID = " + userId;
                statement.executeUpdate(updateUserQuery);
            } else {
                // User does not exist, insert new user
                String insertUserQuery = "INSERT INTO \"USER\" (USERNAME, CHIPS) VALUES ('" + userName + "', " + game.getGameState().getPlayers().get(0).getChips() + ")";
                statement.executeUpdate(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }

            // Insert or update game state for each computer player
            for (Player player : game.getGameState().getPlayers()) {
                if (player != game.getGameState().getPlayers().get(0)) { // Skip the user player to avoid duplicate handling
                    // Check if computer player data already exists
                    String checkGameQuery = "SELECT COUNT(*) FROM \"GAME\" WHERE USER_ID = " + userId
                            + " AND COMPUTER_PLAYER_NAME = '" + player.getName() + "'";
                    try ( ResultSet gameRs = statement.executeQuery(checkGameQuery)) {
                        int count = 0;
                        if (gameRs.next()) {
                            count = gameRs.getInt(1);
                        }

                        if (count > 0) {
                            // Computer player exists, perform an update
                            String updateGameQuery = "UPDATE \"GAME\" SET USER_CHIPS = " + game.getGameState().getPlayers().get(0).getChips()
                                    + ", COMPUTER_CHIPS = " + player.getChips()
                                    + " WHERE USER_ID = " + userId
                                    + " AND COMPUTER_PLAYER_NAME = '" + player.getName() + "'";
                            statement.executeUpdate(updateGameQuery);
                        } else {
                            // Computer player does not exist, insert new record
                            String gameInsertQuery = "INSERT INTO \"GAME\" (USER_ID, USER_CHIPS, COMPUTER_PLAYER_NAME, COMPUTER_CHIPS) VALUES ("
                                    + userId + ", " + game.getGameState().getPlayers().get(0).getChips() + ", '"
                                    + player.getName() + "', " + player.getChips() + ")";
                            statement.executeUpdate(gameInsertQuery);
                        }
                    }
                }
            }

            // Append to GameLog after saving game states
            String logEntry = "Game saved for user: " + userName;
            String winner = game.getGameState().getWinner() != null ? game.getGameState().getWinner().getName() : "None";
            int chips = game.getGameState().getPlayers().get(0).getChips(); // Assuming the first player is the user

            // Call the appendToGameLog method to log the game state
            //FileManager.appendToGameLog(userName, logEntry, winner, chips);
            System.out.println("Game and player states saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving game and player states: " + e.getMessage());
        } finally {
            // Clean up resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // This method will load the game state from the database for a given user
        public static PokerGame loadGame(String userName) {
        GameState gameState = null;
        PokerGame game = new PokerGame();
        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        int pot = 0, currentBet = 0;

        // Adjusted query to exclude 'COMPUTER_PLAYER_NAME'
        String query = "SELECT g.USER_CHIPS, g.COMPUTER_PLAYER_NAME, g.COMPUTER_CHIPS, u.USERNAME "
                + "FROM POKER.GAME g "
                + "JOIN POKER.\"USER\" u ON g.USER_ID = u.ID "
                + "WHERE u.USERNAME = '" + userName + "'";

        try ( Connection conn = DBManager.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery(query)) {

            // Read all results and build player list
            boolean userAdded = false;
            while (rs.next()) {
                if (!userAdded) {
                    players.add(new Player(rs.getString("USERNAME"), rs.getInt("USER_CHIPS"))); // Add user first
                    userAdded = true;
                }
                // Assuming the computer player is still fetched from COMPUTER_CHIPS (assuming a fixed number of computers)
                players.add(new Player(rs.getString("COMPUTER_PLAYER_NAME"), rs.getInt("COMPUTER_CHIPS"))); // Add computer players
            }

            gameState = new GameState(players, communityCards, pot, currentBet);
            game.setGameState(gameState);
            System.out.println("Game loaded successfully from the database.");
            return game;

        } catch (SQLException e) {
            System.err.println("Error loading game from database: " + e.getMessage());
        }
        return null;
    }

    // This method retrieves a list of saved users (by username) from the USER table.
    // Use a subquery to select only the most recent user record
        public static Set<String> getSavedGameFiles() {
        Set<String> savedGames = new HashSet<>();

        // Simple query to get the most recent usernames without using PARTITION or ROW_NUMBER
        String query = "SELECT USERNAME FROM \"POKER\".\"USER\" ORDER BY CREATED_AT DESC";

        try ( Connection conn = DBManager.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery(query)) {

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
        Set<String> savedGames = getSavedGameFiles();  // Load existing usernames

        // Check if the username is already in the Set (which means it's already in the DB)
        if (savedGames.contains(userName)) {
            System.out.println("User already exists: " + userName);
            return false;
        }

        String query = "INSERT INTO \"POKER\".\"USER\" (\"USERNAME\", \"CHIPS\", \"NUMBER_OF_WINS\") VALUES ('" + userName + "', 1000, 0)";

        try ( Connection conn = DBManager.getConnection();  Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("New save file created for user: " + userName);
            return true;

        } catch (SQLException e) {
            System.err.println("Error creating new save file: " + e.getMessage());
            return false;
        }
    }

    /// This method inserts a log entry into the GameLog table.
    public static void appendToGameLog(String userName, String playerCardRankPerRound, String winnerOfRound, String winningHand) {
        String query = "INSERT INTO GAMELOG (USER_ID, USERNAME_NAME, PLAYER_CARD_RANK_PER_ROUND, WINNER_OF_ROUND, WINNING_HAND) VALUES "
                + "((SELECT ID FROM \"USER\" WHERE USERNAME = ? FETCH FIRST ROW ONLY), ?, ?, ?, ?)";

        try ( Connection conn = DBManager.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userName);  // Use the username for subquery
            pstmt.setString(2, userName);  // For USERNAME_NAME column
            pstmt.setString(3, playerCardRankPerRound);  // For PLAYER_CARD_RANK_PER_ROUND column
            pstmt.setString(4, winnerOfRound);  // For WINNER_OF_ROUND column
            pstmt.setString(5, winningHand);  // For WINNING_HAND column

            pstmt.executeUpdate();
            System.out.println("Log entry added for user: " + userName);

        } catch (SQLException e) {
            System.err.println("Error appending to game log: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void updateUserWins(String username, int totalWins) {
        String query = "UPDATE \"USER\" SET NUMBER_OF_WINS = ? WHERE USERNAME = ?";

        try ( Connection conn = DBManager.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, totalWins);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully updated wins for user: " + username);
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error updating wins: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int countTotalWins(String playerName) {
        String query = "SELECT COUNT(*) AS WIN_COUNT FROM GAMELOG WHERE WINNER_OF_ROUND = ?";
        int winCount = 0;

        try ( Connection conn = DBManager.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, playerName);

            try ( ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    winCount = rs.getInt("WIN_COUNT");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error counting total wins: " + e.getMessage());
            e.printStackTrace();
        }

        return winCount;
    }

    // Reads all log entries from the GAMELOG table in the database for a specific user.
    public static List<String> readGameLog(String userName) {
        List<String> logEntries = new ArrayList<>();

        // Modified query to retrieve all necessary fields from GAMELOG and USER tables
        String query = "SELECT g.USERNAME_NAME, g.WINNER_OF_ROUND, g.WINNING_HAND, g.PLAYER_CARD_RANK_PER_ROUND, g.LOG_DATE "
                + "FROM GAMELOG g "
                + "JOIN \"USER\" u ON g.USER_ID = u.ID "
                + "WHERE u.USERNAME = '" + userName + "'";

        try ( Connection conn = DBManager.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery(query)) {

            // Loop through the result set and build the log entries
            while (rs.next()) {
                StringBuilder logEntry = new StringBuilder();

                // Append log date
                logEntry.append("Log Date: ").append(rs.getTimestamp("LOG_DATE")).append("\n");

                // Append player username
                logEntry.append("Username: ").append(rs.getString("USERNAME_NAME")).append("\n");

                // Append winner of the round
                logEntry.append("Winner: ").append(rs.getString("WINNER_OF_ROUND")).append("\n");

                // Append winning hand
                logEntry.append("Winning Hand: ").append(rs.getString("WINNING_HAND")).append("\n");

                // Append player's card rank per round
                logEntry.append("Player Card Rank: ").append(rs.getString("PLAYER_CARD_RANK_PER_ROUND")).append("\n");

                logEntry.append("\n"); // Add a new line between entries
                logEntries.add(logEntry.toString()); // Add each log entry to the list
            }

            if (logEntries.isEmpty()) {
                System.out.println("There are no log entries for user: " + userName);
            } else {
                System.out.println("Log entries read from the database for user: " + userName + "\n");
            }

        } catch (SQLException e) {
            System.err.println("Error reading log entries from the database: " + e.getMessage());
        }

        return logEntries;
    }
}
