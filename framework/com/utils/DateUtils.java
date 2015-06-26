package com.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {

	private static Log log = LogFactory.getLog(DateUtils.class);
	private static final String[] FORMATS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM" };

	public static String SIMPLE_DEFAULT_FORMAT = "yyyy-MM-dd";
	public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_YYMMDDHHMMSS = "yyMMddHHmmss";

	// 第一次调用get将返回null
	private static ThreadLocal threadLocal = new ThreadLocal();

	// 获取线程的变量副本，如果不覆盖initialValue，第一次get返回null，故需要初始化一个SimpleDateFormat，并set到threadLocal中
	public static DateFormat getDateFormat(String type) {
		DateFormat df = (DateFormat) threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(type);
			threadLocal.set(df);
		}
		return df;
	}

	/**
	 * 取得当月的第一天0时0分0移
	 * 
	 * @param current
	 * @return
	 */
	public static Date getStartDayOfMonth(Date current) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		Calendar startCalendar = (Calendar) calendar.clone();
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date start = DateUtils.getStartOfDay(startCalendar.getTime());
		return start;
	}

	/**
	 * 取得当月的最后一天的23时59分59移
	 * 
	 * @param current
	 * @return
	 */
	public static Date getEndDayOfMonth(Date current) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		Date end = DateUtils.getEndOfDay(calendar.getTime());
		return end;
	}

	/**
	 * 取得当天的最早一个0时0分0移
	 * 
	 * @param start
	 * @return
	 */
	public static Date getStartOfHour(Date start) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);
		int year = startCal.get(Calendar.YEAR);
		int month = startCal.get(Calendar.MONTH);
		int day = startCal.get(Calendar.DAY_OF_MONTH);
		int hour = startCal.get(Calendar.HOUR_OF_DAY);
		return new GregorianCalendar(year, month, day, hour, 0, 0).getTime();
	}

	/**
	 * 取得当天的最早一个0时0分0移
	 * 
	 * @param start
	 * @return
	 */
	public static Date getEndOfHour(Date end) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);
		int year = endCal.get(Calendar.YEAR);
		int month = endCal.get(Calendar.MONTH);
		int day = endCal.get(Calendar.DAY_OF_MONTH);
		int hour = endCal.get(Calendar.HOUR_OF_DAY);
		return new GregorianCalendar(year, month, day, hour, 59, 59).getTime();
	}

	/**
	 * 取得当天的最早一个0时0分0移
	 * 
	 * @param start
	 * @return
	 */
	public static Date getStartOfDay(Date start) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);
		int year = startCal.get(Calendar.YEAR);
		int month = startCal.get(Calendar.MONTH);
		int day = startCal.get(Calendar.DAY_OF_MONTH);
		return new GregorianCalendar(year, month, day, 0, 0, 0).getTime();
	}

	/**
	 * 取得当天的最后一个时间23时59分59移
	 * 
	 * @param end
	 * @return
	 */
	public static Date getEndOfDay(Date end) {
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);
		int year = endCal.get(Calendar.YEAR);
		int month = endCal.get(Calendar.MONTH);
		int day = endCal.get(Calendar.DAY_OF_MONTH);
		return new GregorianCalendar(year, month, day, 23, 59, 59).getTime();
	}

	public static boolean isTheSameDay(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2
						.get(Calendar.DAY_OF_MONTH));
	}

	public static String format(Date date) {
		return format(date, DEFAULT_FORMAT);
	}

	public static String formatLongTime(Long time, String format) {
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date toDate(String date) {
		String timeType;
		if (date.length() < 19) {
			timeType = SIMPLE_DEFAULT_FORMAT;
		} else {
			timeType = DEFAULT_FORMAT;
		}
		return toDate(date, timeType);
	}

	public static Date toDate(String date, String format) {
		if (date == null || date.equals("")) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date result = dateFormat.parse(date);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String secondFormat(int second) {
		String str = "";
		int hour = 0;
		int minute = 0;
		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		DecimalFormat df = new DecimalFormat("00");
		return (df.format(hour) + ":" + df.format(minute) + ":" + df
				.format(second));
	}

	/**
	 * 返回当前时间的固定日期格式
	 * 
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getNow(String format) {
		String sdate = null;
		try {
			sdate = new SimpleDateFormat(format).format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sdate;
	}

	/**
	 * 将一个日期，按规定格式返回
	 * 
	 * @param udate
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String UDateToString(Date udate, String format) {
		String sdate = null;
		try {
			sdate = new SimpleDateFormat(format).format(udate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sdate;
	}

	/**
	 * 得到当前的时间.
	 * 
	 * @return 当前的时间,类型:java.util.Date.
	 */
	public static java.util.Date now() {
		return (new java.util.Date(System.currentTimeMillis()));
	}

	// 返回上个月的YYYYMM字符形式
	public static String lastMonth() {
		GregorianCalendar gcDate = new GregorianCalendar();
		int thisYear = gcDate.get(GregorianCalendar.YEAR);// 本年
		int thisMonth = gcDate.get(GregorianCalendar.MONTH) + 1;// 本月

		if (thisMonth == 1) {
			return (thisYear - 1) + "12";
		}

		String lastMonth = String.valueOf(thisMonth - 1);
		lastMonth = lastMonth.length() > 1 ? lastMonth : ("0" + lastMonth);
		return thisYear + lastMonth;
	}

	// 返回昨天的YYYYMMDD字符形式
	public static String lastDay() {
		GregorianCalendar gcDate = new GregorianCalendar();
		gcDate.add(gcDate.DATE, -1);

		int thisYear = gcDate.get(GregorianCalendar.YEAR);
		int thisMonth = gcDate.get(GregorianCalendar.MONTH) + 1;
		int thisDay = gcDate.get(gcDate.DATE);

		String strMonth = (thisMonth > 9) ? (String.valueOf(thisMonth))
				: ("0" + thisMonth);
		String strDay = (thisDay > 9) ? (String.valueOf(thisDay))
				: ("0" + thisDay);

		return thisYear + strMonth + strDay;
	}

	/**
	 * 返回日期型式：2010-10-10 22:22:22
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(java.util.Date date) {
		if (date == null)
			return null;
		return getDateFormat(DEFAULT_FORMAT).format(date);
	}

	// 年月日类型
	public static String formatDateShort(java.util.Date date) {
		if (date == null)
			return null;
		return getDateFormat(SIMPLE_DEFAULT_FORMAT).format(date);
	}

	// protected static final SimpleDateFormat DATE_FORMAT = new
	// SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");

	// 年月日类型
	// protected static final SimpleDateFormat DATE_FORMAT_SHORT = new
	// SimpleDateFormat(
	// "yyyy-MM-dd");

	public static java.util.Date parseDate(String date)
			throws java.text.ParseException {
		if (date == null || date.equals("null") || date.trim().equals("")) {
			return null;
		} else if (date.length() > 10) {
			return getDateFormat(DEFAULT_FORMAT).parse(date);
		} else {
			return getDateFormat(SIMPLE_DEFAULT_FORMAT).parse(date);
		}
	}

	// 年月日类型
	// public static java.util.Date parseDateShort(String date) throws
	// java.text.ParseException{
	// if (date == null || date.trim().equals("")) return null;
	// return DATE_FORMAT_SHORT.parse(date);
	// }

	public static java.sql.Date toSqlDate(java.util.Date date) {
		if (date == null)
			return null;
		return ((date instanceof java.sql.Date) ? ((java.sql.Date) date)
				: new java.sql.Date(date.getTime()));
	}

	public static java.sql.Timestamp toSqlDateTime(java.util.Date date) {
		if (date == null)
			return null;
		return ((date instanceof java.sql.Timestamp) ? ((java.sql.Timestamp) date)
				: new java.sql.Timestamp(date.getTime()));
	}

	// 将字符串数组转成IN查询需要的逗号分隔字符串
	public static String turnStringArrayToString(String as[]) {
		String as1[] = as;
		if (as1 == null || as1.length == 0)
			return "";
		String s = "";
		for (int i = 0; i < as1.length; i++)
			s = s + "'" + as1[i] + "',";

		s = s.substring(0, s.length() - 1);
		return s;
	}

	// 将YYYY-MM格式的月份格式转成YYYYMM格式的字符串
	public static String formatToYyyyMm(String yyyy_mm) {
		yyyy_mm = yyyy_mm.substring(0, 7);
		yyyy_mm = yyyy_mm.replaceAll("-", "");
		return yyyy_mm;
	}

	// 将YYYYMM格式的月份格式转成YYYY-MM格式的字符串
	public static String formatToYyyy_Mm(String yyyymm) {
		String str = yyyymm.substring(0, 4) + "-" + yyyymm.substring(4, 6)
				+ "-01 00:00:00";
		return str;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

}
