/**
 *
 */
package com.appabc.datas.dao.company;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.pvo.TAuthRecord;
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

}
