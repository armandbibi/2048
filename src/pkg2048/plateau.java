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
//https://www.codewars.com/dashboard
//http://stackoverflow.com/questions/15986677/drawing-an-object-using-getgraphics-without-extending-jframe
// stackoverflow.com/questions/21322353/java-jpanel-getgraphics
//https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
import com.sun.javafx.scene.traversal.Direction;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
public class plateau {
    public static final int colonnes = 4;
    public static final int lignes = 4;
    private final int premierecase = 2;
    private final tile[][] board;
    private boolean perdu;
    private boolean gagne;
    private BufferedImage plateaujeu;
    private BufferedImage plateaufinal;
    private int x;
    private int y;
    private boolean commencer;
    private static final int espace = 10;
    //pas les meme, si jamais on change les case ca marche mieux comme ca meme si de base c'est un carré
    public static int largeurplateau = (colonnes+1) * espace+ colonnes *tile.hauteur;
    public static int hauteurplateau = (lignes+1) * espace+ lignes *tile.hauteur;
    
    
    public plateau(int x, int y){
        this.x = x;
        this.y = y;
        board = new tile[lignes][colonnes];
        plateaujeu = new BufferedImage(largeurplateau,hauteurplateau, BufferedImage.TYPE_INT_RGB);
        plateaufinal = new BufferedImage(largeurplateau,hauteurplateau, BufferedImage.TYPE_INT_RGB);
        
        deimage();
        debut();
    }
   
    
    
    
    private void deimage() {
		Graphics2D g = (Graphics2D) plateaujeu.getGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, largeurplateau, hauteurplateau);
		g.setColor(Color.lightGray);

