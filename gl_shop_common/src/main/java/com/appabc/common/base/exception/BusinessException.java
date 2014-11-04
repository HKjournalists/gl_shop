package com.appabc.common.base.exception;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月3日 下午9:29:23
 */

public class BusinessException extends RuntimeException {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

	private int errorCode = 0;
	
	public BusinessException(){
		super();
	}
	
	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(int errorCode,String message){
		super(message);
		this.setErrorCode(errorCode);
	}
	
	public BusinessException(Throwable cause){
		super(cause);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BusinessException(int errorCode,String msg, Throwable cause) {
		super(msg, cause);
		this.setErrorCode(errorCode);
	}

	/**  
	 * errorCode  
	 *  
	 * @return  the errorCode  
	 * @since   1.0.0  
	*/  
	
	public int getErrorCode() {
		return errorCode;
	}

	/**  
	 * @param errorCode the errorCode to set  
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
