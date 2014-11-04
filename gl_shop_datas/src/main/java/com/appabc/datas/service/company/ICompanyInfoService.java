/**
 *
 */
package com.appabc.datas.service.company;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 公司信息SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:30:25
 */
public interface ICompanyInfoService extends IBaseService<TCompanyInfo> {
	
	/**
	 * 获取公司认证资料
	 * @param cid 企业ID
	 * @return
	 */
	public CompanyAllInfo queryAuthCompanyInfo(String cid);
	
	/**  
	 * 根据企业ID获取该企业应缴纳的保证金额度
	 * (这里描述这个方法适用条件 – 可选)  
	 * @param cid 企业ID
	 * @return   
	 *float  
	 * @exception   
	 * @since  1.0.0  
	*/
	public float getShouldDepositAmountByCid(String cid);
	
	/**
	 * 更新企业介绍信息
	 * @param cid 企业ID
	 * @param mark 企业介绍
	 * @param companyImgIds 企业图片
	 */
	public void updateIntroduction(String cid, String mark, String companyImgIds);
	
	/**
	 * 认证申请信息
	 * @param ciBean
	 * @param arBean
	 * @param caBean
	 * @param userid 当前用户ID
	 */
	public void authApply(TCompanyInfo ciBean, TAuthRecord arBean, TCompanyAddress caBean,String userid);
	
	/**
	 * 获取企业评价信息
	 * @param cid
	 * @return
	 */
	public EvaluationInfoBean getEvaluationByCid(String cid);

}
