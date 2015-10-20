package com.glshop.net.ui.basic.view.dialog.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.glshop.net.R;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.menu.MenuAdapter;

/**
 * @Description : 选择菜单对话框
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public class MenuDialog extends BaseMenuDialog<List<MenuItemInfo>, MenuItemInfo> {

	private ListView mLvMenu;

	public MenuDialog(Context context, List<MenuItemInfo> list, IMenuCallback callback) {
		this(context, list, callback, false, null);
	}

	public MenuDialog(Context context, List<MenuItemInfo> list, IMenuCallback callback, boolean isSelectMode, MenuItemInfo selectedMenu) {
		super(context, list, callback);
		mAdapter = new MenuAdapter(context, list, isSelectMode, selectedMenu);
		mLvMenu.setAdapter(mAdapter);
	}

	@Override
	protected void initView(Context context) {
		mView = View.inflate(context, R.layout.dialog_menu, null);
		mTvTitle = getView(R.id.dialog_tv_title);
		mBtnCancel = getView(R.id.dialog_btn_cancel);
		mLvMenu = getView(R.id.lv_menu_list);
		mLvMenu.setOnItemClickListener(this);
	}

}
