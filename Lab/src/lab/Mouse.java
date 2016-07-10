package lab;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;

class Mouse implements MouseListener {
	/*public Game game;
	class Mouse(Game game) {
		this.game = game;
	}*/
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int width;
	private int height;
	public String lastclicked;
	
	public void getPlayerPosition(int x1, int y1, int x2, int y2, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
		this.height = height;
	}
	public String clickedButton(String button) {
		return button;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) { 

		int x = arg0.getX();
		int y = arg0.getY();
		
		 /*try {
            Robot robot = new Robot();
            Rectangle area = new Rectangle(0, 0, width, height);
            final BufferedImage capture = robot.createScreenCapture(area);
            final Color color = new Color(capture.getRGB(x, y));            
            System.out.println(color);
		 } catch (AWTException e) {
	            e.printStackTrace();
	     }*/

		if (Math.abs(x - x1) < 20 && Math.abs(y - y1 ) < 20) {
			clickedButton("player1");
			lastclicked = "player1";
		}
		
		else if (Math.abs(x - x2) < 20 && Math.abs(y - y2 ) < 20) {
			clickedButton("player2");
			lastclicked = "player2";
		}
		else {
			if (y > ((height-width)/2) && y < ((height-width)/2)+width) {
				int tileSize =(int) Math.ceil(width / 9.45);
				int marginSize = (int) Math.ceil(tileSize / 20);
				int tile_x = (int) Math.ceil(x / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((y-((height-width)/2)) / (tileSize+marginSize));
				lastclicked = Integer.toString(tile_x) + " " + Integer.toString(tile_y);
			}
			else if (y < (height-width)/2) {
				lastclicked = "side1";
			}
			else if (y > (height-width)/2+width) {
				lastclicked = "side2";
			}
		} 
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) { 
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) { 
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {

}

}