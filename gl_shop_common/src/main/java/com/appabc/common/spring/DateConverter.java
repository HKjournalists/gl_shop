package com.appabc.common.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @since  : 2014年8月27日 下午6:56:45
 */

public class DateConverter implements Converter<String, Date> {

	private String pattern = "yyyy-MM-dd HH:mm:ss";
	
	/* (non-Javadoc)  
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)  
	 */
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
	    dateFormat.setLenient(false);    
	    try {    
	        return dateFormat.parse(source);    
	    } catch (ParseException e) {    
	        e.printStackTrace();    
	    }           
	    return null; 
	}

	/**  
	 * @param pattern the pattern to set  
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
