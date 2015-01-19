package com.glshop.net.ui.basic.fragment.contract;

import java.util.List;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.model.TipInfoModel;
import com.glshop.net.ui.basic.adapter.contract.FirstNegotiateAdapter;
import com.glshop.net.ui.basic.view.NegotiateListView;
import com.glshop.net.ui.mycontract.ContractOprResultTipsActivity;
import com.glshop.net.ui.mycontract.InputFirstNegotiateActivity;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 第一次议价阶段界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class FirstNegotiateStageFragment extends BaseContractInfoFragment {

	@Override
	protected void initView() {
		mTvContractId = getView(R.id.tv_contract_id);
		mTvCountDownTime = getView(R.id.tv_contract_countdown_time);
		mContractStatus = getView(R.id.ll_contract_status);

		getView(R.id.btn_contract_action_agree).setOnClickListener(this);
		getView(R.id.btn_contract_action_disagree).setOnClickListener(this);
		getView(R.id.btn_contract_action_cancel).setOnClickListener(this);
		getView(R.id.btn_contract_info_detail).setOnClickListener(this);
	}

	@Override
	protected void initData() {
		if (mContractInfo != null) {
			/*List<NegotiateInfoModel> list = new ArrayList<NegotiateInfoModel>();
			for (int i = 0; i < 4; i++) {
				NegotiateInfoModel model = new NegotiateInfoModel();
				model.isMine = i % 2 == 0;
				list.add(model);
			}*/
			mTvContractId.setText(mContractInfo.contractId);
			mTvCountDownTime.setText(String.format(getString(R.string.contract_wait_time), DateUtils.getWaitTime(mContractInfo.expireTime)));
			mContractStatus.setContractInfo(mContractInfo);

			// 显示议价列表信息
			List<NegotiateInfoModel> list = mContractInfo.firstNegotiateList;
			NegotiateListView lv = getView(R.id.ll_first_negotiate_list);
			if (BeanUtils.isNotEmpty(list)) {
				lv.setAdapter(new FirstNegotiateAdapter(mContext, list));
				NegotiateInfoModel negInfo = list.get(0);
				updateActionButton(negInfo.isMine);
			} else {
				lv.setVisibility(View.GONE);
				updateActionButton(true);
			}
		} else {
			showToast("合同信息不能为空!");
			finish();
		}
	}

	/**
	 * 显示操作按钮
	 * @param isMyNegotiate
	 */
	private void updateActionButton(boolean isMyNegotiate) {
		getView(R.id.btn_contract_action_agree).setVisibility(isMyNegotiate ? View.GONE : View.VISIBLE);
		getView(R.id.btn_contract_action_disagree).setVisibility(isMyNegotiate ? View.GONE : View.VISIBLE);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_SUCCESS: // 合同验收或同意第一次议价成功
			onAcceptanceSuccess(respInfo);
			break;
		case ContractMessageType.MSG_CONTRACT_ACCEPTANCE_FAILED: // 合同验收或同意第一次议价失败
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
		tipInfo.operatorTipContent = getString(R.string.operator_tips_agree_first_negotiate_success);
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
	 * 同意对方的第一次议价
	 */
	private void doAgreeAction() {
		showSubmitDialog();
		mContractLogic.acceptanceContract(mContractInfo.contractId, DataConstants.ContractOprType.APPLY_DISPRICE.toValue());
	}

	/**
	 * 不同意对方的第一次议价
	 */
	private void doDisagreeAction() {
		Intent intent = new Intent(mContext, InputFirstNegotiateActivity.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		startActivity(intent);
	}

}
