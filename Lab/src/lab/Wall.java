package lab;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Console;
import java.util.ArrayList;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wall {

	public int w;
	public int l;
	public String wallmarkercolor = "#583668";
	public int markersize;
	
	public Game game;
	
	public Wall(Game game) {
		this.game= game;
	}
	
	
	public void paint(Graphics g) {
		
		markersize = (int) (game.tileSize/5);
		
		for (w = 0; w < 8; w++ ) {
			for (l = 0; l < 8; l++ ) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.decode(wallmarkercolor));
				g2d.fillOval(game.tileSize + w * (game.tileSize + game.marginSize) - markersize/2 + 1/*extra pixel for comfort of eyes*/ , (game.getHeight()-game.getWidth())/2 + game.tileSize + l*(game.tileSize + game.marginSize)-markersize/2,markersize,markersize);
			}
		}
	}
	
		
	
	
}
