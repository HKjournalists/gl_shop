package com.glshop.platform.api.syscfg.data.model;

import java.util.List;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统同步信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class SyncInfoModel extends ResultItem {

	/**
	 * 货物信息列表
	 */
	public List<ProductCfgInfoModel> mProductList;

	/**
	 * 港口信息列表
	 */
	public List<AreaInfoModel> mPortList;

	/**
	 * 省市区信息列表
	 */
	public List<AreaInfoModel> mAreaList;

	/**
	 * 支持的省份列表
	 */
	public List<AreaInfoModel> mSupportProvinceList;

	/**
	 * 银行信息列表
	 */
	public List<BankInfoModel> mBankList;

	/**
	 * 系统参数列表
	 */
	public List<SysParamInfoModel> mSysParamList;

}
