package application_Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ComboBoxUI;
import fileManager.AssetManager;
import fileManager.CityDailyWeather;
import fileManager.FileDownloader;
import fileManager.FileManipulator;
import fileManager.WeatherID;
import input_Control.MenuButtonHoverController;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_Menu extends JPanel{

	private Window window;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));

	}

	public Cus_Menu(Window window, BackgroundControl bgControl, FileDownloader fileDownloader){
		this.window = window;
		setLayout(null);

		JLabel menu = new JLabel("MENU");
		menu.setBounds(100, 80, 500, 100);
		menu.setOpaque(false);
		menu.setFont(AssetManager.fonts[3]);
		menu.setForeground(Color.white);

		int width = 255;
		int height = 255;
		int startX = 400;
		int startY = 130;
		int spacing = 20;

		JButton table = makeButton("Table",AssetManager.tableIcon,PanelID.Table,startX,startY,width,height);

		JButton lineGraph = makeButton("Line Graph",AssetManager.lineGraphIcon,PanelID.LineGraph,startX+width+spacing,startY,width,height);

		JButton pieChart = makeButton("Pie Chart",AssetManager.pieChartIcon,PanelID.PieChart,startX,startY+height+spacing,width,height);

		JButton heatMap = makeButton("HeatMap",AssetManager.heatMapIcon,PanelID.HeatMap,startX+width+spacing,startY+height+spacing,width,height);

		JButton report = makeButton("Report",AssetManager.reportIcon,PanelID.Report,startX+(width+spacing)*2,startY,width,height);
		
		JButton hoverMap = makeButton("Cities Map",AssetManager.citiesMapIcon,PanelID.HoverMap,startX+(width+spacing)*2,startY+height+spacing,width,height);


		JButton closeButton = new JButton(AssetManager.closeButton);
		closeButton.setBounds(Window.WIDTH-50-15, 15, 50,50);
		closeButton.setUI(new BasicButtonUI());
		closeButton.setOpaque(false);
		closeButton.setBorderPainted(false);
		closeButton.addActionListener(e->System.exit(0));

		JLabel bgLabel = createLable("Background:",100,280,200,50);

		String[] bgs = {"No Background", "Theme: Spring", "Theme: Summer","Theme: Autumn","Theme: Winter"};
		JComboBox<String> bgSelection = new JComboBox<String>(bgs);
		bgSelection.setBounds(100, 330, 200, 30);
		bgSelection.setFont(AssetManager.fonts[1]);
		bgSelection.setUI(new Cus_ComboBoxUI());
		bgSelection.setSelectedIndex(bgControl.getCurrentBackground());



		JLabel dataLabel = createLable("Data/Year",100,395,85,30);
		JLabel startLabel = createLable("Start",185,395,55,30);
		JLabel endLabel = createLable("End",245,395,55,30);


		JLabel[] updateLabels = new JLabel[5];
		JTextField[] startYearInputs = new JTextField[5];
		JTextField[] endYearInputs = new JTextField[5];

		int ySpacing = 35;
		int counter = 0;
		for(CityDailyWeather eachCity: fileDownloader.getDownloadData()){
			updateLabels[counter] = createLable(eachCity.getCity(),100,430+ySpacing*counter,85,30);
			startYearInputs[counter]  = createTextArea(185,430+ySpacing*counter,55,30,4);
			startYearInputs[counter].setText(Integer.toString(eachCity.getStartYear()));
			endYearInputs[counter]  = createTextArea(245, 430+ySpacing*counter,55,30,4);
			endYearInputs[counter].setText(Integer.toString(eachCity.getEndYear()));
			counter++;
		}

		JButton update = new JButton("Update");
		update.setForeground(Color.black);
		update.setUI(new BasicButtonUI());
		update.setFont(AssetManager.fonts[1]);
		update.setBounds(100, 620, 95, 30);

		JButton reset = new JButton("Reset");
		reset.setForeground(Color.black);
		reset.setUI(new BasicButtonUI());
		reset.setFont(AssetManager.fonts[1]);
		reset.setBounds(205, 620, 95, 30);

		add(menu);
		add(table);
		add(lineGraph);
		add(pieChart);
		add(heatMap);
		add(report);
		add(hoverMap);
		add(closeButton);
		add(bgSelection);
		add(bgLabel);
		add(update);
		add(reset);


		for(JLabel labels:updateLabels)
			add(labels);
		for(JTextField textAreas:startYearInputs)
			add(textAreas);
		for(JTextField textAreas:endYearInputs)
			add(textAreas);	

		add(dataLabel);
		add(startLabel);
		add(endLabel);


		bgSelection.addActionListener(e->{
			if(bgSelection.getSelectedIndex()==0){
				bgControl.setChanging(false);
			}else if(bgSelection.getSelectedIndex()==1){
				bgControl.setChanging(true);
				bgControl.setCurrentBackground(WeatherID.Spring);
			}else if(bgSelection.getSelectedIndex()==2){
				bgControl.setChanging(true);
				bgControl.setCurrentBackground(WeatherID.Summer);
			}else if(bgSelection.getSelectedIndex()==3){
				bgControl.setChanging(true);
				bgControl.setCurrentBackground(WeatherID.Autumn);
			}else if(bgSelection.getSelectedIndex()==4){
				bgControl.setChanging(true);
				bgControl.setCurrentBackground(WeatherID.Winter);
			}
		});


		update.addActionListener(e->{
			for(int i = 0; i<5;i++){
				fileDownloader.getDownloadData()[i].setStartYear(Integer.parseInt(startYearInputs[i].getText()));
				fileDownloader.getDownloadData()[i].setEndYear(Integer.parseInt(endYearInputs[i].getText()));
				window.changeComp(PanelID.FileDownloading);
			}
		});

		reset.addActionListener(e->{
			for(int i = 0; i<fileDownloader.getDownloadData().length;i++){
				CityDailyWeather eachCity = fileDownloader.getDownloadData()[i];
				startYearInputs[i].setText(Integer.toString(eachCity.getStartYear()));
				endYearInputs[i].setText(Integer.toString(eachCity.getEndYear()));
			}
		});
	}

	private JTextField createTextArea(int x, int y, int width, int height, int wordLimit){
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		textField.setFont(AssetManager.fonts[4]);
		return textField;
	}

	private JLabel createLable(String text, int x, int y, int width,int height){
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		label.setOpaque(false);
		label.setFont(AssetManager.fonts[1]);
		label.setForeground(Color.white);
		return label;
	}

	private JButton makeButton(String text, ImageIcon icon, PanelID functionality, int x, int y, int width, int height){
		JButton button = new JButton(text,icon);
		//button.setIcon(icon);
		button.setBounds(x, y, width, height);
		
		button.setVerticalTextPosition(SwingConstants.TOP);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorderPainted(false);
		button.addActionListener(e->window.changeComp(functionality));
		button.setOpaque(false);
		///button.setBorderPainted(false);
		button.setFont(AssetManager.fonts[6]);
		button.setUI(new BasicButtonUI());
		button.setForeground(Color.white);
		button.addMouseListener(new MenuButtonHoverController(button));
		return button;
	}

}
