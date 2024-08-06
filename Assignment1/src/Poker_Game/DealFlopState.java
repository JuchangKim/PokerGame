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
        for (int i = 0; i < 3; i++) {
            game.getGameState().getCommunityCards().add(game.getDeck().getCard());
        }
        
        Thread.sleep(1000); // Introduce delay
    }
}