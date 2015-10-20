package com.glshop.net.ui.basic.fragment.contract.list;

import android.content.Intent;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.contract.UfmContractListAdapter;
import com.glshop.net.ui.mycontract.UfmContractInfoActivity;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;

/**
 * @Description : 待确认的合同列表Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-1-13 下午2:38:16
 */
public class UfmContractListFragment extends BaseContractListFragment<ContractSummaryInfoModel> {

	public UfmContractListFragment() {

	}

	@Override
	protected BasicAdapter<ContractSummaryInfoModel> getAdapter() {
		mAdapter = new UfmContractListAdapter(mContext, mInitData);
		return mAdapter;
	}

	@Override
	protected void handleItemClick(ContractSummaryInfoModel info) {
		Intent intent = new Intent(mContext, UfmContractInfoActivity.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.contractId);
		startActivity(intent);
	}

	@Override
	protected ContractType getContractType() {
		return ContractType.UNCONFIRMED;
	}

	@Override
	protected void updateDataStatus(DataStatus status) {
		if (status == DataStatus.EMPTY) {
			updateEmptyDataMessage(mContext.getString(R.string.load_ufm_contract_data_empty));
		}
		super.updateDataStatus(status);
	}

}
