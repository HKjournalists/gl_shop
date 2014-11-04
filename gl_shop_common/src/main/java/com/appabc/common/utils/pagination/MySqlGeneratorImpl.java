package com.appabc.common.utils.pagination;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:30:59
 */

public class MySqlGeneratorImpl extends AbstractSQLGenerator implements ISQLGenerator {

	/* (non-Javadoc)  
	 * @see com.appabc.common.utils.pagination.AbstractSQLGenerator#generateDiffPageSql(java.lang.String, com.appabc.common.utils.pagination.PageModel)  
	 */
	public String generatePageSql(String sql, PageModel page) {
		StringBuffer sb = new StringBuffer(sql);
		int currentPageStartIndex = 0;
		if(page.getPageIndex()<=1){
			currentPageStartIndex = 0 * page.getPageSize();
		}else{			
			currentPageStartIndex = (page.getPageIndex()-1) * page.getPageSize();
		}
		sb.append(" limit " + currentPageStartIndex + "," + page.getPageSize());
		return sb.toString();
	}

}
