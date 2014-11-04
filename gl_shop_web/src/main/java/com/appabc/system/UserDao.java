package com.appabc.system;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng
 * @version     : 1.0
 * Create Date  : Oct 15, 2014 3:58:11 PM
 */
@Repository
public class UserDao extends AbstractBaseSystemDao<User> implements RowMapper<User> {
	
	private final static String TABLE = "sys_users";
	private final static String[] FIELDS = new String[] {"id", "username", "realname", "password",
												"enabled", "last_login_time", "create_time"};

	private final static String INSERT_SQL = "insert into " + TABLE + "(username, realname, password, enabled, create_time)"
							+ " values(?, ?, ?, ?, ?)";

	private final static String UPDATE_SQL = "update " + TABLE + " set realname=:realName, password=:password, enabled=:enabled, "
												+ "last_login_time=:lastLoginTime, update_time=current_time where id=:id";
	
	
	public User get(Serializable id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID", id);
		return getNamedParameterJdbcTemplate().queryForObject(
					generateSqlWithParams(FIELDS, new String[] {TABLE}, params),
					params,
					this);
	}
	
	public boolean create(final User user) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", user.getUserName());
//		params.put("realName", user.getRealName());
//		params.put("password", user.getPassword());
//		params.put("enabled", user.isEnabled());
//		params.put("lastLoginTime", user.getLastLoginTime());
//		params.put("registerTime", user.getRegisterTime());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result = getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getRealName());
                ps.setString(3, user.getPassword());
                ps.setBoolean(4, user.isEnabled());
                ps.setTimestamp(5, new Timestamp(user.getCreateTime().getTime()));
                return ps;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().intValue());
		return result == 1;
	}
	
	public boolean delete(User user) {
		return false;
	}
	
	public boolean update(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		int result = getNamedParameterJdbcTemplate().update(UPDATE_SQL, params);
		return result == 1;
	}

	public User findByUserName(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		String sql = generateSqlWithParams(FIELDS, new String[] {TABLE}, params);
		try {
			return getNamedParameterJdbcTemplate().queryForObject(sql, params, this);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUserName(rs.getString("username"));
		user.setRealName(rs.getString("realname"));
		user.setPassword(rs.getString("password"));
		user.setEnabled(rs.getBoolean("enabled"));
		user.setLastLoginTime(rs.getTime("last_login_time"));
		user.setCreateTime(rs.getTimestamp("create_time"));
		return user;
	}
	
    @Override
	public int countBy(Map<String, Object> params) {
		Map<String, Object> cloneParams = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(params)) {
			cloneParams.putAll(params);
		}
    	String sql = generateSqlWithParams(new String[] {"count(*)"}, new String[] {TABLE}, cloneParams);
    	return getNamedParameterJdbcTemplate().queryForObject(sql, cloneParams, Integer.class);
	}

	@Override
	public List<User> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
		Map<String, Object> cloneParams = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(params)) {
			cloneParams.putAll(params);
		}
		String sql = generateSqlWithParams(FIELDS, new String[] {TABLE}, cloneParams, start, count, orderBy);
		return getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource(cloneParams), this);
	}

}
