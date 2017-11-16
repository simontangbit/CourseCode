package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** 地震地图中用于陆地地震的可视化标记类
 * 
 * @author tang
 * @author 你的姓名
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// 调用父类的构造方法
		super(quake);
		
		isOnLand = true;
	}


	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.ellipse(x, y, 2*radius, 2*radius);
		
	}
	

	public String getCountry() {
		return (String) getProperty("country");
	}

		
}