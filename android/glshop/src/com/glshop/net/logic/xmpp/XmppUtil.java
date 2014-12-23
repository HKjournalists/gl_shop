package com.glshop.net.logic.xmpp;

import org.json.JSONException;
import org.json.JSONObject;

import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.StringUtils;

/**
 * xmpp工具类
 */
public class XmppUtil {

	/**
	 * 解析推送内容
	 * @param data
	 * @return
	 */
	public static XmppMsgInfo parseData(String data) {
		XmppMsgInfo info = new XmppMsgInfo();
		if (StringUtils.isNotEmpty(data)) {
			try {
				ResultItem item = new ResultItem(new JSONObject(data));
				if (item != null) {
					try {
						info.businessId = item.getString("businessId");
						info.owner = item.getString("cid");
						info.businessType = MsgBusinessType.convert(item.getEnumValue("businessType"));
						info.content = item.getString("content");
						info.params = (ResultItem) item.get("params");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return info;
	}
}
