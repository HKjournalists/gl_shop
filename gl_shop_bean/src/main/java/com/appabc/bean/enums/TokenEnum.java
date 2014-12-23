/**
 *
 */
package com.appabc.bean.enums;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月17日 下午7:35:20
 */
public enum TokenEnum implements IBaseEnum{
	
	/**
	 * 存在
	 */
	TOKEN_STATUS_EXIST(0,"存在"), 
	/**
	 * 不存在
	 */
	TOKEN_STATUS_NOTEXIST(1,"不存在"),
	/**
	 * 过期
	 */
	TOKEN_STATUS_EXPIRED(2,"过期");
	
	private int val;
	
	private String text;
	
	private TokenEnum(int val,String text){
		this.val=val;
		this.text = text;
	}

	public int getVal() {
		return val;
	}

	public String getText(){
		return text;
	}

	public static TokenEnum enumOf(int value){
		for (TokenEnum os : values()) {
			if (os.val == value) {
				return os;
			}
		}
		return null;
	}
	
	public static String getText(int value) {
		TokenEnum te = enumOf(value);
		if(te != null){
			return te.text;
		}
		return null;
    }
}
