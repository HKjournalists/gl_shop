package com.glshop.platform.net.http.image;

import com.glshop.platform.net.base.IRequestCallBack;
import com.glshop.platform.net.http.HttpRequest;
import com.glshop.platform.net.http.ResponseDataType;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * FileName    : DownloadRequest.java
 * Description : 下载的统一请求
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-24 上午10:41:21
 **/
public class DownloadRequest {
	
	private HttpRequest request;
	
	public DownloadRequest(String url,String savePath,IRequestCallBack callback){
		request = new HttpRequest(callback);
		request.setUrl(url);
		request.setSavePath(savePath);
		request.setMethod(HttpMethod.GET);
		request.setDataType(ResponseDataType.FILE);
	}
	
	public void exec(){
		request.send();
	}
	
	public void cancel() {
		request.cancel();
	}
}
