package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.ListItemView;
import com.glshop.net.ui.mycontract.ToPayContractListActivity;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.PurseInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的钱包主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseActivity extends BasicActivity {

	private static final String TAG = "PurseActivity";

	private TextView mTvProfileName;
	private TextView mTvProfileAccount;

	private LinearLayout llTabDeposit;
	private LinearLayout llTabPayment;
	private FrameLayout flUnpayContract;
	private TextView mTvToPayContractNum;

	private ListItemView mItemDepositBalance;
	private ListItemView mItemPaymentBalance;

	private ListItemView mItemRollOutToDeposit;

	private PurseType purseType = PurseType.DEPOSIT;

	private PurseInfoModel mPurseInfo;
	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse);
		initView();
		initData();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Logger.e(TAG, "onNewIntent");
		mPurseLogic.getPurseInfo();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_my_purse_info);

		mTvProfileName = getView(R.id.profile_name);
		mTvProfileAccount = getView(R.id.profile_account);
		llTabDeposit = getView(R.id.ll_tab_deposit);
		llTabPayment = getView(R.id.ll_tab_payment);
		flUnpayContract = getView(R.id.fl_unpay_contract);
		mTvToPayContractNum = getView(R.id.iv_unpay_contract_num);
		mItemRollOutToDeposit = getView(R.id.ll_roll_out_to_deposits);
		mItemDepositBalance = getView(R.id.ll_deposit_balance);
		mItemPaymentBalance = getView(R.id.ll_payment_balance);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_purse);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.rdoBtn_deposit).setOnClickListener(this);
		getView(R.id.rdoBtn_payment).setOnClickListener(this);
		getView(R.id.btn_unpay_contract).setOnClickListener(this);
		getView(R.id.ll_security_tips).setOnClickListener(this);
		getView(R.id.ll_my_profile).setOnClickListener(this);
		getView(R.id.ll_deposit_balance).setOnClickListener(this);
		getView(R.id.ll_payment_balance).setOnClickListener(this);
		getView(R.id.ll_deposit_rollout).setOnClickListener(this);
		getView(R.id.ll_payment_rollout).setOnClickListener(this);
		getView(R.id.ll_deposit_recharge).setOnClickListener(this);
		getView(R.id.ll_payment_recharge).setOnClickListener(this);
		getView(R.id.ll_deposit_details).setOnClickListener(this);
		getView(R.id.ll_payment_details).setOnClickListener(this);

		mItemRollOutToDeposit.setOnClickListener(this);
	}

	private void initData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getPurseInfo();
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mPurseLogic.getPurseInfo();
	}

	private void updateUI() {
		if (StringUtils.isNotEmpty(getCompanyName())) {
			mTvProfileName.setText(getCompanyName());
		} else {
			mTvProfileName.setVisibility(View.GONE);
		}
		mTvProfileAccount.setText(getUserAccount());

		if (mPurseInfo != null) {
			mItemDepositBalance.setContent(StringUtils.getCashNumber(String.valueOf(mPurseInfo.depositBalance)));
			mItemPaymentBalance.setContent(StringUtils.getCashNumber(String.valueOf(mPurseInfo.paymentBalance)));
			if (mPurseInfo.toPayCount == 0) {
				flUnpayContract.setVisibility(View.GONE);
			} else {
				flUnpayContract.setVisibility(View.VISIBLE);
				if (mPurseInfo.toPayCount <= 99) {
					mTvToPayContractNum.setText(String.valueOf(mPurseInfo.toPayCount));
				} else {
					mTvToPayContractNum.setText("99+");
				}
			}
		}
	}

	private void updateBalance() {
		mItemDepositBalance.setContent(StringUtils.getCashNumber(String.valueOf(GlobalConfig.getInstance().getDepositBalance())));
		mItemPaymentBalance.setContent(StringUtils.getCashNumber(String.valueOf(GlobalConfig.getInstance().getPaymentBalance())));
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_GET_PURSE_INFO_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_GET_PURSE_INFO_FAILED:
			onGetFailed(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO:
			mPurseLogic.getPurseInfo();
			break;
		case GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO:
			updateBalance();
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mPurseInfo = (PurseInfoModel) respInfo.data;
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case GlobalMessageType.PurseMessageType.MSG_GET_PURSE_INFO_FAILED:
				showToast(R.string.error_req_get_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_my_profile:
			if (getParent() instanceof MainActivity) {
				//((MainActivity) getParent()).switchTabView(TabStatus.MY_PROFILE);
				((MainActivity) getParent()).switchTabToMyProfile(true);
			}
			break;
		case R.id.rdoBtn_deposit:
			if (purseType != PurseType.DEPOSIT) {
				purseType = PurseType.DEPOSIT;
				switchView();
			}
			break;
		case R.id.rdoBtn_payment:
			if (purseType != PurseType.PAYMENT) {
				purseType = PurseType.PAYMENT;
				switchView();
			}
			break;
		case R.id.ll_deposit_balance:
			if (isDepositEmpty() || !isDepositEnough()) {
				intent = new Intent(this, PurseBalanceActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue());
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_INFO, mPurseInfo);
				startActivity(intent);
			} else {
				intent = new Intent(this, PurseDealListActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.DEPOSIT.toValue());
				startActivity(intent);
			}
			break;
		case R.id.ll_payment_balance:
			if (isPaymentEmpty()) {
				intent = new Intent(this, PurseBalanceActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.PAYMENT.toValue());
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_INFO, mPurseInfo);
				startActivity(intent);
			} else {
				intent = new Intent(this, PurseDealListActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.PAYMENT.toValue());
				startActivity(intent);
			}
			break;
		case R.id.btn_unpay_contract:
			intent = new Intent(this, ToPayContractListActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_security_tips:

			break;
		case R.id.ll_deposit_rollout:
			if (!isDepositEmpty()) {
				intent = new Intent(this, PayeeSelectActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue());
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, mPurseInfo.depositBalance);
				startActivity(intent);
			} else {
				showToast("余额不足，无法操作!");
			}
			break;
		case R.id.ll_payment_rollout:
			if (!isPaymentEmpty()) {
				intent = new Intent(this, PayeeSelectActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.PAYMENT.toValue());
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, mPurseInfo.paymentBalance);
				startActivity(intent);
			} else {
				showToast("余额不足，无法操作!");
			}
			break;
		case R.id.ll_deposit_recharge:
			intent = new Intent(this, PurseRechargeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue());
			startActivity(intent);
			break;
		case R.id.ll_payment_recharge:
			intent = new Intent(this, PurseRechargeActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.PAYMENT.toValue());
			startActivity(intent);
			break;
		case R.id.ll_roll_out_to_deposits:
			if (!isPaymentEmpty()) {
				intent = new Intent(this, PurseRollOutToDepositActivity.class);
				intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, mPurseInfo.paymentBalance);
				startActivity(intent);
			} else {
				showToast("余额不足，无法操作!");
			}
			break;
		case R.id.ll_deposit_details:
			intent = new Intent(this, PurseDealListActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.DEPOSIT.toValue());
			startActivity(intent);
			break;
		case R.id.ll_payment_details:
			intent = new Intent(this, PurseDealListActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_DEAL_TYPE, PurseType.PAYMENT.toValue());
			startActivity(intent);
			break;
		case R.id.iv_common_back:
			if (getParent() instanceof MainActivity) {
				((MainActivity) getParent()).switchTabView(TabStatus.SHOP);
			} else {
				finish();
			}
			break;
		}
	}

	private boolean isDepositEnough() {
		return mPurseInfo.isDepositEnough;
	}

	private boolean isDepositEmpty() {
		return mPurseInfo.depositBalance <= 0;
	}

	private boolean isPaymentEmpty() {
		return mPurseInfo.paymentBalance <= 0;
	}

	private boolean isAuthed() {
		return GlobalConfig.getInstance().isAuth();
	}

	private void switchView() {
		llTabDeposit.setVisibility(purseType == PurseType.DEPOSIT ? View.VISIBLE : View.GONE);
		llTabPayment.setVisibility(purseType == PurseType.PAYMENT ? View.VISIBLE : View.GONE);
		((TextView) getView(R.id.tv_security_tips)).setText(purseType == PurseType.DEPOSIT ? R.string.purse_deposit_security_tips : R.string.purse_payment_security_tips);
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}

}
