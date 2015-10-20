/**  
 * com.appabc.tools.utils.file.impl.DocFileParser.java  
 *   
 * 2015年7月17日 下午7:05:23  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月17日 下午7:05:23
 */

public class DocFileParser extends AbstractFileParser {

	public DocFileParser(){}
	
	public DocFileParser(String fileName){
		this.fileName = fileName.trim();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.IFileParser#readFileAsString(java.io.InputStream)  
	 */
	@Override
	public String readFileAsString(InputStream in) {
		if(in == null){
			return StringUtils.EMPTY;
		}
		StringBuilder str = new StringBuilder();
		try {
			HWPFDocument doc = new HWPFDocument(in);
			WordExtractor word = new WordExtractor(doc);
			// 直接通过getText()获取文本
			String text = word.getText();
			str.append(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return str.toString();
	}

}
