package lab;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

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
		ArrayList<ArrayList<Integer>> WALLS = game.calculateWalls();
		int player_x = (int) Math.ceil(x / (game.tileSize+game.marginSize));
		int player_y = (int) Math.ceil((y-((game.getHeight()-game.getWidth())/2)) / (game.tileSize+game.marginSize))+1;
		
		
		
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
			// checks if player is next to walls
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y-1, player_x, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y, player_x, player_y-1)))) {
				game.wa1 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y-1, player_x+1, player_y-1))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y-1, player_x, player_y-1)))) {
				game.ww1 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y-1, player_x+1, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y, player_x+1, player_y-1)))) {
				game.wd1 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y, player_x+1, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y, player_x, player_y)))) {
				game.ws1 = true;
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

			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y-1, player_x, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y, player_x, player_y-1)))) {
				game.wa2 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y-1, player_x+1, player_y-1))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y-1, player_x, player_y-1)))) {
				game.ww2 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y-1, player_x+1, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y, player_x+1, player_y-1)))) {
				game.wd2 = true;
			}
			if (WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x, player_y, player_x+1, player_y))) || WALLS.contains(new ArrayList<Integer>(Arrays.asList(player_x+1, player_y, player_x, player_y)))) {
				game.ws2 = true;
			}
			g.setColor(Color.decode(game.player2color));
			
		} // fills oval (that's the player)
		g.fillOval(x, y, game.tileSize-10, game.tileSize-10);
	}
	
	/*public boolean collision() {
		return getBounds().intersects(game.getBounds());*/
		
	}