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

//https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
import java.awt.event.KeyEvent;
public class keyboard {
    
    public static boolean[] pressed = new boolean[256];
    public static boolean[] prev = new boolean[256];
    
    // classe permettant de gerer le clavier 
    private keyboard(){
    }
    
    public static void maj(){
        for (int i = 0; i <4; i++){
            if(i==0)prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            if(i==1)prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            if(i==2)prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            if(i==3)prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
            
        }
    }
    
    public static void appuyer(KeyEvent e){
        pressed[e.getKeyCode()] = true ;
    }
    
    public static void relacher(KeyEvent e){
        pressed[e.getKeyCode()] = false;
    }
    
    public static boolean typed(int clef){
        return !pressed[clef]&&prev[clef];
    }
    
}
