/**  
 * com.appabc.pay.dao.IPrimarKeyDAO.java  
 *   
 * 2014年10月8日 下午5:35:26  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao;

import java.util.List;
import java.util.Map;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;
import com.appabc.pay.bean.TPk;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月8日 下午5:35:26
 */

public interface IPrimaryKeyDAO extends IBaseDao<TPk> {

	void save(TPk tpk);

	void delete(String id);
	
	void update(TPk entity);
	
	TPk query(String id);
	
	List<TPk> queryForList(Map<String, ?> args);
	
	QueryContext<TPk> queryListForPagination(QueryContext<TPk> qContext);
	
}
