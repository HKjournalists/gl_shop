package com.appabc.datas.cms.dao;

import com.appabc.datas.cms.vo.User;
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


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng
 * @version     : 1.0
 * Create Date  : Oct 15, 2014 3:58:11 PM
 */
@Repository
public class UserDao extends AbstractBaseCmsDao<User> {

	public final static String TABLE_NAME = "SYS_USERS";

    public final static UserMapper USER_MAPPER = new UserMapper();

    private final static String FIELD_ID = "ID";
    private final static String FIELD_USERNAME = "USERNAME";
    private final static String FIELD_REALNAME = "REALNAME";
    private final static String FIELD_PASSWORD = "PASSWORD";
    private final static String FIELD_ENABLED = "ENABLED";
    private final static String FIELD_LAST_LOGIN_TIME = "LAST_LOGIN_TIME";
    private final static String FIELD_CREATE_TIME = "CREATE_TIME";
    private final static String FIELD_UPDATE_TIME = "UPDATE_TIME";

	private final static String[] FIELDS = new String[] {FIELD_ID, FIELD_USERNAME, FIELD_REALNAME,
            FIELD_PASSWORD, FIELD_ENABLED, FIELD_LAST_LOGIN_TIME, FIELD_CREATE_TIME};

	private final static String INSERT_SQL = "insert into " + TABLE_NAME + "(" + FIELD_USERNAME + ", " + FIELD_REALNAME
            + ", " + FIELD_PASSWORD + ", " + FIELD_ENABLED + ", " + FIELD_CREATE_TIME + ")"
            + " values(?, ?, ?, ?, ?)";

	private final static String UPDATE_SQL = "update " + TABLE_NAME + " set " + FIELD_REALNAME + "=:realName, "
            + FIELD_PASSWORD + "=:password, " + FIELD_ENABLED + "=:enabled, "
            + FIELD_LAST_LOGIN_TIME + "=:lastLoginTime, " + FIELD_UPDATE_TIME + "=CURRENT_TIMESTAMP where " + FIELD_ID + "=:id";


	public User get(Serializable id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FIELD_ID, id);
		return getNamedParameterJdbcTemplate().queryForObject(
					generateSqlWithParams(FIELDS, new String[] {TABLE_NAME}, params),
					params,
					USER_MAPPER);
	}

	public boolean create(final User user) {
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
		params.put(FIELD_USERNAME, userName);
		String sql = generateSqlWithParams(FIELDS, new String[] {TABLE_NAME}, params);
		try {
			return getNamedParameterJdbcTemplate().queryForObject(sql, params, USER_MAPPER);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}


    @Override
	public int countBy(Map<String, Object> params) {
		Map<String, Object> cloneParams = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(params)) {
			cloneParams.putAll(params);
		}
    	String sql = generateSqlWithParams(new String[] {"count(*)"}, new String[] {TABLE_NAME}, cloneParams);
    	return getNamedParameterJdbcTemplate().queryForObject(sql, cloneParams, Integer.class);
	}

	@Override
	public List<User> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
		Map<String, Object> cloneParams = new HashMap<String, Object>();
		if (!CollectionUtils.isEmpty(params)) {
			cloneParams.putAll(params);
		}
		String sql = generateSqlWithParams(FIELDS, new String[] {TABLE_NAME}, cloneParams, start, count, orderBy);
		return getNamedParameterJdbcTemplate().query(sql, new MapSqlParameterSource(cloneParams), USER_MAPPER);
	}

    private static class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt(FIELD_ID));
            user.setUserName(rs.getString(FIELD_USERNAME));
            user.setRealName(rs.getString(FIELD_REALNAME));
            user.setPassword(rs.getString(FIELD_PASSWORD));
            user.setEnabled(rs.getBoolean(FIELD_ENABLED));
            user.setLastLoginTime(rs.getTime(FIELD_LAST_LOGIN_TIME));
            user.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
            return user;
        }
    }
}
