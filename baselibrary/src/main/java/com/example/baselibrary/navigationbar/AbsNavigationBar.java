package com.example.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 基类
 * Created by douliu on 2017/5/8.
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.NavigationParams> implements INavigation {

    protected P mParams;
    private View mNavigationbar;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBind();
    }

    private void createAndBind() {
        if (mParams == null) {
            return;
        }

        if (mParams.mParent == null) {
            ViewGroup decorView = (ViewGroup) ((Activity) mParams.mContext).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) decorView.getChildAt(0);
        }

        mNavigationbar = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);
        mParams.mParent.addView(mNavigationbar, 0);
        applyView();
    }

    protected void setImageResource(int viewId, int resId) {
        ImageView iv = findView(viewId);
        if (iv != null) {
            iv.setImageResource(resId);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    protected void setText(int viewId, CharSequence text) {
        TextView tv = findView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    protected int getColor(int resId) {
        return ContextCompat.getColor(mParams.mContext, resId);
    }

    protected void setVisible(int viewId, boolean visible) {
        View view = findView(viewId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int viewId) {
        return (T) mNavigationbar.findViewById(viewId);
    }

    @Override
    public abstract int bindLayoutId();

    @Override
    public abstract void applyView();


    public static abstract class Builder {

        public abstract AbsNavigationBar create();

        protected static class NavigationParams {

            public Context mContext;

            public ViewGroup mParent;

            public NavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }

    }
}
