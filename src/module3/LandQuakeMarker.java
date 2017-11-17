package module3;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** 实现在地图上加入陆地地震的标记
 * 
 * @author tang
 * @author 你的姓名
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// 调用EarthquakeMarker构造方法
		super(quake);
		radius=3*getMagnitude();
		// setting field in earthquake marker
		isOnLand = true;
	}


	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// 绘制一个圆表示陆地地震
		// 不需要设置填充颜色
		
		// 提示: 注意EarthquakeMarker类中的radius变量及是如何进行初始化的
		
		// TODO: 实现此方法
		pg.ellipse(x,y,this.radius,this.radius);

	}
	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}



		
}