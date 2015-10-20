/**
 *
 */
package com.appabc.bean.enums;

import com.appabc.common.base.bean.IBaseEnum;


/**
 * @Description : 企业提款人枚举信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月14日 下午8:49:28
 */
public interface AcceptBankInfo extends IBaseEnum{
	
	/**
	 * @Description : 企业提款人状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月14日 下午8:51:58
	 */
	public enum AcceptBankStatus implements AcceptBankInfo {
		
		/**
		 * 其它提款人
		 */
		ACCEPT_BANK_STATUS_OTHER(0,"其它提款人"),
		/**
		 * 默认提款人
		 */
		ACCEPT_BANK_STATUS_DEFAULT(1,"默认提款人"),
		/**
		 * 已删除提款人
		 */
		ACCEPT_BANK_STATUS_DEL(2,"已删除");
		
		private int val;
		
		private String text;
		
		private AcceptBankStatus(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static AcceptBankStatus enumOf(int value){
			for (AcceptBankStatus os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int val) {
			AcceptBankStatus abs = enumOf(val);
			if(abs!=null){
				return abs.text;
			}
			return null;
	    }
		
	}
	
}
