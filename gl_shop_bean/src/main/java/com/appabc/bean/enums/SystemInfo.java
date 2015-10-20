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
 * Create Date  : 2015年3月10日 下午2:49:47
 */
public interface SystemInfo extends IBaseEnum {
	
	/**
	 * @Description : 系统种类:如HTTP,WEB.
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * @Create_Date  : 2015年3月10日 下午2:52:22
	 */
	public enum SystemCategory implements SystemInfo {
		
		SYSTEM_CATEGORY_HTTP(0,"长江电商客户端接入系统"),SYSTEM_CATEGORY_WEB(1,"长江电商后台管理系统");
		
		private int val;
		
		private String text;
		
		private SystemCategory(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}
		
		public static SystemCategory enumOf(int value){
			for (SystemCategory os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			SystemCategory te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 服务器环境枚举
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年3月10日 下午2:52:22
	 */
	public enum ServerEnvironmentEnum implements SystemInfo {
		
		/**
		 * 正式环境
		 */
		SERVER_RELEASE(0,"RELEASE"),
		/**
		 * 开发环境
		 */
		SERVER_DVPT(1,"DVPT"),
		/**
		 * 测试环境
		 */
		SERVER_TEST(2,"TEST");
		
		private int val;
		
		private String text;
		
		private ServerEnvironmentEnum(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static ServerEnvironmentEnum enumOf(int value){
			for (ServerEnvironmentEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			ServerEnvironmentEnum te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
	}

}
