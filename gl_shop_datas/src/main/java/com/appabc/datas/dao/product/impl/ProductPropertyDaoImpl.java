/**
 *
 */
package com.appabc.datas.dao.product.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.product.IProductPropertyDao;


/**
 * @Description : 商品其它属性DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午3:37:09
 */
@Repository
public class ProductPropertyDaoImpl extends BaseJdbcDao<TProductProperty> implements IProductPropertyDao{
	
	private static final String BASE_SQL = " SELECT * FROM T_PRODUCT_PROPERTY WHERE 1=1 "; 
	private static final String INSERTSQL = "INSERT INTO T_PRODUCT_PROPERTY (PID, NAME, TYPES, MAXV, MINV, CONTENT, STATUS, ORDERNO) VALUES (:pid, :name, :types, :maxv, :minv, :content, :status, :orderno)";
	private static final String UPDATESQL = " update T_PRODUCT_PROPERTY set PID = :pid, NAME = :name, TYPES = :types, MAXV = :maxv, MINV = :minv, CONTENT = :content, STATUS = :status, ORDERNO=:orderno  where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_PRODUCT_PROPERTY WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PRODUCT_PROPERTY WHERE ID = :id ";
	private static final String DELETESQLBYPID = "DELETE FROM T_PRODUCT_PROPERTY WHERE PID = :pid";
	
	public TProductProperty mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		TProductProperty pp = new TProductProperty();
		
		pp.setId(rs.getString("ID"));
		pp.setContent(rs.getString("CONTENT"));
		pp.setMaxv(rs.getFloat("MAXV"));
		pp.setMinv(rs.getFloat("MINV"));
		pp.setName(rs.getString("NAME"));
		pp.setPid(rs.getString("PID"));
		pp.setStatus(rs.getString("STATUS"));
		pp.setTypes(rs.getString("TYPES"));
		pp.setOrderno(rs.getInt("ORDERNO"));

		return pp;
	}

	/* (non-Javadoc)根据商品ID删除其属性
	 * @see com.appabc.datas.dao.product.IProductPropertyDao#delByPid(java.lang.String)
	 */
	public void delByPid(String pid){
		
		TProductProperty entity = new TProductProperty();
		entity.setPid(pid);
		
		super.delete(DELETESQLBYPID, entity);
	}

	public void save(TProductProperty entity) {
		saveAutoGenerateKey(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TProductProperty entity) {
		return null;
	}

	public void update(TProductProperty entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductProperty entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TProductProperty query(TProductProperty entity) {
		return null;
	}

	public TProductProperty query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TProductProperty> queryForList(TProductProperty entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuffer(BASE_SQL)), entity);
	}

	public List<TProductProperty> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TProductProperty> queryListForPagination(
			QueryContext<TProductProperty> qContext) {
		return null;
	}

	private String dynamicJoinSqlWithEntity(TProductProperty bean,StringBuffer sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "content", "CONTENT", bean.getContent());
		this.addNameParamerSqlWithProperty(sql, "maxv", "MAXV", bean.getMaxv());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", bean.getPid());
		this.addNameParamerSqlWithProperty(sql, "minv", "MINV", bean.getMinv());
		this.addNameParamerSqlWithProperty(sql, "name", "NAME", bean.getName());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "types", "TYPES", bean.getTypes());
		this.addNameParamerSqlWithProperty(sql, "orderno", "ORDERNO", bean.getOrderno());
		return sql.toString();
	}


}
