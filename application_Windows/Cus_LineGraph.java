package application_Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ComboBoxUI;
import Cus_UIs.Cus_SliderUI;
import fileManager.AssetManager;
import fileManager.CityDailyWeather;
import fileManager.FileDownloader;
import fileManager.FileManipulator;
import methods.Method;
import object_Controller.BackgroundControl;


public class Cus_LineGraph extends JPanel{

	//initialize all the private instances
	private String title; //a string that will hold the title to be display, it is stored in a string because the title might change

	private int width = 1000; //set the width of the actual line graph
	private int height = 400; //set the height of the actual line graph
	private int startX = (Window.WIDTH -width)/2 ; //set the start position X of the line graph
	private int startY = (Window.HEIGHT -height)/2 - 70; //set the end position Y of the line graph

	private TreeMap<String, Float> data; //declare a treeMap that will hold all data
	private TreeMap<String, Float> draw; //declare a treeMap that will hold all data that will be drawn
	private ArrayList<String> keySetDate; //declare an arrayList that will contain all the date needed to be drawn

	private TreeMap<String, Float> totalValue; //set a treeMap that will be used to calculate the monthly average
	private TreeMap<String, Integer> totalCounter; //set a treeMap that will be used to calculate the monthly average

	private float minY; //a float variable that will be used to store the minimum y value of the data set
	private float maxY; //a float variable that will be used to store the maximum y value of the data set

	private float yTotalIndex = 15; //set the total y_index to be 15
	private float yIndexDistance = height/yTotalIndex; //calculate the distance between each y labels
	private float yMinorIndexDistance = yIndexDistance/5; //calculate the the minor distance
	private float yValueDiff; //a float variable used to store the difference between y variables
	private float yScale; // a float variable used to store the scaling of y axis

	private float minX; // a float variable used to store the minimum value of x;
	//private float maxX;
	private float xTotalIndex; //a float variable used to store the total index of x will be calculated and initialized later
	private float xIndexDistance; //a float variable used to store the distance between x Labels
	private float xValueDiff; //a float variable sued to store the difference between x variables

	//2 transform that will be used to rotate and then set the position back to normal
	private AffineTransform normal = new AffineTransform(); 
	private AffineTransform rotate = new AffineTransform();

	private JSlider sliderMonth; //the slider for the month
	private JSlider sliderYear; //the slider for the year

	private JLabel month; //a JLabel to indicate which slider is which
	private JLabel year; //a JLabel to indicate which slider is which

	private JRadioButton monthly; //a JRadioButton that allow the user to select to display monthly data
	private JRadioButton yearly;//a JRadioButton that allow the user to select to display yearly data

	private ButtonGroup group; //JRadioButton are put into this group, so that ont can be selected each time

	private JButton back; // a button that allow the user to go back to main menu

	private JComboBox<String> citySelection;  //a JComboBox that allow the user to select the city type

	private JComboBox<String> dataSelection; //a JComboBox that allow the user to select the data type

	private FileDownloader fileDownloader; //reference to the fileDownloader so that it can be used in other methods within the class

	private JPanel controls; //a control panel that will hold all the controls conponents

