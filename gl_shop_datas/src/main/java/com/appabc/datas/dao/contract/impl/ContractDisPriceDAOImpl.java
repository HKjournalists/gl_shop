package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.IContractDisPriceDAO;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年9月2日 下午2:56:29
 */

@Repository
public class ContractDisPriceDAOImpl extends BaseJdbcDao<TOrderDisPrice>
		implements IContractDisPriceDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_DIS_PRICE (CID,LID,TYPE,CANCELER,CANCELTIME,REASON,BEGINAMOUNT,ENDAMOUNT,BEGINNUM,ENDNUM,PUNREASON,PUNDAY,REMARK) VALUES (:id,:lid,:type,:canceler,:canceltime,:reason,:beginamount,:endamount,:beginnum,:endnum,:punreason,:punday,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_DIS_PRICE SET LID = :lid,TYPE = :type,CANCELER = :canceler,CANCELTIME = :canceltime,REASON = :reason,BEGINAMOUNT = :beginamount,ENDAMOUNT = :endamount,BEGINNUM = :beginnum,ENDNUM = :endnum,PUNREASON = :punreason,PUNDAY = :punday,REMARK = :remark WHERE CID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_DIS_PRICE WHERE CID = :id ";
	private static final String SELECT_SQL = " SELECT CID,LID,TYPE,CANCELER,CANCELTIME,REASON,BEGINAMOUNT,ENDAMOUNT,BEGINNUM,ENDNUM,PUNREASON,PUNDAY,REMARK FROM T_ORDER_DIS_PRICE ";

	private String dynamicJoinSqlWithEntity(TOrderDisPrice entity,
			StringBuffer sql) {
		if (entity == null || sql == null || sql.length() <= 0) {
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "CID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "lid", "LID", entity.getLid());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE",
				entity.getType());
		this.addNameParamerSqlWithProperty(sql, "canceler", "CANCELER",
				entity.getCanceler());
		// CANCELTIME = :canceltime,
		this.addNameParamerSqlWithProperty(sql, "reason", "REASON",
				entity.getReason());
		this.addNameParamerSqlWithProperty(sql, "beginamount", "BEGINAMOUNT",
				entity.getBeginamount());
		this.addNameParamerSqlWithProperty(sql, "endamount", "ENDAMOUNT",
				entity.getEndamount());
		this.addNameParamerSqlWithProperty(sql, "beginnum", "BEGINNUM",
				entity.getBeginnum());
		this.addNameParamerSqlWithProperty(sql, "endnum", "ENDNUM",
				entity.getEndnum());
		this.addNameParamerSqlWithProperty(sql, "punreason", "PUNREASON",
				entity.getPunreason());
		this.addNameParamerSqlWithProperty(sql, "punday", "PUNDAY",
				entity.getPunday());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK",
				entity.getRemark());
		return sql.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void save(TOrderDisPrice entity) {
		super.save(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.
	 * common.base.bean.BaseBean)
	 */
	public KeyHolder saveAutoGenerateKey(TOrderDisPrice entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void update(TOrderDisPrice entity) {
		super.update(UPDATE_SQL, entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean
	 * .BaseBean)
	 */
	public void delete(TOrderDisPrice entity) {
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
	 * com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.
	 * BaseBean)
	 */
	public TOrderDisPrice query(TOrderDisPrice entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)
	 */
	public TOrderDisPrice query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE CID = :id  ");
		return super.query(sql.toString(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	public List<TOrderDisPrice> queryForList(TOrderDisPrice entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super
				.queryForList(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)
	 */
	public List<TOrderDisPrice> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.
	 * common.base.QueryContext)
	 */
	public QueryContext<TOrderDisPrice> queryListForPagination(
			QueryContext<TOrderDisPrice> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	public TOrderDisPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderDisPrice todp = new TOrderDisPrice();

		todp.setId(rs.getString("CID"));
		todp.setLid(rs.getString("LID"));
		todp.setType(rs.getInt("TYPE"));
		todp.setCanceler(rs.getString("CANCELER"));
		todp.setCanceltime(rs.getTimestamp("CANCELTIME"));
		todp.setReason(rs.getString("REASON"));
		todp.setBeginamount(rs.getFloat("BEGINAMOUNT"));
		todp.setEndamount(rs.getFloat("ENDAMOUNT"));
		todp.setBeginnum(rs.getFloat("BEGINNUM"));
		todp.setEndnum(rs.getFloat("ENDNUM"));
		todp.setPunreason(rs.getString("PUNREASON"));
		todp.setPunday(rs.getInt("PUNDAY"));
		todp.setRemark(rs.getString("REMARK"));

		return todp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.datas.dao.contract.IContractDisPriceDAO#queryForList(java.
	 * lang.String)
	 */
	public List<TOrderDisPrice> queryForList(String contractId) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE LID IN (SELECT LID from T_ORDER_OPERATIONS where OID = ? ) ORDER BY CANCELTIME ASC ");
		return super.queryForList(sql.toString(),
				Collections.singletonList(contractId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.datas.dao.contract.IContractDisPriceDAO#queryGoodsDisPriceHisList
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<TContractDisPriceOperation> queryGoodsDisPriceHisList(
			String contractId, String operateId, String disPriceId,String disPriceType) {
		StringBuffer sql = new StringBuffer(
				" SELECT too.LID as LID,too.OID as OID,too.OPERATOR as OPERATOR,too.OPERATIONTIME as OPERATIONTIME, ");
		sql.append(" too.TYPE as TYPE, too.RESULT as RESULT,too.PLID as PLID,too.REMARK as REMARK, ");
		sql.append(" todp.CID as CID,todp.LID as DLID,todp.TYPE as DTYPE, todp.CANCELER as CANCELER,todp.CANCELTIME as CANCELTIME, ");
		sql.append(" todp.REASON as REASON,todp.BEGINAMOUNT as BEGINAMOUNT,todp.ENDAMOUNT as ENDAMOUNT, todp.BEGINNUM as BEGINNUM, ");
		sql.append(" todp.ENDNUM as ENDNUM, todp.PUNREASON as PUNREASON,todp.PUNDAY as PUNDAY,todp.REMARK as DREMARK ");
		sql.append(" FROM T_ORDER_OPERATIONS too LEFT JOIN T_ORDER_DIS_PRICE todp ON too.LID = todp.LID ");
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, " too.OID ", contractId, args);
		this.addStandardSqlWithParameter(sql, " too.LID ", operateId, args);
		this.addStandardSqlWithParameter(sql, " todp.CID ", disPriceId, args);
		this.addStandardSqlWithParameter(sql, " todp.TYPE ", disPriceType, args);
		sql.append(" and too.TYPE in (5 , 6 , 7) ");
		sql.append(" ORDER BY too.OPERATIONTIME ASC ");
		log.info("the sql str is : " + sql);
		return getJdbcTemplate().query(sql.toString(), args.toArray(),
				new RowMapper<TContractDisPriceOperation>() {
					public TContractDisPriceOperation mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						TContractDisPriceOperation bean = new TContractDisPriceOperation();

						bean.setId(rs.getString("LID"));
						bean.setOid(rs.getString("OID"));
						bean.setOperator(rs.getString("OPERATOR"));
						bean.setOperationtime(rs.getTimestamp("OPERATIONTIME"));
						bean.setType(rs.getString("TYPE"));
						bean.setResult(rs.getString("RESULT"));
						bean.setPlid(rs.getString("PLID"));
						bean.setRemark(rs.getString("REMARK"));

						bean.setCid(rs.getString("CID"));
						bean.setDlid(rs.getString("DLID"));
						bean.setDtype(rs.getInt("DTYPE"));
						bean.setCanceler(rs.getString("CANCELER"));
						bean.setCanceltime(rs.getTimestamp("CANCELTIME"));
						bean.setReason(rs.getString("REASON"));
						bean.setBeginamount(rs.getFloat("BEGINAMOUNT"));
						bean.setEndamount(rs.getFloat("ENDAMOUNT"));
						bean.setBeginnum(rs.getFloat("BEGINNUM"));
						bean.setEndnum(rs.getFloat("ENDNUM"));
						bean.setPunreason(rs.getString("PUNREASON"));
						bean.setPunday(rs.getInt("PUNDAY"));
						bean.setDremark(rs.getString("DREMARK"));

						return bean;
					}
				});
	}

}
