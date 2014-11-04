package com.appabc.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 31, 2014 11:36:04 AM
 */
@Repository
public class UserPermissionDao extends AbstractBaseSystemDao<UserPermission> implements RowMapper<UserPermission> {
	
	private final static String TABLE = "sys_user_permissions";
	private final static String INSERT_SQL = "insert into " + TABLE + "(user_id, permission) values (:userId, :permission)";
	private final static String DELETE_SQL = "delete from " + TABLE + " where id=:id";
	private final static String DELETE_BY_USER_AND_PERM_SQL = "delete from " + TABLE + " where user_id=:userId and permission=:permission";
	private final static String LOAD_USER_PERMISSIONS_SQL = "select * from " + TABLE + " where user_id=:userId";
	
	/* (non-Javadoc)  
	 * @see com.appabc.system.AbstractBaseSystemDao#create(java.lang.Object)  
	 */
	@Override
	public boolean create(UserPermission entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", entity.getUserId());
		params.put("permission", entity.getPermission().getId());
		int result = getNamedParameterJdbcTemplate().update(INSERT_SQL, new MapSqlParameterSource(params));
		return result == 1;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.system.AbstractBaseSystemDao#update(java.lang.Object)  
	 */
	@Override
	public boolean update(UserPermission entity) {
		// Always return false since this method will never be called.
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.system.AbstractBaseSystemDao#delete(java.lang.Object)  
	 */
	@Override
	public boolean delete(UserPermission entity) {
		int result = getNamedParameterJdbcTemplate().update(DELETE_SQL, new BeanPropertySqlParameterSource(entity));
		return result == 1;
	}
	
	public boolean delete(int userId, int permId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("permission", permId);
		int result = getNamedParameterJdbcTemplate().update(DELETE_BY_USER_AND_PERM_SQL, new MapSqlParameterSource(params));
		return result == 1;
	}
	
	public List<UserPermission> loadUserPermission(int userId) {
		return getNamedParameterJdbcTemplate()
						.query(LOAD_USER_PERMISSIONS_SQL,
							   new MapSqlParameterSource("userId", userId),
							   this);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.system.AbstractBaseSystemDao#queryForList(java.util.Map, int, int, java.lang.String[])  
	 */
	@Override
	public List<UserPermission> queryForList(Map<String, Object> params,
			int start, int count, String[] orderBy) {
		return getNamedParameterJdbcTemplate().query(LOAD_USER_PERMISSIONS_SQL, new MapSqlParameterSource(params), this);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public UserPermission mapRow(ResultSet rs, int arg1) throws SQLException {
		UserPermission up = new UserPermission();
		up.setId(rs.getInt("id"));
		up.setUserId(rs.getInt("user_id"));
		up.setPermission(rs.getInt("permission"));
		return up;
	}
	

}
