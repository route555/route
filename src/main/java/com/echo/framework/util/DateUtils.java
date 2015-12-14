package com.echo.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

public class DateUtils {
	public static Locale DEFAULT_LOCALE = Locale.KOREA;

	public static DateFormat DATE_FORMAT_YYYY = new SimpleDateFormat("yyyy",
			DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMM = new SimpleDateFormat(
			"yyyyMM", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMM_DASH = new SimpleDateFormat(
			"yyyy-MM", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat(
			"yyyyMMdd", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDD_DOT = new SimpleDateFormat(
			"yyyy.MM.dd", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDD_DASH = new SimpleDateFormat(
			"yyyy-MM-dd", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYMMDDHHMI = new SimpleDateFormat(
			"yyMMddHHmm", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDDHHMI = new SimpleDateFormat(
			"yyyy/MM/dd/HH/mm", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDD_HHMISS_DASH = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_ISO8601FMT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_YYYYMMDDHHMISS = new SimpleDateFormat(
			"yyyyMMddHHmmss", DEFAULT_LOCALE);
	public static DateFormat DATE_FORMAT_HHMI = new SimpleDateFormat("HHmm",
			DEFAULT_LOCALE);

	public static int DAY_SECONDS = 60 * 60 * 24;

	public static Locale getLocale() {
		return DEFAULT_LOCALE;
	}

	public static Calendar getCalendar(int gapSec) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, gapSec);

		return cal;
	}

	public static Calendar getGapDate(Date date, int yearGap, int monthGap,
			int dateGap, int hourGap, int minuteGap, int secondGap,
			int millisecondGap) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}

		cal.add(Calendar.YEAR, yearGap);
		cal.add(Calendar.MONTH, monthGap);
		cal.add(Calendar.DATE, dateGap);
		cal.add(Calendar.HOUR, hourGap);
		cal.add(Calendar.MINUTE, minuteGap);
		cal.add(Calendar.SECOND, secondGap);
		cal.set(Calendar.MILLISECOND, millisecondGap);

		return cal;
	}

	public static Calendar getGapDate(Date date, int yearGap, int monthGap,
			int dateGap) {
		return getGapDate(date, yearGap, monthGap, dateGap, 0, 0, 0, 0);
	}

	public static Calendar getDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);

		return cal;
	}

	public static int getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();

		if (date != null) {
			cal.setTime(date);
		}

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static boolean isFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);

		return cal.getActualMinimum(Calendar.DAY_OF_MONTH) == cal
				.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal
				.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 년도 , 월 , 해달월에 마지막날짜
	 * 
	 * @param month
	 * @return Map
	 */
	public static Map<String, Integer> getDate(int month) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, month);
		map.put("year", cal.get(Calendar.YEAR));
		map.put("month", cal.get(Calendar.MONTH) + 1);
		map.put("actualMaximum", cal.getActualMaximum(Calendar.DATE));
		return map;
	}

	public static boolean isOrderDate(Date beforeDate, Date afterDate) {
		DateTime before = new DateTime(beforeDate);
		DateTime after = new DateTime(afterDate);

		return before.isEqual(after) || before.isBefore(after);
	}

	public static int getDayOfMonth(Date date) {
		Date now = date;
		if (now == null)
			now = new Date();

		return new DateTime(now).getDayOfMonth();
	}

	public static int getYear(Date date) {
		if (date == null)
			return 0;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		if (date == null)
			return 0;

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		if (date == null)
			return 0;

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static Date setMonth(Date date, int month) {
		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.MONDAY, month);
		return cal.getTime();
	}

	public static Date setDay(Date date, int day) {
		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal.getTime();
	}

	public static int getOverlapDays(Date fStart, Date fEnd, Date lStart,
			Date lEnd) {
		if (lStart.compareTo(fEnd) > 0)
			return 0;

		if (fStart.compareTo(lEnd) > 0)
			return 0;

		Date first = null;
		Date last = null;

		first = fStart;
		if (fStart.before(lStart))
			first = lStart;

		last = fEnd;
		if (fEnd.after(lEnd))
			last = lEnd;

		return getDiffDays(first, last);
	}

	public static int getDiffDays(Date first, Date last) {
		return Days.daysBetween(new DateTime(first), new DateTime(last))
				.getDays();
	}

	public static int getDiffMonth(Date first, Date last) {
		return Months.monthsBetween(new DateTime(first), new DateTime(last))
				.getMonths();
	}
}