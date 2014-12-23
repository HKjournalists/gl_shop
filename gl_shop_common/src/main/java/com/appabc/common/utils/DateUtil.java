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
	
	/**
	 * 获取某周第一天（以本周为起点）
	 * @param n 后几周
	 * @return
	 */
	public static Date getFirstDayOfNWeek(int n) {
        Calendar calendar = Calendar.getInstance();// 取当前时间
        calendar.add(Calendar.WEEK_OF_YEAR, n);// 增加n个星期
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {// 如果当前不是星期一
            calendar.add(Calendar.DATE, -1);// 减一天
        }
        return calendar.getTime();// 返回Date对象
    }

    /**
     * 获取某周最后一天（以本周为起点）
     * @param n 后几周
     * @return
     */
    public static Date getLastDayOfNWeek(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, n);// 增加n个星期
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
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
	
	public static int getDifferDayWithTwoDate(Date soure,Date target){
		if (null == soure || null == target) {
	      return -1;
	    }
		Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(soure);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(target);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
	}
	
	public static int getDifferHoursWithTwoDate(Date soure,Date target){
		if (null == soure || null == target) {
	      return -1;
	    }
		Calendar from = Calendar.getInstance();
		from.setTime(soure);
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
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println(DateUtil.strToDate("2010-3-5 13:13:13", DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS).toLocaleString());
		Calendar aCalendar = Calendar.getInstance();
		//aCalendar.set(2014, 9, 14, 10, 10, 10);
		aCalendar.set(Calendar.MONTH, 7);
		Date source = aCalendar.getTime();
		System.out.println(getDifferDayWithTwoDate(source,getNowDate()));
		
		String s = DateUtil.DateToStr(Calendar.getInstance().getTime(), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
		
		System.out.println(s);
	}
    

}
