package lab;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Rectangle;

public class Player {
	int x = 0;
	int y = 0;
	int xa = 1;
	int ya = 1;
	int i;
	boolean first = true;
	public Game game;


	public Player(Game game, int i) {
		this.game= game;
		this.i = i;
	}

	void move() {
		//game.repaint();
	}
	
	void displayHighlights () {
		
		/*if (y>=(game.getHeight()-game.getWidth())/2) { 
			game.highlightupw = 0;
		}*/
	}

	public void paint(Graphics2D g) {
		if(first) {
			if (i == 1) {
				y = ((game.getHeight()-game.getWidth())/2 + 5);
				x = game.getWidth() /2 - (game.tileSize-10)/2;
				
			} else {
				y = ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + 5;
				x = game.getWidth() /2- (game.tileSize-10)/2;
			}
		}

		if (i == 1) { // lisada see, kui ta on alumises aares
			if (y<=(game.getHeight()-game.getWidth())/2 + 5) { 
				game.highlightupw1 = 0;
			} else if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + 5) {
				game.highlightups1 = 0;
			}
			g.setColor(Color.decode(game.player1color));
		} else {
			if (y <=(game.getHeight()-game.getWidth())/2) { 
				game.highlightupw2 = 0;
			} else if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + 5) {
				game.highlightups2 = 0;
			}
			g.setColor(Color.decode(game.player2color));
			
		}
		g.fillOval(x, y, game.tileSize-10, game.tileSize-10);
	}
	
	/*public boolean collision() {
		return getBounds().intersects(game.getBounds());
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, game.tileSize, game.tileSize);
	}*/
	
	//(getHeight()-getWidth())/2)
	
	
}