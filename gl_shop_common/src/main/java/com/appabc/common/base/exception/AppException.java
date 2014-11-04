package com.appabc.common.base.exception;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月23日 下午2:13:32
 */

public class AppException extends BaseException {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	public AppException(){
		super();
	}
	
	public AppException(String msg){
		super(msg);
	}
	
	public AppException(Throwable cause){
		super(cause);
	}
	
	public AppException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
