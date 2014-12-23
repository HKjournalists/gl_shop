/**  
 * com.appabc.pay.service.remote.PassPayRemoteServiceImpl.java  
 *   
 * 2014年9月17日 下午8:08:17  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.remote;

import java.util.List;

import com.appabc.bean.enums.PurseInfo.ExtractStatus;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.LogUtil;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 下午8:08:17
 */

public class PassPayRemoteServiceImpl implements IPassPayService {

	protected LogUtil log = LogUtil.getLogUtil(getClass());

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#initializePurseAccount(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public boolean initializePurseAccount(String cid, PurseType type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getPurseAccountInfo(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public TPassbookInfo getPurseAccountInfo(String cid, PurseType type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveAcceptBank(com.appabc.pay.bean.TAcceptBank)  
	 */
	@Override
	public void saveAcceptBank(TAcceptBank tAcceptBank) throws ServiceException {
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getAcceptBankList(java.lang.String, java.lang.String[])  
	 */
	@Override
	public List<TAcceptBank> getAcceptBankList(String cid, String... args) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOnline(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	@Override
	public boolean depositAccountOnline(String cid, PurseType type,
			float balance, String payNo) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public boolean depositAccountOfflineRequest(String cid, PurseType type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payAccountOffline(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType, com.appabc.pay.bean.OInfo)  
	 */
	@Override
	public boolean payAccountOfflineRequest(String cid, PurseType type, OInfo oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositThirdOrgRecord(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	@Override
	public boolean depositThirdOrgRecord(String cid, PurseType type,
			float balance, String payNo) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String)  
	 */
	@Override
	public List<TPassbookPay> payRecordList(String passId) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public List<TPassbookPay> payRecordList(String cid, PurseType type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordDetail(java.lang.String)  
	 */
	@Override
	public TPassbookPay payRecordDetail(String pid) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequest(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType, java.lang.Float, java.lang.String)  
	 */
	@Override
	public TPassbookDraw extractCashRequest(String cid, PurseType type,
			Float balance, String acceptId) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashAudit(java.lang.String, boolean, java.lang.String, java.lang.String)  
	 */
	@Override
	public boolean extractCashAudit(String tid, boolean result, String reson,
			String cid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashDeduct(java.lang.String)  
	 */
	@Override
	public boolean extractCashDeduct(String tid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public List<TPassbookDraw> extractCashRequestList(String cid, PurseType type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(com.appabc.pay.enums.PurseInfo.ExtractStatus)  
	 */
	@Override
	public List<TPassbookDraw> extractCashRequestList(ExtractStatus status) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositToGuaranty(java.lang.String, java.lang.Float)  
	 */
	@Override
	public boolean depositToGuaranty(String cid, Float balance) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, com.appabc.pay.enums.PurseInfo.PurseType)  
	 */
	@Override
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, PurseType type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToGelation(java.lang.String, float, java.lang.String)  
	 */
	@Override
	public boolean guarantyToGelation(String cid, float balance, String oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToUngelation(java.lang.String, float, java.lang.String)  
	 */
	@Override
	public boolean guarantyToUngelation(String cid, float balance, String oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToDeduct(java.lang.String, java.lang.String, java.lang.Float)  
	 */
	@Override
	public boolean guarantyToDeduct(String sourPassId, String destPassId,
			Float balance) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	@Override
	public void saveOfflinePay(TOfflinePay tOfflinePay) throws ServiceException {
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#auditeOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	@Override
	public boolean auditeOfflinePay(TOfflinePay tOfflinePay) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getOfflinePayList(java.lang.String, java.lang.String[])  
	 */
	@Override
	public List<TOfflinePay> getOfflinePayList(String contractId,
			String... args) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getGuarantyTotal(java.lang.String)  
	 */
	@Override
	public float getGuarantyTotal(String cid) {
		return 0;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	@Override
	public List<TPassbookPay> payRecordList(String cid, PurseType type,
			PayDirection payDirection) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, com.appabc.bean.enums.PurseInfo.PurseType, com.appabc.bean.enums.PurseInfo.TradeType)  
	 */
	@Override
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, TradeType tradeType) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getAcceptBankList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TAcceptBank> getAcceptBankList(
			QueryContext<TAcceptBank> qContext) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookPay> payRecordList(
			QueryContext<TPassbookPay> qContext) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookDraw> extractCashRequestList(
			QueryContext<TPassbookDraw> qContext) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getOfflinePayList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOfflinePay> getOfflinePayList(
			QueryContext<TOfflinePay> qContext) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	@Override
	public boolean depositAccountOffline(String cid, PurseType type,
			float balance, String payNo) {
		return false;
	}


}
