package com.appabc.datas.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.ISystemActivityDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:56:34
 */

@Repository(value="ISystemActivityDAO")
public class SystemActivityDAOImpl extends BaseJdbcDao<TActivityJoin> implements ISystemActivityDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ACTIVITY_JOIN (CID,ARID,PHONE,NAME,REQ_NUM,CREATOR,CREATEDATE,UPDATEDATE,REMARK) VALUES (:cid,:arid,:phone,:name,:reqnum,:creator,:createdate,:updatedate,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_ACTIVITY_JOIN SET CID = :cid,ARID = :arid,PHONE = :phone,NAME = :name,REQ_NUM = :reqnum,CREATOR = :creator,CREATEDATE = :createdate,UPDATEDATE = :updatedate,REMARK = :remark WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ACTIVITY_JOIN WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,CID,ARID,PHONE,NAME,REQ_NUM,CREATOR,CREATEDATE,UPDATEDATE,REMARK FROM T_ACTIVITY_JOIN ";
	
	private String dynamicJoinSqlWithEntity(TActivityJoin entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "arid", "ARID", entity.getArid());
		addNameParamerSqlWithProperty(sql, "phone", "PHONE", entity.getPhone());
		addNameParamerSqlWithProperty(sql, "name", "NAME", entity.getName());
		addNameParamerSqlWithProperty(sql, "reqnum", "REQ_NUM", entity.getReqnum());
		addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		addNameParamerSqlWithProperty(sql, "createdate", "CREATEDATE", entity.getCreatedate());
		addNameParamerSqlWithProperty(sql, "updatedate", "UPDATEDATE", entity.getUpdatedate());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		
		return sql.toString();
	}	
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TActivityJoin entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TActivityJoin entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TActivityJoin entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TActivityJoin entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	@Override
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public TActivityJoin query(TActivityJoin entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TActivityJoin query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TActivityJoin> queryForList(TActivityJoin entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TActivityJoin> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TActivityJoin> queryListForPagination(QueryContext<TActivityJoin> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.system.ISystemActivityDAO#querySystemActivityByPhoneNum(java.lang.String)  
	 */
	@Override
	public List<TActivityJoin> querySystemActivityByPhoneNum(String phone) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<String> args = new ArrayList<String>();
		if(StringUtils.isNotEmpty(phone)){
			sql.append(" AND PHONE = ?  ");
			args.add(phone);
		}
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TActivityJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
		TActivityJoin bean = new TActivityJoin();
		
		bean.setId(rs.getString("ID"));
		bean.setCid(rs.getString("CID"));
		bean.setArid(rs.getString("ARID"));
		bean.setPhone(rs.getString("PHONE"));
		bean.setName(rs.getString("NAME"));
		bean.setReqnum(rs.getDouble("REQ_NUM"));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
		bean.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		bean.setRemark(rs.getString("REMARK"));
		
		return bean;
	}

}
