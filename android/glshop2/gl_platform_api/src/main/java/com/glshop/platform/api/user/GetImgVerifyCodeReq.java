package com.glshop.platform.api.user;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.user.data.GetImgVerifyCodeResult;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType;

/**
 * @Description : 获取图像验证码请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:13:07
 */
public class GetImgVerifyCodeReq extends BaseRequest<GetImgVerifyCodeResult> {

	/**
	 * 用户设备号
	 */
	public String deviceId;

	/**
	 * 图形验证码保存地址
	 */
	public String imgCodePath;

	public GetImgVerifyCodeReq(Object invoker, IReturnCallback<GetImgVerifyCodeResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetImgVerifyCodeResult getResultObj() {
		return new GetImgVerifyCodeResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("deviceId", deviceId);
		request.setDataType(ResponseDataType.FILE);
		request.setSavePath(imgCodePath);

		File file = new File(imgCodePath);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
	}

	@Override
	protected void parseData(GetImgVerifyCodeResult result, ResultItem item) {
		File file = new File(imgCodePath);
		if (file.isFile() && file.length() > 0) {
			// 读取本地已下载的图形验证码图片
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			result.imgVerifyCode = bitmap;
		}
	}

	@Override
	protected String getTypeURL() {
		return "/imgcode/getCode";
	}

}
