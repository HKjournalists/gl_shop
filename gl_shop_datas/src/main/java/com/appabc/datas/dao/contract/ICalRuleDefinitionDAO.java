package com.appabc.datas.dao.contract;

import com.appabc.bean.pvo.TCalRuleDefinition;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午3:59:15
 */

public interface ICalRuleDefinitionDAO extends IBaseDao<TCalRuleDefinition> {
	
	/**
	 * @description 分页查询规则定义信息
	 * @param qContext
	 * @return QueryContext<TCalRuleDefinition>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<TCalRuleDefinition> queryListForPaginationForSQL(QueryContext<TCalRuleDefinition> qContext);
	
}
