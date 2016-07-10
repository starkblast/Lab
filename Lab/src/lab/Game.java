package lab;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Console;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
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
	private int marginSize;
	public int highlightupw1;
	public int highlightupw2;
	public int highlightups1;
	public int highlightups2;
	public static JFrame frame;//margin size: (getWidth() - (getWidth() / 9.45)) / 8
	
	/*public Rectangle getBounds() { // collider
		return new Rectangle(0, 0, getWidth(), (getHeight()-getWidth())/2);
	}*/ 
	Player Player1 = new Player(this, 1);
	Player Player2 = new Player(this, 2);
	Turn turn = new Turn(this);
	static Mouse mml = new Mouse();
	
	/*public void move() {
		Player1.move();
		Player2.move();
	}*/
	public void movePlayer(int i) {
		if (i == 1)
			Player1.y = Player1.y + tileSize + marginSize;
			
			
		if (i == 2)
			Player2.y = Player2.y - tileSize - marginSize;
			
		//move();
		repaint();
	}

	public void paint(Graphics g) {
		if (lastClickedGame != "") {
			if (lastClickedGame == "player1") {
				Player1.first = false;
				Player2.first = false;
				movePlayer(1);
				turn.switchPlayer();
			}
		if (lastClickedGame == "player2"){
				Player1.first = false;
				Player2.first = false;
				movePlayer(2);
				turn.switchPlayer();
			}
			lastClickedGame = "";
		}
		tileSize =  (int) Math.ceil(getWidth() / 9.45);
		marginSize = (int) Math.ceil(tileSize / 20);
		highlightupw1 = tileSize - 10;
		highlightupw2 = tileSize - 10;
		highlightups1 = tileSize - 10;
		highlightups2 = tileSize - 10;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.decode(gridbackgroundcolor));	
		// must kast
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
				g2d.fillRect(j*tileSize + j*marginSize, (getHeight()-getWidth())/2 + i*tileSize + i*marginSize, tileSize, tileSize);
			}
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Player1.paint(g2d);
		Player2.paint(g2d);
		mml.getPlayerPosition(Player1.x+(tileSize-10)/2, Player1.y+(tileSize-10)/2, Player2.x+(tileSize-10)/2, Player2.y+(tileSize-10)/2, getWidth(), getHeight());
		repaint();
		
		//HIGHLIGHTS
		if (turn.onePlaysNext == true) { //Player 1 turn
			if(Player1.i == 1) {
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player1.x, Player1.y+ tileSize + marginSize, highlightups1, highlightups1); // playerist allapoole
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player1.x + tileSize + marginSize, Player1.y, tileSize-10, tileSize-10); //playerist paremale
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player1.x - tileSize - marginSize, Player1.y, tileSize-10, tileSize-10); //playerist vasakule
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player1.x, Player1.y - tileSize - marginSize, highlightupw1, highlightupw1); //playerist ülespoole
			}
			
		} else { //Player 2 turn
			if (Player2.i != 1) {
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player2.x, Player2.y+ tileSize + marginSize, highlightups2, highlightups2); // playerist allapoole
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player2.x + tileSize + marginSize, Player2.y, tileSize-10, tileSize-10); //playerist paremale
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player2.x - tileSize - marginSize, Player2.y, tileSize-10, tileSize-10); //playerist vasakule
				
				g2d.setColor(Color.decode(highlightcolor));
				g2d.fillRect(Player2.x, Player2.y - tileSize - marginSize, highlightupw2, highlightupw2); 
				//playerist ülespoole
			}
			
		}
			
	}
	

	
	public static void main(String[] args) throws InterruptedException {
		frame = new JFrame("Labyrinth cancer game");
		frame.addMouseListener(mml);
		frame.add(new Game());
		Game game = new Game();
		frame.setSize(376, 616);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		while (true) {
			//game.move();
			game.repaint();
			if (mml.lastclicked != "") {
				lastClickedGame = mml.lastclicked;
				mml.lastclicked = "";
			}
			Thread.sleep(10);
		}
	
		
	}
}