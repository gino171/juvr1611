package de.ginosoft.energy.data;


/**
 * Description which type of value a measure is,
 * e.g. outside temperature, boiler temperature, etc.
 * Each description has an ID, a descriptive string
 * @author y1603
 *
 */
public class MeasureDescription  {

	private String ID;
	private String description;
	private DataUnit unit;
	
	public MeasureDescription(String ID, String description, DataUnit unit) {
		if (ID == null) {
			throw new IllegalArgumentException("ID of Data unit must not be null");
		}
		if (unit == null) {
			throw new IllegalArgumentException("Data unit must not be null for BMeasureDescription '"+ID+"'");
		}
		this.ID = ID;
		this.description = description;
		this.unit = unit;
	}

	/**
	 * @return Unique ID of the measure type. For each measure, the must be unique
	 * ID, e.g. one ID for "outside temperature" or "boiler temperature"
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return (Localized) Textual description of the measure, eg
	 * "outside temperature" or "boiler temperature"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return DataUnit describing of which type the measured value, 
	 * e.g. BDataUnit.TEMPERATUR for measures that deliver temperature values.
	 */
	public DataUnit getDataUnit() {
		return this.unit;
	}

	@Override
	public int hashCode() {
		return ID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof MeasureDescription))
			return false;
		MeasureDescription other = (MeasureDescription) obj;
		return other.getID().equals(ID);
	}	
	
	
}
