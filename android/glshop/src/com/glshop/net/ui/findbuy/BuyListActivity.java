package com.glshop.net.ui.findbuy;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.TabStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.buy.IBuyLogic;
import com.glshop.net.logic.syscfg.mgr.SysCfgMgr;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.BuyListFragment;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog;
import com.glshop.net.ui.basic.view.dialog.menu.BuyFilterDialog;
import com.glshop.net.ui.basic.view.dialog.menu.PopupMenu;
import com.glshop.net.ui.basic.view.dialog.menu.PopupMenu.IMenuCallback;
import com.glshop.platform.api.DataConstants.BuyOrderType;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.OrderStatus;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 找买找卖主页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class BuyListActivity extends BasicFragmentActivity implements BaseMenuDialog.IMenuCallback {

	private static final String TAG = "BuyListActivity";

	private static final String TAB_BUYER = "fragment-tab-buyer";
	private static final String TAB_SELLER = "fragment-tab-seller";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private BuyListFragment mFragmentBuyer;
	private BuyListFragment mFragmentSeller;

	private BuyType buyType = BuyType.BUYER;

	private RadioButton mRdoBtnBuyer;
	private RadioButton mRdoBtnSeller;

	private View mDropdownMenu;

	private BuyFilterDialog mFilerMenuDilaog;
	private BuyFilterInfoModel mBuyFilterInfo = new BuyFilterInfoModel();

	private IBuyLogic mBuyLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate()");
		setContentView(R.layout.activity_buy_list);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		((TextView) findViewById(R.id.tv_commmon_title)).setText(R.string.find_buy);

		mRdoBtnBuyer = getView(R.id.rdoBtn_buyer);
		mRdoBtnSeller = getView(R.id.rdoBtn_seller);
		mDropdownMenu = getView(R.id.ll_dropdown_menu);

		Button btnFilter = getView(R.id.btn_commmon_action);
		btnFilter.setVisibility(View.VISIBLE);
		btnFilter.setText(R.string.filter);
		btnFilter.setOnClickListener(this);

		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.btn_pub_message).setOnClickListener(this);
		getView(R.id.ll_dropdown_menu).setOnClickListener(this);
		getView(R.id.ll_dropdown_menu).setOnClickListener(this);
		getView(R.id.rdoBtn_buyer).setOnClickListener(this);
		getView(R.id.rdoBtn_seller).setOnClickListener(this);

	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			buyType = BuyType.convert(savedState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB));
			updateTabState();
			Logger.e(TAG, "TabIndex = " + buyType.toValue());
		}
		Calendar calendar = Calendar.getInstance();
		mBuyFilterInfo.year = String.valueOf(calendar.get(Calendar.YEAR));
		mFragmentBuyer = getFragment(TAB_BUYER);
		mFragmentSeller = getFragment(TAB_SELLER);

		if (mFragmentBuyer == null) {
			mFragmentBuyer = new BuyListFragment();
			mFragmentBuyer.setArguments(createFragmentArgs(BuyType.BUYER));
		}
		if (mFragmentSeller == null) {
			mFragmentSeller = new BuyListFragment();
			mFragmentSeller.setArguments(createFragmentArgs(BuyType.SELLER));
		}

		mFragmentBuyer.setBuyFilterInfo(mBuyFilterInfo);
		mFragmentSeller.setBuyFilterInfo(mBuyFilterInfo);

		switchView();
	}

	private Bundle createFragmentArgs(BuyType type) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_BUY_INFO_TYPE, type.toValue());
		return args;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dropdown_menu:
			showDropdownMenu();
			break;

		case R.id.btn_commmon_action:
			showFilterMenu();
			break;

		case R.id.rdoBtn_buyer:
			if (buyType != BuyType.BUYER) {
				buyType = BuyType.BUYER;
				switchView();
			}
			break;

		case R.id.rdoBtn_seller:
			if (buyType != BuyType.SELLER) {
				buyType = BuyType.SELLER;
				switchView();
			}
			break;

		case R.id.btn_pub_message:
			pubBuyInfo();
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

	private void switchView() {
		if (buyType == BuyType.BUYER) {
			switchFragment(FRAGMENT_CONTAINER, mFragmentBuyer, TAB_BUYER, mFragmentSeller);
		} else {
			switchFragment(FRAGMENT_CONTAINER, mFragmentSeller, TAB_SELLER, mFragmentBuyer);
		}
	}

	private void showDropdownMenu() {
		List<String> menus = Arrays.asList(getResources().getStringArray(R.array.find_buy_filter_type));
		mDropdownMenu.setEnabled(false);
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_up);
		final int index = getDropFilterIndex();
		PopupMenu menu = new PopupMenu(this, menus, menus.get(index), mDropdownMenu.getWidth(), new IMenuCallback() {

			@Override
			public void onMenuItemClick(int position) {
				Logger.e(TAG, "Click Menu position = " + position);
				if (index != position) {
					saveDropFilterStatus(position);
					if (buyType == BuyType.BUYER) {
						mFragmentBuyer.setBuyFilterInfo(mBuyFilterInfo);
						mFragmentBuyer.onReloadData();
					} else {
						mFragmentSeller.setBuyFilterInfo(mBuyFilterInfo);
						mFragmentSeller.onReloadData();
					}
				}
			}

			@Override
			public void onDismiss() {
				Logger.d(TAG, "onDismiss");
				mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_down);
				mDropdownMenu.setEnabled(true);
			}
		});
		menu.showAsDropDown(mDropdownMenu);
	}

	private int getDropFilterIndex() {
		BuyOrderType orderType = mBuyFilterInfo.orderType;
		OrderStatus orderStatus = mBuyFilterInfo.orderStatus;
		int index = 0;
		if (orderType == BuyOrderType.EXPIRY && orderStatus == OrderStatus.DESC) {
			index = 0;
		} else if (orderType == BuyOrderType.PRICE && orderStatus == OrderStatus.DESC) {
			index = 1;
		} else if (orderType == BuyOrderType.PRICE && orderStatus == OrderStatus.ASC) {
			index = 2;
		} else if (orderType == BuyOrderType.CREDIT && orderStatus == OrderStatus.DESC) {
			index = 3;
		} else {
			index = 0;
		}
		return index;
	}

	private void saveDropFilterStatus(int index) {
		switch (index) {
		case 0:
			mBuyFilterInfo.orderType = BuyOrderType.EXPIRY;
			mBuyFilterInfo.orderStatus = OrderStatus.DESC;
			break;
		case 1:
			mBuyFilterInfo.orderType = BuyOrderType.PRICE;
			mBuyFilterInfo.orderStatus = OrderStatus.DESC;
			break;
		case 2:
			mBuyFilterInfo.orderType = BuyOrderType.PRICE;
			mBuyFilterInfo.orderStatus = OrderStatus.ASC;
			break;
		case 3:
			mBuyFilterInfo.orderType = BuyOrderType.CREDIT;
			mBuyFilterInfo.orderStatus = OrderStatus.DESC;
			break;
		}
	}

	private void showFilterMenu() {
		closeDialog(mFilerMenuDilaog);
		List<AreaInfoModel> areaList = SysCfgMgr.getIntance(this).loadAreaList();
		List<ProductCfgInfoModel> productList = SysCfgMgr.getIntance(this).loadProductList();
		BuyFilterDialog filerMenu = new BuyFilterDialog(this, this, areaList, productList, mBuyFilterInfo);
		filerMenu.setMenuType(GlobalMessageType.MenuType.SELECT_PRODUCT_SPEC);
		filerMenu.setTitle(getString(R.string.menu_title_buy_filter));
		filerMenu.show();
	}

	private void pubBuyInfo() {
		if (isLogined()) {
			Intent intent = new Intent(this, PubModeSelectActivity.class);
			startActivity(intent);
		} else {
			showToast(R.string.user_not_login);
		}
	}

	private void updateTabState() {
		mRdoBtnBuyer.setChecked(buyType == BuyType.BUYER);
		mRdoBtnSeller.setChecked(buyType == BuyType.SELLER);
	}

	@Override
	public void onMenuClick(int type, int position, Object obj) {

	}

	@Override
	public void onConfirm(Object obj) {
		if (obj != null && !mBuyFilterInfo.equals((BuyFilterInfoModel) obj)) {
			mBuyFilterInfo = (BuyFilterInfoModel) obj;
			if (buyType == BuyType.BUYER) {
				mFragmentBuyer.setBuyFilterInfo(mBuyFilterInfo);
				mFragmentBuyer.onReloadData();
			} else {
				mFragmentSeller.setBuyFilterInfo(mBuyFilterInfo);
				mFragmentSeller.onReloadData();
			}
		}
	}

	@Override
	public void onCancel() {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, buyType.toValue());
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	protected void initLogics() {
		mBuyLogic = LogicFactory.getLogicByClass(IBuyLogic.class);
	}

}
