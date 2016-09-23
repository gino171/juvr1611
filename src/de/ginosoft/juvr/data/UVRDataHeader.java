package de.ginosoft.juvr.data;

public class UVRDataHeader {
	private int m_numRecords=0;
	private int m_time = 0;
	private int m_kennung=0;
	private int m_version=0;
	
	public int getM_kennung() {
		return m_kennung;
	}
	
	public void setKennung(int m_kennung) {
		this.m_kennung = m_kennung;
	}
	public int getNumRecords() {
		return m_numRecords;
	}
	public void setNumRecords(int records) {
		m_numRecords = records;
	}
	public int getTtime() {
		return m_time;
	}
	public void setTime(int m_time) {
		this.m_time = m_time;
	}
	public int getVersion() {
		return m_version;
	}
	public void setVersion(int m_version) {
		this.m_version = m_version;
	}
	public String toString() {
		String res = "BUVRHeaderData:\n";
		res += "Anzahl Datensätze = "+getNumRecords()+"\n";
		res += "Zeit = "+m_time+"\n";
		res += "Kennung = "+m_kennung+"\n";
		res += "Version = "+m_version+"\n";
		
		return res;
	}
}
