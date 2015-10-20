package com.glshop.net.utils;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;

/**
 * 合同工具类
 */
public class ContractUtil {

	/**
	 * 判断合同状态是否有效
	 * @param info
	 * @return
	 */
	public static boolean isValid(ContractSummaryInfoModel info) {
		return info.contractType != ContractType.ENDED && info.contractType != ContractType.DELETION;
	}

	/**
	 * 判断是否为本人申请的仲裁
	 * @param info
	 * @return
	 */
	public static boolean isMineApplyArbitrate(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER) {
			if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.APPLY_ARBITRATION) {
				return true;
			}
		} else if (info.buyType == BuyType.SELLER) {
			if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.APPLY_ARBITRATION) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否本人已评价
	 * @param info
	 * @return
	 */
	public static boolean isMineDoneEva(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER && info.isBuyerEva) {
			return true;
		} else if (info.buyType == BuyType.SELLER && info.isSellerEva) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为本人取消的合同
	 * @param info
	 * @return
	 */
	public static boolean isMineCanceled(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER) {
			if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.SINGLE_CANCEL) {
				return true;
			}
		} else if (info.buyType == BuyType.SELLER) {
			if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.SINGLE_CANCEL) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断合同是否可移至已结束
	 * @param info
	 * @return
	 */
	public static boolean isAutoMoveMode(ContractSummaryInfoModel info) {
		if (!isValid(info)) {
			if (info.lifeCycle == ContractLifeCycle.SINGLECANCEL_FINISHED || info.lifeCycle == ContractLifeCycle.BUYER_UNPAY_FINISHED || info.lifeCycle == ContractLifeCycle.ARBITRATED
					|| info.lifeCycle == ContractLifeCycle.NORMAL_FINISHED) {
				return DateUtils.covertDate2Long(DateUtils.COMMON_DATE_FORMAT, info.expireTime) >= System.currentTimeMillis();
			}
		}
		return false;
	}
}
