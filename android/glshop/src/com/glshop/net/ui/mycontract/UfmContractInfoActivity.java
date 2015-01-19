package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.fragment.contract.ContractDetailFragmentV2;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.AgreeContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 待确认合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class UfmContractInfoActivity extends BaseContractInfoActivity {

	private static final String TAB_MODEL = "fragment-tab-model";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private Button mBtnDelete;

	private ContractDetailFragmentV2 mFragmentModel;
	private ContractModelInfo mModelInfo;

	private ConfirmDialog mConfirmDialog;
	private ConfirmDialog mCancelDialog;
	private ConfirmDialog mDeleteDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unconfirmed_contract_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);

		initLoadView();
		mNormalDataView = getView(R.id.ll_ufm_contract_info);

		mBtnDelete = getView(R.id.btn_commmon_action);
		mBtnDelete.setText("");
		mBtnDelete.setBackgroundResource(R.drawable.selector_btn_delete);
		mBtnDelete.setOnClickListener(this);

		getView(R.id.btn_confirm_now).setOnClickListener(this);
		getView(R.id.btn_cancel_confirm).setOnClickListener(this);
		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		if (StringUtils.isNotEmpty(mContractId)) {
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getContractModel(mContractId);
		} else {
			showToast("合同ID不能为空!");
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractModel(mContractId);
	}

	private void updateUI() {
		mFragmentModel = getFragment(TAB_MODEL);
		if (mFragmentModel == null) {
			mFragmentModel = new ContractDetailFragmentV2();
			mFragmentModel.setArguments(createFragmentArgs());
		}
		showFragment(FRAGMENT_CONTAINER, mFragmentModel, TAB_MODEL);

		updateActionBar();
	}

	private Bundle createFragmentArgs() {
		Bundle args = new Bundle();
		args.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_MODEL_INFO, mModelInfo);
		return args;
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_GET_CONTRACT_MODEL_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case ContractMessageType.MSG_GET_CONTRACT_MODEL_FAILED:
			onGetFailed(respInfo);
			break;
		case ContractMessageType.MSG_AGREE_CONTRACT_SIGN_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case ContractMessageType.MSG_AGREE_CONTRACT_SIGN_FAILED:
			onSubmitFailed(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_CANCEL_SUCCESS:
			onCancelSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_CANCEL_FAILED:
			onCancelFailed(respInfo);
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mModelInfo = (ContractModelInfo) respInfo.data;
			if (mModelInfo != null) {
				updateDataStatus(DataStatus.NORMAL);
				updateUI();
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	private void showConfirmDialog() {
		closeDialog(mConfirmDialog);
		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.confirmed_contract_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				if (checkDepositStatus()) {
					showSubmitDialog();
					mContractLogic.agreeContractSign(mContractId);
				} else {
					//TODO 跳转保证金不足页面
				}
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mConfirmDialog.show();
	}

	private void showCancelDialog() {
		closeDialog(mConfirmDialog);
		mCancelDialog = new ConfirmDialog(this, R.style.dialog);
		mCancelDialog.setContent(getString(R.string.cancel_contract_warning_tip));
		mCancelDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				mContractLogic.multiCancelContract(mContractId, ContractCancelType.CANCEL);
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mCancelDialog.show();
	}

	private void showDeleteDialog() {
		closeDialog(mDeleteDialog);
		mDeleteDialog = new ConfirmDialog(this, R.style.dialog);
		mDeleteDialog.setContent(getString(R.string.delete_contract_warning_tip));
		mDeleteDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				showSubmitDialog();
				mContractLogic.multiCancelContract(mContractId, ContractCancelType.DELETE);
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mDeleteDialog.show();
	}

	private boolean checkDepositStatus() {
		if (GlobalConstants.Common.VALIDATE_DEPOSIT_STATUS && !GlobalConfig.getInstance().isDepositEnough()) {
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_confirm_now:
			showConfirmDialog();
			break;
		case R.id.btn_cancel_confirm:
			showCancelDialog();
			break;
		case R.id.btn_commmon_action:
			showDeleteDialog();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void onSubmitSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.UNCONFIRMED, ContractType.ONGOING);
		// 更新UI
		AgreeContractInfoModel info = (AgreeContractInfoModel) respInfo.data;

		String datetime = info.singedDatetime;
		if (StringUtils.isNotEmpty(datetime)) {
			datetime = DateUtils.convertDate2String(DateUtils.COMMON_DATE_FORMAT, DateUtils.CONTRACT_DATETIME_FORMAT, datetime);
		}

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		if (info.isAnotherSigned) {
			tipInfo.operatorTipContent = String.format(getString(R.string.operator_tips_confirm_contract_success_2), datetime);
		} else {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_confirm_contract_success_1);
		}
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onCancelSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.UNCONFIRMED, ContractType.ENDED);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_cancel_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_CONTRACT;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onCancelFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void updateActionBar() {
		if (mModelInfo != null) {
			if (mModelInfo.lifeCycle == ContractLifeCycle.DRAFTING) {
				if (mModelInfo.buyType == BuyType.BUYER) {
					if (mModelInfo.buyerStatus != null && mModelInfo.buyerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
						getView(R.id.btn_confirm_now).setVisibility(View.GONE);
					} else {
						getView(R.id.btn_confirm_now).setVisibility(View.VISIBLE);
					}
				} else {
					if (mModelInfo.sellerStatus != null && mModelInfo.sellerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
						getView(R.id.btn_confirm_now).setVisibility(View.GONE);
					} else {
						getView(R.id.btn_confirm_now).setVisibility(View.VISIBLE);
					}
				}
			} else {
				getView(R.id.btn_confirm_now).setVisibility(View.GONE);
			}
		}
	}

}
