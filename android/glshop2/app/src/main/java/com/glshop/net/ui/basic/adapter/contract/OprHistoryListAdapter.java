package com.glshop.net.ui.basic.adapter.contract;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.contract.data.model.ContractOprInfoModel;

/**
 * @Description : 合同操作历史记录列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class OprHistoryListAdapter extends BasicAdapter<ContractOprInfoModel> {

	public OprHistoryListAdapter(Context context, List<ContractOprInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_opr_history_list_item, null);
		}

		ContractOprInfoModel model = getItem(position);

		TextView tvDealTime = ViewHolder.get(convertView, R.id.tv_opr_date_time);
		tvDealTime.setText(model.dateTime);

		TextView tvDealType = ViewHolder.get(convertView, R.id.tv_opr_summary);
		tvDealType.setText(model.summary);

		View itemBg = ViewHolder.get(convertView, R.id.ll_history_item_bg);
		itemBg.setBackgroundResource(position == getCount() - 1 ? R.drawable.bg_contract_history_selected : R.drawable.bg_contract_history_normal);

		return convertView;
	}

}
