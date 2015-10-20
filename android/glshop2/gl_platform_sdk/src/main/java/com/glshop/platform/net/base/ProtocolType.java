package com.glshop.platform.net.base;

/**
 * FileName    : ProtocolType.java
 * Description : 网络请求的协议类型
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:28:33
 **/
public enum ProtocolType {
	/**http 和 https的网络协议*/
	HTTP,
	/**webServic协议*/
	WEBSERVICES,
	/**socket协议*/
	SOCKET;

	/**请求响应的事件*/
	public enum ResponseEvent {
		/**任务开始*/
		START,
		/**任务上报进度*/
		PROGRESS,
		/**任务完成*/
		SUCCESS,
		/**任务出错*/
		ERROR,
		/**请求取消*/
		CANCEL;
		
		public static boolean isFinish(ResponseEvent event){
			return event == ResponseEvent.SUCCESS || event == ResponseEvent.ERROR|| event == ResponseEvent.CANCEL;
		}
	}
}
