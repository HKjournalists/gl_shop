/**  
 * com.appabc.pay.service.local.impl.PayThirdRecordServiceImpl.java  
 *   
 * 2015年3月3日 上午10:32:55  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.appabc.bean.enums.PurseInfo.RequestType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.pay.bean.TPayThirdOrgRecord;
import com.appabc.pay.dao.IPayThirdRecordDAO;
import com.appabc.pay.service.local.IPayThirdRecordService;
import com.appabc.tools.utils.ToolsConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午10:32:55
 */

public class PayThirdRecordServiceImpl extends BaseService<TPayThirdOrgRecord> implements
		IPayThirdRecordService {

	private IPayThirdRecordDAO iPayThirdRecordDAO;
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TPayThirdOrgRecord entity) {
		iPayThirdRecordDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TPayThirdOrgRecord entity) {
		iPayThirdRecordDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPayThirdOrgRecord entity) {
		iPayThirdRecordDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iPayThirdRecordDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPayThirdOrgRecord query(TPayThirdOrgRecord entity) {
		return iPayThirdRecordDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TPayThirdOrgRecord query(Serializable id) {
		return iPayThirdRecordDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPayThirdOrgRecord> queryForList(TPayThirdOrgRecord entity) {
		return iPayThirdRecordDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPayThirdOrgRecord> queryForList(Map<String, ?> args) {
		return iPayThirdRecordDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPayThirdOrgRecord> queryListForPagination(
			QueryContext<TPayThirdOrgRecord> qContext) {
		return iPayThirdRecordDAO.queryListForPagination(qContext);
	}

	/**  
	 * iPayThirdRecordDAO  
	 *  
	 * @return  the iPayThirdRecordDAO  
	 * @since   1.0.0  
	*/  
	
	public IPayThirdRecordDAO getiPayThirdRecordDAO() {
		return iPayThirdRecordDAO;
	}

	/**  
	 * @param iPayThirdRecordDAO the iPayThirdRecordDAO to set  
	 */
	public void setiPayThirdRecordDAO(IPayThirdRecordDAO iPayThirdRecordDAO) {
		this.iPayThirdRecordDAO = iPayThirdRecordDAO;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdRecordService#savePayThirdOrgRecord(java.lang.String, java.lang.String, com.appabc.bean.enums.PurseInfo.RequestType)  
	 */
	@Override
	public TPayThirdOrgRecord savePayThirdOrgRecord(String oid,String paramsContent, RequestType rt) {
		if (StringUtils.isEmpty(oid) || StringUtils.isEmpty(paramsContent) || rt == null){
			return null;
		}
		Date now = DateUtil.getNowDate();
		TPayThirdOrgRecord bean = new TPayThirdOrgRecord();
		bean.setOid(oid);
		bean.setParamsContent(paramsContent);
		bean.setType(rt.getVal());
		bean.setTradeTime(now);
		bean.setCreateTime(now);
		bean.setUpdateTime(now);
		bean.setCreator(ToolsConstant.SYSTEMCID);
		this.iPayThirdRecordDAO.save(bean);
		return bean;
	}

}
