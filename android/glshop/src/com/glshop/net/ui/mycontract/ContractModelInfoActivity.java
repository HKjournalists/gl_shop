package com.glshop.net.ui.mycontract;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.fragment.contract.ContractDetailFragmentV2;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同模板信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractModelInfoActivity extends BaseContractInfoActivity {

	private static final String TAB_MODEL = "fragment-tab-model";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private ContractDetailFragmentV2 mFragmentModel;
	private ContractModelInfo mModelInfo;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_model_info);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		initLoadView();
		mNormalDataView = getView(R.id.ll_ufm_contract_info);
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

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
