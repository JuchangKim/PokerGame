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
    // the pot variable is private to ensure changes are made in a controlled manner.
    private int pot;

    // Constructor initializes the pot to 0 when a BettingSystem object is created.
    public BettingSystem() {
        this.pot = 0;
    }

    // This method adds the specified amount to the pot.
    public void addToPot(int amount) {
        this.pot += amount;
    }

    // This method resets the pot to 0, clearing all the money in it.
    public void resetPot() {
        this.pot = 0;
    }

    // This method returns the current amount of money in the pot.
    public int getPot() {
        return pot;
    }
}

