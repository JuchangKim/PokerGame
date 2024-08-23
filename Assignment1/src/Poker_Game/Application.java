/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public class Application {
    
    public static void main(String[] args) {
        
        //this is the logic for connecting to and running PokerCLI
        PokerCLI pokerCLI = new PokerCLI();
        try {
            pokerCLI.start();
        } catch (InterruptedException e) {
            // Handle the exception, perhaps log it or print a message
            System.out.println("The game was interrupted.");
            e.printStackTrace();
        }
    }
}

