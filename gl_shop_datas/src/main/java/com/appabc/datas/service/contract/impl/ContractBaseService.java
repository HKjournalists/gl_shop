package com.appabc.datas.service.contract.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.contract.IContractDisPriceDAO;
import com.appabc.datas.dao.contract.IContractMineDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractBaseService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.EveryUtil;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.GuarantStatusCheck;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 合同模块基本的service
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月6日 下午2:45:35
 */

public abstract class ContractBaseService<T extends BaseBean> extends BaseService<T> implements IContractBaseService<T> {
	
	@Autowired
	protected GuarantStatusCheck gsCheck;
	
	@Autowired
	protected MessageSendManager mesgSender;
	
	@Autowired
	protected PrimaryKeyGenerator pKGenerator;

	@Autowired
	protected IPassPayService iPassPayService;
	
	@Autowired
	protected IContractMineDAO iContractMineDAO;
	
	@Autowired
	protected IContractOperationDAO iContractOperationDAO;
	
	@Autowired
	protected IContractDisPriceDAO iContractDisPriceDAO;
	
	/**
	 * @Description : 构造消息实体
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return MessageInfoBean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	private MessageInfoBean structureMessageInfoBean(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg){
		if(businessType == null || StringUtils.isEmpty(businessId) || StringUtils.isEmpty(cid) || content == null){
			return null;
		}
		MessageInfoBean mi = new MessageInfoBean(businessType,businessId,cid,content);
		mi.setSendSystemMsg(systemMsg);
		mi.setSendShotMsg(shortMsg);
		mi.setSendPushMsg(xmppMsg);
		return mi;
	}
	/**
	 * @Description : 获取配置消息根据CODE
	 * @param code
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		return this.getMessage(code, "datas");
	}
	
	/**
	 * @Description : 获取配置消息根据CODE和local
	 * @param code;localTag
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code,String localTag){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		if(StringUtils.isNotEmpty(localTag)){
			return MessagesUtil.getMessage(code, Locale.forLanguageTag(localTag));
		} else {
			return MessagesUtil.getMessage(code, Locale.forLanguageTag("datas"));
		}
	}
	
	/**
	 * @Description : 获取表主键,根据业务ID
	 * @param bid;
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return pKGenerator.getPKey(bid);
	}
	
	/**
	 * @Description : 检查企业是否保证金足额
	 * @param cid;
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public boolean checkCashGuarantyEnough(String cid){
		float shouldGuarantNum = gsCheck.getGuarantStatus(cid);
		float total = iPassPayService.getGuarantyTotal(cid);
		if(shouldGuarantNum > total){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @Description : 合同结束后,如果有效期到了,就自动移到合同结束列表.
	 * @param bean;operator;other
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void contractTimeOutMoveToFinishList(TOrderInfo bean,String operator,String...other) throws ServiceException{
		if(bean == null){
			return ;
		}
		Date limittime = bean.getLimittime();
		if(limittime == null){
			return ;
		}
		if(bean.getStatus() != ContractStatus.FINISHED){
			return ;
		}
		boolean isFlag = false;
		if(other != null && other.length > 0){
			isFlag = true;
		}
		//save or update the mine contract with cid or oid.
		Date now = DateUtil.getNowDate();
		int diffDays = DateUtil.getDifferDayWithTwoDate(bean.getLimittime(), now);
		if(diffDays >= 0){
			boolean b = iContractMineDAO.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getBuyerid(), bean.getStatus(), bean.getLifecycle(), operator);
			if(!b){
				throw new ServiceException(ServiceErrorCode.CONTRACT_MOVETO_MYORDERLIST_ERROR,StringUtils.EMPTY);
			}
			
			boolean c = iContractMineDAO.saveOrUpdateMineContractWithCidOid(bean.getId(), bean.getSellerid(), bean.getStatus(), bean.getLifecycle(), isFlag == true ? other[0] : operator);
			if(!c){
				throw new ServiceException(ServiceErrorCode.CONTRACT_MOVETO_MYORDERLIST_ERROR,StringUtils.EMPTY);
			}
		}
	}
	
	/**
	 * @Description : 绑定合同冻结保证金操作
	 * @param oid;buyerId;sellerId;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public double guarantyToGelation(String oid,String buyerId,String sellerId,double totalAmount) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(buyerId) || StringUtils.isEmpty(sellerId) || totalAmount <= 0f){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		this.guarantyToGelation(oid, buyerId, totalAmount);
		double balance = this.guarantyToGelation(oid, sellerId, totalAmount);
		return balance;
	}
	
	/**
	 * @Description : 绑定合同冻结保证金操作
	 * @param oid;cid;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public double guarantyToGelation(String oid,String cid,double totalAmount) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || totalAmount <= 0f){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		double balance = ContractCostDetailUtil.getGuarantyCost(totalAmount);
		if(balance > 0){
			boolean f = iPassPayService.guarantyToGelation(cid, balance, oid);
			if(!f){
				throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR));
			}
		}
		return balance;
	}
	
	/**
	 * @Description : 绑定合同解冻保证金操作
	 * @param oid;cid;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public double guarantyToUngelation(String oid,String cid,double totalAmount) throws ServiceException {
		TPassbookPay pay = iPassPayService.getGuarantyToGelationRecord(oid, cid);
		if(pay == null){
			return 0f;
		}
		double amount = pay.getAmount();
		boolean ff = iPassPayService.guarantyToUngelation(cid, amount, oid);
		if(!ff){
			throw new ServiceException(ServiceErrorCode.CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_CONFIRM_CONTRACT_NOENOUGHGUANT_ERROR));
		}
		return amount;
	}
	
	/**
	 * @Description : 绑定合同解冻保证金操作
	 * @param oid;buyerId;sellerId;totalAmount
	 * @return float
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public double guarantyToUngelation(String oid,String buyerId,String sellerId,double totalAmount) throws ServiceException{
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(buyerId) || StringUtils.isEmpty(sellerId) || totalAmount <= 0f){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		this.guarantyToUngelation(oid, buyerId, totalAmount);
		double balance = this.guarantyToUngelation(oid, sellerId, totalAmount);
		return balance;
	}
	
	/**
	 * @Description : 进行合同结算操作[支持正常结算,单方取消结算,仲裁结算,未付款超时结束合同结算]
	 * @param bean;cid;cname;others
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void contractFinalEstimate(TOrderInfo bean,String cid,String cname,String... others) throws ServiceException{
		if(bean == null || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		//解冻双方的保证金,并计算出扣除的手续费用
		double balance = guarantyToUngelation(bean.getId(), bean.getBuyerid(), bean.getSellerid(), bean.getTotalamount());
		//获取买家实际支付的金额
		double payFundsAmount = iPassPayService.getContractPayFundsAmount(bean.getBuyerid(), bean.getId());
		
		//正常结算：1,解冻双方的保证金; 2,把买家支付货款付给卖家;3,扣除卖家的手术服务费用;4,返还多余的货款给买家;[其中存在议价的过程,如有议价要加上议价的信息].
		if(bean.getStatus() == ContractStatus.FINISHED && (bean.getLifecycle() == ContractLifeCycle.NORMAL_FINISHED || bean.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED)){
			//如果这里有议价过程,请加上议价的过程.
			//解冻双方的保证金
			TPassbookInfo buyerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
			TPassbookInfo sellerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.DEPOSIT);
			//查看是否有议价记录,如果有,就计算议价后的货款,进行结算.
			
			//如果没有议价记录,就直接用订单价格总量进行计算
			double contractTotalAmount = 0.0;
			List<TContractDisPriceOperation> result = iContractDisPriceDAO.queryGoodsDisPriceHisList(bean.getId(), StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.FUNDGOODS_DISPRICE.getVal());
			boolean isRightFinalAmount = false;
			if(CollectionUtils.isNotEmpty(result)){
				isRightFinalAmount = EveryUtil.EqNumNotHalfBetweenAandB(RandomUtil.mulRound(result.get(0).getEndnum(), result.get(0).getEndamount()), bean.getSettleamount());
			}else{
				isRightFinalAmount = EveryUtil.EqNumNotHalfBetweenAandB(bean.getTotalamount(), bean.getSettleamount());
			}
			if(bean.getSettleamount()>0.0 && !isRightFinalAmount){
				contractTotalAmount = bean.getSettleamount();
			}else if(CollectionUtils.isNotEmpty(result)){
				TContractDisPriceOperation disEntity = result.get(0);
				contractTotalAmount = RandomUtil.mulRound(disEntity.getEndnum(), disEntity.getEndamount());
			} else {
				contractTotalAmount = bean.getTotalamount();
			}
			if(contractTotalAmount > payFundsAmount){
				contractTotalAmount = payFundsAmount;
			}
			//把买家支付货款付给卖家,扣除卖家的手术服务费用
			if(contractTotalAmount > 0.0){
				iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), sellerPurseInfo.getId(), contractTotalAmount, TradeType.PAYMENT_FOR_GOODS,bean.getId());
				double serviceBalance = ContractCostDetailUtil.getServiceCost(contractTotalAmount);
				if(serviceBalance > 0.0){				
					iPassPayService.transferAccounts(sellerPurseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), serviceBalance, TradeType.SERVICE_CHARGE,bean.getId());
				}
			}
			
			//支付货款时,发现有多余货款返还给买家
			if(payFundsAmount > contractTotalAmount){
				double retAmount = RandomUtil.subRound(payFundsAmount, contractTotalAmount);
				if(retAmount > 0){				
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), buyerPurseInfo.getId(), retAmount, TradeType.PLATFORM_RETURN,bean.getId());
				}
			}
			
			//设置支付金额
			bean.setAmount(contractTotalAmount);
		//单方取消结算：1,解冻双方的保证金; 2,扣除违约方的保证金给对方, 3,如买家已经付款,返还货款给买家.	
		}else if(bean.getStatus() == ContractStatus.FINISHED && bean.getLifecycle() == ContractLifeCycle.SINGLECANCEL_FINISHED){
			//解冻双方的保证金
			//获取操作取消的人的钱包账户信息即源账户信息
			TPassbookInfo sourAcc = iPassPayService.getPurseAccountInfo(cid, PurseType.GUARANTY);
			String olid = bean.getBuyerid().equalsIgnoreCase(cid) ? bean.getSellerid() : bean.getBuyerid();
			//获取目标账户信息.
			TPassbookInfo destAcc = iPassPayService.getPurseAccountInfo(olid, PurseType.GUARANTY);
			//将取消方的保证金转给被取消方.
			iPassPayService.transferAccounts(sourAcc.getId(), destAcc.getId(), TradeType.VIOLATION_DEDUCTION, TradeType.VIOLATION_REPARATION,balance,bean.getId());
			//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
			//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
			boolean isPayContract = iContractOperationDAO.getIsContractPayRecord(bean.getId(), bean.getBuyerid());
			if(isPayContract){
				TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
				if(payFundsAmount > 0.0){
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), payFundsAmount, TradeType.PLATFORM_RETURN,bean.getId());
				}
				bean.setAmount(payFundsAmount);
			}
		//双方取消结算:1,解冻双方的保证金;2,如果买家已经付款了,将货款给返还.
		}else if(bean.getStatus() == ContractStatus.FINISHED && bean.getLifecycle() == ContractLifeCycle.DUPLEXCANCEL_FINISHED){
			//解冻双方的保证金
			//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
			//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
			boolean isPayContract = iContractOperationDAO.getIsContractPayRecord(bean.getId(), bean.getBuyerid());
			if(isPayContract){
				TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
				if(payFundsAmount > 0.0){
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), payFundsAmount, TradeType.PLATFORM_RETURN,bean.getId());
				}
				bean.setAmount(payFundsAmount);
			}
		//仲裁结算：A,如果继续交易,1,解冻双方的保证金; 2,按照仲裁的价格和数量计算货款并支付给卖家; 3,扣除卖家的服务费; 4,返还多余的货款给买家.
		//         B,不交易,1,解冻双方的保证金; 2,如果买家已付货款就返回货款给买家.
		}else if(bean.getStatus() == ContractStatus.FINISHED && bean.getLifecycle() == ContractLifeCycle.ARBITRATED){
			//相应的解冻保证金
			String isTrade = others!=null && others.length > 0 ? others[0] : StringUtils.EMPTY;
			boolean _isTrade = BooleanUtils.toBooleanObject(isTrade);
			if(_isTrade){
				double contractTotalAmount = 0.0;
				List<TContractDisPriceOperation> result = iContractDisPriceDAO.queryGoodsDisPriceHisList(bean.getId(), StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.ARBITRATION_DISPRICE.getVal());
				boolean isRightFinalAmount = false;
				if(CollectionUtils.isNotEmpty(result)){
					isRightFinalAmount = EveryUtil.EqNumNotHalfBetweenAandB(RandomUtil.mulRound(result.get(0).getEndnum(), result.get(0).getEndamount()), bean.getAmount());
				}else{
					isRightFinalAmount = EveryUtil.EqNumNotHalfBetweenAandB(bean.getTotalamount(), bean.getAmount());
				}
				if(bean.getAmount()>0.0 && !isRightFinalAmount){
					contractTotalAmount = bean.getAmount();
				}else if(CollectionUtils.isNotEmpty(result)){
					TContractDisPriceOperation disEntity = result.get(0);
					contractTotalAmount = RandomUtil.mulRound(disEntity.getEndnum(), disEntity.getEndamount());
				} else {
					contractTotalAmount = bean.getTotalamount();
				}
				if(contractTotalAmount > payFundsAmount){
					contractTotalAmount = payFundsAmount;
				}
				TPassbookInfo buyerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
				TPassbookInfo sellerPurseInfo = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.DEPOSIT);
				
				//把买家支付货款付给卖家,扣除卖家的手术服务费用
				if(contractTotalAmount > 0.0){
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), sellerPurseInfo.getId(), contractTotalAmount, TradeType.PAYMENT_FOR_GOODS,bean.getId());
					double serviceBalance = ContractCostDetailUtil.getServiceCost(contractTotalAmount);
					if(serviceBalance > 0.0){				
						iPassPayService.transferAccounts(sellerPurseInfo.getId(), MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), serviceBalance, TradeType.SERVICE_CHARGE,bean.getId());
					}
				}
				
				//支付货款时,发现有多余货款返还给买家
				if(payFundsAmount > contractTotalAmount){
					double retAmount = RandomUtil.subRound(payFundsAmount, contractTotalAmount);
					if(retAmount > 0){				
						iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), buyerPurseInfo.getId(), retAmount, TradeType.PLATFORM_RETURN, bean.getId());
					}
				}
				
				//设置支付金额
				bean.setAmount(contractTotalAmount);
			}else{
				//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
				//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
				boolean isPayContract = iContractOperationDAO.getIsContractPayRecord(bean.getId(), bean.getBuyerid());
				if(isPayContract){
					TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
					if(payFundsAmount > 0.0){
						iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), payFundsAmount, TradeType.PLATFORM_RETURN,bean.getId());
					}
					bean.setAmount(payFundsAmount);
				}
			}
		//未付款超时结束合同结算：1, 解冻双方的保证金; 2,将买家的保证金扣除给卖家.	
		}else if(bean.getStatus() == ContractStatus.FINISHED && bean.getLifecycle() == ContractLifeCycle.BUYER_UNPAY_FINISHED){
			//这需要去走合同买家未付款结束结算过程,放到service里面操作
			//相应的解冻保证金
			//获取操作取消的人的钱包账户信息即源账户信息
			TPassbookInfo sourAcc = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.GUARANTY);
			//获取目标账户信息.
			TPassbookInfo destAcc = iPassPayService.getPurseAccountInfo(bean.getSellerid(), PurseType.GUARANTY);
			//将取消方的保证金转给被取消方.
			iPassPayService.transferAccounts(sourAcc.getId(), destAcc.getId(), TradeType.VIOLATION_DEDUCTION,TradeType.VIOLATION_REPARATION,balance, bean.getId());
			//将买家支付的货款返还给买家.[如果合同处理起草,签订,付款中的状态,则不需要返回货款,本来买家就还没有支付货款]
			//业务操作是买家支付过，会在操作记录里面记录支付记录，查询支付记录，有就退还合同款项
			boolean isPayContract = iContractOperationDAO.getIsContractPayRecord(bean.getId(), bean.getBuyerid());
			if(isPayContract){
				TPassbookInfo purseInfo = iPassPayService.getPurseAccountInfo(bean.getBuyerid(), PurseType.DEPOSIT);
				if(payFundsAmount > 0.0){
					iPassPayService.transferAccounts(MessagesUtil.getMessage(SystemConstant.PLATFORMPURSEDEPOSITFLAG), purseInfo.getId(), payFundsAmount, TradeType.PLATFORM_RETURN,bean.getId());
				}
				bean.setAmount(payFundsAmount);
			}
		}
	}
	/**
	 * @Description : 发送消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg){
		MessageInfoBean mi = structureMessageInfoBean(businessType,businessId,cid,content,systemMsg,shortMsg,xmppMsg);
		mesgSender.msgSend(mi);
	}
	
	public void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg,Map<String,Object> params){
		MessageInfoBean mi = structureMessageInfoBean(businessType,businessId,cid,content,systemMsg,shortMsg,xmppMsg);
		if(!org.springframework.util.CollectionUtils.isEmpty(params)){
			Iterator<Entry<String, Object>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> e = it.next();
				mi.addParam(e.getKey(), e.getValue());
			}
		}
		mesgSender.msgSend(mi);
	}
	
	public void sendMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,boolean systemMsg,boolean shortMsg,boolean xmppMsg,KeyValue kv){
		MessageInfoBean mi = structureMessageInfoBean(businessType,businessId,cid,content,systemMsg,shortMsg,xmppMsg);
		if(kv != null && StringUtils.isNotEmpty(kv.getKey()) && kv.getValue() != null){
			mi.addParam(kv.getKey(), kv.getValue());
		}
		mesgSender.msgSend(mi);
	}
	
	/**
	 * @Description : 发送消息接口,包含系统消息,短消息,xmpp消息
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendAllMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, true, true, true);
	}
	
	public void sendAllMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,Map<String,Object> params){
		this.sendMessage(businessType, businessId, cid, content, true, true, true,params);
	}
	
	public void sendAllMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,KeyValue kv){
		this.sendMessage(businessType, businessId, cid, content, true, true, true,kv);
	}
	
	/**
	 * @Description : 发送系统消息和xmpp消息接口
	 * @param businessType;businessId;cid;content;systemMsg;shortMsg;xmppMsg
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, true, false, true);
	}
	
	public void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,Map<String,Object> params){
		this.sendMessage(businessType, businessId, cid, content, true, false, true,params);
	}
	
	public void sendSystemXmppMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,KeyValue kv){
		this.sendMessage(businessType, businessId, cid, content, true, false, true,kv);
	}
	
	/**
	 * @Description : 仅发送短消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public void sendOnlyShortMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content){
		this.sendMessage(businessType, businessId, cid, content, false, true, false);
	}
	
	public void sendOnlyShortMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,Map<String,Object> params){
		this.sendMessage(businessType, businessId, cid, content, false, true, false,params);
	}
	
	public void sendOnlyShortMessage(MsgBusinessType businessType, String businessId, String cid, SystemMessageContent content,KeyValue kv){
		this.sendMessage(businessType, businessId, cid, content, false, true, false,kv);
	}
	
	/**
	 * @Description : 仅发送系统消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	@Override
	public void sendOnlySystemMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content) {
		this.sendMessage(businessType, businessId, cid, content, true, false, false);
	}
	
	public void sendOnlySystemMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content,Map<String,Object> params) {
		this.sendMessage(businessType, businessId, cid, content, true, false, false,params);
	}
	
	public void sendOnlySystemMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content,KeyValue kv) {
		this.sendMessage(businessType, businessId, cid, content, true, false, false,kv);
	}

	/**
	 * @Description : 仅发送XMPP消息接口
	 * @param businessType;businessId;cid;content
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	@Override
	public void sendOnlyXmppMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content) {
		this.sendMessage(businessType, businessId, cid, content, false, false, true);
	}
	
	public void sendOnlyXmppMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content,Map<String,Object> params) {
		this.sendMessage(businessType, businessId, cid, content, false, false, true,params);
	}
	
	public void sendOnlyXmppMessage(MsgBusinessType businessType,String businessId, String cid, SystemMessageContent content,KeyValue kv) {
		this.sendMessage(businessType, businessId, cid, content, false, false, true,kv);
	}
	
	public static class KeyValue implements org.apache.commons.collections.KeyValue{

		private String key;
		
		private Object value;
		
		public KeyValue(){}
		
		public KeyValue(String key,Object value){
			this.key = key;
			this.value = value;
		}
		
		public void setKey(String key){
			this.key = key;
		}
		/* (non-Javadoc)  
		 * @see org.apache.commons.collections.KeyValue#getKey()  
		 */
		@Override
		public String getKey() {
			return key;
		}

		public void setValue(Object value){
			this.value = value;
		}
		
		/* (non-Javadoc)  
		 * @see org.apache.commons.collections.KeyValue#getValue()  
		 */
		@Override
		public Object getValue() {
			return value;
		}
		
	}
}
