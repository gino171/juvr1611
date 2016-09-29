package de.ginosoft.juvr.misc;

import java.text.SimpleDateFormat;

import de.ginosoft.juvr.data.UVRDataSet;

public class UVRDataSetWriter {

	private UVRDataSet uvrDataSet;
	private HeatingProperty prop;

	public UVRDataSetWriter(UVRDataSet dataset, HeatingProperty prop) {
		this.uvrDataSet = dataset;
		this.prop = prop;
	}

	public String toString() {
		String ret = prop.getProperty("HEADER", "");
		String dateformat = prop.getProperty("DATEFORMAT",
				"dd.MM.yyyy HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat(dateformat);
		ret += "Date = " + (df.format(uvrDataSet.getDate())) + "\n";

		for (int i = 0; i < 16; i++) {
			String sensName = prop.getProperty("SENSOR_" + (i + 1), "Sensor_"
					+ (i + 1));
			if (!sensName.equals("HIDE")) {
				ret += sensName + " = " + uvrDataSet.getInput(i) + " ["
						+ uvrDataSet.getInputUnit(i).getUnit() + "]\n";
			}
		}

		String sOn = prop.getProperty("ACTOR_ON", "ON");
		String sOff = prop.getProperty("ACTOR_OFF", "OFF");
		for (int i = 0; i < 16; i++) {
			String actName = prop.getProperty("ACTOR_" + (i + 1), "Actor_"
					+ (i + 1));
			if (!actName.equals("HIDE")) {
				if (uvrDataSet.getOutput(i)) {
					ret += actName + " = " + sOn + "\n";
				} else {
					ret += actName + " = " + sOff + "\n";
				}
			}
		}
		String rName = prop.getProperty("ROT_A1", "Rotation_A1");
		if ((uvrDataSet.getTurn_A1() > 0) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getTurn_A1() + "\n";

		rName = prop.getProperty("ROT_A2", "Rotation_A2");
		if ((uvrDataSet.getTurn_A2() > 0) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getTurn_A2() + "\n";

		rName = prop.getProperty("ROT_A6", "Rotation_A6");
		if ((uvrDataSet.getTurn_A6() > 0) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getTurn_A6() + "\n";

		rName = prop.getProperty("ROT_A7", "Rotation_A7");
		if ((uvrDataSet.getTurn_A7() > 0) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getTurn_A7() + "\n";

		rName = prop.getProperty("POWER_1_ACT", "power_1_currently");
		if ((uvrDataSet.isHasPower1()) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getPower1() + " [kW]\n";
		rName = prop.getProperty("POWER_1_EARN", "power_1_earnings");
		if ((uvrDataSet.isHasPower1()) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getMwz1() + " [kWh]\n";
		rName = prop.getProperty("POWER_2_ACT", "power_2_currently");
		if ((uvrDataSet.isHasPower2()) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getPower2() + " [kW]\n";
		rName = prop.getProperty("POWER_2_EARN", "power_2_earnings");
		if ((uvrDataSet.isHasPower2()) && (!rName.equals("HIDE")))
			ret += rName + " = " + uvrDataSet.getMwz2() + " [kWh]\n";

		ret += prop.getProperty("FOOTER", "");
		return ret;

	}

}
