package check;

import base.BaseTestCase;

import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.user.GetImgVerifyCodeReq;
import com.glshop.platform.api.user.GetSmsVerifyCodeExReq;
import com.glshop.platform.api.user.GetSmsVerifyCodeReq;
import com.glshop.platform.api.user.ModifyPwdReq;
import com.glshop.platform.api.user.RefreshTokenReq;
import com.glshop.platform.api.user.RegUserReq;
import com.glshop.platform.api.user.ResetPwdReq;
import com.glshop.platform.api.user.UserLoginReq;
import com.glshop.platform.api.user.UserLogoutReq;
import com.glshop.platform.api.user.ValidImgVerifyCodeReq;

/**
 * @Description : 用户信息测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class UserAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		addTestClass(UserLoginReq.class, new CallBackBuilder<CommonResult>()); // 用户登录
		//addTestClass(UserLogoutReq.class, new CallBackBuilder<CommonResult>()); // 用户注销
		//addTestClass(RegUserReq.class, new CallBackBuilder<CommonResult>()); // 注册用户
		//addTestClass(GetSmsVerifyCodeReq.class, new CallBackBuilder<CommonResult>()); // 获取短信验证码
		//addTestClass(GetSmsVerifyCodeExReq.class, new CallBackBuilder<CommonResult>()); // 获取短信验证码,同时验证图形验证码
		//addTestClass(GetImgVerifyCodeReq.class, new CallBackBuilder<CommonResult>()); // 获取图形验证码
		//addTestClass(ValidImgVerifyCodeReq.class, new CallBackBuilder<CommonResult>()); // 验证图形验证码
		//addTestClass(ModifyPwdReq.class, new CallBackBuilder<CommonResult>()); // 修改密码
		//addTestClass(ResetPwdReq.class, new CallBackBuilder<CommonResult>()); // 重置密码
		//addTestClass(RefreshTokenReq.class, new CallBackBuilder<CommonResult>()); // 刷新token
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof UserLoginReq) { // 用户登录
			UserLoginReq req = (UserLoginReq) request;
			req.account = "18665361276";
			req.password = "123456";
		} else if (request instanceof UserLogoutReq) { // 用户注销
			UserLogoutReq req = (UserLogoutReq) request;
			req.account = "18665361276";
		} else if (request instanceof GetSmsVerifyCodeReq) { // 获取短信验证码
			GetSmsVerifyCodeReq req = (GetSmsVerifyCodeReq) request;
			req.account = "18665361276";
		} else if (request instanceof GetSmsVerifyCodeExReq) { // 获取短信验证码,同时验证图形验证码
			GetSmsVerifyCodeExReq req = (GetSmsVerifyCodeExReq) request;
			req.deviceId = "8669456283655555";
			req.account = "18665361276";
			req.imgVerifyCode = "342276";
		} else if (request instanceof RegUserReq) { // 注册用户
			RegUserReq req = (RegUserReq) request;
			req.account = "18665361276";
			req.password = "123456";
			req.smsVerifyCode = "9592";
		} else if (request instanceof ModifyPwdReq) { // 修改密码
			ModifyPwdReq req = (ModifyPwdReq) request;
			req.account = "18665361276";
			req.newPassword = "1234567";
			req.oldPassword = "123456";
			req.smsVerifyCode = "1470";
		} else if (request instanceof ResetPwdReq) { // 重置密码
			ResetPwdReq req = (ResetPwdReq) request;
			req.account = "18665361276";
			req.newPassword = "123456";
			req.smsVerifyCode = "8706";
		} else if (request instanceof RefreshTokenReq) {

		} else if (request instanceof GetImgVerifyCodeReq) { // 获取图形验证码
			GetImgVerifyCodeReq req = (GetImgVerifyCodeReq) request;
			req.deviceId = "8669456283655555";
			req.imgCodePath = TEMP_DIR + "imgcode.jpg";
		} else if (request instanceof ValidImgVerifyCodeReq) { // 验证图形验证码
			ValidImgVerifyCodeReq req = (ValidImgVerifyCodeReq) request;
			req.deviceId = "8669456283655555";
			req.verifyCode = "342276";
		}
	}

}
