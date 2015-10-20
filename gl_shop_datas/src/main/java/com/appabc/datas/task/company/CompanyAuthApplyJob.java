/**
 *
 */
package com.appabc.datas.task.company;

import java.util.Calendar;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description : 新认证申请添加到后台任务表
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月2日 下午2:25:21
 */
public class CompanyAuthApplyJob extends BaseJob {
	
	private IAuthRecordService authRecordService = (IAuthRecordService)super.ac.getBean("authRecordService");
	private ICompanyInfoService companyInfoService = (ICompanyInfoService)super.ac.getBean("ICompanyInfoService");
	private TaskService taskService = (TaskService)super.ac.getBean("taskService");

	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		List<TAuthRecord> arList = authRecordService.queryNewListForNotInTask();
		
		logUtil.info("新认证申请个数："+arList.size());
		
		Task<?> task = null;
		for (TAuthRecord ar : arList) {
			try {
				task = new Task();
				task.setCreateTime(Calendar.getInstance().getTime());
				task.setType(TaskType.VerifyInfo);
				task.setCompany(companyInfoService.query(ar.getCid()));
				task.setObjectId(ar.getId());
				
				taskService.createTask(task); // 创建任务
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}

}
