package de.ginosoft.energy.data;

import java.text.ParseException;
import java.util.Date;

/**
 * One Datapoint, a value measures at a certain time
 * @author Tobias
 *
 */
public class DataPoint {
	
	public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
	
	
	private long  timeStamp;
	private double value;
	
	public DataPoint(long time, double value) {
		timeStamp = time  ;
		this.value = value;
	}
	
	public DataPoint(java.util.Date date, double value) {
		this(date.getTime(),value);
	}
	
	public DataPoint(String date, double value) {
		timeStamp = getStringAsDate(date);
		this.value = value;
	}
	
	public long getTime() {
		return timeStamp;
	}
	
	public java.util.Date getDate() {
		return new java.util.Date(timeStamp );
	}
	
	public String getTimeAsString() {
		return getDateAsString(getTime());
	}
	
	public String getTimeAsSQLTimeStamp() {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(getDate());
	}
	
	public double getValue() {
		return value;
	}
	
	public String toString() {
		return getTimeAsString() +";"+value ;
	}
	
	/**
	 * @param date
	 * @return String representation of the date (long)
	 */
	public static String getDateAsString(long date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(DATE_FORMAT);
		return df.format(new Date(date));
	}
	
	/**
	 * Parses the date in the string and returns the correspondig long.
	 * Throws IllegalArgumentException if String is in the wrong format (not DATE_FORMAT)
	 * @param date
	 * @return 
	 */
	public static long getStringAsDate(String date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(DATE_FORMAT);
		try {
			return df.parse(date).getTime();
		} catch (ParseException e) {
			throw new IllegalArgumentException("Failed to parse date '"+date+"'. ",e);
		}
	}
	
	/**
	 * 
	 * @param other
	 * @return true if timestamp of the object is before timestamp of <other>
	 */
	public boolean isBefore(DataPoint other) {
		return getTime() < other.getTime();
	}
	
	/**
	 * 
	 * @param other
	 * @return true if timestamp of the object is after timestamp of <other>
	 */
	public boolean isAfter(DataPoint other) {
		return getTime() > other.getTime();
	}
	
	/**
	 * 
	 * @param other
	 * @return true if timestamp of the object is the same as timestamp of <other>
	 */
	public boolean isAtSameTime(DataPoint other) {
		return getTime() == other.getTime();
	}
}
