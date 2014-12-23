package com.appabc.datas.system.dao;

import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.pvo.TAuthRecord;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zouxifeng on 11/26/14.
 */
@Repository
public class VerifyInfoTaskDao extends AbstractTaskDao<TAuthRecord> {

    @Override
    protected String getJoinTableName() {
        return "T_AUTH_RECORD";
    }

    @Override
    protected String getShortJoinTableName() {
        return "ar";
    }

    @Override
    protected String[] getJoinTableFields() {
        return new String[] {"ar.AUTHID", "ar.CID", "ar.IMGID", "ar.AUTHSTATUS", "ar.DEALSTATUS", "ar.CREATEDATE",
                             "ar.AUTHOR", "ar.AUTHRESULT", "ar.AUTHDATE", "ar.REMARK", "ar.TYPE", "ar.ABID"};
    }

    @Override
    protected String getJoinTableIdFieldName() {
        return "AUTHID";
    }

    @Override
    protected RowMapper<TAuthRecord> getJoinTableRowMapper() {
        return new RowMapper<TAuthRecord>() {
            @Override
            public TAuthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                TAuthRecord t = new TAuthRecord();
                t.setId(rs.getString("ar.AUTHID"));
                t.setAuthstatus(AuthRecordInfo.AuthRecordStatus.enumOf(rs.getString("ar.AUTHSTATUS")));
                t.setAuthdate(rs.getTimestamp("ar.AUTHDATE"));
                t.setAuthor(rs.getString("ar.AUTHOR"));
                t.setAuthresult(rs.getString("ar.AUTHRESULT"));
                t.setCid(rs.getString("ar.CID"));
                t.setCreatedate(rs.getTimestamp("ar.CREATEDATE"));
                t.setDealstatus(rs.getInt("ar.DEALSTATUS"));
                t.setImgid(rs.getInt("ar.IMGID"));
                t.setRemark(rs.getString("ar.REMARK"));
                t.setType(AuthRecordInfo.AuthRecordType.enumOf(rs.getInt("ar.TYPE")));
                t.setAbid(rs.getString("ar.ABID"));
                return t;
            }
        };
    }
}
