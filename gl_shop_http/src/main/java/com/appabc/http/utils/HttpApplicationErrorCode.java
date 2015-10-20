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
	
	//系统活动手机号码重复申请
	public static final int SYSTEM_ACTIVITY_AGAIN_ERROR = 800001001;
	
	//系统活动手机号码格式不对
	public static final int SYSTEM_ACTIVITY_PHONENUM_FORMAT_ERROR = 800001002;

	public final class HttpAppSystemConstant {
		
		/**********************输出给客户端相关信息的提示音的KEY**********************/
		
		/**********************合同相关*********************/
		public static final String ENTITY_IS_NULL = "ENTITY_IS_NULL";
		
		public static final String CONTRACT_ID_IS_NULL = "CONTRACT_ID_IS_NULL";
		
		public static final String CONTRACT_CONFIRM_SUCCESS_TIPS = "CONTRACTCONFIRMSUCCESSTIPS";
		
		public static final String NOT_BUYER_NOTO_PAY_CONTRACT = "NOTBUYERNOTOPAYCONTRACT";
		
		public static final String PAY_CONTRACT_FUNDS_TIPS = "PAYCONTRACTFUNDSTIPS";
		
		public static final String THE_VALIDATE_CODE_ISNULL = "THE_VALIDATE_CODE_ISNULL";
		
		public static final String THE_VALIDATE_CODE_EXPIREDATE = "THE_VALIDATE_CODE_EXPIREDATE";
		
		public static final String USER_PASSWORD_ISNULL = "USERPASSWORDISNULL";
		
		public static final String USER_PASSWORD_ISWORRY = "USERPASSWORDISWORRY";
		
		public static final String CONTRACT_NOTICESHIPPING_GOODS = "CONTRACTNOTICESHIPPINGGOODS";
		
		public static final String CONTRACT_CANCEL_SUCCESS_TIPS = "CONTRACTCANCELSUCCESSTIPS";
		
		public static final String CONTRACT_APPLY_RESULT_ISNULL = "CONTRACT_APPLY_RESULT_ISNULL";
		
		public static final String CONTRACT_INVALIDATION_TYPE = "CONTRACT_INVALIDATION_TYPE";
		
		public static final String OPERATE_SUCCESS_TIPS = "OPERATESUCCESSTIPS";
		
		public static final String CONTRACT_DISPRICE_ISNULL = "CONTRACT_DISPRICE_ISNULL";
		
		public static final String CONTRACT_DISNUM_ISNULL = "CONTRACT_DISNUM_ISNULL";
		
		public static final String CONTRACT_FINALESTIMATE_AMOUNT_ISNULL = "CONTRACT_FINALESTIMATE_AMOUNT_ISNULL";
		
		public static final String CONTRACT_CONFIR_MUN_INSTALLGOODS = "CONTRACTCONFIRMUNINSTALLGOODS";
		
		public static final String CONTRACT_VALIDATE_GOODSINFO = "CONTRACTVALIDATEGOODSINFO";
		
		public static final String BEEVALUATION_CID_IS_NULL = "BEEVALUATION_CID_IS_NULL";
		
		public static final String CONTRACT_CREDIT_ISNULL = "CONTRACT_CREDIT_ISNULL";
		
		public static final String CONTRACT_SATISFACTION_ISNULL = "CONTRACT_SATISFACTION_ISNULL";
		
		public static final String CONTRACT_EVALUATE_SUCCESS_TIPS = "CONTRACTEVALUATESUCCESSTIPS";
		
		public static final String CONTRACT_APPLY_TYPE_ISNULL = "CONTRACT_APPLY_TYPE_ISNULL";
		
		public static final String RETURN_NO_DATA_TIPS = "RETURN_NO_DATA_TIPS";
		
		/**********************合同相关*********************/
		
		
		/**********************钱包相关*********************/
		
		public static final String INITIALIZE_PURSEACCOUNT_SUCCESS_TIPS = "INITIALIZE_PURSEACCOUNT_SUCCESS_TIPS";
		
		public static final String INITIALIZE_PURSEACCOUNT_ERROR_TIPS = "INITIALIZE_PURSEACCOUNT_ERROR_TIPS";
		
		public static final String PURSEACCOUNT_PARAMETER_ISNULL = "PURSEACCOUNT_PARAMETER_ISNULL";
		
		public static final String PURSEACCOUNT_PARAMETER_NOTMATCH = "PURSEACCOUNT_PARAMETER_NOTMATCH";
		
		public static final String PURSEACCOUNT_EXTRACT_CASH_REQUEST_TIPS = "PURSEACCOUNT_EXTRACT_CASH_REQUEST_TIPS";
		
		public static final String PURSEACCOUNT_VALIDATE_EXTRACT_CASH_SUCCESS = "PURSEACCOUNT_VALIDATE_EXTRACT_CASH_SUCCESS";
		
		public static final String PURSEACCOUNT_VALIDATE_EXTRACT_CASH_FAIL = "PURSEACCOUNT_VALIDATE_EXTRACT_CASH_FAIL";
		
		public static final String PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_SUCCESS = "PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_SUCCESS";
		
		public static final String PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_FAIL = "PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_FAIL";
		
		public static final String PURSEACCOUNT_DEPOSIT_THIRD_SUCCESS = "PURSEACCOUNT_DEPOSIT_THIRD_SUCCESS";
		
		public static final String PURSEACCOUNT_DEPOSIT_THIRD_FAIL = "PURSEACCOUNT_DEPOSIT_THIRD_FAIL";
		
		public static final String PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_SUCCESS = "PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_SUCCESS";
		
		public static final String PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_FAIL = "PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_FAIL";
		
		public static final String PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_SUCCESS = "PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_SUCCESS";
		
		public static final String PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_FAIL = "PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_FAIL";
		
		/**********************钱包相关*********************/
		
		public static final String SYSTEM_ACTIVITY_NAME_NULL = "SYSTEM_ACTIVITY_NAME_NULL";
		
		public static final String SYSTEM_ACTIVITY_PHONE_NULL = "SYSTEM_ACTIVITY_PHONE_NULL";
		
		public static final String SYSTEM_ACTIVITY_REQNUM_NULL = "SYSTEM_ACTIVITY_REQNUM_NULL";
		
		public static final String SYSTEM_ACTIVITY_REQUEST_AGAIN = "SYSTEM_ACTIVITY_REQUEST_AGAIN";
		
		public static final String SYSTEM_ACTIVITY_PHONENUM_FORMAT = "SYSTEM_ACTIVITY_PHONENUM_FORMAT";
		
		/**********************输出给客户端相关信息的提示音的KEY**********************/
		
	}
	
}
