/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
import java.util.Scanner;

public class PokerCLI {
    private PokerGame game;
    private Scanner scanner;

    public PokerCLI() {
        game = new PokerGame();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Poker!");
        setupPlayers();
        game.startGame();
    }

    private void setupPlayers() {
        System.out.print("Enter the number of players: ");
        int numPlayers = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            game.addPlayer(name, 1000); // Default chips for each player
        }
    }
}
