package application_Windows;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import fileManager.AssetManager;
import object_Controller.MassHandler;
import objects.City;
import objects.Loading;
import objects.Map;

public class Cus_HeatMap_Control extends JPanel{

	//setting up private instance variables
	private Thread calculateThread;

	private JTextField requestX;
	private JTextField requestY;

	//constructor
	public Cus_HeatMap_Control(Window window, Map canadaMap,MassHandler handler, Loading loading){
		setLayout(null);
		setOpaque(false);
		setBackground(Color.black);

		//initializing labels that contain "year, month, day"
		JLabel yearLabel = createLabel("Year:",30,20,50,30);
		JLabel monthLabel = createLabel("Month:",30,55,50,30);
		JLabel dayLabel = createLabel("Day:",30,90,50,30);

		//setting up areas where the user can type their specified year, month, and date
		JTextField requestYear = createTextArea(125,20,100,30,4);
		JTextField requestMonth =createTextArea(125,55,100,30,2);
		JTextField requestDay = createTextArea(125,90,100,30,2);

		//setting up the button that will commence the calculations once the user has entered their specified data
		JButton calculate = createButton("Calculate",30,140,100,30);
		
		//setting up the labels that contain "x position", "y position", and "temperature" of specified city that the user wishes to search for 
		JLabel xLabel = createLabel("X:",30,225,50,30);
		JLabel yLabel = createLabel("Y:",30,260,50,30);
		JLabel tempLabel = createLabel("Temperature:",30,295,94,30);

		//setting up areas where the user can type their specified city (x and position), and its temperature
		requestX = createTextArea(125,225,100,30,4);
		requestY =createTextArea(125,260,100,30,4);
		JTextField requestTemp =createTextArea(125,295,100,30,2);

		//button where once clicked, will show the specified city that the user wishes to search for on the heat map
		JButton addCity = createButton("Add City",30,335,100,30);
		
		//button that brings user back to main menu
		JButton back = createButton("Back",30,370,100,30);

		//add components to the main window
		add(yearLabel);
		add(monthLabel);
		add(dayLabel);

		add(requestYear);
		add(requestMonth);
		add(requestDay);

		add(calculate);
		add(back);

		add(xLabel);
		add(yLabel);
		add(tempLabel);

		add(requestX);
		add(requestY);
		add(requestTemp);

		add(addCity);

		//add an action listener to calculate so that once clicked, the calculations for the specified city will begin
		calculate.addActionListener(e->{
			calculate.setEnabled(false);
			addCity.setEnabled(false);
			handler.addObject(loading);
			initCalculateThread(requestYear, requestMonth, requestDay, canadaMap, handler, loading, calculate, addCity);
			calculateThread.start();
		});

		//add an action listener to back button so once clicked, user can return to main menu
		back.addActionListener(e->{
			window.changeComp(PanelID.Menu);
		});

		//add an action listener to addCity button once clicked, the specified city will be added and shown on the new heat map
		addCity.addActionListener(e->{
			calculate.setEnabled(false);
			addCity.setEnabled(false);
			handler.addObject(loading);
			initAddCityThread(canadaMap, handler, loading, calculate, addCity, requestTemp);
			calculateThread.start();
		});
	}
	
	//method that creates a general basis for all buttons on this window
	private JButton createButton(String text, int x, int y, int width, int height ){
		JButton button = new JButton(text);
		button.setBounds(x,y,width,height);
		button.setFont(AssetManager.fonts[1]);
		button.setForeground(Color.black);
		button.setUI(new BasicButtonUI());
		return button;
	}
	
	//method that reinitializes a thread that will add a city to the array and recalculate
	private void initAddCityThread(Map canadaMap, MassHandler handler, Loading loading, JButton calculate, JButton addCity,JTextField requestTemp){
		calculateThread = new Thread(){
			public void run() {
				Integer xPosition = null;
				Integer yPosition = null;
				Float cityTemp = null;
				try{
					xPosition = Integer.parseInt(requestX.getText());
				} catch(NumberFormatException e){
					requestX.setText("Wrong Input");
				}

				try{
					yPosition = Integer.parseInt(requestY.getText());
				} catch(NumberFormatException e){
					requestY.setText("Wrong Input");
				}

				try{
					cityTemp = Float.parseFloat(requestTemp.getText());
				} catch(NumberFormatException e){
					requestTemp.setText("Wrong Input");
				}

				if(xPosition!=null&&yPosition!=null&&cityTemp!=null){
					canadaMap.getCities().add(new City("Test City",xPosition,yPosition,cityTemp));
					canadaMap.refreshColor();
				}
				
				handler.removeObject(loading);
				calculate.setEnabled(true);
				addCity.setEnabled(true);

			}
		};
		calculateThread.setDaemon(true);

	}

	//method that reinitializes the cities based on the selected date
	private void initCalculateThread(JTextField requestYear,JTextField requestMonth,JTextField requestDay,
			Map canadaMap, MassHandler handler, Loading loading, JButton calculate, JButton addCity){
		calculateThread = new Thread(){
			public void run() {
				canadaMap.setYear(getText(requestYear));
				canadaMap.setMonth(getText(requestMonth));
				canadaMap.setDay(getText(requestDay));
				canadaMap.refreshCities();
				canadaMap.refreshColor();
				handler.removeObject(loading);
				calculate.setEnabled(true);
				addCity.setEnabled(true);
			}
		};
		calculateThread.setDaemon(true);
	}

	//method that takes in user input and checks for appropriate format
	private int getText(JTextField TextArea){
		int result;
		try{
			result = Integer.parseInt(TextArea.getText());
		}catch(Exception e){
			result = 0;
		}
		return result;
	}
	
	//method that creates a general basis for all labels on this window
	private JLabel createLabel(String text,int x, int y, int width, int height){
		JLabel label = new JLabel(text);
		label.setBounds(x,y,width,height);
		label.setFont(AssetManager.fonts[1]);
		label.setForeground(Color.white);
		return label;
	}

	//method that creates a general basis for all text areas on this window
	private JTextField createTextArea(int x, int y, int width, int height, int wordLimit){
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		textField.setFont(AssetManager.fonts[1]);
		return textField;
	}

	//setters and getters for x and y positions
	public JTextField getRequestX() {
		return requestX;
	}

	public void setRequestX(JTextField requestX) {
		this.requestX = requestX;
	}

	public JTextField getRequestY() {
		return requestY;
	}

	public void setRequestY(JTextField requestY) {
		this.requestY = requestY;
	}
}







