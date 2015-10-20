package com.glshop.net.ui.basic.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 适配器基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午10:01:18
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

	protected Context mContext;

	protected List<T> mLists = new ArrayList<T>();

	public BasicAdapter(Context context, List<T> list) {
		mContext = context;
		addList(list, false);
	}

	@Override
	public int getCount() {
		return mLists.size();
	}

	@Override
	public T getItem(int position) {
		return mLists.get(position);
	}

	public void addList(List<T> list) {
		addList(list, true);
	}

	public void addList(List<T> list, boolean isNotify) {
		if (list != null) {
			if (BeanUtils.isNotEmpty(list)) {
				Logger.d("","mLists="+list.size());
				mLists.addAll(list);
			}
			if (isNotify) {
				notifyDataSetChanged();
			}
		}
	}

	public void setList(List<T> list) {
		setList(list, true);
	}

	public void setList(List<T> list, boolean isNotify) {
		if (list != null) {
			mLists.clear();
			if (BeanUtils.isNotEmpty(list)) {
				addList(list, isNotify);
			}
		}
	}

	public List<T> getList() {
		return mLists;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
