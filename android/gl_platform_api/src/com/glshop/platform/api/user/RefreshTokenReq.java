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
 * @Description : 刷新Token请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:13:07
 */
public class RefreshTokenReq extends BaseRequest<LoginResult> {

	/**
	 * 用户账号
	 */
	public String account;

	/**
	 * 用户Token
	 */
	public String token;

	public RefreshTokenReq(Object invoker, IReturnCallback<LoginResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected LoginResult getResultObj() {
		return new LoginResult();
	}

	@Override
	protected void buildParams() {
		//request.addParam("account", account);
		request.addParam("oldUserToken", token);
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
		return "/auth/updateUserToken";
	}
}
