package de.ginosoft.juvr.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.ginosoft.juvr.data.UVRDataHeader;
import de.ginosoft.juvr.data.UVRDataSet;

/**
 * Reader class to read all data records stored in UVR host into memory
 * when calling doCommand()
 * @author Tobias Blickle, 2011
 * 
 */
public class UVRDataReader extends AbstractReader {

	private static char UVR_READ_DATA = 0xAC;
	private int m_numRecToRead = 0;
	private ArrayList<UVRDataSet> m_data = new ArrayList<UVRDataSet>();

	/**
	 * Reads all records available on UVR host
	 * @param conn Established connection to UVR host
	 */
	public UVRDataReader(UVRConnection conn) throws IOException {
		super(conn);
		UVRDataHeaderReader r = new UVRDataHeaderReader(conn);
		UVRDataHeader header =  r.getHeader();
		m_numRecToRead = header.getNumRecords();
	}
	/**
	 * 
	 * @param conn Established connection to UVR host
	 * @param numRecordtoRead How many records are available
	 */
	public UVRDataReader(UVRConnection conn, int numRecordtoRead){
		super(conn);
		m_numRecToRead = numRecordtoRead;
	}

	/*
	 * (non-Javadoc)
	 * @see de.blickle.uvr.read.BAbstractReader#doCommand()
	 */
	private void doCommand() throws IOException {
		int offset = 0;
		// read data in 8 block chunks.
		while (m_numRecToRead > 0) {
			// read max 8 entries
			int blocksize = Math.min(8, m_numRecToRead);
			sendCommand(offset, blocksize);
			readBlock(blocksize);
			m_numRecToRead -= blocksize;
			offset += blocksize;
		}
	}
	
	/*
	 * Returns all data read from UVR host
	 */
	public List<UVRDataSet> getData () throws IOException {
		if (m_numRecToRead >0 ) {
			doCommand();
		}
		return m_data;
	}

	/**
	 * Reads one block of max 8 data items, i.e. numToRead * 64 + 1 checksum
	 * byte.
	 * 
	 * @param numToRead
	 * @throws IOException
	 */
	private void readBlock(int numToRead) throws IOException {
		byte[] ans = new byte[64];

		for (int i = 0; i < numToRead; i++) {
			m_conn.getInputStream().read(ans, 0, 64);
			HistoricDataSetDecoder dec = new HistoricDataSetDecoder(ans);
			m_data.add(dec.getDataSet());
		}
		// Checksum
		m_conn.getInputStream().read();
	}

	/**
	 * Send a command to read specified num of data records from given offset
	 * @param offset
	 * @param blocksize
	 * @throws IOException
	 */
	private void sendCommand(int offset, int blocksize) throws IOException {

		byte buffer[] = new byte[6];
		buffer[0] = (byte) UVR_READ_DATA;
		buffer[1] = 0;
		buffer[2] = (byte) ((offset / 2) & 0xFF);
		buffer[3] = (byte) ((offset / 512) & 0xFF);
		buffer[4] = (byte) blocksize;
		buffer[5] = (byte) ((buffer[0] + buffer[1] + buffer[2] + buffer[3] + buffer[4]) % 0x100);

		m_conn.getOutputStream().write(buffer);
		m_conn.getOutputStream().flush();
	}

}
