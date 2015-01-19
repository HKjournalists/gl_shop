package com.glshop.net.logic.syscfg;

import java.util.List;

import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 系统参数业务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public interface ISysCfgLogic extends ILogic {

	/**
	 * 获取货物信息列表
	 */
	List<ProductCfgInfoModel> getLocalProductList();

	/**
	 * 获取已分类的货物信息列表
	 */
	List<ProductCfgInfoModel> getLocalProductCategoryList();

	/**
	 * 获取港口信息列表
	 */
	List<AreaInfoModel> getLocalPortList();

	/**
	 * 获取交易地域信息列表
	 */
	List<AreaInfoModel> getLocalAreaList();

	/**
	 * 获取银行信息列表
	 */
	List<BankInfoModel> getLocalBankList();

	/**
	 * 获取系统参数信息列表
	 */
	List<SysParamInfoModel> getLocalSysParam();

	/**
	 * 同步默认的系统参数
	 */
	void syncSysCfgInfo();

	/**
	 * 同步typeList中的系统参数
	 * @param typeList
	 */
	void syncSysCfgInfo(List<String> typeList);

	/**
	 * 导入本地地域信息
	 */
	void importAreaCfgInfo();

	/**
	 * 获取父地域信息
	 * @param areaCode
	 */
	List<AreaInfoModel> getParentAreaInfo(String areaCode);

	/**
	 * 获取子地域信息
	 * @param areaCode
	 */
	List<AreaInfoModel> getChildAreaInfo(String areaCode);

}
