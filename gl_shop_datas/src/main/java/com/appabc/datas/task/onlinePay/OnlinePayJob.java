package com.appabc.datas.task.onlinePay;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPayThirdInfoService;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月4日 下午4:44:50
 */

public class OnlinePayJob extends BaseJob {

	private IPayThirdInfoService iPayThirdInfoService = (IPayThirdInfoService)ac.getBean("IPayThirdInfoService");
	
	private IPassPayService iPassPayService = (IPassPayService)ac.getBean("IPassPayLocalService");
	
	/* (non-Javadoc)  
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)  
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		List<TPayThirdOrgInfo> result = iPayThirdInfoService.queryForListWithStatus(TradeStatus.REQUEST);
		for(TPayThirdOrgInfo bean : result){
			try {
				logUtil.info("the report to union pay trade is : " + bean.getId() +" -- " + bean.getOid());
				iPassPayService.reportToUnionPayTradeResult(bean.getOid());
			} catch (ServiceException e) {
				logUtil.error(e);
			}
		}
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
	}

}
