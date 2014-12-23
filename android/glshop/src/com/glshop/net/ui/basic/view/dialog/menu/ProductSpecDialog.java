package com.glshop.net.ui.basic.view.dialog.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.glshop.net.R;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.net.ui.basic.adapter.menu.MenuAdapterV2;
import com.glshop.net.ui.basic.adapter.menu.MenuAdapterV3;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;

/**
 * @Description : 货物规格菜单对话框
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class ProductSpecDialog extends BaseMenuDialog<List<ProductCfgInfoModel>, ProductCfgInfoModel> {

	private MenuAdapterV2 mLeftAdapter;
	private MenuAdapterV3 mRightAdapter;

	private ListView mLvLeft;
	private ListView mLvRight;

	private List<MenuItemInfo> mLeftMenu = new ArrayList<MenuItemInfo>();
	private List<MenuItemInfo> mRightMenu = new ArrayList<MenuItemInfo>();

	private Map<MenuItemInfo, List<MenuItemInfo>> mMenuMap;

	private MenuItemInfo mSelectedLeftMenu;
	private MenuItemInfo mDefaultLeftMenu;
	private MenuItemInfo mSelectedRightMenu;

	public ProductSpecDialog(Context context, List<ProductCfgInfoModel> list, IMenuCallback callback) {
		this(context, list, callback, false, null, null);
	}

	public ProductSpecDialog(Context context, List<ProductCfgInfoModel> list, IMenuCallback callback, boolean isSelectMode, MenuItemInfo leftMenu, MenuItemInfo rightMenu) {
		super(context, list, callback);

		mMenuMap = SysCfgUtils.getSandCategoryMenu(list);
		mSelectedLeftMenu = mDefaultLeftMenu = leftMenu;
		mSelectedRightMenu = rightMenu;

		Set<MenuItemInfo> keys = mMenuMap.keySet();
		Iterator<MenuItemInfo> it = keys.iterator();
		while (it.hasNext()) {
			mLeftMenu.add(it.next());
		}
		MenuUtil.sortMenuList(mLeftMenu);

		mRightMenu.addAll(mMenuMap.get(mSelectedLeftMenu));

		mLeftAdapter = new MenuAdapterV2(context, mLeftMenu, true, mSelectedLeftMenu);
		mRightAdapter = new MenuAdapterV3(context, mRightMenu, true, mSelectedRightMenu);

		mLvLeft.setAdapter(mLeftAdapter);
		mLvRight.setAdapter(mRightAdapter);
	}

	@Override
	protected void initView(Context context) {
		mView = View.inflate(context, R.layout.dialog_product_spec_menu, null);
		mTvTitle = getView(R.id.dialog_tv_title);
		mBtnCancel = getView(R.id.dialog_btn_cancel);

		mLvLeft = getView(R.id.lv_menu_list_left);
		mLvRight = getView(R.id.lv_menu_list_right);

		mLvLeft.setOnItemClickListener(this);
		mLvRight.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent == mLvLeft) {
			mSelectedLeftMenu = mLeftMenu.get(position);
			mRightMenu.clear();
			mRightMenu.addAll(mMenuMap.get(mLeftMenu.get(position)));

			mLeftAdapter.setSelectedMenu(mLeftMenu.get(position));
			if (mSelectedLeftMenu.menuText.equals(mDefaultLeftMenu.menuText)) {
				mRightAdapter.setSelectedMenu(mSelectedRightMenu);
			} else {
				mRightAdapter.setSelectedMenu(null);
			}

			mRightAdapter.setList(mRightMenu, true);
			mLvRight.setSelection(0);
		} else if (parent == mLvRight) {
			closeDialog();
			if (callback != null) {
				callback.onMenuClick(mMenuType, position, mRightMenu.get(position).menuID);
			}
		}
	}
}
