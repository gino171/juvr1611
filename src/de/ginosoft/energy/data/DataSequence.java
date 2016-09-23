package de.ginosoft.energy.data;


import java.util.ArrayList;
import java.util.List;


/**
 * represents a sequence of measured data, i.e. sequence of pair (timestamp, value)
 * ordered by timestamp
 * @author Tobias
 *
 */
public class DataSequence implements Sequence{
	
	private MeasureDescription measure;
	private long firstData = -1;
	private long lastData = -1;
	protected java.util.List<DataPoint> sequence;

	public DataSequence(MeasureDescription measure) {
		if (measure == null) {
			throw new IllegalArgumentException("Measure must not be null.");
		}
		this.measure = measure;
		sequence = new java.util.ArrayList<DataPoint>() ;
	}
	
	
	protected DataSequence(MeasureDescription measure, java.util.List<DataPoint> data) {
		this.measure = measure;
		sequence = data;
		if (data.size() > 0) {
			firstData = sequence.get(0).getTime();
			lastData = sequence.get(sequence.size()-1).getTime();
		}
	}
	
	
	public MeasureDescription getMeasure() {
		return measure;
	}
	
	public void setMeasure(MeasureDescription measure) {
		this.measure = measure;
	}
	
	/**
	 * returns values as double-array
	 * @return
	 */
	public double[] getValues() {
		double[] res = new double[sequence.size()];
		java.util.Iterator<DataPoint> it = getIterator();
		int i=0;
		while (it.hasNext()) {
			res[i]=it.next().getValue();
			i++;
		}
		return res;
	}
	
	public List<Double> getValuesAsList() {
		List<Double> res = new ArrayList<Double>();
		for (DataPoint point : sequence) {
			res.add(point.getValue());
		}
		return res;
	}
	
	/**
	 * 
	 * @return Measuring times as Sting in Format "dd.MM.yyyy HH:mm:ss"
	 */
	public List<String> getTimesAsList() {
		List<String> res = new ArrayList<String>();
		for (DataPoint point : sequence) {
			res.add(point.getTimeAsString());
		}
		return res;
	}
	
	public List<DataPoint> getList() {
		return sequence;
	}
	
	public java.util.Iterator<DataPoint> getIterator() {
		return sequence.iterator();
	}
	
	public int getSize() {
		return sequence.size();
	}
	
	/**
	 * Tries to eliminate measurement errors
	 * @return Number of "smoothing" operations on the whole datasequence.
	 */
	public int smoothen() {
		int numSmoothings = 0;
		for (int i=1; i< sequence.size()-1; i++) {
			double lastVal=sequence.get(i-1).getValue();
			double thisVal=sequence.get(i).getValue();
			double nextVal=sequence.get(i+1).getValue();
			
			double dx1 = thisVal-lastVal;
			double dx2 = nextVal-thisVal;
			double dx3 = nextVal-lastVal;
			if (Math.abs(dx1)>10*Math.abs(dx3) && Math.abs(dx2)>10*Math.abs(dx3) && dx1*dx2 < 0) {
				// thisVal probably measurement error
				//System.out.println("Probably wrong measurement at " +m_data.get(i)+" changed to "+(nextVal+lastVal)/2);
				sequence.set(i, new DataPoint(sequence.get(i).getDate(),(nextVal+lastVal)/2));
				numSmoothings ++;
			}
		}
		return numSmoothings;
	}
	
	
	/**
	 * returns linear interpolated value at any point in time.
	 * @param date
	 * @return
	 */
	public double getValueAt(long date) {
		if (date < firstData || date > lastData) {
			throw new IllegalArgumentException("Date "+date+" out of range.");
		}
		int low = getIndexByDate(date);
		if (low+1 < sequence.size()) {
			//linear interpolation
			double m = (double)(sequence.get(low+1).getValue() - sequence.get(low).getValue()) / (double)(sequence.get(low+1).getTime()- sequence.get(low).getTime());
			double res =  sequence.get(low).getValue() + m*(date - sequence.get(low).getTime());
			return res;
		} else {
			return sequence.get(low).getValue();
		}
	}
	
	public double getValueAt(String date) {
		return getValueAt(DataPoint.getStringAsDate(date));
	}
	
