package com.appabc.bean.bo;

import com.appabc.bean.pvo.TSystemMessage;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月10日 下午3:49:00
 */

public class SystemMessageEx extends TSystemMessage {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -8088177637954073712L;

	private String qyPhone;
	
	private String operId;
	
	private String operName;

	/**  
	 * qyPhone  
	 *  
	 * @return  the qyPhone  
	 * @since   1.0.0  
	 */
	
	public String getQyPhone() {
		return qyPhone;
	}

	/**  
	 * @param qyPhone the qyPhone to set  
	 */
	public void setQyPhone(String qyPhone) {
		this.qyPhone = qyPhone;
	}

	/**  
	 * operId  
	 *  
	 * @return  the operId  
	 * @since   1.0.0  
	 */
	
	public String getOperId() {
		return operId;
	}

	/**  
	 * @param operId the operId to set  
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}

	/**  
	 * operName  
	 *  
	 * @return  the operName  
	 * @since   1.0.0  
	 */
	
	public String getOperName() {
		return operName;
	}

	/**  
	 * @param operName the operName to set  
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
}
