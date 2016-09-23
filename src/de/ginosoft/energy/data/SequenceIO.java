package de.ginosoft.energy.data;

import java.io.IOException;
import java.io.InputStream;


public class SequenceIO {


	/**
	 * Write the sequence into file
	 * @param data
	 * @param fileName
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeSequence(DataSequence data, String fileName, String filePath) throws IOException {
		java.io.OutputStream o = new java.io.FileOutputStream(filePath+fileName+".csv");
		java.util.Iterator <DataPoint> it = data.getIterator();
		java.text.DecimalFormat format = new java.text.DecimalFormat();
		o.write(("TIME;"+data.getMeasure().getID()+":"+data.getMeasure().getDataUnit().getID()).getBytes());o.write(13);
		while (it.hasNext()){
			DataPoint point = it.next();
			String time = point.getTimeAsString();
			double value = point.getValue();
			
			o.write(time.getBytes());
			o.write(';');
			
			o.write(format.format(value).getBytes());
			o.write(13);
			
		}
		o.close();
	}
	
	public static void writeSequence(DataSequence data, String fileName) throws IOException {
		writeSequence(data, fileName,"");
	}
	
	/**
	 * Read sequence from file.
	 * @param filename
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static DataSequence readSequence(String filename, String filepath) throws IOException {
		
		java.io.InputStream in = new java.io.FileInputStream(filepath+filename);
		return readSequence(in);
	}
	
	public static DataSequence readSequence(InputStream in) throws IOException {
		// Build a scanner for cr+lf.
		java.util.Scanner lineScanner = new java.util.Scanner(in);
		char del = 13;
		char lf=10;
		lineScanner.useDelimiter("["+del+lf+"]");
		
		
		java.text.DateFormat df = new java.text.SimpleDateFormat("dd.MM.yy HH:mm:ss");
		java.text.NumberFormat deci = java.text.DecimalFormat.getInstance();
		
		// Decode header line
		String line = lineScanner.next();
		java.util.Scanner colScanner = new java.util.Scanner(line);
		colScanner.useDelimiter(";");
		String dateString=colScanner.next();
		if (!"TIME".equals(dateString)) {
			throw new IOException("Expected keyword 'TIME' in header");
		}
		String measure = colScanner.next();
		if (measure == null) {
			throw new IOException("Expected keyword of measure to read in header");
		}
		
		String[] unit = measure.split(":");
		if (unit.length != 2) {
			throw new IOException("Expected keyword of measure in format 'MEASURE_ID:UNIT_ID' instead of '"+measure+"'");
		}
		String measureName = unit[0];
		String unitName = unit[1];
		
		DataUnit unitO =  DataUnit.getDataUnit(unitName);
		DataSequence retSeq = new DataSequence(new MeasureDescription(measureName, measureName, unitO));
		
		// now read the lines
		while (lineScanner.hasNext()) {
			line = lineScanner.next();	
			colScanner = new java.util.Scanner(line);
			colScanner.useDelimiter(";");
			if (colScanner.hasNext()) {
				String dateText = colScanner.next();
				if (colScanner.hasNext()) {
					String data = colScanner.next();
					try {
						java.util.Date date = df.parse(dateText);
						double value = deci.parse(data).doubleValue();
						retSeq.addDataPoint(new DataPoint(date,value));
					}
					catch (java.text.ParseException ex) {
						// rows that can not be parsed will be ignored
						ex.printStackTrace();
					}
				}
			}
		}
		return retSeq;
	}
	
	
	public static DataSequence readSequence(String filename) throws IOException {
		return readSequence(filename,"");
	}
	

}
