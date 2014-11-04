/**
 *
 */
package com.appabc.tools.enums;

/**
 * @Description : 用户账号
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月24日 下午3:24:17
 */
public interface UserInfo {
	
	/**
	 * @Description : 用户状态枚举类
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月2日 下午7:29:25
	 */
	public enum UserStatus implements UserInfo {
		
		
		USER_STATUS_NORMAL("0"), // 正常
		USER_STATUS_LIMITED("1"), // 受限
		USER_STATUS_DISABLE("2"); // 禁用
		
		private String val;
		
		private UserStatus(String val){
			this.val=val;
		}

		public String getVal() {
			return val;
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
		 * android 客户端
		 */
		CLIENT_TYPE_ANDROID("0"),
		/**
		 * IOS 客户端
		 */
		CLIENT_TYPE_IOS("1");
		
		private String val;
		
		private ClientTypeEnum(String val){
			this.val=val;
		}
		
		public String getVal() {
			return val;
		}
		
		
	}

}
