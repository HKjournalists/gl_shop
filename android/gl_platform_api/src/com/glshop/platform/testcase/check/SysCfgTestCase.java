package check;

import java.util.HashMap;
import java.util.Map;

import base.BaseTestCase;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.syscfg.GetSyscfgInfoReq;
import com.glshop.platform.api.syscfg.data.GetSyscfgInfoResult;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 系统配置参数测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class SysCfgTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		testSyncSysCfg(); // 同步系统配置信息
	}

	public void testSyncSysCfg() {
		Map<String, String> sysCfgTimestamp = new HashMap<String, String>();
		// Old Timestamp
		sysCfgTimestamp.put("2", "2014-1-1 11:22:33");
		sysCfgTimestamp.put("4", "2014-1-1 11:22:33");
		sysCfgTimestamp.put("5", "2014-1-1 11:22:33");
		sysCfgTimestamp.put("6", "2014-1-1 11:22:33");

		// New Timestamp
		//		sysCfgTimestamp.put("2", "2014-04-06 01:02:03");
		//		sysCfgTimestamp.put("4", "2014-04-07 01:02:03");
		//		sysCfgTimestamp.put("5", "2014-04-08 01:02:03");
		//		sysCfgTimestamp.put("6", "2014-04-09 01:02:03");

		GetSyscfgInfoReq req = new GetSyscfgInfoReq(this, new IReturnCallback<GetSyscfgInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetSyscfgInfoResult result) {
				Logger.e(TAG, "Result = " + (result == null ? null : result.toString()));
				finish(event, result);
			}
		});
		req.sysCfgTimestamp = sysCfgTimestamp;
		req.exec();
		startWaiting();
	}

}
