package fileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class FileManipulator {

	public static ArrayList<String> date;

	public static HashMap<String,TreeMap<String, Float>> maxTemp;
	public static HashMap<String,TreeMap<String, Float>> minTemp;
	public static HashMap<String,TreeMap<String, Float>> meanTemp;

	public static HashMap<String,TreeMap<String, Float>> heatDays;
	public static HashMap<String,TreeMap<String, Float>> coolDays;

	public static HashMap<String,TreeMap<String, Float>> totalRain;
	public static HashMap<String,TreeMap<String, Float>> totalSnow;

	public static HashMap<String,TreeMap<String, Float>> totalPrecip;
	public static HashMap<String,TreeMap<String, Float>> snowOnGround;

	public static HashMap<String,TreeMap<String, Float>> direcGust;
	public static HashMap<String,TreeMap<String, Float>> speedGust;

	public static ArrayList<HashMap<String,TreeMap<String, Float>>> accessor;


	public static String[] cities = {"Charlottetown","Iqaluit","Markham",
			"Vancouver","Whitehorse"};



	private final int INDEX_DATE = 0;
	private final int INDEX_YEAR = 1;
	private final int INDEX_MONTH = 2;
	private final int INDEX_DAY = 3;
	private final int INDEX_MAX_TEMP = 4;
	private final int INDEX_MIN_TEMP = 5;
	private final int INDEX_MEAN_TEMP = 6;
	private final int INDEX_HEAT_DEG_DAYS = 7;
	private final int INDEX_COOL_DEG_DAYS = 8;
	private final int INDEX_TOTAL_RAIN = 9; //usually Empty
	private final int INDEX_TOTAL_SNOW = 10; //usually Empty
	private final int INDEX_TOTAL_PRECIP = 11;
	private final int INDEX_SNOW_ON_GRND = 12;
	private final int INDEX_DIR_OF_MAX_GUST = 13;
	private final int INDEX_SPD_OF_MAX_GUST = 14;

	public FileManipulator(){

		date = new ArrayList<String>();

		maxTemp = new HashMap<String,TreeMap<String, Float>>();
		minTemp = new HashMap<String,TreeMap<String, Float>>();
		meanTemp= new HashMap<String,TreeMap<String, Float>>();

		heatDays= new HashMap<String,TreeMap<String, Float>>();
		coolDays= new HashMap<String,TreeMap<String, Float>>();

		totalRain = new HashMap<String,TreeMap<String, Float>>();
		totalSnow = new HashMap<String,TreeMap<String, Float>>();

		totalPrecip= new HashMap<String,TreeMap<String, Float>>();
		snowOnGround= new HashMap<String,TreeMap<String, Float>>();

		direcGust= new HashMap<String,TreeMap<String, Float>>();
		speedGust= new HashMap<String,TreeMap<String, Float>>();


		accessor = new ArrayList<HashMap<String,TreeMap<String, Float>>>();
		accessor.add(maxTemp);
		accessor.add(minTemp);
		accessor.add(meanTemp);
		accessor.add(heatDays);
		accessor.add(coolDays);
		accessor.add(totalRain);
		accessor.add(totalSnow);
		accessor.add(totalPrecip);
		accessor.add(snowOnGround);
		accessor.add(direcGust);
		accessor.add(speedGust);

	}

	public void startCollecting(FileDownloader downloader){
		
		date.clear();

		maxTemp.clear();
		minTemp.clear();
		meanTemp.clear();

		heatDays.clear();
		coolDays.clear();

		totalRain.clear();
		totalSnow.clear();

		totalPrecip.clear();
		snowOnGround.clear();
		
		direcGust.clear();
		speedGust.clear();

		try {

			for(HashMap<String,TreeMap<String, Float>> eachMap : accessor){
				for(String cityName : cities){
					eachMap.put(cityName,new TreeMap<String, Float>());
				}
			}

			for(CityDailyWeather citiesYearRange: downloader.getDownloadData()){
				String cityName = citiesYearRange.getCity();
				for(int year = citiesYearRange.getStartYear(); year<=citiesYearRange.getEndYear();year++){
					BufferedReader br = new BufferedReader(new FileReader("Data/"+cityName+"/"+cityName+"-"+Integer.toString(year)+".txt"));

					while(!br.readLine().startsWith("\"Date/Time\"")){
						continue;
					}

					for(;;){
						String currentLine = br.readLine();
						if(currentLine==null){
							break;
						}
						currentLine = currentLine.replaceAll("[^0-9,.-]", "");
						currentLine = currentLine.replaceAll(",,", ",");
						currentLine = currentLine.replaceAll(",,", ",/,");
						currentLine = currentLine.replaceAll(",,", ",/,");


						String[] tokens = currentLine.split(",");

						//System.out.println(tokens[INDEX_DATE]);


						if(!tokens[INDEX_DATE].equals("/")){
							date.add(tokens[INDEX_DATE]);
						}

						if(!tokens[INDEX_MAX_TEMP].equals("/")){
							maxTemp.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_MAX_TEMP]));
						}

						if(!tokens[INDEX_MIN_TEMP].equals("/")){
							minTemp.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_MIN_TEMP]));
						}

						if(!tokens[INDEX_MEAN_TEMP].equals("/")){
							meanTemp.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_MEAN_TEMP]));
						}

						if(!tokens[INDEX_HEAT_DEG_DAYS].equals("/")){
							heatDays.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_HEAT_DEG_DAYS]));
						}

						if(!tokens[INDEX_COOL_DEG_DAYS].equals("/")){
							coolDays.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_COOL_DEG_DAYS]));
						}

						if(!tokens[INDEX_TOTAL_RAIN].equals("/")){
							totalRain.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_TOTAL_RAIN]));
						}

						if(!tokens[INDEX_TOTAL_SNOW].equals("/")){
							totalSnow.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_TOTAL_SNOW]));
						}

						if(!tokens[INDEX_TOTAL_PRECIP].equals("/")){
							totalPrecip.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_TOTAL_PRECIP]));
						}

						if(!tokens[INDEX_SNOW_ON_GRND].equals("/")){
							snowOnGround.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_SNOW_ON_GRND]));
						}

						if(!tokens[INDEX_DIR_OF_MAX_GUST].equals("/")){
							direcGust.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_DIR_OF_MAX_GUST]));
						}

						if(!tokens[INDEX_SPD_OF_MAX_GUST].equals("/")){
							speedGust.get(cityName).put(tokens[INDEX_DATE],Float.parseFloat(tokens[INDEX_SPD_OF_MAX_GUST]));
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashSet<String> removeRepeatedDate = new HashSet<>(date);
		date = new ArrayList<>(removeRepeatedDate);
		Collections.sort(date);

	}
}
