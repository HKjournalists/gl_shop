package com.glshop.net.ui.basic.view.listitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 买卖页面自定义列表项控件，用于输入买卖信息属性
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class BuyEditItemView extends BaseItemView {

	private ImageView mIvItemIcon;
	private TextView mTvItemTitle;
	private TextView mTvItemSecondTitle;
	private EditText mEtItemContent;

	public BuyEditItemView(Context context) {
		super(context);
	}

	public BuyEditItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.layout_buy_edit_item);

		llContainer = getView(R.id.ll_list_item);
		mIvItemIcon = getView(R.id.iv_item_icon);
		mTvItemTitle = getView(R.id.tv_item_title);
		mTvItemSecondTitle = getView(R.id.tv_item_second_title);
		mEtItemContent = getView(R.id.et_item_content);
	}

	@Override
	protected void initData(AttributeSet attrs) {
		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BuyEditItemView);

		Drawable itemBg = null;
		Drawable itemIcon = null;

		CharSequence title = "";
		CharSequence secondTitle = "";
		CharSequence hintText = "";

		int contentWidth = 0;

		boolean isShowItemIcon = false;

		int inputType = EditorInfo.TYPE_NULL;

		final int N = array.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.BuyEditItemView_itemBg:
				itemBg = array.getDrawable(attr);
				break;
			case R.styleable.BuyEditItemView_itemIcon:
				itemIcon = array.getDrawable(attr);
				break;
			case R.styleable.BuyEditItemView_itemTitle:
				title = array.getText(attr);
				break;
			case R.styleable.BuyEditItemView_itemSecondTitle:
				secondTitle = array.getText(attr);
				break;
			case R.styleable.BuyEditItemView_itemEditHintText:
				hintText = array.getText(attr);
				break;
			case R.styleable.BuyEditItemView_itemEditWidth:
				contentWidth = array.getDimensionPixelSize(attr, -1);
				break;
			case R.styleable.BuyEditItemView_showItemIcon:
				isShowItemIcon = array.getBoolean(attr, false);
				break;
			case R.styleable.BuyEditItemView_itemInputType:
				inputType = array.getInt(attr, EditorInfo.TYPE_NULL);
				break;
			}
		}

		llContainer.setBackgroundDrawable(itemBg);
		mIvItemIcon.setBackgroundDrawable(itemIcon);

		int padding = getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		llContainer.setPadding(padding, 0, padding, 0);

		mTvItemTitle.setText(title);
		mTvItemSecondTitle.setText(secondTitle);

		if (hintText != null && StringUtils.isNotEmpty(hintText.toString())) {
			mEtItemContent.setHint(hintText);
		}

		if (contentWidth > 0) {
			mEtItemContent.setWidth(contentWidth);
		}

		mIvItemIcon.setVisibility(isShowItemIcon ? View.VISIBLE : View.GONE);

		if (inputType != EditorInfo.TYPE_NULL) {
			setEditInputType(inputType);
		}

		array.recycle();
	}

	@Override
	public void setContentText(String text) {
		mEtItemContent.setText(text);
	}

	@Override
	public String getContentText() {
		return mEtItemContent.getText().toString();
	}

	public EditText getEditText() {
		return mEtItemContent;
	}

	public void setInputType(int type) {
		mEtItemContent.setInputType(type);
	}

	private void setEditInputType(int type) {
		int inputType = InputType.TYPE_NULL;
		switch (type) {
		case 0:
			inputType = InputType.TYPE_NULL;
			break;
		case 1:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
			break;
		case 2:
			inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
			break;
		case 3:
			inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS;
			break;
		case 4:
			inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
			break;
		case 5:
			inputType = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
			break;
		case 6:
			inputType = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
			break;
		case 7:
			inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE;
			break;
		case 8:
			inputType = InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE;
			break;
		case 9:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
			break;
		case 10:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
			break;
		case 11:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
			break;
		case 12:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT;
			break;
		case 13:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE;
			break;
		case 14:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE;
			break;
		case 15:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
			break;
		case 16:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS;
			break;
		case 17:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
			break;
		case 18:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
			break;
		case 19:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT;
			break;
		case 20:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER;
			break;
		case 21:
			inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC;
			break;
		//case 22:
		//	inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
		//	break;
		//case 23:
		//	inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
		//	break;
		case 24:
			inputType = InputType.TYPE_CLASS_NUMBER;
			break;
		case 25:
			inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
			break;
		case 26:
			inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
			break;
		//case 27:
		//	inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
		//	break;
		case 28:
			inputType = InputType.TYPE_CLASS_PHONE;
			break;
		case 29:
			inputType = InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL;
			break;
		case 30:
			inputType = InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE;
			break;
		case 31:
			inputType = InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME;
			break;
		}
		setInputType(inputType);
	}

}
