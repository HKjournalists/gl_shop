package com.glshop.net.ui.basic.adapter.buy.filter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;

/**
 * @Description : 带Checkbox的筛选复选菜单菜单适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class FilterAdapterV1 extends BasicAdapter<AreaInfoModel> implements OnClickListener {

	protected View mListView;

	private AreaInfoModel mFocusedInfo;

	private OnItemSelectChangeListener mListener;

	public FilterAdapterV1(Context context, OnItemSelectChangeListener listener, List<AreaInfoModel> list) {
		super(context, list);
		this.mListener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_filter_list_item_v1, null);
		}

		AreaInfoModel model = getItem(position);

		if (mFocusedInfo != null && mFocusedInfo.code.equals(model.code)) {
			ViewHolder.get(convertView, R.id.ll_filter_list_item).setBackgroundResource(R.drawable.bg_list_item_press);
		} else {
			ViewHolder.get(convertView, R.id.ll_filter_list_item).setBackgroundResource(R.drawable.selector_list_item);
		}

		TextView mTvContent = ViewHolder.get(convertView, R.id.tv_item_content);
		mTvContent.setText(model.name);

		CheckedTextView ckbView = ViewHolder.get(convertView, R.id.chkTv_select);
		ckbView.setChecked(model.isSelectedForUI);

		View llCkbView = ViewHolder.get(convertView, R.id.ll_selected_status);
		llCkbView.setTag(position);
		llCkbView.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_selected_status:
			CheckedTextView ckbView = (CheckedTextView) v.findViewById(R.id.chkTv_select);
			ckbView.toggle();
			int position = (Integer) v.getTag();
			AreaInfoModel info = getItem(position);
			info.isSelectedForUI = ckbView.isChecked();
			if (mListener != null) {
				mListener.onItemSelectChanged(mListView, getItem(position), position);
			}
			break;
		}
	}

	public void setListView(View v) {
		mListView = v;
	}

	public void setFocusedInfo(AreaInfoModel info) {
		mFocusedInfo = info;
		notifyDataSetChanged();
	}

	public interface OnItemSelectChangeListener {

		void onItemSelectChanged(View parent, AreaInfoModel info, int position);

	}

}