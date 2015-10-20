package com.appabc.datas.cms.dao;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.enums.UserInfo.UserStatus;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.MultiTypeBeanPropertySqlParameterSource;
import com.appabc.datas.cms.dao.tasks.ITaskDaoMeta;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zou Xifeng on 11/10/14.
 */
@Repository
public class TaskDao extends AbstractBaseCmsDao<Task> {

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
    public final static String FIELD_UPDATE_TIME = "task.UPDATE_TIME";

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

    // Customer user
    private final static String CUSTOMER_FIELD_ID = "cu.ID";
    private final static String CUSTOMER_FIELD_CID = "cu.CID";
    private final static String CUSTOMER_FIELD_PHONE = "cu.PHONE";

    // Sys user fields
    private final static String SYSUSER_FIELD_ID = "u.ID";
    private final static String SYSUSER_FIELD_USERNAME = "u.USERNAME";
    private final static String SYSUSER_FIELD_REALNAME = "u.REALNAME";

    private final static String[] FIELDS =  new String[] {
            FIELD_ID, FIELD_TYPE, FIELD_OBJECT_ID, FIELD_OWNER,
            FIELD_CLAIM_TIME, FIELD_FINISHED, FIELD_FINISH_TIME,
            FIELD_CREATE_TIME,FIELD_UPDATE_TIME,
            COMPANY_FIELD_ID, COMPANY_FIELD_CNAME, COMPANY_FIELD_MARK,
            COMPANY_FIELD_CONTACT, COMPANY_FIELD_CPHONE, COMPANY_FIELD_CTYPE,
            COMPANY_FIELD_AUTHSTATUS, COMPANY_FIELD_STATUS, COMPANY_FIELD_CREATE_DATE,
            COMPANY_FIELD_UPDATE_DATE, COMPANY_FIELD_UPDATER, COMPANY_FIELD_TEL,
            COMPANY_FIELD_BAILSTATUS,
            CUSTOMER_FIELD_ID, CUSTOMER_FIELD_PHONE,
            SYSUSER_FIELD_ID, SYSUSER_FIELD_USERNAME, SYSUSER_FIELD_REALNAME
    };

    private final static CompanyInfoRowMapper COMPANY_INFO_ROW_MAPPER = new CompanyInfoRowMapper();
    private final static SysUserMapper SYS_USER_MAPPER = new SysUserMapper();
    private final static CustomerUserRowMapper CUSTOMER_USER_ROW_MAPPER = new CustomerUserRowMapper();

    private final static String USER_TABLE_NAME = "SYS_USERS";
    private final static String COMPANY_TABLE_NAME = "T_COMPANY_INFO";
    private final static String CUSTOMER_USER_TABLE_NAME = "T_USER";

    private final static String INSERT_SQL = "insert into " + TABLE_NAME + "(TYPE, CID, OBJECT_ID, OWNER, FINISHED, CREATE_TIME)" +
            "values (?, ?, ?, ?, ?, ?)";

    private final static String UPDATE_SQL = "update " + TABLE_NAME + " set OWNER=:owner, FINISHED=:finished, FINISH_TIME=:finishTime, UPDATE_TIME=CURRENT_TIMESTAMP where ID=:id";
    
    private final static String DELETE_SQL = "delete from " + TABLE_NAME + " where ID=:ID";

    private final static String DELETE_BY_TYPE_AND_OBJECTID_SQL = "delete from " + TABLE_NAME + " where TYPE=:type AND OBJECT_ID=:objectId";

    private final static String GET_SQL_JOIN_USER = TABLE_NAME + " as task left join " +
            USER_TABLE_NAME + " as u on task.OWNER=u.ID ";

