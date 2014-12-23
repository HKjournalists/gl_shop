/**
 *
 */
package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月23日 上午11:33:49
 */
public interface PushInfo extends IBaseEnum{
	
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
		CONFIG_STATUS_AVAILABLE(0,"可用的"),
		/**
		 * 不可用
		 */
		ADDRESS_STATUS_UNAVAILABLE(1,"不可用");
		
		private int val;
		
		private String text;

		private ConfigStatusEnum(int val,String text){
			this.val = val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static ConfigStatusEnum enumOf(int value){
			for (ConfigStatusEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			ConfigStatusEnum cse = enumOf(value);
			if(cse != null){
				return cse.text;
			}
			return null;
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
		
		public static ConfigTypeEnum enumOf(String value){
			for (ConfigTypeEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
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
		MSG_TYPE_PAYLOAD(0,"透传"),
		/**
		 * 通知
		 */
		MSG_TYPE_NOTICE(1,"通知");
		
		private int val;
		
		private String text;
		
		private MsgTypeEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static MsgTypeEnum enumOf(int value){
			for (MsgTypeEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			MsgTypeEnum mte = enumOf(value);
			if(mte != null){
				return mte.text;
			}
			return null;
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
		PUSH_TYPE_SINGLE(1,"单个"),
		/**
		 * 多个
		 */
		PUSH_TYPE_LIST(2,"多个"),
		/**
		 * 用户组,按标签
		 */
		PUSH_TYPE_GROUP(2,"用户组,按标签"),
		/**
		 * 所有用户
		 */
		PUSH(3,"所有用户");
		
		private int val;
		
		private String text;
		
		private PushTypeEnum(int val,String text){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static PushTypeEnum enumOf(int value){
			for (PushTypeEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			PushTypeEnum pte = enumOf(value);
			if(pte != null){
				return pte.text;
			}
			return null;
	    }
		
	}

}
