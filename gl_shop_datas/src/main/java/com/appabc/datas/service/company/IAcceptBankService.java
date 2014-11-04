package com.appabc.datas.service.company;

import com.appabc.common.base.service.IBaseService;
import com.appabc.pay.bean.TAcceptBank;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 下午9:19:38
 */

public interface IAcceptBankService extends IBaseService<TAcceptBank> {
	
	/**
	 * 提款人认证申请
	 * @param abBean
	 */
	public void authApply(TAcceptBank abBean);
	
	/**
	 * 设置默认提款人
	 * @param id
	 */
	public void setDefault(String id);

	/**
	 * 重新认证提款人信息
	 * @param abBean
	 */
	void reAuthApply(TAcceptBank abBean);

}
