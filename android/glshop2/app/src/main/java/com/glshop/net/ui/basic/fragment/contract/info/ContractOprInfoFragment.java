package com.glshop.net.ui.basic.fragment.contract.info;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr;
import com.glshop.net.ui.basic.timer.GlobalTimerMgr.ITimerListener;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.mycontract.ContractEvaluationListActivity;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.mycontract.PaymentConfirm4BuyerActivity;
import com.glshop.net.ui.mycontract.PaymentConfirm4SellerActivity;
import com.glshop.net.ui.mycontract.PaymentPayWarningActivity;
import com.glshop.net.ui.mypurse.PaymentSubmitActivity;
import com.glshop.net.utils.ContractUtil;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.contract.data.model.ArbitrateInfoModel;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.NumberUtils;
import com.glshop.platform.utils.StringUtils;

import java.util.List;

/**
 * @Description : 进行中和已结束的合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractOprInfoFragment extends BaseContractInfoFragmentV2 implements ITimerListener {

	private View llToPay;
	private View llStatusWarning;
	private View llBillContent;
	private View llOriInfo;
	private View llNormalInfo;
	private View llArbitrateInfo;
	private View llPlatformInfo;
	private View llArbitrateTips;
	private View llPlatformFees; // 平台手续费
	private View llDepositUnfreeze; // 交易保证金解冻
	private View llDepositDeduct; // 保证金扣除
	private View llDeposiReturn; // 货款返还
	private View llDeposiEqualize; // 被违约赔偿
	private View llReceivedPayment; // 实收货款

	private TextView mTvToPayLimitTime;
	private TextView mTvToPayWarning;
	private TextView mTvContractStatus;
	private TextView mTvContractActionWarning;

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
	private TextView mTvArbitrateDealtime;
	private TextView mTvAmount;//最终成交价格
	private TextView mTvAmount2;//最终货款最终成交价格


	private TextView mTvBillDatetime; // 平台结算时间
	private TextView mTvPlatformFees; // 平台手续费
	private TextView mTvDepositUnfreeze; // 交易保证金解冻
	private TextView mTvDepositDeduct; // 保证金扣除
	private TextView mTvDepositReturn; // 货款返还
	private TextView mTvDepositEqualize; // 被违约赔偿
	private TextView mTvReceivedPayment; // 实收货款

	private TextView mTvArbitrateRemarks;

	private Button mBtnToPay;
	private Button mBtnConfirm;
	private Button mBtnViewEva;
	private Button mBtnMove;
	private Button mBtnCancel;

	private ConfirmDialog mCancelDialog;
	private ConfirmDialog mMoveDialog;

	@Override
	protected void initView() {
		llToPay = getView(R.id.ll_to_pay);
		llStatusWarning = getView(R.id.ll_contract_action_warning);
		llBillContent = getView(R.id.ll_bill_content);
		llOriInfo = getView(R.id.ll_contract_original_info);
		llNormalInfo = getView(R.id.ll_contract_normal_bill_info);
		llArbitrateInfo = getView(R.id.ll_contract_arbitrate_bill_info);
		llPlatformInfo = getView(R.id.ll_platform_info);
		llArbitrateTips = getView(R.id.ll_arbitrate_tips_info);
		llPlatformFees = getView(R.id.ll_platform_fees);
		llDepositUnfreeze = getView(R.id.ll_deposit_unfreeze);
		llDepositDeduct = getView(R.id.ll_deposit_deduct);
		llDeposiReturn = getView(R.id.ll_deposit_return);
		llDeposiEqualize = getView(R.id.ll_deposit_equalize);
		llReceivedPayment = getView(R.id.ll_received_payment);

		mTvToPayLimitTime = getView(R.id.tv_to_pay_limit_time);
		mTvToPayWarning = getView(R.id.tv_to_pay_warning);
		mTvContractStatus = getView(R.id.tv_contract_status);
		mTvContractActionWarning = getView(R.id.tv_contract_action_warning);

		mTvOriUnitPrice = getView(R.id.tv_ori_unit_price);
		mTvOriAmount = getView(R.id.tv_ori_amount);
		mTvOriTotalMoney = getView(R.id.tv_ori_total_money);
		mTvPayedMoney = getView(R.id.tv_payed_money);

		mTvNormalUnitPrice = getView(R.id.tv_normal_unit_price);
		mTvNormalAmount = getView(R.id.tv_normal_amount);
		mTvNormalTotalMoney = getView(R.id.tv_normal_total_money);

		mTvArbitrateUnitPrice = getView(R.id.tv_arbitrate_unit_price);
		mTvArbitrateAmount = getView(R.id.tv_arbitrate_amount);
		mTvArbitrateTotalMoney = getView(R.id.tv_arbitrate_total_money);
		mTvArbitrateDealtime = getView(R.id.tv_arbitrate_dealtime);
		mTvAmount=getView(R.id.tv_last_total_money);
		mTvAmount2=getView(R.id.tv_last_total_money_2);
		mTvAmount.setText("无");
		mTvAmount2.setText("无");

		mTvBillDatetime = getView(R.id.tv_bill_datetime);
		mTvPlatformFees = getView(R.id.tv_platform_fees);
		mTvDepositUnfreeze = getView(R.id.tv_deposit_unfreeze);
		mTvDepositDeduct = getView(R.id.tv_deposit_deduct);
		mTvDepositReturn = getView(R.id.tv_deposit_return);
		mTvDepositEqualize = getView(R.id.tv_deposit_equalize);
		mTvReceivedPayment = getView(R.id.tv_received_payment);

		mTvArbitrateRemarks = getView(R.id.tv_arbitrate_remarks);

		mBtnToPay = getView(R.id.btn_contract_to_pay);
		mBtnConfirm = getView(R.id.btn_contract_confirm);
		mBtnViewEva = getView(R.id.btn_contract_eva);
		mBtnMove = getView(R.id.btn_contract_move);
		mBtnCancel = getView(R.id.btn_contract_action_cancel);

		mBtnToPay.setOnClickListener(this);
		mBtnConfirm.setOnClickListener(this);
		mBtnViewEva.setOnClickListener(this);
		mBtnMove.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mContractInfo != null) {
			updateActionBar();
			if (ContractUtil.isValid(mContractInfo)) {
				llBillContent.setVisibility(View.GONE);
			} else {
				llBillContent.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		GlobalTimerMgr.getInstance().addTimerListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		GlobalTimerMgr.getInstance().removeTimerListener(this);
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_CANCEL_SUCCESS: // 合同取消成功
			onCancelContractSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_CANCEL_FAILED: // 合同取消失败
			onCancelContractFailed(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_SUCCESS:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onMultiCancelSuccess(respInfo);
			}
			break;
		case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onMultiCancelFailed(respInfo);
			}
			break;
		case ContractMessageType.MSG_REFRESH_PAY_WAIT_TIME:
			if (mTvToPayLimitTime != null && mContractInfo != null) {
				mTvToPayLimitTime.setText(DateUtils.getWaitTime(mContractInfo.payExpireTime));
			}
			break;
		}
	}

	private void onCancelContractSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.contract_action_cancel);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		tipInfo.operatorTipContent = getString(isBuyer() ? R.string.operator_tips_buyer_cancel_contract_success : R.string.operator_tips_seller_cancel_contract_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);

		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onCancelContractFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onMultiCancelSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);

		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		if (respInfo.intArg1 == ContractCancelType.MOVE_TO_ENDED.toValue()) {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_move_success);
		} else if (respInfo.intArg1 == ContractCancelType.DELETE.toValue()) {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_delete_contract_success);
		}
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);

		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onMultiCancelFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case ContractMessageType.MSG_CONTRACT_CANCEL_FAILED: // 合同取消失败
			case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_contract_to_pay:
			if (checkBalance()) {
				intent = new Intent(mContext, PaymentSubmitActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, mContractInfo.finalPayMoney);
				startActivity(intent);
			} else {
				intent = new Intent(mContext, PaymentPayWarningActivity.class);
				intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
				double needToRecharge = GlobalConfig.getInstance().getPaymentBalance() - mContractInfo.tradeAmount * mContractInfo.unitPrice;
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, NumberUtils.round(Math.abs(needToRecharge), 2));
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.PAYMENT.toValue());
				startActivity(intent);
				finish();
			}
			break;
		case R.id.btn_contract_confirm:
			if (mContractInfo.buyType == BuyType.BUYER) {
				intent = new Intent(mContext, PaymentConfirm4BuyerActivity.class);
			} else if (mContractInfo.buyType == BuyType.SELLER) {
				intent = new Intent(mContext, PaymentConfirm4SellerActivity.class);
			}
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			break;
		case R.id.btn_contract_eva:
			intent = new Intent(mContext, ContractEvaluationListActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
			startActivity(intent);
			break;
		case R.id.btn_contract_action_cancel:
			showCancelDialog();
			break;
		case R.id.btn_contract_move:
			showMoveDialog();
			break;
		}
	}

	private boolean checkBalance() {
		if (GlobalConstants.CfgConstants.VALIDATE_PAYMENT_BALANCE) {
			return GlobalConfig.getInstance().getPaymentBalance() >= mContractInfo.tradeAmount * mContractInfo.unitPrice;
		}
		return true;
	}

	private void updateActionBar() {
		if (mContractInfo != null) {
			if (mContractInfo.buyType == BuyType.BUYER) {
				updateBuyerActionBar();
			} else {
				updateSellerActionBar();
			}
		}
	}

	private void updateBuyerActionBar() {
		if (mContractInfo.lifeCycle == ContractLifeCycle.SINGED) {
			llToPay.setVisibility(View.VISIBLE);
			mTvContractStatus.setVisibility(View.GONE);
		} else {
			llToPay.setVisibility(View.GONE);
			mTvContractStatus.setVisibility(View.VISIBLE);
		}

		switch (mContractInfo.lifeCycle) {
		case SINGED: // 已签订
			mTvToPayLimitTime.setText(DateUtils.getWaitTime(mContractInfo.payExpireTime));
			mTvToPayWarning.setText(mContext.getString(R.string.contract_action_pay_warning1));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_to_pay_warning));

			mBtnToPay.setVisibility(View.VISIBLE);
			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case PAYED_FUNDS: // 已付款
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_payed_buyer));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_payment_confirm_warning1));

			mBtnConfirm.setVisibility(View.VISIBLE);
			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case CONFIRMING_GOODS_FUNDS: // 货款确认中
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_confirmed1));
			String limitTime = SysCfgUtils.getSellerConfirmLimitTime(mContext);
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_wait_confirm_warning1, limitTime));

			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case ARBITRATING: // 仲裁中 
			if (ContractUtil.isMineApplyArbitrate(mContractInfo)) {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_arbitrating));
			} else {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_seller_arbitrating));
			}
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_arbitrating_warning));
			break;
		case BUYER_UNPAY_FINISHED: // 买家未付款结束
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_unpay_finished_buyer));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_unpay_finished_warning1));

			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case SINGLECANCEL_FINISHED: // 取消结束
			if (ContractUtil.isMineCanceled(mContractInfo)) {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_canceled_finished));
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_canceled_finished_warning2));
			} else {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_seller_canceled_finished));
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_canceled_finished_warning4));
			}

			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case ARBITRATED: // 仲裁结束
			llToPay.setVisibility(View.GONE);
			mTvContractStatus.setVisibility(View.GONE);
			llStatusWarning.setVisibility(View.GONE);
			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case NORMAL_FINISHED: // 正常结束
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_normal_finished));
			if (isMineDoneEva(mContractInfo)) {
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_buyer_normal_finished_warning2));
			} else {
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_buyer_normal_finished_warning1));
			}
			// 显示结算信息
			updateBillInfo();

			mBtnViewEva.setVisibility(View.VISIBLE);
			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		default:
			// TODO
			break;
		}
	}

	private void updateSellerActionBar() {
		if (mContractInfo.lifeCycle == ContractLifeCycle.SINGED) {
			llToPay.setVisibility(View.VISIBLE);
			mTvContractStatus.setVisibility(View.GONE);
		} else {
			llToPay.setVisibility(View.GONE);
			mTvContractStatus.setVisibility(View.VISIBLE);
		}

		switch (mContractInfo.lifeCycle) {
		case SINGED: // 已签订
			mTvToPayLimitTime.setText(DateUtils.getWaitTime(mContractInfo.payExpireTime));
			mTvToPayWarning.setText(mContext.getString(R.string.contract_action_pay_warning2));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_send_warning));

			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case PAYED_FUNDS: // 已付款
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_payed_seller));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_payment_confirm_warning2));

			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case CONFIRMING_GOODS_FUNDS: // 货款确认中
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_confirmed2));
			String limitTime = SysCfgUtils.getSellerConfirmLimitTime(mContext);
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_wait_confirm_warning2, limitTime, limitTime));

			mBtnConfirm.setText(mContext.getString(R.string.contract_action_seller_payment_confirm));
			mBtnConfirm.setVisibility(View.VISIBLE);
			mBtnCancel.setVisibility(View.VISIBLE);
			break;
		case ARBITRATING: // 仲裁中 
			if (ContractUtil.isMineApplyArbitrate(mContractInfo)) {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_arbitrating));
			} else {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_buyer_arbitrating));
			}
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_arbitrating_warning));
			break;
		case BUYER_UNPAY_FINISHED: // 买家未付款结束
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_unpay_finished_seller));
			mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_unpay_finished_warning2));

			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case SINGLECANCEL_FINISHED: // 取消结束
			if (ContractUtil.isMineCanceled(mContractInfo)) {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_canceled_finished));
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_canceled_finished_warning1));
			} else {
				mTvContractStatus.setText(mContext.getString(R.string.contract_status_buyer_canceled_finished));
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_canceled_finished_warning3));
			}

			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case ARBITRATED: // 仲裁结束
			llToPay.setVisibility(View.GONE);
			mTvContractStatus.setVisibility(View.GONE);
			llStatusWarning.setVisibility(View.GONE);

			// 显示结算信息
			updateBillInfo();

			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		case NORMAL_FINISHED: // 正常结束
			mTvContractStatus.setText(mContext.getString(R.string.contract_status_normal_finished));
			if (isMineDoneEva(mContractInfo)) {
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_seller_normal_finished_warning2));
			} else {
				mTvContractActionWarning.setText(mContext.getString(R.string.contract_action_seller_normal_finished_warning1));
			}

			// 显示结算信息
			updateBillInfo();

			mBtnViewEva.setVisibility(View.VISIBLE);
			if (!isMovedToEnded(mContractInfo)) {
				mBtnMove.setVisibility(View.VISIBLE);
			}
			break;
		default:
			// TODO
			break;
		}
	}

	private void updateBillInfo() {
		if (mContractInfo != null) {
			mTvOriUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
			mTvOriAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
			mTvOriTotalMoney.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.tradeAmount * mContractInfo.unitPrice)));
			mTvPayedMoney.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.payedMoney)));

			updateBillDatetime();
			updateDealInfo(llDepositUnfreeze, mTvDepositUnfreeze, DealOprType.UNGELATION_GUARANTY, true); // 保证金解冻
			switch (mContractInfo.lifeCycle) {
			case NORMAL_FINISHED: // 正常结束
				llNormalInfo.setVisibility(View.VISIBLE);
				if (0.0==mContractInfo.finalyAmount){
					mTvAmount.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.receivedMoney)));
					mTvAmount2.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.receivedMoney)));
				}else {
					mTvAmount.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.finalyAmount)));
					mTvAmount2.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.finalyAmount)));
				}
				if (mContractInfo.finalNegotiateInfo != null) {
					NegotiateInfoModel negInfo = mContractInfo.finalNegotiateInfo;
					mTvNormalUnitPrice.setText(StringUtils.getDefaultNumber(negInfo.negUnitPrice));
					mTvNormalAmount.setText(StringUtils.getDefaultNumber(negInfo.negAmount));
					mTvNormalTotalMoney.setText(StringUtils.getCashNumber(String.valueOf(negInfo.negUnitPrice * negInfo.negAmount)));
				} else {
					mTvNormalUnitPrice.setText(StringUtils.getDefaultNumber(mContractInfo.unitPrice));
					mTvNormalAmount.setText(StringUtils.getDefaultNumber(mContractInfo.tradeAmount));
					mTvNormalTotalMoney.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.tradeAmount * mContractInfo.unitPrice)));
				}
				if (isBuyer()) {
					updateDealInfo(llDeposiReturn, mTvDepositReturn, DealOprType.PLATFORM_RETURN, true); // 货款返还
				} else {
					updateDealInfo(llPlatformFees, mTvPlatformFees, DealOprType.SERVICE_CHARGE, true); // 平台手续费
					//updateDealInfo(llReceivedPayment, mTvReceivedPayment, DealOprType.PAYMENT_FOR_GOODS, true); // 实收货款
					updateReceivedPayment(); // 实收货款
				}
				break;
			case SINGLECANCEL_FINISHED: // 取消结束
				if (isBuyer()) {
					if (ContractUtil.isMineCanceled(mContractInfo)) {
						updateDealInfo(llDepositDeduct, mTvDepositDeduct, DealOprType.VIOLATION_DEDUCTION, true); // 违约保证金扣除
						updateDealInfo(llDeposiReturn, mTvDepositReturn, DealOprType.PLATFORM_RETURN, true); // 货款返还
					} else {
						updateDealInfo(llDeposiEqualize, mTvDepositEqualize, DealOprType.VIOLATION_REPARATION, true); // 被违约赔偿
						updateDealInfo(llDeposiReturn, mTvDepositReturn, DealOprType.PLATFORM_RETURN, true); // 货款返还
					}
				} else {
					if (ContractUtil.isMineCanceled(mContractInfo)) {
						updateDealInfo(llDepositDeduct, mTvDepositDeduct, DealOprType.VIOLATION_DEDUCTION, true); // 违约保证金扣除
					} else {
						updateDealInfo(llDeposiEqualize, mTvDepositEqualize, DealOprType.VIOLATION_REPARATION, true); // 被违约赔偿
					}
				}
				break;
			case ARBITRATED: // 仲裁结束
				llArbitrateInfo.setVisibility(View.VISIBLE);
				llArbitrateTips.setVisibility(View.VISIBLE);
				ArbitrateInfoModel arbInfo = mContractInfo.arbitrateInfo;
				if (arbInfo != null) {

					mTvArbitrateUnitPrice.setText(StringUtils.getDefaultNumber(arbInfo.unitPrice));
					mTvArbitrateAmount.setText(StringUtils.getDefaultNumber(arbInfo.amount));

					mTvArbitrateTotalMoney.setText(StringUtils.getCashNumber(arbInfo.amount * arbInfo.unitPrice));
					mTvArbitrateDealtime.setText(arbInfo.dealTime);
					if (StringUtils.isNotEmpty(arbInfo.remarks)) {
						mTvArbitrateRemarks.setText(arbInfo.remarks);
					} else {
						mTvArbitrateRemarks.setText(getString(R.string.data_empty));
					}
					if (0.0==mContractInfo.arbitrateInfo.amount*mContractInfo.arbitrateInfo.unitPrice){
						mTvAmount.setText(StringUtils.getCashNumber(String.valueOf(0.0)));
						mTvAmount2.setText(StringUtils.getCashNumber(String.valueOf(0.0)));
					}else {
						mTvAmount.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.receivedMoney)));
						mTvAmount2.setText(StringUtils.getCashNumber(String.valueOf(mContractInfo.receivedMoney)));
					}
				} else {
					mTvArbitrateUnitPrice.setText("0.00");
					mTvArbitrateAmount.setText("0.00");
					mTvArbitrateTotalMoney.setText("0.00");
					mTvArbitrateRemarks.setText(getString(R.string.data_empty));
				}

				if (isBuyer()) {
					updateDealInfo(llDeposiReturn, mTvDepositReturn, DealOprType.PLATFORM_RETURN, true); // 货款返还
				} else {
					//updateDealInfo(llReceivedPayment, mTvReceivedPayment, DealOprType.PAYMENT_FOR_GOODS, true); // 实收货款
					updateReceivedPayment(); // 实收货款
				}
				break;
			case BUYER_UNPAY_FINISHED: // 买家未付款结束
				if (isBuyer()) {
					updateDealInfo(llDepositDeduct, mTvDepositDeduct, DealOprType.VIOLATION_DEDUCTION, true); // 违约保证金扣除
				} else {
					updateDealInfo(llDeposiEqualize, mTvDepositEqualize, DealOprType.VIOLATION_REPARATION, true); // 被违约赔偿
				}
				break;
			}
		}
	}

	private void updateBillDatetime() {
		String datetime = "";
		List<DealSummaryInfoModel> dealList = mContractInfo.dealList;
		if (BeanUtils.isNotEmpty(dealList)) {
			DealSummaryInfoModel info = getDealInfo(dealList, DealOprType.UNGELATION_GUARANTY);
			if (info != null) {
				datetime = info.dealTime;
			}
		}
		if (StringUtils.isNEmpty(datetime)) {
			datetime = mContractInfo.updateTime;
		}
		mTvBillDatetime.setText(datetime);
	}

	/*private void updatePayedMoney() {
		List<DealSummaryInfoModel> dealList = mContractInfo.dealList;
		if (BeanUtils.isNotEmpty(dealList)) {
			DealSummaryInfoModel info = getDealInfo(dealList, DealOprType.PAYMENT_FOR_GOODS);
			if (info != null) {
				mTvPayedMoney.setText(StringUtils.getCashNumber(String.valueOf(info.dealMoney)));
			} else {
				mTvPayedMoney.setText("0");
			}
		} else {
			mTvPayedMoney.setText("0");
		}
	}*/

	private void updateReceivedPayment() {
		llReceivedPayment.setVisibility(View.VISIBLE);
		if (mContractInfo.receivedMoney != 0) {
			mTvReceivedPayment.setText("+" + StringUtils.getCashNumber(String.valueOf(mContractInfo.receivedMoney)));
			mTvReceivedPayment.setTextColor(mContext.getResources().getColor(R.color.green_deal));
		} else {
			mTvReceivedPayment.setText("0.00");
		}
	}

	private void updateDealInfo(View container, TextView view, DealOprType type, boolean forceToShow) {
		if (view != null) {
			List<DealSummaryInfoModel> dealList = mContractInfo.dealList;
			if (BeanUtils.isNotEmpty(dealList) && type != null) {
				DealSummaryInfoModel info = getDealInfo(dealList, type);
				if (info != null) {
					container.setVisibility(View.VISIBLE);
					if (info.directionType == DealDirectionType.IN) {
						view.setText("+" + StringUtils.getCashNumber(String.valueOf(info.dealMoney)));
						view.setTextColor(mContext.getResources().getColor(R.color.green_deal));
					} else {
						view.setText("-" + StringUtils.getCashNumber(String.valueOf(info.dealMoney)));
						view.setTextColor(mContext.getResources().getColor(R.color.orange));
					}
				} else {
					if (forceToShow) {
						container.setVisibility(View.VISIBLE);
						view.setText("0.00");
					} else {
						container.setVisibility(View.GONE);
					}
				}
			} else {
				if (forceToShow) {
					container.setVisibility(View.VISIBLE);
					view.setText("0.00");
				} else {
					container.setVisibility(View.GONE);
				}
			}
		}
	}

	private DealSummaryInfoModel getDealInfo(List<DealSummaryInfoModel> list, DealOprType type) {
		for (DealSummaryInfoModel info : list) {
			if (type == info.oprType) {
				return info;
			}
		}
		return null;
	}

	private boolean isBuyer() {
		return mContractInfo.buyType == BuyType.BUYER;
	}

	private boolean isMineDoneEva(ContractSummaryInfoModel info) {
		if (info.buyType == BuyType.BUYER && info.isBuyerEva) {
			return true;
		} else if (info.buyType == BuyType.SELLER && info.isSellerEva) {
			return true;
		}
		return false;
	}

	private boolean isMovedToEnded(ContractInfoModel info) {
		if (info.myContractType == ContractType.ENDED || info.myContractType == ContractType.DELETION) {
			return true;
		}
		return false;
	}

	private void showCancelDialog() {
		closeDialog(mCancelDialog);
		mCancelDialog = new ConfirmDialog(mContext, R.style.dialog);
		mCancelDialog.setContent(getString(isBuyer() ? R.string.buyer_cancel_contract_warning_tips : R.string.seller_cancel_contract_warning_tips));
		mCancelDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				doCancelAction();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mCancelDialog.show();
	}

	private void showMoveDialog() {
		closeDialog(mMoveDialog);
		mMoveDialog = new ConfirmDialog(mContext, R.style.dialog);
		mMoveDialog.setContent(getString(R.string.move_contract_warning_tips));
		mMoveDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				doMoveAction();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mMoveDialog.show();
	}

	/**
	 * 取消合同操作
	 */
	private void doCancelAction() {
		showSubmitDialog();
		mContractLogic.cancelContract(mContractInfo.contractId);
	}

	/**
	 * 移至已结束合同操作
	 */
	private void doMoveAction() {
		showSubmitDialog();
		mInvoker = String.valueOf(System.currentTimeMillis());
		mContractLogic.multiCancelContract(mInvoker, mContractInfo.contractId, ContractCancelType.MOVE_TO_ENDED);
	}

	@Override
	public void onTimerTick() {
		if (mContractInfo != null && mContractInfo.lifeCycle == ContractLifeCycle.SINGED) {
			getHandler().sendEmptyMessage(ContractMessageType.MSG_REFRESH_PAY_WAIT_TIME);
		}
	}

}
