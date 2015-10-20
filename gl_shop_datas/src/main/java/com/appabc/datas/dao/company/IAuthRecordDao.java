/**
 *
 */
package com.appabc.datas.dao.company;

import java.util.List;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 认证记录DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午5:58:43
 */
public interface IAuthRecordDao extends IBaseDao<TAuthRecord> {
	
	/**
	 * 获取认证状态的记录数
	 * @param cid
	 * @param austatus
	 * @return
	 */
	public int getCountByCidAndAuthstauts(String cid, AuthRecordStatus austatus);
	
	/**
	 * 分页查询审核记录
	 * @param qContext
	 * @param authType
	 * @param authStatus
	 * @return
	 */
	public QueryContext<TAuthRecord> queryListForPaginationByTypeAndAuthstatus(QueryContext<TAuthRecord> qContext, AuthRecordType authType, AuthRecordStatus authStatus);

	/**
	 * 查询新认证的记录（未添加到任务表中[SYS_TASKS]的数据）
	 * @return
	 */
	public List<TAuthRecord> queryNewListForNotInTask();

	/**
	 * 用户身份认证，已审核列表
	 * @param qContext
	 * @return
	 */
	QueryContext<TAuthRecord> queryParentAuthRecordOfInsteadListForPagination(
			QueryContext<TAuthRecord> qContext);

	/**
	 * 企业认证日志
	 * @param cid
	 * @return
	 */
	List<TAuthRecord> queryAuthLogListByCid(String cid);

}
