/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game.Panel;

import Poker_Game.Database.FileManager;
import Poker_Game.Player;
import Poker_Game.PokerGame;

public class PokerController {
    private PokerGame game;

    public PokerController(PokerGame game) {
        this.game = game;
    }

    // depends on click button, different method is running.
    public void performAction(String action, Player player) throws InterruptedException {
        switch (action.toLowerCase()) {
            case "call":
                handleCall(player);
                break;
            case "fold":
                handleFold(player);
                break;
            case "raise":
                handleRaise(player);
                break;
            case "check":
                handleCheck(player);
                break;
            case "exit":
                handleExit();
                break;
        }
        game.notifyGameUpdated();  // Notify the view of the game state change
    }

    // when user click call, user's chips betting
    private void handleCall(Player player) {
        int callAmount = game.getGameState().getCurrentBet() - player.getCurrentBet();
        if (player.getChips() >= callAmount) {
            player.call(callAmount);
            game.getBettingSystem().addToPot(callAmount);
            player.setCurrentBet(game.getGameState().getCurrentBet());
        }
        game.setAnnouncement(player.getName() + " Choose Call", 1);
    }

    // when user click fold, user quit current game
    private void handleFold(Player player) {
        game.setAnnouncement(player.getName() + " Choose Fold", 1);
        player.fold();
    }

    // when user click raise, user's chips are betting
    private void handleRaise(Player player) {
        int raiseAmount = game.getGameState().getCurrentBet() * 2;
        if (player.getChips() >= raiseAmount) {
            int increaseAmount = raiseAmount - game.getGameState().getCurrentBet();
            player.raise(increaseAmount);
            game.getBettingSystem().addToPot(increaseAmount);
            game.getGameState().setCurrentBet(raiseAmount);
            player.setCurrentBet(game.getGameState().getCurrentBet());
        }
        game.setAnnouncement(player.getName() + " Choose Raise", 1);
    }

    // when user click check, user skip the current round.
    private void handleCheck(Player player) {
        if (game.getGameState().getCurrentBet() > player.getCurrentBet()) {
            game.setAnnouncement("You need to call before you can check.", 1);
        }
        game.setAnnouncement(player.getName() + " Choose Check", 1);
    }

    // when user click exit game, the game is saved and exit current game
    private void handleExit() {
        FileManager.saveGame(game, game.getGameState().getPlayers().get(0).getName());
        System.exit(0);
    }
}

