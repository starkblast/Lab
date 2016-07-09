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
	private String player1 = "#588C7E";
	private String player2 = "#8C4646";
	public String player1color = "#6677AA";
	public String player2color = "#AA7766";
	private String gridcolor = "#F2AE72";
	private String gridbackgroundcolor ="#D96479";
	public int tileSize;  //tile size: getWidth() / 9.45
	private int marginSize; //margin size: (getWidth() - (getWidth() / 9.45)) / 8
	
	Player Player1 = new Player(this, 1);
	Player Player2 = new Player(this, 2);
	
	static Mouse mml = new Mouse();

	public void paint(Graphics g) {
		System.out.println(Player1.x);
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
	}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Labyrinth cancer game");

		frame.addMouseListener(mml);
		frame.add(new Game());
		frame.setSize(376, 616);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
	
	}
}