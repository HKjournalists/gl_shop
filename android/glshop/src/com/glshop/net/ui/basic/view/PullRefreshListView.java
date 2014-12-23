
package com.glshop.net.ui.basic.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:43
 */
public class PullRefreshListView extends ListView implements OnScrollListener {

    private final static int RELEASE_To_REFRESH = 0;

    private final static int PULL_To_REFRESH = 1;

    private final static int REFRESHING = 2;

    private final static int DONE = 3;

    private final static int LOADING = 4;

    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 1;

    private LayoutInflater inflater;

    private LinearLayout headView,headLayout;

    private TextView tipsTextview;

    private TextView lastUpdatedTextView;

    private ImageView arrowImageView;

    private ProgressBar progressBar;
    
//    private RotateAnimation animation;
    
//    private Animation animationPrepare;

    private Animation ratateAnimation;
    private Animation reverseRatateAnimation;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored2;
    
    private int headContentWidth;

    private int headContentHeight;

    private int startY,startY2,startHeight2;

    private int firstItemIndex;

    private int state;

    private boolean isBack;

    private OnRefreshListener refreshListener;

    private NewScrollerListener newScrollerListener;

    private boolean isRefreshable;

    /** listview下面的加载更多 */
    private LinearLayout llFootViewLoadmore;
    
    //下拉刷新的时候是否需要显示头部View
    private boolean mShowHeaderView = true;

    public PullRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private View myHead;
    private int headContentHeight2;
    
    
    private void init(Context context) {
    	ratateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate180);
    	reverseRatateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_reverse180);
    	ratateAnimation.setFillAfter(true);
    	reverseRatateAnimation.setFillAfter(true);
    	
        // setCacheColorHint(context.getResources().getColor(R.color.transparent));
        inflater = LayoutInflater.from(context);

        headView = (LinearLayout)inflater.inflate(R.layout.listview_header, null);

        llFootViewLoadmore = (LinearLayout)inflater.inflate(R.layout.listview_footer, null);
        //设置不可点击
        llFootViewLoadmore.setEnabled(false);
        
        llFootViewLoadmore.setVisibility(View.INVISIBLE);
        
        addFooterView(llFootViewLoadmore, null, false);

        arrowImageView = (ImageView)headView.findViewById(R.id.head_arrowImageView);
