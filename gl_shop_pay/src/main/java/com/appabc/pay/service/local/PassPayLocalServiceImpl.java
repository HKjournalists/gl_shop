/**  
 * com.appabc.pay.service.local.PassPayServiceImpl.java  
 *   
 * 2014年9月17日 下午8:03:47  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.bean.TPassbookThirdCheck;
import com.appabc.pay.enums.PurseInfo.BusinessType;
import com.appabc.pay.enums.PurseInfo.DeviceType;
import com.appabc.pay.enums.PurseInfo.ExtractStatus;
import com.appabc.pay.enums.PurseInfo.OnOffLine;
import com.appabc.pay.enums.PurseInfo.PayDirection;
import com.appabc.pay.enums.PurseInfo.PayWay;
import com.appabc.pay.enums.PurseInfo.PurseType;
import com.appabc.pay.enums.PurseInfo.TradeStatus;
import com.appabc.pay.enums.PurseInfo.TradeType;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.util.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 下午8:03:47
 */

public class PassPayLocalServiceImpl extends BaseService<BaseBean> implements IPassPayService {
	
	private IPayInfoService iPayInfoService;

	private IPayDetailService iPayDetailService;
	
	private IOfflinePayService iOfflinePayService;
	
	private IAcceptBankService iAcceptBankService;	
	
	private IPassbookPayService iPassbookPayService;
	
	private IPassbookInfoService iPassbookInfoService;
	
	private IPassbookDrawService iPassbookDrawService;
	
	private IPassbookThirdCheckService iPassbookThirdCheckService;

	private PrimaryKeyGenerator PKGenerator;
	
	protected LogUtil log = LogUtil.getLogUtil(getClass());
	
	/**  
	 * iPayInfoService  
	 *  
	 * @return  the iPayInfoService  
	 * @since   1.0.0  
	 */
	
	public IPayInfoService getIPayInfoService() {
		return iPayInfoService;
	}

	/**  
	 * @param iPayInfoService the iPayInfoService to set  
	 */
	public void setIPayInfoService(IPayInfoService iPayInfoService) {
		this.iPayInfoService = iPayInfoService;
	}
	
	/**  
	 * iPayDetailService  
	 *  
	 * @return  the iPayDetailService  
	 * @since   1.0.0  
	 */
	
	public IPayDetailService getIPayDetailService() {
		return iPayDetailService;
	}

	/**  
	 * @param iPayDetailService the iPayDetailService to set  
	 */
	public void setIPayDetailService(IPayDetailService iPayDetailService) {
		this.iPayDetailService = iPayDetailService;
	}

	/**  
	 * iPassbookPayService  
	 *  
	 * @return  the iPassbookPayService  
	 * @since   1.0.0  
	 */
	
	public IPassbookPayService getIPassbookPayService() {
		return iPassbookPayService;
	}

	/**  
	 * @param iPassbookPayService the iPassbookPayService to set  
	 */
	public void setIPassbookPayService(IPassbookPayService iPassbookPayService) {
		this.iPassbookPayService = iPassbookPayService;
	}

	/**  
	 * iPassbookInfoService  
	 *  
	 * @return  the iPassbookInfoService  
	 * @since   1.0.0  
	 */
	
	public IPassbookInfoService getIPassbookInfoService() {
		return iPassbookInfoService;
	}

	/**  
	 * @param iPassbookInfoService the iPassbookInfoService to set  
	 */
	public void setIPassbookInfoService(IPassbookInfoService iPassbookInfoService) {
		this.iPassbookInfoService = iPassbookInfoService;
	}

	/**  
	 * iPassbookDrawService  
	 *  
	 * @return  the iPassbookDrawService  
	 * @since   1.0.0  
	 */
	
	public IPassbookDrawService getIPassbookDrawService() {
		return iPassbookDrawService;
	}

	/**  
	 * @param iPassbookDrawService the iPassbookDrawService to set  
	 */
	public void setIPassbookDrawService(IPassbookDrawService iPassbookDrawService) {
		this.iPassbookDrawService = iPassbookDrawService;
	}

