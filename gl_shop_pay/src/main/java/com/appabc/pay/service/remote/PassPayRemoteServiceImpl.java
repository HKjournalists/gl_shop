/**  
 * com.appabc.pay.service.remote.PassPayRemoteServiceImpl.java  
 *   
 * 2014年9月17日 下午8:08:17  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.remote;

import java.util.List;

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
	 * @see com.appabc.pay.service.IPassPayService#initializePurseAccount(java.lang.String, java.lang.String)  
	 */
	public boolean initializePurseAccount(String cid, String type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getPurseAccountInfo(java.lang.String, java.lang.String)  
	 */
	public TPassbookInfo getPurseAccountInfo(String cid, String type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveAcceptBank(com.appabc.pay.bean.TAcceptBank)  
	 */
	public void saveAcceptBank(TAcceptBank tAcceptBank) throws ServiceException {
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getAcceptBankList(java.lang.String, java.lang.String[])  
	 */
	public List<TAcceptBank> getAcceptBankList(String cid, String... args) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOnline(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositAccountOnline(String cid, String type, float balance,
			String payNo) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, java.lang.String)  
	 */
	public boolean depositAccountOffline(String cid, String type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payAccountOffline(java.lang.String, java.lang.String, java.lang.String)  
	 */
	public boolean payAccountOffline(String cid, String type, OInfo oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositThirdOrgRecord(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositThirdOrgRecord(String cid, String type,
			float balance, String payNo) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String)  
	 */
	public List<TPassbookPay> payRecordList(String passId) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String, java.lang.String)  
	 */
	public List<TPassbookPay> payRecordList(String cid, String type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordDetail(java.lang.String)  
	 */
	public TPassbookPay payRecordDetail(String pid) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequest(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public TPassbookDraw extractCashRequest(String cid, String type,
			Float balance, String acceptId) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashAudit(java.lang.String, boolean, java.lang.String, java.lang.String)  
	 */
	public boolean extractCashAudit(String tid, boolean result, String reson,
			String cid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashDeduct(java.lang.String)  
	 */
	public boolean extractCashDeduct(String tid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String, java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(String cid, String type) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(String status) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositToGuaranty(java.lang.String, java.lang.Float)  
	 */
	public boolean depositToGuaranty(String cid, Float balance) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, String type) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToGelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToGelation(String cid, float balance, String oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToUngelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToUngelation(String cid, float balance, String oid) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToDeduct(java.lang.String, java.lang.String, java.lang.Float)  
	 */
	public boolean guarantyToDeduct(String sourPassId, String destPassId,
			Float balance) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	public void saveOfflinePay(TOfflinePay tOfflinePay) throws ServiceException {
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#auditeOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	public boolean auditeOfflinePay(TOfflinePay tOfflinePay) {
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getOfflinePayList(java.lang.String, java.lang.String[])  
	 */
	public List<TOfflinePay> getOfflinePayList(String contractId,
			String... args) {
		return null;
	}

}
