package com.appabc.datas.service.system.impl;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.IMobileAppVersionDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.system.IMobileAppVersionService;
import com.appabc.datas.tool.ServiceErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月22日 上午11:04:36
 */

@Service(value="IMobileAppVersionService")
public class MobileAppVersionServiceImpl extends BaseService<TMobileAppVersion> implements
		IMobileAppVersionService {

	@Autowired
	private IMobileAppVersionDAO iMobileAppVersionDAO;

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void add(TMobileAppVersion entity) {
		iMobileAppVersionDAO.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void modify(TMobileAppVersion entity) {
		iMobileAppVersionDAO.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TMobileAppVersion entity) {
		iMobileAppVersionDAO.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		iMobileAppVersionDAO.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TMobileAppVersion query(TMobileAppVersion entity) {
		return iMobileAppVersionDAO.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
	public TMobileAppVersion query(Serializable id) {
		return iMobileAppVersionDAO.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TMobileAppVersion> queryForList(TMobileAppVersion entity) {
		return iMobileAppVersionDAO.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
	public List<TMobileAppVersion> queryForList(Map<String, ?> args) {
		return iMobileAppVersionDAO.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TMobileAppVersion> queryListForPagination(
			QueryContext<TMobileAppVersion> qContext) {
		return iMobileAppVersionDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.IMobileAppVersionService#getMobileAppUpdateInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public TMobileAppVersion getMobileAppUpdateInfo(String devices,
			String lastNo) throws ServiceException {
		if(StringUtils.isEmpty(devices) || StringUtils.isEmpty(lastNo)){
			throw new ServiceException(ServiceErrorCode.RESULT_ERROR_CODE,"The parameters is not allow null.");
		}
		DeviceType dType = DeviceType.enumOf(devices);
		if(dType == null){
			throw new ServiceException(ServiceErrorCode.RESULT_ERROR_CODE,"the Device type is not match.");
		}
		return iMobileAppVersionDAO.queryMobileAppUpdateInfo(dType, lastNo);
	}

}
