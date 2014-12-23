package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @Description : 主页广告列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-29 下午5:01:00
 */
public class AdvertPageAdapter extends PagerAdapter {

	private List<View> mList;

	public AdvertPageAdapter(List<View> list) {
		mList = list;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	public View getItem(int position) {
		if (mList != null && position >= 0 && position < mList.size()) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		((ViewPager) collection).addView(getItem(position));
		return getItem(position);
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}