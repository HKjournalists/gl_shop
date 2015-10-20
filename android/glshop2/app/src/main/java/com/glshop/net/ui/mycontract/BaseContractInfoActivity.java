package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.view.ContractStatusView;
import com.glshop.net.ui.basic.view.NegotiateListView;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同信息界面基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public abstract class BaseContractInfoActivity extends BasicFragmentActivity {

	/** 合同ID */
	protected String mContractId;

	/** 合同信息 */
	protected ContractInfoModel mContractInfo;

	/** 合同状态图 */
	protected ContractStatusView mContractStatus;

	/** 议价列表 */
	protected NegotiateListView mNegotiateList;

	/** 合同ID */
	protected TextView mTvContratId;

	/** 合同倒计时 */
	protected TextView mTvCountDownTime;

	/** 常驻操作按钮1 */
	protected Button mBtnAction1;

	/** 常驻操作按钮2 */
	protected Button mBtnAction2;

	/** 常驻取消合同按钮 */
	protected Button mBtnActionCancel;

	/** 合同编号 */
	protected TextView mTvContractId;

	/** 取消确认对话框 */
	private ConfirmDialog mCancelDialog;

	/** 请求对话框 */
	protected CommonProgressDialog mSubmitDialog;

	/** 合同模块Activity堆栈 */
	//private static Stack<Activity> mContractStack = new Stack<Activity>();

	protected IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mContractStack.add(this);
		//Logger.e(TAG, "add to Stack & Size = " + mContractStack.size());
		mContractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		mContractInfo = (ContractInfoModel) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTACT_CUSTOM_SERVICE_SUCCESS:
			onContactCustomServiceSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTACT_CUSTOM_SERVICE_FAILED:
			onContactCustomServiceFailed(respInfo);
			break;
		}
	}

	private void onContactCustomServiceSuccess(RespInfo respInfo) {
		closeSubmitDialog();

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.contract_title_contact_custom_service);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_feedback_success_title_1);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contact_custom_service_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onContactCustomServiceFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case ContractMessageType.MSG_CONTACT_CUSTOM_SERVICE_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	protected void showSubmitDialog() {
		closeSubmitDialog();
		mSubmitDialog = new CommonProgressDialog(this, getString(R.string.do_request_ing));
		mSubmitDialog.show();
	}

	protected void closeSubmitDialog() {
		if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
			mSubmitDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_commmon_action:
			doContactCustomServiceAction();
			break;
		case R.id.btn_contract_action_cancel:
			showCancelDialog();
			break;
		case R.id.btn_contract_info_detail:
			Intent intent = new Intent(this, ContractDetailActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void finish() {
		//mContractStack.remove(this);
		//Logger.e(TAG, "remove from Stack & Size = " + mContractStack.size());
		super.finish();
	}

	/**
	 * 清除合同模块页面堆栈
	 */
	public void cleanStack() {
		super.cleanStack();
		//Logger.e(TAG, "StackSize = " + mContractStack.size());
		/*while (!mContractStack.isEmpty()) {
			Activity activity = mContractStack.pop();
			activity.finish();
		}*/
	}

	private void showCancelDialog() {
		if (mCancelDialog != null && mCancelDialog.isShowing()) {
			mCancelDialog.dismiss();
		}

		mCancelDialog = new ConfirmDialog(this, R.style.dialog);
		mCancelDialog.setContent(getString(mContractInfo.buyType == BuyType.BUYER ? R.string.buyer_cancel_contract_warning_tips : R.string.seller_cancel_contract_warning_tips));
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
	 * 联系客服操作
	 */
	protected void doContactCustomServiceAction() {
		if (mContractInfo != null) {
			showSubmitDialog();
			mContractLogic.contactCustomService(mContractInfo.contractId);
		}
	}

	/**
	 * 取消合同操作
	 */
	protected void doCancelAction() {
		showSubmitDialog();
		mContractLogic.cancelContract(mContractInfo.contractId);
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

	protected boolean isSameContract(String oldContractId, String newContractId) {
		if (StringUtils.isNotEmpty(oldContractId) && StringUtils.isNotEmpty(newContractId) && oldContractId.equals(newContractId)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
