package check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.BaseTestCase;

import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.AddAddrReq;
import com.glshop.platform.api.profile.DeleteAddrReq;
import com.glshop.platform.api.profile.GetAddrInfoReq;
import com.glshop.platform.api.profile.GetAddrListReq;
import com.glshop.platform.api.profile.GetContactListReq;
import com.glshop.platform.api.profile.GetMyProfileInfoReq;
import com.glshop.platform.api.profile.SetDefaultAddrReq;
import com.glshop.platform.api.profile.SubmitAuthInfoReq;
import com.glshop.platform.api.profile.UpdateAddrReq;
import com.glshop.platform.api.profile.UpdateCompanyIntroReq;
import com.glshop.platform.api.profile.UpdateContactReq;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;

/**
 * @Description : 企业资料信息测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class ProfileAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		//addTestClass(SubmitAuthInfoReq.class, new CallBackBuilder<CommonResult>()); //提交认证申请
		//addTestClass(GetMyProfileInfoReq.class, new CallBackBuilder<GetMyProfileInfoResult>()); // 获取我的个人资料信息
		//addTestClass(UpdateContactReq.class, new CallBackBuilder<CommonResult>()); // 更新企业联系人信息
		//addTestClass(GetContactListReq.class, new CallBackBuilder<GetContactListResult>()); // 获取企业联系人列表
		//addTestClass(UpdateCompanyIntroReq.class, new CallBackBuilder<CommonResult>()); // 更新企业介绍
		//addTestClass(AddAddrReq.class, new CallBackBuilder<CommonResult>()); // 添加卸货地址
		//addTestClass(GetAddrListReq.class, new CallBackBuilder<GetAddrListResult>()); // 获取卸货地址列表
		//addTestClass(GetAddrInfoReq.class, new CallBackBuilder<GetAddrInfoResult>()); // 获取卸货地址详情
		//addTestClass(UpdateAddrReq.class, new CallBackBuilder<CommonResult>()); // 更新卸货地址
		//addTestClass(DeleteAddrReq.class, new CallBackBuilder<CommonResult>()); // 删除卸货地址
		//addTestClass(SetDefaultAddrReq.class, new CallBackBuilder<CommonResult>()); // 设置默认卸货地址
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof SubmitAuthInfoReq) {
			SubmitAuthInfoReq req = (SubmitAuthInfoReq) request;

			AuthInfoModel info = new AuthInfoModel();
			// 设置基本参数
			info.profileType = ProfileType.COMPANY;
			info.profileImgList = ImageInfoUtils.getImageInfo(Arrays.asList("14"));

			// 添加企业介绍信息
			CompanyIntroInfoModel introInfo = new CompanyIntroInfoModel();
			introInfo.introduction = "深圳长江电商有限公司，信用良好，环境优美！";
			// 添加企业介绍图片ID
			List<ImageInfoModel> imgId = new ArrayList<ImageInfoModel>();
			ImageInfoModel image1 = new ImageInfoModel();
			image1.cloudId = "14";
			imgId.add(image1);
			introInfo.imgList = imgId;

			info.introInfo = introInfo;
			req.info = info;
		} else if (request instanceof GetMyProfileInfoReq) {
			GetMyProfileInfoReq req = (GetMyProfileInfoReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		} else if (request instanceof UpdateContactReq) {
			UpdateContactReq req = (UpdateContactReq) request;
			req.companyId = "CompanyInfoId000000811102014END";

			// 添加联系人
			ArrayList<ContactInfoModel> contactList = new ArrayList<ContactInfoModel>();
			ContactInfoModel defaultContact = new ContactInfoModel();
			defaultContact.name = "张三";
			defaultContact.telephone = "18666668888";
			defaultContact.fixPhone = "0755-66668888";
			defaultContact.isDefault = true;

			ContactInfoModel contact = new ContactInfoModel();
			contact.name = "李四";
			contact.telephone = "18699999999";
			contact.fixPhone = "0755-99999999";

			contactList.add(defaultContact);
			contactList.add(contact);

			req.infoList = contactList;
		} else if (request instanceof GetContactListReq) {
			GetContactListReq req = (GetContactListReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		} else if (request instanceof UpdateCompanyIntroReq) {
			UpdateCompanyIntroReq req = (UpdateCompanyIntroReq) request;

			CompanyIntroInfoModel info = new CompanyIntroInfoModel();
			info.introduction = "深圳长江电商有限公司，信用良好，环境优美！";
			info.companyId = "CompanyInfoId000000811102014END";
			// 添加图片ID
			List<ImageInfoModel> imgId = new ArrayList<ImageInfoModel>();
			ImageInfoModel image1 = new ImageInfoModel();
			image1.cloudId = "14";
			imgId.add(image1);
			info.imgList = imgId;

			req.info = info;
		} else if (request instanceof AddAddrReq) {
			AddAddrReq req = (AddAddrReq) request;

			AddrInfoModel info = new AddrInfoModel();
			info.companyId = "CompanyInfoId000000811102014END";
			info.deliveryAddrDetail = "深圳市宝安区码头";
			info.uploadPortWaterDepth = 20.8f;
			info.uploadPortShippingWaterDepth = 20.8f;

			// 添加图片ID
			List<ImageInfoModel> imgId = new ArrayList<ImageInfoModel>();
			ImageInfoModel image1 = new ImageInfoModel();
			image1.cloudId = "49";
			ImageInfoModel image2 = new ImageInfoModel();
			image2.cloudId = "50";
			imgId.add(image1);
			imgId.add(image2);
			info.addrImageList = imgId;

			req.info = info;
		} else if (request instanceof GetAddrListReq) {
			GetAddrListReq req = (GetAddrListReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		} else if (request instanceof GetAddrInfoReq) {
			GetAddrInfoReq req = (GetAddrInfoReq) request;
			req.addrId = "10";
		} else if (request instanceof UpdateAddrReq) {
			UpdateAddrReq req = (UpdateAddrReq) request;
			AddrInfoModel info = new AddrInfoModel();
			info.addrId = "8";
			info.deliveryAddrDetail = "深圳市福田码头";
			info.uploadPortWaterDepth = 20.8f;
			info.uploadPortShippingWaterDepth = 20.8f;

			// 添加图片ID
			List<ImageInfoModel> imgId = new ArrayList<ImageInfoModel>();
			ImageInfoModel image1 = new ImageInfoModel();
			image1.cloudId = "54";
			imgId.add(image1);
			info.addrImageList = imgId;

			req.info = info;
		} else if (request instanceof DeleteAddrReq) {
			DeleteAddrReq req = (DeleteAddrReq) request;
			req.addrId = "9";
		} else if (request instanceof SetDefaultAddrReq) {
			SetDefaultAddrReq req = (SetDefaultAddrReq) request;
			req.addrId = "10";
		}
	}

}
