package com.glshop.net.ui.basic.adapter.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.glshop.net.R;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;

/**
 * @Description : 选择菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class MenuAdapterV3 extends MenuAdapter {

	public MenuAdapterV3(Context context, List<MenuItemInfo> list, boolean isSelectMode, MenuItemInfo selectedMenu) {
		super(context, list, isSelectMode, selectedMenu);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = super.getView(position, convertView, parent);
		ViewHolder.get(convertView, R.id.ll_menu_item).setBackgroundResource(0);

		return convertView;
	}
}