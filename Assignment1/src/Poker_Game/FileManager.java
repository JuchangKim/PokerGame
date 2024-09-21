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
import java.io.*;
import java.util.*;

public class FileManager {

    //Directory where saved games will be stored 
    private static final String SAVE_DIRECTORY = "saved_games/";

    // Static block to ensure the save directory exists when the class is loaded.
    static {
        // Ensure the save directory exists
        new File(SAVE_DIRECTORY).mkdirs();
    }

    //Saves the current game state to a file with the specified name.
    public static void saveGameState(GameState gameState, String fileName) {
        String filePath = SAVE_DIRECTORY + fileName + ".txt";
        try ( PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Iterate over all players and save their relevant information
            for (Player player : gameState.getPlayers()) {

                writer.println("Player Name: " + player.getName()); //Save the player's name
                writer.println("Player Balance: " + player.getChips()); // Save the player's current chip balance.
                writer.println("Number of Wins: " + player.getNumOfWin()); // Save the player's number of wins.
                writer.println("Current Bet: " + gameState.getCurrentBet()); // Save the current bet amount.
                writer.println(""); // Add an empty line for separation between players.
            }
            System.out.println("Game saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    //Loads a game state from the specified file.
    public static GameState loadGameState(String fileName) {
        String filePath = SAVE_DIRECTORY + fileName + ".txt";
          File file = new File(filePath);
        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Player> players = new ArrayList<>();
            List<Card> communityCards = new ArrayList<>();
            int pot = 0, currentBet = 0;
            Player winner = null;

            String line;
            Player currentPlayer = null;

        if (file.exists() && file.length() == 0) {
        System.out.println("There is no user record so create new record.");

        // Delete the empty file
        if (file.delete()) {
            System.out.println("Empty file deleted: " + filePath);

            // Create a new save file and log file
            if (createNewSaveFile(fileName)) {
                System.out.println("New user record created: " + filePath);
            } else {
                System.err.println("Failed to create new user record.");
            } 
        } else {
            System.err.println("Failed to delete empty file: " + filePath);
        }
        return null; // Return null since there's no game state to load
    }
            
            // Read the file line by line and extract the game state information
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player Name: ")) {
                    String playerName = line.substring("Player Name: ".length());
                    currentPlayer = new Player(playerName, 0); // Create a new player with a starting balance of 0
                    players.add(currentPlayer); 
                } else if (line.startsWith("Player Balance: ")) {
                    int balance = Integer.parseInt(line.substring("Player Balance: ".length()));
                    if (currentPlayer != null) {
                        currentPlayer.addToChips(balance); // Set balance of the main player, i'm not sure if this is meant to be getChps()
                    }
                } else if (line.startsWith("Community Cards: ")) {
                    // Logic to parse and add community cards
                } else if (line.startsWith("Pot: ")) {
                    pot = Integer.parseInt(line.substring("Pot: ".length())); // Set the pot value.
                } else if (line.startsWith("Current Bet: ")) {
                    currentBet = Integer.parseInt(line.substring("Current Bet: ".length())); // Set the current bet.
                } else if (line.startsWith("Number Of Wins: ")) {
                    int num = Integer.parseInt(line.substring("Number Of Wins: ".length()));
                    currentPlayer.setNumOfWin(num); // Set the player's number of wins.
                }
            }
            
            // Create a new GameState object with the loaded values
            GameState gameState = new GameState(players, communityCards, pot, currentBet);
            if (winner != null) {
                gameState.setWinner(winner);
            }

            System.out.println("Game loaded successfully from " + filePath);
            return gameState;
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    //Retrieves the names of all saved game files in the save directory
    public static List<String> getSavedGameFiles() {
        File directory = new File(SAVE_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
        
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().replace(".txt", "");
                if (!fileName.endsWith("_log")) { // Filter out filenames ending with "_log"
                    fileNames.add(fileName);
                }
            }
        }
        //If there is no user, printing "There is no user."
        if(fileNames.isEmpty()) 
        {
            System.out.println("There is no user");
        }
        return fileNames;
    }

    //Creates a new save file with the specified name
    public static boolean createNewSaveFile(String fileName) {
        File file = new File(SAVE_DIRECTORY + fileName + ".txt");
        String filePath = SAVE_DIRECTORY + fileName + "_log.txt";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
            System.out.println("Log is created " + filePath);
            return file.createNewFile(); // Create the main save file.
        } catch (IOException e) {
            System.err.println("Error creating new save file: " + e.getMessage());
            return false;
        }
    }

    //Appends a log entry to the specified log file.
    public static void appendToGameLog(String fileName, String logEntry) {
        String filePath = SAVE_DIRECTORY + fileName + "_log.txt";
        //This Try Catch method has been generated from ChatGPT
        try ( PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) { // 'true' for appending
            writer.println(logEntry); // Add the log entry.
            System.out.println("Log entry added to " + filePath);
        } catch (IOException e) {
            System.err.println("Error appending to log file: " + e.getMessage());
        }
    }

    //Reads all log entries from the specified log file
    public static List<String> readGameLog(String fileName) {
        String filePath = SAVE_DIRECTORY + fileName + "_log.txt";
        List<String> logEntries = new ArrayList<>();
        //This Try Catch method has been generated from ChatGPT
        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line); // Add each line to the list of log entries
            }
            //If there is no record. printing "There is no record."
            if(logEntries.isEmpty())
            {
                System.out.println("There is no record.");
            }
            System.out.println("Log entries read from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
        return logEntries;
    }

}
