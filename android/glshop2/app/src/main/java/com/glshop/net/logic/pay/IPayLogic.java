package com.glshop.net.logic.pay;

import java.util.List;

import com.glshop.platform.api.purse.data.model.PayInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 支付业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:17:12
 */
public interface IPayLogic extends ILogic {

	/**
	 * 添加支付结果记录
	 * @param account
	 * @param info
	 */
	void addPayInfo(String account, PayInfoModel info);

	/**
	 * 查询未上报的支付记录
	 * @param account
	 * return
	 */
	List<PayInfoModel> getUnreportedPayInfo(String account);

	/**
	 * 更新支付记录
	 * @param info
	 */
	void updatePayInfo(PayInfoModel info);

	/**
	 * 删除支付记录
	 * @param orderId
	 */
	void deletePayInfo(String orderId);

}
