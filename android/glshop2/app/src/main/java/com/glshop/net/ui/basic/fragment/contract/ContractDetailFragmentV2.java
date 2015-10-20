package com.glshop.net.ui.basic.fragment.contract;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.myprofile.DischargeAddrInfoActivity;
import com.glshop.net.ui.myprofile.OtherProfileActivity;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 合同详情Fragment
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-11 下午3:04:53
 */
public class ContractDetailFragmentV2 extends BasicFragment {

	private static final String TAG = "ContractDetailFragment";

	private WebView mWebView;

	private ContractModelInfo mModelInfo;

	public ContractDetailFragmentV2() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(TAG, "onCreate");
		if (savedInstanceState != null) {
			mModelInfo = (ContractModelInfo) savedInstanceState.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_INFO);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_contact_detail_v2, container, false);
		initView();
		updateUI();
		return mRootView;
	}

	@Override
	protected void initArgs() {
		Bundle bundle = this.getArguments();
		mModelInfo = (ContractModelInfo) bundle.getSerializable(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_MODEL_INFO);
	}

	private void initView() {
		getView(R.id.btn_discharge_addr).setOnClickListener(this);
		getView(R.id.btn_view_profile).setOnClickListener(this);

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

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_discharge_addr: // 交易地址
			intent = new Intent(mContext, DischargeAddrInfoActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_ADDR_ID, mModelInfo.buyId);
			startActivity(intent);
			break;
		case R.id.btn_view_profile: // 对方企业资料
			intent = new Intent(mContext, OtherProfileActivity.class);
			intent.putExtra(GlobalAction.ProfileAction.EXTRA_KEY_COMPANY_ID, getOtherCompanyId());
			startActivity(intent);
			break;
		}
	}

	private String getOtherCompanyId() {
		String companyId = "";
		if (mModelInfo != null) {
			if (mModelInfo.buyType == BuyType.BUYER) {
				companyId = mModelInfo.sellCompanyId;
			} else {
				companyId = mModelInfo.buyCompanyId;
			}
		}
		return companyId;
	}

	private void updateUI() {
		if (mModelInfo != null) {
			byte[] htmlContent = Base64.decode(mModelInfo.content, Base64.DEFAULT);
			Logger.i(TAG, "ContractContent = " + new String(htmlContent));
			mWebView.loadDataWithBaseURL(null, new String(htmlContent), "text/html", "UTF-8", null);
		}
	}

	public void setContractInfo(ContractModelInfo info) {
		mModelInfo = info;
		updateUI();
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

}
