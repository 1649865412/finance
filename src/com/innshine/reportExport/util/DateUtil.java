package com.innshine.reportExport.util;

//import static com.google.common.collect.ImmutableMap.of;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.innshine.reportExport.entity.ConditionEntity;

public class DateUtil {

	private static final Date Date = null;

	// str转为Date
	public static Date strtodate(String datedate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf.parse(datedate);
		return date2;
	}

	// Date转为str
	public static String transDateToDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = new Date(time);
		return format.format(date);
	}

	public static String transLongToDateString(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(time);
		return format.format(date);
	}
   
	public static String getLastMonth(String month,String year) throws ParseException{
		String time=year+"-"+month+"-01";
		String lastMonthTime=lastMonth(time);
		return lastMonthTime.substring(5,7);
	}
	/**
	 * 由相差天数，求另一个时间
	 * 
	 * @throws ParseException
	 */
	public static String getNewDay(int late, String begin)
			throws ParseException {
		String end = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOne = dateFormat.parse(begin);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOne);
		calendar.add(Calendar.DAY_OF_MONTH, late);
		end = dateFormat.format(calendar.getTime());
		return end;
	}

	// 当前月初
	public static String nowmonthfirst() throws ParseException {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		String monthstr = "";
		if (month < 10) {
			monthstr = "0" + month;
		} else {
			monthstr = month + "";
		}
		String day = "01";
		return (year + "-" + monthstr + "-" + day);
	}

	// 当前下月时间
	public static String lastmonthfirst() throws ParseException {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 1);
		Date dateNow = now.getTime();
		String time = transDateToDateString(dateNow);
		return time;
	}

	/**
	 * 求两个时间相差天数,小的，大的
	 * 
	 * @throws ParseException
	 */
	public static long getDateLate(String a, String b) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Long c = sf.parse(b).getTime() - sf.parse(a).getTime();
		long d = c / 1000 / 60 / 60 / 24;// 天
		return d + 1;
	}

	/**
	 * 取得服务器的当前时间，format格式的字符串
	 * 
	 * @param format
	 *            参数“yyyy-MM-dd HH:mm:ss”
	 * @return 当前时间返回的格式 返回：2005-12-11 10:35:40
	 */
	public static String getToday(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date currentDateTime = new Date();
		return sdf.format(currentDateTime);
	}

	/**
	 * 取得服务器的当前时间
	 * 
	 * 
	 * @return 当前时间返回的格式 返回：2007年8月23日
	 */
	public static String getCNToday() {
		Date currentDateTime = new Date();
		return (1900 + currentDateTime.getYear()) + "年"
				+ (1 + currentDateTime.getMonth()) + "月"
				+ currentDateTime.getDate() + "日";
	}

	// 求下个月时间
	public static String nextMonth(String nowTime) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(strtodate(nowTime));
		calender.add(Calendar.MONTH, 1);
		return transDateToDateString(calender.getTime());

	}
	
	//同比n年的时间范围
	public static String comparYearTime(String nowTime,int year) throws ParseException{
		Calendar now = Calendar.getInstance();
		now.setTime(strtodate(nowTime));
		now.add(Calendar.YEAR, year);
		String time = transDateToDateString(now.getTime());
		return time;
	}

	// 求上个月时间
	public static String lastMonth(String nowTime) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(strtodate(nowTime));
		calender.add(Calendar.MONTH, -1);
		return transDateToDateString(calender.getTime());
	}

	// 如果是日月类型，则以结束时间为准不超过一年的范围
	public static Map<String, String> shopTimeAnalyseCheck(
			Map<String, String> param, String timeType) {
		String beginTime = param.get("beginTime");
		String endTime = param.get("endTime");
		String begYear = beginTime.substring(0, 4);// 年
		String endYear = endTime.substring(0, 4);
		if (!timeType.equals("M")) {
			if (!begYear.equals(endYear)) {
				beginTime = endYear + "-01-01";
			}
		}
		param.put("beginTime", beginTime);
		return param;
	}

	/**
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static boolean RMDayCheck(String begin, String end)
			throws ParseException {
		boolean flag = false;
		
		if (getDateLate(begin, end) > 45) {
			flag = true;
		}
		return flag;
	}

	// 求时间分析的图例(R日M月)
	public static String[] getTypeValueRM(String firstTime, String endTime,
			int circle) throws ParseException {
		String str = "";
		if (DateUtil.strtodate(firstTime).after(DateUtil.strtodate(endTime))) {
			str = firstTime;
			firstTime = endTime;
			endTime = str;
		}
		int begb = Integer.parseInt(firstTime.substring(0, 4));
		int endb = Integer.parseInt(endTime.substring(0, 4));
		String end = firstTime.substring(4, firstTime.length());
		String ende = endTime.substring(4, endTime.length());
		List list = new ArrayList();
		list.add(firstTime + "到" + endTime);
		for (int i = 1; i <= circle; i++) {
			list.add((begb - i) + end + "到" + (endb - i) + ende);
		}

		return (String[]) list.toArray(new String[list.size()]);

	}

	// 求时间分析的图例(Z季)
	public static String[] getTypeValueZ(String firstTime, String endTime,
			int year, int circle) throws ParseException {
		int firstJidu = Integer.parseInt(firstTime);
		int lastJidu = Integer.parseInt(endTime);
		int mid = 0;
		if (firstJidu - lastJidu > 0) {
			mid = firstJidu;
			firstJidu = lastJidu;
			lastJidu = mid;
		}
		List list = new ArrayList();
		list.add(year + "年" + firstJidu + "至" + lastJidu + "季");
		for (int i = 1; i <= circle; i++) {
			list.add((year - i) + "年" + firstJidu + "至" + lastJidu + "季");
		}
		return (String[]) list.toArray(new String[list.size()]);

	}

	// 求时间分析的图例(Y年)(从大到小)
	public static String[] getTypeValueY(int year, int circle)
			throws ParseException {
		List List = new ArrayList();
		for (int i = 0; i <= circle; i++) {
			List.add(year - i + "年");
		}
		return (String[]) List.toArray(new String[List.size()]);

	}



	// 求时间分析x轴(Y年)
	public static String[] xValueY(int first, int last) throws ParseException {
		int mid = 0;
		if (first - last > 0) {
			mid = first;
			first = last;
			last = mid;
		}
		List<String> List = new ArrayList();
		for (int i = first; i <= last; i++) {
			List.add(i + "季");
		}
		return (String[]) List.toArray(new String[List.size()]);
	}

	// 各图例时间集(R日M月)
	public static List<List> getValueTimeList(String firstTime, String endTime,
			int circle) {
		List<List> listTime = new ArrayList();
		int begb = Integer.parseInt(firstTime.substring(0, 4));
		int endb = Integer.parseInt(endTime.substring(0, 4));
		String end = firstTime.substring(4, firstTime.length());
		String ende = endTime.substring(4, endTime.length());
		listTime.add(DateUtil.display(firstTime, endTime));
		for (int i = 1; i <= circle; i++) {
			listTime.add(DateUtil.display((begb + i) + end, (endb + i) + ende));
		}
		return listTime;
	}

	// 求两个日期的范围每一天
	public static List<String> display(String dateFirst, String dateSecond) {
		List list = new ArrayList();
		int i = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date dateOne = dateFormat.parse(dateFirst);
			Date dateTwo = dateFormat.parse(dateSecond);

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(dateOne);

			while (calendar.getTime().before(dateTwo)) {
				list.add(dateFormat.format(calendar.getTime()));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				i += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(dateSecond);
		return list;
	}

	public static String[] getPowerArray() {
		String[] deparmentidList = new String[13];
		for (int i = 0; i <= 12; i++) {
			deparmentidList[i] = i + "";
		}
		return deparmentidList;

	}

	// 求时间分析x轴(M月) 和（销售分析导出表纵列表格日期 ）01-01
	public static String[] xValueRM(String firstTime, String endTime)
			throws ParseException {
		String str = "";
		if (DateUtil.strtodate(firstTime).after(DateUtil.strtodate(endTime))) {
			str = firstTime;
			firstTime = endTime;
			endTime = str;
		}
		List list = new ArrayList();
		List result = DateUtil.display(firstTime, endTime);
		for (int i = 0; i < result.size(); i++) {
			String strb = result.get(i).toString();
			list.add(strb.substring(5, strb.length()));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	// （当月 -上月 ）/上月*100 set 当月
	public static Double getcontrastlast(Double a, Double b) {
		Double c = a - b;
		Double d = c / b;
		System.out.println("===" + d * 100);
		return d * 100;
	}

	// 求时间分析x轴(R日)14-01-01(避免jreefarchart横轴不可重复的bug)
	public static String[] xValueR(String firstTime, String endTime)
			throws ParseException {
		String str = "";
		if (DateUtil.strtodate(firstTime).after(DateUtil.strtodate(endTime))) {
			str = firstTime;
			firstTime = endTime;
			endTime = str;
		}
		List list = new ArrayList();
		List result = DateUtil.display(firstTime, endTime);
		for (int i = 0; i < result.size(); i++) {
			String strb = result.get(i).toString();
			list.add(strb.substring(2, strb.length()));
			// list.add(strb);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 20060506 -> 2006-05-06
	 */
	public static String str8to10(String date) {
		String datetmp = date.substring(0, 4) + "-" + date.substring(4, 6)
				+ "-" + date.substring(6, 8);
		return datetmp;
	}

	public static String str6to8(String date) {
		if (null != date) {
			return date.substring(0, 2) + ":" + date.substring(2, 4) + ":"
					+ date.substring(4, 6);
		}
		return "";
	}
	
	

	// 生成年的月份，参数是，哪年，到哪个月
	public static List alowmonth(String beginMonth, String endMonth, int year) {
		int begin = 0, end = 0;
		if (beginMonth.indexOf("0") != -1 && beginMonth.indexOf("0") == 0) {
			begin = Integer.parseInt(beginMonth.substring(1));
		} else {
			begin = Integer.parseInt(beginMonth);
		}
		if (endMonth.indexOf("0") != -1 && endMonth.indexOf("0") == 0) {
			end = Integer.parseInt(endMonth.substring(1));
		} else {
			end = Integer.parseInt(endMonth);
		}

		List<String> timelist = new ArrayList<String>();
		;
		Calendar now = Calendar.getInstance();
		String month = "";
		String day = "01";

		for (int i = 0; i < end + 1; i++) {
			if (i < 10) {
				month = "0" + i + "";
			} else
				month = i + "";
			if (i >= begin) {
				timelist.add(month);
			}
		}
		return timelist;
	}

	/**
	 * 格式化字符串类型日期, 如:
	 * dateStrFormat("20070309044018","yyyyMMddHHmmss","yyyy-MM-dd hh:mm:ss")
	 * 返回2007-03-09 04:40:18
	 * 
	 * @param dateStr
	 * @param orgFormat
	 * @param newFormat
	 * @return str
	 */
	public static String dateStrFormat(String dateStr, String orgFormat,
			String newFormat) {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(orgFormat);
			date = format.parse(dateStr);
		} catch (ParseException pe) {

			return dateStr;
		}
		return DateUtil.getDateTime(newFormat, date);
	}

	/**
	 * 日期字符串格式的转化(yyyy-MM-dd 或 yyyy-MM-dd hh:mm:ss)
	 * 
	 * 
	 * @return 当前时间返回的格式 返回：××××年××月××日
	 */
	public static String getCNDate(String dateStr) {
		if (dateStr.length() >= 10)
			return dateStr.substring(0, 4) + "年"
					+ Integer.parseInt(dateStr.substring(5, 7)) + "月"
					+ Integer.parseInt(dateStr.substring(8, 10)) + "日";
		else
			return "";
	}

	/**
	 * 以指定的日期格式String将Date类型转换为String This method generates a string
	 * representation of a date's date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 返回当前时间长整型
	 * 
	 * @return
	 */
	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回当前时间字符型
	 * 
	 * @return
	 */
	public static String getLongDate() {
		long d = System.currentTimeMillis();
		return String.valueOf(d);
	}

	/**
	 * 格式化日期,格式化后可直接insert into [DB]
	 * 
	 * @return
	 * 
	 */
	public static String dateToStr(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	// 判断时间是不是今月的
	// public static boolean check(String day){
	// return DateUtil.getXcDay(DateUtil.nowmonthfirst(),day);
	// }
	public static String dateToStrYyyyMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd", Locale
					.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("MMdd", Locale
					.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrHHmmss(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("HHmmss", Locale
					.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStr(Date date, String format) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale
					.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	/**
	 * 反向格式化日期
	 * 
	 * @return
	 * 
	 */
	public static Date strToDate(String str) {
		if (str == null)
			return null;
		// DateFormat defaultDate = DateFormat.getDateInstance();
		// 细化日期的格式
		SimpleDateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = null;
		try {
			date = defaultDate.parse(str);
		} catch (ParseException pe) {
		}
		return date;
	}

	public static Date strToDate2(String str) {
		if (str == null)
			return null;
		// DateFormat defaultDate = DateFormat.getDateInstance();
		// 细化日期的格式
		SimpleDateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			date = defaultDate.parse(str);
		} catch (ParseException pe) {
		}
		return date;
	}

	/**
	 * 反向格式化日期
	 * 
	 * @param str
	 *            要格式化字符串
	 * @param formatStr
	 *            字符串的日期格式
	 * @return
	 */
	public static Date strToDate(String str, String formatStr) {
		try {
			if (str == null)
				return null;
			if (formatStr == null) {
				formatStr = "yyyy-MM-dd hh:mm";
			}

			SimpleDateFormat defaultDate = new SimpleDateFormat(formatStr);

			Date date = null;
			try {
				date = defaultDate.parse(str);
			} catch (ParseException pe) {
			}

			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date calDate(Date date, int yearNum, int monthNum, int dateNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 *            起始日期
	 * @param yearNum
	 *            年增减数
	 * @param monthNum
	 *            月增减数
	 * @param dateNum
	 *            日增减数
	 * @param hourNum
	 *            小时增减数
	 * @param minuteNum
	 *            分钟增减数
	 * @param secondNum
	 *            秒增减数
	 * @return
	 */
	public static Date calDate(Date date, int yearNum, int monthNum,
			int dateNum, int hourNum, int minuteNum, int secondNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			cal.add(Calendar.HOUR_OF_DAY, hourNum);
			cal.add(Calendar.MINUTE, minuteNum);
			cal.add(Calendar.SECOND, secondNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回当前时间，格式'yyyy-mm-dd HH:mm:ss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getLocalDate() {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	public static String getLocalDate(String f) {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(f);
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'yyyyMMddHHmmss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getSimpleDate() {
		//
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'f' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getFormatDate(String f) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		Date dt = new Date();
		return df.format(dt);
	}

	public static String getFormatDate(String f, Date dt) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		return df.format(dt);

	}

	/**
	 * 得到当天日期和之前日期列表
	 * 
	 * @param orlTime
	 * @return
	 */
	public static List<String> getSixList() {
		Date date = new Date();
		Date toDaySix = strToDate(dateToStr(date, "yyyy-MM-dd") + " 06:00");
		Date daySix = null;
		if (date.getTime() - toDaySix.getTime() > 0) {
			// //大于6点
			daySix = date;
		} else {
			daySix = calDate(date, 0, 0, -1);
		}
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			l.add(dateToStr(calDate(daySix, 0, 0, -i), "yyyy-MM-dd"));
		}
		return l;
	}

	/**
	 * 格式化日期为“2004年9月13日”
	 * 
	 * @param orlTime
	 *            2012-04-08
	 * @return
	 */
	public static String parseCnDate(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}

		if (orlTime.length() < 10) {
			return "";
		}
		String sYear = orlTime.substring(0, 4);
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sYear + "年" + sMonth + "月" + sDay + "日";
	}

	/**
	 * 格式化日期为“9月13日”
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String parseCnDateNoYear(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}

		if (orlTime.length() < 10) {
			return "";
		}
		String sYear = orlTime.substring(0, 4);
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sMonth + "月" + sDay + "日";
	}

	/**
	 * 取整函数
	 * 
	 * @param mord
	 * @return
	 */
	public static String delFrontZero(String mord) {
		int im = -1;
		try {
			im = Integer.parseInt(mord);
			return String.valueOf(im);
		} catch (Exception e) {
			return mord;
		}
	}

	public static String getWeekStr(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return getWeekStr(c);
	}

	public static String getWeekStr(Calendar c) {
		int a = c.get(Calendar.DAY_OF_WEEK);
		switch (a) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return null;
		}
	}

	public static Integer getCNDayOfWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int a = c.get(Calendar.DAY_OF_WEEK);
		switch (a) {
		case 1:
			return 7;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		default:
			return 1;
		}
	}

	public static Integer getWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public static Integer getYear(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR);
	}

	public static Integer getMonth(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.MONTH) + 1;
	}

	public static Integer getQuarter(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int month = c.get(Calendar.MONTH);
		if (month < 3) {
			return 1;
		} else if (month >= 3 && month < 6) {
			return 2;
		} else if (month >= 6 && month < 9) {
			return 3;
		} else if (month >= 9 && month < 12) {
			return 4;
		} else {
			return null;
		}
	}

	public static Date getdecDateOfMinute(Date time, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, -minute);
		return c.getTime();
	}

	public static Date getdecDateOfDate(Date time, int date) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DATE, -date);
		return c.getTime();
	}

	public static String getStringMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.SECOND, 59);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	public static Date getTodayDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getYesterDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 计算两日期相差天数
	 * 
	 * @param strDateStart
	 *            "2012-08-4"
	 * @param strDateEnd
	 *            "2012-08-14"
	 * @return 10 A>Bfalse A<=Btrue 注：A>B是指在B时间之后
	 */
	public static boolean getXcDay(String strDateStart, String strDateEnd) {
		String strSeparator = "-"; // 日期分隔符
		String[] strDateArrayStart;
		String[] strDateArrayEnd;
		long intDay;
		strDateArrayStart = strDateStart.split(strSeparator);
		strDateArrayEnd = strDateEnd.split(strSeparator);
		Date strDateS = new Date(strDateArrayStart[0] + "/"
				+ strDateArrayStart[1] + "/" + strDateArrayStart[2]);
		Date strDateE = new Date(strDateArrayEnd[0] + "/" + strDateArrayEnd[1]
				+ "/" + strDateArrayEnd[2]);
		intDay = (strDateE.getTime() - strDateS.getTime()) / (1000 * 3600 * 24);
		if (intDay >= 0) {

			return true;
		}
		return false;
	}

	// 生成年的月份，参数是，哪年，到哪个月
	public static List alowmonth(int begin, int end) {
		List<String> timelist = new ArrayList<String>();
		;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String month = "";
		String day = "01";

		for (int i = 0; i < end + 1; i++) {
			if (i < 10) {
				month = "0" + i + "";
			} else
				month = i + "";
			if (i >= begin) {
				timelist.add(year + "-" + month + "-" + day + " 00:00:00");
			}
		}
		return timelist;
	}

	// 生成年的月份，参数是，哪年，到哪个月
	public static List alowmonth(int year) {
		List<String> timelist = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		// int year = now.get(Calendar.YEAR);
		String month = "";
		String day = "01";

		for (int i = 0; i < 12 + 1; i++) {
			if (i < 10) {
				month = "0" + i + "";
			} else
				month = i + "";
			if (i >= 1) {
				timelist.add(year + "-" + month + "-" + day + " 00:00:00");
			}
		}
		return timelist;
	}

	// 求输入时间查询范围map,开始时间，结束时间(日查询)
	public static Map<String, String> getfulletimeDay(String beginTime,
			String endTime, int circle) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		String str = "";
		if (strtodate(beginTime).after(strtodate(endTime))) {
			str = beginTime;
			beginTime = endTime;
			endTime = str;
		}
		map.put("beginTime", getNewDay(-(365 * circle), beginTime));
		map.put("endTime", getNewDay(-(365 * circle), endTime));
		return map;
	}

	// 求输入时间查询范围map,开始时间，结束时间(年查询)
	public static Map<String, String> getfulletimeYear(int year
	// ,int count
	) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("beginTime", year + "-01-01");
		map.put("endTime", (year + 1) + "-01-01");
		// map.put("endTime",(year+count)+"-01-01");
		return map;
	}

	// 求输入两个月份数，年数，返回两个月当前的时间查询范围map,开始时间，结束时间
	public static Map<String, String> getfulletimeMonth(String firstmonth2,
			String lastmonth2, int year) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		String day = "01";
		String monthstr = "";
		String monthstr2 = "";
		int year2 = 0;
		int firstmonth = Integer.parseInt(firstmonth2);
		int lastmonth = Integer.parseInt(lastmonth2);
		int mid = 0;
		if (firstmonth - lastmonth > 0) {
			mid = firstmonth;
			firstmonth = lastmonth;
			lastmonth = mid;
		}
		if (firstmonth < 10) {
			monthstr = "0" + firstmonth;
		} else {
			monthstr = "" + firstmonth;
		}
		if (lastmonth == 12) {
			year2 = year + 1;
			monthstr2 = "01";
		} else {
			year2 = year;
			lastmonth = lastmonth + 1;
			if (lastmonth < 10) {
				monthstr2 = "0" + lastmonth;
			} else {
				monthstr2 = "" + lastmonth;
			}
		}
		map.put("beginTime", year + "-" + monthstr + "-" + day);
		map.put("endTime", year2 + "-" + monthstr2 + "-" + day);
		return map;

	}

	// 求输入两个季度数，年数，返回两个季当前的时间查询范围map,开始时间，结束时间
	public static Map<String, String> getfulletimeJidu(String firstJidu2,
			String lastJidu2, int year) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		String day = "01";
		String monthstr = "";
		String monthstr2 = "";
		int firstJidu = Integer.parseInt(firstJidu2);
		int lastJidu = Integer.parseInt(lastJidu2);
		int mid = 0;
		if (firstJidu - lastJidu > 0) {
			mid = firstJidu;
			firstJidu = lastJidu;
			lastJidu = mid;
		}
		int year2 = 0;
		int firsttime = (firstJidu - 1) * 3 + 1;
		int secondetime = lastJidu * 3 + 1;
		if (firsttime < 10) {
			monthstr = "0" + firsttime;
		} else {
			monthstr = "" + firsttime;
		}

		if (secondetime >= 12) {
			year2 = year + 1;
			monthstr2 = "01";
		} else {
			year2 = year;
			if (secondetime < 10) {
				monthstr2 = "0" + secondetime;
			} else {
				monthstr2 = "" + secondetime;
			}
		}
		map.put("beginTime", year + "-" + monthstr + "-" + day);
		map.put("endTime", year2 + "-" + monthstr2 + "-" + day);
		return map;
	}

	// 求输入两个月份数，年数，返回两个月当前的时间查询范围map,开始时间，结束时间(改为当前时间)
	public static Map<String, String> getfulletimeMonth(int firstmonth,
			int lastmonth, int year) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		String day = "01";
		String monthstr = "";
		String monthstr2 = "";
		int year2 = 0;
		if (firstmonth < 10) {
			monthstr = "0" + firstmonth;
		} else {
			monthstr = "" + firstmonth;
		}
		if (lastmonth == 12) {
			year2 = year + 1;
			monthstr2 = "01";
		} else {
			year2 = year;
			lastmonth = lastmonth + 1;
			if (lastmonth < 10) {
				monthstr2 = "0" + lastmonth;
			} else {
				monthstr2 = "" + lastmonth;
			}
		}
		map.put("beginTime", year + "-" + monthstr + "-" + day);
		map.put("endTime", nowtimeString());// 结束时间(改为当前时间)
		return map;

	}
  
	/**
	 * 功能:同比年组装
	 * <p>作者 杨荣忠 2015-1-26 下午08:05:10
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getYearTlist(ConditionEntity conditionEntity)
	{
		int count=conditionEntity.getCircle();
		int nowYear=Integer.parseInt(conditionEntity.getBeginTime().substring(0,4));
		List<String>list =new ArrayList();
		list.add(nowYear+"");
		for(int i=1;i<=count;i++){
		    list.add((nowYear-i)+"");
		}
		return  (String[]) list.toArray(new String[list.size()]);
	}
	
	// 当前时间
	public static String nowtimeString() throws ParseException {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		String monthstr = "";
		if (month < 10) {
			monthstr = "0" + month;
		} else {
			monthstr = month + "";
		}
		int day = now.get(Calendar.DAY_OF_MONTH);
		return year + "-" + monthstr + "-" + (day < 10 ? "0" + day : day);
	}

	// 求返回范围的list
	public static List<Integer> getIndex(int first, int last)
			throws ParseException {
		List<Integer> List = new ArrayList();
		for (int i = first; i <= last; i++) {
			List.add(i);
		}
		return List;
	}

	// 求明天的开始时间
	public static String addDay(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}

	// 求两个时间范围的month 2014-01-01
	public static List getMonthList(String beginTime, String endTime) {
		List list = new ArrayList();
		if (beginTime != "" && endTime != "") {
			String begb = beginTime.substring(0, 4);
			String bege = endTime.substring(0, 4);
			String endb = beginTime.substring(5, 7);
			String ende = endTime.substring(5, 7);

			if (begb.equals(bege)) {
				list = alowmonth(endb, ende, Integer.parseInt(begb));
			} else {
				// 只求今年的月报，可扩展 ，以结束时间所在年的月份
				/*
				 * int yearLess=Integer.parseInt(bege)-Integer.parseInt(begb)-1;
				 * List listFirst=alowmonth(endb, 12+"",Integer.parseInt(begb));
				 * List listSecond=new ArrayList(); for(int
				 * i=1;i<=yearLess;i++){ listSecond.addAll(alowmonth( 1+"",
				 * 12+"",Integer.parseInt(begb)+i)); }
				 */
				List listThird = alowmonth(1 + "", ende, Integer.parseInt(bege));
				// list.addAll(listFirst);
				// list.addAll(listSecond);
				list.addAll(listThird);
			}
		} else {
			// 默认当前年当前月向前推3个月()
			list = alowmonth(Integer.parseInt(getLocalDate().substring(5, 7))
					- 3 + "", getLocalDate().substring(5, 7), Integer
					.parseInt(getLocalDate().substring(0, 4)));
		}

		return list;
	}

	public static long getTimeMillis() {
		Date dt = new Date();
		Long time = dt.getTime();// 这就是距离1970年1月1日0点0分0秒的毫秒数
		return (System.currentTimeMillis());// 与上面的相同
	}

	public static String changeMoneyType(Double num) {
		String resultnum = "0.0";
		Format fm1 = new DecimalFormat("##,###.00");
		if (fm1.format(num).equals(".00"))
			resultnum = "0";
		else if (num < 1)
		//	resultnum = num.toString().substring(0, 5);
			resultnum = persentFormat(num);
		else
			resultnum = fm1.format(num);
		
	//	if(resultnum.l)
		return resultnum;
	}
	
	public static String persentFormat(double num){
	//	double d = 3.1465926;
		String result = String.format("%.2f", num);
	//	System.out.println(result); 
		return result;
	}

	
	

	// 测试main
	public static void main(String[] args) throws Exception {
		double[][] dataOnetoTwo = new double[][] { { 672, 766 }, { 325, 521},
				{ 332, 256 } };
		double[][] dataThridtoFour = new double[][] { { 1, 256 },{ 672, 766 } };
        double[][] data=null;
		data=(double[][]) ArrayUtils.addAll(dataOnetoTwo, dataThridtoFour);
		System.out.println("sdf");
	}
}
