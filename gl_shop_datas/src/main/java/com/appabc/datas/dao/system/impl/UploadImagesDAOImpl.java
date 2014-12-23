package com.appabc.datas.dao.system.impl;

import com.appabc.bean.enums.FileInfo.FileCommitServer;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.FileInfo.FileStyle;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IUploadImagesDAO;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午9:10:11
 */

@Component(value="IUploadImagesDAO")
public class UploadImagesDAOImpl extends BaseJdbcDao<TUploadImages> implements
		IUploadImagesDAO {

	private static final String INSERT_SQL = " INSERT INTO T_UPLOAD_IMAGES (ID,OID,OTYPE,FNAME,FTYPE,FSIZE,FPATH,FULLPATH,FSERVERID,COMMITSERVER,CREATEDATE,PID,FSTYLE) VALUES (:id,:oid,:otype,:fname,:ftype,:fsize,:fpath,:fullpath,:fserverid,:commitserver,:createdate,:pid,:fstyle) ";
	private static final String UPDATE_SQL = " UPDATE T_UPLOAD_IMAGES SET OID = :oid,OTYPE = :otype,FNAME = :fname,FTYPE = :ftype,FSIZE = :fsize,FPATH = :fpath,FULLPATH = :fullpath,FSERVERID= :fserverid,COMMITSERVER = :commitserver,CREATEDATE = :createdate,PID=:pid,FSTYLE=:fstyle WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_UPLOAD_IMAGES WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,OID,OTYPE,FNAME,FTYPE,FSIZE,FPATH,FULLPATH,FSERVERID,COMMITSERVER,CREATEDATE,PID,FSTYLE FROM T_UPLOAD_IMAGES ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_UPLOAD_IMAGES WHERE 1=1 ";
	
	private String dynamicJoinSqlWithEntity(TUploadImages entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		addNameParamerSqlWithProperty(sql, "otype", "OTYPE", entity.getOtype());
		addNameParamerSqlWithProperty(sql, "fname", "FNAME", entity.getFname());
		addNameParamerSqlWithProperty(sql, "ftype", "FTYPE", entity.getFtype());
		addNameParamerSqlWithProperty(sql, "fsize", "FSIZE", entity.getFsize());
		addNameParamerSqlWithProperty(sql, "fpath", "FPATH", entity.getFpath());
		addNameParamerSqlWithProperty(sql, "fullpath", "FULLPATH", entity.getFullpath());
		addNameParamerSqlWithProperty(sql, "fserverid", "FSERVERID", entity.getFserverid());
		addNameParamerSqlWithProperty(sql, "commitserver", "COMMITSERVER", entity.getCommitserver());
		addNameParamerSqlWithProperty(sql, "createdate", "CREATEDATE", entity.getCreatedate());
		addNameParamerSqlWithProperty(sql, "pid", "PID", entity.getPid());
		addNameParamerSqlWithProperty(sql, "fstyle", "FSTYLE", entity.getFstyle());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TUploadImages entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TUploadImages entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TUploadImages entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TUploadImages entity) {
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
	public TUploadImages query(TUploadImages entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TUploadImages query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TUploadImages> queryForList(TUploadImages entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		dynamicJoinSqlWithEntity(entity,sql);
		sql.append(" ORDER BY ID ASC");
		return super.queryForList(sql.toString(), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TUploadImages> queryForList(Map<String, ?> args) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TUploadImages> queryListForPagination(
			QueryContext<TUploadImages> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TUploadImages mapRow(ResultSet rs, int rowNum) throws SQLException {
		TUploadImages bean = new TUploadImages();
		
		bean.setId(rs.getString("ID"));
		bean.setOid(rs.getString("OID"));
		bean.setOtype(FileOType.enumOf(rs.getString("OTYPE")));
		bean.setFname(rs.getString("FNAME"));
		bean.setFtype(rs.getString("FTYPE"));
		bean.setFsize(rs.getLong("FSIZE"));
		bean.setFpath(rs.getString("FPATH"));
		bean.setFullpath(rs.getString("FULLPATH"));
		bean.setFserverid(rs.getString("FSERVERID"));
		bean.setCommitserver(FileCommitServer.enumOf(rs.getString("COMMITSERVER")));
		bean.setCreatedate(rs.getTimestamp("CREATEDATE"));
		bean.setPid(rs.getInt("PID"));
		bean.setFstyle(FileStyle.enumOf(rs.getString("FSTYLE")));
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.system.IUploadImagesDAO#getCountByFpath(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getCountByFpath(String fPath) {
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		List<Object> args = new ArrayList<Object>();

		sql.append(" AND FPATH=?");
		args.add(fPath);

		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

}
