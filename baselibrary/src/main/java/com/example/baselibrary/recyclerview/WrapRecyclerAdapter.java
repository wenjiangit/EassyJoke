package com.example.baselibrary.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by douliu on 2017/5/15.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SparseArray<View> mHeaders;

    private SparseArray<View> mFooters;

    private static int BASE_HEADER_INDEX = 1000000;

    private static int BASE_FOOTER_INDEX = 2000000;

    private RecyclerView.Adapter mAdapter;

    public WrapRecyclerAdapter(@NonNull RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        this.mHeaders = new SparseArray<>();
        this.mFooters = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderType(viewType)) {//头部
            return onCreateHeaderAndFooterViewHolder(mHeaders.get(viewType));
        } else if (isFooterType(viewType)) {//底部
            return onCreateHeaderAndFooterViewHolder(mFooters.get(viewType));
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    private boolean isHeaderType(int viewType) {
        return mHeaders.indexOfKey(viewType) >= 0;
    }

    private boolean isFooterType(int viewType) {
        return mFooters.indexOfKey(viewType) >= 0;
    }

    private RecyclerView.ViewHolder onCreateHeaderAndFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        //头部和底部不需要绑定视图
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }

        // Adapter
        final int adjPosition = position - getHeadersCount();
        int adapterCount = mAdapter.getItemCount();
        if (adjPosition < adapterCount) {
            mAdapter.onBindViewHolder(holder, adjPosition);
            if (mAdapter instanceof CommonRecyclerAdapter) {
                final CommonRecyclerAdapter.OnItemClickListener itemClickListener = ((CommonRecyclerAdapter) mAdapter).getItemClickListener();
                if (itemClickListener != null) {
                    holder.itemView.setOnClickListener(new CommonRecyclerAdapter.ItemClickListener(adjPosition) {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onItemClick(holder,adjPosition);
                        }
                    });
                }
            }
        }

    }

    public boolean isHeaderPosition(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooterPosition(int position) {
        return position >= mAdapter.getItemCount() + getHeadersCount();
    }


    @Override
    public int getItemViewType(int position) {
        // Header (negative positions will throw an IndexOutOfBoundsException)
        if (isHeaderPosition(position)) {
            return mHeaders.keyAt(position);
        }

        // Footer
        if (isFooterPosition(position)) {
            position = position - getHeadersCount() - mAdapter.getItemCount();
            return mFooters.keyAt(position);
        }

        // Adapter
        final int adjPosition = position - getHeadersCount();
        return mAdapter.getItemViewType(adjPosition);
    }

    @Override
    public int getItemCount() {
        return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
    }


    public void addHeaderView(View view) {
        if (mHeaders.indexOfValue(view) == -1) {
            mHeaders.put(BASE_HEADER_INDEX++, view);
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {
        if (mFooters.indexOfValue(view) == -1) {
            mFooters.put(BASE_FOOTER_INDEX++, view);
            notifyDataSetChanged();
        }
    }

    public void removeHeader(View view) {
        int index = mHeaders.indexOfValue(view);
        if (index != -1) {
            mHeaders.remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeFooter(View view) {
        int index = mFooters.indexOfValue(view);
        if (index != -1) {
            mFooters.remove(index);
            notifyDataSetChanged();
        }
    }

    public int getHeadersCount() {
        return mHeaders.size();
    }

    public int getFootersCount() {
        return mFooters.size();
    }


    public void adjustSpanSize(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position)
                            || isFooterPosition(position);
                    return isHeaderOrFooter ? manager.getSpanCount() : 1;
                }
            });
        }
    }





}
