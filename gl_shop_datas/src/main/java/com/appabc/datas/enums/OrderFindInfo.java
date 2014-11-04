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
 * Create Date  : 2014年9月12日 上午11:03:06
 */
public interface OrderFindInfo {
	
	/**
	 * @Description : 买家，卖家
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月12日 上午11:05:14
	 */
	public enum OrderTypeEnum implements OrderFindInfo {
		
		/**
		 * 买家
		 */
		ORDER_TYPE_BUY(1), 
		/**
		 * 卖家
		 */
		ORDER_TYPE_SELL(2); 
		
		private int val;
		
		private OrderTypeEnum(int val){
			this.val = val;
		}
		public int getVal() {
			return val;
		}

	}
	
	/**
	 * @Description : 合同大状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月19日 下午9:53:22
	 */
	public enum OrderOverallStatusEnum implements OrderFindInfo {
			
		/**
		 * 有效
		 */
		ORDER_OVERALL_STATUS_EFFECTIVE(0), 
		/**
		 * 无效
		 */
		ORDER_OVERALL_STATUS_INVALID(1);
		
		private int val;
		
		private OrderOverallStatusEnum(int val){
			this.val = val;
		}
		public int getVal() {
			return val;
		}
	}
	
	/**
	 * @Description : 询单状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月12日 下午9:49:21
	 */
	public enum OrderStatusEnum implements OrderFindInfo {
		
		/**
		 * 有效，已发布
		 */
		ORDER_STATUS_YES(0),
		/**
		 * 无效，审核不通过
		 */
		ORDER_STATUS_NO(1),
		/**
		 * 无效，已产生合同
		 */
		ORDER_STATUS_CLOSE(2),
		/**
		 * 无效，销售光，销售量为0（全部兄弟子合同状态）
		 */
		ORDER_STATUS_ZERO(3),
		/**
		 * 无效，到期失效
		 */
		ORDER_STATUS_FAILURE(4),
		/**
		 * 无效，已取消
		 */
		ORDER_STATUS_CANCEL(5),
		/**
		 * 无效，已删除，预留
		 */
		ORDER_STATUS_DELETE(6);
		
		
		private int val;
		
		private OrderStatusEnum(int val){
			this.val = val;
		}
		public int getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 卸货地址指定
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月12日 上午11:21:59
	 */
	public enum OrderAddressTypeEnum implements OrderFindInfo {
		
		ORDER_ADDRESS_TYPE_BUY(1), // 买家
		ORDER_ADDRESS_TYPE_SELL(2); // 卖卖
		
		private int val;
		
		private OrderAddressTypeEnum(int val){
			this.val = val;
		}
		public int getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 单地发布和多地发布
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月16日 上午10:10:12
	 */
	public enum OrderMoreAreaEnum implements OrderFindInfo {
		
		ORDER_MORE_AREA_NO("1"), // 单地发布
		ORDER_MORE_AREA_YES("2"); // 多地发布
		
		private String val;
		
		private OrderMoreAreaEnum(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
		
	}
	
	/**
	 * @Description : 交易意向状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年9月16日 上午10:17:19
	 */
	public enum OrderItemEnum implements OrderFindInfo {
		
		ITEM_STATUS_APPLY(0), // 已申请，未处理
		ITEM_STATUS_SUCCESS(1),// 已处理，撮合成功
		ITEM_STATUS_FAILURE(2);// 已处理，撮合失败
		
		private int val;
		
		private OrderItemEnum(int val){
			this.val = val;
		}
		
		public int getVal() {
			return val;
		}
		
	}

}
