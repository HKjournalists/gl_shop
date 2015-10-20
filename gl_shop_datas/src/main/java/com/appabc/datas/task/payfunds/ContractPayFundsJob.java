/**
 * com.appabc.quartz.tool.job.ContractPayFundsJob.java
 *
 * 2014年10月29日 上午10:59:50
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.payfunds;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.ToolsConstant;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import java.util.List;


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月29日 上午10:59:50
 */

public class ContractPayFundsJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");
	
	/* (non-Javadoc)
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.SINGED);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		logUtil.info(" the Contract LifeCycle is Singed will be buyer unpay finished and final estimate. ");
		for(TOrderInfo bean : result){
			try {
				iContractOperationService.jobAutoTimeoutContractPayFundFinish(bean, ToolsConstant.SYSTEMCID, ToolsConstant.SYSTEMCNAME);
			} catch (ServiceException e) {
				logUtil.error(e);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
		entity.setLifecycle(ContractLifeCycle.IN_THE_PAYMENT);
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		logUtil.info(" the Contract LifeCycle is In the PayMent will be buyer unpay finished and final estimate. ");
		for(TOrderInfo bean : result1){
			try {
				iContractOperationService.jobAutoTimeoutContractPayFundFinish(bean, ToolsConstant.SYSTEMCID, ToolsConstant.SYSTEMCNAME);
			} catch (ServiceException e) {
				logUtil.error(e);
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result1) ? result1.size() : result1);
	}

}
