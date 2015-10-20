package com.glshop.net.utils;

import android.content.Context;

import com.glshop.net.R;
import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.DepositStatusType;
import com.glshop.platform.api.DataConstants.PayType;
import com.glshop.platform.api.DataConstants.ProfileType;

/**
 * 枚举工具类
 */
public class EnumUtil {

	/**
	 * 获取流水枚举类型
	 * @param type
	 * @return
	 */
	public static String parseDealType(DealOprType type) {
		String data = "";
		switch (type) {
		case DEPOSIT:
			data = "汇入";
			break;
		case EXTRACT_CASH_GELATION:
			data = "提取冻结";
			break;
		case EXTRACT_CASH_UNGELATION:
			data = "提取解冻";
			break;
		case EXTRACT_CASH_SUCCESS:
			data = "提取成功";
			break;
		case EXTRACT_CASH_FAILURE:
			data = "提取失败";
			break;
		case GELATION_GUARANTY:
			data = "保证金冻结";
			break;
		case UNGELATION_GUARANTY:
			data = "保证金解冻";
			break;
		case GELATION_DEPOSIT:
			data = "买货冻结";
			break;
		case UNGELATION_DEPOSIT:
			data = "买货解冻";
			break;
		case VIOLATION_DEDUCTION:
			data = "赔付对方付出";
			break;
		case MANPOWER_DEDUCTION:
			data = "平台扣除+备注";
			break;
		case VIOLATION_REPARATION:
			data = "对方赔付";
			break;
		case MANPOWER_REPARATION:
			data = "平台汇入+备注";
			break;
		case DEPOSIT_GUARANTY:
			data = "货款转保证金";
			break;
		case PAYMENT_FOR_GOODS:
			data = "支付货款";
			break;
		case SERVICE_CHARGE:
			data = "交易扣除费";
			break;
		case PLATFORM_RETURN:
			data = "平台返还";
			break;
		case PLATFORM_PAY:
			data = "平台支付";
			break;
		case PLATFORM_SUBSIDY:
			data = "平台补贴";
			break;
		case OTHERS_TRANSFER:
			data = "其他";
			break;
		}
		return data;
	}

	/**
	 * 获取流水支付类型
	 * @param type
	 * @return
	 */
	public static String parsePayType(PayType type) {
		String data = "";
		switch (type) {
		case NETBANK_PAY:
			data = "在线支付";
			break;
		case BANK_DEDUCT:
			data = "银行转账";
			break;
		case PLATFORM_DEDUCT:
			data = "长江电商钱包";
			break;
		case OFFLINE_PAY:
			data = "线下付款";
			break;
		}
		return data;
	}

	/**
	 * 获取企业类型
	 * @param type
	 * @return
	 */
	public static String parseProfileType(Context context, ProfileType type) {
		String data = "";
		switch (type) {
		case COMPANY:
			data = context.getString(R.string.auth_type_company_v1);
			break;
		case SHIP:
			data = context.getString(R.string.auth_type_ship_v1);
			break;
		case PEOPLE:
			data = context.getString(R.string.auth_type_people_v1);
			break;
		}
		return data;
	}

	/**
	 * 获取认证状态类型
	 * @param type
	 * @return
	 */
	public static String parseAuthType(Context context, AuthStatusType type) {
		String data = "";
		switch (type) {
		case UN_AUTH:
			data = context.getString(R.string.auth_status_unauth);
			break;
		case AUTH_SUCCESS:
			data = context.getString(R.string.auth_status_authed);
			break;
		case AUTHING:
			data = context.getString(R.string.auth_status_authing);
			break;
		case AUTH_FAILED:
			data = context.getString(R.string.auth_status_auth_failed);
			break;
		}
		return data;
	}

	/**
	 * 获取保证金状态类型
	 * @param type
	 * @return
	 */
	public static String parseDepositStatusType(Context context, DepositStatusType type) {
		String data = "";
		switch (type) {
		case UN_RECHARGE:
			data = context.getString(R.string.deposit_status_unrecharge);
			break;
		case RECHARGE_SUCCESS:
			data = context.getString(R.string.deposit_status_recharged);
			break;
		}
		return data;
	}

}
