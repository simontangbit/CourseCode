package module3;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author tang
 * @author 你的姓名
 *
 */
public class CityMarker extends SimplePointMarker {
	
	// 三角形标记的大小, 在draw方法中使用
	public static final int TRI_SIZE = 5;  
	
	public CityMarker(Location location) {
		super(location);
	}
	
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	
	/**
	 * 在地图上绘制标记
	 */
	public void draw(PGraphics pg, float x, float y) {
		// 保存之前的绘制风格
		pg.pushStyle();
		
		// TODO: 添加代码实现绘制三角形用于表示CityMarker
		// 提示: pg是可以用来调用图形方法的对象.  例如 pg.fill(255, 0, 0)可以设置颜色为红色
		// x和y是所绘制对象的中心，用于计算出坐标并传递给图形绘制方法.  
		// 例如pg.rect(x, y, 10, 10)将绘制一个10x10的正方形，左上角坐标为x, y
		// 其它方法可以参考processing库的文档
		
		
		// 恢复之前的绘制风格
		pg.popStyle();
	}
	
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	
	public float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}
	
}
