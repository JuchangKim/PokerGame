package Poker_Game;

import java.util.Scanner;

public class PokerCLI {
    private PokerGame game;
    private Scanner scanner;

    public PokerCLI() {
        game = new PokerGame();
        scanner = new Scanner(System.in);
    }

    public void start() throws InterruptedException {
        System.out.println("Welcome to Poker!");
        setupPlayers();
        game.startGame();
    }

    private void setupPlayers() throws InterruptedException {
        
        game.addPlayer("User", 1000); // Add user with 1000 chips
        for (int i = 1; i < 5; i++) {
            Thread.sleep(1000);
            game.addPlayer("Computer" + " " + i, 1000); // Add computer players
        }
    }
}
