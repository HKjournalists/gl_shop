package com.appabc.common.utils;

import org.apache.commons.lang3.StringUtils;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月20日 下午1:41:40
 */

public class CommonResult extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private static CommonResult cr = new CommonResult();
	
	private boolean success = true;
	
	private String message;
	
	private Object data;
	
	public CommonResult(){}
	
	public CommonResult(boolean success,String message){
		this.success = success;
		this.message = message;
	}
	
	public CommonResult(boolean success,Object data){
		this.success = success;
		this.data = data;
	}
	
	public CommonResult(boolean success,String message,Object data){
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public static final CommonResult getCommonResult(boolean success,String message){
		if(cr==null){
			cr = new CommonResult();
		}
		cr.setSuccess(success);
		if(StringUtils.isNotBlank(message)){
			cr.setMessage(message);
		}
		return cr;
	}
	
	public static final CommonResult getCommonResult(boolean success,String message,Object data){
		if(cr==null){
			cr = new CommonResult();
		}
		cr.setSuccess(success);
		if(StringUtils.isNotBlank(message)){
			cr.setMessage(message);
		}
		if(data != null){
			cr.setData(data);
		}
		return cr;
	}

	/**  
	 * success  
	 *  
	 * @return  the success  
	 * @since   1.0.0  
	 */
	
	public boolean isSuccess() {
		return success;
	}

	/**  
	 * @param success the success to set  
	 */
	public void setSuccess(boolean success) {
		this.success = success;
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
	
	public Object getData() {
		return data;
	}

	/**  
	 * @param data the data to set  
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
