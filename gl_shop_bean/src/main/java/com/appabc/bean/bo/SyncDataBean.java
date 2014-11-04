/**
 *
 */
package com.appabc.bean.bo;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月11日 下午2:09:48
 */
public class SyncDataBean { 
	
	private Object Data;
	private String timeStamp;
	
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		Data = data;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
