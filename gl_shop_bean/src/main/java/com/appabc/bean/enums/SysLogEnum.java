/**
 *
 */
package com.appabc.bean.enums;

import org.apache.commons.lang.StringUtils;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 系统日志枚举
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月16日 下午4:19:12
 */
public interface SysLogEnum extends IBaseEnum{
	
	/**
	 * @Description : 日志类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年1月16日 下午4:25:40
	 */
	public enum LogType implements SysLogEnum{
		
		/**
		 * 待定,001
		 */
		LOG_TYPE_001(0,""), 
		/**
		 * 待定,002
		 */
		LOG_TYPE_002(1,"");
		
		private int val;
		
		private String text;
		
		private LogType(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static LogType enumOf(int value){
			for (LogType os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			LogType te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
	}
	
	/**
	 * @Description : 日志级别
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年1月16日 下午4:25:40
	 */
	public enum LogLevel implements SysLogEnum{
		
		/**
		 * 致命错误
		 */
		LOG_LEVEL_FATAL (0,"致命错误"), 
		/**
		 * 一般错误
		 */
		LOG_LEVEL_ERROR (1,"一般错误"), 
		/**
		 * 警告
		 */
		LOG_LEVEL_WARN (2,"警告"), 
		/**
		 * 信息提示
		 */
		LOG_LEVEL_INFO (3,"信息提示");
		
		private int val;
		
		private String text;
		
		private LogLevel(int val,String text){
			this.val=val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public String getText(){
			return text;
		}

		public static LogLevel enumOf(int value){
			for (LogLevel os : values()) {
				if (os.val == value) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(int value) {
			LogLevel te = enumOf(value);
			if(te != null){
				return te.text;
			}
			return null;
	    }
	}
	
	/**
	 * @Description : 日志业务类型
	 * @Copyright   : GL. All Rights Reserved
	 * @Company     : 江苏国立网络技术有限公司
	 * @author      : 杨跃红
	 * @version     : 1.0
	 * Create Date  : 2015年1月16日 下午5:58:01
	 */
	public enum LogBusinessType implements SysLogEnum {
		/******************帐号模块**************************/
		/**
		 * 用户注册
		 */
		BUSINESS_TYPE_USER_REGISTER("100","用户注册"),
		/**
		 * 用户登录
		 */
		BUSINESS_TYPE_USER_LOGIN("101","用户登录"),
		/**
		 * 用户退出登录
		 */
		BUSINESS_TYPE_USER_LOGOUT("102","退出登录"),
		/**
		 * 用户修改密码
		 */
		BUSINESS_TYPE_USER_MDY_PWD("103","用户修改密码"),
		/**
		 * 用户密码找回
		 */
		BUSINESS_TYPE_USER_FIND_PWD("104","用户密码找回"),
		/**
		 * TOKEN更新
		 */
		BUSINESS_TYPE_USER_TOKEN_UPDATE("105","TOKEN更新"),
		
		/******************企业模块**************************/
		/**
		 * 提交企业资料认证申请
		 */
		BUSINESS_TYPE_COMPANY_AUTH_APPLY("200","提交企业资料认证申请"), 
		/**
		 * 企业资料审核
		 */
		BUSINESS_TYPE_COMPANY_AUTH_CHECK("201","企业资料审核"), 
		/**
		 * 提交企业收款人认证申请
		 */
		BUSINESS_TYPE_COMPANY_PAYEE_AUTH_APPLY("210","提交企业收款人认证申请"), 
		/**
		 * 提交企业收款人认证申请
		 */
		BUSINESS_TYPE_COMPANY_PAYEE_AUTH_CHECK("210","提交企业收款人认证申请"), 
		
		/******************询单模块**************************/
		/**
		 * 询单审核
		 */
		BUSINESS_TYPE_ORDER_FIND_CHECK("300","询单审核"), 
		/**
		 * 询单取消发布
		 */
		BUSINESS_TYPE_ORDER_FIND_CANCEL("301","询单取消发布"), 
		/**
		 * 询单修改
		 */
		BUSINESS_TYPE_ORDER_FIND_UPDATE("302","询单修改"), 
		/**
		 * 询单回滚
		 */
		BUSINESS_TYPE_ORDER_FIND_ROLLBACK("303","询单回滚"), 
		
		/******************合同模块**************************/
		/**
		 * 合同签订
		 */
		BUSINESS_TYPE_CONTRACT_SIGN("400","合同签订"), 
		/**
		 * 合同取消
		 */
		BUSINESS_TYPE_CONTRACT_CANCEL("401","合同取消"),
		/**
		 * 合同完成
		 */
		BUSINESS_TYPE_CONTRACT_END("402","合同完成"),
		
		/******************钱包模块**************************/
		/**
		 * 保证金金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANG_GUARANTY("500","保证金金额变动"),
		/**
		 * 钱包货款金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANG_DEPOSIT("501","货款金额变动"),
		/**
		 * 钱包金额变动
		 */
		BUSINESS_TYPE_MONEY_CHANGE("502","钱包金额变动"),
		
		/******************系统模块**************************/
		/**
		 * 系统配置参数修改
		 */
		BUSINESS_TYPE_SYSTEM_PARAM_MODIFY("900","系统配置参数修改");
		
		private String val;
		
		private String text;
		
		private LogBusinessType(String val,String text){
			this.val = val;
			this.text = text;
		}
		
		public String getVal() {
			return val;
		}
		
		public String getText(){
			return text;
		}

		public static LogBusinessType enumOf(String value){
			for (LogBusinessType os : values()) {
				if (StringUtils.equalsIgnoreCase(os.val,value)) {
					return os;
				}
			}
			return null;
		}
		
		public static String getText(String value) {
			LogBusinessType mbt = enumOf(value);
			if(mbt != null){
				return mbt.text;
			}
			return null;
	    }
		
	}
	
	
}
