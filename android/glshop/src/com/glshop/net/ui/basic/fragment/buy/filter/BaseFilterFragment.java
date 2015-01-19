package com.glshop.net.ui.basic.fragment.buy.filter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.glshop.net.common.GlobalAction;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 筛选信息BaseFilterFragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public abstract class BaseFilterFragment extends BasicFragment {

	/** Fragment layout ID */
	private int layoutId = 0;

	/** Root Scroll Container */
	protected ScrollView mRootScrollView;

	/** 视图是否已初始化 */
	protected boolean isInited = false;

	/** 筛选信息 */
	protected BuyFilterInfoModelV2 mFilterInfo;

	/** Fragment calllback */
	//protected Callback mCallback;

	protected ISysCfgLogic mSysCfgLogic;
	protected IBuyLogic mBuyLogic;

	public BaseFilterFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(layoutId, container, false);
		//mRootScrollView = getView(R.id.ll_scroll_container);
		initView();
		initData();
		isInited = true;
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		layoutId = bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID);
		mFilterInfo = (BuyFilterInfoModelV2) bundle.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
	}

	protected BuyFilterInfoModelV2 getDefaultInfo() {
		BuyFilterInfoModelV2 info = (BuyFilterInfoModelV2) getArguments().getSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
		if (info == null) {
			info = new BuyFilterInfoModelV2();
		}
		return info;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		//Logger.e(TAG, "Show = " + !hidden);
		if (!hidden) {
			if (mRootScrollView != null) {
				mRootScrollView.scrollTo(0, 0);
			}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		/*if (activity instanceof Callback) {
			mCallback = (Callback) activity;
		}*/
	}

	@Override
	protected void initLogics() {
		mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

	/**
	 * 初始化View
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 重置筛选
	 */
	public abstract void onFilterReset();

	/**
	 * 取消筛选
	 */
	public abstract void onFilterCancel();

	/**
	 * 保存筛选条件
	 */
	public abstract void onFilterSave();

	/**
	 * 界面更新
	 */
	protected abstract void updateFilterUI();

	/**
	 * 参数检验
	 */
	public abstract boolean checkArgs();

	/**
	 * 获取筛选信息
	 * @return
	 */
	public abstract BuyFilterInfoModelV2 getFilterInfo(boolean isUpdate);

}
