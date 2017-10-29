package module3;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** 实现一个地震图上的地震可视化标记
 * 
 * @author tang
 * @author 你的名字
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker
{
	
	// 地震是否发生在陆地上？子类中对这个变量进行赋值
	protected boolean isOnLand;

	// SimplePointMarker has a field "radius" which is inherited
	// by Earthquake marker:
	// protected float radius;
	//
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
  
	
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// 添加用于表示颜色的常量

	
	// 在子类中实现的抽象方法
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
		
	
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// 添加属性radius，并将其加入properties中
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}
	

	// 调用抽象方法drawEarthquake，并检查age的值，如果需要则绘制X
	public void draw(PGraphics pg, float x, float y) {
		// 保持之前的风格设置
		pg.pushStyle();
			
		// 根据depth来确定标记的颜色
		colorDetermine(pg);

		// 调用在子类中实现的抽象方法来绘制标记
		drawEarthquake(pg, x, y);
		
		// 可选 TODO: 对昨天发生的地震，在标记上绘制一个X		
		if (properties.get("age").equals("Past Day")){
			pg.text("X",x,y);
		}
		// 恢复之前的风格
		pg.popStyle();
		
	}
	
	// 根据depth来确定标记的颜色，使用pg.fill方法来设置pg的填充色
	// 建议: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		//TODO: 实现此方法
		if (getDepth()>=THRESHOLD_DEEP)
			pg.fill(255,0,0);
		else if (getDepth()>=THRESHOLD_INTERMEDIATE)
			pg.fill(0,0,255);
		else
			pg.fill(255,255,0);
	}
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	
	
}
