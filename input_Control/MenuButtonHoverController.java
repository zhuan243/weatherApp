package input_Control;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import application_Windows.Cus_Menu;
import fileManager.AssetManager;



public class MenuButtonHoverController extends MouseAdapter{
	
	private JButton button;
	
	public MenuButtonHoverController(JButton button){
		this.button = button;
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		button.setForeground(Color.green);
		button.setFont(AssetManager.fonts[7]);
	}
	
	
	@Override
	public void mouseExited(MouseEvent e){
		button.setForeground(Color.white);
		button.setFont(AssetManager.fonts[6]);
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		button.setForeground(Color.cyan);
		button.setFont(AssetManager.fonts[6]);
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		button.setForeground(Color.white);
		button.setFont(AssetManager.fonts[6]);
	}

}
