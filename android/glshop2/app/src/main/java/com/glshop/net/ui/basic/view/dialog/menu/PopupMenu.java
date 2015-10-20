package com.glshop.net.ui.basic.view.dialog.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.glshop.net.R;

/**
 * @Description : 通用下拉菜单控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-21 下午6:03:33
 */
public class PopupMenu extends PopupWindow implements OnItemClickListener, OnDismissListener {

	private Context mContext;

	private List<String> mMenuList;
	private BaseAdapter mAdapter;

	private ListView mLvMenu;

	private int mMenuWidth = 0;

	private IMenuCallback callback;

	/**
	 * @param context
	 */
	public PopupMenu(Context context, List<String> menu, BaseAdapter adapter, IMenuCallback callback) {
		this(context, menu, 0, adapter, callback);
	}

	/**
	 * @param context
	 */
	public PopupMenu(Context context, List<String> menu, int menuWidth, BaseAdapter adapter, IMenuCallback callback) {
		super(context);
		this.mContext = context;
		this.mMenuList = menu;
		this.mMenuWidth = menuWidth;
		this.mAdapter = adapter;
		this.callback = callback;
		this.initView();
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
			mLvMenu.setAdapter(mAdapter);
			mLvMenu.setOnItemClickListener(this);
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
