package com.glshop.net.ui.findbuy;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
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
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.BuyListFragment;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.BuyOrderType;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.OrderStatus;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
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
	private MenuDialog menuBuyOrder;

	private BuyFilterInfoModelV2 mBuyFilterInfoV2 = new BuyFilterInfoModelV2();

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
		getView(R.id.rdoBtn_buyer).setOnClickListener(this);
		getView(R.id.rdoBtn_seller).setOnClickListener(this);

	}

	private void initData(Bundle savedState) {
		if (savedState != null) {
			buyType = BuyType.convert(savedState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB));
			updateTabState();
			Logger.e(TAG, "TabIndex = " + buyType.toValue());
		}
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

		switchView();
	}

	private Bundle createFragmentArgs(BuyType type) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_BUY_INFO_TYPE, type.toValue());
		args.putSerializable(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mBuyFilterInfoV2);
		return args;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dropdown_menu:
			showBuyOrderMenu();
			break;

		case R.id.btn_commmon_action:
			showFilterMenuV2();
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

	private void showBuyOrderMenu() {
		closeDialog(menuBuyOrder);
		List<MenuItemInfo> menus = MenuUtil.makeMenuList(Arrays.asList(getResources().getStringArray(R.array.find_buy_order_type)));
		mDropdownMenu.setEnabled(false);
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_up);
		menuBuyOrder = new MenuDialog(this, menus, this, true, menus.get(getDropFilterIndex()));
		menuBuyOrder.setMenuType(GlobalMessageType.MenuType.SELECT_BUY_ORDER_TYPE);
		menuBuyOrder.setTitle(getString(R.string.menu_title_select_buy_order));
		menuBuyOrder.show();
	}

	private int getDropFilterIndex() {
		BuyOrderType orderType = mBuyFilterInfoV2.orderType;
		OrderStatus orderStatus = mBuyFilterInfoV2.orderStatus;
		int index = 0;
		if (orderType == BuyOrderType.EXPIRY && orderStatus == OrderStatus.DESC) {
			index = 0;
		} else if (orderType == BuyOrderType.PRICE && orderStatus == OrderStatus.ASC) {
			index = 1;
		} else if (orderType == BuyOrderType.PRICE && orderStatus == OrderStatus.DESC) {
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
			mBuyFilterInfoV2.orderType = BuyOrderType.EXPIRY;
			mBuyFilterInfoV2.orderStatus = OrderStatus.DESC;
			break;
		case 1:
			mBuyFilterInfoV2.orderType = BuyOrderType.PRICE;
			mBuyFilterInfoV2.orderStatus = OrderStatus.ASC;
			break;
		case 2:
			mBuyFilterInfoV2.orderType = BuyOrderType.PRICE;
			mBuyFilterInfoV2.orderStatus = OrderStatus.DESC;
			break;
		case 3:
			mBuyFilterInfoV2.orderType = BuyOrderType.CREDIT;
			mBuyFilterInfoV2.orderStatus = OrderStatus.DESC;
			break;
		}
	}

	private void showFilterMenuV2() {
		Intent intent = new Intent(this, BuyFilterActivity.class);
		intent.putExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO, mBuyFilterInfoV2);
		startActivityForResult(intent, GlobalMessageType.ActivityReqCode.REQ_EDIT_BUY_FILTER_INFO);
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
		if (type == GlobalMessageType.MenuType.SELECT_BUY_ORDER_TYPE) {
			restoreFilterIcon();
			if (position != getDropFilterIndex()) {
				saveDropFilterStatus(position);
				updateBuyList();
			}
		}
	}

	@Override
	public void onConfirm(int type, Object obj) {
		if (type == GlobalMessageType.MenuType.SELECT_BUY_ORDER_TYPE) {
			restoreFilterIcon();
		}
	}

	@Override
	public void onCancel(int type) {
		if (type == GlobalMessageType.MenuType.SELECT_BUY_ORDER_TYPE) {
			restoreFilterIcon();
		}
	}

	private void restoreFilterIcon() {
		mDropdownMenu.findViewById(R.id.iv_common_menu_icon).setBackgroundResource(R.drawable.ic_dropdown_menu_down);
		mDropdownMenu.setEnabled(true);
	}

	private void updateBuyList() {
		mFragmentBuyer.updateBuyFilterInfo(mBuyFilterInfoV2);
		mFragmentSeller.updateBuyFilterInfo(mBuyFilterInfoV2);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.e(TAG, "onActivityResult: reqCode = " + requestCode + ", respCode = " + resultCode);
		switch (requestCode) {
		case GlobalMessageType.ActivityReqCode.REQ_EDIT_BUY_FILTER_INFO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					BuyFilterInfoModelV2 filterInfo = (BuyFilterInfoModelV2) data.getSerializableExtra(GlobalAction.BuyAction.EXTRA_KEY_BUY_FILTER_INFO);
					if (filterInfo != null && !filterInfo.equals(mBuyFilterInfoV2)) {
						mBuyFilterInfoV2 = filterInfo;
						updateBuyList();
					}
				}
			}
			break;
		}
	}

	@Override
	protected boolean isAddToStack() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, buyType.toValue());
	}

}
