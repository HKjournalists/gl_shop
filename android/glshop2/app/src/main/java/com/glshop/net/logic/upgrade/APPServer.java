package com.glshop.net.logic.upgrade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.glshop.platform.utils.Logger;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : 监听app安装/卸载
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/9/21  14:35
 */
public class APPServer extends BroadcastReceiver {
    private static final String TAG = "APPServer";

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Logger.d(TAG, "安装了:" + packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Logger.d(TAG, "卸载了:" + packageName + "包名的程序");
        }
    }

}
