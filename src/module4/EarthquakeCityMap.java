package module4;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 *  交互式地图应用
 *  @author 你的姓名
 * */
public class EarthquakeCityMap extends PApplet {
	
	private static final long serialVersionUID = 1L;

	// 如果要使用离线地图，将下面的变量设为true
	private static final boolean offline = false;
	
	/** 离线地图文件 */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	

	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// 包含城市信息和国家信息的文件名
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// 地图
	private UnfoldingMap map;
	
	// 城市标记
	private List<Marker> cityMarkers;
	// 地震标记
	private List<Marker> quakeMarkers;

	// 国家标记
	private List<Marker> countryMarkers;
	
	private CommonMarker lastSelected;
	@SuppressWarnings("unused")
	private CommonMarker lastClicked;
	
	public void setup() {		
		// (1) 初始化画布和地图
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new OpenStreetMap.OpenStreetMapProvider());
			// 如果要使用离线地图来测试，将下一行代码的注释去掉
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// 用于测试时：将下面2行代码中的一行去掉注释
		//earthquakesURL = "test1.atom";
		//earthquakesURL = "test2.atom";
		
		// (2) 读取地震数据和地理信息
	    //     STEP 1: 加载国家特征和标记
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
        //	   STEP 2: 读入城市数据
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
        //	   STEP 3: 读入地震的RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
	      //检查是否为LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    // 用于调试
	    printQuakes();
	 		
	    // (3) 在地图中加入标记
	    //     注意: 国家标记没有加到地图中，只使用几何信息
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	    
	}  // End setup
	
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
		
	}
	
	
	// TODO: 添加方法
	//   private void sortAndPrint(int numToPrint)
	// 并在setUp中调用此方法
	
	/** 鼠标移动时的事件处理器
	 */
	@Override
	public void mouseMoved()
	{
		// 清除上一次所选内容
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(quakeMarkers);
		selectMarkerIfHover(cityMarkers);
		//loop();
	}
	
	// 如果当前光标位置有一个标记，并且lastSelected为null时， 
	// 将lasstSelected设置为当前光标下的第一个标记
	// 注意不要选择2个记录. 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// TODO: 实现这个方法
	}

	
	/** 鼠标点击的事件处理器
	 * 显示某次地震及影响的城市
	 * 如果点击的是城市，则显示该城市所在的范围内的所有地震
	 */
	@Override
	public void mouseClicked()
	{
		// TODO: 实现这个方法
	}
	
	// 辅助方法：用于检查某个城市标记是否被点击
	@SuppressWarnings("unused")
	private void checkCitiesForClick()
	{
		// TODO: 实现这个方法	
	}
	
	// 辅助方法：用于检查某个地震标记是否被点击
	@SuppressWarnings("unused")
	private void checkEarthquakesForClick()
	{
		// TODO: 实现这个方法
	}
	
	// 循环并将所有标记隐藏
	@SuppressWarnings("unused")
	private void unhideMarkers() {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(false);
		}
			
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
	}
	
	// 用于在界面中绘制key的辅助方法
	private void addKey() {	
		fill(255, 250, 240);
		
		int xbase = 25;
		int ybase = 50;
		
		rect(xbase, ybase, 150, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", xbase+25, ybase+25);
		
		fill(150, 30, 30);
		int tri_xbase = xbase + 35;
		int tri_ybase = ybase + 50;
		triangle(tri_xbase, tri_ybase-CityMarker.TRI_SIZE, tri_xbase-CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE, tri_xbase+CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE);

		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		text("City Marker", tri_xbase + 15, tri_ybase);
		
		text("Land Quake", xbase+50, ybase+70);
		text("Ocean Quake", xbase+50, ybase+90);
		text("Size ~ Magnitude", xbase+25, ybase+110);
		
		fill(255, 255, 255);
		ellipse(xbase+35, 
				ybase+70, 
				10, 
				10);
		rect(xbase+35-5, ybase+90-5, 10, 10);
		
		fill(color(255, 255, 0));
		ellipse(xbase+35, ybase+140, 12, 12);
		fill(color(0, 0, 255));
		ellipse(xbase+35, ybase+160, 12, 12);
		fill(color(255, 0, 0));
		ellipse(xbase+35, ybase+180, 12, 12);
		
		textAlign(LEFT, CENTER);
		fill(0, 0, 0);
		text("Shallow", xbase+50, ybase+140);
		text("Intermediate", xbase+50, ybase+160);
		text("Deep", xbase+50, ybase+180);

		text("Past hour", xbase+50, ybase+200);
		
		fill(255, 255, 255);
		int centerx = xbase+35;
		int centery = ybase+200;
		ellipse(centerx, centery, 12, 12);

		strokeWeight(2);
		line(centerx-8, centery-8, centerx+8, centery+8);
		line(centerx-8, centery+8, centerx+8, centery-8);
		
		
	}
	
	// 检查地震是否发生在陆地上。如果是的话，设置在所属国家对应的PointFeature的"country"属性中，并返回true
	// 否则返回false
	private boolean isLand(PointFeature earthquake) {
		
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		
		return false;
	}
	
	/* 打印国家和地震数量，格式如下
	 * Country1: numQuakes1
	 * Country2: numQuakes2
	 * ...
	 * OCEAN QUAKES: numOceanQuakes
	 * */
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers)
			{
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
	}
	
	// 检查某次地震是否发生在该国范围内，
	// 如果是的话，将国家信息添加到地震的属性之中 
	// 注意：请勿修改此处的代码
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// 获取地点
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if (country.getClass() == MultiMarker.class) {

			// looping over markers making up MultiMarker
			for (Marker marker : ((MultiMarker) country).getMarkers()) {

				// checking if inside
				if (((AbstractShapeMarker) marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));

					// return if is inside one
					return true;
				}
			}
		}

		// check if inside country represented by SimplePolygonMarker
		else if (((AbstractShapeMarker) country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));

			return true;
		}
		return false;
	}

}
