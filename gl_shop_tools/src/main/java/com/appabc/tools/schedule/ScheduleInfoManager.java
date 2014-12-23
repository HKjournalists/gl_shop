/**  
 * com.appabc.tools.schedule.ScheduleInfoManager.java  
 *   
 * 2014年11月20日 下午5:19:12  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.tools.bean.ScheduleInfoBean;
import com.appabc.tools.schedule.utils.BaseCronTrigger;
import com.appabc.tools.schedule.utils.BaseJob;
import com.appabc.tools.service.schedule.IScheduleService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月20日 下午5:19:12
 */

public class ScheduleInfoManager {
	
	@Autowired
	private IScheduleService iScheduleService;
	
	private boolean enableDisableScheduleInfo(ScheduleInfoBean sibean,boolean enableOrDisable){
		if(sibean == null || StringUtils.isEmpty(sibean.getId())){
			return false;
		}
		ScheduleInfoBean bean = iScheduleService.query(sibean.getId());
		bean.setIsValid(enableOrDisable);
		iScheduleService.modify(bean);
		return true;
	}
	
	public boolean registerScheduleInfo(Class<? extends BaseJob> jobCls,Class<? extends BaseCronTrigger> cronTriggerCls){
		if(jobCls == null || cronTriggerCls == null){
			return false;
		}
		ScheduleInfoBean siBean = this.registerScheduleInfo(jobCls.getSimpleName()+" : "+cronTriggerCls.getSimpleName(),"Job is: "+jobCls.getSimpleName()+" CronTrigger: "+cronTriggerCls.getSimpleName(), jobCls, cronTriggerCls);
		return siBean != null && StringUtils.isNotEmpty(siBean.getId()) && RandomUtil.str2int(siBean.getId()) > 0;
	}
	
	public ScheduleInfoBean registerScheduleInfo(String name,String desc,Class<? extends BaseJob> jobCls,Class<? extends BaseCronTrigger> cronTriggerCls){
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(desc) || jobCls == null || cronTriggerCls == null){
			return null;
		}
		ScheduleInfoBean entity = new ScheduleInfoBean();
		entity.setName(name);
		entity.setDesc(desc);
		entity.setJobClassName(jobCls.getName());
		entity.setTriggerClassName(cronTriggerCls.getName());
		entity.setIsValid(true);
		entity.setCreateDate(DateUtil.getNowDate());
		iScheduleService.add(entity);
		return entity;
	}
	
	public boolean enableScheduleInfo(ScheduleInfoBean sibean){
		return this.enableDisableScheduleInfo(sibean, true);
	}
	
	public boolean disableScheduleInfo(ScheduleInfoBean sibean){
		return this.enableDisableScheduleInfo(sibean, false);
	}
	
	public boolean removeScheduleInfo(ScheduleInfoBean sibean){
		if(sibean == null){
			return false;
		}
		iScheduleService.delete(sibean);
		return true;
	}
	
	public boolean removeScheduleInfo(String schInfoId){
		if(StringUtils.isEmpty(schInfoId)){
			return false;
		}
		iScheduleService.delete(schInfoId);
		return true;
	}
	
}
