package application_Windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ScrollBarUI;
import fileManager.AssetManager;
import input_Control.SwitchButtonHoverController;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_CitiesInformationScene extends JPanel{

	//setting up private instance variables
	private JLabel title1,title2, cityPic, cityFlag, descriptionTitle;
	private JButton back;
	private int currentImageIndex;
	private ImageIcon[] cityImages;

	//constructor
	public Cus_CitiesInformationScene(String cityName, ImageIcon[] cityImages, String informationText, Window window){

		//introducing background images variable
		this.cityImages = cityImages;
		currentImageIndex = 1;
		
		//setting up home button that will take user back to main menu
		back = new JButton("Back");
		back.setBounds(1080, 620, 100, 30);
		back.addActionListener(e->window.changeComp(PanelID.HoverMap));
		back.setFont(AssetManager.fonts[1]);
		back.setUI(new BasicButtonUI());

		title1 = new JLabel("City Of");
		title1.setFont(AssetManager.fonts[6]);
		title1.setForeground(Color.white);
		title1.setBounds(80, 40, 500, 50);
		
		//setting up name of city that user chooses on CityMap
		title2 = new JLabel(cityName);
		title2.setFont(AssetManager.fonts[6]);
		title2.setForeground(Color.white);
		title2.setBounds(80, 100, 500, 50);
		
		//setting up description header in the panel for the city
		descriptionTitle = new JLabel("City's Description");
		descriptionTitle.setFont(AssetManager.fonts[0]);
		descriptionTitle.setForeground(Color.white);
		descriptionTitle.setBounds(730, 40, 500, 50);

		//pictures of city chosen will rotate as time progresses
		cityPic = new JLabel();
		cityPic.setBounds(80, 200, 600, 450);
		cityPic.setIcon(cityImages[currentImageIndex]);

		//setting up picture for the chosen city flag
		cityFlag = new JLabel();
		cityFlag.setBounds(430, 40, 250, 150);
		cityFlag.setIcon(cityImages[0]);

		//this is where the actual description of each city will be stored; textbox that is not editable
		JTextPane txt = new JTextPane();
		txt.setEditable(false);
		txt.setFont(AssetManager.fonts[5]);
		txt.setForeground(Color.white);
		txt.setText(informationText);
		txt.setOpaque(false);

		//initializing a scroll pane for the text pane so the user can go through the whole description of each city
		JScrollPane scrollPane = new JScrollPane(txt);
		scrollPane.setBounds(730, 120, 450, 480);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUI(new Cus_ScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new Cus_ScrollBarUI());
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getHorizontalScrollBar().setOpaque(false);
		
		//initializing left and right buttons so user can switch to different pictures
		JButton left = makeSwitchButton(AssetManager.leftSwitchButtonImage,-1,80,385,50,80);
		JButton right = makeSwitchButton(AssetManager.rightSwitchButtonImage, 1,630,385,50,80);
		
		//setting layout of the panel and adding components to the panel
		setLayout(null);
		add(left);
		add(right);
		add(back);
		add(cityFlag);
		add(scrollPane);
		add(cityPic);
		add(title1);
		add(title2);
		add(descriptionTitle);
		
	}
	
	//method that sets up the left and right buttons so that the user can switch between images of the same city
	public JButton makeSwitchButton(ImageIcon[] switchType, int num, int x, int y, int width, int height){
		JButton switchButton = new JButton();
		switchButton.setIcon(switchType[0]);
		switchButton.setBounds(x,y,width,height);
		switchButton.setOpaque(false);
		switchButton.setBorderPainted(false);
		switchButton.setUI(new BasicButtonUI());
		switchButton.addMouseListener(new SwitchButtonHoverController(switchButton, switchType));
		switchButton.addActionListener(e->{
			currentImageIndex += num;
			if(currentImageIndex>=cityImages.length){
				currentImageIndex = 1;
			}else if(currentImageIndex <= 0){
				currentImageIndex = cityImages.length - 1;
			}
			cityPic.setIcon(cityImages[currentImageIndex]);
		});
		return switchButton;
	}
	
	//method that draws the images onto the current panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));
		
	}

}



