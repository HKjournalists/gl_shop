package com.glshop.net.logic.db;

import android.provider.BaseColumns;

/**
 * @Description : 数据库常量
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class DBConstants {

	public interface Common {

		public static final String DATABASE_NAME = "gl_shop.db";

		public static final int DATABASE_VERSION = 3;

	}

	/**
	 * 系统参数类型表
	 */
	public interface TableSyscfgType {

		public static final String TABLE_NAME = "table_syscfg_type";

		public interface Column extends BaseColumns {

			public static final String TYPE = "type";

			public static final String TIMESTAMP = "timestamp";

		}

		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.TYPE + " text," 
				+ Column.TIMESTAMP + " text)";

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.TYPE, 
			Column.TIMESTAMP };

	}
	
	/**
	 * 系统参数数据表
	 */
	public interface TableSyscfgData {

		public static final String TABLE_NAME = "table_syscfg_data";

		public interface Column extends BaseColumns {

			public static final String TYPE = "type";

			public static final String CODE = "code";
			
			public static final String VALUE = "value";
			
			public static final String PCODE = "pcode";
			
			public static final String ORDER_NO = "order_no";

		}

		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.TYPE + " text," 
				+ Column.CODE + " text," 
				+ Column.VALUE + " text," 
				+ Column.PCODE + " text,"
				+ Column.ORDER_NO + " text)"; 

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.TYPE, 
			Column.CODE, 
			Column.VALUE, 
			Column.PCODE,
			Column.ORDER_NO }; 

	}
	
	/**
	 * 系统货物数据表
	 */
	public interface TableProductData {

		public static final String TABLE_NAME = "table_product_data";

		public interface Column extends BaseColumns {

			public static final String TYPE_CODE = "type_code";
			
			public static final String TYPE_NAME = "type_name";
			
			public static final String TYPE_ORDER = "type_order";
			
			public static final String CATEGORY_ID = "category_id";
			
			public static final String CATEGORY_CODE = "category_code";
			
			public static final String CATEGORY_NAME = "category_name";
			
			public static final String CATEGORY_ORDER = "category_order";
			
			public static final String SUB_CATEGORY_ID = "sub_category_id";
			
			public static final String SUB_CATEGORY_CODE = "sub_category_code";
			
			public static final String SUB_CATEGORY_NAME = "sub_category_name";
			
			public static final String SUB_CATEGORY_ORDER = "sub_category_order";
			
			public static final String MAX_SIZE = "max_size";
			
			public static final String MIN_SIZE = "min_size";

		}

		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.TYPE_CODE + " text," 
				+ Column.TYPE_NAME + " text," 
				+ Column.TYPE_ORDER + " text," 
				+ Column.CATEGORY_ID + " text," 
				+ Column.CATEGORY_CODE + " text," 
				+ Column.CATEGORY_NAME + " text," 
				+ Column.CATEGORY_ORDER + " text," 
				+ Column.SUB_CATEGORY_ID + " text," 
				+ Column.SUB_CATEGORY_CODE + " text," 
				+ Column.SUB_CATEGORY_NAME + " text," 
				+ Column.SUB_CATEGORY_ORDER + " text," 
				+ Column.MAX_SIZE + " float," 
				+ Column.MIN_SIZE + " float)";

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.TYPE_CODE, 
			Column.TYPE_NAME, 
			Column.TYPE_ORDER, 
			Column.CATEGORY_ID, 
			Column.CATEGORY_CODE, 
			Column.CATEGORY_NAME, 
			Column.CATEGORY_ORDER, 
			Column.SUB_CATEGORY_ID, 
			Column.SUB_CATEGORY_CODE, 
			Column.SUB_CATEGORY_NAME, 
			Column.SUB_CATEGORY_ORDER, 
			Column.MAX_SIZE, 
			Column.MIN_SIZE };

	}
	
	/**
	 * 系统货物属性数据表
	 */
	public interface TableProductPropData {

		public static final String TABLE_NAME = "table_product_prop_data";

		public interface Column extends BaseColumns {

			public static final String PROP_ID = "prop_id";
			
			public static final String PROP_CODE = "prop_code";
			
			public static final String TYPE_CODE = "type_code";
			
			public static final String CATEGORY_CODE = "category_code";
			
			public static final String SUB_CATEGORY_CODE = "sub_category_code";
			
			public static final String MAX_SIZE = "max_size";
			
			public static final String MIN_SIZE = "min_size";
			
			public static final String DEFAULT_SIZE = "default_size";

		}

		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.PROP_ID + " text," 
				+ Column.PROP_CODE + " text," 
				+ Column.TYPE_CODE + " text," 
				+ Column.CATEGORY_CODE + " text," 
				+ Column.SUB_CATEGORY_CODE + " text," 
				+ Column.MAX_SIZE + " float," 
				+ Column.MIN_SIZE + " float," 
				+ Column.DEFAULT_SIZE + " float)";

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.PROP_ID, 
			Column.PROP_CODE, 
			Column.TYPE_CODE, 
			Column.CATEGORY_CODE, 
			Column.SUB_CATEGORY_CODE, 
			Column.MAX_SIZE, 
			Column.MIN_SIZE, 
			Column.DEFAULT_SIZE };

	}
	
	/**
	 * 支付结果缓存表
	 */
	public interface TablePay {

		public static final String TABLE_NAME = "table_pay";

		public interface Column extends BaseColumns {

			public static final String ACCOUNT = "account";
			
			public static final String ORDER_ID = "order_id";
			
			public static final String THIRD_PAY_ID = "third_pay_id";
			
			public static final String USER_ID = "user_id";
			
			public static final String SUBJECT = "subject";
			
			public static final String DESCRIPTION = "description";
			
			public static final String TOTAL_PRICE = "total_price";

			public static final String RESULT = "result";

		}

		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.ACCOUNT + " text," 
				+ Column.ORDER_ID + " text," 
				+ Column.THIRD_PAY_ID + " text," 
				+ Column.USER_ID + " text," 
				+ Column.SUBJECT + " text,"
				+ Column.DESCRIPTION + " text,"
				+ Column.TOTAL_PRICE + " text," 
				+ Column.RESULT + " text)";

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.ACCOUNT, 
			Column.ORDER_ID, 
			Column.THIRD_PAY_ID, 
			Column.USER_ID, 
			Column.SUBJECT, 
			Column.DESCRIPTION, 
			Column.TOTAL_PRICE, 
			Column.RESULT };

	}
	
	/**
	 * 消息盒子表
	 */
	public interface TableMessage {

		public static final String TABLE_NAME = "table_message";

		public interface Column extends BaseColumns {

			public static final String ACCOUNT = "account";
			
			public static final String USER_ID = "user_id";
			
			public static final String MESSAGE_ID = "message_id";
			
			public static final String TYPE = "type";
			
			public static final String CONTENT = "content";
			
			public static final String DATETIME = "dateTime";
			
			public static final String ISREADED = "isReaded";
			
			public static final String ISREPORTED = "isReported";

		}
		
		public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" 
				+ Column._ID + " INTEGER primary key autoincrement," 
				+ Column.ACCOUNT + " text," 
				+ Column.USER_ID + " text," 
				+ Column.MESSAGE_ID + " text," 
				+ Column.TYPE + " integer,"
				+ Column.CONTENT + " text," 
				+ Column.DATETIME + " text," 
				+ Column.ISREADED + " integer,"
				+ Column.ISREPORTED + " integer)";

		public static final String[] ALL_COLUMNS = new String[] { 
			Column._ID, 
			Column.ACCOUNT, 
			Column.USER_ID, 
			Column.MESSAGE_ID, 
			Column.TYPE, 
			Column.CONTENT, 
			Column.DATETIME, 
			Column.ISREADED, 
			Column.ISREPORTED };
		
	}

}
