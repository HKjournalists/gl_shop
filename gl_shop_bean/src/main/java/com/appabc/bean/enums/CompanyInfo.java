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
 * Create Date  : 2014年9月22日 下午5:05:44
 */
public interface CompanyInfo extends IBaseEnum{
	
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
		AUTH_STATUS_NO("0","未认证"),
		/**
		 * 已认证
		 */
		AUTH_STATUS_YES("1","已认证"),
		/**
		 * 审核中
		 */
		AUTH_STATUS_ING("2","审核中");
		
		
		private String val;
		
		private String text;
		
		private CompanyAuthStatus(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static CompanyAuthStatus enumOf(String value){
			for (CompanyAuthStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String val) {
			CompanyAuthStatus cas = enumOf(val);
			if(cas != null){
				return cas.text;
			}
			return null;
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
		BAIL_STATUS_NO("0","未缴纳"),
		/**
		 * 已缴纳
		 */
		BAIL_STATUS_YES("1","已缴纳");
		
		
		private String val;
		
		private String text;
		
		private CompanyBailStatus(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static CompanyBailStatus enumOf(String value){
			for (CompanyBailStatus os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String val) {
			CompanyBailStatus cbs = enumOf(val);
			if(cbs != null){
				return cbs.text;
			}
			return null;
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
		COMPANY_TYPE_ENTERPRISE("0","企业"),
		/**
		 * 船舶
		 */
		COMPANY_TYPE_SHIP("1","船舶"),
		/**
		 * 个人
		 */
		COMPANY_TYPE_PERSONAL("2","个人");
		
		
		private String value;
		
		private String text;
		
		private CompanyType(String val,String text){
			this.value = val;
			this.text = text;
		}
		
		public String getVal() {
			return value;
		}
		
		public String getText() {
			return text;
		}
		
		public static CompanyType enumOf(String value){
			for (CompanyType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.value, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String val) {
			CompanyType ct = enumOf(val);
			if(ct != null){
				return ct.text;
			}
			return null;
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
		ADDRESS_STATUS_OTHER(0,"其它卸货地址"),
		/**
		 * 默认卸货地址
		 */
		ADDRESS_STATUS_DEFULT(1,"默认卸货地址");
		
		
		private int val;
		
		private String text;
		
		private AddressStatus(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static AddressStatus enumOf(int value){
			for (AddressStatus os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			AddressStatus as = enumOf(value);
			if(as != null){
				return as.text;
			}
			return null;
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
	public enum PersonalAuthSex implements CompanyInfo {
		
		/**
		 * 女，Female
		 */
		PERSONAL_SEX_F(0,"女"),
		/**
		 * 男，Male
		 */
		PERSONAL_SEX_M(1,"男");
		
		
		private int val;
		
		private String text;
		
		private PersonalAuthSex(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText() {
			return text;
		}
		
		public static PersonalAuthSex enumOf(int value){
			for (PersonalAuthSex os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			PersonalAuthSex as = enumOf(value);
			if(as != null){
				return as.text;
			}
			return null;
		}
		
	}

}
