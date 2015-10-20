package com.appabc.common.utils;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年8月26日 下午12:03:56
 */

public class ErrorCode {

	public static final int GENERIC_ERROR_CODE = 1000;
	public static final int GENERIC_EXCEPTION_CODE = 2000;
	public static final int USER_STATUS_ERROR = 10001; // 用户状态异常
	public static final int DATA_IS_NOT_COMPLETE = 10002; // 数据不完整
	public static final int ERROR_VLD_CODE = 10003; // 验证码错误
	public static final int SMS_SEND_FAIL = 10004; // 短信发送发失败
	public static final int USER_LOGIN_ERROR = 10005; // 用户名密码错误
	public static final int ORIGINAL_PWD_ERROR = 10006; // 原始密码错误
	public static final int NEW_PWD_EQ_ORI_PWD = 10007; // 新旧密码不能相同
	public static final int USER_DOES_NOT_EXIST = 10008; // 用户不存在
	public static final int USER_ALREADY_EXISTS = 10009; // 用户已存在
	/**用户未登陆*/
	public final static int USER_UNLOGINE_ERROR = 300001;
	/**用户token过期*/
	public final static int TOKEN_IS_OUT_DATE = 300002;
	/**请求签名错误*/
	public final static int CLIENT_SIGN_ERROR = 300003;
	/**参数为空*/
	public final static int PARAMETER_IS_NULL = 10000;
	/**返回错误*/
	public final static int RESULT_ERROR_CODE = 20000;
	/** 操作限制 */
	public static final int OPERATING_RESTRICTIONS = 30000;
	/** 重复提交,重复操作 */
	public static final int SUBMIT_OPERATOR_AGAIN = 40000;
	/** 当前时间在结束时间之后（当前时间大于结束时间） */
	public static final int DATE_NOWTIME_AFTER_ENDTIME = 50000;
	/** 开始时间在结束时间之后（开始时间大于结束时间） */
	public static final int DATE_STARTTIME_AFTER_ENDTIME = 50001;
	
}
