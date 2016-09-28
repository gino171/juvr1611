package de.ginosoft.juvr.read;

import java.io.IOException;
import java.io.InputStream;

import de.ginosoft.juvr.data.UVRDataHeader;

/**
 * reads the header information from UVR1611
 * Call doCommand() to extract the data
 * Result can be obtained by getHeader()
 * doCommand() is only executed once if valid data is retrieved.
 * @author tbl
 *
 */
public class UVRDataHeaderReader extends AbstractReader {
	
	private static char UVR_READ_HEADER_DATA = 0xAA;
	private static final int UVR1611 = 0x76;
	

	private int m_checkSum=0;
	private int m_byteCounter = 0;
	private UVRDataHeader m_header;
	
	
	public UVRDataHeaderReader(UVRConnection conn) {
		super(conn);
	}

	public void doCommand() throws IOException{
		 int[] time = new int[3];
		 int satzlaenge;
		 int[] startadresse = new int[3];
		 int[] endadresse = new int[3];
		 int pruefsum=0;
		 
		 // Data already read
		 if (m_header != null) {
			 return;
		 }
		 
		 UVRDataHeader header = new UVRDataHeader();
		 InputStream in=m_conn.getInputStream();
		 
		 sendCommand(UVR_READ_HEADER_DATA);
		 
		 header.setKennung(readNext(in));
		 header.setVersion(readNext(in));
		 time[0] =  readNext(in);
		 time[1] =  readNext(in);
		 time[2] =  readNext(in);
		 satzlaenge =  readNext(in);
		 startadresse[0] =  readNext(in);
		 startadresse[1] =  readNext(in);
		 startadresse[2] =  readNext(in);
		 endadresse[0] =  readNext(in);
		 endadresse[1] =  readNext(in);
		 endadresse[2] =  readNext(in);
		 pruefsum = in.read();
		
		
		if (pruefsum != m_checkSum%0x100) {
			throw new IOException("Checksum in data header invalid.");
		}
		
		if (m_byteCounter != 12) {
			throw new IOException("Too few data received: "+m_byteCounter);
		}
		
		if (satzlaenge !=  UVR1611) {
			throw new IOException("BUVRDataHeaderReader supports only UVR1611 data");
		}
		header.setNumRecords(decodeNumRecords(endadresse));
		header.setTime(decodeTime(time));
		m_header = header;
	}
	
	public UVRDataHeader getHeader() throws IOException{
		if (m_header != null) {
			return m_header;
		} else {
			doCommand();
			return m_header;
		}
	}
	
	
	private int decodeTime(int[] time) throws IOException {
		return time[0]+time[1]*0x100+time[2]*0x10000;
	}
	
	private int decodeNumRecords(int[] address) throws IOException {
		  /* UCHAR byte1, byte2, byte3; */
		  int byte1, byte2, byte3;

		  /* Byte 1 - lowest */
		  switch ( address[0])
		    {
		    case 0x0  : byte1 = 1; break;
		    case 0x40 : byte1 = 2; break;
		    case 0x80 : byte1 = 3; break;
		    case 0xc0 : byte1 = 4; break;
		    case 0xff : return 0;		// best guess by tbl ;-)
		    default: throw new IOException ("Illegal value in header end address low-byte: " + address[0]);
		    }

		  /* Byte 2 - mitte */
		  byte2 = (address[1] / 0x02) * 0x04;

		  /* Byte 3 - highest */
		  byte3 = (address[2] * 0x100)*0x02;

		  return byte1 + byte2 + byte3;

	}
	
	private  int readNext(InputStream in) throws IOException {
		int data =  in.read();
		m_checkSum+=data;
		m_byteCounter++;
		return data;
	}
	
}
