package com.glshop.net.logic.syscfg.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

import com.glshop.net.R;
import com.glshop.net.logic.db.dao.syscfg.ISyscfgDao;
import com.glshop.net.logic.db.dao.syscfg.SyscfgDao;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.SysCfgCode;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.SyncInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 系统参数配置管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-9 上午11:54:11
 */
public class SysCfgMgr {

	private Context mContext;

	private static SysCfgMgr mInstance;

	/** 货物信息 */
	private List<ProductCfgInfoModel> mProductList;

	/** 已分类的货物信息 */
	private List<ProductCfgInfoModel> mProductCategoryList;

	/** 港口信息 */
	private List<AreaInfoModel> mPortList;

	/** 交易地域(省市区)信息 */
	private List<AreaInfoModel> mAreaList = new ArrayList<AreaInfoModel>();

	/** 银行列表参数信息 */
	private List<BankInfoModel> mBankList;

	/** 系统参数列表信息 */
	private List<SysParamInfoModel> mSysParamList;

	/** 系统默认同步参数列表 */
	private List<String> mDefaultSyncTypeList;

	private ISyscfgDao mSysCfgDao;

	private SysCfgMgr(Context context) {
		mContext = context;

		// 初始化默认同步参数列表
		mDefaultSyncTypeList = new ArrayList<String>();
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PRODUCT_CATEGORY);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PRODUCT_SUB_CATEGORY);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PORT_SECTION);
		//mDefaultSyncTypeList.add(SysCfgCode.TYPE_AREA);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_AREA_PROVINCE_CONTROL);
		//mDefaultSyncTypeList.add(SysCfgCode.TYPE_BANK);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_SYSPARAM);

		mSysCfgDao = SyscfgDao.getInstance(mContext);
	}

	public static synchronized SysCfgMgr getIntance(Context context) {
		if (mInstance == null) {
			mInstance = new SysCfgMgr(context);
		}
		return mInstance;
	}

	/**
	 * 加载本地货物类型列表
	 * @return
	 */
	public synchronized List<ProductCfgInfoModel> loadProductList() {
		List<ProductCfgInfoModel> productList = null;
		if (BeanUtils.isNotEmpty(mProductList)) {
			productList = mProductList;
		} else {
			List<ProductCfgInfoModel> list = mSysCfgDao.queryProductInfo(mContext);
			if (BeanUtils.isEmpty(list)) {
				list = SysCfgLocalMgr.getIntance(mContext).loadProductList();
			}
			mProductList = list;
			productList = list;
		}
		return productList;
	}

	/**
	 * 获取已分类的货物信息列表
	 */
	public synchronized List<ProductCfgInfoModel> getProductCategoryList() {
		if (BeanUtils.isEmpty(mProductCategoryList)) {
			List<ProductCfgInfoModel> productList = SysCfgMgr.getIntance(mContext).loadProductList();
			if (BeanUtils.isNotEmpty(productList)) {
				mProductCategoryList = new ArrayList<ProductCfgInfoModel>();
				ProductCfgInfoModel topSandTypeInfo = new ProductCfgInfoModel();
				topSandTypeInfo.mTypeCode = DataConstants.SysCfgCode.TYPE_PRODUCT_SAND;
				topSandTypeInfo.mTypeName = mContext.getString(R.string.product_type_sand);
				topSandTypeInfo.childList = new ArrayList<ProductCfgInfoModel>();
				ProductCfgInfoModel topStoneTypeInfo = new ProductCfgInfoModel();
				topStoneTypeInfo.mTypeCode = DataConstants.SysCfgCode.TYPE_PRODUCT_STONE;
				topStoneTypeInfo.mTypeName = mContext.getString(R.string.product_type_stone);
				topStoneTypeInfo.childList = new ArrayList<ProductCfgInfoModel>();

				mProductCategoryList.add(topSandTypeInfo);
				mProductCategoryList.add(topStoneTypeInfo);

				for (ProductCfgInfoModel info : productList) {
					if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
						ProductCfgInfoModel topSandCategoryInfo = getProductCategoryInfo(topSandTypeInfo.childList, info.mCategoryCode);
						if (topSandCategoryInfo == null) {
							topSandCategoryInfo = new ProductCfgInfoModel();
							topSandCategoryInfo.mTypeCode = info.mTypeCode;
							topSandCategoryInfo.mTypeName = mContext.getString(R.string.product_type_sand);
							topSandCategoryInfo.mCategoryCode = info.mCategoryCode;
							topSandCategoryInfo.mCategoryName = info.mCategoryName;
							topSandCategoryInfo.mCategoryOrder = info.mCategoryOrder;
							topSandCategoryInfo.parent = topSandTypeInfo;
							topSandCategoryInfo.childList = new ArrayList<ProductCfgInfoModel>();
							topSandTypeInfo.childList.add(topSandCategoryInfo);
						}
						info.parent = topSandCategoryInfo;
						topSandCategoryInfo.childList.add(info);
					} else if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(info.mTypeCode)) {
						info.parent = topStoneTypeInfo;
						topStoneTypeInfo.childList.add(info);
					}
				}

				sortProductList(topStoneTypeInfo.childList, 1);
				sortProductList(topSandTypeInfo.childList, 1);
				for (ProductCfgInfoModel info : topSandTypeInfo.childList) {
					sortProductList(info.childList, 2);
				}

			}
		}
		return mProductCategoryList;
	}

	private void sortProductList(List<ProductCfgInfoModel> list, final int depth) {
		if (BeanUtils.isNotEmpty(list)) {
			Collections.sort(list, new Comparator<ProductCfgInfoModel>() {

				@Override
				public int compare(ProductCfgInfoModel info1, ProductCfgInfoModel info2) {
					if (depth == 1) {
						if (StringUtils.isNotEmpty(info1.mCategoryOrder) && StringUtils.isNotEmpty(info2.mCategoryOrder)) {
							if (StringUtils.isDigital(info1.mCategoryOrder) && StringUtils.isDigital(info2.mCategoryOrder)) {
								int lhs = Integer.parseInt(info1.mCategoryOrder);
								int rhs = Integer.parseInt(info2.mCategoryOrder);
								return lhs - rhs;
							} else {
								return info1.mCategoryOrder.compareTo(info2.mCategoryOrder);
							}
						} else {
							return 0;
						}
					} else if (depth == 2) {
						if (StringUtils.isNotEmpty(info1.mSubCategoryOrder) && StringUtils.isNotEmpty(info2.mSubCategoryOrder)) {
							if (StringUtils.isDigital(info1.mSubCategoryOrder) && StringUtils.isDigital(info2.mSubCategoryOrder)) {
								int lhs = Integer.parseInt(info1.mSubCategoryOrder);
								int rhs = Integer.parseInt(info2.mSubCategoryOrder);
								return lhs - rhs;
							} else {
								return info1.mSubCategoryOrder.compareTo(info2.mSubCategoryOrder);
							}
						} else {
							return 0;
						}
					}
					return 0;
				}
			});
		}
	}

	private ProductCfgInfoModel getProductCategoryInfo(List<ProductCfgInfoModel> list, String categoryType) {
		if (StringUtils.isNotEmpty(categoryType) && BeanUtils.isNotEmpty(list)) {
			for (ProductCfgInfoModel info : list) {
				if (categoryType.equals(info.mCategoryCode)) {
					return info;
				}
			}
		}
		return null;
	}

	/**
	 * 加载本地港口列表
	 * @return
	 */
	public synchronized List<AreaInfoModel> loadPortList() {
		List<AreaInfoModel> portList = null;
		if (BeanUtils.isNotEmpty(mPortList)) {
			portList = mPortList;
		} else {
			List<AreaInfoModel> list = mSysCfgDao.queryPortInfo(mContext);
			if (BeanUtils.isEmpty(list)) {
				list = SysCfgLocalMgr.getIntance(mContext).loadPortList();
			}
			if (BeanUtils.isNotEmpty(list)) {
//				Collections.sort(list, new Comparator<AreaInfoModel>() {
//
//					@Override
//					public int compare(AreaInfoModel info1, AreaInfoModel info2) {
//						return info1.code.compareTo(info2.code);
//					}
//
//				});
			}
			mPortList = list;
			portList = list;
		}
		return portList;
	}

	/**
	 * 加载本地交易地域列表
	 * @return
	 */
	public synchronized List<AreaInfoModel> loadAreaList() {
		List<AreaInfoModel> areaList = null;
		if (BeanUtils.isNotEmpty(mAreaList)) {
			areaList = mAreaList;
		} else {
			//TODO
		}
		return areaList;
	}

	/**
	 * 加载省市区列表
	 * @param fullAreaCode
	 * @return
	 */
	public List<AreaInfoModel> loadTreadAreaList(String fullAreaCode) {
		List<AreaInfoModel> areaInfoList = null;
		if (StringUtils.isNotEmpty(fullAreaCode)) {
			int depth = 1;
			if (fullAreaCode.indexOf("|") < 0) {
				depth = 1;
			} else if (fullAreaCode.split("\\|").length <= 3) {
				depth = 2;
			} else {
				// 目前仅显示省市区三级列表查询
				return null;
			}
			areaInfoList = getAreaList(mAreaList, fullAreaCode, depth);
		}
		return areaInfoList;
	}

	private List<AreaInfoModel> getAreaList(List<AreaInfoModel> areaList, String fullAreaCode, int depth) {
		List<AreaInfoModel> areaInfoList = null;
		if (StringUtils.isNotEmpty(fullAreaCode)) {
			if (depth <= 1) {
				if (DataConstants.SysCfgCode.AREA_TOP_CODE.equals(fullAreaCode)) {
					if (BeanUtils.isEmpty(mAreaList)) {
						// 查询数据库
						mAreaList = mSysCfgDao.queryChildAreaInfo(mContext, DataConstants.SysCfgCode.AREA_TOP_CODE);

						// 仅显示同步参数指定的省份
						List<AreaInfoModel> supportProvinceList = mSysCfgDao.querySupportProvinceList(mContext);
						if (BeanUtils.isNotEmpty(mAreaList) && BeanUtils.isNotEmpty(supportProvinceList)) {
							try {
								for (int i = mAreaList.size() - 1; i >= 0; i--) {
									AreaInfoModel info = mAreaList.get(i);
									if (getAreaInfo(supportProvinceList, info.code) == null) {
										mAreaList.remove(info);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						sortAreaList(mAreaList);
					}
					areaInfoList = mAreaList;
				}
			} else {
				if (fullAreaCode.indexOf("|") > 0) {
					if (BeanUtils.isNotEmpty(areaList)) {
						String[] areaCodeList = fullAreaCode.split("\\|");
						int maxDepth = areaCodeList.length;
						if (depth <= maxDepth) {
							String curCode = areaCodeList[depth - 1];
							AreaInfoModel curArea = getAreaInfo(areaList, curCode);
							if (curArea != null) {
								if (BeanUtils.isEmpty(curArea.childList)) {
									List<AreaInfoModel> childList = mSysCfgDao.queryChildAreaInfo(mContext, curCode);
									sortAreaList(mAreaList);
									updateLinkStatus(curArea, childList);
								}
								if (depth >= maxDepth) {
									return curArea.childList;
								} else {
									areaInfoList = getAreaList(curArea.childList, fullAreaCode, depth + 1);
								}
							} else {
								return null;
							}
						}
					}
				}
			}
		}
		return areaInfoList;
	}

	private void updateLinkStatus(AreaInfoModel parent, List<AreaInfoModel> childList) {
		if (parent != null && BeanUtils.isNotEmpty(childList)) {
			parent.childList = childList;
			for (AreaInfoModel info : childList) {
				info.parent = parent;
				info.isSelectedForUI = parent.isSelectedForUI;
				info.isSelectedForDB = parent.isSelectedForDB;
			}
		}
	}

	private void sortAreaList(List<AreaInfoModel> areaList) {
		if (BeanUtils.isNotEmpty(areaList)) {
			Collections.sort(areaList, new Comparator<AreaInfoModel>() {

				@Override
				public int compare(AreaInfoModel info1, AreaInfoModel info2) {
					return info1.code.compareTo(info2.code);
				}

			});
		}
	}

	private AreaInfoModel getAreaInfo(List<AreaInfoModel> areaList, String curCode) {
		if (StringUtils.isNotEmpty(curCode) && BeanUtils.isNotEmpty(areaList)) {
			for (AreaInfoModel info : areaList) {
				if (info != null && curCode.equals(info.code)) {
					return info;
				}
			}
		}
		return null;
	}

	/**
	 * 加载地域父列表
	 * @return
	 */
	public synchronized List<AreaInfoModel> loadParentAreaList(String areaCode) {
		return mSysCfgDao.queryParentAreaInfo(mContext, areaCode);
	}

	/**
	 * 加载本地银行列表
	 * @return
	 */
	public synchronized List<BankInfoModel> loadBankList() {
		List<BankInfoModel> bankList = null;
		if (BeanUtils.isNotEmpty(mBankList)) {
			bankList = mBankList;
		} else {
			bankList = SysCfgLocalMgr.getIntance(mContext).loadBankList();
			mBankList = bankList;
		}
		return bankList;
	}

	/**
	 * 加载本地系统参数列表
	 * @return
	 */
	public synchronized List<SysParamInfoModel> loadSysParamList() {
		List<SysParamInfoModel> sysParamList = null;
		if (BeanUtils.isNotEmpty(mSysParamList)) {
			sysParamList = mSysParamList;
		} else {
			List<SysParamInfoModel> list = mSysCfgDao.querySysParamInfo(mContext);
			if (BeanUtils.isEmpty(list)) {
				list = SysCfgLocalMgr.getIntance(mContext).loadSysParamList();
			}
			mSysParamList = list;
			sysParamList = list;
		}
		return sysParamList;
	}

	/**
	 * 加载本地数据库中同步时间戳信息
	 * @param typeList
	 * @return
	 */
	public synchronized Map<String, String> loadSyncTimestamp(List<String> typeList) {
		Map<String, String> data = null;
		if (BeanUtils.isNotEmpty(typeList)) {
			data = mSysCfgDao.querySyscfgTimestamp(mContext, typeList);
			if (data != null) {
				for (String type : typeList) {
					if (!data.containsKey(type)) {
						data.put(type, "");
					}
				}
			}
		}
		return data;
	}

	/**
	 * 保存系统同步信息至本地数据库
	 * @param sysCfgTimestamp
	 * @param syncInfo
	 */
	public synchronized void saveSysCfgInfo(Map<String, String> sysCfgTimestamp, SyncInfoModel syncInfo) {
		if (BeanUtils.isNotEmpty(sysCfgTimestamp) && syncInfo != null) {

			// 添加或更新同步参数Type时间戳数据
			Iterator<Entry<String, String>> it = sysCfgTimestamp.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				Map typeMap = mSysCfgDao.querySyscfgTimestamp(mContext, entry.getKey());
				if (BeanUtils.isEmpty(typeMap)) {
					// 本地无数据，直接添加
					mSysCfgDao.insertSyscfgType(mContext, entry.getKey(), entry.getValue());
				} else {
					// 本地已有数据，则更新数据
					mSysCfgDao.updateSyscfgType(mContext, entry.getKey(), entry.getValue());
				}
			}

			// 保存货物信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PRODUCT_CATEGORY) || sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PRODUCT_SUB_CATEGORY)) {
				mSysCfgDao.deleteProductInfo(mContext);
				mSysCfgDao.insertProductInfo(mContext, syncInfo.mProductList);
			}

			// 保存港口信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PORT_SECTION)) {
				mSysCfgDao.deletePortInfo(mContext);
				mSysCfgDao.insertPortInfo(mContext, syncInfo.mPortList);
			}

			// 保存省市区省份筛选列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_AREA_PROVINCE_CONTROL)) {
				mSysCfgDao.deleteSupportProvinceInfo(mContext);
				mSysCfgDao.insertSupportProvinceInfo(mContext, syncInfo.mSupportProvinceList);
			}

			// 保存系统参数信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_SYSPARAM)) {
				mSysCfgDao.deleteSysParamInfo(mContext);
				mSysCfgDao.insertSysParamInfo(mContext, syncInfo.mSysParamList);
			}

		}
	}

	public List<String> getDefaultSyncTypeList() {
		return mDefaultSyncTypeList;
	}

}
