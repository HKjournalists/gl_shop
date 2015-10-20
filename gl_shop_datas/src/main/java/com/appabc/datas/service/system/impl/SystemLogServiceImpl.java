package com.appabc.datas.service.system.impl;

import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.bean.pvo.TSystemLog;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.ISystemLogDAO;
import com.appabc.datas.service.system.ISystemLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月22日 上午11:01:26
 */

@Service(value = "ISystemLogService")
public class SystemLogServiceImpl extends BaseService<TSystemLog> implements
		ISystemLogService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<TSystemLog> logList = new ArrayList<TSystemLog>(100); // 初始化100;

	@Autowired
	private ISystemLogDAO iSystemLogDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public void add(TSystemLog entity) {
		entity.setCreatetime(Calendar.getInstance().getTime());
		iSystemLogDAO.save(entity);
	}
	
	@Override
	public void addToCache(TSystemLog entity){
		entity.setCreatetime(Calendar.getInstance().getTime());
		logList.add(entity);
		if(logList.size() >= 100){
			saveTheLogsInTheCache();
		}
	}
	
	/* (non-Javadoc)保存缓存中的日志
	 * @see com.appabc.datas.service.system.ISystemLogService#saveTheLogsInTheCache()
	 */
	@Override
	public void saveTheLogsInTheCache(){
		logger.info("System Logs Size is : "+logList.size());
		for (TSystemLog log : logList) {
			try {
				iSystemLogDAO.save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		logList.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void modify(TSystemLog entity) {
		iSystemLogDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	@Override
	public void delete(TSystemLog entity) {
		iSystemLogDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iSystemLogDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
	public TSystemLog query(TSystemLog entity) {
		return iSystemLogDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TSystemLog query(Serializable id) {
		return iSystemLogDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	@Override
	public List<TSystemLog> queryForList(TSystemLog entity) {
		return iSystemLogDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TSystemLog> queryForList(Map<String, ?> args) {
		return iSystemLogDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TSystemLog> queryListForPagination(
			QueryContext<TSystemLog> qContext) {
		return iSystemLogDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.ISystemLogService#queryCountLoginUserOfDate(java.util.Date)
	 */
	@Override
	public int queryCountLoginUserOfDate(Date date) {
		if(date == null) return 0;
		return this.iSystemLogDAO.queryCountLoginUserOfDate(date);
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.ISystemLogService#queryLastLoginTimeByUser(java.lang.String)
	 */
	@Override
	public Date queryLastLoginTimeByUser(String username) {
		if(StringUtils.isNotEmpty(username)){
			TSystemLog slog = new TSystemLog();
			slog.setBusinessid(username);
			slog.setBusinesstype(LogBusinessType.BUSINESS_TYPE_USER_LOGIN);
			slog.setLoglevel(LogLevel.LOG_LEVEL_INFO);
			
			slog = queryRecentOfOneRecord(slog);
			if(slog != null){
				return slog.getCreatetime();
			}else{
				logger.warn("user login Record is null, username is " + username);
			}
		}else{
			logger.warn("username is null");
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.ISystemLogService#queryRecentOfOneRecord(com.appabc.bean.pvo.TSystemLog)
	 */
	@Override
	public TSystemLog queryRecentOfOneRecord(TSystemLog entity) {
		return this.iSystemLogDAO.queryRecentOfOneRecord(entity);
	}
	
}
