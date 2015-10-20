package com.appabc.datas.task.expirecontract;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractMineService;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.ToolsConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月5日 上午11:59:50
 */

public class ExpireContractJob extends BaseJob {

	private IContractInfoService iContractInfoService = (IContractInfoService)ac.getBean("IContractInfoService");
	
	private IContractMineService iContractMineService = (IContractMineService)ac.getBean("IContractMineService");
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		Date now = DateUtil.getNowDate();
		//过滤为未付货款结束的合同的有效期.
		TOrderInfo entity = new TOrderInfo();
		entity.setLifecycle(ContractLifeCycle.BUYER_UNPAY_FINISHED);
		entity.setStatus(ContractStatus.FINISHED);
		List<TOrderInfo> result = iContractInfoService.queryForList(entity);
		logUtil.info("Enter the Contract Life Cycle with the Buyer unpay Finished to finish the Contract.");
		for(TOrderInfo bean : result){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				try {
					iContractMineService.jobAutoSetupContractExpireDayFinish(bean, ToolsConstant.SYSTEMCID);
				} catch (ServiceException e) {
					logUtil.error(e);
				}
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
		//过滤单方取消结束合同的有效期
		entity.setStatus(ContractStatus.FINISHED);
		entity.setLifecycle(ContractLifeCycle.SINGLECANCEL_FINISHED);
		logUtil.info("Enter the Contract Life Cycle with the Single Cancel Finished to finish the Contract.");
		List<TOrderInfo> result1 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result1){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				try {
					iContractMineService.jobAutoSetupContractExpireDayFinish(bean, ToolsConstant.SYSTEMCID);
				} catch (ServiceException e) {
					logUtil.error(e);
				}
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result1) ? result1.size() : result1);
		//过滤合同的正常结束的有效期
		entity.setStatus(ContractStatus.FINISHED);
		entity.setLifecycle(ContractLifeCycle.NORMAL_FINISHED);
		logUtil.info("Enter the Contract Life Cycle with the Normal Finished to finish the Contract.");
		List<TOrderInfo> result2 = iContractInfoService.queryForList(entity);
		for(TOrderInfo bean : result2){
			int diffDays = DateUtil.getDifferDayWithTwoDate(now, bean.getLimittime());
			if(diffDays <= 0){
				logUtil.info(" set the Contract id is : "+bean.getId()+" and the Contract Life Cycle is "+bean.getLifecycle()+" to the Contract Expire Day List. ");
				try {
					iContractMineService.jobAutoSetupContractExpireDayFinish(bean, ToolsConstant.SYSTEMCID);
				} catch (ServiceException e) {
					logUtil.error(e);
				}
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(CollectionUtils.isNotEmpty(result2) ? result2.size() : result2);
	}

}
