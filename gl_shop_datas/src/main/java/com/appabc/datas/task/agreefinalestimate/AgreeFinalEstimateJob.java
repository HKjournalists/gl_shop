package com.appabc.datas.task.agreefinalestimate;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.ToolsConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月16日 上午11:47:52
 */

public class AgreeFinalEstimateJob extends BaseJob {
	
	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractDisPriceService iContractDisPriceService = (IContractDisPriceService)ac.getBean("IContractDisPriceService");
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.CONFIRMING_GOODS_FUNDS);
		entity.setStatus(ContractStatus.DOING);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		if(CollectionUtils.isNotEmpty(result)){
			for(TOrderInfo bean : result){
				try {
					iContractDisPriceService.jobAutoAgreeContractFinalEstimate(bean, ToolsConstant.SYSTEMCID, ToolsConstant.SYSTEMCNAME, StringUtils.EMPTY);
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
