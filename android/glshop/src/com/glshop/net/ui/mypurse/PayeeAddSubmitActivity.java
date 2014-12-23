package com.glshop.net.ui.mypurse;

import java.util.List;

import android.app.Dialog;
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
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.CommonMessageType;
import com.glshop.net.common.GlobalMessageType.PurseMessageType;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.logic.transfer.ITransferLogic;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr;
import com.glshop.net.ui.basic.timer.CountdownTimerMgr.ITimerCallback;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.net.utils.SystemUtil;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 添加收款提交界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PayeeAddSubmitActivity extends BasicActivity implements IMenuCallback {

	private PayeeInfoModel info;

	private MenuDialog menuBankList;

	private TextView mTvBank;
	private EditText mEtSubBankName;
	private EditText mEtBankCard;
	private EditText mEtSmsCode;
	private TextView mTvUserPhone;

	private Button mBtnGetSmsVerifyCode;

	private String bankCode;

	/** 请求标识 */
	private String mInvoker = String.valueOf(System.currentTimeMillis());

	private FileInfo mFileInfo;

	private IPurseLogic mPurseLogic;
	private ITransferLogic mTransferLogic;
	private IUserLogic mUserLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_payee_add_submit);
		initView();
		initData();
	}

	private void initView() {
		mTvBank = getView(R.id.tv_bank);
		mEtSubBankName = getView(R.id.et_sub_bank_name);
		mEtBankCard = getView(R.id.et_bank_card);
		mTvUserPhone = getView(R.id.tv_user_phone);
		mEtSmsCode = getView(R.id.et_sms_code);
		mBtnGetSmsVerifyCode = getView(R.id.btn_send_sms_verfycode);

		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.supplement_payee);
		getView(R.id.ll_select_bank).setOnClickListener(this);
		getView(R.id.btn_submit).setOnClickListener(this);
		getView(R.id.btn_send_sms_verfycode).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		info = (PayeeInfoModel) getIntent().getSerializableExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO);
		mTvUserPhone.setText(getUserPhone());
		if (info != null) {
			if (StringUtils.isNotEmpty(info.id)) {
				((TextView) getView(R.id.tv_commmon_title)).setText(R.string.modify_payee);
			}
			bankCode = info.bankCode;
			MenuItemInfo defaultItem = getDefaultMenu();
			if (StringUtils.isEmpty(bankCode)) {
				bankCode = defaultItem.menuCode;
			}
			mTvBank.setText(defaultItem.menuText);
			mEtSubBankName.setText(info.subBank);
			mEtSubBankName.setSelection(mEtSubBankName.getText().toString().length());
			mEtBankCard.setText(info.card);
		} else {
			finish();
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
		case CommonMessageType.MSG_FILE_UPLOAD_SUCCESS: // 图片上传成功
			onUploadSuccess(respInfo);
			break;
		case CommonMessageType.MSG_FILE_UPLOAD_FAILED: // 图片上传失败
			onUploadFailed(respInfo);
			break;
		case PurseMessageType.MSG_ADD_PAYEE_INFO_SUCCESS: // 新增成功
			onSubmitSuccess(respInfo);
			break;
		case PurseMessageType.MSG_ADD_PAYEE_INFO_FAILED: // 新增失败
			onSubmitFailed(respInfo);
			break;
		case PurseMessageType.MSG_UPDATE_PAYEE_INFO_SUCCESS: // 更新成功
			onSubmitSuccess(respInfo);
			break;
		case PurseMessageType.MSG_UPDATE_PAYEE_INFO_FAILED: // 更新失败
			onSubmitFailed(respInfo);
			break;
		}
	}

	private void onUploadSuccess(RespInfo respInfo) {
		String invoker = String.valueOf(respInfo.invoker);
		if (mInvoker.equals(invoker)) {
			savePayeeInfo(mFileInfo.cloudId);
		}
	}

	private void onUploadFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();

		tipInfo.operatorTipTitle = getString(R.string.add_payee);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_submit_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_add_payee_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mypurse);

		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void doSubmitAction() {
		if (checkArgs()) {
			if (needToUploadImage()) {
				// 有图片更新，则先上传图片
				uploadImage();
			} else {
				// 无图片更新，则直接提交
				savePayeeInfo(info.certImgInfo.cloudId);
			}
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isEmpty(mEtBankCard.getText().toString().trim())) {
			showToast("银行卡号不能为空!");
		} else if (StringUtils.isEmpty(mEtSmsCode.getText().toString().trim())) {
			showToast(R.string.sms_verify_code_empty);
		} else if (!SystemUtil.isSmsVerifyCode(mEtSmsCode.getText().toString().trim())) {
			showToast(R.string.sms_verify_code_format_error);
		} else {
			return true;
		}
		return false;
	}

	private boolean needToUploadImage() {
		return info.certImgInfo.localFile != null;
	}

	/**
	 * 上传图片
	 */
	private void uploadImage() {
		mFileInfo = new FileInfo();
		mFileInfo.file = info.certImgInfo.localFile;
		mInvoker = String.valueOf(System.currentTimeMillis());
		mTransferLogic.uploadFile(mInvoker, mFileInfo);
		showSubmitDialog();
	}

	private void savePayeeInfo(String id) {
		ActivityUtil.hideKeyboard(this);
		stopTimer(); // 清除倒计时状态

		info.bankCode = bankCode;
		info.subBank = mEtSubBankName.getText().toString().trim();
		info.card = mEtBankCard.getText().toString().trim();
		if (info.certImgInfo != null) {
			info.certImgInfo.cloudId = id;
		}

		if (!needToUploadImage()) {
			showSubmitDialog();
		}

		if (StringUtils.isNotEmpty(info.id)) {
			mPurseLogic.updatePayeeInfo(info, mEtSmsCode.getText().toString().trim());
		} else {
			mPurseLogic.addPayeeInfo(info, mEtSmsCode.getText().toString().trim());
		}
	}

	private void showBankMenu() {
		closeMenuDialog(menuBankList);
		List<MenuItemInfo> menu = SysCfgUtils.getBankMenu(this);
		menuBankList = new MenuDialog(this, menu, this, true, getDefaultMenu());
		menuBankList.setMenuType(GlobalMessageType.MenuType.SELECT_BANK);
		menuBankList.setTitle(getString(R.string.menu_title_select_bank));
		menuBankList.show();
	}

	private void closeMenuDialog(Dialog menu) {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
		}
	}

	private MenuItemInfo getDefaultMenu() {
		List<MenuItemInfo> menu = SysCfgUtils.getBankMenu(this);
		if (BeanUtils.isNotEmpty(menu)) {
			if (StringUtils.isNotEmpty(bankCode)) {
				for (MenuItemInfo info : menu) {
					if (bankCode.equals(info.menuCode)) {
						return info;
					}
				}
			} else {
				return menu.get(0);
			}
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		ActivityUtil.hideKeyboard(this);
		switch (v.getId()) {
		case R.id.btn_send_sms_verfycode:
			// 获取验证码
			if (!isNetConnected()) {
				showToast(R.string.network_disconnected);
			} else {
				getSmsVerfiyCode();
			}
			break;
		case R.id.ll_select_bank:
			showBankMenu();
			break;
		case R.id.btn_submit:
			doSubmitAction();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	public void onConfirm(Object obj) {

	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {
		switch (type) {
		case GlobalMessageType.MenuType.SELECT_BANK:
			String selectBank = ((MenuItemInfo) obj).menuText;
			bankCode = ((MenuItemInfo) obj).menuCode;
			mTvBank.setText(selectBank);
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
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
		mTransferLogic = LogicFactory.getLogicByClass(ITransferLogic.class);
		mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
	}

}
