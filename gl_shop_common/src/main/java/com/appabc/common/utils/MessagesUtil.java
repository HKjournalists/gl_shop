package com.appabc.common.utils;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import com.appabc.common.spring.BeanLocator;

/**
 * 
 * MessagesUtil.getMessage("test",Locale.ENGLISH) get i18n properties constants from messages_en.properties
 * MessagesUtil.getMessage("message") get system properties constants from messages_prop.properties
 * MessagesUtil.getMessage("CONTRACT_ID_IS_NULL") get system properties constants from messages_prop.properties
 * @Description : message utils and i18n utils
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月12日 下午7:51:45
 */

public class MessagesUtil {

	private static LogUtil log = LogUtil.getLogUtil(MessagesUtil.class);
	private static MessageSource ms = BeanLocator.getMessageSource(); 
	private static Locale locale = new Locale("prop");
	
	private static void checkNullCodeLocal(String code,Locale l){
		if(StringUtils.isEmpty(code) || l == null){
			throw new RuntimeException("code or locale is not allow null.");
		}
	}
	
	public static double getDoubleValue(String code){
		return getDoubleValue(code,locale);
	}
	
	public static double getDoubleValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toDouble(getMessage(code,l), 0d);
	}
	
	public static float getFloatValue(String code){
		return getFloatValue(code,locale);
	}
	
	public static float getFloatValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toFloat(getMessage(code,l), 0f);
	}
	
	public static long getLongValue(String code){
		return getLongValue(code,locale);
	}
	
	public static long getLongValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toLong(getMessage(code,l), 0l);
	}
	
	public static short getShortValue(String code){
		return getShortValue(code,locale);
	}
	
	public static short getShortValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toShort(getMessage(code,l), (short)0);
	}
	
	public static byte getByteValue(String code){
		return getByteValue(code,locale);
	}
	
	public static byte getByteValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toByte(getMessage(code,l), (byte)0);
	}
	
	public static int getIntValue(String code){
		return getIntValue(code,locale);
	}
	
	public static int getIntValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return NumberUtils.toInt(getMessage(code,l), 0);
	}
	
	public static boolean getBooleanValue(String code){
		return getBooleanValue(code,locale);
	}
	
	public static boolean getBooleanValue(String code,Locale l){
		checkNullCodeLocal(code,l);
		return BooleanUtils.toBoolean(getMessage(code,l));
	}
	
	public static String getMessage(String code){
		return getMessage(code, null, locale);
	}
	
	public static String getMessage(String code,Locale l){
		return getMessage(code,null, l);
	}
	
	public static String getMessage(String code,Object[] args){
		return getMessage(code, args, locale);
	}
	
	public static String getMessage(String code,Object[] args,Locale locale){
		log.debug("get the " + code + " values; the args is "+args+"; the locale is "+locale.getDisplayName());
		return ms.getMessage(code, args, locale);
	}
	
	public static String getMessage(String code,String defaultMessage){
		return getMessage(code,null,defaultMessage);
	}
	
	public static String getMessage(String code,Object[] args,String defaultMessage){
		return getMessage(code,args,defaultMessage,locale);
	}
	
	public static String getMessage(String code,Object[] args,String defaultMessage,Locale locale){
		log.debug("get the " + code + " values; the args is "+args+"; the locale is "+locale.getDisplayName()+" the default value is "+defaultMessage);
		return ms.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(MessageSourceResolvable resolvable){
		return getMessage(resolvable,locale);
	}
	
	public static String getMessage(MessageSourceResolvable resolvable, Locale locale){
		log.debug("the locale is "+locale.getDisplayName()+" resolvable default message is "+resolvable.getDefaultMessage());
		return ms.getMessage(resolvable, locale);
	}
	
}
