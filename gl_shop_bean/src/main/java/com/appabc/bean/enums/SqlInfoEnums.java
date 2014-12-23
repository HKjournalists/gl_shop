/**
 *
 */
package com.appabc.bean.enums;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月18日 下午9:02:07
 */
public interface SqlInfoEnums {
	
	/**
	 * @Description : SQL排序
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年11月18日 下午9:04:49
	 */
	public enum OrderBySort implements SqlInfoEnums {
		
		/**
		 * 正序
		 */
		SORT_ASC(0,"ASC"),
		/**
		 * 倒序
		 */
		SORT_DESC(1,"DESC");
		
		private int val;
		
		private String text;

		private OrderBySort(int val,String text){
			this.val = val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static OrderBySort enumOf(int value){
			for (OrderBySort os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getOrderText(int value) {
			OrderBySort obs = enumOf(value);
			if(obs != null){
				return obs.text;
			}
			return SORT_ASC.getText(); // 其它情况默认正序
	    }
		
	}

}
