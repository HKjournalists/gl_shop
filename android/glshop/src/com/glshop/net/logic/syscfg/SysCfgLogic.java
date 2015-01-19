package com.glshop.net.logic.syscfg;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalMessageType.SysCfgMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.syscfg.mgr.SysCfgLocalMgr;
import com.glshop.net.logic.syscfg.mgr.SysCfgMgr;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.syscfg.GetSyscfgInfoReq;
import com.glshop.platform.api.syscfg.data.GetSyscfgInfoResult;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.api.syscfg.data.model.BankInfoModel;
import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.api.syscfg.data.model.SysParamInfoModel;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 系统参数业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午3:40:32
 */
public class SysCfgLogic extends BasicLogic implements ISysCfgLogic {

	public SysCfgLogic(Context context) {
		super(context);
	}

	@Override
	public List<ProductCfgInfoModel> getLocalProductList() {
		return SysCfgMgr.getIntance(mcontext).loadProductList();
	}

	@Override
	public List<ProductCfgInfoModel> getLocalProductCategoryList() {
		return SysCfgMgr.getIntance(mcontext).getProductCategoryList();
	}

	@Override
	public List<AreaInfoModel> getLocalPortList() {
		return SysCfgMgr.getIntance(mcontext).loadPortList();
	}

	@Override
	public List<AreaInfoModel> getLocalAreaList() {
		return SysCfgMgr.getIntance(mcontext).loadAreaList();
	}

	@Override
	public List<BankInfoModel> getLocalBankList() {
		return SysCfgMgr.getIntance(mcontext).loadBankList();
	}

	@Override
	public List<SysParamInfoModel> getLocalSysParam() {
		return SysCfgMgr.getIntance(mcontext).loadSysParamList();
	}

	@Override
	public void syncSysCfgInfo() {
		syncSysCfgInfo(SysCfgMgr.getIntance(mcontext).getDefaultSyncTypeList());
	}

	@Override
	public void syncSysCfgInfo(List<String> typeList) {
		Map<String, String> typeTimestamp = SysCfgMgr.getIntance(mcontext).loadSyncTimestamp(typeList);
		GetSyscfgInfoReq req = new GetSyscfgInfoReq(this, new IReturnCallback<GetSyscfgInfoResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetSyscfgInfoResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetSyscfgInfoResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						message.what = SysCfgMessageType.MSG_GET_SYSCFG_INFO_SUCCESS;
						//respInfo.data = result.data;
						//保存同步参数信息
						SysCfgMgr.getIntance(mcontext).saveSysCfgInfo(result.sysCfgTimestamp, result.sysSyncInfo);
					} else {
						message.what = SysCfgMessageType.MSG_GET_SYSCFG_INFO_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.sysCfgTimestamp = typeTimestamp;
		req.exec();
	}

	@Override
	public void importAreaCfgInfo() {
		SysCfgLocalMgr.getIntance(mcontext).importLocalAreaCfg();
	}

	@Override
	public List<AreaInfoModel> getParentAreaInfo(String areaCode) {
		return SysCfgMgr.getIntance(mcontext).loadParentAreaList(areaCode);
	}

	@Override
	public List<AreaInfoModel> getChildAreaInfo(String areaCode) {
		return SysCfgMgr.getIntance(mcontext).loadTreadAreaList(areaCode);
	}

}
