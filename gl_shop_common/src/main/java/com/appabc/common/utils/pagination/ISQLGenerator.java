package com.appabc.common.utils.pagination;

import java.util.Date;

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
	
	/**
	 * convert the start Date and end Date to where d bewteen d1 and d2;
	 * eg:
	 * mysql  is : UNIX_TIMESTAMP(PAYTIME) BETWEEN UNIX_TIMESTAMP('2015-03-12 15:00:00') AND UNIX_TIMESTAMP('2015-05-13 17:00:00')
	 * oracle is : update between to_date('2007-07-07 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2007-09-07 00:00:00','yyyy-mm-dd hh24:mi:ss') 
	 * 
	 * */
	String convertDateToWhereCauseSql(String dbColumnName,Date start,Date end);
}
