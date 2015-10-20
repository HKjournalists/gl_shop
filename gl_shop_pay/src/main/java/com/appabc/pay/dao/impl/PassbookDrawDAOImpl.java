/**  
 * com.appabc.pay.dao.impl.PassbookDrawDAOImpl.java  
 *   
 * 2014年9月17日 上午11:13:50  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import com.appabc.bean.enums.PurseInfo.ExtractStatus;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookDrawEx;
import com.appabc.pay.dao.IPassbookDrawDAO;

/**
 * @Description : pass book draw DAO IMPL
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date : 2014年9月17日 上午11:13:50
 */

public class PassbookDrawDAOImpl extends BaseJdbcDao<TPassbookDraw> implements
		IPassbookDrawDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_DRAW (TID,AID,AMOUNT,CREATETIME,DEALTIME,DEALER,DEALSTATUS,PID,STATUS,MARK) VALUES (:id,:aid,:amount,:createtime,:dealtime,:dealer,:dealstatus,:pid,:status,:mark) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_DRAW SET AID = :aid,AMOUNT = :amount,CREATETIME = :createtime,DEALTIME = :dealtime,DEALER = :dealer,DEALSTATUS = :dealstatus,PID = :pid,STATUS = :status,MARK = :mark WHERE TID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_DRAW WHERE TID = :id ";
	private static final String SELECT_SQL = " SELECT TID,AID,AMOUNT,CREATETIME,DEALTIME,DEALER,DEALSTATUS,PID,STATUS,MARK FROM T_PASSBOOK_DRAW ";
	
	private String dynamicJoinSqlWithEntity(TPassbookDraw entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "TID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "aid", "AID", entity.getAid());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		this.addNameParamerSqlWithProperty(sql, "dealtime", "DEALTIME", entity.getDealtime());
		this.addNameParamerSqlWithProperty(sql, "dealer", "DEALER", entity.getDealer());
		this.addNameParamerSqlWithProperty(sql, "dealstatus", "DEALSTATUS", entity.getDealstatus());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "mark", "MARK", entity.getMark());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookDraw entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookDraw entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookDraw entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookDraw entity) {
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
	public TPassbookDraw query(TPassbookDraw entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookDraw query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE TID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookDraw> queryForList(TPassbookDraw entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookDraw> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookDraw> queryListForPagination(
			QueryContext<TPassbookDraw> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookDraw mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPassbookDraw entity = new TPassbookDraw();
		
		entity.setId(rs.getString("TID"));
		entity.setAid(rs.getString("AID"));
		entity.setAmount(rs.getDouble("AMOUNT"));
		entity.setCreatetime(rs.getTimestamp("CREATETIME"));
		entity.setDealtime(rs.getTimestamp("DEALTIME"));
		entity.setDealer(rs.getString("DEALER"));
		entity.setDealstatus(rs.getString("DEALSTATUS"));
		entity.setStatus(ExtractStatus.enumOf(rs.getInt("STATUS")));
		entity.setPid(rs.getString("PID"));
		entity.setMark(rs.getString("MARK"));
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPassbookDrawDAO#getTPassbookDrawByPassId(java.lang.String)  
	 */
	public List<TPassbookDraw> getTPassbookDrawByPassId(String passId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT draw.TID AS TID, draw.AID AS AID,draw.AMOUNT AS AMOUNT,draw.CREATETIME AS CREATETIME, draw.DEALTIME AS DEALTIME, draw.DEALER AS DEALER, draw.DEALSTATUS AS DEALSTATUS,draw.PID AS PID,draw.STATUS AS STATUS,draw.MARK AS MARK FROM T_PASSBOOK_DRAW draw ");
		sql.append(" LEFT JOIN T_PASSBOOK_PAY pay ON draw.PID = pay.PID ");
		sql.append(" WHERE pay.PASSID = ? ");
		return super.queryForList(sql.toString(), Collections.singletonList(passId));
	}

	private RowMapper<TPassbookDrawEx> rowMapper = new RowMapper<TPassbookDrawEx>() {

		@Override
		public TPassbookDrawEx mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			TPassbookDrawEx bean = new TPassbookDrawEx();

			bean.setId(rs.getString("TID"));
			bean.setAid(rs.getString("AID"));
			bean.setAmount(rs.getDouble("AMOUNT"));
			bean.setCreatetime(rs.getTimestamp("CREATETIME"));
			bean.setDealtime(rs.getTimestamp("DEALTIME"));
			bean.setDealer(rs.getString("DEALER"));
			bean.setDealstatus(rs.getString("DEALSTATUS"));
			bean.setStatus(ExtractStatus.enumOf(rs.getInt("STATUS")));
			bean.setPid(rs.getString("PID"));
			bean.setMark(rs.getString("MARK"));
			bean.setPassid(rs.getString("PASSID"));
			bean.setPaytime(rs.getTimestamp("PAYTIME"));
			bean.setPasstype(PurseType.enumOf(rs.getString("PASSTYPE")));
			bean.setBalance(rs.getDouble("BALANCE"));
			bean.setCid(rs.getString("CID"));
			bean.setUsername(rs.getString("USERNAME"));
			bean.setPhone(rs.getString("PHONE"));
			
			return bean;
		}
		
	};
	
	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPassbookDrawDAO#extractCashRequestListEx(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPassbookDrawEx> extractCashRequestListEx(
			QueryContext<TPassbookDrawEx> qContext) {
		if(qContext == null){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ftpp.PASSID AS PASSID,ftpp.PAYTIME AS PAYTIME,tpi.PASSTYPE AS PASSTYPE,tpi.AMOUNT AS BALANCE,tpi.CID AS CID,tu.USERNAME AS USERNAME,tu.PHONE AS PHONE, ");
		sql.append(" tpd.TID AS TID,tpd.AID AS AID,tpd.AMOUNT AS AMOUNT,tpd.CREATETIME AS CREATETIME,tpd.DEALTIME AS DEALTIME,tpd.DEALER AS DEALER,tpd.DEALSTATUS AS DEALSTATUS, ");
		sql.append(" tpd.PID AS PID,tpd.STATUS AS STATUS,tpd.MARK AS MARK FROM T_PASSBOOK_DRAW tpd LEFT JOIN T_PASSBOOK_PAY tpp ON tpd.PID = tpp.PID LEFT JOIN T_PASSBOOK_PAY ftpp ON tpp.PPID = ftpp.PID ");
		sql.append(" LEFT JOIN T_PASSBOOK_INFO tpi ON ftpp.PASSID = tpi.PASSID LEFT JOIN T_USER tu ON tu.CID = tpi.CID ");
		sql.append(" WHERE 1 = 1 ");
		
		List<Object> args = new ArrayList<Object>();
		addStandardSqlWithParameter(sql, "tpd.TID", qContext.getParameter("id"),args);
		addStandardSqlWithParameter(sql, "tpd.AID", qContext.getParameter("aid"),args);
		addStandardSqlWithParameter(sql, "tpd.AMOUNT", qContext.getParameter("amount"),args);
		addStandardSqlWithParameter(sql, "tpd.CREATETIME", qContext.getParameter("createtime"),args);
		addStandardSqlWithParameter(sql, "tpd.DEALTIME", qContext.getParameter("dealtime"),args);
		addStandardSqlWithParameter(sql, "tpd.DEALER", qContext.getParameter("dealer"),args);
		addStandardSqlWithParameter(sql, "tpd.DEALSTATUS", qContext.getParameter("dealstatus"),args);
		addStandardSqlWithParameter(sql, "tpd.PID", qContext.getParameter("pid"),args);
		addStandardSqlWithParameter(sql, "tpd.STATUS", qContext.getParameter("status"),args);
		addStandardSqlWithParameter(sql, "tpd.MARK", qContext.getParameter("mark"),args);
		addStandardSqlWithParameter(sql, "ftpp.PASSID", qContext.getParameter("passid"),args);
		addStandardSqlWithParameter(sql, "ftpp.PAYTIME", qContext.getParameter("paytime"),args);
		addStandardSqlWithParameter(sql, "tpi.PASSTYPE", qContext.getParameter("passtype"),args);
		addStandardSqlWithParameter(sql, "tpi.AMOUNT", qContext.getParameter("balance"),args);
		addStandardSqlWithParameter(sql, "tpi.CID", qContext.getParameter("cid"),args);
		addStandardSqlWithParameter(sql, "tu.USERNAME", qContext.getParameter("username"),args);
		addStandardSqlWithParameter(sql, "tu.PHONE", qContext.getParameter("phone"),args);
		qContext.setParamList(args);
		if (qContext.getPage().getPageIndex() < 0) {
			List<TPassbookDrawEx> list = getJdbcTemplate().query(sql.toString(),CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
			qContext.getQueryResult().setResult(list);
			qContext.getQueryResult().setTotalSize(list.size());
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("the count sql str is  : " + countSql);
			// 获取记录总数
			@SuppressWarnings("deprecation")
			int count = getJdbcTemplate().queryForInt(countSql,CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray());
			qContext.getQueryResult().setTotalSize(count);
			qContext.getPage().setTotalSize(count);

			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),qContext.getPage());
			log.info("the page sql str is  : " + pageSql);
			// 获取分页后的记录数量
			List<TPassbookDrawEx> list = getJdbcTemplate().query(pageSql,CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray(), rowMapper);
			qContext.getQueryResult().setResult(list);
		}
		return qContext;
	}

}
