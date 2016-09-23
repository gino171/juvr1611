package de.ginosoft.juvr.data;

import de.ginosoft.energy.data.DataUnit;
import de.ginosoft.energy.data.MeasureList;


/**
 * List of provides measures by UVR1611
 * @author y1603
 *
 */
public class UVRMeasureList extends MeasureList {
	
	public UVRMeasureList() {
		super();
		buildList();
	}
	
	private void buildList() {
		addMeasure("WW_SPEICHER", "Warmwasser Speicher", DataUnit.TEMPERATUR, 0);
		addMeasure("WW_REF", "Speicher Referenz", DataUnit.TEMPERATUR, 2);
		addMeasure("KESSEL", "Kesseltemperatur", DataUnit.TEMPERATUR, 3);
		addMeasure("SOLARVL", "Solar Vorlauf",DataUnit.TEMPERATUR, 4);
		addMeasure("SOLARRL", "Solar Rücklauf", DataUnit.TEMPERATUR, 5);
		addMeasure("KOLLEKTOR", "Kollektortemperatur", DataUnit.TEMPERATUR, 7);
		addMeasure("AUSSEN", "Aussentemperatur", DataUnit.TEMPERATUR, 9);
		addMeasure("ZIRKULATION", "Zirkulationsleitung", DataUnit.TEMPERATUR, 10);
		addMeasure("HZVORLAUF", "Heizungsvorlauf", DataUnit.TEMPERATUR, 11);
		addMeasure("RAUM", "Raumtemperatur", DataUnit.TEMPERATUR, 13);
		addMeasure("PUMPE_SOLAR","Solarpumpe", DataUnit.ACTOR,0);
		addMeasure("PUMPE_HEIZUNG","Heizungspumpe", DataUnit.ACTOR,2);
		addMeasure("PUMPE_WW","WW Zirkulation", DataUnit.ACTOR,4);
		addMeasure("PUMPE_WWLADE","WW Speicher Ladepumpe", DataUnit.ACTOR,6);
		addMeasure("BRENNER_ANF","Anfoderung Brenner", DataUnit.ACTOR,11);
	}
	
	private void addMeasure(String ID, String desc, DataUnit unit, int idx) {
		this.addMeasure(new UVRMeasureDescription(ID, desc, unit, idx));
	}
	

}
