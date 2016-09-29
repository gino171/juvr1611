package de.ginosoft.energy.data;

/**
 * A dataitem is a datapoint and its corresponding measure
 * @author Tobias
 *
 */
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
