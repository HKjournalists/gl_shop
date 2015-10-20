package com.glshop.net.ui.basic.adapter.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 适配器基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午10:01:18
 */
public abstract class BasicExpandListAdapter<K, V> extends BaseExpandableListAdapter {

	protected Context mContext;

	protected List<K> mGroupList = new ArrayList<K>();

	protected Map<K, ArrayList<V>> mChildMap = new HashMap<K, ArrayList<V>>();

	public BasicExpandListAdapter(Context context, List<K> group, Map<K, ArrayList<V>> data) {
		mContext = context;
		setGroupData(group, data, false);
	}

	public void setGroupData(List<K> group, Map<K, ArrayList<V>> data, boolean isNotify) {
		if (BeanUtils.isNotEmpty(group) && BeanUtils.isNotEmpty(data)) {
			mGroupList.clear();
			mChildMap.clear();
			mGroupList.addAll(group);
			mChildMap.putAll(data);
			if (isNotify) {
				notifyDataSetChanged();
			}
		}
	}

	public List<K> getGroupList() {
		return mGroupList;
	}

	public Map<K, ArrayList<V>> getChildMap() {
		return mChildMap;
	}

	@Override
	public int getGroupCount() {
		return mGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		K group = getGroupItem(groupPosition);
		if (group != null) {
			ArrayList<V> childList = mChildMap.get(group);
			if (BeanUtils.isNotEmpty(childList)) {
				return childList.size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public K getGroupItem(int groupPosition) {
		if (BeanUtils.isNotEmpty(mGroupList) && groupPosition >= 0 && groupPosition <= mGroupList.size() - 1) {
			return mGroupList.get(groupPosition);
		} else {
			return null;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (BeanUtils.isNotEmpty(mGroupList) && groupPosition >= 0 && groupPosition <= mGroupList.size() - 1) {
			K groupObj = mGroupList.get(groupPosition);
			return mChildMap.get(groupObj);
		} else {
			return null;
		}
	}

	@Override
	public V getChild(int groupPosition, int childPosition) {
		K groupIndexObj = mGroupList.get(groupPosition);
		ArrayList<V> groupList = mChildMap.get(groupIndexObj);
		return groupList != null ? groupList.get(childPosition) : null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
