package com.appabc.common.utils;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年8月25日 下午8:20:24
 */

public class CheckResult {
	
	/**错误码*/
	private int errorCode;
	/**信息内容*/
	private String message;
	/**对象*/
	private Object[] data;
	/**操作是否成功*/
	private boolean isError = false;
	 
	/**
	 * 错误的构造
	 * 创建一个新的实例 CheckResult.  
	 *  
	 * @param errorCode
	 * @param errorMessage
	 */
	public CheckResult(int errorCode,String errorMessage) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.isError = true;
	}
	
	public CheckResult(String message,Object... obj){
		this.data = obj;
		this.message = message;
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

	/**  
	 * message  
	 *  
	 * @return  the message  
	 * @since   1.0.0  
	 */
	
	public String getMessage() {
		return message;
	}

	/**  
	 * @param message the message to set  
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

	/**  
	 * data  
	 *  
	 * @return  the data  
	 * @since   1.0.0  
	 */
	
	public Object[] getData() {
		return data;
	}

	/**  
	 * @param data the data to set  
	 */
	public void setData(Object[] data) {
		this.data = data;
	}

	/**  
	 * isError  
	 *  
	 * @return  the isError  
	 * @since   1.0.0  
	 */
	
	public boolean isError() {
		return isError;
	}

	/**  
	 * @param isError the isError to set  
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
	
	
}
