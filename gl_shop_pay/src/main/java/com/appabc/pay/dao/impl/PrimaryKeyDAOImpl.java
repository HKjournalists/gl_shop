/**  
 * com.appabc.pay.dao.impl.PrimaryKeyDAOImpl.java  
 *   
 * 2014年10月8日 下午5:36:52  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.dao.impl;

import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.pay.bean.TPk;
import com.appabc.pay.dao.IPrimaryKeyDAO;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月8日 下午5:36:52
 */

@Repository
public class PrimaryKeyDAOImpl extends BaseJdbcDao<TPk> implements IPrimaryKeyDAO {

private static final String INSERT_SQL = " INSERT INTO T_PK (ID,BID,MAXVAL,MINVAL,CURVAL,LENGTH,BPREFIX,BSUFFIX,CREATETIME) VALUES (:id,:bid,:maxval,:minval,:curval,:length,:bprefix,:bsuffix,:createtime) ";
	
	private static final String DELETEBYID_SQL = " DELETE FROM T_PK WHERE ID = :id ";
	
	private static final String UPDATEENTITY_SQL = " UPDATE T_PK SET BID = :bid,MAXVAL = :maxval,MINVAL = :minval,CURVAL = :curval,LENGTH = :length,BPREFIX = :bprefix,BSUFFIX = :bsuffix,CREATETIME = :createtime WHERE ID = :id ";
	
	private static final String QUERYBYID_SQL = " SELECT ID,BID,MAXVAL,MINVAL,CURVAL,LENGTH,BPREFIX,BSUFFIX,CREATETIME FROM T_PK WHERE ID = :id ";
	
	private static final StringBuffer QUERYBYENTITY_SQL = new StringBuffer(" SELECT ID,BID,MAXVAL,MINVAL,CURVAL,LENGTH,BPREFIX,BSUFFIX,CREATETIME FROM T_PK WHERE 1=1 ");
	
	private static final StringBuffer QUERYBYARGS_SQL = new StringBuffer(" SELECT ID,BID,MAXVAL,MINVAL,CURVAL,LENGTH,BPREFIX,BSUFFIX,CREATETIME FROM T_PK WHERE 1=1 ");
	
	public TPk mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPk t = new TPk();
		t.setId(rs.getString("ID"));
		t.setBid(rs.getString("BID"));
		t.setMinval(rs.getInt("MINVAL"));
		t.setMaxval(rs.getInt("MAXVAL"));
		t.setCurval(rs.getInt("CURVAL"));
		t.setLength(rs.getInt("LENGTH"));
		t.setBprefix(rs.getString("BPREFIX"));
		t.setBsuffix(rs.getString("BSUFFIX"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		return t;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#createAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TPk entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TPk entity) {
		super.delete(DELETEBYID_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TPk query(TPk entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(QUERYBYENTITY_SQL);
		this.addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		this.addNameParamerSqlWithProperty(sql, "bid", "BID", entity.getBid());
		this.addNameParamerSqlWithProperty(sql, "bprefix", "BPREFIX", entity.getBprefix());
		this.addNameParamerSqlWithProperty(sql, "bsuffix", "BSUFFIX", entity.getBsuffix());
		return super.query(sql.toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TPk> queryForList(TPk entity) {
		return super.queryForList(QUERYBYARGS_SQL.toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#save(com.appabc.bean.pvo.TPk)  
	 */
	public void save(TPk tpk) {
		super.save(INSERT_SQL, tpk);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#delete(java.lang.String)  
	 */
	public void delete(String id) {
		super.delete(DELETEBYID_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#update(com.appabc.bean.pvo.TPk)  
	 */
	public void update(TPk entity) {
		super.update(UPDATEENTITY_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#query(java.lang.String)  
	 */
	public TPk query(String id) {
		return super.query(QUERYBYID_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#queryForList(java.util.Map)  
	 */
	public List<TPk> queryForList(Map<String, ?> args) {
		return super.queryForList(QUERYBYARGS_SQL.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.pk.IPKDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TPk> queryListForPagination(QueryContext<TPk> qContext) {
		return super.queryListForPagination(QUERYBYARGS_SQL.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		this.delete(String.valueOf(id));
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TPk query(Serializable id) {
		return this.query(String.valueOf(id));
	}

}
