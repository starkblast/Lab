package lab;

// here be dragons but don't worry
// everything is going to be fine
// you just need to believe

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	
	private int i;
	private int j;
	private static String lastClickedGame = "";
	//colors
	public String player1 = "#588C7E";
	public String player2 = "#8C4646";
	public String player1color = "#6677AA";
	public String player2color = "#AA7766";
	private String gridcolor = "#F2AE72";
	private String gridbackgroundcolor ="#D96479";
	private String highlightcolor ="#F2E394";
	public int tileSize;  //tile size: getWidth() / 9.45
	public int marginSize;
	public int highlightupw1;
	public int highlightupa1;
	public int highlightups1;
	public int highlightupd1;
	public int highlightupw2;
	public int highlightupa2;
	public int highlightups2;
	public int highlightupd2;

    static Point pointStart = null;
    static Point pointEnd = null;
    
	public ArrayList<String> moves = new ArrayList<String>(); // current available moves defined by triangles

	public ArrayList<ArrayList<Integer>> walls1 = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> walls2 = new ArrayList<ArrayList<Integer>>();

	public static JFrame frame;
	
	Player Player1 = new Player(this, 1);  //classid
	Player Player2 = new Player(this, 2);
	Turn turn = new Turn(this);
	Wall wall = new Wall(this);
	static Mouse mml = new Mouse();
	static MotionMouse mml2 = new MotionMouse();


	/*public Rectangle getBounds() { // collider
		return new Rectangle(0, 0, getWidth(), (getHeight()-getWidth())/2);
	}*/ 
	
	public void movePlayer(int i) { // moves player in direction defined by i
		if (turn.onePlaysNext) {
			if (i == 0) { 
				Player1.y = Player1.y - tileSize - marginSize;
			} else if (i == 1) {
				Player1.x = Player1.x - tileSize - marginSize;
			} else if (i == 2) {
				Player1.y = Player1.y + tileSize + marginSize;
			} else if (i == 3) {
				Player1.x = Player1.x + tileSize + marginSize;
			}
		}
		else {
			if (i == 0) { 
				Player2.y = Player2.y - tileSize - marginSize;
			} else if (i == 1) {
				Player2.x = Player2.x - tileSize - marginSize;
			} else if (i == 2) {
				Player2.y = Player2.y + tileSize + marginSize;
			} else if (i == 3) {
				Player2.x = Player2.x + tileSize + marginSize;
			}
		}	

		turn.switchPlayer();
		repaint();
	}

	public void paint(Graphics g) {
		if (lastClickedGame != "") { // handles clicks
			if (moves.contains(lastClickedGame)) { // if move player attempted is available
				for (int i = 0; i < 4; i++) {
				    if(moves.get(i).equals(lastClickedGame)){
				        if (wall.walltoggle % 2 == 0)
				        	movePlayer(i);
				    }
				}
			}
			if (lastClickedGame != "" && lastClickedGame != null) { // stops placing players in default locations
				Player1.first = false;
				Player2.first = false;
			}
			
			if (lastClickedGame =="side1" || lastClickedGame =="side2") { // call wall grid
				wall.walltoggle++;
			}
			
			lastClickedGame = "";
		}
		
		tileSize =  (int) Math.ceil(getWidth() / 9.45);
		marginSize = (int) Math.ceil(tileSize / 20);
		
		highlightupw1 = 3;
		highlightupa1 = 3;
		highlightups1 = 3;
		highlightupd1 = 3;
		
		highlightupw2 = 3;
		highlightupa2 = 3;
		highlightups2 = 3;
		highlightupd2 = 3;
		
		Graphics2D g2d = (Graphics2D) g;
		// look nice
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.decode(gridbackgroundcolor));	
		// grid background color
		g2d.fillRect(0, (getHeight()-getWidth())/2, getWidth(), getWidth());
		// ülemine kast
		g2d.setColor(Color.decode(player1));
		g2d.fillRect(0, 0, getWidth(), (getHeight()-getWidth())/2);
		// alumine kast
		g2d.setColor(Color.decode(player2));
		g2d.fillRect(0, (getHeight()-getWidth())/2+getWidth(), getWidth(), (getHeight()-getWidth())/2);
		
		//DRAW GRID
		g2d.setColor(Color.decode(gridcolor));
		for (i=0; i<9 ;i++) {
			for (j=0; j<9 ;j++) {
				if (i == 0 || i == 8) {
					g2d.setColor(Color.decode("#F2BE82"));
					g2d.fillRect(j*tileSize + j*marginSize, (getHeight()-getWidth())/2 + i*tileSize + i*marginSize, tileSize, tileSize);
					g2d.setColor(Color.decode(gridcolor));
				} else
					g2d.fillRect(j*tileSize + j*marginSize, (getHeight()-getWidth())/2 + i*tileSize + i*marginSize, tileSize, tileSize);
			}
		}
		
		
		Player1.paint(g2d);
		Player2.paint(g2d);
		wall.paint(g2d);

		// DRAW WALLS
		for (int x = 0; x < walls1.size(); x++) { // player 1 walls
            g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/7));
			g.drawLine(walls1.get(x).get(0), walls1.get(x).get(1), walls1.get(x).get(2), walls1.get(x).get(3));
			g.drawString(Integer.toString(10-walls1.size()), 0, 50);
		}
		for (int x = 0; x < walls2.size(); x++) { // player 2 walls 
            g.setColor(Color.decode(wall.wallmarkercolor));
            g2d.setStroke(new BasicStroke(tileSize/7));
			g.drawLine(walls2.get(x).get(0), walls2.get(x).get(1), walls2.get(x).get(2), walls2.get(x).get(3));
			g.drawString(Integer.toString(10-walls2.size()), 150, 50);

		}
        if (wall.walltoggle % 2 != 0) {
        	
			if (pointStart != null) {
	            g.setColor(Color.decode(wall.wallmarkercolor)); 
	            g2d.setStroke(new BasicStroke(tileSize/6));
	            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y); // draw unfinished walls
	            
	            // add correct walls to array
	            if (Math.abs(pointStart.x - pointEnd.x) <= 5 && Math.abs(pointStart.y-pointEnd.y) >= tileSize*2+marginSize) {
	            	if (pointEnd.y > pointStart.y) {
	            		if (turn.onePlaysNext)
	            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y + tileSize*2+marginSize)));
	            		else
	            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y + tileSize*2+marginSize)));
	            	} else {
	            		if (turn.onePlaysNext)
	            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y - tileSize*2-marginSize)));
	            		else
	            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x, pointStart.y - tileSize*2-marginSize)));
	            	}
	            	mml.Start = null;
	            	pointStart = null;
	            } else if (Math.abs(pointStart.y - pointEnd.y) <= 5 && Math.abs(pointStart.x-pointEnd.x) >= tileSize*2+marginSize) {
	            	if (pointEnd.x > pointStart.x) {
	            		if (turn.onePlaysNext)
	            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x + tileSize*2+marginSize, pointStart.y)));
	            		else
	            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x + tileSize*2+marginSize, pointStart.y)));

	            	} else {
	            		if (turn.onePlaysNext)
	            			walls1.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x - tileSize*2-marginSize, pointStart.y)));
	            		else
	            			walls2.add(new ArrayList<Integer>(Arrays.asList(pointStart.x, pointStart.y, pointStart.x - tileSize*2-marginSize, pointStart.y)));
	            	}
	            	mml.Start = null; // stop drawing
	            	pointStart = null;
	            }
	        }
        }

		mml.getPlayerPosition(Player1.x+(tileSize-10)/2, Player1.y+(tileSize-10)/2, Player2.x+(tileSize-10)/2, Player2.y+(tileSize-10)/2, getWidth(), getHeight());
		repaint(); // send players' coordinates to Mouse
		
		
		// visible directions
		
		int[] xPointsUp = {Player1.x + 8, Player1.x + (tileSize - 10 )/2,Player1.x + tileSize - 18}; //triangle
		int[] yPointsUp = {Player1.y - marginSize - 10 - tileSize/10,Player1.y - marginSize - tileSize/2 - marginSize,Player1.y - marginSize - 10 - tileSize/10};
		
		int[] xPointsRight = {Player1.x + tileSize + marginSize + tileSize/10,Player1.x + tileSize + marginSize + tileSize/10 ,Player1.x + tileSize + (tileSize-10)/2}; //triangle
		int[] yPointsRight = {Player1.y + 8,Player1.y + tileSize - 18, Player1.y + (tileSize-10)/2};
		
		int[] xPointsDown = {Player1.x + 8, Player1.x + (tileSize - 10 )/2,Player1.x + tileSize - 18}; //triangle
		int[] yPointsDown = {Player1.y + marginSize + tileSize/10 + tileSize ,Player1.y + tileSize + marginSize + tileSize/2 + marginSize - 10 ,Player1.y + marginSize + tileSize + tileSize/10};
		
		int[] xPointsLeft = {Player1.x - 8 - tileSize/10 - marginSize,Player1.x - 8 - tileSize/10 - marginSize ,Player1.x - 8 - marginSize - (tileSize-10)/2}; //triangle
		int[] yPointsLeft = {Player1.y + 8,Player1.y + tileSize - 18, Player1.y + (tileSize-10)/2};	
		
		int[] xPointsUp2 = {Player2.x + 8, Player2.x + (tileSize - 10 )/2,Player2.x + tileSize - 18}; //triangle
		int[] yPointsUp2 = {Player2.y - marginSize - 10 - tileSize/10,Player2.y - marginSize - tileSize/2 - marginSize,Player2.y - marginSize - 10 - tileSize/10};
		
		int[] xPointsRight2 = {Player2.x + tileSize + marginSize + tileSize/10,Player2.x + tileSize + marginSize + tileSize/10 ,Player2.x + tileSize + (tileSize-10)/2}; //triangle
		int[] yPointsRight2 = {Player2.y + 8,Player2.y + tileSize - 18, Player2.y + (tileSize-10)/2};
		
		int[] xPointsDown2 = {Player2.x + 8, Player2.x + (tileSize - 10 )/2,Player2.x + tileSize - 18}; //triangle
		int[] yPointsDown2 = {Player2.y + marginSize + tileSize/10 + tileSize ,Player2.y + tileSize + marginSize + tileSize/2 + marginSize - 10 ,Player2.y + marginSize + tileSize + tileSize/10};
		
		int[] xPointsLeft2 = {Player2.x - 8 - tileSize/10 - marginSize,Player2.x - 8 - tileSize/10 - marginSize ,Player2.x - 8 - marginSize - (tileSize-10)/2}; //triangle
		int[] yPointsLeft2 = {Player2.y + 8,Player2.y + tileSize - 18, Player2.y + (tileSize-10)/2};	
		
		if (turn.onePlaysNext == true) { // shows possible directions of player 1
			if(Player1.i == 1) {
				moves = new ArrayList<String>();
				for (int j = 0; j < 4; j++)
					moves.add("");
				// bug: lisab arraysse default asukoha, arvestamata, et vb on kaugemal
				g2d.setColor(Color.decode(highlightcolor));
				if (Math.abs(Player1.y - Player2.y) == tileSize+marginSize && Player1.x == Player2.y) {
					//if second player on adjacent tile
				} else
				g.fillPolygon(xPointsUp, yPointsUp, highlightupw1);
				g.fillPolygon(xPointsDown, yPointsDown, highlightups1);
				g.fillPolygon(xPointsLeft, yPointsLeft, highlightupa1);
				g.fillPolygon(xPointsRight, yPointsRight, highlightupd1);
				
				if (highlightups1 > 0) { // if world doesn't end
					int tile_x = (int) Math.ceil(Player1.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player1.y+ tileSize + marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				if (highlightupw1 > 0) {
					int tile_x = (int) Math.ceil(Player1.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player1.y- tileSize - marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				int tile_x = (int) Math.ceil((Player1.x - tileSize - marginSize) / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((Player1.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				moves.set(1, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				tile_x = (int) Math.ceil((Player1.x + tileSize + marginSize) / (tileSize+marginSize));
				tile_y = (int) Math.ceil((Player1.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				moves.set(3, Integer.toString(tile_x) + " " + Integer.toString(tile_y));

			}

			
		} else { //Player 2's possible directions
			if (Player2.i != 1) {
				g2d.setColor(Color.decode(highlightcolor));
				if (Math.abs(Player1.y - Player2.y) == tileSize+marginSize && Player1.x == Player2.y) {
				} else
				g.fillPolygon(xPointsUp2, yPointsUp2, highlightupw2);
				g.fillPolygon(xPointsDown2, yPointsDown2, highlightups2);
				g.fillPolygon(xPointsRight2, yPointsRight2, highlightupd2);
				g.fillPolygon(xPointsLeft2, yPointsLeft2, highlightupa2);
				
				if (highlightups2 > 0) {
					int tile_x = (int) Math.ceil(Player2.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player2.y+ tileSize + marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					moves.set(2, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				if (highlightupw2 > 0) {
					int tile_x = (int) Math.ceil(Player2.x / (tileSize+marginSize));
					int tile_y = (int) Math.ceil((Player2.y- tileSize - marginSize-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
					moves.set(0, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				}
				int tile_x = (int) Math.ceil((Player2.x - tileSize - marginSize) / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((Player2.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				moves.set(1, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
				tile_x = (int) Math.ceil((Player2.x + tileSize + marginSize) / (tileSize+marginSize));
				tile_y = (int) Math.ceil((Player2.y-((getHeight()-getWidth())/2)) / (tileSize+marginSize));
				moves.set(3, Integer.toString(tile_x) + " " + Integer.toString(tile_y));
			}	
		}		
	}
	

	
	public static void main(String[] args) throws InterruptedException {
		// stuff that makes the game appear and do stuff
		frame = new JFrame("Labyrinth cancer game");
		frame.getContentPane().addMouseListener(mml);
		frame.getContentPane().add(new Game());
		frame.getContentPane().addMouseMotionListener(mml2);
		Game game = new Game();
		frame.setSize(376, 616);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		while (true) {
			// checks for new clicks
			game.repaint();
			if (mml.lastclicked != "") {
				lastClickedGame = mml.lastclicked;
				mml.lastclicked = "";
			}
			if (mml.Start != null) {
				pointStart = mml.Start;
				mml.Start = null;
			}
			if (mml2.pointEnd != null) {
				pointEnd = mml2.pointEnd;
			}
			// sleeps
			Thread.sleep(10);
		}
	
		
	}
}