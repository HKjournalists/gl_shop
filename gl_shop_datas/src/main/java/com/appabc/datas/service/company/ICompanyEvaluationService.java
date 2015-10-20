package com.appabc.datas.service.company;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

import java.util.List;


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月11日 下午8:16:44
 */

public interface ICompanyEvaluationService extends IBaseService<TCompanyEvaluation> {

	/**
	 * @Description 评价合同
	 * @param operator,operatorName,bean
	 * @return TOrderArbitration
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void toEvaluateContract(String operator,String operatorName,TCompanyEvaluation bean) throws ServiceException;

	/**
	 * @Description 获取合同评价列表
	 * @param operator,operatorName,bean
	 * @return List<CompanyEvaluationInfo>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<CompanyEvaluationInfo> getEvaluationContractList(CompanyEvaluationInfo cei);
	
	/**
	 * 获取被评价信息，返回内容中的用户信息是评价人的
	 * @param cei
	 * @return
	 */
	List<CompanyEvaluationInfo> queryEvaluationListByCompany(CompanyEvaluationInfo cei);


}
