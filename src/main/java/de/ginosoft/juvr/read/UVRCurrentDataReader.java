package de.ginosoft.juvr.read;

import java.io.IOException;

import de.ginosoft.juvr.data.UVRDataSet;

/**
 * Reads current data from UVR host and returns is when calling getCurrentData()
 * @author gino171
 *
 */
public class UVRCurrentDataReader extends AbstractReader {
	
	private static char UVR_CURRENT_DATA = 0xAB;
	private CurrentDataSetDecoder m_res;
	
	public UVRCurrentDataReader(UVRConnection conn) throws IOException {
		super(conn);
	}
	
	
	/**
	 * Retrieve the current data
	 * @return Data set with current status / values of the UVR device
	 * @throws IOException
	 */
	public UVRDataSet getCurrentData() throws IOException {
		doCommand();
		return m_res.getDataSet();
		
	}
	
	private void doCommand() throws IOException {
		sendCommand(UVR_CURRENT_DATA);
		byte[] ans = new byte[56];
		byte first= (byte)m_conn.getInputStream().read();
		if (first==-128) {
			ans = m_conn.readBlock(56);
			m_res = new CurrentDataSetDecoder(ans);
		}
	}
	
}
