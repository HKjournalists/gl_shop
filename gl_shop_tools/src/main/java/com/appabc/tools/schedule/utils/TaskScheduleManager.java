/**  
 * com.appabc.tools.schedule.TaskScheduleManager.java  
 *   
 * 2014年10月27日 下午6:29:58  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.schedule.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import com.appabc.common.spring.BeanLocator;
import com.appabc.tools.bean.ScheduleInfoBean;
import com.appabc.tools.service.schedule.IScheduleService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月27日 下午6:29:58
 */

public class TaskScheduleManager {
	
	private static Scheduler sched;
	
	private static final String DEFAULT_TASKPROP_PATH = "/TaskProp.properties";
	
	private static final String XML_FILE_EXTENSION = ".xml";
	
	private IScheduleService iScheduleService = (IScheduleService)BeanLocator.getBean("IScheduleService");
	
	protected static final Logger logUtil = Logger.getLogger(TaskScheduleManager.class);
	
	protected void initScheduler() throws SchedulerException{
		if(sched == null){
			SchedulerFactory sf = new StdSchedulerFactory();
			try {
				sched = sf.getScheduler();
			} catch (SchedulerException e) {
				logUtil.debug("init the Scheduler is failure, ex is: ", e);
				throw e;
			}
		}
	}
	
	protected Properties loadProperties() throws IOException{
		Properties prop = new Properties();
		ClassPathResource resource = new ClassPathResource(DEFAULT_TASKPROP_PATH, TaskScheduleManager.class);
		InputStream is = null;
		try {
			is = resource.getInputStream();
			String filename = resource.getFilename();
			if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
				prop.loadFromXML(is);
			}else {
				prop.load(is);
			}
		} catch (IOException e) {
			logUtil.debug(e.getMessage(), e);
			throw e;
		}finally {
			is.close();
		}
		return prop;
	}
	
	protected Object loadInstanceWithClass(String className) throws Exception{
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
		try {
			Class<?> cls = ClassUtils.forName(className, cl);
			return cls.newInstance();
		} catch (Exception e) {
			logUtil.error(e.getMessage(), e);
			logUtil.error("can not load the class. because the class file is inexistence, class name is : ["+className + "].");
			return null;
		}
	}
	
	protected void loadSchedulerWithProp() throws Exception{
		Properties prop = this.loadProperties();
		for(Entry<Object,Object> entry : prop.entrySet()){
			Object key = entry.getKey();
			Object value = entry.getValue();
			if(key == null || value == null){
				continue;
			}
			Object o = loadInstanceWithClass(key.toString());
			if(o == null){
				continue;
			}
			BaseJob bj = (BaseJob)o;
			JobDetail jobDe = new JobDetail(bj.NAME,bj.GROUP,bj.getClass());
			
			Object j = loadInstanceWithClass(value.toString());
			if(j == null){
				continue;
			}
			BaseCronTrigger bct = (BaseCronTrigger)j;
			bct.setJobName(bj.NAME);
			bct.setJobGroup(bj.GROUP);
			
			try {
				sched.addJob(jobDe, true);
				sched.scheduleJob(bct);
				logUtil.info(" add the schedule name ["+bj.NAME+"] to "+sched.getClass().getName()+" is success.");
			} catch (SchedulerException e) {
				logUtil.debug(e.getMessage(), e);
				throw e;
			}
		}
		logUtil.info(" add the ["+prop.size()+"]  schedules to "+sched.getClass().getName()+" are successfull.");
	}
	
	protected void loadSchedulerWithDB() throws Exception {
		ScheduleInfoBean entity = new ScheduleInfoBean();
		entity.setIsValid(true);
		List<ScheduleInfoBean> result = iScheduleService.queryForList(entity);
		if(result == null || CollectionUtils.isEmpty(result)){
			return ;
		}
		int size = 0;
		for(ScheduleInfoBean bean : result){
			String jobClsName = bean.getJobClassName();
			String triggerClsName = bean.getTriggerClassName();
			if(jobClsName == null || triggerClsName == null){
				continue;
			}
			Object o = loadInstanceWithClass(jobClsName);
			if(o == null){
				continue;
			}
			BaseJob bj = (BaseJob)o;
			JobDetail jobDe = null;
			if(StringUtils.isNotEmpty(bean.getJobName()) && StringUtils.isNotEmpty(bean.getJobGroup())){
				jobDe = new JobDetail(bean.getJobName(),bean.getJobGroup(),bj.getClass());
			}else{
				jobDe = new JobDetail(bj.NAME,bj.GROUP,bj.getClass());
			}
			
			Object j = loadInstanceWithClass(triggerClsName);
			if(j == null){
				continue;
			}
			BaseCronTrigger bct = (BaseCronTrigger)j;
			if(StringUtils.isNotEmpty(bean.getTriggerName()) && StringUtils.isNotEmpty(bean.getTriggerGroup())){
				bct.setName(bean.getTriggerName());
				bct.setGroup(bean.getTriggerGroup());
			}
			
			if(StringUtils.isNotEmpty(bean.getJobName()) && StringUtils.isNotEmpty(bean.getJobGroup())){
				bct.setJobName(bean.getJobName());
				bct.setJobGroup(bean.getJobGroup());
			}else{
				bct.setJobName(bj.NAME);
				bct.setJobGroup(bj.GROUP);
			}
			
			try {
				sched.addJob(jobDe, true);
				sched.scheduleJob(bct);
				logUtil.info(" add the schedule name ["+bean.getName()+"] to "+sched.getClass().getName()+" is success.");
				size++;
			} catch (SchedulerException e) {
				logUtil.debug(e.getMessage(), e);
				throw e;
			}
		}
		logUtil.info(" add the ["+size+"]  schedules to "+sched.getClass().getName()+" are successfull.");
	}
	
	protected void loadScheduler() throws Exception{
		this.loadSchedulerWithProp();
		this.loadSchedulerWithDB();
	}
	
	protected void startScheduler(){
		if(sched != null){
			try {
				sched.start();
			} catch (SchedulerException e) {
				logUtil.debug("start the Scheduler is failure, ex is: ", e);
			}
		}else{
			logUtil.debug("The Scheduler is : "+sched);
		}
	}
	
	protected void shutDown(){
		if(sched != null){
			try {
				sched.shutdown();
			} catch (SchedulerException e) {
				logUtil.debug("shut down the Scheduler is failure, ex is: ", e);
			}
		}else{
			logUtil.debug("The Scheduler is : "+sched);
		}
	}
	
	protected void run() {
		try {
			initScheduler();
			loadScheduler();
			startScheduler();
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		TaskScheduleManager tsm = new TaskScheduleManager();
		tsm.run();
		try {
			Thread.sleep(100L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
		tsm.shutDown();
	}
	
	public static void start(){
		TaskScheduleManager tsm = new TaskScheduleManager();
		tsm.run();
		logUtil.debug("start the task schedule manager...");
	}
	
	public static void stop(){
		if(sched != null){
			try {
				sched.shutdown(true);
			} catch (SchedulerException e) {
				logUtil.debug("shut down the Scheduler is failure, ex is: ", e);
			}
		}else{
			logUtil.debug("The Scheduler is : "+sched);
		}
	}
	
}
