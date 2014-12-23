/**
 *
 */
package com.appabc.datas.service.company;

import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 船舶认证信息service接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午8:40:27
 */
public interface ICompanyShippingService extends IBaseService<TCompanyShipping> {
	
	/**
	 * 根据公司ID查询船舶信息
	 * @param cid
	 * @return
	 */
	public TCompanyShipping queryByCid(String cid);

}
