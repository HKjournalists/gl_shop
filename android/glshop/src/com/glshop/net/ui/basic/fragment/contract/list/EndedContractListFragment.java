package com.glshop.net.ui.basic.fragment.contract.list;

import android.content.Intent;

import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.contract.EndedContractListAdapter;
import com.glshop.net.ui.mycontract.ContractInfoActivityV2;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;

/**
 * @Description : 已结束的合同列表Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-1-13 下午2:38:16
 */
public class EndedContractListFragment extends BaseContractListFragment<ContractSummaryInfoModel> {

	public EndedContractListFragment() {

	}

	@Override
	protected BasicAdapter<ContractSummaryInfoModel> getAdapter() {
		mAdapter = new EndedContractListAdapter(mContext, mInitData);
		return mAdapter;
	}

	@Override
	protected void handleItemClick(ContractSummaryInfoModel info) {
		Intent intent = new Intent(mContext, ContractInfoActivityV2.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.contractId);
		startActivity(intent);
	}

	@Override
	protected ContractType getContractType() {
		return ContractType.ENDED;
	}

}
