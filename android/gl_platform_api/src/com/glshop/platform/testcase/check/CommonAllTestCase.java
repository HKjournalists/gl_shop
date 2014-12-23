package check;

import base.BaseTestCase;

import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.file.UploadFileReq;

/**
 * @Description : 通用功能测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class CommonAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		addTestClass(UploadFileReq.class, new CallBackBuilder<CommonResult>()); // 上传文件
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof UploadFileReq) {
			UploadFileReq req = (UploadFileReq) request;
			req.localPath = TEMP_DIR + "test_img5.jpg";
		}
	}

}
