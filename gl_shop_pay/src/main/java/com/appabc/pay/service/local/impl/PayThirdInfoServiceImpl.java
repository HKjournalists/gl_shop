/**  
 * com.appabc.pay.service.local.impl.PayThirdInfoServiceImpl.java  
 *   
 * 2015年3月3日 上午10:31:05  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayInstitution;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.RequestType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.ObjectUtil;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.dao.IPayThirdInfoDAO;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.local.IPassbookInfoService;
import com.appabc.pay.service.local.IPayThirdInfoService;
import com.appabc.pay.service.local.IPayThirdRecordService;
import com.appabc.pay.util.CodeConstant;
import com.appabc.pay.util.PaySystemConstant;
import com.appabc.pay.util.UPSDKUtil;
import com.appabc.pay.util.UPSDKUtil.responseStatus;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午10:31:05
 */

public class PayThirdInfoServiceImpl extends BaseService<TPayThirdOrgInfo> implements IPayThirdInfoService {

	private PrimaryKeyGenerator PKGenerator;

	private IPayThirdInfoDAO iPayThirdInfoDAO;
	
	private IPassbookInfoService iPassbookInfoService;
	
	private IPayThirdRecordService iPayThirdRecordService;
	
	/**  
	 * iPassbookInfoService  
	 *  
	 * @return  the iPassbookInfoService  
	 * @since   1.0.0  
	*/  
	
	public IPassbookInfoService getiPassbookInfoService() {
		return iPassbookInfoService;
	}

	/**  
	 * @param iPassbookInfoService the iPassbookInfoService to set  
	 */
	public void setiPassbookInfoService(IPassbookInfoService iPassbookInfoService) {
		this.iPassbookInfoService = iPassbookInfoService;
	}
	
	/**  
	 * iPayThirdRecordService  
	 *  
	 * @return  the iPayThirdRecordService  
	 * @since   1.0.0  
	*/  
	
	public IPayThirdRecordService getiPayThirdRecordService() {
		return iPayThirdRecordService;
	}

	/**  
	 * @param iPayThirdRecordService the iPayThirdRecordService to set  
	 */
	public void setiPayThirdRecordService(IPayThirdRecordService iPayThirdRecordService) {
		this.iPayThirdRecordService = iPayThirdRecordService;
	}
	
	/**  
	 * iPayThirdInfoDAO  
	 *  
	 * @return  the iPayThirdInfoDAO  
	 * @since   1.0.0  
	*/  
	
	public IPayThirdInfoDAO getiPayThirdInfoDAO() {
		return iPayThirdInfoDAO;
	}

	/**  
	 * @param iPayThirdInfoDAO the iPayThirdInfoDAO to set  
	 */
	public void setiPayThirdInfoDAO(IPayThirdInfoDAO iPayThirdInfoDAO) {
		this.iPayThirdInfoDAO = iPayThirdInfoDAO;
	}
	
	/**  
	 * pKGenerator  
	 *  
	 * @return  the pKGenerator  
	 * @since   1.0.0  
	*/  
	
	public PrimaryKeyGenerator getPKGenerator() {
		return PKGenerator;
	}

