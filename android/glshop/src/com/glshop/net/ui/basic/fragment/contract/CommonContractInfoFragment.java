package com.glshop.net.ui.basic.fragment.contract;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.mycontract.ContractEvaluationListActivity;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.mycontract.InputFirstNegotiateActivity;
import com.glshop.net.ui.mycontract.InputSecondNegotiateActivity;
import com.glshop.net.ui.mypurse.SelectRechargeTypeActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 进行中的合同信息界面(基本状态)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class CommonContractInfoFragment extends BaseContractInfoFragment {

	private TextView mTvContractStatusTips;

	private boolean isFirstCheckingStep;

	@Override
	protected void initView() {
		mBtnAction1 = getView(R.id.btn_contract_action_1);
		mBtnAction2 = getView(R.id.btn_contract_action_2);
		mBtnActionCancel = getView(R.id.btn_contract_action_cancel);
		mTvContractStatusTips = getView(R.id.tv_contract_status_info);
		mTvContractId = getView(R.id.tv_contract_id);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		mContractStatus = getView(R.id.ll_contract_status);

		getView(R.id.btn_contract_action_1).setOnClickListener(this);
		getView(R.id.btn_contract_action_2).setOnClickListener(this);
		getView(R.id.btn_contract_action_cancel).setOnClickListener(this);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		mTvContractStatusTips.setText(getString(R.string.contract_to_pay_warning_tip));
		if (mContractInfo != null) {
			mTvContractId.setText(mContractInfo.contractId);
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
			mContractStatus.setContractInfo(mContractInfo);
		} else {

		}
		updateActionStatus();
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_SUCCESS: // 合同验收或同意议价成功
			onAcceptanceSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_FAILED: // 合同验收或同意议价失败
			onAcceptanceFailed(respInfo);
			break;
		case ContractMessageType.MSG_COMFIRM_DISCHARGE_SUCCESS: // 确认卸货成功
			onDischargeSuccess(respInfo);
			break;
		case ContractMessageType.MSG_COMFIRM_DISCHARGE_FAILED: // 确认卸货失败
			onDischargeFailed(respInfo);
			break;
		case ContractMessageType.MSG_COMFIRM_RECEIPT_SUCCESS: // 确认收货成功
			onReceiptSuccess(respInfo);
			break;
		case ContractMessageType.MSG_COMFIRM_RECEIPT_FAILED: // 确认卸货失败
			onReceiptFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_contract_action_1:
			doFirstAction();
			break;
		case R.id.btn_contract_action_2:
			doSecondAction();
			break;
		}
	}

	private void updateActionStatus() {
		if (mContractInfo.buyType == BuyType.BUYER) {
			// 买家状态
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
				// 操作：支付货款
				mTvContractStatusTips.setText(getString(R.string.contract_to_pay_warning_tip));
				mBtnAction1.setText(getString(R.string.contract_action_pay));
				mBtnAction1.setVisibility(View.VISIBLE);
				mBtnAction2.setVisibility(View.GONE);
				getView(R.id.devider_action_2).setVisibility(View.GONE);
				break;
			case IN_THE_PAYMENT: // 付款中
				// 操作：无
				mTvContractStatusTips.setText(getString(R.string.contract_to_pay_warning_tip));
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case PAYED_FUNDS: // 已付款
				// 操作：开始抽样验收
				mTvContractStatusTips.setText(getString(R.string.contract_to_first_check_warning_tip));
				if (!isFirstCheckingStep) {
					// 开始验收
					mBtnAction1.setText(getString(R.string.contract_action_begin_first_check));
					mBtnAction1.setVisibility(View.VISIBLE);
					mBtnAction2.setVisibility(View.GONE);
					getView(R.id.devider_action_1).setVisibility(View.VISIBLE);
					getView(R.id.devider_action_2).setVisibility(View.GONE);
					mBtnAction1.setBackgroundResource(R.drawable.selector_btn_orange);
				} else {
					// 验收通过 & 验收不通过
					mBtnAction1.setText(getString(R.string.contract_action_begin_first_check_pass));
					mBtnAction2.setText(getString(R.string.contract_action_begin_first_check_failed));
					mBtnAction1.setVisibility(View.VISIBLE);
					mBtnAction2.setVisibility(View.VISIBLE);
					getView(R.id.devider_action_1).setVisibility(View.VISIBLE);
					getView(R.id.devider_action_2).setVisibility(View.VISIBLE);
					mBtnAction1.setBackgroundResource(R.drawable.selector_btn_green);
					mBtnAction2.setBackgroundResource(R.drawable.selector_btn_orange);
				}
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：全量验收通过 & 验收不通过
				break;
			case FULL_TAKEOVERING:
				// 同意 &不同意第二次议价
				break;
			case FULL_TAKEOVERED: // 全量验收通过 
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case UNINSTALLED_GOODS: // 已卸货 
				// 操作：确认收货
				mTvContractStatusTips.setText(getString(R.string.operator_tips_confirm_receipt));
				mBtnAction1.setText(getString(R.string.contract_action_confirm_discharge));
				mBtnAction1.setVisibility(View.VISIBLE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case NORMAL_FINISHED: // 正常结束
				// 操作：立即评价
				if (mContractInfo.buyerStatus != null && mContractInfo.buyerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
					// 已评价
					mBtnAction1.setVisibility(View.GONE);
					mBtnAction2.setVisibility(View.GONE);
				} else {
					// 未评价
					mBtnAction1.setText(getString(R.string.contract_action_view_evaluation));
					mBtnAction1.setVisibility(View.VISIBLE);
					mBtnAction2.setVisibility(View.GONE);
				}
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			case CANCELING: // 取消中
				// 操作：同意取消
				if (mContractInfo.buyerStatus != null && mContractInfo.buyerStatus.oprType == ContractOprType.CANCEL_CONFIRM) {
					mBtnAction1.setVisibility(View.GONE);
				} else {
					mBtnAction1.setText(getString(R.string.contract_action_confirm_cancel));
					mBtnAction1.setVisibility(View.VISIBLE);
				}
				mBtnAction2.setVisibility(View.GONE);
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			default:
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			}
		} else {
			// 卖家状态
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
			case IN_THE_PAYMENT: // 付款中
				// 操作：无
				mTvContractStatusTips.setText(getString(R.string.contract_to_wait_buyer_pay_tip));
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
			case PAYED_FUNDS: // 已付款
				// 操作：无
				mTvContractStatusTips.setText(getString(R.string.contract_to_wait_buyer_first_check_tip));
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：无
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case FULL_TAKEOVERING: // 全量验收中
				// 操作：验收通过 & 验收不通过
				mBtnAction1.setText("同意全量验收通过");
				mBtnAction2.setText("不同意全量验收通过,有异议");
				mBtnAction1.setVisibility(View.VISIBLE);
				mBtnAction2.setVisibility(View.VISIBLE);
				break;
			case FULL_TAKEOVERED: // 议价全量验收通过
				// 操作：确认卸货
				mBtnAction1.setText(getString(R.string.contract_action_confirm_delivery));
				mBtnAction1.setVisibility(View.VISIBLE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case UNINSTALLED_GOODS: // 已卸货
			case NORMAL_FINISHED: // 正常结束
				// 操作：立即评价
				if (mContractInfo.sellerStatus != null && mContractInfo.sellerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
					// 已评价
					mBtnAction1.setVisibility(View.GONE);
					mBtnAction2.setVisibility(View.GONE);
				} else {
					// 未评价
					mBtnAction1.setText(getString(R.string.contract_action_view_evaluation));
					mBtnAction1.setVisibility(View.VISIBLE);
					mBtnAction2.setVisibility(View.GONE);
				}
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			case CANCELING: // 取消中
				// 操作：同意取消
				if (mContractInfo.sellerStatus != null && mContractInfo.sellerStatus.oprType == ContractOprType.CANCEL_CONFIRM) {
					mBtnAction1.setVisibility(View.GONE);
				} else {
					mBtnAction1.setText(getString(R.string.contract_action_confirm_cancel));
					mBtnAction1.setVisibility(View.VISIBLE);
				}
				mBtnAction2.setVisibility(View.GONE);
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			default:
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				mBtnActionCancel.setVisibility(View.GONE);
				break;
			}
		}
	}

	private void doFirstAction() {
		Intent intent = null;
		if (mContractInfo.buyType == BuyType.BUYER) {
			switch (mContractInfo.lifeCycle) {
			// 买家操作
			case SINGED: // 已签订
				// 操作：支付货款
				intent = new Intent(mContext, SelectRechargeTypeActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.PAYMENT.toValue());
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, mContractInfo.finalPayMoney);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
				startActivity(intent);
				break;
			case PAYED_FUNDS: // 已付款
				// 操作：开始抽样验收
				if (!isFirstCheckingStep) {
					isFirstCheckingStep = true;
					// 开始验收 -> 验收通过 & 验收不通过
					updateActionStatus();
				} else {
					// 验收通过 -> 提交
					doAcceptanceContract();
				}
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：全量验收通过 & 验收不通过
				// 全量验收通过
				break;
			case FULL_TAKEOVERING:
				// 同意 &不同意第二次议价
				break;
			case UNINSTALLED_GOODS: // 已卸货 
				// 操作：确认收货
				showSubmitDialog();
				mContractLogic.confirmReceipt(mContractInfo.contractId);
				break;
			case RECEIVED_GOODS: // 已收货
			case NORMAL_FINISHED: // 正常结束
				// 操作：立即评价
				/*if (mContractInfo.buyerStatus != null && mContractInfo.buyerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
					// 已评价
				} else {
					// 未评价
					toEvaluateContract();
				}*/
				toEvaluateContract();
				break;
			}
		} else {
			// 卖家操作
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
				// 操作：无
				mTvContractStatusTips.setText(getString(R.string.contract_to_pay_warning_tip));
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：全量验收通过 & 验收不通过
				break;
			case FULL_TAKEOVERING: // 全量验收中
				// 操作：同意全量验收通过
				doAcceptanceContract();
				break;
			case FULL_TAKEOVERED: // 全量验收通过
				// 操作：确认卸货
				showSubmitDialog();
				mContractLogic.confirmDischarge(mContractInfo.contractId);
				break;
			case UNINSTALLED_GOODS: // 卸货成功
			case NORMAL_FINISHED: // 正常结束
				// 操作：立即评价
				/*if (mContractInfo.sellerStatus != null && mContractInfo.sellerStatus.oprType == ContractOprType.EVALUATION_CONTRACT) {
					// 已评价
				} else {
					// 未评价
					toEvaluateContract();
				}*/
				toEvaluateContract();
				break;
			}
		}
	}

	private void doSecondAction() {
		Intent intent = null;
		if (mContractInfo.buyType == BuyType.BUYER) {
			// 买家操作
			switch (mContractInfo.lifeCycle) {
			case PAYED_FUNDS: // 已付款
				// 操作：开始抽样验收 -> 验收不通过
				if (isFirstCheckingStep) {
					intent = new Intent(mContext, InputFirstNegotiateActivity.class);
					intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
					startActivity(intent);
				}
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：全量验收通过 & 验收不通过
				// 全量验收通过
				break;
			case FULL_TAKEOVERING:
				// 同意 &不同意第二次议价
				break;
			}
		} else {
			// 卖家操作
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
				// 操作：无
				mTvContractStatusTips.setText(getString(R.string.contract_to_pay_warning_tip));
				mBtnAction1.setVisibility(View.GONE);
				mBtnAction2.setVisibility(View.GONE);
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				// 操作：同意 &不同意第一次议价
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				// 操作：全量验收通过 & 验收不通过
				break;
			case FULL_TAKEOVERING:
				// 操作：不同意全量验收通过
				intent = new Intent(mContext, InputSecondNegotiateActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
				startActivity(intent);
				break;
			case FULL_TAKEOVERED: // 议价全量验收通过
				// 操作：无
				break;
			}
		}

	}

	/**
	 * 合同验收或同意议价
	 */
	private void doAcceptanceContract() {
		showSubmitDialog();
		if (mContractInfo.buyType == BuyType.BUYER && mContractInfo.lifeCycle == ContractLifeCycle.PAYED_FUNDS) {
			// 买家第一次抽样验收无议价，直接验收通过
			mContractLogic.acceptanceContract(mContractInfo.contractId, DataConstants.ContractOprType.VALIDATE_PASS.toValue());
		} else {
			// 其它
			mContractLogic.acceptanceContract(mContractInfo.contractId, DataConstants.ContractOprType.APPLY_DISPRICE.toValue());
		}
	}

	private void onReceiptSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_delivery_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
	}

	private void onReceiptFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onDischargeSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_discharge_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
	}

	private void onDischargeFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onAcceptanceSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_agree_second_negotiate_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
	}

	private void onAcceptanceFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void toEvaluateContract() {
		Intent intent = new Intent(mContext, ContractEvaluationListActivity.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		startActivity(intent);
	}

	@Override
	public boolean onBack() {
		boolean canBack = true;
		if (mContractInfo.buyType == BuyType.BUYER) {
			switch (mContractInfo.lifeCycle) {
			case PAYED_FUNDS: // 已付款
				if (isFirstCheckingStep) {
					isFirstCheckingStep = false;
					// 验收通过 & 验收不通过 -> 开始验收
					updateActionStatus();
					canBack = false;
				}
				break;
			}
		}
		return canBack;
	}

}
