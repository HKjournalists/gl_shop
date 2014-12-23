package check;

import java.util.Arrays;

import base.BaseTestCase;

import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.message.GetMessageListReq;
import com.glshop.platform.api.message.GetUnreadedMsgNumReq;
import com.glshop.platform.api.message.RptMessageReadedReq;

/**
 * @Description : 消息中心测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class MessageAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		//addTestClass(GetMessageListReq.class, new CallBackBuilder<GetMessageListResult>()); // 消息中心列表
		//addTestClass(RptMessageReadedReq.class, new CallBackBuilder<CommonResult>()); // 设置消息已读
		//addTestClass(GetUnreadedMsgNumReq.class, new CallBackBuilder<GetUnreadedMsgResult>()); // 获取未读消息数量
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof GetMessageListReq) { // 消息中心列表
			GetMessageListReq req = (GetMessageListReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		} else if (request instanceof RptMessageReadedReq) { // 设置消息已读
			RptMessageReadedReq req = (RptMessageReadedReq) request;
			req.idList = Arrays.asList("2", "2", "2");
		} else if (request instanceof GetUnreadedMsgNumReq) { // 获取未读消息数量
			GetUnreadedMsgNumReq req = (GetUnreadedMsgNumReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		}
	}

}
