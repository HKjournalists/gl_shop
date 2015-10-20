package com.appabc.datas.cms.dao.tasks;

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
public class VerifyInfoMeta implements ITaskDaoMeta<TAuthRecord> {

    public final static VerifyInfoMeta INSTANCE = new VerifyInfoMeta();

    public String getJoinTableName() {
        return "T_AUTH_RECORD";
    }

    public String getJoinTableAliasName() {
        return "ar";
    }

    public String[] getJoinTableFields() {
        return new String[] {"ar.AUTHID", "ar.CID", "ar.IMGID", "ar.AUTHSTATUS", "ar.DEALSTATUS", "ar.CREATEDATE",
                             "ar.AUTHOR", "ar.AUTHRESULT", "ar.AUTHDATE", "ar.REMARK", "ar.TYPE", "ar.ABID"};
    }

    public String getJoinTableIdFieldName() {
        return "AUTHID";
    }

    @Override
    public boolean requireCompanyAuthenticated() {
        return false;
    }

    public RowMapper<TAuthRecord> getJoinTableRowMapper() {
        return TAuthRecordRowMapper.INSTANCE;
    }

    private static class TAuthRecordRowMapper implements RowMapper<TAuthRecord> {

        public final static TAuthRecordRowMapper INSTANCE = new TAuthRecordRowMapper();

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
    }
}
