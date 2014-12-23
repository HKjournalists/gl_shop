package com.glshop.net.ui.basic.view.listitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.glshop.net.R;

/**
 * @Description : 买卖页面自定义列表项控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public abstract class BaseItemView extends LinearLayout implements OnClickListener {

	protected LinearLayout llContainer;

	protected OnClickListener mOnClickListener;

	public BaseItemView(Context context) {
		this(context, null);
	}

	public BaseItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initData(attrs);
	}

	protected void setContentView(int layout) {
		View view = LayoutInflater.from(getContext()).inflate(layout, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, lp);
	}

	@Override
	public void setOnClickListener(OnClickListener listener) {
		mOnClickListener = listener;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_list_item:
			if (mOnClickListener != null) {
				mOnClickListener.onClick(this);
			}
			break;
		}
	}

	@Override
	public void setBackgroundColor(int color) {
		llContainer.setBackgroundColor(color);
	}

	@Override
	public void setBackgroundDrawable(Drawable d) {
		llContainer.setBackgroundDrawable(d);
	}

	@Override
	public void setBackgroundResource(int resid) {
		llContainer.setBackgroundResource(resid);
	}

	protected abstract void initView();

	protected abstract void initData(AttributeSet attrs);

	public abstract void setContentText(String text);

	public abstract String getContentText();

}
