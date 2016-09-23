package de.ginosoft.juvr.read;


import de.ginosoft.energy.data.DataItem;
import de.ginosoft.energy.data.DataPoint;
import de.ginosoft.energy.data.DataUnit;
import de.ginosoft.juvr.data.UVRDataSet;
import de.ginosoft.juvr.data.UVRMeasureDescription;


/*
 * One complete set of measured data at one point in time
 * including sensors and actors
 * 
 * @author tbl
 *
 */
public class CommonDataSetDecoder {

	
	protected UVRDataSet m_dataSet = new UVRDataSet();
	
	/**
	 * C'tor from byte array read from BL-NET / UVR 1611
	 * @param data
	 */
	protected CommonDataSetDecoder(byte[] data){

		for (int i=0; i<16; i++) {
			int value = byte2short(data[2*i],data[2*i+1]);
			m_dataSet.setInput(i, convertInput(value));
			m_dataSet.setInputUnit(i, convertUnit(value));
		}
	
		//Decode output status
		int output=byte2short(data[32], data[33]);
		for (int i=0; i<16; i++) {
			int mask = 1<<i;
			m_dataSet.setOutput(i, (output & mask)!=0);
		}
		
		
		m_dataSet.setTurn_A1(convertDrehZahl(data[34]));
		m_dataSet.setTurn_A2(convertDrehZahl(data[35]));
		m_dataSet.setTurn_A6(convertDrehZahl(data[36]));
		m_dataSet.setTurn_A7(convertDrehZahl(data[37]));
		
		m_dataSet.setHasPower1(((data[38]&0x01) >0));
		if (m_dataSet.isHasPower1()){
			m_dataSet.setPower1(convertPower(data[39],data[40],data[41],data[42]));
			m_dataSet.setMwz1(convertWMZ(data[43], data[44], data[45],data[46]));
		}
		
		m_dataSet.setHasPower2(((data[38]&0x02) >0));
		if (m_dataSet.isHasPower2()) {
			m_dataSet.setPower2(convertPower(data[47],data[48],data[49],data[50]));
			m_dataSet.setMwz2(convertWMZ(data[51], data[52], data[53], data[54]));
		}

	}

	/**
	 *
	 * @return the data object containing the measured UVD data
	 */
	public UVRDataSet getDataSet() {
		return m_dataSet;
	}
	
	/**
	 * returns the measured value of the type datatype, or null if no value exists
	 * @param datatype
	 * @return
	 */
	public DataItem getDataItem(UVRMeasureDescription measure) {
		int index = measure.getIndex();
		if (measure.getDataUnit().isActor()) {
			// handle actors
			return new DataItem(new DataPoint(m_dataSet.getDate(),m_dataSet.getOutputAsDouble(index)), measure);
			
		} else {
			return new DataItem(new DataPoint(m_dataSet.getDate(),m_dataSet.getInput(index)),measure);
		}		
	}

	

	private int byte2short(byte lo, byte hi) {
		return ((0xff & lo) + 256* (hi&0xFF));
	}

		private static Double convertInput(int value) {

		double sign = (value & 0x8000) != 32768 ? 1.0 : -1.0;
		double baseval = (double)(value & 0xfff);
		if (sign < 0.0D) {
			// negative
			baseval=-(0x1000-baseval);
		}
		switch(value & 0x7000)
		{
		case 0:
			// Not used
			return UVRDataSet.UNUSED;

		case 0x1000: 
			// digital value
			return Double.valueOf(sign >= 0.0D ? 0.0D : 1.0D);

		case 0x2000: 
			// temperature
			return Double.valueOf(baseval / 10D);

		case 0x3000:
			// volumenstrom
			return Double.valueOf(baseval * 4D);

		case 0x6000:
			//radiation
			return Double.valueOf(baseval);

		case 0x7000:
			//room sensor
			return Double.valueOf((value &0x3ff) / 10D);
		}
		return UVRDataSet.UNUSED;
	}

	private static DataUnit convertUnit(int value) {
		switch(value & 0x7000)
		{
		case 0:
			// Not used
			return DataUnit.UNUSED;

		case 0x1000: 
			// digital value
			return DataUnit.BINARY_INPUT;

		case 0x2000: 
			// temperature
			return DataUnit.TEMPERATUR;

		case 0x3000:
			// volumenstrom
			return DataUnit.VOLUMESTREAM;

		case 0x6000:
			//radiation
			return DataUnit.RADIATION;

		case 0x7000:
			//room sensor
			return DataUnit.TEMPERATUR;
		}
		return null;

	}

	private double convertPower(byte ll, byte lh, byte hl, byte hh) {
		if ((hh & 0x80) > 0) {
			// negative
			return (10*( (65536*(hh &0x7F)+ 256* (hl & 0xFF)+ (lh & 0xFF))-65536*128) + (double)((ll & 0xFF) *10 / 256.0)) / 100.0;
		} else {
			// positive
			return (10*(65536*(hh &0xFF)+ 256* (hl & 0xFF)+ (lh & 0xFF)) + (double)((ll & 0xFF) *10 / 256.0)) / 100.0;
		}
	}

	/** 
	 * 
	 * @param val
	 * @return -1, if val not active, or val;
	 */
	private short convertDrehZahl(byte val) {
		if ((val & 0x80) == 0) {
			return (short)(val & 0x1F);
		}else {
			return -1;
		}
	}

	private double convertWMZ(byte kwl,byte kwh, byte mwl, byte mwh) {
		return 1000*((mwh & 0xFF)*256+(mwl & 0xFF))+(((kwh & 0xFF)*256+(kwl & 0xFF))/10.0);
	}
}