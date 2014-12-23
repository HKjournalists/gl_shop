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
	
	/**三种数据库类型常量*/
	public static final String DATABASE_ORACLE = "ORACLE";
	public static final String DATABASE_MYSQL = "MYSQL";
	public static final String DATABASE_SQLSERVER = "SQLSERVER";
	/**默认使用的数据库的类型常量*/
	public static final String DEFAULT_DIALECT = DATABASE_MYSQL;
	
	/**返回客户端消息的常量*/
	public static final String DATA = "DATA";//数据前缀
	public static final String RESULT = "RESULT";//返回结果前缀
	public static final String FAIL = "FAIL";//返回失败前缀
	public static final String MESSAGE = "MESSAGE";//返回消息前缀
	public static final String ERRORCODE = "ERRORCODE";//返回异常编码
	public static final String ERRORMESSAGE = "ERRORMESSAGE";//返回异常消息
	public static final String EXCEPTIONMESSAGE = "System error,please contact administrator.";//返回异常信息
	
	/** 平台钱包保证金的标记 */
	public static final String PLATFORMPURSEGUARANTYFLAG = "PLATFORMPURSEGUARANTYFLAG";
	/** 平台钱包货款的标记 */
	public static final String PLATFORMPURSEDEPOSITFLAG = "PLATFORMPURSEDEPOSITFLAG";
	
	/** 存放在session中登录用户的user对象的标记 */
	public static final String LOGINUSERMARKER = "LOGINUSERMARKER";//用户登录标记
	public static final String ACCESS_TOKEN = "USER_TOKEN";//用户TOKEN
	public static final String ENCRYPT_KEY = "ENCRYPT_KEY";//用户加密标记
	public static final String SIGN_KEY = "sign";//用户签名标记
	public static final String APPID_KEY = "appabc.com";//用户APP Key
	
	/** 交易区域分段CODE 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_RIVER_SECTION = "RIVER_SECTION";
	/** 商品大类(黄砂、石子等) 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_GOODS_TYPE = "GOODS";
	/** 交易地域 对应T_PUBLIC_CODES表中的CODE */
	public static final String CODE_AREA = "AREA";
	
	/**
	 * 短信验证码KEY前缀
	 */
	public static final String SMS_CODE_KEY = "MSM_CODE_";
	/**
	 * 图片验证码KEY前缀
	 */
	public static final String IMG_CODE_KEY = "IMG_CODE_";
	
	/*****系统参数KEY******************/
	public static final String 	USERTOKEN_EFF_TIME_LENGTH = "USERTOKEN_EFF_TIME_LENGTH"; // 	userToken有效时长，单位：秒
	public static final String 	IMG_VLD_CODE_TIME_LENGTH = "IMG_VLD_CODE_TIME_LENGTH"; // 	图片验证码有效时长，单位秒
	public static final String 	SMS_VLD_CODE_TIME_LENGTH = "SMS_VLD_CODE_TIME_LENGTH"; // 	短信验证码有效时长，单位秒
	public static final String 	BOND_ENTERPRISE = "BOND_ENTERPRISE"; // 	保证金额度-企业
	public static final String 	BOND_PERSONAL = "BOND_PERSONAL"; // 	保证金额度-个人
	public static final String 	BOND_SHIP_0_1000 = "BOND_SHIP_0_1000"; // 	保证金额度-船舶1000吨及以下
	public static final String 	BOND_SHIP_1001_5000 = "BOND_SHIP_1001_5000"; // 	保证金额度-船舶2000~5000吨
	public static final String 	BOND_SHIP_5001_10000 = "BOND_SHIP_5001_10000"; // 	保证金额度-船舶6000~10000吨
	public static final String 	BOND_SHIP_10001_15000 = "BOND_SHIP_10001_15000"; // 	保证金额度-船舶10000吨以上
	public static final String 	GUARANTY_PERCENT = "GUARANTY_PERCENT"; // 	一笔合同扣除保证金的额度
	public static final String 	SERVICE_PERCENT = "SERVICE_PERCENT"; // 	合同交易扣除服务费的额度
	public static final String 	DISCOUNT_PERCENT = "DISCOUNT_PERCENT"; // 	买家付款折扣
	public static final String 	SYNC_RIVER_SECTION_TIME = "SYNC_RIVER_SECTION_TIME"; // 	更新时间-港口列表
	public static final String 	SYNC_GOODS_TYPE_TIME = "SYNC_GOODS_TYPE_TIME"; // 	更新时间-商品类型
	public static final String 	SYNC_GOODS_TIME = "SYNC_GOODS_TIME"; // 	更新时间-商品
	public static final String 	SYNC_AREA_TIME = "SYNC_AREA_TIME"; // 	更新时间-交易地域
	public static final String 	SYNC_BANKS_TIME = "SYNC_BANKS_TIME"; // 	更新时间-银行列表
	public static final String 	SYNC_SYS_PARAM_TIME = "SYNC_SYS_PARAM_TIME"; // 	更新时间-系统配置参数
	public static final String 	UPLOADFILE_DIR = "UPLOADFILE_DIR"; // 	上传文件的存放目录【相对路径】
	public static final String 	UPLOADFILE_DOMAIN = "UPLOADFILE_DOMAIN"; // 	访问文件的路径
	public static final String 	CUSTOMER_SERVICE_TEL = "CUSTOMER_SERVICE_TEL"; // 	客服电话

	/**SQL排序枚举,顺序和倒序*/
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