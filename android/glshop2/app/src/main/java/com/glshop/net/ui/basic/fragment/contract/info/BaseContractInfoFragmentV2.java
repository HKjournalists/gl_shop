package com.glshop.net.ui.basic.fragment.contract.info;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.view.ContractStatusViewV2;
import com.glshop.net.ui.basic.view.dialog.CommonProgressDialog;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.base.manager.MessageCenter;

/**
 * @Description : 合同信息界面Fragment基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public abstract class BaseContractInfoFragmentV2 extends BasicFragment {

	/** Fragment layout ID */
	private int layoutId = 0;

	/** 合同ID */
	protected String mContractId;

	/** 合同信息 */
	protected ContractInfoModel mContractInfo;

	/** 合同状态图 */
	protected ContractStatusViewV2 mContractStatus;

	/** 请求对话框 */
	protected CommonProgressDialog mSubmitDialog;

	protected IContractLogic mContractLogic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(layoutId, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		layoutId = bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID);
		mContractInfo = (ContractInfoModel) bundle.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
	}

	protected void showSubmitDialog() {
		closeSubmitDialog();
		mSubmitDialog = new CommonProgressDialog(mContext, getString(R.string.do_request_ing));
		mSubmitDialog.show();
	}

	protected void closeSubmitDialog() {
		if (mSubmitDialog != null && mSubmitDialog.isShowing()) {
			mSubmitDialog.dismiss();
		}
	}

	/**
	 * 更新合同列表
	 */
	protected void refreshContractList(ContractType... typeList) {
		for (ContractType type : typeList) {
			Message refreshMsg = new Message();
			refreshMsg.what = GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_LIST;
			refreshMsg.arg2 = type.toValue();
			MessageCenter.getInstance().sendMessage(refreshMsg);
		}
	}

	protected void finish() {
		if (getActivity() != null) {
			getActivity().finish();
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

	protected abstract void initView();

	protected abstract void initData();

}
