/**  
 * com.appabc.pay.service.local.PassPayServiceImpl.java  
 *   
 * 2014年9月17日 下午8:03:47  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;


import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.BusinessType;
import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.ExtractStatus;
import com.appabc.bean.enums.PurseInfo.OnOffLine;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.bean.TPassbookThirdCheck;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.util.PaySystemConstant;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.bean.SMSTemplate;
import com.appabc.tools.utils.GuarantStatusCheck;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

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
	
	private IPayAcceptBankService iAcceptBankService;	
	
	private IPassbookPayService iPassbookPayService;
	
	private IPassbookInfoService iPassbookInfoService;
	
	private IPassbookDrawService iPassbookDrawService;
	
	private IPassbookThirdCheckService iPassbookThirdCheckService;

	private PrimaryKeyGenerator PKGenerator;
	
	private MessageSendManager mesgSender;
	
	private GuarantStatusCheck gsCheck;
	
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
	
	public IPayAcceptBankService getIAcceptBankService() {
		return iAcceptBankService;
	}

	/**  
	 * @param iAcceptBankService the iAcceptBankService to set  
	 */
	public void setIAcceptBankService(IPayAcceptBankService iAcceptBankService) {
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
	
	/**  
	 * mesgSender  
	 *  
	 * @return  the mesgSender  
	 * @since   1.0.0  
	*/  
	
	public MessageSendManager getMesgSender() {
		return mesgSender;
	}

	/**  
	 * @param mesgSender the mesgSender to set  
	 */
	public void setMesgSender(MessageSendManager mesgSender) {
		this.mesgSender = mesgSender;
	}

	/**  
	 * gsCheck  
	 *  
	 * @return  the gsCheck  
	 * @since   1.0.0  
	*/  
	
	public GuarantStatusCheck getGsCheck() {
		return gsCheck;
	}

	/**  
	 * @param gsCheck the gsCheck to set  
	 */
	public void setGsCheck(GuarantStatusCheck gsCheck) {
		this.gsCheck = gsCheck;
	}
	
	private String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return PKGenerator.getPKey(bid);
	}
	
	@SuppressWarnings("unused")
	private void checkGuarantEnoughPushMessage(MessageInfoBean mi,String cid,float guarantyBalance){
		if(mi == null || StringUtils.isEmpty(cid) || guarantyBalance <= 0){
			return;
		}
		mi.setSendPushMsg(true);
		float shouldGuarantNum = gsCheck.getGuarantStatus(cid);
		float totalGuarantyUsed = this.getGuarantyTotal(cid);
		boolean isGuarantyEnough = shouldGuarantNum != 0 && totalGuarantyUsed != 0 && shouldGuarantNum <= totalGuarantyUsed;
		mi.addParam("isGuarantyEnough", isGuarantyEnough);
		mi.addParam("balance", guarantyBalance);
	}
	
	public void checkAccountSetParamsToPushMessage(MessageInfoBean mi,String cid,PurseType type,boolean isPushMesg){
		if(mi == null || StringUtils.isEmpty(cid) || isPushMesg == false){
			return;
		}
		mi.setSendPushMsg(isPushMesg);
		if(type == null){
			float shouldGuarantNum = gsCheck.getGuarantStatus(cid);
			float totalGuarantyUsed = this.getGuarantyTotal(cid);
			boolean isGuarantyEnough = shouldGuarantNum != 0 && totalGuarantyUsed != 0 && shouldGuarantNum <= totalGuarantyUsed;
			mi.addParam("isGuarantyEnough", isGuarantyEnough);
			//if the account is deposit, so to do something
			TPassbookInfo info = this.getPurseAccountInfo(cid, PurseType.GUARANTY);
			mi.addParam("guarantybalance", info.getAmount());
			TPassbookInfo info1 = this.getPurseAccountInfo(cid, PurseType.DEPOSIT);
			mi.addParam("depositbalance", info1.getAmount());
		}
		//if the account is guaranty, so to do something
		if(type == PurseType.GUARANTY){
			float shouldGuarantNum = gsCheck.getGuarantStatus(cid);
			float totalGuarantyUsed = this.getGuarantyTotal(cid);
			boolean isGuarantyEnough = shouldGuarantNum != 0 && totalGuarantyUsed != 0 && shouldGuarantNum <= totalGuarantyUsed;
			mi.addParam("isGuarantyEnough", isGuarantyEnough);
			//if the account is deposit, so to do something
			TPassbookInfo info = this.getPurseAccountInfo(cid, type);
			if(info != null){
				mi.addParam("balance", info.getAmount());
			}
		}else if(type == PurseType.DEPOSIT){
			//mi.addParam("balance", info.getAmount());
			TPassbookInfo info = this.getPurseAccountInfo(cid, type);
			if(info != null){
				mi.addParam("balance", info.getAmount());
			}
		}
	}
	
	/*private void savePursePayOutInputLine(String sourId,String destId,String sourCid,String destCid,float sourBalance,float destBalance,String oid,String status,TradeType t,PayWay p,float amount){
		TPassbookPay input = this.savePursePay(sourId, sourCid, StringUtils.EMPTY, oid, StringUtils.EMPTY, amount, sourBalance, PayDirection.OUTPUT, t, p, status, StringUtils.EMPTY);
		this.savePursePay(destId, destCid, StringUtils.EMPTY, oid, StringUtils.EMPTY, amount, destBalance, PayDirection.INPUT, t, p, status, input.getId());
	}
	
	private TPassbookPay savePursePay(String passId,String cid,String payNo,String oid,String name,float amount,float balance,PayDirection pDirection,TradeType tType,PayWay pWay,String status){
		return savePursePay(passId, cid, payNo, oid, name, amount, balance, pDirection, tType, pWay, DeviceType.MOBILE, status, StringUtils.EMPTY, StringUtils.EMPTY);
	}
	
	private TPassbookPay savePursePay(String passId,String cid,String payNo,String oid,String name,float amount,float balance,PayDirection pDirection,TradeType tType,PayWay pWay,String status,String parentId){
		return savePursePay(passId, cid, payNo, oid, name, amount, balance, pDirection, tType, pWay, DeviceType.MOBILE, status, StringUtils.EMPTY, parentId);
	}
	
	private TPassbookPay savePursePay(String passId,String cid,String payNo,String oid,String name,float amount,float balance,PayDirection pDirection,TradeType tType,PayWay pWay,DeviceType dType,String status,String remark,String parentId){
		Date now = DateUtil.getNowDate();
		TPassbookPay entity = new TPassbookPay();
		entity.setId(getKey(PaySystemConstant.PURSEPAYID));
		entity.setPassid(passId);
		entity.setOid(oid);
		entity.setOtype(tType);
		entity.setPayno(payNo);
		entity.setName(name);
		entity.setAmount(amount);
		entity.setNeedamount(amount);
		entity.setDirection(pDirection);
		entity.setPaytype(pWay);
		entity.setPaytime(now);
		entity.setStatus(status);
		entity.setCreatedate(now);
		entity.setCreator(cid);
		entity.setDevices(dType);
		entity.setRemark(remark);
		entity.setPpid(parentId);
		entity.setBalance(balance);
		iPassbookPayService.add(entity);
		return entity;
	}*/
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#initializePurseAccount(java.lang.String, java.lang.String)  
	 */
	public boolean initializePurseAccount(String cid, PurseType type) {
		if (StringUtils.isEmpty(cid)) {
			return false;
		}
		if(type == null){
			TPassbookInfo depositInfo = new TPassbookInfo();
			depositInfo.setId(getKey(PaySystemConstant.PASSBOOKINFOBID));
			depositInfo.setCid(cid);
			depositInfo.setAmount(0f);
			depositInfo.setPasstype(PurseType.DEPOSIT);
			depositInfo.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(depositInfo);
			
			TPassbookInfo guarantyInfo = new TPassbookInfo();
			guarantyInfo.setId(getKey(PaySystemConstant.PASSBOOKINFOBID));
			guarantyInfo.setCid(cid);
			guarantyInfo.setAmount(0f);
			guarantyInfo.setPasstype(PurseType.GUARANTY);
			guarantyInfo.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(guarantyInfo);
			return true;
		}else{
			TPassbookInfo entity = new TPassbookInfo();
			entity.setId(getKey(PaySystemConstant.PASSBOOKINFOBID));
			entity.setCid(cid);
			entity.setAmount(0f);
			entity.setPasstype(type);
			entity.setCreatetime(DateUtil.getNowDate());
			iPassbookInfoService.add(entity);
			return true;
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getPurseAccountInfo(java.lang.String, java.lang.String)  
	 */
	public TPassbookInfo getPurseAccountInfo(String cid, PurseType type) {
		if(StringUtils.isEmpty(cid) || type == null){
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
		tAcceptBank.setId(getKey(PaySystemConstant.ACCEPTBANKID));
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
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOnline(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	@Override
	public boolean depositAccountOnline(String cid, PurseType type,
			float balance, String payNo) {
		return this.depositAccountOnline(cid, type, balance, payNo, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOnline(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositAccountOnline(String cid, PurseType type, float balance,
			String payNo,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(payNo) || StringUtils.isEmpty(cid)
				|| type == null || balance <= 0) {
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
			entity.setStatus(TradeStatus.SUCCESS);
			iPassbookThirdCheckService.modify(entity);
		}else{
			entity.setId(getKey(PaySystemConstant.PURSETHIRDCHECKID));
			entity.setPassid(passId);
			entity.setPayno(payNo);
			entity.setOtype(TradeType.DEPOSIT);
			entity.setPatytime(DateUtil.getNowDate());
			//entity.setName("");
			entity.setAmount(balance);
			entity.setDirection(PayDirection.INPUT);
			entity.setPaytype(PayWay.NETBANK_PAY);
			entity.setStatus(TradeStatus.SUCCESS);
			entity.setDevices(DeviceType.MOBILE);
			iPassbookThirdCheckService.add(entity);
			//save the Passbook Info
			tpbi.setAmount(tpbi.getAmount()+balance);
			iPassbookInfoService.modify(tpbi);
			//save the pay
			//savePursePay(passId, cid, payNo, StringUtils.EMPTY, StringUtils.EMPTY, balance, tpbi.getAmount(), PayDirection.INPUT, TradeType.DEPOSIT, PayWay.NETBANK_PAY, TradeStatus.SUCCESS.getVal());
			TPassbookPay payEntity = new TPassbookPay();
			payEntity.setId(getKey(PaySystemConstant.PURSEPAYID));
			payEntity.setPassid(passId);
			//payEntity.setOid("");
			payEntity.setOtype(TradeType.DEPOSIT);
			payEntity.setPayno(payNo);
			//payEntity.setName("");
			payEntity.setAmount(balance);
			payEntity.setNeedamount(balance);
			payEntity.setDirection(PayDirection.INPUT);
			payEntity.setPaytype(PayWay.NETBANK_PAY);
			payEntity.setPaytime(DateUtil.getNowDate());
			payEntity.setStatus(TradeStatus.SUCCESS.getVal());
			payEntity.setCreatedate(DateUtil.getNowDate());
			payEntity.setCreator(cid);
			payEntity.setDevices(DeviceType.MOBILE);
			payEntity.setBalance(tpbi.getAmount());
			iPassbookPayService.add(payEntity);
			
			//充值保证金或者货款账户类型
			SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(type.getText(),DateUtil.getNowDate(),TradeType.DEPOSIT.getText(),"+"+balance);
			MessageInfoBean mi = new MessageInfoBean(type == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,tpbi.getId(),cid,content);
			mi.setSendSystemMsg(true);
			mi.setSendShotMsg(true);
			mi.setSmsTemplate(SMSTemplate.getTemplateWallet(type.getText(), DateUtil.getNowDate(), TradeType.DEPOSIT.getText(), "+"+balance));
			//检查和推送消息,账户的余额信息和以及额度的比较
			this.checkAccountSetParamsToPushMessage(mi, cid, type,sendGuarantPushMesgToClient);
			getMesgSender().msgSend(mi);
		}
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, java.lang.String)  
	 */
	public boolean depositAccountOfflineRequest(String cid, PurseType type) {
		if(type == null){
			return false;
		}
		TOfflinePay bean = new TOfflinePay();
		bean.setId(getKey(PaySystemConstant.PURSEOFFLINEPAYID));
		bean.setOtype(type);
		bean.setPtype(OnOffLine.OFFLINE);
		bean.setCreater(cid);
		bean.setCreatetime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST);
		bean.setBtype(BusinessType.DEPOSIT);
		bean.setAmount(0f);
		bean.setTotal(0f);
		iOfflinePayService.add(bean);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	@Override
	public boolean depositAccountOffline(String cid, PurseType type,
			float balance, String payNo) {
		return this.depositAccountOffline(cid,type,balance,payNo,true);
	}
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositAccountOffline(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, float, java.lang.String)  
	 */
	public boolean depositAccountOffline(String cid, PurseType type,
			float balance, String payNo,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(payNo) || StringUtils.isEmpty(cid)
				|| type == null || balance <= 0) {
			return false;
		}
		TPassbookInfo tPurseInfo = new TPassbookInfo();
		tPurseInfo.setCid(cid);
		tPurseInfo.setPasstype(type);
		TPassbookInfo tpbi = iPassbookInfoService.query(tPurseInfo);
		//save the Passbook Info
		tpbi.setAmount(tpbi.getAmount()+balance);
		iPassbookInfoService.modify(tpbi);
		
		//save the pay 
		Date now = DateUtil.getNowDate();
		TPassbookPay payEntity = new TPassbookPay();
		payEntity.setId(getKey(PaySystemConstant.PURSEPAYID));
		payEntity.setPassid(tpbi.getId());
		//payEntity.setOid("");
		payEntity.setOtype(TradeType.DEPOSIT);
		payEntity.setPayno(payNo);
		//payEntity.setName("");
		payEntity.setAmount(balance);
		payEntity.setNeedamount(balance);
		payEntity.setDirection(PayDirection.INPUT);
		payEntity.setPaytype(PayWay.BANK_DEDUCT);
		payEntity.setPaytime(now);
		payEntity.setStatus(TradeStatus.SUCCESS.getVal());
		payEntity.setCreatedate(now);
		payEntity.setCreator(cid);
		payEntity.setDevices(DeviceType.MOBILE);
		payEntity.setBalance(tpbi.getAmount());
		iPassbookPayService.add(payEntity);
		//savePursePay(tpbi.getId(), cid, payNo, StringUtils.EMPTY, StringUtils.EMPTY, balance, tpbi.getAmount(), PayDirection.INPUT, TradeType.DEPOSIT, PayWay.BANK_DEDUCT, TradeStatus.SUCCESS.getVal());
		//充值保证金或者货款账户类型
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(type.getText(),DateUtil.getNowDate(),TradeType.DEPOSIT.getText(),"+"+balance);
		MessageInfoBean mi = new MessageInfoBean(type == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,tpbi.getId(),cid,content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(type.getText(), DateUtil.getNowDate(), TradeType.DEPOSIT.getText(), "+"+balance));
		//检查和推送消息,账户的余额信息和以及额度的比较
		this.checkAccountSetParamsToPushMessage(mi, cid, type,sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi);
		return true;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payAccountOffline(java.lang.String, java.lang.String)  
	 */
	public boolean payAccountOfflineRequest(String cid,PurseType type,OInfo oid) {
		if(type == null || oid == null){
			return false;
		}
		TOfflinePay bean = new TOfflinePay();
		bean.setId(getKey(PaySystemConstant.PURSEOFFLINEPAYID));
		bean.setOtype(type);
		bean.setPtype(OnOffLine.OFFLINE);
		bean.setCreater(cid);
		bean.setCreatetime(DateUtil.getNowDate());
		bean.setStatus(TradeStatus.REQUEST);
		bean.setBtype(BusinessType.PAY);
		bean.setOid(oid.getOid());
		bean.setAmount(oid.getAmount());
		bean.setTotal(oid.getTotal());
		this.iOfflinePayService.add(bean);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositThirdOrgRecord(java.lang.String, java.lang.String, float, java.lang.String)  
	 */
	public boolean depositThirdOrgRecord(String cid, PurseType type,
			float balance, String payNo) {
		if(type == null || StringUtils.isEmpty(payNo) || balance <= 0){
			return false;
		}
		TPassbookInfo tPurseInfo = new TPassbookInfo();
		tPurseInfo.setCid(cid);
		tPurseInfo.setPasstype(type);
		TPassbookInfo tpbi = iPassbookInfoService.query(tPurseInfo);
		String passId = tpbi.getId();
		
		TPassbookThirdCheck entity = new TPassbookThirdCheck();
		entity.setId(getKey(PaySystemConstant.PURSETHIRDCHECKID));
		entity.setPassid(passId);
		entity.setPayno(payNo);
		entity.setOtype(TradeType.DEPOSIT);
		entity.setPatytime(DateUtil.getNowDate());
		entity.setAmount(balance);
		entity.setDirection(PayDirection.INPUT);
		entity.setPaytype(PayWay.NETBANK_PAY);
		entity.setStatus(TradeStatus.SUCCESS);
		entity.setDevices(DeviceType.MOBILE);
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
	public List<TPassbookPay> payRecordList(String cid, PurseType type) {
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		TPassbookInfo info = iPassbookInfoService.query(entity);
		return this.payRecordList(info.getId());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	public List<TPassbookPay> payRecordList(String cid, PurseType type,
			PayDirection payDirection) {
		if(StringUtils.isEmpty(cid) || type == null || payDirection == null){
			return null;
		}
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		TPassbookInfo info = iPassbookInfoService.query(entity);
		TPassbookPay pay = new TPassbookPay();
		pay.setPassid(info.getId());
		pay.setDirection(payDirection);
		return iPassbookPayService.queryForList(pay);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordDetail(java.lang.String)  
	 */
	public TPassbookPay payRecordDetail(String pid) {
		return iPassbookPayService.query(pid);
	}

	public TPassbookDraw extractCashRequest(String cid, PurseType type,
			Float balance, String acceptId) {
		return this.extractCashRequest(cid, type, balance, acceptId, true);
	}
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequest(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public TPassbookDraw extractCashRequest(String cid, PurseType type,
			Float balance, String acceptId,boolean sendGuarantPushMesgToClient) {
		//String acceptId,Float balance,String cid,String result
		if(type == null || StringUtils.isEmpty(acceptId) || balance <= 0){
			return null;
		}
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		entity = iPassbookInfoService.query(entity);
		if(entity.getAmount()<balance){
			return null;
		}
		Date now = DateUtil.getNowDate();
		//提款申请,将提款账户里面钱冻结到平台账户里面，扣款操作
		entity.setAmount(entity.getAmount()-balance);
		iPassbookInfoService.modify(entity);
		//提款申请,将提款账户里面钱冻结到平台账户里面，扣款生成流水
		TPassbookPay pay = new TPassbookPay();
		pay.setId(getKey(PaySystemConstant.PURSEPAYID));
		pay.setPassid(entity.getId());
		pay.setOtype(TradeType.EXTRACT_CASH_GELATION);
		pay.setName(cid);
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.OUTPUT);
		pay.setPaytype(PayWay.PLATFORM_DEDUCT);
		pay.setPaytime(now);
		pay.setStatus(String.valueOf(ExtractStatus.REQUEST.getVal()));
		pay.setCreatedate(DateUtil.getNowDate());
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE);
		pay.setBalance(entity.getAmount());
		iPassbookPayService.add(pay);
		
		//提款申请,将提款账户里面钱冻结到平台账户里面，平台收款
		String destAccountId = type == PurseType.GUARANTY ? MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEGUARANTYFLAG) : MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG);
		TPassbookInfo destAccount = iPassbookInfoService.query(destAccountId);
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		
		//this.savePursePayOutInputLine(entity.getId(), destAccount.getId(), cid, destAccount.getCid(), entity.getAmount(), destAccount.getAmount(), StringUtils.EMPTY, String.valueOf(ExtractStatus.REQUEST.getVal()), TradeType.EXTRACT_CASH_GELATION, PayWay.PLATFORM_DEDUCT, balance);
		//提款申请,将提款账户里面钱冻结到平台账户里面，平台收款生成流水
		TPassbookPay inputPay = new TPassbookPay();
		inputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		inputPay.setPassid(destAccount.getId());
		inputPay.setOtype(TradeType.EXTRACT_CASH_GELATION);
		inputPay.setAmount(balance);
		inputPay.setNeedamount(balance);
		inputPay.setDirection(PayDirection.INPUT);
		inputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		inputPay.setPaytime(now);
		inputPay.setStatus(String.valueOf(ExtractStatus.REQUEST.getVal()));
		inputPay.setCreatedate(now);
		inputPay.setCreator(destAccount.getCid());
		inputPay.setDevices(DeviceType.MOBILE);
		inputPay.setBalance(destAccount.getAmount());
		inputPay.setPpid(pay.getId());
		iPassbookPayService.add(inputPay);
		
		//提款申请,生成提款记录到提现记录表
		TPassbookDraw draw =  new TPassbookDraw();
		draw.setAid(acceptId);
		draw.setStatus(ExtractStatus.REQUEST);
		draw.setCreatetime(now);
		draw.setPid(inputPay.getId());
		draw.setAmount(balance);
		draw.setId(getKey(PaySystemConstant.PURSEDRAWID));
		iPassbookDrawService.add(draw);
		
		//消息通知
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(entity.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_GELATION.getText(),"-"+draw.getAmount());
		MessageInfoBean mi = new MessageInfoBean(entity.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,draw.getId(),entity.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(entity.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_GELATION.getText(),"-"+draw.getAmount()));
		//检查和推送消息,账户的余额信息和以及额度的比较
		this.checkAccountSetParamsToPushMessage(mi, cid, type,sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi);
		return draw;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashAudit(java.lang.String, boolean, java.lang.String, java.lang.String)  
	 */
	public boolean extractCashAudit(String tid, boolean result, String reson,
			String cid) {
		return this.extractCashAudit(tid, result, reson, cid, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashAudit(java.lang.String, boolean, java.lang.String, java.lang.String)  
	 */
	public boolean extractCashAudit(String tid, boolean result, String reson,
			String cid,boolean sendGuarantPushMesgToClient) {
		TPassbookDraw draw = iPassbookDrawService.query(tid);
		TPassbookPay pay = iPassbookPayService.query(draw.getPid());
		TPassbookInfo info = iPassbookInfoService.query(pay.getPassid());
		if(draw.getAmount() > info.getAmount()){
			return false;
		}
		//提款申请审核通过,改变状态，成功之后，还需要调用扣款接口进行扣款
		if(result){
			draw.setStatus(ExtractStatus.SUCCESS);
		}else{
			//提款申请审核失败,将冻结的钱返回给用户，
			draw.setStatus(ExtractStatus.FAILURE);
			pay.setOtype(TradeType.EXTRACT_CASH_FAILURE);
			pay.setStatus(String.valueOf(ExtractStatus.FAILURE.getVal()));
			
			//提款申请审核失败,将冻结的钱返回给用户，从平台账户扣钱给申请用户
			Date now = DateUtil.getNowDate();
			TPassbookInfo sourAccount = iPassbookInfoService.query(pay.getPassid());
			sourAccount.setAmount(sourAccount.getAmount()-draw.getAmount());
			iPassbookInfoService.modify(sourAccount);
			
			//提款申请审核失败,将冻结的钱返回给用户，从平台账户扣钱给申请用户并生成流水
			TPassbookPay outputPay = new TPassbookPay();
			outputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
			outputPay.setPassid(sourAccount.getId());
			outputPay.setOtype(TradeType.EXTRACT_CASH_UNGELATION);
			outputPay.setName(sourAccount.getCid());
			outputPay.setAmount(draw.getAmount());
			outputPay.setNeedamount(draw.getAmount());
			outputPay.setDirection(PayDirection.OUTPUT);
			outputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
			outputPay.setPaytime(now);
			outputPay.setStatus(String.valueOf(ExtractStatus.FAILURE.getVal()));
			outputPay.setCreatedate(DateUtil.getNowDate());
			outputPay.setCreator(sourAccount.getCid());
			outputPay.setDevices(DeviceType.MOBILE);
			outputPay.setBalance(sourAccount.getAmount());
			outputPay.setPpid(pay.getId());
			iPassbookPayService.add(outputPay);
			
			//提款申请审核失败,将冻结的钱返回给用户，从平台账户扣钱给申请用户
			TPassbookPay pPay = iPassbookPayService.query(pay.getPpid());
			TPassbookInfo destAccount = iPassbookInfoService.query(pPay.getPassid());
			destAccount.setAmount(destAccount.getAmount()+draw.getAmount());
			iPassbookInfoService.modify(destAccount);
			
			//提款申请审核失败,将冻结的钱返回给用户，从平台账户扣钱给申请用户并生成流水
			TPassbookPay inputPay = new TPassbookPay();
			inputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
			inputPay.setPassid(destAccount.getId());
			inputPay.setOtype(TradeType.EXTRACT_CASH_UNGELATION);
			inputPay.setAmount(draw.getAmount());
			inputPay.setNeedamount(draw.getAmount());
			inputPay.setDirection(PayDirection.INPUT);
			inputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
			inputPay.setPaytime(now);
			inputPay.setStatus(String.valueOf(ExtractStatus.FAILURE.getVal()));
			inputPay.setCreatedate(now);
			inputPay.setCreator(destAccount.getCid());
			inputPay.setDevices(DeviceType.MOBILE);
			inputPay.setBalance(destAccount.getAmount());
			inputPay.setPpid(outputPay.getId());
			iPassbookPayService.add(inputPay);
			
			//消息通知
			SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_FAILURE.getText(),"-"+draw.getAmount());
			MessageInfoBean mi = new MessageInfoBean(destAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,draw.getId(),destAccount.getCid(),content);
			mi.setSendSystemMsg(true);
			mi.setSendShotMsg(true);
			mi.setSmsTemplate(SMSTemplate.getTemplateWallet(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_FAILURE.getText(),"-"+draw.getAmount()));
			//检查和推送消息,账户的余额信息和以及额度的比较
			this.checkAccountSetParamsToPushMessage(mi, cid, destAccount.getPasstype(),sendGuarantPushMesgToClient);
			getMesgSender().msgSend(mi);
		}
		//提款申请审核，提款修改申请处理记录
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
		TPassbookInfo info = iPassbookInfoService.query(pay.getPassid());
		Date now = DateUtil.getNowDate();
		if(draw.getAmount() > info.getAmount()){
			return false;
		}
		draw.setStatus(ExtractStatus.DEDUCT);
		pay.setOtype(TradeType.EXTRACT_CASH_SUCCESS);
		pay.setStatus(String.valueOf(ExtractStatus.SUCCESS.getVal()));
		if(StringUtils.isNotEmpty(pay.getPpid())){
			TPassbookPay p = iPassbookPayService.query(pay.getPpid());
			p.setOtype(TradeType.EXTRACT_CASH_SUCCESS);
			p.setStatus(String.valueOf(ExtractStatus.SUCCESS.getVal()));
			iPassbookPayService.modify(p);
		}
		iPassbookPayService.modify(pay);
		iPassbookDrawService.modify(draw);
		//提款申请审核成功,将冻结的钱通过银行打给用户，从平台账户扣钱..
		info.setAmount(info.getAmount()-draw.getAmount());
		iPassbookInfoService.modify(info);
		//提款申请审核成功,将冻结的钱通过银行打给用户，从平台账户扣钱..并生成流水
		TPassbookPay outputPay = new TPassbookPay();
		outputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		outputPay.setPassid(info.getId());
		outputPay.setOtype(TradeType.EXTRACT_CASH_SUCCESS);
		outputPay.setAmount(draw.getAmount());
		outputPay.setNeedamount(draw.getAmount());
		outputPay.setDirection(PayDirection.OUTPUT);
		outputPay.setPaytype(PayWay.BANK_DEDUCT);
		outputPay.setPaytime(now);
		outputPay.setStatus(TradeStatus.SUCCESS.getVal());
		outputPay.setCreatedate(now);
		outputPay.setCreator(info.getCid());
		outputPay.setDevices(DeviceType.MOBILE);
		outputPay.setBalance(info.getAmount());
		outputPay.setPpid(pay.getId());
		iPassbookPayService.add(outputPay);
		//
		//提款申请成功变动变动
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(info.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_SUCCESS.getText(),"-"+draw.getAmount());
		MessageInfoBean mi = new MessageInfoBean(info.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,draw.getId(),info.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(info.getPasstype().getText(),DateUtil.getNowDate(),TradeType.EXTRACT_CASH_SUCCESS.getText(),"-"+draw.getAmount()));
		getMesgSender().msgSend(mi);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String, java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(String cid, PurseType type) {
		TPassbookInfo entity = new TPassbookInfo();
		entity.setCid(cid);
		entity.setPasstype(type);
		entity = iPassbookInfoService.query(entity);
		return iPassbookDrawService.getTPassbookDrawByPassId(entity.getId());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(java.lang.String)  
	 */
	public List<TPassbookDraw> extractCashRequestList(ExtractStatus status) {
		TPassbookDraw entity = new TPassbookDraw();
		entity.setStatus(status);
		return iPassbookDrawService.queryForList(entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositToGuaranty(java.lang.String, java.lang.Float)  
	 */
	public boolean depositToGuaranty(String cid, Float balance) {
		return this.depositToGuaranty(cid, balance, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#depositToGuaranty(java.lang.String, java.lang.Float)  
	 */
	public boolean depositToGuaranty(String cid, Float balance,boolean sendGuarantPushMesgToClient) {
		if(StringUtils.isEmpty(cid) || balance <= 0){
			return false;
		}
		TPassbookInfo depositAccount = new TPassbookInfo();
		depositAccount.setCid(cid);
		depositAccount.setPasstype(PurseType.DEPOSIT);
		depositAccount = iPassbookInfoService.query(depositAccount);
		if(depositAccount.getAmount()<balance){
			return false;
		}
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY);
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		
		guarantyAccount.setAmount(guarantyAccount.getAmount()+balance);
		depositAccount.setAmount(depositAccount.getAmount()-balance);
		iPassbookInfoService.modify(guarantyAccount);
		iPassbookInfoService.modify(depositAccount);
		
		//货款充值保证金账户,货款账户扣除
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoneyOther(DateUtil.getNowDate(),balance);
		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_MONEY_CHANGE,depositAccount.getId(),cid,content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWalletDespositToGuaranty(DateUtil.getNowDate(),balance));
		//检查和推送消息,账户的余额信息和以及额度的比较
		this.checkAccountSetParamsToPushMessage(mi, cid, null,sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi);
		
		//货款充值保证金账户,保证金账户增加
		/*SystemMessageContent content1 = SystemMessageContent.getMsgContentOfMoney(PurseType.GUARANTY.getText(),DateUtil.getNowDate(),TradeType.DEPOSIT_GUARANTY.getText(),"+"+balance);
		MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY,guarantyAccount.getId(),cid,content1);
		mi1.setSendSystemMsg(true);
		mi1.setSendShotMsg(true);
		mi1.setSmsTemplate(SMSTemplate.getTemplateWallet(PurseType.GUARANTY.getText(),DateUtil.getNowDate(),TradeType.DEPOSIT_GUARANTY.getText(),"+"+balance));
		if(guarantyAccount.getPasstype() == PurseType.GUARANTY&&sendGuarantPushMesgToClient){
			mi1 = this.checkGuarantEnoughPushMessage(mi1, cid, guarantyAccount.getAmount());
		}
		//检查和推送消息,账户的余额信息和以及额度的比较
		this.checkAccountSetParamsToPushMessage(mi, cid, guarantyAccount.getPasstype(),sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi1);*/
		
		Date now = DateUtil.getNowDate();
		TPassbookPay pay = new TPassbookPay();
		pay.setId(getKey(PaySystemConstant.PURSEPAYID));
		pay.setPassid(guarantyAccount.getId());
		pay.setOtype(TradeType.DEPOSIT_GUARANTY);
		pay.setAmount(balance);
		pay.setNeedamount(balance);
		pay.setDirection(PayDirection.INPUT);
		pay.setPaytype(PayWay.PLATFORM_DEDUCT);
		pay.setPaytime(now);
		pay.setStatus(TradeStatus.SUCCESS.getVal());
		pay.setCreatedate(now);
		pay.setCreator(cid);
		pay.setDevices(DeviceType.MOBILE);
		pay.setBalance(guarantyAccount.getAmount());
		iPassbookPayService.add(pay);
		
		TPassbookPay pay1 = new TPassbookPay();
		pay1.setId(getKey(PaySystemConstant.PURSEPAYID));
		pay1.setPassid(depositAccount.getId());
		pay1.setOtype(TradeType.DEPOSIT_GUARANTY);
		pay1.setAmount(balance);
		pay1.setNeedamount(balance);
		pay1.setDirection(PayDirection.OUTPUT);
		pay1.setPaytype(PayWay.PLATFORM_DEDUCT);
		pay1.setPaytime(now);
		pay1.setStatus(TradeStatus.SUCCESS.getVal());
		pay1.setCreatedate(now);
		pay1.setCreator(cid);
		pay1.setDevices(DeviceType.MOBILE);
		pay1.setBalance(depositAccount.getAmount());
		iPassbookPayService.add(pay1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, java.lang.String)  
	 */
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, PurseType type) {
		return this.transferAccounts(sourPassId, destPassId, balance, TradeType.PAYMENT_FOR_GOODS);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, com.appabc.bean.enums.PurseInfo.PurseType, com.appabc.bean.enums.PurseInfo.TradeType)  
	 */
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, TradeType tradeType) {
		return this.transferAccounts(sourPassId, destPassId, balance, tradeType, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#transferAccounts(java.lang.String, java.lang.String, java.lang.Float, com.appabc.bean.enums.PurseInfo.PurseType, com.appabc.bean.enums.PurseInfo.TradeType)  
	 */
	public boolean transferAccounts(String sourPassId, String destPassId,
			Float balance, TradeType tradeType,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(sourPassId) || StringUtils.isEmpty(destPassId)
				|| balance <= 0){
			return false;
		}
		if(tradeType == null){
			tradeType = TradeType.PAYMENT_FOR_GOODS;
		}
		TPassbookInfo sourAccount = iPassbookInfoService.query(sourPassId);
		TPassbookInfo destAccount = iPassbookInfoService.query(destPassId);
		if(sourAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		//modify the sour account
		sourAccount.setAmount(sourAccount.getAmount()-balance);
		iPassbookInfoService.modify(sourAccount);
		//save the in put pay line
		TPassbookPay inputPay = new TPassbookPay();
		inputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		inputPay.setPassid(sourAccount.getId());
		inputPay.setOtype(tradeType);
		inputPay.setAmount(balance);
		inputPay.setNeedamount(balance);
		inputPay.setDirection(PayDirection.INPUT);
		inputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		inputPay.setPaytime(now);
		inputPay.setStatus(TradeStatus.SUCCESS.getVal());
		inputPay.setCreatedate(now);
		inputPay.setCreator(sourAccount.getCid());
		inputPay.setDevices(DeviceType.MOBILE);
		inputPay.setBalance(sourAccount.getAmount());
		iPassbookPayService.add(inputPay);
		//modify the dest account
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		//save the out put pay line
		TPassbookPay outputPay = new TPassbookPay();
		outputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		outputPay.setPassid(destAccount.getId());
		outputPay.setOtype(tradeType);
		outputPay.setAmount(balance);
		outputPay.setNeedamount(balance);
		outputPay.setDirection(PayDirection.OUTPUT);
		outputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		outputPay.setPaytime(now);
		outputPay.setStatus(TradeStatus.SUCCESS.getVal());
		outputPay.setCreatedate(now);
		outputPay.setCreator(destAccount.getCid());
		outputPay.setDevices(DeviceType.MOBILE);
		outputPay.setBalance(destAccount.getAmount());
		iPassbookPayService.add(outputPay);
		
		//转账操作,源账户扣除
		SystemMessageContent content1 = SystemMessageContent.getMsgContentOfMoney(sourAccount.getPasstype().getText(),DateUtil.getNowDate(),tradeType.getText(),"-"+balance);
		MessageInfoBean mi1 = new MessageInfoBean(sourAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,sourAccount.getId(),sourAccount.getCid(),content1);
		mi1.setSendSystemMsg(true);
		mi1.setSendShotMsg(true);
		mi1.setSmsTemplate(SMSTemplate.getTemplateWallet(sourAccount.getPasstype().getText(),DateUtil.getNowDate(),tradeType.getText(),"-"+balance));
		/*if(sourAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi1, sourAccount.getCid(), sourAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi1, sourAccount.getCid(), sourAccount.getPasstype(),sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi1);
		//转账操作,目标账户扣除
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(destAccount.getPasstype().getText(),DateUtil.getNowDate(),tradeType.getText(),"+"+balance);
		MessageInfoBean mi = new MessageInfoBean(destAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,destAccount.getId(),destAccount.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		/*if(destAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi, destAccount.getCid(), destAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi, destAccount.getCid(), destAccount.getPasstype(),sendGuarantPushMesgToClient);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(destAccount.getPasstype().getText(),DateUtil.getNowDate(),tradeType.getText(),"+"+balance));
		getMesgSender().msgSend(mi);
		return true;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToGelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToGelation(String cid, float balance, String oid) {
		return this.guarantyToGelation(cid, balance, oid, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToGelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToGelation(String cid, float balance, String oid,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(oid)
				|| balance <= 0){
			return false;
		}
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY);
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		TPassbookInfo destAccount = iPassbookInfoService.query(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG));
		if(guarantyAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		guarantyAccount.setAmount(guarantyAccount.getAmount()-balance);
		iPassbookInfoService.modify(guarantyAccount);
		
		TPassbookPay outputPay = new TPassbookPay();
		outputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		outputPay.setOid(oid);
		outputPay.setPassid(guarantyAccount.getId());
		outputPay.setOtype(TradeType.GELATION_GUARANTY);
		outputPay.setAmount(balance);
		outputPay.setNeedamount(balance);
		outputPay.setDirection(PayDirection.OUTPUT);
		outputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		outputPay.setPaytime(now);
		outputPay.setStatus(TradeStatus.SUCCESS.getVal());
		outputPay.setCreatedate(now);
		outputPay.setCreator(cid);
		outputPay.setDevices(DeviceType.MOBILE);
		outputPay.setBalance(guarantyAccount.getAmount());
		iPassbookPayService.add(outputPay);
		
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		
		TPassbookPay inputPay = new TPassbookPay();
		inputPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		inputPay.setOid(oid);
		inputPay.setPassid(destAccount.getId());
		inputPay.setOtype(TradeType.GELATION_GUARANTY);
		inputPay.setAmount(balance);
		inputPay.setNeedamount(balance);
		inputPay.setDirection(PayDirection.INPUT);
		inputPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		inputPay.setPaytime(now);
		inputPay.setStatus(TradeStatus.SUCCESS.getVal());
		inputPay.setCreatedate(now);
		inputPay.setCreator(destAccount.getCid());
		inputPay.setDevices(DeviceType.MOBILE);
		inputPay.setBalance(destAccount.getAmount());
		iPassbookPayService.add(inputPay);
		//guarantyToGelation操作,源账户扣除
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(guarantyAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.GELATION_GUARANTY.getText(),"-"+balance);
		MessageInfoBean mi = new MessageInfoBean(guarantyAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,guarantyAccount.getId(),guarantyAccount.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(guarantyAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.GELATION_GUARANTY.getText(),"-"+balance));
		/*if(guarantyAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi, guarantyAccount.getCid(), guarantyAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi, guarantyAccount.getCid(), guarantyAccount.getPasstype(),sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi);
		
		SystemMessageContent content1 = SystemMessageContent.getMsgContentOfMoney(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.GELATION_GUARANTY.getText(),"+"+balance);
		MessageInfoBean mi1 = new MessageInfoBean(destAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,destAccount.getId(),destAccount.getCid(),content1);
		mi1.setSendSystemMsg(true);
		mi1.setSendShotMsg(true);
		mi1.setSmsTemplate(SMSTemplate.getTemplateWallet(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.GELATION_GUARANTY.getText(),"+"+balance));
		getMesgSender().msgSend(mi1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToUngelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToUngelation(String cid, float balance, String oid) {
		return this.guarantyToUngelation(cid, balance, oid, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToUngelation(java.lang.String, float, java.lang.String)  
	 */
	public boolean guarantyToUngelation(String cid, float balance, String oid,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(oid) || balance <= 0){
			return false;
		}
		TPassbookInfo guarantyAccount = new TPassbookInfo();
		guarantyAccount.setCid(cid);
		guarantyAccount.setPasstype(PurseType.GUARANTY);
		guarantyAccount = iPassbookInfoService.query(guarantyAccount);
		TPassbookInfo destAccount = iPassbookInfoService.query(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG));
		if(destAccount.getAmount()<balance){
			return false;
		}
		Date now = DateUtil.getNowDate();
		guarantyAccount.setAmount(guarantyAccount.getAmount()+balance);
		iPassbookInfoService.modify(guarantyAccount);
		TPassbookPay inPay = new TPassbookPay();
		inPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		inPay.setPassid(guarantyAccount.getId());
		inPay.setOid(oid);
		inPay.setOtype(TradeType.UNGELATION_GUARANTY);
		inPay.setAmount(balance);
		inPay.setNeedamount(balance);
		inPay.setDirection(PayDirection.INPUT);
		inPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		inPay.setPaytime(now);
		inPay.setStatus(TradeStatus.SUCCESS.getVal());
		inPay.setCreatedate(now);
		inPay.setCreator(cid);
		inPay.setDevices(DeviceType.MOBILE);
		inPay.setBalance(guarantyAccount.getAmount());
		iPassbookPayService.add(inPay);
		
		destAccount.setAmount(destAccount.getAmount()-balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay outPay = new TPassbookPay();
		outPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		outPay.setPassid(destAccount.getId());
		outPay.setOid(oid);
		outPay.setOtype(TradeType.UNGELATION_GUARANTY);
		outPay.setAmount(balance);
		outPay.setNeedamount(balance);
		outPay.setDirection(PayDirection.OUTPUT);
		outPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		outPay.setPaytime(now);
		outPay.setStatus(TradeStatus.SUCCESS.getVal());
		outPay.setCreatedate(now);
		outPay.setCreator(destAccount.getCid());
		outPay.setDevices(DeviceType.MOBILE);
		outPay.setBalance(destAccount.getAmount());
		iPassbookPayService.add(outPay);
		//guarantyToGelation操作,源账户扣除
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(guarantyAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.UNGELATION_GUARANTY.getText(),"+"+balance);
		MessageInfoBean mi = new MessageInfoBean(guarantyAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,guarantyAccount.getId(),guarantyAccount.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		/*if(guarantyAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi, guarantyAccount.getCid(), guarantyAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi, guarantyAccount.getCid(), guarantyAccount.getPasstype(),sendGuarantPushMesgToClient);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(guarantyAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.UNGELATION_GUARANTY.getText(),"+"+balance));
		getMesgSender().msgSend(mi);
		
		SystemMessageContent content1 = SystemMessageContent.getMsgContentOfMoney(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.UNGELATION_GUARANTY.getText(),"-"+balance);
		MessageInfoBean mi1 = new MessageInfoBean(destAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,destAccount.getId(),destAccount.getCid(),content1);
		mi1.setSendSystemMsg(true);
		mi1.setSendShotMsg(true);
		mi1.setSmsTemplate(SMSTemplate.getTemplateWallet(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.UNGELATION_GUARANTY.getText(),"-"+balance));
		getMesgSender().msgSend(mi1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToDeduct(java.lang.String, java.lang.String, java.lang.Float)  
	 */
	public boolean guarantyToDeduct(String sourPassId, String destPassId,
			Float balance) {
		return this.guarantyToDeduct(sourPassId, destPassId, balance, true);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#guarantyToDeduct(java.lang.String, java.lang.String, java.lang.Float)  
	 */
	public boolean guarantyToDeduct(String sourPassId, String destPassId,
			Float balance,boolean sendGuarantPushMesgToClient) {
		if (StringUtils.isEmpty(sourPassId) || StringUtils.isEmpty(destPassId)
				|| balance <= 0){
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
		TPassbookPay inPay = new TPassbookPay();
		inPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		inPay.setPassid(sourAccount.getId());
		inPay.setOtype(TradeType.VIOLATION_DEDUCTION);
		inPay.setAmount(balance);
		inPay.setNeedamount(balance);
		inPay.setDirection(PayDirection.INPUT);
		inPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		inPay.setPaytime(now);
		inPay.setStatus(TradeStatus.SUCCESS.getVal());
		inPay.setCreatedate(now);
		inPay.setCreator(sourAccount.getCid());
		inPay.setDevices(DeviceType.MOBILE);
		inPay.setBalance(sourAccount.getAmount());
		iPassbookPayService.add(inPay);
		
		destAccount.setAmount(destAccount.getAmount()+balance);
		iPassbookInfoService.modify(destAccount);
		TPassbookPay outPay = new TPassbookPay();
		outPay.setId(getKey(PaySystemConstant.PURSEPAYID));
		outPay.setPassid(destAccount.getId());
		outPay.setOtype(TradeType.VIOLATION_DEDUCTION);
		outPay.setAmount(balance);
		outPay.setNeedamount(balance);
		outPay.setDirection(PayDirection.OUTPUT);
		outPay.setPaytype(PayWay.PLATFORM_DEDUCT);
		outPay.setPaytime(now);
		outPay.setStatus(TradeStatus.SUCCESS.getVal());
		outPay.setCreatedate(now);
		outPay.setCreator(destAccount.getCid());
		outPay.setDevices(DeviceType.MOBILE);
		outPay.setBalance(destAccount.getAmount());
		iPassbookPayService.add(outPay);
		//guarantyToDeduct操作,源账户扣除
		SystemMessageContent content = SystemMessageContent.getMsgContentOfMoney(sourAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.VIOLATION_DEDUCTION.getText(),"-"+balance);
		MessageInfoBean mi = new MessageInfoBean(sourAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,sourAccount.getId(),sourAccount.getCid(),content);
		mi.setSendSystemMsg(true);
		mi.setSendShotMsg(true);
		mi.setSmsTemplate(SMSTemplate.getTemplateWallet(sourAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.VIOLATION_DEDUCTION.getText(),"-"+balance));
		/*if(sourAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi, sourAccount.getCid(), sourAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi, sourAccount.getCid(), sourAccount.getPasstype(),sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi);
		
		SystemMessageContent content1 = SystemMessageContent.getMsgContentOfMoney(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.VIOLATION_DEDUCTION.getText(),"+"+balance);
		MessageInfoBean mi1 = new MessageInfoBean(destAccount.getPasstype() == PurseType.GUARANTY ? MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_GUARANTY : MsgBusinessType.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT,destAccount.getId(),destAccount.getCid(),content1);
		mi1.setSendSystemMsg(true);
		mi1.setSendShotMsg(true);
		mi1.setSmsTemplate(SMSTemplate.getTemplateWallet(destAccount.getPasstype().getText(),DateUtil.getNowDate(),TradeType.VIOLATION_DEDUCTION.getText(),"+"+balance));
		/*if(destAccount.getPasstype() == PurseType.GUARANTY && sendGuarantPushMesgToClient){
			checkGuarantEnoughPushMessage(mi1, destAccount.getCid(), destAccount.getAmount());
		}*/
		this.checkAccountSetParamsToPushMessage(mi1, destAccount.getCid(), destAccount.getPasstype(),sendGuarantPushMesgToClient);
		getMesgSender().msgSend(mi1);
		return true;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#saveOfflinePay(com.appabc.pay.bean.TOfflinePay)  
	 */
	public void saveOfflinePay(TOfflinePay tOfflinePay) throws ServiceException {
		tOfflinePay.setId(getKey(PaySystemConstant.PURSEOFFLINEPAYID));//PURSEPAYID
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

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getGuarantyTotal(java.lang.String)  
	 */
	@Override
	public float getGuarantyTotal(String cid) {
		if(StringUtils.isEmpty(cid)){
			return 0;
		}
		float total = 0;
		TPassbookInfo info = this.getPurseAccountInfo(cid, PurseType.GUARANTY);
		total += info.getAmount().floatValue();
		
		TPassbookPay outEntity = new TPassbookPay();
		outEntity.setOtype(TradeType.GELATION_GUARANTY);
		outEntity.setPassid(info.getId());
		outEntity.setStatus(TradeStatus.SUCCESS.getVal());
		outEntity.setDirection(PayDirection.OUTPUT);
		List<TPassbookPay> outResult = iPassbookPayService.queryForList(outEntity);
		
		TPassbookPay inEntity = new TPassbookPay();
		inEntity.setOtype(TradeType.UNGELATION_GUARANTY);
		inEntity.setPassid(info.getId());
		inEntity.setStatus(TradeStatus.SUCCESS.getVal());
		inEntity.setDirection(PayDirection.INPUT);
		List<TPassbookPay> inResult = iPassbookPayService.queryForList(inEntity);
		
		if(CollectionUtils.isNotEmpty(outResult)){
			boolean flag = false;
			for(TPassbookPay tpp : outResult){
				flag = true;
				for(TPassbookPay itpp : inResult){
					if (StringUtils.equalsIgnoreCase(tpp.getOid(),itpp.getOid())
							|| StringUtils.isEmpty(tpp.getOid())
							|| StringUtils.isEmpty(itpp.getOid())){
						flag = false;
						break;
					}
				}
				if(flag){
					total += tpp.getAmount().doubleValue();
				}
			}
		}
		return total;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getAcceptBankList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TAcceptBank> getAcceptBankList(
			QueryContext<TAcceptBank> qContext) {
		return iAcceptBankService.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#payRecordList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookPay> payRecordList(
			QueryContext<TPassbookPay> qContext) {
		return iPassbookPayService.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#extractCashRequestList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookDraw> extractCashRequestList(
			QueryContext<TPassbookDraw> qContext) {
		return iPassbookDrawService.queryListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.service.IPassPayService#getOfflinePayList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOfflinePay> getOfflinePayList(
			QueryContext<TOfflinePay> qContext) {
		return iOfflinePayService.queryListForPagination(qContext);
	}

}
