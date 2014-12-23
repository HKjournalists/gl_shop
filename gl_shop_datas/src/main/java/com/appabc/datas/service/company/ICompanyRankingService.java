package com.appabc.datas.service.company;

import com.appabc.bean.pvo.TCompanyRanking;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 企业信息统计SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * @Create_Date : 2014年10月10日 下午6:03:02
 */

public interface ICompanyRankingService extends IBaseService<TCompanyRanking> {
	
	/**
	 * 相关合同总数和相关成功合同总数
	 * */
	void calculateTradeSuccessRate(String cid) throws ServiceException;
	
	/**
	 * 评价的数据
	 * */
	void calculateTradeEvaluationRate(String cid) throws ServiceException;
	
}
