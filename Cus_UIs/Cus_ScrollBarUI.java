package Cus_UIs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import fileManager.AssetManager;

public class Cus_ScrollBarUI extends BasicScrollBarUI{
	
	//customized scroll bar, images are called from AssetManager
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(AssetManager.strokes[5]);
		g2d.setColor(Color.white);
		if(trackBounds.height<=300){
			g2d.drawLine(trackBounds.x, trackBounds.y + trackBounds.height / 2, 
					trackBounds.x + trackBounds.width, trackBounds.y + trackBounds.height / 2);
		}else{
			g2d.drawLine(trackBounds.x + trackBounds.width / 2, trackBounds.y , 
					trackBounds.x + trackBounds.width/2, trackBounds.y + trackBounds.height);
		}
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override    
	protected JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}

	private JButton createZeroButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(0, 0));
		button.setMinimumSize(new Dimension(0, 0));
		button.setMaximumSize(new Dimension(0, 0));
		return button;
	}
}
