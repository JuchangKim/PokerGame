package Poker_Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerGame extends FileManager {
    private GameState gameState;
    private Deck deck;
    private Scanner scanner;
    private BettingSystem bettingSystem;
    private PokerCLI pokerCli;
   
    
    public PokerGame() {
        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        gameState = new GameState(players, communityCards, 0, 0, 0);
        deck = new Deck();
        bettingSystem = new BettingSystem();
        scanner = new Scanner(System.in);
        
    }

    public void addPlayer(String name, int chips) {
        getGameState().getPlayers().add(new Player(name, chips));
    }

    public void startGame() throws InterruptedException {
       
        while (true) {
            for (Player player : getGameState().getPlayers()) {
           player.setIsInGame(true);
           player.setFolded(false);
           player.setCurrentBet(0);
        }
            
            playRound();
            Thread.sleep(1000); // Introduce delay
            
            while (true) {
            System.out.println("Do you want to play another game? (yes/no)");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                break; // Continue to the next game
            } else if (response.equalsIgnoreCase("no")) {
                saveRecord(getGameState().getPlayers().get(0).getName(), getGameState().getPlayers().get(0).getChips());
                return; // Exit the method, ending the game
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        }
    }

    private void playRound() throws InterruptedException {
        GameStateAction initializeState = new InitializeState();
        initializeState.play(this);
        
        if (onePlayerIsInGame()) {
            return;
        }
        
        GameStateAction flopState = new DealFlopState();
        flopState.play(this);
        playBettingRound("The Flop");
        
        if (onePlayerIsInGame()) {
            return;
        }
        
        GameStateAction turnState = new DealTurnState();
        turnState.play(this);
        playBettingRound("The Turn");
        
        if (onePlayerIsInGame()) {
            return;
        }
        
        GameStateAction riverState = new DealRiverState();
        riverState.play(this);
        playBettingRound("The River");

        GameStateAction determineWinnerState = new DetermineWinnerState();
        determineWinnerState.play(this);
    }
    private boolean onePlayerIsInGame(){
        int playersInGame = 0;
        Player lastPlayer = null;

        for (Player player : getGameState().getPlayers()) {
            if (player.getIsInGame()) {
                playersInGame++;
                lastPlayer = player;
            }
        }

        if (playersInGame == 1 && lastPlayer != null) {
            System.out.println("All other players have folded. " + lastPlayer.getName() + " is the winner!\n");
            lastPlayer.addToChips(getBettingSystem().getPot());
            getBettingSystem().resetPot();

            for (Player p : getGameState().getPlayers()) {
                System.out.println(p.getName() + " has " + p.getChips() + " chips \n");
            }

            return true; // Indicate that the round should end
        }

    return false; // Continue the round if more than one player is still in the game
    }
    private void playBettingRound(String roundName) throws InterruptedException {
        System.out.println(roundName + " Round\n");
        System.out.println("Community Cards: " + getGameState().getCommunityCards() + "\n");

        for (Player player : getGameState().getPlayers()) {
            if (player.getIsInGame()) {
                Thread.sleep(1000); // Introduce delay
                playerTurn(player);
            }
        }
    }

    public void playerTurn(Player player) throws InterruptedException {
        if (player.getName().equals("Computer 1") || player.getName().equals("Computer 2") ||
                player.getName().equals("Computer 3") || player.getName().equals("Computer 4")) {
            computerTurn(player);
        } else {
            userTurn(player);
        }
    }

    private void userTurn(Player player) throws InterruptedException {
        System.out.println("Your turn. Your hand: " + player.getHand()+ "\n");
        System.out.println("Current Pot: " + getBettingSystem().getPot() + ", Current Bet: " + getGameState().getCurrentBet() + "\n");
        System.out.println("Options: 1. Call 2. Fold 3. Raise 4. Check 5. Exit Game \n");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                int callAmount = getGameState().getCurrentBet() - player.getCurrentBet();
                if (player.getChips() >= callAmount) {
                    player.call(callAmount);
                    getBettingSystem().addToPot(callAmount);
                    player.setCurrentBet(getGameState().getCurrentBet());
                } else {
                    System.out.println("Insufficient chips to call!");
                }
                break;
            case "2":
                player.fold();
                break;
            case "3":
                int raiseAmount = getGameState().getCurrentBet() * 2;
                if (player.getChips() >= raiseAmount) {
                    int increaseAmount = raiseAmount - getGameState().getCurrentBet();
                    player.raise(increaseAmount);
                    getBettingSystem().addToPot(increaseAmount);
                    getGameState().setCurrentBet(raiseAmount);
                    player.setCurrentBet(getGameState().getCurrentBet());
                } else {
                    System.out.println("Insufficient chips to raise!");
                }
                break;
            case "4":
                if (getGameState().getCurrentBet() > player.getCurrentBet()) {
                    System.out.println("You need to call before you can check.");
                    userTurn(player);
                }
                break;
            case "5":
                saveRecord(getGameState().getPlayers().get(0).getName(), getGameState().getPlayers().get(0).getChips());
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
                int callAmount = getGameState().getCurrentBet() - player.getCurrentBet();
                if (player.getChips() >= callAmount) {
                    player.call(callAmount);
                    getBettingSystem().addToPot(callAmount);
                    player.setCurrentBet(getGameState().getCurrentBet());
                } else {
                    player.fold();
                }
                break;
            case 2:
                player.fold();
                break;
            case 3:
                int raiseAmount = getGameState().getCurrentBet() * 2;
                if (player.getChips() >= raiseAmount) {
                    int increaseAmount = raiseAmount - getGameState().getCurrentBet();
                    player.raise(increaseAmount);
                    getBettingSystem().addToPot(increaseAmount);
                    getGameState().setCurrentBet(raiseAmount);
                    player.setCurrentBet(getGameState().getCurrentBet());
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

    /**
     * @param bettingSystem the bettingSystem to set
     */
    public void setBettingSystem(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}