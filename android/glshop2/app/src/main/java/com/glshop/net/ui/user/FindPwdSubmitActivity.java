package com.glshop.net.ui.user;

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
import com.glshop.net.common.GlobalConstants.TipActionBackType;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr.ITimerCallback;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 找回密码、修改密码提交页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class FindPwdSubmitActivity extends BasicActivity {

	private static final String TAG = "FindPwdSubmitActivity";

	private TextView mTvTitle;
	private TextView mTvAccount;
	private EditText mEtSmsVerifyCode;
	private EditText mEtOldPwd;
	private EditText mEtNewPwd;
	private EditText mEtConfirmNewPwd;
	private Button mBtnGetSmsVerifyCode;
	private Button mBtnSumbit;

	private IUserLogic mUserLogic;

	private boolean isModifyPwd;

	private String mAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pwd_submit);
		initView();
		initData();
	}

	private void initView() {
		mTvTitle = getView(R.id.tv_commmon_title);
		mTvAccount = getView(R.id.tv_account);
		mEtSmsVerifyCode = getView(R.id.et_sms_verify_code);
		mEtOldPwd = getView(R.id.et_old_password);
		mEtNewPwd = getView(R.id.et_new_password);
		mEtConfirmNewPwd = getView(R.id.et_confirm_new_password);
		mBtnGetSmsVerifyCode = getView(R.id.btn_get_sms_verfiycode);
		mBtnSumbit = getView(R.id.btn_submit);

		mBtnGetSmsVerifyCode.setOnClickListener(this);
		mBtnSumbit.setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		isModifyPwd = getIntent().getBooleanExtra(GlobalAction.UserAction.EXTRA_IS_MODIFY_PWD, false);
		mAccount = getIntent().getStringExtra(GlobalAction.UserAction.EXTRA_USER_ACCOUNT);
		mTvAccount.setText(mAccount);
		if (isModifyPwd) {
			mTvTitle.setText(R.string.user_modify_password);
			//mEtOldPwd.setVisibility(View.VISIBLE);
			getView(R.id.ll_old_password).setVisibility(View.VISIBLE);
		} else {
			mTvTitle.setText(R.string.user_find_password);
			//mEtOldPwd.setVisibility(View.GONE);
			getView(R.id.ll_old_password).setVisibility(View.GONE);
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
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
		case UserMessageType.MSG_RESET_PASSWORD_SUCCESS:
		case UserMessageType.MSG_MODIFY_PASSWORD_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case UserMessageType.MSG_RESET_PASSWORD_FAILED:
		case UserMessageType.MSG_MODIFY_PASSWORD_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			// 提交
			if (isModifyPwd) {
				submitModPwdReq();
			} else {
				submitFindPwdReq();
			}
			break;
		case R.id.btn_get_sms_verfiycode:
			// 重新获取验证码
			if (!isNetConnected()) {
				showToast(R.string.network_disconnected);
			} else {
				getSmsVerfiyCode();
			}
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	private void submitFindPwdReq() {
		String verifyCode = mEtSmsVerifyCode.getText().toString().trim();
		String newPwd = mEtNewPwd.getText().toString();
		//String newConfirmPwd = mEtConfirmNewPwd.getText().toString();

		if (StringUtils.isEmpty(verifyCode)) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(verifyCode)) {
			showToast(R.string.sms_verify_code_format_error);
		} else if (!checkPassword()) {
			// TODO
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			ActivityUtil.hideKeyboard(this);
			stopTimer(); // 清除倒计时状态
			showSubmitDialog();

			mUserLogic.resetPassword(mAccount, SystemUtil.getEncryptPassword(mAccount, newPwd), verifyCode);
		}
	}

	private void submitModPwdReq() {
		String verifyCode = mEtSmsVerifyCode.getText().toString().trim();
		String oldPwd = mEtOldPwd.getText().toString();
		String newPwd = mEtNewPwd.getText().toString();
		String newConfirmPwd = mEtConfirmNewPwd.getText().toString();

		if (StringUtils.isEmpty(verifyCode)) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(verifyCode)) {
			showToast(R.string.sms_verify_code_format_error);
		} else if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(newConfirmPwd)) {
			showToast(R.string.password_empty);
		} else if (!checkPassword()) {
			// TODO
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			ActivityUtil.hideKeyboard(this);
			stopTimer(); // 清除倒计时状态
			showSubmitDialog();
			oldPwd = SystemUtil.getEncryptPassword(mAccount, oldPwd);
			newPwd = SystemUtil.getEncryptPassword(mAccount, newPwd);
			mUserLogic.modifyPassword(mAccount, oldPwd, newPwd, verifyCode);
		}
	}

	private boolean checkPassword() {
		String newPwd = mEtNewPwd.getText().toString();
		String newConfirmPwd = mEtConfirmNewPwd.getText().toString();
		if (StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(newConfirmPwd)) {
			showToast(R.string.password_empty);
			return false;
		} else if (!newPwd.equals(newConfirmPwd)) {
			showToast(R.string.password_not_equal);
			return false;
		} else if (newPwd.length() < 6 || newPwd.length() > 12) {
			showToast(R.string.password_length_invalid);
			return false;
		}
		return true;
	}

	private void getSmsVerfiyCode() {
		mInvoker = String.valueOf(System.currentTimeMillis());
		mUserLogic.getSmsVerifyCode(mInvoker, mAccount);
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

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		if (isModifyPwd) {
			tipInfo.operatorTipTitle = getString(R.string.modify_pwd_success);
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_mod_pwd_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_mod_pwd_success_content);
			tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_mod_pwd_success_action_text);
		} else {
			tipInfo.operatorTipTitle = getString(R.string.reset_pwd_success);
			tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_find_pwd_success_title);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_reg_success_content);
			tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_mod_pwd_success_action_text);
		}
		tipInfo.operatorTipActionClass1 = LoginActivity.class;
		//tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_USER_LOGIN;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
		cleanStack();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_10007))) {
					showToast(R.string.error_code_10007);
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
			case UserMessageType.MSG_MODIFY_PASSWORD_SUCCESS:
			case UserMessageType.MSG_RESET_PASSWORD_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
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
	protected boolean needLogin() {
		return false;
	}

	@Override
	protected void initLogics() {
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
