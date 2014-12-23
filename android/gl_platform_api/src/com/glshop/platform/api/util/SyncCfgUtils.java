package com.glshop.platform.api.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.SysCfgCode;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.api.syscfg.data.model.SyscfgInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 系统参数配置工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-11-10 上午11:21:49
 */
public class SyncCfgUtils {

	/**
	 * 解析货物列表
	 * @param productItem
	 * @param productSpecItem
	 * @param sysCfgTimestamp
	 * @return
	 */
	public static List<ProductCfgInfoModel> parseSysProductData(ResultItem productItem, ResultItem productSpecItem, Map<String, String> sysCfgTimestamp) {
		List<SyscfgInfoModel> productTypeList = new ArrayList<SyscfgInfoModel>();
		if (productItem != null) {
			List<ResultItem> productItemList = (ArrayList<ResultItem>) productItem.get("data");
			if (productItemList != null && productItemList.size() > 0) {
				if (sysCfgTimestamp != null) {
					sysCfgTimestamp.put(SysCfgCode.TYPE_PRODUCT_CATEGORY, productItem.getString("timeStamp"));
				}
				for (ResultItem ri : productItemList) {
					SyscfgInfoModel info = new SyscfgInfoModel();
					info.code = ri.getString("val");
					info.pCode = ri.getString("pcode");
					info.value = ri.getString("name");
					info.orderNo = ri.getString("orderno");
					productTypeList.add(info);
				}
			}
		}

		List<ProductCfgInfoModel> productSpecList = new ArrayList<ProductCfgInfoModel>();
		if (productSpecItem != null) {
			List<ResultItem> productSpecItemList = (ArrayList<ResultItem>) productSpecItem.get("data");
			if (productSpecItemList != null && productSpecItemList.size() > 0) {
				if (sysCfgTimestamp != null) {
					sysCfgTimestamp.put(SysCfgCode.TYPE_PRODUCT_SUB_CATEGORY, productSpecItem.getString("timeStamp"));
				}
				for (ResultItem ri : productSpecItemList) {
					ProductCfgInfoModel info = new ProductCfgInfoModel();

					// 解析类型
					info.mTypeCode = ri.getString("pcode");
					info.mTypeName = getProductTypeName(info.mTypeCode, productTypeList);

					if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode) || DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(info.mTypeCode)) {
						if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
							info.mCategoryCode = ri.getString("ptype");
							info.mCategoryName = getProductTypeName(info.mCategoryCode, productTypeList);
							info.mCategoryOrder = getProductTypeOrderNo(info.mCategoryCode, productTypeList);
							info.mSubCategoryId = ri.getString("id");
							info.mSubCategoryCode = ri.getString("pname");
							info.mSubCategoryName = ri.getString("pname");
							info.mSubCategoryOrder = ri.getString("orderno");
						} else {
							info.mCategoryId = ri.getString("id");
							info.mCategoryCode = ri.getString("pname");
							info.mCategoryName = ri.getString("pname");
							info.mCategoryOrder = ri.getString("orderno");
						}

						// 解析规格大小
						ResultItem sizeItem = (ResultItem) ri.get("psize");
						if (sizeItem != null) {
							info.mMaxSize = sizeItem.getFloat("maxv");
							info.mMinSize = sizeItem.getFloat("minv");
						}

						// 解析属性列表
						List<ResultItem> propItemList = (ArrayList<ResultItem>) ri.get("propertyList");
						List<ProductPropInfoModel> propList = new ArrayList<ProductPropInfoModel>();
						if (propItemList != null && propItemList.size() > 0) {
							for (ResultItem prop : propItemList) {
								ProductPropInfoModel propInfo = new ProductPropInfoModel();
								propInfo.mPropId = prop.getString("id");
								propInfo.mPropCode = prop.getString("code");
								propInfo.mTypeCode = info.mTypeCode;
								propInfo.mCategoryCode = info.mCategoryCode;
								propInfo.mSubCategoryCode = info.mSubCategoryCode;
								propInfo.mDefaultSize = prop.getFloat("content");
								propInfo.mMaxSize = prop.getFloat("maxv");
								propInfo.mMinSize = prop.getFloat("minv");
								propList.add(propInfo);
							}
							info.mPropList = propList;
						}

						productSpecList.add(info);
					}
				}
			}
		}
		return productSpecList;
	}

	/**
	 * 解析港口列表
	 * @param areaItem
	 * @param sysCfgTimestamp
	 * @return
	 */
	public static List<AreaInfoModel> parseSysAreaData(ResultItem areaItem, Map<String, String> sysCfgTimestamp) {
		List<AreaInfoModel> areaList = new ArrayList<AreaInfoModel>();
		if (areaItem != null) {
			List<ResultItem> areaItemList = (ArrayList<ResultItem>) areaItem.get("data");
			if (BeanUtils.isNotEmpty(areaItemList)) {
				if (sysCfgTimestamp != null) {
					sysCfgTimestamp.put(SysCfgCode.TYPE_PORT_SECTION, areaItem.getString("timeStamp"));
				}
				for (ResultItem ri : areaItemList) {
					AreaInfoModel info = new AreaInfoModel();
					info.type = SysCfgCode.TYPE_PORT_SECTION;
					info.code = ri.getString("val");
					info.name = ri.getString("name");
					info.pCode = ri.getString("pcode");
					areaList.add(info);
				}
				if (BeanUtils.isNotEmpty(areaList)) {
					Collections.sort(areaList, new Comparator<AreaInfoModel>() {

						@Override
						public int compare(AreaInfoModel info1, AreaInfoModel info2) {
							return info1.code.compareTo(info2.code);
						}

					});
				}
			}
		}
		return areaList;
	}

	/**
	 * 解析银行列表
	 * @param areaItem
	 * @param sysCfgTimestamp
	 * @return
	 */
	public static List<BankInfoModel> parseSysBankData(ResultItem bankItem, Map<String, String> sysCfgTimestamp) {
		List<BankInfoModel> bankList = new ArrayList<BankInfoModel>();
		if (bankItem != null) {
			List<ResultItem> bankItemList = (ArrayList<ResultItem>) bankItem.get("data");
			if (BeanUtils.isNotEmpty(bankItemList)) {
				if (sysCfgTimestamp != null) {
					sysCfgTimestamp.put(SysCfgCode.TYPE_BANK, bankItem.getString("timeStamp"));
				}
				for (ResultItem ri : bankItemList) {
					BankInfoModel info = new BankInfoModel();
					info.type = SysCfgCode.TYPE_BANK;
					info.code = ri.getString("val");
					info.name = ri.getString("name");
					info.pCode = ri.getString("pcode");
					bankList.add(info);
				}
				if (BeanUtils.isNotEmpty(bankList)) {
					Collections.sort(bankList, new Comparator<BankInfoModel>() {

						@Override
						public int compare(BankInfoModel info1, BankInfoModel info2) {
							return info1.code.compareTo(info2.code);
						}

					});
				}
			}
		}
		return bankList;
	}

	/**
	 * 解析系统参数列表
	 * @param sysParamItem
	 * @param sysCfgTimestamp
	 * @return
	 */
	public static List<SysParamInfoModel> parseSysParamData(ResultItem sysParamItem, Map<String, String> sysCfgTimestamp) {
		List<SysParamInfoModel> sysParamList = new ArrayList<SysParamInfoModel>();
		if (sysParamItem != null) {
			List<ResultItem> sysParamItemList = (ArrayList<ResultItem>) sysParamItem.get("data");
			if (sysParamItemList != null && sysParamItemList.size() > 0) {
				sysCfgTimestamp.put(SysCfgCode.TYPE_SYSPARAM, sysParamItem.getString("timeStamp"));
				for (ResultItem ri : sysParamItemList) {
					SysParamInfoModel info = new SysParamInfoModel();
					info.type = SysCfgCode.TYPE_SYSPARAM;
					info.name = ri.getString("pname");
					info.code = ri.getString("pvalue");
					info.pCode = ri.getString("pcode");
					sysParamList.add(info);
				}
			}
		}
		return sysParamList;
	}

	private static String getProductTypeName(String typeCode, List<SyscfgInfoModel> list) {
		if (list != null && typeCode != null) {
			for (SyscfgInfoModel info : list) {
				if (typeCode.equals(info.code)) {
					return info.value;
				}
			}
		}
		return null;
	}

	private static String getProductTypeOrderNo(String typeCode, List<SyscfgInfoModel> list) {
		if (list != null && typeCode != null) {
			for (SyscfgInfoModel info : list) {
				if (typeCode.equals(info.code)) {
					return info.orderNo;
				}
			}
		}
		return null;
	}

}
