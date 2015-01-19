package com.glshop.net.ui.mycontract;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.fragment.contract.BaseContractInfoFragment;
import com.glshop.net.ui.basic.fragment.contract.CommonContractInfoFragment;
import com.glshop.net.ui.basic.fragment.contract.ContractOprRemindFragment;
import com.glshop.net.ui.basic.fragment.contract.EndedContractInfoFragment;
import com.glshop.net.ui.basic.fragment.contract.FirstNegotiateStageFragment;
import com.glshop.net.ui.basic.fragment.contract.SecondNegotiateStageFragment;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 进行中和已结束的合同信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractInfoActivity extends BaseContractInfoActivity {

	private static final String FRAGMENT_CONTRACT = "fragment-contract";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private BaseContractInfoFragment mFragmentContract;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_info);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(FRAGMENT_CONTAINER);
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.btn_commmon_action).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		if (mContractInfo != null) {
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
		} else {
			updateDataStatus(DataStatus.LOADING);
			mInvoker = String.valueOf(System.currentTimeMillis());
			mContractLogic.getContractInfo(mInvoker, mContractId);
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mInvoker = String.valueOf(System.currentTimeMillis());
		mContractLogic.getContractInfo(mInvoker, mContractId);
	}

	private void updateUI() {
		if (isUnConfirmContract()) {
			// 如果当前合同状态为未签订的状态，跳转到待确认合同界面
			Intent intent = new Intent(this, UfmContractInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractInfo.contractId);
			startActivity(intent);
			finish();
			return;
		}
		mFragmentContract = getFragment(FRAGMENT_CONTRACT);
		if (mContractInfo.buyType == BuyType.BUYER) {
			// 买家状态
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
			case IN_THE_PAYMENT: // 付款中
			case PAYED_FUNDS: // 已付款
			case SENT_GOODS: // 已发货
			case FULL_TAKEOVERED: // 全量验收通过 
				if (mFragmentContract == null) {
					mFragmentContract = new CommonContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ongoing_contract_common));
				}
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				if (mFragmentContract == null) {
					mFragmentContract = new FirstNegotiateStageFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_first_negotiate_stage));
				}
				break;
			case SIMPLE_CHECKED: // 抽样验收通过
				if (mFragmentContract == null) {
					mFragmentContract = new ContractOprRemindFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_contract_opr_remind));
				}
				break;
			case FULL_TAKEOVERING: // 全量验收中
				if (mFragmentContract == null) {
					mFragmentContract = new SecondNegotiateStageFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_second_negotiate_stage));
				}
				break;
			case NORMAL_FINISHED: // 正常结束
			case SINGLECANCEL_FINISHED: // 单方取消结束
			case DUPLEXCANCEL_FINISHED: // 双方取消结束
			case EXPIRATION_FINISHED: // 终止结束
				getView(R.id.btn_commmon_action).setVisibility(View.GONE);
				if (mFragmentContract == null) {
					mFragmentContract = new EndedContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ended_contract_info));
				}
				break;
			default:
				if (mFragmentContract == null) {
					mFragmentContract = new CommonContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ongoing_contract_common));
				}
				break;
			}
		} else {
			// 卖家状态
			switch (mContractInfo.lifeCycle) {
			case SINGED: // 已签订
			case IN_THE_PAYMENT: // 付款中
			case PAYED_FUNDS: // 已付款
			case SENT_GOODS: // 已发货
			case SIMPLE_CHECKED: // 抽样验收通过
			case FULL_TAKEOVERED: // 全量验收通过 
				if (mFragmentContract == null) {
					mFragmentContract = new CommonContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ongoing_contract_common));
				}
				break;
			case SIMPLE_CHECKING: // 抽样验收中
				if (mFragmentContract == null) {
					mFragmentContract = new FirstNegotiateStageFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_first_negotiate_stage));
				}
				break;
			case FULL_TAKEOVERING: // 全量验收中
				if (BeanUtils.isNotEmpty(mContractInfo.secondNegotiateList)) {
					// 有议价列表，则显示议价界面
					if (mFragmentContract == null) {
						mFragmentContract = new SecondNegotiateStageFragment();
						mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_second_negotiate_stage));
					}
				} else {
					if (mFragmentContract == null) {
						mFragmentContract = new CommonContractInfoFragment();
						mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ongoing_contract_common));
					}
				}
				break;
			case NORMAL_FINISHED: // 正常结束
			case SINGLECANCEL_FINISHED: // 单方取消结束
			case DUPLEXCANCEL_FINISHED: // 双方取消结束
			case EXPIRATION_FINISHED: // 终止结束
				getView(R.id.btn_commmon_action).setVisibility(View.GONE);
				if (mFragmentContract == null) {
					mFragmentContract = new EndedContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ended_contract_info));
				}
				break;
			default:
				if (mFragmentContract == null) {
					mFragmentContract = new CommonContractInfoFragment();
					mFragmentContract.setArguments(createFragmentArgs(R.layout.fragment_ongoing_contract_common));
				}
				break;
			}
		}
		updateFragment();
	}

	/**
	 * 当前合同状态是否为未签订状态
	 * @return
	 */
	private boolean isUnConfirmContract() {
		if (mContractInfo != null && mContractInfo.lifeCycle == ContractLifeCycle.DRAFTING) {
			return true;
		}
		return false;
	}

	private Bundle createFragmentArgs(int layoutId) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.ContractAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, layoutId);
		args.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		return args;
	}

	private void updateFragment() {
		showFragment(FRAGMENT_CONTAINER, mFragmentContract, FRAGMENT_CONTRACT);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		if (respInfo != null && isCurRequest(mInvoker, String.valueOf(respInfo.invoker))) {
			switch (message.what) {
			case ContractMessageType.MSG_GET_CONTRACT_INFO_SUCCESS:
				onGetContractInfoSuccess(respInfo);
				break;
			case ContractMessageType.MSG_GET_CONTRACT_INFO_FAILED:
				onGetContractInfoFailed(respInfo);
				break;
			}
		}
	}

	private void onGetContractInfoSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mContractInfo = (ContractInfoModel) respInfo.data;
			if (mContractInfo != null) {
				updateUI();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetContractInfoFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_common_back:
			if (mFragmentContract == null || mFragmentContract.onBack()) {
				finish();
			}
			break;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (mFragmentContract != null && !mFragmentContract.onBack()) {
				return true;
			} else {
				return super.dispatchKeyEvent(event);
			}
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

}
