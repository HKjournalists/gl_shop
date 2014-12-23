package com.appabc.datas.service.contract.impl;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.dao.contract.IContractInfoDAO;
import com.appabc.datas.dao.contract.IContractOperationDAO;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractOperationService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月3日 下午5:40:36
 */

@Service(value="IContractOperationService")
public class ContractOperationServiceImpl extends BaseService<TOrderOperations>
		implements IContractOperationService {

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
	public void add(TOrderOperations entity) {
		entity.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		iContractOperationDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void modify(TOrderOperations entity) {
		iContractOperationDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */
	public void delete(TOrderOperations entity) {
		iContractOperationDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		iContractOperationDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public TOrderOperations query(TOrderOperations entity) {
		return iContractOperationDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TOrderOperations query(Serializable id) {
		return iContractOperationDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public List<TOrderOperations> queryForList(TOrderOperations entity) {
		return iContractOperationDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TOrderOperations> queryForList(Map<String, ?> args) {
		return iContractOperationDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */
	public QueryContext<TOrderOperations> queryListForPagination(
			QueryContext<TOrderOperations> qContext) {
		return iContractOperationDAO.queryListForPagination(qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.datas.service.contract.IContractOperationService#
	 * applyOrNotGoodsInfo(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public TOrderOperations applyOrNotGoodsInfo(String contractId, String type,
			String operator, String result, String pid) {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		TOrderOperations entity = new TOrderOperations();
		entity.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(new Date());
		entity.setPlid(pid);
		entity.setType(ContractOperateType.enumOf(type));
		entity.setResult(result);
		entity.setOrderstatus(bean.getLifecycle());
		entity.setRemark(result);
		iContractOperationDAO.save(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#confirmUninstallGoods(java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void confirmUninstallGoods(String contractId,String confirmer,String confirmerName) throws ServiceException {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		if(!StringUtils.equalsIgnoreCase(bean.getSellerid(), confirmer)){
			throw new ServiceException(ServiceErrorCode.CONTRACT_UNINSTALLS_GOODS_NOTSELLER_ERROR,"您不是卖家,不能进行卸货操作.");
		}
		Date now = new Date();
		bean.setStatus(ContractStatus.DOING);
		bean.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS);
		bean.setUpdater(confirmer);
		bean.setUpdatetime(now);
		iContractInfoDAO.update(bean);
		TOrderOperations operator = new TOrderOperations();
		operator.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		operator.setOid(contractId);
		operator.setOperator(confirmer);
		operator.setOperationtime(now);
		operator.setType(ContractOperateType.CONFIRM_UNINSTALLGOODS);
		operator.setOrderstatus(ContractLifeCycle.UNINSTALLED_GOODS);
		StringBuffer result = new StringBuffer(confirmerName);
		result.append(MessagesUtil.getMessage("UNINSTALLGOODSTIPS"));
		operator.setResult(result.toString());
		operator.setRemark(result.toString());
		this.add(operator);
		//议价完成,卖家确认卸货后，提醒买家确认收货.
		MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractReceiptGoodsTimeout(contractId));
		mi.setSendSystemMsg(true);
		mi.setSendPushMsg(true);
		mesgSender.msgSend(mi);
		//卖家确认卸货,触发条件发送消息告诉卖家可以进行评价
		MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_EVALUATION,contractId,bean.getSellerid(),SystemMessageContent.getMsgContentOfContractCompletion(contractId));
		mi1.setSendSystemMsg(true);
		mi1.setSendPushMsg(true);
		mesgSender.msgSend(mi1);

	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#getContractChangeHistory(java.lang.String)
	 */
	public List<?> getContractChangeHistory(String contractId) {
		return iContractOperationDAO.queryForList(contractId);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#applyOrPassGoodsInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public TOrderOperations applyOrPassGoodsInfo(String contractId,String operType, String pid, String operator,String operatorName) throws ServiceException {
		TOrderInfo bean = iContractInfoDAO.query(contractId);
		bean.setStatus(ContractStatus.DOING);
		ContractLifeCycle lifeCycle = bean.getLifecycle();
		boolean isFullTakeOvered = false;
		//ContractLifeCycle.PAYED_FUNDS.getValue()
		if (lifeCycle == ContractLifeCycle.PAYED_FUNDS || lifeCycle == ContractLifeCycle.SENT_GOODS || lifeCycle == ContractLifeCycle.SIMPLE_CHECKING){
			if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.VALIDATE_PASS.getVal())){
				if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKING){
					throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR,"the contract lifecycle is not let you to operator it.");
				}
				bean.setLifecycle(ContractLifeCycle.SIMPLE_CHECKED);
			}else if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.APPLY_DISPRICE.getVal())){
				//ContractOperateType.enumOf(operType) == ContractOperateType.APPLY_DISPRICE;
				bean.setLifecycle(ContractLifeCycle.SIMPLE_CHECKED);
			}else{
				throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR,"the contract lifecycle is not let you to operator it.");
			}
		}else if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKED || lifeCycle == ContractLifeCycle.FULL_TAKEOVERING){
			//bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERED);
			//处理第二次议价的同意议价的操作（正常情况下同意则导致第二次议价通过，但是第二次议价的买发起第一次也是调同意接口因此这时候不能将议价结束）
			if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.APPLY_DISPRICE.getVal())){
				//ContractOperateType.enumOf(operType) == ContractOperateType.APPLY_DISPRICE;
				if(lifeCycle == ContractLifeCycle.SIMPLE_CHECKED){
					//刚好第二次议价的买家第一次的同意，必须等待卖的同意才能算议价通过
					bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERING);
				}else{
					//其他安装正常的逻辑结束议价
					bean.setLifecycle(ContractLifeCycle.FULL_TAKEOVERED);
					isFullTakeOvered =  true;
				}
			}else{
				throw new ServiceException(ServiceErrorCode.CONTRACT_JUST_ALLOW_APPLY_DISPRICE_ERROR,"you can not validate pass the contract, then you just apply disprice the contract, then the value is 7. ");
			}
		}else{
			throw new ServiceException(ServiceErrorCode.CONTRACT_LIFECYCLEIS_ERROR,"the contract lifecycle is not let you to operator it.");
		}
		iContractInfoDAO.update(bean);
		TOrderOperations entity = new TOrderOperations();
		entity.setId(getKey(DataSystemConstant.CONTRACTOPERATIONID));
		entity.setOid(contractId);
		entity.setOperator(operator);
		entity.setOperationtime(DateUtil.getNowDate());
		entity.setOrderstatus(bean.getLifecycle());
		entity.setPlid(pid);
		entity.setType(ContractOperateType.enumOf(operType));
		StringBuffer result = new StringBuffer(operatorName);
		if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.APPLY_DISPRICE.getVal())){
			result.append(MessagesUtil.getMessage("CONTRACT_APPLY_DISPRICE_TIPS"));
		}else if(StringUtils.equalsIgnoreCase(operType,ContractOperateType.VALIDATE_PASS.getVal())){
			result.append(MessagesUtil.getMessage("CONTRACT_VALIDATE_PASS_TIPS"));
		}
		entity.setResult(result.toString());
		entity.setRemark(result.toString());
		iContractOperationDAO.save(entity);
		//买家同意全量验收议价后，告诉卖家.
		if(isFullTakeOvered){
			MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,bean.getSellerid(),SystemMessageContent.getMsgContentOfContractReceivingEndToBuyer(contractId));
			mi.setSendSystemMsg(true);
			mi.setSendPushMsg(true);
			mesgSender.msgSend(mi);

			MessageInfoBean mi1 = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_CONTRACT_ING,contractId,bean.getBuyerid(),SystemMessageContent.getMsgContentOfContractReceiptGoods(contractId));
			mi1.setSendSystemMsg(true);
			mi1.setSendPushMsg(true);
			mesgSender.msgSend(mi1);
		}

		return entity;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForList(java.lang.String, java.lang.String)
	 */
	public List<TOrderOperations> queryForList(String contractId, String type) {
		return iContractOperationDAO.queryForList(contractId, type);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForList(java.lang.String)
	 */
	public List<TOrderOperations> queryForList(String contractId) {
		return this.iContractOperationDAO.queryForList(contractId);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.contract.IContractOperationService#queryForListWithOIDAndOper(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TOrderOperations> queryForListWithOIDAndOper(String contractId,
			String operator) {
		return iContractOperationDAO.queryForListWithOIDAndOper(contractId, operator);
	}

}
