/**
 *
 */
package com.appabc.datas.dao.order.impl;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.order.IOrderProductInfoDao;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description : 交易中的商品信息DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 下午4:24:13
 */
@Repository
public class OrderProductInfoDaoImpl extends BaseJdbcDao<TOrderProductInfo> implements IOrderProductInfoDao {

	private static final String INSERTSQL = " insert into T_ORDER_PRODUCT_INFO (FID, SID, PID, PNAME, PTYPE, PSIZE, PCOLOR, PADDRESS, UNIT, REMARK, PCODE) values (:fid, :sid, :pid, :pname, :ptype, :psize, :pcolor, :paddress, :unit, :premark,:pcode) ";
	private static final String UPDATESQL = " update T_ORDER_PRODUCT_INFO set FID = :fid, SID = :sid, PID = :pid, PNAME = :pname, PTYPE = :ptype, PSIZE = :psize, PCOLOR = :pcolor, PADDRESS = :paddress, UNIT = :unit, REMARK = :premark,PCODE=:pcode where ID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_ORDER_PRODUCT_INFO WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_ORDER_PRODUCT_INFO WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_ORDER_PRODUCT_INFO WHERE 1=1 ";

	public void save(TOrderProductInfo entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TOrderProductInfo entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TOrderProductInfo entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TOrderProductInfo entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TOrderProductInfo query(TOrderProductInfo entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public TOrderProductInfo query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TOrderProductInfo> queryForList(TOrderProductInfo entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	private String dynamicJoinSqlWithEntity(TOrderProductInfo bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "fid", "FID", bean.getFid());
		this.addNameParamerSqlWithProperty(sql, "sid", "SID", bean.getSid());
		this.addNameParamerSqlWithProperty(sql, "pid", "PID", bean.getPid());
		this.addNameParamerSqlWithProperty(sql, "pname", "PNAME", bean.getPname());
		this.addNameParamerSqlWithProperty(sql, "ptype", "PTYPE", bean.getPtype());
		this.addNameParamerSqlWithProperty(sql, "psize", "PSIZE", bean.getPsize());
		this.addNameParamerSqlWithProperty(sql, "pcolor", "PCOLOR", bean.getPcolor());
		this.addNameParamerSqlWithProperty(sql, "paddress", "PADDRESS", bean.getPaddress());
		this.addNameParamerSqlWithProperty(sql, "unit", "UNIT", bean.getUnit());
		this.addNameParamerSqlWithProperty(sql, "premark", "REMARK", bean.getPremark());
		this.addNameParamerSqlWithProperty(sql, "pcode", "PCODE", bean.getPcode());
		return sql.toString();
	}

	public List<TOrderProductInfo> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TOrderProductInfo> queryListForPagination(
			QueryContext<TOrderProductInfo> qContext) {
		return super.queryListForPagination(dynamicJoinSqlWithEntity(qContext.getBeanParameter(),  new StringBuilder(BASE_SQL)), qContext);
	}

	public TOrderProductInfo mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		TOrderProductInfo t = new TOrderProductInfo();

		t.setId(rs.getString("ID"));
		t.setFid(rs.getString("FID"));
		t.setPaddress(rs.getString("PADDRESS"));
		t.setPcolor(rs.getString("PCOLOR"));
		t.setPid(rs.getString("PID"));
		t.setPname(rs.getString("PNAME"));
		t.setPsize(rs.getString("PSIZE"));
		t.setPtype(rs.getString("PTYPE"));
		t.setPremark(rs.getString("REMARK"));
		t.setSid(rs.getString("SID"));
		t.setUnit(UnitEnum.enumOf(rs.getString("UNIT")));
		t.setPcode(rs.getString("PCODE"));

		return t;
	}

}
