package com.glshop.net.ui.basic.adapter.menu;

import java.util.List;

import android.content.Context;

import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;

/**
 * @Description : 菜单适配器基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public abstract class BaseMenuAdapter extends BasicAdapter<MenuItemInfo> {

	/**
	 * 是否为选择模式
	 */
	protected boolean mIsSelectMode;

	/**
	 * 当前选择菜单选项
	 */
	protected MenuItemInfo mSelectedMenu;

	public BaseMenuAdapter(Context context, List<MenuItemInfo> list) {
		this(context, list, false, null);
	}

	public BaseMenuAdapter(Context context, List<MenuItemInfo> list, boolean isSelectMode, MenuItemInfo selectedMenu) {
		super(context, list);
		this.mIsSelectMode = isSelectMode;
		if (isSelectMode) {
			this.mSelectedMenu = selectedMenu;
		}
	}

	public void setSelectedMenu(MenuItemInfo menu) {
		this.mSelectedMenu = menu;
		this.notifyDataSetChanged();
	}

	public MenuItemInfo getSelectedMenu() {
		return this.mSelectedMenu;
	}

}