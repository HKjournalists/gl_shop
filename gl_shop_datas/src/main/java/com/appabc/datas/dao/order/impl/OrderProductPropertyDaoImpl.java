/**
 *
 */
package com.appabc.datas.dao.order.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderProductPropertyDao;

/**
 * @Description : 交易中的商品属性DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:30:17
 */
@Repository
public class OrderProductPropertyDaoImpl extends BaseJdbcDao<TOrderProductProperty> implements IOrderProductPropertyDao{
	
	private static final String INSERTSQL = " insert into T_ORDER_PRODUCT_PROPERTY (PPID, PID, NAME, TYPES, MAXV, MINV, CONTENT, STATUS, ORDERNO) values (:ppid, :pid, :name, :types, :maxv, :minv, :content, :status, :orderno) ";
	private static final String UPDATESQL = " update T_ORDER_PRODUCT_PROPERTY set PPID = :ppid, PID = :pid, NAME = :name, TYPES = :types, MAXV = :maxv, MINV = :minv, CONTENT = :content, STATUS = :status, ORDERNO=:orderno where SID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_PRODUCT_PROPERTY WHERE SID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_PRODUCT_PROPERTY WHERE SID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_ORDER_PRODUCT_PROPERTY WHERE 1=1 "; 

	public void save(TOrderProductProperty entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderProductProperty entity) {
		return null;
	}

	public void update(TOrderProductProperty entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderProductProperty entity) {
		StringBuffer sql = new StringBuffer("DELETE FROM T_ORDER_PRODUCT_PROPERTY WHERE 1=1 ");
		dynamicJoinSqlWithEntity(entity,  sql);
		super.delete(sql.toString(), entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderProductProperty query(TOrderProductProperty entity) {
		return null;
	}

	public TOrderProductProperty query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderProductProperty> queryForList(TOrderProductProperty entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TOrderProductProperty> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TOrderProductProperty> queryListForPagination(
			QueryContext<TOrderProductProperty> qContext) {
		return null;
	}

	public TOrderProductProperty mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderProductProperty pp = new TOrderProductProperty();
		
		pp.setId(rs.getString("SID"));
		pp.setContent(rs.getString("CONTENT"));
		pp.setMaxv(rs.getFloat("MAXV"));
		pp.setMinv(rs.getFloat("MINV"));
		pp.setName(rs.getString("NAME"));
		pp.setPid(rs.getString("PID"));
		pp.setPpid(rs.getInt("PPID"));
		pp.setStatus(rs.getString("STATUS"));
		pp.setTypes(rs.getString("TYPES"));
		pp.setOrderno(rs.getInt("ORDERNO"));

		return pp;
	}
	
	private String dynamicJoinSqlWithEntity(TOrderProductProperty bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "SID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "content", "CONTENT", bean.getContent());
		this.addNameParamerSqlWithProperty(sql, "maxv", "MAXV", bean.getMaxv());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", bean.getPid());
		this.addNameParamerSqlWithProperty(sql, "minv", "MINV", bean.getMinv());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", bean.getName());
		this.addNameParamerSqlWithProperty(sql, "ppid", "PPID", bean.getPpid());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "types", "TYPES", bean.getTypes());
		this.addNameParamerSqlWithProperty(sql, "orderno", "ORDERNO", bean.getOrderno());
		return sql.toString();
	}

}
