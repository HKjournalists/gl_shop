/**
 *
 */
package com.appabc.datas.service.user.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TUserSetting;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.user.IUserSettingDao;
import com.appabc.datas.service.user.IUserSettingService;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月7日 下午4:52:23
 */
@Service
public class UserSettingServiceImpl extends BaseService<TUserSetting> implements IUserSettingService {
	
	@Autowired
	private IUserSettingDao userSettingDao;
	@Autowired
	private SystemParamsManager spm;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TUserSetting entity) {
		userSettingDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TUserSetting entity) {
		userSettingDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TUserSetting entity) {
		userSettingDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		userSettingDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TUserSetting query(TUserSetting entity) {
		return userSettingDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TUserSetting query(Serializable id) {
		return userSettingDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TUserSetting> queryForList(TUserSetting entity) {
		return userSettingDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TUserSetting> queryForList(Map<String, ?> args) {
		return userSettingDao.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TUserSetting> queryListForPagination(
			QueryContext<TUserSetting> qContext) {
		return userSettingDao.queryListForPagination(qContext);
	}
	
	/* (non-Javadoc)判断未认证用户本次登录是否需要提醒
	 * @see com.appabc.datas.service.user.IUserSettingService#getRemind(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int getRemind(String cid) {
		
		Date now = Calendar.getInstance().getTime();
		
		TUserSetting entity = new TUserSetting();
		entity.setCid(cid);
		entity = this.query(entity);
		
		// 无记录，首次提醒
		if(entity == null) {
			entity = new TUserSetting();
			entity.setAuthremindtime(now);
			entity.setCid(cid);
			this.add(entity);
			
			return 1;
		}
		
		// 如果上次提醒时间为空/上次提醒时间距离现在超过配置的天数，需要再次提醒
		Date art = entity.getAuthremindtime();
		if(art == null || Math.abs(DateUtil.daysBetween(art, now)) >= spm.getInt(SystemConstant.AUTH_REMIND_TIME)) {
			entity.setAuthremindtime(now);
			this.modify(entity);
			return 1;
		}
		
		return 0;
	}

}
