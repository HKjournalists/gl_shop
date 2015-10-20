/**
 *
 */
package com.appabc.datas.dao.company;

import java.util.List;

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

	/**
	 * @param cid
	 * @return
	 */
	TCompanyInfo queryAuthCmpInfo(String cid);
	
	TCompanyInfo queryCmpInfoByUserPhone(String phone);
	
	List<TCompanyInfo> queryCmpInfoListByUserPhones(String phones) ;

	public int queryCount(TCompanyInfo entity);
	
	/**
	 *  查询后台任务列表中无效的认证信息
	 */
	List<TCompanyInfo> queryInvalidListForTask();
	/**
	 * 新添加的用户
	 * @return
	 */
	List<TCompanyInfo> queryNewListForTask();
}
