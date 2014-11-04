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
 * Create Date  : 2014年9月23日 下午2:19:29
 */
public interface AuthRecordInfo {
	
	/**
	 * @Description : 认证审核状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月23日 下午2:27:11
	 */
	public enum AuthRecordStatus implements AuthRecordInfo {
		
		/**
		 * 审核不通过
		 */
		AUTH_STATUS_CHECK_NO("0"),
		/**
		 * 审核通过
		 */
		AUTH_STATUS_CHECK_YES("1"),
		/**
		 * 审核中
		 */
		AUTH_STATUS_CHECK_ING("2"),
		/**
		 * 过期,(重新认证时把前面认证通过的记录改为过期状态)
		 */
		AUTH_STATUS_EXPIRE("3");
		
		
		private String val;
		
		private AuthRecordStatus(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 任务处理状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月23日 下午2:30:40
	 */
	public enum AuthDealStatus implements AuthRecordInfo {
		
		/**
		 * 无人处理
		 */
		AUTH_DEAL_STATUS_NO("0"),
		/**
		 * 有人处理
		 */
		AUTH_DEAL_STATUS_YES("1");
		
		
		private String val;
		
		private AuthDealStatus(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 认证结果
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月23日 下午3:03:20
	 */
	public enum AuthResult implements AuthRecordInfo {
		
		/**
		 * 认证通过
		 */
		AUTH_RESULT_YES("1"),
		/**
		 * 认证不通过
		 */
		AUTH_RESULT_NO("2");
		
		
		private String val;
		
		private AuthResult(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 认证类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月23日 下午8:32:19
	 */
	public enum AuthRecordType implements AuthRecordInfo {
		
		/**
		 * 认证类型：企业认证
		 */
		AUTH_RECORD_TYPE_COMPANY(1),
		/**
		 * 认证类型：提款账户认证
		 */
		AUTH_RECORD_TYPE_BANK(2);
		
		
		private int val;
		
		private AuthRecordType(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
	}

}
