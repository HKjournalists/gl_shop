/**
 *
 */
package com.appabc.datas.dao.company;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 公司信息DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:29:01
 */
public interface ICompanyInfoDao extends IBaseDao<TCompanyInfo> {
	
	/**
	 * 根据企业ID查询已认证通过的企业详情
	 * @param cid
	 * @return
	 */
	public CompanyAllInfo queryAuthCompanyInfo(String cid);

}
