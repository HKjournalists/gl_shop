package com.glshop.net.ui.basic.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glshop.net.R;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.adapter.base.ViewHolder;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.SystemMsgType;
import com.glshop.platform.api.message.data.model.MessageInfoModel;

import java.util.List;

/**
 * @author : 叶跃丰
 * @version : 1.0
 *          Create Date  : 2014-7-30 上午11:26:26
 * @Description : 消息列表适配器
 * @Copyright : GL. All Rights Reserved
 * @Company : 深圳市国立数码动画有限公司
 */
public class MessageListAdapter extends BasicAdapter<MessageInfoModel> {

    public static final String TAG="MessageListAdapter";


    public Handler mHandler;

    public MessageListAdapter(Context context, List<MessageInfoModel> list) {
        super(context, list);
    }

    public MessageListAdapter(Context context, List<MessageInfoModel> list, Handler handler) {
        super(context, list);
        mHandler = handler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_message_list_item, null);
        }
        ImageView iv_line=ViewHolder.get(convertView,R.id.iv_line);
        ImageView checkbox = ViewHolder.get(convertView, R.id.checkbox);
        final MessageInfoModel model = getItem(position);

        TextView mTvType = ViewHolder.get(convertView, R.id.iv_message_type);
        if(SystemMsgType.TRANSACTION==model.type){
            mTvType.setText(mContext.getString(R.string.message_type_transaction));
        } else if(SystemMsgType.ADVISORY==model.type){
            mTvType.setText(mContext.getString(R.string.message_type_info));
        } else if(SystemMsgType.SYSTEM==model.type){
            mTvType.setText(mContext.getString(R.string.message_type_system));
        } else if(SystemMsgType.ACTIVE==model.type){
            mTvType.setText(mContext.getString(R.string.message_type_active));
        }

        TextView mTvTime = ViewHolder.get(convertView, R.id.iv_message_time);
        mTvTime.setText(model.dateTime);

        if (model.editor)
            checkbox.setVisibility(View.VISIBLE);
        else
            checkbox.setVisibility(View.GONE);

        if (model.select)
            checkbox.setBackgroundResource(R.drawable.btn_checkbox_orange_selected);
        else
            checkbox.setBackgroundResource(R.drawable.btn_checkbox_orange_unselected);
//        Logger.d(TAG,"model type="+model.type.toValue()+"model editor="+model.editor);
//        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Message message = mHandler.obtainMessage();
//                if (isChecked) {
//                    model.select = true;
//                } else {
//                    model.select = false;
//                }
//                message.obj = model;
//                message.what = GlobalMessageType.MsgCenterMessageType.MSG_SELECT_MSG_NUM;
//                mHandler.sendMessage(message);
//            }
//        });
//        if (model.select) {
//            checkbox.setChecked(true);
//        } else {
//            checkbox.setChecked(false);
//        }

        TextView mTvMessageContent = ViewHolder.get(convertView, R.id.iv_message_content);
        mTvMessageContent.setText(String.valueOf(model.content));

		if (model.status == DataConstants.MessageStatus.READED) {
            iv_line.setVisibility(View.GONE);
			mTvMessageContent.setTextColor(mContext.getResources().getColor(R.color.gray));
		} else {
            iv_line.setVisibility(View.VISIBLE);
			mTvMessageContent.setTextColor(mContext.getResources().getColor(R.color.black));
		}

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
