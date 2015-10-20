package com.glshop.platform.api.base;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.GlobalErrorCode;
import com.glshop.platform.api.DataConstants.GlobalMessageType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.base.ErrorItem;
import com.glshop.platform.net.base.IRequestCallBack;
import com.glshop.platform.net.base.IResponseItem;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.HttpRequest;
import com.glshop.platform.net.http.ResponseDataType;
import com.glshop.platform.net.http.ResponseDataType.RequestType;
import com.glshop.platform.utils.Logger;

/**
 * FileName    : BaseRequest.java
 * Description : 业务请求的封装
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-14 下午2:42:42
 **/
public abstract class BaseRequest<T extends CommonResult> {

	/**日志的TAG*/
	protected String TAG = this.getClass().getSimpleName();
	/**应用传进来的callback*/
	protected IReturnCallback<T> callback;
	/**请求实体*/
	protected HttpRequest request;
	/**请求对象标识*/
	protected Object invoker;

	public BaseRequest(Object invoker, IReturnCallback<T> callBackx) {
		this.invoker = invoker;
		this.callback = callBackx;
	}

	/**
	 * 回调的统一处理
	 * @param event
	 * @param response
	 */
	private void doCallBack(ResponseEvent event, IResponseItem response) {
		try {
			T result = null;
			//只有在返回成功才进行数据转换
			if (event != ResponseEvent.START) {
				result = getResultObj();
				//上传或者下载的任务生成进度信息
				if (RequestType.DOWNLOAD == request.getRequestType() || RequestType.UPLOAD == request.getRequestType()) {
					result.progress = new ProgressInfo();
				}
			}
			//事件处理
			if (event == ResponseEvent.SUCCESS) {
				parseSuccess(result, response);
			} else if (event == ResponseEvent.ERROR || event == ResponseEvent.CANCEL) {
				parseError(result, response);
			} else if (event == ResponseEvent.PROGRESS) {
				if (result.progress != null) {
					parseProgress(result, response);
				} else {
					Logger.d(TAG, "no progress no docallBack:" + result);
					return;
				}
			}
			this.callback.onReturn(invoker, event, result);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e(TAG, "doCallBack Exception:" + e.toString());
		}
	}

	/**
	 * 获取成功
	 * @param result
	 * @param response
	 */
	protected void parseSuccess(T result, IResponseItem response) {
		if (response != null) {
			if (request.getDataType() == ResponseDataType.FILE) {
				// 返回数据类型为文件流，则不解析response数据
				result.retcode = DataConstants.MSG_OK;
				parseData(result, null);
			} else {
				ResultItem item = response.getResultItem(ResultItem.class);
				if (item != null) {
					result.retcode = item.getInt("retcode", DataConstants.MSG_OK);
					result.msg = item.getString("message");
					result.datetime = item.getLong("datetime");
					result.error = response.getErrorItem();

					if (item.getBoolean("RESULT")) {
						// API业务操作成功，解析其他数据
						parseData(result, item);
					} else {
						// API业务操作失败，返回操作码
						String errorCode = item.getString("ERRORCODE");
						String errorMsg = item.getString("MESSAGE");
						result.error = new ErrorItem(errorCode, errorMsg);
						result.retcode = 0;
						handleGlobalError(errorCode);
					}
				}
			}
		}
	}

	/**
	 * 统一处理全局错误码
	 */
	private void handleGlobalError(String errorCode) {
		if (errorCode != null) {
			if (errorCode.equals(GlobalErrorCode.USER_NOT_LOGIN)) {
				MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.MSG_USER_NOT_LOGINED);
			} else if (errorCode.equals(GlobalErrorCode.USER_TOKEN_EXPIRE)) {
				MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.MSG_USER_TOKEN_EXPIRE);
			}
		}
	}

	/***
	 * 获取error
	 * @param result
	 * @param response
	 */
	protected void parseError(T result, IResponseItem response) {
		if (response != null) {
			result.error = response.getErrorItem();
		}
	}

	/**
	 * 实现进度
	 * @param result
	 * @param response
	 */
	protected void parseProgress(T result, IResponseItem response) {
		result.progress.totalSize = response.getTotalSize();
		result.progress.completeSize = response.getCompleteSize();
	}

	/**
	 * 请求执行方法
	 */
	public final void exec() {
		//重试执行重新new处理
		this.request = new HttpRequest(new IRequestCallBack() {

			@Override
			public void onResponseEvent(ResponseEvent event, IResponseItem response) {
				doCallBack(event, response);
			}
		});
		request.setEncode("UTF-8");
		//请求的基本地址
		request.setUrl(PlatformConfig.getString(PlatformConfig.SERVICES_URL) + getTypeURL());
		/*TelephonyManager tm = (TelephonyManager) PlatformConfig.getContext().getSystemService(PlatformConfig.getContext().TELEPHONY_SERVICE);
		request.addHttpHeader("MAC", BeanUtils.getMAC());
		request.addHttpHeader("IMEI", tm.getDeviceId());*/
		request.addHttpHeader("VERSION", "1.1.3");
		//绑定参数
		buildParams();
		//触发请求
		request.send();
	}

	/**
	 * 取消执行
	 */
	public final void cancel() {
		if (request != null) {
			request.cancel();
		}
	}

	/**
	 * 生成返回的对象
	 * @return
	 */
	protected abstract T getResultObj();

	/**
	 * 创建参数
	 */
	protected abstract void buildParams();

	/**
	 * 转换获取成功后具体的业务数据
	 * @param response
	 * @return
	 */
	protected abstract void parseData(T result, ResultItem item);

	/**
	 * 不同的业务类型对应不同的请求地址
	 * */
	protected abstract String getTypeURL();

}
