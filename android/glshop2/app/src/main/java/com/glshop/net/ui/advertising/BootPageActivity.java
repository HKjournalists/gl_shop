package com.glshop.net.ui.advertising;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.glshop.net.R;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.user.IUserLogic;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.BasicActivity;
import com.glshop.net.ui.basic.view.cycleview.CyclePagerAdapter;
import com.glshop.net.ui.basic.view.cycleview.CycleViewPager;
import com.glshop.platform.base.manager.LogicFactory;
import com.glshop.platform.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class BootPageActivity extends BasicActivity {

    private CycleViewPager viewPager;
    private CyclePagerAdapter cyclePagerAdapter;
    private List<View> viewList = new ArrayList<>();
    private IUserLogic mUserLogic;
    private ImageView iv_star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_page);
        initView();
        initData();
    }

    private void initView() {
        viewPager = getView(R.id.vp_bp);
    }

    private void initData() {

        View view = View.inflate(this, R.layout.adapter_bootpage_one, null);
        viewList.add(view);

        view = View.inflate(this, R.layout.adapter_bootpage_two, null);
        viewList.add(view);

        view = View.inflate(this, R.layout.adapter_bootpage_three, null);
        iv_star = (ImageView) view.findViewById(R.id.iv_star);
        iv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHandler().sendEmptyMessageDelayed(GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE, 100);
            }
        });
        viewList.add(view);
        cyclePagerAdapter = new CyclePagerAdapter<View>(viewList, this, false, false);
        viewPager.setAdapter(cyclePagerAdapter);
        viewPager.setCycleMoveEnabled(false);
    }

    @Override
    protected void handleStateMessage(Message message) {
        super.handleStateMessage(message);
        Logger.d(TAG, "handleStateMessage: what = " + message.what);
        switch (message.what) {
            case GlobalMessageType.UserMessageType.MSG_BOOT_COMPLETE:
                autoLogin();
                gotoMainPage();
                break;
        }
    }

    private void autoLogin() {
        checkUserLogic();
        boolean result = mUserLogic.autoLogin(true);
        Logger.d(TAG, "AutoLogin result = " + result);
    }

    private void gotoMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void checkUserLogic() {
        if (mUserLogic == null) {
            mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
        }
    }

    @Override
    protected void initLogics() {
        mUserLogic = LogicFactory.getLogicByClass(IUserLogic.class);
    }

}