	/**  
	 * iPassbookThirdCheckService  
	 *  
	 * @return  the iPassbookThirdCheckService  
	 * @since   1.0.0  
	 */
	
	public IPassbookThirdCheckService getIPassbookThirdCheckService() {
		return iPassbookThirdCheckService;
	}

	/**  
	 * @param iPassbookThirdCheckService the iPassbookThirdCheckService to set  
	 */
	public void setIPassbookThirdCheckService(
			IPassbookThirdCheckService iPassbookThirdCheckService) {
		this.iPassbookThirdCheckService = iPassbookThirdCheckService;
	}

	/**  
	 * iAcceptBankService  
	 *  
	 * @return  the iAcceptBankService  
	 * @since   1.0.0  
	*/  
	
	public IAcceptBankService getIAcceptBankService() {
		return iAcceptBankService;
	}

	/**  
	 * @param iAcceptBankService the iAcceptBankService to set  
	 */
	public void setIAcceptBankService(IAcceptBankService iAcceptBankService) {
		this.iAcceptBankService = iAcceptBankService;
	}
	
	/**  
	 * iOfflinePayService  
	 *  
	 * @return  the iOfflinePayService  
	 * @since   1.0.0  
	 */
	
	public IOfflinePayService getIOfflinePayService() {
		return iOfflinePayService;
	}

