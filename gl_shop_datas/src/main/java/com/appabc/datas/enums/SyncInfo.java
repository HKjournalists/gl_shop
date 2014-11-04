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
 * Create Date  : 2014年9月29日 下午3:24:54
 */
public interface SyncInfo {
	
	/**
	 * @Description : 同步分类
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月29日 下午3:29:22
	 */
	public enum SyncType implements SyncInfo {
		
		/**
		 * 商品大类
		 */
		SYNC_TYPE_GOOD(1), 
		/**
		 * 商品子类
		 */
		SYNC_TYPE_GOOD_CHILDREN(2), 
		/**
		 * 港口分段
		 */
		SYNC_TYPE_RIVER_SECTION(3), 
		/**
		 * 交易地域
		 */
		SYNC_TYPE_AREA(4), 
		/**
		 * 平台支持银行
		 */
		SYNC_TYPE_BANK(5), 
		/**
		 * 系统配置参数
		 */
		SYNC_TYPE_SYS_PARAM(6), 
		/**
		 * 其它
		 */
		SYNC_TYPE_OTHER(10);
		
		
		private int val;
		
		private SyncType(int val){
			this.val=val;
		}

		public int getVal() {
			return val;
		}

		
	}

}
