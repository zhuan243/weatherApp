package input_Control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import fileManager.AssetManager;



public class CityButtonHoverController extends MouseAdapter{
	
	private JButton button;
	private JLabel label;
	private ImageIcon[] buttonImage;
	
	public CityButtonHoverController(JButton button, JLabel label, ImageIcon[] buttonImage){
		this.button = button;
		this.label = label;
		this.buttonImage = buttonImage;
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		button.setBorderPainted(true);
		button.setIcon(buttonImage[1]);
		label.setIcon(AssetManager.cityIconArrow_green);
	}
	
	
	@Override
	public void mouseExited(MouseEvent e){
		button.setBorderPainted(false);
		button.setIcon(buttonImage[0]);
		label.setIcon(AssetManager.cityIconArrow_white);
	}
	
	
}