	//Constructor method
	public Cus_LineGraph(Window window, FileDownloader fileDownloader){
		this.fileDownloader = fileDownloader; //initialize the fileDownloader


		//initialize all the data structures that will be used to hold the data
		draw = new TreeMap<String, Float>(); 
		keySetDate = new ArrayList<String>();
		totalValue = new TreeMap<String, Float>();
		totalCounter = new TreeMap<String, Integer>();

		controls = new JPanel(); //initialize the control panel
		controls.setLayout(null); //set the layout of the control panel to be null
		controls.setBounds(startX, startY+height+70, width, 150); //set the bound


		//some sliders, labels, buttons are created with a method, because usually two or more of the same type is needed
		sliderMonth = makeSlider(1,12,1,1,80,0+10,600,50); // make the slider for the month control
		sliderYear = makeSlider(0,0,0,1,80-6,50+10,600+13,50); //make the slider for the year control

		month = makeLabel("Month:",0,0,100,50); //make the month label with its text, and the bounds position
		year = makeLabel("Year:",0,50,100,50); //make the year label with its text, and the bounds position

		monthly = makeRadioButton("Monthly",true, 720,20,80,25); //make the monthly radioButtons, it is on default selected
		yearly = makeRadioButton("Yearly",false, 820,20,80,25); //make the yearly radioButtons, it is on default not selected

		//initialize the button group and add button month and yearly radioButtons to the group
		group = new ButtonGroup();
		group.add(monthly);
		group.add(yearly);

		back = new JButton("Back"); //initialize the JButton back with its text
		back.addActionListener(e->window.changeComp(PanelID.Menu)); //add the functionality to the button
		back.setBounds(920,20,80,30);//set the bounds of the back button
		back.setFont(AssetManager.fonts[1]); //set the font of the button
		back.setUI(new BasicButtonUI()); //remove the default blue background

		citySelection = dataSelection(FileManipulator.cities, 720,65,130,30); //create the combo box based on the array of cities

		//the array of data types that will be displayed and allowed to be chosen in the comboBox
		final String[] dataForms = {
				"Max Temp", "Min Temp","Mean Temp",
				"Heat Days","Cool Days",
				"Total Rain","Total Snow",
				"Total Precip","Snow On Ground",
				"Direc Of Gust","Speed Of Gust"};
		dataSelection =  dataSelection(dataForms, 870,65,130,30); //create the combo box based on the data types


		//add all controls components to the control panel
		controls.add(month);
		controls.add(sliderMonth);
		controls.add(year);
		controls.add(sliderYear);
		controls.add(monthly);
		controls.add(yearly);
		controls.add(back);
		controls.add(citySelection);
		controls.add(dataSelection);
		//refresh the control panel so that everything is up to date and display properly
		controls.repaint(); 
		controls.revalidate();

		controls.setOpaque(false); //set the controls panel to be transparent

		setLayout(null); //set the layout of the main panel to be null
		add(controls); //add the control panel to the main panel

		//set the functionality of all the sliders and buttons etc.
		sliderMonth.addChangeListener(e->{
			//sliderMonth will get and refresh the data and then repaint;
			getData(String.format("%04d", (int)sliderYear.getValue()),String.format("%02d", (int)sliderMonth.getValue()));
			repaint();

		});

		sliderYear.addChangeListener(e->{
			// sliderYear will get both of the sliderMonth and the sliderYear, and then refresh
			refresh(sliderMonth,sliderYear);
			controls.repaint(); //repaint the slider, so that so overlap thump
		});

		yearly.addActionListener(e->{
			//when yearly is selected, sliderMonth is disabled
			sliderMonth.setEnabled(false);
			//get the data from the sliderYear, and refresh once
			getData(String.format("%04d", (int)sliderYear.getValue()));
			repaint();
		});

		monthly.addActionListener(e->{
			//when monthly is selected
			sliderMonth.setEnabled(true); //make sure that sliderMonth is enabled
			//get the data from both sliderYear, and sliderMonth, and refresh the paintComponent
			getData(String.format("%04d", (int)sliderYear.getValue()),String.format("%02d", (int)sliderMonth.getValue()));
			repaint();
		});

		dataSelection.addActionListener(e->{
			// when data type comboBox changed selection
			changeData(dataSelection.getSelectedIndex(),(String) citySelection.getSelectedItem()); //change the data type based on the selected item
			refresh(sliderMonth,sliderYear); //refresh based on selected year and month
		});

		citySelection.addActionListener(e->{
			//when city type comboBox changed selection
			refreshSlider((String) citySelection.getSelectedItem(), sliderYear); //refresh the slider so that it is the correct range of data
			changeData(dataSelection.getSelectedIndex(),(String) citySelection.getSelectedItem()); //change the data type based on the selected item
			refresh(sliderMonth,sliderYear);//refresh based on selected year and month
		});


		//when the line graph is first made, calculate the refresh the scene so that one graph will show initially
		refreshAll(sliderMonth, sliderYear, citySelection, dataSelection);

	}

	//method used to create the dataSelection comboBox
	private JComboBox<String> dataSelection(String[] array, int x, int y, int width, int height){
		JComboBox<String> comboBox = new JComboBox<>(array); //initialize the comboBox based on the array of wanted items
		comboBox.setBounds(x,y,width,height); //set the bound
		comboBox.setSelectedIndex(0); //set the default selected index
		comboBox.setUI(new Cus_ComboBoxUI()); //set the UI to be a customized one
		comboBox.setFont(AssetManager.fonts[1]); //set the font
		return comboBox; //return the comboBox
	}


