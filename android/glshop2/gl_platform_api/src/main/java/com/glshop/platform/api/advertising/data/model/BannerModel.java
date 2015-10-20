package com.glshop.platform.api.advertising.data.model;

import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : glshop2
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/8/24  10:33
 */
public class BannerModel extends ResultItem {

    /**
     * 广告图图片地址
     */


    public ImageInfoModel imageInfoModel;


    /**
     * 广告图跳转地址
     */
    public String banner_linkurl;

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("BannerModel[");
        sb.append("imageInfoModel="+imageInfoModel.cloudUrl);
        sb.append(",banner_linkurl="+banner_linkurl);
        sb.append("]");
        return super.toString();
    }
}
