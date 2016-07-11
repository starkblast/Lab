package lab;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MotionMouse implements MouseMotionListener {

    public Point pointEnd = null;
	
	@Override
	public void mouseDragged(MouseEvent e) {
        pointEnd = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        pointEnd = e.getPoint();

	}

}
