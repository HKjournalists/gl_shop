package com.glshop.net.ui.basic.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.view.dialog.menu.BaseMenuDialog.IMenuCallback;
import com.glshop.net.ui.basic.view.dialog.menu.MenuDialog;
import com.glshop.platform.utils.StringUtils;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 自定义多地域编辑列表控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class AreaEditListView extends LinearLayout implements View.OnClickListener {

	public static final int MAX_AREA_SIZE = 5;

	/**
	 * 区域列表
	 */
	private ViewGroup mContainer;

	/**
	 * 是否编辑模式
	 */
	private boolean isEditMode;

	/**
	 * 港口列表菜单
	 */
	private MenuDialog menuPortList;

	/**
	 * 当前选择港口控件
	 */
	private View mSelectItem;
	private TextView mTvSelectPort;

	private List<MenuItemInfo> mAreaMenu;

	private List<AreaPriceInfoModel> mPriceList;

	//private String mSelectAreaCode;

	public AreaEditListView(Context context) {
		this(context, null);
	}

	public AreaEditListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_area_container, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mContainer, lp);
		initData();
	}

	private void initData() {
		mAreaMenu = SysCfgUtils.getAreaMenu(this.getContext());
	}

	public void setData(List<AreaPriceInfoModel> data) {
		this.mPriceList = data;
		reset();
		if (BeanUtils.isNotEmpty(mPriceList)) {
			for (int i = 0; i < mPriceList.size(); i++) {
				addArea(mPriceList.get(i), i + 1);
			}
		} else {
			addArea(getDefaultAreaPriceInfo(), 1);
		}
	}

	public boolean checkArgs() {
		final int size = mContainer.getChildCount();
		for (int i = 0; i < size; i++) {
			EditText price = (EditText) mContainer.getChildAt(i).findViewById(R.id.et_area_price);
			if (StringUtils.isEmpty(price.getText().toString().trim()) || !StringUtils.checkNumber(price.getText().toString().trim())) {
				return false;
			}
		}
		return true;
	}

	public List<AreaPriceInfoModel> getData(boolean isMoreArea) {
		mPriceList = new ArrayList<AreaPriceInfoModel>();
		final int size = isMoreArea ? mContainer.getChildCount() : 1;
		for (int i = 0; i < size; i++) {
			TextView areaName = ((TextView) mContainer.getChildAt(i).findViewById(R.id.tv_item_area));
			EditText areaPrice = (EditText) mContainer.getChildAt(i).findViewById(R.id.et_area_price);
			AreaPriceInfoModel priceInfo = new AreaPriceInfoModel();
			AreaInfoModel areaInfo = new AreaInfoModel();
			areaInfo.name = areaName.getText().toString();
			areaInfo.code = (String) mContainer.getChildAt(i).findViewById(R.id.ll_item_select_area).getTag();
			priceInfo.areaInfo = areaInfo;
			priceInfo.unitPrice = Float.parseFloat(areaPrice.getText().toString());
			mPriceList.add(priceInfo);
		}
		return mPriceList;
	}

	/**
	 * 添加发布区域
	 */
	public void addArea() {
		addArea(getDefaultAreaPriceInfo(), mContainer.getChildCount() + 1);
	}

	public boolean canAdd() {
		return mContainer.getChildCount() < MAX_AREA_SIZE;
	}

	/**
	 * 添加发布区域
	 */
	public void addArea(AreaPriceInfoModel info, int index) {
		View area = LayoutInflater.from(getContext()).inflate(R.layout.layout_area_edit_item, null);
		area.findViewById(R.id.iv_area_item_delete).setTag(area);
		area.findViewById(R.id.ll_item_select_area).setTag(info.areaInfo.code);
		area.findViewById(R.id.iv_area_item_delete).setOnClickListener(this);
		area.findViewById(R.id.ll_item_select_area).setOnClickListener(this);

		TextView areaTitle = ((TextView) area.findViewById(R.id.tv_item_title));
		TextView areaName = ((TextView) area.findViewById(R.id.tv_item_area));
		EditText areaPrice = ((EditText) area.findViewById(R.id.et_area_price));

		areaTitle.setText("交易地域"/* + index*/);
		if (info != null) {
			if (StringUtils.isNotEmpty(info.areaInfo.name)) {
				areaName.setText(info.areaInfo.name);
			} else {
				areaName.setText(SysCfgUtils.getAreaName(getContext(), info.areaInfo.code));
			}
			areaPrice.setText(String.valueOf(info.unitPrice));
		}

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//lp.height = 2 * getResources().getDimensionPixelSize(R.dimen.buy_item_height);
		mContainer.addView(area, lp);
	}

	/**
	 * 切换编辑模式
	 */
	public void toggleEditMode() {
		isEditMode = !isEditMode;
		final int size = mContainer.getChildCount();
		for (int i = 0; i < size; i++) {
			mContainer.getChildAt(i).findViewById(R.id.rl_edit_item).setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
		}
	}

	/**
	 * 是否是编辑模式
	 * @return
	 */
	public boolean isEditMode() {
		return isEditMode;
	}

	/**
	 * 显示港口列表菜单
	 */
	private void showPortListMenu() {
		closeDialog(menuPortList);
		String areaCode = (String) mSelectItem.getTag();
		menuPortList = new MenuDialog(this.getContext(), mAreaMenu, new IMenuCallback() {

			@Override
			public void onConfirm(int type, Object obj) {

			}

			@Override
			public void onCancel(int type) {

			}

			@Override
			public void onMenuClick(int type, int position, Object obj) {
				mSelectItem.setTag(menuPortList.getAdapter().getItem(position).menuCode);
				mTvSelectPort.setText(menuPortList.getAdapter().getItem(position).menuText);
			}
		}, true, getDefaultAreaMenuItem(areaCode));
		menuPortList.setMenuType(GlobalMessageType.MenuType.SELECT_PORT);
		menuPortList.setTitle(this.getResources().getString(R.string.menu_title_select_port));
		menuPortList.show();
	}

	private void closeDialog(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_area_item_delete:
			if (mContainer.getChildCount() > 1) {
				View view = (View) v.getTag();
				mContainer.removeView(view);
			}
			break;
		case R.id.ll_item_select_area:
			mSelectItem = v;
			mTvSelectPort = (TextView) v.findViewById(R.id.tv_item_area);
			showPortListMenu();
			break;
		}
	}

	private MenuItemInfo getDefaultAreaMenuItem(String areaCode) {
		MenuItemInfo defaultMenu = null;
		if (StringUtils.isNotEmpty(areaCode)) {
			for (MenuItemInfo item : mAreaMenu) {
				if (item.menuCode.equals(areaCode)) {
					defaultMenu = item;
					break;
				}
			}
		}
		if (defaultMenu == null) {
			defaultMenu = mAreaMenu.get(0);
		}
		return defaultMenu;
	}

	private AreaPriceInfoModel getDefaultAreaPriceInfo() {
		MenuItemInfo defaultMenu = getDefaultAreaMenuItem(null);
		AreaPriceInfoModel info = new AreaPriceInfoModel();
		AreaInfoModel areaInfo = new AreaInfoModel();
		areaInfo.name = defaultMenu.menuText;
		areaInfo.code = defaultMenu.menuCode;
		info.areaInfo = areaInfo;
		return info;
	}

	public void reset() {
		mContainer.removeAllViews();
	}

}
