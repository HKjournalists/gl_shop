package com.glshop.net.ui.basic.fragment.message;

import android.content.Intent;

import com.glshop.net.common.GlobalAction;
import com.glshop.net.ui.basic.adapter.MessageListAdapter;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.setting.MessageDetailActivity;
import com.glshop.platform.api.DataConstants;

import java.io.Serializable;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : 消息列表-资讯类型
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/9/21  17:16
 */
public class MessageAdvisoryFragment extends BaseMessageListFragment {

    @Override
    protected void initArgs() {
        super.initArgs();
        message_type="4";
    }

    @Override
    protected BasicAdapter getAdapter() {


        mAdapter=new MessageListAdapter(mContext,mInitData, getHandler());
        return mAdapter;
    }

    @Override
    protected void handleItemClick(Object info) {
        Intent intent = new Intent(mContext, MessageDetailActivity.class);
        intent.putExtra(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_INFO, (Serializable) info);
        startActivity(intent);
    }

    @Override
    protected DataConstants.SystemMsgType getSysMsgType() {
        return DataConstants.SystemMsgType.ADVISORY;
    }

}
