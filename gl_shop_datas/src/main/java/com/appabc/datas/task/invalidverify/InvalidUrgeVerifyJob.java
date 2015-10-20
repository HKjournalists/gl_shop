package com.appabc.datas.task.invalidverify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.JobExecutionContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.tools.schedule.utils.BaseJob;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月7日 下午7:22:49
 */

public class InvalidUrgeVerifyJob extends BaseJob {

	private TaskService taskService = (TaskService)ac.getBean("taskService");
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		Date now = DateUtil.getNowDate();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("finished", false);
		List<Task> Verifytasks = taskService.queryForList(params, TaskType.UrgeVerify);
		for(Task t : Verifytasks){					
			int h = DateUtil.getDifferHoursWithTwoDate(t.getUpdateTime(), now);
			try{
				if(h>=24){				
					taskService.releaseTask(t.getId(), TaskType.UrgeVerify);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		List<Task> Deposittasks = taskService.queryForList(params, TaskType.UrgeDeposit);
		for(Task t : Deposittasks){					
			int h = DateUtil.getDifferHoursWithTwoDate(t.getUpdateTime(), now);
			try{
				if(h>=24){				
					taskService.releaseTask(t.getId(), TaskType.UrgeDeposit);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
	}
}
