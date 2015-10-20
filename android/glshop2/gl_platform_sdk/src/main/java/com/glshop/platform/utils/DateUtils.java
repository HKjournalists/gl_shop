package com.glshop.platform.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName    : DateUtils.java
 * Description : 日期数据格式类
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午2:00:20
 **/
public class DateUtils {

	public static final String ALL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String TIME_FORMAT = "yyyyMMddHHmmss";

	/** 根据格式获取日期字符串 */
	public static String getNowDate(String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(new Date());
	}
}
