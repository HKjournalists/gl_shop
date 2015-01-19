package com.glshop.net.ui.basic.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.glshop.net.R;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.logic.syscfg.mgr.SysCfgUtils;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 自定义今日价格列表控件
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-19 上午10:47:07
 */
public class TodayPriceListView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "TodayPriceListView";

	/** 正在加载视图 */
	protected View mLoadingDataView;

	/** 加载完成，显示正常数据视图 */
	protected View mNormalDataView;

	/** 加载完成，显示空数据视图 */
	protected View mEmptyDataView;

	/** 加载失败视图 */
	protected View mLoadErrorView;

	private ViewGroup mContainer;

	/** 页面切换控件 */
	private ViewFlipper mViewFlipper;

	/** 今日报价数据集 */
	private List<TodayPriceModel> mPriceList;

	/** 默认一个Page页面的Item数 */
	private static final int PAGE_SIZE = 3;

	/** 两个Page页面的Item数 */
	private static final int VIEW_SIZE = PAGE_SIZE * 2;

	/** 当前显示item index */
	private int curIndex = 0;

	/** 上一页 */
	private ImageButton mBtnPrevious;

	/** 下一页 */
	private ImageButton mBtnNext;

	/** 两个Page页面的Item数组 */
	private LinearLayout[] viewArray = new LinearLayout[VIEW_SIZE];

	/** 当前是否显示为第一页 */
	private boolean isFirstPage = true;

	/** 是否已加载 */
	private boolean isLoaded = false;

	/** 区域编号 */
	private String mAreaCode = "";

	private Callback mCallback;

	public TodayPriceListView(Context context) {
		this(context, null);
	}

	public TodayPriceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mContainer = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_today_price_list, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(mContainer, lp);

		mNormalDataView = getView(R.id.ll_normal_data);
		mLoadingDataView = getView(R.id.ll_loading_data);
		mLoadErrorView = getView(R.id.ll_load_data_error);
		mEmptyDataView = getView(R.id.ll_empty_data);

		mViewFlipper = getView(R.id.vf_today_price);
		mBtnPrevious = getView(R.id.btn_pre_page);
		mBtnNext = getView(R.id.btn_next_page);

		viewArray[0] = getView(R.id.ll_today_price_feature_1);
		viewArray[1] = getView(R.id.ll_today_price_feature_2);
		viewArray[2] = getView(R.id.ll_today_price_feature_3);
		viewArray[3] = getView(R.id.ll_today_price_feature_4);
		viewArray[4] = getView(R.id.ll_today_price_feature_5);
		viewArray[5] = getView(R.id.ll_today_price_feature_6);

		mEmptyDataView.setOnClickListener(this);
		mLoadErrorView.setOnClickListener(this);
		mBtnPrevious.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);

		for (View v : viewArray) {
			v.setOnClickListener(this);
		}

		//setTypeFace();
		updateDataStatus(DataStatus.LOADING);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_empty_data:
		case R.id.ll_load_data_error:
			if (mCallback != null) {
				mCallback.onReloadData();
			}
			break;

		case R.id.btn_pre_page:
			showPreviousPage();
			break;

		case R.id.btn_next_page:
			showNextPage();
			break;

		case R.id.ll_today_price_feature_1:
		case R.id.ll_today_price_feature_2:
		case R.id.ll_today_price_feature_3:
		case R.id.ll_today_price_feature_4:
		case R.id.ll_today_price_feature_5:
		case R.id.ll_today_price_feature_6:
			if (mCallback != null) {
				mCallback.onItemClick();
			}
			break;
		}
	}

	//	/**
	//	 * 修改字体
	//	 */
	//	private void setTypeFace() {
	//		Typeface face = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/today_price_font.ttf");
	//		((TextView) llPriceFeature1.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature2.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature3.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature4.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature5.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature6.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature7.findViewById(R.id.tv_today_price)).setTypeface(face);
	//		((TextView) llPriceFeature8.findViewById(R.id.tv_today_price)).setTypeface(face);
	//	}

	/**
	 * 设置数据
	 * @param list
	 */
	public void setPriceList(List<TodayPriceModel> list) {
		this.mPriceList = list;
		if (BeanUtils.isNotEmpty(mPriceList)) {
			updatePriceList();
		} else {
			mBtnPrevious.setEnabled(false);
			mBtnNext.setEnabled(false);
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	/**
	 * 更新加载数据视图状态
	 * @param status
	 */
	public void updateDataStatus(DataStatus status) {
		mNormalDataView.setVisibility(status == DataStatus.NORMAL ? View.VISIBLE : View.GONE);
		mLoadingDataView.setVisibility(status == DataStatus.LOADING ? View.VISIBLE : View.GONE);
		mEmptyDataView.setVisibility(status == DataStatus.EMPTY ? View.VISIBLE : View.GONE);
		mLoadErrorView.setVisibility(status == DataStatus.ERROR ? View.VISIBLE : View.GONE);
	}

	/**
	 * 更新列表
	 */
	private void updatePriceList() {
		curIndex = 0;
		isFirstPage = true;
		mBtnPrevious.setEnabled(false);
		if (mPriceList.size() <= PAGE_SIZE) {
			mBtnNext.setEnabled(false);
		} else {
			mBtnNext.setEnabled(true);
		}

		if (mViewFlipper.getDisplayedChild() != 0) {
			mViewFlipper.setDisplayedChild(0);
		}

		int size = mPriceList.size() >= VIEW_SIZE ? VIEW_SIZE : mPriceList.size();
		for (int i = 0; i < size; i++) {
			TodayPriceModel info = mPriceList.get(i);
			updateProductUI(info, viewArray[i]);
		}

		if (mPriceList.size() < VIEW_SIZE) {
			for (int i = mPriceList.size(); i < size; i++) {
				viewArray[i].setVisibility(View.INVISIBLE);
			}
		}

	}

	/**
	 * 显示上一页
	 */
	private void showPreviousPage() {
		isFirstPage = !isFirstPage;
		if (curIndex > 0) {
			curIndex -= PAGE_SIZE;
			curIndex = curIndex < 0 ? 0 : curIndex;
		}
		mBtnPrevious.setEnabled(curIndex > 0);
		mBtnNext.setEnabled(true);
		updateListData();

		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.left_to_right_in));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.left_to_right_out));
		mViewFlipper.showPrevious();
	}

	/**
	 * 显示下一页
	 */
	private void showNextPage() {
		isFirstPage = !isFirstPage;
		if (curIndex + PAGE_SIZE * 2 <= mPriceList.size()) {
			curIndex += PAGE_SIZE;
		} else {
			curIndex = mPriceList.size() - PAGE_SIZE;
		}
		mBtnPrevious.setEnabled(true);
		mBtnNext.setEnabled(curIndex + PAGE_SIZE < mPriceList.size());
		updateListData();

		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.right_to_left_in));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.right_to_left_out));
		mViewFlipper.showNext();
	}

	/**
	 * 更新数据
	 */
	private void updateListData() {
		//Logger.e(TAG, "curIndex = " + curIndex + ", isFirstPage = " + isFirstPage);
		if (isFirstPage) {
			for (int i = 0; i < PAGE_SIZE && curIndex + i < mPriceList.size(); i++) {
				//Logger.w(TAG, "View-" + i + ", CurIndex = " + (curIndex + i));
				TodayPriceModel info = mPriceList.get(curIndex + i);
				updateProductUI(info, viewArray[i]);
			}
		} else {
			for (int i = 0; i < PAGE_SIZE && curIndex + i < mPriceList.size(); i++) {
				//Logger.e(TAG, "View-" + (i + PAGE_SIZE) + ", CurIndex = " + (curIndex + i));
				TodayPriceModel info = mPriceList.get(curIndex + i);
				updateProductUI(info, viewArray[i + PAGE_SIZE]);
			}
		}
	}

	/**
	 * 更新今日报价数据
	 * @param info
	 * @param container
	 */
	private void updateProductUI(TodayPriceModel info, View container) {
		TextView tvPrice = (TextView) container.findViewById(R.id.tv_today_price);
		TextView tvTopCategory = (TextView) container.findViewById(R.id.tv_product_top_category);
		TextView tvSubCategory = (TextView) container.findViewById(R.id.tv_product_sub_category);

		if (StringUtils.isNEmpty(info.todayPrice)) {
			tvPrice.setText(getResources().getString(R.string.price_data_empty));
		} else {
			tvPrice.setText(info.todayPrice);
		}

		if (info.productInfo != null) {
			// 显示大类型
			String topCategory = info.productInfo.mCategoryName;
			if (StringUtils.isNotEmpty(topCategory)) {
				tvTopCategory.setText(topCategory);
				tvSubCategory.setVisibility(View.VISIBLE);
			} else {
				tvTopCategory.setVisibility(View.GONE);
			}

			// 显示子类型
			String subCategory = SysCfgUtils.getSubProductFullName(info.productInfo);
			if (StringUtils.isNotEmpty(subCategory)) {
				tvSubCategory.setText(subCategory);
				tvSubCategory.setVisibility(View.VISIBLE);
			} else {
				tvSubCategory.setVisibility(View.GONE);
			}
			//Logger.e(TAG, "Price = " + info.todayPrice + ", topCategory = " + info.productInfo.mCategoryName + ", subCategory = " + subCategory);
		} else {
			tvTopCategory.setVisibility(View.GONE);
			tvSubCategory.setVisibility(View.GONE);
		}
	}

	public void setIsLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

	public void setAreaCode(String area) {
		this.mAreaCode = area;
	}

	public String getAreaCode() {
		return this.mAreaCode;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

	private GestureDetector mGestureDetector = new GestureDetector(new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Logger.e(TAG, "onFling");
			if (BeanUtils.isNotEmpty(mPriceList)) {
				if (e1.getX() - e2.getX() > 120) {//如果是从右向 左滑动  
					if (mBtnNext.getVisibility() == View.VISIBLE && mBtnNext.isEnabled()) {
						mBtnNext.performClick();
						return true;
					}
				} else if (e1.getX() - e2.getX() < -120) {//如果是从左向 右滑动 
					if (mBtnPrevious.getVisibility() == View.VISIBLE && mBtnPrevious.isEnabled()) {
						mBtnPrevious.performClick();
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
	});

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x" + Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	public interface Callback {

		public void onItemClick();

		public void onReloadData();

	}

}
