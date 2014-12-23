package com.appabc.datas.dao.company.impl;

import com.appabc.bean.pvo.TCompanyRanking;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyRankingDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 企业信息统计DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * @Create_Date  : 2014年10月10日 下午9:02:37
 */
@Repository
public class CompanyRankingDaoImpl extends BaseJdbcDao<TCompanyRanking> implements ICompanyRankingDao {

	private static final String INSERTSQL = " insert into T_COMPANY_RANKING (SID, CID, ORDERSNUM, ORDERSPERSENT, DEGRESS, EVALUATION, STATDATE,CREDIT) values (:id, :cid, :ordersnum, :orderspersent, :degress, :evaluation, :statdate, :credit) ";
	private static final String UPDATESQL = " update T_COMPANY_RANKING set CID = :cid, ORDERSNUM = :ordersnum, ORDERSPERSENT = :orderspersent, DEGRESS = :degress, EVALUATION = :evaluation, STATDATE = :statdate, CREDIT = :credit  where SID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_RANKING WHERE SID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_RANKING WHERE SID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_RANKING WHERE 1=1 ";

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TCompanyRanking entity) {
		super.save(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TCompanyRanking entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TCompanyRanking entity) {
		super.update(UPDATESQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TCompanyRanking entity) {
		super.delete(DELETESQLBYID, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TCompanyRanking query(TCompanyRanking entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TCompanyRanking query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TCompanyRanking> queryForList(TCompanyRanking entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TCompanyRanking> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TCompanyRanking> queryListForPagination(
			QueryContext<TCompanyRanking> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TCompanyRanking mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyRanking t = new TCompanyRanking();

		t.setId(rs.getString("SID"));
		t.setCid(rs.getString("CID"));
		t.setDegress(rs.getFloat("DEGRESS"));
		t.setEvaluation(rs.getFloat("EVALUATION"));
		t.setOrdersnum(rs.getInt("ORDERSNUM"));
		t.setOrderspersent(rs.getFloat("ORDERSPERSENT"));
		t.setCredit(rs.getFloat("CREDIT"));
		t.setStatdate(rs.getTimestamp("STATDATE"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TCompanyRanking bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "SID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "degress", "DEGRESS", bean.getDegress());
		this.addNameParamerSqlWithProperty(sql, "evaluation", "EVALUATION", bean.getEvaluation());
		this.addNameParamerSqlWithProperty(sql, "ordersnum", "ORDERSNUM", bean.getOrdersnum());
		this.addNameParamerSqlWithProperty(sql, "orderspersent", "ORDERSPERSENT", bean.getOrderspersent());
		this.addNameParamerSqlWithProperty(sql, "credit", "CREDIT", bean.getCredit());
		this.addNameParamerSqlWithProperty(sql, "statdate", "STATDATE", bean.getStatdate());
		return sql.toString();
	}

}
