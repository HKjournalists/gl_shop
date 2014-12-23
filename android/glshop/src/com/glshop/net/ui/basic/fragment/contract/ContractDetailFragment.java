package com.glshop.net.ui.basic.fragment.contract;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.contract.IContractLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.mycontract.UfmContractInfoActivity;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 合同详情Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class ContractDetailFragment extends BasicFragment {

	private static final String TAG = "ContractDetailFragment";

	private WebView mWebView;

	private String mContractId;

	private ContractModelInfo mModelInfo;

	private boolean isRestored = false;

	private IContractLogic mContractLogic;

	private Callback mCallback;

	public ContractDetailFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
		if (savedInstanceState != null) {
			mModelInfo = (ContractModelInfo) savedInstanceState.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
			if (mModelInfo != null) {
				isRestored = true;
				Logger.e(TAG, "InitData = " + mModelInfo);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_contact_detail, container, false);
		initView();
		initData();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		mContractId = bundle.getString(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID);
	}

	private void initView() {
		initLoadView();
		mNormalDataView = getView(R.id.ll_contract_detail);

		mWebView = (WebView) getView(R.id.wv_contract_detail_model);

		WebSettings settings = mWebView.getSettings();

		// 这个得需要，支持JS脚本
		settings.setJavaScriptEnabled(true);
		//settings.setUseWideViewPort(true); //禁用此属性，避免导致协议不能完整显示 
		//settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		//settings.setLoadWithOverviewMode(true);

		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				Logger.i(TAG, "onProgressChanged & progress = " + progress);
			}
		});

		mWebView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Logger.i(TAG, "shouldOverrideUrlLoading & url = " + url);
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {

			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Logger.e(TAG, "onReceivedError");
				//view.loadUrl("file:///android_asset/error.html");
			}

		});
	}

	private void initData() {
		//mWebView.loadUrl("http://www.glshop.com.cn");
		if (!isRestored) {
			updateDataStatus(DataStatus.LOADING);
			mContractLogic.getContractModel(mContractId);
		} else {
			isRestored = false;
			updateDataStatus(DataStatus.NORMAL);
			updateUI();
		}
	}

	@Override
	protected void onReloadData() {
		updateDataStatus(DataStatus.LOADING);
		mContractLogic.getContractModel(mContractId);
	}

	private void updateUI() {
		if (mModelInfo != null) {
			String summary = "";
			if (mModelInfo.buyType == BuyType.BUYER) {
				summary = getString(R.string.buy) + mModelInfo.sellCompanyName + mModelInfo.productName;
			} else {
				summary = getString(R.string.sell) + mModelInfo.sellCompanyName + mModelInfo.productName;
			}
			((TextView) getView(R.id.tv_contract_name)).setText(/*mModelInfo.contractName*/summary);
			((TextView) getView(R.id.tv_contract_first_party)).setText(mModelInfo.buyCompanyName);
			((TextView) getView(R.id.tv_contract_second_party)).setText(mModelInfo.sellCompanyName);
			byte[] htmlContent = Base64.decode(mModelInfo.content, Base64.DEFAULT);
			Logger.i(TAG, "ContractContent = " + new String(htmlContent));
			mWebView.loadDataWithBaseURL(null, new String(htmlContent), "text/html", "UTF-8", null);
			if (mCallback != null) {
				mCallback.update(mModelInfo);
			}
		}
	}

	@Override
	protected void handleStateMessage(Message message) {
		Logger.d(TAG, "handleStateMessage: what = " + message.what);
		RespInfo respInfo = getRespInfo(message);
		switch (message.what) {
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACT_MODEL_SUCCESS:
			onGetSuccess(respInfo);
			break;
		case GlobalMessageType.ContractMessageType.MSG_GET_CONTRACT_MODEL_FAILED:
			onGetFailed(respInfo);
			break;
		}
	}

	private void onGetSuccess(RespInfo respInfo) {
		if (respInfo.data != null) {
			mModelInfo = (ContractModelInfo) respInfo.data;
			if (mModelInfo != null) {
				updateDataStatus(DataStatus.NORMAL);
				updateUI();
			} else {
				updateDataStatus(DataStatus.EMPTY);
			}
		} else {
			updateDataStatus(DataStatus.EMPTY);
		}
	}

	private void onGetFailed(RespInfo respInfo) {
		updateDataStatus(DataStatus.ERROR);
		handleErrorAction(respInfo);
	}

	@Override
	protected void updateDataStatus(DataStatus status) {
		super.updateDataStatus(status);
		if (getActivity() instanceof UfmContractInfoActivity) {
			((UfmContractInfoActivity) getActivity()).updateDataStatus(status);
		}
	}

	public ContractModelInfo getContractInfo() {
		return mModelInfo;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mModelInfo != null) {
			outState.putSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO, mModelInfo);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof Callback) {
			mCallback = (Callback) activity;
		}
	}

	@Override
	protected void initLogics() {
		mContractLogic = LogicFactory.getLogicByClass(IContractLogic.class);
	}

	public interface Callback {

		public void update(ContractModelInfo info);

	}

}
