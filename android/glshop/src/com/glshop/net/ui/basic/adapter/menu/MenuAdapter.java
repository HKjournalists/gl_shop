package com.glshop.net.ui.basic.adapter.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;

/**
 * @Description : 通用单列选择菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class MenuAdapter extends BaseMenuAdapter {

	public MenuAdapter(Context context, List<MenuItemInfo> list, boolean isSelectMode, MenuItemInfo selectedMenu) {
		super(context, list, isSelectMode, selectedMenu);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.menu_select_list_item, null);
		}

		MenuItemInfo menu = getItem(position);

		TextView tvMenuText = ViewHolder.get(convertView, R.id.tv_menu_text);
		tvMenuText.setText(menu.menuText);
		ImageView ivMenuStatus = ViewHolder.get(convertView, R.id.iv_menu_select_status);

		if (mIsSelectMode && mSelectedMenu != null && menu.menuText.equals(mSelectedMenu.menuText)) {
			tvMenuText.setTextColor(mContext.getResources().getColor(R.color.orange));
			ivMenuStatus.setVisibility(View.VISIBLE);
		} else {
			tvMenuText.setTextColor(mContext.getResources().getColor(R.color.blue));
			ivMenuStatus.setVisibility(View.GONE);
		}

		if (position == getCount() - 1) {
			ViewHolder.get(convertView, R.id.ll_menu_item).setBackgroundResource(R.drawable.selector_item_bottom);
		} else {
			ViewHolder.get(convertView, R.id.ll_menu_item).setBackgroundResource(R.drawable.selector_item_middle);
		}

		return convertView;
	}
}