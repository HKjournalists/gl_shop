package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.IContractCancelDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date  : 2014年9月2日 下午2:48:45
 */

@Repository
public class ContractCancelDAOImpl extends BaseJdbcDao<TOrderCancel> implements
		IContractCancelDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_CANCEL (CID,LID,CANCELER,CANCELTYPE,CANCELTIME,REASON,REMARK) VALUES (:id,:lid,:canceler,:canceltype,:canceltime,:reason,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_CANCEL SET LID = :lid,CANCELER = :canceler,CANCELTYPE = :canceltype,CANCELTIME = :canceltime,REASON = :reason,REMARK = :remark WHERE CID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_CANCEL WHERE CID = :id ";
	private static final String SELECT_SQL = " SELECT CID,LID,CANCELER,CANCELTYPE,CANCELTIME,REASON,REMARK FROM T_ORDER_CANCEL ";
	
	private String dynamicJoinSqlWithEntity(TOrderCancel entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "CID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "lid", "LID", entity.getLid());
		this.addNameParamerSqlWithProperty(sql, "canceler", "CANCELER", entity.getLid());
		this.addNameParamerSqlWithProperty(sql, "canceltype", "CANCELTYPE", entity.getCanceltype());
		this.addNameParamerSqlWithProperty(sql, "reason", "REASON", entity.getReason());
		//CANCELTIME = :canceltime
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TOrderCancel entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TOrderCancel entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TOrderCancel entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOrderCancel entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(com.appabc.common.base.bean.BaseBean)  
	 */
	public TOrderCancel query(TOrderCancel entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)  
	 */
	public TOrderCancel query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE CID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOrderCancel> queryForList(TOrderCancel entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)  
	 */
	public List<TOrderCancel> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "lid", "LID", args.get("lid"));
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOrderCancel> queryListForPagination(
			QueryContext<TOrderCancel> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TOrderCancel mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderCancel entity = new TOrderCancel();
		
		entity.setId(rs.getString("CID"));
		entity.setLid(rs.getString("LID"));
		entity.setCanceler(rs.getString("CANCELER"));
		entity.setCanceltype(rs.getString("CANCELTYPE"));
		entity.setCanceltime(rs.getTimestamp("CANCELTIME"));
		entity.setReason(rs.getString("REASON"));
		entity.setRemark(rs.getString("REMARK"));
		
		return entity;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractCancelDAO#getCancelListByOID(java.lang.String)  
	 */
	public List<TOrderCancel> getCancelContractListByOID(String oid) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(" SELECT toc.* FROM T_ORDER_CANCEL toc LEFT JOIN T_ORDER_OPERATIONS too on too.LID = toc.LID ");
		sql.append(" where  1=1  ");//too.OID
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "too.OID", oid, args);
		return super.queryForList(sql.toString(), args);
	}

}
