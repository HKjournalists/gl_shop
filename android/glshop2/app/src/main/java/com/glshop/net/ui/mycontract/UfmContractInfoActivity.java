package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalErrorMessage;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.fragment.contract.ContractDetailFragmentV2;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.ContractUtil;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.DataConstants.PurseType;
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

	private View mBtnConfirm;
	private View mBtnCancel;
	private ImageButton mBtnDelete;

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
		mBtnConfirm = getView(R.id.btn_confirm_now);
		mBtnCancel = getView(R.id.btn_cancel_confirm);

		mBtnDelete.setImageResource(R.drawable.selector_btn_delete);
		mBtnDelete.setOnClickListener(this);

		getView(R.id.btn_confirm_now).setOnClickListener(this);
		getView(R.id.btn_cancel_confirm).setOnClickListener(this);
		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		mModelInfo = (ContractModelInfo) getIntent().getSerializableExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_MODEL_INFO);
		if (mModelInfo != null) {
			mContractId = mModelInfo.contractId;
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
		} else {
			if (StringUtils.isNotEmpty(mContractId)) {
				updateDataStatus(DataStatus.LOADING);
				mContractLogic.getContractModel(mContractId);
			} else {
				showToast("合同ID不能为空!");
				finish();
			}
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
		if (message.what == ContractMessageType.MSG_REFRESH_CONTRACT_INFO) {
			if (isCurrentActivity()) {
				String newContractId = String.valueOf(message.obj);
				if (isSameContract(mContractId, newContractId)) {
					Intent intent = new Intent(this, UfmContractInfoActivity.class);
					intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, newContractId);
					startActivity(intent);
					finish();
					return;
				}
			}
		}

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
		case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_SUCCESS:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onCancelSuccess(respInfo);
			}
			break;
		case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED:
			if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
				onCancelFailed(respInfo);
			}
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
				if (checkDeposit()) {
					showSubmitDialog();
					mContractLogic.agreeContractSign(mContractId);
				} else {
					gotoRecharge();
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
				mInvoker = String.valueOf(System.currentTimeMillis());
				mContractLogic.multiCancelContract(mInvoker, mContractId, ContractCancelType.CANCEL);
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
				mInvoker = String.valueOf(System.currentTimeMillis());
				mContractLogic.multiCancelContract(mInvoker, mContractId, ContractCancelType.DELETE);
			}

			@Override
			public void onCancel(int type) {

			}
		});
		mDeleteDialog.show();
	}

	private boolean checkDeposit() {
		if (GlobalConstants.CfgConstants.VALIDATE_DEPOSIT_BALANCE) {
			return calNeedDeposit() <= GlobalConfig.getInstance().getDepositBalance();
		}
		return true;
	}

	private double toRechargeDeposit() {
		return Math.abs(calNeedDeposit() - GlobalConfig.getInstance().getDepositBalance());
	}

	private double calNeedDeposit() {
		double needDeposit = 0;
		if (mModelInfo != null) {
			needDeposit = Double.parseDouble(mModelInfo.amount) * SysCfgUtils.getDepositPercent(this);
			needDeposit = needDeposit > 3000 ? 3000 : needDeposit;
		}
		return needDeposit;
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
			//tipInfo.operatorTipContent = String.format(getString(R.string.operator_tips_confirm_contract_success_2), datetime);
			tipInfo.operatorTipContent = getString(R.string.operator_tips_confirm_contract_success_3);
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
		if (respInfo != null) {
			String errorCode = respInfo.errorCode;
			if (StringUtils.isNotEmpty(errorCode)) {
				if (errorCode.equals(String.valueOf(GlobalErrorMessage.ErrorCode.ERROR_CODE_100005010))) {
					showToast(R.string.error_code_100005010);
					gotoRecharge();
				} else {
					handleErrorAction(respInfo);
				}
			} else {
				handleErrorAction(respInfo);
			}
		}
	}

	private void onCancelSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.UNCONFIRMED, ContractType.ENDED);

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_opr_success_title);
		if (respInfo.intArg1 == ContractCancelType.CANCEL.toValue()) {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_cancel_draft_success);
		} else if (respInfo.intArg1 == ContractCancelType.DELETE.toValue()) {
			tipInfo.operatorTipContent = getString(R.string.operator_tips_delete_draft_success);
		}
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

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case ContractMessageType.MSG_GET_CONTRACT_MODEL_FAILED:
				showToast(R.string.error_req_get_info);
				break;
			case ContractMessageType.MSG_AGREE_CONTRACT_SIGN_FAILED:
			case ContractMessageType.MSG_CONTRACT_MULTI_CANCEL_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	private void gotoRecharge() {
		Intent intent = new Intent(UfmContractInfoActivity.this, PaymentPayWarningActivity.class);
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue());
		intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_MONEY, toRechargeDeposit());
		startActivity(intent);
		finish();
	}

	private void updateActionBar() {
		if (mModelInfo != null) {
			if (ContractUtil.isValid(mModelInfo)) {
				if (mModelInfo.lifeCycle == ContractLifeCycle.DRAFTING) {
					if (mModelInfo.buyType == BuyType.BUYER) {
						if (mModelInfo.buyerStatus != null && mModelInfo.buyerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
							mBtnConfirm.setVisibility(View.GONE);
							mBtnCancel.setVisibility(View.GONE);
						} else {
							mBtnConfirm.setVisibility(View.VISIBLE);
							mBtnCancel.setVisibility(View.VISIBLE);
						}
					} else {
						if (mModelInfo.sellerStatus != null && mModelInfo.sellerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
							mBtnConfirm.setVisibility(View.GONE);
							mBtnCancel.setVisibility(View.GONE);
						} else {
							mBtnConfirm.setVisibility(View.VISIBLE);
							mBtnCancel.setVisibility(View.VISIBLE);
						}
					}
				} else {
					mBtnConfirm.setVisibility(View.GONE);
					mBtnCancel.setVisibility(View.GONE);
				}
			} else {
				mBtnConfirm.setVisibility(View.GONE);
				mBtnCancel.setVisibility(View.GONE);
				if (mModelInfo.myContractType == ContractType.DELETION) {
					// 已删除，不再显示删除按钮
					mBtnDelete.setVisibility(View.GONE);
				} else {
					mBtnDelete.setVisibility(View.VISIBLE);
				}
			}
		}
	}

}
