package com.glshop.net.logic.db.dao.pay;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.glshop.net.logic.db.DBConstants.TablePay;
import com.glshop.net.logic.db.DBManager;
import com.glshop.platform.api.purse.data.model.PayInfoModel;

/**
 * @Description : 支付缓存管理业务实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public class PayDao implements IPayDao {

	private static PayDao mInstance;

	private PayDao(Context context) {

	}

	public static synchronized PayDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new PayDao(context);
		}
		return mInstance;
	}

	/**
	 * 查询所有支付记录列表
	 * @param context
	 * @param account
	 * @return
	 */
	public List<PayInfoModel> queryPayInfo(Context context, String account) {
		List<PayInfoModel> infoList = new ArrayList<PayInfoModel>();
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TablePay.TABLE_NAME, TablePay.ALL_COLUMNS, null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					PayInfoModel info = convertCursor2PayInfo(cursor);
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
		return infoList;
	}

	/**
	 * 查询所有未上报的支付记录列表
	 * @param context
	 * @param account
	 * @return
	 */
	@Override
	public List<PayInfoModel> queryUnreportedPayInfo(Context context, String account) {
		List<PayInfoModel> infoList = new ArrayList<PayInfoModel>();
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TablePay.TABLE_NAME, TablePay.ALL_COLUMNS, null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					PayInfoModel info = convertCursor2PayInfo(cursor);
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
		return infoList;
	}

	/**
	 * 添加支付记录
	 * @param context
	 * @param info
	 * @return
	 */
	public long insertPayInfo(Context context, PayInfoModel info) {
		ContentValues values = convertPayInfo2CV(info);
		long result = DBManager.insert(context, TablePay.TABLE_NAME, values);
		values.clear();
		return result;
	}

	/**
	 * 更新支付记录
	 * @param context
	 * @param info
	 * @return
	 */
	public boolean updatePayInfo(Context context, PayInfoModel info) {
		ContentValues values = convertPayInfo2CV(info);
		String where = TablePay.Column.ORDER_ID + " = ?";
		String[] args = new String[] { info.orderId };
		boolean result = DBManager.update(context, TablePay.TABLE_NAME, values, where, args) != -1;
		values.clear();
		return result;
	}

	/**
	 * 删除支付记录
	 * @param context
	 * @param id
	 * @return
	 */
	public boolean deletePayInfo(Context context, String id) {
		String where = TablePay.Column.ORDER_ID + " = ?";
		String[] args = new String[] { id };
		return DBManager.delete(context, TablePay.TABLE_NAME, where, args) != -1;
	}

	/**
	 * 转换PayInfoModel为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertPayInfo2CV(PayInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TablePay.Column.ORDER_ID, info.orderId);
		values.put(TablePay.Column.THIRD_PAY_ID, info.thirdPayId);
		values.put(TablePay.Column.USER_ID, info.userId);
		values.put(TablePay.Column.SUBJECT, info.subject);
		values.put(TablePay.Column.DESCRIPTION, info.description);
		values.put(TablePay.Column.TOTAL_PRICE, info.totalPrice);
		values.put(TablePay.Column.RESULT, info.result);
		return values;
	}

	/**
	 * 转换Cursor为PayInfoModel
	 * @param cursor
	 * @return
	 */
	private PayInfoModel convertCursor2PayInfo(Cursor cursor) {
		PayInfoModel info = new PayInfoModel();
		info.orderId = cursor.getString(cursor.getColumnIndex(TablePay.Column.ORDER_ID));
		info.thirdPayId = cursor.getString(cursor.getColumnIndex(TablePay.Column.THIRD_PAY_ID));
		info.userId = cursor.getString(cursor.getColumnIndex(TablePay.Column.USER_ID));
		info.subject = cursor.getString(cursor.getColumnIndex(TablePay.Column.SUBJECT));
		info.description = cursor.getString(cursor.getColumnIndex(TablePay.Column.DESCRIPTION));
		info.totalPrice = cursor.getString(cursor.getColumnIndex(TablePay.Column.TOTAL_PRICE));
		info.result = cursor.getString(cursor.getColumnIndex(TablePay.Column.RESULT));
		return info;
	}

}
