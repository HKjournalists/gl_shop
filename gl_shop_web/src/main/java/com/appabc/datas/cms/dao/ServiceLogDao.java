package com.appabc.datas.cms.dao;

import com.appabc.datas.cms.vo.ServiceLog;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.cms.vo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
 *
 * Created by zouxifeng on 3/25/15.
 */
@Repository
public class ServiceLogDao extends AbstractBaseCmsDao<ServiceLog> {

    private final static String FIELD_ID = "log.ID";
    private final static String FIELD_CID = "CID";
    private final static String FIELD_OBJECT_ID = "OBJECT_ID";
    private final static String FIELD_TYPE = "TYPE";
    private final static String FIELD_USER_ID = "u.ID";
    private final static String FIELD_USER_USERNAME = "u.USERNAME";
    private final static String FIELD_USER_REALNAME = "u.REALNAME";
    private final static String FIELD_CONTENT = "CONTENT";
    private final static String FIELD_OPERATOR = "OPERATOR";
    private final static String FIELD_CREATE_TIME = "log.CREATE_TIME";

    private final static String TABLE_NAME = "SYS_SERVICE_LOGS log";

    private final static String SELECT_SQL = "SELECT "
            + StringUtils.join(new String[] {
                        FIELD_ID, FIELD_CID, FIELD_OBJECT_ID, FIELD_TYPE, FIELD_CONTENT,
                        FIELD_OPERATOR, FIELD_CREATE_TIME, FIELD_USER_ID,
                        FIELD_USER_USERNAME, FIELD_USER_REALNAME}, ',')
            + " FROM " + TABLE_NAME + ", " + UserDao.TABLE_NAME + " u "
            + " WHERE log.OBJECT_ID=:objectId and log.TYPE=:type and log.OPERATOR=u.id "
            + " ORDER BY " + FIELD_CREATE_TIME + " DESC";

    private final static String SELECT_BY_CID_AND_TYPE_SQL = "SELECT "
            + StringUtils.join(new String[] {
                        FIELD_ID, FIELD_CID, FIELD_OBJECT_ID, FIELD_TYPE, FIELD_CONTENT,
                        FIELD_OPERATOR, FIELD_CREATE_TIME, FIELD_USER_ID,
                        FIELD_USER_USERNAME, FIELD_USER_REALNAME}, ',')
            + " FROM " + TABLE_NAME + ", " + UserDao.TABLE_NAME + " u "
            + " WHERE CID=:cid and log.TYPE=:type and log.OPERATOR=u.id "
            + " ORDER BY " + FIELD_CREATE_TIME + " DESC";

    private final static String CREATE_SQL = "INSERT INTO SYS_SERVICE_LOGS ("
            + StringUtils.join(new String[] {FIELD_CID, FIELD_OBJECT_ID, FIELD_TYPE,
                                    FIELD_CONTENT, FIELD_OPERATOR, "CREATE_TIME"}, ',')
            + ") VALUES(?, ?, ?, ?, ?, ?)";

    @Override
    public ServiceLog get(Serializable id) {
        throw new RuntimeException("I haven't found any requirement will query by log id,"
                + " where are you from?");
    }

    public List<ServiceLog> query(String objectId, ServiceLogType type) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        params.put("type", type.getType());
        return getNamedParameterJdbcTemplate().query(SELECT_SQL, params,
                new RowMapper<ServiceLog>() {
                    @Override
                    public ServiceLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ServiceLog log = new ServiceLog();
                        log.setId(rs.getInt(FIELD_ID));
                        log.setCompanyId(rs.getString(FIELD_CID));
                        log.setObjectId(rs.getString(FIELD_OBJECT_ID));
                        log.setType(ServiceLogType.valueOf(rs.getInt(FIELD_TYPE)));
                        log.setContent(rs.getString(FIELD_CONTENT));
                        User operator = new User();
                        operator.setId(rs.getInt(FIELD_USER_ID));
                        operator.setUserName(rs.getString(FIELD_USER_USERNAME));
                        operator.setRealName(rs.getString(FIELD_USER_REALNAME));
                        log.setOperator(operator);
                        log.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
                        return log;
                    }
                });
    }

    public List<ServiceLog> queryByCompanyIdAndType(String cid, ServiceLogType type) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", cid);
        params.put("type", type.getType());
        return getNamedParameterJdbcTemplate().query(SELECT_BY_CID_AND_TYPE_SQL, params,
                new RowMapper<ServiceLog>() {
                    @Override
                    public ServiceLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ServiceLog log = new ServiceLog();
                        log.setId(rs.getInt(FIELD_ID));
                        log.setCompanyId(rs.getString(FIELD_CID));
                        log.setObjectId(rs.getString(FIELD_OBJECT_ID));
                        log.setType(ServiceLogType.valueOf(rs.getInt(FIELD_TYPE)));
                        log.setContent(rs.getString(FIELD_CONTENT));
                        User operator = new User();
                        operator.setId(rs.getInt(FIELD_USER_ID));
                        operator.setUserName(rs.getString(FIELD_USER_USERNAME));
                        operator.setRealName(rs.getString(FIELD_USER_REALNAME));
                        log.setOperator(operator);
                        log.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
                        return log;
                    }
                });
    }


    @Override
    public boolean create(final ServiceLog entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getCompanyId());
                ps.setString(2, entity.getObjectId());
                ps.setInt(3, entity.getType().getType());
                ps.setString(4, entity.getContent());
                ps.setInt(5, entity.getOperator().getId());
                ps.setTimestamp(6, new Timestamp(entity.getCreateTime().getTime()));
                return ps;
            }
        }, keyHolder);
        entity.setId(keyHolder.getKey().intValue());

        return result == 1;
    }

    @Override
    public boolean update(ServiceLog entity) {
        throw new RuntimeException("Should not call update method on service log,"
                + " where are you from?");
    }

    @Override
    public boolean delete(ServiceLog entity) {
        throw new RuntimeException("Service logs should not never deleted,"
                + " where are you from?");
    }

    @Override
    public List<ServiceLog> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
        throw new RuntimeException("Should not call this method, where are you from?");
    }

}
