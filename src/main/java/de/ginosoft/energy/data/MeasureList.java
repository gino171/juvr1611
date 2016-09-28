package de.ginosoft.energy.data;

import java.util.List;
import java.util.ArrayList;

/**
 * Maintains all available measures
 * @author y1603
 *
 */
public class MeasureList {

	private  List<MeasureDescription> measureList = new ArrayList<MeasureDescription>();
	
	public MeasureList() {}
	
	protected void addMeasure(MeasureDescription item) {
		if (!hasMeasure(item.getID())) {
			this.measureList.add(item);
		} else {
			throw new IllegalArgumentException("Measure "+item.getID()+" already defined");
		}
	}
	
	/*
	 * returns true, if the measure with the ID is known.
	 */
	public boolean hasMeasure(String ID) {
		return (null != getDescription(ID));
	}
	
	/**
	 * 
	 * @param ID
	 * @return The measure description interface of the requested measure 
	 * or null, if the measure is unknown.
	 */
	public MeasureDescription getDescription(String ID) {
		for (MeasureDescription iter : measureList) {
			if (iter.getID().equals(ID)) {
				return iter;
			}
		}
		return null;
	}
	
	public List<MeasureDescription> getAllMeasures() {
		return measureList;
	}
	
}
