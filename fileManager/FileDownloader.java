package fileManager;

import java.awt.TextArea;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class FileDownloader {

	private CityDailyWeather[] downloadData; 

	public FileDownloader(){
		
		downloadData = new CityDailyWeather[5]; 
		downloadData[0] = new CityDailyWeather("Charlottetown", 1991,2002,6928);
		downloadData[1] = new CityDailyWeather("Iqaluit", 2004,2016,42503);
		downloadData[2] = new CityDailyWeather("Markham", 1986,2015,4841);
		downloadData[3] = new CityDailyWeather("Vancouver", 1925,2016,888);
		downloadData[4] = new CityDailyWeather("Whitehorse", 1959,2012,1618);
	}
	

	public void download(JTextArea textField){
		try{
			for(CityDailyWeather everyCity: downloadData){
				for(int i = everyCity.getStartYear(); i<=everyCity.getEndYear(); i++){
					String place = "Data/"+everyCity.getCity()+"/"+everyCity.getCity()+"-"+Integer.toString(i)+".txt";
					URL website_Iqaluit = new URL("http://climate.weather.gc.ca/climateData/bulkdata_e.html?format=csv&stationID="+
							Integer.toString(everyCity.getStationID())+"&Year="+Integer.toString(i)+"&Month=5&Day=1&timeframe=2&submit=Download+Data");
					ReadableByteChannel rbc_Iqaluit = Channels.newChannel(website_Iqaluit.openStream());
					FileOutputStream fos_Iqaluit = new FileOutputStream(place);
					fos_Iqaluit.getChannel().transferFrom(rbc_Iqaluit, 0, Long.MAX_VALUE);
					textField.setText(textField.getText()+"\n"+String.format("Downloading data to: %s", place));
					textField.setSelectionStart(textField.getText().length());
					textField.setSelectionEnd(textField.getText().length());
				}
			}
			textField.setText(textField.getText()+"\n\n"+"Finished downloading all files");
			textField.setSelectionStart(textField.getText().length());
			textField.setSelectionEnd(textField.getText().length());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	

	public void download(){
		try{
			for(CityDailyWeather everyCity: downloadData){
				for(int i = everyCity.getStartYear(); i<=everyCity.getEndYear(); i++){
					URL website_Iqaluit = new URL("http://climate.weather.gc.ca/climateData/bulkdata_e.html?format=csv&stationID="+
							Integer.toString(everyCity.getStationID())+"&Year="+Integer.toString(i)+"&Month=5&Day=1&timeframe=2&submit=Download+Data");
					ReadableByteChannel rbc_Iqaluit = Channels.newChannel(website_Iqaluit.openStream());
					FileOutputStream fos_Iqaluit = new FileOutputStream("Data/"+everyCity.getCity()+"/"+everyCity.getCity()+"-"+Integer.toString(i)+".txt");
					fos_Iqaluit.getChannel().transferFrom(rbc_Iqaluit, 0, Long.MAX_VALUE);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new FileDownloader().download();
	}

	public CityDailyWeather[] getDownloadData() {
		return downloadData;
	}

	public void setDownloadData(CityDailyWeather[] downloadData) {
		this.downloadData = downloadData;
	}
	
	
	
	
}
