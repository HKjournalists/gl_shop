package com.glshop.net.ui.basic.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.platform.utils.StringUtils;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 自定义多地域显示列表控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class AreaListView extends LinearLayout {

	/**
	 * 区域列表
	 */
	private ViewGroup mContainer;

	/**
	 * 多地域数据
	 */
	private List<AreaPriceInfoModel> mAreaList;

	public AreaListView(Context context) {
		this(context, null);
	}

	public AreaListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_area_container, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mContainer, lp);
	}

	/**
	 * 设置多地域数据
	 * @param data
	 */
	public void setData(List<AreaPriceInfoModel> data) {
		mAreaList = data;
		if (BeanUtils.isNotEmpty(mAreaList)) {
			int size = mAreaList.size();
			for (int i = 0; i < size; i++) {
				addArea(i + 1, mAreaList.get(i), i == size - 1);
			}
		}
	}

	/**
	 * 添加发布区域
	 */
	private void addArea(int index, AreaPriceInfoModel info, boolean isLast) {
		View area = LayoutInflater.from(getContext()).inflate(R.layout.layout_area_item, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//lp.height = 2 * getResources().getDimensionPixelSize(R.dimen.buy_item_height);

		TextView areaTitle = (TextView) area.findViewById(R.id.tv_item_area_title);
		areaTitle.setText(String.format(getResources().getString(R.string.business_sell_area_title), index));

		TextView areaName = (TextView) area.findViewById(R.id.tv_item_area_name);
		if (StringUtils.isNotEmpty(info.areaInfo.name)) {
			areaName.setText(info.areaInfo.name);
		} else {
			areaName.setText(SysCfgUtils.getAreaName(getContext(), info.areaInfo.code));
		}

		TextView areaPrice = (TextView) area.findViewById(R.id.tv_item_content_price);
		areaPrice.setText(String.valueOf(info.unitPrice));

		if (isLast) {
			area.findViewById(R.id.ll_item_middle).setVisibility(View.GONE);
			area.findViewById(R.id.ll_item_bottom).setVisibility(View.VISIBLE);
		}

		mContainer.addView(area, lp);
	}

}
