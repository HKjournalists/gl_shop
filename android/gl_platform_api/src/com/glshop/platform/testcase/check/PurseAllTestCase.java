package check;

import base.BaseTestCase;

import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.AddPayeeReq;
import com.glshop.platform.api.purse.DeletePayeeReq;
import com.glshop.platform.api.purse.GetDealInfoReq;
import com.glshop.platform.api.purse.GetDealsReq;
import com.glshop.platform.api.purse.GetPayeeListReq;
import com.glshop.platform.api.purse.GetPurseInfoReq;
import com.glshop.platform.api.purse.RollOutReq;
import com.glshop.platform.api.purse.RollOutToDepositReq;
import com.glshop.platform.api.purse.RptOfflinePayReq;
import com.glshop.platform.api.purse.RptOfflineRechargeReq;
import com.glshop.platform.api.purse.RptOnlineRechargeReq;
import com.glshop.platform.api.purse.SetDefaultPayeeReq;
import com.glshop.platform.api.purse.UpdatePayeeReq;
import com.glshop.platform.api.purse.data.model.PayInfoModel;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.api.purse.data.model.RolloutInfoModel;
import com.glshop.platform.api.util.ImageInfoUtils;

/**
 * @Description : 钱包信息测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class PurseAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		//addTestClass(GetPurseInfoReq.class, new CallBackBuilder<GetPurseInfoResult>()); // 获取我的钱包账户信息
		//addTestClass(RptOnlineRechargeReq.class, new CallBackBuilder<CommonResult>()); // 线上已充值
		//addTestClass(RptOfflineRechargeReq.class, new CallBackBuilder<CommonResult>()); // 线下已充值
		//addTestClass(RptOfflinePayReq.class, new CallBackBuilder<CommonResult>()); // 线下已付款
		//addTestClass(GetPayeeListReq.class, new CallBackBuilder<CommonResult>()); // 获取收款人列表
		//addTestClass(AddPayeeReq.class, new CallBackBuilder<CommonResult>()); // 新增收款人
		//addTestClass(UpdatePayeeReq.class, new CallBackBuilder<CommonResult>()); // 更新收款人信息
		//addTestClass(DeletePayeeReq.class, new CallBackBuilder<CommonResult>()); // 删除收款人
		//addTestClass(SetDefaultPayeeReq.class, new CallBackBuilder<CommonResult>()); // 设置默认收款人
		//addTestClass(RollOutReq.class, new CallBackBuilder<CommonResult>()); // 提现申请
		//addTestClass(RollOutToDepositReq.class, new CallBackBuilder<CommonResult>()); // 转款至保证金
		//addTestClass(GetDealsReq.class, new CallBackBuilder<GetDealsResult>()); // 获取明细列表
		//addTestClass(GetDealInfoReq.class, new CallBackBuilder<GetDealInfoResult>()); // 获取明细详情
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof GetPurseInfoReq) { // 获取我的钱包账户信息
			GetPurseInfoReq req = (GetPurseInfoReq) request;
			req.purseType = PurseType.DEPOSIT;
		} else if (request instanceof RptOnlineRechargeReq) { // 线上已充值
			RptOnlineRechargeReq req = (RptOnlineRechargeReq) request;
			PayInfoModel info = new PayInfoModel();
			info.type = PurseType.DEPOSIT;
			info.totalPrice = "3000";
			info.thirdPayId = "00000000001";
			info.userId = "CompanyInfoId000000811102014END";
			req.info = info;
		} else if (request instanceof RptOfflineRechargeReq) { // 线下已充值
			RptOfflineRechargeReq req = (RptOfflineRechargeReq) request;
			req.purseType = PurseType.DEPOSIT;
		} else if (request instanceof RptOfflinePayReq) { // 线下已付款
			RptOfflinePayReq req = (RptOfflinePayReq) request;
			req.contractId = "201410170008017";
			req.type = "0";
		} else if (request instanceof GetPayeeListReq) { // 获取收款人列表
			GetPayeeListReq req = (GetPayeeListReq) request;
			req.companyId = "CompanyInfoId000000811102014END";
		} else if (request instanceof AddPayeeReq) { // 新增收款人
			AddPayeeReq req = (AddPayeeReq) request;
			PayeeInfoModel info = new PayeeInfoModel();
			info.companyId = "CompanyInfoId000000811102014END";
			info.name = "张三2";
			info.card = "6226 0978 1111 2222";
			info.bankCode = "002";
			info.subBank = "南山支行2";
			info.certImgInfo = ImageInfoUtils.getImageInfo("86");
			req.info = info;
			req.smsCode = "0626";
		} else if (request instanceof UpdatePayeeReq) { // 更新收款人信息
			UpdatePayeeReq req = (UpdatePayeeReq) request;
			PayeeInfoModel info = new PayeeInfoModel();
			info.companyId = "CompanyInfoId000000811102014END";
			info.id = "ACCEPTBANKID201410170000008150005";
			info.name = "张三1";
			info.card = "6226 0978 1111 2223";
			info.bankCode = "001";
			info.subBank = "南山支行";
			info.certImgInfo = ImageInfoUtils.getImageInfo("86");
			req.info = info;
		} else if (request instanceof DeletePayeeReq) { // 删除收款人
			DeletePayeeReq req = (DeletePayeeReq) request;
			req.payeeId = "ACCEPTBANKID201410170000008150005";
		} else if (request instanceof SetDefaultPayeeReq) { // 设置默认收款人
			SetDefaultPayeeReq req = (SetDefaultPayeeReq) request;
			req.payeeId = "ACCEPTBANKID201410170000008150005";
		} else if (request instanceof RollOutReq) { // 提现申请
			RollOutReq req = (RollOutReq) request;
			RolloutInfoModel info = new RolloutInfoModel();
			info.type = PurseType.DEPOSIT;
			info.payeeId = "ACCEPTBANKID201410170000007143940";
			info.money = "100";
			req.info = info;
		} else if (request instanceof RollOutToDepositReq) { // 转款至保证金
			RollOutToDepositReq req = (RollOutToDepositReq) request;
			req.money = "100";
		} else if (request instanceof GetDealsReq) { // 获取明细列表
			GetDealsReq req = (GetDealsReq) request;
			req.type = PurseType.DEPOSIT;
		} else if (request instanceof GetDealInfoReq) { // 获取明细详情
			GetDealInfoReq req = (GetDealInfoReq) request;
			req.id = "PURSEPAYID2014101500001145905";
		}
	}
}
