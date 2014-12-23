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
 * Create Date  : 2014年9月22日 下午2:10:09
 */
public interface SmsInfo extends IBaseEnum{
	
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
		SEND_TYPE_REGISTER("REGISTER","注册"),
		/**
		 * 普通发送
		 */
		SEND_TYPE_COMMON("COMMON","普通发送");
		
		private String val;
		
		private String text;
		
		private SmsSendTypeEnum(String val,String text){
			this.val=val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static SmsSendTypeEnum enumOf(String value){
			for (SmsSendTypeEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			SmsSendTypeEnum sste = enumOf(value);
			if(sste != null){
				return sste.text;
			}
			return null;
	    }
		
	}
	
}
