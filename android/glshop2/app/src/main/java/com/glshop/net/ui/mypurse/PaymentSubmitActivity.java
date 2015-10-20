package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr.ITimerCallback;
import com.glshop.net.ui.mycontract.BaseContractInfoActivity;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.mycontract.PaymentPayWarningActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.NumberUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 货款账户支付提交界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PaymentSubmitActivity extends BaseContractInfoActivity {

	private TextView mTvToPayMoney;
	private TextView mTvAviableMoney;
	private TextView mTvPhone;
	private EditText mEtSmsCode;
	private EditText mEtPassword;
	private Button mBtnGetSmsVerifyCode;

	private double needPayMoney;

	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_payment_submit);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_payment);

		mTvToPayMoney = getView(R.id.tv_to_pay_money);
		mTvAviableMoney = getView(R.id.tv_aviable_money);
		mTvPhone = getView(R.id.tv_phone_number);
		mEtSmsCode = getView(R.id.et_sms_verify_code);
		mEtPassword = getView(R.id.et_input_pwd);
		mBtnGetSmsVerifyCode = getView(R.id.btn_get_sms_verfiycode);

		getView(R.id.btn_get_sms_verfiycode).setOnClickListener(this);
		getView(R.id.btn_submit).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mTvPhone.setText(getUserPhone());
		needPayMoney = getIntent().getDoubleExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, 0);
		mTvToPayMoney.setText(StringUtils.getCashNumber(needPayMoney));
		mTvAviableMoney.setText(StringUtils.getCashNumber(GlobalConfig.getInstance().getPaymentBalance()));
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_SUCCESS:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetSmsVerifyCodeSuccess();
			}
			break;
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetSmsVerifyCodeFailed(respInfo);
			}
			break;
		case ContractMessageType.MSG_CONTRACT_PAY_ONLINE_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_PAY_ONLINE_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_sms_verfiycode:
			// 获取验证码
			if (!isNetConnected()) {
				showToast(R.string.network_disconnected);
			} else {
				getSmsVerfiyCode();
			}
			break;
		case R.id.btn_submit:
			submitPayment();
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	private void getSmsVerfiyCode() {
		mInvoker = String.valueOf(System.currentTimeMillis());
		mUserLogic.getSmsVerifyCode(mInvoker, getUserPhone());
		mBtnGetSmsVerifyCode.setEnabled(false);
		mBtnGetSmsVerifyCode.setText(R.string.geting_verifycode_hint);
	}

	private void onGetSmsVerifyCodeSuccess() {
		startTimer();
	}

	private void onGetSmsVerifyCodeFailed(RespInfo respInfo) {
		handleErrorAction(respInfo);

		// 恢复获取按钮状态
		mBtnGetSmsVerifyCode.setEnabled(true);
		mBtnGetSmsVerifyCode.setText(getString(R.string.get_sms_verifycode));
	}

	private void submitPayment() {
		if (checkArgs()) {
			ActivityUtil.hideKeyboard(this);
			stopTimer(); // 清除倒计时状态
			showSubmitDialog();
			String pwd = SystemUtil.getEncryptPassword(getUserAccount(), mEtPassword.getText().toString());
			mContractLogic.payContractOnline(mContractId, mEtSmsCode.getText().toString(), pwd);
		}
	}

	private boolean checkArgs() {
		String smsCode = mEtSmsCode.getText().toString().trim();
		String password = mEtPassword.getText().toString().trim();
		if (StringUtils.isEmpty(smsCode)) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(mEtSmsCode.getText().toString().trim())) {
			showToast(R.string.sms_verify_code_format_error);
		} else if (StringUtils.isEmpty(password)) {
			showToast(R.string.password_empty);
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			return true;
		}
		return false;
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING);
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO);

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.pay_success_title);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_pay_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_pay_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);

		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100005014))) {
					showToast(R.string.error_code_100005014);
					gotoRecharge();
				} else if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_10005))) {
					showToast(R.string.error_code_10005_2);
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED:
				showToast(R.string.error_req_get_smscode);
				break;
			case ContractMessageType.MSG_CONTRACT_PAY_ONLINE_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	private void gotoRecharge() {
		Intent intent = new Intent(this, PaymentPayWarningActivity.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		double needToRecharge = GlobalConfig.getInstance().getPaymentBalance() - mContractInfo.tradeAmount * mContractInfo.unitPrice;
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, NumberUtils.round(Math.abs(needToRecharge), 2));
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.PAYMENT.toValue());
		startActivity(intent);
		finish();
	}

	Handler mTimeHander = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonMessageType.MSG_TIMER_START:
				mBtnGetSmsVerifyCode.setEnabled(false);
				mBtnGetSmsVerifyCode.setText(CountdownTimerMgr.WAIT_NEXT_TIME + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_PROGRESS:
				mBtnGetSmsVerifyCode.setText(String.valueOf(msg.obj) + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_STOP:
				mBtnGetSmsVerifyCode.setEnabled(true);
				mBtnGetSmsVerifyCode.setText(getString(R.string.get_sms_verifycode));
				break;
			}
		}

	};

	private void startTimer() {
		CountdownTimerMgr.getInstance().startTimer(CountdownTimerMgr.WAIT_NEXT_TIME * CountdownTimerMgr.TIME_UNIT, new ITimerCallback() {

			@Override
			public void onStart() {
				mTimeHander.sendEmptyMessage(CommonMessageType.MSG_TIMER_START);
			}

			@Override
			public void onProgress(int progress) {
				Message.obtain(mTimeHander, CommonMessageType.MSG_TIMER_PROGRESS, progress).sendToTarget();
			}

			@Override
			public void onEnd() {
				mTimeHander.sendEmptyMessage(CommonMessageType.MSG_TIMER_STOP);
			}
		});
	}

	private void stopTimer() {
		CountdownTimerMgr.getInstance().stopTimer();
		mBtnGetSmsVerifyCode.setEnabled(true);
		mBtnGetSmsVerifyCode.setText(getString(R.string.get_sms_verifycode));
	}

	@Override
	protected void initLogics() {
		super.initLogics();
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
