package application_Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicButtonUI;

import Cus_UIs.Cus_ScrollBarUI;
import fileManager.AssetManager;
import fileManager.FileDownloader;
import fileManager.FileManipulator;
import methods.Method;
import object_Controller.BackgroundControl;

public class Cus_FileDownloading extends JPanel{
	//Set up different window, textfield, and a button. Calling up fileDownloader and File Manipulator.
	private Window window;
	private FileDownloader fileDownloader;
	private FileManipulator fileManipulator;
	private JTextArea textField;
	private JButton back, startDownload;

	public Cus_FileDownloading(Window window, FileDownloader fileDownloader, FileManipulator fileManipulator){
		//Assigning variables to window, fileDownloader, and fileManipulator so they may be used in the code.
		this.window = window;
		this.fileDownloader = fileDownloader;
		this.fileManipulator = fileManipulator;

		// Text pane where updated information will be downloaded and visualized. 
		textField = new JTextArea();
		textField.setFont(AssetManager.fonts[1]);
		textField.setText("Are you sure you want to update Data?\nIf so, click \"Start Downloading\" button.\nWarning: No interuption should be made while downloading!");
		textField.setEditable(false); //do not allow user to edit text
		textField.setFocusable(false); //ensure scrolling does not affect this text field
		textField.setOpaque(false); //make the background transparent
		textField.setForeground(Color.white); //Set the foreground to white


		// Add a scroll pane to the text field
		JScrollPane scroller = new JScrollPane(textField); 
		scroller.setBounds((Window.WIDTH-1000)/2, (Window.HEIGHT-600)/2, 1000, 500);
		scroller.setOpaque(false);//make background transparent
		scroller.getViewport().setOpaque(false);
		scroller.setHorizontalScrollBar(null); //no horizontal scroll
		scroller.getVerticalScrollBar().setUI(new Cus_ScrollBarUI()); //add a vertical scroll
		scroller.getVerticalScrollBar().setOpaque(false);

		//button for downloading updated data
		startDownload = new JButton("Start Download");
		startDownload.setBounds(920, 630, 150, 30);
		startDownload.setFont(AssetManager.fonts[1]);
		startDownload.setUI(new BasicButtonUI());
		
		//back button to return to main menu
		back = new JButton("Back");
		back.setBounds(1080, 630, 150, 30);
		back.setFont(AssetManager.fonts[1]);
		back.setUI(new BasicButtonUI());
		
		setLayout(null);//set absolute positioning
		add(scroller); //add the scroll pane to JPanel
		add(back); //add back button to JPanel
		add(startDownload);//add start download button to JPanel

		startDownload.addActionListener(e->{ //pressing the startDownload button will initiate the following method
			download(); 
		});

		back.addActionListener(e->window.changeComp(PanelID.Menu)); //pressing the back button will return to main menu
	}

	public void download(){ //method for updating data

		textField.setText("Downloading\nThis might take a while\n");
		Thread t = new Thread(){
			@Override
			public void run(){
				//does not allow these buttons to be pressed
				back.setEnabled(false); 
				startDownload.setEnabled(false);
				fileDownloader.download(textField);//download using the fileDownloader class based on inputed year
				textField.setText(textField.getText()+"\n"+"Organizing Data");
				textField.setSelectionStart(textField.getText().length());
				textField.setSelectionEnd(textField.getText().length());
				fileManipulator.startCollecting(fileDownloader); //File manipulator to re-organize data to be usable in code.
				textField.setText(textField.getText()+"\n\n"+"Finished!\nYou can go back to main menu now.");
				textField.setSelectionStart(textField.getText().length());
				textField.setSelectionEnd(textField.getText().length());
				//re-authorize the use of these buttons
				back.setEnabled(true);
				startDownload.setEnabled(true);
			}
		};
		t.start();
		
	}
	//method used to add java images, or more specifically, adding the background to the frame
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));

	}
}
