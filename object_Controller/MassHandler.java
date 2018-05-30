package object_Controller;

import java.awt.Graphics;
import java.util.ArrayList;

import objects.EmptyObject;

public class MassHandler {

	private ArrayList<EmptyObject> objects = new ArrayList<>();
	
	public void tick(){
		for(int i = 0; i < objects.size(); i++){
			EmptyObject tempObject = objects.get(i);
			tempObject.tick();
		}
	}

	public void render(Graphics g){
		for(int i = 0; i < objects.size(); i++){
			EmptyObject tempObject = objects.get(i);
			tempObject.render(g);
		}
	}
	
	
	public void addObject(EmptyObject object){
		this.objects.add(object);
	}
	
	public void addObject(ArrayList<EmptyObject> objects){
		this.objects.addAll(objects);
	}


	public void removeObject(EmptyObject object){
		this.objects.remove(object);
	}
	
	public void removeObject(ArrayList<EmptyObject> objects){
		this.objects.removeAll(objects);
	}
	
	public void clearObject(){
		this.objects.clear();
	}
	
}





