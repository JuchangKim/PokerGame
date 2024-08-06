package Poker_Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerGame{
    private GameState gameState;
    private Deck deck;
    private Scanner scanner;
    private BettingSystem bettingSystem;

    public PokerGame() {
        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        gameState = new GameState(players, communityCards, 0, 0, 0);
        deck = new Deck();
        bettingSystem = new BettingSystem();
        gameState = new GameState(players, communityCards, 0, 0, 0);
        scanner = new Scanner(System.in);
    }

    public void addPlayer(String name, int chips) {
        gameState.getPlayers().add(new Player(name, chips));
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
        GameStateAction initializeState = new InitializeState();
        initializeState.play(this);
        
        GameStateAction flopState = new DealFlopState();
        flopState.play(this);
        playBettingRound("The Flop");

        GameStateAction turnState = new DealTurnState();
        turnState.play(this);
        playBettingRound("The Turn");

        GameStateAction riverState = new DealRiverState();
        riverState.play(this);
        playBettingRound("The River");

        GameStateAction determineWinnerState = new DetermineWinnerState();
        determineWinnerState.play(this);
    }

    private void playBettingRound(String roundName) throws InterruptedException {
        System.out.println(roundName + " Round");
        System.out.println("Community Cards: " + gameState.getCommunityCards());

        for (Player player : gameState.getPlayers()) {
            if (player.getIsInGame()) {
                Thread.sleep(1000); // Introduce delay
                playerTurn(player);
            }
        }
    }

    public void playerTurn(Player player) throws InterruptedException {
        if (player.getName().equals("User")) {
            userTurn(player);
        } else {
            computerTurn(player);
        }
    }

    private void userTurn(Player player) throws InterruptedException {
        System.out.println("Your turn. Your hand: " + player.getHand());
        System.out.println("Current Pot: " + gameState.getPot() + ", Current Bet: " + gameState.getCurrentBet());
        System.out.println("Options: 1. Call 2. Fold 3. Raise 4. Check 5. Exit Game");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                int callAmount = gameState.getCurrentBet() - player.getCurrentBet();
                if (player.getChips() >= callAmount) {
                    player.reduceFromChips(callAmount);
                    bettingSystem.addToPot(callAmount);
                    player.setCurrentBet(gameState.getCurrentBet());
                } else {
                    System.out.println("Insufficient chips to call!");
                }
                break;
            case 2:
                player.fold();
                break;
            case 3:
                int raiseAmount = gameState.getCurrentBet() * 2;
                if (player.getChips() >= raiseAmount) {
                    int increaseAmount = raiseAmount - gameState.getCurrentBet();
                    player.reduceFromChips(increaseAmount);
                    bettingSystem.addToPot(increaseAmount);
                    gameState.setCurrentBet(raiseAmount);
                    player.setCurrentBet(gameState.getCurrentBet());
                } else {
                    System.out.println("Insufficient chips to raise!");
                }
                break;
            case 4:
                if (gameState.getCurrentBet() > player.getCurrentBet()) {
                    System.out.println("You need to call before you can check.");
                    userTurn(player);
                }
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Try again.");
                userTurn(player);
                break;
        }
    }

    private void computerTurn(Player player) throws InterruptedException {
        int choice = (int) (Math.random() * 3) + 1;
        switch (choice) {
            case 1:
                int callAmount = gameState.getCurrentBet() - player.getCurrentBet();
                if (player.getChips() >= callAmount) {
                    player.reduceFromChips(callAmount);
                    bettingSystem.addToPot(callAmount);
                    player.setCurrentBet(gameState.getCurrentBet());
                } else {
                    player.fold();
                }
                break;
            case 2:
                player.fold();
                break;
            case 3:
                int raiseAmount = gameState.getCurrentBet() * 2;
                if (player.getChips() >= raiseAmount) {
                    int increaseAmount = raiseAmount - gameState.getCurrentBet();
                    player.reduceFromChips(increaseAmount);
                    bettingSystem.addToPot(increaseAmount);
                    gameState.setCurrentBet(raiseAmount);
                    player.setCurrentBet(gameState.getCurrentBet());
                } else {
                    player.fold();
                }
                break;
        }
        System.out.println(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"));
    }

    public GameState getGameState() {
        return gameState;
    }

    public Deck getDeck() {
        return deck;
    }

    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }
}
