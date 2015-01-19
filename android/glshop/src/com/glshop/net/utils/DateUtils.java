package com.glshop.net.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.glshop.platform.utils.StringUtils;

/**
 * FileName    : DateUtils.java
 * Description : 日期数据格式类
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 叶跃丰
 * @version    : 1.0
 * Create Date : 2014-8-8 下午2:00:20
 **/
public class DateUtils {

	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PUB_DATE_FORMAT = "yyyy.MM.dd";
	public static final String TAKE_PHOTO_NAME_FORMAT = "yyyyMMdd_HHmmss";
	public static final String TRADE_DATE_FORMAT = "yyyy年MM月dd日";
	public static final String TRADE_DATE_FORMAT_V2 = "yyyy-MM-dd";
	public static final String CONTRACT_DATETIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
	public static final String CONTRACT_DATETIME_FORMAT_V2 = "yyyy年MM月dd日HH时mm分";
	private static final String TRADE_WAIT_TIME_FORMAT = "%s天%s小时%s分";
	private static final String TRADE_WAIT_TIME_FORMAT_WITH_SECOND = "%s天%s小时%s分%s秒";

	public static final long TIME_UNIT_SECOND = 1000;
	public static final long TIME_UNIT_MINUTE = 60;
	public static final long TIME_UNIT_HOUR = 60 * 60;
	public static final long TIME_UNIT_DAY = 24 * 60 * 60;

	/**
	 * 将String类型的date数据转换为long类型
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static long covertDate2Long(String pattern, String strDate) {
		long longDate = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		try {
			Date newDate = sdf.parse(strDate);
			longDate = newDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return longDate;
	}

	/**
	 * 将long类型的date数据转换为String类型
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String convertDate2String(String pattern, long date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		return sdf.format(new Date(date));
	}

	/**
	 * 将fromPatterng格式的String类型的date数据转换为fromPattern格式的String类型数据
	 * @param fromPattern
	 * @param toPattern
	 * @param strDate
	 * @return
	 */
	public static String convertDate2String(String fromPattern, String toPattern, String strDate) {
		String newDate = "";
		SimpleDateFormat fromSdf = new SimpleDateFormat(fromPattern, Locale.CHINA);
		SimpleDateFormat toSdf = new SimpleDateFormat(toPattern, Locale.CHINA);
		try {
			Date date = fromSdf.parse(strDate);
			newDate = toSdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isNEmpty(newDate)) {
			newDate = strDate;
		}
		return newDate;
	}

	/**
	 * 获取客户端默认时间显示(将COMMON_DATE_FORMAT格式的String类型的date数据转换为PUB_DATE_FORMAT格式的String类型数据)
	 * @param fromPattern
	 * @param toPattern
	 * @param strDate
	 * @return
	 */
	public static String getDefaultDate(String strDate) {
		String newDate = "";
		SimpleDateFormat fromSdf = new SimpleDateFormat(COMMON_DATE_FORMAT, Locale.CHINA);
		SimpleDateFormat toSdf = new SimpleDateFormat(TRADE_DATE_FORMAT, Locale.CHINA);
		try {
			Date date = fromSdf.parse(strDate);
			newDate = toSdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isNEmpty(newDate)) {
			newDate = strDate;
		}
		return newDate;
	}

	/**
	 * 获取等待的时间，时间格式：X天X小时X分
	 * @param strTime
	 * @return
	 */
	public static String getWaitTime(String strTime) {
		long time = covertDate2Long(COMMON_DATE_FORMAT, strTime) - System.currentTimeMillis();
		time /= TIME_UNIT_SECOND;
		int day = (int) (time / TIME_UNIT_DAY);
		int hour = (int) ((time % TIME_UNIT_DAY) / TIME_UNIT_HOUR);
		int minute = (int) ((time % TIME_UNIT_DAY % TIME_UNIT_HOUR) / TIME_UNIT_MINUTE);
		//day + "天" + hour + "小时" + minute + "分";
		if (day < 0 || hour < 0 || minute < 0) {
			day = hour = minute = 0;
		}
		return String.format(TRADE_WAIT_TIME_FORMAT, day, hour, minute);
	}

