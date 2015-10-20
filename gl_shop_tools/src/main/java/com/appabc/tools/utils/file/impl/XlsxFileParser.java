/**  
 * com.appabc.tools.utils.file.impl.XlsxFileParser.java  
 *   
 * 2015年7月16日 下午4:41:28  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.utils.file.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月16日 下午4:41:28
 */

public class XlsxFileParser extends XlsFileParser {

	public XlsxFileParser(){
		super();
	}
	
	public XlsxFileParser(String fileName){
		super(fileName);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.impl.XlsFileParser#setValue(org.apache.poi.hssf.usermodel.HSSFCell)  
	 */
	protected String getXssfCellValue(XSSFCell xssfCell) {
		if(xssfCell == null){
			return StringUtils.EMPTY;
		}
		if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return xssfCell.getStringCellValue();
		}
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.utils.file.impl.XlsFileParser#readFileAsString(java.io.InputStream)  
	 */
	@Override
	public String readFileAsString(InputStream in) {
		if(in == null){
			return StringUtils.EMPTY;
		}
		StringBuilder str = new StringBuilder();
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
							XSSFCell xssfCell = xssfRow.getCell(cellNum);
							if (xssfCell != null) {
								xssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
								str.append(getXssfCellValue(xssfCell));
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
