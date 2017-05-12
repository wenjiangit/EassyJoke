package com.example.framelibrary.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselibrary.navigationbar.AbsNavigationBar;
import com.example.framelibrary.R;

/**
 * 默认的导航栏
 * Created by douliu on 2017/5/8.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.NavigationParams> {

    public DefaultNavigationBar(Builder.NavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.navigationbar;
    }

    @Override
    public void applyView() {
        if (!TextUtils.isEmpty(mParams.mTitle)) {
            setText(R.id.tv_title, mParams.mTitle);
        }
        if (!TextUtils.isEmpty(mParams.mLeftText)) {
            setText(R.id.tv_left, mParams.mLeftText);
        }
        if (!TextUtils.isEmpty(mParams.mRightText)) {
            setText(R.id.tv_right, mParams.mRightText);
        }
        if (mParams.mLeftIcon != 0) {
            setImageResource(R.id.iv_left, mParams.mLeftIcon);
        }
        if (mParams.mRightIcon != 0) {
            setImageResource(R.id.iv_right, mParams.mRightIcon);
        }
        if (mParams.mRightClickListener != null) {
            setOnClickListener(R.id.tv_right, mParams.mRightClickListener);
            setOnClickListener(R.id.iv_right, mParams.mRightClickListener);
        }
        if (mParams.mLeftClickListener != null) {
            setOnClickListener(R.id.tv_left, mParams.mLeftClickListener);
            setOnClickListener(R.id.iv_left, mParams.mLeftClickListener);
        }
        if (mParams.mBackGroundColor != 0) {
            findView(R.id.rl_title_bar).setBackgroundColor(getColor(mParams.mBackGroundColor));
        }
        if (mParams.mCanBack) {
            setVisible(R.id.iv_left, true);
            setOnClickListener(R.id.iv_left, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParams.mContext instanceof AppCompatActivity) {
                        ((AppCompatActivity) mParams.mContext).finish();
                    }
                }
            });
        } else {
            setVisible(R.id.iv_left, false);
        }
    }

    public static class Builder extends AbsNavigationBar.Builder {

        final NavigationParams P;

        public Builder(Activity activity) {
            this(activity, (ViewGroup) activity.findViewById(android.R.id.content)
                    .findViewById(R.id.fl_title_bar));
        }

        public Builder(Context context, ViewGroup parent) {
            P = new NavigationParams(context, parent);
        }

        public Builder setTitle(CharSequence text) {
            P.mTitle = text;
            return this;
        }

        public Builder setRightText(CharSequence text) {
            P.mRightText = text;
            return this;
        }

        public Builder setRightText(@StringRes int resId) {
            this.setRightText(P.mContext.getText(resId));
            return this;
        }

        public Builder setLeftText(CharSequence text) {
            P.mLeftText = text;
            return this;
        }

        public Builder setLeftText(@StringRes int resId) {
            this.setLeftText(P.mContext.getText(resId));
            return this;
        }

        public Builder setRightIcon(@DrawableRes int resId) {
            P.mRightIcon = resId;
            return this;
        }

        public Builder setLeftIcon(@DrawableRes int resId) {
            P.mLeftIcon = resId;
            return this;
        }

        public Builder setRightClick(View.OnClickListener listener) {
            P.mRightClickListener = listener;
            return this;
        }

        public Builder setLeftClick(View.OnClickListener listener) {
            P.mLeftClickListener = listener;
            return this;
        }

        public Builder setBackGroundColor(@ColorRes int resId) {
            P.mBackGroundColor = resId;
            return this;
        }

        public Builder setBack(boolean canBack) {
            P.mCanBack = canBack;
            return this;
        }


        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(P);
        }


        static class NavigationParams extends AbsNavigationBar.Builder.NavigationParams {

            CharSequence mTitle;
            CharSequence mRightText;
            CharSequence mLeftText;
            @DrawableRes
            int mRightIcon;
            @DrawableRes
            int mLeftIcon;
            View.OnClickListener mRightClickListener;
            View.OnClickListener mLeftClickListener;
            @ColorRes
            int mBackGroundColor;
            boolean mCanBack = true;

            public NavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }


    }

}
