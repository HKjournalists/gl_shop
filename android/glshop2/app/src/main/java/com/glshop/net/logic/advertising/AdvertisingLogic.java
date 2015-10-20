package com.glshop.net.logic.advertising;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.advertising.IndexBannerReq;
import com.glshop.platform.api.advertising.JoinGroupBuyReq;
import com.glshop.platform.api.advertising.data.BannerListResult;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ProtocolType;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * Created by guoweilong on 2015/8/7.
 * 广告活动图业务实现
 */
public class AdvertisingLogic extends BasicLogic implements IAdvertisingLogic {
    public AdvertisingLogic(Context context) {
        super(context);
    }

    @Override
    public void submitInfo(String username, String userPhone, int number, String remark) {
        JoinGroupBuyReq req=new JoinGroupBuyReq(this, new IReturnCallback<CommonResult>() {
            @Override
            public void onReturn(Object invoker, ProtocolType.ResponseEvent event, CommonResult result) {
                if (ProtocolType.ResponseEvent.isFinish(event)){
                    Logger.i(TAG, "JoinResult = " + result.toString());
                    Message message = new Message();
                    message.obj = getOprRespInfo(result);
                    if (result.isSuccess()) {
                        message.what = GlobalMessageType.Advertising.JOIN_GROUPBUY_SUCCESS;
                    } else {
                        message.what = GlobalMessageType.Advertising.JOIN_GROUPBUY_FAILED;
                    }
                    sendMessage(message);
                }
            }
        });

        req.username=username;
        req.userPhone=userPhone;
        req.neednumber=number;
        if (!StringUtils.isEmpty(remark))
            req.userremark=remark;
        req.exec();
    }

    @Override
    public void getIndexBanner(String clientType) {
        IndexBannerReq req=new IndexBannerReq(this, new IReturnCallback<BannerListResult>() {
            @Override
            public void onReturn(Object invoker, ProtocolType.ResponseEvent event, BannerListResult result) {
                if (ProtocolType.ResponseEvent.isFinish(event)){
                    Logger.i(TAG,"IndexbannerResult"+result.toString());
                    Message message=new Message();
                    RespInfo respInfo =getOprRespInfo(this,result.datas,result);
                    message.obj=respInfo.data;
                    if (result.isSuccess()) {
                        message.what = GlobalMessageType.Advertising.BANNER_SUCCESS;
                    } else {
                        message.what = GlobalMessageType.Advertising.BANNER_FAILED;
                    }
                    sendMessage(message);
                }
            }
        });
        req.clientType=clientType;
        req.exec();
    }
}
