package com.example.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselibrary.R;
import com.example.baselibrary.utils.DensityUtil;

/**
 *
 * 自定义Dialog
 * Created by douliu on 2017/5/4.
 */

public class MyAlertDialog extends Dialog{

    final AlertController mAlert;

    private Context mContext;

    public MyAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        mAlert = new AlertController(this, getWindow());
    }

    public void setText(@IdRes int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    public void setText(@IdRes int viewId,@StringRes int id) {
        this.setText(viewId, mContext.getText(id));
    }

    public void setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId, listener);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        return mAlert.getView(viewId);
    }


    public static class Builder {

        private int mTheme;

        final AlertController.AlertParams P;

        public Builder(@NonNull Context context) {
            this(context, R.style.dialog);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            this.mTheme = themeResId;
            P = new AlertController.AlertParams(context);
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }


        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置内容
         * @param layoutResId 布局id
         * @return this
         */
        public Builder setContentView(@LayoutRes int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }


        /**
         * 设置内容
         * @param view view
         * @return this
         */
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置文本
         * @param viewId id
         * @param text 内容
         * @return this
         */
        public Builder setText(@IdRes int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置文本
         * @param viewId id
         * @param resId 资源id
         * @return this
         */
        public Builder setText(@IdRes int viewId, @StringRes int resId) {
            P.mTextArray.put(viewId, P.mContext.getText(resId));
            return this;
        }

        /**
         * 设置点击事件
         * @param viewId id
         * @param listener 监听器
         * @return this
         */
        public Builder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
            P.mClickArray.put(viewId, listener);
            return this;
        }

        /**
         * 宽度充满屏幕
         * @return this
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置位置
         * @param isAnimation 是否需要动画
         * @return this
         */
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimations = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置宽高
         * @param width dialog宽
         * @param height dialog高
         * @return this
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = DensityUtil.dp2px(P.mContext,width);
            P.mHeight = DensityUtil.dp2px(P.mContext,height);
            return this;
        }

        /**
         * 添加默认的动画
         * @return this
         */
        public Builder addDefaultAnimation() {
            P.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         * @param animation 资源id
         * @return this
         */
        public Builder setAnimations(@StyleRes int animation) {
            P.mAnimations = animation;
            return this;
        }


        public MyAlertDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final MyAlertDialog dialog = new MyAlertDialog(P.mContext, mTheme);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public MyAlertDialog show() {
            final MyAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
