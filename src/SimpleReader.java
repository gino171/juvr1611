

import de.ginosoft.juvr.data.UVRDataSet;
import de.ginosoft.juvr.data.UVRDataSetWriter;
import de.ginosoft.juvr.read.UVR1611Connection;
import de.ginosoft.juvr.read.UVRCurrentDataReader;

public class SimpleReader {
	
	public static void main(String args[]) throws Exception {
		
		UVRDataSet res;
		UVR1611Connection conn = new UVR1611Connection();
		UVRCurrentDataReader reader = new UVRCurrentDataReader(conn);
		res = reader.getCurrentData();
		UVRDataSetWriter writer = new UVRDataSetWriter(res);
		System.out.println(writer);
		conn.close();
		
	}

}
