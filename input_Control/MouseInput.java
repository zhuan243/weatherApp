package input_Control;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import objects.Map;



public class MouseInput extends MouseAdapter{
	
	
	private Map canadaMap;
	private JTextField requestX;
	private JTextField requestY;
	
	public MouseInput(Map canadaMap,JTextField requestX, JTextField requestY){
		this.canadaMap = canadaMap;
		this.requestX = requestX;
		this.requestY = requestY;
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		requestX.setText(Integer.toString(e.getX()-257));
		requestY.setText(Integer.toString(e.getY()));
	}
}
