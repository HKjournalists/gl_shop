package com.glshop.net.ui.basic.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.glshop.net.R;

/**
 * Created by guoweilong on 2015/8/7.
 *自定义对话框
 */
public class CommonDialog extends BaseDialog {

    private Context mContext;
    /**
     * 底部弹出的Dialog.
     */
    private Dialog mBottomDialog;

    /**
     * 居中弹出的Dialog.
     */
    private Dialog mCenterDialog;

    /**
     * 顶部弹出的Dialog.
     */
    private Dialog mTopDialog;

    /**
     * 底部弹出的Dialog的View.
     */
    private View mBottomDialogView = null;

    /**
     * 居中弹出的Dialog的View.
     */
    private View mCenterDialogView = null;

    /**
     * 顶部弹出的Dialog的View.
     */
    private View mTopDialogView = null;

    /**
     * 弹出的Dialog的左右边距.
     */
    private int dialogPadding =0;

    /**
     * 屏幕宽度.
     */
    public int diaplayWidth = 320;

    /**
     * 屏幕高度.
     */
    public int diaplayHeight = 480;

    /**
     * 顶部Dialog.
     */
    public static final int DIALOG_TOP=1;

    /**
     * 中间Dialog.
     */
    public static final int DIALOG_CENTER=2;

    /**
     * 底部Dialog.
     */
    public static final int DIALOG_BOTTOM=3;

    /**
     * 设置点击屏幕Dialog是否消失.
     */
    private boolean CanceledOnTouchOutside=false;

    public CommonDialog(Context context) {
        super(context);
        // 获取分辨率
        mContext=context;
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        mContext=context;
    }

    public CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;

    }

    public CommonDialog(Context context,double width) {
        super(context);
        // 获取分辨率
        mContext=context;
        diaplayWidth= (int) width;
    }
    /**
     * 描述：在底部显示一个Dialog,id为1,在中间显示id为2.
     *
     * @param id   Dialog的类型
     * @param view 指定一个新View
     */
    public void showDialog(int id, View view) {

        if (id == DIALOG_BOTTOM) {
            mBottomDialogView = view;
            if (mBottomDialog == null) {
                mBottomDialog = new Dialog(mContext);
                setDialogLayoutParams(mBottomDialog, dialogPadding, Gravity.BOTTOM);
            }
            mBottomDialog.setContentView(mBottomDialogView, new ViewGroup.LayoutParams(diaplayWidth - dialogPadding, ViewGroup.LayoutParams.WRAP_CONTENT));
            mBottomDialog.show();
        } else if (id == DIALOG_CENTER) {
            mCenterDialogView = view;
            if (mCenterDialog == null) {
                mCenterDialog = new Dialog(mContext);
                setDialogLayoutParams(mCenterDialog, dialogPadding, Gravity.CENTER);
            }
            mCenterDialog.setContentView(mCenterDialogView, new ViewGroup.LayoutParams(diaplayWidth - dialogPadding, ViewGroup.LayoutParams.WRAP_CONTENT));
            mCenterDialog.show();
        } else if (id ==DIALOG_TOP) {
            mTopDialogView = view;
            if (mTopDialog == null) {
                mTopDialog = new Dialog(mContext);
                setDialogLayoutParams(mTopDialog, dialogPadding, Gravity.TOP);
            }
            mTopDialog.setContentView(mTopDialogView, new ViewGroup.LayoutParams(diaplayWidth - dialogPadding, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTopDialog.show();
        } else {
            Log.i("CommonDialog", "Dialog的ID传错了");
        }
    }


    /**
     * 描述：设置弹出Dialog的属性.
     *
     * @param dialog        弹出Dialog
     * @param dialogPadding 如果Dialog不是充满屏幕，要设置这个值
     * @param gravity       the gravity
     */
    private void setDialogLayoutParams(Dialog dialog, int dialogPadding, int gravity) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.white);
        WindowManager.LayoutParams lp = window.getAttributes();
        // 此处可以设置dialog显示的位置
        window.setGravity(gravity);

        // 设置宽度
        lp.width = diaplayWidth - dialogPadding;

        lp.width = diaplayWidth;
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        // 背景透明
        // lp.screenBrightness = 0.2f;
        // lp.alpha = 0.8f;
        // lp.dimAmount = 0f;
        window.setAttributes(lp);
        // 添加动画
        window.setWindowAnimations(android.R.style.Animation_Dialog);
        // 设置点击屏幕Dialog不消失
        dialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);

    }

    /**
     * 描述：关闭Dialog
     *
     * @param id   Dialog的类型
     * @param view 指定一个新View
     */
    public void dismissDialog(int id, View view) {

        if (id == CommonDialog.DIALOG_BOTTOM) {
            mBottomDialogView = view;
            if (mBottomDialog != null && mBottomDialog.isShowing()) {
                mBottomDialog.dismiss();
            }
        } else if (id ==  CommonDialog.DIALOG_CENTER) {
            mCenterDialogView = view;
            if (mCenterDialog != null && mCenterDialog.isShowing()) {
                mCenterDialog.dismiss();
            }
        } else if (id ==  CommonDialog.DIALOG_TOP) {
            mTopDialogView = view;
            if (mTopDialog != null && mTopDialog.isShowing()) {
                mTopDialog.dismiss();
            }
        } else {
            Log.i("CommonDialog", "Dialog的ID传错了");
        }
    }

    public boolean isCanceledOnTouchOutside() {
        return CanceledOnTouchOutside;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        CanceledOnTouchOutside = canceledOnTouchOutside;
    }
}
