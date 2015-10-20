package com.appabc.datas.service.contract.impl;

import com.appabc.bean.pvo.TCalRuleUse;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.contract.ICalRuleUseDAO;
import com.appabc.datas.service.contract.ICalRuleUseService;
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
 * Create Date  : 2014年9月3日 下午6:12:59
 */

@Service
public class CalRuleUseServiceImpl extends ContractBaseService<TCalRuleUse> implements
		ICalRuleUseService {

	@Autowired
	private ICalRuleUseDAO iCalRuleUseDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TCalRuleUse entity) {
		iCalRuleUseDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TCalRuleUse entity) {
		iCalRuleUseDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TCalRuleUse entity) {
		iCalRuleUseDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		iCalRuleUseDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TCalRuleUse query(TCalRuleUse entity) {
		return iCalRuleUseDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TCalRuleUse query(Serializable id) {
		return iCalRuleUseDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TCalRuleUse> queryForList(TCalRuleUse entity) {
		return iCalRuleUseDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TCalRuleUse> queryForList(Map<String, ?> args) {
		return iCalRuleUseDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TCalRuleUse> queryListForPagination(
			QueryContext<TCalRuleUse> qContext) {
		return iCalRuleUseDAO.queryListForPagination(qContext);
	}

}
