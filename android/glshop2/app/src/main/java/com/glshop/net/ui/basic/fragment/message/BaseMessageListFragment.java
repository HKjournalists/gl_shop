package com.glshop.net.ui.basic.fragment.message;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.DataStatus;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.cache.DataCenter;
import com.glshop.net.logic.message.IMessageLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.basic.BasicFragment;
import com.glshop.net.ui.basic.adapter.base.BasicAdapter;
import com.glshop.net.ui.basic.view.PullRefreshListView;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : veidy
 * @version : 1.0
 * @Description : glshop2
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络科技有限公司
 * @Create Date  : 2015/9/21  17:16
 */
public abstract class BaseMessageListFragment<T> extends BasicFragment implements OnItemClickListener {

//    protected static final String TAG="BaseMessageListFragment";
    protected BasicAdapter<T> mAdapter;
    protected PullRefreshListView mlvmessageList;
    protected boolean isRestored = false;
    protected boolean hasNextPage = true;
    protected ArrayList<T> mInitData = new ArrayList<>();
    protected OnFragmentInteractionListener fragmentInteractionListener;
    protected IMessageLogic mMessageLogic;
    protected String message_type;
    protected int unReadTotalSize = 0;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    private boolean isEditor=false;
    @Override
    protected void initArgs() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate");
        if (savedInstanceState != null) {
            Logger.d(TAG, "onSaveInstanceState != null");
            mInitData = (ArrayList<T>) savedInstanceState.getSerializable(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_LIST);
            if (mInitData != null) {
                isRestored = true;
                //Logger.e(TAG, "InitData = " + mInitData);
            }
        }
    }

    @Override
    protected void initLogics() {
        super.initLogics();
        mMessageLogic = LogicFactory.getLogicByClass(IMessageLogic.class);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d(TAG, "onSaveInstanceState");
        if (mAdapter.getList().size() > 0) {
            outState.putSerializable(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_LIST, (ArrayList<T>) mAdapter.getList());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_message_list, container, false);
        Logger.d(TAG, "onCreateView"+"isPrepared="+isPrepared);
        initView();
//        isPrepared = true;
//        onLoadData();
         initData();
        return mRootView;
    }

    @Override
    protected void onLoadData() {
        if (!isPrepared||!isVisible){
            return;
        }
        initData();
    }

    private void initView() {

        initLoadView();
        mIvEmpty.setBackgroundResource(R.mipmap.icon_empty_msg);
        mTvEmtpyData.setText(getResources().getString(R.string.tip_empty_message));
       LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) mTvEmtpyData.getLayoutParams();
        layoutParams.topMargin=20;
        mTvEmtpyData.setLayoutParams(layoutParams);
        mlvmessageList = (PullRefreshListView) mRootView.findViewById(R.id.lv_message_list);
        mlvmessageList.setIsRefreshable(true);
        mlvmessageList.hideFootView();

        setOnRefreshListener(mlvmessageList);
        setOnScrollListener(mlvmessageList);
        mlvmessageList.setOnItemClickListener(this);

        mNormalDataView = mlvmessageList;
    }

    private void initData() {
        mAdapter = getAdapter();
        mlvmessageList.setAdapter(mAdapter);
        if (!isRestored) {
            updateDataStatus(GlobalConstants.DataStatus.LOADING);
            mMessageLogic.getMessageListFromServer(getCompanyId(), getSysMsgType(), DEFAULT_INDEX, PAGE_SIZE, GlobalConstants.DataReqType.INIT);

        } else {
            isRestored = false;
            updateDataStatus(GlobalConstants.DataStatus.NORMAL);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        T info = mAdapter.getItem(position - mlvmessageList.getHeaderViewsCount());
        if (!isEditor)
        handleItemClick(info);
        else{
            Message message=getHandler().obtainMessage();
            MessageInfoModel messageInfoModel= (MessageInfoModel)info;
            if (messageInfoModel.select)
                messageInfoModel.select=false;
            else
                messageInfoModel.select=true;

            message.obj =messageInfoModel;
            mAdapter.notifyDataSetChanged();
            message.what = GlobalMessageType.MsgCenterMessageType.MSG_SELECT_MSG_NUM;
            sendMessage(message);
        }

    }

    @Override
    protected void onRefresh() {
        Logger.e(TAG, "---onRefresh---");
        mMessageLogic.getMessageListFromServer(getCompanyId(), getSysMsgType(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
    }

    @Override
    protected void onScrollMore() {
        Logger.e(TAG, "---onLoadMore---");
        if (hasNextPage) {
            mlvmessageList.showLoading();
            mMessageLogic.getMessageListFromServer(getCompanyId(), getSysMsgType(), pageIndex, PAGE_SIZE, DataReqType.MORE);
        } else {
            mlvmessageList.showLoadFinish();
        }
    }

    @Override
    public void onReloadData() {
        super.onReloadData();
        Logger.e(TAG, "---onReloadData---");
        mlvmessageList.scrollTop();
        updateDataStatus(DataStatus.LOADING);
        mMessageLogic.getMessageListFromServer(getCompanyId(), getSysMsgType(), DEFAULT_INDEX, PAGE_SIZE, DataReqType.REFRESH);
    }

    protected abstract BasicAdapter<T> getAdapter();

    protected abstract void handleItemClick(T info);

    protected abstract DataConstants.SystemMsgType getSysMsgType();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentInteractionListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        Logger.d(TAG, "handleStateMessage: what = " + message.what + " & RespInfo = " + message.obj);
        RespInfo respInfo = getRespInfo(message);
        switch (message.what) {
            case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_LIST_SUCCESS:
                onGetSuccess(respInfo);
                break;
            case GlobalMessageType.MsgCenterMessageType.MSG_GET_MESSAGE_LIST_FAILED:
                onGetFailed(respInfo);
                break;
            case GlobalMessageType.MsgCenterMessageType.MSG_SELECT_MSG_NUM:
                //选中的消息
                MessageInfoModel messageInfoModel= (MessageInfoModel) message.obj;
                List<MessageInfoModel> data = DataCenter.getInstance().getData(getDataType());
                List<MessageInfoModel> select=new ArrayList<>();
                for (MessageInfoModel infoModel : data) {
                    if (infoModel.id.equals(messageInfoModel.id)){
                        Logger.d(TAG,"infoModel.id="+infoModel.id);
                        infoModel.select=messageInfoModel.select;
                    }
                    if (infoModel.select==true){
                        Logger.d(TAG,"infoModel.select id="+infoModel.id);
                        select.add(infoModel);
                    }
                }
                ResultItem resultItem=new ResultItem();
                Logger.d(TAG,"select===="+select.size());
                resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_EDITOR,select);
                fragmentInteractionListener.onFragmentInteraction(resultItem);

                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetSuccess(RespInfo respInfo) {
        Logger.d(TAG,"respInfo.intArg2="+respInfo.intArg2);
        if (respInfo != null && respInfo.intArg2 == getSysMsgType().toValue()) {
            GlobalConstants.DataReqType type = DataReqType.convert(respInfo.intArg1);
            int size = (Integer) respInfo.data;
            unReadTotalSize = respInfo.intArg2;
            Logger.d(TAG, "type=" + type.toValue() + "size=" + size + "getSysMsgType()=" + getSysMsgType());
            if (type == DataReqType.INIT && size == 0) {
                updateDataStatus(GlobalConstants.DataStatus.EMPTY);
            } else {
                updateDataStatus(GlobalConstants.DataStatus.NORMAL);
                if (type == DataReqType.REFRESH) {
                    pageIndex = DEFAULT_INDEX;
                    if (BeanUtils.isEmpty(DataCenter.getInstance().getData(getDataType()))) {
                        updateDataStatus(DataStatus.EMPTY);
                    }
                }
                Logger.d(TAG, "getDataType()=" + getDataType() + "datacenter=" + DataCenter.getInstance().getData(getDataType()).size());
                List data=DataCenter.getInstance().getData(getDataType());
                for (int i = 0; i < data.size(); i++) {
                    MessageInfoModel message= (MessageInfoModel) data.get(i);
                    if (isEditor)
                        message.editor=true;
                    else
                        message.editor=false;
                }
                mAdapter.setList(data);
                mlvmessageList.onRefreshSuccess();
                if (type == DataReqType.INIT||type==DataReqType.REFRESH) {
                    mlvmessageList.setSelection(0);
                }
                //if (type == DataReqType.INIT || type == DataReqType.MORE) {
                if (size > 0) {
                    pageIndex++;
                }
                hasNextPage = size >= PAGE_SIZE;
//                if (hasNextPage) {
//                    mlvmessageList.showLoading();
//                } else {
//                    mlvmessageList.showLoadFinish();
//                }
                //}
            }

        }
    }

    private void onGetFailed(RespInfo respInfo) {
        if (respInfo != null) {
            handleErrorAction(respInfo);
            DataReqType type = DataReqType.convert(respInfo.intArg1);
            if (type == DataReqType.MORE) {
                mlvmessageList.showLoadFail();
            } else {
                updateDataStatus(DataStatus.ERROR);
            }
        }
    }

    @Override
    protected void updateDataStatus(DataStatus status) {
        super.updateDataStatus(status);
        List<MessageInfoModel> data =DataCenter.getInstance().getData(getDataType());
        ResultItem resultItem = new ResultItem();
        if (BeanUtils.isEmpty(data)) {
            resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EMPYT, true);
        }else {
            resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EMPYT, false);
        }

        if (GlobalConstants.DataStatus.EMPTY==status){
            mIvEmpty.setBackgroundResource(R.mipmap.icon_empty_msg);
            mTvEmtpyData.setText(getResources().getString(R.string.tip_empty_message));
            if (getUserVisibleHint()){
                Logger.d(TAG,"可见");
                fragmentInteractionListener.onFragmentInteraction(resultItem);
            }else {
                Logger.d(TAG,"不可见");
            }
        }else if(GlobalConstants.DataStatus.NORMAL==status){
            if (getUserVisibleHint()){
                Logger.d(TAG,"可见");
                fragmentInteractionListener.onFragmentInteraction(resultItem);
            }else {
                Logger.d(TAG,"不可见");
            }
        }
    }

    protected int getDataType() {
        int dataType = DataCenter.DataType.MESSAGE_ALL_LIST;
        switch (getSysMsgType()) {
            case ALL:
                dataType = DataCenter.DataType.MESSAGE_ALL_LIST;
                break;
            case SYSTEM:
                dataType = DataCenter.DataType.MESSAGE_SYSTEM_LIST;
                break;
            case TRANSACTION:
                dataType = DataCenter.DataType.MESSAGE_TRANSACTION_LIST;
                break;
            case ADVISORY:
                dataType = DataCenter.DataType.MESSAGE_INFO_LIST;
                break;
            case ACTIVE:
                dataType = DataCenter.DataType.MESSAGE_ACTIVITY_LIST;
                break;
        }
        return dataType;
    }
    public void ToAllFragment(ResultItem uri) {
        Logger.d(TAG, "uri");
        boolean editor = uri.getBoolean(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EDITOR);
        boolean selectorall = uri.getBoolean(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_ALL);
        Logger.d(TAG, "editor=" + editor+"  selector="+selectorall);
        List<MessageInfoModel> data =DataCenter.getInstance().getData(getDataType());
        if (BeanUtils.isNotEmpty(data)) {
            if (editor) {
                isEditor=true;
                mlvmessageList.setIsRefreshable(false);
                for (MessageInfoModel messageInfoModel : data) {

                    messageInfoModel.editor = true;
                    if (selectorall) {
                        messageInfoModel.select = true;
                    } else {
                        messageInfoModel.select = false;
                    }
                }
                ResultItem resultItem = new ResultItem();
                if (selectorall) {

                    Logger.d(TAG, "selectall====" + data.size());
                    resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_SELECT_EDITOR, data);

                }else {

                }
                fragmentInteractionListener.onFragmentInteraction(resultItem);
            } else {
                isEditor=false;
                mlvmessageList.setIsRefreshable(true);
                for (MessageInfoModel messageInfoModel : data) {

                    messageInfoModel.editor = false;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d(TAG,"isVisibleToUser="+isVisibleToUser);
        if (isVisibleToUser) {
            List<MessageInfoModel> data =DataCenter.getInstance().getData(getDataType());
            Logger.d(TAG,"data="+data.size());
            ResultItem resultItem = new ResultItem();
            if (BeanUtils.isEmpty(data)) {
                resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EMPYT, true);
            }else {
                resultItem.put(GlobalAction.MessageAction.EXTRA_KEY_MESSAGE_EMPYT, false);
            }
            fragmentInteractionListener.onFragmentInteraction(resultItem);
        }
    }


}
