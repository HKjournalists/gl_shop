/**
 *
 */
package com.appabc.datas.service.codes;

import java.util.List;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 公共代码SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午2:39:28
 */
public interface IPublicCodesService extends IBaseService<TPublicCodes> {
	
	/**
	 * 根据CODE查询
	 * @param code
	 * @return
	 */
	public List<TPublicCodes> queryListByCode(String code);

}
