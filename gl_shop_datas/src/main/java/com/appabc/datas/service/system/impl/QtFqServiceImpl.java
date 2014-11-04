/**
 *
 */
package com.appabc.datas.service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TQtFq;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.system.IQtFqDao;
import com.appabc.datas.service.system.IQtFqService;

/**
 * @Description : 意见反馈
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 上午11:33:05
 */
@Service
public class QtFqServiceImpl implements IQtFqService {
	
	@Autowired
	private IQtFqDao qtFqDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TQtFq entity) {
		qtFqDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TQtFq entity) {
		qtFqDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TQtFq entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		qtFqDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TQtFq query(TQtFq entity) {
		return qtFqDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TQtFq query(Serializable id) {
		return qtFqDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TQtFq> queryForList(TQtFq entity) {
		return qtFqDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TQtFq> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TQtFq> queryListForPagination(
			QueryContext<TQtFq> qContext) {
		return null;
	}

}
