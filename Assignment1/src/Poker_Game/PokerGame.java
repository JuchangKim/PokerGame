package Poker_Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//The PokerGame class manages the overall flow of a poker game. It extends the FileManager class
//for handling file operations related to game state and logs.
public class PokerGame extends FileManager {

    //these variables are all private to ensure modification is conducted in a controlled state
    private GameState gameState;         // The current state of the game, including players, community cards, etc.
    private Deck deck;                   // The deck of cards used in the game.
    private Scanner scanner;             // Scanner object for reading user input from the console.
    private BettingSystem bettingSystem; // The betting system managing the pot and bets.
    private PokerCLI pokerCli;           // A command-line interface for interacting with the game (unused in this snippet).
    private String username;             // The username of the current player (used for saving and loading game states).
    private PlayAgainMenu playagainmenu;
    private String response;
    private GameStage gameStage;
    private List<GameListener> listeners = new ArrayList<>(); // To connect the GameStage
    private String announcement;
    private String round;
    private boolean isFinished;
    
    public PokerGame() {

        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        gameState = new GameState(players, communityCards, 0, 0);
        deck = new Deck();
        bettingSystem = new BettingSystem();
        scanner = new Scanner(System.in);
        playagainmenu = new PlayAgainMenu(this);
        response = "";
        isFinished = false;
    }
    
    public interface GameStateListener {
        void onGameStateUpdated();
    }

    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    void notifyGameUpdated() {
        for (GameListener listener : listeners) {
            listener.onGameStateUpdated();
        }
    }
    
    public void resetGame() {
    // Reset the game state for a new round
    for (Player player : getGameState().getPlayers()) {
        player.setFolded(false);
        player.setIsInGame(true);
        player.setCurrentBet(0);
    }
    getBettingSystem().resetPot();  // Reset the betting pot
    getDeck().shuffleDeck();  // Shuffle the deck for the new game
    // Any other necessary resets
}
    
    //add a neew player to the game
    public void addPlayer(String name, int chips) {

        getGameState().getPlayers().add(new Player(name, chips));
    }

