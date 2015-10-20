package com.appabc.datas.dao.order.impl;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.pagination.ISQLGenerator;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.common.utils.pagination.PaginationInfoDataBaseBuiler;
import com.appabc.datas.dao.order.IOrderFindMatchDAO;
import com.appabc.datas.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年4月30日 下午4:45:18
 */

@Repository
public class OrderFindMatchDAOImpl extends BaseJdbcDao<TOrderFindMatch> implements
		IOrderFindMatchDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_FIND_MATCH (OWNER,TARGET,GUARANTY,OP_TYPE,STATUS,TITLE,OPFID,OCFID,TFID,REMARK) VALUES (:owner,:target,:guaranty,:opType,:status,:title,:opfid,:ocfid,:tfid,:remark) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_FIND_MATCH SET OWNER = :owner,TARGET = :target,GUARANTY = :guaranty,OP_TYPE = :opType,STATUS = :status,TITLE = :title,OPFID = :opfid,OCFID = :ocfid,TFID = :tfid,REMARK = :remark WHERE RID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_FIND_MATCH WHERE RID = :id ";
	private static final String SELECT_SQL = " SELECT RID,OWNER,TARGET,GUARANTY,OP_TYPE,STATUS,TITLE,OPFID,OCFID,TFID,REMARK FROM T_ORDER_FIND_MATCH ";

	private String dynamicJoinSqlWithEntity(TOrderFindMatch entity,StringBuilder sql) {
		if (entity == null || sql == null || sql.length() <= 0) {
			return sql.toString();
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "RID", entity.getId());
		addNameParamerSqlWithProperty(sql, "owner", "OWNER", entity.getOwner());
		addNameParamerSqlWithProperty(sql, "target", "TARGET", entity.getTarget());
		addNameParamerSqlWithProperty(sql, "guaranty", "GUARANTY", entity.getGuaranty());
		addNameParamerSqlWithProperty(sql, "opType", "OP_TYPE", entity.getOpType());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "title", "TITLE", entity.getTitle());
		addNameParamerSqlWithProperty(sql, "opfid", "OPFID", entity.getOpfid());
		addNameParamerSqlWithProperty(sql, "ocfid", "OCFID", entity.getOcfid());
		addNameParamerSqlWithProperty(sql, "tfid", "TFID", entity.getTfid());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());

		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void save(TOrderFindMatch entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TOrderFindMatch entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void update(TOrderFindMatch entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public void delete(TOrderFindMatch entity) {
		super.delete(DELETE_SQL, entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) {
		super.delete(DELETE_SQL, id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public TOrderFindMatch query(TOrderFindMatch entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)
	 */
	@Override
	public TOrderFindMatch query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE RID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)
	 */
	@Override
	public List<TOrderFindMatch> queryForList(TOrderFindMatch entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity, sql), entity);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)
	 */
	@Override
	public List<TOrderFindMatch> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TOrderFindMatch> queryListForPagination(
			QueryContext<TOrderFindMatch> qContext) {
		return super.queryListForPagination(SELECT_SQL, qContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public TOrderFindMatch mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderFindMatch t = new TOrderFindMatch();

		t.setId(rs.getString("RID"));
		t.setOwner(rs.getString("OWNER"));
		t.setTarget(rs.getString("TARGET"));
		t.setGuaranty(rs.getDouble("GUARANTY"));
		t.setOpType(OrderFindMatchOpTypeEnum.enumOf(rs.getInt("OP_TYPE")));
		t.setStatus(OrderFindMatchStatusEnum.enumOf(rs.getInt("STATUS")));
		t.setTitle(rs.getString("TITlE"));
		t.setOpfid(rs.getString("OPFID"));
		t.setOcfid(rs.getString("OCFID"));
		t.setTfid(rs.getString("TFID"));
		t.setRemark(rs.getString("REMARK"));

		return t;
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindMatchDAO#queryOrderFindMatchInfoWithStatus(com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)
	 */
	@Override
	public List<TOrderFindMatch> queryOrderFindMatchInfoWithStatus(String owner,OrderFindMatchStatusEnum status) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, "OWNER", owner, args);
		this.addStandardSqlWithParameter(sql, "STATUS", status == null ? null : status.getVal(), args);
		sql.append(" ORDER BY RID DESC ");
		return super.queryForList(sql.toString(), args);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindMatchDAO#updateOrderFindMatchStatusByPFid(java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)
	 */
	@Override
	public void updateOrderFindMatchStatusByPFid(String parentFId,
			OrderFindMatchStatusEnum status) throws ServiceException {
		if (StringUtils.isEmpty(parentFId) || status == null) {
			throw new ServiceException("the parameters is not allow null.");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE T_ORDER_FIND_MATCH SET STATUS = :status WHERE OCFID = :ocfid ");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", status.getVal());
		paramMap.put("ocfid", parentFId);
		int result = getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
		log.debug(result);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindMatchDAO#updateOrderFindMatchStatusByRid(java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)
	 */
	@Override
	public void updateOrderFindMatchStatusByRid(String rid,
			OrderFindMatchStatusEnum status) throws ServiceException {
		if (StringUtils.isEmpty(rid) || status == null) {
			throw new ServiceException("the parameters is not allow null.");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE T_ORDER_FIND_MATCH SET STATUS = :status WHERE RID = :id ");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", status.getVal());
		paramMap.put("id", rid);
		int result = getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
		log.info(result);
	}

	private RowMapper<TOrderFindMatchEx> rowMapper = new RowMapper<TOrderFindMatchEx>() {
		public TOrderFindMatchEx mapRow(ResultSet rs,int rowNum) throws SQLException {
			TOrderFindMatchEx t = new TOrderFindMatchEx();

			String octype = rs.getString("TCTYPE");
			t.settCtype(CompanyType.enumOf(octype));
			t.settPhone(rs.getString("TPHONE"));
			t.settCname(rs.getString("TCNAME"));
			String tctype = rs.getString("OCTYPE");
			t.setoCtype(CompanyType.enumOf(tctype));
			t.setoPhone(rs.getString("OPHONE"));
			t.setoCname(rs.getString("OCNAME"));
			t.setfPulishTime(rs.getTimestamp("FPULISHTIME"));
			t.setfItemTime(rs.getTimestamp("FITEMTIME"));

			t.setId(rs.getString("RID"));
			t.setOwner(rs.getString("OWNER"));
			t.setTarget(rs.getString("TARGET"));
			t.setGuaranty(rs.getDouble("GUARANTY"));
			t.setOpType(OrderFindMatchOpTypeEnum.enumOf(rs.getInt("OP_TYPE")));
			t.setStatus(OrderFindMatchStatusEnum.enumOf(rs.getInt("STATUS")));
			t.setTitle(rs.getString("TITlE"));
			t.setOpfid(rs.getString("OPFID"));
			t.setOcfid(rs.getString("OCFID"));
			t.setTfid(rs.getString("TFID"));
			t.setRemark(rs.getString("REMARK"));

			return t;
		}
	};

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindMatchDAO#queryOrderFindMatchExInfoWithStatus(java.lang.String, com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum)
	 */
	@Override
	public List<TOrderFindMatchEx> queryOrderFindMatchExInfoWithStatus(String owner, OrderFindMatchStatusEnum status) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT OTU.PHONE AS OPHONE,OTCI.CTYPE AS OCTYPE, TTU.PHONE AS TPHONE, TTCI.CTYPE AS TCTYPE, TOFM.* FROM T_ORDER_FIND_MATCH TOFM ");
		sql.append(" LEFT JOIN T_USER OTU ON OTU.CID = TOFM.OWNER LEFT JOIN T_COMPANY_INFO OTCI ON OTCI.ID = TOFM.OWNER ");
		sql.append(" LEFT JOIN T_USER TTU ON TTU.CID = TOFM.TARGET LEFT JOIN T_COMPANY_INFO TTCI ON TTCI.ID = TOFM.TARGET ");
		sql.append(" WHERE 1 = 1 ");

		List<Object> args = new ArrayList<Object>();
		addStandardSqlWithParameter(sql, "TOFM.OWNER", owner, args);
		addStandardSqlWithParameter(sql, "TOFM.STATUS", status == null ? null : status.getVal(), args);
		sql.append(" ORDER BY TOFM.RID DESC ");

		return getJdbcTemplate().query(sql.toString(), args.toArray(), rowMapper);
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.dao.order.IOrderFindMatchDAO#findOrderFindMatchExInfoForPagination(com.appabc.common.base.QueryContext)
	 */
	@Override
	public QueryContext<TOrderFindMatchEx> findOrderFindMatchExInfoForPagination(QueryContext<TOrderFindMatchEx> qContext) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT OTU.PHONE AS OPHONE,OTCI.CTYPE AS OCTYPE, OTCI.CNAME AS OCNAME, TTU.PHONE AS TPHONE, TTCI.CTYPE AS TCTYPE, TTCI.CNAME AS TCNAME, FIND.CREATIME AS FPULISHTIME, FIND_ITEM.CREATETIME AS FITEMTIME,TOFM.* FROM T_ORDER_FIND_MATCH TOFM ");
		sql.append(" LEFT JOIN T_USER OTU ON OTU.CID = TOFM.OWNER LEFT JOIN T_COMPANY_INFO OTCI ON OTCI.ID = TOFM.OWNER ");
		sql.append(" LEFT JOIN T_USER TTU ON TTU.CID = TOFM.TARGET LEFT JOIN T_COMPANY_INFO TTCI ON TTCI.ID = TOFM.TARGET ");
		sql.append(" LEFT JOIN T_ORDER_FIND FIND ON FIND.FID = TOFM.OPFID LEFT JOIN T_ORDER_FIND_ITEM FIND_ITEM ON (FIND_ITEM.FID = TOFM.OPFID AND FIND_ITEM.UPDATER = TOFM.TARGET) ");
		sql.append(" WHERE 1 = 1 ");

		List<Object> listArgs = new ArrayList<Object>();
		this.addStandardSqlWithParameter(sql, " TOFM.OWNER ", qContext.getParameter("owner"), listArgs);
		
		this.addStandardSqlWithParameter(sql, " TOFM.TARGET ", qContext.getParameter("target"), listArgs);

		int status = qContext.getParameter("status") == null ? null : ((OrderFindMatchStatusEnum)qContext.getParameter("status")).getVal();
		this.addStandardSqlWithParameter(sql, " TOFM.STATUS ", status, listArgs);

		Integer opType = qContext.getParameter("opType") == null ? null : ((OrderFindMatchOpTypeEnum)qContext.getParameter("opType")).getVal();
		this.addStandardSqlWithParameter(sql, " TOFM.OP_TYPE ", opType, listArgs);
		
		this.addStandardSqlWithParameter(sql, " TOFM.OPFID ", qContext.getParameter("opfid"), listArgs);
		
		qContext.setParamList(listArgs);
		sql.append(" ORDER BY TOFM.RID DESC ");

		Object[] args = CollectionUtils.isEmpty(qContext.getParamList()) ? null: qContext.getParamList().toArray();
		if (qContext.getPage().getPageIndex() <= 0) {
			log.info("The Query Sql Is  : " + sql);
			List<TOrderFindMatchEx> list = getJdbcTemplate().query(sql.toString(), args, rowMapper);
			QueryResult<TOrderFindMatchEx> qr = qContext.getQueryResult();
			qr.setResult(list);
			qr.setTotalSize(list.size());
			qContext.setQueryResult(qr);
		} else {
			ISQLGenerator iSQLGenerator = PaginationInfoDataBaseBuiler.generateSQLGenerateFactory();
			String countSql = iSQLGenerator.generateCountSql(sql.toString());
			log.info("The Count Sql Is  : " + countSql);
			PageModel pm = qContext.getPage();
			QueryResult<TOrderFindMatchEx> qr = qContext.getQueryResult();
			String pageSql = iSQLGenerator.generatePageSql(sql.toString(),pm);
			log.info("The Page Sql Is  : " + pageSql);
			// 获取记录总数
			int count = getJdbcTemplate().queryForObject(countSql, args, Integer.class);
			qr.setTotalSize(count);
			pm.setTotalSize(count);
			// 获取分页后的记录数量
			List<TOrderFindMatchEx> list = getJdbcTemplate().query(pageSql, args, rowMapper);
			qr.setResult(list);
		}
		return qContext;
	}

}
