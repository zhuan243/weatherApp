package application_Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ComboBoxUI;
import Cus_UIs.Cus_ScrollBarUI;
import fileManager.AssetManager;
import fileManager.FileManipulator;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_Report extends JPanel implements KeyListener{

	private JLabel yearLabel,speech,box1,box2,box3,minTemp,maxTemp,avgTemp, imageHolder1, imageHolder2,imageHolder3;
	private JComboBox<String> combo;
	private JTextField yearField;
	private JButton yearButton,home;
	private String script;
	private String city;
	private String trend; 
	private float max=0;
	private float total=0;
	private float totalCounter=0;
	private float min=0;
	private float avg=0;

	private TreeMap<String, Integer> counter;
	private TreeMap<String, Float> monthly;
	private TreeMap<String, Float> average;


	public Cus_Report(Window window){

		counter=new TreeMap<>();
		monthly=new TreeMap<>(); 
		average=new TreeMap<>();

		setLayout(null);
		setBackground(Color.green);

		minTemp = new JLabel();
		minTemp.setBounds(625,360,100,50);
		minTemp.setFont(AssetManager.fonts[0]);
		minTemp.setForeground(Color.white);

		maxTemp = new JLabel();
		maxTemp.setBounds(1056,360,100,50);
		maxTemp.setFont(AssetManager.fonts[0]);
		maxTemp.setForeground(Color.white);

		avgTemp = new JLabel();
		avgTemp.setBounds(850,360,100,50);
		avgTemp.setFont(AssetManager.fonts[0]);
		avgTemp.setForeground(Color.white);

		box1 = new JLabel("Min Temp");
		box1.setBounds(640,270,100,50);
		box1.setFont(AssetManager.fonts[1]);

		box2 = new JLabel("Avg Temp");
		box2.setBounds(850,270,100,50);
		box2.setFont(AssetManager.fonts[1]);

		box3 = new JLabel("Max Temp");
		box3.setBounds(1062,270,100,50);
		box3.setFont(AssetManager.fonts[1]);

		imageHolder1 = new JLabel(AssetManager.sun);
		imageHolder1.setBounds(620,470,100,100);
		imageHolder1.setFont(AssetManager.fonts[1]);

		imageHolder2 = new JLabel(AssetManager.sun);
		imageHolder2.setBounds(830,470,100,100);
		imageHolder2.setFont(AssetManager.fonts[1]);

		imageHolder3 = new JLabel(AssetManager.sun);
		imageHolder3.setBounds(1042,470,100,100);
		imageHolder3.setFont(AssetManager.fonts[1]);




		yearLabel = new JLabel("Year-Month:");
		yearLabel.setBounds(30,20,100,30);
		yearLabel.setFont(AssetManager.fonts[1]);
		yearLabel.setForeground(Color.white);

		speech = new JLabel();
		speech.setFont(AssetManager.fonts[1]);
		speech.setHorizontalTextPosition(JLabel.CENTER);

		JScrollPane scroller = new JScrollPane(speech);
		scroller.setBounds(390, 85, 270, 130);
		scroller.setOpaque(false);
		scroller.getViewport().setOpaque(false);
		scroller.setHorizontalScrollBar(null);
		scroller.getVerticalScrollBar().setUI(new Cus_ScrollBarUI());
		scroller.getVerticalScrollBar().setOpaque(false);
		scroller.setBorder(BorderFactory.createEmptyBorder());

		yearField = new JTextField();
		yearField.setBounds(135,20,100,30);
		yearField.addKeyListener(this);
		yearField.setFont(AssetManager.fonts[1]);


		JLabel comboIndicater = new JLabel("City:");
		comboIndicater.setBounds(Window.WIDTH/2+100, 130, 50, 30);
		comboIndicater.setFont(AssetManager.fonts[1]);
		comboIndicater.setForeground(Color.white);
		
		String[] abc = new String[]{"Charlottetown","Iqaluit","Markham",
				"Vancouver","Whitehorse"};
		combo = new JComboBox<String>(abc);
		combo.setBounds(Window.WIDTH/2+150, 130, 200, 30);
		combo.setFont(AssetManager.fonts[1]);
		combo.setUI(new Cus_ComboBoxUI());
		combo.addActionListener(e->forecastScript());

		yearButton = new JButton("Go!");
		yearButton.setBounds(30,65,100,30);
		yearButton.addActionListener(e->forecastScript());
		yearButton.setUI(new BasicButtonUI());
		yearButton.setFont(AssetManager.fonts[1]);

		home = new JButton("Back");
		home.setBounds(30,100,100,30);
		home.addActionListener(e->window.changeComp(PanelID.Menu));
		home.setUI(new BasicButtonUI());
		home.setFont(AssetManager.fonts[1]);

		forecastScript();

		add(minTemp);
		add(maxTemp);
		add(avgTemp);
		add(box1);
		add(box2);
		add(box3);
		add(comboIndicater);
		add(combo);
		add(yearField);
		add(yearLabel);
		add(scroller);
		add(yearButton);
		add(home);
		add(imageHolder1);
		add(imageHolder2);
		add(imageHolder3);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));

		g2d.drawImage(AssetManager.reporter, -10, 0, null);
		g2d.drawImage(AssetManager.speechBubble, 300, 20, null);

	}

	private void calculate(){
		counter.clear();
		monthly.clear(); 
		average.clear();

		max=-999;
		min=999;
		total=0;
		totalCounter=0;
		avg=0;
		city= (String)combo.getSelectedItem();
		trend = "";




		for(String year: FileManipulator.meanTemp.get(city).keySet()){
			if(year.startsWith(yearField.getText())){
				float f = FileManipulator.meanTemp.get(city).get(year);
				if(FileManipulator.maxTemp.get(city).containsKey(year)){
					float fMax = FileManipulator.maxTemp.get(city).get(year);
					max = Math.max(max, fMax);
				}
				if(FileManipulator.maxTemp.get(city).containsKey(year)){
					float fMin = FileManipulator.minTemp.get(city).get(year);
					min = Math.min(min, fMin);
				}
				total+=f;
				totalCounter++;
				avg=total/totalCounter;
				String yearMonth = year.substring(0, 7);
				if(counter.containsKey(yearMonth)){
					counter.put(yearMonth, counter.get(yearMonth)+1);
					monthly.put(yearMonth,monthly.get(yearMonth)+f);
				}
				else{
					counter.put(yearMonth,1);
					monthly.put(yearMonth,f);
				}
			}
		}

		if(!monthly.isEmpty()){
			for(String key: monthly.keySet()){
				average.put(key, monthly.get(key)/counter.get(key));
			}

			String beforeKey = average.firstKey();
			float beforeValue = average.get(average.firstKey());
			String beforeTrend = "";

			for(String key: average.keySet()){
				float value = average.get(key);
				if(!key.equals((average.firstKey()))){
					String currentTrend = "";
					if(value >= beforeValue){
						currentTrend = "Increasing";
					}else{
						currentTrend = "Decreasing";
					}
					if(!currentTrend.equals(beforeTrend)||key.equals(average.lastKey())){
						trend+=String.format("%s~%s is: %s<br>", beforeKey, key, beforeTrend);
						beforeKey = key;
						beforeTrend = currentTrend;
					}
					beforeValue = value;

				}else{
					if(value >= beforeValue){
						beforeTrend = "Increasing";
					}else{
						beforeTrend = "Decreasing";
					}
				}
			}
		}else {
			max = 0;
			min = 0;
			trend = "No Data Available";
		}
		
		if(trend.equals("")){
			trend = "Trend Analysis only availble <br>for yearly data.";
		}


	}

	private void forecastScript(){
		calculate();
		script=String.format("<html><span bg color=\"white\">Good evening.<br>Welcome to Leo's Forecast 101! </span><br>"
				+ "<span bg color=\"white\">The city is:</span><span bg color=\"yellow\"> %s </span><br>"
				+ "<span bg color=\"white\">Minimum Temperature:</span><span bg color=\"yellow\"> %.2f </span><br>"
				+ "<span bg color=\"white\">Average Temperature:</span><span bg color=\"yellow\"> %.2f </span><br>"
				+ "<span bg color=\"white\">Maximum Temperature:</span><span bg color=\"yellow\"> %.2f </span><br>"
				+ "<span bg color=\"white\">The trend(available for yearly data)is: </span><br><span bg color=\"yellow\"> %s </span><br>"
				+ "<span bg color=\"white\">Thank you, Leo out.</span></html>", city, min, avg, max, trend);
		speech.setText(script);

		minTemp.setText(String.format("%.2f",min));
		changeImage(imageHolder1,min);
		avgTemp.setText(String.format("%.2f",avg));
		changeImage(imageHolder2,avg);
		maxTemp.setText(String.format("%.2f",max));
		changeImage(imageHolder3,max);
	}

	private void changeImage(JLabel imageHolder, float temp){
		if(temp<-5){
			imageHolder.setIcon(AssetManager.snowflake);
		}else if(temp>=-5&&temp<=15)
			imageHolder.setIcon(AssetManager.cloudsun);
		else if(temp>15){
			imageHolder.setIcon(AssetManager.sun);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	} 

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			forecastScript();
		}

	}
}
