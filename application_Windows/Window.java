package application_Windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Cus_UIs.Cus_ComboBoxUI;
import fileManager.AssetManager;
import fileManager.CitiesInformation;
import fileManager.FileDownloader;
import fileManager.FileManipulator;
import fileManager.WeatherID;
import input_Control.FrameMouseDragController;
import object_Controller.BackgroundControl;
import objects.ID;
import objects.Loading;

/**
 * 
 * This class ...
 * 
 * @author Leo
 *
 */

public class Window{

	//public final Variable that indicate the WIDTH and HEIGHT of the window
	public final static int WIDTH = 1280;
	public final static int HEIGHT =  WIDTH * 9 / 16;

	private Component comp;
	private JFrame frame;

	private HashMap<PanelID, Component> panels;

	private JPanel progessPanel;

	private Thread t;

	private int sleepValue = 50;

	private boolean loading;

	private void init(){
		assetManager.init();

		fileManipulator.startCollecting(fileDownloader);
		
		panels = new HashMap<>();
		BackgroundControl bgControl = new BackgroundControl(this, assetManager);
		Cus_HeatMap heatMap = new Cus_HeatMap(this);
		Cus_Menu menu = new Cus_Menu(this,bgControl,fileDownloader);
		Cus_LineGraph lineGraph = new Cus_LineGraph(this, fileDownloader);
		Cus_Table table = new Cus_Table(this);
		Cus_PieChartScene pieChart = new Cus_PieChartScene(this);
		Cus_HoverMapScene hoverMap = new Cus_HoverMapScene(this);
		
		
		Cus_CitiesInformationScene charlottetownPage = new Cus_CitiesInformationScene("Charlottetown",AssetManager.charlottetownImageFlag, CitiesInformation.charlottownInfo, this);
		Cus_CitiesInformationScene vancouverPage = new Cus_CitiesInformationScene("Vancouver",AssetManager.vancouverImageFlag, CitiesInformation.vancouverInfo, this);
		Cus_CitiesInformationScene whitehorsePage = new Cus_CitiesInformationScene("Whitehorse",AssetManager.whitehorseImageFlag, CitiesInformation. whitehorseInfo, this);
		Cus_CitiesInformationScene iqaluitPage = new Cus_CitiesInformationScene("Iqaluit",AssetManager.iqaluitImageFlag, CitiesInformation.iqaluitInfo, this);
		Cus_CitiesInformationScene markhamPage = new Cus_CitiesInformationScene("Markham",AssetManager.markhamImageFlag, CitiesInformation.markhamInfo, this);

		Cus_FileDownloading loadingScene = new Cus_FileDownloading(this, fileDownloader, fileManipulator);
		
		Cus_Report report = new Cus_Report(this);
		
		panels.put(PanelID.HeatMap, heatMap);
		panels.put(PanelID.Menu, menu);
		panels.put(PanelID.LineGraph, lineGraph);
		panels.put(PanelID.Table, table);
		panels.put(PanelID.PieChart, pieChart);
		panels.put(PanelID.HoverMap, hoverMap);
		panels.put(PanelID.CharlottetownPage, charlottetownPage);
		panels.put(PanelID.VancouverPage, vancouverPage);
		panels.put(PanelID.WhitehorsePage, whitehorsePage);
		panels.put(PanelID.IqaluitPage, iqaluitPage);
		panels.put(PanelID.MarkhamPage, markhamPage);
		panels.put(PanelID.FileDownloading, loadingScene);
		panels.put(PanelID.Report,report);

		for(Component everyPanel: panels.values()){
			everyPanel.setBounds(0,0,WIDTH,HEIGHT);
		}
		this.comp = panels.get(PanelID.Menu);

		frame.remove(progessPanel);

		changeComp(PanelID.Menu);
		loading = false;
	}

	/**
	 * This method...
	 * 
	 * @author Zhenyu
	 * @param title	fsdgsdfgsdf
	 */

	private AssetManager assetManager;
	private FileDownloader fileDownloader;
	private FileManipulator fileManipulator;



	public Window(String title, AssetManager assetManager, FileDownloader fileDownloader, FileManipulator fileManipulator){

		this.assetManager = assetManager;
		this.fileDownloader = fileDownloader;
		this.fileManipulator = fileManipulator;
		assetManager.initLoading();
		loading = true;

		frame = new JFrame(title);

		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

		frame.setUndecorated(true);

		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		FrameMouseDragController dragControl = new FrameMouseDragController(frame);
		frame.addMouseListener(dragControl);
		frame.addMouseMotionListener(dragControl);

		Loading loadingAnimation = new Loading(350,0,ID.Loading);
		progessPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				loadingAnimation.tick();
				loadingAnimation.render(g);
			}
		};
		frame.add(progessPanel);
		progessPanel.setBounds(0,0,WIDTH,HEIGHT);
		frame.setVisible(true);

		t = new Thread() {
			public void run() {
				while(loading){
					progessPanel.repaint();
					//System.out.print("running");
					try {
						sleep(sleepValue);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		};
		t.setDaemon(true);
		t.start();

		init();
	}


	public void changeComp(PanelID key){
		if(comp instanceof Cus_HeatMap){
			((Cus_HeatMap) comp).stop();
		}
		frame.remove(this.comp);
		this.comp = panels.get(key);
		frame.add(comp);
		frame.repaint();
		frame.revalidate();

		if(comp instanceof Cus_HeatMap){
			((Cus_HeatMap) comp).start();
		}		
	}

	public void repaint(){
		if(frame!=null)
			frame.repaint();
	}
}

