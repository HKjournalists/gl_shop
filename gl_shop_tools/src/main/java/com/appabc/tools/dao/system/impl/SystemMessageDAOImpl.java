package com.appabc.tools.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appabc.bean.bo.SystemMessageEx;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.bean.enums.SystemInfo.SystemCategory;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.tools.dao.system.ISystemMessageDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午2:39:11
 */

@Component(value="ISystemMessageDAO")
public class SystemMessageDAOImpl extends BaseJdbcDao<TSystemMessage> implements
		ISystemMessageDAO {

	private static final String INSERT_SQL = " INSERT INTO T_SYSTEM_MESSAGE (MSGID,QYID,CONTENT,TYPE,BUSINESSID,BUSINESSTYPE,STATUS,CREATOR,CREATETIME,READTIME,DELETETIME,SYSTEMCATEGORY,REMARK) VALUES (:id,:qyid,:content,:type,:businessid,:businesstype,:status,:creator,:createtime,:deletetime,:readtime,:systemcategory,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_SYSTEM_MESSAGE SET QYID = :qyid,CONTENT = :content,TYPE = :type,BUSINESSID = :businessid,BUSINESSTYPE = :businesstype,STATUS = :status,CREATOR = :creator,CREATETIME = :createtime,READTIME= :readtime,DELETETIME= :deletetime,SYSTEMCATEGORY = :systemcategory,REMARK = :remark WHERE MSGID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_SYSTEM_MESSAGE WHERE MSGID = :id ";
	private static final String SELECT_SQL = " SELECT MSGID,QYID,CONTENT,TYPE,BUSINESSID,BUSINESSTYPE,STATUS,CREATOR,CREATETIME,READTIME,DELETETIME,SYSTEMCATEGORY,REMARK FROM T_SYSTEM_MESSAGE ";
	private static final String COUNT_SQL = " SELECT COUNT(0) AS COUNTNUM FROM T_SYSTEM_MESSAGE WHERE 1=1 ";
	
	private String dynamicJoinSqlWithEntity(TSystemMessage entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "MSGID", entity.getId());
		addNameParamerSqlWithProperty(sql, "qyid", "QYID", entity.getQyid());
		addNameParamerSqlWithProperty(sql, "content", "CONTENT", entity.getContent());
		addNameParamerSqlWithProperty(sql, "type", "TYPE", entity.getType());
		addNameParamerSqlWithProperty(sql, "businessid", "BUSINESSID", entity.getBusinessid());
		addNameParamerSqlWithProperty(sql, "businesstype", "BUSINESSTYPE", entity.getBusinesstype());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "readtime", "READTIME", entity.getReadtime());
		addNameParamerSqlWithProperty(sql, "deletetime", "DELETETIME", entity.getDeletetime());
		addNameParamerSqlWithProperty(sql, "systemcategory", "SYSTEMCATEGORY", entity.getSystemcategory()!=null ? entity.getSystemcategory().getVal() : null);
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		
		return sql.toString();
	}
	
	private RowMapper<SystemMessageEx> r = new RowMapper<SystemMessageEx>() {

		@Override
		public SystemMessageEx mapRow(ResultSet rs, int rowNum) throws SQLException {
			SystemMessageEx bean = new SystemMessageEx();
			
			bean.setId(rs.getString("MSGID"));
			bean.setQyid(rs.getString("QYID"));
			bean.setContent(rs.getString("CONTENT"));
			bean.setType(MsgType.enumOf(rs.getInt("TYPE")));
			bean.setBusinessid(rs.getString("BUSINESSID"));
			bean.setBusinesstype(MsgBusinessType.enumOf(rs.getString("BUSINESSTYPE")));
			bean.setStatus(MsgStatus.enumOf(rs.getInt("STATUS")));
			bean.setCreator(rs.getString("CREATOR"));
			bean.setCreatetime(rs.getTimestamp("CREATETIME"));
			bean.setReadtime(rs.getTimestamp("READTIME"));
			bean.setReadtime(rs.getTimestamp("DELETETIME"));
			bean.setSystemcategory(SystemCategory.enumOf(rs.getInt("SYSTEMCATEGORY")));
			bean.setRemark(rs.getString("REMARK"));
			bean.setQyPhone(rs.getString("PHONE"));
			bean.setOperId(rs.getString("SUID"));
			bean.setOperName(rs.getString("REALNAME"));
			
			return bean;
		}
		
	};
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	public void save(TSystemMessage entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	public KeyHolder saveAutoGenerateKey(TSystemMessage entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	public void update(TSystemMessage entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	public void delete(TSystemMessage entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)  
	 */
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)  
	 */
	public TSystemMessage query(TSystemMessage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	public TSystemMessage query(Serializable id) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE MSGID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	public List<TSystemMessage> queryForList(TSystemMessage entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	public List<TSystemMessage> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	public QueryContext<TSystemMessage> queryListForPagination(
			QueryContext<TSystemMessage> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		
		dynamicJoinSqlWithEntity(qContext.getBeanParameter(), sql);
		addNameParamerSqlWithProperty(sql, "cid", "QYID", qContext.getParameter("cid"));
		qContext.setOrder("DESC");
		qContext.setOrderColumn("CREATETIME");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	public TSystemMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
		TSystemMessage bean = new TSystemMessage();
		
		bean.setId(rs.getString("MSGID"));
		bean.setQyid(rs.getString("QYID"));
		bean.setContent(rs.getString("CONTENT"));
		bean.setType(MsgType.enumOf(rs.getInt("TYPE")));
		bean.setBusinessid(rs.getString("BUSINESSID"));
		bean.setBusinesstype(MsgBusinessType.enumOf(rs.getString("BUSINESSTYPE")));
		bean.setStatus(MsgStatus.enumOf(rs.getInt("STATUS")));
		bean.setCreator(rs.getString("CREATOR"));
		bean.setCreatetime(rs.getTimestamp("CREATETIME"));
		bean.setReadtime(rs.getTimestamp("READTIME"));
		bean.setSystemcategory(SystemCategory.enumOf(rs.getInt("SYSTEMCATEGORY")));
		bean.setRemark(rs.getString("REMARK"));
		
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.system.ISystemMessageDAO#getCountByEntity(com.appabc.bean.pvo.TSystemMessage)
	 */
	@SuppressWarnings("deprecation")
	public int getCountByEntity(TSystemMessage entity) {
		
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		
		List<Object> args = new ArrayList<Object>();
		
		super.addStandardSqlWithParameter(sql, "QYID", entity.getQyid(), args);
		super.addStandardSqlWithParameter(sql, "STATUS", entity.getStatus(), args);
		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.tools.dao.system.ISystemMessageDAO#queryMessageExListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<SystemMessageEx> queryMessageExListForPagination(QueryContext<SystemMessageEx> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TSM.MSGID,TSM.QYID,TSM.CONTENT,TSM.TYPE,TSM.BUSINESSID,TSM.BUSINESSTYPE,TSM.STATUS,TSM.CREATOR,TSM.CREATETIME,TSM.READTIME,TSM.DELETETIME,TSM.SYSTEMCATEGORY,TSM.REMARK,TU.PHONE,SU.ID AS SUID,SU.USERNAME,SU.REALNAME FROM T_SYSTEM_MESSAGE TSM LEFT JOIN T_USER TU ON TSM.QYID = TU.CID LEFT JOIN SYS_USERS SU ON SU.ID = TSM.CREATOR WHERE 1 = 1 ");
		
		List<Object> listArgs = new ArrayList<Object>();
		
		Object status = qContext.getParameter("status");
		if(status != null){
			addStandardSqlWithParameter(sql, " TSM.STATUS ", (int)status, listArgs);
		}
		Object systemCategory = qContext.getParameter("systemCategory");
		if(systemCategory != null){
			addStandardSqlWithParameter(sql, " TSM.SYSTEMCATEGORY ", (int)systemCategory, listArgs);
		}
		//QueryContext.DateQueryEntry dateQueryEntry = (QueryContext.DateQueryEntry)qContext.getParameter("createtime");
		Object createtime = qContext.getParameter("createtime");
		if(createtime != null){
			QueryContext.DateQueryEntry dateQueryEntry = (QueryContext.DateQueryEntry)createtime;
			addStandardSqlWithDateQuery(sql, " TSM.CREATETIME ", dateQueryEntry.getStartDate(), dateQueryEntry.getEndDate());
		}
		
		qContext.setParamList(listArgs);
		
		sql.append(" ORDER BY TSM.CREATETIME DESC ");
		
		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<SystemMessageEx> list = getJdbcTemplate().query(sql.toString(), args, r);
			QueryResult<SystemMessageEx> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<SystemMessageEx> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<SystemMessageEx> list = getJdbcTemplate().query(pageSql, args, r);
			qr.setResult(list);
		}
		return qContext;
	}

	@Override
	public QueryContext<TSystemMessage> queryListByTypeForPagination(
			QueryContext<TSystemMessage> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		
		dynamicJoinSqlWithEntity(qContext.getBeanParameter(), sql);
		addNameParamerSqlWithProperty(sql, "cid", "QYID", qContext.getParameter("cid"));
		addNameParamerSqlWithProperty(sql, "type", "TYPE", qContext.getParameter("type"));
		sql.append(" AND `STATUS` != 2 ");
		qContext.setOrder("DESC");
		qContext.setOrderColumn("CREATETIME");
		return super.queryListForPagination(sql.toString(), qContext);
	}

}