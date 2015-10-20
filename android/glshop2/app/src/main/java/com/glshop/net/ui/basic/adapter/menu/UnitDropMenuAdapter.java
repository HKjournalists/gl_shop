package com.glshop.net.ui.basic.adapter.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;

/**
 * @Description : 单位下拉菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class UnitDropMenuAdapter extends BasicAdapter<String> {

	private String selectedMenu;

	public UnitDropMenuAdapter(Context context, List<String> list, String selectedMenu) {
		super(context, list);
		this.selectedMenu = selectedMenu;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.menu_unit_list_item, null);
		}

		String data = getItem(position);

		TextView mTvMenu = ViewHolder.get(convertView, R.id.tv_menu_item);
		mTvMenu.setText(data);

		View itemBg = ViewHolder.get(convertView, R.id.ll_menu_item);
		if (position == 0) {
			itemBg.setBackgroundResource(data.equals(selectedMenu) ? R.drawable.bg_item_top_press : R.drawable.selector_item_top);
		} else if (position == getCount() - 1) {
			itemBg.setBackgroundResource(data.equals(selectedMenu) ? R.drawable.bg_item_bottom_press : R.drawable.selector_item_bottom);
		} else {
			itemBg.setBackgroundResource(data.equals(selectedMenu) ? R.drawable.bg_item_middle_press : R.drawable.selector_item_middle);
		}
		return convertView;
	}
}