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
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractDisPriceDAO;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import com.appabc.tools.utils.SystemMessageContent;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:45:55
 */

@Service
public class ContractDisPriceServiceImpl extends BaseService<TOrderDisPrice>
		implements IContractDisPriceService {

	@Autowired
	private IContractDisPriceDAO iContractDisPriceDAO;

	@Autowired
	private IContractOperationDAO iContractOperationDAO;

	@Autowired
	private IContractInfoDAO iContractInfoDAO;

	@Autowired
	private PrimaryKeyGenerator pKGenerator;

	@Autowired
	private MessageSendManager mesgSender;
	
	private String getKey(String bid){
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		return pKGenerator.getPKey(bid);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public void add(TOrderDisPrice entity) {
		entity.setId(getKey(DataSystemConstant.CONTRACTDISPRICEID));
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
		TOrderInfo contractInfo = iContractInfoDAO.query(contractId);
		//如果合同为空,就不能操作了.
		if(contractInfo == null){
			throw new ServiceException(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,"the contract is null.");
		}
		contractInfo.setStatus(ContractStatus.DOING);
		if (contractInfo.getLifecycle() == ContractLifeCycle.PAYED_FUNDS
			|| contractInfo.getLifecycle() == ContractLifeCycle.SENT_GOODS
			|| contractInfo.getLifecycle() == ContractLifeCycle.SIMPLE_CHECKING){
			contractInfo.setLifecycle(ContractLifeCycle.SIMPLE_CHECKING);
			bean.setType(ContractDisPriceType.SAMPLE_CHECK);
		}else if(contractInfo.getLifecycle() == ContractLifeCycle.SIMPLE_CHECKED
				||contractInfo.getLifecycle() == ContractLifeCycle.FULL_TAKEOVERING){
			contractInfo.setLifecycle(ContractLifeCycle.FULL_TAKEOVERING);
			bean.setType(ContractDisPriceType.FULL_TAKEOVER);
		}else{
			throw new ServiceException(ServiceErrorCode.CONTRACT_JUST_ALLOW_DISPRICE_ERROR,"the contract current status and lifecycle is not allow you to dis price.");
		}
		iContractInfoDAO.update(contractInfo);
		// step 1 : make sure the begin amount and number
		List<TOrderDisPrice> result = iContractDisPriceDAO.queryForList(contractId);
		if (result != null && result.size() > 0) {
			TOrderDisPrice price = result.get(result.size() - 1);
			bean.setBeginamount(price.getEndamount());
			bean.setBeginnum(price.getEndnum());
		} else {
			TOrderInfo o = iContractInfoDAO.query(contractId);
			bean.setBeginamount(o.getPrice());
			bean.setBeginnum(o.getTotalnum());
		}
		// step 2 : save the record to operation table
		TOrderOperations operationBean = new TOrderOperations();
		operationBean.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operationBean.setOid(contractId);
		operationBean.setOperator(bean.getCanceler());
		operationBean.setOperationtime(bean.getCanceltime());
		operationBean.setType(ContractOperateType.DIS_PRICE);
		operationBean.setOrderstatus(contractInfo.getLifecycle());
		StringBuffer sb = new StringBuffer(operatorName);
		sb.append(MessagesUtil.getMessage("CONTRACT_DIS_PRICE_TIPS"));
		operationBean.setResult(sb.toString());
		operationBean.setRemark(sb.toString());
		operationBean.setPlid(bean.getLid());
		iContractOperationDAO.save(operationBean);
		// step 3 : save the record to DisPrice table
		bean.setLid(operationBean.getId());
		bean.setId(pKGenerator.generatorBusinessKeyByBid("CONTRACTDISPRICEID"));
		iContractDisPriceDAO.save(bean);
		if(StringUtils.equalsIgnoreCase(contractInfo.getSellerid(), bean.getCanceler())){//卖家操作，发消息给买家
			MessageInfoBean sellerMi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,contractInfo.getBuyerid(),SystemMessageContent.getMsgContentOfContractNewBargaining(contractId));
			sellerMi.setSendSystemMsg(true);
			sellerMi.setSendPushMsg(true);
			mesgSender.msgSend(sellerMi);
		}else if(StringUtils.equalsIgnoreCase(contractInfo.getBuyerid(), bean.getCanceler())){//买家操作，发消息给卖 家
			MessageInfoBean sellerMi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,contractInfo.getSellerid(),SystemMessageContent.getMsgContentOfContractReplyBargaining(contractId));
			sellerMi.setSendSystemMsg(true);
			sellerMi.setSendPushMsg(true);
			mesgSender.msgSend(sellerMi);
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

}
