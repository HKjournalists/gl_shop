package com.glshop.net.ui.basic.fragment.contract;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.mycontract.InputSecondNegotiateActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 合同关键操作提醒说明界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractOprRemindFragment extends BaseContractInfoFragment {

	@Override
	protected void initView() {
		mTvContractId = getView(R.id.tv_contract_id);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		mContractStatus = getView(R.id.ll_contract_status);
		mBtnAction1 = getView(R.id.btn_contract_action_agree);
		mBtnAction2 = getView(R.id.btn_contract_action_disagree);

		getView(R.id.btn_contract_action_agree).setOnClickListener(this);
		getView(R.id.btn_contract_action_disagree).setOnClickListener(this);
		getView(R.id.btn_contract_action_cancel).setOnClickListener(this);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		mTvContractId.setText(mContractInfo.contractId);
		mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
		mContractStatus.setContractInfo(mContractInfo);
		mBtnAction1.setText(getString(R.string.contract_action_begin_second_check_pass));
		mBtnAction2.setText(getString(R.string.contract_action_begin_second_check_failed));
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_SUCCESS: // 同意全量验收成功
			onAcceptanceSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_FAILED: // 同意全量验收失败
			onAcceptanceFailed(respInfo);
			break;
		}
	}

	private void onAcceptanceSuccess(RespInfo respInfo) {
		closeSubmitDialog();
		refreshContractList(ContractType.ONGOING, ContractType.ENDED);
		Intent intent = new Intent(mContext, ContractOprResultTipsActivity.class);
		TipInfoModel tipInfo = new TipInfoModel();
		tipInfo.operatorTipTitle = getString(R.string.my_contract);
		tipInfo.operatorTipTypeTitle = getString(R.string.operator_tips_confirm_success_title);
		tipInfo.operatorTipContent = getString(R.string.operator_tips_confirm_success_title);
		tipInfo.operatorTipActionText1 = getString(R.string.operator_tips_action_text_view_mycontract);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_KEY_TIPS_INFO, tipInfo);
		startActivity(intent);
	}

	private void onAcceptanceFailed(RespInfo respInfo) {
		closeSubmitDialog();
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_contract_action_agree:
			doAgreeAction();
			break;
		case R.id.btn_contract_action_disagree:
			doDisagreeAction();
			break;
		}
	}

	/**
	 * 同意全量验收
	 */
	private void doAgreeAction() {
		showSubmitDialog();
		mContractLogic.acceptanceContract(mContractInfo.contractId, DataConstants.ContractOprType.APPLY_DISPRICE.toValue());
	}

	/**
	 * 不同意全量验收
	 */
	private void doDisagreeAction() {
		Intent intent = new Intent(mContext, InputSecondNegotiateActivity.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		startActivity(intent);
	}

}
