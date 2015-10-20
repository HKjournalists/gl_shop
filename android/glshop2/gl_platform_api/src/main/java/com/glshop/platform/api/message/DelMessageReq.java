package com.glshop.platform.api.message;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

import java.util.List;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : 删除消息
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/9/23  16:58
 */
public class DelMessageReq extends BaseRequest<CommonResult> {

    /**
     * 消息ID多个id用逗号间隔
     */
    public List<String> msgids;
    public DelMessageReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
        super(invoker, callBackx);
    }

    @Override
    protected CommonResult getResultObj() {
        return new CommonResult();
    }

    @Override
    protected void buildParams() {
        if (msgids != null && msgids.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < msgids.size(); i++) {
                if (i == 0) {
                    sb.append(msgids.get(i));
                } else {
                    sb.append("," + msgids.get(i));
                }
            }
            request.addParam("msgids", sb.toString());
        }
    }

    @Override
    protected void parseData(CommonResult result, ResultItem item) {

    }

    @Override
    protected String getTypeURL() {
        return "/msg/delete";
    }
}
