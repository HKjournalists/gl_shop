package com.appabc.http.utils;

import com.appabc.common.utils.ErrorCode;

/**
 * @Description : http project error code set
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date  : 2014年9月2日 下午1:56:55
 */
public final class HttpApplicationErrorCode extends ErrorCode {
	
	/*参数为空*/
	public final static int PARAMETER_IS_NULL = 10000;
	
	/*返回错误*/
	public final static int RESULT_ERROR_CODE = 20000;
	
	/*合同模板为空*/
	public static final int CONTRACT_DETAIL_TEMPLATE_ISNULL = 100005001;
	
	// 支付合同错误 
	public static final int CONTRACT_PAY_ERROR = 100005002;
	
	// 取消合同错误 
	public static final int CONTACT_SINGLE_CANCEL_ERROR = 100005003;
	
	// 确认合同错误 
	public static final int CONTRACT_CONFIRM_CONTRACT_ERROR = 100005004;
	
	// 确认卖家卸货错误 
	public static final int CONTRACT_CONFIRM_UNINSTALLGOODS_ERROR = 100005005;
	
	// 确认买家收货错误 
	public static final int CONTRACT_CONFIRM_RECEIVEGOODS_ERROR = 100005006;
	
}
