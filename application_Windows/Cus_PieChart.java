package application_Windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JPanel;

import fileManager.AssetManager;
import fileManager.FileManipulator;
import methods.Method;
import object_Controller.BackgroundControl;


public class Cus_PieChart extends JPanel {

	private Random r = new Random();
	public static String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int startAngle;
	private int endAngle;
	private Font font;
	private TreeMap <String, Integer> counter;
	private ArrayList<Color> colors;

	public Cus_PieChart() {

		colors = AssetManager.pieChartColors;

		counter = new TreeMap <>();
		x = 700;
		y = 180;
		width = 400;
		height = 400;

		setLayout(null);
	}


	private TreeMap<String, Float> goThrough;


	public void changeCity(String name,String dateType){

		int gap = 0;
		if(dateType.equalsIgnoreCase("Mean Temperature")){
			goThrough =  FileManipulator.meanTemp.get(name);
			gap = 10;
		}else if(dateType.equalsIgnoreCase("Minimum Temperature")){
			goThrough =  FileManipulator.minTemp.get(name);
			gap = 10;
		}else if(dateType.equalsIgnoreCase("Maximum Temperature")){
			goThrough =  FileManipulator.maxTemp.get(name);
			gap = 10;
		}else if(dateType.equalsIgnoreCase("Heat Days")){
			goThrough = FileManipulator.heatDays.get(name);
			gap = 10;
		}else if(dateType.equalsIgnoreCase("Cool Days")){
			goThrough = FileManipulator.coolDays.get(name);
			gap = 5;
		}else if(dateType.equalsIgnoreCase("Total Rain")){
			goThrough = FileManipulator.totalRain.get(name);
			gap = 5;
		}else if(dateType.equalsIgnoreCase("Total Snow")){
			goThrough = FileManipulator.totalSnow.get(name);
			gap = 3;
		}else if(dateType.equalsIgnoreCase("Total Percipitation")){
			goThrough = FileManipulator.totalPrecip.get(name);
			gap = 5;
		}else if(dateType.equalsIgnoreCase("Snow On Ground")){
			goThrough = FileManipulator.snowOnGround.get(name);
			gap = 10;
		}else if(dateType.equalsIgnoreCase("Wind Speed")){
			goThrough = FileManipulator.speedGust.get(name);
			gap = 40;
		}

		counter = new TreeMap <>();
		for (float temp:goThrough.values()){
			String key = "";
			if(dateType.equalsIgnoreCase("Total Rain")||dateType.equalsIgnoreCase("Total Snow")||dateType.equalsIgnoreCase("Total Percipitation")
					||dateType.equalsIgnoreCase("Snow On Ground")||dateType.equalsIgnoreCase("Wind Speed")){
				if(temp>=0&&temp<=gap){
					key = "0~"+gap;
				}else if(temp>=gap){
					key = ">"+gap;
				}
			}else{
				key += (int)(Math.floor(temp/gap)) * gap + "~";
				key += (int) (Math.floor(temp/gap)) * gap + gap;
			}


			incrementTreeMap(counter, key);

		}
		repaint();
	}


	public void incrementTreeMap(TreeMap<String, Integer> map, String key){

		if (map.containsKey(key)){
			map.put(key, map.get(key)+1);

		}
		else{
			map.put(key, 1);

		}
	}



	@Override
	public void paintComponent (Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;


		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);

		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));

		startAngle = 0;

		int total = 0;
		for (int cycle: counter.values()){
			total += cycle;	
		}

		int colorCounter = 0;


		int dimensionChange = 0;

		g2d.setFont(new Font("TimesRoman", font.BOLD, 15));
		for (String range: counter.keySet()){
			int frequency = counter.get(range);
			dimensionChange++;

			g2d.setColor(colors.get(colorCounter));
			int angle = (int)(Math.ceil(frequency * 360.0 / total));
			g2d.fillArc(x,y,width,height,startAngle, angle);
			float targetAngle = (float) ((startAngle + startAngle + angle)/2.0);
			float distanceX = (float) (Math.cos(targetAngle/180.0 * Math.PI)* 250.0);
			float distanceY = (float) (Math.sin(targetAngle/180.0 * Math.PI) * -230.0);
			startAngle += angle;


			g2d.setColor(Color.white);
			g2d.setFont(AssetManager.fonts[1]);
			if(dimensionChange == 3){

				g2d.drawString(range , x-25 + width/2 + distanceX, y - 30 + height/2 + distanceY);

			}else{
				g2d.drawString(range , x-25 + width/2 + distanceX, y + height/2 + distanceY);
			}

			colorCounter++;
			if (colorCounter == colors.size()){
				colorCounter = 0;
			}
		}





	}







}
