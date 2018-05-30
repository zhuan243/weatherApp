package input_Control;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class FrameMouseDragController extends MouseAdapter{

	
	private Point mouseDownCompCoords;
	private JFrame frame;
	
	public FrameMouseDragController(JFrame frame){
		this.frame = frame;
	}
	  
	public void mouseReleased(MouseEvent e) {
		mouseDownCompCoords = null;
	}
	public void mousePressed(MouseEvent e) {
		mouseDownCompCoords = e.getPoint();
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Point currCoords = e.getLocationOnScreen();
		frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
	}

}
