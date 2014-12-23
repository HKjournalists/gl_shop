package com.glshop.net.ui.mycontract;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.TabContractInfoStatus;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.adapter.TabPagerAdapter;
import com.glshop.net.ui.basic.adapter.TabPagerAdapter.TabInfo;
import com.glshop.net.ui.basic.fragment.contract.ContractDetailFragment;
import com.glshop.net.ui.basic.fragment.contract.ContractOprHistoryFragment;
import com.glshop.net.ui.basic.view.TabIndicator;
import com.glshop.platform.utils.StringUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 合同详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractDetailActivity extends BasicFragmentActivity implements ViewPager.OnPageChangeListener {

	private static final String TAB_MODEL = "fragment-tab-model";
	private static final String TAB_HISTORY = "fragment-tab-history";

	private TabContractInfoStatus tabStatus = TabContractInfoStatus.MODEL;

	private static final int FRAGMENT_CONTAINER = R.id.contract_viewpager;

	private ContractDetailFragment mFragmentModel;
	private ContractOprHistoryFragment mFragmentHistory;

	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;

	private TabIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_info_detail);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);

		mViewPager = getView(FRAGMENT_CONTAINER);
		mIndicator = getView(R.id.tab_pager_indicator);
	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			tabStatus = TabContractInfoStatus.convert(savedState.getInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB));
			Logger.e(TAG, "TabIndex = " + tabStatus.toValue());
		}

		String contractId = getIntent().getStringExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
		if (StringUtils.isNotEmpty(contractId)) {
			final FragmentManager fragmentManager = getSupportFragmentManager();
			final FragmentTransaction transaction = fragmentManager.beginTransaction();

			mFragmentModel = getFragment(TAB_MODEL);
			mFragmentHistory = getFragment(TAB_HISTORY);

			if (mFragmentModel == null) {
				mFragmentModel = new ContractDetailFragment();
				mFragmentModel.setArguments(createFragmentArgs(contractId));
			}
			if (mFragmentHistory == null) {
				mFragmentHistory = new ContractOprHistoryFragment();
				mFragmentHistory.setArguments(createFragmentArgs(contractId));
			}

			transaction.hide(mFragmentModel);
			transaction.hide(mFragmentHistory);

			transaction.commit();

			List<TabInfo> tabs = new ArrayList<TabInfo>();
			tabs.add(new TabInfo(0, getString(R.string.contract_model), TAB_MODEL, ContractDetailFragment.class, mFragmentModel));
			tabs.add(new TabInfo(1, getString(R.string.contract_opr_history), TAB_HISTORY, ContractOprHistoryFragment.class, mFragmentHistory));

			mIndicator.init(tabStatus.toValue(), tabs, mViewPager);

			mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
			mViewPager.setAdapter(mPagerAdapter);
			mViewPager.setOnPageChangeListener(this);
			mViewPager.setOffscreenPageLimit(1);
			switchTab();
		} else {
			showToast("合同ID不能为空!");
			finish();
		}
	}

	private Bundle createFragmentArgs(String contractId) {
		Bundle args = new Bundle();
		args.putString(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, contractId);
		return args;
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void switchTab() {
		mViewPager.setCurrentItem(tabStatus.toValue());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB, tabStatus.toValue());
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
		tabStatus = TabContractInfoStatus.convert(position);
		mIndicator.onSwitched(position);
	}
}