    private final static String BASE_WHERE_CLAUSE = "where task.CID=c.ID and " + CUSTOMER_FIELD_CID + "=" + COMPANY_FIELD_ID + " ";
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private <T> StringBuilder buildQuerySql(Map<String, Object> params,
                                        ITaskDaoMeta<T> meta) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("select " + StringUtils.join(FIELDS, ","));
        if (ArrayUtils.isNotEmpty(meta.getJoinTableFields())) {
            for (String f : meta.getJoinTableFields()) {
                sb.append(", " + meta.getJoinTableAliasName() + "." + f);
            }
        }
        sb.append(" from " + COMPANY_TABLE_NAME + " c, " + CUSTOMER_USER_TABLE_NAME + " cu, ");
        if (StringUtils.isNotEmpty(meta.getJoinTableName()) && StringUtils.isNotEmpty(meta.getJoinTableAliasName())) {
            sb.append(meta.getJoinTableName() + " " + meta.getJoinTableAliasName() + ", ");
        }
        sb.append(GET_SQL_JOIN_USER);
        sb.append(BASE_WHERE_CLAUSE);
        if (StringUtils.isNotEmpty(meta.getJoinTableName()) && StringUtils.isNotEmpty(meta.getJoinTableAliasName())) {
            sb.append(" and " + FIELD_OBJECT_ID + "=" + meta.getJoinTableAliasName() + "." + meta.getJoinTableIdFieldName());
        }
        if (meta.requireCompanyAuthenticated()) {
            sb.append(" and " + COMPANY_FIELD_AUTHSTATUS + "=" + AuthRecordStatus.AUTH_STATUS_CHECK_YES.getVal());
        }

        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }

        return sb;
    }

    @Override
    public Task get(Serializable id) {
        throw new IllegalAccessError("This method should not be called. use get(id, meta)");
    }

    public <T> Task get(Serializable id, ITaskDaoMeta<T> meta) {
        Map<String, Object> params = new HashMap<>();
        params.put(FIELD_ID, id);
        StringBuilder sb = buildQuerySql(params, meta);
        sb.append(" and " + FIELD_ID + "=:" + FIELD_ID);
        Task t = getNamedParameterJdbcTemplate().queryForObject(sb.toString(), new MapSqlParameterSource(params),
                new TaskRowMapper(meta.getJoinTableRowMapper()));
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
                TaskType type = task.getType();
                int typeIndex = type.getValue();
                ps.setInt(1, typeIndex);
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
        int key = keyHolder.getKey().intValue();
        task.setId(String.valueOf(key));
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

    public int countBy(Map<String, Object> params, TaskType type) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from " + TABLE_NAME + " task ");
        if (!CollectionUtils.isEmpty(params)) {
            sb.append("where 1=1");
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Generate count task sql: {}", sb.toString());
        }
        return getNamedParameterJdbcTemplate().queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public List<Task> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
        throw new RuntimeException("This method should not be called. Call queryForList(Map<String, Object>, ITaskDaoMeta<T>, int, int, String[])");
    }

    public <T> List<Task> queryForList(Map<String, Object> params, ITaskDaoMeta<T> meta, int start, int count, String orderBy) {
        StringBuilder sb = buildQuerySql(params, meta);
        if(orderBy != null){
        	sb.append(orderBy);
        }

        if (count > 0) {
            sb.append(" limit " + count);
        }

        if (start > 0) {
            sb.append(" offset " + start);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Task dao generated task query sql: {}", sb.toString());
        }

        List<Task> result = this.getNamedParameterJdbcTemplate().query(sb.toString(),
                params, new TaskRowMapper(meta.getJoinTableRowMapper()));
        return result;
    }
    
    private String buildQSql(Map<String,Object> params,String orderBy){
    	int taskType = params.get("taskType") != null ? (Integer)params.get("taskType") : TaskType.MATCH_ORDER_REQUEST;
    	String taskFinished = params.get("taskFinished") != null ? String.valueOf(params.get("taskFinished")) : "0";
    	String passType = params.get("passType") != null ? String.valueOf(params.get("passType")) : PurseType.GUARANTY.getVal();
    	int task_owner = params.get("task.owner") != null ? (Integer)params.get("task.owner") : 0;
    	StringBuilder sb = new StringBuilder(" SELECT TASK.ID AS TID,TASK.CID AS TCID,TASK.TYPE TTYPE,TASK.OBJECT_ID AS TOBJECT_ID,TASK.OWNER AS TOWNER,TASK.CLAIM_TIME AS TCLAIM_TIME,TASK.FINISHED AS TFINISHED,TASK.FINISH_TIME AS TFINISH_TIME,TASK.CREATE_TIME AS TCREATE_TIME,TASK.UPDATE_TIME AS TUPDATE_TIME, ");
    	sb.append(" COMPANY.ID AS COMID,COMPANY.CNAME AS CCNAME,COMPANY.MARK AS CMARK,COMPANY.CONTACT AS CCONTACT,COMPANY.CPHONE AS CCPHONE,COMPANY.CTYPE AS CCTYPE,COMPANY.AUTHSTATUS AS CAUTHSTATUS,COMPANY.STATUS AS CSTATUS,COMPANY.CREATEDATE AS CCREATEDATE,COMPANY.UPDATEDATE AS CUPDATEDATE,COMPANY.UPDATER AS CUPDATER,COMPANY.TEL AS CTEL,COMPANY.BAILSTATUS AS CBAILSTATUS, ");
    	sb.append(" SUSER.ID AS SID ,SUSER.USERNAME AS SUSERNAME ,SUSER.REALNAME AS SREALNAME ,SUSER.PASSWORD AS SPASSWORD,SUSER.ENABLED AS SENABLED,SUSER.LAST_LOGIN_TIME AS SLAST_LOGIN_TIME,SUSER.CREATE_TIME AS SCREATE_TIME ,SUSER.UPDATE_TIME AS SUPDATE_TIME, ");
    	sb.append(" USER.ID AS UID ,USER.CID AS UCID ,USER.USERNAME AS UUSERNAME ,USER.PASSWORD AS UPASSWORD ,USER.NICK AS UNICK ,USER.PHONE AS UPHONE ,USER.LOGO AS ULOGO ,USER.STATUS AS USTATUS ,USER.CREATEDATE AS UCREATEDATE ,USER.UPDATEDATE AS UUPDATEDATE ,USER.CLIENTID AS UCLIENTID ,USER.CLIENTTYPE AS UCLIENTTYPE ,USER.VERSION AS UVERSION, ");
    	sb.append(" FIND.FID AS FFID, FIND.CID AS FCID, FIND.TITlE AS FTITlE, FIND.TYPE AS FTYPE, FIND.ADDRESSTYPE AS FADDRESSTYPE, FIND.PRICE AS FPRICE, FIND.TOTALNUM AS FTOTALNUM, FIND.NUM AS FNUM,FIND.STARTTIME AS FSTARTTIME, FIND.ENDTIME AS FENDTIME, FIND.MOREAREA AS FMOREAREA, FIND.AREA AS FAREA, FIND.CREATER AS FCREATER, FIND.CREATIME AS FCREATIME, FIND.LIMITIME AS FLIMITIME, FIND.STATUS AS FSTATUS, ");
    	sb.append(" FIND.REMARK AS FREMARK, FIND.PARENTID AS FPARENTID, FIND.UPDATETIME AS FUPDATETIME, FIND.UPDATER AS FUPDATER, FIND.OVERALLSTATUS AS FOVERALLSTATUS,FIND.CONTRACTID AS FCONTRACTID,FIND.MATCHINGNUM AS FMATCHINGNUM,FIND.OVERALLTYPE AS FOVERALLTYPE,B.CREATETIME AS FAPPLYDATE,A.APPLYNUM AS FAPPLYNUM,PASSINFO.AMOUNT AS FAMOUNT ");
    	sb.append(" FROM SYS_TASKS TASK LEFT JOIN T_COMPANY_INFO COMPANY ON TASK.CID = COMPANY.ID LEFT JOIN SYS_USERS SUSER ON SUSER.ID = TASK.OWNER ");
    	sb.append(" LEFT JOIN T_PASSBOOK_INFO PASSINFO ON PASSINFO.CID = TASK.CID AND PASSINFO.PASSTYPE = "+passType);
    	sb.append(" LEFT JOIN T_USER USER ON USER.CID = TASK.CID LEFT JOIN T_ORDER_FIND FIND ON FIND.FID = TASK.OBJECT_ID ");
    	sb.append(" LEFT JOIN (SELECT T.OBJECT_ID,COUNT(FIND_ITEM.ID) AS APPLYNUM FROM SYS_TASKS T LEFT JOIN T_ORDER_FIND_ITEM FIND_ITEM ON T.OBJECT_ID = FIND_ITEM.FID WHERE T.TYPE = "+taskType+" AND T.FINISHED = "+taskFinished+" GROUP BY T.OBJECT_ID ) A ON A.OBJECT_ID = TASK.OBJECT_ID ");
    	sb.append(" LEFT JOIN (SELECT A.FID,A.CREATETIME FROM (SELECT FIND_ITEM.FID, FIND_ITEM.CREATETIME FROM T_ORDER_FIND_ITEM FIND_ITEM ORDER BY FIND_ITEM.CREATETIME DESC ) A GROUP BY A.FID ) B ON B.FID = TASK.OBJECT_ID ");
    	sb.append(" WHERE TASK.TYPE = "+taskType+" AND TASK.FINISHED = "+taskFinished);
    	if(task_owner!=0){
    		sb.append(" AND TASK.OWNER = "+task_owner);
    	}
    	if(StringUtils.isNotEmpty(orderBy)){
    		sb.append(orderBy);
    	}
    	return sb.toString();
    }
    
    public int countTaskForList(Map<String, Object> params,String orderBy) {
        StringBuilder sb = new StringBuilder(" select count(*) from ( ");
        sb.append(buildQSql(params,orderBy));
        sb.append(" )  task ");
        if (logger.isDebugEnabled()) {
            logger.debug("Generate count task sql: {}", sb.toString());
        }
        return getNamedParameterJdbcTemplate().queryForObject(sb.toString(), params, Integer.class);
    }
    
    public List<Task> queryTaskForList(Map<String,Object> params,int start,int count,String orderBy){
    	if(params == null){
    		return null;
    	}
    	StringBuilder sb = new StringBuilder(this.buildQSql(params,orderBy));
    	if(count > 0){
    		sb.append(" limit " + count);
    	}
    	if (start > 0) {
            sb.append(" offset " + start);
        }
    	if (logger.isDebugEnabled()) {
            logger.debug("Task dao generated task query sql: {}", sb.toString());
        }
    	return this.getNamedParameterJdbcTemplate().query(sb.toString(), params, new RowMapper<Task>() {
			@Override
			public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				Task t = new Task();
				
	            String UID = rs.getString("UID");
	            if(StringUtils.isNotEmpty(UID)){
	            	TUser tUser = new TUser();
					tUser.setId(UID);
					tUser.setCid(rs.getString("UCID"));
					tUser.setNick(rs.getString("UNICK"));
					tUser.setUsername(rs.getString("UUSERNAME"));
					tUser.setPhone(rs.getString("UPHONE"));
					tUser.setCreatedate(rs.getTimestamp("UCREATEDATE"));
					tUser.setLogo(rs.getString("ULOGO"));
					tUser.setPassword(rs.getString("UPASSWORD"));
					tUser.setStatus(UserStatus.enumOf(rs.getString("USTATUS")));
					tUser.setUpdatedate(rs.getTimestamp("UUPDATEDATE"));
					tUser.setClientid(rs.getString("UCLIENTID"));
					tUser.setClienttype(ClientTypeEnum.enumOf(rs.getString("UCLIENTTYPE")));
					tUser.setVersion(rs.getString("UVERSION"));
					t.setCustomer(tUser);
	            }
				
	            String COMID = rs.getString("COMID");
	            if(StringUtils.isNotEmpty(COMID)){
	            	TCompanyInfo tCompanyInfo = new TCompanyInfo();
					tCompanyInfo.setId(COMID);
					tCompanyInfo.setCname(rs.getString("CCNAME"));
					tCompanyInfo.setAuthstatus(AuthRecordStatus.enumOf(rs.getString("CAUTHSTATUS")));
					tCompanyInfo.setContact(rs.getString("CCONTACT"));
					tCompanyInfo.setCphone(rs.getString("CCPHONE"));
					tCompanyInfo.setCreatedate(rs.getTimestamp("CCREATEDATE"));
					tCompanyInfo.setCtype(CompanyType.enumOf(rs.getString("CCTYPE")));
					tCompanyInfo.setMark(rs.getString("CMARK"));
					tCompanyInfo.setStatus(rs.getString("CSTATUS"));
					tCompanyInfo.setUpdatedate(rs.getTimestamp("CUPDATEDATE"));
					tCompanyInfo.setUpdater(rs.getString("CUPDATER"));
					tCompanyInfo.setTel(rs.getString("CTEL"));
					tCompanyInfo.setBailstatus(CompanyBailStatus.enumOf(rs.getString("CBAILSTATUS")));
					t.setCompany(tCompanyInfo);
	            }
				
	            Integer ownerId = rs.getInt("SID");
	            if (ownerId != null && ownerId != 0) {
	            	User user = new User();
					user.setId(ownerId);
		            user.setUserName(rs.getString("SUSERNAME"));
		            user.setRealName(rs.getString("SREALNAME"));
		            user.setPassword(rs.getString("SPASSWORD"));
		            user.setEnabled(rs.getBoolean("SENABLED"));
		            user.setLastLoginTime(rs.getTimestamp("SLAST_LOGIN_TIME"));
		            user.setCreateTime(rs.getTimestamp("SCREATE_TIME"));
					t.setOwner(user);
	            }
				
	            String FFID = rs.getString("FFID");
	            if(StringUtils.isNotEmpty(FFID)){
	            	TOrderFind orderFind = new TOrderFind();
					orderFind.setId(FFID);
					orderFind.setAddresstype(OrderAddressTypeEnum.enumOf(rs.getInt("FADDRESSTYPE")));
					orderFind.setArea(rs.getString("FAREA"));
					orderFind.setCid(rs.getString("FCID"));
					orderFind.setCreater(rs.getString("FCREATER"));
					orderFind.setCreatime(rs.getTimestamp("FCREATIME"));
					orderFind.setEndtime(rs.getTimestamp("FENDTIME"));
					orderFind.setLimitime(rs.getTimestamp("FLIMITIME"));
					orderFind.setMorearea(OrderMoreAreaEnum.enumOf(rs.getString("FMOREAREA")));
					orderFind.setNum(rs.getFloat("FNUM"));
					orderFind.setParentid(rs.getString("FPARENTID"));
					orderFind.setPrice(rs.getFloat("FPRICE"));
					orderFind.setRemark(rs.getString("FREMARK"));
					orderFind.setStarttime(rs.getTimestamp("FSTARTTIME"));
					orderFind.setStatus(OrderStatusEnum.enumOf(rs.getInt("FSTATUS")));
					orderFind.setTitle(rs.getString("FTITlE"));
					orderFind.setTotalnum(rs.getFloat("FTOTALNUM"));
					orderFind.setType(OrderTypeEnum.enumOf(rs.getInt("FTYPE")));
					orderFind.setUpdater(rs.getString("FUPDATER"));
					orderFind.setUpdatetime(rs.getTimestamp("FUPDATETIME"));
					orderFind.setOverallstatus(OrderOverallStatusEnum.enumOf(rs.getInt("FOVERALLSTATUS")));
					orderFind.setContractid(rs.getString("FCONTRACTID"));
					orderFind.setMatchingnum(rs.getInt("FMATCHINGNUM"));
					orderFind.setOveralltype(OrderOverallTypeEnum.enumOf(rs.getInt("FOVERALLTYPE")));
					orderFind.setApplyDate(rs.getTimestamp("FAPPLYDATE"));
					orderFind.setApplyNum(rs.getInt("FAPPLYNUM"));
					orderFind.setGuaranty(rs.getDouble("FAMOUNT"));
					t.setTaskObject(orderFind);
	            }
	            
				t.setUpdateTime(rs.getTimestamp("TUPDATE_TIME"));
				t.setCreateTime(rs.getTimestamp("TCREATE_TIME"));
				t.setFinishTime(rs.getTimestamp("TFINISH_TIME"));
				t.setFinished(rs.getBoolean("TFINISHED"));
				t.setClaimTime(rs.getTimestamp("TCLAIM_TIME"));
				t.setObjectId(rs.getString("TOBJECT_ID"));
				t.setType(TaskType.valueOf(rs.getInt("TTYPE")));
				t.setId(rs.getString("TID"));
				
				return t;
			}
		});
    }

    private static class RawTaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task t = new Task();
            t.setId(rs.getString(FIELD_ID));
            t.setType(TaskType.valueOf(rs.getInt(FIELD_TYPE)));
            t.setObjectId(rs.getString(FIELD_OBJECT_ID));
            t.setClaimTime(rs.getTimestamp(FIELD_CLAIM_TIME));
            t.setFinished(rs.getBoolean(FIELD_FINISHED));
            t.setFinishTime(rs.getTimestamp(FIELD_FINISH_TIME));
            t.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
            t.setUpdateTime(rs.getTimestamp(FIELD_UPDATE_TIME));
            return t;
        }
    }

    private static class TaskRowMapper extends RawTaskRowMapper {

        private final RowMapper<?> taskObjectRowMapper;

        public TaskRowMapper(RowMapper<?> taskObjectRowMapper) {
            this.taskObjectRowMapper = taskObjectRowMapper;
        }

        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task t = super.mapRow(rs, rowNum);
            Integer ownerId = (Integer) rs.getObject(FIELD_OWNER);
            if (ownerId != null) {
                t.setOwner(SYS_USER_MAPPER.mapRow(rs, rowNum));
            }
            t.setCompany(COMPANY_INFO_ROW_MAPPER.mapRow(rs, rowNum));
            if (t.getObjectId() != null && taskObjectRowMapper != null) {
                t.setTaskObject(taskObjectRowMapper.mapRow(rs, rowNum));
            }
            t.setCustomer(CUSTOMER_USER_ROW_MAPPER.mapRow(rs, rowNum));
            return t;
        }
    }

    private static class SysUserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt(SYSUSER_FIELD_ID));
            user.setUserName(rs.getString(SYSUSER_FIELD_USERNAME));
            user.setRealName(rs.getString(SYSUSER_FIELD_REALNAME));
            return user;
        }
    }

    private static class CompanyInfoRowMapper implements RowMapper<TCompanyInfo> {
        @Override
        public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            TCompanyInfo t = new TCompanyInfo();

            t.setId(rs.getString(COMPANY_FIELD_ID));
            t.setCname(rs.getString(COMPANY_FIELD_CNAME));
            t.setAuthstatus(AuthRecordStatus.enumOf(rs.getString(COMPANY_FIELD_AUTHSTATUS)));
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

    private static class CustomerUserRowMapper implements RowMapper<TUser> {

        @Override
        public TUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            TUser u = new TUser();
            u.setId(rs.getString(CUSTOMER_FIELD_ID));
            u.setPhone(rs.getString(CUSTOMER_FIELD_PHONE));
            return u;
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

    /**
     * 根据类型和业务ID删除任务
     * @param type
     * @param objectId
     */
    public void delByTypeAndObjectId(TaskType type, String objectId){

    	if(type != null && StringUtils.isNotEmpty(objectId)){
    		Task entity = new Task();
    		entity.setType(type);
    		entity.setObjectId(objectId);
    		SqlParameterSource paramSource = new MultiTypeBeanPropertySqlParameterSource(entity);
    		getNamedParameterJdbcTemplate().update(DELETE_BY_TYPE_AND_OBJECTID_SQL, paramSource);
    	}else{
    		logger.info("delete error, type="+type+",objectId="+objectId);
    	}
    }

}
