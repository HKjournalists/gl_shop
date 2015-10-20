package com.appabc.datas.task.cancelcontract;


import com.appabc.bean.enums.ContractInfo.ContractCancelType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.ToolsConstant;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月13日 上午11:59:54
 */

public class CancelContractJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	private IContractCancelService iContractCancelService = (IContractCancelService)ac.getBean("IContractCancelService");

	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.CANCELING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result){
			//根据取消确认类型和取消中合同生命周期来过滤合同的操作记录
			TOrderOperations operator = new TOrderOperations();
			operator.setOid(bean.getId());
			operator.setOperator(bean.getSellerid());
			operator.setType(ContractOperateType.CANCEL_CONFIRM);
			operator.setOrderstatus(ContractLifeCycle.CANCELING);
			List<TOrderOperations> sellerResult = iContractOperationService.queryForList(operator);
			operator.setOperator(bean.getBuyerid());
			List<TOrderOperations> buyerResult = iContractOperationService.queryForList(operator);

			TOrderCancel cancel = new TOrderCancel();
			if(CollectionUtils.isNotEmpty(sellerResult)){
				cancel.setLid(sellerResult.get(0).getId());
			}
			cancel.setCanceltype(ContractCancelType.DUPLEX_CANCEL);
			List<TOrderCancel> cancelResult = iContractCancelService.queryForList(cancel);
			if(CollectionUtils.isEmpty(cancelResult) && CollectionUtils.isNotEmpty(buyerResult)){
				cancel.setLid(buyerResult.get(0).getId());
				cancelResult = iContractCancelService.queryForList(cancel);
			}
			Date now = DateUtil.getNowDate();
			//如果超过了双方取消的时限
			if(DateUtil.getDifferHoursWithTwoDate(bean.getUpdatetime(), now)>=ContractCostDetailUtil.getContractDuplexCancelLimitNum() && CollectionUtils.isEmpty(cancelResult)){
				List<TOrderOperations> operationLists = iContractOperationService.queryForList(bean.getId());
				if(CollectionUtils.isNotEmpty(operationLists)){
					TOrderOperations lastOperator = operationLists.get(operationLists.size()-2);
					try {
						iContractCancelService.jobAutoRollbackCancelContract(bean, ToolsConstant.SYSTEMCID, lastOperator.getOrderstatus());
					} catch (ServiceException e) {
						logUtil.error(e);
					}
				}
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
	}

}
