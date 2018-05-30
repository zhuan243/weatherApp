package object_Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import application_Windows.Window;
import fileManager.AssetManager;
import fileManager.WeatherID;

public class BackgroundControl implements Runnable{



	private Thread t;
	private boolean running = true;

	private boolean changing;

	private Window window;
	private AssetManager assetManager;
	public static Float backgroundDarkLayer;

	private WeatherID currentBackground;





	public BackgroundControl(Window window, AssetManager assetManager){
		backgroundDarkLayer = 0.4f;

		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		int month = Integer.parseInt(dateFormat.format(date));
		if(month>=4&&month<=6)
			currentBackground = WeatherID.Spring;
		else if(month>=7&&month<=9)
			currentBackground = WeatherID.Summer;
		else if(month>=10&&month<=12)
			currentBackground = WeatherID.Autumn;
		else if(month>=1&&month<=3)
			currentBackground = WeatherID.Winter;

		changing = true;
		this.window = window;
		this.assetManager = assetManager;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while(running){
			try {
				if(changing){

					while(backgroundDarkLayer<0.99f){
						backgroundDarkLayer += 0.01f;
						window.repaint();
						t.sleep(50);
					}
					assetManager.changeBackground(currentBackground);
					while(backgroundDarkLayer>0.4f){
						backgroundDarkLayer -= 0.01f;
						window.repaint();
						t.sleep(50);
					}
					t.sleep(1000);
				}else{
					while(backgroundDarkLayer<0.99f){
						backgroundDarkLayer += 0.01f;
						window.repaint();
						t.sleep(50);
					}
					backgroundDarkLayer = 1f;
					window.repaint();
					t.sleep(1000);
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public int getCurrentBackground() {
		if (currentBackground==WeatherID.Spring)
			return 1;
		else if (currentBackground==WeatherID.Summer)
			return 2;
		else if (currentBackground==WeatherID.Autumn)
			return 3;
		else if (currentBackground==WeatherID.Winter)
			return 4;

		return 0;
	}

	public void setCurrentBackground(WeatherID currentBackground) {
		this.currentBackground = currentBackground;
	}

	public boolean isChanging() {
		return changing;
	}

	public void setChanging(boolean changing) {
		this.changing = changing;
	}



}
