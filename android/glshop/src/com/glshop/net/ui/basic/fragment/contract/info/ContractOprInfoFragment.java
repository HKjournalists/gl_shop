package com.glshop.net.ui.basic.fragment.contract.info;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.mypurse.PaymentSubmitActivity;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;

/**
 * @Description : 进行中和已结束的合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractOprInfoFragment extends BaseContractInfoFragmentV2 {

	private View llBillContent;
	private View llOriInfo;
	private View llNormalInfo;
	private View llArbitrateInfo;
	private View llPlatformInfo;
	private View llArbitrateTips;

	private TextView mTvOriUnitPrice;
	private TextView mTvOriAmount;
	private TextView mTvOriTotalMoney;
	private TextView mTvPayedMoney;

	private TextView mTvNormalUnitPrice;
	private TextView mTvNormalAmount;
	private TextView mTvNormalTotalMoney;

	private TextView mTvArbitrateUnitPrice;
	private TextView mTvArbitrateAmount;
	private TextView mTvArbitrateTotalMoney;

	private TextView mTvDepositUnfreeze;
	private TextView mTvDepositDeduct;
	private TextView mTvDepositReturn;

	private TextView mTvArbitrateRemarks;

	@Override
	protected void initView() {
		mBtnAction = getView(R.id.btn_contract_action);
		llBillContent = getView(R.id.ll_bill_content);
		llOriInfo = getView(R.id.ll_contract_original_info);
		llNormalInfo = getView(R.id.ll_contract_normal_bill_info);
		llArbitrateInfo = getView(R.id.ll_contract_arbitrate_bill_info);
		llPlatformInfo = getView(R.id.ll_platform_info);
		llArbitrateTips = getView(R.id.ll_arbitrate_tips_info);

		mTvOriUnitPrice = getView(R.id.tv_ori_unit_price);
		mTvOriAmount = getView(R.id.tv_ori_amount);
		mTvOriTotalMoney = getView(R.id.tv_ori_total_money);
		mTvPayedMoney = getView(R.id.tv_payed_money);

		mTvNormalUnitPrice = getView(R.id.tv_normal_unit_price);
		mTvNormalAmount = getView(R.id.tv_normal_amount);
		mTvNormalTotalMoney = getView(R.id.tv_normal_total_money);

		mTvArbitrateUnitPrice = getView(R.id.tv_arbitrate_unit_price);
		mTvArbitrateAmount = getView(R.id.tv_arbitrate_amount);
		mTvNormalTotalMoney = getView(R.id.tv_arbitrate_total_money);

		mTvDepositUnfreeze = getView(R.id.tv_deposit_unfreeze);
		mTvDepositDeduct = getView(R.id.tv_deposit_deduct);
		mTvDepositReturn = getView(R.id.tv_deposit_return);

		mTvArbitrateRemarks = getView(R.id.tv_arbitrate_remarks);

		mBtnAction.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mContractInfo != null) {
			updateActionBar();
			if (isValid(mContractInfo)) {
				llBillContent.setVisibility(View.GONE);
			} else {
				llBillContent.setVisibility(View.VISIBLE);
				updateBillInfo();
			}
		} else {
			// TODO
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_contract_action:
			intent = new Intent(mContext, PaymentSubmitActivity.class);
			//intent = new Intent(mContext, PaymentConfirm4BuyerActivity.class);
			//intent = new Intent(mContext, PaymentConfirm4SellerActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void updateActionBar() {

	}

	private void updateBillInfo() {
		if (mContractInfo != null) {
			switch (mContractInfo.lifeCycle) {
			case NORMAL_FINISHED: // 正常结束

				break;
			case SINGLECANCEL_FINISHED: // 取消结束

				break;
			case ARBITRATED: // 仲裁结束

				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束

				break;
			}
		}
	}

	private void updateStatus(ContractInfoModel info) {
		String statusDetail = "";
		String nextStatus = "";

		if (info.buyType == BuyType.BUYER) {
			switch (info.lifeCycle) {
			case SINGED: // 已签订
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_1);
				break;
			case PAYED_FUNDS: // 已付款
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_1);
				break;
			case CONFIRMING_GOODS_FUNDS: // 货款确认中
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_2);
				break;
			case ARBITRATING: // 仲裁中 
				if (isMimeApplyArbitrate(info)) {
					statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_5);
				} else {
					statusDetail = mContext.getString(R.string.ongoing_contract_buyer_valid_6);
				}
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_6);
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_3);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case ARBITRATED: // 仲裁结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case NORMAL_FINISHED: // 正常结束
				statusDetail = mContext.getString(R.string.ongoing_contract_buyer_invalid_4);
				if (isMimeDoneEva(info)) {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_8);
				} else {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_4);
				}
				break;
			default:
				// TODO
				break;
			}
		} else if (info.buyType == BuyType.SELLER) {
			switch (info.lifeCycle) {
			case SINGED: // 已签订
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_1);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_1);
				break;
			case PAYED_FUNDS: // 已付款
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_2);
				break;
			case CONFIRMING_GOODS_FUNDS: // 货款确认中
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_3);
				break;
			case ARBITRATING: // 仲裁中 
				if (isMimeApplyArbitrate(info)) {
					statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_6);
				} else {
					statusDetail = mContext.getString(R.string.ongoing_contract_seller_valid_5);
				}
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_6);
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_seller_action_4);
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_2);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case ARBITRATED: // 仲裁结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_3);
				nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_7);
				break;
			case NORMAL_FINISHED: // 正常结束
				statusDetail = mContext.getString(R.string.ongoing_contract_seller_invalid_4);
				if (isMimeDoneEva(info)) {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_8);
				} else {
					nextStatus = mContext.getString(R.string.ongoing_contract_buyer_action_4);
				}
				break;
			default:
				// TODO
				break;
			}
		}

	}

	private boolean isValid(ContractInfoModel info) {
		boolean isValid = false;
		switch (info.lifeCycle) {
		case SINGED: // 已签订
		case PAYED_FUNDS: // 已付款
		case CONFIRMING_GOODS_FUNDS: // 货款确认中
		case ARBITRATING: // 仲裁中 
			isValid = true;
			break;
		}
		return isValid;
	}

	private boolean isMimeApplyArbitrate(ContractInfoModel info) {
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

	private boolean isMimeDoneEva(ContractInfoModel info) {
		if (info.buyType == BuyType.BUYER) {
			if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
				return true;
			}
		} else if (info.buyType == BuyType.SELLER) {
			if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
				return true;
			}
		}
		return false;
	}

}
