package Poker_Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerGame {
    private List<Player> players;
    private Deck deck;
    private List<Card> communityCards;
    private int pot;
    private int currentBet;
    private Scanner scanner;

    public PokerGame() {
        players = new ArrayList<>();
        deck = new Deck();
        communityCards = new ArrayList<>();
        pot = 0;
        currentBet = 0;
        scanner = new Scanner(System.in);
    }

    public void addPlayer(String name, int chips) {
        players.add(new Player(name, chips));
    }

    public void startGame() {
        // Ensure we have 5 players
        if (players.size() < 5) {
            for (int i = players.size(); i < 5; i++) {
                addPlayer("Computer" + i, 1000);
            }
        }

        while (true) {
            playRound();
            System.out.println("Do you want to play another game? (yes/no)");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private void playRound() {
        deck.reset();
        communityCards.clear();
        pot = 0;
        currentBet = 0;

        for (Player player : players) {
            player.clearHand();
            player.addCardToHand(deck.getCard());
            player.addCardToHand(deck.getCard());
        }

        System.out.println("Starting new round...");
        for (Player player : players) {
            System.out.println(player);
        }

        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.getCard());
        }
        playBettingRound("The Flop");

        communityCards.add(deck.getCard());
        playBettingRound("The Turn");

        communityCards.add(deck.getCard());
        playBettingRound("The River");

        determineWinner();
    }

    private void playBettingRound(String roundName) {
        System.out.println(roundName + " Round");
        System.out.println("Community Cards: " + communityCards);

        for (Player player : players) {
            if (player.getIsInGame()) {
                playerTurn(player);
            }
        }
    }

    private void playerTurn(Player player) {
        if (player.getName().equals("User")) {
            userTurn(player);
        } else {
            ComputerTurn(player);
        }
    }

    private void userTurn(Player player) {
        System.out.println("Your turn. Your hand: " + player.getHand());
        System.out.println("Current Pot: " + pot + ", Current Bet: " + currentBet);
        System.out.println("Options: 1. Call 2. Fold 3. Raise 4. Check");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1: // Call
                player.reduceFromChips(currentBet);
                pot += currentBet;
                break;
            case 2: // Fold
                player.fold();
                break;
            case 3: // Raise
                int raiseAmount = currentBet * 2;
                player.reduceFromChips(raiseAmount);
                pot += raiseAmount;
                currentBet = raiseAmount;
                break;
            case 4: // Check
                // No action needed
                break;
        }
    }

    private void ComputerTurn(Player player) {
        // Simple AI logic: Randomly call, fold, or raise
        int choice = (int) (Math.random() * 3) + 1;
        switch (choice) {
            case 1: // Call
                player.reduceFromChips(currentBet);
                pot += currentBet;
                break;
            case 2: // Fold
                player.fold();
                break;
            case 3: // Raise
                int raiseAmount = currentBet * 2;
                player.reduceFromChips(raiseAmount);
                pot += raiseAmount;
                currentBet = raiseAmount;
                break;
        }
        System.out.println(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"));
    }

    private void determineWinner() {
        System.out.println("Determining winner...");
        // Implement winner determination logic here
        System.out.println(PokerRules.determineWinner(players, communityCards));
    }
}
