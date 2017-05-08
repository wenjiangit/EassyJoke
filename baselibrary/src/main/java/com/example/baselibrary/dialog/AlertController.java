package com.example.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * dialog控制类
 * Created by douliu on 2017/5/4.
 */

class AlertController {

    private MyAlertDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;

    AlertController(MyAlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public MyAlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setText(int id, CharSequence text) {
        mViewHelper.setText(id, text);
    }

    public void setOnClickListener(int id, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(id, listener);
    }

    public <T extends View> T getView(int id) {
        return mViewHelper.getView(id);
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        mViewHelper = viewHelper;
    }

    static class AlertParams {

        public Context mContext;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mAnimations = 0;
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;


        public AlertParams(Context context) {
            this.mContext = context;
        }


        public void apply(AlertController alert) {

            //设置布局
            DialogViewHelper helper = null;
            if (mViewLayoutResId != 0) {
                helper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                helper = new DialogViewHelper(mView);
            }

            if (helper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }

            alert.getDialog().setContentView(helper.getContentView());

            alert.setViewHelper(helper);

            //设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0;i < textArraySize;i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //设置点击事件
            int clickArraySize = mClickArray.size();
            for (int i = 0;i < clickArraySize;i++) {
                alert.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            Window window = alert.getWindow();

            //设置位置
            window.setGravity(mGravity);

            //设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

        }
    }
}
