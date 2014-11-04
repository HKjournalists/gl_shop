package com.appabc.common.base.exception;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午9:22:01
 */

public abstract class BaseException extends Exception {

	/**  
	 * serialVersionUID:用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 4917469711588504305L;

	public BaseException(){
		super();
	}
	
	public BaseException(String msg){
		super(msg);
	}
	
	public BaseException(Throwable cause){
		super(cause);
	}
	
	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
