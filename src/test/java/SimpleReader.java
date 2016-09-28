

import de.ginosoft.juvr.data.UVRDataSet;
import de.ginosoft.juvr.misc.HeatingProperty;
import de.ginosoft.juvr.misc.UVRDataSetWriter;
import de.ginosoft.juvr.read.UVR1611Connection;
import de.ginosoft.juvr.read.UVRCurrentDataReader;

public class SimpleReader {
	
	public static void main(String args[]) throws Exception {
		
		HeatingProperty prop = new HeatingProperty();
		int port = Integer.parseInt(prop.getProperty("UVR1611PORT"));
		String url = prop.getProperty("UVR1611URL");
		
		UVRDataSet res;
		UVR1611Connection conn = new UVR1611Connection(url, port);
		UVRCurrentDataReader reader = new UVRCurrentDataReader(conn);
		res = reader.getCurrentData();
		UVRDataSetWriter writer = new UVRDataSetWriter(res);
		System.out.println(writer);
		conn.close();
		
	}
}
