package Poker_Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


//The PokerGame class manages the overall flow of a poker game. It extends the PokerGameCore abstract class
//for handling file operations related to game state and logs.

public class PokerGame extends PokerGameCore {
    
    private Scanner scanner;
    private PokerCLI pokerCli;
    private String username;
    private PlayAgainMenu playagainmenu;
    private GameStage gameStage;

    public PokerGame() {
        super();
        scanner = new Scanner(System.in);
    }

    @Override
    public void startGame(String username) throws InterruptedException {
        this.username = username;
        
        if (FileManager.createNewSaveFile(username)) {
            System.out.println("New save file created: " + username);
        } else {
            System.out.println("Save file already exists. Loading game...");
        }
        
        resetGame();
        notifyGameUpdated();
        
        playRound();
        Thread.sleep(1000);
       
        notifyGameUpdated();
        
        System.out.println("Do you want to play another game? (yes/no)");
        
        Thread.sleep(1000);
        
        this.setAnnouncement("Winner is : " + gameState.getWinner() + "\n", 0);
        
        for (Player p : gameState.getPlayers()) {
            int totalWins = FileManager.countTotalWins(p.getName());
            System.out.println(p.getName() + " total wins: " + totalWins);
        }
        
        setIsFinished(true);
    }

    @Override
    protected void playRound() throws InterruptedException {
        setAnnouncement("Starting Game", 0);

        GameStateAction initializeState = new InitializeState();
        initializeState.play(this);
        
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return;
        }
        

        setAnnouncement("The flop round", 0);

        GameStateAction flopState = new DealFlopState();
        flopState.play(this);
        playBettingRound("The Flop");
        
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return;
        }

        notifyGameUpdated();
        
        setAnnouncement("The turn round", 0);

        GameStateAction turnState = new DealTurnState();
        turnState.play(this);
        playBettingRound("The Turn");
        
        notifyGameUpdated();
        
        if (onePlayerIsInGame()) {
            return;
        }

        notifyGameUpdated();
        
        setAnnouncement("The river round", 0);

        GameStateAction riverState = new DealRiverState();
        riverState.play(this);
        playBettingRound("The River");
        
        notifyGameUpdated();
        
        setAnnouncement("Determining The Winner", 0);
        GameStateAction determineWinnerState = new DetermineWinnerState();
        determineWinnerState.play(this);
        notifyGameUpdated();
    }

    @Override
    protected void playBettingRound(String roundName) throws InterruptedException {
        this.setRound(roundName);
        
        System.out.println(roundName + " Round\n");
        
        notifyGameUpdated();
        System.out.println("Community Cards: " + getGameState().getCommunityCards() + "\n");
        
        
        for (Player player : getGameState().getPlayers()) {
            if (player.getIsInGame()) {
                Thread.sleep(1000);
                playerTurn(player);
            }
        }
    }

    @Override
    public void playerTurn(Player player) throws InterruptedException {
        if (getGameState().getPlayers().get(0).getName().equals(player.getName())) {
            this.setAnnouncement("Choose Your Option", 5);
            notifyGameUpdated();
            for (GameListener listener : listeners) {
                listener.onPlayerTurn(player);
            }
            this.setAnnouncement(" ", 5);
            notifyGameUpdated();
        } else {
            computerTurn(player);
        }
    }

    private void computerTurn(Player player) throws InterruptedException {
        int choice = (int) (Math.random() * 3) + 1;
        switch (choice) {
            case 1:
                handleCall(player);
                break;
            case 2:
                handleFold(player);
                break;
            case 3:
                handleRaise(player);
                break;
        }
        System.out.println(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"));
        this.setAnnouncement(player.getName() + " chose to " + (choice == 1 ? "Call" : choice == 2 ? "Fold" : "Raise"), getGameState().getPlayers().indexOf(player) + 1);
        notifyGameUpdated();
    }

    private void handleCall(Player player) {
        int callAmount = getGameState().getCurrentBet() - player.getCurrentBet();
        if (player.getChips() >= callAmount) {
            player.call(callAmount);
            getBettingSystem().addToPot(callAmount);
            player.setCurrentBet(getGameState().getCurrentBet());
            setAnnouncement(player.getName() + " called.", getGameState().getPlayers().indexOf(player) + 1);
        } else {
            handleFold(player);
        }
    }

    private void handleFold(Player player) {
        setAnnouncement(player.getName() + " folded.", getGameState().getPlayers().indexOf(player) + 1);
        player.fold();
    }

    private void handleRaise(Player player) {
        int raiseAmount = getGameState().getCurrentBet() * 2;
        if (player.getChips() >= raiseAmount) {
            int increaseAmount = raiseAmount - getGameState().getCurrentBet();
            player.raise(increaseAmount);
            getBettingSystem().addToPot(increaseAmount);
            getGameState().setCurrentBet(raiseAmount);
            player.setCurrentBet(getGameState().getCurrentBet());
            setAnnouncement(player.getName() + " raised.", getGameState().getPlayers().indexOf(player) + 1);
        } else {
            handleFold(player);
        }
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }
}
