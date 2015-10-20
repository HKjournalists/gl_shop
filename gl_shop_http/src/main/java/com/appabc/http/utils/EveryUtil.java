package com.appabc.http.utils;


import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.utils.LogUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月13日 上午11:48:29
 */

public class EveryUtil {
	
	protected static LogUtil log = LogUtil.getLogUtil(EveryUtil.class);
	
	public static final String URLPATHSEPARATOR = "/";
	
	public static String formatDecimalNumber(float num){
		DecimalFormat df1 = new DecimalFormat("##.##%");//##.##% 百分比格式，后面不足2位的用0补齐  
		String result = df1.format(num);
		return result;
	}
	
	/**
	 * 验证手机号码的格式
	 * */
	public static boolean isPhoneNumber(String no){
		if(StringUtils.isEmpty(no)){
			return false;
		}
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		if(p.matcher(no.trim()).matches()){
			return true;
		}
		return false;
	}
	
}
