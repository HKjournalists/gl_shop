package com.glshop.net.ui.mypurse;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.utils.ActivityUtil;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.net.http.image.ImageLoaderManager;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 添加收款人主界面
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-17 下午5:01:00
 */
public class PayeeAddActivity extends BasicActivity {

	private EditText mEtUserName;

	private ImageView mIvItemUploadPic;

	private PayeeInfoModel info;

	private boolean isModify = false;

	private String filePath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_payee_add);
		initView();
		initData();
	}

	private void initView() {
		((TextView) getView(R.id.tv_commmon_title)).setText(R.string.add_payee);

		mEtUserName = getView(R.id.et_bank_username);
		mIvItemUploadPic = getView(R.id.iv_item_product_pic);

		getView(R.id.iv_item_product_pic).setOnClickListener(this);
		getView(R.id.btn_next_step).setOnClickListener(this);
		getView(R.id.tv_view_demo).setOnClickListener(this);
		getView(R.id.iv_common_back).setOnClickListener(this);
	}

	private void initData() {
		info = (PayeeInfoModel) getIntent().getSerializableExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO);
		if (info != null) {
			isModify = true;
			((TextView) getView(R.id.tv_commmon_title)).setText(R.string.modify_payee);
			((TextView) getView(R.id.tv_add_payee_title)).setText(R.string.modify_payee);
			mEtUserName.setText(info.name);
			mEtUserName.setSelection(mEtUserName.getText().toString().length());
			if (info.certImgInfo != null) {
				// 显示认证照片
				ImageLoaderManager.getIntance().display(this, info.certImgInfo.cloudThumbnailUrl, mIvItemUploadPic, IMAGE_DEFAULT, IMAGE_FAILED);
			}
		}

		// 个人身份不显示证明材料
		if (isPersonType()) {
			getView(R.id.item_devider_auth_pic).setVisibility(View.GONE);
			getView(R.id.ll_auth_pic).setVisibility(View.GONE);
			getView(R.id.ll_auth_name).setBackgroundResource(R.drawable.selector_item_bottom);
			int padding = getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
			getView(R.id.ll_auth_name).setPadding(padding, 0, padding, 0);
		}
	}

	/**
	 * 认证类型是否为个人
	 * @return
	 */
	private boolean isPersonType() {
		ProfileType type = GlobalConfig.getInstance().getProfileType();
		return type != null && type == ProfileType.PEOPLE;
	}

	@Override
	public void onClick(View v) {
		ActivityUtil.hideKeyboard(this);
		switch (v.getId()) {
		case R.id.btn_next_step:
			doNextStep();
			break;
		case R.id.iv_item_product_pic:
			showUploadPicTypeMenu();
			break;
		case R.id.tv_view_demo:
			ImageInfoModel info = new ImageInfoModel();
			info.resourceId = isModify ? R.drawable.bg_payee_demo_modify : R.drawable.bg_payee_demo_add;
			browseImage(info);
			break;
		case R.id.iv_common_back:
			finish();
			break;
		}
	}

	private void doNextStep() {
		if (checkArgs()) {
			Intent intent = new Intent(this, PayeeAddSubmitActivity.class);
			intent.putExtra(GlobalAction.PurseAction.EXTRA_KEY_PAYEE_INFO, getPayInfo());
			startActivity(intent);
		}
	}

	private boolean checkArgs() {
		if (StringUtils.isEmpty(mEtUserName.getText().toString().trim())) {
			showToast("姓名不能为空!");
		} else if (!isPersonType() && !isModify && StringUtils.isEmpty(filePath)) {
			showToast("图片信息不能为空!");
		} else {
			return true;
		}
		return false;
	}

	private PayeeInfoModel getPayInfo() {
		if (info == null) {
			info = new PayeeInfoModel();
			info.certImgInfo = new ImageInfoModel();
		}
		info.companyId = getCompanyId();
		info.name = mEtUserName.getText().toString().trim();
		if (!isPersonType() && StringUtils.isNotEmpty(filePath)) {
			info.certImgInfo.localFile = new File(filePath);
		}
		return info;
	}

	@Override
	protected void onPictureSelect(Bitmap bitmap, String file) {
		mIvItemUploadPic.setImageBitmap(null);
		mIvItemUploadPic.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
		filePath = file;
	}

}
