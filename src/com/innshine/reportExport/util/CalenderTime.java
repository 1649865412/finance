package com.innshine.reportExport.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalenderTime {

	
	/**
	 * 功能:str转为Date
	 * <p>作者 杨荣忠 2014-9-23 上午09:40:07
	 * @param datedate
	 * @return
	 * @throws ParseException
	 */
	public static Date strtodate(String datedate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = sdf.parse(datedate);
		return date2;
	}

	/**
	 * 功能:Date转为str
	 * <p>作者 杨荣忠 2014-9-23 上午09:40:16
	 * @param date
	 * @return
	 */
	public static String transDateToDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = new Date(time);
		return format.format(date);
	}

	
	/**
	 * 功能:long time转str(年月日格式)
	 * <p>作者 杨荣忠 2014-9-23 上午09:40:31
	 * @param time
	 * @return
	 */
	public static String transLongToDateString(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(time);
		return format.format(date);
	}

	
	
	/**
	 * 由相差天数，求另一个时间
	 * 差值（可正负），开始时间
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

	
	
	/**
	 * 功能:求当前时间月初一号
	 * <p>作者 杨荣忠 2014-9-23 上午09:41:17
	 * @return
	 * @throws ParseException
	 */
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

	
	/**
	 * 功能:当前时间的下月时间
	 * <p>作者 杨荣忠 2014-9-23 上午09:41:45
	 * @return
	 * @throws ParseException
	 */
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

	
	
	/**
	 * 功能:求某个时间的下个月时间
	 * <p>作者 杨荣忠 2014-9-23 上午09:42:26
	 * @param nowTime
	 * @return
	 * @throws ParseException
	 */
	public static String nextMonth(String nowTime) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(strtodate(nowTime));
		calender.add(Calendar.MONTH, 1);
		return transDateToDateString(calender.getTime());

	}

	/**
	 * 功能:求某个时间的上个月时间
	 * <p>作者 杨荣忠 2014-9-23 上午09:42:51
	 * @param nowTime
	 * @return
	 * @throws ParseException
	 */
	public static String lastMonth(String nowTime) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(strtodate(nowTime));
		calender.add(Calendar.MONTH, -1);
		return transDateToDateString(calender.getTime());
	}

	
	/**
	 * 功能:求某个时间的上个月时间月初一号
	 * <p>作者 杨荣忠 2014-9-23 上午09:42:51
	 * @param nowTime
	 * @return
	 * @throws ParseException
	 */
	public static String lastMonthFirst(String nowTime) throws ParseException {
		Calendar now = Calendar.getInstance();
		now.setTime(strtodate(nowTime));
		now.add(Calendar.MONTH, -1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		String time = transDateToDateString(now.getTime());
		return time;

	}

	/**
	 * 功能:求当前时间的月初一号
	 * <p>作者 杨荣忠 2014-9-23 上午09:42:51
	 * @param nowTime
	 * @return
	 * @throws ParseException
	 */
	public static String nowMonthFirst(String nowTime) throws ParseException {
		Calendar now = Calendar.getInstance();
		now.setTime(strtodate(nowTime));
		now.set(Calendar.DAY_OF_MONTH, 1);
		String time = transDateToDateString(now.getTime());
		return time;

	}

	
	/**
	 * 功能:当前时间上月月初一号
	 * <p>作者 杨荣忠 2014-9-23 上午09:44:17
	 * @return
	 * @throws ParseException
	 */
	public static String premonthfirst() throws ParseException {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		String time = transDateToDateString(now.getTime());
		return time;

	}
	
	
	/**
	 * 功能:同比n年的时间
	 * <p>作者 杨荣忠 2014-9-23 上午09:46:40
	 * @param nowTime
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public static String comparYearTime(String nowTime,int year) throws ParseException{
		Calendar now = Calendar.getInstance();
		now.setTime(strtodate(nowTime));
		now.set(Calendar.YEAR, year);
		String time = transDateToDateString(now.getTime());
		return time;
	}
	
	
	/**
	 * MD5加密处理
	 * 
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			/** 获得MD5摘要算法的 MessageDigest 对象 **/
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			/** 使用指定的字节更新摘要 **/
			mdInst.update(btInput);
			/** 获得密文 **/
			byte[] md = mdInst.digest();
			/** 把密文转换成十六进制的字符串形式 **/
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 测试main
	public static void main(String[] args) throws Exception {
		System.out.println(MD5("杨荣忠"));
	}

}
