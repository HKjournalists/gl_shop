/**
 *
 */
package com.appabc.datas.enums;

/**
 * @Description : 卸货地址状态
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 下午2:35:49
 */
public enum AddressEnum {
	
	/**
	 * 非默认
	 */
	ADDRESS_STATUS_DEFAULT_NO(0),
	/**
	 * 默认
	 */
	ADDRESS_STATUS_DEFAULT_YES(1);
	
	private int val;

	private AddressEnum(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
	
	
}
