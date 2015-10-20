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

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TPhonePackage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IPhonePackageDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月20日 上午10:56:58
 */

@Repository
public class PhonePackageDAOImpl extends BaseJdbcDao<TPhonePackage> implements IPhonePackageDAO {

	private static final String INSERT_SQL = " INSERT INTO T_PHONE_PACKAGE (ID,PID,PNAME,PHONE,CTYPE,CID,CNAME,CREATOR,CREATEDATE,UPDATEDATE) VALUES (:id,:pid,:pname,:phone,:ctype,:cid,:cname,:creator,:createdate,:updatedate) ";
	private static final String UPDATE_SQL = " UPDATE T_PHONE_PACKAGE SET PID = :pid,PNAME = :pname,PHONE = :phone,CTYPE = :ctype,CID = :cid,CNAME = :cname,CREATOR = :creator,CREATEDATE= :createdate,UPDATEDATE = :updatedate WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_PHONE_PACKAGE WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,PID,PNAME,PHONE,CTYPE,CID,CNAME,CREATOR,CREATEDATE,UPDATEDATE FROM T_PHONE_PACKAGE ";
	
	private String dynamicJoinSqlWithEntity(TPhonePackage entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
	
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		addNameParamerSqlWithProperty(sql, "pname", "PNAME", entity.getPname());
		addNameParamerSqlWithProperty(sql, "phone", "PHONE", entity.getPhone());
		addNameParamerSqlWithProperty(sql, "ctype", "CTYPE", entity.getCtype());
		addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "cname", "CNAME", entity.getCname());
		addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		addNameParamerSqlWithProperty(sql, "createdate", "CREATEDATE", entity.getCreatedate());
		addNameParamerSqlWithProperty(sql, "updatedate", "UPDATEDATE", entity.getUpdatedate());
		return sql.toString();
	}	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TPhonePackage entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TPhonePackage entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TPhonePackage entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TPhonePackage entity) {
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
	public TPhonePackage query(TPhonePackage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TPhonePackage query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TPhonePackage> queryForList(TPhonePackage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		dynamicJoinSqlWithEntity(entity,sql);
		sql.append(" ORDER BY ID ASC");
		return super.queryForList(sql.toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TPhonePackage> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TPhonePackage> queryListForPagination(
			QueryContext<TPhonePackage> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TPhonePackage mapRow(ResultSet rs, int rowNum) throws SQLException {
		TPhonePackage bean = new TPhonePackage();
		
		bean.setId(rs.getString("ID"));
		bean.setPid(rs.getString("PID"));
		bean.setPname(rs.getString("PNAME"));
		bean.setPhone(rs.getString("PHONE"));
		bean.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
		bean.setCid(rs.getString("CID"));
		bean.setCname(rs.getString("CNAME"));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
		bean.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		
		return bean;
	}
	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.system.IPhonePackageDAO#queryPhonePackageListByPid(java.lang.String)  
	 */
	@Override
	public List<TPhonePackage> queryPhonePackageListByPid(String pid) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<String> args = new ArrayList<String>();
		if(StringUtils.isNotEmpty(pid)){
			sql.append(" AND PID = ? ");
			args.add(pid);
		}
		sql.append(" ORDER BY ID ASC");
		return super.queryForList(sql.toString(), args);
	}

}
