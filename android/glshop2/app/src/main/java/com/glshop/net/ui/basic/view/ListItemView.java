package com.glshop.net.ui.basic.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;

/**
 * @Description : 自定义列表项控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class ListItemView extends LinearLayout implements OnClickListener {

	private LinearLayout llContent;
	private ImageView mIvItemIcon;
	private TextView mTvItemTitle;
	private TextView mTvItemSecondTitle;
	private TextView mTvItemContent;

	private OnClickListener mOnClickListener;

	public ListItemView(Context context) {
		this(context, null);
	}

	public ListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		initData(attrs);
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(view, lp);

		llContent = getView(R.id.ll_list_item);
		mIvItemIcon = getView(R.id.iv_item_icon);
		mTvItemTitle = getView(R.id.tv_item_title);
		mTvItemSecondTitle = getView(R.id.tv_item_second_title);
		mTvItemContent = getView(R.id.tv_item_content);

		llContent.setOnClickListener(this);
	}

	private void initData(AttributeSet attrs) {
		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ListItemView);

		Drawable itemBg = null;
		Drawable itemIcon = null;

		CharSequence title = "";
		CharSequence secondTitle = "";
		CharSequence content = "";

		ColorStateList contentTextColor = null;

		final int N = array.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.ListItemView_itemBgType:
				int itemType = array.getInt(R.styleable.ListItemView_itemBgType, 0);
				switch (itemType) {
				case 0:
					itemBg = getResources().getDrawable(R.drawable.selector_item_single);
					break;
				case 1:
					itemBg = getResources().getDrawable(R.drawable.selector_item_top);
					break;
				case 2:
					itemBg = getResources().getDrawable(R.drawable.selector_item_middle);
					break;
				case 3:
					itemBg = getResources().getDrawable(R.drawable.selector_item_bottom);
					break;
				}
				break;
			case R.styleable.ListItemView_itemIcon:
				itemIcon = array.getDrawable(attr);
				break;
			case R.styleable.ListItemView_itemTitle:
				title = array.getText(attr);
				break;
			case R.styleable.ListItemView_itemSecondTitle:
				secondTitle = array.getText(attr);
				break;
			case R.styleable.ListItemView_itemContent:
				content = array.getText(attr);
				break;
			case R.styleable.ListItemView_itemContentColor:
				contentTextColor = array.getColorStateList(attr);
				break;
			}
		}

		llContent.setBackgroundDrawable(itemBg);
		mIvItemIcon.setBackgroundDrawable(itemIcon);
		mTvItemTitle.setText(title);
		mTvItemSecondTitle.setText(secondTitle);
		mTvItemContent.setText(content);

		if (contentTextColor != null) {
			mTvItemContent.setTextColor(contentTextColor);
		}

		array.recycle();
	}

	@Override
	public void setOnClickListener(OnClickListener listener) {
		mOnClickListener = listener;
	}

	public void setTitle(String text) {
		mTvItemTitle.setText(text);
	}

	public void setSecondTitle(String text) {
		mTvItemSecondTitle.setText(text);
	}

	public void setContent(String content) {
		mTvItemContent.setText(content);
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

}
