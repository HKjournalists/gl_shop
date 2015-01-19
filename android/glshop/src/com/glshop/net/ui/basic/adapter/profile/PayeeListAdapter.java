package com.glshop.net.ui.basic.adapter.profile;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;

/**
 * @Description : 收款人列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class PayeeListAdapter extends BasicAdapter<PayeeInfoModel> {

	/**
	 * 当前选中收款人
	 */
	private PayeeInfoModel mSelectedPayeeInfo;

	public PayeeListAdapter(Context context, List<PayeeInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_payee_list_item, null);
		}

		PayeeInfoModel model = getItem(position);

		TextView mTvPayeeName = ViewHolder.get(convertView, R.id.tv_payee_name);
		mTvPayeeName.setText(String.valueOf(model.name));

		TextView mTvPayeeBank = ViewHolder.get(convertView, R.id.tv_payee_bank);
		mTvPayeeBank.setText(String.valueOf(model.bankName));

		TextView mTvPayeeCard = ViewHolder.get(convertView, R.id.tv_payee_card);
		mTvPayeeCard.setText(String.valueOf(model.card));

		TextView mTvDefaultPayee = ViewHolder.get(convertView, R.id.tv_default_payee);
		ImageView mIvSelectPayee = ViewHolder.get(convertView, R.id.iv_selected_payee);

		mTvDefaultPayee.setVisibility(model.isDefault ? View.VISIBLE : View.GONE);

		if (mSelectedPayeeInfo != null) {
			mIvSelectPayee.setVisibility(mSelectedPayeeInfo.id.equals(model.id) ? View.VISIBLE : View.GONE);
		} else {
			mIvSelectPayee.setVisibility(model.isDefault ? View.VISIBLE : View.GONE);
			// 管理列表显示审核状态信息
			TextView mIvPayeeStatus = ViewHolder.get(convertView, R.id.tv_payee_status);
			switch (model.status) {
			case AUTHED:
				mIvPayeeStatus.setVisibility(View.GONE);
				break;
			case AUTHING:
				mIvPayeeStatus.setVisibility(View.VISIBLE);
				mIvPayeeStatus.setText("审核中");
				break;
			case AUTH_FAILED:
				mIvPayeeStatus.setVisibility(View.VISIBLE);
				mIvPayeeStatus.setText("审核未通过");
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	public void setSelectedPayeeInfo(PayeeInfoModel info) {
		mSelectedPayeeInfo = info;
		this.notifyDataSetChanged();
	}

	public PayeeInfoModel getSelectedPayeeInfo() {
		return mSelectedPayeeInfo;
	}

}
