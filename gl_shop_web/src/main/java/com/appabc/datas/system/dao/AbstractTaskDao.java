package com.appabc.datas.system.dao;

import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.datas.system.Task;
import com.appabc.datas.system.TaskType;
import com.appabc.datas.system.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zou Xifeng on 11/10/14.
 */
public abstract class AbstractTaskDao<T> extends AbstractBaseSystemDao<Task> implements RowMapper<Task> {

    public final static String TABLE_NAME = "SYS_TASKS";

    // Task table fields
    public final static String FIELD_ID = "task.ID";
    public final static String FIELD_OBJECT_ID = "task.OBJECT_ID";
    public final static String FIELD_TYPE = "task.TYPE";
    public final static String FIELD_OWNER = "task.OWNER";
    public final static String FIELD_CLAIM_TIME = "task.CLAIM_TIME";
    public final static String FIELD_FINISHED = "task.FINISHED";
    public final static String FIELD_FINISH_TIME = "task.FINISH_TIME";
    public final static String FIELD_CREATE_TIME = "task.CREATE_TIME";

    // Company table fields
    private final static String COMPANY_FIELD_ID = "c.ID";
    private final static String COMPANY_FIELD_CNAME = "c.CNAME";
    private final static String COMPANY_FIELD_MARK = "c.MARK";
    private final static String COMPANY_FIELD_CONTACT = "c.CONTACT";
    private final static String COMPANY_FIELD_CPHONE = "c.CPHONE";
    private final static String COMPANY_FIELD_CTYPE = "c.CTYPE";
    private final static String COMPANY_FIELD_AUTHSTATUS = "c.AUTHSTATUS";
    private final static String COMPANY_FIELD_STATUS = "c.STATUS";
    private final static String COMPANY_FIELD_CREATE_DATE = "c.CREATEDATE";
    private final static String COMPANY_FIELD_UPDATE_DATE = "c.UPDATEDATE";
    private final static String COMPANY_FIELD_UPDATER = "c.UPDATER";
    private final static String COMPANY_FIELD_TEL = "c.TEL";
    private final static String COMPANY_FIELD_BAILSTATUS = "c.BAILSTATUS";

    private final static String[] FIELDS =  new String[] {
            FIELD_ID, FIELD_TYPE, FIELD_OBJECT_ID, FIELD_OWNER,
            FIELD_CLAIM_TIME, FIELD_FINISHED, FIELD_FINISH_TIME,
            FIELD_CREATE_TIME,
            COMPANY_FIELD_ID, COMPANY_FIELD_CNAME, COMPANY_FIELD_MARK,
            COMPANY_FIELD_CONTACT, COMPANY_FIELD_CPHONE, COMPANY_FIELD_CTYPE,
            COMPANY_FIELD_AUTHSTATUS, COMPANY_FIELD_STATUS, COMPANY_FIELD_CREATE_DATE,
            COMPANY_FIELD_UPDATE_DATE, COMPANY_FIELD_UPDATER, COMPANY_FIELD_TEL,
            COMPANY_FIELD_BAILSTATUS
    };

    private final static CompanyInfoRowMapper COMPANY_INFO_ROW_MAPPER = new CompanyInfoRowMapper();

    private final static String COMPANY_TABLE_NAME = "T_COMPANY_INFO";

    private final static String INSERT_SQL = "insert into " + TABLE_NAME + "(TYPE, CID, OBJECT_ID, OWNER, FINISHED, CREATE_TIME)" +
            "values (?, ?, ?, ?, ?, ?)";

    private final static String UPDATE_SQL = "update " + TABLE_NAME + " set OWNER=:OWNER, FINISHED=:FINISHED, FINISH_TIME=:FINISHTIME, UPDATE_TIME=CURRENT_TIME where ID=:ID";

    private final static String DELETE_SQL = "delete from " + TABLE_NAME + " where ID=:ID";

    private final static String GET_SQL_JOIN_USER = TABLE_NAME + " task left join " +
            UserDao.TABLE_NAME + " u on task.OWNER=u.ID where task.CID=c.ID ";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract String getJoinTableName();

    protected abstract String getShortJoinTableName();

    protected abstract String[] getJoinTableFields();

    protected abstract String getJoinTableIdFieldName();

    protected abstract RowMapper<T> getJoinTableRowMapper();

