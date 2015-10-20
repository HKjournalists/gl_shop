/**  
 * com.appabc.tools.utils.file.impl.XlsFileParser.java  
 *   
 * 2015年7月16日 下午4:36:04  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午4:36:04
 */

public class XlsFileParser extends AbstractFileParser {

	public XlsFileParser(){}

	public XlsFileParser(String fileName){
		this.fileName = fileName;
	}
	
	protected String getHssfCellValue(HSSFCell hssfCell){
		if(hssfCell == null){
			return StringUtils.EMPTY;
		}
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
             return String.valueOf(hssfCell.getBooleanCellValue());
         } else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
             return String.valueOf((int)hssfCell.getNumericCellValue());
         } else {
             return hssfCell.getStringCellValue();
         }
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
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
			 for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				 if(hssfSheet == null){
					 continue;
				 }
				 for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					 if (hssfRow != null) {
						 for(int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++){
							 HSSFCell hssfCell = hssfRow.getCell(cellNum);
							 if(hssfCell != null){
								 hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
								 str.append(getHssfCellValue(hssfCell));
								 str.append(COMMA);
							 }
						 }
					 }
				 }
			 }
			 if(str.length()>0 && str.lastIndexOf(COMMA) != -1){
				str.deleteCharAt(str.length() - 1);
			 }
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
