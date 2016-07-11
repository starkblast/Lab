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
	int wallsRemaining = 10;
	
	boolean first = true;
	public Game game;


	public Player(Game game, int i) {
		this.game= game;
		this.i = i;
	}

	void move() {}

	public void paint(Graphics2D g) {
		/*if(first) { // moves to default location
			if (i == 1) {
				y = ((game.getHeight()-game.getWidth())/2 + game.tileSize/8);
				x = game.getWidth() /2 - (game.tileSize-10)/2;
				
			} else {
				y = ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + game.tileSize/8;
				x = game.getWidth() /2- (game.tileSize-10)/2;
			}

		}*/
		if (i == 1) { // checks if world ends next to player 1
			if (y<=(game.getHeight()-game.getWidth())/2 + 5) { 
				game.highlightupw1 = 0;
			} else if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + game.tileSize/8) {
				game.highlightups1 = 0;
			}
			if (y<=(game.getHeight()-game.getWidth())/2 + 5 + game.tileSize + game.marginSize)
				game.highdw1 = true;
			if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + game.tileSize/8 - game.tileSize - game.marginSize) {
				game.highds1 = true;
			}
			g.setColor(Color.decode(game.player1color));
		} else { // checks if world ends next to player 2
			if (y<=(game.getHeight()-game.getWidth())/2) { 
				game.highlightupw2 = 0;
			} else if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + game.tileSize/8) {
				game.highlightups2 = 0;
			}
			if (y<=(game.getHeight()-game.getWidth())/2 + 5 + game.tileSize + game.marginSize)
				game.highdw2 = true;
			if (y >= ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + game.tileSize/8 - game.tileSize + game.marginSize)
				game.highds2 = true;
		
			g.setColor(Color.decode(game.player2color));
			
		} // fills oval (that's the player)
		g.fillOval(x, y, game.tileSize-10, game.tileSize-10);
	}
	
	/*public boolean collision() {
		return getBounds().intersects(game.getBounds());*/
		
	}