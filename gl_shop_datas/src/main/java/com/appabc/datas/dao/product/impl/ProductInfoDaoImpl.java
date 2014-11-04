/**
 *
 */
package com.appabc.datas.dao.product.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 商品基本信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午2:39:01
 */
@Repository
public class ProductInfoDaoImpl extends BaseJdbcDao<TProductInfo> implements IProductInfoDao{
	
	@Autowired
	private PrimaryKeyGenerator pk;
	
	private static final String INSERTSQL = " INSERT INTO T_PRODUCT_INFO (PID, PNAME, PCODE, PTYPE, PSIZE, PCOLOR, PADDRESS, UNIT, REMARK) values (:id, :pname, :pcode, :ptype, :psize, :pcolor, :paddress, :unit, :remark) ";
	private static final String UPDATESQL = " update T_PRODUCT_INFO set PNAME = :pname, PCODE = :pcode, PTYPE = :ptype, PSIZE = :psize, PCOLOR = :pcolor, PADDRESS = :paddress, UNIT = :unit, REMARK = :remark WHERE PID = :id";
	private static final String DELETESQLBYID = " DELETE FROM T_PRODUCT_INFO where PID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_PRODUCT_INFO WHERE PID = :id ";
	private static final String SELECTSQLBYPCODE = " SELECT * FROM T_PRODUCT_INFO WHERE PCODE = :pcode ";
	
	private static final String BASE_SQL = " SELECT * FROM T_PRODUCT_INFO WHERE 1=1 "; 

	public TProductInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TProductInfo pi = new TProductInfo();
		
		pi.setId(rs.getString("PID"));
		pi.setPaddress(rs.getString("PADDRESS"));
		pi.setPcode(rs.getString("PCODE"));
		pi.setPcolor(rs.getString("PCOLOR"));
		pi.setPname(rs.getString("PNAME"));
		pi.setPsize(rs.getString("PSIZE"));
		pi.setPtype(rs.getString("PTYPE"));
		pi.setRemark(rs.getString("REMARK"));
		pi.setUnit(rs.getString("UNIT"));
		
		return pi;
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}
	
	/* (non-Javadoc)根据商品类型code查询商品
	 * @see com.appabc.datas.dao.product.IProductInfoDao#queryByPcode(java.lang.String)
	 */
	public List<TProductInfo> queryByPcode(String pcode) {
		TProductInfo entity = new TProductInfo();
		entity.setPcode(pcode);
		
		return queryForList(SELECTSQLBYPCODE, entity);
	}

	public TProductInfo queryByid(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public void save(TProductInfo entity) {
		entity.setId(pk.generatorBusinessKeyByBid("PRODUCTID"));
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TProductInfo entity) {
		return null;
	}

	public void update(TProductInfo entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductInfo entity) {
	}

	public TProductInfo query(TProductInfo entity) {
		return null;
	}

	public TProductInfo query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TProductInfo> queryForList(TProductInfo entity) {
		
		StringBuffer sql = new StringBuffer(BASE_SQL);
		
		if(entity.getPcode() != null && !entity.getPcode().isEmpty()){
			sql.append(" AND PCODE = :pcode");
		}
		return super.queryForList(sql.toString(), entity);
	}

	public List<TProductInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TProductInfo> queryListForPagination(
			QueryContext<TProductInfo> qContext) {
		return null;
	}

}
