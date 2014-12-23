/**
 *
 */
package com.appabc.tools.dao.user.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo.CompanyAuthStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.BaseJdbcDao;
import com.appabc.tools.dao.user.IToolCompanyDao;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 杨跃红
 * @version : 1.0 Create Date : 2014年12月12日 下午5:01:33
 */
@Repository
public class ToolCompanyDaoImpl extends BaseJdbcDao<TCompanyInfo> implements
		IToolCompanyDao {

	/**
	 * 根据ID获取企业信息
	 */
	private static final String SQL_SELECT_COMPANY_INFO_BYID = " SELECT * FROM T_COMPANY_INFO WHERE ID = :id ";
	/**
	 * 根据企业ID获取船舶信息
	 */
	private static final String SQL_SELECT_SHIPPING_INFO_BYCID = " SELECT cs.* FROM T_COMPANY_INFO ci, T_AUTH_RECORD ar, T_COMPANY_SHIPPING cs WHERE ci.id = ar.CID AND ar.TYPE = :artype AND ar.AUTHSTATUS = :authstatus AND ar.AUTHID = cs.AUTHID AND ci.CTYPE = :ctype AND ci.ID = :cid  ORDER BY cs.UPDATEDATE desc limit 0,1 ";

	@Override
	public TCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TCompanyInfo t = new TCompanyInfo();

		t.setId(rs.getString("ID"));
		t.setCname(rs.getString("CNAME"));
		t.setAuthstatus(CompanyAuthStatus.enumOf(rs.getString("AUTHSTATUS")));
		t.setContact(rs.getString("CONTACT"));
		t.setCphone(rs.getString("CPHONE"));
		t.setCreatedate(rs.getTimestamp("CREATEDATE"));
		t.setCtype(CompanyType.enumOf(rs.getString("CTYPE")));
		t.setMark(rs.getString("MARK"));
		t.setStatus(rs.getString("STATUS"));
		t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));
		t.setUpdater(rs.getString("UPDATER"));
		t.setTel(rs.getString("TEL"));
		t.setBailstatus(CompanyBailStatus.enumOf(rs.getString("BAILSTATUS")));
		return t;
	}

	@Override
	public void save(TCompanyInfo entity) {
	}

	@Override
	public KeyHolder saveAutoGenerateKey(TCompanyInfo entity) {
		return null;
	}

	@Override
	public void update(TCompanyInfo entity) {
	}

	@Override
	public void delete(TCompanyInfo entity) {
	}

	@Override
	public void delete(Serializable id) {
	}

	@Override
	public TCompanyInfo query(TCompanyInfo entity) {
		return null;
	}

	@Override
	public TCompanyInfo query(Serializable id) {
		return super.query(SQL_SELECT_COMPANY_INFO_BYID, id);
	}

	@Override
	public List<TCompanyInfo> queryForList(TCompanyInfo entity) {
		return null;
	}

	@Override
	public List<TCompanyInfo> queryForList(Map<String, ?> args) {
		return null;
	}

	@Override
	public QueryContext<TCompanyInfo> queryListForPagination(
			QueryContext<TCompanyInfo> qContext) {
		return null;
	}

	public TCompanyShipping queryShippingByCid(String cid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("ctype", CompanyType.COMPANY_TYPE_SHIP.getVal());
		map.put("authstatus", AuthRecordStatus.AUTH_STATUS_CHECK_YES.getVal());
		map.put("artype",AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal());
//		map.put("ci.ID", cid);
//		map.put("ci.CTYPE", CompanyType.COMPANY_TYPE_SHIP);
//		map.put("ar.AUTHSTATUS", AuthRecordStatus.AUTH_STATUS_CHECK_YES);
//		map.put("ar.TYPE",AuthRecordType.AUTH_RECORD_TYPE_COMPANY);

		List<TCompanyShipping> result = getNamedParameterJdbcTemplate().query(
				SQL_SELECT_SHIPPING_INFO_BYCID, map,
				new RowMapper<TCompanyShipping>() {
					public TCompanyShipping mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TCompanyShipping t = new TCompanyShipping();

						t.setId(rs.getString("ID"));
						t.setAuthid(rs.getInt("authid"));
						t.setPregistry(rs.getString("pregistry"));
						t.setSbusinesser(rs.getString("sbusinesser"));
						t.setScreatetime(rs.getString("screatetime"));
						t.setSdeep(rs.getFloat("sdeep"));
						t.setSlength(rs.getFloat("slength"));
						t.setSload(rs.getFloat("sload"));
						t.setSmateriall(rs.getFloat("smateriall"));
						t.setSname(rs.getString("sname"));
						t.setSno(rs.getString("sno"));
						t.setSorg(rs.getString("sorg"));
						t.setSover(rs.getFloat("sover"));
						t.setSowner(rs.getString("sowner"));
						t.setStotal(rs.getFloat("stotal"));
						t.setStype(rs.getString("stype"));
						t.setSwidth(rs.getFloat("swidth"));
						t.setUpdatedate(rs.getTimestamp("UPDATEDATE"));

						return t;
					}
				});
		if (CollectionUtils.isNotEmpty(result)) {
			return result.get(0);
		} else {
			return null;
		}
	}

}