//        arrowImageView.setMinimumWidth(70);
//        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar)headView.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView)headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView)headView.findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headContentWidth = headView.getMeasuredWidth();

        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();

        Logger.d("size", "width:" + headContentWidth + " height:" + headContentHeight);

        headLayout = new LinearLayout(getContext());
        headLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.addView(headView);
        
        addHeaderView(headLayout, null, false);
        
        state = DONE;
        isRefreshable = false;
        
    }

    /**
     * 可以添加搜索框
     */
    public void addHead(View view){
    	view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    	
    	LinearLayout layout = new LinearLayout(getContext());
    	layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    	layout.addView(view);
    	myHead = layout;
    	
    	measureView(myHead);
        headContentHeight2 = myHead.getMeasuredHeight();
        
        myHead.setPadding(0, -1 * headContentHeight2, 0, 0);
        myHead.invalidate();
        headLayout.addView(myHead);
    }
    
    /**
     * 设置是否需要显示头部的HeaderView 
     * @param showHeaderView
     */
    public void setShowHeaderView(boolean showHeaderView){
    	this.mShowHeaderView = showHeaderView;
    }
    
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisibleItem;
        if (newScrollerListener != null) {
            newScrollerListener.newScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (newScrollerListener != null) {
            newScrollerListener.newScrollChanged(view, scrollState);
        }
    }

    //设置是否可以下滑刷新
    public void setIsRefreshable(boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
    }
    
    int fullUpY = -1;
    
    public boolean onTouchEvent(MotionEvent event) {
        //这里不能直接判断可以刷新，有些手机可以相应两个点击事件，导致界面没有执行up事件
//        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isRefreshable) {
                        break;
                    }
                    
                    if (firstItemIndex == 0 && !isRecored) {
                    	if(myHead == null || myHead.getMeasuredHeight() == headContentHeight2){
                            isRecored = true;
                            //headContentHeight2的含义是为了下拉刷新，需要用户多拖动headContentHeight2的距离,才进入下来刷新
                            startY = (int)event.getY()/2 + headContentHeight2;
                    	}
                    }
                    if(myHead != null && firstItemIndex != 0 && headView.getMeasuredHeight() == 0){
                    	myHead.setPadding(0, -headContentHeight2, 0, 0);
                    }
                    break;
                    
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:

                    if (state != REFRESHING && state != LOADING) {
                        //					if (state == DONE) {
                        //						// 什么都不做
                        //					}
                        if (state == PULL_To_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();

                            //						Log.v(TAG, "由下拉刷新状态，到done状态");
                        }
                        if (state == RELEASE_To_REFRESH) {

                            state = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();

                            //						Log.v(TAG, "由松开刷新状态，到done状态");
                        }
                    }

                    isRecored = false;
                    isRecored2 = false;
                    isBack = false;
                    fullUpY = -1;
                    
                    break;

                case MotionEvent.ACTION_MOVE:
                	if(myHead != null && myHead.getMeasuredHeight() != headContentHeight2){
                        int tempY = (int)event.getY() / 1;
                        if (!isRecored2 && firstItemIndex == 0) {
                        	isRecored2 = true;
                            startY2 = tempY;
                            startHeight2 = myHead.getMeasuredHeight();
                        }
                		if(isRecored2){
                            int padding = tempY - startY2 + startHeight2 -headContentHeight2;
                            if(padding < -headContentHeight2){
                            	padding = -headContentHeight2;
                            }
                            if(padding > 0){
                            	padding = 0;
                            }
                            myHead.setPadding(0, padding, 0, 0);
                		}
                		fullUpY = -1;
                		break;
                	}else if(myHead != null && firstItemIndex == 0 && headView.getMeasuredHeight() == 0){
                		//全部显示状态，如果是往上推，则消失搜索框（解决在数据很短的情况下，无法影藏搜索框）
                        int tempY = (int)event.getY() / 1;
                        if(fullUpY == -1){
                        	fullUpY = tempY;
                        	break;
                        }else if(fullUpY > tempY){
                        	int padding = tempY - fullUpY;
                            if(padding < -headContentHeight2){
                            	padding = -headContentHeight2;
                            }
                            myHead.setPadding(0, padding, 0, 0);
                        	break;
                        }
                	}
                    //将纵坐标除以2，防止下拉的时候，下拉出来的面积增长过快，即手指所点的地方下移时没有跟手指移动一致，速度比手指快 modify by kuangbiao
                    int tempY = (int)event.getY() / 2;

                    //很多情况下回直接走move，不走down事件，因此这里需要做一个是否可以刷新的判断，
                    if (!isRecored && firstItemIndex == 0 && isRefreshable) {
                        //					Log.v(TAG, "在move时候记录下位置");
                        isRecored = true;
                        startY = tempY + headContentHeight2;;
                    }

                    if (state != REFRESHING && isRecored && state != LOADING) {
                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                        // 可以松手去刷新了
                        if (state == RELEASE_To_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();

                                //							Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();

                                //							Log.v(TAG, "由松开刷新状态转变到done状态");
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            //						else {
                            //							// 不用进行特别的操作，只用更新paddingTop的值就行了
                            //						}
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (state == PULL_To_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                state = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();

                                //							Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();

                                //							Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
                            }
                        }

                        // done状态下
                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }
                        
                        if(mShowHeaderView){			
	                        // 更新headView的size
	                        if (state == PULL_To_REFRESH) {
	                            headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);
	                        }
	
	                        // 更新headView的paddingTop
	                        if (state == RELEASE_To_REFRESH) {
	                            headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
	                        }
                        }
                    }
                    break;
            }
