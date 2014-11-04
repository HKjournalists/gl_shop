/**
 *
 */
package com.appabc.tools.enums;

/**
 * @Description : 推送枚举
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月28日 下午2:36:41
 */
public interface PushEnums {
	
	/**
	 * @Description : 推送业务类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月22日 下午2:12:10
	 */
	public enum BusinessTypeEnum implements PushEnums {
		
		/**
		 * 企业资料认证
		 */
		BUSINESS_TYPE_COMPANY_AUTH(0), 
		/**
		 * 企业收款人认证
		 */
		BUSINESS_TYPE_COMPANY_PAYEE_AUTH(1), 
		/**
		 * 询单，供求信息
		 */
		BUSINESS_TYPE_ORDER_FIND(2), 
		/**
		 * 合同签订
		 */
		BUSINESS_TYPE_CONTRACT_SIGN(3), 
		/**
		 * 合同进行中
		 */
		BUSINESS_TYPE_CONTRACT_ING(4), 
		/**
		 * 钱包账户
		 */
		BUSINESS_TYPE_ACCOUNT(5); 
		
		private int val;
		
		private BusinessTypeEnum(int val){
			this.val=val;
		}

		public int getVal() {
			return val;
		}

		
	}

}
