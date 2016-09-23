package de.ginosoft.juvr.read;

import java.util.Date;

public class CurrentDataSetDecoder extends CommonDataSetDecoder {

	
	public CurrentDataSetDecoder(byte[] data) {
		super(data);
		// data as of right now
		m_dataSet.setDate( new Date());
	}
}
