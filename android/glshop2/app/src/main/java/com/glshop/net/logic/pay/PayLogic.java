package com.glshop.net.logic.pay;

import java.util.List;

import android.content.Context;

import com.glshop.net.logic.db.dao.pay.IPayDao;
import com.glshop.net.logic.db.dao.pay.PayDao;
import com.glshop.platform.api.purse.data.model.PayInfoModel;
import com.glshop.platform.base.logic.BaseLogic;

/**
 * @Description : 支付业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:17:44
 */
public class PayLogic extends BaseLogic implements IPayLogic {

	/**
	 * @param context
	 */
	public PayLogic(Context context) {
		super(context);
	}

	@Override
	public void addPayInfo(String account, PayInfoModel info) {
		IPayDao payDao = PayDao.getInstance(mcontext);
		payDao.insertPayInfo(mcontext, info);
	}

	@Override
	public List<PayInfoModel> getUnreportedPayInfo(String account) {
		IPayDao payDao = PayDao.getInstance(mcontext);
		List<PayInfoModel> data = payDao.queryUnreportedPayInfo(mcontext, account);
		return data;
	}

	@Override
	public void updatePayInfo(PayInfoModel info) {
		IPayDao payDao = PayDao.getInstance(mcontext);
		payDao.updatePayInfo(mcontext, info);
	}

	@Override
	public void deletePayInfo(String orderId) {
		IPayDao payDao = PayDao.getInstance(mcontext);
		payDao.deletePayInfo(mcontext, orderId);
	}

}
