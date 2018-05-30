package Cus_UIs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

import fileManager.AssetManager;

public class Cus_SliderUI extends BasicSliderUI{
	
	//customized slider, images called from AssetManager
	public Cus_SliderUI(JSlider slider) {
		super(slider);
	}

	

	@Override
	protected Dimension getThumbSize() {
		return new Dimension(12, 16);
	}

	@Override
	public void paintTrack(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(AssetManager.strokes[5]);
		g2d.setColor(Color.white);
		g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, 
				trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
	}

	@Override
	public void paintThumb(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillOval(thumbRect.x-1,thumbRect.y,15,15);		
	}
	
	@Override
	protected void paintMajorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x) {
		g.setColor(Color.white);
		super.paintMajorTickForHorizSlider(g, tickBounds, x);
	}
	

}
