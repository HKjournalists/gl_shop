package com.glshop.net.ui.basic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.advertising.Ad_GroupPurchase;
import com.glshop.net.ui.browser.BrowseProtocolActivity;
import com.glshop.platform.api.advertising.data.model.BannerModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.http.image.ImageLoaderManager;

import java.util.List;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : glshop2
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/8/24  11:53
 */
public class BannerAdapter extends PagerAdapter {
    /**
     * 显示view
     */
    private List<View> mListView;

    private List<BannerModel> mListModel;
    private Context mContext;

    private   int oldSize=0;
    /**
     * item是否可以点击
     */
    private boolean isClick;

    //Todo (当mList size小于等于3时存在bug，待修复)
    public BannerAdapter(List<View> list,List<BannerModel> data,Context context,boolean itemClick) {
        mListView = list;
        oldSize=mListView.size();
        mListModel=data;
        mContext=context;
        isClick=itemClick;

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
        ((ViewPager) container).removeView(mListView.get(position % (oldSize)));
    }


    public Object instantiateItem(View container, final int position) {
        View view = mListView.get(position %(oldSize));

        if (isClick)
            view.setEnabled(true);
        else
            view.setEnabled(false);

        final BannerModel bannerModel=mListModel.get(position %(oldSize));
        ImageView iv= (ImageView) view.findViewById(R.id.iv_advert_bg);
        ImageInfoModel imageInfoModel=bannerModel.imageInfoModel;

        if (null!=imageInfoModel) {
            String imgUrl = imageInfoModel.cloudUrl;
            ImageLoaderManager.getIntance().display(this, imgUrl, iv, R.drawable.banner_fail, R.drawable.banner_fail);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("app".equals(bannerModel.banner_linkurl)) {
                    //点击跳转活动页面
                    Intent intent = new Intent(mContext, Ad_GroupPurchase.class);
                    mContext.startActivity(intent);
                } else if ("NO".equals(bannerModel.banner_linkurl)) {

                } else {
                    //点击跳转微信url
                    Intent intent_BrowseProtocolActivity = new Intent(mContext, BrowseProtocolActivity.class);
                    String link_url = bannerModel.banner_linkurl;
                    intent_BrowseProtocolActivity.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE, mContext.getResources().getString(R.string.app_name));
                    intent_BrowseProtocolActivity.putExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL, link_url);
                    mContext.startActivity(intent_BrowseProtocolActivity);
                }
            }
        });
        ((ViewPager) container).addView(view, 0);
        return view;
    }
}
