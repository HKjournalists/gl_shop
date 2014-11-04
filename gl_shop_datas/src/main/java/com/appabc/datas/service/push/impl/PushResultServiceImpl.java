/**
 *
 */
package com.appabc.datas.service.push.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TPushResult;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.push.IPushResultDao;
import com.appabc.datas.service.push.IPushResultService;

/**
 * @Description : 消息推送结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午8:41:15
 */
@Service
public class PushResultServiceImpl implements IPushResultService {
	
	@Autowired
	private IPushResultDao pushResultDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TPushResult entity) {
		pushResultDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TPushResult entity) {
		pushResultDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TPushResult entity) {
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		pushResultDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TPushResult query(TPushResult entity) {
		return pushResultDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TPushResult query(Serializable id) {
		return pushResultDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TPushResult> queryForList(TPushResult entity) {
		return pushResultDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TPushResult> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TPushResult> queryListForPagination(
			QueryContext<TPushResult> qContext) {
		return null;
	}

}
