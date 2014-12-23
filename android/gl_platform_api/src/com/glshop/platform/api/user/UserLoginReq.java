package com.glshop.platform.api.user;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.DepositStatusType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.user.data.LoginResult;
import com.glshop.platform.api.user.data.model.LoginRespInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 用户登录请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:47:10
 */
public class UserLoginReq extends BaseRequest<LoginResult> {

	/**
	 * 用户名
	 */
	public String account;

	/**
	 * 密码
	 */
	public String password;

	/**
	 * 客户端ID
	 */
	public String clientID;

	public UserLoginReq(Object invoker, IReturnCallback<LoginResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected LoginResult getResultObj() {
		return new LoginResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("username", account);
		request.addParam("password", password);
		request.addParam("clientid", clientID);
		request.addParam("clienttype", "0");
	}

	@Override
	protected void parseData(LoginResult result, ResultItem item) {
		if (item != null) {
			List<ResultItem> itemList = (ArrayList<ResultItem>) item.get("DATA");
			if (itemList != null && itemList.size() > 0) {
				ResultItem resultItem = itemList.get(0);
				LoginRespInfoModel info = new LoginRespInfoModel();
				info.account = resultItem.getString("username");
				info.phone = resultItem.getString("phone");
				info.companyId = resultItem.getString("cid");
				info.companyName = resultItem.getString("cname");
				info.contractCount = resultItem.getInt("contractTotal");
				info.myBuyCount = resultItem.getInt("orderfindTotal");
				info.token = resultItem.getString("userToken");
				info.tokenExpire = resultItem.getLong("effTimeLength");
				ResultItem typeItem = (ResultItem) resultItem.get("ctype");
				if (typeItem != null) {
					info.profileType = ProfileType.convert(resultItem.getEnumValue("ctype", ProfileType.COMPANY.toValue()));
				}
				// 保证金状态
				ResultItem depositStatusItem = (ResultItem) resultItem.get("bailstatus");
				if (depositStatusItem != null) {
					info.isDepositEnough = DepositStatusType.convert(depositStatusItem.getInt("val")) == DepositStatusType.RECHARGE_SUCCESS;
				}
				result.data = info;
			}
		}
	}

	@Override
	protected String getTypeURL() {
		return "/auth/login";
	}

}
