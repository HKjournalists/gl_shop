package com.appabc.datas.cms.dao.tasks;

import org.springframework.jdbc.core.RowMapper;

/**
 * Defines meta data for task dao.
 *
 * Created by zouxifeng on 12/12/14.
 */
public interface ITaskDaoMeta<T> {

    String getJoinTableName();

    String getJoinTableAliasName();

    String[] getJoinTableFields();

    String getJoinTableIdFieldName();

    boolean requireCompanyAuthenticated();

    RowMapper<T> getJoinTableRowMapper();
}
