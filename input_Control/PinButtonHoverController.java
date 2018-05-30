package input_Control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import fileManager.AssetManager;



public class PinButtonHoverController extends MouseAdapter{
	
	private JLabel label;
	
	public PinButtonHoverController(JLabel label){
		this.label = label;
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		label.setVisible(true);
	}
	
	
	@Override
	public void mouseExited(MouseEvent e){
		label.setVisible(false);
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		label.setVisible(false);
	}
	
}
