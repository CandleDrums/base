package com.cds.base.util.misc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description 日期工具
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月18日 上午10:01:06
 * @version 1.0
 * @since JDK 1.8
 */
public class DateUtils {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYY_MM = "yyyy-MM";
	public final static String MM_DD = "MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String ECMA_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String YYYYMMDDHH = "yyyyMMddHH";
	public static final String YYYYMMDD = "yyyyMMdd";
	public final static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public SimpleDateFormat dfForm = null;

	/**
	 * @description 解析字符串转换成Date
	 * @param dt
	 * @param sFmt
	 * @return
	 * @returnType Date
	 * @exception @since 1.0.0
	 */
	public static Date parseDate(String dt, String sFmt) {
		if (StringUtils.isEmpty(dt))
			return null;
		SimpleDateFormat sdfFrom = new SimpleDateFormat(sFmt);

		try {
			return sdfFrom.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param sDate
	 * @param sFmtFrom
	 * @param sFmtTo
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(String sDate, String sFmtFrom, String sFmtTo) throws ParseException {

		SimpleDateFormat sdfFrom = new SimpleDateFormat(sFmtFrom);

		SimpleDateFormat sdfTo = new SimpleDateFormat(sFmtTo);

		return sdfTo.format(sdfFrom.parse(sDate));
	}

	/**
	 * 格式化日期
	 * 
	 * @param dt
	 * @param sFmt
	 * @return
	 */
	public static String formatDate(Date dt, String sFmt) {

		if (dt == null || sFmt == null)

			return "";

		SimpleDateFormat sdfFrom = new SimpleDateFormat(sFmt);

		return sdfFrom.format(dt).toString();

	}
	/*
	 * public static void main(String[] args) { log.info(formatDate(new
	 * Date(),SIMPLE_HOUR_FMT)); }
	 */

	/**
	 * 格式化日期,yyyy-MM-dd
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatDate(Date dt) {

		return formatDate(dt, YYYY_MM_DD);
	}

	/**
	 * 格式化日期,yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatDateTime(Date dt) {

		return formatDate(dt, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 格式化日期,yyyyMMddHHmmss
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatSimpleDate(Date dt) {

		return formatDate(dt, YYYYMMDDHHMMSS);
	}

	/**
	 * 方法描述：返回时间 HH:mm:ss
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-11-2 下午02:49:03
	 * @param date
	 * @return
	 */
	public static String formateSimpleTime(Date date) {
		return formatDate(date, HH_MM_SS);
	}

	/**
	 * 方法描述：获取当前时间HH:SS:00
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-11-2 下午03:56:45
	 * @return
	 */
	public static String getCurrentTime() {
		String str = getCurrentDateTime();
		return str.substring(11, 17) + "00";
	}

	/**
	 * 得到当前时间,yyyyMMddHHmmss
	 * 
	 * @param args
	 */
	public static String getCurrentSimpleTime() {
		Date dt = new Date();
		return formatDate(dt, YYYYMMDDHHMMSS);
	}

	/**
	 * @description 获取当前精确时间
	 * @return
	 * @returnType String
	 * @author liming
	 */
	public static String getCurrentSimpleExactTime() {
		Date dt = new Date();
		return formatDate(dt, YYYYMMDDHHMMSSSSS);
	}

	/**
	 * 得到当前时间,yyyy-MM-dd HH:mm:ss
	 * 
	 * @param args
	 */
	public static String getCurrentDateTime() {
		Date dt = new Date();
		return formatDate(dt, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 得到当前时间,yyyy-MM-dd
	 * 
	 * @param args
	 */
	public static String getCurrentDate() {
		Date dt = new Date();
		return formatDate(dt, YYYY_MM_DD);
	}

	/**
	 * 得到当前时间,yyyy-MM-dd
	 * 
	 * @param args
	 */
	public static String getCurrentDate(String fmt) {
		Date dt = new Date();
		return formatDate(dt, fmt);
	}

	/**
	 * @description 获取昨日时间
	 * @return
	 * @returnType String
	 * @exception @since 1.0.0
	 */
	public static String getYestodayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return formatDate(cal.getTime(), YYYY_MM_DD);
	}

	/**
	 * @description 获取前日时间
	 * @return
	 * @returnType String
	 * @exception @since 1.0.0
	 */
	public static String getBeforeYestodayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		return formatDate(cal.getTime(), YYYY_MM_DD);
	}

	/**
	 * @description 获取明日时间
	 * @return
	 * @returnType String
	 * @exception @since 1.0.0
	 */
	public static String getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		return formatDate(cal.getTime(), YYYY_MM_DD);
	}

	/**
	 * @description 获取昨日DATE
	 * @return
	 * @returnType String
	 * @exception @since 1.0.0
	 */
	public static Date getYestodayTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return Timestamp.valueOf(new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date()));
	}

	/**
	 * 转换日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp conver2Timestamp(String date) {
		return Timestamp.valueOf(date);
	}

	/**
	 * 方法描述：返回Date型日期，yyyy-MM-dd HH:mm:ss
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-4-28 下午01:15:32
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return sdf.parse(dateStr);
	}

	/**
	 * 方法描述：获取近半年时间,yyyyMM
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-4-28 下午01:15:35
	 * @return
	 */
	public static String[] getNearHalfYear() {
		String[] halfYear = new String[6];
		String today = getCurrentDate();
		String yearTemp = today.substring(0, 4);
		String monthTemp = today.substring(5, 7);
		int year = Integer.parseInt(yearTemp);
		int month = Integer.parseInt(monthTemp) + 1;
		if (month > 6) {
			for (int i = 0; i < 6; i++) {
				month--;
				halfYear[i] = yearTemp + month;
			}
		} else {
			switch (month) {
			case 6: {
				halfYear[0] = yearTemp + "05";
				halfYear[1] = yearTemp + "04";
				halfYear[2] = yearTemp + "03";
				halfYear[3] = yearTemp + "02";
				halfYear[4] = yearTemp + "01";
				halfYear[5] = (year - 1) + "12";
				break;
			}
			case 5: {
				halfYear[0] = yearTemp + "04";
				halfYear[1] = yearTemp + "03";
				halfYear[2] = yearTemp + "02";
				halfYear[3] = yearTemp + "01";
				halfYear[4] = (year - 1) + "12";
				halfYear[5] = (year - 1) + "11";
				break;
			}
			case 4: {
				halfYear[0] = yearTemp + "03";
				halfYear[1] = yearTemp + "02";
				halfYear[2] = yearTemp + "01";
				halfYear[3] = (year - 1) + "12";
				halfYear[4] = (year - 1) + "11";
				halfYear[5] = (year - 1) + "10";
				break;
			}
			case 3: {
				halfYear[0] = yearTemp + "02";
				halfYear[1] = yearTemp + "01";
				halfYear[2] = (year - 1) + "12";
				halfYear[3] = (year - 1) + "11";
				halfYear[4] = (year - 1) + "10";
				halfYear[5] = (year - 1) + "09";
				break;
			}
			case 2: {
				halfYear[0] = yearTemp + "01";
				halfYear[1] = (year - 1) + "12";
				halfYear[2] = (year - 1) + "11";
				halfYear[3] = (year - 1) + "10";
				halfYear[4] = (year - 1) + "09";
				halfYear[5] = (year - 1) + "08";
				break;
			}
			case 1: {
				halfYear[0] = (year - 1) + "12";
				halfYear[1] = (year - 1) + "11";
				halfYear[2] = (year - 1) + "10";
				halfYear[3] = (year - 1) + "09";
				halfYear[4] = (year - 1) + "08";
				halfYear[5] = (year - 1) + "07";
				break;
			}
			}
		}

		return halfYear;
	}

	/**
	 * 方法描述：获取今日星期
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-10-31 下午05:03:27
	 * @return
	 */
	public static int getTodayWeek() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	/**
	 * 方法描述：判断是否是今天的星期
	 * 
	 * @author ming.li <a href="http://286.iteye.com">iteye blog</a>
	 * @time 2011-10-31 下午05:05:48
	 * @param weekSet
	 * @return
	 */
	public static boolean isTodayWeek(Set<?> weekSet) {
		int weekDay = getTodayWeek();
		return (weekSet.contains(weekDay));
	}

	public static String getUnixTime(Timestamp appointTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		try {
			date = df.parse(String.valueOf(appointTime));
			long s = date.getTime();
			return String.valueOf(s).substring(0, 10);
		} catch (ParseException e) {
			return "";
		}
	}

	public static String getDateString(Timestamp appointTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		try {
			date = df.parse(String.valueOf(appointTime));
			return formatSimpleDate(date);
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * @description 获取小时索引
	 * @param cspfDate
	 * @return
	 * @returnType int
	 * @exception @since 1.0.0
	 */
	public static int getIndexFromHour(String cspfDate) {
		return Integer.parseInt(cspfDate.substring(11, 13));
	}

	public static int getIndexFromDay(String cspfDate) {
		return Integer.parseInt(cspfDate.substring(8, 10));
	}

	public static int getIndexFromMonth(String cspfDate) {
		return Integer.parseInt(cspfDate.substring(5, 7));
	}

	/**
	 * @description 判断时间是否是空
	 * @param t
	 * @return
	 * @returnType boolean
	 * @exception @since 1.0.0
	 */
	public static boolean hasTime(Timestamp t) {
		if (t != null) {
			return true;
		}
		return false;
	}

	/**
	 * @description 获取结束时间
	 * @param endDate
	 * @return
	 * @returnType Date
	 * @exception @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	public static Date getEndDate(Date endDate) {
		if (endDate == null) {
			try {
				return string2Date("9999-12-12 23:59:59");
			} catch (ParseException e) {
				return new Date("9999-12-12 23:59:59");
			}
		}
		return endDate;
	}

	/**
	 * 获取时间周日
	 */
	public static String getSunday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return df.format(cal.getTime());
	}

	/**
	 * 获取时间周六
	 */
	public static String getSaturday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return df.format(cal.getTime());

	}

	/**
	 * @description 获取时间戳
	 * @return String
	 */
	public static String getCurrentTimestampStr() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * @description 当前日期前后天数日志
	 * @return Date
	 */
	public static Date getDateFromNow(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, days);
		return now.getTime();
	}

	/**
	 * 获取两个日期相差的月数
	 * <p>
	 *      
	 */
	public static int getMonthDiff(Date d1, Date d2) {

		Calendar c1 = Calendar.getInstance();

		Calendar c2 = Calendar.getInstance();

		c1.setTime(d1);

		c2.setTime(d2);

		int year1 = c1.get(Calendar.YEAR);

		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);

		int month2 = c2.get(Calendar.MONTH);

		int day1 = c1.get(Calendar.DAY_OF_MONTH);

		int day2 = c2.get(Calendar.DAY_OF_MONTH);

		// 获取年的差值 

		int yearInterval = year1 - year2;

		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数

		if (month1 < month2 || month1 == month2 && day1 < day2)

			yearInterval--;
		// 获取月数差值

		int monthInterval = (month1 + 12) - month2;

		if (day1 < day2)
			monthInterval--;

		monthInterval %= 12;

		int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);

		return monthsDiff;

	}
}
