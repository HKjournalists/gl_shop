package com.glshop.net.ui.basic.view.cycleview;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @Description : 自定义可循环滚动的PagerAdapter
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class CyclePagerAdapter<T extends View> extends PagerAdapter {

	/**
	 * 显示数据
	 */
	private List<T> mList;

	public CyclePagerAdapter(List<T> list) {
		mList = list;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mList.get(position % mList.size()));
	}

	public Object instantiateItem(View container, int position) {
		View view = mList.get(position % mList.size());
		((ViewPager) container).addView(view, 0);
		return view;
	}
}