package com.glshop.net.ui.advertising;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glshop.net.GLApplication;
import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.advertising.IAdvertisingLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.advertising.weixin.Util;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.CustomPageIndicator;
import com.glshop.net.ui.basic.view.cycleview.CyclePagerAdapter;
import com.glshop.net.ui.basic.view.cycleview.CycleViewPager;
import com.glshop.net.ui.basic.view.dialog.CommonDialog;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告团购活动详情页面
 */
public class Ad_GroupPurchase extends BasicActivity implements  ViewPager.OnPageChangeListener{

    private ImageButton iv_left_back;
    private TextView tv_center_title;
    private ImageView btn_right;


    private TextView tv_ad_one_dec;

    private TextView tv_ad_two_tip;

    private TextView GroupPurchase_head_two_dec;


    private EditText et_username;

    private EditText et_userphone;

    private EditText et_neednumber;

    private EditText et_remark;

    private final static int TOTAL_SIZE=200;

    //微信分享

    private CommonDialog commonDialog;

    private View dialog_view;

    private Button btn_weixin;

    private Button btn_friend;

    private CycleViewPager mViewPager;
    private CyclePagerAdapter<View> mPagerAdapter;
    private CustomPageIndicator mPageIndicator;

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;//微信分享到朋友圈支持的最低版本号

    private IAdvertisingLogic iAdvertisingLogic;

    private LinearLayout layout_center_gift;
    private ImageView iv_gift_two;

