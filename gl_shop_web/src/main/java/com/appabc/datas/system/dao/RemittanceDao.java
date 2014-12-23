package com.appabc.datas.system.dao;

import com.appabc.bean.enums.PurseInfo;
import com.appabc.datas.system.Remittance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 12/4/14.
 */
@Repository
public class RemittanceDao extends AbstractBaseSystemDao<Remittance> implements RowMapper<Remittance> {

    private final static String TABLE_NAME = "SYS_FINANCE_REMITS";
    private final static String FIELD_ID = "ID";
    private final static String FIELD_AMOUNT = "AMOUNT";
    private final static String FIELD_RECEIVE_TIME = "RECEIVE_TIME";
    private final static String FIELD_BANK_SERIAL_NUMBER = "BANK_SERIAL_NUMBER";
    private final static String FIELD_MOBILE = "MOBILE";
    private final static String FIELD_REMITTER_ACCOUNT = "REMITTER_ACCOUNT";
    private final static String FIELD_REMITTER = "REMITTER";
    private final static String FIELD_CONTRACT_ID = "CONTRACT_ID";
    private final static String FIELD_COMPANY = "COMPANY";
    private final static String FIELD_STATUS = "STATUS";
    private final static String FIELD_REMARK = "REMARK";
    private final static String FIELD_COMPANY_ID = "TARGET_COMPANY_ID";
    private final static String FIELD_WALLET_TYPE = "TARGET_WALLET_TYPE";
    private final static String FIELD_CREATOR = "CREATOR";
    private final static String FIELD_PROCESSOR = "PROCESSOR";
    private final static String FIELD_AUDITOR = "AUDITOR";
    private final static String FIELD_CREATE_TIME = "CREATE_TIME";
    private final static String FIELD_UPDATE_TIME = "UPDATE_TIME";
    private final static String FIELD_FINISH_TIME = "FINISH_TIME";

    private final static String SELECT_SQL = "select * from " + TABLE_NAME + " fr where fr.ID=:ID";

    // FIXME: Remove this after using ORM framework
    @Autowired
    private UserDao userDao;

    public Remittance get(Serializable id) {
        return getNamedParameterJdbcTemplate().queryForObject(SELECT_SQL,
                                new MapSqlParameterSource("ID", id), this);
    }

    public Remittance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Remittance r = new Remittance();
        r.setId(rs.getInt(FIELD_ID));
        r.setAmount(rs.getFloat(FIELD_AMOUNT));
        r.setReceiveTime(rs.getTimestamp(FIELD_RECEIVE_TIME));
        r.setBankSerialNumber(rs.getString(FIELD_BANK_SERIAL_NUMBER));
        r.setRemitterAccount(rs.getString(FIELD_REMITTER_ACCOUNT));
        r.setMobile(rs.getString(FIELD_MOBILE));
        r.setRemitter(rs.getString(FIELD_REMITTER));
        r.setContractId(rs.getString(FIELD_CONTRACT_ID));
        r.setCompany(rs.getString(FIELD_COMPANY));
        r.setStatus(Remittance.Status.valueOf(rs.getInt(FIELD_STATUS)));
        r.setCreateTime(rs.getTimestamp(FIELD_CREATE_TIME));
        r.setUpdateTime(rs.getTimestamp(FIELD_UPDATE_TIME));
        r.setFinishTime(rs.getTimestamp(FIELD_FINISH_TIME));

        if (rs.getObject(FIELD_CREATOR) != null) {
            r.setCreator(userDao.get(rs.getInt(FIELD_CREATOR)));
        }

        if (rs.getObject(FIELD_PROCESSOR) != null) {
            r.setProcessor(userDao.get(rs.getInt(FIELD_PROCESSOR)));
        }

        if (rs.getObject(FIELD_AUDITOR) != null) {
            r.setAuditor(userDao.get(rs.getInt(FIELD_AUDITOR)));
        }

        if (rs.getObject(FIELD_WALLET_TYPE) != null) {
            r.setTargetWalletType(PurseInfo.PurseType.enumOf(rs.getString(FIELD_WALLET_TYPE)));
        }

        r.setTargetCompanyId(rs.getString(FIELD_COMPANY_ID));

