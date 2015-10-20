package com.glshop.net.common;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.SparseIntArray;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.utils.ToastUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 全局错误类型定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午2:31:15
 */
public final class GlobalErrorMessage {

	public static final boolean DEBUG = false;

	/**
	 * 全局统一默认错误信息
	 */
	public static final int DEFAULT_ERROR_MSG = R.string.error_req_error;

	/**
	 * 全局错误码定义
	 */
	public interface ErrorCode {

		public static final int ERROR_CODE_UNKNOW = -1; // 默认未知异常
		public static final int ERROR_CODE_10003 = 10003; // 验证码错误
		public static final int ERROR_CODE_10004 = 10004; // 短信发送失败
		public static final int ERROR_CODE_10005 = 10005; // 用户名或密码错误
		public static final int ERROR_CODE_10006 = 10006; // 旧密码错误
		public static final int ERROR_CODE_10007 = 10007; // 新密码不能和旧密码相同
		public static final int ERROR_CODE_10008 = 10008; // 用户不存在
		public static final int ERROR_CODE_10009 = 10009; // 用户已注册
		public static final int ERROR_CODE_50000 = 50000; // 交易结束时间不能小于当前时间
		public static final int ERROR_CODE_50001 = 50001; // 交易起始时间不能大于交易结束时间
		public static final int ERROR_CODE_100003005 = 100003005; // 单价最大值不能大于%s
		public static final int ERROR_CODE_100003006 = 100003006; // 总量最大值不能大于%s
		public static final int ERROR_CODE_100005009 = 100005009; // 合同重复确认
		public static final int ERROR_CODE_100005024 = 100005024; // 您已经评价过,不能重复评价!
		public static final int ERROR_CODE_100005025 = 100005025; // 该合同的评价期限已过期,系统已自动默认评价，用户无法评价!
		public static final int ERROR_CODE_100003003 = 100003003; // 不能重复申请交易询盘!
		public static final int ERROR_CODE_100005023 = 100005023; // 您不是合同买卖双方，不能操作此合同!
		public static final int ERROR_CODE_100005027 = 100005027; // 您不是合同的买家，不能结算申请!
		public static final int ERROR_CODE_100005028 = 100005028; // 您不是合同的卖家，不能同意结算申请!
		public static final int ERROR_CODE_100005034 = 100005034; // 合同确认操作超时
		public static final int ERROR_CODE_100005035 = 100005035; // 合同付款操作超时
		public static final int ERROR_CODE_100005011 = 100005011; // 合同当前状态不能进行付款操作
		public static final int ERROR_CODE_100005010 = 100005010; // 保证金账户余额不足!
		public static final int ERROR_CODE_100005014 = 100005014; // 货款账户余额不足!

	}

	private static SparseIntArray mErrorMsg = new SparseIntArray();

