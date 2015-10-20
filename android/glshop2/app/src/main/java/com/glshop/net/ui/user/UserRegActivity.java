package com.glshop.net.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ProtocolConstants;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.browser.BrowseProtocolActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 输入注册手机号页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class UserRegActivity extends BasicActivity {

	private static final String TAG = "UserRegActivity";

	private EditText mEtUserAccount;
	private CheckedTextView mCkbTvAgreeProtocol;
	private Button mBtnNextStep;
	private ConfirmDialog mConfirmDialog;

	private IUserLogic mUserLogic;

	private String mRegAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_reg_account);
		initView();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.input_user_phonenumber);
		mEtUserAccount = getView(R.id.et_user_account);
		mCkbTvAgreeProtocol = getView(R.id.chkTv_agree_protocol);
		mBtnNextStep = getView(R.id.btn_next_step);

		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.ll_agree_protocol).setOnClickListener(this);
		getView(R.id.tv_protocol_detail).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case UserMessageType.MSG_CHECK_REG_USER_SUCCESS:
			if (respInfo != null) {
				onCheckUserSuccess();
			}
			break;
		case UserMessageType.MSG_CHECK_REG_USER_FAILED:
			if (respInfo != null) {
				onCheckUserFailed(respInfo);
			}
			break;
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_SUCCESS:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetCodeSuccess();
			}
			break;
		case UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onGetCodeFailed(respInfo);
			}
			break;
		}
	}

	private void onCheckUserSuccess() {
		closeSubmitDialog();
		showConfirmDialog();
	}

	private void onCheckUserFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onGetCodeSuccess() {
		closeSubmitDialog();
		Intent intent = new Intent(this, UserRegSubmitActivity.class);
		intent.putExtra(GlobalAction.UserAction.EXTRA_REG_ACCOUNT, mRegAccount);
		startActivity(intent);
	}

	private void onGetCodeFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case UserMessageType.MSG_CHECK_REG_USER_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			case UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED:
				showToast(R.string.error_req_get_smscode);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_step:
			doNextAction();
			break;

		case R.id.ll_agree_protocol:
			mCkbTvAgreeProtocol.toggle();
			mBtnNextStep.setEnabled(mCkbTvAgreeProtocol.isChecked());
			break;

		case R.id.tv_protocol_detail:
			Intent intent = new Intent(this, BrowseProtocolActivity.class);
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, getString(R.string.user_service_protocol_url));
			intent.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, ProtocolConstants.USER_SERVICE_PROTOCOL_URL);
			startActivity(intent);
			break;

		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	private void doNextAction() {
		String account = mEtUserAccount.getText().toString().trim();
		if (StringUtils.isEmpty(account)) {
			showToast(R.string.phone_empty);
		} else if (!StringUtils.isPhoneNumber(account)) {
			showToast(R.string.phonenumber_format_error);
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			ActivityUtil.hideKeyboard(this);
			mRegAccount = account;
			checkRegUser();
		}
	}

	private void checkRegUser() {
		closeSubmitDialog();
		showSubmitDialog(getString(R.string.do_check_user_ing));
		mUserLogic.checkUser(mRegAccount);
	}

	private void showConfirmDialog() {
		closeDialog(mConfirmDialog);
		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.send_sms_verifycode) + mRegAccount);
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				getSmsVerfiyCode();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mConfirmDialog.show();
	}

	private void getSmsVerfiyCode() {
		closeSubmitDialog();
		showSubmitDialog(getString(R.string.do_get_sms_code_ing));
		mInvoker = String.valueOf(System.currentTimeMillis());
		mUserLogic.getSmsVerifyCode(mInvoker, mRegAccount, "REGISTER");
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
