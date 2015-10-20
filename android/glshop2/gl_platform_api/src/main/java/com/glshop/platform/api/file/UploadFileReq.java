package com.glshop.platform.api.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.file.data.UploadFileResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 上传文件请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class UploadFileReq extends BaseRequest<UploadFileResult> {

	/**
	 * 本地文件路径
	 */
	public String localPath;

	public UploadFileReq(Object invoker, IReturnCallback<UploadFileResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected UploadFileResult getResultObj() {
		return new UploadFileResult();
	}

	@Override
	protected void buildParams() {
		request.setTimeout(120000); // 设置连接超时120s
		request.setReadtime(120000); // 设置读写超时120s
		request.addParam("filename", "test_auth.jpg");
		request.addFileParam("file", new File(localPath));
	}

	@Override
	protected void parseData(UploadFileResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		if (items.size() > 0) {
			ResultItem fileItem = items.get(0);
			result.cloudId = fileItem.getString("id");
			result.cloudFilePath = fileItem.getString("url");
			result.cloudFileThumbnailPath = fileItem.getString("thumbnailSmall");
		}
	}

	@Override
	protected String getTypeURL() {
		return "/file/upload";
	}

}
