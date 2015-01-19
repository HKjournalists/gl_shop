package com.appabc.datas.dao.contract.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.dao.contract.IContractMineDAO;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月24日 下午5:32:31
 */

@Repository
public class ContractMineDAOImpl extends BaseJdbcDao<TOrderMine> implements IContractMineDAO {

	private static final String INSERT_SQL = " INSERT INTO T_ORDER_MINE (CID,OID,STATUS,LIFECYCLE,REMARK,CREATOR,CREATETIME,UPDATER,UPDATETIME) VALUES (:cid, :oid, :status, :lifecycle, :remark, :creator, :createtime, :updater, :updatetime) ";
	private static final String UPDATE_SQL = " UPDATE T_ORDER_MINE SET CID = :cid,OID = :oid,STATUS = :status,LIFECYCLE = :lifecycle,REMARK = :remark,CREATOR = :creator,CREATETIME = :createtime,UPDATER = :updater,UPDATETIME = :updatetime WHERE ID = :id ";
	private static final String DELETE_SQL = " DELETE FROM T_ORDER_MINE WHERE ID = :id ";
	private static final String SELECT_SQL = " SELECT ID,CID,OID,STATUS,LIFECYCLE,REMARK,CREATOR,CREATETIME,UPDATER,UPDATETIME FROM T_ORDER_MINE ";
	
	private String dynamicJoinSqlWithEntity(TOrderMine entity,StringBuilder sql){
		if(entity==null||sql==null||sql.length()<=0){
			return null;
		}
		sql.append(" WHERE 1 = 1 ");
		addNameParamerSqlWithProperty(sql, "id", "ID", entity.getId());
		addNameParamerSqlWithProperty(sql, "cid", "CID", entity.getCid());
		addNameParamerSqlWithProperty(sql, "oid", "OID", entity.getOid());
		addNameParamerSqlWithProperty(sql, "status", "STATUS", entity.getStatus());
		addNameParamerSqlWithProperty(sql, "lifecycle", "LIFECYCLE", entity.getLifecycle());
		addNameParamerSqlWithProperty(sql, "remark", "REMARK", entity.getRemark());
		addNameParamerSqlWithProperty(sql, "creator", "CREATOR", entity.getCreator());
		addNameParamerSqlWithProperty(sql, "createtime", "CREATETIME", entity.getCreatetime());
		addNameParamerSqlWithProperty(sql, "updater", "UPDATER", entity.getUpdater());
		addNameParamerSqlWithProperty(sql, "updatetime", "UPDATETIME", entity.getUpdatetime());
		return sql.toString();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#save(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void save(TOrderMine entity) {
		super.save(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#saveAutoGenerateKey(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public KeyHolder saveAutoGenerateKey(TOrderMine entity) {
		return super.saveAutoGenerateKey(INSERT_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#update(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void update(TOrderMine entity) {
		super.update(UPDATE_SQL, entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#delete(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public void delete(TOrderMine entity) {
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
	public TOrderMine query(TOrderMine entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.query(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#query(java.io.Serializable)  
	 */
	@Override
	public TOrderMine query(Serializable id) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE ID = :id  ");
		return super.query(sql.toString(), id);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(com.appabc.common.base.bean.BaseBean)  
	 */
	@Override
	public List<TOrderMine> queryForList(TOrderMine entity) {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		return super.queryForList(dynamicJoinSqlWithEntity(entity,sql), entity);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryForList(java.util.Map)  
	 */
	@Override
	public List<TOrderMine> queryForList(Map<String, ?> args) {
		return super.queryForList(SELECT_SQL, args);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.common.base.dao.IBaseDao#queryListForPagination(com.appabc.common.base.QueryContext)  
	 */
	@Override
	public QueryContext<TOrderMine> queryListForPagination(
			QueryContext<TOrderMine> qContext) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		return super.queryListForPagination(sql.toString(), qContext);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)  
	 */
	@Override
	public TOrderMine mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderMine tom = new TOrderMine();
		tom.setId(rs.getString("ID"));
		tom.setCid(rs.getString("CID"));
		tom.setOid(rs.getString("OID"));
		tom.setStatus(ContractStatus.enumOf(rs.getString("STATUS")));
		tom.setLifecycle(ContractLifeCycle.enumOf(rs.getString("LIFECYCLE")));
		tom.setCreator(rs.getString("CREATOR"));
		tom.setCreatetime(rs.getTimestamp("CREATETIME"));
		tom.setRemark(rs.getString("REMARK"));
		tom.setUpdater(rs.getString("UPDATER"));
		tom.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		return tom;
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractMineDAO#queryOrderMineWithCidOid(java.lang.String, java.lang.String)  
	 */
	@Override
	public TOrderMine queryOrderMineWithCidOid(String oid, String cid) {
		
		List<Object> args = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_SQL);
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND CID = ? ");
		args.add(cid);
		sql.append(" AND OID = ? ");
		args.add(oid);
		List<TOrderMine> result = getJdbcTemplate().query(sql.toString(), args.toArray(), this);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.contract.IContractMineDAO#saveOrUpdateMineContractWithCidOid(java.lang.String, java.lang.String, com.appabc.bean.enums.ContractInfo.ContractStatus, com.appabc.bean.enums.ContractInfo.ContractLifeCycle, java.lang.String)  
	 */
	@Override
	public boolean saveOrUpdateMineContractWithCidOid(String oid, String cid,
			ContractStatus status, ContractLifeCycle lifeCycle, String operator) {
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(oid) || status == null || lifeCycle == null || StringUtils.isEmpty(operator)){
			return false;
		}
		TOrderMine tom = this.queryOrderMineWithCidOid(oid, cid);
		Date now = DateUtil.getNowDate();
		if(tom == null){
			TOrderMine t = new TOrderMine();
			t.setCid(cid);
			t.setOid(oid);
			t.setStatus(status);
			t.setLifecycle(lifeCycle);
			t.setCreator(operator);
			t.setCreatetime(now);
			save(t);
		} else {			
			tom.setStatus(status);
			tom.setLifecycle(lifeCycle);
			tom.setUpdater(operator);
			tom.setUpdatetime(now);
			update(tom);
		}
		return true;
	}

}
