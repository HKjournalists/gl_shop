package com.appabc.datas.cms.dao.tasks;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.appabc.pay.bean.TPassbookInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月11日 下午2:43:47
 */

public class UrgeDepositMeta implements ITaskDaoMeta<TPassbookInfo>{

	public final static UrgeDepositMeta INSTANCE = new UrgeDepositMeta();
	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#getJoinTableName()  
	 */
	@Override
	public String getJoinTableName() {
		// TODO Auto-generated method stub
		return "T_PASSBOOK_INFO";
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#getJoinTableAliasName()  
	 */
	@Override
	public String getJoinTableAliasName() {
		// TODO Auto-generated method stub
		return "TPI";
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#getJoinTableFields()  
	 */
	@Override
	public String[] getJoinTableFields() {
		// TODO Auto-generated method stub
		return new String[] {"PASSID"};
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#getJoinTableIdFieldName()  
	 */
	@Override
	public String getJoinTableIdFieldName() {
		// TODO Auto-generated method stub
		return "PASSID";
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#requireCompanyAuthenticated()  
	 */
	@Override
	public boolean requireCompanyAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.cms.dao.tasks.ITaskDaoMeta#getJoinTableRowMapper()  
	 */
	@Override
	public RowMapper<TPassbookInfo> getJoinTableRowMapper() {
		// TODO Auto-generated method stub
		return UrgeDepositRowMapper.INSTANCE;
	}

	private static class UrgeDepositRowMapper implements RowMapper<TPassbookInfo> {
        public final static UrgeDepositRowMapper INSTANCE = new UrgeDepositRowMapper();
         @Override
        public TPassbookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        	 TPassbookInfo tpi=new TPassbookInfo();  
        	tpi.setId(rs.getString("PASSID"));
            return tpi;
        }
    }
}
