package module2;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;
import processing.core.PFont;
import processing.core.PGraphics;

/**
 * EarthquakeCityMap
 * 
 * 在地图上显示地震数据
 * 
 * @author tang
 *
 */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map; 
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	public PFont font;
	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// 如果想使用本地文件，将下一行代码去掉注释
			// earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // 创建Marker数组
	    List<Marker> markers = new ArrayList<Marker>();

	    // 使用ParseFeed获取地震数据
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // TODO: 循环处理每个地震数据，
	    // 调用下面定义的createMarker来对每个地震创建SimplePointMarker和PointFeature
	    // 之后存入数组中
	    for (PointFeature point:earthquakes){
	    	markers.add(createMarker(point));
		}
	    
	    // 将markers添加到地图上并显示
	    map.addMarkers(markers);
		font=createFont("Arial",16,true);
	}
		
	/* createMarker: 用于创建Feature和Marker的辅助方法
	 * 
	 * TODO: 添加代码，可根据地震级别来区分标记  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// 如果需要打印PointFeature中的所有内容，将下一行代码去掉注释
//		System.out.println(feature.getProperties());

		// 根据给定的PointFeature中的location来创建SimplePointMarker
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
//		System.out.println(marker);

		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// 下面的示例是使用Procession的color方法来生成代表黄色的整数值
	    int yellow = color(255, 255, 0);
		int red=color(255,0,0);
		int blue=color(0,0,255);
		// TODO : 添加代码，根据地震级别来设计标记的大小和颜色  
	    // 注意在上面定义的2个常量：THRESHOLD_MODERATE和 THRESHOLD_LIGHT
	    // 可以与这2个值进行比较来确定地震的级别
	    if (mag>=THRESHOLD_MODERATE){
			marker.setColor(red);
		}else if(mag<THRESHOLD_MODERATE&&mag>=THRESHOLD_LIGHT){
			marker.setColor(yellow);
		}
		else
			marker.setColor(blue);
	    // 返回marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}
	// helper method to draw key in GUI
	// TODO: 实现此方法，在地图中显示标记说明
	private void addKey() 
	{	
		// 使用Processing中提供的方法来绘制图形
		fill(219,219,190);
		rect(0,50,200,400);
		noFill();
		fill(0);
		textFont(font);
		text("earthquake map",50,70);
		text("5级以上地震",70,150);
		text("4级以上地震",70,200);
		text("4级以下地震",70,250);
		noFill();
		fill(255,0,0);
		ellipse(30,150,20,20);
		noFill();
		fill(255, 255, 0);
		ellipse(30,200,15,15);
		noFill();
		fill(0,0,255);
		ellipse(30,250,10,10);
		noFill();
	}

	public static void main(String[] args){
		PApplet.main(new String[] {"--present", "module2.EarthquakeCityMap"});
	}
}
