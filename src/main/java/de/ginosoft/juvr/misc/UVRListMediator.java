package de.ginosoft.juvr.misc;

import java.util.Iterator;
import java.util.List;

import de.ginosoft.energy.data.DataContainer;
import de.ginosoft.energy.data.DataSequence;
import de.ginosoft.energy.data.MeasureDescription;
import de.ginosoft.juvr.data.UVRDataSet;
import de.ginosoft.juvr.data.UVRMeasureList;

/**
 * Transforms a list of UVRDataSets into BDataContainer object
 */
public class UVRListMediator {
	
	private DataContainer container;
	
	public UVRListMediator(List<UVRDataSet> uvrdata, UVRMeasureList measures) {
		container = new DataContainer();
		Iterator<UVRDataSet> it = uvrdata.iterator();
		while (it.hasNext()) {
			UVRDataSet currData = it.next();
			UVRDataMediator dp = new UVRDataMediator(currData);
			Iterator<MeasureDescription> measureIt = measures.getAllMeasures().iterator();
			while (measureIt.hasNext()) {
				MeasureDescription currMeasure = measureIt.next();
				if (!container.hasSequence(currMeasure.getID())) {
					container.addSequence(new DataSequence(currMeasure));
				}
				DataSequence currSequence = container.getSequence(currMeasure.getID());
				currSequence.addDataPoint(dp.getDataPoint(currMeasure.getID()));
			}
		}
	}
	
	public DataContainer getContainer() {
		return container;
	}

}
