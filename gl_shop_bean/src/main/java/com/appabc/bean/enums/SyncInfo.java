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
 * Create Date  : 2014年9月29日 下午3:24:54
 */
public interface SyncInfo extends IBaseEnum{
	
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
		 * 商品分类
		 */
		SYNC_TYPE_GOOD_TYPE(1,"商品分类"), 
		/**
		 * 商品
		 */
		SYNC_TYPE_GOODS(2,"商品"), 
		/**
		 * 港口分段
		 */
		SYNC_TYPE_RIVER_SECTION(3,"港口分段"), 
		/**
		 * 交易地域
		 */
		SYNC_TYPE_AREA(4,"交易地域"), 
		/**
		 * 平台支持银行
		 */
		SYNC_TYPE_BANK(5,"平台支持银行"), 
		/**
		 * 系统配置参数
		 */
		SYNC_TYPE_SYS_PARAM(6,"系统配置参数"), 
		/**
		 * 区域省份控制
		 */
		SYNC_AREA_PROVINCE_CONTROL(7,"区域省份控制"), 
		/**
		 * 其它
		 */
		SYNC_TYPE_OTHER(10,"其它");
		
		
		private int val;
		
		private String text;
		
		private SyncType(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static SyncType enumOf(int value){
			for (SyncType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			SyncType st = enumOf(value);
			if(st != null){
				return st.text;
			}
			return null;
	    }
		
	}

}
