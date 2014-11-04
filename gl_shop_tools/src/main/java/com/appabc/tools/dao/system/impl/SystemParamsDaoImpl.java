/**
 *
 */
package com.appabc.tools.dao.system.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TSystemParams;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.system.ISystemParamsDao;

/**
 * @Description : 系统参数DAO实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月3日 上午10:01:06
 */
@Repository
public class SystemParamsDaoImpl extends BaseJdbcDao<TSystemParams> implements ISystemParamsDao {
	
	private static final String INSERTSQL = " insert into T_SYSTEM_PARAMS (PNAME, PVALUE, PTYPE, DEFAULTVALUE, UPDATER, UPDATETIME, DESCRIPTION) values (:pname, :pvalue, :ptype, :defaultvalue, :updater, :updatetime, :description) ";
	private static final String UPDATESQL = " update T_SYSTEM_PARAMS set PNAME = :pname, PVALUE = :pvalue, PTYPE = :ptype, DEFAULTVALUE = :defaultvalue, UPDATER = :updater,  UPDATETIME = :updatetime, DESC = :desc where SID = :id ";
	private static final String DELETESQLBYID = " DELETE FROM T_SYSTEM_PARAMS WHERE SID = :id ";
	private static final String SELECTSQLBYID = " SELECT * FROM T_SYSTEM_PARAMS WHERE SID = :id ";
	
	private static final String BASE_SQL = " SELECT * FROM T_SYSTEM_PARAMS WHERE 1=1 "; 

	public void save(TSystemParams entity) {
		super.save(INSERTSQL, entity);
	}

	public KeyHolder saveAutoGenerateKey(TSystemParams entity) {
		return null;
	}

	public void update(TSystemParams entity) {
		super.update(UPDATESQL, entity);
	}

	public void delete(TSystemParams entity) {
	}

	public void delete(Serializable id) {
		super.delete(DELETESQLBYID, id);
	}

	public TSystemParams query(TSystemParams entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		if(entity.getPname() != null && !entity.getPname().isEmpty()){
			sql.append(" AND PNAME=:pname ");
		}
		
		List<TSystemParams> list = super.queryForList(sql.toString(), entity);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	public TSystemParams query(Serializable id) {
		return super.query(SELECTSQLBYID, id);
	}

	public List<TSystemParams> queryForList(TSystemParams entity) {
		StringBuffer sql = new StringBuffer(BASE_SQL);
		
		if(entity.getPname() != null && !entity.getPname().isEmpty()){
			sql.append(" AND PNAME:pname ");
		}
		
		return super.queryForList(sql.toString(), entity);
	}

	public List<TSystemParams> queryForList(Map<String, ?> args) {
		return null;
	}

	public QueryContext<TSystemParams> queryListForPagination(
			QueryContext<TSystemParams> qContext) {
		return null;
	}

	public TSystemParams mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TSystemParams t = new TSystemParams();
		
		t.setId(rs.getString("SID"));
		t.setDefaultvalue(rs.getString("DEFAULTVALUE"));
		t.setPname(rs.getString("PNAME"));
		t.setPtype(rs.getString("PTYPE"));
		t.setPvalue(rs.getString("PVALUE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setUpdatetime(rs.getTimestamp("UPDATETIME"));
		t.setDescription(rs.getString("DESCRIPTION"));
		
		return t;
	}

}
