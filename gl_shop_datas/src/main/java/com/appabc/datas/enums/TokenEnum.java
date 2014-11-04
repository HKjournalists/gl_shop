/**
 *
 */
package com.appabc.datas.enums;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月17日 下午7:35:20
 */
public enum TokenEnum {
	
	/**
	 * 存在
	 */
	TOKEN_STATUS_EXIST(0), 
	/**
	 * 不存在
	 */
	TOKEN_STATUS_NOTEXIST(1),
	/**
	 * 过期
	 */
	TOKEN_STATUS_EXPIRED(2);
	
	private int val;
	
	private TokenEnum(int val){
		this.val=val;
	}

	public int getVal() {
		return val;
	}

}
