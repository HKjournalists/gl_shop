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

	private int errorCode = 1000;
	
	public BaseException(){
		super();
	}
	
	public BaseException(String msg){
		super(msg);
	}
	
	public BaseException(int errorCode,String msg){
		this(msg);
		this.errorCode = errorCode;
	}
	
	public BaseException(Throwable cause){
		super(cause);
	}
	
	public BaseException(int errorCode,Throwable cause){
		this(cause);
		this.errorCode = errorCode;
	}
	
	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BaseException(int errorCode,String msg, Throwable cause) {
		this(msg, cause);
		this.errorCode = errorCode;
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
