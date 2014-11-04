package com.appabc.common.utils.pagination;
/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:31:47
 */

public class OracleGeneratorImpl extends AbstractSQLGenerator implements ISQLGenerator {

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

}
