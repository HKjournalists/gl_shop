package com.appabc.common.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * @Description : base log utils
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午4:10:22
 */

public class LogUtil {
	
	protected Logger log = null;
	
	private LogUtil(){
		this.log = Logger.getLogger(LogUtil.class);
	}
	
	private LogUtil(Class<?> clz){
		this.log = Logger.getLogger(clz);
	}
	
	public boolean isEnabledFor(Priority level){
		return this.log.isEnabledFor(level);
	}
	
	public static LogUtil getLogUtil(){
		return new LogUtil();
	} 
	
	public static LogUtil getLogUtil(Class<?> clz){
		return new LogUtil(clz);
	}
	
	public void log(Level level,Object message){
		if(log.isEnabledFor(level)){
			log.log(level, message);
		}
	}
	
	public void log(Level level,Object message,Throwable t){
		if(log.isEnabledFor(level)){
			log.log(level, message,t);
		}
	}
	
	public void info(Object message,Throwable t){
		log(Level.INFO,message,t);
	}
	
	public void info(Object message){
		log(Level.INFO,message);
	}
	
	public void warn(Object message,Throwable t) {
		log(Level.WARN, message,t);
	}
	
	public void warn(Object message) {
		log(Level.WARN, message);
	}
	
	public void fatal(Object message,Throwable t) {
		log(Level.FATAL, message,t);
	}
	
	public void fatal(Object message) {
		log(Level.FATAL, message);
	}
	
	public void error(Object message,Throwable t) {
		log(Level.ERROR, message,t);
	}
	
	public void error(Object message) {
		log(Level.ERROR, message);
	}
	
	public void debug(Object message,Throwable t) {
		log(Level.DEBUG, message,t);
	}
	
	public void debug(Object message) {
		log(Level.DEBUG, message);
		
	}
	
	public boolean isDebugEnabled(){
		return log.isDebugEnabled();
	}
	
	public boolean isInfoEnabled(){
		return log.isInfoEnabled();
	}
	
}
