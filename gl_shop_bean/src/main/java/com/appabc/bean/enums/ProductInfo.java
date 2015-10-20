package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

public interface ProductInfo extends IBaseEnum{
	
	/**
	 * @Description : 单位
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年2月11日 下午2:17:20
	 */
	public enum UnitEnum implements ProductInfo {
		
		/**
		 * 吨
		 */
		UNIT_TON("UNIT001","吨"),
		/**
		 * 立方
		 */
		UNIT_CUBE("UNIT002","立方"),
		/**
		 * 毫米
		 */
		UNIT_MM("UNIT003","mm");
		
		
		private String val;
		
		private String text;
		
		private UnitEnum(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static UnitEnum enumOf(String value){
			for (UnitEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			UnitEnum ue = enumOf(value);
			if(ue != null){
				return ue.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 属性状态
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年11月6日 下午5:00:49
	 */
	public enum PropertyStatusEnum implements ProductInfo {
		
		/**
		 * 其它属性
		 */
		PROPERTY_STATUS_0("0","其它属性"),
		
		/**
		 * 直接属性
		 */
		PROPERTY_STATUS_1("1","直接属性");
		
		
		private String val;
		
		private String text;
		
		private PropertyStatusEnum(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static PropertyStatusEnum enumOf(String value){
			for (PropertyStatusEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			PropertyStatusEnum pse = enumOf(value);
			if(pse != null){
				return pse.text;
			}
			return null;
	    }
		
	}
	
	/**
	 * @Description : 商品属性编码
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年11月14日 下午5:35:33
	 */
	public enum PropertyCodeEnum implements ProductInfo {
		
		/**
		 * 含泥量
		 */
		PROPERTY_CODE_MUD_CONTENT("MUD_CONTENT","含泥量"),
		/**
		 * 泥块含量
		 */
		PROPERTY_CODE_CLAY_CONTENT("CLAY_CONTENT","泥块含量"),
		/**
		 * 表观密度
		 */
		PROPERTY_CODE_APPARENT_DENSITY("APPARENT_DENSITY","表观密度"),
		/**
		 * 堆积密度
		 */
		PROPERTY_CODE_BULK_DENSITY("BULK_DENSITY","堆积密度"),
		/**
		 * 坚固性指标
		 */
		PROPERTY_CODE_CONSISTENCY_INDEX("CONSISTENCY_INDEX","坚固性指标"),
		/**
		 * 压碎值指标
		 */
		PROPERTY_CODE_CRUSHING_VALUE_INDEX("CRUSHING_VALUE_INDEX","压碎值指标"),
		/**
		 * 针片状颗粒含量
		 */
		PROPERTY_CODE_ELONGATED_PARTICLES("ELONGATED_PARTICLES","针片状颗粒含量"),
		/**
		 * 含水率
		 */
		PROPERTY_CODE_MOISTURE_CONTENT("MOISTURE_CONTENT","含水率");
		
		
		private String val;
		
		private String text;
		
		private PropertyCodeEnum(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static PropertyCodeEnum enumOf(String value){
			for (PropertyCodeEnum os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			PropertyCodeEnum pce = enumOf(value);
			if(pce != null){
				return pce.text;
			}
			return null;
		}
		
	}

}
