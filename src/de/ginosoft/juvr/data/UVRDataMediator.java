package de.ginosoft.juvr.data;

import java.util.Date;

import de.ginosoft.energy.data.DataPoint;
import de.ginosoft.energy.data.DataProvider;
import de.ginosoft.energy.data.MeasureDescription;
import de.ginosoft.energy.data.MeasureList;

/**
 * Wrapper providing the current values for the UVR1611
 * Abstracts from the internal representation of the UVRDataSet to the
 * more general interface BIDataProvider 
 * @author y1603
 *
 */
public class UVRDataMediator implements DataProvider {

	private UVRDataSet data;
	private static UVRMeasureList measurelist = new UVRMeasureList();
	
	public UVRDataMediator(UVRDataSet data) {
		this.data = data;
	}
	
	@Override
	public boolean hasMeasure(String valueID) {	
		return measurelist.hasMeasure(valueID);
	}

	@Override
	public double getValue(String valueID) {
		UVRMeasureDescription desc = (UVRMeasureDescription)measurelist.getDescription(valueID);
		if (desc==null) {
			throw new IllegalArgumentException("The measure '"+valueID+"' is unknown to UVR data provider.");
		}
		return data.getInput(desc.getIndex());	
	}
	
	@Override
	public MeasureDescription getDescription(String valueID) {
		return measurelist.getDescription(valueID);
	}

	@Override
	public MeasureList getAvailableMeasures() {
		return measurelist;
	}

	@Override
	public Date getDate() {
		return data.getDate();
	}

	@Override
	public DataPoint getDataPoint(String measureID) {
		return new DataPoint(data.getDate(), getValue(measureID));
	}

}
