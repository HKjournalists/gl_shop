package com.glshop.net.logic.syscfg.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

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

	/** 货物(沙子)参数信息 */
	//private List<SyscfgInfoModel> mProductSandList;

	/** 货物(石头)参数信息 */
	//private List<SyscfgInfoModel> mProductStoneList;

	/** 省份列表参数信息 */
	//private List<SyscfgInfoModel> mProvinceList;

	/** 城市列表参数信息 */
	//private List<SyscfgInfoModel> mCityList;

	/** 市区列表参数信息 */
	//private List<SyscfgInfoModel> mDistrictList;

	/** 货物信息 */
	private List<ProductCfgInfoModel> mProductList;

	/** 地域信息 */
	private List<AreaInfoModel> mAreaList;

	/** 银行列表参数信息 */
	private List<BankInfoModel> mBankList;

	/** 系统参数列表信息 */
	private List<SysParamInfoModel> mSysParamList;

	/** 系统默认同步参数列表 */
	private List<String> mDefaultSyncTypeList;

	private SysCfgMgr(Context context) {
		mContext = context;

		// 初始化默认同步参数列表
		mDefaultSyncTypeList = new ArrayList<String>();
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PRODUCT_CATEGORY);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PRODUCT_SUB_CATEGORY);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_PORT_SECTION);
		//mDefaultSyncTypeList.add(SysCfgCode.TYPE_AREA);
		//mDefaultSyncTypeList.add(SysCfgCode.TYPE_BANK);
		mDefaultSyncTypeList.add(SysCfgCode.TYPE_SYSPARAM);
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
			ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);
			List<ProductCfgInfoModel> list = sysCfgDao.queryProductInfo(mContext);
			if (BeanUtils.isEmpty(list)) {
				list = SysCfgLocalMgr.getIntance(mContext).loadProductList();
			}
			mProductList = list;
			productList = list;
		}
		return productList;
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
			ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);
			List<AreaInfoModel> list = sysCfgDao.queryAreaInfo(mContext);
			if (BeanUtils.isEmpty(list)) {
				list = SysCfgLocalMgr.getIntance(mContext).loadAreaList();
			}
			if (BeanUtils.isNotEmpty(list)) {
				Collections.sort(list, new Comparator<AreaInfoModel>() {

					@Override
					public int compare(AreaInfoModel info1, AreaInfoModel info2) {
						return info1.code.compareTo(info2.code);
					}

				});
			}
			mAreaList = list;
			areaList = list;
		}
		return areaList;
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
			ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);
			List<SysParamInfoModel> list = sysCfgDao.querySysParamInfo(mContext);
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
			ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);
			data = sysCfgDao.querySyscfgTimestamp(mContext, typeList);
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
			ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);

			// 添加或更新同步参数Type时间戳数据
			Iterator<Entry<String, String>> it = sysCfgTimestamp.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				Map typeMap = sysCfgDao.querySyscfgTimestamp(mContext, entry.getKey());
				if (BeanUtils.isEmpty(typeMap)) {
					// 本地无数据，直接添加
					sysCfgDao.insertSyscfgType(mContext, entry.getKey(), entry.getValue());
				} else {
					// 本地已有数据，则更新数据
					sysCfgDao.updateSyscfgType(mContext, entry.getKey(), entry.getValue());
				}
			}

			// 保存货物信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PRODUCT_CATEGORY) || sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PRODUCT_SUB_CATEGORY)) {
				sysCfgDao.deleteProductInfo(mContext);
				sysCfgDao.insertProductInfo(mContext, syncInfo.mProductList);
			}

			// 保存地域信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_PORT_SECTION)) {
				sysCfgDao.deleteAreaInfo(mContext);
				sysCfgDao.insertAreaInfo(mContext, syncInfo.mAreaList);
			}

			// 保存系统参数信息列表
			if (sysCfgTimestamp.containsKey(DataConstants.SysCfgCode.TYPE_SYSPARAM)) {
				sysCfgDao.deleteSysParamInfo(mContext);
				sysCfgDao.insertSysParamInfo(mContext, syncInfo.mSysParamList);
			}

		}
	}

	public List<String> getDefaultSyncTypeList() {
		return mDefaultSyncTypeList;
	}

}
