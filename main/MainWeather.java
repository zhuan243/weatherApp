package main;

import application_Windows.Window;
import fileManager.AssetManager;
import fileManager.FileDownloader;
import fileManager.FileManipulator;
import object_Controller.BackgroundControl;

public class MainWeather{
	
	public static void main(String[] args) {
		
	
		AssetManager assetManager = new AssetManager();
		FileDownloader fileDownloader = new FileDownloader();
		FileManipulator fileManipulator = new FileManipulator();
		//System.out.println(FileManipulator.meanTemp.get("Markham"));
		Window window = new Window("Weather",assetManager,fileDownloader,fileManipulator);
		
	}
	
}
