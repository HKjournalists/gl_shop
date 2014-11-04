/**
 *
 */
package com.appabc.datas.enums;

/**
 * @Description : 企业提款人枚举信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月14日 下午8:49:28
 */
public interface AcceptBankInfo {
	
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
		ACCEPT_BANK_STATUS_OTHER(0),
		/**
		 * 默认提款人
		 */
		ACCEPT_BANK_STATUS_DEFAULT(1);
		
		private int val;
		
		private AcceptBankStatus(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 提款人认证状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月14日 下午9:30:57
	 */
	public enum AcceptAuthStatus implements AcceptBankInfo {
		
		/**
		 * 审核不通过
		 */
		AUTH_STATUS_CHECK_NO(0),
		/**
		 * 审核通过
		 */
		AUTH_STATUS_CHECK_YES(1),
		/**
		 * 审核中
		 */
		AUTH_STATUS_CHECK_ING(2);
		
		private int val;
		
		private AcceptAuthStatus(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
	}

}
