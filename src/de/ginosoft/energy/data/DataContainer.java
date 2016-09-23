package de.ginosoft.energy.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A data container stores several sequences of measurements
 * @author y1603
 *
 */
public class DataContainer {

	// Map MeasureID -> Datasequence
	private Map<String, DataSequence> measureID2SequenceMap = new HashMap<String, DataSequence>();
	// List of all available Measures
	private List<MeasureDescription> measures = new ArrayList<MeasureDescription>();
	
	
	/**
	 * adds one sequence to the container.
	 * IllegalArgumentException, if sequence already known.
	 * @param sequence
	 */
	public void addSequence(DataSequence sequence) {
		if (measureID2SequenceMap.get(sequence.getMeasure().getID()) != null ) {
			throw new IllegalArgumentException("Sequence with ID "+sequence.getMeasure().getID()+ " already stored in container.");
		}
		measures.add(sequence.getMeasure());
		measureID2SequenceMap.put(sequence.getMeasure().getID(),sequence);
	}
	
	/**
	 * 
	 * @param measureID
	 * @return the BDataSequence with the all values of the measure (measureID) or IllegalArgumentException
	 */
	public DataSequence getSequence(String measureID) {
		DataSequence ret =measureID2SequenceMap.get(measureID); 
		if (ret == null) {
			throw new IllegalArgumentException("BDataContainer: Unknown measure "+measureID);
		}
		return ret; 
	}
	
	public boolean hasSequence(String measureId) {
		return (null != measureID2SequenceMap.get(measureId));
	}
	
	/**
	 * All measures available in this container.
	 * @return
	 */
	public List<MeasureDescription> getAllMeasures() {
		return measures;
	}
	
	public String toString() {
		String res="";
		for (String iter : measureID2SequenceMap.keySet()) {
			res += iter + "\n";
		}
		return res;
	}
}
