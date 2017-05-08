package com.example.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 帮助类
 * Created by douliu on 2017/5/4.
 */

class DialogViewHelper {

    private View mContentView;

    private SparseArray<WeakReference<View>> mViews = new SparseArray<>();

    public DialogViewHelper(Context context, int layoutResId) {
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public DialogViewHelper(View view) {
        mContentView = view;
    }

    public View getContentView() {
        return mContentView;
    }

    public void setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        WeakReference<View> reference = mViews.get(viewId);
        View view;
        if (reference == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, new WeakReference<>(view));
        } else {
            view = reference.get();
        }
        return (T) view;
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }
}
