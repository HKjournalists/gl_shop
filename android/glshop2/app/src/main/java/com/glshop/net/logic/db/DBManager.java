package com.glshop.net.logic.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Description : 数据库管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午4:57:50
 */
public final class DBManager {

	private DBManager(Context context) {

	}

	/**
	 * 插入数据操作
	 * @param context
	 * @param table
	 * @param values
	 * @return
	 */
	public static long insert(Context context, String table, ContentValues values) {
		SQLiteDatabase database = DBHelper.getIntance(context).getWritableDatabase();
		return database.insert(table, null, values);
	}

	/**
	 * 批量插入数据操作
	 * @param context
	 * @param table
	 * @param values
	 * @return
	 */
	public static boolean bulkInsert(Context context, String table, List<ContentValues> values) {
		SQLiteDatabase database = DBHelper.getIntance(context).getWritableDatabase();
		boolean result = false;
		if (values != null && values.size() > 0) {
			// 添加事务
			database.beginTransaction();
			try {
				for (ContentValues cv : values) {
					database.insert(table, null, cv);
				}
				database.setTransactionSuccessful();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				database.endTransaction();
			}
		}
		return result;
	}

	/**
	 * 执行原生SQL操作
	 * @param context
	 * @param sql
	 */
	public static void execSQL(Context context, String sql) {
		SQLiteDatabase database = DBHelper.getIntance(context).getWritableDatabase();
		database.execSQL(sql);
	}

	/**
	 * 更新数据操作
	 * @param context
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public static int update(Context context, String table, ContentValues values, String whereClause, String[] whereArgs) {
		SQLiteDatabase database = DBHelper.getIntance(context).getWritableDatabase();
		return database.update(table, values, whereClause, whereArgs);
	}

	/**
	 * 系统默认API查询操作
	 * @param context
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public static Cursor query(Context context, String table, String[] columns, String selection, String selectionArgs[], String groupBy, String having, String orderBy) {
		SQLiteDatabase database = DBHelper.getIntance(context).getReadableDatabase();
		Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}

	/**
	 * 原生SQL语句查询操作
	 * @param context
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public static Cursor query(Context context, String sql, String[] selectionArgs) {
		SQLiteDatabase database = DBHelper.getIntance(context).getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		return cursor;
	}

	/**
	 * 删除操作
	 * @param context
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public static int delete(Context context, String table, String whereClause, String whereArgs[]) {
		SQLiteDatabase database = DBHelper.getIntance(context).getWritableDatabase();
		return database.delete(table, whereClause, whereArgs);
	}

}
