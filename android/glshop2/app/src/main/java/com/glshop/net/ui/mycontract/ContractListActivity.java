package com.glshop.net.ui.mycontract;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter;
import com.glshop.net.ui.basic.adapter.base.TabPagerAdapter.TabInfo;
import com.glshop.net.ui.basic.fragment.contract.list.EndedContractListFragment;
import com.glshop.net.ui.basic.fragment.contract.list.OngoingContractListFragment;
import com.glshop.net.ui.basic.fragment.contract.list.UfmContractListFragment;
import com.glshop.net.ui.basic.view.TabIndicator;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 我的合同主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class ContractListActivity extends BasicFragmentActivity implements ViewPager.OnPageChangeListener {

	private static final String TAB_UNCONFIRM = "fragment-tab-unconfirm";
	private static final String TAB_ONGOING = "fragment-tab-ongoing";
	private static final String TAB_ENDED = "fragment-tab-ended";

	private static final int FRAGMENT_CONTAINER = R.id.contract_viewpager;

	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;

	private UfmContractListFragment mFragmentUnconfirmed;
	private OngoingContractListFragment mFragmentOngoing;
	private EndedContractListFragment mFragmentEnded;

	private ContractType contractType = ContractType.UNCONFIRMED;

	private RadioButton mRdoBtnUnconfirmed;
	private RadioButton mRdoBtnOngoing;
	private RadioButton mRdoBtnEnded;

	private TabIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_list);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.my_contract);
		getView(R.id.iv_common_back).setOnClickListener(this);

		getView(R.id.rdoBtn_unconfirmed).setOnClickListener(this);
		getView(R.id.rdoBtn_ongoing).setOnClickListener(this);
		getView(R.id.rdoBtn_ended).setOnClickListener(this);

		mRdoBtnUnconfirmed = getView(R.id.rdoBtn_unconfirmed);
		mRdoBtnOngoing = getView(R.id.rdoBtn_ongoing);
		mRdoBtnEnded = getView(R.id.rdoBtn_ended);
		mViewPager = getView(FRAGMENT_CONTAINER);
		mIndicator = getView(R.id.tab_pager_indicator);
	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			contractType = ContractType.convert(savedState.getInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB));
			updateTabState();
			Logger.e(TAG, "TabIndex = " + contractType.toValue());
		}

		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();

		mFragmentUnconfirmed = getFragment(TAB_UNCONFIRM);
		mFragmentOngoing = getFragment(TAB_ONGOING);
		mFragmentEnded = getFragment(TAB_ENDED);

		if (mFragmentUnconfirmed == null) {
			mFragmentUnconfirmed = new UfmContractListFragment();
		}
		if (mFragmentOngoing == null) {
			mFragmentOngoing = new OngoingContractListFragment();
		}
		if (mFragmentEnded == null) {
			mFragmentEnded = new EndedContractListFragment();
		}

		transaction.hide(mFragmentUnconfirmed);
		transaction.hide(mFragmentOngoing);
		transaction.hide(mFragmentEnded);

		transaction.commitAllowingStateLoss();

		List<TabInfo> tabs = new ArrayList<TabInfo>();
		tabs.add(new TabInfo(0, getString(R.string.my_unconfirmed_contract), TAB_UNCONFIRM, UfmContractListFragment.class, mFragmentUnconfirmed));
		tabs.add(new TabInfo(1, getString(R.string.my_ongoing_contract), TAB_ONGOING, OngoingContractListFragment.class, mFragmentOngoing));
		tabs.add(new TabInfo(2, getString(R.string.my_ended_contract), TAB_ENDED, EndedContractListFragment.class, mFragmentEnded));

		mIndicator.init(contractType.toValue(), tabs, mViewPager);

		mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOffscreenPageLimit(1);
		switchTab();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rdoBtn_unconfirmed:
			if (contractType != ContractType.UNCONFIRMED) {
				contractType = ContractType.UNCONFIRMED;
				switchTab();
			}
			break;

		case R.id.rdoBtn_ongoing:
			if (contractType != ContractType.ONGOING) {
				contractType = ContractType.ONGOING;
				switchTab();
			}
			break;

		case R.id.rdoBtn_ended:
			if (contractType != ContractType.ENDED) {
				contractType = ContractType.ENDED;
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
		mViewPager.setCurrentItem(contractType.toValue());
	}

	private void updateTabState() {
		mRdoBtnUnconfirmed.setChecked(contractType == ContractType.UNCONFIRMED);
		mRdoBtnOngoing.setChecked(contractType == ContractType.ONGOING);
		mRdoBtnEnded.setChecked(contractType == ContractType.ENDED);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.ContractAction.EXTRA_KEY_SELECTED_TAB, contractType.toValue());
	}

	@Override
	protected boolean isAddToStack() {
		return false;
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
		contractType = ContractType.convert(position);
		updateTabState();
		mIndicator.onSwitched(position);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Logger.d(TAG,"onRestart()");
	}
}
