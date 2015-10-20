package com.appabc.datas.task.urgeverify;

import org.quartz.JobExecutionContext;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄木俊
 * @version : 1.0
 * @Create_Date : 2015年9月7日 下午6:06:27
 */

public class UrgeVerifyJob extends BaseJob {

	private ICompanyInfoService companyInfoService = (ICompanyInfoService) super.ac.getBean("ICompanyInfoService");

	@SuppressWarnings("rawtypes")
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		try
		{
			companyInfoService.jobQueryVerifyListForTask();
			companyInfoService.jobQueryDepositListForTask();
		}
		catch (Exception e) {
			logUtil.error(e);
		}	
	}
}
