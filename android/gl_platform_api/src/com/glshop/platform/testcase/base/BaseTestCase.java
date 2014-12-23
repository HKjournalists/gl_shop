package base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import android.os.Environment;
import android.test.AndroidTestCase;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 单元测试基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:50:07
 */
public abstract class BaseTestCase extends AndroidTestCase {

	protected String TAG = "HttpRequestTask";

	protected boolean isCallback = true;

	/** 根目录 */
	private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gl_shop";

	/** 日志路径 */
	private static final String LOG_DIR = ROOT + "/log/glshop.log";

	/** Temp目录 */
	protected static final String TEMP_DIR = ROOT + "/temp/";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		//初始化所有的参数
		PlatformConfig.init(this.getContext());
		//PlatformConfig.setValue(PlatformConfig.SERVICES_URL, "http://192.168.31.243:8080/gl_shop_http");
		PlatformConfig.setValue(PlatformConfig.SERVICES_URL, "http://192.168.1.223:8080/gl_shop_http");
		PlatformConfig.setValue(PlatformConfig.USER_TOKEN, "8de84077c71022a3d18361041a1661b6");

		// 初始化日志
		Logger.initLogger(Logger.VERBOSE, true, LOG_DIR);
	}

	protected void finish(ResponseEvent evnt, CommonResult result) {
		if (ResponseEvent.isFinish(evnt)) {
			isCallback = true;
			Logger.d(TAG, "resultObj:[" + result.toString() + "]");
		} else {
			Logger.d(TAG, evnt + " resultObj:[" + (result == null ? "" : result.toString()) + "]");
		}
	}

	protected void startWaiting() {
		isCallback = false;
		while (!isCallback) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 添加需要复制的处理
	 * @param t
	 * @param callBack
	 */
	protected void addTestClass(Class<? extends BaseRequest<?>> className, CallBackBuilder<?> callBack) {
		try {
			Constructor cons = className.getConstructor(new Class[] { Object.class, IReturnCallback.class });
			BaseRequest<?> request = (BaseRequest<?>) cons.newInstance(null, callBack.getReturnCallback());
			setValues(request);

			request.exec();
			startWaiting();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e(TAG, "addTestClass excepiton :[" + e.toString() + "]");
		}
	}

	/**
	 * 反射赋值
	 * @param request
	 */
	private void setValues(BaseRequest<?> request) {
		Field[] field = request.getClass().getFields();
		if (field != null) {
			for (Field tmpField : field) {

				try {
					if (tmpField.getType() == String.class) {
						tmpField.set(request, tmpField.getName());

					} else if (tmpField.getType() == int.class || tmpField.getType() == Integer.class) {
						tmpField.set(request, 1);

					} else if (tmpField.getType() == float.class || tmpField.getType() == Float.class) {
						tmpField.set(request, 2);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Logger.e(TAG, "addTestClass excepiton :[" + e.getMessage() + "]");
				}
			}
		}
		setRequestValues(request);
	}

	/**生成对应的回调类*/
	public class CallBackBuilder<T extends CommonResult> {
		public IReturnCallback<T> getReturnCallback() {
			return new IReturnCallback<T>() {

				@Override
				public void onReturn(Object invoker, ResponseEvent event, T result) {
					finish(event, (CommonResult) result);
				}
			};
		}
	}

	/**重新赋值*/
	protected void setRequestValues(BaseRequest<?> request) {
	}

	public abstract void testExec();

}
