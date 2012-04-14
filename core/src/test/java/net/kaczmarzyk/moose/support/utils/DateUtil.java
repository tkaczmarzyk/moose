package net.kaczmarzyk.moose.support.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public final class DateUtil {

	private DateUtil() {
	}
	
	public static Date date(int year, int month, int day) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		resetTime(cal);
		return cal.getTime();
	}
	
	public static void resetTime(Calendar cal) {
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
	}
}
