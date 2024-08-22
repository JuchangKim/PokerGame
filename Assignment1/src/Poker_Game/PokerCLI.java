package Poker_Game;

import java.util.Scanner;

public class PokerCLI {
    private PokerGame game;
    private Scanner scanner;
    private FileManager fileManager;
    private GameState gameState;

    public PokerCLI() {
        game = new PokerGame();
        scanner = new Scanner(System.in);
        fileManager = new FileManager();
        
    }

    public void start() throws InterruptedException {
       Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Poker Game!");
        
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        
        GameState record = fileManager.loadGameState(username);
            if (record != null) {
                System.out.println("Welcome back, " + record.getPlayers().get(0).getName() + "!");
                game.addPlayer(record.getPlayers().get(0).getName(), record.getPlayers().get(0).getChips());
              
                game.setGameState(record);
                ComputerSetupPlayers();
            } else {
                System.out.println("Username not found. Starting a new game.");
                userSetupPlayers(username, 1000);
                ComputerSetupPlayers();
            }
        game.startGame(username);
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
