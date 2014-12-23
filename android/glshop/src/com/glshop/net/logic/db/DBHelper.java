package com.glshop.net.logic.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.glshop.platform.utils.Logger;

/**
 * @Description : 数据库Helper类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午4:57:50
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	private Context mContext;

	private static DBHelper mInstance;

	// 升级方法名称
	private static final String UPGRADE_METHOD_NAME = "upgradeVersion";

	public static synchronized DBHelper getIntance(Context context) {
		if (mInstance == null) {
			mInstance = new DBHelper(context);
		}
		return mInstance;
	}

	private DBHelper(Context context) {
		this(context, DBConstants.Common.DATABASE_NAME, null, DBConstants.Common.DATABASE_VERSION);
	}

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConstants.TableSyscfgType.CREATE_TABLE_SQL);
		db.execSQL(DBConstants.TableSyscfgData.CREATE_TABLE_SQL);
		db.execSQL(DBConstants.TableProductData.CREATE_TABLE_SQL);
		db.execSQL(DBConstants.TableProductPropData.CREATE_TABLE_SQL);
		db.execSQL(DBConstants.TableMessage.CREATE_TABLE_SQL);
		db.execSQL(DBConstants.TablePay.CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Logger.i(TAG, "DB onUpgrade: oldVersion = " + oldVersion + ", newVersion = " + newVersion);
		if (newVersion <= oldVersion) {
			Logger.i(TAG, "newVersion <= oldVersion, do not upgrade!");
			return;
		}

		// 执行oldVersion和newVersion版本之间每个版本的升级操作.
		// 方法参数类型
		Class[] parameterTypes = new Class[] { SQLiteDatabase.class };
		// 遍历中间每个版本
		for (int i = oldVersion + 1; i <= newVersion; i++) {
			Logger.i(TAG, "upgradeVersion=" + i);
			try {
				/*
				 * 数据库升级方法名称说明:
				 * upgradeVersion+版本号
				 * 如:版本3要升级数据库,则只需新增一个方法upgradeVersion3(SQLiteDatabase db,String currVersion)即可
				 */
				// 通过反射机制获得该版本对应的方法名称,规则为:upgradeVersion+版本号
				Method upgradeMethod = this.getClass().getDeclaredMethod(UPGRADE_METHOD_NAME + i, parameterTypes);
				Logger.i(TAG, "execute method=" + upgradeMethod.getName());
				// 执行该版本相关升级操作
				upgradeMethod.invoke(this, db);
			} catch (SecurityException e) {
				Logger.e(TAG, "数据库升级时发生异常!", e);
			} catch (NoSuchMethodException e) {
				Logger.e(TAG, "数据库升级时发生异常!", e);
			} catch (IllegalArgumentException e) {
				Logger.e(TAG, "数据库升级时发生异常!", e);
			} catch (IllegalAccessException e) {
				Logger.e(TAG, "数据库升级时发生异常!", e);
			} catch (InvocationTargetException e) {
				Logger.e(TAG, "数据库升级时发生异常!", e);
			}
		}
		Logger.i(TAG, "upgrade finish!");
	}

	/**
	 * 版本2数据库中表中新增了order字段，用于货物规格排序。
	 * @param db
	 */
	protected void upgradeVersion2(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableSyscfgType.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableSyscfgData.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableProductData.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableProductPropData.CREATE_TABLE_SQL);
	}

	/**
	 * 版本3数据库中表中修改TableProductData、TableProductPropData中Text数据类型为Float类型
	 * @param db
	 */
	protected void upgradeVersion3(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableSyscfgType.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableSyscfgData.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableProductData.CREATE_TABLE_SQL);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TableProductPropData.CREATE_TABLE_SQL);
	}

}
