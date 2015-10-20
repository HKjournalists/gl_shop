/**
 *
 */
package com.appabc.datas.dao.product.impl;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.tools.utils.PrimaryKeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	private static final String INSERTSQL = " INSERT INTO T_PRODUCT_INFO (PID, PNAME, PCODE, PTYPE, PSIZE, PCOLOR, PADDRESS, UNIT, REMARK,ORDERNO) values (:id, :pname, :pcode, :ptype, :psize, :pcolor, :paddress, :unit, :remark,:orderno) ";
	private static final String UPDATESQL = " update T_PRODUCT_INFO set PNAME = :pname, PCODE = :pcode, PTYPE = :ptype, PSIZE = :psize, PCOLOR = :pcolor, PADDRESS = :paddress, UNIT = :unit, REMARK = :remark,ORDERNO=:orderno WHERE PID = :id";
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
		pi.setUnit(UnitEnum.enumOf(rs.getString("UNIT")));
		pi.setOrderno(rs.getInt("ORDERNO"));

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
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TProductInfo entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TProductInfo entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public TProductInfo query(TProductInfo entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TProductInfo query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TProductInfo> queryForList(TProductInfo entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TProductInfo> queryForList(Map<String, ?> args) {
		StringBuffer sql = new StringBuffer(BASE_SQL);

		return queryForList(sql.toString(), args);
	}

	public QueryContext<TProductInfo> queryListForPagination(
			QueryContext<TProductInfo> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	private String dynamicJoinSqlWithEntity(TProductInfo bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "pid", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "paddress", "PADDRESS", bean.getPaddress());
		this.addNameParamerSqlWithProperty(sql, "pcode", "PCODE", bean.getPcode());
		this.addNameParamerSqlWithProperty(sql, "pcolor", "PCOLOR", bean.getPcolor());
		this.addNameParamerSqlWithProperty(sql, "pname", "PNAME", bean.getPname());
		this.addNameParamerSqlWithProperty(sql, "psize", "PSIZE", bean.getPsize());
		this.addNameParamerSqlWithProperty(sql, "ptype", "PTYPE", bean.getPtype());
		this.addNameParamerSqlWithProperty(sql, "remark", "REMARK", bean.getRemark());
		this.addNameParamerSqlWithProperty(sql, "unit", "UNIT", bean.getUnit());
		this.addNameParamerSqlWithProperty(sql, "orderno", "ORDERNO", bean.getOrderno());
		return sql.toString();
	}
}
