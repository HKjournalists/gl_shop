package com.glshop.net.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;
import com.igexin.sdk.PushManager;

/**
 * @Description : 登录页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class LoginActivity extends BasicActivity {

	private static final String TAG = "LoginActivity";

	private EditText mEtUserAccount;
	private EditText mEtUserPassword;
	private CheckedTextView chkTvRememberPwd;

	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.user_login);
		mEtUserAccount = (EditText) getView(R.id.et_user_account);
		mEtUserPassword = (EditText) getView(R.id.et_user_password);
		chkTvRememberPwd = (CheckedTextView) getView(R.id.chkTv_remember_pwd);

		getView(R.id.btn_user_login).setOnClickListener(this);
		getView(R.id.btn_user_register).setOnClickListener(this);
		getView(R.id.btn_user_find_pwd).setOnClickListener(this);
		getView(R.id.chkTv_remember_pwd).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		String defualtAccount = getUserAccount();
		if (StringUtils.isNotEmpty(defualtAccount)) {
			mEtUserAccount.setText(defualtAccount);
			mEtUserAccount.setSelection(mEtUserAccount.getText().toString().length());
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
			case UserMessageType.MSG_LOGIN_SUCCESS: // 登录成功
				onLoginSuccess();
				break;
			case UserMessageType.MSG_LOGIN_FAILED: // 登录失败
				onLoginFailed(respInfo);
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_user_login: // 登录
			doLogin();
			break;
		case R.id.btn_user_register: // 注册
			Intent regIntent = new Intent(this, UserRegActivity.class);
			startActivity(regIntent);
			break;
		case R.id.btn_user_find_pwd: // 忘记密码
			Intent findPwdIntent = new Intent(this, FindPwdActivity.class);
			String account = mEtUserAccount.getText().toString().trim();
			if (StringUtils.isPhoneNumber(account)) {
				findPwdIntent.putExtra(GlobalAction.UserAction.EXTRA_USER_ACCOUNT, account);
			}
			startActivity(findPwdIntent);
			break;
		case R.id.chkTv_remember_pwd: // 记住密码
			chkTvRememberPwd.toggle();
			break;
		case R.id.iv_common_back: // Back
			ActivityUtil.hideKeyboard(this);
			gotoMainPage(false);
			break;
		}
	}

	private void doLogin() {
		String account = mEtUserAccount.getText().toString().trim();
		String password = mEtUserPassword.getText().toString();
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
			showToast(R.string.user_login_info_error);
		} else if (!StringUtils.isPhoneNumber(account)) {
			showToast(R.string.phonenumber_format_error);
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			ActivityUtil.hideKeyboard(this);
			showSubmitDialog(getString(R.string.user_logining));
			mUserLogic.login(account, SystemUtil.getEncryptPassword(account, password), PushManager.getInstance().getClientid(this));
		}
	}

	private void onLoginSuccess() {
		closeSubmitDialog();
		//showToast("登录成功");

		PlatformConfig.setValue(GlobalConstants.SPKey.IS_REMEMBER_USER_PWD, chkTvRememberPwd.isChecked());

		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.MsgCenterMessageType.MSG_REFRESH_UNREAD_MSG_NUM);
		gotoMainPage(true);
	}

	private void onLoginFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (DataConstants.GlobalErrorCode.USER_NOT_LOGIN.equals(errorCode) || DataConstants.GlobalErrorCode.USER_TOKEN_EXPIRE.equals(errorCode)) {
					showToast(R.string.error_req_user_login);
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
			case UserMessageType.MSG_LOGIN_FAILED: // 登录失败
				showToast(R.string.error_req_user_login);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	/**
	 * 跳转主界面
	 */
	private void gotoMainPage(boolean isLogined) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle data = getIntent().getExtras();
		if (isLogined && data != null && data.getBoolean(GlobalAction.UserAction.EXTRA_GOTO_MYPROFILE)) {
			intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PROFILE);
		}
		startActivity(intent);
		finish();
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	public void onBackPressed() {
		gotoMainPage(false);
		super.onBackPressed();
	}

	@Override
	protected void initLogics() {
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
