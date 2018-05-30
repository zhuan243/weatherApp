package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import application_Windows.Window;
import fileManager.AssetManager;
import fileManager.FileManipulator;
import methods.Method;

public class Map extends EmptyObject{

	

	private ArrayList<City> cities;
	private HashMap<String, Float> tempCoord;
	
	private String year = "2005";
	private String month = "00";
	private String day = "00";
	
	private String date;
	
	private TreeMap<String, Float> minTemp1 = FileManipulator.minTemp.get("Charlottetown");
	private TreeMap<String, Float> minTemp2 = FileManipulator.minTemp.get("Iqaluit");
	private TreeMap<String, Float> minTemp3 = FileManipulator.minTemp.get("Markham");
	private TreeMap<String, Float> minTemp4 = FileManipulator.minTemp.get("Vancouver");
	private TreeMap<String, Float> minTemp5 = FileManipulator.minTemp.get("Whitehorse");
	

	public Map(float x, float y, ID id){
		super(x,y,id);
		
		cities = new ArrayList<>();
		tempCoord = new HashMap<>();
		
		refreshCities();
		refreshColor();
	}
	
	public void refreshCities(){
		cities.clear();
		
		date = year+"-"+month+"-"+day;
		date = date.replaceAll("-00", "");
		
		cities.add(new City("Charlottetown",681,570,Method.findAvg(minTemp1, date)));
		cities.add(new City("Iqaluit",514,341,Method.findAvg(minTemp2,date)));
		cities.add(new City("Markham",543,665,Method.findAvg(minTemp3,date)));
		cities.add(new City("Vancouver",91,607,Method.findAvg(minTemp4,date)));
		cities.add(new City("Whitehorse",38,416,Method.findAvg(minTemp5,date)));
		
		checkCities();
	}
	
	
	private void checkCities(){
		for(int i=0; i<cities.size();i++){
			if(cities.get(i).getTemp().isNaN()){
				cities.remove(i);
				i--;
			}
		}
	}

	public void refreshColor(){
		tempCoord.clear();

		int factor = cities.size();
		
		for(City everyCity: cities){
			for(int yP = 0; yP< AssetManager.canadaMap.getHeight();yP++){
				for(int xP = 0; xP<AssetManager.canadaMap.getWidth(); xP++){
					if(Math.hypot(xP-everyCity.getX(), yP-everyCity.getY())<20){
						tempCoord.put(xP+","+yP,100000f);
					}else{
						double distance = yP-everyCity.getY() +   50*Math.sin(0.009*(xP-everyCity.getX()));
						if(tempCoord.containsKey(xP+","+yP)){
							tempCoord.put(xP+","+yP, (float) (((everyCity.getTemp()+  distance/10*(Math.random()+0.2))/factor) +(tempCoord.get(xP+","+yP))));
						}else{
							tempCoord.put(xP+","+yP,(float) ((everyCity.getTemp() + distance/10*(Math.random()+0.2))/factor));
						}
					}
				}
			}
		}


		for(int yP = 0; yP< AssetManager.canadaMap.getHeight();yP++){
			for(int xP = 0; xP<AssetManager.canadaMap.getWidth(); xP++){
				int p = AssetManager.canadaMap.getRGB(xP, yP);
				if (p!=0){ //land
					float temp;
					if(tempCoord.containsKey(xP+","+yP))
						temp = tempCoord.get(xP+","+yP);
					else
						temp = 0;
					if(temp>=-1000&&temp<-40)
						p = Method.getIntFromColor(0,0,75);
					else if(temp>=-40&&temp<-30)
						p = Method.getIntFromColor(0,0,105);
					else if(temp>=-30&&temp<-20)
						p = Method.getIntFromColor(0,0,155);
					else if(temp>=-20&&temp<-10)
						p = Method.getIntFromColor(0,0,255);
					else if(temp>=-10&&temp<0)
						p = Method.getIntFromColor(0,105,255);
					else if(temp>=0&&temp<10)
						p = Method.getIntFromColor(0,155,205);
					else if(temp>=10&&temp<20)
						p = Method.getIntFromColor(0,205,155);
					else if(temp>=20&&temp<=1000)
						p = Method.getIntFromColor(0,255,105);
					else if(temp>1000)
						p = Method.getIntFromColor(255,0,0);

				}
				AssetManager.canadaMap.setRGB(xP, yP, p);
			}
		}
	}



	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.drawImage(AssetManager.canadaMap, (int)x, (int)y, null);
		g2d.setColor(Color.white);
		g2d.setFont(AssetManager.fonts[1]);
		g2d.drawString("For: "+date,1000, 50);
		g2d.drawString("Cities", 1000, 100);
		g2d.drawString("Mean Temperature", 1150, 100);
		
		int yCoord = 140;
		for(City eachCity: this.getCities()){
			g2d.drawString(eachCity.getName(), 1000, yCoord);
			g2d.drawString(Float.toString(eachCity.getTemp()), 1150, yCoord);
			yCoord+=40;
		}
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	public int getYear() {
		return Integer.parseInt(year);
	}

	public void setYear(int year) {
		this.year = String.format("%04d", year);
	}
	
	public int getMonth() {
		return Integer.parseInt(month);
	}

	public void setMonth(int month) {
		this.month = String.format("%02d", month);
	}
	
	public int getDay() {
		return Integer.parseInt(day);
	}

	public void setDay(int day) {
		this.day = String.format("%02d", day);
	}

}
