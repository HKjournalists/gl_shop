/**  
 * com.appabc.tools.utils.file.impl.AbstractFileParser.java  
 *   
 * 2015年7月16日 下午3:52:27  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import com.appabc.common.utils.LogUtil;
import com.appabc.tools.utils.file.IFileParser;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午3:52:27
 */

public abstract class AbstractFileParser implements IFileParser {
	
	protected LogUtil log = LogUtil.getLogUtil(getClass()); 
	
	protected String fileName;
	
	public static final String FILE_SUFFIX_EMPTY = "empty";
	
	public static final String FILE_SUFFIX_XLS = "xls";
	
	public static final String FILE_SUFFIX_XLSX = "xlsx";
	
	public static final String FILE_SUFFIX_TXT = "txt";
	
	public static final String FILE_SUFFIX_CSV = "csv";
	
	public static final String FILE_SUFFIX_DOC = "doc";
	
	public static final String FILE_SUFFIX_DOCX = "docx";

	/*-----------------------------------
	    笨方法：String s = "你要去除的字符串"; 1.去除空格：s = s.replace('\\s','');2.去除回车：s = s.replace('\n','');
	    这样也可以把空格和回车去掉，其他也可以照这样做。
	    注：\n 回车(\u000a) \t 水平制表符(\u0009) \s 空格(\u0008) \r 换行(\u000d)*/
	protected static String replaceBlank(String str) {
		if(StringUtils.isEmpty(str)){
			return StringUtils.EMPTY;
		}
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#validatePhoneFileContentFormat(java.lang.String)  
	 */
	@Override
	public boolean validatePhoneFileContentFormat(String content) {
		if(StringUtils.isEmpty(content)){
			return false;
		}
		String[] strs = this.splitStrToArrayByRegex(content, COMMA);
		boolean ret = true;
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		for(String str : strs){
			if(StringUtils.isEmpty(str.trim())){
				continue;
			}
			if(!p.matcher(str.trim()).matches()){
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#validateFileContentFormat(java.lang.String, java.util.List)  
	 */
	@Override
	public boolean validateFileContentFormat(String content, List<Pattern> rules) {
		return false;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#readFileAsString(byte[])  
	 */
	@Override
	public String readFileAsString(byte[] bytes) {
		if(bytes == null){
			return StringUtils.EMPTY;
		}
		return new String(bytes,Charsets.UTF_8);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#splitStrToArrayByRegex(java.lang.String, java.lang.String)  
	 */
	@Override
	public String[] splitStrToArrayByRegex(String content, String regex) {
		if(StringUtils.isEmpty(regex) || StringUtils.isEmpty(content)){
			return null;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(content);
        if(m.matches()){
        	content = m.replaceAll("");
        }
		String[] strs = null;
		if(content.indexOf(regex) != -1){
			strs = content.split(regex);
		}else{
			strs = new String[]{content};
		}
		List<String> t = new ArrayList<String>();
		for(String str : strs){
			if(StringUtils.isEmpty(str.trim())){
				continue;
			}
            if(m.matches()){
            	t.add(m.replaceAll(""));
            }else{
            	t.add(str);
            }
		}
		return t.toArray(new String[]{});
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#joinStrArrToStrByRegex(java.lang.String[], java.lang.String)  
	 */
	@Override
	public String joinStrArrToStrByRegex(String[] strs, String regex) {
		if(StringUtils.isEmpty(regex) || strs==null || strs.length <= 0){
			return StringUtils.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for(String str : strs){
			if(StringUtils.isEmpty(str.trim())){
				continue;
			}
			str = replaceBlank(str);
			sb.append(str).append(regex);
		}
		if(sb.length()>0 && sb.lastIndexOf(regex) != -1){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**  
	 * fileName  
	 *  
	 * @return  the fileName  
	 * @since   1.0.0  
	*/  
	
	public String getFileName() {
		return fileName;
	}

	/**  
	 * @param fileName the fileName to set  
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
