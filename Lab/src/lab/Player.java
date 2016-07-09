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


public class Player {
	int x = 0;
	int y = 0;
	int xa = 1;
	int ya = 1;
	int i;
	public Game game;

	public Player(Game game, int i) {
		this.game= game;
		this.i = i;
		//if (i == 1) {

		//}
	}

	void move() {
		if (x + xa < 0)
			xa = 1;
		if (x + xa > game.getWidth() - 30)
			xa = -1;
		if (y + ya < 0)
			ya = 1;
		if (y + ya > game.getHeight() - 30)
			ya = -1;

		x = x + xa;
		y = y + ya;
	}

	public void paint(Graphics2D g) {
		System.out.println(x);
		
		if (i == 1) {
			g.setColor(Color.decode(game.player1color));
			y = ((game.getHeight()-game.getWidth())/2 + 5);
			x = game.getWidth() /2 - (game.tileSize-10)/2;
		} else {
			g.setColor(Color.decode(game.player2color));
			y = ((game.getHeight()-game.getWidth())/2 + game.getWidth()) - game.tileSize + 5;
			x = game.getWidth() /2- (game.tileSize-10)/2;
		}
		g.fillOval(x, y, game.tileSize-10, game.tileSize-10);
	}
}