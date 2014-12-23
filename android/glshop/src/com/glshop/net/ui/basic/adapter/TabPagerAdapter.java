package com.glshop.net.ui.basic.adapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description : 通用ViewPager+Fragment模型PagerAdapter基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-29 下午5:01:00
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

	private FragmentManager mFragmentManager = null;
	private FragmentTransaction mCurTransaction = null;
	private Fragment mCurrentPrimaryItem;

	private List<TabInfo> tabs = new ArrayList<TabInfo>();

	public TabPagerAdapter(FragmentManager fm, List<TabInfo> tabs) {
		super(fm);
		this.mFragmentManager = fm;
		this.tabs.addAll(tabs);
	}

	@Override
	public Fragment getItem(int position) {
		return tabs.size() > 0 ? tabs.get(position).fragment : null;
	}

	@Override
	public int getCount() {
		return tabs.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}
		mCurTransaction.hide((Fragment) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		// Do we already have this fragment?
		String name = makeFragmentName(position);
		Fragment fragment = mFragmentManager.findFragmentByTag(name);
		if (fragment != null) {
			mCurTransaction.attach(fragment);
		} else {
			fragment = getItem(position);
			mCurTransaction.add(container.getId(), fragment, makeFragmentName(position));
		}
		if (fragment != mCurrentPrimaryItem) {
			fragment.setMenuVisibility(false);
			fragment.setUserVisibleHint(false);
		}

		mCurTransaction.show(fragment);
		return fragment;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		if (mCurTransaction != null) {
			mCurTransaction.commitAllowingStateLoss();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	public void setPrimaryItem(View container, int position, Object object) {
		Fragment fragment = (Fragment) object;
		if (mCurrentPrimaryItem != fragment) {
			if (mCurrentPrimaryItem != null) {
				mCurrentPrimaryItem.setUserVisibleHint(false);
			}
			if (fragment != null) {
				fragment.setUserVisibleHint(true);
			}
			mCurrentPrimaryItem = fragment;
		}
	}

	private String makeFragmentName(int position) {
		return tabs.get(position).tag;
	}

	/**
	 * Tab选项卡
	 */
	public static class TabInfo implements Parcelable {

		private int id;
		private int icon;
		private String tag;
		private String name = null;
		public boolean hasTips = false;
		public Fragment fragment = null;
		public boolean notifyChange = false;
		@SuppressWarnings("rawtypes")
		public Class fragmentClass = null;

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, Class clazz) {
			this(id, name, 0, clazz);
		}

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, String tag, Class clazz, Fragment fragment) {
			super();

			this.name = name;
			this.id = id;
			this.tag = tag;
			this.icon = 0;
			fragmentClass = clazz;
			this.fragment = fragment;
		}

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, boolean hasTips, Class clazz) {
			this(id, name, 0, clazz);
			this.hasTips = hasTips;
		}

		@SuppressWarnings("rawtypes")
		public TabInfo(int id, String name, int iconid, Class clazz) {
			super();

			this.name = name;
			this.id = id;
			icon = iconid;
			fragmentClass = clazz;
		}

		public TabInfo(Parcel p) {
			this.id = p.readInt();
			this.tag = p.readString();
			this.name = p.readString();
			this.icon = p.readInt();
			this.notifyChange = p.readInt() == 1;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setIcon(int iconid) {
			icon = iconid;
		}

		public int getIcon() {
			return icon;
		}

		@SuppressWarnings({ "rawtypes" })
		public Fragment createFragment() {
			if (fragment == null) {
				Constructor constructor;
				try {
					constructor = fragmentClass.getConstructor(new Class[0]);
					fragment = (Fragment) constructor.newInstance(new Object[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return fragment;
		}

		public final Parcelable.Creator<TabInfo> CREATOR = new Parcelable.Creator<TabInfo>() {
			public TabInfo createFromParcel(Parcel p) {
				return new TabInfo(p);
			}

			public TabInfo[] newArray(int size) {
				return new TabInfo[size];
			}
		};

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel p, int flags) {
			p.writeInt(id);
			p.writeString(tag);
			p.writeString(name);
			p.writeInt(icon);
			p.writeInt(notifyChange ? 1 : 0);
		}
	}

};