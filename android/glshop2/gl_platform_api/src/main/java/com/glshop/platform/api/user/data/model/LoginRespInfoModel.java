package com.glshop.platform.api.user.data.model;

import com.glshop.platform.api.DataConstants.PayEnvType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 登录初始化信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 上午11:21:55
 */
public class LoginRespInfoModel extends ResultItem {

	/**
	 * 登录账户名
	 */
	public String account;

	/**
	 * 登录手机号
	 */
	public String phone;

	/**
	 * 用户ID
	 */
	public String userId;

	/**
	 * 当前用户身份 
	 */
	public ProfileType profileType;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 企业名称
	 */
	public String companyName;

	/**
	 * 用户Token
	 */
	public String token;

	/**
	 * Token有效期
	 */
	public long tokenExpire;

	/**
	 * 当前用户合同总数
	 */
	public int contractCount;

	/**
	 * 当前用户发布供求数
	 */
	public int myBuyCount;

	/**
	 * 保证金是否足够
	 */
	public boolean isDepositEnough;

	/**
	 * 保证金余额
	 */
	public double depositBalance;

	/**
	 * 货款余额
	 */
	public double paymentBalance;

	/**
	 * 是否已认证
	 */
	public boolean isAuth = false;

	/**
	 * 支付环境
	 */
	public PayEnvType payEnvType = PayEnvType.RELEASE;
	
	public boolean isAuthRemind = false;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LoginResult [");
		sb.append("account = " + account);
		sb.append(",userId = " + userId);
		sb.append(",profileType = " + profileType);
		sb.append(",companyId = " + companyId);
		sb.append(",companyName = " + companyName);
		sb.append(",token = " + token);
		sb.append(",tokenExpire = " + tokenExpire);
		sb.append(",contractCount = " + contractCount);
		sb.append(",myBuyCount = " + myBuyCount);
		sb.append(",isDepositEnough = " + isDepositEnough);
		sb.append(",paymentBalance = " + paymentBalance);
		sb.append(",payEnvType = " + payEnvType != null ? payEnvType.toValue() : null);
		sb.append("]");
		return super.toString() + sb.toString();
	}

}