	/**  
	 * @param pKGenerator the pKGenerator to set  
	 */
	public void setPKGenerator(PrimaryKeyGenerator pKGenerator) {
		PKGenerator = pKGenerator;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TPayThirdOrgInfo entity) {
		iPayThirdInfoDAO.save(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TPayThirdOrgInfo entity) {
		iPayThirdInfoDAO.update(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPayThirdOrgInfo entity) {
		iPayThirdInfoDAO.delete(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		iPayThirdInfoDAO.delete(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TPayThirdOrgInfo query(TPayThirdOrgInfo entity) {
		return iPayThirdInfoDAO.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TPayThirdOrgInfo query(Serializable id) {
		return iPayThirdInfoDAO.query(id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForList(TPayThirdOrgInfo entity) {
		return iPayThirdInfoDAO.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForList(Map<String, ?> args) {
		return iPayThirdInfoDAO.queryForList(args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPayThirdOrgInfo> queryListForPagination(
			QueryContext<TPayThirdOrgInfo> qContext) {
		return iPayThirdInfoDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdInfoService#savePayThirdOrgInfo(java.lang.String, double, com.appabc.bean.enums.PurseInfo.PurseType)  
	 */
	@Override
	public TPayThirdOrgInfo savePayThirdOrgInfo(String cid, double balance,PurseType pt) {
		if (StringUtils.isEmpty(cid) || pt == null || balance <= 0){
			return null;
		}
		TPassbookInfo passBookInfo = new TPassbookInfo();
		passBookInfo.setCid(cid);
		passBookInfo.setPasstype(pt);
		passBookInfo = getiPassbookInfoService().query(passBookInfo);
		
		String id = getPKGenerator().getPKey(PaySystemConstant.PURSETHIRDCHECKID);
		Date now = DateUtil.getNowDate();
		
		TPayThirdOrgInfo entity = new TPayThirdOrgInfo();
		entity.setId(id);
		entity.setPassid(passBookInfo.getId());
		entity.setOid(id);
		entity.setOtype(TradeType.DEPOSIT);
		entity.setName(TradeType.DEPOSIT.getText()+PayWay.NETBANK_PAY.getText()+PayDirection.INPUT.getText());
		entity.setAmount(balance);
		entity.setDirection(PayDirection.INPUT);
		entity.setPaytype(PayWay.NETBANK_PAY);
		entity.setPatytime(now);
		entity.setPayno(StringUtils.EMPTY);
		entity.setStatus(TradeStatus.REQUEST);
		entity.setDevices(DeviceType.MOBILE);
		entity.setPayInstitution(PayInstitution.UNIONPAY);
		this.add(entity);
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdInfoService#reportToUnionPayTradeResult()  
	 */
	@Override
	@Deprecated
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=Exception.class)
	public void reportToUnionPayTradeResult(String oid) throws ServiceException {
		if(StringUtils.isEmpty(oid)){
			throw new ServiceException(CodeConstant.PARAMETER_IS_NULL,StringUtils.EMPTY);
		}
		TPayThirdOrgInfo bean = query(oid);
		Map<String, String> params = UPSDKUtil.setUpTradeQueryParams(bean.getOid(),bean.getTnTime());
		Map<String, String> resmap = UPSDKUtil.submitData(params,UPSDKUtil.sdkConfig.getAppRequestUrl());
		if(!ObjectUtil.isEmpty(resmap)){
			iPayThirdRecordService.savePayThirdOrgRecord(bean.getOid(), SDKUtil.coverMap2String(resmap),RequestType.REQUEST);
			bean.setQueryId(resmap.get("queryId"));
			bean.setPayno(resmap.get("traceNo"));
			bean.setRemark(resmap.get("respMsg"));
			if(responseStatus.enumOf(resmap.get("respCode")) == responseStatus.ZEROZERO && responseStatus.enumOf(resmap.get("origRespCode")) == responseStatus.ZEROZERO){
				bean.setStatus(TradeStatus.SUCCESS);
			}else{
				bean.setStatus(TradeStatus.FAILURE);
			}
			modify(bean);
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdInfoService#getUnionPayTnOrderId()  
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=Exception.class)
	public TPayThirdOrgInfo getUnionPayTnOrderId(String cid,double balance,PurseType pt) {
		if(StringUtils.isEmpty(cid) || balance <= 0.0 || pt == null){
			return null;
		}
		TPayThirdOrgInfo bean = this.savePayThirdOrgInfo(cid,balance,pt);
		double m = bean.getAmount()*100;
		DecimalFormat df=new DecimalFormat("#"); 
		Map<String, String> params = UPSDKUtil.setUpTradeRequestParams(bean.getOid(),df.format(m));
		Map<String, String> submitFromData = UPSDKUtil.signData(params);
		Map<String, String> resmap = UPSDKUtil.submitUrl(submitFromData, UPSDKUtil.sdkConfig.getAppRequestUrl());
		getiPayThirdRecordService().savePayThirdOrgRecord(bean.getOid(), SDKUtil.coverMap2String(resmap),RequestType.REQUEST);
		if(!ObjectUtil.isEmpty(resmap) && responseStatus.enumOf(resmap.get("respCode")) == responseStatus.ZEROZERO){
			bean.setTn(resmap.get("tn"));
			bean.setTnTime(resmap.get("txnTime"));
			bean.setRemark(resmap.get("respMsg"));
			modify(bean);
			return bean;
		} else {
			this.delete(bean.getId());
			return this.getUnionPayTnOrderId(cid, balance, pt);
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdInfoService#getUnionPayTradeStatus()  
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=Exception.class)
	public Object getUnionPayTradeStatus(String oid) {
		if(StringUtils.isEmpty(oid)){
			return null;
		}
		TPayThirdOrgInfo bean = query(oid);
		Map<String, String> params = UPSDKUtil.setUpTradeQueryParams(bean.getOid(),bean.getTnTime());
		Map<String, String> resmap = UPSDKUtil.submitData(params,UPSDKUtil.sdkConfig.getAppRequestUrl());
		iPayThirdRecordService.savePayThirdOrgRecord(bean.getOid(), SDKUtil.coverMap2String(resmap),RequestType.REQUEST);
		if(!ObjectUtil.isEmpty(resmap)){
			bean.setQueryId(resmap.get("queryId"));
			bean.setPayno(resmap.get("traceNo"));
			bean.setRemark(resmap.get("respMsg"));
			if(responseStatus.enumOf(resmap.get("respCode")) == responseStatus.ZEROZERO && responseStatus.enumOf(resmap.get("origRespCode")) == responseStatus.ZEROZERO){
				bean.setStatus(TradeStatus.SUCCESS);
			} else {
				bean.setStatus(TradeStatus.FAILURE);
			}
			modify(bean);
		}
		return resmap;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.local.IPayThirdInfoService#queryForListWithStatus(com.appabc.bean.enums.PurseInfo.TradeStatus)  
	 */
	@Override
	public List<TPayThirdOrgInfo> queryForListWithStatus(TradeStatus status) {
		if(status == null){
			return null;
		}
		return iPayThirdInfoDAO.queryForListWithStatus(status);
	}

}