    //动态设置礼包图片的间距
    boolean gift_hasMeasured = false;
    boolean layout_hasMeasured = false;
    private int gift_width=0;//礼包的宽度
    private int layout_width=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate()");
        setContentView(R.layout.activity_ad__group_purchase);
        initView();
        initData();

    }


    private void initView(){
        iv_left_back=getView(R.id.iv_common_back);
        tv_center_title=getView(R.id.tv_commmon_title);
        btn_right=getView(R.id.iv_commmon_action);
        tv_center_title.setText(getResources().getString(R.string.title_ad_GroupPurchase_activity));
        btn_right.setVisibility(View.VISIBLE);

        btn_right.setOnClickListener(this);
        iv_left_back.setOnClickListener(this);

        tv_ad_two_tip=getView(R.id.tv_ad_two_tip);
        GroupPurchase_head_two_dec=getView(R.id.GroupPurchase_head_two_dec);
        tv_ad_one_dec=getView(R.id.tv_ad_one_dec);

        mViewPager = getView(R.id.advert_viewpager);
        mViewPager.setOnPageChangeListener(this);


        et_username=getView(R.id.et_ad_two_usernamevalues);
        et_userphone=getView(R.id.et_ad_two_userphonevalues);
        et_neednumber=getView(R.id.et_ad_two_neednumbervalues);
        et_remark=getView(R.id.et_userremark_values);

        et_remark.addTextChangedListener(textSzieWatcher);
        getView(R.id.ibtn_freebooking).setOnClickListener(this);

        commonDialog=new CommonDialog(this,(double)width);
        commonDialog.setCanceledOnTouchOutside(true);
        dialog_view=getLayoutInflater().inflate(R.layout.weixin_share_dialog,null);
        btn_weixin= (Button) dialog_view.findViewById(R.id.btn_weixin);
        btn_friend=(Button) dialog_view.findViewById(R.id.btn_weixinfriend);
        btn_weixin.setOnClickListener(this);
        btn_friend.setOnClickListener(this);
        dialog_view.findViewById(R.id.tv_weixin_tip).setOnClickListener(this);
        dialog_view.findViewById(R.id.tv_weixinfriend_tip).setOnClickListener(this);

        layout_center_gift=getView(R.id.layout_center_gift);
        iv_gift_two=getView(R.id.gift_two);

        ViewTreeObserver vto = layout_center_gift.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!gift_hasMeasured) {

                   //先获取layout的宽度
                    layout_width= layout_center_gift.getMeasuredWidth();;
                    gift_hasMeasured = true;
                    Logger.d(TAG, "layout_width=" + layout_width);
                }
                return true;
            }
        });
        //动态算中间两个礼包imageview 的间距
        ViewTreeObserver iv_gift = iv_gift_two.getViewTreeObserver();

        iv_gift.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!layout_hasMeasured) {

                    gift_width = iv_gift_two.getMeasuredWidth();

                    layout_hasMeasured = true;
                    //算出间隔
                    int iv_margin=(layout_width-gift_width*2)/3;

                    Logger.d(TAG, "iv_gift "+ gift_width+"iv_margin="+iv_margin);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( iv_gift_two.getMeasuredWidth(),  iv_gift_two.getHeight());  // , 1是可选写的
                    lp.setMargins(iv_margin,0 , iv_margin, 0);
                    iv_gift_two.setLayoutParams(lp);
                }
                return true;
            }
        });

    }

    private void initData(){

        String values="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;洞庭湖黄砂为800里洞庭湖水下10几米底层开采清洗而成,符合国家的《建筑用砂》(GB/T14684-2001)标准。<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;蕴涵量丰富,颗粒饱满、圆润、大小均匀,含泥少、无毒无污染、抗腐耐酸碱强,品质上乘的优质洞庭湖黄砂,是房地产,高铁,轻轨,地铁,路桥和管桩公司等大型工程的主要建筑材料。";
        tv_ad_one_dec.setText(Html.fromHtml(values));

        SpannableString tv_ad_two_tip_String=new SpannableString(getResources().getString(R.string.GroupPurchase_two_dec));
        tv_ad_two_tip_String.setSpan(new BackgroundColorSpan(Color.YELLOW),8,11,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置背景色为黄色
        tv_ad_two_tip_String.setSpan(new ForegroundColorSpan(Color.RED), 8, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体颜色
        tv_ad_two_tip.setText(tv_ad_two_tip_String);

        SpannableString GroupPurchase_head_two_dec_String=new SpannableString(getResources().getString(R.string.GroupPurchase_head_two_dec));
        GroupPurchase_head_two_dec_String.setSpan(new BackgroundColorSpan(Color.YELLOW),22,28,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置背景色为黄色
        GroupPurchase_head_two_dec_String.setSpan(new ForegroundColorSpan(Color.RED), 22, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体颜色

        GroupPurchase_head_two_dec.setText(GroupPurchase_head_two_dec_String);

        List<View> advertList = new ArrayList<View>();

        View view = View.inflate(this, R.layout.adapter_advert_item, null);
        view.findViewById(R.id.iv_advert_bg).setBackgroundResource(R.mipmap.ad_banner_one);
        advertList.add(view);

        view = View.inflate(this, R.layout.adapter_advert_item, null);
        view.findViewById(R.id.iv_advert_bg).setBackgroundResource(R.mipmap.ad_banner_two);
        advertList.add(view);

        view = View.inflate(this, R.layout.adapter_advert_item, null);
        view.findViewById(R.id.iv_advert_bg).setBackgroundResource(R.mipmap.ad_banner_three);
        advertList.add(view);

        view = View.inflate(this, R.layout.adapter_advert_item, null);
        view.findViewById(R.id.iv_advert_bg).setBackgroundResource(R.mipmap.ad_banner_four);
        advertList.add(view);

        mPagerAdapter = new CyclePagerAdapter<View>(advertList,this,false);
        mViewPager.setAdapter(mPagerAdapter);

        mPageIndicator = (CustomPageIndicator) findViewById(R.id.advert_page_indicator);
        mPageIndicator.setPageCount(advertList.size());

        //mViewPager.setCurrentItem(0);
        mPageIndicator.setPageSelectedIndex(0);
        Logger.d(TAG, "Integer.MAX_VALUE / advertList.size() /2=" + advertList.size() * (Integer.MAX_VALUE / advertList.size() / 2));
//		mViewPager.setCurrentItem(advertList.size() * (Integer.MAX_VALUE / advertList.size() / 2));

        mViewPager.setCycleMoveEnabled(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d(TAG, "intent");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_common_back:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_SHOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_commmon_title:

                break;
            case R.id.iv_commmon_action:
                commonDialog.showDialog(CommonDialog.DIALOG_BOTTOM,dialog_view);
                break;
            case R.id.btn_weixin:
            case R.id.tv_weixin_tip:
                if (GLApplication.getApi().isWXAppInstalled()) {
                    weixin_share(false);//分享到微信好友
                }else {
                    showToast(getResources().getString(R.string.isWXAppInstalled));
                }
                break;
            case R.id.btn_weixinfriend:
            case R.id.tv_weixinfriend_tip:
                if (GLApplication.getApi().isWXAppInstalled()) {
                    int wxSdkVersion = GLApplication.getApi().getWXAppSupportAPI();
                    if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
                        weixin_share(true);//分享到微信朋友圈
                    } else {
                        showToast(getResources().getString(R.string.WXAppSupportAPI));
                    }
                }else {
                        showToast(getResources().getString(R.string.isWXAppInstalled));
                }
                break;
            case R.id.ibtn_freebooking:
                String username=et_username.getText().toString().trim();
                String userphone=et_userphone.getText().toString().trim();
                String userneednumber=et_neednumber.getText().toString().trim();
                String userremark=et_remark.getText().toString().trim();

                if (StringUtils.isEmpty(username)){
                    showToast(R.string.empty_name);
                    return;
                }
                if (StringUtils.isEmpty(userphone)){
                    showToast(R.string.empty_phone);
                    return;
                }
                if (StringUtils.isEmpty(userneednumber)){
                    showToast(R.string.empty_neednumber);
                    return;
                }
                if (!StringUtils.isPhoneNumber(userphone)){
                    showToast(R.string.phonenumber_format_error);
                    return;
                }
                if (!isNetConnected()) {
                    showToast(R.string.network_disconnected);
                    return;
                }
                iAdvertisingLogic.submitInfo(username,userphone,Integer.parseInt(userneednumber),userremark);
                break;
            default:
                break;
        }
    }

    private void weixin_share(boolean isWXSceneTimeline){

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://mp.weixin.qq.com/s?__biz=MzAxNzQxOTQ2OQ==&mid=218293114&idx=1&sn=8b7eafdd20caeb005ed08733c65e1b9f#rd";

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.title=getResources().getString(R.string.activity_ad_GroupPurchase_weixintitle);
        msg.description =getResources().getString(R.string.activity_ad_GroupPurchase_weixindec);

        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_share);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        if (isWXSceneTimeline) {
             req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        // 调用api接口发送数据到微信
        GLApplication.getApi().sendReq(req);
    }




    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageIndicator.setPageSelectedIndex(position % mPageIndicator.getPageCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(TAG, "onResume");
        mViewPager.startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i(TAG, "onPause");
        mViewPager.stopAnimation();
        commonDialog.dismissDialog(CommonDialog.DIALOG_BOTTOM, dialog_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"onDestroy()");
        commonDialog.dismissDialog(CommonDialog.DIALOG_BOTTOM, dialog_view);
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        Logger.d(TAG, "handleStateMessage: what = " + message.what);
        RespInfo respInfo = getRespInfo(message);
        switch (message.what) {
            case GlobalMessageType.Advertising.JOIN_GROUPBUY_SUCCESS:
                showToast(getResources().getString(R.string.join_succes));
                break;
            case GlobalMessageType.Advertising.JOIN_GROUPBUY_FAILED:
                onJoinFailed(respInfo);
                break;
            default:
                break;
        }
    }

    @Override
    protected void showErrorMsg(RespInfo respInfo) {
        super.showErrorMsg(respInfo);
    }

    @Override
    protected void initLogics() {
        Logger.d(TAG,"initLogics");
        iAdvertisingLogic= LogicFactory.getLogicByClass(IAdvertisingLogic.class);
    }

    /**
     * 加入失败
     */
    private void onJoinFailed(RespInfo respInfo){
        if (respInfo != null) {

            String errorCode = respInfo.errorCode;

            if (StringUtils.isNotEmpty(errorCode)) {
                if (DataConstants.AdverstingErrorCode.REPEATJOIN.equals(errorCode)){
                        showToast(getResources().getString(R.string.join_repeat));
                }
            }

            }

    }


    /** 计算文字的数量 */
    private TextWatcher textSzieWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null)
                return;
            int value = TOTAL_SIZE - s.toString().trim().length();
            Logger.d(TAG,"value="+value);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (TOTAL_SIZE == s.length()) {
                Toast.makeText(Ad_GroupPurchase.this,getResources().getString(R.string.remark_length),Toast.LENGTH_SHORT).show();
            }
            Logger.d(TAG,"beforeTextChanged s="+s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            Logger.d(TAG,"afterTextChanged s"+s.toString());
            if (TOTAL_SIZE<s.length()){
                Logger.d(TAG, "afterTextChanged s"+s.length());
                s.delete(TOTAL_SIZE,s.length());
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_SHOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
