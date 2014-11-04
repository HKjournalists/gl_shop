package com.appabc.datas.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.system.IMobileAppVersionDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午9:12:48
 */

@Component(value="IMobileAppVersionDAO")
public class MobileAppVersionDAOImpl extends BaseJdbcDao<TMobileAppVersion> implements
		IMobileAppVersionDAO {

	private static final String INSERT_SQL = " INSERT INTO T_MOBILE_APP_VERSION (BID,DEVICES,LASTNAME,LASTEST,LASTNO,MARK,FILESIZE,DOWNURL,ISFORCE,FEQUENCY,UNIT,UPDATER,UPDATETIME) VALUES (:id,:devices,:lastname,:lastest,:lastno,:mark,:filesize,:downurl,:isforce,:fequency,:unit,:updater,:updatetime) ";
	private static final String UPDATE_SQL = " UPDATE T_MOBILE_APP_VERSION SET DEVICES = :devices,LASTNAME = :lastname,LASTEST = :lastest,LASTNO = :lastno,MARK = :mark,FILESIZE = :filesize,DOWNURL = :downurl,ISFORCE= :isforce,FEQUENCY = :fequency,UNIT = :unit,UPDATER = :updater,UPDATETIME = :updatetime WHERE BID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_MOBILE_APP_VERSION WHERE BID = :id ";
	private static final String SELECT_SQL = " SELECT BID,DEVICES,LASTNAME,LASTEST,LASTNO,MARK,FILESIZE,DOWNURL,ISFORCE,FEQUENCY,UNIT,UPDATER,UPDATETIME FROM T_MOBILE_APP_VERSION ";
	
	private String dynamicJoinSqlWithEntity(TMobileAppVersion entity,StringBuffer sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "BID", entity.getId());
		addNameParamerSqlWithProperty(sql, "devices", "DEVICES", entity.getDevices());
		addNameParamerSqlWithProperty(sql, "lastname", "LASTNAME", entity.getLastname());
		addNameParamerSqlWithProperty(sql, "lastest", "LASTEST", entity.getLastest());
		addNameParamerSqlWithProperty(sql, "lastno", "LASTNO", entity.getLastno());
		addNameParamerSqlWithProperty(sql, "mark", "MARK", entity.getMark());
		addNameParamerSqlWithProperty(sql, "filesize", "FILESIZE", entity.getFilesize());
		addNameParamerSqlWithProperty(sql, "downurl", "DOWNURL", entity.getDownurl());
		addNameParamerSqlWithProperty(sql, "isforce", "ISFORCE", entity.getIsforce());
		addNameParamerSqlWithProperty(sql, "fequency", "FEQUENCY", entity.getFequency());
		addNameParamerSqlWithProperty(sql, "unit", "UNIT", entity.getUnit());
		addNameParamerSqlWithProperty(sql, "updater", "UPDATER", entity.getUpdater());
		addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", entity.getUpdatetime());
		
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TMobileAppVersion entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TMobileAppVersion entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TMobileAppVersion entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TMobileAppVersion entity) {
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
	public TMobileAppVersion query(TMobileAppVersion entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TMobileAppVersion query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE BID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TMobileAppVersion> queryForList(TMobileAppVersion entity) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TMobileAppVersion> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TMobileAppVersion> queryListForPagination(
			QueryContext<TMobileAppVersion> qContext) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TMobileAppVersion mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TMobileAppVersion bean = new TMobileAppVersion();
		
		bean.setId(rs.getString("BID"));
		bean.setDevices(rs.getString("DEVICES"));
		bean.setLastname(rs.getString("LASTNAME"));
		bean.setLastest(rs.getString("LASTEST"));
		bean.setLastno(rs.getInt("LASTNO"));
		bean.setMark(rs.getString("MARK"));
		bean.setFilesize(rs.getString("FILESIZE"));
		bean.setDownurl(rs.getString("DOWNURL"));
		bean.setIsforce(rs.getString("ISFORCE"));
		bean.setFequency(rs.getInt("FEQUENCY"));
		bean.setUnit(rs.getString("UNIT"));
		bean.setUpdater(rs.getString("UPDATER"));
		bean.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		
		return bean;
	}

}
