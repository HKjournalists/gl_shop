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
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr.ITimerCallback;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.InputDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.api.purse.data.model.RolloutInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 转出提交界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PurseRollOutSubmitActivity extends BasicActivity {

	private PurseType purseType;
	private PayeeInfoModel payeeInfo;

	private TextView mTvBankName;
	private TextView mTvBankAccount;
	private TextView mTvBankCard;

	private TextView mTvAviableMoney;
	private TextView mTvUserPhone;
	private EditText mEtRolloutMoney;
	private EditText mEtSmsCode;

	private Button mBtnSendSmsVerfyCode;

	private String password;

	private float aviableMoney;

	private InputDialog mInputPwdDialog;

	private IPurseLogic mPurseLogic;
	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_roll_out_submit);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_roll_out);

		mTvBankName = getView(R.id.tv_bank_name);
		mTvBankAccount = getView(R.id.tv_bank_account);
		mTvBankCard = getView(R.id.tv_bank_card);
		mTvAviableMoney = getView(R.id.tv_aviable_money);
		mEtRolloutMoney = getView(R.id.et_roll_money);
		mTvUserPhone = getView(R.id.tv_user_phone);
		mEtSmsCode = getView(R.id.et_sms_code);
		mBtnSendSmsVerfyCode = getView(R.id.btn_send_sms_verfycode);

		getView(R.id.btn_submit).setOnClickListener(this);
		getView(R.id.btn_send_sms_verfycode).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		payeeInfo = (PayeeInfoModel) getIntent().getSerializableExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO);
		aviableMoney = getIntent().getFloatExtra(GlobalAction.PurseAction.EXTRA_KEY_ROLLOUT_MONEY, 0);
		if (aviableMoney > 0 && payeeInfo != null) {
			mTvBankName.setText(payeeInfo.bankName);
			mTvBankAccount.setText(payeeInfo.name);
			mTvBankCard.setText(payeeInfo.card);
			mTvAviableMoney.setText(StringUtils.getCashNumber(String.valueOf(aviableMoney)));
			mTvUserPhone.setText(getUserPhone());
		} else {
			showToast("提现信息不能为空!");
			finish();
		}
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
		case GlobalMessageType.PurseMessageType.MSG_ROLL_OUT_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_ROLL_OUT_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();

		tipInfo.operatorTipTitle = getString(R.string.roll_out_title);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_roll_out_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mypurse);

		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send_sms_verfycode:
			// 获取验证码
			if (!isNetConnected()) {
				showToast(R.string.network_disconnected);
			} else {
				getSmsVerfiyCode();
			}
			break;
		case R.id.btn_submit:
			doSubmitAction();
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(PurseRollOutSubmitActivity.this);
			finish();
			break;
		}
	}

	private void doSubmitAction() {
		if (checkArgs()) {
			showInputPwdDialog();
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isEmpty(mEtRolloutMoney.getText().toString().trim())) {
			showToast("转出金额不能为空!");
		} else if (Float.parseFloat(mEtRolloutMoney.getText().toString().trim()) <= 0) {
			showToast("转出金额必须大于0!");
		} else if (Float.parseFloat(mEtRolloutMoney.getText().toString().trim()) > aviableMoney) {
			showToast("转出金额不能大于可用余额!");
		} else if (StringUtils.isEmpty(mEtSmsCode.getText().toString().trim())) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(mEtSmsCode.getText().toString().trim())) {
			showToast(R.string.sms_verify_code_format_error);
		} else {
			return true;
		}
		return false;
	}

	private void showInputPwdDialog() {
		if (mInputPwdDialog != null && mInputPwdDialog.isShowing()) {
			mInputPwdDialog.dismiss();
		}

		mInputPwdDialog = new InputDialog(this, R.style.dialog);
		mInputPwdDialog.setTitle(getString(R.string.input_password_hint));
		mInputPwdDialog.setPasswordEnable(true);
		mInputPwdDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				if (StringUtils.isEmpty((String) obj)) {
					showToast(getString(R.string.password_empty));
				} else {
					password = (String) obj;
					showSubmitDialog();
					RolloutInfoModel info = new RolloutInfoModel();
					info.type = purseType;
					info.payeeId = payeeInfo.id;
					info.money = mEtRolloutMoney.getText().toString().trim();
					password = SystemUtil.getEncryptPassword(getUserPhone(), password);
					mPurseLogic.rollOut(info, mEtSmsCode.getText().toString().trim(), password);
				}
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mInputPwdDialog.show();
	}

	private void getSmsVerfiyCode() {
		mInvoker = String.valueOf(System.currentTimeMillis());
		mUserLogic.getSmsVerifyCode(mInvoker, getUserPhone());
		mBtnSendSmsVerfyCode.setEnabled(false);
		mBtnSendSmsVerfyCode.setText(R.string.geting_verifycode_hint);
	}

	private void onGetSmsVerifyCodeSuccess() {
		startTimer();
	}

	private void onGetSmsVerifyCodeFailed(RespInfo respInfo) {
		handleErrorAction(respInfo);

		// 恢复获取按钮状态
		mBtnSendSmsVerfyCode.setEnabled(true);
		mBtnSendSmsVerfyCode.setText(getString(R.string.get_sms_verifycode));
	}

	Handler mTimeHander = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonMessageType.MSG_TIMER_START:
				mBtnSendSmsVerfyCode.setEnabled(false);
				mBtnSendSmsVerfyCode.setText(CountdownTimerMgr.WAIT_NEXT_TIME + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_PROGRESS:
				mBtnSendSmsVerfyCode.setText(String.valueOf(msg.obj) + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_STOP:
				mBtnSendSmsVerfyCode.setEnabled(true);
				mBtnSendSmsVerfyCode.setText(getString(R.string.get_sms_verifycode));
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
		mBtnSendSmsVerfyCode.setEnabled(true);
		mBtnSendSmsVerfyCode.setText(getString(R.string.get_sms_verifycode));
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
