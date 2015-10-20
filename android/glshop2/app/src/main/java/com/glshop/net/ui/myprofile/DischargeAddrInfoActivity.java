package com.glshop.net.ui.myprofile;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType.ProfileMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.listitem.BuyTextItemView;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 合同卸货地点详情界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class DischargeAddrInfoActivity extends BasicActivity {

	private static final String TAG = "DischargeAddrInfoActivity";

	private String addrId;

	private AddrInfoModel info;

	private TextView mTvAddrDetail1;
	private TextView mTvAddrDetail2;
	private BuyTextItemView mItemPortWaterDepth;
	private BuyTextItemView mItemPortShipTon;

	private ImageView mIvItemAddrPic1;
	private ImageView mIvItemAddrPic2;
	private ImageView mIvItemAddrPic3;

	private IContractLogic mContractLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discharge_addr_info);
		initView();
		initData();
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_addr_info);
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.contract_trade_address);

		mTvAddrDetail1 = getView(R.id.tv_addr_detail_1);
		mTvAddrDetail2 = getView(R.id.tv_addr_detail_2);

		mItemPortWaterDepth = getView(R.id.ll_item_port_water_depth);
		mItemPortShipTon = getView(R.id.ll_item_shipping_ton);

		mIvItemAddrPic1 = getView(R.id.iv_item_addr_pic_1);
		mIvItemAddrPic2 = getView(R.id.iv_item_addr_pic_2);
		mIvItemAddrPic3 = getView(R.id.iv_item_addr_pic_3);

		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		addrId = getIntent().getStringExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_ID);
		Logger.e(TAG, "AddrId = " + addrId);

		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractAddrInfo(addrId);
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractAddrInfo(addrId);
	}

	/**
	 * 更新卸货地址信息
	 */
	private void updateUI() {
		if (info != null) {
			if (StringUtils.isNEmpty(info.areaName)) {
				mTvAddrDetail1.setText(getString(R.string.data_empty));
			} else {
				mTvAddrDetail1.setText(info.areaName);
			}
			if (StringUtils.isNEmpty(info.deliveryAddrDetail)) {
				mTvAddrDetail2.setText(getString(R.string.data_empty));
			} else {
				mTvAddrDetail2.setText(info.deliveryAddrDetail);
			}
			mItemPortWaterDepth.setContentText(info.uploadPortWaterDepth != 0 ? String.valueOf(info.uploadPortWaterDepth) : getString(R.string.data_empty));
			mItemPortShipTon.setContentText(info.shippingTon != 0 ? String.valueOf(info.shippingTon) : getString(R.string.data_empty));

			List<ImageInfoModel> imgUrl = info.addrImageList;
			if (BeanUtils.isEmpty(imgUrl)) {
				getView(R.id.item_devider_discharge_addr).setVisibility(View.GONE);
				getView(R.id.ll_addr_pic).setVisibility(View.GONE);
			} else {
				getView(R.id.item_devider_discharge_addr).setVisibility(View.VISIBLE);
				getView(R.id.ll_addr_pic).setVisibility(View.VISIBLE);
				int count = imgUrl.size();
				if (count >= 1) {
					mIvItemAddrPic1.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(0) != null ? imgUrl.get(0).cloudThumbnailUrl : "", mIvItemAddrPic1, IMAGE_DEFAULT, IMAGE_FAILED);
				}
				if (count >= 2) {
					mIvItemAddrPic2.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(1) != null ? imgUrl.get(1).cloudThumbnailUrl : "", mIvItemAddrPic2, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemAddrPic2.setVisibility(View.INVISIBLE);
				}
				if (count >= 3) {
					mIvItemAddrPic3.setVisibility(View.VISIBLE);
					ImageLoaderManager.getIntance().display(this, imgUrl.get(2) != null ? imgUrl.get(2).cloudThumbnailUrl : "", mIvItemAddrPic3, IMAGE_DEFAULT, IMAGE_FAILED);
				} else {
					mIvItemAddrPic3.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case ProfileMessageType.MSG_GET_CONTRACT_ADDR_INFO_SUCCESS:
			onGetAddrInfoSuccess(respInfo);
			break;
		case ProfileMessageType.MSG_GET_CONTRACT_ADDR_INFO_FAILED:
			onGetAddrInfoFailed(respInfo);
			break;
		}
	}

	private void onGetAddrInfoSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			info = (AddrInfoModel) respInfo.data;
			if (info != null) {
				updateUI();
				updateDataStatus(DataStatus.NORMAL);
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetAddrInfoFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	protected void showErrorMsg(RespInfo respInfo) {
		if (respInfo != null) {
			switch (respInfo.respMsgType) {
			case ProfileMessageType.MSG_GET_CONTRACT_ADDR_INFO_FAILED:
				showToast(R.string.error_req_get_info);
				break;
			default:
				super.showErrorMsg(respInfo);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			ActivityUtil.hideKeyboard(this);
			finish();
			break;
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

}
