package com.appabc.tools.dao.pk;

import com.appabc.bean.pvo.TPk;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午4:50:29
 */

public interface IPKDao extends IBaseDao<TPk> {
	
	void save(TPk tpk);

	void delete(String id);
	
	void update(TPk entity);
	
	TPk query(String id);
	
	List<TPk> queryForList(Map<String, ?> args);
	
	QueryContext<TPk> queryListForPagination(QueryContext<TPk> qContext);
	
}
