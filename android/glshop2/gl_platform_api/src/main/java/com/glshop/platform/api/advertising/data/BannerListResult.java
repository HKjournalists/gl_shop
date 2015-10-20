package com.glshop.platform.api.advertising.data;

import com.glshop.platform.api.advertising.data.model.BannerModel;
import com.glshop.platform.api.base.CommonResult;

import java.util.List;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : 广告图列表
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/8/24  10:43
 */
public class BannerListResult extends CommonResult {

    public List<BannerModel> datas;

    @Override
    public String toString() {
        return super.toString()+","+datas;
    }
}
