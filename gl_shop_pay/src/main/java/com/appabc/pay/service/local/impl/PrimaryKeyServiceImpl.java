/**  
 * com.appabc.pay.service.local.impl.PrimaryKeyServiceImpl.java  
 *   
 * 2014年10月8日 下午5:40:27  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.pay.bean.TPk;
import com.appabc.pay.dao.IPrimaryKeyDAO;
import com.appabc.pay.service.local.IPrimaryKeyService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月8日 下午5:40:27
 */

@Service
public class PrimaryKeyServiceImpl extends BaseService<TPk> implements
		IPrimaryKeyService {

	@Autowired
	private IPrimaryKeyDAO iPrimaryKeyDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TPk entity) {
		iPrimaryKeyDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TPk entity) {
		iPrimaryKeyDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPk entity) {
		this.delete(entity.getId());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)  
	 */
	public void delete(String id) {
		iPrimaryKeyDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TPk query(TPk entity) {
		return iPrimaryKeyDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)  
	 */
	public TPk query(String id) {
		return iPrimaryKeyDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPk> queryForList(TPk entity) {
		Map<String,Object> args  = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(entity.getId())){
			args.put("id", entity.getId());
		}
		if(!StringUtils.isEmpty(entity.getBid())){
			args.put("bid", entity.getId());
		}
		if(!StringUtils.isEmpty(entity.getBprefix())){
			args.put("bprefix", entity.getBprefix());
		}
		if(!StringUtils.isEmpty(entity.getBsuffix())){
			args.put("bsuffix", entity.getBsuffix());
		}
		log.info(args);
		return iPrimaryKeyDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TPk> queryForList(Map<String, ?> args) {
		return iPrimaryKeyDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPk> queryListForPagination(QueryContext<TPk> qContext) {
		return iPrimaryKeyDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		this.delete((String)id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TPk query(Serializable id) {
		return this.query((String)id);
	}

}
