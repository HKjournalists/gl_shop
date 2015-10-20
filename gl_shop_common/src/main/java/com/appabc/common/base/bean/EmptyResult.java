package com.appabc.common.base.bean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月20日 下午12:05:18
 */

public class EmptyResult extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private String mesg;

	/**  
	 * mesg  
	 *  
	 * @return  the mesg  
	 * @since   1.0.0  
	 */
	
	public String getMesg() {
		return mesg;
	}

	/**  
	 * @param mesg the mesg to set  
	 */
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	
}
