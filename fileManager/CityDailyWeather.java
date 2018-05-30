package fileManager;

public class CityDailyWeather {
	
	private String city;
	private int startYear;
	private int endYear;
	private int stationID;


	public CityDailyWeather(String city, int startYear, int endYear, int stationID){
		this.city = city;
		this.startYear = startYear;
		this.endYear = endYear;
		this.stationID = stationID;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getStartYear() {
		return startYear;
	}


	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}


	public int getEndYear() {
		return endYear;
	}


	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}


	public int getStationID() {
		return stationID;
	}


	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	
	
}
