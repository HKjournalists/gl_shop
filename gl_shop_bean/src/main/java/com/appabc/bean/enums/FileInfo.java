/**
 *
 */
package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 文件上传枚举
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 下午8:20:06
 */
public interface FileInfo extends IBaseEnum{

	public enum FileOType implements FileInfo {
		
		/**
		 * 企业认证图片
		 */
		FILE_OTYPE_COMPANY_AUTH("0","企业认证图片"),
		/**
		 * 企业图片
		 */
		FILE_OTYPE_COMPANY("1","企业图片"),
		/**
		 * 企业卸货地址图片
		 */
		FILE_OTYPE_ADDRESS("2","企业卸货地址图片"),
		/**
		 * 询单卸货地址图片
		 */
		FILE_OTYPE_ADDRESS_ORDER("3","询单卸货地址图片"),
		/**
		 * 企业提款人认证图片
		 */
		FILE_OTYPE_BANK("4","企业提款人认证图片"),
		/**
		 * 询单的货物图片
		 */
		FILE_OTYPE_PRODUCT_ORDER("5","询单的货物图片");
		
		
		private String val;
		
		private String text;
		
		private FileOType(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static FileOType enumOf(String value){
			for (FileOType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			FileOType fot = enumOf(value);
			if(fot != null){
				return fot.text;
			}
			return null;
	    }
		
	}
	
	public enum FileCommitServer implements FileInfo {
		
		/**
		 * 接口服务
		 */
		COMMIT_SERVER_GLSHOPHTTP("GL_SHOP_HTTP","接口服务"),
		/**
		 * 后台管理
		 */
		COMMIT_SERVER_GLSHOPWEB("GL_SHOP_WEB","后台管理");
		
		
		private String val;
		
		private String text;
		
		private FileCommitServer(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static FileCommitServer enumOf(String value){
			for (FileCommitServer os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			FileCommitServer fcs = enumOf(value);
			if(fcs != null){
				return fcs.text;
			}
			return null;
	    }
	}
	
	/**
	 * @Description : 图片的型号，原图或是略图
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2014年10月31日 下午6:15:41
	 */
	public enum FileStyle implements FileInfo {
		
		/**
		 * 原图
		 */
		FILE_STYLE_ORIGINAL("0","原图"),
		/**
		 * 略图
		 */
		FILE_STYLE_SMALL("1","略图");
		
		
		private String val;
		
		private String text;
		
		private FileStyle(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}
		
		public static FileStyle enumOf(String value){
			for (FileStyle os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val, value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			FileStyle fs = enumOf(value);
			if(fs != null){
				return fs.text;
			}
			return null;
	    }
		
	}
}
