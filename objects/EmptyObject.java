package objects;

import java.awt.Graphics;

public abstract class EmptyObject {

	protected float x, y;
	protected ID id;

	public EmptyObject(float x,float y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public abstract void tick();
	public abstract void render(Graphics g);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}	
	
}
