package de.ginosoft.energy.data;

import java.lang.reflect.Field;

/*
 * Possible units of measure for a value
 */
public class DataUnit {

	
	public static final DataUnit TIME = new DataUnit("TIME","Date", "");
	public static final DataUnit TEMPERATUR = new DataUnit("TEMPERATUR","Temperatur", "°C");
	public static final DataUnit VOLUMESTREAM = new DataUnit("VOLUMESTREAM","Volumestream", "l/h");
	public static final DataUnit RADIATION = new DataUnit("RADIATION", "Radiation", "w/m²");
	public static final DataUnit BINARY_INPUT = new DataUnit("BINARY_INPUT","Binary", "binary");
	public static final DataUnit UNUSED = new DataUnit("UNUSED","unused", "<unused>");
	public static final DataUnit ACTOR = new DataUnit("ACTOR", "Actor", "binary");
	public static final DataUnit POWER = new DataUnit("POWER", "Power", "kWh");
	public static final DataUnit VOLUME = new DataUnit("VOLUME", "Volume", "m^3");
	public static final DataUnit DURATION = new DataUnit("DURATION", "Duration", "h");
	public static final DataUnit NUMERIC = new DataUnit("NUMERIC", "Generic Number", "");
	
	private String m_ID;
	private String m_Name; 
	private String m_Unit;
	
	private DataUnit(String ID, String Name, String Unit){
		m_ID=ID;
		m_Name=Name;
		m_Unit=Unit;
	}
	
	public String getID() {
		return m_ID;
	}
	
	public String getName() {
		return m_Name;
	}
	
	public String getUnit() {
		return m_Unit;
	}
	
	/**
	 * 
	 * @return true, if the measure is the state of an output port
	 */
	public boolean isActor() {
		return this==DataUnit.ACTOR;
	}
	
	public String toString() {
		return "BDataUnit "+getID() + ": "+getName()+" ["+getUnit()+"]";
	}
	/**
	 * 
	 * @param unitID
	 * @return the data unit object instance that has the name of the given unitID or null
	 */
	public static DataUnit getDataUnit(String unitID) {
		try {
			Field field = DataUnit.class.getDeclaredField(unitID);
			return (DataUnit) field.get(null);
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_ID == null) ? 0 : m_ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataUnit other = (DataUnit) obj;
		if (m_ID == null) {
			if (other.m_ID != null)
				return false;
		} else if (!m_ID.equals(other.m_ID))
			return false;
		return true;
	}
}
