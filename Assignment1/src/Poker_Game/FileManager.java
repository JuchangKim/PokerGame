/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 *
import java.io.*;
import java.util.List;

public class FileManager {
    public static void saveGameState(GameState gameState, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGameState(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}*/

import java.io.*;
import java.util.*;

public class FileManager {
    private static final String SAVE_DIRECTORY = "saved_games/";

    static {
        // Ensure the save directory exists
        new File(SAVE_DIRECTORY).mkdirs();
    }

    public static void saveGameState(GameState gameState, String fileName) {
        String filePath = SAVE_DIRECTORY + fileName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Assuming the first player is the main player (user)
            Player mainPlayer = gameState.getPlayers().get(0);
            writer.println("Player Name: " + mainPlayer.getName());
            writer.println("Player Balance: " + mainPlayer.getChips()); //Hi JC i'm not sure if this is meant to be get Chips?
            writer.println("Community Cards: " + gameState.getCommunityCards());
            writer.println("Pot: " + gameState.getPot());
            writer.println("Current Bet: " + gameState.getCurrentBet());
            writer.println("Current Player Index: " + gameState.getCurrentPlayerIndex());
            
            // Save winner if exists
            if (gameState.getWinner() != null) {
                writer.println("Winner: " + gameState.getWinner().getName());
            }

            System.out.println("Game saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static GameState loadGameState(String fileName) {
        String filePath = SAVE_DIRECTORY + fileName + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Player> players = new ArrayList<>();
            List<Card> communityCards = new ArrayList<>();
            int pot = 0, currentBet = 0, currentPlayerIndex = 0;
            Player winner = null;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player Name: ")) {
                    String playerName = line.substring("Player Name: ".length());
                    players.add(new Player(playerName, 0)); // Add with 0 balance for now
                } else if (line.startsWith("Player Balance: ")) {
                    int balance = Integer.parseInt(line.substring("Player Balance: ".length()));
                    players.get(0).getChips(); // Set balance of the main player, i'm not sure if this is meant to be getChps()
                } else if (line.startsWith("Community Cards: ")) {
                    // Logic to parse and add community cards
                } else if (line.startsWith("Pot: ")) {
                    pot = Integer.parseInt(line.substring("Pot: ".length()));
                } else if (line.startsWith("Current Bet: ")) {
                    currentBet = Integer.parseInt(line.substring("Current Bet: ".length()));
                } else if (line.startsWith("Current Player Index: ")) {
                    currentPlayerIndex = Integer.parseInt(line.substring("Current Player Index: ".length()));
                } else if (line.startsWith("Winner: ")) {
                    String winnerName = line.substring("Winner: ".length());
                    winner = new Player(winnerName, 0); // Winner balance can be set later
                }
            }

            GameState gameState = new GameState(players, communityCards, pot, currentBet, currentPlayerIndex);
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

    public static List<String> getSavedGameFiles() {
        File directory = new File(SAVE_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                fileNames.add(file.getName().replace(".txt", ""));
            }
        }
        return fileNames;
    }

    public static boolean createNewSaveFile(String fileName) {
        File file = new File(SAVE_DIRECTORY + fileName + ".txt");
        try {
            return file.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating new save file: " + e.getMessage());
            return false;
        }
    }
}

