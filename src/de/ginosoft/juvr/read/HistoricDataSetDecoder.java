package de.ginosoft.juvr.read;

import java.util.GregorianCalendar;


/**
 * Object decoding one data set to generate from historical data readings
 * @author tobias
 *
 */
public class HistoricDataSetDecoder extends CommonDataSetDecoder {
	
	/**
	 * C'tor with byte array read from UVR host
	 * @param data
	 */
	public HistoricDataSetDecoder(byte[] data) {
		super(data);
		GregorianCalendar cal = new GregorianCalendar(2000+data[60],data[59]-1,data[58],data[57],data[56],data[55]);
		m_dataSet.setDate( cal.getTime());

	}
	
}
