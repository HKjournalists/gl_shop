package com.glshop.net.ui.basic.fragment.buy.pub;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.PubBuyIndicatorType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.DeliveryAddrType;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 发布信息BasePubInfoFragmentV2
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 下午4:34:24
 */
public abstract class BasePubInfoFragmentV2 extends BasicFragment {

	/** Fragment layout ID */
	private int layoutId = 0;

	/** Root Scroll Container */
	protected ScrollView mRootScrollView;

	/** 视图是否已初始化 */
	protected boolean isInited = false;

	/** 买卖信息 */
	protected BuyInfoModel mPubInfo;

	/** 货物规格属性信息 */
	protected ProductInfoModel mProductPropInfo;

	/** 卸货地址信息 */
	protected AddrInfoModel mSelectAddrInfo;

	/** 是否处于修改模式 */
	protected boolean isModifyMode = false;

	/** Fragment calllback */
	protected Callback mCallback;

	protected BuyType mBuyType;
	protected ProductType mProductType;
	protected String mProductCategoryCode;
	protected String mProductSpecId;
	protected String mTradeAreaCode;
	protected String mTradeAreaName;
	protected DeliveryAddrType mDeliveryAddrType;

	protected ISysCfgLogic mSysCfgLogic;
	protected IBuyLogic mBuyLogic;

	public BasePubInfoFragmentV2() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.e(TAG, "onCreateView");
		mRootView = inflater.inflate(layoutId, container, false);
		mRootScrollView = getView(R.id.ll_scroll_container);
		initView();
		initData();
		isInited = true;
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		layoutId = bundle.getInt(GlobalAction.BuyAction.EXTRA_KEY_FRAGMENT_LAYOUT_ID);
		isModifyMode = bundle.getBoolean(GlobalAction.BuyAction.EXTRA_KEY_IS_MODIFY_BUY_INFO, false);
		mPubInfo = (BuyInfoModel) bundle.getSerializable(GlobalAction.BuyAction.EXTRA_KEY_MODIFY_BUY_INFO);
		if (mPubInfo == null) {
			mPubInfo = new BuyInfoModel();
			mPubInfo.companyId = getCompanyId();
		}
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

	public void updateBuyInfo(BuyInfoModel buyInfo) {
		mPubInfo = buyInfo;
		if (isInited) {
			updateBuyUI();
		}
	}

	protected boolean isAddrValide(AddrInfoModel addrInfo) {
		boolean isValide = false;
		if (addrInfo != null) {
			if (!isModifyMode && StringUtils.isNotEmpty(addrInfo.addrId)) {
				isValide = true;
			} else if (isModifyMode && StringUtils.isNotEmpty(addrInfo.areaCode)) {
				isValide = true;
			}
		}
		return isValide;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof Callback) {
			mCallback = (Callback) activity;
		}
	}

	/**
	 * 获取焦点
	 * @param view
	 */
	protected void requestFocus(View view) {
		((EditText) view).requestFocus();
		((EditText) view).requestFocusFromTouch();
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
	 * 界面更新
	 */
	protected abstract void updateBuyUI();

	/**
	 * 获取发布信息
	 * @return
	 */
	public abstract BuyInfoModel getBuyInfo(boolean isUpdate);

	/**
	 * 发布Fragment回调接口
	 */
	public interface Callback {

		/**
		 * 切换发布界面(包括货物信息，交易信息，发布预览)
		 * @param type
		 */
		public void switchPubIndicator(PubBuyIndicatorType type, boolean sequence);

		/**
		 * 切换发布类型
		 * @param type
		 */
		public void switchPubBuyType(BuyType type);

	}

}
