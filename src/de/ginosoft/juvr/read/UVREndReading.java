package de.ginosoft.juvr.read;

import java.io.IOException;
import java.io.InputStream;

public class UVREndReading extends AbstractReader {

	private static char UVR_READING_END = 0xAD;
	
	public UVREndReading(UVRConnection conn) {
		super(conn);
	}
	
	public int doCommand() throws IOException {
		sendCommand(UVR_READING_END);
		InputStream in=m_conn.getInputStream();
		return in.read();
	}
	
}
