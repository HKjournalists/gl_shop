package com.glshop.net.ui.findbuy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter.DataType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.ISysCfgLogic;
import com.glshop.net.ui.basic.BasicFragmentActivity;
import com.glshop.net.ui.basic.fragment.PriceForecastFragment;
import com.glshop.net.ui.basic.fragment.PriceForecastFragmentEx;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 价格预测页面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PriceForecastActivity extends BasicFragmentActivity {

	private static final String TAG = "PriceForecastActivity";

	private static final String TAB_SAND = "fragment-tab-sand";
	private static final String TAB_STONE = "fragment-tab-stone";

	private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

	private PriceForecastFragmentEx mFragmentSand;
	private PriceForecastFragment mFragmentStone;

	private ProductType productType = ProductType.SAND;

	private RadioButton mRdoBtnSand;
	private RadioButton mRdoBtnStone;

	private MenuDialog menuPortList;
	private TextView mTvItemPort;
	private View mIvPortMenuIcon;

	private String mAreaName = "";
	private Map<String, String> mAreaMap = new HashMap<String, String>();

	private ISysCfgLogic mSysCfgLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_forecast);
		initView();
		initData(savedInstanceState);
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.menu_price_forecast);
		getView(R.id.iv_common_back).setOnClickListener(this);
		getView(R.id.rdoBtn_product_sand).setOnClickListener(this);
		getView(R.id.rdoBtn_product_stone).setOnClickListener(this);
		getView(R.id.ll_port_list).setOnClickListener(this);

		mRdoBtnSand = getView(R.id.rdoBtn_product_sand);
		mRdoBtnStone = getView(R.id.rdoBtn_product_stone);
		mTvItemPort = getView(R.id.tv_port_item);
		mIvPortMenuIcon = getView(R.id.iv_port_dropdown);
	}

	private void initData(Bundle savedState) {
		List<AreaInfoModel> areaList = mSysCfgLogic.getLocalPortList();
		if (BeanUtils.isNotEmpty(areaList)) {
			for (int i = 0; i < areaList.size(); i++) {
				AreaInfoModel info = areaList.get(i);
				mAreaMap.put(info.name, info.code);
			}
			mAreaName = areaList.get(0).name;
			mTvItemPort.setText(mAreaName);
		}

		if (savedState != null) {
			productType = ProductType.convert(savedState.getInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB));
		} else {
			productType = ProductType.convert(getIntent().getIntExtra(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, ProductType.SAND.toValue()));
		}
		Logger.e(TAG, "TabIndex = " + productType.toValue());
		updateTabState();

		mFragmentSand = getFragment(TAB_SAND);
		mFragmentStone = getFragment(TAB_STONE);

		if (mFragmentSand == null) {
			mFragmentSand = new PriceForecastFragmentEx();
			mFragmentSand.setArguments(createFragmentArgs(ProductType.SAND));
			mFragmentSand.setAreaCode(mAreaMap.get(mAreaName));
		}
		if (mFragmentStone == null) {
			mFragmentStone = new PriceForecastFragment();
			mFragmentStone.setArguments(createFragmentArgs(ProductType.STONE));
			mFragmentStone.setAreaCode(mAreaMap.get(mAreaName));
		}

		switchView();
	}

	private Bundle createFragmentArgs(ProductType type) {
		Bundle args = new Bundle();
		args.putInt(GlobalAction.BuyAction.EXTRA_KEY_PRODUCT_TYPE, type.toValue());
		return args;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rdoBtn_product_sand:
			if (productType != ProductType.SAND) {
				productType = ProductType.SAND;
				switchTab(mFragmentSand);
			}
			break;
		case R.id.rdoBtn_product_stone:
			if (productType != ProductType.STONE) {
				productType = ProductType.STONE;
				switchTab(mFragmentStone);
			}
			break;
		case R.id.ll_port_list:
			showPortListMenu();
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void switchTab(PriceForecastFragment fragment) {
		boolean isAdded = fragment.isAdded();
		boolean needRefresh = false;
		if (!isAdded) {
			fragment.setAreaCode(mAreaMap.get(mAreaName));
		} else {
			String areaCode = mAreaMap.get(mAreaName);
			if (StringUtils.isNotEmpty(areaCode) && !areaCode.equals(fragment.getAreaCode())) {
				fragment.setAreaCode(mAreaMap.get(mAreaName));
				needRefresh = true;
			}
		}

		switchView();

		if (needRefresh) {
			fragment.refresh();
		}
	}

	private void switchView() {
		if (productType == ProductType.SAND) {
			switchFragment(FRAGMENT_CONTAINER, mFragmentSand, TAB_SAND, mFragmentStone);
		} else {
			switchFragment(FRAGMENT_CONTAINER, mFragmentStone, TAB_STONE, mFragmentSand);
		}
	}

	private void updateTabState() {
		mRdoBtnSand.setChecked(productType == ProductType.SAND);
		mRdoBtnStone.setChecked(productType == ProductType.STONE);
	}

	private void showPortListMenu() {
		closeDialog(menuPortList);
		Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate180);
		rotateAnimation.setFillAfter(true);
		final Animation reverseAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_reverse180);
		reverseAnimation.setFillAfter(true);
		reverseAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				refreshData();
			}
		});

		mIvPortMenuIcon.startAnimation(rotateAnimation);

		//List<String> menu = Arrays.asList(getResources().getStringArray(R.array.port_type));
		List<MenuItemInfo> menu = MenuUtil.makeMenuAList(mSysCfgLogic.getLocalPortList());
		menuPortList = new MenuDialog(this, menu, new IMenuCallback() {

			@Override
			public void onConfirm(int type, Object obj) {
				mIvPortMenuIcon.startAnimation(reverseAnimation);
			}

			@Override
			public void onCancel(int type) {
				mIvPortMenuIcon.startAnimation(reverseAnimation);
			}

			@Override
			public void onMenuClick(int type, int position, Object obj) {
				mIvPortMenuIcon.startAnimation(reverseAnimation);
				mAreaName = ((MenuItemInfo) obj).menuText;
				mTvItemPort.setText(mAreaName);
				//refreshData();
			}
		}, true, new MenuItemInfo(mTvItemPort.getText().toString()));
		menuPortList.setMenuType(GlobalMessageType.MenuType.SELECT_PORT);
		menuPortList.setTitle(getString(R.string.menu_title_select_port));
		menuPortList.show();
	}

	private void refreshData() {
		PriceForecastFragment fragment = getCurFragment();
		if (!mAreaMap.get(mAreaName).equals(fragment.getAreaCode())) {
			fragment.setAreaCode(mAreaMap.get(mAreaName));
			fragment.refresh();
		}
	}

	private PriceForecastFragment getCurFragment() {
		return productType == ProductType.SAND ? mFragmentSand : mFragmentStone;
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	protected int[] getDataType() {
		return new int[] { DataType.SAND_FORECAST_PRICE_LIST, DataType.STONE_FORECAST_PRICE_LIST };
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(GlobalAction.BuyAction.EXTRA_KEY_SELECTED_TAB, productType.toValue());
	}

	@Override
	protected void initLogics() {
		mSysCfgLogic = LogicFactory.getLogicByClass(ISysCfgLogic.class);
	}

}
