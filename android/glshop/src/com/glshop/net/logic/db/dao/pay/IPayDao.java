package com.glshop.net.logic.db.dao.pay;

import java.util.List;

import android.content.Context;

import com.glshop.platform.api.purse.data.model.PayInfoModel;

/**
 * @Description : 支付缓存管理接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public interface IPayDao {

	/**
	 * 查询所有支付记录列表
	 * @param context
	 * @param account
	 * @return
	 */
	List<PayInfoModel> queryPayInfo(Context context, String account);

	/**
	 * 查询所有未上报的支付记录列表
	 * @param context
	 * @param account
	 * @return
	 */
	List<PayInfoModel> queryUnreportedPayInfo(Context context, String account);

	/**
	 * 添加支付记录
	 * @param context
	 * @param info
	 * @return
	 */
	long insertPayInfo(Context context, PayInfoModel info);

	/**
	 * 更新支付记录
	 * @param context
	 * @param info
	 * @return
	 */
	boolean updatePayInfo(Context context, PayInfoModel info);

	/**
	 * 删除支付记录
	 * @param context
	 * @param id
	 * @return
	 */
	boolean deletePayInfo(Context context, String id);
}
