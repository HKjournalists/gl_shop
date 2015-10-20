package com.glshop.platform.base.manager;

import java.util.List;
import java.util.Vector;

import android.os.Handler;
import android.os.Message;

/**
 * FileName    : MessageCenter.java
 * Description : 消息中心
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午11:31:15
 **/
public class MessageCenter {

	/** Hanlder队列，handler */
	private List<Handler> mHandlerList = new Vector<Handler>();
	private static MessageCenter CENTER = null;

	private MessageCenter() {

	}

	public static MessageCenter getInstance() {
		if (CENTER == null) {
			CENTER = new MessageCenter();
		}
		return CENTER;
	}

	public void addHandler(Handler handler) {
		mHandlerList.add(handler);
	}

	public void removeHandler(Handler handler) {
		mHandlerList.remove(handler);
	}
	
	public void sendMessageDelay(Message message,long delaytime) {
		for (Handler handler : mHandlerList) {
			handler.sendMessageDelayed(Message.obtain(message),delaytime);
		}
	}

	public void sendMessage(int what, Object obj) {
		Message message = new Message();
		message.obj = obj;
		message.what = what;
		sendMessage(message);
	}

	public void sendMessage(Message message) {
		for (Handler handler : mHandlerList) {
			handler.sendMessage(Message.obtain(message));
		}
	}

	public void sendEmptyMesage(int what) {
		for (Handler handler : mHandlerList) {
			handler.sendEmptyMessage(what);
		}
	}
	
	public void sendEmptyMesageDelay(int what,long delaytime) {
		for (Handler handler : mHandlerList) {
			handler.sendEmptyMessageDelayed(what,delaytime);
		}
	}
}
