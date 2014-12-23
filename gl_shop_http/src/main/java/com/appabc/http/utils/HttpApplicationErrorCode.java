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
	
	/*合同模板为空*/
	public static final int CONTRACT_DETAIL_TEMPLATE_ISNULL = 100005001;
	
	// 支付合同错误 
	public static final int CONTRACT_PAY_ERROR = 100005002;
	
	// 取消合同错误 
	public static final int CONTRACT_SINGLE_CANCEL_ERROR = 100005003;
	
	// 确认合同错误 
	public static final int CONTRACT_CONFIRM_CONTRACT_ERROR = 100005004;
	
	// 确认卖家卸货错误 
	public static final int CONTRACT_CONFIRM_UNINSTALLGOODS_ERROR = 100005005;
	
	// 确认买家收货错误 
	public static final int CONTRACT_CONFIRM_RECEIVEGOODS_ERROR = 100005006;
	
	//合同确认议价操作错误
	public static final int CONTRACT_VALIDATE_GOODS_DIS_PRICE = 100005007;
	
	//合同咨询客户错误
	public static final int CONTRACT_CONSULTING_SERVICE_ERROR = 100005008;
	
	//移动终端程序版本更新错误
	public static final int MOBILE_APP_VERSION_ERROR = 700005001;
	
}
