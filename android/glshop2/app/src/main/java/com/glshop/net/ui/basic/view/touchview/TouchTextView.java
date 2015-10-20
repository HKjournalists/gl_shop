package com.glshop.net.ui.basic.view.touchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @Description : 自定义TextView，同时将touch事件传递到父view
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 上午10:33:49
 */
public class TouchTextView extends TextView {

	public TouchTextView(Context context) {
		super(context);
	}

	public TouchTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		((View) getParent()).onTouchEvent(event);
		return super.onTouchEvent(event);
	}

}
