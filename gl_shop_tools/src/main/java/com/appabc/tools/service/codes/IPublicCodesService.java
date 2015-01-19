/**
 *
 */
package com.appabc.tools.service.codes;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.service.IBaseService;

import java.util.List;

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
	 * @param ishidden 是否为隐藏属性; null：全部，0：显示，1：隐藏
	 * @return
	 */
	public List<TPublicCodes> queryListByCode(String code, Integer ishidden);

}
