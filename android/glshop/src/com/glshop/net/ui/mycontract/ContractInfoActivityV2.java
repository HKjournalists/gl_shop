package com.glshop.net.ui.mycontract;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.ContractInfoType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType.ContractMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter.TabInfo;
import com.glshop.net.ui.basic.fragment.contract.info.ContractOprInfoFragment;
import com.glshop.net.ui.basic.fragment.contract.info.ContractStatusInfoFragment;
import com.glshop.net.ui.basic.view.TabIndicator;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 我的合同主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractInfoActivityV2 extends BasicFragmentActivity implements ViewPager.OnPageChangeListener {

	private static final String TAB_CONTRACT_OPERATION = "fragment-tab-operation";
	private static final String TAB_CONTRACT_STATUS = "fragment-tab-status";

	private static final int FRAGMENT_CONTAINER = R.id.contract_viewpager;

	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;

	private ContractOprInfoFragment mFragmentOperation;
	private ContractStatusInfoFragment mFragmentStatus;

	private ContractInfoType contractInfoType = ContractInfoType.OPERATION;
	private ContractInfoModel mContractInfo;

	private RadioButton mRdoBtnOperation;
	private RadioButton mRdoBtnStatus;

	private TabIndicator mIndicator;

	private String mContractId;
	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_info_v2);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);

		initLoadView();
		mNormalDataView = getView(R.id.ll_contract_info);

		getView(R.id.ll_contract_model_info).setOnClickListener(this);
		getView(R.id.rdoBtn_operation).setOnClickListener(this);
		getView(R.id.rdoBtn_status).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mRdoBtnOperation = getView(R.id.rdoBtn_operation);
		mRdoBtnStatus = getView(R.id.rdoBtn_status);
		mViewPager = getView(FRAGMENT_CONTAINER);
		mIndicator = getView(R.id.tab_pager_indicator);
	}

	private void initData() {
		mContractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		if (StringUtils.isNotEmpty(mContractId)) {
			updateDataStatus(DataStatus.LOADING);
			mInvoker = String.valueOf(System.currentTimeMillis());
			mContractLogic.getContractInfo(mInvoker, mContractId);
		} else {
			showToast("合同ID不能为空!");
			finish();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mInvoker = String.valueOf(System.currentTimeMillis());
		mContractLogic.getContractInfo(mInvoker, mContractId);
	}

	private void updateUI() {
		/*if (savedState != null) {
			contractInfoType = ContractInfoType.convert(savedState.getInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB));
			updateTabState();
			Logger.e(TAG, "TabIndex = " + contractInfoType.toValue());
		}*/

		updateBuyInfo();

		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();

		mFragmentOperation = getFragment(TAB_CONTRACT_OPERATION);
		mFragmentStatus = getFragment(TAB_CONTRACT_STATUS);

		if (mFragmentOperation == null) {
			mFragmentOperation = new ContractOprInfoFragment();
			mFragmentOperation.setArguments(createFragmentArgs(R.layout.fragment_contract_opr_info));
		}
		if (mFragmentStatus == null) {
			mFragmentStatus = new ContractStatusInfoFragment();
			mFragmentStatus.setArguments(createFragmentArgs(R.layout.fragment_contract_status_info));
		}

		transaction.hide(mFragmentOperation);
		transaction.hide(mFragmentStatus);

		transaction.commit();

		List<TabInfo> tabs = new ArrayList<TabInfo>();
		tabs.add(new TabInfo(0, getString(R.string.contract_tab_opr), TAB_CONTRACT_OPERATION, ContractOprInfoFragment.class, mFragmentOperation));
		tabs.add(new TabInfo(1, getString(R.string.contract_tab_status), TAB_CONTRACT_STATUS, ContractStatusInfoFragment.class, mFragmentStatus));

		mIndicator.init(contractInfoType.toValue(), tabs, mViewPager);

		mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOffscreenPageLimit(1);
		switchTab();
	}

	private void updateBuyInfo() {
		TextView tvProductSpec = getView(R.id.tv_buy_product_spec);
		tvProductSpec.setText(mContractInfo.productName);

		TextView tvUnitPrice = getView(R.id.tv_buy_unit_price);
		tvUnitPrice.setText(String.valueOf(mContractInfo.unitPrice));

		TextView mTvBuyAmount = getView(R.id.tv_buy_amount);
		mTvBuyAmount.setText(String.valueOf(mContractInfo.tradeAmount));
	}

	private Bundle createFragmentArgs(int layoutId) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID, layoutId);
		args.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mContractInfo);
		return args;
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
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
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_contract_model_info:
			intent = new Intent(this, ContractModelInfoActivity.class);
			intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, mContractId);
			startActivity(intent);
			break;
		case R.id.rdoBtn_operation:
			if (contractInfoType != ContractInfoType.OPERATION) {
				contractInfoType = ContractInfoType.OPERATION;
				switchTab();
			}
			break;
		case R.id.rdoBtn_status:
			if (contractInfoType != ContractInfoType.STATUS) {
				contractInfoType = ContractInfoType.STATUS;
				switchTab();
			}
			break;
		case R.id.iv_common_back:
			if (getParent() instanceof MainActivity) {
				((MainActivity) getParent()).switchTabView(TabStatus.SHOP);
			} else {
				finish();
			}
			break;
		}
	}

	private void switchTab() {
		mViewPager.setCurrentItem(contractInfoType.toValue());
	}

	private void updateTabState() {
		mRdoBtnOperation.setChecked(contractInfoType == ContractInfoType.OPERATION);
		mRdoBtnStatus.setChecked(contractInfoType == ContractInfoType.STATUS);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mIndicator.onScrolled((mViewPager.getWidth() + mViewPager.getPageMargin()) * position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		contractInfoType = ContractInfoType.convert(position);
		updateTabState();
		mIndicator.onSwitched(position);
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB, contractInfoType.toValue());
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
