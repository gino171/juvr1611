package de.ginosoft.juvr.data;

import de.ginosoft.energy.data.DataUnit;
import de.ginosoft.energy.data.MeasureDescription;


public class UVRMeasureDescription extends MeasureDescription {

	private int idx;
	public UVRMeasureDescription(String ID, String desc, DataUnit unit, int idx) {
		super(ID,desc,unit);
		this.idx = idx;
	}
	
	public int getIndex() {
		return idx;
	}
}
