package com.glshop.net.ui.basic.adapter.profile;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.net.utils.DateUtils;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;

/**
 * @Description : 企业评价列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class EvaluationListAdapter extends BasicAdapter<EvaluationInfoModel> implements View.OnClickListener {

	public EvaluationListAdapter(Context context, List<EvaluationInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_evaluation_list_item, null);
		}

		LinearLayout llContainer = ViewHolder.get(convertView, R.id.ll_list_item_container);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.buy_item_padding);
		if (position == getCount() - 1) {
			llContainer.setPadding(padding, padding, padding, padding);
		} else {
			llContainer.setPadding(padding, padding, padding, 0);
		}

		EvaluationInfoModel info = getItem(position);

		TextView tvUser = ViewHolder.get(convertView, R.id.tv_user_name);
		tvUser.setText(info.user);

		TextView tvDateTime = ViewHolder.get(convertView, R.id.tv_datetime);
		tvDateTime.setText(DateUtils.getDefaultDate(info.dateTime));

		RatingBar mRbSatisfaction = ViewHolder.get(convertView, R.id.rb_satisfaction);
		mRbSatisfaction.setRating(info.satisfactionPer);

		RatingBar mRbCredit = ViewHolder.get(convertView, R.id.rb_credit);
		mRbCredit.setRating(info.sincerityPer);

		TextView tvDetail = ViewHolder.get(convertView, R.id.tv_evaluation_detail);
		tvDetail.setText(info.content);

		Button btnDetail = ViewHolder.get(convertView, R.id.btn_evaluation_detail);
		tvDetail.setTag(position);
		tvDetail.setSingleLine(info.isSingleLine);
		btnDetail.setTag(tvDetail);
		updateDetailStatus(btnDetail, info.isSingleLine);

		btnDetail.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_evaluation_detail:
			TextView tvDetail = (TextView) v.getTag();
			int position = (Integer) tvDetail.getTag();
			EvaluationInfoModel info = getItem(position);
			info.isSingleLine = !info.isSingleLine;
			tvDetail.setSingleLine(info.isSingleLine);
			updateDetailStatus((Button) v, info.isSingleLine);
			break;
		}
	}

	private void updateDetailStatus(Button btnDetail, boolean isSingleLine) {
		Drawable icon = null;
		icon = isSingleLine ? mContext.getResources().getDrawable(R.drawable.ic_expand) : mContext.getResources().getDrawable(R.drawable.ic_shrink);
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
		btnDetail.setCompoundDrawables(icon, null, null, null);
		btnDetail.setText(isSingleLine ? mContext.getString(R.string.expand) : mContext.getString(R.string.shrink));
	}

}