		for (int row = 0; row < lignes; row++) {
			for (int col = 0; col < colonnes; col++) {
				int x = espace + espace * col + tile.hauteur * col;
				int y = espace + espace * row + tile.hauteur * row;
				g.fillRoundRect(x, y, tile.hauteur, tile.hauteur, tile.arc_heigth, tile.arc_heigth);
                                
			}
		}
	}
    
    
    
    public void rendu(Graphics2D graphic){
        Graphics2D G = (Graphics2D) plateaufinal.getGraphics();
        G.drawImage(plateaujeu, 0, 0,null);
        
        for (int row = 0; row < lignes; row++) {
			for (int col = 0; col < colonnes; col++) {
				tile actuelle = board[row][col];
                                if (actuelle == null) continue;
                                actuelle.rendu(G);
			}
		}
        graphic.drawImage(plateaufinal, x, y,null);
        G.dispose();
    }
    
    public void update(){
        desclicks();
        for (int row = 0; row < lignes; row++) {
                        
			for (int col = 0; col < colonnes; col++) {
                            tile actuelle = board[row][col];
                            if(actuelle== null)continue;
                            actuelle.update();
                            resetposition(actuelle,row,col);
                            if(actuelle.getvaleur() == 2048) // genial tu as gagné, gg
                            {
                                gagne = true;
                            }
			}
		}
    }
    
    public void resetposition(tile actuelle,int  ligne, int colonne){
        if(actuelle == null)return;
        
        int x = gettileX(colonne);
        int y = gettileY(ligne);
        
        int xprime =  actuelle.getX() - x;
        int yprime = actuelle.getY() - y;
        
        if(Math.abs(xprime)< tile.vitesse){
            actuelle.setX(actuelle.getX()- xprime);
        }
        if(Math.abs(yprime)< tile.vitesse){
            actuelle.setY(actuelle.getY()- yprime);
            
        }
        if(Math.abs(xprime)<0){
            actuelle.setX(actuelle.getX()+ tile.vitesse);
        }
         if(Math.abs(yprime)<0){
            actuelle.setY(actuelle.getY()+ tile.vitesse);
        }
         
        if(xprime > 0){
            actuelle.setX(actuelle.getX()-tile.vitesse);
        }
         
        if(yprime > 0){
            actuelle.setY(actuelle.getY()-tile.vitesse);
        }
        
        
    }
    private void movetile(Direction dir){
        boolean peut = false;
        int horizontal = 0;
        int vertical = 0;
        
        if(dir == Direction.LEFT){
            horizontal = -1;
            for(int i = 0; i < lignes; i++){
                for (int j = 0; j < colonnes; j++) {
                    if(!peut){
                        peut = move(i,j,vertical,horizontal,dir);
                    }
                    else{
                        move(j,i,vertical,horizontal,dir);
                        
                    }
                }
            }
        }
         if(dir == Direction.DOWN){
            vertical = 1;
            for(int i = lignes; i>0; i--){
                for (int j = 0; j >= colonnes; j++) {
                    if(!peut){
                        peut = move(i,j,vertical,horizontal,dir);
                    }
                    else{
                        move(i,j,vertical,horizontal,dir);
                        
                    }
                }
            }
        }
          if(dir == Direction.RIGHT){
            horizontal = 1;
            for(int i = 0; i < lignes; i++){
                for (int j = colonnes; j > 0; j--) {
                    if(!peut){
                        peut = move(i,j,vertical,horizontal,dir);
                    }
                    else{
                        move(i,j,vertical,horizontal,dir);
                        
                    }
                }
            }
        }
           if(dir == Direction.UP){
            vertical = -1;
            for(int i = 0; i <lignes; i++){
                for (int j = 0; j < colonnes; j++) {
                    if(!peut){
                        peut = move(i,j,vertical,horizontal,dir);
                    }
                    else{
                        move(i,j,vertical,horizontal,dir);
                        
                    }
                }
            }
        }
           for (int i = 0; i < lignes; i++) {
               for (int j = 0; j < colonnes; j++) {
                   tile actuelle = board[i][j];
                   if(actuelle == null)continue;
                   actuelle.canCombine();
               }
        }
           if(peut){
               randowtile();
               faucheuse();
           }
             
    }
  private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction direction) {
		boolean canMove = false;
		tile actuelle = board[row][col];
		if (actuelle == null) return false;
		boolean move = true;
		int newCol = col;
		int newRow = row;
		while (move) {
			newCol += horizontalDirection;
			newRow += verticalDirection;
			if (verifielessorties(direction, newRow, newCol)) break;
			if (board[newCol][newRow] == null) {
				board[newRow][newCol] = actuelle;
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].slide(new pointpoint(newRow, newCol));
			}
			else if (board[newRow][newCol].getvaleur() == actuelle.getvaleur() && board[newRow][newCol].canCombine()) {
				board[newRow][newCol].setcancombine(false);
				board[newRow][newCol].setvaleur(board[newRow][newCol].getvaleur() * 2);
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].slide(new pointpoint(newRow, newCol));
				
		
			}
			else {
				move = false;
			}
		}
		return canMove;
	}

    
    private boolean verifielessorties(Direction dir, int Nligne,int Ncol){
        if(dir == Direction.LEFT ){
            return Ncol<0;
        }
        else if(dir == Direction.RIGHT ){
            return Ncol > colonnes - 1;
        }
        else  if(dir == Direction.UP ){
            return Nligne<0;
        }
        else if (dir == Direction.DOWN){
            return Nligne > lignes;
        }
        return false;
    }
    private void desclicks(){
        if(keyboard.typed(KeyEvent.VK_LEFT)){
            movetile(Direction.LEFT);
            
        }
        if(keyboard.typed(KeyEvent.VK_RIGHT)){
            
            movetile(Direction.RIGHT);
        }
        if(keyboard.typed(KeyEvent.VK_UP)){
            
            movetile(Direction.UP);
        }
        if(keyboard.typed(KeyEvent.VK_DOWN)){
            movetile(Direction.DOWN);

        }
    }
    
    private void debut(){
        for (int i = 0; i< premierecase; i++ ){
            randowtile();
        }
    }
    //https://docs.oracle.com/javase/7/docs/api/java/util/Random.html
     private void randowtile(){
        Random random = new Random();
        boolean notvalid = true;
        
        // evite de faire spawner des case la ou il y a dja des tiles .
        while(notvalid){
            int position = random.nextInt(lignes* colonnes);
            int ligne = position / lignes;
            int colonne = position % colonnes;
            
            tile actuelle = board[ligne][colonne];
            if(actuelle == null){
                int value = random.nextInt(10)<9?2:4;// permet de changer enter 2 et 4 avec 90% chance de 2
                tile tile = new tile (value, gettileX(colonne), gettileY(ligne) );
                board[colonne][ligne] = tile;
                notvalid = false;
                System.out.print("yolo");
            }
        }
    }
    
     private void faucheuse(){
         for (int i = 0; i < lignes; i++) {
             for (int j = 0; j < colonnes; j++) {
                 if(board[i][j] == null)return;
                 if(scout(i,j,board[i][j])){
                     
                 }
             }
             
         }
     }
     
    private boolean scout(int ligne, int colonne, tile actuelle){
        if(ligne > 0){
            tile verif = board[ligne-1][colonne];
            if(verif == null) return true;
            if (actuelle.getvaleur() == verif.getvaleur()) return true;
            
        }
        if(ligne < lignes -1){
            tile verif = board[ligne+1][colonne];
            if(verif == null) return true;
            if(actuelle.getvaleur() == verif.getvaleur()) return true;
            
        }
          if(colonne > 0){
            tile verif = board[ligne][colonne-1];
            if(verif == null) return true;
            if (actuelle.getvaleur() == verif.getvaleur()) return true;
            
        }
        if(colonne < colonnes -1){
            tile verif = board[ligne][colonne+1];
            if(verif == null) return true;
            if(actuelle.getvaleur() == verif.getvaleur()) return true;
            
        }
        return false;
    }
    
    public int gettileX(int col){
        return espace+col * tile.hauteur + col*espace ;
    }
    
    public int gettileY(int col){
        return espace+col * tile.hauteur + col*espace ;
    }
    
}
