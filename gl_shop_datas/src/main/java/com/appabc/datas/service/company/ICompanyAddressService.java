/**
 *
 */
package com.appabc.datas.service.company;

import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.common.base.service.IBaseService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description : 公司卸货地址SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 上午11:25:08
 */
public interface ICompanyAddressService extends IBaseService<TCompanyAddress>{

	/**
	 * 设置为默认卸货地址
	 * @param id
	 */
	public void setDefault(Serializable id);

	/**
	 * 查询列表，带图片
	 * @param entity
	 * @return
	 */
	public List<TCompanyAddress> queryForListHaveImgs(TCompanyAddress entity);

}
