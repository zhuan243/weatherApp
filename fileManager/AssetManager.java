package fileManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import application_Windows.Window;

import methods.Method;


//A class to keep all the images organized. Other classes using images will call it up from here
public class AssetManager {

	//map of canada
	public static BufferedImage canadaMap; 
	
	//Gif of loading screen
	public static Image loading;

	//Background image
	public static Image backgroundImage;

	//Array of four seasons, and within those seasons, another array of images.
	public static ArrayList<Image> background_Spring;
	public static ArrayList<Image> background_Summer;
	public static ArrayList<Image> background_Autumn;
	public static ArrayList<Image> background_Winter;

	
	public static ImageIcon dropDownArrow;

	//icons used in pieChart
	public static ImageIcon[] charlottetown;
	public static ImageIcon[] iqaluit;
	public static ImageIcon[] markham;
	public static ImageIcon[] vancouver;
	public static ImageIcon[] whitehorse;

	public static ImageIcon cityIconArrow_white;
	public static ImageIcon cityIconArrow_green;

	public static ImageIcon closeButton;

	public static Image hoverMap_Map;

	public static ImageIcon bluePin;
	public static ImageIcon redPin;
	public static ImageIcon greenPin;
	public static ImageIcon yellowPin;
	public static ImageIcon whitePin;

	public static ImageIcon[] whitehorseImageFlag;
	public static ImageIcon[] vancouverImageFlag;
	public static ImageIcon[] iqaluitImageFlag;
	public static ImageIcon[] charlottetownImageFlag;
	public static ImageIcon[] markhamImageFlag;

	public static ImageIcon[] leftSwitchButtonImage;
	public static ImageIcon[] rightSwitchButtonImage;

	public static Image reporter;
	public static Image speechBubble;

	public static ImageIcon sun;
	public static ImageIcon cloudsun;
	public static ImageIcon snowflake;

	public static ImageIcon tableIcon;
	public static ImageIcon lineGraphIcon;
	public static ImageIcon pieChartIcon;
	public static ImageIcon reportIcon;
	public static ImageIcon heatMapIcon;
	public static ImageIcon citiesMapIcon;


	private static float[] dash1 = {5.0f};
	private static float[] dash2 = {10.0f};
	private static float[] dash3 = {3.0f};

	public static Color blue = new Color(101,153,255);
	public static Color lightblue = new Color(82,216,255);
	public static Color lightBlueGrey = new Color(141,158,193);
	public static Color lighterBlueGrey = new Color(196,205,223);
	public static Color grey = new Color(64,64,64);

	public static ArrayList<Color> pieChartColors;
	/*
	public static Color[] pieChartColors_Winter = new Color[]{
			new Color(214,218,204),
			new Color(68,104,126),
			new Color(236,241,221),
			new Color(24,45,74),
			new Color(153,165,163),
			new Color(0,3,36),
			new Color(209,218,201),
			new Color(3,15,39),
			new Color(175,188,181),
			new Color(113,128,125),
	};	

	public static Color[] pieChartColors_Spring = new Color[]{
			new Color(187,184,21),
			new Color(255,248,191),
			new Color(21,41,48),
			new Color(147,196,213),
			new Color(93,73,14),
			new Color(233,201,32),
			new Color(35,23,1),
			new Color(41,149,175)
	};
	 */

