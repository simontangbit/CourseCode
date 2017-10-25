package module3;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** 实现在地图上加入海洋地震的标记
 * 
 * @author tang
 * @author 你的姓名
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// 绘制一个正方形表示海洋中的地震
		// 不需要设置填充颜色。
		
		// 提示: 注意EarthquakeMarker类中的radius变量及是如何进行初始化的
		
		// TODO: 实现此方法
		
	}
	


	

}
