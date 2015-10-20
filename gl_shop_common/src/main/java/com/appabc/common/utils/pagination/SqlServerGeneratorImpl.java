package com.appabc.common.utils.pagination;

import java.util.Date;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:32:39
 */

public class SqlServerGeneratorImpl extends AbstractSQLGenerator implements ISQLGenerator {

	private SqlServerGeneratorImpl(){}
	
	private static SqlServerGeneratorImpl instance = new SqlServerGeneratorImpl();
	
	public static SqlServerGeneratorImpl getInstance(){
		return instance;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.AbstractSQLGenerator#generateDiffPageSql(java.lang.String, com.appabc.common.utils.pagination.PageModel)  
	 */
	public String generatePageSql(String sql, PageModel page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.ISQLGenerator#convertDateToWhereCauseSql(java.lang.String, java.util.Date, java.util.Date)  
	 */
	@Override
	public String convertDateToWhereCauseSql(String dbColumnName, Date start,
			Date end) {
		// TODO Auto-generated method stub
		return null;
	}

}