	/**
	 * 获取等待的时间，时间格式：X天X小时X分
	 * @param time
	 * @return
	 */
	public static String getWaitTime(long time) {
		time /= TIME_UNIT_SECOND;
		int day = (int) (time / TIME_UNIT_DAY);
		int hour = (int) ((time % TIME_UNIT_DAY) / TIME_UNIT_HOUR);
		int minute = (int) ((time % TIME_UNIT_DAY % TIME_UNIT_HOUR) / TIME_UNIT_MINUTE);
		//day + "天" + hour + "小时" + minute + "分";
		if (day < 0 || hour < 0 || minute < 0) {
			day = hour = minute = 0;
		}
		return String.format(TRADE_WAIT_TIME_FORMAT, day, hour, minute);
	}

	/**
	 * 获取等待的时间，时间格式：X天X小时X分
	 * @param time
	 * @return
	 */
	public static String getWaitTimeWithSecond(String strTime) {
		long time = covertDate2Long(COMMON_DATE_FORMAT, strTime) - System.currentTimeMillis();
		int day = (int) (time / TIME_UNIT_DAY);
		int hour = (int) ((time % TIME_UNIT_DAY) / TIME_UNIT_HOUR);
		int minute = (int) ((time % TIME_UNIT_DAY % TIME_UNIT_HOUR) / TIME_UNIT_MINUTE);
		int second = (int) (time % TIME_UNIT_DAY % TIME_UNIT_HOUR % TIME_UNIT_MINUTE);
		//day + "天" + hour + "小时" + minute + "分" + second + "秒";
		if (day < 0 || hour < 0 || minute < 0 || second < 0) {
			day = hour = minute = second = 0;
		}
		return String.format(TRADE_WAIT_TIME_FORMAT_WITH_SECOND, day, hour, minute, second);
	}

	/**
	 * 获取等待的时间，时间格式：X天X小时X分X秒
	 * @param time
	 * @return
	 */
	public static String getWaitTimeWithSecond(long time) {
		time /= TIME_UNIT_SECOND;
		int day = (int) (time / TIME_UNIT_DAY);
		int hour = (int) ((time % TIME_UNIT_DAY) / TIME_UNIT_HOUR);
		int minute = (int) ((time % TIME_UNIT_DAY % TIME_UNIT_HOUR) / TIME_UNIT_MINUTE);
		int second = (int) (time % TIME_UNIT_DAY % TIME_UNIT_HOUR % TIME_UNIT_MINUTE);
		//day + "天" + hour + "小时" + minute + "分" + second + "秒";
		if (day < 0 || hour < 0 || minute < 0 || second < 0) {
			day = hour = minute = second = 0;
		}
		return String.format(TRADE_WAIT_TIME_FORMAT_WITH_SECOND, day, hour, minute, second);
	}

	/**
	 * 从字符串日期中获取年份
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static int getYearFromStrDate(String pattern, String strDate) {
		Calendar calendar = getCalendar(pattern, strDate);
		if (calendar == null) {
			return -1;
		}
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 从字符串日期中获取月份
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static int getMonthFromStrDate(String pattern, String strDate) {
		Calendar calendar = getCalendar(pattern, strDate);
		if (calendar == null) {
			return -1;
		}
		return convertENMonth2CN(calendar.get(Calendar.MONTH));
	}

	/**
	 * 从字符串日期中获取日期
	 * @param pattern
	 * @param strDate
	 * @return
	 */
	public static int getDayFromStrDate(String pattern, String strDate) {
		Calendar calendar = getCalendar(pattern, strDate);
		if (calendar == null) {
			return -1;
		}
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	private static Calendar getCalendar(String pattern, String strDate) {
		Calendar calendar = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		Date dateTime = null;
		try {
			dateTime = sdf.parse(strDate);
			calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setTime(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * 获取以指定日期格式的日期显示
	 * @param pattern
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStrDate(String pattern, int year, int month, int day) {
		String strDate = "";

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month, day);
		Date date = calendar.getTime();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
			strDate = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * 转换中文月份为英文月份
	 * @param month
	 * @return
	 */
	public static int convertCNMonth2EN(int month) {
		return month - 1;
	}

	/**
	 * 转换英文月份为中文月份
	 * @param month
	 * @return
	 */
	public static int convertENMonth2CN(int month) {
		return month + 1;
	}

}
