package com.glshop.platform.api.setting;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.setting.data.GetUpgradeInfoResult;
import com.glshop.platform.api.setting.data.model.AppInfoModel;
import com.glshop.platform.api.setting.data.model.UpgradeInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取升级信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetUpgradeInfoReq extends BaseRequest<GetUpgradeInfoResult> {

	/**
	 * 应用信息
	 */
	public AppInfoModel info;

	public GetUpgradeInfoReq(Object invoker, IReturnCallback<GetUpgradeInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetUpgradeInfoResult getResultObj() {
		return new GetUpgradeInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("devices", info.deviceType);
		request.addParam("lastNo", info.versionCode);
	}

	@Override
	protected void parseData(GetUpgradeInfoResult result, ResultItem item) {
		ResultItem appItem = (ResultItem) item.get("DATA");
		if (appItem != null) {
			UpgradeInfoModel appInfo = new UpgradeInfoModel();
			appInfo.versionCode = appItem.getString("lastno");
			appInfo.versionName = appItem.getString("lastname");
			appInfo.size = String.valueOf(appItem.getFloat("filesize"));
			appInfo.url = appItem.getString("downurl");
			appInfo.md5 = appItem.getString(/*"md5"*/"LASTEST");
			appInfo.isForceUpgrade = appItem.getInt("isforce") == 1;
			appInfo.description = appItem.getString("mark");
			result.data = appInfo;
		}
	}

	@Override
	protected String getTypeURL() {
		return "/noAuthUrl/checkVersion";
	}

}
