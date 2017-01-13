/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

/**
 *
 * @author Armand
 */

import javax.swing.JFrame;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // creation de la fenetre et lancempent du jeu.
    degame game = new degame();   
    JFrame window = new JFrame("2048");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.add(game);
    window.pack();
    window.setVisible(true); 
    
   game.debut();
        
    }
    
}
