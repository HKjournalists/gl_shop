/**  
 * com.appabc.tools.utils.file.IFileParser.java  
 *   
 * 2015年7月16日 下午3:31:44  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午3:31:44
 */

public interface IFileParser {
	
	final String COMMA = ",";
	
	boolean validatePhoneFileContentFormat(String content);
	
	boolean validateFileContentFormat(String content,List<Pattern> rules);
	
	String readFileAsString(InputStream in);
	
	String readFileAsString(byte[] bytes);
	
	String[] splitStrToArrayByRegex(String content,String regex);
	
	String joinStrArrToStrByRegex(String[] strs,String regex);

}
