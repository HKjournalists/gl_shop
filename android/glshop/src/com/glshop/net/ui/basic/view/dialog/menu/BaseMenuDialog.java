package com.glshop.net.ui.basic.view.dialog.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.menu.BaseMenuAdapter;
import com.glshop.net.ui.basic.view.dialog.BaseDialog;

/**
 * @Description : 选择菜单对话框基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-18 上午10:44:25
 */
public abstract class BaseMenuDialog<T, V> extends BaseDialog implements OnItemClickListener {

	protected TextView mTvTitle;

	protected Button mBtnConfirm;
	protected Button mBtnCancel;

	protected IMenuCallback callback;

	protected BaseMenuAdapter mAdapter;

	protected int mMenuType = GlobalMessageType.MenuType.UNKNOWN;

	public BaseMenuDialog(Context context, T list, IMenuCallback callback) {
		this(context, list, callback, false, null);
	}

	public BaseMenuDialog(Context context, T list, IMenuCallback callback, boolean isSelectMode, V selectedMenu) {
		super(context, R.style.dialog);
		setMenuCallback(callback);
		initView(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		setContentView(mView, lp);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		getWindow().setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画  

		getView(R.id.dialog_btn_confirm).setOnClickListener(this);
		getView(R.id.dialog_btn_cancel).setOnClickListener(this);
		getView(R.id.ll_menu_bg).setOnClickListener(this);
	}

	public void setTitle(String text) {
		mTvTitle.setText(text);
	}

	public void setCancelText(String text) {
		mBtnCancel.setText(text);
	}

	public void setMenuCallback(IMenuCallback callback) {
		this.callback = callback;
		setCallback(callback);
	}

	public void setMenuType(int type) {
		this.mMenuType = type;
	}

	public BasicAdapter<MenuItemInfo> getAdapter() {
		return mAdapter;
	}

	public void setConfirmVisiable(boolean visiable) {
		if (mBtnConfirm != null) {
			mBtnConfirm.setVisibility(visiable ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		closeDialog();
		if (callback != null) {
			callback.onMenuClick(mMenuType, position, mAdapter.getItem(position));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_btn_confirm:
			mBtnConfirm.setClickable(false);
			if (callback != null && checkResult()) {
				callback.onConfirm(getResult());
			}
			closeDialog();
			break;
		case R.id.ll_menu_bg:
		case R.id.dialog_btn_cancel:
			mBtnCancel.setClickable(false);
			if (callback != null) {
				callback.onCancel();
			}
			closeDialog();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (callback != null) {
			callback.onCancel();
		}
	}

	protected boolean checkResult() {
		return true;
	}

	protected Object getResult() {
		return null;
	}

	protected abstract void initView(Context context);

	/**
	 * 菜单回调接口
	 */
	public interface IMenuCallback extends IDialogCallback {

		/**
		 * 点击回调函数
		 * @param type 菜单类型
		 * @param position 选择菜单position
		 * @param obj 回调对象
		 */
		public void onMenuClick(int type, int position, Object obj);

	}

}
