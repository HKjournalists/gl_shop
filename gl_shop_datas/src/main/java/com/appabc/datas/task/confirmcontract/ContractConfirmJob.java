/**
 * com.appabc.quartz.tool.job.ContractConfirmJob.java
 *
 * 2014年10月27日 下午6:34:14
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmcontract;


import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.exception.ServiceException;
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
 * @Create_Date  : 2014年10月27日 下午6:34:14
 */

public class ContractConfirmJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	/* (non-Javadoc)
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.DRAFTING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		Date now = DateUtil.getNowDate();
		double wd = ContractCostDetailUtil.getContractDraftConfirmLimitNumWD();
		for(TOrderInfo bean : result){
			long diffTimeMillis = DateUtil.getDifferTimeMillisTwoDate(bean.getCreatime(), now);
			long definedTimeMillis = (long)RandomUtil.mulRound(wd,(60 * 60 * 1000));
			if(diffTimeMillis>=definedTimeMillis){
				try {
					iContractOperationService.jobAutoDraftConfirmTimeoutFinish(bean, ToolsConstant.SYSTEMCID);
				} catch (ServiceException e) {
					logUtil.error(e);
				}
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
	}

}
