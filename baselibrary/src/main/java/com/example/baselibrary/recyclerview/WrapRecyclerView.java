package com.example.baselibrary.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * Created by douliu on 2017/5/15.
 */

public class WrapRecyclerView extends RecyclerView{


    private WrapRecyclerAdapter mWrapAdapter;

    private RecyclerView.Adapter mAdapter;


    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
            }

         }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyItemInserted(positionStart);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyItemRemoved(positionStart);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            if (mAdapter != mWrapAdapter) {
                mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
            }

        }
    };

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;

        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapAdapter = new WrapRecyclerAdapter(adapter);
        }

        super.setAdapter(mWrapAdapter);

        // 注册一个观察者
        mAdapter.registerAdapterDataObserver(mObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mWrapAdapter.adjustSpanSize(this);

    }


    public void addHeaderView(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.addFooterView(view);
        }
    }

    public void removeHeader(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.removeHeader(view);
        }
    }

    public void removeFooter(View view) {
        if (mWrapAdapter != null) {
            mWrapAdapter.removeFooter(view);
        }
    }

    public boolean isHeaderPosition(int position) {
        return mWrapAdapter.isHeaderPosition(position);
    }


    public boolean isHeaderOrFooterPostion(int position) {
        return mWrapAdapter != null && (mWrapAdapter.isHeaderPosition(position)
                || mWrapAdapter.isFooterPosition(position));
    }

}
