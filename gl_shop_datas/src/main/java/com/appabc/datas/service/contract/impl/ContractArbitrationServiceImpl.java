package com.appabc.datas.service.contract.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.dao.contract.IContractArbitrationDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.utils.SystemMessageContent;

/**
 * @Description : 合同仲裁的操作service
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 
 * @see : 2014年9月3日 下午5:57:49
 */

@Service
public class ContractArbitrationServiceImpl extends ContractBaseService<TOrderArbitration> implements IContractArbitrationService {

	@Autowired
	private IContractArbitrationDAO iContractArbitrationDAO;

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private IContractMineService iContractMineService;

	@Autowired
	private ICompanyRankingService iCompanyRankingService;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderArbitration entity) {
		entity.setId(getKey(DataSystemConstant.CONTRACTARBITRATIONID));
		iContractArbitrationDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderArbitration entity) {
		iContractArbitrationDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderArbitration entity) {
		iContractArbitrationDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractArbitrationDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderArbitration query(TOrderArbitration entity) {
		return iContractArbitrationDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderArbitration query(Serializable id) {
		return iContractArbitrationDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderArbitration> queryForList(TOrderArbitration entity) {
		return iContractArbitrationDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderArbitration> queryForList(Map<String, ?> args) {
		return iContractArbitrationDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderArbitration> queryListForPagination(
			QueryContext<TOrderArbitration> qContext) {
		return iContractArbitrationDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#
	 * toContractArbitration(java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public TOrderArbitration toContractArbitration(String oid,String cid,String cname,String flid) throws ServiceException{
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo contract = iContractInfoDAO.query(oid);
		if(contract == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		if(!StringUtils.equalsIgnoreCase(cid, contract.getBuyerid()) && !StringUtils.equalsIgnoreCase(cid, contract.getSellerid())){
			throw new ServiceException(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR));
		}
		boolean isArbitratedFlag = !((contract.getLifecycle() == ContractLifeCycle.CONFIRMING_GOODS_FUNDS || contract.getLifecycle() == ContractLifeCycle.PAYED_FUNDS) && contract.getStatus() == ContractStatus.DOING);
		if(isArbitratedFlag){			
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
		}
		TOrderOperations queryEntity = new TOrderOperations();
		queryEntity.setOid(oid);
		queryEntity.setOperator(cid);
		queryEntity.setType(ContractOperateType.APPLY_ARBITRATION);
		List<TOrderOperations> res = iContractOperationDAO.queryForList(queryEntity);
		if(CollectionUtils.isNotEmpty(res)){
			throw new ServiceException(ServiceErrorCode.SUBMIT_OPERATOR_AGAIN,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_REPEAT_SUBMIT_ERROR));
		}
		ContractLifeCycle clc = contract.getLifecycle();
		// step 1 : update the contract info;
		Date now = DateUtil.getNowDate();
		contract.setStatus(ContractStatus.DOING);
		contract.setLifecycle(ContractLifeCycle.ARBITRATING);
		contract.setUpdater(cid);
		contract.setUpdatetime(now);
		iContractInfoDAO.update(contract);

		// step 2 : save the operation info to operation table;
		TOrderOperations operate = new TOrderOperations();
		operate.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operate.setOid(oid);
		operate.setOperator(cid);
		operate.setOperationtime(now);
		operate.setType(ContractOperateType.APPLY_ARBITRATION);
		operate.setOrderstatus(contract.getLifecycle());
		operate.setOldstatus(clc);
		StringBuilder result = new StringBuilder(cname+getMessage(DataSystemConstant.MESSAGEKEY_TOCONTRACTARBITRATIONTIPS));
		operate.setResult(result.toString());
		operate.setRemark(result.toString());
		operate.setPlid(flid);
		iContractOperationDAO.save(operate);
		
		// step 3 : save the arbitration to the arbitration table;
		TOrderArbitration arbitration = new TOrderArbitration();
		arbitration.setId(getKey(DataSystemConstant.CONTRACTARBITRATIONID));
		arbitration.setLid(operate.getId());
		arbitration.setCreater(cid);
		arbitration.setCreatetime(now);
		arbitration.setStatus(ContractArbitrationStatus.REQUEST);
		arbitration.setRemark(result.toString());
		this.add(arbitration);
		if(StringUtils.equalsIgnoreCase(cid, contract.getBuyerid())){
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_APPLY_ARBITRATION, contract.getId(), contract.getSellerid(), SystemMessageContent.getMsgContentOfContractBuyerApplyArbitration(contract.getRemark()));
		} else {
			sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_APPLY_ARBITRATION, contract.getId(), contract.getBuyerid(), SystemMessageContent.getMsgContentOfContractSellerApplyArbitration(contract.getRemark()));
		}
		return arbitration;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#
	 * getContractArbitrationHistroy(java.lang.String)
	 */
	public List<?> getContractArbitrationHistroy(String oid) {
		return iContractArbitrationDAO.queryArbitrationForList(oid);
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#toConsultingService(java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	@Deprecated
	public TOrderArbitration toConsultingService(String oid, String cid,
			String cname) throws ServiceException {
		// step 1 : update the contract info;
		if(StringUtils.isEmpty(oid) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		TOrderInfo contract = iContractInfoDAO.query(oid);
		Date now = DateUtil.getNowDate();
		/*contract.setStatus(ContractStatus.DOING);
		contract.setLifecycle(ContractLifeCycle.ARBITRATION);
		contract.setUpdater(operator);
		contract.setUpdatetime(now);
		iContractInfoDAO.update(contract);*/
		// step 2 : save the operation info to operation table;
		
		StringBuilder result = new StringBuilder(cname);
		result.append(contract.getLifecycle().getText());
		result.append(getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_CONSULTING_SERVICETIPS));
		//result.append(MessagesUtil.getMessage("TOCONTRACTARBITRATIONTIPS"));
		TOrderOperations operate = new TOrderOperations();
		operate.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operate.setOid(oid);
		operate.setOperator(cid);
		operate.setOperationtime(now);
		operate.setType(ContractOperateType.CONSULTING_SERVICE);
		operate.setOrderstatus(contract.getLifecycle());
		operate.setOldstatus(contract.getLifecycle());
		operate.setResult(result.toString());
		operate.setRemark(result.toString());
		iContractOperationDAO.save(operate);
		// step 3 : save the arbitration to the arbitration table;
		TOrderArbitration arbitration = new TOrderArbitration();
		arbitration.setId(getKey(DataSystemConstant.CONTRACTARBITRATIONID));
		arbitration.setLid(operate.getId());
		arbitration.setCreater(cid);
		arbitration.setCreatetime(now);
		arbitration.setStatus(ContractArbitrationStatus.REQUEST);
		arbitration.setRemark(result.toString());
		this.add(arbitration);
		return arbitration;
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#contractArbitractionProcess(boolean, java.lang.String, java.lang.String, java.lang.String, float, float)  
	 */
	@Override
	public void contractArbitractionProcess(boolean isTrade, String aid, String cid, String cname, double num, double price) throws ServiceException {
		this.contractArbitractionProcess(isTrade, aid, cid, cname, num, price, StringUtils.EMPTY);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#contractArbitractionProcess(boolean, java.lang.String, java.lang.String, java.lang.String, float, float, java.lang.String)  
	 */
	@Override
	public void contractArbitractionProcess(boolean isTrade, String aid,String cid, String cname, double num, double price, String result)
			throws ServiceException {
		contractArbitractionProcess(isTrade, aid, cid, cname, num, price, 0, result);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#getContractArbitrationInfo()  
	 */
	@Override
	public List<ContractArbitrationBean> getContractArbitrationInfoForList(ContractArbitrationStatus status) {
		if(status == null){
			return null;
		}
		return this.iContractArbitrationDAO.queryContractArbitrationInfoForList(status);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#getContractArbitrationInfoListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<ContractArbitrationBean> getContractArbitrationInfoListForPagination(
			QueryContext<ContractArbitrationBean> qContext) {
		if (qContext == null) {
			return null;
		}
		return iContractArbitrationDAO.getContractArbitrationInfoListForPagination(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.service.contract.IContractArbitrationService#contractArbitractionProcess(boolean, java.lang.String, java.lang.String, java.lang.String, double, double, double, java.lang.String)  
	 */
	@Override
	public void contractArbitractionProcess(boolean isTrade, String aid,
			String cid, String cname, double num, double price, double amount,
			String result) throws ServiceException {
		if(StringUtils.isEmpty(aid) ||StringUtils.isEmpty(cid) || StringUtils.isEmpty(cname)){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR));
		}
		if(isTrade && num <= 0 && price <= 0){
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_ARBITRATION_PRICE_NUM_ERROR));
		}
		TOrderInfo o = iContractInfoDAO.queryContractWithAID(aid);
		if(o == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		if(o.getLifecycle() != ContractLifeCycle.ARBITRATING){
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR, getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_LIFECYCLEIS_ERROR));
		}
		if(amount > 0.0){
			o.setAmount(amount);
		}
		ContractLifeCycle clc = o.getLifecycle();
		Date now = DateUtil.getNowDate();
		o.setStatus(ContractStatus.FINISHED);
		o.setLifecycle(ContractLifeCycle.ARBITRATED);
		o.setUpdater(cid);
		o.setUpdatetime(now);
		iContractInfoDAO.update(o);
		
		TOrderArbitration toa = iContractArbitrationDAO.query(aid);
		StringBuilder sb = new StringBuilder(cname+getMessage(DataSystemConstant.MESSAGEKEY_CONTRACT_ARBITRATION_PROCESSTIPS));
		toa.setDealer(cid);
		toa.setDealtime(now);
		toa.setStatus(ContractArbitrationStatus.SUCCESS);
		toa.setDealresult(result);
		toa.setRemark(sb.toString());
		iContractArbitrationDAO.update(toa);
		
		TOrderOperations oper = new TOrderOperations();
		oper.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		oper.setOid(o.getId());
		oper.setOperator(cid);
		oper.setOperationtime(now);
		oper.setType(ContractOperateType.ARBITRATION_CONTRACT);
		oper.setOrderstatus(ContractLifeCycle.ARBITRATED);
		oper.setOldstatus(clc);
		oper.setResult(sb.toString());
		oper.setRemark(sb.toString());
		iContractOperationDAO.save(oper);
		
		if(isTrade){
			TOrderDisPrice bean = new TOrderDisPrice();
			bean.setLid(oper.getId());
			bean.setCanceler(cid);
			bean.setCanceltime(now);
			bean.setType(ContractDisPriceType.ARBITRATION_DISPRICE);
			List<TOrderDisPrice> res = iContractDisPriceDAO.queryForList(o.getId());
			if (CollectionUtils.isNotEmpty(res)) {
				TOrderDisPrice priceObj = res.get(res.size()-1);
				bean.setBeginamount(priceObj.getEndamount());
				bean.setBeginnum(priceObj.getEndnum());
			} else {
				bean.setBeginamount(o.getPrice());
				bean.setBeginnum(o.getTotalnum());
			}
			bean.setEndamount(price);
			bean.setEndnum(num);
			bean.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
			iContractDisPriceDAO.save(bean);
		} else {
			TOrderDisPrice bean = new TOrderDisPrice();
			bean.setLid(oper.getId());
			bean.setCanceler(cid);
			bean.setCanceltime(now);
			bean.setType(ContractDisPriceType.ARBITRATION_DISPRICE);
			bean.setBeginamount(o.getPrice());
			bean.setBeginnum(o.getTotalnum());
			bean.setEndamount(0.0);
			bean.setEndnum(0.0);
			bean.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
			iContractDisPriceDAO.save(bean);
		}
		
		//这需要去走合同正常的结算过程,放到service里面操作
		contractFinalEstimate(o, cid, cname, String.valueOf(isTrade));
		//更新实际支付货款的值.
		iContractInfoDAO.update(o);
		//移动合同的信息,操作我的合同列表信息,仲裁结束的合同还是在进行的合同列表中,只有当用户手动操作才会到已经结束列表
		//iContractMineDAO.saveOrUpdateMineContractWithCidOid(o.getId(), o.getBuyerid(), o.getStatus(), o.getLifecycle(), cid);
		//iContractMineDAO.saveOrUpdateMineContractWithCidOid(o.getId(), o.getSellerid(), o.getStatus(), o.getLifecycle(), cid);
		//save or update the mine contract with cid or oid.
		//contractTimeOutMoveToFinishList(o, cid);
		
		//计算交易成功率.
		iCompanyRankingService.calculateTradeSuccessRate(o.getBuyerid());
		iCompanyRankingService.calculateTradeSuccessRate(o.getSellerid());

		//合同仲裁完成,提醒买家和卖家仲裁结束
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ARBITRATED_FINALESTIMATE, o.getId(), o.getBuyerid(), SystemMessageContent.getMsgContentOfContractArbitratedResult(o.getRemark()));
		sendSystemXmppMessage(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ARBITRATED_FINALESTIMATE, o.getId(), o.getSellerid(), SystemMessageContent.getMsgContentOfContractArbitratedResult(o.getRemark()));
	}

}
