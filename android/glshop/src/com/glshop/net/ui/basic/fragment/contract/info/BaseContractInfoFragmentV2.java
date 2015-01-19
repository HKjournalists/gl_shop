package com.glshop.net.ui.basic.fragment.contract.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.view.ContractStatusView;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.mycontract.ContractDetailActivity;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 合同信息界面Fragment基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public abstract class BaseContractInfoFragmentV2 extends BasicFragment {

	/** Fragment layout ID */
	private int layoutId = 0;

	/** 合同ID */
	protected String mContractId;

	/** 合同信息 */
	protected ContractInfoModel mContractInfo;

	/** 合同状态图 */
	protected ContractStatusView mContractStatus;

	/** 常驻操作按钮 */
	protected Button mBtnAction;

	/** 常驻取消合同按钮 */
	protected Button mBtnActionCancel;

	/** 取消合同确认对话框 */
	private ConfirmDialog mCancelDialog;

	/** 请求对话框 */
	protected CommonProgressDialog mSubmitDialog;

	protected IContractLogic mContractLogic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(layoutId, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		layoutId = bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID);
		mContractInfo = (ContractInfoModel) bundle.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_CANCEL_SUCCESS: // 合同取消成功
			onCancelContractSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_CANCEL_FAILED: // 合同取消失败
			onCancelContractFailed(respInfo);
			break;
		}
	}

	private void onCancelContractSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.contract_action_cancel);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_apply_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_cancel_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onCancelContractFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_contract_action_cancel:
			showCancelDialog();
			break;
		case R.id.btn_contract_info_detail:
			Intent intent = new Intent(mContext, ContractDetailActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
			break;
		}
	}

	private void showCancelDialog() {
		if (mCancelDialog != null && mCancelDialog.isShowing()) {
			mCancelDialog.dismiss();
		}

		mCancelDialog = new ConfirmDialog(mContext, R.style.dialog);
		mCancelDialog.setContent(getString(R.string.cancel_contract_warning_tips));
		mCancelDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				doCancelAction();
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mCancelDialog.show();
	}

	/**
	 * 取消合同操作
	 */
	protected void doCancelAction() {
		showSubmitDialog();
		mContractLogic.cancelContract(mContractInfo.contractId);
	}

	protected void showSubmitDialog() {
		closeSubmitDialog();
		mSubmitDialog = new CommonProgressDialog(mContext, getString(R.string.do_request_ing));
		mSubmitDialog.show();
	}

	protected void closeSubmitDialog() {
		if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
			mSubmitDialog.dismiss();
		}
	}

	/**
	 * 更新合同列表
	 */
	protected void refreshContractList(ContractType... typeList) {
		for (ContractType type : typeList) {
			Message refreshMsg = new Message();
			refreshMsg.what = GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_LIST;
			refreshMsg.arg2 = type.toValue();
			MessageCenter.getInstance().sendMessage(refreshMsg);
		}
	}

	protected void finish() {
		if (getActivity() != null) {
			getActivity().finish();
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

	protected abstract void initView();

	protected abstract void initData();

}
