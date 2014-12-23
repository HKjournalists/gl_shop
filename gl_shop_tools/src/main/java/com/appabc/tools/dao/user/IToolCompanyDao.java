/**
 *
 */
package com.appabc.tools.dao.user;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月12日 下午5:01:06
 */
public interface IToolCompanyDao extends IBaseDao<TCompanyInfo> {
	
	/**
	 * 根据企业ID获取船舶信息，如果不是船舶返回空
	 * @param cid
	 * @return
	 */
	public TCompanyShipping queryShippingByCid(String cid);
}
