package lab;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
	private int i;
	private int j;
	private String player1 = "#330000";
	private String player2 = "#003300";
			
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);	
		g2d.fillRect(0, 120, 376, 376);
		g2d.setColor(Color.decode(player1));
		g2d.fillRect(0, 0, 376, 120);
		g2d.setColor(Color.decode(player2));
		g2d.fillRect(0, 496, 376, 120);
		g2d.setColor(Color.GRAY);
		for (i=0; i<9 ;i++) {
			for (j=0; j<9 ;j++) {
				g2d.fillRect(j*40 + j*2, 120 + i*40 + i*2, 40, 40);
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Labyrinth cancer game");
		frame.add(new Game());
		frame.setSize(376, 616);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
	
	}
}
