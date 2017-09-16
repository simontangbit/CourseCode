package module1;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * 并列显示2幅不同地点的地图
 * 
 * @author tang
 *
 */
public class HelloWorld extends PApplet
{
	/**
	 * 目标：添加代码显示第二幅地图（北京），缩放， 设置背景
	 */
	// You can ignore this.  It's to keep eclipse from reporting a warning
	private static final long serialVersionUID = 1L;

	/** 离线时使用的地图 */
	public static String mbTilesString = "tiles/blankLight-1-3.mbtiles";
	
	// IF YOU ARE WORKING OFFLINE: Change the value of this variable to true
	private static final boolean offline = true;
	
	/** 地图1 */
	UnfoldingMap map1;
	
	/** 地图2 */ 
	UnfoldingMap map2;

	public void setup() {
		size(800, 600, P2D);  // 设置Applet窗口大小：800x600
		                      // The OPENGL argument indicates to use the 
		                      // Processing library's 2D drawing

		// 设置窗口的背景色
		this.background(200, 200, 200);
		
		// 设置地图提供者
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		AbstractMapProvider osProvider = new OpenStreetMap.OpenStreetMapProvider();
		// 设置缩放级别
		int zoomLevel = 10;
		
		if (offline) {
			// 离线地图  
			provider = new MBTilesMapProvider(mbTilesString);
			// 离线时的最大缩放级别
			zoomLevel = 3;
		}
		
		// 创建UnfoldingMap对象.  
		// 第2个至第5个参数：地图x, y坐标, 宽(width)和高(height)
		// 创建第2幅地图时也需要使用这些参数来让它显示在正确的位置.
		// 第6个参数用于指定地图提供者.  
		// 也可以使用MBTilesMapProvider来使用离线地图 
		map1 = new UnfoldingMap(this, 50, 50, 350, 500, osProvider);

		// 缩放并且将中心设置在 
	    // 32.9 (latitude) and -117.2 (longitude)
	    map1.zoomAndPanTo(zoomLevel, new Location(32.9f, -117.2f));
		
		// 使地图可交互
		MapUtils.createDefaultEventDispatcher(this, map1);
		
		// TODO: 在下面添加代码来创建map2 
		// 并修改下面的 draw() 
		
	}

	/** Draw the Applet window.  */
	public void draw() {
		// TODO: 加入代码显示地图
		map1.draw();
	}

	
}
