package com.appabc.common.utils.pagination;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.utils.DateUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:30:59
 */

public class MySqlGeneratorImpl extends AbstractSQLGenerator implements ISQLGenerator {

	private MySqlGeneratorImpl(){}
	
	private static MySqlGeneratorImpl instance = new MySqlGeneratorImpl();
	
	public static MySqlGeneratorImpl getInstance(){
		return instance;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.AbstractSQLGenerator#generateDiffPageSql(java.lang.String, com.appabc.common.utils.pagination.PageModel)  
	 */
	public String generatePageSql(String sql, PageModel page) {
		StringBuffer sb = new StringBuffer(sql);
		int currentPageStartIndex = 0;
		if(page.getPageIndex() > 1){
			currentPageStartIndex = (page.getPageIndex()-1) * page.getPageSize();
		}
		sb.append(" limit " + currentPageStartIndex + "," + page.getPageSize());
		return sb.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.ISQLGenerator#convertDateToWhereCauseSql(java.util.Date, java.util.Date, java.util.Date)  
	 */
	@Override
	public String convertDateToWhereCauseSql(String dbColumnName, Date start, Date end) {
		if(StringUtils.isEmpty(dbColumnName) || start == null || end == null){
			return StringUtils.EMPTY;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" UNIX_TIMESTAMP(");
		sql.append(dbColumnName);
		sql.append(") BETWEEN UNIX_TIMESTAMP('");
		sql.append(DateUtil.DateToStr(start, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		sql.append("') AND UNIX_TIMESTAMP('");
		sql.append(DateUtil.DateToStr(end, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		sql.append("') ");
		return sql.toString();
	}

}
