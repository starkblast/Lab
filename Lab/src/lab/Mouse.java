package lab;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Mouse implements MouseListener {
	/*public Game game;
	class Mouse(Game game) {
		this.game = game;
	}*/
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	public String lastclicked;
	
	public void getPlayerPosition(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public String clickedButton(String button) {
		return button;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) { 
		int x = arg0.getX();
		int y = arg0.getY();

		if (Math.abs(x - x1) < 20 && Math.abs(y - y1 ) < 20) {
			clickedButton("player1");
			lastclicked = "player1";
		}
		
		if (Math.abs(x - x2) < 20 && Math.abs(y - y2 ) < 20) {
			clickedButton("player2");
			lastclicked = "player2";
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) { 
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) { }
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {

}

}