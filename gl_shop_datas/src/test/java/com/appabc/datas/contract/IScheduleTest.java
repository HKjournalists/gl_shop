package com.appabc.datas.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.common.utils.DateUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.task.confirmcontract.ContractConfirmJob;
import com.appabc.datas.task.confirmcontract.ContractConfirmTrigger;
import com.appabc.tools.bean.ScheduleInfoBean;
import com.appabc.tools.service.schedule.IScheduleService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月3日 下午4:48:16
 */

public class IScheduleTest extends AbstractDatasTest {
	
	@Autowired
	private IScheduleService iScheduleService;
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	@Rollback(value=true)
	public void mainTest() {
		ScheduleInfoBean entity = new ScheduleInfoBean();
		entity.setName("first test schedule");
		entity.setDesc("first test schedule");
		/*entity.setJobName(jobName);
		entity.setJobGroup(jobGroup);*/
		entity.setJobClassName(ContractConfirmJob.class.getName());
		
		/*entity.setTriggerName(triggerName);
		entity.setTriggerGroup(triggerGroup);*/
		entity.setTriggerClassName(ContractConfirmTrigger.class.getName());
		
		entity.setIsValid(true);
		entity.setCreateDate(DateUtil.getNowDate());
		iScheduleService.add(entity);
	}

}
