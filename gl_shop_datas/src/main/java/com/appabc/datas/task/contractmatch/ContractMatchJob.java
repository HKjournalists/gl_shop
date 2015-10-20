/**
 *
 */
package com.appabc.datas.task.contractmatch;

import java.util.Calendar;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.appabc.bean.pvo.TOrderFind;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description : 新的需要撮合成合同的询单任务
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月2日 下午5:05:11
 */
public class ContractMatchJob extends BaseJob {
	
	private IOrderFindService orderFindService = (IOrderFindService)super.ac.getBean("IOrderFindService");
	private ICompanyInfoService companyInfoService = (ICompanyInfoService)super.ac.getBean("ICompanyInfoService");
	private TaskService taskService = (TaskService)super.ac.getBean("taskService");
	
	private static boolean isRunning = false; // 是否正在运行
	private static int runNum = 0;
	
	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		logUtil.info("=======>runNum:" + runNum++);
		logUtil.info("=======>isRunning:" + isRunning);
		if(isRunning == true){
			logUtil.info("=======>return");
			return;
		}
		
		isRunning = true;
		// 将新的供求加入任务列表
		List<TOrderFind> ofList = orderFindService.queryNewListForTask();
		logUtil.info("=======>NEW ORDER_FIND NUM:" + ofList.size());
		
		Task<?> task = null;
		for (TOrderFind of : ofList) {
			try {
				task = new Task();
				task.setCreateTime(Calendar.getInstance().getTime());
				task.setType(TaskType.MatchOrderRequest);
				task.setCompany(companyInfoService.query(of.getCid()));
				task.setObjectId(of.getId());
				logUtil.info("=======>add fid:" + of.getId()+", matching NUM:"+of.getMatchingnum());
				taskService.createTask(task); // 创建任务
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 将失效的供求，并在任务列表中的任务删除
		List<TOrderFind> ofInvalidList = orderFindService.queryInvalidListForTask();
		logUtil.info("=======>Invalid ORDER FIND NUM:" + ofInvalidList.size());
		
		for (TOrderFind of : ofInvalidList) {
			try {
				this.taskService.delByTypeAndObjectId(TaskType.MatchOrderRequest, of.getId());
				logUtil.info("=======>del fid:" + of.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		isRunning = false;
	}

}
