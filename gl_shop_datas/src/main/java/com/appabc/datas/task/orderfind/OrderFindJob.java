/**
 * com.appabc.tools.schedule.task.orderfind.OrderFindJob.java
 *
 * 2014年10月31日 下午1:49:12
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.task.orderfind;

import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.utils.ToolsConstant;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月31日 下午1:49:12
 */

public class OrderFindJob extends BaseJob {

	private IOrderFindService iOrderFindService = (IOrderFindService)ac.getBean("IOrderFindService");
	
	private TaskService taskService = (TaskService)ac.getBean("taskService");

	/* (non-Javadoc)
	 * @see com.appabc.tools.schedule.utils.BaseJob#doExecutionJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void doExecutionJob(JobExecutionContext context) {
		logUtil.info(this.getClass().getName());
		logUtil.info(context.getTrigger().getName());
		List<TOrderFind> result = iOrderFindService.getParentOrderFindByStatusAndOverallStatus(OrderStatusEnum.ORDER_STATUS_YES, OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE);
		logUtil.info(" the order find num is : " + result != null ? result.size() : 0);
		Date now = DateUtil.getNowDate();
		for(TOrderFind bean : result){
			boolean flag = DateUtil.isTargetGtSourceDate(bean.getEndtime(), now);
			if(flag){
				logUtil.info(" the time out order find id is : " + bean.getId());
				try{
					iOrderFindService.jobAutoTimeoutOrderFindWithSystem(bean, ToolsConstant.SYSTEMCID);
				}catch(Exception e){
					logUtil.debug(e.getMessage(), e);
				}
			}
		}
		logUtil.info(CollectionUtils.isNotEmpty(result) ? result.size() : result);
		//add the task release out of date.
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("finished", false);
		List<Task> tasks = taskService.queryForList(params, TaskType.MatchOrderRequest);
		logUtil.info(" UnFinished the task num is : " + tasks != null ? tasks.size() : 0);
		for(Task<?> t : tasks){
			int h = DateUtil.getDifferHoursWithTwoDate(t.getUpdateTime(), now);
			try{
				if(h>=24){
					logUtil.info(" the release task id is : " + t.getId());
					taskService.releaseTask(t.getId(), TaskType.MatchOrderRequest);
				}
			}catch(Exception e){
				logUtil.debug(e.getMessage(), e);
			}
		}
		logUtil.info(CollectionUtils.isNotEmpty(tasks) ? tasks.size() : tasks);
	}
}
