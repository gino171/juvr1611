package de.ginosoft.juvr.misc;

import java.text.ParseException;

public class DateUtil {
	
	private static final java.text.SimpleDateFormat m_df = new java.text.SimpleDateFormat("dd.MM.yy HH:mm:ss");
	private static final java.text.SimpleDateFormat m_dfdb = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static long convertDate(String date) throws ParseException {
		java.util.Date dateobj = m_df.parse(date);
		return dateobj.getTime();
	}
	
	public static String convertDate(java.util.Date date) {
		return m_df.format(date);
	}
	
	public static String convertToDBDate(String date) throws ParseException {
		java.util.Date dateobj = m_df.parse(date);
		return m_dfdb.format(dateobj);
	}
}
