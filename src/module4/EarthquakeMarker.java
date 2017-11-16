package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** 实现一个地震图上的地震可视化标记
 * 
 * @author tang
 * @author 你的名字
 *
 */
// TODO: 实现Comparable接口
public abstract class EarthquakeMarker extends CommonMarker
{
	
	// 地震是否发生在陆地上？
	// 子类中对这个变量进行赋值
	protected boolean isOnLand;

	// 地震标记的半径 
	protected float radius;
	
	
	// 用于距离的常量
	protected static final float kmPerMile = 1.6f;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// 添加用于颜色的常量

	
	// 在子类中实现的抽象方法
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
		
	
	// 构造方法
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}
	
	// TODO: 添加方法
	// public int compareTo(EarthquakeMarker marker)
	
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// 保存之前的风格
		pg.pushStyle();
			
		// 根据地震深度来确定标记的颜色
		colorDetermine(pg);
		
		// 调用子类中实现的抽象方法来绘制标记形状
		drawEarthquake(pg, x, y);
		
		// IMPLEMENT: add X over marker if within past day		
		String age = getStringProperty("age");
		if ("Past Hour".equals(age) || "Past Day".equals(age)) {
			
			pg.strokeWeight(2);
			int buffer = 2;
			pg.line(x-(radius+buffer), 
					y-(radius+buffer), 
					x+radius+buffer, 
					y+radius+buffer);
			pg.line(x-(radius+buffer), 
					y+(radius+buffer), 
					x+radius+buffer, 
					y-(radius+buffer));
			
		}
		
		// 恢复之前的风格
		pg.popStyle();
		
	}

	/** 如果标记被选中，则显示地震的名称 */
	public void showTitle(PGraphics pg, float x, float y)
	{
		// TODO: 实现此方法
		
	}

	
	/**
	 * 返回地震的“危害范围”半径 
	 * 
	 */
	public double threatCircle() {	
		double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
		double km = (miles * kmPerMile);
		return km;
	}
	
	// 根据地震深度来确定标记的颜色
	// Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
		
		if (depth < THRESHOLD_INTERMEDIATE) {
			pg.fill(255, 255, 0);
		}
		else if (depth < THRESHOLD_DEEP) {
			pg.fill(0, 0, 255);
		}
		else {
			pg.fill(255, 0, 0);
		}
	}
	
	
	public String toString()
	{
		return getTitle();
	}
	
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
