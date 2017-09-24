package demos;

import processing.core.PApplet;
import processing.core.PImage;

import javax.print.DocFlavor;
import java.awt.*;

/**
 * 演示PApplet
 * 
 * @author 王关飞
 *
 */
public class MyPApplet extends PApplet{
	private PImage _img;
	public String ImageURL;
	public int ImageHeight=300;

	public int ScreenWidth,ScreenHeight;
	
	public void setup() {

		ScreenWidth=500;
		ScreenHeight=300;
		size(ScreenWidth,ScreenHeight);

	}
	
	public void draw() {
//		Add drawing code for MyPApplet
		//drawSun();
		drawHappyFace();

	}
	
	public void drawSun() {
		ImageURL="palmTrees.jpg";
		_img=loadImage(ImageURL,"jpg");
		_img.resize(0,height);
		image(_img,0,0);
		fill(255,204,0);
		ellipse(80,50,ScreenWidth/4,ScreenHeight/3);
	}
	
	/** Return the RGB color of the sun at this number of seconds in the minute */
	public int[] sunColorSec(float seconds)
	{
		int[] rgb = new int[3];

		return rgb;
	}
	
	public void drawHappyFace() {

		background(125, 125, 125);
		fill(255,204,0);
		ellipse(ScreenWidth/2,ScreenHeight/2,
				ScreenWidth/1.2f,
				ScreenHeight/1f);
		fill(51);
		ellipse(150,80,50,50);
		ellipse(350,80,50,50);
		noFill();
		arc(250, 200, 60, 60, TWO_PI, TWO_PI+PI);
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
//		PApplet.main(new String[] {"--present", "demos.MyPApplet"});
		PApplet.main(new String[] {"--present", "module1.HelloWorld"});
	}
}