//        }

        return super.onTouchEvent(event);
    }

    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
    	if(!mShowHeaderView){   //时间轴模块下拉刷新的时候，不需要显示头部View
    		return;
    	}
    
        switch (state) {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(ratateAnimation);

                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_slip));

                //			Log.v(TAG, "当前状态，松开刷新");
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseRatateAnimation);

                    tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_down));
                } else {
                    tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_down));
                }
                //			Log.v(TAG, "当前状态，下拉刷新");
                break;

            case REFRESHING:
                //刷新中
                showHeaderLoading();

                //			Log.v(TAG, "当前状态,正在刷新...");
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
//                arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.ic_pull_to_refresh);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_down));
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                //			Log.v(TAG, "当前状态，done");
                break;
        }
    }

    public void showHeaderLoading() {
        headView.setPadding(0, 0, 0, 0);

        progressBar.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.setVisibility(View.GONE);
        tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refreshing));
        lastUpdatedTextView.setVisibility(View.VISIBLE);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.refreshListener = onRefreshListener;
        isRefreshable = true;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
    
    /***
     * 刷新成功
     * 有刷新成功的提示
     */
    public void onRefreshSuccess(){
        state = DONE;
        SimpleDateFormat format = new SimpleDateFormat(getContext().getString(
                R.string.activity_display_basic_time_format));
        String date = format.format(new Date());
        lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date);
        //arrowImageView.setImageResource(R.drawable.refresh_cloud_ok);
        arrowImageView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refreshok));
        postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                changeHeaderViewByState();
                
            }
        }, 1000);
        
    }

    public void onRefreshComplete() {
        state = DONE;
        SimpleDateFormat format = new SimpleDateFormat(getContext().getString(
                R.string.activity_display_basic_time_format));
        String date = format.format(new Date());
        lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date);
        changeHeaderViewByState();
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    // 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        SimpleDateFormat format = new SimpleDateFormat(getContext().getString(
                R.string.activity_display_basic_time_format));
        String date = format.format(new Date());
        lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date);
        super.setAdapter(adapter);
    }

    public interface NewScrollerListener {

        public void newScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

        public void newScrollChanged(AbsListView view, int scrollState);

    }

    public void setNewScrollerListener(NewScrollerListener newScrollerListener) {
        this.newScrollerListener = newScrollerListener;
        setOnScrollListener(this);

    }

    /**
     * <全部加载完成>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showLoadFinish() {
        hideFootView();
    }

    /**
     * <加载更多失败>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showLoadFail() {
        showFootView();
        ((TextView)llFootViewLoadmore.findViewById(R.id.tv_footer_view)).setText(R.string.common_loadmore_fail);
        llFootViewLoadmore.findViewById(R.id.pb_footer_view).setVisibility(View.GONE);
    }

    /**
     * <显示加载更多中>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showLoading() {
        showFootView();
        llFootViewLoadmore.setVisibility(View.VISIBLE);
        ((LinearLayout)llFootViewLoadmore.findViewById(R.id.ll_footer_loading)).setVisibility(View.VISIBLE);
        ((TextView)llFootViewLoadmore.findViewById(R.id.tv_footer_view)).setText(R.string.common_loadmore_ing);
        llFootViewLoadmore.findViewById(R.id.pb_footer_view).setVisibility(View.VISIBLE);
    }

    /**
     * <隐藏footview>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void hideFootView() {
//        removeFooterView(llFootViewLoadmore);
    	((LinearLayout)llFootViewLoadmore.findViewById(R.id.ll_footer_loading)).setVisibility(View.GONE);
    }

    public void showFootView() {
//        //显示前先remove原来的
//        if (getAdapter() != null) {
//            removeFooterView(llFootViewLoadmore);
//        }
//        addFooterView(llFootViewLoadmore, null, false);
    	llFootViewLoadmore.setVisibility(View.VISIBLE);
    }
    
}
