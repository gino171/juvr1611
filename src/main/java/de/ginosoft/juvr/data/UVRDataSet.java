package de.ginosoft.juvr.data;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
*/

import de.ginosoft.energy.data.DataUnit;



/**
 * Object containing one set of data observed by UVR 1611
 * Input and output item have no meta information and are completely generic
 * @author tbl
 *
 */
// @Entity
public class UVRDataSet {
	
	public static final double UNUSED = -999999.999;
//	@Id
//	@Temporal(TemporalType.TIMESTAMP)
	protected Date date;

	// don't use array for persistance reason
	protected double input0;
	protected double input1;
	protected double input2;
	protected double input3;
	protected double input4;
	protected double input5;
	protected double input6;
	protected double input7;
	protected double input8;
	protected double input9;
	protected double input10;
	protected double input11;
	protected double input12;
	protected double input13;
	protected double input14;
	protected double input15;


	protected DataUnit[] inputUnit = new DataUnit[16];

	// don't use array for persistance reason
	protected boolean output0;
	protected boolean output1;
	protected boolean output2;
	protected boolean output3;
	protected boolean output4;
	protected boolean output5;
	protected boolean output6;
	protected boolean output7;
	protected boolean output8;
	protected boolean output9;
	protected boolean output10;
	protected boolean output11;
	protected boolean output12;
	protected boolean output13;
	protected boolean output14;
	protected boolean output15;

	protected boolean hasPower1;
	protected boolean hasPower2;
	protected double power1;
	protected double power2;
	protected double mwz1;
	protected double mwz2;
	protected short turn_A1;
	protected short turn_A2;
	protected short turn_A6;
	protected short turn_A7;

	public double getInput(int idx) {
		checkIndexRange(idx);
		try {
			Field field = this.getClass().getDeclaredField("input"+idx);
			return field.getDouble(this);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}
	}

	

	public void setInput(int idx, double value ){
		checkIndexRange(idx);
		try {
			Field field = this.getClass().getDeclaredField("input"+idx);
			field.setDouble(this, value);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}
	}

	public DataUnit getInputUnit(int idx) {
		checkIndexRange(idx);
		//return DataUnit.UNUSED;
		return inputUnit[idx];
	}

	public void setInputUnit(int idx, DataUnit value ){
		if (idx <0 || idx >=16) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}
		this.inputUnit[idx]=value;
	}

	public boolean getOutput(int idx) {
		if (idx <0 || idx >=16) {
			throw new IllegalArgumentException("Output index must be in the range from 0-15 (but is "+idx+" ).");
		}
		try {
			Field field = this.getClass().getDeclaredField("output"+idx);
			return field.getBoolean(this);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}

	}

	public void setOutput(int idx, boolean value) {
		if (idx <0 || idx >=16) {
			throw new IllegalArgumentException("Output index must be in the range from 0-15 (but is "+idx+" ).");
		}
		try {
			Field field = this.getClass().getDeclaredField("output"+idx);
			field.setBoolean(this, value);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}

	}
	
	/*
	 * returns the status of the actor with id <outputID> (0-15)
	 * as double value (0.0 = off, 1.0 = on)
	 */
	public double getOutputAsDouble(int outputID) {
		return getOutput(outputID) ? 1 : 0;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isHasPower1() {
		return hasPower1;
	}
	public void setHasPower1(boolean hasPower1) {
		this.hasPower1 = hasPower1;
	}
	public boolean isHasPower2() {
		return hasPower2;
	}
	public void setHasPower2(boolean hasPower2) {
		this.hasPower2 = hasPower2;
	}
	public double getMwz1() {
		return mwz1;
	}
	public void setMwz1(double mwz1) {
		this.mwz1 = mwz1;
	}
	public double getMwz2() {
		return mwz2;
	}
	public void setMwz2(double mwz2) {
		this.mwz2 = mwz2;
	}
	public double getPower1() {
		return power1;
	}
	public void setPower1(double power1) {
		this.power1 = power1;
	}
	public double getPower2() {
		return power2;
	}
	public void setPower2(double power2) {
		this.power2 = power2;
	}
	public short getTurn_A1() {
		return turn_A1;
	}
	public void setTurn_A1(short turn_A1) {
		this.turn_A1 = turn_A1;
	}
	public short getTurn_A2() {
		return turn_A2;
	}
	public void setTurn_A2(short turn_A2) {
		this.turn_A2 = turn_A2;
	}
	public short getTurn_A6() {
		return turn_A6;
	}
	public void setTurn_A6(short turn_A6) {
		this.turn_A6 = turn_A6;
	}
	public short getTurn_A7() {
		return turn_A7;
	}
	public void setTurn_A7(short turn_A7) {
		this.turn_A7 = turn_A7;
	} 


	public String toString() {

		String ret = "Dataset:\n";
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		ret+="Date = "+(df.format(getDate()))+"\n";

		for (int i=0; i<16; i++) {
			ret+="Input "+i+" = "+getInput(i)+" ["+getInputUnit(i).getUnit()+"]\n";
		}


		for (int i=0; i<16; i++) {
			ret+="Output "+i+"= "+getOutput(i)+"\n";

		}
		return ret;



	}

	private void checkIndexRange(int idx) {
		if (idx <0 || idx >=16) {
			throw new IllegalArgumentException("Input index must be in the range from 0-15 (but is "+idx+" ).");
		}
	}

}
