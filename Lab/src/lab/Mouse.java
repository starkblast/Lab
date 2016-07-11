package lab;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;


import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;

class Mouse implements MouseListener {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int width;
	private int height;
	public String lastclicked;
	public Point Start = null;
	
	public void getPlayerPosition(int x1, int y1, int x2, int y2, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { 

		int x = arg0.getX();
		int y = arg0.getY();

		if (Math.abs(x - x1) < 20 && Math.abs(y - y1 ) < 20) {   //player1 location
			lastclicked = "player1";
		}
		
		else if (Math.abs(x - x2) < 20 && Math.abs(y - y2 ) < 20) {   //player2 location
			lastclicked = "player2";
		}
		else { // gets coordinates of clicked tile
			if (y > ((height-width)/2) && y < ((height-width)/2)+width) {
				int tileSize =(int) Math.ceil(width / 9.45);
				int marginSize = (int) Math.ceil(tileSize / 20);
				int tile_x = (int) Math.ceil(x / (tileSize+marginSize));
				int tile_y = (int) Math.ceil((y-((height-width)/2)) / (tileSize+marginSize));
				lastclicked = Integer.toString(tile_x) + " " + Integer.toString(tile_y);
			}
			else if (y < (height-width)/2) { // checks if sides are clicked
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
	public void mousePressed(MouseEvent arg0) { // checks if wall-grid-markers are clicked or at least almost clicked
		//Start = arg0.getPoint();
		int x = arg0.getX();
		int y = arg0.getY();
		int tileSize =(int) Math.ceil(width / 9.45);
		int marginSize = (int) Math.ceil(tileSize / 20);
		int tile_x = (int) Math.ceil(x / (tileSize+marginSize));
		int tile_y = (int) Math.ceil((y-((height-width)/2)) / (tileSize+marginSize))+1;
		if (Math.abs(tile_x * (tileSize+marginSize) - x) <= 10 && tile_y * (tileSize + marginSize) - ((height-width)/2) - y <= 10) {
			if (y > (height-width)/2 && y < (height-width)/2 + width)
				Start = new Point(tile_x * (tileSize+marginSize)-2, tile_y*(tileSize+marginSize)+((height-width)/2)-2);
		}
		else 
			Start = null; 
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Start = null;
	}
  

}