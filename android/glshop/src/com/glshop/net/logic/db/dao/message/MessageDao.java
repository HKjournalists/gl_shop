package com.glshop.net.logic.db.dao.message;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.glshop.net.logic.db.DBConstants.TableMessage;
import com.glshop.net.logic.db.DBManager;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.SystemMsgType;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 消息中心管理业务实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:02:02
 */
public class MessageDao implements IMessageDao {

	private static MessageDao mInstance;

	private MessageDao(Context context) {

	}

	public static synchronized MessageDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new MessageDao(context);
		}
		return mInstance;
	}

	@Override
	public int queryUnreadedNumber(Context context, String account) {
		Cursor cursor = null;
		int result = 0;
		try {
			String sql = "select count(*) from " + TableMessage.TABLE_NAME + " where " + TableMessage.Column.ACCOUNT + " = ? AND " + TableMessage.Column.ISREADED + " = ?";
			String[] args = new String[] { account, String.valueOf(DataConstants.MessageStatus.UNREADED.toValue()) };
			cursor = DBManager.query(context, sql, args);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					result = cursor.getInt(0);
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
		return result;
	}

	@Override
	public List<MessageInfoModel> queryUnreportedMessageList(Context context, String account) {
		List<MessageInfoModel> infoList = new ArrayList<MessageInfoModel>();
		String where = TableMessage.Column.ACCOUNT + " = ? AND " + TableMessage.Column.ISREPORTED + " = ?";
		String[] args = new String[] { account, "1" };
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableMessage.TABLE_NAME, TableMessage.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					MessageInfoModel info = convertCursor2MessageInfo(cursor);
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

	@Override
	public List<MessageInfoModel> queryMessageInfo(Context context, String account) {
		List<MessageInfoModel> infoList = new ArrayList<MessageInfoModel>();
		String where = TableMessage.Column.ACCOUNT + " = ?";
		String[] args = new String[] { account };
		Cursor cursor = null;
		try {
			cursor = DBManager.query(context, TableMessage.TABLE_NAME, TableMessage.ALL_COLUMNS, where, args, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					MessageInfoModel info = convertCursor2MessageInfo(cursor);
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

	@Override
	public long insertMessageInfo(Context context, MessageInfoModel info) {
		ContentValues values = convertMessageInfo2CV(info);
		long result = DBManager.insert(context, TableMessage.TABLE_NAME, values);
		values.clear();
		return result;
	}

	@Override
	public boolean insertMessageInfo(Context context, List<MessageInfoModel> list) {
		List<ContentValues> values = new ArrayList<ContentValues>();
		for (MessageInfoModel info : list) {
			values.add(convertMessageInfo2CV(info));
		}
		boolean result = DBManager.bulkInsert(context, TableMessage.TABLE_NAME, values);
		for (ContentValues cv : values) {
			cv.clear();
		}
		return result;
	}

	@Override
	public boolean updateMessageInfo(Context context, MessageInfoModel info) {
		ContentValues values = convertMessageInfo2CV(info);
		String where = TableMessage.Column.MESSAGE_ID + " = ?";
		String[] args = new String[] { info.id };
		boolean result = DBManager.update(context, TableMessage.TABLE_NAME, values, where, args) != -1;
		values.clear();
		return result;
	}

	@Override
	public boolean updateMessageInfo(Context context, List<MessageInfoModel> list) {
		boolean result = true;
		if (BeanUtils.isNotEmpty(list)) {
			for (MessageInfoModel info : list) {
				boolean subResult = updateMessageInfo(context, info);
				if (!subResult) {
					result = false;
				}
			}
		}
		return result;
	}

	@Override
	public boolean deleteMessageInfo(Context context, String id) {
		String where = TableMessage.Column.MESSAGE_ID + " = ?";
		String[] args = new String[] { id };
		return DBManager.delete(context, TableMessage.TABLE_NAME, where, args) != -1;
	}

	/**
	 * 转换MessageInfo为ContentValues
	 * @param info
	 * @return
	 */
	private ContentValues convertMessageInfo2CV(MessageInfoModel info) {
		ContentValues values = new ContentValues();
		values.put(TableMessage.Column.ACCOUNT, info.account);
		values.put(TableMessage.Column.MESSAGE_ID, info.id);
		values.put(TableMessage.Column.USER_ID, info.userID);
		values.put(TableMessage.Column.TYPE, info.type.toValue());
		values.put(TableMessage.Column.CONTENT, info.content);
		values.put(TableMessage.Column.DATETIME, info.dateTime);
		values.put(TableMessage.Column.ISREADED, info.status.toValue());
		values.put(TableMessage.Column.ISREPORTED, info.isReported ? 1 : 0);
		return values;
	}

	/**
	 * 转换Cursor为MessageInfo
	 * @param cursor
	 * @return
	 */
	private MessageInfoModel convertCursor2MessageInfo(Cursor cursor) {
		MessageInfoModel info = new MessageInfoModel();
		info.account = cursor.getString(cursor.getColumnIndex(TableMessage.Column.ACCOUNT));
		info.id = cursor.getString(cursor.getColumnIndex(TableMessage.Column.MESSAGE_ID));
		info.userID = cursor.getString(cursor.getColumnIndex(TableMessage.Column.USER_ID));
		info.type = SystemMsgType.convert(cursor.getInt(cursor.getColumnIndex(TableMessage.Column.TYPE)));
		info.content = cursor.getString(cursor.getColumnIndex(TableMessage.Column.CONTENT));
		info.dateTime = cursor.getString(cursor.getColumnIndex(TableMessage.Column.DATETIME));
		info.status = DataConstants.MessageStatus.convert(cursor.getInt(cursor.getColumnIndex(TableMessage.Column.ISREADED)));
		info.isReported = cursor.getInt(cursor.getColumnIndex(TableMessage.Column.ISREADED)) == 1;
		return info;
	}

}
