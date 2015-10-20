/**
 *
 */
package com.appabc.tools.service.codes.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.dao.codes.IPublicCodesDao;
import com.appabc.tools.service.codes.IPublicCodesService;
import com.appabc.tools.service.system.ISystemParamsService;

/**
 * @Description : 公共代码SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午2:39:50
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PublicCodesServiceImpl implements IPublicCodesService{
	
	@Autowired
	private IPublicCodesDao publicCodesDao;
	@Autowired
	private ISystemParamsService systemParamsService;
	

	public void add(TPublicCodes entity) {
		this.publicCodesDao.save(entity);
		updateToSystemParamAndCache();
	}

	public void modify(TPublicCodes entity) {
		this.publicCodesDao.update(entity);
		updateToSystemParamAndCache();
	}

	public void delete(TPublicCodes entity) {
		this.publicCodesDao.delete(entity);
	}

	public void delete(Serializable id) {
		this.publicCodesDao.delete(id);
	}

	public TPublicCodes query(TPublicCodes entity) {
		return this.publicCodesDao.query(entity);
	}

	public TPublicCodes query(Serializable id) {
		return this.publicCodesDao.query(id);
	}

	public List<TPublicCodes> queryForList(TPublicCodes entity) {
		return this.publicCodesDao.queryForList(entity);
	}

	public List<TPublicCodes> queryForList(Map<String, ?> args) {
		return this.publicCodesDao.queryForList(args);
	}

	public QueryContext<TPublicCodes> queryListForPagination(
			QueryContext<TPublicCodes> qContext) {
		return this.publicCodesDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)根据CODE查询
	 * @see com.appabc.tools.service.codes.IPublicCodesService#queryListByCode(java.lang.String, java.lang.Integer)
	 */
	public List<TPublicCodes> queryListByCode(String code, Integer ishidden) {
		TPublicCodes entity = new TPublicCodes();
		entity.setCode(code);
		entity.setIshidden(ishidden);
		return this.publicCodesDao.queryForList(entity);
	}
	
	@Override
	public String getMaxValue(TPublicCodes entity){
		return this.publicCodesDao.getMaxValue(entity);
	}
	
	@Override
	public List<TPublicCodes> queryListInNoDel(String code) {
		return publicCodesDao.queryListInNoDel(code);
	}
	
	/**
	 * 更新系统参数配置信息表数据和缓存中的数据
	 */
	private void updateToSystemParamAndCache(){
		systemParamsService.updateValueByName(SystemConstant.SYNC_RIVER_SECTION_TIME, DateUtil.DateToStr(Calendar.getInstance().getTime(), DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
	}

}
