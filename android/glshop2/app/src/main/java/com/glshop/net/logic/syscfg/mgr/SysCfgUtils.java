package com.glshop.net.logic.syscfg.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.glshop.net.R;
import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.net.utils.MenuUtil;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.DataConstants.SysCfgCode;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.api.buy.data.model.ProductInfoModel;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 系统参数工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-9 上午11:54:11
 */
public class SysCfgUtils {

	private static final String TAG = "SysCfgUtils";

	public static List<MenuItemInfo> getSandTopCategoryMenu(List<ProductCfgInfoModel> list) {
		List<MenuItemInfo> menuList = new ArrayList<MenuItemInfo>();
		if (BeanUtils.isNotEmpty(list)) {
			Set<MenuItemInfo> menuSet = new HashSet<MenuItemInfo>();
			for (ProductCfgInfoModel info : list) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
					MenuItemInfo leftMenuItem = new MenuItemInfo();
					leftMenuItem.menuText = info.mCategoryName;
					leftMenuItem.menuCode = info.mCategoryCode;
					leftMenuItem.menuID = info.mCategoryId;
					leftMenuItem.orderNo = info.mCategoryOrder;
					menuSet.add(leftMenuItem);
				}
			}
			menuList.addAll(menuSet);
			MenuUtil.sortMenuList(menuList);
		}
		return menuList;
	}

	public static List<MenuItemInfo> getSandSubCategoryMenu(String topCategory, List<ProductCfgInfoModel> list, boolean isShowUnit) {
		List<MenuItemInfo> menuList = new ArrayList<MenuItemInfo>();
		if (StringUtils.isNotEmpty(topCategory) && BeanUtils.isNotEmpty(list)) {
			for (ProductCfgInfoModel cfgInfo : list) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(cfgInfo.mTypeCode) && topCategory.equals(cfgInfo.mCategoryCode)) {
					MenuItemInfo rightMenuItem = new MenuItemInfo();
					String size = "";
					if (cfgInfo.mMinSize == 0 && cfgInfo.mMaxSize == 0) {
						size = "";
					} else if (cfgInfo.mMinSize != 0 && cfgInfo.mMaxSize == 0) {
						size = "(>=" + cfgInfo.mMinSize + ")";
					} else {
						size = "(" + cfgInfo.mMinSize + "-" + cfgInfo.mMaxSize + ")";
					}
					rightMenuItem.menuText = cfgInfo.mSubCategoryName + size + (isShowUnit ? "mm" : "");
					rightMenuItem.menuCode = cfgInfo.mSubCategoryCode;
					rightMenuItem.menuID = cfgInfo.mSubCategoryId;
					rightMenuItem.orderNo = cfgInfo.mSubCategoryOrder;
					menuList.add(rightMenuItem);
				}
			}
			MenuUtil.sortMenuList(menuList);
		}
		return menuList;
	}

	public static Map<MenuItemInfo, List<MenuItemInfo>> getSandCategoryMenu(List<ProductCfgInfoModel> list) {
		Map<MenuItemInfo, List<MenuItemInfo>> menuMap = new HashMap<MenuItemInfo, List<MenuItemInfo>>();
		if (BeanUtils.isNotEmpty(list)) {
			// Find Left MenuItem List
			Set<MenuItemInfo> leftMenuList = new HashSet<MenuItemInfo>();
			for (ProductCfgInfoModel info : list) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
					MenuItemInfo leftMenuItem = new MenuItemInfo();
					leftMenuItem.menuText = info.mCategoryName;
					leftMenuItem.menuCode = info.mCategoryCode;
					leftMenuItem.menuID = info.mCategoryId;
					leftMenuItem.orderNo = info.mCategoryOrder;
					leftMenuList.add(leftMenuItem);
				}
			}
			List<MenuItemInfo> leftMenus = new ArrayList<MenuItemInfo>(leftMenuList);
			MenuUtil.sortMenuList(leftMenus);

			// Find right MenuItem List
			Iterator<MenuItemInfo> it = leftMenus.iterator();
			while (it.hasNext()) {
				MenuItemInfo leftItem = it.next();
				List<MenuItemInfo> rightMenuItems = new ArrayList<MenuItemInfo>();
				for (ProductCfgInfoModel cfgInfo : list) {
					if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(cfgInfo.mTypeCode) && leftItem.menuCode.equals(cfgInfo.mCategoryCode)) {
						MenuItemInfo rightMenuItem = new MenuItemInfo();
						String size = "";
						if (cfgInfo.mMinSize == 0 && cfgInfo.mMaxSize == 0) {
							size = "";
						} else if (cfgInfo.mMinSize != 0 && cfgInfo.mMaxSize == 0) {
							size = "(>=" + cfgInfo.mMinSize + ")";
						} else {
							size = "(" + cfgInfo.mMinSize + "-" + cfgInfo.mMaxSize + ")";
						}
						rightMenuItem.menuText = cfgInfo.mSubCategoryName + size;
						rightMenuItem.menuCode = cfgInfo.mSubCategoryCode;
						rightMenuItem.menuID = cfgInfo.mSubCategoryId;
						rightMenuItem.orderNo = cfgInfo.mSubCategoryOrder;
						rightMenuItems.add(rightMenuItem);
					}
				}
				MenuUtil.sortMenuList(rightMenuItems);
				menuMap.put(leftItem, rightMenuItems);
			}
		}
		return menuMap;
	}

	public static List<MenuItemInfo> getStoneCategoryMenu(List<ProductCfgInfoModel> list, boolean isShowUnit) {
		Set<MenuItemInfo> leftMenuList = new HashSet<MenuItemInfo>();
		if (BeanUtils.isNotEmpty(list)) {
			for (ProductCfgInfoModel info : list) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_STONE.equals(info.mTypeCode)) {
					MenuItemInfo leftMenuItem = new MenuItemInfo();
					String size = "";
					if (info.mMinSize == 0 && info.mMaxSize == 0) {
						size = "";
					} else if (info.mMinSize != 0 && info.mMaxSize == 0) {
						size = "(>=" + info.mMinSize + ")";
					} else {
						size = "(" + info.mMinSize + "-" + info.mMaxSize + ")";
					}
					leftMenuItem.menuText = info.mCategoryName + size + (isShowUnit && StringUtils.isNotEmpty(size) ? "mm" : "");
					leftMenuItem.menuCode = info.mCategoryCode;
					leftMenuItem.menuID = info.mCategoryId;
					leftMenuItem.orderNo = info.mCategoryOrder;
					leftMenuList.add(leftMenuItem);
				}
			}
		}
		ArrayList<MenuItemInfo> menuList = new ArrayList<MenuItemInfo>(leftMenuList);
		MenuUtil.sortMenuList(menuList);
		return menuList;
	}

	public static List<MenuItemInfo> getAreaMenu(Context context) {
		List<MenuItemInfo> areaMenu = new ArrayList<MenuItemInfo>();
		List<AreaInfoModel> areaList = SysCfgMgr.getIntance(context).loadAreaList();
		if (BeanUtils.isNotEmpty(areaList)) {
			for (AreaInfoModel info : areaList) {
				MenuItemInfo menuInfo = new MenuItemInfo(info.name, info.code);
				areaMenu.add(menuInfo);
			}
		}
		return areaMenu;
	}

	public static String getAreaName(Context context, String areaCode) {
		String areaName = "";
		if (StringUtils.isNotEmpty(areaCode)) {
			List<AreaInfoModel> areaList = SysCfgMgr.getIntance(context).loadAreaList();
			if (BeanUtils.isNotEmpty(areaList)) {
				for (AreaInfoModel info : areaList) {
					if (areaCode.equals(info.code)) {
						areaName = info.name;
						break;
					}
				}
			}
		}
		return areaName;
	}

	public static List<MenuItemInfo> getBankMenu(Context context) {
		List<MenuItemInfo> bankMenu = new ArrayList<MenuItemInfo>();
		List<BankInfoModel> bankList = SysCfgMgr.getIntance(context).loadBankList();
		if (BeanUtils.isNotEmpty(bankList)) {
			for (BankInfoModel info : bankList) {
				MenuItemInfo menuInfo = new MenuItemInfo(info.name, info.code);
				bankMenu.add(menuInfo);
			}
		}
		return bankMenu;
	}

	public static String getBankName(Context context, String bankCode) {
		String bankName = "";
		if (StringUtils.isNotEmpty(bankCode)) {
			List<BankInfoModel> bankList = SysCfgMgr.getIntance(context).loadBankList();
			if (BeanUtils.isNotEmpty(bankList)) {
				for (BankInfoModel info : bankList) {
					if (bankCode.equals(info.code)) {
						bankName = info.name;
						break;
					}
				}
			}
		}
		return bankName;
	}

	public static ProductInfoModel getProductPropInfo(ProductType type, String specId, List<ProductCfgInfoModel> list) {
		List<ProductPropInfoModel> propList = null;
		if (BeanUtils.isNotEmpty(list)) {
			//Logger.i("", "SpecIdSize = " + list.size() + ", SpecId = " + specId);
			for (ProductCfgInfoModel info : list) {
				//Logger.e("", "SpecId = " + info.mSubCategoryId);
				if (type == ProductType.SAND && info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_SAND) && specId.equals(info.mSubCategoryId)) {
					propList = info.mPropList;
					break;
				} else if (type == ProductType.STONE && info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_STONE) && specId.equals(info.mCategoryId)) {
					propList = info.mPropList;
					break;
				}
			}
		}
		return parseProductPropInfo(propList);
	}

	public static ProductInfoModel parseProductPropInfo(List<ProductPropInfoModel> list) {
		ProductInfoModel propInfo = new ProductInfoModel();
		if (BeanUtils.isNotEmpty(list)) {
			for (ProductPropInfoModel prop : list) {
				if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_SEDIMENT)) {
					propInfo.sedimentPercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_SEDIMENT_BLOCK)) {
					propInfo.sedimentBlockPercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_WATER)) {
					propInfo.waterPercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_APPEARANCE)) {
					propInfo.appearanceDensity = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_STACKING)) {
					propInfo.stackingPercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_CRUNCH)) {
					propInfo.crunchPercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_NEEDLE_PLATE)) {
					propInfo.needlePlatePercentage = (ProductPropInfoModel) prop.clone();
				} else if (prop.mPropCode.equals(DataConstants.SysCfgCode.TYPE_PROP_STURDINESS)) {
					propInfo.sturdinessPercentage = (ProductPropInfoModel) prop.clone();
				}
			}
		}
		return propInfo;
	}

	public static String convertPropInfo2JSON(ProductInfoModel info) {
		String json = "";
		if (info != null) {
			JSONArray array = new JSONArray();
			array.put(getPropJSONObject(info.sedimentPercentage));
			array.put(getPropJSONObject(info.sedimentBlockPercentage));
			array.put(getPropJSONObject(info.waterPercentage));
			array.put(getPropJSONObject(info.appearanceDensity));
			array.put(getPropJSONObject(info.stackingPercentage));
			array.put(getPropJSONObject(info.crunchPercentage));
			array.put(getPropJSONObject(info.needlePlatePercentage));
			array.put(getPropJSONObject(info.sturdinessPercentage));
			json = array.toString();
		}
		return json;
	}

	private static JSONObject getPropJSONObject(ProductPropInfoModel info) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", info.mPropId);
			object.put("content", info.mRealSize);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static double getCompanyDeposit(Context context) {
		double money = 0;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		for (SysParamInfoModel info : list) {
			if (SysCfgCode.TYPE_BOND_ENTERPRISE.equals(info.name)) {
				money = Double.parseDouble(info.code);
				break;
			}
		}
		return money;
	}

	public static double getShipDeposit(Context context, double weight) {
		double money = 0;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (weight > 0) {
			for (SysParamInfoModel info : list) {
				if (info.name.startsWith(SysCfgCode.TYPE_BOND_SHIP)) {
					try {
						String[] weightRange = info.name.substring(SysCfgCode.TYPE_BOND_SHIP.length()).split("_");
						if (weight >= Double.parseDouble(weightRange[0]) && weight <= Double.parseDouble(weightRange[1])) {
							money = Double.parseDouble(info.code);
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return money;
	}

	public static double getLowestShipDeposit(Context context) {
		double money = 0;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		for (int i = 0; i < list.size(); i++) {
			SysParamInfoModel info = list.get(i);
			if (info.name.startsWith(SysCfgCode.TYPE_BOND_SHIP)) {
				try {
					if (i == 0) {
						money = Double.parseDouble(info.code);
					} else if (money <= Double.parseDouble(info.code)) {
						money = Double.parseDouble(info.code);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return money;
	}

	public static double calRechargeMoney(Context context, ProfileType type, double weight) {
		double money = 0;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (type == ProfileType.COMPANY) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_BOND_ENTERPRISE.equals(info.name)) {
					money = Double.parseDouble(info.code);
					break;
				}
			}
		} else if (type == ProfileType.PEOPLE) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_BOND_PERSONAL.equals(info.name)) {
					money = Double.parseDouble(info.code);
					break;
				}
			}
		} else if (type == ProfileType.SHIP) {
			if (weight > 0) {
				for (SysParamInfoModel info : list) {
					if (info.name.startsWith(SysCfgCode.TYPE_BOND_SHIP)) {
						try {
							String[] weightRange = info.name.substring(SysCfgCode.TYPE_BOND_SHIP.length()).split("_");
							if (weight >= Double.parseDouble(weightRange[0]) && weight <= Double.parseDouble(weightRange[1])) {
								money = Double.parseDouble(info.code);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return money;
	}

	/**
	 * 获取卖家同意确认的有效期
	 * @param context
	 * @return
	 */
	public static String getSellerConfirmLimitTime(Context context) {
		String limitTime = "72";
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (BeanUtils.isNotEmpty(list)) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_CONTRACT_AGREEFINALESTIME_LIMIT_TIME.equals(info.name)) {
					limitTime = info.code;
					break;
				}
			}
		}
		return limitTime;
	}

	/**
	 * 获取保证金冻结百分比
	 * @param context
	 * @return
	 */
	public static double getDepositPercent(Context context) {
		double percent = 0.05;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (BeanUtils.isNotEmpty(list)) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_DEPOSIT_PERCENT.equals(info.name)) {
					try {
						percent = Double.parseDouble(info.code);
					} catch (Exception e) {

					}
					break;
				}
			}
		}
		return percent;
	}

	/**
	 * 获取交易服务费百分比
	 * @param context
	 * @return
	 */
	public static double getServicePercent(Context context) {
		double percent = 0.03;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (BeanUtils.isNotEmpty(list)) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_SERVICE_PERCENT.equals(info.name)) {
					try {
						percent = Double.parseDouble(info.code);
					} catch (Exception e) {

					}
					break;
				}
			}
		}
		return percent;
	}

	/**
	 * 获取合同默认评价期限
	 * @param context
	 * @return
	 */
	public static int getEvaLimitDay(Context context) {
		int limitDay = 7;
		List<SysParamInfoModel> list = SysCfgMgr.getIntance(context).loadSysParamList();
		if (BeanUtils.isNotEmpty(list)) {
			for (SysParamInfoModel info : list) {
				if (SysCfgCode.TYPE_CONTRACT_EVALUATION_LIMIT_TIME.equals(info.name)) {
					try {
						limitDay = Integer.parseInt(info.code) / 24;
					} catch (Exception e) {

					}
					break;
				}
			}
		}
		return limitDay;
	}

	/**
	 * 获取货物子规格详细名称
	 * @param context
	 * @param categoryType 大类型
	 * @param subCategoryType 小类型
	 * @param pid 具体类型id
	 * @return
	 */
	public static String getSubProductFullName(Context context, String type, String categoryType, String pid) {
		String subProductName = "";
		ProductCfgInfoModel info = getProductCfgInfo(context, type, categoryType, pid);
		if (info != null) {
			subProductName = getSubProductFullName(info);
		}
		return subProductName;
	}

	/**
	 * 获取货物子规格详细名称
	 * @param context
	 * @param info 货物属性信息
	 * @return
	 */
	public static String getSubProductFullName(ProductCfgInfoModel info) {
		String subProductName = "";
		if (info != null) {
			if (info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_SAND)) {
				String size = getSize(info.mMaxSize, info.mMinSize);
				if (StringUtils.isNotEmpty(size)) {
					subProductName = info.mSubCategoryName + size;
				}
			} else if (info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_STONE)) {
				String size = getSize(info.mMaxSize, info.mMinSize);
				if (StringUtils.isNotEmpty(size)) {
					subProductName = size;
				}
			}
		}
		return subProductName;
	}

	/**
	 * 获取货物全称，以.分隔。
	 * @param context
	 * @param categoryType 大类型
	 * @param subCategoryType 小类型
	 * @param pid 具体类型id
	 * @return
	 */
	public static String getProductFullName(Context context, String type, String categoryType, String pid) {
		String productName = "";
		ProductCfgInfoModel info = getProductCfgInfo(context, type, categoryType, pid);
		if (info != null) {
			String size = getSize(info.mMaxSize, info.mMinSize);
			if (type.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_SAND)) {
				if (StringUtils.isNotEmpty(size)) {
					productName = context.getString(R.string.product_type_sand) + "." + info.mCategoryName + "." + info.mSubCategoryName + size + "mm";
				} else {
					productName = context.getString(R.string.product_type_sand) + "." + info.mCategoryName + "." + info.mSubCategoryName;
				}
			} else if (type.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_STONE)) {
				if (StringUtils.isNotEmpty(size)) {
					productName = context.getString(R.string.product_type_stone) + "." + info.mCategoryName + size + "mm";
				} else {
					productName = context.getString(R.string.product_type_stone) + "." + info.mCategoryName;
				}
			}
		}
		return productName;
	}

	public static String getSize(ProductCfgInfoModel info) {
		String size = "";
		if (info != null) {
			size = getSize(info.mMaxSize, info.mMinSize);
		}
		return size;
	}

	public static String getSize(float max, float min) {
		String size = "";
		if (min == 0 && max == 0) {
			size = "";
		} else if (min != 0 && max == 0) {
			size = "(>=" + min + ")";
		} else {
			size = "(" + min + "-" + max + ")";
		}
		return size;
	}

	/**
	 * 获取货物属性详情
	 * @param context
	 * @param categoryType 大类型
	 * @param subCategoryType 小类型
	 * @param pid 具体类型id
	 * @return
	 */
	public static ProductCfgInfoModel getProductCfgInfo(Context context, String type, String categoryType, String pid) {
		ProductCfgInfoModel productInfo = null;
		if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(pid)) {
			List<ProductCfgInfoModel> productList = SysCfgMgr.getIntance(context).loadProductList();
			if (BeanUtils.isNotEmpty(productList)) {
				for (ProductCfgInfoModel info : productList) {
					if (type.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_SAND)) {
						if (StringUtils.isNotEmpty(categoryType) && categoryType.equals(info.mCategoryCode) && pid.equals(info.mSubCategoryId)) {
							return info;
						}
					} else if (type.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_STONE)) {
						if (pid.equals(info.mCategoryId)) {
							return info;
						}
					}
				}
			}
		}
		return productInfo;
	}

	/**
	 * 解析黄砂价格趋势列表
	 * @param context
	 * @param priceList
	 * @return
	 */
	public static Object[] parseSandPriceForecastList(Context context, List<ForecastPriceModel> priceList) {
		Object[] sandGroupList = new Object[2];
		Map<MenuItemInfo, List<ForecastPriceModel>> groupMap = new HashMap<MenuItemInfo, List<ForecastPriceModel>>();
		List<ProductCfgInfoModel> productCfglist = SysCfgMgr.getIntance(context).loadProductList();
		if (BeanUtils.isNotEmpty(productCfglist) && BeanUtils.isNotEmpty(priceList)) {
			// Find Group Item List
			Set<MenuItemInfo> groupList = new HashSet<MenuItemInfo>();
			for (ProductCfgInfoModel info : productCfglist) {
				if (DataConstants.SysCfgCode.TYPE_PRODUCT_SAND.equals(info.mTypeCode)) {
					MenuItemInfo leftMenuItem = new MenuItemInfo();
					leftMenuItem.menuText = info.mCategoryName;
					leftMenuItem.menuCode = info.mCategoryCode;
					leftMenuItem.menuID = info.mCategoryId;
					leftMenuItem.orderNo = info.mCategoryOrder;
					groupList.add(leftMenuItem);
				}
			}
			// sort top category
			List<MenuItemInfo> groupItemList = new ArrayList<MenuItemInfo>(groupList);
			MenuUtil.sortMenuList(groupItemList);

			// Find Child Item List
			for (ForecastPriceModel priceInfo : priceList) {
				ProductCfgInfoModel productCfgInfo = getProductCfgInfo(context, DataConstants.SysCfgCode.TYPE_PRODUCT_SAND, priceInfo.productSepcType, priceInfo.productSepcId);
				if (productCfgInfo != null) {
					priceInfo.productInfo = productCfgInfo;
					Iterator<MenuItemInfo> it = groupItemList.iterator();
					while (it.hasNext()) {
						MenuItemInfo groupItem = it.next();
						if (productCfgInfo.mCategoryCode.equals(groupItem.menuCode)) {
							List<ForecastPriceModel> priceItemList = groupMap.get(groupItem);
							if (priceItemList == null) {
								priceItemList = new ArrayList<ForecastPriceModel>();
								groupMap.put(groupItem, priceItemList);
							}
							priceItemList.add(priceInfo);
							break;
						}
					}
				}
			}

			// Sort Child Item List
			Iterator<Entry<MenuItemInfo, List<ForecastPriceModel>>> it = groupMap.entrySet().iterator();
			while (it.hasNext()) {
				List<ForecastPriceModel> priceItemList = it.next().getValue();
				if (BeanUtils.isNotEmpty(priceItemList)) {
					Collections.sort(priceItemList, new Comparator<ForecastPriceModel>() {

						@Override
						public int compare(ForecastPriceModel info1, ForecastPriceModel info2) {
							if (info1.productInfo != null && info2.productInfo != null && StringUtils.isNotEmpty(info1.productInfo.mSubCategoryOrder)
									&& StringUtils.isNotEmpty(info2.productInfo.mSubCategoryOrder)) {
								if (StringUtils.isDigital(info1.productInfo.mSubCategoryOrder) && StringUtils.isDigital(info2.productInfo.mSubCategoryOrder)) {
									int lhs = Integer.parseInt(info1.productInfo.mSubCategoryOrder);
									int rhs = Integer.parseInt(info2.productInfo.mSubCategoryOrder);
									return lhs - rhs;
								} else {
									return info1.productInfo.mSubCategoryOrder.compareTo(info2.productInfo.mSubCategoryOrder);
								}
							} else {
								return 0;
							}
						}

					});
				}
			}

			sandGroupList[0] = groupItemList;
			sandGroupList[1] = groupMap;
		}
		return sandGroupList;
	}

	/**
	 * 解析石子价格趋势列表
	 * @param context
	 * @param priceList
	 * @return
	 */
	public static void parseStonePriceForecastList(Context context, List<ForecastPriceModel> priceList) {
		if (BeanUtils.isNotEmpty(priceList)) {
			// Find Top Item List
			for (ForecastPriceModel priceInfo : priceList) {
				ProductCfgInfoModel productCfgInfo = getProductCfgInfo(context, DataConstants.SysCfgCode.TYPE_PRODUCT_STONE, priceInfo.productSepcType, priceInfo.productSepcId);
				if (productCfgInfo != null) {
					priceInfo.productInfo = productCfgInfo;
				}
			}
			// sort top category
			sortForecastPriceList(ProductType.STONE, priceList);
		}
	}

	/**
	 * 排序今日报价列表
	 * @param type
	 * @param priceList
	 */
	public static void sortTodayPriceList(ProductType type, List<TodayPriceModel> priceList) {
		if (BeanUtils.isNotEmpty(priceList)) {
			if (type == ProductType.SAND) {
				Collections.sort(priceList, new Comparator<TodayPriceModel>() {

					@Override
					public int compare(TodayPriceModel info1, TodayPriceModel info2) {
						if (info1.productInfo != null && info2.productInfo != null && StringUtils.isNotEmpty(info1.productInfo.mCategoryOrder)
								&& StringUtils.isNotEmpty(info2.productInfo.mCategoryOrder) && StringUtils.isNotEmpty(info1.productInfo.mSubCategoryOrder)
								&& StringUtils.isNotEmpty(info2.productInfo.mSubCategoryOrder)) {
							if (StringUtils.isDigital(info1.productInfo.mCategoryOrder) && StringUtils.isDigital(info2.productInfo.mCategoryOrder)
									&& StringUtils.isDigital(info1.productInfo.mSubCategoryOrder) && StringUtils.isDigital(info2.productInfo.mSubCategoryOrder)) {
								int topSort = Integer.parseInt(info1.productInfo.mCategoryOrder) - Integer.parseInt(info2.productInfo.mCategoryOrder);
								int subSort = Integer.parseInt(info1.productInfo.mSubCategoryOrder) - Integer.parseInt(info2.productInfo.mSubCategoryOrder);
								if (topSort == 0) {
									return subSort;
								} else {
									return topSort;
								}
							} else {
								return 0;
							}
						} else {
							return 0;
						}
					}
				});
			} else if (type == ProductType.STONE) {
				Collections.sort(priceList, new Comparator<TodayPriceModel>() {

					@Override
					public int compare(TodayPriceModel info1, TodayPriceModel info2) {
						if (info1.productInfo != null && info2.productInfo != null && StringUtils.isNotEmpty(info1.productInfo.mCategoryOrder)
								&& StringUtils.isNotEmpty(info2.productInfo.mCategoryOrder)) {
							if (StringUtils.isDigital(info1.productInfo.mCategoryOrder) && StringUtils.isDigital(info2.productInfo.mCategoryOrder)) {
								int lhs = Integer.parseInt(info1.productInfo.mCategoryOrder);
								int rhs = Integer.parseInt(info2.productInfo.mCategoryOrder);
								return lhs - rhs;
							} else {
								return info1.productInfo.mCategoryOrder.compareTo(info2.productInfo.mCategoryOrder);
							}
						} else {
							return 0;
						}
					}

				});
			}
		}
	}

	/**
	 * 排序未来报价列表
	 * @param type
	 * @param priceList
	 */
	public static void sortForecastPriceList(ProductType type, List<ForecastPriceModel> priceList) {
		if (BeanUtils.isNotEmpty(priceList)) {
			if (type == ProductType.SAND) {
				// TODO
			} else if (type == ProductType.STONE) {
				Collections.sort(priceList, new Comparator<ForecastPriceModel>() {

					@Override
					public int compare(ForecastPriceModel info1, ForecastPriceModel info2) {
						if (info1.productInfo != null && info2.productInfo != null && StringUtils.isNotEmpty(info1.productInfo.mCategoryOrder)
								&& StringUtils.isNotEmpty(info2.productInfo.mCategoryOrder)) {
							if (StringUtils.isDigital(info1.productInfo.mCategoryOrder) && StringUtils.isDigital(info2.productInfo.mCategoryOrder)) {
								int lhs = Integer.parseInt(info1.productInfo.mCategoryOrder);
								int rhs = Integer.parseInt(info2.productInfo.mCategoryOrder);
								return lhs - rhs;
							} else {
								return info1.productInfo.mCategoryOrder.compareTo(info2.productInfo.mCategoryOrder);
							}
						} else {
							return 0;
						}
					}

				});
			}
		}
	}

	/**
	 * 重置货物列表筛选状态
	 */
	public synchronized static void resetProductFilterList(Context context) {
		List<ProductCfgInfoModel> typeCategoryList = SysCfgMgr.getIntance(context).getProductCategoryList();
		if (BeanUtils.isNotEmpty(typeCategoryList)) {
			for (ProductCfgInfoModel info : typeCategoryList) {
				info.isSelectedForUI = false;
				updateChildStatus(info, false);
			}
		}
	}

	private static void updateChildStatus(ProductCfgInfoModel productInfo, boolean isSelectedForUI) {
		if (productInfo != null && BeanUtils.isNotEmpty(productInfo.childList)) {
			List<ProductCfgInfoModel> childList = productInfo.childList;
			for (ProductCfgInfoModel info : childList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, productInfo.isSelectedForUI);
			}
		}
	}

	/**
	 * 重置地域列表筛选状态
	 */
	public synchronized static void resetAreaFilterList(Context context) {
		List<AreaInfoModel> areaList = SysCfgMgr.getIntance(context).loadAreaList();
		if (BeanUtils.isNotEmpty(areaList)) {
			for (AreaInfoModel info : areaList) {
				info.isSelectedForUI = false;
				updateChildStatus(info, false);
			}
		}
	}

	private static void updateChildStatus(AreaInfoModel areaInfo, boolean isSelectedForUI) {
		if (areaInfo != null && BeanUtils.isNotEmpty(areaInfo.childList)) {
			List<AreaInfoModel> childList = areaInfo.childList;
			for (AreaInfoModel info : childList) {
				info.isSelectedForUI = isSelectedForUI;
				updateChildStatus(info, areaInfo.isSelectedForUI);
			}
		}
	}

}
