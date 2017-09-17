package demos;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 演示PApplet
 * 
 * @author tang
 *
 */
public class MyPApplet extends PApplet{
	PImage img;
	
	public void setup() {
		//Add setup code for MyPApplet
		
	}
	
	public void draw() {
		//Add drawing code for MyPApplet
		
	}
	
	public void drawSun() {
		
	}
	
	/** Return the RGB color of the sun at this number of seconds in the minute */
	public int[] sunColorSec(float seconds)
	{
		int[] rgb = new int[3];
		
		return rgb;
	}
	
	public void drawHappyFace() {
		
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "demos.MyPApplet"});
	}
}


