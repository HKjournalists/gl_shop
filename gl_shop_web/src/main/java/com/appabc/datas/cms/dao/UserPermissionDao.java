package com.appabc.datas.cms.dao;

import com.appabc.datas.cms.vo.UserPermission;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 31, 2014 11:36:04 AM
 */
@Repository
public class UserPermissionDao extends AbstractBaseCmsDao<UserPermission> implements RowMapper<UserPermission> {

	private final static String TABLE_NAME = "SYS_USER_PERMISSIONS";
    private final static String FIELD_ID = "ID";
    private final static String FIELD_USER_ID = "USER_ID";
    private final static String FIELD_PERMISSION = "PERMISSION";
	private final static String INSERT_SQL = "insert into " + TABLE_NAME + "(" + FIELD_USER_ID + ", " + FIELD_PERMISSION + ") values (:"
            + FIELD_USER_ID + ", :" + FIELD_PERMISSION + ")";
	private final static String DELETE_SQL = "delete from " + TABLE_NAME + " where " + FIELD_ID + "=:" + FIELD_ID;
	private final static String DELETE_BY_USER_AND_PERM_SQL = "delete from " + TABLE_NAME + " where " + FIELD_USER_ID
            + "=:" + FIELD_USER_ID + " and " + FIELD_PERMISSION + "=:" + FIELD_PERMISSION;
	private final static String LOAD_USER_PERMISSIONS_SQL = "select * from " + TABLE_NAME + " where " + FIELD_USER_ID + "=:" + FIELD_USER_ID;


    @Override
    public UserPermission get(Serializable id) {
        SqlParameterSource params = new MapSqlParameterSource(FIELD_USER_ID, id);
        return getNamedParameterJdbcTemplate().queryForObject(LOAD_USER_PERMISSIONS_SQL, params, this);
    }

    /* (non-Javadoc)
         * @see AbstractBaseCmsDao#create(java.lang.Object)
         */
	@Override
	public boolean create(UserPermission entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FIELD_USER_ID, entity.getUserId());
		params.put(FIELD_PERMISSION, entity.getPermission().getId());
		int result = getNamedParameterJdbcTemplate().update(INSERT_SQL, new MapSqlParameterSource(params));
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see AbstractBaseCmsDao#update(java.lang.Object)
	 */
	@Override
	public boolean update(UserPermission entity) {
		// Always return false since this method will never be called.
		return false;
	}

	/* (non-Javadoc)
	 * @see AbstractBaseCmsDao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(UserPermission entity) {
		int result = getNamedParameterJdbcTemplate().update(DELETE_SQL, new BeanPropertySqlParameterSource(entity));
		return result == 1;
	}

	public boolean delete(int userId, int permId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FIELD_USER_ID, userId);
		params.put(FIELD_PERMISSION, permId);
		int result = getNamedParameterJdbcTemplate().update(DELETE_BY_USER_AND_PERM_SQL, new MapSqlParameterSource(params));
		return result == 1;
	}

	public List<UserPermission> loadUserPermission(int userId) {
		return getNamedParameterJdbcTemplate()
						.query(LOAD_USER_PERMISSIONS_SQL,
							   new MapSqlParameterSource(FIELD_USER_ID, userId),
							   this);
	}

	/* (non-Javadoc)
	 * @see AbstractBaseCmsDao#queryForList(java.util.Map, int, int, java.lang.String[])
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
		up.setId(rs.getInt(FIELD_ID));
		up.setUserId(rs.getInt(FIELD_USER_ID));
		up.setPermission(rs.getInt(FIELD_PERMISSION));
		return up;
	}


}
