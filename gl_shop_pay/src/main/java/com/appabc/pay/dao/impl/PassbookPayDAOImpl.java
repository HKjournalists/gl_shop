/**  
 * com.appabc.pay.dao.impl.PassbookPayDAPImpl.java  
 *   
 * 2014年9月17日 上午11:15:48  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.KeyHolder;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.dao.IPassbookPayDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月17日 上午11:15:48
 */

public class PassbookPayDAOImpl extends BaseJdbcDao<TPassbookPay> implements
		IPassbookPayDAO {
	
	private static final String INSERT_SQL = " INSERT INTO T_PASSBOOK_PAY (PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,NEEDAMOUNT,DIRECTION,PAYTYPE,PAYTIME,STATUS,CREATEDATE,UPDATEDATE,CREATOR,DEVICES,REMARK,BALANCE,PPID) VALUES (:id,:passid,:oid,:otype,:payno,:name,:amount,:needamount,:direction,:paytype,:paytime,:status,:createdate,:updatedate,:creator,:devices,:remark,:balance,:ppid) ";
	private static final String UPDATE_SQL = " UPDATE T_PASSBOOK_PAY SET PASSID = :passid,OID = :oid,OTYPE = :otype,PAYNO = :payno,NAME = :name,AMOUNT = :amount,NEEDAMOUNT = :needamount,DIRECTION = :direction,PAYTYPE = :paytype,PAYTIME = :paytime,STATUS = :status,CREATEDATE = :createdate,UPDATEDATE = :updatedate,CREATOR = :creator,DEVICES = :devices,REMARK = :remark,BALANCE = :balance,PPID = :ppid  WHERE PID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PASSBOOK_PAY WHERE PID = :id ";
	private static final String SELECT_SQL = " SELECT PID,PASSID,OID,OTYPE,PAYNO,NAME,AMOUNT,NEEDAMOUNT,DIRECTION,PAYTYPE,PAYTIME,STATUS,CREATEDATE,UPDATEDATE,CREATOR,DEVICES,REMARK,BALANCE,PPID FROM T_PASSBOOK_PAY ";
	
	private static final String SELECT_SQL_LEFTJOIN_PASSBOOKINFO = " SELECT PAY.PID AS PID,PAY.PASSID AS PASSID,PAY.OID AS OID,OI.REMARK AS CONTRACTNAME,PAY.OTYPE AS OTYPE,PAY.PAYNO AS PAYNO,PAY.NAME AS NAME,PAY.AMOUNT AS AMOUNT,PAY.NEEDAMOUNT AS NEEDAMOUNT,PAY.DIRECTION AS DIRECTION,PAY.PAYTYPE AS PAYTYPE,PAY.PAYTIME AS PAYTIME,PAY.STATUS AS STATUS,PAY.CREATEDATE AS CREATEDATE,PAY.UPDATEDATE AS UPDATEDATE,PAY.CREATOR AS CREATOR,PAY.DEVICES AS DEVICES,PAY.REMARK AS REMARK,PAY.BALANCE AS BALANCE,PAY.PPID AS PPID FROM T_PASSBOOK_PAY PAY LEFT JOIN T_PASSBOOK_INFO INFO ON INFO.PASSID = PAY.PASSID LEFT JOIN T_ORDER_INFO OI ON OI.OID=PAY.OID ";
	
	private String dynamicJoinSqlWithEntity(TPassbookPay entity,StringBuilder sql){
		if(entity == null || sql == null || sql.length() <= 0){
			return StringUtils.EMPTY;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "PID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "passid", "PASSID", entity.getPassid());
		this.addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		this.addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		this.addNameParamerSqlWithProperty(sql, "payno", "PAYNO", entity.getPayno());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", entity.getName());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		this.addNameParamerSqlWithProperty(sql, "needamount", "NEEDAMOUNT", entity.getNeedamount());
		this.addNameParamerSqlWithProperty(sql, "direction", "DIRECTION", entity.getDirection());
		this.addNameParamerSqlWithProperty(sql, "paytype", "PAYTYPE", entity.getPaytype());
		this.addNameParamerSqlWithProperty(sql, "paytime", "PAYTIME", entity.getPaytime());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		this.addNameParamerSqlWithProperty(sql, "createdate", "CREATEDATE", entity.getCreatedate());
		this.addNameParamerSqlWithProperty(sql, "updatedate", "UPDATEDATE", entity.getUpdatedate());
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		this.addNameParamerSqlWithProperty(sql, "devices", "DEVICES", entity.getDevices());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		this.addNameParamerSqlWithProperty(sql, "balance", "BALANCE", entity.getBalance());
		this.addNameParamerSqlWithProperty(sql, "ppid", "PPID", entity.getPpid());
		
		sql.append(" ORDER BY PAYTIME DESC ");
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TPassbookPay entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPassbookPay entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TPassbookPay entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPassbookPay entity) {
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
	public TPassbookPay query(TPassbookPay entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPassbookPay query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE PID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPassbookPay> queryForList(TPassbookPay entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TPassbookPay> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		//this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPassbookPay> queryListForPagination(
			QueryContext<TPassbookPay> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL_LEFTJOIN_PASSBOOKINFO);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		addStandardSqlWithParameter(sql, "INFO.CID", qContext.getParameter("cid"), args);
		addStandardSqlWithParameter(sql, "INFO.PASSTYPE", qContext.getParameter("type"), args);
		addStandardSqlWithParameter(sql, "PAY.OTYPE", qContext.getParameter("oType"), args);
		addStandardSqlWithParameter(sql, "PAY.DIRECTION", qContext.getParameter("direction"), args);
		//操作开始时间  操作结束时间
		QueryContext.DateQueryEntry dateQueryEntry = (QueryContext.DateQueryEntry)qContext.getParameter("paytime");
		if(dateQueryEntry != null){			
			addStandardSqlWithDateQuery(sql, "PAY.PAYTIME", dateQueryEntry.getStartDate(), dateQueryEntry.getEndDate());
		}
		sql.append(" ORDER BY PAY.PAYTIME DESC, PAY.PID DESC ");
		qContext.setParamList(args);
		return super.queryListForPaginationForStandardSQL(sql.toString(), qContext);
	}
	
	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TPassbookPay mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPassbookPay entity = new TPassbookPay();
		
		entity.setId(rs.getString("PID"));
		entity.setPassid(rs.getString("PASSID"));
		entity.setOid(rs.getString("OID"));
		entity.setOtype(TradeType.enumOf(rs.getString("OTYPE")));
		entity.setPayno(rs.getString("PAYNO"));
		entity.setName(rs.getString("NAME"));
		entity.setAmount(rs.getDouble("AMOUNT"));
		entity.setNeedamount(rs.getDouble("NEEDAMOUNT"));
		entity.setDirection(PayDirection.enumOf(rs.getInt("DIRECTION")));
		entity.setPaytype(PayWay.enumOf(rs.getString("PAYTYPE")));
		entity.setPaytime(rs.getTimestamp("PAYTIME"));
		entity.setStatus(rs.getString("STATUS"));
		entity.setCreatedate(rs.getTimestamp("CREATEDATE"));
		entity.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		entity.setCreator(rs.getString("CREATOR"));
		entity.setDevices(DeviceType.enumOf(rs.getString("DEVICES")));
		entity.setRemark(rs.getString("REMARK"));
		entity.setBalance(rs.getDouble("BALANCE"));
		entity.setPpid(rs.getString("PPID"));
		entity.setPpid(rs.getString("PPID"));
		try { entity.setContractname(rs.getString("CONTRACTNAME")); } catch (SQLException e) {}
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPassbookPayDAO#getPayRecordListWithOid(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, java.lang.String, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	@Override
	public List<TPassbookPay> getPayRecordListWithOid(String cid,
			PurseType type, String oid, PayDirection payDirection) {
		if(StringUtils.isEmpty(oid)){
			return null;
		}
		StringBuilder sql = new StringBuilder(SELECT_SQL_LEFTJOIN_PASSBOOKINFO);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "INFO.CID", cid, args);
		this.addStandardSqlWithParameter(sql, "PAY.OID", oid, args);
		this.addStandardSqlWithParameter(sql, "PAY.DIRECTION", payDirection, args);
		if(StringUtils.isNotEmpty(cid) && type != null){
			this.addStandardSqlWithParameter(sql, "INFO.PASSTYPE", type.getVal(), args);
		}else if(StringUtils.isNotEmpty(cid) && type == null){
			sql.append(" AND ( INFO.PASSTYPE = '"+PurseType.GUARANTY.getVal()+"' OR INFO.PASSTYPE = '"+PurseType.DEPOSIT.getVal()+"' )");
		}
		sql.append(" ORDER BY PAY.PAYTIME DESC, PAY.OID DESC ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.pay.dao.IPassbookPayDAO#queryPayListWithParams(java.lang.String, com.appabc.bean.enums.PurseInfo.PurseType, java.lang.String, com.appabc.bean.enums.PurseInfo.TradeType, com.appabc.bean.enums.PurseInfo.PayDirection)  
	 */
	@Override
	public List<TPassbookPay> queryPayListWithParams(String cid,
			PurseType pType, String oid, TradeType tType, PayDirection direction) {
		StringBuilder sql = new StringBuilder(SELECT_SQL_LEFTJOIN_PASSBOOKINFO);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "INFO.CID", cid, args);
		this.addStandardSqlWithParameter(sql, "INFO.PASSTYPE", pType.getVal(), args);
		this.addStandardSqlWithParameter(sql, "PAY.OID", oid, args);
		this.addStandardSqlWithParameter(sql, "PAY.DIRECTION", direction.getVal(), args);
		this.addStandardSqlWithParameter(sql, "PAY.OTYPE", tType.getVal(), args);
		sql.append(" ORDER BY PAY.PAYTIME DESC, PAY.OID DESC ");
		return super.queryForList(sql.toString(), args);
	}

}
