package com.appabc.common.utils.pagination;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午3:03:43
 */

public abstract class AbstractSQLGenerator implements ISQLGenerator {

	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.ISQLGenerator#generateCountSql(java.lang.String)  
	 */
	public String generateCountSql(String sql) {
		StringBuffer countSql = new StringBuffer();
		countSql.append(" select count(0) from ( ");
		countSql.append(sql);
		countSql.append(" ) myCount");
		return countSql.toString();
	}
	
}