        return r;
    }

    public boolean create(final Remittance entity) {
        final StringBuilder sql = new StringBuilder();
        String[] fields = new String[] {FIELD_AMOUNT, FIELD_RECEIVE_TIME, FIELD_BANK_SERIAL_NUMBER, FIELD_MOBILE,
                   FIELD_REMITTER_ACCOUNT, FIELD_REMITTER, FIELD_CONTRACT_ID, FIELD_COMPANY,
                   FIELD_REMARK, FIELD_STATUS, FIELD_CREATOR, FIELD_CREATE_TIME};
        sql.append("insert into " + TABLE_NAME)
                .append("(" + StringUtils.join(fields, ", ") + ")")
                .append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setFloat(1, entity.getAmount());
                ps.setTimestamp(2, new Timestamp(entity.getReceiveTime().getTime()));
                ps.setString(3, entity.getBankSerialNumber());
                ps.setString(4, entity.getMobile());
                ps.setString(5, entity.getRemitterAccount());
                ps.setString(6, entity.getRemitter());
                ps.setString(7, entity.getContractId());
                ps.setString(8, entity.getCompany());
                ps.setString(9, entity.getRemark());
                ps.setInt(10, entity.getStatus().getValue());
                ps.setInt(11, entity.getCreator().getId());
                ps.setTimestamp(12, new Timestamp(entity.getCreateTime().getTime()));
                return ps;
            }
        }, keyHolder);
        entity.setId(keyHolder.getKey().intValue());

        return result == 1;
    }

    public boolean update(Remittance entity) {
        StringBuilder sb = new StringBuilder(256);
        String[] fields = new String[] {
            FIELD_STATUS, FIELD_WALLET_TYPE, FIELD_PROCESSOR, FIELD_AUDITOR, FIELD_UPDATE_TIME, FIELD_FINISH_TIME,
            FIELD_COMPANY_ID
        };

        List<String> paramsPair = new LinkedList<>();
        List<Object> parameters = new LinkedList<>();
        List<Integer> paramTypes = new LinkedList<>();
        paramsPair.add(FIELD_STATUS + "=?");
        parameters.add(entity.getStatus().getValue());
        paramTypes.add(Types.INTEGER);
        if (entity.getProcessor() != null) {
            paramsPair.add(FIELD_PROCESSOR + "=?");
            parameters.add(entity.getProcessor().getId());
            paramTypes.add(Types.INTEGER);
        }
        if (entity.getAuditor() != null) {
            paramsPair.add(FIELD_AUDITOR + "=?");
            parameters.add(entity.getAuditor().getId());
            paramTypes.add(Types.INTEGER);
        }
        if (entity.getFinishTime() != null) {
            paramsPair.add(FIELD_FINISH_TIME + "=?");
            parameters.add(entity.getFinishTime());
            paramTypes.add(Types.TIMESTAMP);
        }
        if (entity.getTargetWalletType() != null) {
            paramsPair.add(FIELD_WALLET_TYPE + "=?");
            parameters.add(entity.getTargetWalletType().getVal());
            paramTypes.add(Types.CHAR);
        }
        paramsPair.add(FIELD_UPDATE_TIME + "=?");
        parameters.add(new Date());
        paramTypes.add(Types.TIMESTAMP);

        paramsPair.add(FIELD_COMPANY_ID + "=?");
        parameters.add(entity.getTargetCompanyId());
        paramTypes.add(Types.VARCHAR);

        parameters.add(entity.getId());
        paramTypes.add(Types.INTEGER);

        sb.append("update " + TABLE_NAME + " set " + StringUtils.join(paramsPair, ","))
                .append(" where " + FIELD_ID + "=?");
        Object[] params = new Object[parameters.size()];
        parameters.toArray(params);
        int[] types = new int[parameters.size()];
        for (int i = 0; i < types.length; i++) {
            types[i] = paramTypes.get(i);
        }
        parameters.toArray(params);
        int result = getJdbcTemplate().update(sb.toString(), params, types);

        return result == 1;
    }

    public boolean delete(Remittance entity) {
        return false;
    }

    public int countBy(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from " + TABLE_NAME + " r ");
        if (!CollectionUtils.isEmpty(params)) {
            sb.append(" where 1=1 ");
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }
        return getNamedParameterJdbcTemplate().queryForObject(sb.toString(), params, Integer.class);
    }

    public List<Remittance> queryForList(Map<String, Object> params, int start, int count, String[] orderBy) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from " + TABLE_NAME);
        if (!CollectionUtils.isEmpty(params)) {
            sb.append(" where 1=1 ");
            for (String key : params.keySet()) {
                sb.append(" and " + key + "=:" + key);
            }
        }

        if (count > 0) {
            sb.append(" limit " + count);
        }

        if (start > 0) {
            sb.append(" offset " + start);
        }

        List<Remittance> result = this.getNamedParameterJdbcTemplate().query(sb.toString(), params, this);
        return result;
    }

}
