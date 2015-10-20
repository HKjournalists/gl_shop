package com.appabc.datas.service.contract.impl;

import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.EveryUtil;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.SystemMessageContent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description : 合同议价信息记录服务
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:45:55
 */

@Service(value="IContractDisPriceService")
public class ContractDisPriceServiceImpl extends ContractBaseService<TOrderDisPrice> implements IContractDisPriceService {

	@Autowired
	private ICompanyRankingService iCompanyRankingService;
	
	@Autowired
	private IContractMineService iContractMineService;
	
	@Autowired
	private IContractInfoService iContractInfoService;

	@Autowired
	private IContractInfoDAO iContractInfoDAO;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderDisPrice entity) {
		//entity.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
		iContractDisPriceDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderDisPrice entity) {
		iContractDisPriceDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderDisPrice entity) {
		iContractDisPriceDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractDisPriceDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderDisPrice query(TOrderDisPrice entity) {
		return iContractDisPriceDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderDisPrice query(Serializable id) {
		return iContractDisPriceDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderDisPrice> queryForList(TOrderDisPrice entity) {
		return iContractDisPriceDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderDisPrice> queryForList(Map<String, ?> args) {
		return iContractDisPriceDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderDisPrice> queryListForPagination(
			QueryContext<TOrderDisPrice> qContext) {
		return iContractDisPriceDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#
	 * validateGoodsDisPrice(com.appabc.bean.pvo.TOrderDisPrice)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void validateGoodsDisPrice(String contractId, String operatorName,TOrderDisPrice bean) throws ServiceException {
		if(StringUtils.isEmpty(contractId) || StringUtils.isEmpty(operatorName) || bean == null){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo contractInfo = iContractInfoDAO.query(contractId);
		//如果合同为空,就不能操作了.
		if(contractInfo == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		contractInfo.setStatus(ContractStatus.DOING);
		ContractLifeCycle clc = contractInfo.getLifecycle();
		if (clc == ContractLifeCycle.PAYED_FUNDS || clc == ContractLifeCycle.SENT_GOODS || clc == ContractLifeCycle.SIMPLE_CHECKING){
			contractInfo.setLifecycle(ContractLifeCycle.SIMPLE_CHECKING);
			bean.setType(ContractDisPriceType.SAMPLE_CHECK);
		} else if (clc == ContractLifeCycle.SIMPLE_CHECKED || clc == ContractLifeCycle.FULL_TAKEOVERING){
			contractInfo.setLifecycle(ContractLifeCycle.FULL_TAKEOVERING);
			bean.setType(ContractDisPriceType.FULL_TAKEOVER);
		} else {
			throw new ServiceException(ServiceErrorCode.CONTRACT_JUST_ALLOW_DISPRICE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_JUST_ALLOW_DISPRICE_ERROR));
		}
		iContractInfoDAO.update(contractInfo);

		// step 1 : make sure the begin amount and number
		List<TOrderDisPrice> result = iContractDisPriceDAO.queryForList(contractId);
		if (CollectionUtils.isNotEmpty(result)) {
			TOrderDisPrice price = result.get(result.size() - 1);
			bean.setBeginamount(price.getEndamount());
			bean.setBeginnum(price.getEndnum());
		} else {
			bean.setBeginamount(contractInfo.getPrice());
			bean.setBeginnum(contractInfo.getTotalnum());
		}
		
		// step 2 : save the record to operation table
		TOrderOperations operationBean = new TOrderOperations();
		operationBean.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operationBean.setOid(contractId);
		operationBean.setOperator(bean.getCanceler());
		operationBean.setOperationtime(bean.getCanceltime());
		operationBean.setType(ContractOperateType.DIS_PRICE);
		operationBean.setOrderstatus(contractInfo.getLifecycle());
		operationBean.setOldstatus(clc);
		StringBuilder sb = new StringBuilder(operatorName);
		sb.append(MessagesUtil.getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_DIS_PRICE_TIPS));
		operationBean.setResult(sb.toString());
		operationBean.setRemark(sb.toString());
		operationBean.setPlid(bean.getLid());
		iContractOperationDAO.save(operationBean);
		
		// step 3 : save the record to DisPrice table
		bean.setLid(operationBean.getId());
		bean.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
		iContractDisPriceDAO.save(bean);
		
		if(StringUtils.equalsIgnoreCase(contractInfo.getSellerid(), bean.getCanceler())){//卖家操作，发消息给买家
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, contractId, contractInfo.getBuyerid(), SystemMessageContent.getMsgContentOfContractNewBargaining(contractId));
		}else if(StringUtils.equalsIgnoreCase(contractInfo.getBuyerid(), bean.getCanceler())){//买家操作，发消息给卖 家
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING, contractId, contractInfo.getSellerid(), SystemMessageContent.getMsgContentOfContractReplyBargaining(contractId));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#
	 * getGoodsDisPriceInfo(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public List<TContractDisPriceOperation> getGoodsDisPriceHisList(String contractId,
			String operateId, String disPriceId,String disPriceType) {
		return iContractDisPriceDAO.queryGoodsDisPriceHisList(contractId, operateId, disPriceId, disPriceType);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#applyContractFinalEstimate(java.lang.String, java.lang.String, java.lang.String, float, float)  
	 */
	@Override
	public void applyContractFinalEstimate(String oid, String cid,String cname, double price, double num) throws ServiceException {
		this.applyContractFinalEstimate(oid, cid, cname, price, num, 0);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#applyContractFinalEstimate(java.lang.String, java.lang.String, java.lang.String, double, double, double)  
	 */
	@Override
	public void applyContractFinalEstimate(String oid, String cid,String cname, double price, double num, double finalAmount) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo tOrderInfo = iContractInfoDAO.query(oid);
		if(tOrderInfo == null || !(tOrderInfo.getLifecycle() == ContractLifeCycle.PAYED_FUNDS && tOrderInfo.getStatus() == ContractStatus.DOING)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOALLOW_ERROR));
		}
		if(!StringUtils.equalsIgnoreCase(cid, tOrderInfo.getBuyerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOTBUYER_TOAPPLY_FINALESTIMATE, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTACT_FINALESTIMATE_NOTBUYER_ERROR));
		}
		double disAmount = RandomUtil.mulRound(price, num);
		double totalAmount = RandomUtil.mulRound(tOrderInfo.getPrice(), tOrderInfo.getTotalnum());
		if(disAmount > 0.0 && finalAmount > 0.0){
			boolean disf = EveryUtil.EqNumNotHalfBetweenAandB(disAmount, finalAmount);
			if(disf){
				throw new ServiceException(ServiceErrorCode.CONTRACT_FINALESTIMATE_AMOUNT_SCOPE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_FINALESTIMATE_AMOUNT_SCOPE_ERROR));
			}
			tOrderInfo.setSettleamount(finalAmount);
		}else if(totalAmount > 0.0 && finalAmount > 0.0){
			boolean totf = EveryUtil.EqNumNotHalfBetweenAandB(totalAmount, finalAmount);
			if(totf){
				throw new ServiceException(ServiceErrorCode.CONTRACT_FINALESTIMATE_AMOUNT_SCOPE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_FINALESTIMATE_AMOUNT_SCOPE_ERROR));
			}
			tOrderInfo.setSettleamount(finalAmount);
		}
		ContractLifeCycle clc = tOrderInfo.getLifecycle();
		Date now = DateUtil.getNowDate();
		tOrderInfo.setUpdater(cid);
		tOrderInfo.setUpdatetime(now);
		tOrderInfo.setLifecycle(ContractLifeCycle.CONFIRMING_GOODS_FUNDS);
		tOrderInfo.setStatus(ContractStatus.DOING);
		iContractInfoDAO.update(tOrderInfo);
		
		//申请合同货物和货款确认操作
		TOrderOperations applyOper = new TOrderOperations();
		applyOper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		applyOper.setOid(oid);
		applyOper.setOperator(cid);
		applyOper.setOperationtime(now);
		applyOper.setType(ContractOperateType.FUNDS_GOODS_CONFIRM);
		applyOper.setOrderstatus(ContractLifeCycle.CONFIRMING_GOODS_FUNDS);
		applyOper.setOldstatus(clc);
		StringBuilder applyResult = new StringBuilder(cname);
		applyResult.append(getMessage(DataSystemConstant.MESSAGEKEY_APPLYCONTRACTFINALESTIMATE));
		applyOper.setResult(applyResult.toString());
		applyOper.setRemark(applyResult.toString());
		iContractOperationDAO.save(applyOper);
		
		if(price > 0.0 && num > 0.0){
			TOrderDisPrice bean = new TOrderDisPrice();
			bean.setLid(applyOper.getId());
			bean.setCanceler(cid);
			bean.setCanceltime(now);
			bean.setType(ContractDisPriceType.FUNDGOODS_DISPRICE);
			//去掉查询以前的议价记录,这边不需要
			bean.setBeginamount(tOrderInfo.getPrice());
			bean.setBeginnum(tOrderInfo.getTotalnum());
			bean.setEndamount(price);
			bean.setEndnum(num);
			bean.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
			this.iContractDisPriceDAO.save(bean);
		}
		//买家货物和货款申请确认
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE, oid, tOrderInfo.getSellerid(), SystemMessageContent.getMsgContentOfContractApplyFundsGoodsConfirmWithBuyer(tOrderInfo.getRemark(),ContractCostDetailUtil.getContractAgreeFinalEstimateLimitNum()));
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#agreeContractFinalEstimate(java.lang.String, java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	public void agreeContractFinalEstimate(String oid, String cid,String cname, String flid) throws ServiceException {
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo bean = iContractInfoDAO.query(oid);
		if(bean == null || !(bean.getLifecycle() == ContractLifeCycle.CONFIRMING_GOODS_FUNDS && bean.getStatus() == ContractStatus.DOING)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOALLOW_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOALLOW_ERROR));
		}
		if(!StringUtils.equalsIgnoreCase(cid, bean.getSellerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOTSELLER_TOAGREE_FINALESTIMATE,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTACT_FINALESTIMATE_NOTSELLER_ERROR));
		}
		Date now = DateUtil.getNowDate();
		ContractLifeCycle clc = bean.getLifecycle();
		
		bean.setUpdater(cid);
		bean.setUpdatetime(now);
		bean.setLifecycle(ContractLifeCycle.FINALESTIMATE_FINISHED);
		bean.setStatus(ContractStatus.FINISHED);
		this.iContractInfoDAO.update(bean);
		//这需要去走合同正常的结算过程,放到service里面操作
		contractFinalEstimate(bean, cid, cname);
		
		//生成合同结算操作明细
		TOrderOperations estimateOperator = new TOrderOperations();
		estimateOperator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		estimateOperator.setOid(oid);
		estimateOperator.setOperator(cid);
		estimateOperator.setOperationtime(now);
		estimateOperator.setType(ContractOperateType.AGREE_FUND_GOODS_CONFIRM);
		estimateOperator.setOrderstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
		estimateOperator.setOldstatus(clc);
		StringBuilder estimateResult = new StringBuilder(cname);
		estimateResult.append(getMessage(DataSystemConstant.MESSAGEKEY_FINALESTIMATEFINISHEDTIPS));
		estimateOperator.setResult(estimateResult.toString());
		estimateOperator.setRemark(estimateResult.toString());
		estimateOperator.setPlid(flid);
		iContractOperationDAO.save(estimateOperator);
		
		//更新合同的状态为正常结束
		bean.setUpdater(cid);
		bean.setUpdatetime(now);
		bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		bean.setStatus(ContractStatus.FINISHED);
		this.iContractInfoDAO.update(bean);
		//合同完成结束的操作记录
		TOrderOperations finishOper = new TOrderOperations();
		finishOper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		finishOper.setOid(oid);
		finishOper.setOperator(cid);
		finishOper.setOperationtime(now);
		finishOper.setType(ContractOperateType.CONTRACT_FINISHED);
		finishOper.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED);
		finishOper.setOldstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
		StringBuilder finishMesg = new StringBuilder(cname);
		finishMesg.append(getMessage(DataSystemConstant.MESSAGEKEY_FINISH_CONTRACT_TIPS));
		finishOper.setResult(finishMesg.toString());
		finishOper.setRemark(finishMesg.toString());
		iContractOperationDAO.save(finishOper);
		
		//save or update the mine contract with cid or oid.
		contractTimeOutMoveToFinishList(bean, cid);
		
		//计算交易成功率.
		iCompanyRankingService.calculateTradeSuccessRate(bean.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(bean.getSellerid());

		//买家确认收货,触发条件发送消息告诉买家可以进行评价
		//sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractCompletion(oid));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
		
	}
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#getGoodsDisPriceHistroyListWithSampleCheck(java.lang.String)  
	 */
	@Override
	public List<TContractDisPriceOperation> getGoodsDisPriceHistroyListWithSampleCheck(String oid) {
		return iContractDisPriceDAO.queryGoodsDisPriceHistroyListWithSampleCheck(oid);
	}
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#getGoodsDisPriceHistroyListWithFullTakeover(java.lang.String)  
	 */
	@Override
	public List<TContractDisPriceOperation> getGoodsDisPriceHistroyListWithFullTakeover(String oid) {
		return iContractDisPriceDAO.queryGoodsDisPriceHistroyListWithFullTakeover(oid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#jobAutoAgreeContractFinalEstimate(java.lang.String, java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void jobAutoAgreeContractFinalEstimate(TOrderInfo bean, String cid,String cname, String flid) throws ServiceException {
		if(bean == null || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			return ;
		}
		Date now = DateUtil.getNowDate();
		TOrderOperations result = iContractOperationDAO.queryForListWithOidAndCidAndType(bean.getId(), bean.getBuyerid(), ContractOperateType.FUNDS_GOODS_CONFIRM);
		Date d = null;
		if(result != null){
			d = result.getOperationtime();
		} else {
			d = bean.getUpdatetime();
		}
		int hours = DateUtil.getDifferHoursWithTwoDate(d, now);
		if (hours >= ContractCostDetailUtil.getContractAgreeFinalEstimateLimitNum()) {
			ContractLifeCycle clc = bean.getLifecycle();
			String oid = bean.getId();
			log.info(" Contract is "+oid+"; enter the auto agree final estimate. ");
			
			bean.setUpdater(cid);
			bean.setUpdatetime(now);
			bean.setLifecycle(ContractLifeCycle.FINALESTIMATE_FINISHED);
			bean.setStatus(ContractStatus.FINISHED);
			iContractInfoService.modify(bean);
			// 这需要去走合同正常的结算过程,放到service里面操作
			iContractInfoService.contractFinalEstimate(bean, cid, cname);
			log.info(" Contract is "+oid+"; finished the final estimate . ");
			
			// 生成合同结算操作明细
			TOrderOperations estimateOper = new TOrderOperations();
			estimateOper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			estimateOper.setOid(oid);
			estimateOper.setOperator(cid);
			estimateOper.setOperationtime(now);
			estimateOper.setType(ContractOperateType.AGREE_FUND_GOODS_CONFIRM);
			estimateOper.setOrderstatus(ContractLifeCycle.FINALESTIMATE_FINISHED);
			estimateOper.setOldstatus(clc);
			StringBuilder estimateResult = new StringBuilder(cname);
			estimateResult.append(getMessage(DataSystemConstant.MESSAGEKEY_FINALESTIMATEFINISHEDTIPS));
			estimateOper.setResult(estimateResult.toString());
			estimateOper.setRemark(estimateResult.toString());
			estimateOper.setPlid(flid);
			iContractOperationDAO.save(estimateOper);

			// 更新合同的状态为正常结束
			bean.setUpdater(cid);
			bean.setUpdatetime(now);
			bean.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
			bean.setStatus(ContractStatus.FINISHED);
			iContractInfoService.modify(bean);
			log.info(" Contract is "+oid+"; finished the Contract. ");
			
			// 合同完成结束的操作记录
			TOrderOperations finishOper = new TOrderOperations();
			finishOper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
			finishOper.setOid(oid);
			finishOper.setOperator(cid);
			finishOper.setOperationtime(now);
			finishOper.setType(ContractOperateType.CONTRACT_FINISHED);
			finishOper.setOrderstatus(ContractLifeCycle.NORMAL_FINISHED);
			finishOper.setOldstatus(ContractLifeCycle.NORMAL_FINISHED);
			StringBuilder finishMesg = new StringBuilder(cname);
			finishMesg.append(getMessage(DataSystemConstant.MESSAGEKEY_FINISH_CONTRACT_TIPS));
			finishOper.setResult(finishMesg.toString());
			finishOper.setRemark(finishMesg.toString());
			finishOper.setPlid(estimateOper.getId());
			iContractOperationDAO.save(finishOper);
			// save or update the mine contract with cid or oid.
			iContractInfoService.contractTimeOutMoveToFinishList(bean, cid);
			log.info(" Contract is "+oid+"; do the time out move to the finish list. ");
			// 计算交易成功率.
			iCompanyRankingService.calculateTradeSuccessRate(bean.getBuyerid());
			iCompanyRankingService.calculateTradeSuccessRate(bean.getSellerid());
			log.info(" Contract is "+oid+"; do the rank . ");
			//买家确认收货,触发条件发送消息告诉买家可以进行评价
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getBuyerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE, oid, bean.getSellerid(), SystemMessageContent.getMsgContentOfContractToEvaluation(bean.getRemark()));
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#getOrderDisPriceListByOperId(java.lang.String)  
	 */
	@Override
	public List<TOrderDisPrice> getOrderDisPriceListByOperId(String lid) {
		if(StringUtils.isEmpty(lid)){
			return null;
		}
		return iContractDisPriceDAO.queryOrderDisPriceListByOperId(lid);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractDisPriceService#queryForList(java.lang.String)  
	 */
	@Override
	public List<TOrderDisPrice> queryForList(String contractId) {
		if(StringUtils.isEmpty(contractId)){
			return null;
		}
		return iContractDisPriceDAO.queryForList(contractId);
	}

}