	/**  
	 * @param iOfflinePayService the iOfflinePayService to set  
	 */
	public void setIOfflinePayService(IOfflinePayService iOfflinePayService) {
		this.iOfflinePayService = iOfflinePayService;
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
	 * @see com.appabc.pay.service.IPassPayService#initializePurseAccount(java.lang.String, java.lang.String)  
	 */
	public boolean initializePurseAccount(String cid, String type) {
		if (StringUtils.isEmpty(cid)) {
			return false;
		}
		if(StringUtils.isEmpty(type)){
			TPassbookInfo entity1 = new TPassbookInfo();
			entity1.setId(PKGenerator.generatorBusinessKeyByBid("PASSID"));
			entity1.setCid(cid);
			entity1.setAmount(0f);
			entity1.setPasstype(PurseType.DEPOSIT.getValue());
			entity1.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(entity1);
			
			TPassbookInfo entity = new TPassbookInfo();
			entity.setId(PKGenerator.generatorBusinessKeyByBid("PASSID"));
			entity.setCid(cid);
			entity.setAmount(0f);
			entity.setPasstype(PurseType.GUARANTY.getValue());
			entity.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(entity);
			return true;
		}
		if (StringUtils.equalsIgnoreCase(type, PurseType.GUARANTY.getValue())
				|| StringUtils.equalsIgnoreCase(type,
						PurseType.DEPOSIT.getValue())) {
			TPassbookInfo entity = new TPassbookInfo();
			entity.setId(PKGenerator.generatorBusinessKeyByBid("PASSID"));
			entity.setCid(cid);
			entity.setAmount(0f);
			entity.setPasstype(type);
			entity.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(entity);
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getPurseAccountInfo(java.lang.String, java.lang.String)  
	 */
	public TPassbookInfo getPurseAccountInfo(String cid, String type) {
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(type)){
			return null;
		}
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		return iPassbookInfoService.query(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveAcceptBank(com.appabc.pay.bean.TAcceptBank)  
	 */
	public void saveAcceptBank(TAcceptBank tAcceptBank) throws ServiceException {
		tAcceptBank.setId(PKGenerator.generatorBusinessKeyByBid("ACCEPTBANKID"));
		iAcceptBankService.add(tAcceptBank);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getAcceptBankList(java.lang.String, java.lang.String[])  
	 */
	public List<TAcceptBank> getAcceptBankList(String cid, String... args) {
		TAcceptBank tab = new TAcceptBank();
		if(args != null && args.length > 0){
			if(args.length==1){				
				tab.setBankcard(args[0]);
			}else if(args.length==2){
				tab.setBankcard(args[0]);
				tab.setCarduser(args[1]);
			}else if(args.length==3){
				tab.setBankcard(args[0]);
				tab.setCarduser(args[1]);
				tab.setBankname(args[2]);
			}
		}
		tab.setCid(cid);
		return iAcceptBankService.queryForList(tab);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOnline(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositAccountOnline(String cid, String type, float balance,
			String payNo) {
		if (StringUtils.isEmpty(payNo) || StringUtils.isEmpty(cid)
				|| StringUtils.isEmpty(type) || balance <= 0) {
			return false;
		}
		TPassbookInfo tPurseInfo = new TPassbookInfo();
		tPurseInfo.setCid(cid);
		tPurseInfo.setPasstype(type);
		TPassbookInfo tpbi = iPassbookInfoService.query(tPurseInfo);
		
		String passId = tpbi.getId();
		TPassbookThirdCheck entity = new TPassbookThirdCheck();
		entity.setPayno(payNo);
		entity.setPassid(passId);
		List<TPassbookThirdCheck> result = this.iPassbookThirdCheckService.queryForList(entity);
		if(result!=null && result.size()>0){
			entity = result.get(0);
			entity.setStatus(TradeStatus.SUCCESS.getValue());
			iPassbookThirdCheckService.modify(entity);
		}else{
			entity.setId(PKGenerator.generatorBusinessKeyByBid("PURSETHIRDCHECKID"));
			entity.setPassid(passId);
			entity.setPayno(payNo);
			entity.setOtype(TradeType.DEPOSIT.getValue());
			entity.setPatytime(DateUtil.getNowDate());
			//entity.setName("");
			entity.setAmount(balance);
			entity.setDirection(PayDirection.INPUT.getValue());
			entity.setPaytype(PayWay.NETBANK_PAY.getValue());
			entity.setStatus(TradeStatus.SUCCESS.getValue());
			entity.setDevices(DeviceType.MOBILE.getValue());
			iPassbookThirdCheckService.add(entity);
			//
			TPassbookPay payEntity = new TPassbookPay();
			payEntity.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
			payEntity.setPassid(passId);
			//payEntity.setOid("");
			payEntity.setOtype(TradeType.DEPOSIT.getValue());
			payEntity.setPayno(payNo);
			//payEntity.setName("");
			payEntity.setAmount(balance);
			payEntity.setNeedamount(balance);
			payEntity.setDirection(PayDirection.INPUT.getValue());
			payEntity.setPaytype(PayWay.NETBANK_PAY.getValue());
			payEntity.setPatytime(DateUtil.getNowDate());
			payEntity.setStatus(TradeStatus.SUCCESS.getValue());
			payEntity.setCreatedate(DateUtil.getNowDate());
			payEntity.setCreator(cid);
			payEntity.setDevices(DeviceType.MOBILE.getValue());
			iPassbookPayService.add(payEntity);
			//
			tpbi.setAmount(tpbi.getAmount()+balance);
			iPassbookInfoService.modify(tpbi);
		}
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, java.lang.String)  
	 */
	public boolean depositAccountOffline(String cid, String type) {
		if(StringUtils.isEmpty(type)){
			return false;
		}
		TOfflinePay bean = new TOfflinePay();
		bean.setId(PKGenerator.generatorBusinessKeyByBid("PURSEOFFLINEPAYID"));
		bean.setOtype(type);
		bean.setPtype(OnOffLine.OFFLINE.getValue());
		bean.setCreater(cid);
		bean.setCreattime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST.getValue());
		bean.setBtype(BusinessType.DEPOSIT.getValue());
		bean.setAmount(0f);
		bean.setTotal(0f);
		iOfflinePayService.add(bean);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payAccountOffline(java.lang.String, java.lang.String)  
	 */
	public boolean payAccountOffline(String cid,String type,OInfo oid) {
		if(StringUtils.isEmpty(type) || oid == null){
			return false;
		}
		TOfflinePay bean = new TOfflinePay();
		bean.setId(PKGenerator.generatorBusinessKeyByBid("PURSEOFFLINEPAYID"));
		bean.setOtype(type);
		bean.setPtype(OnOffLine.OFFLINE.getValue());
		bean.setCreater(cid);
		bean.setCreattime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST.getValue());
		bean.setBtype(BusinessType.PAY.getValue());
		bean.setOid(oid.getOid());
		bean.setAmount(oid.getAmount());
		bean.setTotal(oid.getTotal());
		this.iOfflinePayService.add(bean);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositThirdOrgRecord(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositThirdOrgRecord(String cid, String type,
			float balance, String payNo) {
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(payNo) || balance <= 0){
			return false;
		}
		TPassbookInfo tPurseInfo = new TPassbookInfo();
		tPurseInfo.setCid(cid);
		tPurseInfo.setPasstype(type);
		TPassbookInfo tpbi = iPassbookInfoService.query(tPurseInfo);
		String passId = tpbi.getId();
		
		TPassbookThirdCheck entity = new TPassbookThirdCheck();
		entity.setId(PKGenerator.generatorBusinessKeyByBid("PURSETHIRDCHECKID"));
		entity.setPassid(passId);
		entity.setPayno(payNo);
		entity.setOtype(TradeType.DEPOSIT.getValue());
		entity.setPatytime(DateUtil.getNowDate());
		entity.setAmount(balance);
		entity.setDirection(PayDirection.INPUT.getValue());
		entity.setPaytype(PayWay.NETBANK_PAY.getValue());
		entity.setStatus(TradeStatus.SUCCESS.getValue());
		entity.setDevices(DeviceType.MOBILE.getValue());
		iPassbookThirdCheckService.add(entity);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String)  
	 */
	public List<TPassbookPay> payRecordList(String passId) {
		TPassbookPay entity = new TPassbookPay();
		entity.setPassid(passId);
		return iPassbookPayService.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String, java.lang.String)  
	 */
	public List<TPassbookPay> payRecordList(String cid, String type) {
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		TPassbookInfo info = iPassbookInfoService.query(entity);
		return this.payRecordList(info.getId());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordDetail(java.lang.String)  
	 */
	public TPassbookPay payRecordDetail(String pid) {
		return iPassbookPayService.query(pid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequest(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public TPassbookDraw extractCashRequest(String cid, String type,
			Float balance, String acceptId) {
		//String acceptId,Float balance,String cid,String result
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(acceptId) || balance <= 0){
			return null;
		}
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		entity = iPassbookInfoService.query(entity);
		if(entity.getAmount()<balance){
			return null;
		}
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(entity.getId());
		pay.setOtype(TradeType.EXTRACT_CASH_REQUEST.getValue());
		pay.setName(cid);
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.OUTPUT.getValue());
		pay.setPaytype(PayWay.BANK_DEDUCT.getValue());
		pay.setPatytime(DateUtil.getNowDate());
		pay.setStatus(String.valueOf(ExtractStatus.REQUEST.getValue()));
		pay.setCreatedate(DateUtil.getNowDate());
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		TPassbookDraw draw =  new TPassbookDraw();
		draw.setAid(acceptId);
		draw.setStatus(ExtractStatus.REQUEST.getValue());
		draw.setCreatetime(DateUtil.getNowDate());
		draw.setPid(pay.getId());
		draw.setAmount(balance);
		draw.setId(PKGenerator.generatorBusinessKeyByBid("PURSEDRAWID"));
		iPassbookDrawService.add(draw);
		return draw;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashAudit(java.lang.String, boolean, java.lang.String, java.lang.String)  
	 */
	public boolean extractCashAudit(String tid, boolean result, String reson,
			String cid) {
		TPassbookDraw draw = iPassbookDrawService.query(tid);
		TPassbookPay pay = iPassbookPayService.query(draw.getPid());
		if(result){
			draw.setStatus(ExtractStatus.SUCCESS.getValue());
		}else{
			draw.setStatus(ExtractStatus.FAILURE.getValue());
			pay.setOtype(TradeType.EXTRACT_CASH_FAILURE.getValue());
			pay.setStatus(String.valueOf(ExtractStatus.FAILURE.getValue()));
		}
		draw.setDealer(cid);
		draw.setDealtime(DateUtil.getNowDate());
		draw.setDealstatus(reson);
		iPassbookPayService.modify(pay);
		iPassbookDrawService.modify(draw);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashDeduct(java.lang.String)  
	 */
	public boolean extractCashDeduct(String tid) {
		TPassbookDraw draw = iPassbookDrawService.query(tid);
		TPassbookPay pay = iPassbookPayService.query(draw.getPid());
		draw.setStatus(ExtractStatus.DEDUCT.getValue());
		pay.setOtype(TradeType.EXTRACT_CASH_SUCCESS.getValue());
		pay.setStatus(String.valueOf(ExtractStatus.SUCCESS.getValue()));
		iPassbookPayService.modify(pay);
		iPassbookDrawService.modify(draw);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String, java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(String cid, String type) {
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		entity = iPassbookInfoService.query(entity);
		return iPassbookDrawService.getTPassbookDrawByPassId(entity.getId());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(String status) {
		TPassbookDraw entity = new TPassbookDraw();
		entity.setStatus(RandomUtil.str2int(status));
		return iPassbookDrawService.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositToGuaranty(java.lang.String, java.lang.Float)  
	 */
	public boolean depositToGuaranty(String cid, Float balance) {
		if(StringUtils.isEmpty(cid) || balance <= 0){
			return false;
		}
		TPassbookInfo depositAccount = new TPassbookInfo();
		depositAccount.setCid(cid);
		depositAccount.setPasstype(PurseType.DEPOSIT.getValue());
		depositAccount = iPassbookInfoService.query(depositAccount);
		if(depositAccount.getAmount()<balance){
			return false;
		}
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY.getValue());
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		
		guarantyAccount.setAmount(guarantyAccount.getAmount()+balance);
		depositAccount.setAmount(depositAccount.getAmount()-balance);
		iPassbookInfoService.modify(guarantyAccount);
		iPassbookInfoService.modify(depositAccount);
		
		Date now = DateUtil.getNowDate();
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(guarantyAccount.getId());
		pay.setOtype(TradeType.DEPOSIT_GUARANTY.getValue());
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT.getValue());
		pay.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay.setPatytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getValue());
		pay.setCreatedate(now);
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay1.setPassid(depositAccount.getId());
		pay1.setOtype(TradeType.DEPOSIT_GUARANTY.getValue());
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.OUTPUT.getValue());
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay1.setPatytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getValue());
		pay1.setCreatedate(now);
		pay1.setCreator(cid);
		pay1.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, String type) {
		if (StringUtils.isEmpty(sourPassId) || StringUtils.isEmpty(destPassId)
				|| balance < 0){
			return false;
		}
		TPassbookInfo sourAccount = iPassbookInfoService.query(sourPassId);
		TPassbookInfo destAccount = iPassbookInfoService.query(destPassId);
		if(sourAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		sourAccount.setAmount(sourAccount.getAmount()-balance);
		iPassbookInfoService.modify(sourAccount);
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(sourAccount.getId());
		pay.setOtype(TradeType.PAYMENT_FOR_GOODS.getValue());
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT.getValue());
		pay.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay.setPatytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getValue());
		pay.setCreatedate(now);
		pay.setCreator(sourAccount.getCid());
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay1.setPassid(destAccount.getId());
		pay1.setOtype(TradeType.PAYMENT_FOR_GOODS.getValue());
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.OUTPUT.getValue());
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay1.setPatytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getValue());
		pay1.setCreatedate(now);
		pay1.setCreator(destAccount.getCid());
		pay1.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToGelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToGelation(String cid, float balance, String oid) {
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY.getValue());
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		TPassbookInfo destAccount = iPassbookInfoService.query(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG));
		if(guarantyAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		guarantyAccount.setAmount(guarantyAccount.getAmount()-balance);
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setOid(oid);
		pay.setPassid(guarantyAccount.getId());
		pay.setOtype(TradeType.GELATION_GUARANTY.getValue());
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.OUTPUT.getValue());
		pay.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay.setPatytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getValue());
		pay.setCreatedate(now);
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setOid(oid);
		pay1.setPassid(destAccount.getId());
		pay1.setOtype(TradeType.GELATION_GUARANTY.getValue());
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.INPUT.getValue());
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay1.setPatytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getValue());
		pay1.setCreatedate(now);
		pay1.setCreator(destAccount.getCid());
		pay1.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToUngelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToUngelation(String cid, float balance, String oid) {
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY.getValue());
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		TPassbookInfo destAccount = iPassbookInfoService.query(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG));
		if(destAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		guarantyAccount.setAmount(guarantyAccount.getAmount()+balance);
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(guarantyAccount.getId());
		pay.setOtype(TradeType.UNGELATION_GUARANTY.getValue());
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT.getValue());
		pay.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay.setPatytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getValue());
		pay.setCreatedate(now);
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		
		destAccount.setAmount(destAccount.getAmount()-balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay1.setPassid(destAccount.getId());
		pay1.setOtype(TradeType.UNGELATION_GUARANTY.getValue());
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.OUTPUT.getValue());
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay1.setPatytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getValue());
		pay1.setCreatedate(now);
		pay1.setCreator(destAccount.getCid());
		pay1.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay1);
		
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToDeduct(java.lang.String, java.lang.String, java.lang.Float)  
	 */
	public boolean guarantyToDeduct(String sourPassId, String destPassId,
			Float balance) {
		TPassbookInfo sourAccount = iPassbookInfoService.query(sourPassId);
		TPassbookInfo destAccount = iPassbookInfoService.query(destPassId);
		if(sourAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		sourAccount.setAmount(sourAccount.getAmount()-balance);
		iPassbookInfoService.modify(sourAccount);
		TPassbookPay pay = new TPassbookPay();
		pay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay.setPassid(sourAccount.getId());
		pay.setOtype(TradeType.VIOLATION_DEDUCTION.getValue());
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT.getValue());
		pay.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay.setPatytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getValue());
		pay.setCreatedate(now);
		pay.setCreator(sourAccount.getCid());
		pay.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay);
		
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(PKGenerator.generatorBusinessKeyByBid("PURSEPAYID"));
		pay1.setPassid(destAccount.getId());
		pay1.setOtype(TradeType.VIOLATION_DEDUCTION.getValue());
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.OUTPUT.getValue());
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT.getValue());
		pay1.setPatytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getValue());
		pay1.setCreatedate(now);
		pay1.setCreator(destAccount.getCid());
		pay1.setDevices(DeviceType.MOBILE.getValue());
		iPassbookPayService.add(pay1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	public void saveOfflinePay(TOfflinePay tOfflinePay) throws ServiceException {
		tOfflinePay.setId(PKGenerator.generatorBusinessKeyByBid("PURSEOFFLINEPAYID"));//PURSEPAYID
		iOfflinePayService.add(tOfflinePay);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#auditeOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	public boolean auditeOfflinePay(TOfflinePay tOfflinePay) {
		iOfflinePayService.modify(tOfflinePay);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getOfflinePayList(java.lang.String, java.lang.String[])  
	 */
	public List<TOfflinePay> getOfflinePayList(String contractId,
			String... args) {
		TOfflinePay entity = new TOfflinePay();
		entity.setOid(contractId);
		return iOfflinePayService.queryForList(entity);
	}
	
}
