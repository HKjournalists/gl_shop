package com.appabc.datas.cms.dao.tasks;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TCompanyInfo;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月8日 下午3:56:03
 */
@Repository
public class UrgeVerifyMeta implements ITaskDaoMeta<TCompanyInfo> {

	public final static UrgeVerifyMeta INSTANCE = new UrgeVerifyMeta();
	
	@Override
	public String getJoinTableName() {	
		return "T_COMPANY_INFO";
	}
	@Override
	public String getJoinTableAliasName() {
		return "TCI";
	}
	@Override
	public String[] getJoinTableFields() {
		return new String[] {"ID"};
	}
	@Override
	public String getJoinTableIdFieldName() {
		return "ID";
	}
	@Override
	public boolean requireCompanyAuthenticated() {	
		return false;
	}	
	@Override
	public RowMapper<TCompanyInfo> getJoinTableRowMapper() {
		return UrgeVerifyRowMapper.INSTANCE;
	}
	private static class UrgeVerifyRowMapper implements RowMapper<TCompanyInfo> {
        public final static UrgeVerifyRowMapper INSTANCE = new UrgeVerifyRowMapper();
         @Override
        public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TCompanyInfo tci=new TCompanyInfo();  
        	tci.setId(rs.getString("ID"));
            return tci;
        }
    }
}
