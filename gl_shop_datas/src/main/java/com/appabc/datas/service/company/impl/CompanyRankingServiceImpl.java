package com.appabc.datas.service.company.impl;

import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TCompanyRanking;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.dao.company.ICompanyRankingDao;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyRankingService;
import com.appabc.datas.service.contract.IContractInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description : 企业信息统计service实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * @Create_Date : 2014年10月10日 下午6:03:15
 */
@Service(value="ICompanyRankingService")
public class CompanyRankingServiceImpl implements ICompanyRankingService {

	@Autowired
	private ICompanyRankingDao companyRankingDao;

	@Autowired
	private IContractInfoService iContractInfoService;

	@Autowired
	private ICompanyEvaluationService iCompanyEvaluationService;
	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#add(com.appabc.common.base.bean.BaseBean)
	 */
	public void add(TCompanyRanking entity) {
		companyRankingDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TCompanyRanking entity) {
		companyRankingDao.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TCompanyRanking entity) {
		this.companyRankingDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		companyRankingDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TCompanyRanking query(TCompanyRanking entity) {
		return companyRankingDao.query(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	public TCompanyRanking query(Serializable id) {
		return companyRankingDao.query(id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TCompanyRanking> queryForList(TCompanyRanking entity) {
		return companyRankingDao.queryForList(entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	public List<TCompanyRanking> queryForList(Map<String, ?> args) {
		return this.companyRankingDao.queryForList(args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.service.IBaseService#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TCompanyRanking> queryListForPagination(
			QueryContext<TCompanyRanking> qContext) {
		return this.companyRankingDao.queryListForPagination(qContext);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyRankingService#calculateTradeRate(java.lang.String)
	 */
	@Override
	public void calculateTradeSuccessRate(String cid) throws ServiceException{
		TCompanyRanking entity = new TCompanyRanking();
		entity.setCid(cid);
		entity = this.query(entity);
		//查询相关合同总数和相关成功合同总数
		EvaluationInfoBean eib = iContractInfoService.getTransactionSuccessRate(cid);
		if(entity == null){
			TCompanyRanking tcr = new TCompanyRanking();
			tcr.setCid(cid);
			tcr.setStatdate(DateUtil.getNowDate());
			tcr.setOrdersnum(eib.getTransactionSuccessNum());
			tcr.setOrderspersent(eib.getTransactionSuccessRate());
			this.add(tcr);
		} else {
			entity.setStatdate(DateUtil.getNowDate());
			entity.setOrdersnum(eib.getTransactionSuccessNum());
			entity.setOrderspersent(eib.getTransactionSuccessRate());
			this.modify(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.company.ICompanyRankingService#calculateTradeEvaluationRate(java.lang.String)
	 */
	@Override
	public void calculateTradeEvaluationRate(String cid)
			throws ServiceException {
		TCompanyRanking entity = new TCompanyRanking();
		entity.setCid(cid);
		entity = this.query(entity);
		//查询相关评价的数据
		TCompanyEvaluation tce = new TCompanyEvaluation();
		tce.setCid(cid);
		List<TCompanyEvaluation> list = iCompanyEvaluationService.queryForList(tce);
		int degressTotal = 0;
		int creditTotal = 0;
		float degress = 0f;
		float credit = 0f;
		if(list != null && list.size() > 0){
			int size = list.size();
			for(TCompanyEvaluation t : list){
				degressTotal += t.getSatisfaction();
				creditTotal += t.getCredit();
			}
			degress = RandomUtil.div(degressTotal, size).floatValue();
			credit = RandomUtil.div(creditTotal, size).floatValue();
		}
		if(entity == null){
			TCompanyRanking tcr = new TCompanyRanking();
			tcr.setCid(cid);
			tcr.setCredit(credit);
			tcr.setDegress(degress);
			tcr.setStatdate(DateUtil.getNowDate());
			this.add(tcr);
		} else {
			entity.setCredit(credit);
			entity.setDegress(degress);
			entity.setStatdate(DateUtil.getNowDate());
			this.modify(entity);
		}
	}

}
