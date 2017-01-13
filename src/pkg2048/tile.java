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

//http://zetcode.com/tutorials/javagamestutorial/snake/
import java.awt.Color;
import java.awt.image.BufferedImage; // permet l'utilisation de BufferedImage: ajout des image dans le projet. si jamais je bug:https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html
import java.awt.Graphics2D;
import java.awt.Font;

public class tile {
    
    public static final int hauteur = 80,  vitesse = 20;
    public static final int arc_width = 15;
    public static final int arc_heigth = 15;
    
   private int  value;
   private BufferedImage tile;
   private Color couleur;
   private Color texte;
   private int x, y;
   
   private pointpoint pointpointpoint;
   private boolean canCombine = true;
   //constructeur de case
   public tile(int value, int x, int y ){
       this.value = value;
       this.x = x;
       pointpointpoint = new pointpoint(x,y);
       tile = new BufferedImage(hauteur, hauteur,BufferedImage.TYPE_INT_ARGB );
       this.y = y;
       dessin();
       
   }
   
   public void rendu(Graphics2D g){
       g.drawImage(tile, x, y, null);
       
   }
   
   private void dessin(){
       Graphics2D g = (Graphics2D)tile.getGraphics();
       if (value == 2  ){
           couleur = new Color(0x7A1FA2);
           texte = new Color(0x000000);
           
       }
       if (value == 4  ){
           couleur = new Color(0x000000);
           texte = new Color(0x000000);
           
       }
       if (value == 8  ){
           couleur = new Color(0xD800AD );
           texte = new Color(0xffffff);
           
       }
       if (value == 16  ){
           couleur = new Color(0xFFFFCD);
           texte = new Color(0xffffff);
           
       }
       if (value == 32  ){
           couleur = new Color(0xFF66DE);
           texte = new Color(0xffffff);
           
       }
       if (value == 64  ){
           couleur = new Color(0xE1B1FF);
           texte = new Color(0xffffff);
           
       }
       if (value == 128  ){
           couleur = new Color(0x71005F);
           texte = new Color(0xffffff);
           
       }
       if (value == 256  ){
           couleur = new Color(0xD800AD);
           texte = new Color(0xffffff);
           
       }
       if (value == 512  ){
           couleur = new Color(0xFFFFCD);
           texte = new Color(0xffffff);
           
       }
       if (value == 1024  ){
           couleur = new Color(0xFF66DE);
           texte = new Color(0xffffff);
           
       }
       if (value == 2048  ){
           couleur = new Color(0xE1B1FF);
           texte = new Color(0xffffff);
           
       }
       if (value > 2048  ){
           couleur = new Color(0xfffff);
           texte = new Color(0x000000);
           
       }
      
       g.setColor(new Color(0,0,0,0));
       g.fillRect(0, 0, hauteur, hauteur);
       
       g.setColor(couleur);
       g.fillRoundRect(0, 0, hauteur, hauteur,arc_width, arc_heigth);
       
       
       g.setColor((texte));
      
      
   }
   
   static void update(){
       
   }
   
   //https://coderanch.com/t/481563/java/Placing-text-BufferedImage
   
   
   
   public  int getvaleur(){
       return value;
   }
   public void setvaleur(int k){
       this.value = k;
   }
   
   
   public boolean canCombine(){
       return canCombine;
   }
   
   public void setcancombine(boolean k){
       this.canCombine = k;
   }
   
   public void slide(pointpoint slideTo) {
		this.pointpointpoint = slideTo;
	}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
   
}