	/**
	 * Adds a new datapoint that must be later that the oldest datapoint stored so far.
	 * Throws IllegalArgumentException if point before oldest point.
	 * @param point
	 */
	public void addDataPoint(DataPoint point) {
		if (firstData == -1 ) {
			firstData = point.getTime();
		}
		if (point.getTime() > lastData) {
			sequence.add(point);
			lastData = point.getTime();	
		} else {
			throw new IllegalArgumentException("New point is before old point " + point.toString());
		}
	}
	
	public void addSequence(DataSequence other) {
		if (!other.getMeasure().equals(getMeasure())) {
			throw new IllegalArgumentException("Measures of sequence to add ("+other.getMeasure().toString()+") not equal to measure of sequence ("+getMeasure().toString()+").");
		}
		for (DataPoint point : other.getList()) {
			this.addDataPoint(point);
		}
	}
	
	
	/**
	 * returns a sequence including start and endDate
	 * @param startDate in format "dd.MM.yy HH:mm:ss"
	 * @param endDate in format "dd.MM.yy HH:mm:ss"
	 * @return Subsequence as BDataSequence. Throws IllegalArgumentException.
	 */
	public DataSequence getSubSequence(String startDate, String endDate) {
		return getSubSequence(DataPoint.getStringAsDate(startDate), DataPoint.getStringAsDate(endDate));
	}
	
	/**
	 * Returns a sequence of all BDataPoints of this sequence that lay between startDate (including) and endDate(including) 
	 * Sequence might be empty but is never null.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public DataSequence getSubSequence(long startDate, long endDate) {
		if (sequence.isEmpty()) {
			return new DataSequence(getMeasure()); // Empty sequence
		}
		int fromIndex = getStartIndexByDate(startDate,endDate);
		int toIndex = getEndIndexByDate(startDate,endDate);
		if (fromIndex >= 0 && fromIndex <= sequence.size() && toIndex>=0 && toIndex <= sequence.size() && fromIndex<toIndex) {
			return new DataSequence(getMeasure(), sequence.subList(fromIndex, toIndex));
		} else {
			return new DataSequence(getMeasure()); // Empty sequence
		}
	}
	
	
	protected int getStartIndexByDate(long startdate, long enddate) {
		if (startdate < firstData) {
			if (enddate < firstData) {
				return -1; // (startdate - enddate) before firstdata
			} else {
				return 0; 
			}
		} else {
			return getIndexByDate(startdate);
		}
		
	}
	
	protected int getEndIndexByDate(long startdate, long enddate) {
		if (enddate > lastData) {
			if (startdate > lastData) {
				return -1; // (startdate - enddate) after lastdata
			} else {
				return sequence.size();
			}
		} else {
			int idx = getIndexByDate(enddate);
			if (idx == -1) {
				return -1;
			} else {
				return idx+1; // +1, because upper limit is exclusive
			}
		}
	}
	
	/**
	 * returns the highest index of a datapoint before <date> or -1 if no data 
	 * @param date
	 * @return
	 */
	protected int getIndexByDate(long date) {
		if (date < firstData) {
			return -1;
		}
		if (date > lastData) {
			return -1; //sequence.size() - 1;
		}
		if (date == firstData) {
			// fistData matches, return right now,
			// as firstData == lastData might the case.
			return 0;
		}
		// do a guess of index
		int currIdx = (int) (sequence.size() * (date - firstData) / (lastData - firstData));
		if (currIdx<0) currIdx=0;
		if (currIdx>=sequence.size()) currIdx=sequence.size()-1;
		
		if (sequence.get(currIdx).getTime() < date) {
			while (sequence.get(currIdx).getTime() <= date) {
				currIdx++;
			}
			return currIdx-1;
		} else {
			while ((sequence.get(currIdx).getTime() > date) && (currIdx >0 )) {
				currIdx--;
			}
			return currIdx;
		}
	}
	
	public String toString() {
		String res = getMeasure().getID()+ " ("+getMeasure().getDescription()+"):\n";
		java.util.Iterator<DataPoint> i=sequence.iterator();
		while (i.hasNext()) {
			res+=i.next().toString()+"\n";
		}
		return res;
	}
	
	
}
