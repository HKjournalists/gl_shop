package com.appabc.datas.dao.company.impl;

import com.appabc.bean.enums.AcceptBankInfo.AcceptBankStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.IAcceptBankDAO;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.tools.utils.PrimaryKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月24日 下午8:36:22
 */
@Repository
public class AcceptBankDAOImpl extends BaseJdbcDao<TAcceptBank> implements IAcceptBankDAO {

	@Autowired
	private PrimaryKeyGenerator pkg;

	private static final String INSERT_SQL = " INSERT INTO T_ACCEPT_BANK (ID,CID,AUTHID,BANKCARD,BANKACCOUNT,CARDUSER,CARDUSERID,BANKTYPE,BANKNAME,ADDR,REMARK,CREATETIME,UPDATETIME,CREATOR,STATUS,AUTHSTATUS) VALUES (:id,:cid,:authid,:bankcard,:bankaccount,:carduser,:carduserid,:banktype,:bankname,:addr,:remark,:createtime,:updatetime,:creator,:status,:authstatus) ";
	private static final String UPDATE_SQL = " UPDATE T_ACCEPT_BANK SET CID = :cid,AUTHID = :authid,BANKCARD = :bankcard,BANKACCOUNT = :bankaccount,CARDUSER = :carduser,CARDUSERID = :carduserid,BANKTYPE = :banktype,BANKNAME = :bankname,ADDR = :addr,REMARK = :remark,CREATETIME = :createtime,UPDATETIME = :updatetime,CREATOR = :creator,STATUS=:status,AUTHSTATUS=:authstatus WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ACCEPT_BANK WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ab.ID,ab.CID,ab.AUTHID,ab.BANKCARD,ab.BANKACCOUNT,ab.CARDUSER," +
			"ab.CARDUSERID,ab.BANKTYPE,ab.BANKNAME,ab.ADDR,ab.REMARK,ab.CREATETIME,ab.UPDATETIME,ab.CREATOR," +
			"ab.STATUS,ab.AUTHSTATUS, ci.cname, ci.ctype FROM T_ACCEPT_BANK ab, T_COMPANY_INFO ci WHERE ab.cid=ci.id ";

	private String dynamicJoinSqlWithEntity(TAcceptBank entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		if (sql.indexOf(" WHERE ") == -1) {
			sql.append(" WHERE 1 = 1 ");
		}
		addNameParamerSqlWithProperty(sql, "id", "ab.ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "cid", "ab.CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "authid", "ab.AUTHID", entity.getAuthid());
		addNameParamerSqlWithProperty(sql, "bankcard", "ab.BANKCARD", entity.getBankcard());
		addNameParamerSqlWithProperty(sql, "bankaccount", "ab.BANKACCOUNT", entity.getBankaccount());
		addNameParamerSqlWithProperty(sql, "carduser", "ab.CARDUSER", entity.getCarduser());
		addNameParamerSqlWithProperty(sql, "carduserid", "ab.CARDUSERID", entity.getCarduserid());
		addNameParamerSqlWithProperty(sql, "blanktype", "ab.BANKTYPE", entity.getBanktype());
		addNameParamerSqlWithProperty(sql, "bankname", "ab.BANKNAME", entity.getBankname());
		addNameParamerSqlWithProperty(sql, "addr", "ab.ADDR", entity.getAddr());
		addNameParamerSqlWithProperty(sql, "remark", "ab.REMARK", entity.getRemark());
		addNameParamerSqlWithProperty(sql, "createtime", "ab.CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "updatetime", "ab.UPDATETIME", entity.getUpdatetime());
		addNameParamerSqlWithProperty(sql, "creator", "ab.CREATOR", entity.getCreator());
		addNameParamerSqlWithProperty(sql, "status", "ab.STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "authstatus", "ab.AUTHSTATUS", entity.getAuthstatus());
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	public void save(TAcceptBank entity) {
		entity.setId(pkg.generatorBusinessKeyByBid("ACCEPTBANKID"));
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TAcceptBank entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	public void update(TAcceptBank entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	public void delete(TAcceptBank entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	public TAcceptBank query(TAcceptBank entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	public TAcceptBank query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" and ab.ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	public List<TAcceptBank> queryForList(TAcceptBank entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		String querySql = dynamicJoinSqlWithEntity(entity,sql) + " AND ab.STATUS <> " + AcceptBankStatus.ACCEPT_BANK_STATUS_DEL.getVal();
		return super.queryForList(querySql, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	public List<TAcceptBank> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	public QueryContext<TAcceptBank> queryListForPagination(
			QueryContext<TAcceptBank> qContext) {
		StringBuilder sb = new StringBuilder(SELECT_SQL.length() * 2);
		sb.append(SELECT_SQL);
		if (qContext.getBeanParameter() != null) {
			dynamicJoinSqlWithEntity(qContext.getBeanParameter(), sb);
		}
		return super.queryListForPagination(sb.toString(), qContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public TAcceptBank mapRow(ResultSet rs, int rowNum) throws SQLException {
		TAcceptBank bean = new TAcceptBank();
		TCompanyInfo ci = new TCompanyInfo();
		String cid = rs.getString("CID");

		bean.setId(rs.getString("ID"));
		bean.setCid(cid);
		bean.setAuthid(rs.getString("AUTHID")==null? null : rs.getInt("AUTHID"));
		bean.setBankcard(rs.getString("BANKCARD"));
		bean.setBankaccount(rs.getString("BANKACCOUNT"));
		bean.setCarduser(rs.getString("CARDUSER"));
		bean.setCarduserid(rs.getString("CARDUSERID"));
		bean.setBanktype(rs.getString("BANKTYPE"));
		bean.setBankname(rs.getString("BANKNAME"));
		bean.setAddr(rs.getString("ADDR"));
		bean.setRemark(rs.getString("REMARK"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setStatus(AcceptBankStatus.enumOf(rs.getInt("STATUS")));
		bean.setAuthstatus(AuthRecordStatus.enumOf(rs.getInt("AUTHSTATUS")+""));

		ci.setId(cid);
		ci.setCname(rs.getString("cname"));
		ci.setCtype(CompanyInfo.CompanyType.enumOf(rs.getString("ctype")));
		bean.setCompany(ci);

		TAuthRecord ar = new TAuthRecord();
		ar.setId(String.valueOf(bean.getAuthid()));

		return bean;
	}

	@Override
	public QueryContext<TAcceptBank> queryListForAuditFinished(QueryContext<TAcceptBank> queryContext) {
		StringBuilder sb = new StringBuilder(SELECT_SQL.length() * 2);
		TAcceptBank ab = new TAcceptBank();
		ab.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_ING);
		Map<String, Object> params = new HashMap<>();
		params.put("authstatus", ab.getAuthstatus().getVal());
		queryContext.setBeanParameter(ab);
		queryContext.setParameters(params);
		sb.append(SELECT_SQL + " and ab.authstatus<>:authstatus ");
		return super.queryListForPagination(sb.toString(), queryContext);
	}
}