	//method used to create the JRadioButton
	private JRadioButton makeRadioButton(String text, boolean init, int x, int y, int width, int height){
		JRadioButton radioButton = new JRadioButton(text,init); //true is checked, false is unchecked, initialize on for the button and the text
		radioButton.setBounds(x,y,width,height); //set the bounds
		radioButton.setFont(AssetManager.fonts[1]); //set the font
		radioButton.setForeground(Color.white); //set the font color to white
		radioButton.setOpaque(false); //set it to be transparent
		radioButton.setFocusable(false); //set it so that it can not be focused
		return radioButton; //return the button
	}


	//method used to create the JLabel
	private JLabel makeLabel(String text, int x, int y, int width, int height){
		JLabel label = new JLabel(text); //initliaze the label based on its text
		label.setBounds(x,y,width,height); //set the bounds
		label.setFont(AssetManager.fonts[1]); //set the font
		label.setForeground(Color.white); //set the font color to be white
		return label;//return the label
	}

	//method used to create the JSlider
	private JSlider makeSlider(int min, int max, int value, int space, int x, int y, int width, int height){
		JSlider slider = new JSlider(); //initialize the slider
		slider.setMinimum(min); //set the minimum value of the slider
		slider.setMaximum(max); //set the maximum value of the slider
		slider.setValue(value); //set the default selected value of the slider
		slider.setMajorTickSpacing(space); //set the major tick spacing
		slider.setPaintTicks(true); //set the ticks to be painted
		slider.setPaintLabels(true); //set the labels to be painted
		slider.setBounds(x,y,width,height); //set the bounds
		slider.setFont(AssetManager.fonts[4]); //set the font
		slider.setOpaque(false); //set it to be transparent
		slider.setForeground(Color.white); //set the font color to be white

		//set the UI later because the slider UI have to use static variabels for the slider, if the slider is not yet created, it will show an error 
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				slider.setUI(new Cus_SliderUI(slider)); //later set the UI to be the customized slider UI
			}
		});
		return slider; //return the slider
	}

	//a method that will refresh based on everything
	private void refreshAll(JSlider sliderMonth, JSlider sliderYear, JComboBox<String> citySelection, JComboBox<String> dataSelection){
		changeData(dataSelection.getSelectedIndex(),(String) citySelection.getSelectedItem()); //changed the data based on the data type selected, and city type selected
		this.title = (String) dataSelection.getSelectedItem(); //set the title to be the data selected
		refresh(sliderMonth,sliderYear); //refresh the data based on the slider value of the month and the year
		refreshSlider((String) citySelection.getSelectedItem(), sliderYear); //refresh the slider to make sure that is selecting the correct data range
	}

	//a method that will refresh the year slider based on the data range of the city
	private void refreshSlider(String city, JSlider sliderYear){
		for(CityDailyWeather citiesDataRange: fileDownloader.getDownloadData()){ //go through all the city and find the correct one
			if(citiesDataRange.getCity().equals(city)){ //is the city is the one that is selected
				sliderYear.setMinimum(citiesDataRange.getStartYear()); //set the minimum value of the slider to the start year of the data of the city
				sliderYear.setMaximum(citiesDataRange.getEndYear());//set the maximum value of the slider to the end year of the data of the city
				//sliderYear.setValue(citiesDataRange.getStartYear());
				int yearRange = citiesDataRange.getEndYear()-citiesDataRange.getStartYear(); //calculate how many years are there

				// to avoid the labels to be crowded and overlapped, calculate the index of the labels so that there will be at most 10 labels present as once
				sliderYear.setLabelTable(sliderYear.createStandardLabels(yearRange/10, citiesDataRange.getStartYear()));

			}
		}
	}

	private void changeData(int index,String city){
		//based on the index selection, try get the correct data type of that city
		if(index==0)
			this.data = FileManipulator.maxTemp.get(city);
		else if(index==1)
			this.data = FileManipulator.minTemp.get(city);
		else if(index==2)
			this.data = FileManipulator.meanTemp.get(city);
		else if(index==3)
			this.data = FileManipulator.heatDays.get(city);
		else if(index==4)
			this.data = FileManipulator.coolDays.get(city);
		else if(index==5)
			this.data = FileManipulator.totalRain.get(city);
		else if(index==6)
			this.data = FileManipulator.totalSnow.get(city);
		else if(index==7)
			this.data = FileManipulator.totalPrecip.get(city);
		else if(index==8)
			this.data = FileManipulator.snowOnGround.get(city);
		else if(index==9)
			this.data = FileManipulator.direcGust.get(city);
		else if(index==10)
			this.data = FileManipulator.speedGust.get(city);
	}

	//refresh the slider based on the 2 slider
	private void refresh(JSlider monitor, JSlider masterSlider){
		if(monitor.isEnabled()) // if monthly slider is enabled
			//get data from both monthly and yearly slider
			getData(String.format("%04d", (int)masterSlider.getValue()),String.format("%02d", (int)monitor.getValue()));
		else // is monthly slider is disabled
			//get the data from yearly slider only
			getData(String.format("%04d", (int)masterSlider.getValue()));

		this.repaint(0,0,Window.WIDTH, startY + height + 100); //repaint only and top part of the panel
	}



	//calculate the total x index of the line graph
	private void findTotalIndexX(){
		minX = 1; // the minimumX will be set to 1
		xTotalIndex = keySetDate.size()-1; // the total will be the size of the date list -1
		//maxX = minX +xTotalIndex;


		if(xTotalIndex>31){
			xValueDiff = 10; //when the data is selected on yearly draw the label every 10 distance apart to avoid crowded labels
		}else{
			xValueDiff = 1;//when the data is selected on monthly draw the label every month
		}

		xIndexDistance = width/xTotalIndex; //calculate the needed distance between each index
	}

	//find and maximum and minimum y of the data set
	private void findMaxMinY(){
		//check if the data that is needed to be drawn is empty or not
		if(!draw.values().isEmpty()){ //if it is not empty
			maxY = Collections.max(draw.values()); //find and maximum value
			minY = Collections.min(draw.values()); //find and minimum value
		}else{ //else if it is empty
			maxY = 0; //set maximum y value to be 0
			minY = 0; //set minimum y value to be 0
		}
		if(maxY==minY){ //if the maximum and minimum is the same (constant relationship)
			maxY+=10; //then give it a +10 maximum data range
			minY-=10; //and -10 minimum data range
		}

		yScale = 13f*yIndexDistance/(maxY-minY); //calculate the scaling factor of the graph
	}

	//method that will find the difference between y values
	private void findValueDiff(){
		float range = maxY - minY; //the range of the data will be maximum - minimum
		yValueDiff = range/(yTotalIndex-2); //calculate the needed difference between y values
	}


	//method that will reset the date need to be drawn
	private void getAllDate(){
		keySetDate.clear(); //clear the original ones
		String startDate; //need a string to store the starting date
		String endDate; //need a string to store the ending date

		if(!draw.isEmpty()){ // if the data needed to be drawn is not empty
			startDate = draw.firstKey(); //startDate is the first key
			endDate = draw.lastKey(); //endData is the last key
		}else{ //else if the data needed to be drawn is empty
			startDate = "0000-00-00";  //reset the start date
			endDate = "0000-00-00"; //reset the ending date
		}

		//if the date is not empty
		if(!startDate.equals("0000-00-00")){
			//go through all the dates and try to find and start date and the end date
			//2 boolean variable to store the current status regarding finding the starting and the ending data
			boolean foundStartDate = false;
			boolean foundEndDate = false;
			//go through all the dates
			for(String dateString: FileManipulator.date){
				if(dateString.startsWith(startDate)) //is the startData is about the same with the starting date
					foundStartDate =true; //indicate that the part of the for loop is included after starting date
				if(foundStartDate && !foundEndDate){ //indicate that the part of the for loop is included before the ending date
					if(startDate.length()==10)  //if the date length is 10 (included the day) then add it to the date list
						keySetDate.add(dateString);
					else if (startDate.length()==7) //if the date length is 7 (not including the day) then add the starting string of the dataString
						if(!keySetDate.contains(dateString.substring(0, 7)))
							keySetDate.add(dateString.substring(0, 7));
				}
				if(dateString.startsWith(endDate)) //if the end date is found, exit out of the for loop
					break;

			}
		}
	}


	//method that will get the data based on only the year
	private void getData(String year){
		draw.clear(); //clear the initial data

		for(String key: data.keySet()){ //for all data in the entire data set
			for(int yearCount = 1; yearCount<=12; yearCount++){ //for loop 12 times to illustrate the 12 months within a year
				String speDateMonth = year+"-"+String.format("%02d", yearCount); //temporailiy store the String of year and month
				if(key.startsWith(speDateMonth)){ //if the key start with the year and the month
					if(totalValue.containsKey(speDateMonth)){ //if the treeMaps already contains the key then add it to the previous value
						totalValue.put(speDateMonth, totalValue.get(speDateMonth)+data.get(key)); //add to the previous value to get the total
						totalCounter.put(speDateMonth, totalCounter.get(speDateMonth)+1); //add 1 so that it gets the total counter of how many days is in that month
					}else{ //if the treeMaps does not already contains the key then initialize it
						totalValue.put(speDateMonth, data.get(key)); //put the key value as the value
						totalCounter.put(speDateMonth, 1); //initialize the counter to 1
					}

				}
			}
		}

		//for every key inside of the total value keySet, get the average
		for(String key: totalValue.keySet()){
			draw.put(key, totalValue.get(key)/totalCounter.get(key)); //total/counter=average
		}

		//initialize everything else
		init();

		//clear the totalValue and totalCounter so that it can be used correctly later
		totalValue.clear();
		totalCounter.clear();
	}


	//get the data based on the year and the month
	private void getData(String year, String month){
		draw.clear(); //clear the data before
		//for every key inside of the entire data's keyset
		for(String key: data.keySet()){
			if(key.startsWith(year+"-"+month)){ //if the target key start with the correct year and the correct month
				draw.put(key, data.get(key)); //put the data into the data that need to drawn
			}
		}
		//initialize everythine else
		init();

	} 

	private void init(){
		getAllDate(); //get all the date needed
		findMaxMinY(); //find the maximum minimum and scaling of the y axis
		findValueDiff(); //find and difference between the values
		findTotalIndexX(); //find the total index of x, and the distance between each index
	}

	//draw the stuffs
	@Override
	public void paintComponent(Graphics g){
		//get the actual title that need to be drawn
		this.title = String.format("%s Of %s In %4d", dataSelection.getSelectedItem(), citySelection.getSelectedItem(), sliderYear.getValue());
		//is monthly is selected add the selected month to the title as well
		if(monthly.isSelected())
			this.title += String.format("-%02d", sliderMonth.getValue());
		//call super method and draw the component
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g; //cast graphics to 2D graphics

		//draw the images, the line graph, the coordinates, the axis and grid
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null); //draw the background image
		g2d.setColor(Color.black); //set the color to black
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer)); //set the transparency of the next component
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT); //draw a dark layer on top of the background image
		g2d.setComposite(Method.makeTransparent(1)); //set the transparency back to opaque



		g2d.setColor(Color.white); //set the color to white
		//g2d.drawRect(startX, startY, width, height);
		g2d.drawLine(startX, startY, startX, startY+height); //draws the basic x axis 
		g2d.drawLine(startX, startY+height, startX+width, startY+height); //draws the basic y axis


		g2d.setFont(AssetManager.fonts[0]); //set the font
		g2d.drawString(title, startX+width/2-title.length()*7, startY-20); //draws the title with that font
		g2d.setFont(AssetManager.fonts[1]); //set the font


		rotate.setToRotation(Math.toRadians(-90), startX-70, startY+title.length()*7); //set the rotation coordincates, based on the coordinate of the feature
		normal = g2d.getTransform(); //get the current transform position so that it can be transfrom back later
		g2d.setTransform(rotate); //set the transfrom to the rotated position

		g2d.drawString(title, startX-70, startY+title.length()*7); //draw the title again to indicate what the y-axis will be
		g2d.setTransform(normal);//set the transfrom beck to te normal

		g2d.drawString("Date", startX +width+30, startY+height+20); //draw the date which is an indication of what the x-axis will be
		g2d.setFont(AssetManager.fonts[2]); //set the font

		//draws the y-axis
		for(int i = 1; i<=yTotalIndex; i++){
			int currentY =(int)(startY+height-yIndexDistance*i); //calculate the currentY index needed
			g2d.setColor(Color.white); //set the color to white
			g2d.setStroke(AssetManager.strokes[1]); //set the stoke
			g2d.drawLine(startX, currentY, startX+width, currentY); //draw a line to indicate the major tick
			//convert the value to a string and then draw in on the screen
			g2d.drawString(Double.toString(Math.round((minY + yValueDiff*(i-1))*10.0)/10.0), startX-37, currentY+5);
			for(int j=0; j<=4;j++){ //for loop to draw the minor ticks
				g2d.setColor(Color.white); //set the color to white
				if(j==0){ //first line
					g2d.setStroke(AssetManager.strokes[2]); //set the stroke
					//draw the line
					g2d.drawLine(startX, (int)(currentY+j*yMinorIndexDistance), startX-7, (int)(currentY+j*yMinorIndexDistance));
				}else{
					//other lines will be for the minor ticks
					g2d.drawLine(startX, (int)(currentY+j*yMinorIndexDistance), startX-3, (int)(currentY+j*yMinorIndexDistance));
					g2d.setColor(Color.black); //set the color to black
					g2d.setStroke(AssetManager.strokes[3]); //set the stroke
					//with a different stroke draw the string again downward to make the tick stand out
					g2d.drawLine(startX, (int)(currentY+j*yMinorIndexDistance), startX+width, (int)(currentY+j*yMinorIndexDistance));
				}
			}
		}

		//use a for loop to draw all the x-axis
		for(int i = 0; i<=keySetDate.size()-1; i++){
			int currentX =(int)(startX+xIndexDistance*i); //calculate the currentX value of the label and lines
			g2d.setColor(Color.white); //set thee color to white
			g2d.setStroke(AssetManager.strokes[1]);  //set the stroke

			// indicate if it is a major tick or not
			if(i%(int)xValueDiff==0){ //if it is a major tick
				g2d.setColor(Color.white); //set the color to white
				g2d.drawLine(currentX, startY, currentX, startY +height); //draw the line that help the user to read the data
				rotate.setToRotation(Math.toRadians(-90), currentX+5, startY+height+60); //set the rotation to 90 degrees to draw the date labels
				g2d.setTransform(rotate); //set the transfrom to rotated to the target position
				g2d.drawString(keySetDate.get(i), currentX+5, startY+height+60); //draw the string of the date
				g2d.setTransform(normal); //set thr transform back to normal
			}
		}

		g2d.setStroke(AssetManager.strokes[0]); //set the stroke

		// have a temporaily variable that will store the previous dot to connect the lines
		Integer beforeXCoord = null;  
		Integer beforeYCoord = null;

		//for every key in the key set
		for(String key: draw.keySet()){
			float xValue = keySetDate.indexOf(key)+1; //get the xValue
			float yValue = draw.get(key); //get the yValue

			float xCoord = ((xValue - minX) *xIndexDistance) +startX; //calculate the x position based on the x value
			float yCoord = startY+height-yIndexDistance-(yValue-minY)*yScale; //calculate thee y position based on the y value
			g2d.setColor(Color.white); //set the color to white
			if( draw.keySet().size()<=31){ //if the draw data is out monthly
				g2d.drawRect((int)xCoord-4, (int)yCoord-4, 8, 8); //biggger coordinates
			}else{ //if the draw data is out yearly
				g2d.drawRect((int)xCoord-3, (int)yCoord-3, 6, 6); //smaller coordinates
			}
			
			//if it does have the before coordinates
			if (beforeXCoord !=null&&beforeYCoord !=null){
				//if the distance is higher than 1
				if(Math.floor(xCoord-beforeXCoord-xIndexDistance)>=1){
					g2d.setStroke(AssetManager.strokes[4]); //draw doted lines
				}
				g2d.drawLine(beforeXCoord, beforeYCoord, (int)xCoord, (int)yCoord); //connect the lines
				g2d.setStroke(AssetManager.strokes[0]); //set the stroke
			}
			beforeXCoord = (int)xCoord; //save the currentX as before
			beforeYCoord = (int)yCoord; //sabe the currentY as before
		}

		if(draw.isEmpty()){ //if the draw is empty meaning there is no data
			g2d.setColor(Color.white); //set the color to white
			g2d.setFont(AssetManager.fonts[3]); //set the font
			g2d.drawString("No Data", startX+width/2-150,startY+height/2+20); //draw there is "No Data" in the center of the screen
		}
	}
}
