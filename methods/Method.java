package methods;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.TreeMap;

public class Method {
	public static Random r = new Random();

	public static float clamp(float var, float min, float max){
		if(var>=max)
			return var = max;
		else if(var<=min)
			return var = min;
		else
			return var;
	}

	public static int random(int start, int end){
		return r.nextInt(end-start)+start;
	}

	public static int getIntFromColor(int Red, int Green, int Blue){
		Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
		Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
		Blue = Blue & 0x000000FF; //Mask out anything not blue.

		return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}

	public static BufferedImage toBufferedImage(Image img){
		if (img instanceof BufferedImage)
			return (BufferedImage) img;

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		// Return the buffered image
		return bimage;
	}

	public static float findAvg(TreeMap<String, Float> temp, String specificDate){

		float total = 0f;
		int counter = 0;
		for(String date: temp.keySet()){
			if(date.startsWith(specificDate)){
				total+=temp.get(date);
				counter++;
			}
		}

		return total/counter;
	}


	public static AlphaComposite makeTransparent(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type,alpha));
	}
	
	
}
