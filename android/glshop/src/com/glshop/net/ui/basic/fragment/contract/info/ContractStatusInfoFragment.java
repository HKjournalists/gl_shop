package com.glshop.net.ui.basic.fragment.contract.info;

/**
 * @Description : 合同状态信息界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractStatusInfoFragment extends BaseContractInfoFragmentV2 {

	@Override
	protected void initView() {
		//mContractStatus = getView(R.id.ll_contract_status);
	}

	@Override
	protected void initData() {
		if (mContractInfo != null) {
			//mContractStatus.setContractInfo(mContractInfo);
		} else {
			// TODO: show error msg
		}
	}

}
