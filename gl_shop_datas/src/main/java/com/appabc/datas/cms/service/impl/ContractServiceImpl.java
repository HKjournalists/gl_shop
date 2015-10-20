package com.appabc.datas.cms.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.TaskArbitrationInfo;
import com.appabc.bean.bo.TaskContractInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.datas.cms.dao.IContractDAO;
import com.appabc.datas.cms.service.IContractService;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年6月30日 下午2:13:45
 */

@Service
public class ContractServiceImpl implements IContractService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private IContractDAO iContractDAO;
	
	@Autowired
    private IContractInfoService contractInfoService;
	
	@Autowired
    private IContractOperationService contractOperationService;
	
	@Autowired
    private IContractArbitrationService contractArbitrationService;
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#customerMakeAndMatchTOrderNoTask(com.appabc.bean.bo.OrderFindAllBean, java.lang.String, java.lang.String, java.lang.String)  
	 */
	@Override
	public void customerMakeAndMatchTOrderNoTask(OrderFindAllBean orderAll, String sellerId, String buyerId, User operator) throws ServiceException {
		if (StringUtils.isEmpty(sellerId) || orderAll == null || StringUtils.isEmpty(buyerId) || operator == null) {
			String message = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, message);
		}
		TOrderInfo order = contractInfoService.makeAndMatchTOrderWithCustomService(orderAll,sellerId,buyerId,operator.getUserName());
		
        Task<TOrderInfo> task = new Task<>();
        task.setObjectId(order.getId());
        task.setCreateTime(DateUtil.getNowDate());
        task.setType(TaskType.ContractConfirm);
        task.setFinished(false);
        task.setOwner(operator);
        TCompanyInfo company = new TCompanyInfo();
        company.setId(orderAll.getOfBean().getCid());
        task.setCompany(company);
        taskService.createTask(task);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#customerMakeAndMatchTOrderHaveTask(com.appabc.bean.bo.OrderFindAllBean)  
	 */
	@Override
	public void customerMakeAndMatchTOrderHaveTask(OrderFindAllBean order,String beCid,User operator,Task<OrderAllInfor> task) throws ServiceException {
		if (StringUtils.isEmpty(beCid) || order == null || task == null || operator == null) {
			String message = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, message);
		}
        contractInfoService.makeAndMatchATOrderInfo(order, beCid, operator.getRealName());
        taskService.completeTask(task.getId(), TaskType.MatchOrderRequest);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#getUnconfirmOrderInfoList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskContractInfo> getConfirmContractOrderList(QueryContext<TaskContractInfo> qContext) {
		if(qContext == null){
			return null;
		}
		return iContractDAO.getConfirmContractOrderList(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void add(TaskContractInfo entity) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void modify(TaskContractInfo entity) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TaskContractInfo entity) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TaskContractInfo query(TaskContractInfo entity) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)  
	 */
	@Override
	public TaskContractInfo query(Serializable id) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TaskContractInfo> queryForList(TaskContractInfo entity) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)  
	 */
	@Override
	public List<TaskContractInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskContractInfo> queryListForPagination(QueryContext<TaskContractInfo> qContext) {
		return null;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#contractArbitractionProcess(boolean, java.lang.String, java.lang.String, java.lang.String, double, double, java.lang.String)  
	 */
	@Override
	public void contractArbitractionProcess(boolean isTrade,String aid,User user,double num,double price,String note) throws ServiceException{
        contractArbitractionProcess(isTrade, aid, user, num, price, 0, note);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#getContractArbitrationList(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TaskArbitrationInfo> getContractArbitrationList(QueryContext<TaskArbitrationInfo> qContext) {
		if(qContext == null){
			return null;
		}
		return iContractDAO.getContractArbitrationList(qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.service.IContractService#contractArbitractionProcess(boolean, java.lang.String, com.appabc.datas.cms.vo.User, double, double, double, java.lang.String)  
	 */
	@Override
	public void contractArbitractionProcess(boolean isTrade, String aid,
			User user, double num, double price, double finalAmount, String note)
			throws ServiceException {
		if (StringUtils.isEmpty(aid) || user == null) {
			String message = MessagesUtil.getMessage(DataSystemConstant.EXCEPTIONKEY_PARAMETER_IS_NOT_ALLOW_NULL_ERROR, Locale.forLanguageTag("datas"));
			throw new ServiceException(ServiceErrorCode.PARAMETER_IS_NULL, message);
		}
		String id = String.valueOf(user.getId());
		
		TOrderArbitration toa = contractArbitrationService.query(aid);
		TOrderOperations op = contractOperationService.query(toa.getLid());
        TOrderInfo order = contractInfoService.query(op.getOid());
		if(finalAmount > 0.0){
			contractArbitrationService.contractArbitractionProcess(isTrade, aid, id, user.getRealName(), num, price, finalAmount, note);
		}else{
			contractArbitrationService.contractArbitractionProcess(isTrade, aid, id, user.getRealName(), num, price, note);
		}
		
		Task<TOrderInfo> task = new Task<>();
        task.setObjectId(aid);
        task.setCreateTime(DateUtil.getNowDate());
        task.setType(TaskType.ContractArbitrate);
        task.setFinished(false);
        task.setOwner(user);
        TCompanyInfo company = new TCompanyInfo();
        company.setId(order.getSellerid());
        task.setCompany(company);
        taskService.createTask(task);
	}

}

