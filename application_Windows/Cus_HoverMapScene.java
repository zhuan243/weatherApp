package application_Windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

import fileManager.AssetManager;
import input_Control.PinButtonHoverController;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_HoverMapScene extends JPanel{

	//Set variables for a JLabel and a Window
	private JLabel title;
	private Window window;
	
	//Constructor for the hoverMap
	public Cus_HoverMapScene(Window window){
		//create variable so window can be used
		this.window = window;
		
		//create and set title to "Map of Canada"
		title = new JLabel("Map of Canada");
		title.setFont(AssetManager.fonts[7]);
		title.setForeground(Color.white);
		title.setBounds(150, 20, 350, 100);

	
		//Creates label for the names of the specific cities, uses makePinButtonLabel to reduce code. Color adjusted to pins.
		JLabel whitehorseButtonLabel = makePinButtonLabel("Whitehorse",Color.white,175,195,200,50);
		JLabel vancouverButtonLabel =  makePinButtonLabel("Vancouver",Color.blue,175,370,200,50);
		JLabel iqaluitButtonLabel =  makePinButtonLabel("Iqaluit",Color.red,750,210,200,50);
		JLabel charlottetownButtonLabel =  makePinButtonLabel("Charlottetown",Color.green,920,407,200,50);
		JLabel markhamButtonLabel =  makePinButtonLabel("Markham",Color.yellow,750,500,200,50);
		
		//Buttons are created for specific cities. Images of pins are called from AssetManager. Uses makePinButton to reduce code.
		JButton whitehorseButton = makePinButton(AssetManager.whitePin,whitehorseButtonLabel, 200,235,38,53, PanelID.WhitehorsePage);
		JButton vancouverButton = makePinButton(AssetManager.bluePin,vancouverButtonLabel, 200,410,38,53, PanelID.VancouverPage);
		JButton iqaluitButton = makePinButton(AssetManager.redPin, iqaluitButtonLabel, 755,250,38,53, PanelID.IqaluitPage);
		JButton charlottetownButton = makePinButton(AssetManager.greenPin,charlottetownButtonLabel, 960,447,38,53, PanelID.CharlottetownPage);
		JButton markhamButton = makePinButton(AssetManager.yellowPin,markhamButtonLabel, 770,540,38,53, PanelID.MarkhamPage);
		
		//Back button to return to menu
		JButton back = new JButton("Back");
		back.setBounds(1080, 620, 100, 30);
		back.setFont(AssetManager.fonts[1]);
		back.setUI(new BasicButtonUI());
		back.addActionListener(e->window.changeComp(PanelID.Menu)); //adding the function of the button
		
		setLayout(null); //Absolute layout
		
		//Adding the cities' name labels and pin images to the JPanel
		add(whitehorseButton);
		add(vancouverButton);
		add(iqaluitButton);
		add(charlottetownButton);
		add(markhamButton);
		add(whitehorseButtonLabel);
		add(vancouverButtonLabel);
		add(iqaluitButtonLabel);
		add(charlottetownButtonLabel);
		add(markhamButtonLabel);
		add(back);
		//add title (label) to JPanel
		add(title);

	}

	//paint component: method to draw background and
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));
		g2d.drawImage(AssetManager.hoverMap_Map, Window.WIDTH/2-500, 0, this);
	}
	//The construction of the pin name labels
	private JLabel makePinButtonLabel(String text,Color color, int x, int y, int width, int height){
		JLabel pinButtonLabel = new JLabel(text);
		pinButtonLabel.setBounds(x, y, width, height);
		pinButtonLabel.setOpaque(false);
		pinButtonLabel.setFont(AssetManager.fonts[5]);
		pinButtonLabel.setForeground(color);
		pinButtonLabel.setVisible(false);
		return pinButtonLabel;
	}
	//the construction of the pin buttons
	private JButton makePinButton(ImageIcon icon, JLabel associatedLabel, int x, int y, int width, int height, PanelID changeTo){
		JButton pinButton = new JButton();
		pinButton.setIcon(icon);
		pinButton.setBounds(x, y, width, height);
		pinButton.setOpaque(false);
		pinButton.setContentAreaFilled(false);
		pinButton.setBorderPainted(false);
		pinButton.addActionListener(e->window.changeComp(changeTo)); //set the button when clicked, to change to the associated city frame.
		pinButton.addMouseListener(new PinButtonHoverController(associatedLabel)); //Only when hovering over button, label shows up.
		return pinButton;

	}

}
