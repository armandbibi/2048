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


//image via 2d Graphics:http://duj.developpez.com/tutoriels/java/dessin/intro/
//http://stackoverflow.com/questions/20769767/calculate-fps-in-java-game

// Pour tous les imports, voir HTTPS://docs.oracle.com
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D; //implemente les graphiques 2D (geometrie, text)
import java.awt.event.KeyListener;
import javax.swing.JPanel; 
import java.awt.Dimension; // Dimention permet d'e"ncapsuler la taille d'un element, plus qu'utile.
import java.awt.image.BufferedImage; // permet l'utilisation de BufferedImage: ajout des image dans le projet. si jamais je bug:https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html
public class degame extends JPanel implements KeyListener, Runnable {

    public static final long serialVersionUID = 1L;
    public static final int hauteur = 630;
    public static final int largeur = 400;
    private plateau Plateau;
    private Thread game;
    private BufferedImage image= new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
    private boolean running = false;
    public long debut;
    public long fin;
    public boolean set;
    
    
    //constructeur
    public degame(){
        setFocusable(true);
        setPreferredSize(new Dimension(largeur, hauteur));
        addKeyListener(this);
        
        // ajout du plateau au jeu 
        Plateau = new plateau( largeur / 2 - plateau.largeurplateau / 2 , hauteur - plateau.hauteurplateau - 10 );
                
    }
    
    private void update(){
     
        Plateau.update();
        keyboard.maj();
    }
    
    
    
    private void rendu(){
        
        //rendu du tableau
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, largeur, hauteur);// faire un rectaglze a la taille de ce que l'on a deja préparé.
        Plateau.rendu(g);
        g.dispose();
        
        
        
        Graphics2D t = (Graphics2D)getGraphics();
        t.drawImage(image, 0, 0, null);
        t.dispose();

    }
    //http://stackoverflow.com/questions/20769767/calculate-fps-in-java-game
    // timer permettant que ca ne charge pas tout le temps, sinon ca chauffe ce qui est étonnant pour un 2048
    @Override
    public void run(){
     
		
		double nsPerUpdate = 1000000000.0 / 60;

		//derniere maj en nano
		double then = System.nanoTime();
		double unprocessed = 0;

		while (running) {

			

			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;

			// Update queue
			while (unprocessed >= 1) {

				// update

				update();
				unprocessed--;
                                rendu();
				
			}

			

			
		}
	 
        
    } 
    
    public synchronized void debut(){
      
	
		if (running) return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
      
    }

    @Override
    public void keyPressed(KeyEvent e) {
       keyboard.appuyer(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyboard.relacher(e);
    }
}