    //startGame() generated with the help of ChatGPT
    //Start the poker game loop, allowing players to play multiple rounds
    public void startGame(String username) throws InterruptedException {
        this.username = username;

        // Attempt to create a new save file for the game or load the existing one
        if (FileManager.createNewSaveFile(username)) {
            System.out.println("New save file created: " + username);
        } else {
            System.out.println("Save file already exists. Loading game state...");
        }
        
            // Reset each player's status at the start of a new game.
            resetGame();
            notifyGameUpdated();
            
            playRound(); //Play a round of poker
            Thread.sleep(1000); // Delay to simulate real gameplay
            setIsFinished(true);
            notifyGameUpdated();
            
                System.out.println("Do you want to play another game? (yes/no)");
                 Thread.sleep(1000);
                gameStage.dispose();

                if (getResponse().equalsIgnoreCase("yes")) {
                    // Log the results of the round to the game log
                    String log = "";
                    for (Player p : gameState.getPlayers()) {
                        if (p.isFolded()) {
                            log += p.getName() + " : Folded, Chips : " + p.getChips() + "\n";
                            this.setAnnouncement(p.getName() + " : Folded, Chips : " + p.getChips() + "\n");
                        } else {
                            log += p.getName() + " : " + p.getHand() + " : " + p.getHand().getHandRank() + " , Chips : " + p.getChips() + "\n";
                            this.setAnnouncement(p.getName() + " : " + p.getHand() + " : " + p.getHand().getHandRank() + " , Chips : " + p.getChips() + "\n");
                        }
                    }
                    log += "Winner is : " + gameState.getWinner() + "\n";
                    this.setAnnouncement("Winner is : " + gameState.getWinner() + "\n");

                    FileManager.appendToGameLog(username, log);// Append log
                    
                    
                } else if (getResponse().equalsIgnoreCase("no")) {
                    // Save the current game state and exit
                    String log = "";
                    for (Player p : gameState.getPlayers()) {
                        if (p.isFolded()) {
                            log += p.getName() + " : Folded, Chips : " + p.getChips() + "\n";
                        } else {
                            log += p.getName() + " : " + p.getHand() + " : " + p.getHand().getHandRank() + " , Chips : " + p.getChips() + "\n";
                        }
                    }
                    log += "Winner is : " + gameState.getWinner() + "\n";

                    FileManager.appendToGameLog(username, log);
                    FileManager.saveGame(this, username);// Save game state
                    // Exit the method, ending the game
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
                
    }
    //playRound() was generated with ChatGPT
    //Plays a single round of poker, including dealing cards and handling bets
    private void playRound() throws InterruptedException {
        this.setRound("Starting Game");
        GameStateAction initializeState = new InitializeState();
        initializeState.play(this); // Initialize the game state
       
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return; // If only one player is in the game, end the round
        }
        
        
       
        GameStateAction flopState = new DealFlopState();
        flopState.play(this); //Deal the flop
        playBettingRound("The Flop"); // Play the betting round for the flop
        
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return; // If only one player is in the game, end the round
        }
        notifyGameUpdated();
        
        
        GameStateAction turnState = new DealTurnState();
        turnState.play(this); //Deal the turn
        playBettingRound("The Turn"); // Play the betting round for the turn
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return; // If only one player is in the game, end the round
        }
        notifyGameUpdated();
        
        
        GameStateAction riverState = new DealRiverState();
        riverState.play(this); //Deal the river
        playBettingRound("The River"); // Play the betting round for the river
        
        notifyGameUpdated();
        
        this.setRound("Determining The Winnder");
        GameStateAction determineWinnerState = new DetermineWinnerState();
        determineWinnerState.play(this); // Determine the winner of the round
        notifyGameUpdated();
        
    }

    //Checks if only one player is still in the game in case all other players have folded
    private boolean onePlayerIsInGame() {
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
            this.setAnnouncement("All other players have folded. " + lastPlayer.getName() + " is the winner!\n");
            gameState.setWinner(lastPlayer); // Set the last remaining player as the winner.
            lastPlayer.addNumOfWin(); // Increment the player's win count.
            lastPlayer.addToChips(getBettingSystem().getPot()); // Add the pot to the player's chips.
            getBettingSystem().resetPot(); // Reset the pot for the next round.

            for (Player p : getGameState().getPlayers()) {
                System.out.println(p.getName() + " has " + p.getChips() + " chips \n");
            }

            return true; // Indicate that the round should end
        }

        
        return false; // Continue the round if more than one player is still in the game
    }

    //Plays a betting round for a specified phase of the game such as Flop, Turn, River
    private void playBettingRound(String roundName) throws InterruptedException {
        this.setRound(roundName);
        
        System.out.println(roundName + " Round\n");
        
        System.out.println("Community Cards: " + getGameState().getCommunityCards() + "\n");

        for (Player player : getGameState().getPlayers()) {
            if (player.getIsInGame()) {
                Thread.sleep(1000); // Delay for players actions
                playerTurn(player); // Handle the player's turn
            }
        }
      
        
    }

    //Handles a player's turn, either for a human player or a computer-controlled player
    public void playerTurn(Player player) throws InterruptedException {
    if (getGameState().getPlayers().get(0).getName().equals(player.getName())) {
        // Notify all listeners that it's the user's turn
        for (GameListener listener : listeners) {
            listener.onPlayerTurn(player);
        }
        userTurn(player);  // Proceed with the user's turn
    } else {
        computerTurn(player);  // Handle the computer's turn
    }
}

    
    //Handles the user's turn, presenting options to call, fold, raise, check, or exit the game
    private void userTurn(Player player) throws InterruptedException {
        System.out.println("Your turn. Your hand: " + player.getHand() + "\n");
        System.out.println("Current Pot: " + getBettingSystem().getPot() + ", Current Bet: " + getGameState().getCurrentBet() + "\n");

    // Buttons will handle the actions, no need for scanner input
       
        }
        


    //Handles a computer player's turn, making decisions based on random chance.
    private void computerTurn(Player player) throws InterruptedException {
        int choice = (int) (Math.random() * 3) + 1; //Randomly select an action
        switch (choice) {
            case 1: //Call
                int callAmount = getGameState().getCurrentBet() - player.getCurrentBet();
                if (player.getChips() >= callAmount) {
                    player.call(callAmount);
                    getBettingSystem().addToPot(callAmount);
                    player.setCurrentBet(getGameState().getCurrentBet());
                } else {
                    player.fold();
                }
                break;
            case 2: //Fold
                player.fold();
                break;
            case 3: //Raise
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
        this.setAnnouncement(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"));
        notifyGameUpdated();
        
        
    }

    // Getter methods for accessing private fields
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

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the announcement
     */
    public String getAnnouncement() {
        return announcement;
    }

    /**
     * @param announcement the announcement to set
     */
    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    /**
     * @return the round
     */
    public String getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(String round) {
        this.round = round;
    }

    /**
     * @return the gameStage
     */
    public GameStage getGameStage() {
        return gameStage;
    }

    /**
     * @param gameStage the gameStage to set
     */
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    /**
     * @return the isFinished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * @param isFinished the isFinished to set
     */
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
  
}
