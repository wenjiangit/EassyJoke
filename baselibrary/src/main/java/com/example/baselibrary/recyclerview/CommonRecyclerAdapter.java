package com.example.baselibrary.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的RecyclerView Adapter
 * Created by douliu on 2017/5/12.
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder>{

    protected Context mContext;
    protected List<T> mData;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private MultiTypeSupport<T> mMultiTypeSupport;


    public CommonRecyclerAdapter(@NonNull Context context, List<T> data, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    public CommonRecyclerAdapter(@NonNull Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getTypeAndLayoutId(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }

        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position));

    }

    protected abstract void convert(ViewHolder holder, T item);

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