	public static Stroke[] strokes = new Stroke[] {
			new BasicStroke(4.0f), // The standard,
			new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,dash1, 0.0f), //dashed
			new BasicStroke(1.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,dash2, 0.0f),
			new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,dash3, 0.0f),
			new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,dash1, 0.0f),
			new BasicStroke(2.0f),	
	};

	public static Font[] fonts  = new Font[] {
			new Font("Agency FB", Font.BOLD, 42),
			new Font("Agency FB", Font.BOLD, 20),
			new Font("Agency FB", Font.BOLD, 12),
			new Font("Agency FB", Font.BOLD, 120),
			new Font("Agency FB", Font.BOLD, 16),
			new Font("Agency FB", Font.BOLD, 24),
			new Font("Agency FB", Font.BOLD, 45),
			new Font("Agency FB", Font.BOLD, 60)
	};

	public void changeBackground(WeatherID id){
		Image changeTo;
		if(id==WeatherID.Spring){
			do{ 
				changeTo = background_Spring.get(Method.random(0, background_Spring.size()));
			}while(changeTo==backgroundImage);

			backgroundImage = changeTo;
			changePieChartColor();
		}else if(id==WeatherID.Summer){
			do{ 
				changeTo = background_Summer.get(Method.random(0, background_Summer.size()));
			}while(changeTo==backgroundImage);
			backgroundImage = changeTo;
			changePieChartColor();
		}else if(id==WeatherID.Autumn){
			do{ 
				changeTo = background_Autumn.get(Method.random(0, background_Autumn.size()));
			}while(changeTo==backgroundImage);
			backgroundImage = changeTo;
			changePieChartColor();
		}else if(id==WeatherID.Winter){
			do{ 
				changeTo = background_Winter.get(Method.random(0, background_Winter.size()));
			}while(changeTo==backgroundImage);
			backgroundImage = changeTo;
			changePieChartColor();
		}
	}

	public void changePieChartColor(){
		pieChartColors.clear();
		BufferedImage tempBuffer= Method.toBufferedImage(backgroundImage);
		while(pieChartColors.size()<=10){
			Color color = new Color(tempBuffer.getRGB(Method.random(0, tempBuffer.getWidth()), Method.random(0, tempBuffer.getHeight())));
			if(!pieChartColors.contains(color)){
				pieChartColors.add(color);
			}
		}
	}
	public void initLoading(){
		loading = new ImageIcon("res\\loading.gif").getImage();
	}

	public void init(){

		UIManager.put("ComboBox.selectionBackground", Color.cyan);
		UIManager.put("RadioButton.select", Color.black);

		charlottetown = new ImageIcon[2];
		iqaluit = new ImageIcon[2];
		markham = new ImageIcon[2];
		vancouver = new ImageIcon[2];
		whitehorse = new ImageIcon[2];

		whitehorseImageFlag = new ImageIcon[7];
		vancouverImageFlag= new ImageIcon[7];
		iqaluitImageFlag= new ImageIcon[7];
		charlottetownImageFlag= new ImageIcon[7];
		markhamImageFlag= new ImageIcon[7];

		leftSwitchButtonImage = new ImageIcon[2];
		rightSwitchButtonImage = new ImageIcon[2];


		try {
			pieChartColors  = new ArrayList<Color>();

			background_Spring = new ArrayList<Image>();
			background_Summer = new ArrayList<Image>();
			background_Autumn = new ArrayList<Image>();
			background_Winter = new ArrayList<Image>();

			for(int i=1; i<=4; i++){
				background_Spring.add(ImageIO.read(new File("res\\spring\\spring"+Integer.toString(i)+".jpg")).getScaledInstance(Window.WIDTH, Window.HEIGHT, Image.SCALE_SMOOTH));
				background_Summer.add(ImageIO.read(new File("res\\summer\\summer"+Integer.toString(i)+".jpg")).getScaledInstance(Window.WIDTH, Window.HEIGHT, Image.SCALE_SMOOTH));
				background_Autumn.add(ImageIO.read(new File("res\\autumn\\autumn"+Integer.toString(i)+".jpg")).getScaledInstance(Window.WIDTH, Window.HEIGHT, Image.SCALE_SMOOTH));
				background_Winter.add(ImageIO.read(new File("res\\winter\\winter"+Integer.toString(i)+".jpg")).getScaledInstance(Window.WIDTH, Window.HEIGHT, Image.SCALE_SMOOTH));
			}



			changeBackground(WeatherID.Winter);

			charlottetown[0] =  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Charlottetown.png")));
			iqaluit[0]= new ImageIcon( ImageIO.read(new File("res\\cityButtons\\Iqaluit.png")));
			markham[0]=  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Markham.png")));
			vancouver[0]=  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Vancouver.png")));
			whitehorse[0]=  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Whitehorse.png")));


			charlottetown[1] =  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Charlottetown_Green.png")));
			iqaluit[1] = new ImageIcon( ImageIO.read(new File("res\\cityButtons\\Iqaluit_Green.png")));
			markham[1] =  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Markham_Green.png")));
			vancouver[1] =  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Vancouver_Green.png")));
			whitehorse[1] =  new ImageIcon(ImageIO.read(new File("res\\cityButtons\\Whitehorse_Green.png")));

			cityIconArrow_white = new ImageIcon(ImageIO.read(new File("res\\buttons\\arrow_White.png")).getScaledInstance(83, 83, Image.SCALE_SMOOTH));
			cityIconArrow_green = new ImageIcon(ImageIO.read(new File("res\\buttons\\arrow_Green.png")).getScaledInstance(83, 83, Image.SCALE_SMOOTH));

			closeButton = new ImageIcon(ImageIO.read(new File("res\\buttons\\close_Button.png")).getScaledInstance(83, 83, Image.SCALE_SMOOTH));

			hoverMap_Map = ImageIO.read(new File("res\\hoverMapImages\\CanadaMap.png")).getScaledInstance(1000, 750, Image.SCALE_SMOOTH);

			bluePin = new ImageIcon("res\\hoverMapImages\\DropPinBlue.png");
			redPin = new ImageIcon("res\\hoverMapImages\\DropPinRed.png");
			greenPin = new ImageIcon("res\\hoverMapImages\\DropPinGreen.png");
			yellowPin = new ImageIcon("res\\hoverMapImages\\DropPinYellow.png");
			whitePin = new ImageIcon("res\\hoverMapImages\\DropPinWhite.png");

			whitehorseImageFlag[0] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\flags\\Whitehorse_flag.png")).getScaledInstance(250, 150, Image.SCALE_SMOOTH));
			vancouverImageFlag[0] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\flags\\Vancouver_flag.png")).getScaledInstance(250, 150, Image.SCALE_SMOOTH));
			iqaluitImageFlag[0] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\flags\\Iqaluit_flag.png")).getScaledInstance(250, 150, Image.SCALE_SMOOTH));
			charlottetownImageFlag[0] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\flags\\Charlottetown_flag.png")).getScaledInstance(250, 150, Image.SCALE_SMOOTH));
			markhamImageFlag[0] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\flags\\Markham_flag.png")).getScaledInstance(250, 150, Image.SCALE_SMOOTH));

			for(int i = 1; i<=6;i++){
				whitehorseImageFlag[i] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\cityImages\\Whitehorse"+Integer.toString(i)+".jpg")).getScaledInstance(600, 450, Image.SCALE_SMOOTH));
				vancouverImageFlag[i] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\cityImages\\Vancouver"+Integer.toString(i)+".jpg")).getScaledInstance(600, 450, Image.SCALE_SMOOTH));
				iqaluitImageFlag[i] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\cityImages\\Iqaluit"+Integer.toString(i)+".jpg")).getScaledInstance(600, 450, Image.SCALE_SMOOTH));
				charlottetownImageFlag[i] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\cityImages\\Charlottetown"+Integer.toString(i)+".jpg")).getScaledInstance(600, 450, Image.SCALE_SMOOTH));
				markhamImageFlag[i] = new ImageIcon(ImageIO.read(new File("res\\hoverMapImages\\cityImages\\Markham"+Integer.toString(i)+".jpg")).getScaledInstance(600, 450, Image.SCALE_SMOOTH));
			}

			leftSwitchButtonImage[0] = new ImageIcon("res\\buttons\\greyLeftPointer.png");
			leftSwitchButtonImage[1] = new ImageIcon("res\\buttons\\greenLeftPointer.png");

			rightSwitchButtonImage[0] = new ImageIcon("res\\buttons\\greyRightPointer.png");
			rightSwitchButtonImage[1] = new ImageIcon("res\\buttons\\greenRightPointer.png");

			dropDownArrow =  new ImageIcon(ImageIO.read(new File("res\\buttons\\drop_down_arrow.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			speechBubble = ImageIO.read(new File("res\\report\\Speech_bubble.png")).getScaledInstance(420, 300, Image.SCALE_SMOOTH);
			reporter = ImageIO.read(new File("res\\report\\reporter.png"));
			sun = new ImageIcon(ImageIO.read(new File("res\\report\\sunFinal.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			cloudsun = new ImageIcon(ImageIO.read(new File("res\\report\\cloudsunFinal.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			snowflake = new ImageIcon(ImageIO.read(new File("res\\report\\snowflakeFinal.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH));

			tableIcon = new ImageIcon("res//buttons//menuButtons//TableIcon.png");
			lineGraphIcon= new ImageIcon("res//buttons//menuButtons//LineGraphIcon.png");
			pieChartIcon= new ImageIcon(ImageIO.read(new File("res//buttons//menuButtons//PieChartIcon.png")).getScaledInstance(230, 180, Image.SCALE_SMOOTH));
			reportIcon= new ImageIcon("res//buttons//menuButtons//ReportIcon.png");
			heatMapIcon= new ImageIcon("res//buttons//menuButtons//HeatMapIcon.png");
			citiesMapIcon= new ImageIcon("res//buttons//menuButtons//MapIcon.png");

			BufferedImage canadaMapBuffer = ImageIO.read(new File("res\\Canada_blank_map.png"));


			canadaMapBuffer = Method.toBufferedImage(canadaMapBuffer.getScaledInstance(canadaMapBuffer.getWidth()*Window.HEIGHT/canadaMapBuffer.getHeight(), Window.HEIGHT, Image.SCALE_SMOOTH));


			for(int y = 0; y< canadaMapBuffer.getHeight();y++){
				for(int x = 0; x<canadaMapBuffer.getWidth(); x++){
					int p = canadaMapBuffer.getRGB(x, y);
					if (p==-2894893){ // land
						p = Method.getIntFromColor(0,0,0);
					}else{
						p = 0;
					}

					canadaMapBuffer.setRGB(x, y, p);
				}
			}

			canadaMap = canadaMapBuffer;
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
