package com.glshop.net.ui.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.PursePayType;
import com.glshop.net.common.GlobalConstants.TipActionBackType;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.common.GlobalMessageType.PurseMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.logic.purse.IPurseLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.tips.OperatorTipsActivity;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.base.manager.LogicFactory;

/**
 * @Description : 线下支付界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class RechargeByOfflineActivity extends BasicActivity {

	private PurseType purseType = PurseType.DEPOSIT;

	private PursePayType payType;

	private String contractId;

	private IPurseLogic mPurseLogic;
	private IContractLogic mContractLogics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_recharge_by_offline);
		initView();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.purse_recharge);
		((TextView) getView(R.id.tv_custom_service_phone)).setText(GlobalConstants.CfgConstants.PLATFORM_CUSTOM_SERVICE_PHONE);
		getView(R.id.btn_done_recharge).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
		contractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		purseType = PurseType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PURSE_TYPE, PurseType.DEPOSIT.toValue()));
		payType = PursePayType.convert(getIntent().getIntExtra(GlobalAction.PurseAction.EXTRA_KEY_PAY_TYPE, PursePayType.RECHARGE.toValue()));
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case PurseMessageType.MSG_RECHARGE_OFFLINE_SUCCESS:
			onRechargeSuccess(respInfo);
			break;
		case PurseMessageType.MSG_RECHARGE_OFFLINE_FAILED:
			onRechargeFailed(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_PAY_OFFLINE_SUCCESS:
			onPaymentSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_PAY_OFFLINE_FAILED:
			onPaymentFailed(respInfo);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_done_recharge:
			doSubmitAction();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void doSubmitAction() {
		showSubmitDialog();
		if (payType == PursePayType.PAYMENT) {
			mContractLogics.payContractOffline(contractId);
		} else {
			mPurseLogic.purseRecharegeOffline(purseType);
		}
	}

	private void onPaymentSuccess(RespInfo respInfo) {
		closeSubmitDialog();

		Intent intent = new Intent(this, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.pay_success_title);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_pay_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_contract_recharge_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);

		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractId);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onRechargeSuccess(RespInfo respInfo) {
		closeSubmitDialog();

		Intent intent = new Intent(this, OperatorTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_purse);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_feedback_success_title_2);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_has_recharge_success);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mypurse);
		tipInfo.operatorTipActionClass1 = MainActivity.class;
		tipInfo.operatorTipAction1 = GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE;
		tipInfo.backType = TipActionBackType.DO_ACTION1;
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
		finish();
	}

	private void onPaymentFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	private void onRechargeFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case PurseMessageType.MSG_RECHARGE_OFFLINE_FAILED:
			case ContractMessageType.MSG_CONTRACT_PAY_OFFLINE_FAILED:
				showToast(R.string.error_req_submit_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	protected void initLogics() {
		mPurseLogic = LogicFactory.getLogicByClass(IPurseLogic.class);
		mContractLogics = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
