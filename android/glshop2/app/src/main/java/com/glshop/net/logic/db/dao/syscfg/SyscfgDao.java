package com.glshop.net.logic.db.dao.syscfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.glshop.net.logic.db.DBConstants.TableAreaCfg;
import com.glshop.net.logic.db.DBConstants.TableAreaData;
import com.glshop.net.logic.db.DBConstants.TableProductData;
import com.glshop.net.logic.db.DBConstants.TableProductPropData;
import com.glshop.net.logic.db.DBConstants.TableSyscfgData;
import com.glshop.net.logic.db.DBConstants.TableSyscfgType;
import com.glshop.net.logic.db.DBManager;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductPropInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 系统参数管理业务实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public class SyscfgDao implements ISyscfgDao {

	private static SyscfgDao mInstance;

	private SyscfgDao(Context context) {

	}

	public static synchronized SyscfgDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SyscfgDao(context);
		}
		return mInstance;
	}

	@Override
	public Map<String, String> querySyscfgTimestamp(Context context, String type) {
		if (StringUtils.isNEmpty(type)) {
			return null;
		}
		return querySyscfgTimestamp(context, Arrays.asList(type));
	}

	@Override
	public Map<String, String> querySyscfgTimestamp(Context context, List<String> typeList) {
		Map<String, String> timestampList = new HashMap<String, String>();

		if (typeList == null || typeList.size() == 0) {
			return null;
		}

		String where = TableSyscfgType.Column.TYPE + createWhereInArgs(typeList);
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableSyscfgType.TABLE_NAME, TableSyscfgType.ALL_COLUMNS, where, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					String type = cursor.getString(cursor.getColumnIndex(TableSyscfgType.Column.TYPE));
					String timestamp = cursor.getString(cursor.getColumnIndex(TableSyscfgType.Column.TIMESTAMP));
					timestampList.put(type, timestamp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return timestampList;
	}

	@Override
	public long insertSyscfgType(Context context, String type, String timestamp) {
		ContentValues values = convertTimestampInfo2CV(type, timestamp);
		long result = DBManager.insert(context, TableSyscfgType.TABLE_NAME, values);
		values.clear();
		return result;
	}

	@Override
	public boolean bulkInsertSyscfgType(Context context, Map<String, String> typeList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		Iterator<Entry<String, String>> it = typeList.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			values.add(convertTimestampInfo2CV(entry.getKey(), entry.getValue()));
		}

		boolean result = DBManager.bulkInsert(context, TableSyscfgType.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public boolean updateSyscfgType(Context context, String type, String timestamp) {
		ContentValues values = convertTimestampInfo2CV(type, timestamp);
		String where = TableSyscfgType.Column.TYPE + " = ?";
		String[] args = new String[] { type };
		boolean result = DBManager.update(context, TableSyscfgType.TABLE_NAME, values, where, args) != -1;
		values.clear();
		return result;
	}

	@Override
	public boolean bulkUpdateSyscfgType(Context context, Map<String, String> timestampList) {
		String where = TableSyscfgType.Column.TYPE + " = ?";
		Iterator<Entry<String, String>> it = timestampList.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			ContentValues values = convertTimestampInfo2CV(entry.getKey(), entry.getValue());
			String[] args = new String[] { entry.getKey() };
			DBManager.update(context, TableSyscfgType.TABLE_NAME, values, where, args);
			values.clear();
		}
		return true;
	}

	@Override
	public boolean deleteSyscfgType(Context context, List<String> typeList) {
		String where = TableSyscfgType.Column.TYPE + createWhereInArgs(typeList);
		return DBManager.delete(context, TableSyscfgType.TABLE_NAME, where, null) != -1;
	}

	@Override
	public List<ProductCfgInfoModel> queryProductInfo(Context context) {
		List<ProductPropInfoModel> propInfoList = queryProductPropInfo(context);
		List<ProductCfgInfoModel> infoList = null;
		if (BeanUtils.isNotEmpty(propInfoList)) {
			Cursor cursor = null;
			try {
				cursor = DBManager.query(context, TableProductData.TABLE_NAME, TableProductData.ALL_COLUMNS, null, null, null, null, null);
				if (cursor != null && cursor.getCount() > 0) {
					infoList = new ArrayList<ProductCfgInfoModel>();
					for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
						ProductCfgInfoModel info = convertCursor2ProductCfgInfo(cursor);
						info.mPropList = getPropList(info.mTypeCode, info.mCategoryCode, info.mSubCategoryCode, propInfoList);
						infoList.add(info);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
					cursor = null;
				}
			}
		}
		return infoList;
	}

	/**
	 * 查询所有货物属性数据
	 * @param context
	 * @return
	 */
	private List<ProductPropInfoModel> queryProductPropInfo(Context context) {
		List<ProductPropInfoModel> infoList = null;
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableProductPropData.TABLE_NAME, TableProductPropData.ALL_COLUMNS, null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<ProductPropInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2ProductPropInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	private List<ProductPropInfoModel> getPropList(String typeCode, String categoryCode, String subCategoryCode, List<ProductPropInfoModel> propInfoList) {
		List<ProductPropInfoModel> propList = new ArrayList<ProductPropInfoModel>();
		if (BeanUtils.isNotEmpty(propInfoList)) {
			for (ProductPropInfoModel info : propInfoList) {
				if (StringUtils.isNotEmpty(info.mTypeCode) && info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_SAND)) {
					if (StringUtils.isNotEmpty(info.mCategoryCode) && StringUtils.isNotEmpty(info.mSubCategoryCode)) {
						if (info.mTypeCode.equals(typeCode) && info.mCategoryCode.equals(categoryCode) && info.mSubCategoryCode.equals(subCategoryCode)) {
							propList.add(info);
						}
					}
				} else if (StringUtils.isNotEmpty(info.mTypeCode) && info.mTypeCode.equals(DataConstants.SysCfgCode.TYPE_PRODUCT_STONE)) {
					if (StringUtils.isNotEmpty(info.mCategoryCode)) {
						if (info.mTypeCode.equals(typeCode) && info.mCategoryCode.equals(categoryCode)) {
							propList.add(info);
						}
					}
				}
			}
		}
		return propList;
	}

	@Override
	public boolean insertProductInfo(Context context, List<ProductCfgInfoModel> infoList) {
		boolean result = false;
		for (ProductCfgInfoModel info : infoList) {
			DBManager.insert(context, TableProductData.TABLE_NAME, convertProductCfgInfo2CV(info));

			List<ContentValues> values = new ArrayList<ContentValues>();
			List<ProductPropInfoModel> propList = info.mPropList;
			for (ProductPropInfoModel prop : propList) {
				values.add(convertProductPropInfo2CV(prop));
			}

			result = DBManager.bulkInsert(context, TableProductPropData.TABLE_NAME, values);
			for (ContentValues cv : values) {
				cv.clear();
			}
		}
		return result;
	}

	@Override
	public boolean deleteProductInfo(Context context) {
		// 1. delete product table;
		// 2. delete product property talbe;
		boolean result1 = DBManager.delete(context, TableProductData.TABLE_NAME, null, null) != -1;
		boolean result2 = DBManager.delete(context, TableProductPropData.TABLE_NAME, null, null) != -1;
		return result1 && result2;
	}

	@Override
	public List<SysParamInfoModel> querySysParamInfo(Context context) {
		List<SysParamInfoModel> infoList = null;
		String where = TableSyscfgData.Column.TYPE + " = ?";
		String[] args = new String[] { DataConstants.SysCfgCode.TYPE_SYSPARAM };

		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableSyscfgData.TABLE_NAME, TableSyscfgData.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<SysParamInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2SysParamInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public boolean insertSysParamInfo(Context context, List<SysParamInfoModel> infoList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (SysParamInfoModel info : infoList) {
			values.add(convertSysParamInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableSyscfgData.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public boolean deleteSysParamInfo(Context context) {
		String where = TableSyscfgData.Column.TYPE + createWhereInArgs(Arrays.asList(DataConstants.SysCfgCode.TYPE_SYSPARAM));
		return DBManager.delete(context, TableSyscfgData.TABLE_NAME, where, null) != -1;
	}

	@Override
	public List<AreaInfoModel> queryPortInfo(Context context) {
		List<AreaInfoModel> infoList = null;
		String where = TableSyscfgData.Column.TYPE + " = ?";
		String[] args = new String[] { DataConstants.SysCfgCode.TYPE_PORT_SECTION };

		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableSyscfgData.TABLE_NAME, TableSyscfgData.ALL_COLUMNS, where, args, null, null, TableSyscfgData.Column.ORDER_NO+" asc ");
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<AreaInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2PortInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public boolean insertPortInfo(Context context, List<AreaInfoModel> infoList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (AreaInfoModel info : infoList) {
			values.add(convertPortInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableSyscfgData.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public boolean deletePortInfo(Context context) {
		String where = TableSyscfgData.Column.TYPE + createWhereInArgs(Arrays.asList(DataConstants.SysCfgCode.TYPE_PORT_SECTION));
		return DBManager.delete(context, TableSyscfgData.TABLE_NAME, where, null) != -1;
	}

	@Override
	public List<AreaInfoModel> querySupportProvinceList(Context context) {
		List<AreaInfoModel> infoList = null;
		String where = TableSyscfgData.Column.TYPE + " = ?";
		String[] args = new String[] { DataConstants.SysCfgCode.TYPE_AREA_PROVINCE_CONTROL };

		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableSyscfgData.TABLE_NAME, TableSyscfgData.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<AreaInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2ProvinceInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public boolean insertSupportProvinceInfo(Context context, List<AreaInfoModel> infoList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (AreaInfoModel info : infoList) {
			values.add(convertProvinceInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableSyscfgData.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public boolean deleteSupportProvinceInfo(Context context) {
		String where = TableSyscfgData.Column.TYPE + createWhereInArgs(Arrays.asList(DataConstants.SysCfgCode.TYPE_AREA_PROVINCE_CONTROL));
		return DBManager.delete(context, TableSyscfgData.TABLE_NAME, where, null) != -1;
	}

	@Override
	public boolean insertAreaCfgInfo(Context context, List<AreaInfoModel> infoList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (AreaInfoModel info : infoList) {
			values.add(convertAreaCfgInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableAreaCfg.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public List<AreaInfoModel> queryAreaCfgInfo(Context context) {
		List<AreaInfoModel> infoList = null;
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableAreaCfg.TABLE_NAME, TableAreaCfg.ALL_COLUMNS, null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<AreaInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2AreaCfgInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public boolean deleteAllAreaCfgInfo(Context context) {
		return DBManager.delete(context, TableAreaCfg.TABLE_NAME, null, null) != -1;
	}

	@Override
	public boolean insertAreaInfo(Context context, List<AreaInfoModel> infoList) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (AreaInfoModel info : infoList) {
			values.add(convertAreaInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableAreaData.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public List<AreaInfoModel> queryAllAreaInfo(Context context) {
		List<AreaInfoModel> infoList = null;
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableAreaData.TABLE_NAME, TableAreaData.ALL_COLUMNS, null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<AreaInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2AreaInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public List<AreaInfoModel> queryParentAreaInfo(Context context, String code) {
		List<AreaInfoModel> infoList = new ArrayList<AreaInfoModel>();
		queryParentAreaInfo(context, infoList, code);
		return infoList;
	}

	private void queryParentAreaInfo(Context context, List<AreaInfoModel> list, String code) {
		String where = TableAreaData.Column.CODE + " = ?";
		String[] args = new String[] { code };
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableAreaData.TABLE_NAME, TableAreaData.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					AreaInfoModel info = convertCursor2AreaInfo(cursor);
					queryParentAreaInfo(context, list, info.pCode);
					list.add(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
	}

	@Override
	public List<AreaInfoModel> queryChildAreaInfo(Context context, String code) {
		List<AreaInfoModel> infoList = null;
		String where = TableAreaData.Column.PCODE + " = ?";
		String[] args = new String[] { code };
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableAreaData.TABLE_NAME, TableAreaData.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				infoList = new ArrayList<AreaInfoModel>();
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					infoList.add(convertCursor2AreaInfo(cursor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
		return infoList;
	}

	@Override
	public boolean deleteAllAreaInfo(Context context) {
		return DBManager.delete(context, TableAreaData.TABLE_NAME, null, null) != -1;
	}

	/**
	 * 转换Timestamp为ContentValues
	 * @param type
	 * @param timestamp
	 * @return
	 */
	private ContentValues convertTimestampInfo2CV(String type, String timestamp) {
		ContentValues values = new ContentValues();
		values.put(TableSyscfgType.Column.TYPE, type);
		values.put(TableSyscfgType.Column.TIMESTAMP, timestamp);
		return values;
	}

	/**
	 * 转换AreaInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertPortInfo2CV(AreaInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableSyscfgData.Column.TYPE, info.type);
		values.put(TableSyscfgData.Column.CODE, info.code);
		values.put(TableSyscfgData.Column.VALUE, info.name);
		values.put(TableSyscfgData.Column.PCODE, info.pCode);
		values.put(TableSyscfgData.Column.ORDER_NO, info.orderNo);
		return values;
	}

	/**
	 * 转换ContentValues为AreaInfoModel
	 * @param cursor
	 * @return
	 */
	private AreaInfoModel convertCursor2PortInfo(Cursor cursor) {
		AreaInfoModel info = new AreaInfoModel();
		info.type = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.TYPE));
		info.code = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.CODE));
		info.name = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.VALUE));
		info.pCode = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.PCODE));
		return info;
	}

	/**
	 * 转换AreaInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertProvinceInfo2CV(AreaInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableSyscfgData.Column.TYPE, info.type);
		values.put(TableSyscfgData.Column.CODE, info.code);
		values.put(TableSyscfgData.Column.VALUE, info.name);
		values.put(TableSyscfgData.Column.PCODE, info.pCode);
		return values;
	}

	/**
	 * 转换ContentValues为AreaInfoModel
	 * @param cursor
	 * @return
	 */
	private AreaInfoModel convertCursor2ProvinceInfo(Cursor cursor) {
		AreaInfoModel info = new AreaInfoModel();
		info.type = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.TYPE));
		info.code = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.CODE));
		info.name = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.VALUE));
		info.pCode = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.PCODE));
		return info;
	}

	/**
	 * 转换AreaInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertAreaCfgInfo2CV(AreaInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableAreaData.Column.AREA_ID, info.id);
		values.put(TableAreaData.Column.CODE, info.code);
		values.put(TableAreaData.Column.NAME, info.name);
		return values;
	}

	/**
	 * 转换ContentValues为AreaInfoModel
	 * @param cursor
	 * @return
	 */
	private AreaInfoModel convertCursor2AreaCfgInfo(Cursor cursor) {
		AreaInfoModel info = new AreaInfoModel();
		info.id = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.AREA_ID));
		info.code = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.CODE));
		info.name = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.NAME));
		return info;
	}

	/**
	 * 转换AreaInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertAreaInfo2CV(AreaInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableAreaData.Column.AREA_ID, info.id);
		values.put(TableAreaData.Column.CODE, info.code);
		values.put(TableAreaData.Column.NAME, info.name);
		values.put(TableAreaData.Column.PCODE, info.pCode);
		values.put(TableAreaData.Column.ORDER_NO, info.orderNo);
		return values;
	}

	/**
	 * 转换ContentValues为AreaInfoModel
	 * @param cursor
	 * @return
	 */
	private AreaInfoModel convertCursor2AreaInfo(Cursor cursor) {
		AreaInfoModel info = new AreaInfoModel();
		info.id = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.AREA_ID));
		info.code = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.CODE));
		info.name = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.NAME));
		info.pCode = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.PCODE));
		info.orderNo = cursor.getString(cursor.getColumnIndex(TableAreaData.Column.ORDER_NO));
		return info;
	}

	/**
	 * 转换SysParamInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertSysParamInfo2CV(SysParamInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableSyscfgData.Column.TYPE, info.type);
		values.put(TableSyscfgData.Column.CODE, info.code);
		values.put(TableSyscfgData.Column.VALUE, info.name);
		values.put(TableSyscfgData.Column.PCODE, info.pCode);
		return values;
	}

	/**
	 * 转换ContentValues为SysParamInfoModel
	 * @param cursor
	 * @return
	 */
	private SysParamInfoModel convertCursor2SysParamInfo(Cursor cursor) {
		SysParamInfoModel info = new SysParamInfoModel();
		info.type = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.TYPE));
		info.code = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.CODE));
		info.name = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.VALUE));
		info.pCode = cursor.getString(cursor.getColumnIndex(TableSyscfgData.Column.PCODE));
		return info;
	}

	/**
	 * 转换ProductCfgInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertProductCfgInfo2CV(ProductCfgInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableProductData.Column.TYPE_CODE, info.mTypeCode);
		values.put(TableProductData.Column.TYPE_NAME, info.mTypeName);
		values.put(TableProductData.Column.TYPE_ORDER, info.mTypeOrder);
		values.put(TableProductData.Column.CATEGORY_ID, info.mCategoryId);
		values.put(TableProductData.Column.CATEGORY_CODE, info.mCategoryCode);
		values.put(TableProductData.Column.CATEGORY_NAME, info.mCategoryName);
		values.put(TableProductData.Column.CATEGORY_ORDER, info.mCategoryOrder);
		values.put(TableProductData.Column.SUB_CATEGORY_ID, info.mSubCategoryId);
		values.put(TableProductData.Column.SUB_CATEGORY_CODE, info.mSubCategoryCode);
		values.put(TableProductData.Column.SUB_CATEGORY_NAME, info.mSubCategoryName);
		values.put(TableProductData.Column.SUB_CATEGORY_ORDER, info.mSubCategoryOrder);
		values.put(TableProductData.Column.MAX_SIZE, info.mMaxSize);
		values.put(TableProductData.Column.MIN_SIZE, info.mMinSize);
		return values;
	}

	/**
	 * 转换ProductPropInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertProductPropInfo2CV(ProductPropInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableProductPropData.Column.PROP_ID, info.mPropId);
		values.put(TableProductPropData.Column.PROP_CODE, info.mPropCode);
		values.put(TableProductPropData.Column.TYPE_CODE, info.mTypeCode);
		values.put(TableProductPropData.Column.CATEGORY_CODE, info.mCategoryCode);
		values.put(TableProductPropData.Column.SUB_CATEGORY_CODE, info.mSubCategoryCode);
		values.put(TableProductPropData.Column.MAX_SIZE, info.mMaxSize);
		values.put(TableProductPropData.Column.MIN_SIZE, info.mMinSize);
		values.put(TableProductPropData.Column.DEFAULT_SIZE, info.mDefaultSize);
		return values;
	}

	/**
	 * 转换ContentValues为ProductCfgInfo
	 * @param cursor
	 * @return
	 */
	private ProductCfgInfoModel convertCursor2ProductCfgInfo(Cursor cursor) {
		ProductCfgInfoModel info = new ProductCfgInfoModel();
		info.mTypeCode = cursor.getString(cursor.getColumnIndex(TableProductData.Column.TYPE_CODE));
		info.mTypeName = cursor.getString(cursor.getColumnIndex(TableProductData.Column.TYPE_NAME));
		info.mTypeOrder = cursor.getString(cursor.getColumnIndex(TableProductData.Column.TYPE_ORDER));
		info.mCategoryId = cursor.getString(cursor.getColumnIndex(TableProductData.Column.CATEGORY_ID));
		info.mCategoryCode = cursor.getString(cursor.getColumnIndex(TableProductData.Column.CATEGORY_CODE));
		info.mCategoryName = cursor.getString(cursor.getColumnIndex(TableProductData.Column.CATEGORY_NAME));
		info.mCategoryOrder = cursor.getString(cursor.getColumnIndex(TableProductData.Column.CATEGORY_ORDER));
		info.mSubCategoryId = cursor.getString(cursor.getColumnIndex(TableProductData.Column.SUB_CATEGORY_ID));
		info.mSubCategoryCode = cursor.getString(cursor.getColumnIndex(TableProductData.Column.SUB_CATEGORY_CODE));
		info.mSubCategoryName = cursor.getString(cursor.getColumnIndex(TableProductData.Column.SUB_CATEGORY_NAME));
		info.mSubCategoryOrder = cursor.getString(cursor.getColumnIndex(TableProductData.Column.SUB_CATEGORY_ORDER));
		info.mMaxSize = cursor.getFloat(cursor.getColumnIndex(TableProductData.Column.MAX_SIZE));
		info.mMinSize = cursor.getFloat(cursor.getColumnIndex(TableProductData.Column.MIN_SIZE));
		return info;
	}

	/**
	 * 转换ContentValues为ProductPropInfo
	 * @param cursor
	 * @return
	 */
	private ProductPropInfoModel convertCursor2ProductPropInfo(Cursor cursor) {
		ProductPropInfoModel info = new ProductPropInfoModel();
		info.mPropId = cursor.getString(cursor.getColumnIndex(TableProductPropData.Column.PROP_ID));
		info.mPropCode = cursor.getString(cursor.getColumnIndex(TableProductPropData.Column.PROP_CODE));
		info.mTypeCode = cursor.getString(cursor.getColumnIndex(TableProductPropData.Column.TYPE_CODE));
		info.mCategoryCode = cursor.getString(cursor.getColumnIndex(TableProductPropData.Column.CATEGORY_CODE));
		info.mSubCategoryCode = cursor.getString(cursor.getColumnIndex(TableProductPropData.Column.SUB_CATEGORY_CODE));
		info.mMaxSize = cursor.getFloat(cursor.getColumnIndex(TableProductPropData.Column.MAX_SIZE));
		info.mMinSize = cursor.getFloat(cursor.getColumnIndex(TableProductPropData.Column.MIN_SIZE));
		info.mDefaultSize = cursor.getFloat(cursor.getColumnIndex(TableProductPropData.Column.DEFAULT_SIZE));
		return info;
	}

	/**
	 * 构建where in语句
	 * @param argsList
	 * @return
	 */
	private String createWhereInArgs(List<String> argsList) {
		StringBuffer whereInArgs = new StringBuffer();
		if (argsList != null && argsList.size() > 0) {
			whereInArgs.append(" in (");
			for (int i = 0; i < argsList.size(); i++) {
				if (i == argsList.size() - 1) {
					whereInArgs.append("\'" + argsList.get(i) + "\'");
				} else {
					whereInArgs.append("\'" + argsList.get(i) + "\',");
				}
			}
			whereInArgs.append(")");
		}
		return whereInArgs.toString();
	}

}
