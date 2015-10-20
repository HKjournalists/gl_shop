package com.glshop.net.ui.basic.view.cycleview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.advertising.Ad_GroupPurchase;
import com.glshop.net.ui.browser.BrowseProtocolActivity;

import java.util.List;

/**
 * @author : 叶跃丰
 * @version : 1.0
 *          Create Date  : 2014-8-19 上午10:47:07
 * @Description : 自定义可循环滚动的PagerAdapter
 * @Copyright : GL. All Rights Reserved
 * @Company : 深圳市国立数码动画有限公司
 */
public class CyclePagerAdapter<T extends View> extends PagerAdapter {

    /**
     * 显示数据
     */
    private List<T> mList;

    private Context mContext;

    private int oldSize = 0;
    /**
     * item是否可以点击
     */
    private boolean isClick;


    private boolean isCycleMoveEnabled = true;

    //Todo (当mList size小于等于3时存在bug，待修复)
    public CyclePagerAdapter(List<T> list, Context context, boolean itemClick) {
        mList = list;
        oldSize = mList.size();
        mContext = context;
        isClick = itemClick;
    }
    public CyclePagerAdapter(List<T> list, Context context, boolean itemClick,boolean moved) {
        mList = list;
        oldSize = mList.size();
        mContext = context;
        isClick = itemClick;
        isCycleMoveEnabled=moved;
    }
    @Override
    public int getCount() {
        if (isCycleMoveEnabled)
            return Integer.MAX_VALUE;
        else
            return mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        if (isCycleMoveEnabled)
            ((ViewPager) container).removeView(mList.get(position % (oldSize)));
        else
            ((ViewPager) container).removeView(mList.get(position));
    }


    public Object instantiateItem(View container, final int position) {
        View view = null;
        if (isCycleMoveEnabled) {
            view = mList.get(position % (oldSize));
        } else {
            view = mList.get(position);
        }

        if (isClick)
            view.setEnabled(true);
        else
            view.setEnabled(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adverst_position = position % mList.size();
                switch (adverst_position) {
                    case 0:
                    case 2:
                        //点击跳转活动页面
                        Intent intent = new Intent(mContext, Ad_GroupPurchase.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                    case 3:
                        //点击跳转微信url
                        Intent intent_BrowseProtocolActivity = new Intent(mContext, BrowseProtocolActivity.class);
                        final String weixin_url = "http://mp.weixin.qq.com/s?__biz=MzAxNzQxOTQ2OQ==&mid=218283804&idx=1&sn=79aa7b2176ae1f99f1e052173a3f86dd#rd";
                        intent_BrowseProtocolActivity.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, mContext.getResources().getString(R.string.app_name));
                        intent_BrowseProtocolActivity.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, weixin_url);
                        mContext.startActivity(intent_BrowseProtocolActivity);
                        break;
                }
            }
        });

        ((ViewPager) container).addView(view, 0);
        return view;
    }
    public boolean isCycleMoveEnabled() {
        return isCycleMoveEnabled;
    }

    public void setIsCycleMoveEnabled(boolean isCycleMoveEnabled) {
        this.isCycleMoveEnabled = isCycleMoveEnabled;
    }

}