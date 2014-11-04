package com.appabc.datas.service.company;

import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月11日 下午8:16:44
 */

public interface ICompanyEvaluationService extends IBaseService<TCompanyEvaluation> {
	
	void toEvaluateContract(String operator,String operatorName,TCompanyEvaluation bean) throws ServiceException;
	
}
