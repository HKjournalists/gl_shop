package com.appabc.datas.service.contract.impl;

import com.appabc.bean.pvo.TCalRuleDefinition;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.contract.ICalRuleDefinitionDAO;
import com.appabc.datas.service.contract.ICalRuleDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午6:18:43
 */

@Service
public class CalRuleDefinitionServiceImpl extends ContractBaseService<TCalRuleDefinition> implements
		ICalRuleDefinitionService {

	@Autowired
	private ICalRuleDefinitionDAO iCalRuleDefinitionDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TCalRuleDefinition entity) {
		iCalRuleDefinitionDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TCalRuleDefinition entity) {
		iCalRuleDefinitionDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TCalRuleDefinition entity) {
		iCalRuleDefinitionDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		iCalRuleDefinitionDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TCalRuleDefinition query(TCalRuleDefinition entity) {
		return iCalRuleDefinitionDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TCalRuleDefinition query(Serializable id) {
		return iCalRuleDefinitionDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TCalRuleDefinition> queryForList(TCalRuleDefinition entity) {
		return iCalRuleDefinitionDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TCalRuleDefinition> queryForList(Map<String, ?> args) {
		return iCalRuleDefinitionDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCalRuleDefinition> queryListForPagination(
			QueryContext<TCalRuleDefinition> qContext) {
		return iCalRuleDefinitionDAO.queryListForPagination(qContext);
	}

}
