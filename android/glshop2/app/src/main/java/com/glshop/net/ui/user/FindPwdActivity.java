package com.glshop.net.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SDCardUtils;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 找回密码、修改密码主页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class FindPwdActivity extends BasicActivity {

	private TextView mTvTitle;
	private EditText mEtUserAccount;
	private EditText mEtImgVerifyCode;
	private TextView mTvImgVerifyCode; // 获取验证码提示
	private ImageView mIvVerifyCodeError; // 获取验证码失败
	private ImageView mIvImgVerifyCode; // 验证码显示
	private Button mBtnGetImgVerifyCode;

	private View llImgVerifyCode;

	private IUserLogic mUserLogic;

	/** 判断是否是修改密码界面 */
	private boolean isModifyPwd;

	private String mAccount;

	/** 获取图形验证码请求标识 */
	private String mImgCodeInvoker = String.valueOf(System.currentTimeMillis());

	/** 获取短信验证码请求标识 */
	private String mSmsCodeInvoker = String.valueOf(System.currentTimeMillis());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pwd);
		initView();
		initData();
	}

	private void initView() {
		mTvTitle = getView(R.id.tv_commmon_title);
		mEtUserAccount = getView(R.id.et_user_account);
		mEtImgVerifyCode = getView(R.id.et_img_verify_code);
		mTvImgVerifyCode = getView(R.id.tv_get_verfiycode_status);
		mIvVerifyCodeError = getView(R.id.iv_get_verfiycode_error);
		mIvImgVerifyCode = getView(R.id.iv_img_verfiycode);
		mBtnGetImgVerifyCode = getView(R.id.btn_get_img_verfiycode);
		llImgVerifyCode = getView(R.id.ll_img_verfiycode);

		getView(R.id.btn_get_img_verfiycode).setOnClickListener(this);
		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		String tips = getString(R.string.find_pwd_security_tips, GlobalConstants.CfgConstants.PLATFORM_CUSTOM_SERVICE_PHONE);
		String highlightTips = GlobalConstants.CfgConstants.PLATFORM_CUSTOM_SERVICE_PHONE;
		((TextView) getView(R.id.tv_find_pwd_tips)).setText(StringUtils.getHighlightText(tips, highlightTips, getResources().getColor(R.color.red)));
	}

	private void initData() {
		isModifyPwd = getIntent().getBooleanExtra(GlobalAction.UserAction.EXTRA_IS_MODIFY_PWD, false);
		String account = getIntent().getStringExtra(GlobalAction.UserAction.EXTRA_USER_ACCOUNT);
		if (StringUtils.isNotEmpty(account)) {
			mEtUserAccount.setText(account);
			requestSelection(mEtUserAccount);
		}

		if (isModifyPwd) {
			mTvTitle.setText(R.string.user_modify_password);
		} else {
			mTvTitle.setText(R.string.user_find_password);
		}
		getImgVerifyCode();
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case UserMessageType.MSG_VALID_IMG_VERFIYCODE_SUCCESS:
			onCheckImgVerifyCodeSuccess();
			break;
		case UserMessageType.MSG_VALID_IMG_VERFIYCODE_FAILED:
			onCheckImgVerifyCodeFailed(respInfo);
			break;
		case UserMessageType.MSG_GET_IMG_VERFIYCODE_SUCCESS:
			if (respInfo != null && isCurRequest(mImgCodeInvoker, String.valueOf(respInfo.invoker))) {
				onGetImgVerifyCodeSuccess(respInfo);
			}
			break;
		case UserMessageType.MSG_GET_IMG_VERFIYCODE_FAILED:
			if (respInfo != null && isCurRequest(mImgCodeInvoker, String.valueOf(respInfo.invoker))) {
				onGetImgVerifyCodeFailed(respInfo);
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_step: // 下一步
			doNextAction();
			break;
		case R.id.btn_get_img_verfiycode: // 重新获取验证码
			getImgVerifyCode();
			break;
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	private void doNextAction() {
		String account = mEtUserAccount.getText().toString().trim();
		String imgVerifyCode = mEtImgVerifyCode.getText().toString();
		if (StringUtils.isEmpty(account)) {
			showToast(R.string.phone_empty);
		} else if (StringUtils.isEmpty(imgVerifyCode)) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isImgVerifyCode(imgVerifyCode)) {
			showToast(R.string.sms_verify_code_format_error);
		} else if (!StringUtils.isPhoneNumber(account)) {
			showToast(R.string.phonenumber_format_error);
		} else if (!isNetConnected()) {
			showToast(R.string.network_disconnected);
		} else {
			mAccount = account;
			ActivityUtil.hideKeyboard(this);
			showSubmitDialog(getString(R.string.do_request_ing));
			//mSmsCodeInvoker = String.valueOf(System.currentTimeMillis());
			//mUserLogic.getSmsVerifyCode(mSmsCodeInvoker, ActivityUtil.getDeviceId(this), account, imgVerifyCode);
			mUserLogic.validImgVerfiyCode(account, ActivityUtil.getDeviceId(this), imgVerifyCode);
		}
	}

	private void getImgVerifyCode() {
		mImgCodeInvoker = String.valueOf(System.currentTimeMillis());
		String file = SDCardUtils.getAvailableSdcard() + "/gl_shop/temp/img_code.jpg";
		mUserLogic.getImgVerifyCode(mImgCodeInvoker, file, ActivityUtil.getDeviceId(this));
		mTvImgVerifyCode.setText(R.string.geting_img_verifycode_hint);
		mBtnGetImgVerifyCode.setEnabled(false);

		llImgVerifyCode.setVisibility(View.VISIBLE);
		mIvVerifyCodeError.setVisibility(View.GONE);
		mIvImgVerifyCode.setVisibility(View.GONE);
	}

	private void onCheckImgVerifyCodeSuccess() {
		closeSubmitDialog();
		Intent intent = new Intent(this, FindPwdSubmitActivity.class);
		intent.putExtra(GlobalAction.UserAction.EXTRA_USER_ACCOUNT, mAccount);
		intent.putExtra(GlobalAction.UserAction.EXTRA_IS_MODIFY_PWD, isModifyPwd);
		startActivity(intent);
	}

	private void onCheckImgVerifyCodeFailed(RespInfo respInfo) {
		closeSubmitDialog();
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_10008))) {
					showToast(R.string.error_code_10008);
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
	}

	private void onGetImgVerifyCodeSuccess(RespInfo info) {
		mIvImgVerifyCode.setImageBitmap((Bitmap) info.data);

		llImgVerifyCode.setVisibility(View.GONE);
		mIvVerifyCodeError.setVisibility(View.GONE);
		mIvImgVerifyCode.setVisibility(View.VISIBLE);

		mBtnGetImgVerifyCode.setEnabled(true);
	}

	private void onGetImgVerifyCodeFailed(RespInfo info) {
		mIvVerifyCodeError.setVisibility(View.VISIBLE);
		mTvImgVerifyCode.setText(R.string.get_verifycode_error);
		mBtnGetImgVerifyCode.setEnabled(true);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case UserMessageType.MSG_VALID_IMG_VERFIYCODE_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
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
