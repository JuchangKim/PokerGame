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
    private BettingSystem bettingSystem;

    public PokerGame() {
    players = new ArrayList<>();
    deck = new Deck();
    communityCards = new ArrayList<>();
    bettingSystem = new BettingSystem(); // Initialize betting system
    scanner = new Scanner(System.in);
    }

    public void addPlayer(String name, int chips) {
        players.add(new Player(name, chips));
    }

    public void startGame() throws InterruptedException {
        while (true) {
            playRound();
            Thread.sleep(1000); // Introduce delay
            System.out.println("Do you want to play another game? (yes/no)");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private void playRound() throws InterruptedException {
        deck.reset();
        communityCards.clear();
        pot = 0;
        currentBet = 0;

        for (Player player : players) {
            player.clearHand();
            player.addCardToHand(deck.getCard());
            player.addCardToHand(deck.getCard());
            Thread.sleep(1000); // Introduce delay
        }

        System.out.println("Starting new round...");
        for (Player player : players) {
            System.out.println(player);
            Thread.sleep(1000);
        }

        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.getCard());
        }
        playBettingRound("The Flop");
        Thread.sleep(1000); // Introduce delay
        communityCards.add(deck.getCard());
        playBettingRound("The Turn");
        Thread.sleep(1000); // Introduce delay
        communityCards.add(deck.getCard());
        playBettingRound("The River");
        Thread.sleep(1000); // Introduce delay
        determineWinner();
    }

    private void playBettingRound(String roundName) throws InterruptedException {
        System.out.println(roundName + " Round");
        System.out.println("Community Cards: " + communityCards);

        for (Player player : players) {
            if (player.getIsInGame()) {
                Thread.sleep(1000); // Introduce delay
                playerTurn(player);
            }
        }
    }

    private void playerTurn(Player player) throws InterruptedException {
        if (player.getName().equals("User")) {
            Thread.sleep(1000); // Introduce delay
            userTurn(player);
        } else {
            computerTurn(player);
            Thread.sleep(1000); // Introduce delay
        }
    }

   private void userTurn(Player player) throws InterruptedException {
    System.out.println("Your turn. Your hand: " + player.getHand());
    System.out.println("Current Pot: " + bettingSystem.getPot() + ", Current Bet: " + currentBet);
    System.out.println("Options: 1. Call 2. Fold 3. Raise 4. Check 5. Exit Game");
    Thread.sleep(1000); // Introduce delay
    int choice = Integer.parseInt(scanner.nextLine());
    switch (choice) {
        case 1: // Call
            int callAmount = currentBet - player.getCurrentBet(); // Adjust for the amount needed to call
            if (player.getChips() >= callAmount) {
                player.reduceFromChips(callAmount);
                bettingSystem.addToPot(callAmount);
                player.setCurrentBet(currentBet); // Update the player's current bet
            } else {
                System.out.println("Insufficient chips to call!");
            }
            break;
        case 2: // Fold
            player.fold();
            break;
        case 3: // Raise
            int raiseAmount = currentBet * 2; // For simplicity, let's double the current bet
            if (player.getChips() >= raiseAmount) {
                int increaseAmount = raiseAmount - currentBet; // Amount to increase the pot by
                player.reduceFromChips(increaseAmount);
                bettingSystem.addToPot(increaseAmount);
                currentBet = raiseAmount; // Update the current bet to the new raise amount
                player.setCurrentBet(currentBet); // Update the player's current bet
            } else {
                System.out.println("Insufficient chips to raise!");
            }
            break;
        case 4: // Check
            if (currentBet > player.getCurrentBet()) {
                System.out.println("You need to call before you can check.");
                userTurn(player);
            }
            break;
        case 5: // Exit Game
            System.exit(0);
            break;
        default:
            System.out.println("Invalid option. Try again.");
            userTurn(player);
            break;
    }
}

private void computerTurn(Player player) throws InterruptedException {
    // Simple AI logic: Randomly call, fold, or raise
    int choice = (int) (Math.random() * 3) + 1;
    Thread.sleep(1000); // Introduce delay
    switch (choice) {
        case 1: // Call
            int callAmount = currentBet - player.getCurrentBet(); // Adjust for the amount needed to call
            if (player.getChips() >= callAmount) {
                player.reduceFromChips(callAmount);
                bettingSystem.addToPot(callAmount);
                player.setCurrentBet(currentBet); // Update the player's current bet
            } else {
                player.fold();
            }
            break;
        case 2: // Fold
            player.fold();
            break;
        case 3: // Raise
            int raiseAmount = currentBet * 2; // For simplicity, let's double the current bet
            if (player.getChips() >= raiseAmount) {
                int increaseAmount = raiseAmount - currentBet; // Amount to increase the pot by
                player.reduceFromChips(increaseAmount);
                bettingSystem.addToPot(increaseAmount);
                currentBet = raiseAmount; // Update the current bet to the new raise amount
                player.setCurrentBet(currentBet); // Update the player's current bet
            } else {
                player.fold();
            }
            break;
    }
    System.out.println(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"));
}
    private void determineWinner() {
    System.out.println("Determining winner...");
    Player winner = PokerRules.determineWinner(players, communityCards);
    System.out.println("The winner is " + winner.getName() + "!");
    if (winner != null) {
        winner.addToChips(bettingSystem.getPot()); // Winner takes all
        bettingSystem.clearPot(); // Clear the pot for the next game
    } else {
        System.out.println("No winner this round.");
    }
}
}
