package de.ginosoft.juvr.read;

import java.io.IOException;
import java.io.InputStream;

/**
 * reads the version of the UVR device 
 * @author gino171
 *
 */
public class UVRVersionReader extends AbstractReader{
	
	private static char UVR_VERSION = 0x81;
	
	public static final byte UVR_1611 = (byte)0xA8;
	
	private byte m_Version;
	
	public UVRVersionReader(UVRConnection conn) {
		super(conn);
	}
	
	public void doCommand() throws IOException {
		sendCommand(UVR_VERSION);
		InputStream in=m_conn.getInputStream();
		m_Version=(byte)in.read();
	}
	
	public byte getVersion() {
		return m_Version;
	}

}
