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
 * Create Date  : 2014年8月23日 下午2:31:47
 */

public class OracleGeneratorImpl extends AbstractSQLGenerator implements ISQLGenerator {

	private OracleGeneratorImpl(){}
	
	private static OracleGeneratorImpl instance = new OracleGeneratorImpl();
	
	public static OracleGeneratorImpl getInstance(){
		return instance;
	}
	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.AbstractSQLGenerator#generateDiffPageSql(java.lang.String, com.appabc.common.utils.pagination.PageModel)  
	 */
	public String generatePageSql(String sql, PageModel page) {
		StringBuffer pageSql = new StringBuffer();
		int currentPageStartIndex = 0;
		if(page.getPageIndex()<=1){
			currentPageStartIndex = 0 * page.getPageSize();
		}else{			
			currentPageStartIndex = (page.getPageIndex()-1) * page.getPageSize();
		}
		pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
        pageSql.append(sql);
        pageSql.append(")  tmp_tb where ROWNUM < ");
        pageSql.append(currentPageStartIndex + page.getPageSize());
        pageSql.append(") where row_id >= ");
        pageSql.append(currentPageStartIndex);
		return pageSql.toString();
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.ISQLGenerator#convertDateToWhereCauseSql(java.lang.String, java.util.Date, java.util.Date)  
	 */
	@Override
	public String convertDateToWhereCauseSql(String dbColumnName, Date start,Date end) {
		if(StringUtils.isEmpty(dbColumnName) || start == null || end == null){
			return StringUtils.EMPTY;
		}
		StringBuilder sql = new StringBuilder();
		//sql.append(" update between to_date('2007-07-07 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2007-09-07 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
		sql.append(dbColumnName);
		sql.append(" BETWEEN to_date('");
		sql.append(DateUtil.DateToStr(start, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		sql.append("','yyyy-mm-dd hh24:mi:ss') ");
		sql.append(" AND to_date('");
		sql.append(DateUtil.DateToStr(end, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
		sql.append("','yyyy-mm-dd hh24:mi:ss') ");
		return sql.toString();
	}

}
