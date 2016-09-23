package de.ginosoft.juvr.data;

import java.io.IOException;
import java.text.SimpleDateFormat;

import de.ginosoft.energy.data.DataUnit;
import de.ginosoft.juvr.misc.HeatingProperty;

public class UVRDataSetHTMLWriter {
	
	private UVRDataSet uvrDataSet;

	public UVRDataSetHTMLWriter(UVRDataSet dataset) {
		uvrDataSet=dataset;
	}
	
	public String toString() {
		
		HeatingProperty prop = null;
		try { 
			prop = new HeatingProperty();
			String ret="<html><head>	<title>Heating</title><link href='tbl.css' rel='stylesheet' type='text/css'></head>"
					+"<body><h2>Online heating monitoring</h2>"
					+"<h3>Description:</h3>"
					+"In 2008 a new wood-based heating from <a href='http://www.solvis.de' target='_blank'>Solvis</a> was installed at home which is controlled by a device that allows logging and analysis of sensors (temperature) and actors of the central heating system."
					+"<h3>Heating control online:</h3>"
					+"Here you see the current status of my heating system from "; 
			String dateformat = prop.getProperty("DATEFORMAT", "dd.MM.yyyy HH:mm:ss");
			SimpleDateFormat df = new SimpleDateFormat(dateformat);
			ret+=(df.format(uvrDataSet.getDate()))+"\n";
			
			ret+="</br>";
			ret+="<div ><table>";
			for (int i=0; i<16; i++) {
				String sensName= prop.getProperty("SENSOR_"+(i+1), "Sensor_"+(i+1));
				if (!sensName.equals("HIDE")) {
					ret+="<tr><td>"+sensName+"</td><td>"+ uvrDataSet.getInput(i)+"</td><td>C</td></tr>\n";
				}
			}
			ret+="</table></div>";
			
			String sOn = prop.getProperty("ACTOR_ON","ON");
			String sOff = prop.getProperty("ACTOR_OFF","OFF");
			ret+="<div ><table>";
			for (int i=0; i<16; i++) {
				String actName= prop.getProperty("ACTOR_"+(i+1), "Actor_"+(i+1));
				if (!actName.equals("HIDE")) {
					if (uvrDataSet.getOutput(i)) {
						ret+="<tr><td>"+actName+"</td><td>"+sOn+"</td></tr>\n";
					} else {
						ret+="<tr><td>"+actName+"</td><td>"+sOff+"</td></tr>\n";
					}
				}
			}
			ret+="</table></div>";
			
			String rName = prop.getProperty("ROT_A1", "Rotation_A1");
			if ((uvrDataSet.getTurn_A1()>0) && (!rName.equals("HIDE")))  ret+=rName+" = "+uvrDataSet.getTurn_A1()+"\n";
			
			rName = prop.getProperty("ROT_A2", "Rotation_A2");
			if ((uvrDataSet.getTurn_A2()>0) && (!rName.equals("HIDE")))  ret+=rName+" = "+uvrDataSet.getTurn_A2()+"\n";
			
			rName = prop.getProperty("ROT_A6", "Rotation_A6");
			if ((uvrDataSet.getTurn_A6()>0) && (!rName.equals("HIDE")))  ret+=rName+" = "+uvrDataSet.getTurn_A6()+"\n";
			
			rName = prop.getProperty("ROT_A7", "Rotation_A7");
			if ((uvrDataSet.getTurn_A7()>0) && (!rName.equals("HIDE")))  ret+=rName+" = "+uvrDataSet.getTurn_A7()+"\n";
			
			rName = prop.getProperty("POWER_1_ACT", "power_1_currently");
			if ((uvrDataSet.isHasPower1()) && (!rName.equals("HIDE"))) ret+=rName +" = "+uvrDataSet.getPower1() +" [kW]\n";
			rName = prop.getProperty("POWER_1_EARN", "power_1_earnings");
			if ((uvrDataSet.isHasPower1()) && (!rName.equals("HIDE"))) ret+=rName +" = "+uvrDataSet.getMwz1() + " [kWh]\n";
			rName = prop.getProperty("POWER_2_ACT", "power_2_currently");
			if ((uvrDataSet.isHasPower2()) && (!rName.equals("HIDE"))) ret+=rName +" = "+uvrDataSet.getPower2() +" [kW]\n";
			rName = prop.getProperty("POWER_2_EARN", "power_2_earnings");
			if ((uvrDataSet.isHasPower2()) && (!rName.equals("HIDE"))) ret+=rName +" = "+uvrDataSet.getMwz2() + " [kWh]\n";
		
			ret+="</body></html>";
			//ret+=prop.getProperty("FOOTER","");
			return ret;
		}
		catch (IOException e) {
			return "Property file not found.";
		}

	}

}
