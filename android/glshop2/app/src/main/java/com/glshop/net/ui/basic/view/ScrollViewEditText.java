package com.glshop.net.ui.basic.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by guoweilong on 2015/8/7.
 * ScrollView里面嵌套EditText EditText可以滑动
 */
public class ScrollViewEditText extends ScrollView {
    public ScrollViewEditText(Context context) {
        super(context);
    }

    public ScrollViewEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollViewEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
