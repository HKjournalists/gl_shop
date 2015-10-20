package com.glshop.platform.base.manager;

import android.app.Application;

import com.glshop.platform.base.config.PlatformConfig;

/**
 * FileName    : CCaApplication.java
 * Description : application处理
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-10 下午10:40:48
 **/
public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		//初始化所有的参数
		PlatformConfig.init(this.getApplicationContext());
		//局域网
		PlatformConfig.setValue(PlatformConfig.SERVICES_URL, "http://192.168.1.16:8080/");
		PlatformConfig.setValue(PlatformConfig.SERVICES_URL_IMAGE, "http://192.168.1.64:8080/");
		//外网 
//		PlatformConfig.setValue(PlatformConfig.SERVICES_URL, "http://192.168.1.16:8080/");
//		PlatformConfig.setValue(PlatformConfig.SERVICES_URL_IMAGE, "http://192.168.1.64:8080/");
		// 全局的异常处理
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
