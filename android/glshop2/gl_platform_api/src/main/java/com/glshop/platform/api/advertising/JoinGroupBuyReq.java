package com.glshop.platform.api.advertising;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.StringUtils;

/**
 * Created by guoweilong on 2015/8/7.
 * 加入团购活动请求
 */
public class JoinGroupBuyReq extends BaseRequest<CommonResult>{

    /**
     * 用户姓名
     */
    public String username;

    /**
     * 用户电话
     */
    public String userPhone;

    /**
     * 需求数量
     */
    public int neednumber;

    /**
     * 备注信息
     */
    public String userremark;

    public JoinGroupBuyReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
        super(invoker, callBackx);
    }

    @Override
    protected CommonResult getResultObj() {
        return new CommonResult();
    }

    @Override
    protected void buildParams() {
        request.addParam("name",username);
        request.addParam("phone",userPhone);
        request.addParam("reqnum",neednumber);
        if (!StringUtils.isEmpty(userremark))
         request.addParam("remark",userremark);
    }

    @Override
    protected void parseData(CommonResult result, ResultItem item) {

    }

    @Override
    protected String getTypeURL() {
        return "/noAuthUrl/system/activity/join";
    }
}
