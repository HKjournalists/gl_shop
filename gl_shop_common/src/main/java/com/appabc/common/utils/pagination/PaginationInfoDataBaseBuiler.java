package com.appabc.common.utils.pagination;

import com.appabc.common.utils.SystemConstant;

/**
 * @Description : 不同数据类型对应生成不同的信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月23日 下午2:17:20
 */

public class PaginationInfoDataBaseBuiler {
	
	private static String dialect = SystemConstant.DEFAULT_DIALECT;
	/**  
	 * generateSQLGenerateFactory(不同的数据库对应不同的语法信息生成器)  
	 * @return ISQLGenerator  
	 * @exception   
	 * @since  1.0.0  
	 */
	public static ISQLGenerator generateSQLGenerateFactory() {
		if(dialect.equalsIgnoreCase(SystemConstant.DATABASE_MYSQL)){
			return MySqlGeneratorImpl.getInstance();
		}else if(dialect.equalsIgnoreCase(SystemConstant.DATABASE_ORACLE)){
			return OracleGeneratorImpl.getInstance();
		}else if(dialect.equalsIgnoreCase(SystemConstant.DATABASE_SQLSERVER)){
			return SqlServerGeneratorImpl.getInstance();
		}else{
			return MySqlGeneratorImpl.getInstance();
		}
	}
	
	public void setDialect(String dialectStr){
		dialect = dialectStr;
	}
}
