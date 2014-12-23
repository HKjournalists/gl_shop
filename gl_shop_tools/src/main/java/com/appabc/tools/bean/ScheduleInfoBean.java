/**  
 * com.appabc.tools.bean.ScheduleInfoBean.java  
 *   
 * 2014年11月3日 下午2:59:41  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.bean;

import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月3日 下午2:59:41
 */

public class ScheduleInfoBean extends BaseBean {

	/**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String desc;
	
	private String jobName;
	
	private String jobGroup;
	
	private String jobClassName;
	
	private String triggerName;
	
	private String triggerGroup;
	
	private String triggerClassName;
	
	private boolean isValid;
	
	private Date createDate;

	/**  
	 * name  
	 *  
	 * @return  the name  
	 * @since   1.0.0  
	 */
	
	public String getName() {
		return name;
	}

	/**  
	 * @param name the name to set  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * desc  
	 *  
	 * @return  the desc  
	 * @since   1.0.0  
	 */
	
	public String getDesc() {
		return desc;
	}

	/**  
	 * @param desc the desc to set  
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**  
	 * jobName  
	 *  
	 * @return  the jobName  
	 * @since   1.0.0  
	 */
	
	public String getJobName() {
		return jobName;
	}

	/**  
	 * @param jobName the jobName to set  
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**  
	 * jobGroup  
	 *  
	 * @return  the jobGroup  
	 * @since   1.0.0  
	 */
	
	public String getJobGroup() {
		return jobGroup;
	}

	/**  
	 * @param jobGroup the jobGroup to set  
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**  
	 * jobClassName  
	 *  
	 * @return  the jobClassName  
	 * @since   1.0.0  
	 */
	
	public String getJobClassName() {
		return jobClassName;
	}

	/**  
	 * @param jobClassName the jobClassName to set  
	 */
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	/**  
	 * triggerName  
	 *  
	 * @return  the triggerName  
	 * @since   1.0.0  
	 */
	
	public String getTriggerName() {
		return triggerName;
	}

	/**  
	 * @param triggerName the triggerName to set  
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**  
	 * triggerGroup  
	 *  
	 * @return  the triggerGroup  
	 * @since   1.0.0  
	 */
	
	public String getTriggerGroup() {
		return triggerGroup;
	}

	/**  
	 * @param triggerGroup the triggerGroup to set  
	 */
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	/**  
	 * triggerClassName  
	 *  
	 * @return  the triggerClassName  
	 * @since   1.0.0  
	 */
	
	public String getTriggerClassName() {
		return triggerClassName;
	}

	/**  
	 * @param triggerClassName the triggerClassName to set  
	 */
	public void setTriggerClassName(String triggerClassName) {
		this.triggerClassName = triggerClassName;
	}

	/**  
	 * isValid  
	 *  
	 * @return  the isValid  
	 * @since   1.0.0  
	 */
	
	public boolean getIsValid() {
		return isValid;
	}

	/**  
	 * @param isValid the isValid to set  
	 */
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	/**  
	 * createDate  
	 *  
	 * @return  the createDate  
	 * @since   1.0.0  
	 */
	
	public Date getCreateDate() {
		return createDate;
	}

	/**  
	 * @param createDate the createDate to set  
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
