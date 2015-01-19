package com.glshop.net.logic.syscfg.mgr;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.glshop.net.common.GlobalConstants;
import com.glshop.net.logic.db.dao.syscfg.ISyscfgDao;
import com.glshop.net.logic.db.dao.syscfg.SyscfgDao;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.api.util.SyncCfgUtils;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.FileUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 本地系统参数配置管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-9 上午11:54:11
 */
public class SysCfgLocalMgr {

	private static final String TAG = "SysCfgLocalMgr";

	private Context mContext;

	private static SysCfgLocalMgr mInstance;

	private static final String LOCAL_SYNC_PATH = "data/sync.data";
	private static final String LOCAL_BANK_PATH = "data/bank.data";
	private static final String LOCAL_AREA_PATH = "data/area.data";

	/** 本地配置数据集 */
	private ResultItem mLocaleSyncData;

	/** 本地银行配置数据集 */
	private ResultItem mLocaleBankData;

	/** 本地地域配置数据集 */
	private ResultItem mLocaleAreaData;

	/** 货物信息 */
	private List<ProductCfgInfoModel> mProductList;

	/** 港口信息 */
	private List<AreaInfoModel> mPortList;

	/** 地域信息 */
	private List<AreaInfoModel> mAreaList;

	/** 银行列表参数信息 */
	private List<BankInfoModel> mBankList;

	/** 系统参数列表信息 */
	private List<SysParamInfoModel> mSysParamList;

	private boolean isImportingAreaCfg = false;

	private SysCfgLocalMgr(Context context) {
		mContext = context;
		mLocaleSyncData = loadLocalData(LOCAL_SYNC_PATH);
		mLocaleBankData = loadLocalData(LOCAL_BANK_PATH);
	}

	public static synchronized SysCfgLocalMgr getIntance(Context context) {
		if (mInstance == null) {
			mInstance = new SysCfgLocalMgr(context);
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
			ResultItem productItem = (ResultItem) mLocaleSyncData.get("DATA|goods");
			ResultItem productSpecItem = (ResultItem) mLocaleSyncData.get("DATA|goodChild");
			if (productItem != null && productSpecItem != null) {
				List<ProductCfgInfoModel> productSpecList = SyncCfgUtils.parseSysProductData(productItem, productSpecItem, null);
				if (BeanUtils.isNotEmpty(productSpecList)) {
					mProductList = productSpecList;
					productList = productSpecList;
				}
			}
		}
		return productList;
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
			ResultItem areaItem = (ResultItem) mLocaleSyncData.get("data|riverSection");
			if (areaItem != null) {
				portList = SyncCfgUtils.parseSysPortData(areaItem, null);
				if (BeanUtils.isNotEmpty(portList)) {
					mPortList = portList;
				}
			}
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
	 * 加载本地银行列表
	 * @return
	 */
	public synchronized List<BankInfoModel> loadBankList() {
		List<BankInfoModel> bankList = null;
		if (BeanUtils.isNotEmpty(mBankList)) {
			bankList = mBankList;
		} else {
			ResultItem bankItem = (ResultItem) mLocaleBankData.get("bank");
			if (bankItem != null) {
				bankList = SyncCfgUtils.parseSysBankData(bankItem, null);
				if (BeanUtils.isNotEmpty(bankList)) {
					mBankList = bankList;
				}
			}
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
			ResultItem sysParamItem = (ResultItem) mLocaleSyncData.get("data|sysParam");
			if (sysParamItem != null) {
				sysParamList = SyncCfgUtils.parseSysParamData(sysParamItem, null);
				if (BeanUtils.isNotEmpty(sysParamList)) {
					mSysParamList = sysParamList;
				}
			}
		}
		return sysParamList;
	}

	public synchronized void importLocalAreaCfg() {
		if (!isImportingAreaCfg) {
			isImportingAreaCfg = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (mLocaleAreaData == null) {
						mLocaleAreaData = loadLocalData(LOCAL_AREA_PATH);
					}
					ResultItem areaItem = (ResultItem) mLocaleAreaData.get("data|area");
					if (areaItem != null) {
						List<AreaInfoModel> areaList = SyncCfgUtils.parseAreaData(areaItem);
						if (BeanUtils.isNotEmpty(areaList)) {
							ISyscfgDao sysCfgDao = SyscfgDao.getInstance(mContext);
							//Step1. 先删除本地数据库中的地域信息
							sysCfgDao.deleteAllAreaInfo(mContext);

							//Step2. 重新导入新的地域列表
							sysCfgDao.insertAreaInfo(mContext, areaList);
							isImportingAreaCfg = false;

							//Step3. 设置已导入标记
							PlatformConfig.setValue(GlobalConstants.SPKey.IS_IMPORTED_AREA_CFG, true);
						}
					}
					mLocaleAreaData = null;
				}
			}).start();
		} else {
			Logger.e(TAG, "importing, so ignore this request!");
		}
	}

	/**
	 * 加载本地缓存数据
	 * @param filePath
	 * @param item
	 */
	private ResultItem loadLocalData(String filePath) {
		ResultItem item = null;
		try {
			String localJSONData = FileUtils.getContent(mContext.getAssets().open(filePath), "UTF-8");
			item = new ResultItem(new JSONObject(localJSONData));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

}
