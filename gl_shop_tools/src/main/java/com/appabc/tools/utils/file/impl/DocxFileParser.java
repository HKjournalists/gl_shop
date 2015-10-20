/**  
 * com.appabc.tools.utils.file.impl.DocxFileParser.java  
 *   
 * 2015年7月17日 下午7:05:49  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月17日 下午7:05:49
 */

public class DocxFileParser extends DocFileParser {
	
	public DocxFileParser(){
		super();
	}
	
	public DocxFileParser(String fileName){
		super(fileName);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.impl.DocFileParser#readFileAsString(java.io.InputStream)  
	 */
	@Override
	public String readFileAsString(InputStream in) {
		if(in == null){
			return StringUtils.EMPTY;
		}
		StringBuilder str = new StringBuilder();
		try {
			XWPFDocument doc = new XWPFDocument(in);
			XWPFWordExtractor word = new XWPFWordExtractor(doc);
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
