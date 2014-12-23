/**
 *
 */
package com.appabc.datas.service.company;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 认证记录SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午6:00:04
 */
public interface IAuthRecordService extends IBaseService<TAuthRecord> {
	
	/**
	 * 获取认证状态的记录数
	 * @param cid
	 * @param austatus
	 * @return
	 */
	public int getCountByCidAndAuthstauts(String cid, AuthRecordStatus austatus);
	
}
