package module3;

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
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 *  交互式地图应用
 *  @author tang
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
	// 地图标记
	private List<Marker> quakeMarkers;

	// 国家标记
	private List<Marker> countryMarkers;
	
	public void setup() {		
		// (1) 初始化画布和地图
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());
			// 如果要使用离线地图，将下一行代码的注释去掉
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// 用于测试：将下面2行代码中的一行去掉注释
		//earthquakesURL = "test1.atom";
		//earthquakesURL = "test2.atom";
		
		// (2) 读取地震数据和地理信息
	    //     STEP 1: 加载国家特征和标记
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//     STEP 2: 读入城市数据
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: 读入地震的RSS feed
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
	
	// 用于在界面中绘制key的辅助方法
	// TODO: 修改此方法，加入自己的实现
	private void addKey() {	
		fill(255, 250, 240);
		rect(25, 50, 150, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", 50, 75);
		
		fill(color(255, 0, 0));
		ellipse(50, 125, 15, 15);
		fill(color(255, 255, 0));
		ellipse(50, 175, 10, 10);
		fill(color(0, 0, 255));
		ellipse(50, 225, 5, 5);
		
		fill(0, 0, 0);
		text("5.0+ Magnitude", 75, 125);
		text("4.0+ Magnitude", 75, 175);
		text("Below 4.0", 75, 225);
	}

	
	
	// 检查地震是否发生在陆地上。如果是的话，设置在所属国家对应的PointFeature的"country"属性中，并返回true
	// 否则返回false
	@SuppressWarnings("unused")
	private boolean isLand(PointFeature earthquake) {
		
		
		// 对所有的国家标记进行循环访问。  
		// 检查地震的PointFeature是否在For each, check if the earthquake PointFeature is in the 
		// country in m.  注意isInCountry的参数是PointFeature和Marker  
		// 如果isInCountry返回true，isLand也应返回true
		for (Marker m : countryMarkers) {
			// TODO: 使用isInCountry方法来完成这个方法
			
		}
		
		
		// 不在任何国家中
		return false;
	}
	
	/* 打印国家和地震数量，格式如下
	 * Country1: numQuakes1
	 * Country2: numQuakes2
	 * ...
	 * OCEAN QUAKES: numOceanQuakes
	 * */
	private void printQuakes() 
	{
		// TODO: 实现这个方法
		
		
	}
	
	
	
	// 检查某次地震是否发生在该国范围内，
	// 如果是的话，将国家信息添加到地震的属性之中 
	// 注意：请勿修改此处的代码
	@SuppressWarnings("unused")
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// 获取地点
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

}
