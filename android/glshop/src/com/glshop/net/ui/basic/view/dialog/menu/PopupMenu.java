package com.glshop.net.ui.basic.view.dialog.menu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.glshop.net.R;

/**
 * @Description : 通用下拉菜单控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-21 下午6:03:33
 */
public class PopupMenu extends PopupWindow implements OnItemClickListener, PopupWindow.OnDismissListener {

	private Context mContext;

	private List<String> mMenuList;

	private String mSelectedMenu;

	private ListView mLvMenu;

	private int mMenuWidth = 0;

	private IMenuCallback callback;

	/**
	 * @param context
	 */
	public PopupMenu(Context context, List<String> menu, IMenuCallback callback) {
		this(context, menu, null, 0, callback);
	}

	/**
	 * @param context
	 */
	public PopupMenu(Context context, List<String> menu, String selectedMenu, int menuWidth, IMenuCallback callback) {
		super(context);
		this.mContext = context;
		this.mMenuList = menu;
		this.mSelectedMenu = selectedMenu;
		this.mMenuWidth = menuWidth;
		this.callback = callback;

		initView();
		initData();
	}

	private void initView() {
		View contentView = View.inflate(mContext, R.layout.menu_list, null);

		mLvMenu = (ListView) contentView.findViewById(R.id.lv_menu);

		this.setContentView(contentView);
		this.setWidth(/*ViewGroup.LayoutParams.WRAP_CONTENT*/mMenuWidth > 0 ? mMenuWidth : 200);
		this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);

		this.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.transparent));
		//this.setAnimationStyle(R.style.popwin_anim_down_style);
		this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		this.setOutsideTouchable(false);
		this.setOnDismissListener(this);
	}

	private void initData() {
		if (mMenuList != null) {
			mLvMenu.setAdapter(new MenuAdapter(mSelectedMenu));
			mLvMenu.setOnItemClickListener(this);
		}
	}

	public class MenuAdapter extends BaseAdapter {

		private String selectedMenu;

		public MenuAdapter(String selectedMenu) {
			super();
			this.selectedMenu = selectedMenu;
		}

		@Override
		public int getCount() {
			return mMenuList == null ? 0 : mMenuList.size();
		}

		@Override
		public Object getItem(int position) {
			return mMenuList == null ? null : mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.menu_list_item, null);
			}

			String menuText = (String) getItem(position);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
			tv.setText(menuText);

			View itemBg = convertView.findViewById(R.id.ll_menu_item);

			if (position == 0) {
				itemBg.setBackgroundResource(menuText.equals(selectedMenu) ? R.drawable.bg_item_top_press : R.drawable.selector_item_top);
			} else if (position == getCount() - 1) {
				itemBg.setBackgroundResource(menuText.equals(selectedMenu) ? R.drawable.bg_item_bottom_press : R.drawable.selector_item_bottom);
			} else {
				itemBg.setBackgroundResource(menuText.equals(selectedMenu) ? R.drawable.bg_item_middle_press : R.drawable.selector_item_middle);
			}

			return convertView;
		}
	}

	/**
	 * 下拉菜单接口
	 */
	public interface IMenuCallback {

		public void onMenuItemClick(int position);

		public void onDismiss();

	}

	@Override
	public void onDismiss() {
		if (callback != null) {
			callback.onDismiss();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (callback != null) {
			callback.onMenuItemClick(position);
		}
		dismiss();
	}

}
