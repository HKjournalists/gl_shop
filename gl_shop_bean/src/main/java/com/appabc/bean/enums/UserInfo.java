/**
 *
 */
package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 用户账号
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月24日 下午3:24:17
 */
public interface UserInfo extends IBaseEnum{
	
	/**
	 * @Description : 用户状态枚举类
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月2日 下午7:29:25
	 */
	public enum UserStatus implements UserInfo {
		
		
		USER_STATUS_NORMAL("0","正常"), // 正常
		USER_STATUS_LIMITED("1","受限"), // 受限
		USER_STATUS_DISABLE("2","禁用"); // 禁用
		
		private String val;
		
		private String text;
		
		private UserStatus(String val,String text){
			this.val=val;
			this.text = text;
		}

		public String getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static UserStatus enumOf(String value){
			for (UserStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			UserStatus us = enumOf(value);
			if(us != null){
				return us.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 客户端登陆类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月2日 下午7:29:25
	 */
	public enum ClientTypeEnum implements UserInfo {
		
		/**
		 * Android 客户端
		 */
		CLIENT_TYPE_ANDROID("0","Android"),
		/**
		 * iPhone 客户端
		 */
		CLIENT_TYPE_IOS("1","iPhone");
		
		private String val;
		
		private String text;
		
		private ClientTypeEnum(String val,String text){
			this.val=val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static ClientTypeEnum enumOf(String value){
			for (ClientTypeEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			ClientTypeEnum cte = enumOf(value);
			if(cte != null){
				return cte.text;
			}
			return null;
	    }
		
	}

}
