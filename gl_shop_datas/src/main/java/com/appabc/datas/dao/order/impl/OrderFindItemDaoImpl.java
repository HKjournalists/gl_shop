/**
 *
 */
package com.appabc.datas.dao.order.impl;

import com.appabc.bean.enums.OrderFindInfo.OrderItemEnum;
import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderFindItemDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : 询单交易申请记录DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:55:00
 */
@Repository
public class OrderFindItemDaoImpl extends BaseJdbcDao<TOrderFindItem> implements IOrderFindItemDao {

	private static final String INSERTSQL = " insert into T_ORDER_FIND_ITEM (FID, UPDATER, CREATETIME, DEALER, RESULT, REMARK, DEALTIME, STATUS) values (:fid, :updater, :createtime, :dealer, :result, :remark, :dealtime, :status) ";
	private static final String UPDATESQL = " update T_ORDER_FIND_ITEM set FID = :fid, UPDATER = :updater, CREATETIME = :createtime, DEALER = :dealer, RESULT = :result, REMARK = :remark, DEALTIME = :dealtime, STATUS=:status where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_FIND_ITEM WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_FIND_ITEM WHERE ID = :id ";
	private static final String COUNT_SQL  = " SELECT COUNT(0) FROM T_ORDER_FIND_ITEM WHERE 1=1 ";

	private static final String BASE_SQL = " SELECT * FROM T_ORDER_FIND_ITEM WHERE 1=1 ";

	public void save(TOrderFindItem entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderFindItem entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TOrderFindItem entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderFindItem entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderFindItem query(TOrderFindItem entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TOrderFindItem query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderFindItem> queryForList(TOrderFindItem entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TOrderFindItem> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TOrderFindItem> queryListForPagination(
			QueryContext<TOrderFindItem> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TOrderFindItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderFindItem t = new TOrderFindItem();

		t.setId(rs.getString("ID"));
		t.setFid(rs.getString("FID"));
		t.setCreatetime(rs.getTimestamp("CREATETIME"));
		t.setDealer(rs.getString("DEALER"));
		t.setDealtime(rs.getTimestamp("DEALTIME"));
		t.setRemark(rs.getString("REMARK"));
		t.setResult(rs.getString("RESULT"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setStatus(OrderItemEnum.enumOf(rs.getInt("STATUS")));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TOrderFindItem bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "fid", "FID", bean.getFid());
		this.addNameParamerSqlWithProperty(sql, "dealer", "DEALER", bean.getDealer());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "result", "RESULT", bean.getResult());
		this.addNameParamerSqlWithProperty(sql, "updater", "UPDATER", bean.getUpdater());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		return sql.toString();
	}
	
	@SuppressWarnings("deprecation")
	public int countByFid(String fid){
		StringBuilder sql = new StringBuilder(COUNT_SQL);
		List<Object> args = new ArrayList<Object>();

		sql.append(" AND FID=?");
		args.add(fid);

		return super.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.dao.order.IOrderFindItemDao#queryOrderFindItemListByFid(java.lang.String)  
	 */
	@Override
	public List<TOrderFindItem> queryOrderFindItemListByFid(String fid) {
		if(StringUtils.isEmpty(fid)){
			return null;
		}
		StringBuilder sql = new StringBuilder(BASE_SQL);
		List<Object> args = new ArrayList<Object>();
		sql.append(" AND FID=?");
		args.add(fid);
		sql.append(" ORDER BY CREATETIME DESC ");
		return getJdbcTemplate().query(sql.toString(), args.toArray(), this);
		//return getJdbcTemplate().queryForList(sql.toString(), args.toArray(), TOrderFindItem.class);
	}

}
