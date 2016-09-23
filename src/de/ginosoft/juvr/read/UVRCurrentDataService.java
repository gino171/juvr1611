package de.ginosoft.juvr.read;


import java.io.IOException;
import java.util.Date;
import java.util.Random;

import de.ginosoft.energy.data.DataProvider;
import de.ginosoft.juvr.data.UVRDataMediator;
import de.ginosoft.juvr.data.UVRDataSet;

/**
 * Reads the current data from UVR and provides it using by a BIDataProvider
 * @author y1603
 *
 */
public class UVRCurrentDataService {
	
	public DataProvider getCurrentData() {
		return new UVRDataMediator(getDummyData());
		//return  new BUVRDataProvider(readData());
	}
	
	private UVRDataSet getDummyData() {
		UVRDataSet uvrdata = new UVRDataSet();
		uvrdata.setDate(new Date());
		Random r = new Random();
		for (int i=0; i < 16; i++) {
			uvrdata.setInput(i,r.nextDouble()*100);
		}
		return uvrdata;
		
	}
	
	private UVRDataSet readData() {
		try {
			UVRConnection c = new UVR1611Connection();
			UVRCurrentDataReader cd = new UVRCurrentDataReader(c);
			return  cd.getCurrentData();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
