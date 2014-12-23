/**
 *
 */
package com.appabc.datas.dao.order.impl;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderAddressDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 询单或合同卸货地址Dao实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午3:48:47
 */
@Repository
public class OrderAddressDaoImpl extends BaseJdbcDao<TOrderAddress> implements IOrderAddressDao {

	private static final String INSERTSQL = " insert into T_ORDER_ADDRESS (FID, OID, TYPE, CREATIME, CRATER, CID, REALDEEP, AREACODE, ADDRESS, LONGITUDE, LATITUDE, DEEP) values (:fid, :oid, :type, :creatime, :crater, :cid, :realdeep, :areacode, :address, :longitude, :latitude, :deep) ";
	private static final String UPDATESQL = " update T_ORDER_ADDRESS set FID = :fid, OID = :oid, TYPE = :type, CREATIME = :creatime, CRATER = :crater, CID = :cid, REALDEEP = :realdeep, AREACODE = :areacode, ADDRESS = :address, LONGITUDE = :longitude, LATITUDE = :latitude, DEEP = :deep where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_ADDRESS WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_ADDRESS WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_ORDER_ADDRESS WHERE 1=1 ";

	public void save(TOrderAddress entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderAddress entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TOrderAddress entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderAddress entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderAddress query(TOrderAddress entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TOrderAddress query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderAddress> queryForList(TOrderAddress entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TOrderAddress> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TOrderAddress> queryListForPagination(
			QueryContext<TOrderAddress> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TOrderAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
		TOrderAddress t = new TOrderAddress();

		t.setId(rs.getString("ID"));
		t.setCid(rs.getString("CID"));
		t.setCreatime(rs.getTimestamp("CREATIME"));
		t.setAddress(rs.getString("ADDRESS"));
		t.setAreacode(rs.getString("AREACODE"));
		t.setCrater(rs.getString("CRATER"));
		t.setDeep(rs.getFloat("DEEP"));
		t.setFid(rs.getString("FID"));
		t.setLatitude(rs.getString("LATITUDE"));
		t.setLongitude(rs.getString("LONGITUDE"));
		t.setOid(rs.getString("OID"));
		t.setRealdeep(rs.getFloat("REALDEEP"));
		t.setType(rs.getString("TYPE"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TOrderAddress bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "address", "ADDRESS", bean.getAddress());
		this.addNameParamerSqlWithProperty(sql, "areacode", "AREACODE", bean.getAreacode());
		this.addNameParamerSqlWithProperty(sql, "crater", "CRATER", bean.getCrater());
		this.addNameParamerSqlWithProperty(sql, "deep", "DEEP", bean.getDeep());
		this.addNameParamerSqlWithProperty(sql, "fid", "FID", bean.getFid());
		this.addNameParamerSqlWithProperty(sql, "latitude", "LATITUDE", bean.getLatitude());
		this.addNameParamerSqlWithProperty(sql, "longitude", "LONGITUDE", bean.getLongitude());
		this.addNameParamerSqlWithProperty(sql, "oid", "OID", bean.getOid());
		this.addNameParamerSqlWithProperty(sql, "realdeep", "REALDEEP", bean.getRealdeep());
		this.addNameParamerSqlWithProperty(sql, "type", "TYPE", bean.getType());
		return sql.toString();
	}

}
