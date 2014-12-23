package check;

import base.BaseTestCase;

import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.AgreeCancelContractReq;
import com.glshop.platform.api.contract.AgreeUncfmContractReq;
import com.glshop.platform.api.contract.CancelContractReq;
import com.glshop.platform.api.contract.ConfirmDischargeReq;
import com.glshop.platform.api.contract.ConfirmReceiptReq;
import com.glshop.platform.api.contract.ContactCustomServiceReq;
import com.glshop.platform.api.contract.ContractAcceptanceReq;
import com.glshop.platform.api.contract.ContractNegotiateReq;
import com.glshop.platform.api.contract.ContractOnlinePayReq;
import com.glshop.platform.api.contract.EvaluateContractReq;
import com.glshop.platform.api.contract.GetCompanyEvaListReq;
import com.glshop.platform.api.contract.GetContractEvaListReq;
import com.glshop.platform.api.contract.GetContractInfoReq;
import com.glshop.platform.api.contract.GetContractModelReq;
import com.glshop.platform.api.contract.GetContractsReq;
import com.glshop.platform.api.contract.GetOprHistoryReq;
import com.glshop.platform.api.contract.GetToPayContractsReq;
import com.glshop.platform.api.contract.data.GetContractInfoResult;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;

/**
 * @Description : 合同信息测试用例
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午10:31:26
 */
public class ContractAllTestCase extends BaseTestCase {

	@Override
	public void testExec() {
		//addTestClass(GetContractsReq.class, new CallBackBuilder<GetContractsResult>()); // 获取我的合同列表
		//addTestClass(GetContractModelReq.class, new CallBackBuilder<GetContractModelResult>()); // 获取待确认的合同详情
		//addTestClass(AgreeUncfmContractReq.class, new CallBackBuilder<CommonResult>()); // 待确认合同的确认
		//addTestClass(GetContractInfoReq.class, new CallBackBuilder<GetContractInfoResult>()); // 合同详情
		//addTestClass(ContractPayReq.class, new CallBackBuilder<CommonResult>()); // 合同付款
		//addTestClass(ContractAcceptanceReq.class, new CallBackBuilder<CommonResult>()); // 议价或验收通过
		//addTestClass(ContractNegotiateReq.class, new CallBackBuilder<CommonResult>()); // 提交议价
		//addTestClass(CancelContractReq.class, new CallBackBuilder<CommonResult>()); // 单方强制取消合同
		//addTestClass(AgreeCancelContractReq.class, new CallBackBuilder<CommonResult>()); // 同意协商取消合同
		//addTestClass(ContactCustomServiceReq.class, new CallBackBuilder<CommonResult>()); // 联系客服
		//addTestClass(ConfirmDischargeReq.class, new CallBackBuilder<CommonResult>()); // 确认卸货
		//addTestClass(ConfirmReceiptReq.class, new CallBackBuilder<CommonResult>()); // 确认收货
		//addTestClass(EvaluateContractReq.class, new CallBackBuilder<CommonResult>()); // 合同评价
		//addTestClass(GetContractEvaListReq.class, new CallBackBuilder<CommonResult>()); // 合同评价列表
		//addTestClass(GetCompanyEvaListReq.class, new CallBackBuilder<CommonResult>()); // 企业评价列表
		//addTestClass(GetOprHistoryReq.class, new CallBackBuilder<CommonResult>()); // 合同操作记录
		//addTestClass(GetToPayContractsReq.class, new CallBackBuilder<GetToPayContractsResult>()); // 待付款的合同列表
	}

	@Override
	protected void setRequestValues(BaseRequest<?> request) {
		super.setRequestValues(request);
		if (request instanceof GetContractsReq) { // 获取我的合同列表
			GetContractsReq req = (GetContractsReq) request;
			req.contractType = ContractType.UNCONFIRMED;
			req.pageIndex = 0;
			req.pageSize = 10;
		} else if (request instanceof GetContractModelReq) { // 获取待确认的合同详情
			GetContractModelReq req = (GetContractModelReq) request;
			req.contractId = "201410170008117";
		} else if (request instanceof AgreeUncfmContractReq) { // 待确认合同的确认
			AgreeUncfmContractReq req = (AgreeUncfmContractReq) request;
			req.contractId = "201410170008017";
		} else if (request instanceof GetContractInfoReq) { // 合同详情
			GetContractInfoReq req = (GetContractInfoReq) request;
			req.contractId = "201410200008220";
		} else if (request instanceof ContractOnlinePayReq) { // 合同付款
			ContractOnlinePayReq req = (ContractOnlinePayReq) request;
			req.contractId = "201410170008017";
			req.smsVerifyCode = "3705";
			req.password = "123456";
		} else if (request instanceof ContractAcceptanceReq) { // 议价或验收通过
			ContractAcceptanceReq req = (ContractAcceptanceReq) request;
			req.contractId = "201410200008220";
			req.type = 7;
			req.pId = "201410200008220";
		} else if (request instanceof ContractNegotiateReq) { // 提交议价
			ContractNegotiateReq req = (ContractNegotiateReq) request;
			NegotiateInfoModel info = new NegotiateInfoModel();
			info.contractId = "201410200008220";
			info.pid = "201410200008120";
			info.negUnitPrice = 43f;
			//info.negotiateAmount = 300f;
			info.reason = "全量验货议价原因2";
			info.remarks = "全量验货议价备注2";
			req.info = info;
		} else if (request instanceof CancelContractReq) { // 单方强制取消合同
			CancelContractReq req = (CancelContractReq) request;
			req.contractId = "201410170008117";
		} else if (request instanceof AgreeCancelContractReq) { // 同意协商取消合同
			AgreeCancelContractReq req = (AgreeCancelContractReq) request;
			req.contractId = "201410170008117";
		} else if (request instanceof ContactCustomServiceReq) { // 联系客服
			ContactCustomServiceReq req = (ContactCustomServiceReq) request;
			req.contractId = "201410160007816";
		} else if (request instanceof ConfirmDischargeReq) { // 确认卸货
			ConfirmDischargeReq req = (ConfirmDischargeReq) request;
			req.contractId = "201410170008017";
		} else if (request instanceof ConfirmReceiptReq) { // 确认收货
			ConfirmReceiptReq req = (ConfirmReceiptReq) request;
			req.contractId = "201410170008017";
		} else if (request instanceof EvaluateContractReq) { // 合同评价
			EvaluateContractReq req = (EvaluateContractReq) request;
			EvaluationInfoModel info = new EvaluationInfoModel();
			info.contractId = "201410170008017";
			info.evaluaterCID = "CompanyInfoId000000811102014END";
			info.satisfactionPer = 4;
			info.sincerityPer = 4;
			info.content = "Very Good!!!";
			req.info = info;
		} else if (request instanceof GetContractEvaListReq) { // 合同评价列表
			GetContractEvaListReq req = (GetContractEvaListReq) request;
			req.contractId = "201410170008017";
		} else if (request instanceof GetCompanyEvaListReq) { // 企业评价列表
			GetCompanyEvaListReq req = (GetCompanyEvaListReq) request;
			req.companyId = "000000915102014";
		} else if (request instanceof GetOprHistoryReq) { // 合同操作记录
			GetOprHistoryReq req = (GetOprHistoryReq) request;
			req.contractId = "201410160007916";
		} else if (request instanceof GetToPayContractsReq) { // 待付款的合同列表
			//GetToPayContractsReq req = (GetToPayContractsReq) request;
		}
	}
}
