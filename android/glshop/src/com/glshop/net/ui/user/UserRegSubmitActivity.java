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
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
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
 * @Description : 输入注册密码页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class UserRegSubmitActivity extends BasicActivity {

	private static final String TAG = "UserRegSubmitActivity";

	private EditText mEtUserPwd;
	private EditText mEtUserPwdConfirm;

	private EditText mEtSmsVerifyCode;
	private Button mBtnGetVerifyCode;

	private IUserLogic mUserLogic;

	private String mRegAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_reg_pwd);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.set_user_password);
		mEtUserPwd = getView(R.id.et_user_password);
		mEtUserPwdConfirm = getView(R.id.et_user_password_confirm);
		mEtSmsVerifyCode = getView(R.id.et_sms_verify_code);
		mBtnGetVerifyCode = getView(R.id.btn_get_sms_verfiycode);

		getView(R.id.btn_delete_new_pwd).setOnClickListener(this);
		getView(R.id.btn_delete_confirm_pwd).setOnClickListener(this);
		getView(R.id.btn_get_sms_verfiycode).setOnClickListener(this);
		getView(R.id.btn_submit_register).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mRegAccount = getIntent().getStringExtra(GlobalAction.UserAction.EXTRA_REG_ACCOUNT);
		((Button) getView(R.id.btn_user_reg_account)).setText(mRegAccount);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_SUCCESS:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetCodeSuccess(respInfo);
			}
			break;
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetCodeFailed(respInfo);
			}
			break;
		case UserMessageType.MSG_REG_USER_SUCCESS:
			onUserRegSuccess();
			break;
		case UserMessageType.MSG_REG_USER_FAILED:
			onUserRegFailed(respInfo);
			break;
		}
	}

	private void getSmsVerfiyCode() {
		mBtnGetVerifyCode.setEnabled(false);
		mBtnGetVerifyCode.setText(R.string.geting_verifycode_hint);
		mInvoker = String.valueOf(System.currentTimeMillis());
		mUserLogic.getSmsVerifyCode(mInvoker, mRegAccount);
	}

	private void onGetCodeSuccess(RespInfo respInfo) {
		startTimer();
	}

	private void onGetCodeFailed(RespInfo respInfo) {
		handleErrorAction(respInfo);

		// 恢复获取按钮状态
		mBtnGetVerifyCode.setEnabled(true);
		mBtnGetVerifyCode.setText(getString(R.string.get_sms_verifycode));
	}

	private void onUserRegSuccess() {
		closeSubmitDialog();
		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.register_success);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_reg_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_reg_success_content);

		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_reg_success_action_text1);
		tipInfo.operatorTipActionClass1 = /*ProfileAuthActivity.class*/LoginActivity.class;

		tipInfo.operatorTipActionText2 = getString(R.string.operator_tips_reg_success_action_text2);
		tipInfo.operatorTipActionClass2 = MainActivity.class;
		tipInfo.operatorTipAction2 = GlobalAction.TipsAction.ACTION_VIEW_SHOP;

		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		Bundle data = new Bundle();
		data.putBoolean(GlobalAction.UserAction.EXTRA_GOTO_MYPROFILE, true);
		intent.putExtras(data);

		startActivity(intent);
		finish();
		cleanStack();
	}

	private void onUserRegFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit_register: // 提交注册
			doRegister();
			break;
		case R.id.btn_get_sms_verfiycode: // 重新获取短信验证码
			if (!isNetConnected()) {
				showToast(R.string.network_disconnected);
			} else {
				getSmsVerfiyCode();
			}
			break;
		case R.id.btn_delete_new_pwd: // 删除新密码
			final String newPwd = mEtUserPwd.getText().toString();
			if (newPwd.length() > 0) {
				mEtUserPwd.requestFocus();
				mEtUserPwd.setText(newPwd.substring(0, newPwd.length() - 1));
			}
			break;
		case R.id.btn_delete_confirm_pwd: // 删除确认密码
			final String newConfirmPwd = mEtUserPwdConfirm.getText().toString();
			if (newConfirmPwd.length() > 0) {
				mEtUserPwdConfirm.requestFocus();
				mEtUserPwdConfirm.setText(newConfirmPwd.substring(0, newConfirmPwd.length() - 1));
			}
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
		}
	}

	private void doRegister() {
		String verifyCode = mEtSmsVerifyCode.getText().toString().trim();
		String newPwd = mEtUserPwd.getText().toString().trim();
		String newConfirmPwd = mEtUserPwdConfirm.getText().toString().trim();
		if (StringUtils.isEmpty(verifyCode)) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(verifyCode)) {
			showToast(R.string.sms_verify_code_format_error);
		} else if (StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(newConfirmPwd)) {
			showToast(R.string.password_empty);
		} else if (!newPwd.equals(newConfirmPwd)) {
			showToast(R.string.password_not_equal);
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			ActivityUtil.hideKeyboard(this);
			stopTimer(); // 清除倒计时状态
			showSubmitDialog();
			mUserLogic.registerUser(mRegAccount, SystemUtil.getEncryptPassword(mRegAccount, newPwd), verifyCode);
		}
	}

	Handler mTimeHander = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonMessageType.MSG_TIMER_START:
				mBtnGetVerifyCode.setEnabled(false);
				mBtnGetVerifyCode.setText(CountdownTimerMgr.WAIT_NEXT_TIME + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_PROGRESS:
				mBtnGetVerifyCode.setText(String.valueOf(msg.obj) + getString(R.string.wait_get_verifycode_hint));
				break;
			case CommonMessageType.MSG_TIMER_STOP:
				mBtnGetVerifyCode.setEnabled(true);
				mBtnGetVerifyCode.setText(getString(R.string.get_sms_verifycode));
				break;
			}
		}

	};

	private void startTimer() {
		CountdownTimerMgr.getInstance().startTimer(CountdownTimerMgr.WAIT_NEXT_TIME * CountdownTimerMgr.TIME_UNIT, new ITimerCallback() {

			@Override
			public void onStart() {
				//Logger.i(TAG, "onStart()");
				mTimeHander.sendEmptyMessage(CommonMessageType.MSG_TIMER_START);
			}

			@Override
			public void onProgress(int progress) {
				//Logger.i(TAG, "onProgress() & progress = " + progress);
				Message.obtain(mTimeHander, CommonMessageType.MSG_TIMER_PROGRESS, progress).sendToTarget();
			}

			@Override
			public void onEnd() {
				//Logger.i(TAG, "onEnd()");
				mTimeHander.sendEmptyMessage(CommonMessageType.MSG_TIMER_STOP);
			}
		});
	}

	private void stopTimer() {
		CountdownTimerMgr.getInstance().stopTimer();
		mBtnGetVerifyCode.setEnabled(true);
		mBtnGetVerifyCode.setText(getString(R.string.get_sms_verifycode));
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
