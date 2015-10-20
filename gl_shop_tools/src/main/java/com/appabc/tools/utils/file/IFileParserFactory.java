/**  
 * com.appabc.tools.utils.file.IFileParserFactory.java  
 *   
 * 2015年7月16日 下午3:32:26  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.appabc.tools.utils.file.impl.AbstractFileParser;
import com.appabc.tools.utils.file.impl.CsvFileParser;
import com.appabc.tools.utils.file.impl.DefaultFileParser;
import com.appabc.tools.utils.file.impl.DocFileParser;
import com.appabc.tools.utils.file.impl.DocxFileParser;
import com.appabc.tools.utils.file.impl.TxtFileParser;
import com.appabc.tools.utils.file.impl.XlsFileParser;
import com.appabc.tools.utils.file.impl.XlsxFileParser;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午3:32:26
 */

public final class IFileParserFactory {
	
	private static Map<String,IFileParser> fileParserCache = new HashMap<String,IFileParser>();
	
	public static IFileParser getFileParser(String fileName){
		IFileParser parser = null;
		if (StringUtils.isEmpty(fileName)) {
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_EMPTY);
			if(parser == null){
				parser = new DefaultFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_EMPTY, parser);
			}
		}
		if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_XLS.toUpperCase())) {
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_XLS);
			if(parser == null){
				parser = new XlsFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_XLS, parser);
			}
		} else if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_XLSX.toUpperCase())) {
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_XLSX);
			if(parser == null){
				parser = new XlsxFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_XLSX, parser);
			}
		} else if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_TXT.toUpperCase())) {
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_TXT);
			if(parser == null){
				parser = new TxtFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_TXT, parser);
			}
		} else if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_CSV.toUpperCase())) {
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_CSV);
			if(parser == null){
				parser = new CsvFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_CSV, parser);
			}
		} else if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_DOC.toUpperCase())){
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_DOC);
			if(parser == null){
				parser = new DocFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_DOC, parser);
			}
		} else if(fileName.toUpperCase().endsWith(AbstractFileParser.FILE_SUFFIX_DOCX.toUpperCase())){
			parser = fileParserCache.get(AbstractFileParser.FILE_SUFFIX_DOCX);
			if(parser == null){
				parser = new DocxFileParser(fileName);
				fileParserCache.put(AbstractFileParser.FILE_SUFFIX_DOCX, parser);
			}
		}
		if(parser == null){
			parser = new DefaultFileParser(fileName);
			fileParserCache.put(AbstractFileParser.FILE_SUFFIX_EMPTY, parser);
		}
		return parser;
	}
	
}
