package de.ginosoft.juvr.read;

import java.io.IOException;

/**
 * Connection with UVR host that assures that the host is of
 * type UVR 1611
 * @author tobias
 *
 */
public class UVR1611Connection extends UVRConnection{
	
	public UVR1611Connection(String url, int port) throws IOException{
		super(url, port);
		UVRVersionReader vr = new UVRVersionReader(this);
		vr.doCommand();
		if (vr.getVersion()!=UVRVersionReader.UVR_1611) {
			//throw new IOException("UVR host not of type UVR 1611");
			System.err.print("UVR host not of type UVR 1611. Expected "+((int)UVRVersionReader.UVR_1611)+" but received "+((int)vr.getVersion()));
		}
	}
}
