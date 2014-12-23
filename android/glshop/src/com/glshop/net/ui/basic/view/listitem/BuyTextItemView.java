package com.glshop.net.ui.basic.view.listitem;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;

/**
 * @Description : 买卖页面自定义列表项控件，用于查看买卖信息属性
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class BuyTextItemView extends BaseItemView {

	private ImageView mIvItemIcon;
	private ImageView mIvItemClickIcon;
	private TextView mTvItemTitle;
	private TextView mTvItemSecondTitle;
	private TextView mTvItemContent;

	public BuyTextItemView(Context context) {
		super(context);
	}

	public BuyTextItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.layout_buy_text_item);

		llContainer = getView(R.id.ll_list_item);
		mIvItemIcon = getView(R.id.iv_item_icon);
		mTvItemTitle = getView(R.id.tv_item_title);
		mTvItemSecondTitle = getView(R.id.tv_item_second_title);
		mTvItemContent = getView(R.id.tv_item_content);
		mIvItemClickIcon = getView(R.id.iv_item_click_icon);

		//llContainer.setOnClickListener(this);
	}

	@Override
	protected void initData(AttributeSet attrs) {
		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BuyTextItemView);

		Drawable itemBg = null;
		Drawable itemIcon = null;
		Drawable itemClickIcon = null;

		CharSequence title = "";
		CharSequence secondTitle = "";
		CharSequence content = "";

		ColorStateList contentTextColor = null;

		boolean isShowItemIcon = false;
		boolean isShowItemClickIcon = false;

		final int N = array.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.BuyTextItemView_itemBg:
				itemBg = array.getDrawable(attr);
				break;
			case R.styleable.BuyTextItemView_itemIcon:
				itemIcon = array.getDrawable(attr);
				break;
			case R.styleable.BuyTextItemView_itemClickIcon:
				itemClickIcon = array.getDrawable(attr);
				break;
			case R.styleable.BuyTextItemView_itemTitle:
				title = array.getText(attr);
				break;
			case R.styleable.BuyTextItemView_itemSecondTitle:
				secondTitle = array.getText(attr);
				break;
			case R.styleable.BuyTextItemView_itemContent:
				content = array.getText(attr);
				break;
			case R.styleable.BuyTextItemView_itemContentColor:
				contentTextColor = array.getColorStateList(attr);
				break;
			case R.styleable.BuyTextItemView_showItemIcon:
				isShowItemIcon = array.getBoolean(attr, false);
				break;
			case R.styleable.BuyTextItemView_showItemClickIcon:
				isShowItemClickIcon = array.getBoolean(attr, false);
				break;
			}
		}

		llContainer.setBackgroundDrawable(itemBg);
		mIvItemIcon.setBackgroundDrawable(itemIcon);
		if (itemClickIcon != null) {
			mIvItemClickIcon.setBackgroundDrawable(itemClickIcon);
		}

		int padding = getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		llContainer.setPadding(padding, 0, padding, 0);

		mTvItemTitle.setText(title);
		mTvItemSecondTitle.setText(secondTitle);
		mTvItemContent.setText(content);

		if (contentTextColor != null) {
			mTvItemContent.setTextColor(contentTextColor);
		}

		mIvItemIcon.setVisibility(isShowItemIcon ? View.VISIBLE : View.GONE);
		mIvItemClickIcon.setVisibility(isShowItemClickIcon ? View.VISIBLE : View.GONE);

		array.recycle();
	}

	@Override
	public void setContentText(String text) {
		mTvItemContent.setText(text);
	}

	@Override
	public String getContentText() {
		return mTvItemContent.getText().toString();
	}

	@Override
	public void setOnClickListener(OnClickListener listener) {
		super.setOnClickListener(listener);
		llContainer.setOnClickListener(this);
	}

	@Override
	public void setClickable(boolean clickable) {
		super.setClickable(clickable);
		llContainer.setClickable(clickable);
	}

	public void setActionIconVisible(boolean visibile) {
		mIvItemClickIcon.setVisibility(visibile ? View.VISIBLE : View.GONE);
	}
}
