package com.glshop.platform.api.advertising;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.advertising.data.BannerListResult;
import com.glshop.platform.api.advertising.data.model.BannerModel;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType;
import com.glshop.platform.utils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : 首页广告图
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/8/24  09:57
 */
public class IndexBannerReq extends BaseRequest<BannerListResult>{

    /**
     * 请求客户端类型
     */
    public String clientType;

    public IndexBannerReq(Object invoker, IReturnCallback<BannerListResult> callBackx) {
        super(invoker, callBackx);
    }

    @Override
    protected BannerListResult getResultObj() {
        return new BannerListResult();
    }


    @Override
    protected void buildParams() {
        request.addParam("btype",clientType);
        request.setMethod(ResponseDataType.HttpMethod.GET);
    }

    @Override
    protected void parseData(BannerListResult result, ResultItem item) {

      List<ResultItem> resultItems=item.getItems("DATA");
        if (BeanUtils.isNotEmpty(resultItems)){
            List<BannerModel> list=new ArrayList<BannerModel>();
            for (ResultItem resultItem : resultItems) {
                BannerModel model=new BannerModel();
                ImageInfoModel imageInfoModel=new ImageInfoModel();
                imageInfoModel.cloudUrl=resultItem.getString("fullpath");
                model.imageInfoModel=imageInfoModel;
                model.banner_linkurl=resultItem.getString("targeturl");
                list.add(model);
            }
            result.datas=list;
        }


    }

    @Override
    protected String getTypeURL() {
        return "/clientBanner/getBannerList";
    }
}
