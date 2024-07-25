/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.util.Random;

/**
 *
 * @author user
 */
public class ComputerPlayers extends Player {
    private Random random;

    public ComputerPlayers(String name, int balance) {
        super(name, balance);
        random = new Random();
    }

    public void makeDecision() {
        // Randomized decisions: bet, raise, fold, check
        int decision = random.nextInt(4);
        switch (decision) {
            case 0:
                // bet
                
                break;
            case 1:
                // raise
                
                break;
            case 2:
                // fold
                // Fold logic here
                break;
            case 3:
                // check
                // Check logic here
                break;
        }
    }
}