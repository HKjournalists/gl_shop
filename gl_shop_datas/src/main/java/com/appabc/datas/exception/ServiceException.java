package com.appabc.datas.exception;

import com.appabc.common.base.exception.BaseException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月23日 下午2:27:58
 */

public class ServiceException extends BaseException {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	public ServiceException(){
		super();
	}
	
	public ServiceException(String msg){
		super(msg);
	}
	
	public ServiceException(int errorCode,String msg){
		super(errorCode,msg);
	}
	
	public ServiceException(int errorCode,Throwable cause){
		super(errorCode,cause);
	}
	
	public ServiceException(int errorCode,String msg, Throwable cause) {
		super(errorCode,msg, cause);
	}
	
}
