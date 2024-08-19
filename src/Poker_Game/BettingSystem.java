/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public class BettingSystem {
    private int pot;

    public BettingSystem() {
        this.pot = 0;
    }

    public void addToPot(int amount) {
        this.pot += amount;
    }

    public void resetPot() {
        this.pot = 0;
    }

    public int getPot() {
        return pot;
    }
    
}