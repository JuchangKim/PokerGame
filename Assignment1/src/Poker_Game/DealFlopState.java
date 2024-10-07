/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public class DealFlopState implements GameStateAction {
    @Override
    public void play(PokerGame game) throws InterruptedException {
        // Deal the first three community cards (the flop) to the board - Round 1
        for (int i = 0; i < 3; i++) {
            game.getGameState().getCommunityCards().add(game.getDeck().getCard());
        }
        game.notifyGameUpdated();
        Thread.sleep(1000); // Introduce delay to simulaate real-time dealing of the flop
    }
}