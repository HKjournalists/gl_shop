package com.appabc.datas.cms.dao.tasks;

import com.appabc.bean.bo.OrderAllInfor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zouxifeng on 12/11/14.
 */
public class OrderRequestMeta implements ITaskDaoMeta<OrderAllInfor> {

    public final static OrderRequestMeta INSTANCE = new OrderRequestMeta();

    private final static String TABLE_ALIAS = "order_request";

    @Override
    public String getJoinTableName() {
        return "T_ORDER_FIND";
    }

    @Override
    public String getJoinTableAliasName() {
        return TABLE_ALIAS;
    }

    @Override
    public String[] getJoinTableFields() {
        return new String[] {"FID", "TITLE", "TYPE", "MATCHINGNUM", "CREATIME"};
    }

    @Override
    public String getJoinTableIdFieldName() {
        return "FID";
    }

    @Override
    public boolean requireCompanyAuthenticated() {
        return false;
    }

    @Override
    public RowMapper<OrderAllInfor> getJoinTableRowMapper() {
        return OrderRequestRowMapper.INSTANCE;
    }

    private static class OrderRequestRowMapper implements RowMapper<OrderAllInfor> {

        public final static OrderRequestRowMapper INSTANCE = new OrderRequestRowMapper();

        private String composeFieldName(String name) {
            return TABLE_ALIAS + '.' + name;
        }

        @Override
        public OrderAllInfor mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderAllInfor obj = new OrderAllInfor();
            obj.setId(rs.getString(composeFieldName("FID")));
            obj.setTitle(rs.getString(composeFieldName("TITLE")));
            obj.setType(rs.getInt(composeFieldName("TYPE")));
            obj.setMatchingnum(rs.getInt(composeFieldName("MATCHINGNUM")));
            obj.setCreatime(rs.getTimestamp(composeFieldName("CREATIME")));
            return obj;
        }
    }
}
