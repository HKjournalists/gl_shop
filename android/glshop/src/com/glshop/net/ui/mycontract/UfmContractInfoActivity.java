package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.fragment.contract.ContractDetailFragment;
import com.glshop.net.ui.basic.view.dialog.BaseDialog.IDialogCallback;
import com.glshop.net.ui.basic.view.dialog.ConfirmDialog;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.AgreeContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 待确认合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class UfmContractInfoActivity extends BaseContractInfoActivity implements ContractDetailFragment.Callback {

	private static final String TAB_MODEL = "fragment-tab-model";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private ContractDetailFragment mFragmentModel;

	private ConfirmDialog mConfirmDialog;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unconfirmed_contract_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.btn_confirm_contract).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		String contractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		if (StringUtils.isNotEmpty(contractId)) {
			mFragmentModel = getFragment(TAB_MODEL);
			if (mFragmentModel == null) {
				mFragmentModel = new ContractDetailFragment();
				Bundle args = new Bundle();
				args.putString(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractId);
				mFragmentModel.setArguments(args);
			}
			showFragment(FRAGMENT_CONTAINER, mFragmentModel, TAB_MODEL);
		} else {
			showToast("合同ID不能为空!");
			finish();
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_AGREE_CONTRACT_SIGN_SUCCESS:
			onSubmitSuccess(respInfo);
			break;
		case ContractMessageType.MSG_AGREE_CONTRACT_SIGN_FAILED:
			onSubmitFailed(respInfo);
			break;
		}
	}

	private void showConfirmDialog() {
		if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
			mConfirmDialog.dismiss();
		}

		mConfirmDialog = new ConfirmDialog(this, R.style.dialog);
		mConfirmDialog.setContent(getString(R.string.confirmed_contract_warning_tip));
		mConfirmDialog.setCallback(new IDialogCallback() {

			@Override
			public void onConfirm(Object obj) {
				if (checkDepositStatus()) {
					showSubmitDialog();
					mContractLogic.agreeContractSign(mFragmentModel.getContractInfo().contractId);
				} else {
					//TODO 跳转保证金不足页面
				}
			}

			@Override
			public void onCancel() {

			}
		});
		mConfirmDialog.show();
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
		case R.id.btn_confirm_contract:
			showConfirmDialog();
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

	public void updateDataStatus(DataStatus status) {
		getView(R.id.btn_confirm_contract).setVisibility(status == DataStatus.NORMAL ? View.VISIBLE : View.GONE);
	}

	private void onSubmitFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void update(ContractModelInfo info) {
		if (info != null) {
			if (info.lifeCycle == ContractLifeCycle.DRAFTING) {
				if (info.buyType == BuyType.BUYER) {
					if (info.buyerStatus != null && info.buyerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
						getView(R.id.btn_confirm_contract).setVisibility(View.GONE);
					} else {
						getView(R.id.btn_confirm_contract).setVisibility(View.VISIBLE);
					}
				} else {
					if (info.sellerStatus != null && info.sellerStatus.oprType == ContractOprType.CONFRIM_CONTRACT) {
						getView(R.id.btn_confirm_contract).setVisibility(View.GONE);
					} else {
						getView(R.id.btn_confirm_contract).setVisibility(View.VISIBLE);
					}
				}
			} else {
				getView(R.id.btn_confirm_contract).setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
