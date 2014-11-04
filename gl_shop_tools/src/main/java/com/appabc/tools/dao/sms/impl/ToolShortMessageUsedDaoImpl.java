/**
 *
 */
package com.appabc.tools.dao.sms.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TShortMessageUsed;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.sms.IToolShortMessageUsedDao;

/**
 * @Description : 短信发送记录DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 上午11:14:35
 */
@Repository
public class ToolShortMessageUsedDaoImpl extends BaseJdbcDao<TShortMessageUsed> implements IToolShortMessageUsedDao{
	
	private static final String INSERTSQL = " insert into T_SHORT_MESSAGE_USED (BUSINESSID, BUSINESSTYPE, SMCONTENT, PHONENUMBER, SENDSTATUS, WAITTIME, SENDTIME, REMARK)values (:businessid, :businesstype, :smcontent, :phonenumber, :sendstatus, :waittime, :sendtime, :remark) ";
	private static final String UPDATESQL = " update T_SHORT_MESSAGE_USED set BUSINESSID = :businessid,BUSINESSTYPE = :businesstype, SMCONTENT = :smcontent, PHONENUMBER = :phonenumber,SENDSTATUS = :sendstatus,WAITTIME = :waittime,SENDTIME = :sendtime,REMARK = :remark where SMUID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_SHORT_MESSAGE_USED where SMUID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_SHORT_MESSAGE_USED WHERE SMUID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_SHORT_MESSAGE_USED WHERE 1=1 "; 

	public void save(TShortMessageUsed entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TShortMessageUsed entity) {
		return null;
	}

	public void update(TShortMessageUsed entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TShortMessageUsed entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TShortMessageUsed query(TShortMessageUsed entity) {
		return null;
	}

	public TShortMessageUsed query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TShortMessageUsed> queryForList(TShortMessageUsed entity) {
		
		StringBuffer sql = new StringBuffer(BASE_SQL);
		
		return super.queryForList(dynamicJoinSqlWithEntity(entity, sql), entity);
	}
	
	private String dynamicJoinSqlWithEntity(TShortMessageUsed bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "SMUID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "businessid", "BUSINESSID", bean.getBusinessid());
		this.addNameParamerSqlWithProperty(sql, "businesstype", "BUSINESSTYPE", bean.getBusinesstype());
		this.addNameParamerSqlWithProperty(sql, "smcontent", "SMCONTENT", bean.getSmcontent());
		this.addNameParamerSqlWithProperty(sql, "phonenumber", "PHONENUMBER", bean.getPhonenumber());
		this.addNameParamerSqlWithProperty(sql, "sendstatus", "SENDSTATUS", bean.getSendstatus());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		return sql.toString();
	}

	public List<TShortMessageUsed> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TShortMessageUsed> queryListForPagination(
			QueryContext<TShortMessageUsed> qContext) {
		return null;
	}

	public TShortMessageUsed mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TShortMessageUsed t = new TShortMessageUsed();
		
		t.setId(rs.getString("SMUID"));
		t.setBusinessid(rs.getString("BUSINESSID"));
		t.setBusinesstype(rs.getString("BUSINESSTYPE"));
		t.setPhonenumber(rs.getString("PHONENUMBER"));
		t.setRemark(rs.getString("REMARK"));
		t.setSendstatus(rs.getInt("SENDSTATUS"));
		t.setSendtime(rs.getTimestamp("SENDTIME"));
		t.setSmcontent(rs.getString("SMCONTENT"));
		t.setWaittime(rs.getTimestamp("WAITTIME"));
		
		return t;
	}

}
