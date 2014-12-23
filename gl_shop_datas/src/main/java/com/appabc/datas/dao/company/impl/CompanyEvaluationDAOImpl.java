package com.appabc.datas.dao.company.impl;

import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyEvaluationDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : company evaluation DAO
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月11日 下午8:14:19
 */

@Repository
public class CompanyEvaluationDAOImpl extends BaseJdbcDao<TCompanyEvaluation>
		implements ICompanyEvaluationDAO {

	private static final String INSERT_SQL = " INSERT INTO T_COMPANY_EVALUATION (CID,OID,SATISFACTION,CREDIT,EVALUATION,CRATEDATE,CREATER) VALUES (:cid,:oid,:satisfaction,:credit,:evaluation,:cratedate,:creater) ";
	private static final String UPDATE_SQL = " UPDATE T_COMPANY_EVALUATION SET CID = :cid,OID = :oid,SATISFACTION = :satisfaction,CREDIT = :credit,EVALUATION = :evaluation,CRATEDATE = :cratedate,CREATER = :creater WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_COMPANY_EVALUATION WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,CID,OID,SATISFACTION,CREDIT,EVALUATION,CRATEDATE,CREATER FROM T_COMPANY_EVALUATION ";

	private static final String SELECTCOMPANY_SQL = " SELECT evalInfo.ID AS ID, evalInfo.CID AS CID, evalInfo.OID AS OID,evalInfo.SATISFACTION AS SATISFACTION,evalInfo.CREDIT AS CREDIT,evalInfo.EVALUATION AS EVALUATION,evalInfo.CRATEDATE AS CRATEDATE,evalInfo.CREATER AS CREATER,ci.CNAME AS CNAME FROM T_COMPANY_EVALUATION evalInfo LEFT JOIN T_COMPANY_INFO ci ON evalInfo.CID = ci.ID  ";

	private String dynamicJoinSqlWithEntity(TCompanyEvaluation entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		addNameParamerSqlWithProperty(sql, "satisfaction", "SATISFACTION", entity.getSatisfaction());
		addNameParamerSqlWithProperty(sql, "credit", "CREDIT", entity.getCredit());
		addNameParamerSqlWithProperty(sql, "evaluation", "EVALUATION", entity.getEvaluation());
		addNameParamerSqlWithProperty(sql, "cratedate", "CRATEDATE", entity.getCratedate());
		addNameParamerSqlWithProperty(sql, "creater", "CREATER", entity.getCreater());
		return sql.toString();
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.
	 * BaseBean)
	 */
	public void save(TCompanyEvaluation entity) {
		super.save(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common
	 * .base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TCompanyEvaluation entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void update(TCompanyEvaluation entity) {
		super.update(UPDATE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void delete(TCompanyEvaluation entity) {
		super.delete(DELETE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public TCompanyEvaluation query(TCompanyEvaluation entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TCompanyEvaluation query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public List<TCompanyEvaluation> queryForList(TCompanyEvaluation entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TCompanyEvaluation> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc
	 * .common.base.QueryContext)
	 */
	public QueryContext<TCompanyEvaluation> queryListForPagination(
			QueryContext<TCompanyEvaluation> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	public TCompanyEvaluation mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TCompanyEvaluation bean = new TCompanyEvaluation();

		bean.setId(rs.getString("ID"));
		bean.setCid(rs.getString("CID"));
		bean.setOid(rs.getString("OID"));
		bean.setSatisfaction(rs.getInt("SATISFACTION"));
		bean.setCredit(rs.getInt("CREDIT"));
		bean.setEvaluation(rs.getString("EVALUATION"));
		bean.setCratedate(rs.getTimestamp("CRATEDATE"));
		bean.setCreater(rs.getString("CREATER"));

		return bean;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.company.ICompanyEvaluationDAO#queryEvaluationContractList(com.appabc.bean.bo.CompanyEvaluationInfo)
	 */
	@Override
	public List<CompanyEvaluationInfo> queryEvaluationContractList(
			CompanyEvaluationInfo cei) {
		if(cei == null){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SELECTCOMPANY_SQL);

		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "evalInfo.ID", cei.getId());
		addNameParamerSqlWithProperty(sql, "cid", "evalInfo.CID", cei.getCid());
		addNameParamerSqlWithProperty(sql, "oid", "evalInfo.OID", cei.getOid());
		addNameParamerSqlWithProperty(sql, "satisfaction", "evalInfo.SATISFACTION", cei.getSatisfaction());
		addNameParamerSqlWithProperty(sql, "credit", "evalInfo.CREDIT", cei.getCredit());
		addNameParamerSqlWithProperty(sql, "evaluation", "evalInfo.EVALUATION", cei.getEvaluation());
		addNameParamerSqlWithProperty(sql, "cratedate", "evalInfo.CRATEDATE", cei.getCratedate());
		addNameParamerSqlWithProperty(sql, "creater", "evalInfo.CREATER", cei.getCreater());
		addNameParamerSqlWithProperty(sql, "cname", "ci.CNAME", cei.getCname());

		log.debug("The Sql Str Is : " + sql.toString() + " ; And The Value Is : "+cei);
		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(cei);
		return getNamedParameterJdbcTemplate().query(sql.toString(), paramSource, new RowMapper<CompanyEvaluationInfo>() {

			@Override
			public CompanyEvaluationInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CompanyEvaluationInfo bean = new CompanyEvaluationInfo();
				bean.setId(rs.getString("ID"));
				bean.setCid(rs.getString("CID"));
				bean.setOid(rs.getString("OID"));
				bean.setSatisfaction(rs.getInt("SATISFACTION"));
				bean.setCredit(rs.getInt("CREDIT"));
				bean.setEvaluation(rs.getString("EVALUATION"));
				bean.setCratedate(rs.getTimestamp("CRATEDATE"));
				bean.setCreater(rs.getString("CREATER"));
				bean.setCname(rs.getString("CNAME"));
				return bean;
			}

		});
	}

}
