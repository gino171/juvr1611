package de.ginosoft.energy.data;

import java.util.Date;

/**
 * A server interface providing measured data 
 * @author y1603
 *
 */
public interface DataProvider {

	/**
	 * @param measureID
	 * @return the value for the given description or throws IllegalArgumentException
	 */
	public double getValue(String measureID);
	
	/**
	 * 
	 * @return the point in time (timestamp) when the data has been measured
	 */
	public Date getDate();
	
	/**
	 * Convenience method
	 * @param measureID
	 * @return the measure value as DataPoint (tuple of value and date). Throws IllegalArgumentException if measureID is unknown.
	 */
	public DataPoint getDataPoint(String measureID); 
		
	/**
	 * @param measureID
	 * @return true, if this provider knows the measure defined by this ID
	 */
	public boolean hasMeasure(String measureID);
	
	/**
	 * @param measureID
	 * @return The metainformation / description for the measure with the given measureID
	 * or null, if the measure is unknown
	 */
	public MeasureDescription getDescription(String measureID);
	

	/**
	 * @return the list of  measures available from this provider
	 */
	public MeasureList getAvailableMeasures();
	
}
