package input_Control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import fileManager.AssetManager;



public class SwitchButtonHoverController extends MouseAdapter{
	
	private JButton button;
	private ImageIcon[] buttonImage;
	
	public SwitchButtonHoverController(JButton button, ImageIcon[] buttonImage){
		this.button = button;
		this.buttonImage = buttonImage;
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		button.setIcon(buttonImage[1]);
	}
	
	
	@Override
	public void mouseExited(MouseEvent e){
		button.setIcon(buttonImage[0]);
	}
	
	
}
