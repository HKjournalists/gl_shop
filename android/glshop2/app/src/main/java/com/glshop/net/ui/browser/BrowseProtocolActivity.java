package com.glshop.net.ui.browser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.platform.utils.Logger;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description : 查看各种协议
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class BrowseProtocolActivity extends BasicActivity {

	private static final String TAG = "BrowseProtocolActivity";

	private TextView mTvTitle;

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_protocol);
		initView();
		initData();
	}

	private void initView() {
		mTvTitle = (TextView) findViewById(R.id.tv_commmon_title);
		mWebView = (WebView) findViewById(R.id.wv_browser_protocol);
		((ImageView) findViewById(R.id.iv_common_back)).setOnClickListener(this);

		WebSettings settings = mWebView.getSettings();

		// 这个得需要，支持JS脚本
		settings.setJavaScriptEnabled(true);
		//DOM 解析，很重要Uncaught TypeError 錯誤的解決方法
		settings.setDomStorageEnabled(true);

		settings.setLoadWithOverviewMode(true);

		settings.setUseWideViewPort(true);

		settings.setSupportZoom(true);

		settings.setBuiltInZoomControls(true);
		//settings.setUseWideViewPort(true); //禁用此属性，避免导致协议不能完整显示
		settings.setPluginState(WebSettings.PluginState.ON);//设置webview支持插件
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				Logger.i(TAG, "onProgressChanged & progress = " + progress);
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				super.onShowCustomView(view, callback);
			}
		});

		mWebView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Logger.i(TAG, "shouldOverrideUrlLoading & url = " + url);

				view.loadUrl(url);
				return false;
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

		mWebView.setWebChromeClient(new WebChromeClient());

	}

	private void initData() {
		String title = getIntent().getStringExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_TITLE);
		mTvTitle.setText(title);

		String url = getIntent().getStringExtra(GlobalAction.BrowserAction.EXTRA_BROWSE_PROTOCOL_URL);
		mWebView.loadUrl(url);

		Logger.i(TAG, "Url = " + url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		//正常退出webveiw后，停止视频播放
		try {
			mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
