package de.ginosoft.juvr.read;

import java.io.IOException;
import java.io.InputStream;

public class UVRResetData extends AbstractReader {

	private static char UVR_RESET_DATA = 0xAF;
	
	public UVRResetData(UVRConnection conn) {
		super(conn);
	}
	
	public int doCommand() throws IOException {
		sendCommand(UVR_RESET_DATA);
		InputStream in=m_conn.getInputStream();
		return in.read();
	}
}
