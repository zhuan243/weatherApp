package application_Windows;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fileManager.AssetManager;
import input_Control.MouseInput;
import methods.Method;
import object_Controller.BackgroundControl;
import object_Controller.MassHandler;
import objects.ID;
import objects.Loading;
import objects.Map;

public class Cus_HeatMap extends JPanel implements Runnable{

	//frame speed, slower value will let the application run slower, vice versa
	private Thread thread;

	private boolean running;
	//MassController that will tick and render everything
	private MassHandler handler;

	private Cus_HeatMap_Control control;
	//called with report is first created, initializing
	public Cus_HeatMap(Window window){
		running = false;
		//initialize the handler 
		handler = new MassHandler();

		Map canadaMap = new Map(Window.WIDTH/2-AssetManager.canadaMap.getWidth(null)/2,0,ID.Map);
		Loading loading = new Loading(350,0,ID.Loading);

		handler.addObject(canadaMap);
		
		
		
		setLayout(null);
		setBackground(Color.black);

		control = new Cus_HeatMap_Control(window,canadaMap,handler, loading);
		control.setBounds(0,0,250,Window.HEIGHT);
		add(control);
		
		addMouseListener(new MouseInput(canadaMap,control.getRequestX(), control.getRequestY()));

	}


	public synchronized void start(){
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		running = true;
	}

	public synchronized void stop(){
		running = false;
	}



	@Override
	public void run() {
		while(running){
			tick();
			render();
			try {
				thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void tick(){
		handler.tick();
	}

	private void render(){
		repaint();
	}


	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.drawImage(AssetManager.backgroundImage, 0, 0, null);
		g2d.setColor(Color.black);
		g2d.setComposite(Method.makeTransparent(BackgroundControl.backgroundDarkLayer));
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g2d.setComposite(Method.makeTransparent(1));
		handler.render(g2d);
	}
}




