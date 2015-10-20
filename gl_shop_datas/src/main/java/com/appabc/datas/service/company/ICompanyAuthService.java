/**
 *
 */
package com.appabc.datas.service.company;

import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 企业认证信息SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月25日 下午9:27:19
 */
public interface ICompanyAuthService extends IBaseService<TCompanyAuth> {
	
	/**
	 * 根据认证记录查询
	 * @param authid
	 * @return
	 */
	public TCompanyAuth queryByAuthid(int authid);

}
