package com.appabc.common.utils.pagination;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:15:22
 */

public interface ISQLGenerator {
	/**
	 * generate pageSql use the sql and page info
	 * */
	String generatePageSql(String sql,PageModel page);
	
	/**
	 * generate countSql use the sql and page info
	 * SELECT COUNT(1) FROM T_ACCEPT_BANK
	 * */
	String generateCountSql(String sql);
}
