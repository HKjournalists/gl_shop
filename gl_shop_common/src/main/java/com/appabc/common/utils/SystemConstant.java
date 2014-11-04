package com.appabc.common.utils;
/**
 * @Description : 系统常量
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月22日 下午5:47:39
 */

public final class SystemConstant {
	
	/**分页常量--当前页数*/
	public static final String PAGEINDEX = "pageIndex";
	/**分页常量--每页大小*/
	public static final String PAGESIZE = "pageSize";
	
	public static final String DATABASE_ORACLE = "ORACLE";
	public static final String DATABASE_MYSQL = "MYSQL";
	public static final String DATABASE_SQLSERVER = "SQLSERVER";
	
	public static final String DEFAULT_DIALECT = DATABASE_MYSQL;
	
	public static final String DATA = "DATA";
	public static final String RESULT = "RESULT";
	public static final String FAIL = "FAIL";
	public static final String MESSAGE = "MESSAGE";
	public static final String ERRORCODE = "ERRORCODE";
	public static final String ERRORMESSAGE = "ERRORMESSAGE";
	public static final String EXCEPTIONMESSAGE = "System error,please contact administrator.";
	
	/** 平台钱包保证金的标记 */
	public static final String PLATFORMPURSEGUARANTYFLAG = "PLATFORMPURSEGUARANTYFLAG";
	/** 平台钱包货款的标记 */
	public static final String PLATFORMPURSEDEPOSITFLAG = "PLATFORMPURSEDEPOSITFLAG";
	
	/** 存放在session中登录用户的user对象的标记 */
	public static final String LOGINUSERMARKER = "LOGINUSERMARKER";
	public static final String ACCESS_TOKEN = "USER_TOKEN";
	public static final String ENCRYPT_KEY = "ENCRYPT_KEY";
	public static final String SIGN_KEY = "SIGN_KEY";
	public static final String APP_KEY = "APP_KEY";
	
	/** 交易区域分段CODE 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_RIVER_SECTION = "RIVER_SECTION";
	/** 商品大类(黄砂、石子等) 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_GOODS = "GOODS";
	/** 交易地域 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_AREA = "AREA";
	
	/* 上传文件的目录名 */
	public static final String UPLOADFILE_DIR = "UPLOADFILE_DIR";
	/* 上传文件服务器的访问的域名 */
	public static final String UPLOADFILE_DOMAIN = "UPLOADFILE_DOMAIN";
	
	/**
	 * 短信验证码KEY前缀
	 */
	public static final String SMS_CODE_KEY = "MSM_CODE_";
	/**
	 * 图片验证码KEY前缀
	 */
	public static final String IMG_CODE_KEY = "IMG_CODE_";
	
	public static enum OrderEnum {
		ORDERASC(0,"ASC"),ORDERDESC(1,"DESC");
		
		private int value;
		private String text;
		
		private OrderEnum(int value,String text){
			this.value = value;
			this.text = text;
		}
		
		public int getValue(){return value;}
		
		public String getText(){return text;}
	}
	
}