package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TOrderCostdetail;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.contract.IContractCostDetailDAO;

/**
 * @Description :  
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create Date : 2014年9月2日 下午3:34:16
 */

@Repository
public class ContractCostDetailDAOImpl extends BaseJdbcDao<TOrderCostdetail> implements
		IContractCostDetailDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_COSTDETAIL (ID,OID,PID,FID,NAME,AMOUNT,CREATEDATE,UPDATEDATE,CREATOR,REMARK) VALUES (:id,:oid,:pid,:fid,:name,:amount,:createdate,:updatedate,:creator,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_COSTDETAIL SET OID = :oid,PID = :pid,FID = :fid,NAME = :name,AMOUNT = :amount,CREATEDATE = :createdate,UPDATEDATE = :updatedate,CREATOR = :creator,REMARK = :remark WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_COSTDETAIL WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,OID,PID,FID,NAME,AMOUNT,CREATEDATE,UPDATEDATE,CREATOR,REMARK FROM T_ORDER_COSTDETAIL ";
	
	private String dynamicJoinSqlWithEntity(TOrderCostdetail entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		this.addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		this.addNameParamerSqlWithProperty(sql, "fid", "FID", entity.getFid());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", entity.getName());
		this.addNameParamerSqlWithProperty(sql, "amount", "AMOUNT", entity.getAmount());
		//CREATEDATE = :createdate
		//UPDATEDATE = :updatedate
		this.addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#create(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TOrderCostdetail entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TOrderCostdetail entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TOrderCostdetail entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TOrderCostdetail entity) {
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
	public TOrderCostdetail query(TOrderCostdetail entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#read(java.io.Serializable)  
	 */
	public TOrderCostdetail query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TOrderCostdetail> queryForList(TOrderCostdetail entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readForList(java.util.Map)  
	 */
	public List<TOrderCostdetail> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#readListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TOrderCostdetail> queryListForPagination(
			QueryContext<TOrderCostdetail> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TOrderCostdetail mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderCostdetail tocd = new TOrderCostdetail();
		
		tocd.setId(rs.getString("ID"));
		tocd.setOid(rs.getString("OID"));
		tocd.setPid(rs.getString("PID"));
		tocd.setFid(rs.getString("FID"));
		tocd.setName(rs.getString("NAME"));
		tocd.setAmount(rs.getFloat("AMOUNT"));
		tocd.setCreatedate(rs.getTimestamp("CREATEDATE"));
		tocd.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		tocd.setCreator(rs.getString("CREATOR"));
		tocd.setRemark(rs.getString("REMARK"));
		
		return tocd;
	}

}
