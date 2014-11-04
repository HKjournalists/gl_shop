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
 * Create Date  : 2014年10月23日 上午11:33:49
 */
public interface PushInfo {
	
	/**
	 * @Description : 推送配置状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月23日 上午11:39:36
	 */
	public enum ConfigStatusEnum implements PushInfo {
		
		/**
		 * 可用的
		 */
		CONFIG_STATUS_AVAILABLE(0),
		/**
		 * 不可用
		 */
		ADDRESS_STATUS_UNAVAILABLE(1);
		
		private int val;

		private ConfigStatusEnum(int val){
			this.val = val;
		}

		public int getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 推送类型状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月23日 上午11:39:36
	 */
	public enum ConfigTypeEnum implements PushInfo {
		
		/**
		 * Android
		 */
		CONFIG_TYPE_ANDROID("Android"),
		/**
		 * IOS
		 */
		CONFIG_TYPE_IOS("IOS");
		
		private String val;
		
		private ConfigTypeEnum(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 推送消息类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月23日 上午11:39:36
	 */
	public enum MsgTypeEnum implements PushInfo {
		
		/**
		 * 透传
		 */
		MSG_TYPE_PAYLOAD(0),
		/**
		 * 通知
		 */
		MSG_TYPE_NOTICE(1);
		
		private int val;
		
		private MsgTypeEnum(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
		
		
	}
	
	/**
	 * @Description : 推送类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月23日 上午11:39:36
	 */
	public enum PushTypeEnum implements PushInfo {
		
		/**
		 * 单个
		 */
		PUSH_TYPE_SINGLE(1),
		/**
		 * 多个
		 */
		PUSH_TYPE_LIST(2),
		/**
		 * 用户组,按标签
		 */
		PUSH_TYPE_GROUP(2),
		/**
		 * 所有用户
		 */
		PUSH(3);
		
		private int val;
		
		private PushTypeEnum(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
		
		
	}


}
