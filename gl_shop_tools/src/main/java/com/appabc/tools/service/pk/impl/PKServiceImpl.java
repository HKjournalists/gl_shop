package com.appabc.tools.service.pk.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TPk;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.tools.dao.pk.IPKDao;
import com.appabc.tools.service.pk.IPKService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午4:37:55
 */

@Service
public class PKServiceImpl extends BaseService<TPk> implements IPKService {
	
	@Autowired
	private IPKDao pkDao;

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	public void add(TPk entity) {
		pkDao.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	public void modify(TPk entity) {
		pkDao.update(entity);
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
		pkDao.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TPk query(TPk entity) {
		return pkDao.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)  
	 */
	public TPk query(String id) {
		return pkDao.query(id);
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
		return pkDao.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	public List<TPk> queryForList(Map<String, ?> args) {
		return pkDao.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPk> queryListForPagination(QueryContext<TPk> qContext) {
		return pkDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		this.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	public TPk query(Serializable id) {
		return this.query(id);
	}

}
