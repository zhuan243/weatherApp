package application_Windows;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ComboBoxUI;
import fileManager.AssetManager;
import input_Control.CityButtonHoverController;
import methods.Method;

public class Cus_PieChartScene extends Cus_PieChart{

	private JComboBox<String> select;
	private JLabel title;
	private JPanel panel1;
	private JPanel panel2;
	private String cityName;
	private String dataType;
	private String[] topics = { "Mean Temperature", "Minimum Temperature", "Maximum Temperature", "Heat Days", "Cool Days", "Total Rain", "Total Snow", "Total Percipitation", "Snow On Ground", "Wind Speed" };

	public Cus_PieChartScene(Window window){

		panel1 = new JPanel();
		panel1.setBounds(50, 0,500,Window.HEIGHT);
		panel1.setOpaque(false);
		panel1.setLayout(null);

		JPanel indicator = new JPanel();
		indicator.setOpaque(false);
		indicator.setLayout(new GridLayout(5,1));
		indicator.setBounds(20, 50, 100, Window.HEIGHT-100);
		
		JPanel controller = new JPanel();
		controller.setOpaque(false);
		controller.setLayout(new GridLayout(5,1));
		controller.setBounds(100, 50, 400, Window.HEIGHT-100);
		
		panel2 = new JPanel();
		panel2.setBounds(570,0,1000,Window.HEIGHT);
		panel2.setOpaque(false);
		panel2.setLayout(null);

		title = new JLabel();
		title.setForeground(Color.WHITE);
		title.setText("Mean Temperature of Markham");
		title.setFont(AssetManager.fonts[0]);
		title.setBounds(110,0,1000,100);

		
		JLabel iqaluit_Label = new JLabel(AssetManager.cityIconArrow_white);
		JLabel vancouver_Label = new JLabel(AssetManager.cityIconArrow_white);
		JLabel charlottetown_Label = new JLabel(AssetManager.cityIconArrow_white);
		JLabel whitehorse_Label = new JLabel(AssetManager.cityIconArrow_white);
		JLabel markham_Label = new JLabel(AssetManager.cityIconArrow_white);

		JButton iqaluit = makeButton(AssetManager.iqaluit,"Iqaluit", iqaluit_Label);
		JButton vancouver = makeButton(AssetManager.vancouver,"Vancouver",vancouver_Label);
		JButton charlottetown = makeButton(AssetManager.charlottetown,"Charlottetown",charlottetown_Label);
		JButton whitehorse = makeButton(AssetManager.whitehorse,"Whitehorse",whitehorse_Label);
		JButton markham = makeButton(AssetManager.markham,"Markham",markham_Label);
		
		
		
		JButton back = new JButton("Back");
		back.setFont(AssetManager.fonts[1]);
		back.setUI(new BasicButtonUI());
		back.addActionListener(e->window.changeComp(PanelID.Menu));
		back.setBounds(450,650,100,30);
		
		
		select = new JComboBox<>(topics);
		select.setSelectedIndex(0);
		select.addItemListener(e->{
			dataType=(String) select.getSelectedItem();
			change();
		});
		select.setFont(AssetManager.fonts[1]);
		select.setUI(new Cus_ComboBoxUI());
		select.setBounds(100,650,300,30);

		cityName = "Markham";
		dataType = (String) select.getSelectedItem();
	
		
		indicator.add(iqaluit_Label);
		controller.add(iqaluit);
		indicator.add(vancouver_Label);
		controller.add(vancouver);
		indicator.add(charlottetown_Label);
		controller.add(charlottetown);
		indicator.add(whitehorse_Label);
		controller.add(whitehorse);
		indicator.add(markham_Label);
		controller.add(markham);
		
		panel1.add(indicator);
		panel1.add(controller);
		
		panel2.add(title);
		panel2.add(select);
		panel2.add(back);
		
		setLayout(null);

		
		add(panel1);
		add(panel2);

		change();

	}

	private JButton makeButton(ImageIcon[] buttonImage, String changeToCity, JLabel label){
		JButton button = new JButton(buttonImage[0]);
		button.setOpaque(false);
		button.setUI(new BasicButtonUI());
		button.addActionListener(e->{
			cityName= changeToCity;
			change();
		});
		button.setBorderPainted(false);
		button.addMouseListener(new CityButtonHoverController(button, label, buttonImage));
		return button;
	}


	private void change(){
		this.changeCity(cityName,dataType);

		if (dataType.equalsIgnoreCase("Mean Temperature")){
			title.setText("Mean Temperature of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Minimum Temperature")){
			title.setText("Minimum Temperature of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Maximum Temperature")){
			title.setText("Maximum Temperature of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Heat Days")){
			title.setText("Heat Days of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Cool Days")){
			title.setText("Cool Days of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Total Rain")){
			title.setText("Total Rain of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Total Snow")){
			title.setText("Total Snow of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Total Percipitation")){
			title.setText("Total Precipitation of "+ cityName);
		}else if (dataType.equalsIgnoreCase("Snow On Ground")){
			title.setText("The Amount of Snow on the Ground in "+ cityName);
		}else if (dataType.equalsIgnoreCase("Wind Speed")){
			title.setText("Speed of Wind of "+ cityName);
		}

		panel1.repaint();
	}

}
