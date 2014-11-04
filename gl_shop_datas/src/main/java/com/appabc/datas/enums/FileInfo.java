/**
 *
 */
package com.appabc.datas.enums;

/**
 * @Description : 文件上传枚举
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 下午8:20:06
 */
public interface FileInfo {

	public enum FileOType implements FileInfo {
		
		/**
		 * 企业认证图片
		 */
		FILE_OTYPE_COMPANY_AUTH("0"),
		/**
		 * 企业图片
		 */
		FILE_OTYPE_COMPANY("1"),
		/**
		 * 卸货地址图片
		 */
		FILE_OTYPE_ADDRESS("2"),
		/**
		 * 交易中商品的卸货地址图片
		 */
		FILE_OTYPE_ADDRESS_ORDER("3"),
		/**
		 * 企业提款人认证图片
		 */
		FILE_OTYPE_BANK("4"),
		/**
		 * 商品图片
		 */
		FILE_OTYPE_PRODUCT("5");
		
		
		private String val;
		
		private FileOType(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
	
	public enum FileCommitServer implements FileInfo {
		
		/**
		 * 接口服务
		 */
		COMMIT_SERVER_GLSHOPHTTP("GL_SHOP_HTTP"),
		/**
		 * 后台管理
		 */
		COMMIT_SERVER_GLSHOPWEB("GL_SHOP_WEB");
		
		
		private String val;
		
		private FileCommitServer(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
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
		FILE_STYLE_ORIGINAL("0"),
		/**
		 * 略图
		 */
		FILE_STYLE_SMALL("1");
		
		
		private String val;
		
		private FileStyle(String val){
			this.val = val;
		}
		
		public String getVal() {
			return val;
		}
	}
}
