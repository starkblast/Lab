package lab;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private String player1 = "#588C7E";
	private String player2 = "#8C4646";
	public String player1color = "#6677AA";
	public String player2color = "#AA7766";
	private String gridcolor = "#F2AE72";
	private String gridbackgroundcolor ="#D96479";
	public int tileSize;  //tile size: getWidth() / 9.45
	private int marginSize;
	public static JFrame frame;//margin size: (getWidth() - (getWidth() / 9.45)) / 8
	
	Player Player1 = new Player(this, 1);
	Player Player2 = new Player(this, 2);

	static Mouse mml = new Mouse();
	
	/*public void move() {
		Player1.move();
		Player2.move();
	}*/
	public void movePlayer(int i) {
		if (i == 1)
			Player1.x = Player1.x + tileSize + marginSize;
		if (i == 2)
			Player2.x = Player2.x + tileSize + marginSize;
		//move();
		repaint();
	}

	public void paint(Graphics g) {
		if (lastClickedGame != "") {
			System.out.println(lastClickedGame);

			if (lastClickedGame == "player1")
				movePlayer(1);
			if (lastClickedGame == "player2")
				movePlayer(2);
			lastClickedGame = "";
		}
		tileSize =  (int) Math.ceil(getWidth() / 9.45);
		marginSize = (int) Math.ceil(tileSize / 20);
		
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