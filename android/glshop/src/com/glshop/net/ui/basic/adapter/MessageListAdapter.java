package com.glshop.net.ui.basic.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.message.data.model.MessageInfoModel;

/**
 * @Description : 消息列表适配器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public class MessageListAdapter extends BasicAdapter<MessageInfoModel> {

	public MessageListAdapter(Context context, List<MessageInfoModel> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_message_list_item, null);
		}

		MessageInfoModel model = getItem(position);

		TextView mTvTime = ViewHolder.get(convertView, R.id.iv_message_time);
		mTvTime.setText(model.dateTime);

		TextView mTvMessageContent = ViewHolder.get(convertView, R.id.iv_message_content);
		mTvMessageContent.setText(String.valueOf(model.content));

		if (model.status == MessageStatus.READED) {
			mTvMessageContent.setTextColor(mContext.getResources().getColor(R.color.gray));
		} else {
			mTvMessageContent.setTextColor(mContext.getResources().getColor(R.color.black));
		}

		return convertView;
	}

}
