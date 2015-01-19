/**
 *
 */
package com.appabc.bean.enums;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 系统日志枚举
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月16日 下午4:19:12
 */
public interface SysLogEnum extends IBaseEnum{
	
	/**
	 * @Description : 日志类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年1月16日 下午4:25:40
	 */
	public enum LogType implements SysLogEnum{
		
		/**
		 * 待定,001
		 */
		LOG_TYPE_001(0,""), 
		/**
		 * 待定,002
		 */
		LOG_TYPE_002(1,"");
		
		private int val;
		
		private String text;
		
		private LogType(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static LogType enumOf(int value){
			for (LogType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			LogType te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
	}
	
	/**
	 * @Description : 日志级别
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年1月16日 下午4:25:40
	 */
	public enum LogLevel implements SysLogEnum{
		
		/**
		 * 致命错误
		 */
		LOG_LEVEL_FATAL (0,"致命错误"), 
		/**
		 * 一般错误
		 */
		LOG_LEVEL_ERROR (1,"一般错误"), 
		/**
		 * 警告
		 */
		LOG_LEVEL_WARN (2,"警告"), 
		/**
		 * 信息提示
		 */
		LOG_LEVEL_INFO (3,"信息提示");
		
		private int val;
		
		private String text;
		
		private LogLevel(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static LogLevel enumOf(int value){
			for (LogLevel os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			LogLevel te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
	}
	
}
