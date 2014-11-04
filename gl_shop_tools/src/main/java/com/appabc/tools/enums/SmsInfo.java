/**
 *
 */
package com.appabc.tools.enums;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午2:10:09
 */
public interface SmsInfo {
	
	/**
	 * @Description : 短信类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月22日 下午2:12:10
	 */
	public enum SmsTypeEnum implements SmsInfo {
		
		/**
		 * 验证码
		 */
		SMS_TEMP_TYPE_PIN("PIN"), 
		/**
		 * 通知
		 */
		SMS_TEMP_TYPE_INFORM("INFORM"); 
		
		private String val;
		
		private SmsTypeEnum(String val){
			this.val=val;
		}

		public String getVal() {
			return val;
		}

		
	}
	
	/**
	 * @Description : 发送类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月24日 下午9:39:35
	 */
	public enum SmsSendTypeEnum implements SmsInfo {
		
		/**
		 * 注册
		 */
		SEND_TYPE_REGISTER("REGISTER"),
		/**
		 * 普通发送
		 */
		SEND_TYPE_COMMON("COMMON");
		
		private String val;
		
		private SmsSendTypeEnum(String val){
			this.val=val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 短信业务类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月22日 下午2:14:23
	 */
	public enum BusinessTypeEnum implements SmsInfo {
		
		/**
		 * 业务类型：用户注册
		 */
		SMS_BUSINESS_TYPE_REGISTER("REGISTER");
		
		private String val;
		
		private BusinessTypeEnum(String val){
			this.val=val;
		}
		
		public String getVal() {
			return val;
		}
		
		
	}

}
