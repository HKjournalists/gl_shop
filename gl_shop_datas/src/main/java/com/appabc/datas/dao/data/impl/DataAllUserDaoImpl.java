/**
 *
 */
package com.appabc.datas.dao.data.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TDataAllUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.datas.dao.data.IDataAllUserDao;

/**
 * @Description : 用户整体数据统计DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月14日 下午5:10:38
 */
@Repository
public class DataAllUserDaoImpl extends BaseJdbcDao<TDataAllUser> implements IDataAllUserDao {

	private static final String INSERTSQL = "insert into T_DATA_ALL_USER (ALL_USER_NUM, AUTH_USER_NUM, BAIL_USER_NUM, SELL_GOODS_NUM, BUY_GOODS_NUM, CONTRACT_ING_NUM, CONTRACT_END_NUM, LOGIN_USER_NUM, CREATE_DATE) values (:allUserNum, :authUserNum, :bailUserNum, :sellGoodsNum, :buyGoodsNum, :contractIngNum, :contractEndNum, :loginUserNum, :createDate)";
	private static final String UPDATESQL = "update T_DATA_ALL_USER set ALL_USER_NUM = :allUserNum, AUTH_USER_NUM = :authUserNum, BAIL_USER_NUM = :bailUserNum, SELL_GOODS_NUM = :sellGoodsNum, BUY_GOODS_NUM = :buyGoodsNum, CONTRACT_ING_NUM = :contractIngNum, CONTRACT_END_NUM = :contractEndNum, LOGIN_USER_NUM = :loginUserNum, CREATE_DATE = :createDate where ID = :id";
	private static final String DELETESQLBYID = " DELETE FROM T_DATA_ALL_USER WHERE ID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_DATA_ALL_USER WHERE ID = :id ";

	private static final String BASE_SQL = " SELECT * FROM T_DATA_ALL_USER WHERE 1=1 ";

	public void save(TDataAllUser entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TDataAllUser entity) {
		return super.saveAutoGenerateKey(INSERTSQL, entity);
	}

	public void update(TDataAllUser entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TDataAllUser entity) {
		super.delete(DELETESQLBYID, entity);
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TDataAllUser query(TDataAllUser entity) {
		return super.query(dynamicJoinSqlWithEntity(entity,new StringBuilder(BASE_SQL)), entity);
	}

	public TDataAllUser query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TDataAllUser> queryForList(TDataAllUser entity) {
		return super.queryForList(dynamicJoinSqlWithEntity(entity,  new StringBuilder(BASE_SQL)), entity);
	}

	public List<TDataAllUser> queryForList(Map<String, ?> args) {
		return super.queryForList(BASE_SQL, args);
	}

	public QueryContext<TDataAllUser> queryListForPagination(
			QueryContext<TDataAllUser> qContext) {
		
		StringBuilder querySql = new StringBuilder(BASE_SQL);
		
		if(qContext.getParameter("startTime") != null && qContext.getParameter("endTime") != null){
			querySql.append(" AND CREATE_DATE >= :startTime AND CREATE_DATE <= :endTime");
		}
		
		querySql.append(" ORDER BY CREATE_DATE DESC");
		return super.queryListForPagination(querySql.toString(), qContext);
	}

	public TDataAllUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		TDataAllUser t = new TDataAllUser();

		t.setId(rs.getString("ID"));
		t.setAllUserNum(rs.getInt("ALL_USER_NUM"));
		t.setAuthUserNum(rs.getInt("AUTH_USER_NUM"));
		t.setBailUserNum(rs.getInt("BAIL_USER_NUM"));
		t.setBuyGoodsNum(rs.getInt("BUY_GOODS_NUM"));
		t.setContractEndNum(rs.getInt("CONTRACT_END_NUM"));
		t.setContractIngNum(rs.getInt("CONTRACT_ING_NUM"));
		t.setCreateDate(rs.getDate("CREATE_DATE"));
		t.setLoginUserNum(rs.getInt("LOGIN_USER_NUM"));
		t.setSellGoodsNum(rs.getInt("SELL_GOODS_NUM"));

		return t;
	}

	private String dynamicJoinSqlWithEntity(TDataAllUser bean,StringBuilder sql){
		if(bean==null||sql==null||sql.length()<=0){
			return null;
		}
		this.addNameParamerSqlWithProperty(sql, "id", "ID", bean.getId());
		this.addNameParamerSqlWithProperty(sql, "allUserNum", "ALL_USER_NUM", bean.getAllUserNum());
		this.addNameParamerSqlWithProperty(sql, "authUserNum", "AUTH_USER_NUM", bean.getAuthUserNum());
		this.addNameParamerSqlWithProperty(sql, "bailUserNum", "BAIL_USER_NUM", bean.getBailUserNum());
		this.addNameParamerSqlWithProperty(sql, "buyGoodsNum", "BUY_GOODS_NUM", bean.getBuyGoodsNum());
		this.addNameParamerSqlWithProperty(sql, "contractEndNum", "CONTRACT_END_NUM", bean.getContractEndNum());
		this.addNameParamerSqlWithProperty(sql, "contractIngNum", "CONTRACT_ING_NUM", bean.getContractIngNum());
		this.addNameParamerSqlWithProperty(sql, "createDate", "CREATE_DATE", bean.getCreateDate());
		this.addNameParamerSqlWithProperty(sql, "loginUserNum", "LOGIN_USER_NUM", bean.getLoginUserNum());
		this.addNameParamerSqlWithProperty(sql, "sellGoodsNum", "SELL_GOODS_NUM", bean.getSellGoodsNum());
		return sql.toString();
	}

}