    private StringBuilder buildQuerySql(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("select " + StringUtils.join(FIELDS, ","));
        if (getJoinTableFields() != null && getJoinTableFields().length != 0) {
            sb.append(", " + StringUtils.join(getJoinTableFields(), ","));
        }
        sb.append(" from T_COMPANY_INFO c, " + getJoinTableName() + " " + getShortJoinTableName() + ", ")
          .append(GET_SQL_JOIN_USER + " and " + FIELD_OBJECT_ID + "=" + getShortJoinTableName() + "." + getJoinTableIdFieldName());

        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task dao generated task query sql: {}", sb.toString());
        }
        return sb;
    }

    @Override
    public Task get(Serializable id) {
        Map<String, Object> params = new HashMap<>();
        params.put(FIELD_ID, id);
        StringBuilder sb = buildQuerySql(params);
        sb.append(" and " + FIELD_ID + "=:" + FIELD_ID);
        Task t = getNamedParameterJdbcTemplate().queryForObject(sb.toString(), new MapSqlParameterSource(params), this);
        return t;
    }

    @Override
    public boolean create(final Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, task.getType().getValue());
                ps.setString(2, task.getCompany().getId());
                ps.setString(3, task.getObjectId());
                if (task.getOwner() != null) {
                    ps.setInt(4, task.getOwner().getId());
                } else {
                    ps.setNull(4, Types.INTEGER);
                }
                ps.setBoolean(5, task.isFinished());
                ps.setTimestamp(6, new Timestamp(task.getCreateTime().getTime()));
                return ps;
            }
        }, keyHolder);
        task.setId(keyHolder.getKey().intValue());
        return result == 1;
    }

    @Override
    public boolean update(Task entity) {
        int result = getNamedParameterJdbcTemplate().update(UPDATE_SQL, new TaskObjectSqlParameterSource(entity));
        return result == 1;
    }

    @Override
    public boolean delete(Task entity) {
        return false;
    }

    @Override
    public int countBy(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from " + TABLE_NAME + " task ");
        if (!CollectionUtils.isEmpty(params)) {
            sb.append(" where 1=1 ");
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }
        return getNamedParameterJdbcTemplate().queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public List<Task> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
        StringBuilder sb = buildQuerySql(params);

        if (count > 0) {
            sb.append(" limit " + count);
        }

        if (start > 0) {
            sb.append(" offset " + start);
        }

        List<Task> result = this.getNamedParameterJdbcTemplate().query(sb.toString(),
                params, this);
        return result;
    }

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task t = new Task();
        t.setId(rs.getInt(FIELD_ID));
        t.setType(TaskType.valueOf(rs.getInt(FIELD_TYPE)));
        t.setObjectId(rs.getString(FIELD_OBJECT_ID));
        Integer ownerId = (Integer) rs.getObject(FIELD_OWNER);
        if (ownerId != null) {
            t.setOwner(UserDao.USER_MAPPER.mapRow(rs, rowNum));
        }
        t.setClaimTime(rs.getTimestamp(FIELD_CLAIM_TIME));
        t.setFinished(rs.getBoolean(FIELD_FINISHED));
        t.setFinishTime(rs.getTimestamp(FIELD_FINISH_TIME));
        t.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));

        t.setCompany(COMPANY_INFO_ROW_MAPPER.mapRow(rs, rowNum));

        if (t.getObjectId() != null) {
            t.setTaskObject(getJoinTableRowMapper().mapRow(rs, rowNum));
        }
        return t;
    }

    private static class CompanyInfoRowMapper implements RowMapper<TCompanyInfo> {
        @Override
        public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            TCompanyInfo t = new TCompanyInfo();

            t.setId(rs.getString(COMPANY_FIELD_ID));
            t.setCname(rs.getString(COMPANY_FIELD_CNAME));
            t.setAuthstatus(CompanyInfo.CompanyAuthStatus.enumOf(rs.getString(COMPANY_FIELD_AUTHSTATUS)));
            t.setContact(rs.getString(COMPANY_FIELD_CONTACT));
            t.setCphone(rs.getString(COMPANY_FIELD_CPHONE));
            t.setCreatedate(rs.getTimestamp(COMPANY_FIELD_CREATE_DATE));
            t.setCtype(CompanyInfo.CompanyType.enumOf(rs.getString(COMPANY_FIELD_CTYPE)));
            t.setMark(rs.getString(COMPANY_FIELD_MARK));
            t.setStatus(rs.getString(COMPANY_FIELD_STATUS));
            t.setUpdatedate(rs.getTimestamp(COMPANY_FIELD_UPDATE_DATE));
            t.setUpdater(rs.getString(COMPANY_FIELD_UPDATER));
            t.setTel(rs.getString(COMPANY_FIELD_TEL));
            t.setBailstatus(CompanyInfo.CompanyBailStatus.enumOf(rs.getString(COMPANY_FIELD_BAILSTATUS)));

            return t;
        }
    }

    private static class TaskObjectSqlParameterSource extends BeanPropertySqlParameterSource {
        private final static String OWNER = "owner";
        public TaskObjectSqlParameterSource(Object object) {
            super(object);
        }

        public int getSqlType(String paramName) {
            if (OWNER.equals(paramName)) {
                return Types.INTEGER;
            }

            return super.getSqlType(paramName);
        }

        public Object getValue(String paramName) {
            Object propValue = super.getValue(paramName);
            if (OWNER.equals(paramName)) {
                User user = (User) propValue;
                return (user != null) ? user.getId() : null;
            }

            return propValue;
        }
    }
}
