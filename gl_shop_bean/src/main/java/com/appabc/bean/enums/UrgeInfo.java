package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;
import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午2:23:01
 */

public interface UrgeInfo extends IBaseEnum {

	
	public enum UrgeType implements UrgeInfo
	{
		URGE_VERIFY("0","催促认证"), 
		URGE_DEPOSIT("1","催促保证金");
		
		private String val;
		
		private String text;
		
		private UrgeType(String val,String text){
			this.val=val;
			this.text = text;
		}

		public String getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static UrgeType enumOf(String value){
			for (UrgeType ut : values()) {
				if (StringUtils.equalsIgnoreCase(ut.val,value)) {
					return ut;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			UrgeType ut = enumOf(value);
			if(ut != null){
				return ut.text;
			}
			return null;
	    }
	}
	
	public enum UrgeStatus implements UrgeInfo
	{
		NEED_URGE("0","需要催促"), 
		NO_URGE("1","不需催促");
		
		private String val;
		
		private String text;
		
		private UrgeStatus(String val,String text){
			this.val=val;
			this.text = text;
		}

		public String getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static UrgeStatus enumOf(String value){
			for (UrgeStatus us : values()) {
				if (StringUtils.equalsIgnoreCase(us.val,value)) {
					return us;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			UrgeStatus us = enumOf(value);
			if(us != null){
				return us.text;
			}
			return null;
	    }
	}
	
}
