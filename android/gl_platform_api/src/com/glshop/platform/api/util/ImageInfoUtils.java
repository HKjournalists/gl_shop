package com.glshop.platform.api.util;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 图片信息工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-11-10 上午11:21:49
 */
public class ImageInfoUtils {

	public static List<ImageInfoModel> getImageInfo(List<String> idList) {
		List<ImageInfoModel> imgList = new ArrayList<ImageInfoModel>();
		if (BeanUtils.isNotEmpty(idList)) {
			for (String id : idList) {
				imgList.add(getImageInfo(id));
			}
		}
		return imgList;
	}

	public static ImageInfoModel getImageInfo(String id) {
		ImageInfoModel imgInfo = new ImageInfoModel();
		imgInfo.cloudId = id;
		return imgInfo;
	}

}
