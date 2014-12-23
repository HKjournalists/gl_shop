package com.glshop.net.ui.mypurse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.base.manager.LogicFactory;

/**
 * @Description : 收款人详情消息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PayeeInfoActivity extends BasicActivity {

	private PayeeInfoModel info;

	private Button mBtnPayeeModify;

	private ConfirmDialog mConfirmDialog;

	private IPurseLogic mPurseLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_payee_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.payee_detail);

		mBtnPayeeModify = getView(R.id.btn_commmon_action);
		mBtnPayeeModify.setVisibility(View.VISIBLE);
		mBtnPayeeModify.setText(R.string.modify);
		mBtnPayeeModify.setOnClickListener(this);

		getView(R.id.btn_set_default_payee).setOnClickListener(this);
		getView(R.id.btn_delete_payee).setOnClickListener(this);
		getView(R.id.tv_view_certificate_document).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		info = (PayeeInfoModel) getIntent().getSerializableExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO);
		if (info != null) {
			((TextView) findViewById(R.id.tv_payee_name)).setText(info.name);
			((TextView) findViewById(R.id.tv_payee_bank)).setText(info.bankName);
			((TextView) findViewById(R.id.tv_sub_bank)).setText(info.subBank);
			((TextView) findViewById(R.id.tv_bank_card)).setText(info.card);
		} else {
			showToast("收款人信息不能为空!");
			finish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.PurseMessageType.MSG_SET_DEFAULT_PAYEE_INFO_SUCCESS:
			onSetDefaultSuccess();
			break;
		case GlobalMessageType.PurseMessageType.MSG_SET_DEFAULT_PAYEE_INFO_FAILED:
			onSetDefaultFailed(respInfo);
			break;
		case GlobalMessageType.PurseMessageType.MSG_DELETE_PAYEE_INFO_SUCCESS:
			onDeleteSuccess();
			break;
		case GlobalMessageType.PurseMessageType.MSG_DELETE_PAYEE_INFO_FAILED:
			onDeleteFailed(respInfo);
			break;
		}
	}

	private void onSetDefaultSuccess() {
		closeSubmitDialog();
		showToast("设置默认收款人成功!");
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onSetDefaultFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onDeleteSuccess() {
		closeSubmitDialog();
		showToast("删除收款人成功!");
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void onDeleteFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commmon_action: // 修改收款人信息
			Intent intent = new Intent(this, PayeeAddActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO, (PayeeInfoModel) info.clone());
			startActivity(intent);
			break;
		case R.id.btn_set_default_payee: // 设置默认收款人
			doSetDefaultAction();
			break;
		case R.id.btn_delete_payee: // 删除收款人
			showConfirmDialog();
			break;
		case R.id.tv_view_certificate_document: // 查看证明材料
			if (info != null) {
				browseImage(info.certImgInfo);
			}
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void doDeleteAction() {
		closeSubmitDialog();
		showSubmitDialog(getString(R.string.submiting_request));
		mPurseLogic.deletePayeeInfo(info.id);
	}

	private void doSetDefaultAction() {
		closeSubmitDialog();
		showSubmitDialog(getString(R.string.submiting_request));
		mPurseLogic.setDefaultPayeeInfo(info.id);
	}

	private void showConfirmDialog() {
		if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
			mConfirmDialog.dismiss();
		}

		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.confirmed_delete_payee_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(Object obj) {
				doDeleteAction();
			}

			@Override
			public void onCancel() {

			}
		});
		mConfirmDialog.show();
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
	}
}