	static {
		//mErrorMsg.put(ErrorCode.ERROR_CODE_UNKNOW, R.string.error_req_error);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10003, R.string.error_code_10003);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10004, R.string.error_code_10004);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10005, R.string.error_code_10005);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10006, R.string.error_code_10006);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10007, R.string.error_code_10007);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10008, R.string.error_code_10008);
		mErrorMsg.put(ErrorCode.ERROR_CODE_10009, R.string.error_code_10009);
		mErrorMsg.put(ErrorCode.ERROR_CODE_50000, R.string.error_code_50000);
		mErrorMsg.put(ErrorCode.ERROR_CODE_50001, R.string.error_code_50001);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005009, R.string.error_code_100005009);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005024, R.string.error_code_100005024);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100003003, R.string.error_code_100003003);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005023, R.string.error_code_100005023);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005025, R.string.error_code_100005025);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005027, R.string.error_code_100005027);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005028, R.string.error_code_100005028);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005034, R.string.error_code_100005034);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005035, R.string.error_code_100005035);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005011, R.string.error_code_100005011);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005010, R.string.error_code_100005010);
		mErrorMsg.put(ErrorCode.ERROR_CODE_100005014, R.string.error_code_100005014);
	}

	/**
	 * 获取ErrorCode对应的错误信息
	 * @param context
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMsg(Context context, String errorCode) {
		return getErrorMsg(context, errorCode, "");
	}

	/**
	 * 获取ErrorCode对应的错误信息，debug下显示原始错误信息
	 * @param context
	 * @param errorCode
	 * @param defaultErrorMsg
	 * @return
	 */
	public static String getErrorMsg(Context context, String errorCode, String defaultErrorMsg) {
		int errorMsgRes = DEFAULT_ERROR_MSG;
		int errCode = getErrorCode(errorCode);
		if (DEBUG) {
			// DEBUG下显示原始错误信息
			if (errCode == ErrorCode.ERROR_CODE_UNKNOW && StringUtils.isNotEmpty(defaultErrorMsg)) {
				return defaultErrorMsg;
			}
		} else {
			errorMsgRes = getErrorMsg(errCode);
		}

		String errorMsg = "";
		try {
			errorMsg = context.getResources().getString(errorMsgRes);
		} catch (NotFoundException e) {
			errorMsg = context.getResources().getString(DEFAULT_ERROR_MSG);
			e.printStackTrace();
		}
		return errorMsg;
	}

	/**
	 * 显示ErrorCode对应的错误信息
	 * @param context
	 * @param errorCode
	 * @param defaultErrorMsg
	 * @return
	 */
	public static boolean handleErrorMsg(Context context, String errorCode) {
		int errCode = getErrorCode(errorCode);
		int errorMsgRes = getErrorMsg(errCode);
		if (errorMsgRes != GlobalErrorMessage.ErrorCode.ERROR_CODE_UNKNOW) {
			String errorMsg = "";
			try {
				errorMsg = context.getResources().getString(errorMsgRes);
			} catch (NotFoundException e) {
				errorMsg = context.getResources().getString(DEFAULT_ERROR_MSG);
				e.printStackTrace();
			}
			ToastUtil.showDefaultToast(context, errorMsg);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取reqType对应的错误信息
	 * @param context
	 * @param reqType
	 * @return
	 */
	public static String getErrorMsg(Context context, DataReqType reqType) {
		String errorMsg = context.getResources().getString(DEFAULT_ERROR_MSG);
		if (reqType != null) {
			switch (reqType) {
			case INIT:
				errorMsg = context.getResources().getString(DEFAULT_ERROR_MSG);
				break;
			case MORE:
				errorMsg = context.getResources().getString(R.string.error_req_load_more);
				break;
			case REFRESH:
				errorMsg = context.getResources().getString(R.string.error_req_refresh);
				break;
			}
		}
		return errorMsg;
	}

	/**
	 * 解析错误码
	 * @param errorCode
	 * @return
	 */
	public static int getErrorCode(String errorCode) {
		int errCode;
		try {
			errCode = Integer.parseInt(errorCode);
		} catch (Exception e) {
			errCode = GlobalErrorMessage.ErrorCode.ERROR_CODE_UNKNOW;
		}
		return errCode;
	}

	/**
	 * 解析错误信息详情
	 * @param errorCode
	 * @return
	 */
	public static int getErrorMsg(int errorCode) {
		return mErrorMsg.get(errorCode, ErrorCode.ERROR_CODE_UNKNOW);
	}

	/**
	 * 是否为全局统一的错误类型
	 * @param errorCode
	 * @return
	 */
	public static boolean isFilterErrorCode(String errorCode) {
		if (StringUtils.isNotEmpty(errorCode)) {
			if (DataConstants.GlobalErrorCode.USER_NOT_LOGIN.equals(errorCode) || DataConstants.GlobalErrorCode.USER_TOKEN_EXPIRE.equals(errorCode)) {
				return true;
			}
		}
		return false;
	}

}
