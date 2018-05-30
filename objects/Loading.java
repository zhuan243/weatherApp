package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import application_Windows.Window;
import fileManager.AssetManager;
import methods.Method;

public class Loading extends EmptyObject{

	private Color green = new Color(0,255,153);
	private String text;
	private int timer;
	public Loading(float x, float y, ID id) {
		super(x, y, id);
		this.text = "Loading";
		this.timer = 0;
	}

	@Override
	public void tick() {
		timer++;
		if(timer%7==0){
			text += ".";
			if(text.length()>=11)
				text = "Loading";
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(Method.makeTransparent(0.8f));
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Window.WIDTH, (int) y);
		g2d.fillRect(0, (int)y, (int)x, Window.HEIGHT-(int)y);
		g2d.fillRect((int)x+600, (int)y, Window.WIDTH-(int)x-600, Window.HEIGHT-(int)y);
		g2d.fillRect((int)x, (int)y+600, 600, Window.HEIGHT-(int)y-600);

		g2d.drawImage(AssetManager.loading, (int)x, (int)y, null);
		g2d.setComposite(Method.makeTransparent(1));
		g2d.setColor(green);
		g2d.setFont(AssetManager.fonts[3]);
		g2d.drawString(text, x+120, y+500);

	}


}
