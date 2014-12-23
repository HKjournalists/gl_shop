package com.glshop.net.utils;

import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.PayType;

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

}
