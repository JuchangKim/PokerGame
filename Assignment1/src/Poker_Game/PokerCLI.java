package Poker_Game;

import java.util.Scanner;

public class PokerCLI {
    private PokerGame game;
    private Scanner scanner;
    private FileManager fileManager;

    public PokerCLI() {
        game = new PokerGame();
        scanner = new Scanner(System.in);
    }

    public void start() throws InterruptedException {
        System.out.println("Welcome to Poker!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        fileManager = new FileManager();
        
        // Check if the username exists in the file and load the corresponding balance
        GameState gameState = fileManager.loadGameState(username);
        if (gameState != null) {
            Player mainPlayer = gameState.getPlayers().get(0);
            System.out.println("Welcome back, " + mainPlayer.getName() + "! Your current balance is: " + mainPlayer.getChips());
            game = new PokerGame();
        } else {
            System.out.println("Username not found. Starting a new account.");
            game.addPlayer(username, 1000); // Start with a default balance of 1000
        }

        ComputerSetupPlayers();
        game.startGame();
    }

    private void userSetupPlayers(String name, int balance) throws InterruptedException {
        game.addPlayer(name, balance); // Add user with the provided balance
    }

    private void ComputerSetupPlayers() throws InterruptedException {
        for (int i = 1; i < 5; i++) {
            Thread.sleep(1000);
            game.addPlayer("Computer" + " " + i, 1000); // Add computer players
        }
    }

    // Save the user's data when the game ends
    
}
