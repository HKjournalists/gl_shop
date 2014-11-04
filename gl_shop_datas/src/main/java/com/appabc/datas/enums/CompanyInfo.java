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
 * Create Date  : 2014年9月22日 下午5:05:44
 */
public interface CompanyInfo {
	
	/**
	 * @Description : 企业认证状态 (是否认证)
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月22日 下午5:10:04
	 */
	public enum CompanyAuthStatus implements CompanyInfo {
		
		/**
		 * 未认证
		 */
		AUTH_STATUS_NO("0"),
		/**
		 * 已认证
		 */
		AUTH_STATUS_YES("1"),
		/**
		 * 审核中
		 */
		AUTH_STATUS_ING("2");
		
		
		private String val;
		
		private CompanyAuthStatus(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 公司保证金是缴纳状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月25日 下午3:42:04
	 */
	public enum CompanyBailStatus implements CompanyInfo {
		
		/**
		 * 未缴纳
		 */
		BAIL_STATUS_NO("0"),
		/**
		 * 已缴纳
		 */
		BAIL_STATUS_YES("1");
		
		
		private String val;
		
		private CompanyBailStatus(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 公司类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月22日 下午5:20:32
	 */
	public enum CompanyType implements CompanyInfo {
		
		/**
		 * 企业
		 */
		COMPANY_TYPE_ENTERPRISE("0"),
		/**
		 * 船舶
		 */
		COMPANY_TYPE_SHIP("1"),
		/**
		 * 个人
		 */
		COMPANY_TYPE_PERSONAL("2");
		
		
		private String val;
		
		private CompanyType(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 企业联系人状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月26日 上午11:05:17
	 */
	public enum ContactStatus implements CompanyInfo {
		
		/**
		 * 其它联系人
		 */
		CONTACT_STATUS_OTHER(0),
		/**
		 * 默认联系人
		 */
		CONTACT_STATUS_DEFULT(1);
		
		
		private int val;
		
		private ContactStatus(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 公司卸货地址状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月28日 下午6:11:40
	 */
	public enum AddressStatus implements CompanyInfo {
		
		/**
		 * 其它卸货地址
		 */
		ADDRESS_STATUS_OTHER(0),
		/**
		 * 默认卸货地址
		 */
		ADDRESS_STATUS_DEFULT(1);
		
		
		private int val;
		
		private AddressStatus(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
	}

}
