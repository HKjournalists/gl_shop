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
 * Create Date  : 2014年9月12日 上午11:03:06
 */
public interface OrderFindInfo extends IBaseEnum{
	
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
		 * 购买
		 */
		ORDER_TYPE_BUY(1,"购买"), 
		/**
		 * 出售
		 */
		ORDER_TYPE_SELL(2,"出售"); 
		
		private int val;
		
		private String text;
		
		private OrderTypeEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static OrderTypeEnum enumOf(int value){
			for (OrderTypeEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			OrderTypeEnum ore = enumOf(value);
			if(ore != null){
				return ore.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 询单大状态
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
		ORDER_OVERALL_STATUS_EFFECTIVE(0,"有效"), 
		/**
		 * 无效
		 */
		ORDER_OVERALL_STATUS_INVALID(1,"无效");
		
		private int val;
		
		private String text;
		
		private OrderOverallStatusEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static OrderOverallStatusEnum enumOf(int value){
			for (OrderOverallStatusEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			OrderOverallStatusEnum oose = enumOf(value);
			if(oose != null){
				return oose.text;
			}
			return null;
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
		ORDER_STATUS_YES(0,"有效，已发布"),
		/**
		 * 无效，审核不通过
		 */
		ORDER_STATUS_NO(1,"无效，审核不通过"),
		/**
		 * 无效，已产生合同
		 */
		ORDER_STATUS_CLOSE(2,"无效，已产生合同"),
		/**
		 * 无效，销售光，销售量为0（全部兄弟子合同状态）
		 */
		ORDER_STATUS_ZERO(3,"无效，销售光，销售量为0"),
		/**
		 * 无效，到期失效
		 */
		ORDER_STATUS_FAILURE(4,"无效，到期失效"),
		/**
		 * 无效，已取消
		 */
		ORDER_STATUS_CANCEL(5,"无效，已取消"),
		/**
		 * 无效，已删除
		 */
		ORDER_STATUS_DELETE(6,"无效，已删除");
		
		
		private int val;
		
		private String text;
		
		private OrderStatusEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static OrderStatusEnum enumOf(int value){
			for (OrderStatusEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			OrderStatusEnum os = enumOf(value);
			if(os != null){
				return os.text;
			}
			return null;
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
		
		/**
		 * 己方指定
		 */
		ORDER_ADDRESS_TYPE_OWN(1,"己方指定"),
		/**
		 * 对方指定
		 */
		ORDER_ADDRESS_TYPE_OTHER(2,"对方指定");
		
		private int val;
		private String text;
		
		public String getText() {
			return text;
		}
		
		private OrderAddressTypeEnum(int val,String text){
			this.text = text;
			this.val = val;
		}
		public int getVal() {
			return val;
		}
		
		public static OrderAddressTypeEnum enumOf(int value){
			for (OrderAddressTypeEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			OrderAddressTypeEnum oate = enumOf(value);
			if(oate != null){
				return oate.text;
			}
			return null;
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
		
		/**
		 * 单地发布
		 */
		ORDER_MORE_AREA_NO("1","单地发布"),
		/**
		 * 多地发布
		 */
		ORDER_MORE_AREA_YES("2","多地发布");
		
		private String val;
		
		private String text;
		
		private OrderMoreAreaEnum(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static OrderMoreAreaEnum enumOf(String value){
			for (OrderMoreAreaEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			OrderMoreAreaEnum omae = enumOf(value);
			if(omae != null){
				return omae.text;
			}
			return null;
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
		
		ITEM_STATUS_APPLY(0,"已申请，未处理"), // 已申请，未处理
		ITEM_STATUS_SUCCESS(1,"已处理，撮合成功"),// 已处理，撮合成功
		ITEM_STATUS_FAILURE(2,"已处理，撮合失败");// 已处理，撮合失败
		
		private int val;
		
		private String text;
		
		private OrderItemEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static OrderItemEnum enumOf(int value){
			for (OrderItemEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			OrderItemEnum oie = enumOf(value);
			if(oie != null){
				return oie.text;
			}
			return null;
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
	public enum MatchingTypeEnum implements OrderFindInfo {
		
		/**
		 * 标记感兴趣
		 */
		MATCHING_TYPE_APPLY(0,"标记感兴趣"), 
		/**
		 * 询单匹配
		 */
		MATCHING_TYPE_ORDERFIND(1,"询单匹配"),
		/**
		 * 身份匹配
		 */
		ITEM_STATUS_IDENTITY(2,"身份匹配");
		
		private int val;
		
		private String text;
		
		private MatchingTypeEnum(int val,String text){
			this.val = val;
			this.text = text;
		}
		
		public int getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static MatchingTypeEnum enumOf(int value){
			for (MatchingTypeEnum os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			MatchingTypeEnum oie = enumOf(value);
			if(oie != null){
				return oie.text;
			}
			return null;
		}
		
	}

}
