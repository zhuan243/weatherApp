package application_Windows;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Cus_UIs.Cus_ComboBoxUI;
import Cus_UIs.Cus_ScrollBarUI;
import fileManager.AssetManager;
import fileManager.FileManipulator;
import input_Control.DocumentControl;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_Table extends JPanel{

	//setting up private instance variables
	private JLabel title;
	private JTextField searchField;
	private JLabel searchLabel;
	private JButton homeButton;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scroller;
	private JLabel cityLabel;
	private JComboBox<String> combo;
	private String city;
	private TableRowSorter<TableModel> sorter;

	//constructor
	public Cus_Table(Window window) {
		
		//initialization of title of table
		title = new JLabel("Data Table");
		title.setBounds(Window.WIDTH/2-60, 30, 300, 40);
		title.setForeground(Color.white);
		title.setFont(AssetManager.fonts[0]);
		
		//array of string that includes all headers for table
		String[] c=new String[]{"Date","Max Temp","Min Temp","Mean Temp",
				"Heat Days","Cool Days", "Total Rain","Total Snow","Total Precipitation",
				"Snow on Ground", "Direct Gust", "Speed Gust"};
		model=new DefaultTableModel(c,0);
		
		//initialize table format, included in Java API
		table=new JTable(model);

		//intialization of variable that will sort the data in the table
		sorter=new TableRowSorter<>(table.getModel());

		//initialize TextField where user can type in what they want to search
		searchField = new JTextField();
		searchField.setBounds(1030, 640, 200, 30);
		searchField.setFont(AssetManager.fonts[1]);
		searchField.getDocument().addDocumentListener(
				new DocumentControl(searchField,sorter));

		//initialize button that will take user back to main menu
		homeButton = new JButton("Back");
		homeButton.setBounds(40, 640, 110, 30);
		homeButton.setFont(AssetManager.fonts[1]);
		homeButton.setUI(new BasicButtonUI());
		homeButton.addActionListener(e->window.changeComp(PanelID.Menu));
		
		//set up of the scroll pane on table that will allow users to view all data
		scroller =new JScrollPane(table);
		scroller.setPreferredSize(new Dimension(1200,600));
		scroller.setBounds(40, 90, 1200, 530);
		
		//initialize current city that user is on
		cityLabel = new JLabel("City:");
		cityLabel.setBounds(Window.WIDTH/2-100-25, 640, 200, 30);
		cityLabel.setForeground(Color.white);
		cityLabel.setFont(AssetManager.fonts[1]);
		
		//initialize search label
		searchLabel = new JLabel("Search:");
		searchLabel.setBounds(960, 640, 200, 30);
		searchLabel.setForeground(Color.white);
		searchLabel.setFont(AssetManager.fonts[1]);

		//initialize array of string that includes all available cities for search
		String[] abc = new String[]{"Charlottetown","Iqaluit","Markham",
				"Vancouver","Whitehorse"};
		
		//initialize combo box where users can choose from a list of city options
		combo = new JComboBox<String>(abc);
		combo.setBounds(Window.WIDTH/2-100+25, 640, 200, 30);
		combo.setFont(AssetManager.fonts[1]);
		combo.setUI(new Cus_ComboBoxUI());
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCity();
			}
		});

		//table aesthetics; to make it look as nice as it is currently
		table.getTableHeader().setFont(AssetManager.fonts[5]);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Color.BLACK);
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setEnabled(false);
		table.setFont(AssetManager.fonts[1]);
		table.setForeground(Color.white);
		table.setRowSorter(sorter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionForeground(Color.cyan);
		table.setRowHeight(30);
		table.setGridColor(Color.white);
		table.setOpaque(false);
		((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
		
		//scrolling pane aesthetics
		scroller.setOpaque(false);
		scroller.getViewport().setOpaque(false);
		scroller.getHorizontalScrollBar().setUI(new Cus_ScrollBarUI());
		scroller.getHorizontalScrollBar().setOpaque(false);
		scroller.getVerticalScrollBar().setUI(new Cus_ScrollBarUI());
		scroller.getVerticalScrollBar().setOpaque(false);
		
		//method that sets up how wide each column can be
		for(int i=0;i<c.length;i++){
			table.getColumnModel().getColumn(i).setPreferredWidth(150);
		}

		//adding components to main Window and repainting each time if necessary
		setLayout(null);
		add(title);
		add(scroller);
		add(searchLabel);
		add(cityLabel);
		add(combo);
		add(searchField);
		add(homeButton);
		repaint();
		revalidate();

		refreshCity();
	}

	//method that draws on the background images behind the table
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));
		
	}

	//method that updates the data (per city) that the user wants to see onto the table
	private void refreshCity(){
		String selected = (String) combo.getSelectedItem();
		model.setRowCount(0);
		city=selected;
		changeCity(model);
		scroller.repaint();
	}

	//method that gets the data for each city as the user switches between cities
	public void changeCity(DefaultTableModel model){
		for(String date: FileManipulator.meanTemp.get(city).keySet()){
			model.addRow(new Object[] {date, FileManipulator.maxTemp.get(city).get(date),
					FileManipulator.minTemp.get(city).get(date),FileManipulator.meanTemp.get(city).get(date),
					FileManipulator.heatDays.get(city).get(date),FileManipulator.coolDays.get(city).get(date),
					FileManipulator.totalRain.get(city).get(date),FileManipulator.totalSnow.get(city).get(date),
					FileManipulator.totalPrecip.get(city).get(date),FileManipulator.snowOnGround.get(city).get(date),
					FileManipulator.direcGust.get(city).get(date),FileManipulator.speedGust.get(city).get(date)});
		}
	}

}


