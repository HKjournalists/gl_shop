/**  
 * com.appabc.tools.service.schedule.impl.ScheduleServiceImpl.java  
 *   
 * 2014年11月3日 下午3:09:31  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.service.schedule.impl;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.tools.bean.ScheduleInfoBean;
import com.appabc.tools.dao.schedule.IScheduleDAO;
import com.appabc.tools.service.schedule.IScheduleService;
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
 * @Create_Date  : 2014年11月3日 下午3:09:31
 */
@Service(value="IScheduleService")
public class ScheduleServiceImpl extends BaseService<ScheduleInfoBean> implements
		IScheduleService {

	@Autowired
	private IScheduleDAO iScheduleDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(ScheduleInfoBean entity) {
		iScheduleDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(ScheduleInfoBean entity) {
		iScheduleDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(ScheduleInfoBean entity) {
		iScheduleDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iScheduleDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public ScheduleInfoBean query(ScheduleInfoBean entity) {
		return iScheduleDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public ScheduleInfoBean query(Serializable id) {
		return iScheduleDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<ScheduleInfoBean> queryForList(ScheduleInfoBean entity) {
		return iScheduleDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<ScheduleInfoBean> queryForList(Map<String, ?> args) {
		return iScheduleDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ScheduleInfoBean> queryListForPagination(
			QueryContext<ScheduleInfoBean> qContext) {
		return iScheduleDAO.queryListForPagination(qContext);
	}

}
