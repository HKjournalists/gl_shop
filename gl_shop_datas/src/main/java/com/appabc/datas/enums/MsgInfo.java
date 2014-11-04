/**
 *
 */
package com.appabc.datas.enums;

/**
 * @Description : 系统消息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月16日 下午2:45:07
 */
public interface MsgInfo {
	
	/**
	 * @Description : 消息状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgStatus implements MsgInfo{
		
		/**
		 * 未读
		 */
		STATUS_IS_READ_NO(0),
		/**
		 * 已读
		 */
		STATUS_IS_READ_YES(1);
		
		private int val;

		private MsgStatus(int val){
			this.val = val;
		}

		public int getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 消息类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgType implements MsgInfo{
		
		/**
		 * 消息类型001，具体待定
		 */
		MSG_TYPE_001(1),
		/**
		 * 消息类型002，具体待定
		 */
		MSG_TYPE_002(2);
		
		private int val;
		
		private MsgType(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 消息业务类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月16日 下午3:33:24
	 */
	public enum MsgBusinessType implements MsgInfo{
		
		/**
		 * 业务类型001，待定
		 */
		MSG_BUSINESS_TYPE_001("1"),
		/**
		 * 业务类型002，待定
		 */
		MSG_BUSINESS_TYPE_002("2");
		
		private String val;
		
		private MsgBusinessType(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
		
	}

}
