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
 * Create Date  : 2015年6月5日 上午11:50:39
 */
public interface ClientEnum extends IBaseEnum {
	
	/**
	 * @Description : 客户端下载渠道
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年6月5日 上午11:55:59
	 */
	public enum ChannelType implements ClientEnum {
		
		/**
		 * 网站下载版
		 */
		CHANNEL_TYPE_WEB_SITE(0,"网站"),
		/**
		 * APPSTORE下载版
		 */
		CHANNEL_TYPE_APPSTORE(1,"APPSTORE");
		
		
		private int val;
		
		private String text;
		
		private ChannelType(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static ChannelType enumOf(int value){
			for (ChannelType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			ChannelType as = enumOf(value);
			if(as != null){
				return as.text;
			}
			return null;
	    }
		
	}
	
	public enum ClientType implements ClientEnum
	{
		/**
		 * ANDROID版
		 */
		ANDROID(0,"ANDROID"),
		/**
		 * IOS版
		 */
		IOS(1,"IOS");
		
		
		private int val;
		
		private String text;
		
		private ClientType(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static ClientType enumOf(int value){
			for (ClientType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			ClientType as = enumOf(value);
			if(as != null){
				return as.text;
			}
			return null;
	    }
	}

}
