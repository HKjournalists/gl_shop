package com.appabc.datas.dao.company;


import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午8:12:05
 */

public interface ICompanyEvaluationDAO extends IBaseDao<TCompanyEvaluation>{

	List<CompanyEvaluationInfo> queryEvaluationContractList(CompanyEvaluationInfo cei);

}
