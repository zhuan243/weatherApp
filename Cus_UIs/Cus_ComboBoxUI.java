package Cus_UIs;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import fileManager.AssetManager;

public class Cus_ComboBoxUI extends BasicComboBoxUI{
	
	//customized combo box. Image of arrow called up from AssetManager
	@Override    
	protected JButton createArrowButton() {
		JButton button = new JButton(AssetManager.dropDownArrow);
		button.setBackground(Color.white);	
		return button;
	}
	

}
