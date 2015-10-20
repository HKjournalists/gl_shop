/**
 *
 */
package com.appabc.tools.service.system.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TSystemParams;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.dao.system.ISystemParamsDao;
import com.appabc.tools.service.system.ISystemParamsService;

/**
 * @Description : 系统参数SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月3日 上午10:02:05
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SystemParamsServiceImpl implements ISystemParamsService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private final String PREFIX = SystemConstant.CACHE_PARAM_PROFIX;
	
	@Autowired
	private ISystemParamsDao systemParamsDao;
	
	@Autowired
	private RedisHelper redisHelper;
	
	public void add(TSystemParams entity) {
		this.systemParamsDao.save(entity);
		this.redisHelper.set(PREFIX + entity.getPname(), entity.getPvalue());
		
		logger.info("系统参数新增：panme:"+PREFIX + entity.getPname()+"\tpvalue:" + entity.getPvalue());
	}

	public void modify(TSystemParams entity) {
		this.systemParamsDao.update(entity);
		this.redisHelper.set(PREFIX + entity.getPname(), entity.getPvalue());
		
		logger.info("系统参数更新：panme:"+PREFIX + entity.getPname()+"\tpvalue:" + entity.getPvalue());
	}

	public void delete(TSystemParams entity) {
	}

	public void delete(Serializable id) {
		TSystemParams entity = this.systemParamsDao.query(id);
		this.systemParamsDao.delete(id);
		this.redisHelper.del(PREFIX + entity.getPname());
		
		logger.info("系统参数删除：panme:"+PREFIX + entity.getPname()+"\tpvalue:" + entity.getPvalue());
	}

	public TSystemParams query(TSystemParams entity) {
		return this.systemParamsDao.query(entity);
	}

	public TSystemParams query(Serializable id) {
		return this.systemParamsDao.query(id);
	}

	public List<TSystemParams> queryForList(TSystemParams entity) {
		return this.systemParamsDao.queryForList(entity);
	}

	public List<TSystemParams> queryForList(Map<String, ?> args) {
		return this.systemParamsDao.queryForList(args);
	}

	public QueryContext<TSystemParams> queryListForPagination(
			QueryContext<TSystemParams> qContext) {
		return this.systemParamsDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.tools.service.system.ISystemParamsService#initDataParameters()  
	 */
	@Override
	public void initDataParameters() {
		systemParamsDao.initDataParameters();
	}
	
	@Override
	public void updateValueByName(String pname, String value) {
		
		TSystemParams entity = new TSystemParams();
		entity.setPname(pname);
		entity = this.systemParamsDao.query(entity);
		
		if(entity != null){
			entity.setUpdater("system");
			entity.setUpdatetime(Calendar.getInstance().getTime());
			entity.setPvalue(value);
			modify(entity);
		}else{
			logger.warn("修改的系统参数不存在，表T_SYSTEM_PARAMS, pname=" + pname);
		}
		
	}

}
