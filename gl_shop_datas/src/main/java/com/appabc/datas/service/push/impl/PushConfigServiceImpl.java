/**
 *
 */
package com.appabc.datas.service.push.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TPushConfig;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.push.IPushConfigDao;
import com.appabc.datas.service.push.IPushConfigService;

/**
 * @Description : 消息推送账号信息配置
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月22日 下午8:06:07
 */
@Service
public class PushConfigServiceImpl implements IPushConfigService {
	
	@Autowired
	private IPushConfigDao pushConfigDao;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void add(TPushConfig entity) {
		pushConfigDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void modify(TPushConfig entity) {
		pushConfigDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	
	public void delete(TPushConfig entity) {
		pushConfigDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	
	public void delete(Serializable id) {
		pushConfigDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	
	public TPushConfig query(TPushConfig entity) {
		return pushConfigDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	
	public TPushConfig query(Serializable id) {
		return pushConfigDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	
	public List<TPushConfig> queryForList(TPushConfig entity) {
		return pushConfigDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	
	public List<TPushConfig> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	
	public QueryContext<TPushConfig> queryListForPagination(
			QueryContext<TPushConfig> qContext) {
		return null;
	}

}
