/**
 *
 */
package com.appabc.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午7:11:42
 */
public class DateUtil {

	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	/**
	 * 获取某天的该周第一天（date==null,以本周为起点）
	 * @param n 后几周
	 * @return
	 */
	public static Date getFirstDayOfNWeek(Date date, int n) {
        Calendar calendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);// 取当前时间
        if(date != null) calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Monday is 2, so need to -1.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, 7 * n -dayOfWeek + 1);
        return calendar.getTime();
    }

    /**
     * 获取某天的该周最后一天（date==null,以本周为起点）
     * @param date 起点时间
     * @param n 后几周
     * @return
     */
    public static Date getLastDayOfNWeek(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        if(date != null) calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Monday is 2, so need to -1.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, 7 * (n + 1) - dayOfWeek);
        return calendar.getTime();
    }

    /**
	 * 字符串转换到时间格式
	 * @param dateStr 需要转换的字符串
	 * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException 转换异常
	 */
	public static Date strToDate(String dateStr,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String DateToStr(Date date,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getDifferDayWithTwoDate(Date source,Date target){
		if (null == source || null == target) {
	      return -1;
	    }
		Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(source);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(target);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
	}

	@SuppressWarnings("deprecation")
	public static boolean isTargetGtSourceDate(Date source,Date target){
		if (null == source || null == target) {
	      return false;
	    }
		source.setHours(23);
		source.setMinutes(59);
		source.setSeconds(59);

		long sourceTimes = source.getTime();
		long targetTimes = target.getTime();

		return targetTimes > sourceTimes;
	}

	public static boolean isTargetGtSourceDateNo235959(Date source,Date target){
		if (null == source || null == target) {
			return false;
		}

		long sourceTimes = source.getTime();
		long targetTimes = target.getTime();

		return targetTimes > sourceTimes;
	}
	
	public static long getDifferTimeMillisTwoDate(Date source,Date target){
		if (null == source || null == target) {
		   return 0;
		}
		long s = source.getTime();
		long d = target.getTime();
		return d-s;
	}

	public static int getDifferHoursWithTwoDate(Date source,Date target){
		if (null == source || null == target) {
	      return -1;
	    }
		Calendar from = Calendar.getInstance();
		from.setTime(source);
		Calendar to = Calendar.getInstance();
		to.setTime(target);
        int hours = (int) Math.abs((to.getTime().getTime() - from.getTime().getTime())
        		/ (60 * 60 * 1000));

        return hours;
	}

	/**
	 * 获取现在时间
	 *
	 * @return
	 */
	public static Date getNowDate() {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
	    String dateString = formatter.format(currentTime);
	    ParsePosition pos = new ParsePosition(0);
	    Date currentTime_2 = formatter.parse(dateString, pos);
	    return currentTime_2;
	}

	/**
	 * 获取日期 +hours 的日期
	 * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
	 * 2015-01-12 14:08:09, 8  ==> 2015-01-12 22:08:09
	 * 2015-01-12 14:08:09, -8 ==> 2015-01-12 06:08:09
	 * @param hours
	 * */
	public static Date getDate(Date source,int hours){
		if(source == null){
			source = getNowDate();
		}
		if(hours == 0){
			return source;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.HOUR, hours);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 获取日期 +hours 的日期
	 * 2015-05-06 12:43:08, 1.1   ==> 2015-01-12 14:08:09
	 * 2015-05-06 12:44:56, 0.25  ==> 2015-05-06 12:59:56
	 * 2015-05-06 12:45:46, -0.25 ==> 2015-05-06 12:30:46
	 * 
	 * @param hours
	 * 
	 * */
	public static Date getDateMoveByHours(Date source,double hours){
		if(source == null){
			source = getNowDate();
		}
		if(hours == 0){
			return source;
		}
		double dd = RandomUtil.mulRound(hours,(60 * 60 * 1000));
		long ss = source.getTime();
		double ee = RandomUtil.addRound(ss, dd);
		Calendar calendar= Calendar.getInstance();
		calendar.setTimeInMillis((long)ee);
		Date now = calendar.getTime();
		return now;
	}
	
	/**
	 * 获取日期 +hours 的日期
	 * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
	 * 2015-01-12 14:08:09, 8  ==> 2015-01-12 22:08:09
	 * 2015-01-12 14:08:09, -8 ==> 2015-01-12 06:08:09
	 * @param source
	 * @param hours
	 * @return Date
	 * */
	public static Date getDateMoveByHour(Date source,int hours){
		return getDate(source, hours);
	}
	
	/**
	 * 获取日期 +Minutes 的日期
	 * 2015-01-12 14:08:09, 0  ==> 2015-01-12 14:08:09
	 * 2015-01-12 14:08:09, 8  ==> 2015-01-12 14:16:09
	 * 2015-01-12 14:08:09, -8 ==> 2015-01-12 14:00:09
	 * @param source
	 * @param hours
	 * @return Date
	 * */
	public static Date getDateMoveByMinute(Date source,int minutes){
		if(source == null){
			source = getNowDate();
		}
		if(minutes == 0){
			return source;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间YYYY-MM-DD（2015-2-4 0:00:00）
	 * @return
	 */
	public static Date getTodayDateOfYMD(){
		Calendar calendar= Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/** 
     * 计算两个日期之间相差的天数 
     * @param date1 
     * @param date2 
     * @return 
     */  
    public static int daysBetween(Date date1,Date date2) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date1);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(date2);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
          
       return Integer.parseInt(String.valueOf(between_days));         
    }  

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println(getTodayDateOfYMD().toLocaleString());
		Date source = DateUtil.getNowDate();
		double wd = 10;
		Date d = getDateMoveByHours(source, wd);
		System.out.println(DateUtil.DateToStr(source, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		System.out.println(DateUtil.DateToStr(d, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		/*Date now = getNowDate();
		Date aNow = getDateMoveByMinute(getNowDate(), 15);
		System.out.println(DateUtil.DateToStr(now, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		System.out.println(DateUtil.DateToStr(aNow, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));*/
//		System.out.println(DateUtil.strToDate("2010-3-5 13:13:13", DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS).toLocaleString());
//		Calendar aCalendar = Calendar.getInstance();
//		//aCalendar.set(2014, 9, 14, 10, 10, 10);
//		aCalendar.set(Calendar.MONTH, 7);
//		Date source = aCalendar.getTime();
//		System.out.println(getDifferDayWithTwoDate(source,getNowDate()));
//
//		String s = DateUtil.DateToStr(Calendar.getInstance().getTime(), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
//
//		System.out.println(s);
//
//		Date now = getNowDate();
//		System.out.println(DateUtil.DateToStr(now, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
//		Date d = getDate(now, 8);
//		System.out.println(DateUtil.DateToStr(d, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
//		Date e = getDate(now, -8);
//		System.out.println(DateUtil.DateToStr(e, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
	}


}
