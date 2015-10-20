/**
 *
 */
package com.appabc.datas.dao.company.impl;

import com.appabc.bean.enums.CompanyInfo.AddressStatus;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.company.ICompanyAddressDao;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 公司卸货地址Dao实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 上午11:24:38
 */
@Repository
public class CompanyAddressDaoImpl extends BaseJdbcDao<TCompanyAddress> implements ICompanyAddressDao {

	private static final String INSERTSQL = " insert into T_COMPANY_ADDRESS (CID, AREACODE, ADDRESS, LONGITUDE, LATITUDE, DEEP, STATUS, REALDEEP, SHIPPINGTON) values (:cid, :areacode, :address, :longitude, :latitude, :deep, :status, :realdeep, :shippington) ";
	private static final String UPDATESQL = " update T_COMPANY_ADDRESS set CID = :cid,  AREACODE = :areacode, ADDRESS = :address, LONGITUDE = :longitude, LATITUDE = :latitude, DEEP = :deep, STATUS = :status, REALDEEP= :realdeep, SHIPPINGTON = :shippington where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_COMPANY_ADDRESS WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_COMPANY_ADDRESS WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_COMPANY_ADDRESS WHERE 1=1 ";

	public void save(TCompanyAddress entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TCompanyAddress entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TCompanyAddress entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TCompanyAddress entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TCompanyAddress query(TCompanyAddress entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TCompanyAddress query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TCompanyAddress> queryForList(TCompanyAddress entity) {
		String sql = dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL));
		return super.queryForList(sql+" ORDER BY STATUS DESC ", entity);
	}

	public List<TCompanyAddress> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TCompanyAddress> queryListForPagination(
			QueryContext<TCompanyAddress> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TCompanyAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyAddress t = new TCompanyAddress();

		t.setId(rs.getString("ID"));
		t.setCid(rs.getString("CID"));
		t.setAddress(rs.getString("ADDRESS"));
		t.setAreacode(rs.getString("AREACODE"));
		t.setDeep(rs.getFloat("DEEP"));
		t.setLatitude(rs.getString("LATITUDE"));
		t.setLongitude(rs.getString("LONGITUDE"));
		t.setStatus(AddressStatus.enumOf(rs.getInt("STATUS")));
		t.setRealdeep(rs.getFloat("REALDEEP"));
		t.setShippington(rs.getFloat("SHIPPINGTON"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TCompanyAddress bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0) {
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "cid", "CID", bean.getCid());
		this.addNameParamerSqlWithProperty(sql, "address", "ADDRESS", bean.getAddress());
		this.addNameParamerSqlWithProperty(sql, "areacode", "AREACODE", bean.getAreacode());
		this.addNameParamerSqlWithProperty(sql, "deep", "DEEP", bean.getDeep());
		this.addNameParamerSqlWithProperty(sql, "latitude", "LATITUDE", bean.getLatitude());
		this.addNameParamerSqlWithProperty(sql, "longitude", "LONGITUDE", bean.getLongitude());
		this.addNameParamerSqlWithProperty(sql, "status", "STATUS", bean.getStatus());
		this.addNameParamerSqlWithProperty(sql, "realdeep", "REALDEEP", bean.getRealdeep());
		this.addNameParamerSqlWithProperty(sql, "shippington", "SHIPPINGTON", bean.getRealdeep());
		return sql.toString();
	}

}
