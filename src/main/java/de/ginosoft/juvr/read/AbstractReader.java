package de.ginosoft.juvr.read;

import java.io.IOException;

/**
 * Abstract reader for the commands that can be send to the UVR unit
 * @author gino171
 *
 */
public class AbstractReader {

	protected UVRConnection m_conn;
	
	protected AbstractReader(UVRConnection conn) {
		m_conn=conn;
	}
	
	protected void sendCommand(char command) throws IOException {
		m_conn.sendCommand(command);
	}
	
}
