package com.glshop.platform.api.syscfg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.syscfg.data.GetSyscfgInfoResult;
import com.glshop.platform.api.syscfg.data.model.SyncInfoModel;
import com.glshop.platform.api.util.SyncCfgUtils;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 获取系统参数信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetSyscfgInfoReq extends BaseRequest<GetSyscfgInfoResult> {

	/** 本地最新同步时间戳信息 */
	public Map<String, String> sysCfgTimestamp;

	public GetSyscfgInfoReq(Object invoker, IReturnCallback<GetSyscfgInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetSyscfgInfoResult getResultObj() {
		return new GetSyscfgInfoResult();
	}

	@Override
	protected void buildParams() {
		if (sysCfgTimestamp != null && sysCfgTimestamp.size() > 0) {
			JSONArray array = new JSONArray();
			Iterator<Entry<String, String>> it = sysCfgTimestamp.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				JSONObject object = new JSONObject();
				try {
					object.put("type", entry.getKey());
					object.put("timeStamp", entry.getValue());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(object);
			}
			Logger.i("GetSyscfgInfoReq", "JSON = " + array.toString());
			request.addParam("typeInfo", array.toString());
		}
	}

	@Override
	protected void parseData(GetSyscfgInfoResult result, ResultItem item) {
		ResultItem productItem = (ResultItem) item.get("DATA|goods");
		ResultItem productSpecItem = (ResultItem) item.get("DATA|goodChild");
		//ResultItem areaItem = (ResultItem) item.get("DATA|area");
		//ResultItem bankItem = (ResultItem) item.get("DATA|banks");
		ResultItem areaItem = (ResultItem) item.get("DATA|riverSection");
		ResultItem sysParamItem = (ResultItem) item.get("DATA|sysParam");

		Map<String, String> sysCfgTimestamp = new HashMap<String, String>();

		SyncInfoModel sysSyncInfo = new SyncInfoModel();
		sysSyncInfo.mProductList = SyncCfgUtils.parseSysProductData(productItem, productSpecItem, sysCfgTimestamp);
		sysSyncInfo.mAreaList = SyncCfgUtils.parseSysAreaData(areaItem, sysCfgTimestamp);
		sysSyncInfo.mSysParamList = SyncCfgUtils.parseSysParamData(sysParamItem, sysCfgTimestamp);

		result.sysCfgTimestamp = sysCfgTimestamp;
		result.sysSyncInfo = sysSyncInfo;
	}

	@Override
	protected String getTypeURL() {
		return "/sync/getInfo";
	}
}
