/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */

// The InitializeState class handles the initialization phase of the poker game,
// including setting up the deck, clearing previous hands, and dealing new cards to each player

public class InitializeState implements GameStateAction {
    @Override
    public void play(PokerGame game) throws InterruptedException {
        
        game.getDeck().reset();
        game.getDeck().shuffleDeck();
        game.getGameState().getCommunityCards().clear();
        game.getGameState().setPot(0);
        game.getGameState().setCurrentBet(10);
        
        // Loop through each player to prepare them for the new round.
        for (Player player : game.getGameState().getPlayers()) {
            
            // Clear the player's hand from any previous round.
            player.clearHand();
            
            // Check if the player has fewer than 100 chips; if so, add 1000 chips to their stack.
            if(player.getChips() < 100) {
                System.out.println(player.getName() + " does not have enough chips."
                                    + "1000 chips are added to the player.");
                player.addToChips(1000);
            }
            
            // Deal two cards to the player.
            player.addCardToHand(game.getDeck().getCard());
            player.addCardToHand(game.getDeck().getCard());
            Thread.sleep(1000); // Delay of 1 second between dealing cards to simulate real-time playing.
        }
        
        // Announce the start of a new round.
        System.out.println("Starting new round...");
        
        // Display each player's current state after the initial setup.
        for (Player player : game.getGameState().getPlayers()) {
            System.out.println(player);
            Thread.sleep(1000); // Delay of 1 second between displaying each player's state
        }
    }
}
