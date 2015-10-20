/**
 * com.appabc.quartz.tool.job.ContractConfirmGoodsInfoJob.java
 *
 * 2014年10月29日 下午2:53:37
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.confirmgoodsinfo;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.utils.DateUtil;
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
 * @Create_Date  : 2014年10月29日 下午2:53:37
 */

public class ContractConfirmGoodsInfoJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");

	private IContractOperationService iContractOperationService = (IContractOperationService)ac.getBean("IContractOperationService");

	/* (non-Javadoc)
	 * @see com.appabc.quartz.tool.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.UNINSTALLED_GOODS);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		int ContractConfirmReceiveGoodsLimitNum = ContractCostDetailUtil.getContractConfirmReceiveGoodsLimitNum();
		Date now = DateUtil.getNowDate();
		for(TOrderInfo bean : result){
			TOrderOperations b = iContractOperationService.queryForListWithOidAndCidAndType(bean.getId(), bean.getSellerid(), ContractOperateType.CONFIRM_UNINSTALLGOODS);
			Date d = null;
			if(result != null){
				d = b.getOperationtime();
			} else {
				d = bean.getUpdatetime();
			}
			//int days = DateUtil.getDifferDayWithTwoDate(bean.getUpdatetime(), DateUtil.getNowDate());
			int hours = DateUtil.getDifferHoursWithTwoDate(d, now);
			if(hours >= ContractConfirmReceiveGoodsLimitNum){
				try {
					iContractOperationService.jobAutoConfirmGoodsInfoContract(bean, ToolsConstant.SYSTEMCID);
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
