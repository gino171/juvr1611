package de.ginosoft.energy.data;


public class DataItem {

	private DataPoint point;
	private MeasureDescription measure;
	
	public DataItem(DataPoint point, MeasureDescription type) {
		this.point = point;
		measure = type;
	}
	
	public DataPoint getPoint() {
		return point;
	}
	
	public MeasureDescription getType() {
		return measure;
	}
}
